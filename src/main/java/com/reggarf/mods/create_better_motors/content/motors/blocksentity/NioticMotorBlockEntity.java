package com.reggarf.mods.create_better_motors.content.motors.blocksentity;

import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlock;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlockEntity;
import com.mrh0.createaddition.blocks.tesla_coil.TeslaCoilBlock;
import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.mrh0.createaddition.sound.CASoundScapes;
import com.mrh0.createaddition.util.Util;

import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.util.StringFormattingTool;

import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

import java.util.List;

public class NioticMotorBlockEntity extends ElectricMotorBlockEntity {

    protected float motorSpeed;
    protected ScrollValueBehaviour generatedSpeed;

    protected final InternalEnergyStorage energy;
    private final IEnergyStorage capability;

    private boolean active = false;

    private boolean ccUpdateRPM = false;
    private float ccNewRPM = 32f;

    private boolean firstTick = true;

    public NioticMotorBlockEntity(BlockEntityType<? extends ElectricMotorBlockEntity> type,
                                  BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.energy = new InternalEnergyStorage(
                CommonConfig.NIOTIC_MOTOR.CAPACITY.get(),
                CommonConfig.NIOTIC_MOTOR.MAX_INPUT.get(),
                0
        );
        this.capability = energy;

        setLazyTickRate(20);
    }

    /* ------------------ CAPABILITIES ------------------ */

