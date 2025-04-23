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


public class SpritedMotorBlockEntity extends ElectricMotorBlockEntity {

    protected float motorSpeed;
    protected ScrollValueBehaviour generatedSpeed;
    protected final InternalEnergyStorage energy;
//    private LazyOptional<IEnergyStorage> lazyEnergy;
//    private LazyOptional<ElectricMotorPeripheral> lazyPeripheral = null;
    private final IEnergyStorage capability;
    private boolean cc_update_rpm = false;
    private float cc_new_rpm = 32.0f;

    private boolean active = false;

    public SpritedMotorBlockEntity(BlockEntityType<? extends ElectricMotorBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        energy = new InternalEnergyStorage(CommonConfig.SPIRITED_ELECTRIC_MOTOR_CAPACITY.get(), CommonConfig.SPIRITED_ELECTRIC_MOTOR_MAX_INPUT.get(), 0);
//        lazyEnergy = LazyOptional.of(() -> energy);
//        if(CreateAddition.CC_ACTIVE) {
//            lazyPeripheral = LazyOptional.of(() -> Peripherals.createElectricMotorPeripheral(this));
//        }
        capability = energy;
        setLazyTickRate(20);
    }
    public static void registerCapabilitiesspirited(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.EnergyStorage.BLOCK,
                CBMBlockEntityTypes.SPIRITED_MOTOR.get(),
                (be, context) -> be.capability
        );
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        CenteredSideValueBoxTransform slot = new CenteredSideValueBoxTransform((motor, side) -> motor.getValue(ElectricMotorBlock.FACING) == side.getOpposite());

        generatedSpeed = new KineticScrollValueBehaviour(CreateLang.translateDirect("generic.speed"), this, slot);
        generatedSpeed.between(-CommonConfig.SPIRITED_ELECTRIC_MOTOR_RPM_RANGE.get(), CommonConfig.SPIRITED_ELECTRIC_MOTOR_RPM_RANGE.get());
        generatedSpeed.value = 16;
        //generatedSpeed.withUnit(i -> Lang.translateDirect("generic.unit.rpm"));
        generatedSpeed.withCallback(i -> this.updateGeneratedRotation(i));
        //generatedSpeed.withStepFunction(ElectricMotorTileEntity::step);
        behaviours.add(generatedSpeed);
    }




    public float calculateAddedStressCapacity() {
        float capacity = CommonConfig.SPIRITED_MAX_STRESS.get()/256f;
        this.lastCapacityProvided = capacity;
        return capacity;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        CreateLang.translate("tooltip.create_better_motors.energy_stored")
                .style(ChatFormatting.WHITE)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_storage",
                        StringFormattingTool.formatLong(energy.getEnergyStored()),
                        StringFormattingTool.formatLong(energy.getMaxEnergyStored()))
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        CreateLang.translate("tooltip.create_better_motors.using")
                .style(ChatFormatting.GRAY)
                .forGoggles(tooltip);

        CreateLang.translate("tooltip.create_better_motors.energy_per_tick", (" " + Util.format(getEnergyConsumptionRate(generatedSpeed.getValue()))))
                .style(ChatFormatting.AQUA)
                .forGoggles(tooltip, 1);

        super.addToGoggleTooltip(tooltip, isPlayerSneaking);

        return true;
    }

    // This is the callback that is called by the ScrollValueBehaviour!
    public void updateGeneratedRotation(int rpm) {
        motorSpeed = rpm;
        super.updateGeneratedRotation();
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
            updateGeneratedRotation();
    }

    // This is the method that determines the absolute true output speed!
    @Override
    public float getGeneratedSpeed() {
        if (!CBMBlocks.SPIRITED_MOTOR.has(getBlockState()))
            return 0;
        return convertToDirection(active ? motorSpeed : 0, getBlockState().getValue(ElectricMotorBlock.FACING));
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        energy.read(tag);
        active = tag.getBoolean("active");
    }

    @Override
    public void writeSafe(CompoundTag tag, HolderLookup.Provider registries) {
        super.writeSafe(tag, registries);
        energy.write(tag);
        tag.putBoolean("active", active);
    }
    @Override
    public void lazyTick() {
        super.lazyTick();
    }

    public static int getEnergyConsumptionRate(float rpm) {
        return Math.abs(rpm) > 0 ? (int)Math.max((double)CommonConfig.SPIRITED_FE_RPM.get() * ((double)Math.abs(rpm) / 256d), (double)CommonConfig.SPIRITED_ELECTRIC_MOTOR_MINIMUM_CONSUMPTION.get()) : 0;
    }


    // CC
    boolean first = true;

    @Override
    public void tick() {
        super.tick();
        if(first) {
            motorSpeed = generatedSpeed.getValue();
            updateGeneratedRotation();
            first = false;
        }

        if(cc_update_rpm) {
            generatedSpeed.setValue(Math.round(cc_new_rpm));
            motorSpeed = cc_new_rpm;
            cc_update_rpm = false;
            updateGeneratedRotation();
        }

        //Old Lazy
        if(level.isClientSide()) return;
        int con = getEnergyConsumptionRate(motorSpeed);
        if(!active) {
            if(energy.getEnergyStored() > con * 2 && !getBlockState().getValue(ElectricMotorBlock.POWERED)) {
                active = true;
                updateGeneratedRotation();
            }
        }
        else {
            int ext = energy.internalConsumeEnergy(con);
            if(ext < con || getBlockState().getValue(ElectricMotorBlock.POWERED)) {
                active = false;
                updateGeneratedRotation();
            }
        }
    }

    @Override
    public void tickAudio() {
        super.tickAudio();
        if (!active) return;
        if (CommonConfig.SPIRITED_AUDIO_ENABLED.get()) CASoundScapes.play(CASoundScapes.AmbienceGroup.DYNAMO, worldPosition, 1);
    }



    // This is the callback used by the CC Peripheral!
    public boolean setRPM(float rpm) {
        rpm = Math.max(Math.min(rpm, CommonConfig.SPIRITED_ELECTRIC_MOTOR_RPM_RANGE.get()), -CommonConfig.SPIRITED_ELECTRIC_MOTOR_RPM_RANGE.get());
        cc_new_rpm = rpm;
        cc_update_rpm = true;
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