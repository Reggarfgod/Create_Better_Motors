package com.reggarf.mods.create_better_motors.content.alternator.blocksentity;

import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.mrh0.createaddition.sound.CASoundScapes;

import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.util.StringFormattingTool;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;

public class CopperAlternatorBlockEntity extends KineticBlockEntity {

    /* ----------------------------- ENERGY ----------------------------- */

    protected final InternalEnergyStorage energy;
    private final LazyOptional<IEnergyStorage> lazyEnergy;

    public CopperAlternatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        energy = new InternalEnergyStorage(
                CommonConfig.COPPER_ALTERNATOR.CAPACITY.get(),
                0,
                CommonConfig.COPPER_ALTERNATOR.MAX_OUTPUT.get()
        );

        lazyEnergy = LazyOptional.of(() -> energy);
    }

    /* ----------------------------- TOOLTIP ----------------------------- */

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        CreateLang.translate("tooltip.create_better_motors.generating")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_output")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.text(" ")
                .translate(
                        "tooltip.create_better_motors.energy_per_tick",
                        StringFormattingTool.formatLong(
                                getEnergyProductionRate((int) getSpeed())
                        )
                )
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        CreateLang.translate("tooltip.create_better_motors.energy_stored")
                .style(ChatFormatting.WHITE)
                .forGoggles(tooltip);

        CreateLang.text(" ")
                .translate(
                        "tooltip.create_better_motors.energy_storage",
                        StringFormattingTool.formatLong(energy.getEnergyStored()),
                        StringFormattingTool.formatLong(energy.getMaxEnergyStored())
                )
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        return true;
    }

    /* --------------------------- CAPABILITY --------------------------- */

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ENERGY)
            return lazyEnergy.cast();
        return super.getCapability(cap, side);
    }

    public boolean isEnergyInput(Direction side) {
        return false;
    }

    public boolean isEnergyOutput(Direction side) {
        return true;
    }

    /* ------------------------------- NBT ------------------------------- */

    @Override
    public void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        energy.read(tag);
    }

    @Override
    public void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        energy.write(tag);
    }

    /* ------------------------------- TICK ------------------------------- */

    private boolean firstTickState = true;

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide())
            return;

        if (firstTickState) {
            firstTick();
            firstTickState = false;
        }

        boolean dirty = false;

        if (Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled()) {
            int produced = getEnergyProductionRate((int) getSpeed());
            if (produced > 0) {
                energy.internalProduceEnergy(produced);
                dirty = true;
            }
        }

        for (Direction d : Direction.values()) {
            if (!isEnergyOutput(d))
                continue;

            IEnergyStorage target = getCachedEnergy(d);
            if (target == null)
                continue;

            int transferable = target.receiveEnergy(
                    CommonConfig.COPPER_ALTERNATOR.MAX_OUTPUT.get(),
                    true
            );

            if (transferable > 0) {
                int extracted = energy.extractEnergy(transferable, false);
                target.receiveEnergy(extracted, false);
                dirty = true;
            }
        }

        if (dirty) {
            sendData(); // 🔥 REQUIRED: sync energy to client
        }
    }

    /* ----------------------------- STRESS ----------------------------- */

    @Override
    public float calculateStressApplied() {
        float impact = CommonConfig.COPPER_ALTERNATOR.MAX_STRESS.get() / 256f;
        lastStressApplied = impact;
        return impact;
    }

    @Override
    protected Block getStressConfigKey() {
        return CBMBlocks.COPPER_ALTERNATOR.get();
    }

    /* ------------------------------ AUDIO ------------------------------ */

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickAudio() {
        super.tickAudio();

        float speed = Math.abs(getSpeed());
        if (speed == 0 || !isSpeedRequirementFulfilled())
            return;

        float pitch = Mth.clamp((speed / 256f) + .5f, .5f, 1.5f);

        if (CommonConfig.COPPER_ALTERNATOR.AUDIO_ENABLED.get())
            CASoundScapes.play(CASoundScapes.AmbienceGroup.DYNAMO, worldPosition, pitch);
    }

    /* -------------------------- ENERGY RATE -------------------------- */

    public static int getEnergyProductionRate(int rpm) {
        rpm = Math.abs(rpm);
        return (int) (
                CommonConfig.COPPER_ALTERNATOR.FE_RPM.get()
                        * ((double) rpm / 256d)
                        * CommonConfig.COPPER_ALTERNATOR.EFFICIENCY.get()
        );
    }

    /* ---------------------------- CACHE ---------------------------- */

    private LazyOptional<IEnergyStorage> escacheUp = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> escacheDown = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> escacheNorth = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> escacheSouth = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> escacheEast = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> escacheWest = LazyOptional.empty();

    public void firstTick() {
        updateCache();
    }

    public void updateCache() {
        if (level.isClientSide())
            return;

        for (Direction side : Direction.values()) {
            BlockEntity be = level.getBlockEntity(worldPosition.relative(side));
            if (be == null) {
                setCache(side, LazyOptional.empty());
                continue;
            }

            setCache(
                    side,
                    be.getCapability(ForgeCapabilities.ENERGY, side.getOpposite())
            );
        }
    }

    public void setCache(Direction side, LazyOptional<IEnergyStorage> storage) {
        switch (side) {
            case UP -> escacheUp = storage;
            case DOWN -> escacheDown = storage;
            case NORTH -> escacheNorth = storage;
            case SOUTH -> escacheSouth = storage;
            case EAST -> escacheEast = storage;
            case WEST -> escacheWest = storage;
        }
    }

    public IEnergyStorage getCachedEnergy(Direction side) {
        return switch (side) {
            case UP -> escacheUp.orElse(null);
            case DOWN -> escacheDown.orElse(null);
            case NORTH -> escacheNorth.orElse(null);
            case SOUTH -> escacheSouth.orElse(null);
            case EAST -> escacheEast.orElse(null);
            case WEST -> escacheWest.orElse(null);
        };
    }

    /* ----------------------------- CLEANUP ----------------------------- */

    @Override
    public void remove() {
        lazyEnergy.invalidate();
        super.remove();
    }
}
