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

/**
 * Brass Alternator Block Entity — generates Forge Energy (FE) from Create rotational energy.
 */
public class BrassAlternatorBlockEntity extends KineticBlockEntity implements IEnergyProvider {

    private final InternalEnergyStorage energy;
    private final IEnergyStorage capability;
    private final EnumMap<Direction, BlockCapabilityCache<IEnergyStorage, Direction>> cache = new EnumMap<>(Direction.class);
    private final EnumSet<Direction> invalidSides = EnumSet.noneOf(Direction.class);

    private boolean firstTickDone = false;

    public BrassAlternatorBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        this.energy = new InternalEnergyStorage(
                CommonConfig.BRASS_ALTERNATOR.CAPACITY.get(),
                0,
                CommonConfig.BRASS_ALTERNATOR.MAX_OUTPUT.get()
        );
        this.capability = energy;
    }

    /** Capability registration */
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CBMBlockEntityTypes.BRASS_ALTERNATOR.get(),
                (be, context) -> be.capability
        );
    }

    /** Main tick logic */
    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide()) return;

        if (!firstTickDone) {
            firstTickDone = true;
            updateCache();
        }

        if (Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled()) {
            int production = getEnergyProductionRate((int) getSpeed());
            energy.internalProduceEnergy(production);
        }

        distributeEnergy();
    }

    /** Push FE to adjacent blocks */
    private void distributeEnergy() {
        for (Direction d : Direction.values()) {
            if (!isEnergyOutput(d)) continue;
            BlockCapabilityCache<IEnergyStorage, Direction> cached = cache.get(d);
            if (cached == null) continue;

            IEnergyStorage target = cached.getCapability();
            if (target == null) continue;

            int toSend = energy.extractEnergy(target.receiveEnergy(CommonConfig.BRASS_ALTERNATOR.MAX_OUTPUT.get(), true), false);
            target.receiveEnergy(toSend, false);
        }
    }

    /** Create stress impact for Create’s kinetic system */
    @Override
    public float calculateStressApplied() {
        float impact = CommonConfig.BRASS_ALTERNATOR.MAX_STRESS.get() / 256f;
        this.lastStressApplied = impact;
        return impact;
    }

    /** Goggles tooltip info */
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.create_better_motors.generating").style(ChatFormatting.GRAY).forGoggles(tooltip);
        CreateLang.translate("tooltip.create_better_motors.energy_output").style(ChatFormatting.GRAY).forGoggles(tooltip);
        CreateLang.translate("tooltip.create_better_motors.energy_per_tick",
                        StringFormattingTool.formatLong(getEnergyProductionRate((int) getSpeed())))
                .style(ChatFormatting.AQUA).forGoggles(tooltip, 1);
        CreateLang.translate("tooltip.create_better_motors.energy_stored").style(ChatFormatting.WHITE).forGoggles(tooltip);
        CreateLang.translate("tooltip.create_better_motors.energy_storage",
                        StringFormattingTool.formatLong(energy.getEnergyStored()),
                        StringFormattingTool.formatLong(energy.getMaxEnergyStored()))
                .style(ChatFormatting.AQUA).forGoggles(tooltip, 1);
        return true;
    }

    /** Energy output check */
    public boolean isEnergyOutput(Direction side) {
        return true;
    }

    /** Client sound tick */
    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickAudio() {
        super.tickAudio();
        float componentSpeed = Math.abs(getSpeed());
        if (componentSpeed == 0 || !isSpeedRequirementFulfilled()) return;

        if (CommonConfig.BRASS_ALTERNATOR.AUDIO_ENABLED.get()) {
            float pitch = Mth.clamp((componentSpeed / 256f) + 0.5f, 0.5f, 1.5f);
            CASoundScapes.play(CASoundScapes.AmbienceGroup.DYNAMO, worldPosition, pitch);
        }
    }

    /** Save & load */
    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        energy.read(tag);
    }

    @Override
    public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
        super.writeSafe(tag, registries);
        energy.write(tag);
    }

    /** Update FE neighbor cache */
    public void updateCache() {
        if (level == null || level.isClientSide()) return;
        for (Direction side : Direction.values()) {
            cache.put(side, BlockCapabilityCache.create(
                    Capabilities.EnergyStorage.BLOCK,
                    (ServerLevel) level,
                    getBlockPos().relative(side),
                    side.getOpposite(),
                    () -> !this.isRemoved(),
                    () -> invalidSides.add(side)
            ));
        }
    }

    /** FE generation rate */
    public static int getEnergyProductionRate(int rpm) {
        rpm = Math.abs(rpm);
        return (int) (
                CommonConfig.BRASS_ALTERNATOR.FE_RPM.get()
                        * (rpm / 256.0)
                        * CommonConfig.BRASS_ALTERNATOR.EFFICIENCY.get()
        );
    }

    @Override
    protected Block getStressConfigKey() {
        return CBMBlocks.BRASS_ALTERNATOR.get();
    }

    @Override
    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return energy;
    }
}
