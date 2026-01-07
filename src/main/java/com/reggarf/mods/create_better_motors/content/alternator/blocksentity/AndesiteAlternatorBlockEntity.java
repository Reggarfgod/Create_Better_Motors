package com.reggarf.mods.create_better_motors.content.alternator.blocksentity;

import com.mrh0.createaddition.energy.IEnergyProvider;
import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.mrh0.createaddition.sound.CASoundScapes;

import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.util.StringFormattingTool;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.BlockCapabilityCache;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;

public class AndesiteAlternatorBlockEntity extends KineticBlockEntity implements IEnergyProvider {

    protected final InternalEnergyStorage energy;
    private final IEnergyStorage capability;

    private final EnumSet<Direction> invalidSides = EnumSet.noneOf(Direction.class);
    private final EnumMap<Direction, BlockCapabilityCache<IEnergyStorage, Direction>> cache =
            new EnumMap<>(Direction.class);

    private boolean firstTickState = true;

    public AndesiteAlternatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.energy = new InternalEnergyStorage(
                CommonConfig.ANDESITE_ALTERNATOR.CAPACITY.get(),
                0,
                CommonConfig.ANDESITE_ALTERNATOR.MAX_OUTPUT.get()
        );
        this.capability = energy;
    }

    /* ------------------ CAPABILITIES ------------------ */

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CBMBlockEntityTypes.ANDESITE_ALTERNATOR.get(),
                (be, ctx) -> be.capability
        );
    }

    /* ------------------ GOGGLES ------------------ */

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        CreateLang.translate("tooltip.create_better_motors.generating")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_output")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.translate(
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

        CreateLang.translate(
                        "tooltip.create_better_motors.energy_storage",
                        StringFormattingTool.formatLong(energy.getEnergyStored()),
                        StringFormattingTool.formatLong(energy.getMaxEnergyStored())
                )
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        return true;
    }

    /* ------------------ ENERGY IO ------------------ */

    public boolean isEnergyInput(Direction side) {
        return false;
    }

    public boolean isEnergyOutput(Direction side) {
        return true;
    }

    /* ------------------ NBT ------------------ */

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        energy.read(tag);
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        energy.write(tag);
    }

    @Override
    public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
        super.writeSafe(tag, registries);
        energy.write(tag);
    }

    /* ------------------ TICK ------------------ */

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide)
            return;

        if (firstTickState) {
            updateCache();
            firstTickState = false;
        }

        boolean produced = false;

        if (Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled()) {
            energy.internalProduceEnergy(getEnergyProductionRate((int) getSpeed()));
            produced = true;
        }

        for (Direction d : Direction.values()) {
            if (!isEnergyOutput(d))
                continue;

            BlockCapabilityCache<IEnergyStorage, Direction> cap = cache.get(d);
            if (cap == null)
                continue;

            IEnergyStorage target = cap.getCapability();
            if (target == null)
                continue;

            int accepted = target.receiveEnergy(
                    CommonConfig.ANDESITE_ALTERNATOR.MAX_OUTPUT.get(),
                    true
            );
            int extracted = energy.extractEnergy(accepted, false);
            target.receiveEnergy(extracted, false);

            if (extracted > 0)
                produced = true;
        }

        if (produced)
            sendData();
    }

    /* ------------------ STRESS ------------------ */

    @Override
    public float calculateStressApplied() {
        float impact = CommonConfig.ANDESITE_ALTERNATOR.MAX_STRESS.get() / 256f;
        lastStressApplied = impact;
        return impact;
    }

    /* ------------------ AUDIO ------------------ */

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickAudio() {
        super.tickAudio();

        float componentSpeed = Math.abs(getSpeed());
        if (componentSpeed == 0 || !isSpeedRequirementFulfilled())
            return;

        float pitch = Mth.clamp((componentSpeed / 256f) + .5f, .5f, 1.5f);
        if (CommonConfig.ANDESITE_ALTERNATOR.AUDIO_ENABLED.get())
            CASoundScapes.play(
                    CASoundScapes.AmbienceGroup.DYNAMO,
                    worldPosition,
                    pitch
            );
    }

    /* ------------------ HELPERS ------------------ */

    public static int getEnergyProductionRate(int rpm) {
        rpm = Math.abs(rpm);
        return (int) (
                CommonConfig.ANDESITE_ALTERNATOR.FE_RPM.get()
                        * ((double) rpm / 256d)
                        * CommonConfig.ANDESITE_ALTERNATOR.EFFICIENCY.get()
        );
    }

    @Override
    protected Block getStressConfigKey() {
        return CBMBlocks.ANDESITE_ALTERNATOR.get();
    }

    /* ------------------ CAPABILITY CACHE ------------------ */

    public void updateCache() {
        if (!(level instanceof ServerLevel server))
            return;

        cache.clear();
        invalidSides.clear();

        for (Direction side : Direction.values()) {
            cache.put(
                    side,
                    BlockCapabilityCache.create(
                            Capabilities.EnergyStorage.BLOCK,
                            server,
                            worldPosition.relative(side),
                            side.getOpposite(),
                            () -> !this.isRemoved(),
                            () -> invalidSides.add(side)
                    )
            );
        }
    }

    @Override
    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return energy;
    }
}