    public static void registerCapabilitiesniotic(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CBMBlockEntityTypes.NIOTIC_MOTOR.get(),
                (be, ctx) -> be.capability
        );
    }

    /* ------------------ BEHAVIOURS ------------------ */

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        CenteredSideValueBoxTransform slot =
                new CenteredSideValueBoxTransform(
                        (motor, side) ->
                                motor.getValue(ElectricMotorBlock.FACING) == side.getOpposite()
                );

        generatedSpeed = new KineticScrollValueBehaviour(
                CreateLang.translateDirect("generic.speed"),
                this,
                slot
        );

        generatedSpeed.between(
                -CommonConfig.NIOTIC_MOTOR.RPM_RANGE.get(),
                CommonConfig.NIOTIC_MOTOR.RPM_RANGE.get()
        );

        generatedSpeed.value = 16;
        generatedSpeed.withCallback(this::updateGeneratedRotation);

        behaviours.add(generatedSpeed);
    }

    /* ------------------ STRESS ------------------ */

    public float calculateAddedStressCapacity() {
        float capacity = CommonConfig.NIOTIC_MOTOR.MAX_STRESS.get() / 256f;
        lastCapacityProvided = capacity;
        return capacity;
    }

    /* ------------------ GOGGLES ------------------ */

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

        CreateLang.builder()
                .add(CreateLang.translateDirect("tooltip.create_better_motors.generator_stats")
                        .withStyle(ChatFormatting.WHITE))
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.generates")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        long stressAtSpeed = Math.round(
                CommonConfig.NIOTIC_MOTOR.MAX_STRESS.get()
                        * (Math.abs(generatedSpeed.getValue()) / 256f)
        );

        CreateLang.text(" ")
                .add(CreateLang.number(stressAtSpeed)
                        .text(" ")
                        .translate("generic.unit.stress")
                        .style(ChatFormatting.AQUA))
                .forGoggles(tooltip, 1);

        boolean hasEnergy = energy.getEnergyStored() > 0;

        if (!hasEnergy) {
            CreateLang.translate("tooltip.create_better_motors.no_energy")
                    .style(ChatFormatting.RED)
                    .forGoggles(tooltip);

            CreateLang.text(" ")
                    .translate("tooltip.create_better_motors.connect_energy")
                    .style(ChatFormatting.DARK_RED)
                    .forGoggles(tooltip, 1);
        } else {
            CreateLang.translate("tooltip.create_better_motors.stores")
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip);

            CreateLang.text(" ")
                    .translate(
                            "tooltip.create_better_motors.energy",
                            StringFormattingTool.formatLong(energy.getEnergyStored()),
                            StringFormattingTool.formatLong(energy.getMaxEnergyStored())
                    )
                    .style(ChatFormatting.AQUA)
                    .forGoggles(tooltip, 1);

            CreateLang.translate("tooltip.create_better_motors.use")
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip);

            CreateLang.text(" ")
                    .translate(
                            "tooltip.create_better_motors.energy_per_tick",
                            Util.format(getEnergyConsumptionRate(generatedSpeed.getValue()))
                    )
                    .style(ChatFormatting.AQUA)
                    .forGoggles(tooltip, 1);
        }

        CreateLang.translate("tooltip.create_better_motors.max_speed")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.text(" ")
                .translate(
                        "tooltip.create_better_motors.rpm",
                        Math.abs(generatedSpeed.getValue())
                )
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        return true;
    }

    /* ------------------ ROTATION ------------------ */

    public void updateGeneratedRotation(int rpm) {
        motorSpeed = rpm;
        super.updateGeneratedRotation();

        if (!level.isClientSide)
            sendData();
    }

    @Override
    public float getGeneratedSpeed() {
        if (!CBMBlocks.NIOTIC_MOTOR.has(getBlockState()))
            return 0;

        return convertToDirection(
                active ? motorSpeed : 0,
                getBlockState().getValue(ElectricMotorBlock.FACING)
        );
    }

    /* ------------------ TICK ------------------ */

    @Override
    public void tick() {
        super.tick();

        if (firstTick) {
            motorSpeed = generatedSpeed.getValue();
            updateGeneratedRotation();
            firstTick = false;
        }

        if (ccUpdateRPM) {
            generatedSpeed.setValue(Math.round(ccNewRPM));
            motorSpeed = ccNewRPM;
            ccUpdateRPM = false;
            updateGeneratedRotation();
        }

        if (level.isClientSide)
            return;

        int consumption = getEnergyConsumptionRate(motorSpeed);

        if (!active) {
            if (energy.getEnergyStored() > consumption * 2
                    && !getBlockState().getValue(ElectricMotorBlock.POWERED)) {
                active = true;
                updateGeneratedRotation();
                sendData();
            }
        } else {
            int drained = energy.internalConsumeEnergy(consumption);

            if (drained > 0)
                sendData();

            if (drained < consumption
                    || getBlockState().getValue(ElectricMotorBlock.POWERED)) {
                active = false;
                updateGeneratedRotation();
                sendData();
            }
        }
    }

    /* ------------------ AUDIO ------------------ */

    @Override
    public void tickAudio() {
        super.tickAudio();
        if (!active) return;
        if (CommonConfig.NIOTIC_MOTOR.AUDIO_ENABLED.get())
            CASoundScapes.play(CASoundScapes.AmbienceGroup.DYNAMO, worldPosition, 1);
    }

    /* ------------------ NBT ------------------ */

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        energy.write(tag);
        tag.putBoolean("active", active);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        energy.read(tag);
        active = tag.getBoolean("active");
    }

//    @Override
//    public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
//        super.writeSafe(tag, registries);
//        energy.write(tag);
//        tag.putBoolean("active", active);
//    }

    /* ------------------ HELPERS ------------------ */

    public static int getEnergyConsumptionRate(float rpm) {
        return Math.abs(rpm) > 0
                ? (int) Math.max(
                CommonConfig.NIOTIC_MOTOR.FE_RPM.get()
                        * (Math.abs(rpm) / 256d),
                CommonConfig.NIOTIC_MOTOR.MIN_CONSUMPTION.get()
        )
                : 0;
    }

    public boolean setRPM(float rpm) {
        rpm = Math.max(
                Math.min(rpm, CommonConfig.NIOTIC_MOTOR.RPM_RANGE.get()),
                -CommonConfig.NIOTIC_MOTOR.RPM_RANGE.get()
        );
        ccNewRPM = rpm;
        ccUpdateRPM = true;
        return true;
    }

    public float getRPM() {
        return motorSpeed;
    }

    public int getGeneratedStress() {
        return (int) calculateAddedStressCapacity();
    }

    public int getEnergyConsumption() {
        return getEnergyConsumptionRate(motorSpeed);
    }

    public boolean isPoweredState() {
        return getBlockState().getValue(TeslaCoilBlock.POWERED);
    }
}
