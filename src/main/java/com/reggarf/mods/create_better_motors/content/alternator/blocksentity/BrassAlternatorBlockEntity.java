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
import net.minecraft.world.level.block.entity.BlockEntity;
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

public class BrassAlternatorBlockEntity extends KineticBlockEntity implements IEnergyProvider {

    protected final InternalEnergyStorage energy;
    private final IEnergyStorage capability;

    private final EnumSet<Direction> invalidSides = EnumSet.allOf(Direction.class);
    private final EnumMap<Direction, BlockCapabilityCache<IEnergyStorage, Direction>> cache = new EnumMap<>(Direction.class);

    public BrassAlternatorBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        energy = new InternalEnergyStorage(CommonConfig.BRASS_ALTERNATOR_CAPACITY.get(), 0, CommonConfig.BRASS_ALTERNATOR_MAX_OUTPUT.get());
        capability = energy;
    }
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CBMBlockEntityTypes.BRASS_ALTERNATOR.get(),
                (be, context) -> be.capability
        );
    }
    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.create_better_motors.generating")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_output")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_per_tick",
                        StringFormattingTool.formatLong(getEnergyProductionRate((int)getSpeed())))
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);
        CreateLang.translate("tooltip.create_better_motors.energy_stored")
                .style(ChatFormatting.WHITE)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_storage",
                        StringFormattingTool.formatLong(energy.getEnergyStored()),
                        StringFormattingTool.formatLong(energy.getMaxEnergyStored()))
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        return true;
    }

    public boolean isEnergyInput(Direction side) {
        return false;
    }

    public boolean isEnergyOutput(Direction side) {
        return true;
    }


    private boolean firstTickState = true;

    @Override
    public void tick() {
        super.tick();
        if (level == null) return;
        if (level.isClientSide()) return;
        if (firstTickState) firstTick();
        firstTickState = false;

        if (Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled())
            energy.internalProduceEnergy(getEnergyProductionRate((int)getSpeed()));

        for (Direction d : Direction.values()) {
            if(!isEnergyOutput(d)) continue;
            IEnergyStorage ies = cache.get(d).getCapability();
            if(ies == null) continue;
            int ext = energy.extractEnergy(ies.receiveEnergy(CommonConfig.BRASS_ALTERNATOR_MAX_OUTPUT.get(), true), false);
            ies.receiveEnergy(ext, false);
        }
    }

    @Override
    public float calculateStressApplied() {
        float impact = CommonConfig.BRASS_ALTERNATOR_MAX_STRESS.get()/256f;
        this.lastStressApplied = impact;
        return impact;
    }
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
    @OnlyIn(Dist.CLIENT)
    @Override
    public void tickAudio() {
        super.tickAudio();

        float componentSpeed = Math.abs(getSpeed());
        if (componentSpeed == 0 || !isSpeedRequirementFulfilled())
            return;

        float pitch = Mth.clamp((componentSpeed / 256f) + .5f, .5f, 1.5f);
        if (CommonConfig.BRASS_ALTERNATOR_AUDIO_ENABLED.get()) CASoundScapes.play(CASoundScapes.AmbienceGroup.DYNAMO, worldPosition, pitch);
    }

    public static int getEnergyProductionRate(int rpm) {
        rpm = Math.abs(rpm);
        return (int)((double)CommonConfig.BRASS_ALTERNATOR_FE_RPM.get() * ((double)Math.abs(rpm) / 256d) * CommonConfig.BRASS_ALTERNATOR_EFFICIENCY.get());//return (int)((double)Config.FE_TO_SU.get() * ((double)Math.abs(rpm)/256d) * EFFICIENCY);
    }

    @Override
    protected Block getStressConfigKey() {
        return CBMBlocks.BRASS_ALTERNATOR.get();
    }



    public void firstTick() {
        updateCache();
    };


    public void updateCache() {
        if (level == null) return;
        if (level.isClientSide()) return;
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

    @Override
    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return energy;
    }

}
