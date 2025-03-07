package com.reggarf.mods.create_better_motors.registry;



import com.reggarf.mods.create_better_motors.content.alternator.AlternatorBlockEntity;

import com.reggarf.mods.create_better_motors.content.creative_energy.CreativeEnergyBlockEntity;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorRenderer;


import com.reggarf.mods.create_better_motors.content.motors.variants.*;
import com.reggarf.mods.create_better_motors.content.multimeter.MultiMeterBlockEntity;

import com.reggarf.mods.create_better_motors.tools.CBMHalfShaftRenderer;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.simibubi.create.content.kinetics.gauge.GaugeVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;


public class CBMBlockEntityTypes {

    public static final BlockEntityEntry<ElectricalConnectorBlockEntity> ELECTRICAL_CONNECTOR = REGISTRATE
            .blockEntity("electrical_connector", ElectricalConnectorBlockEntity::new)
            .validBlocks(CBMBlocks.ELECTRICAL_CONNECTOR)
            .renderer(() -> ElectricalConnectorRenderer::new)
            .register();


    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> BASIC_MOTOR = REGISTRATE
            .blockEntity("basic_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new BasicMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.BASIC_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();


    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> STARTER_MOTOR = REGISTRATE
            .blockEntity("starter_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new StarterMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.STARTER_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();



    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> HARDENED_MOTOR = REGISTRATE
            .blockEntity("hardened_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new HardenedMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.HARDENED_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();


    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> BLAZING_MOTOR = REGISTRATE
            .blockEntity("blazing_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new BlazingMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.BLAZING_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> NIOTIC_MOTOR = REGISTRATE
            .blockEntity("niotic_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new NioticMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.NIOTIC_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> SPIRITED_MOTOR = REGISTRATE
            .blockEntity("spirited_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new SpiritedMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.SPIRITED_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity> NITRO_MOTOR = REGISTRATE
            .blockEntity("nitro_motor", com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity.create(new NitroMotorVariant()))
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.NITRO_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();
//    public static final BlockEntityEntry<MotorBlockEntity> VOID_MOTOR = REGISTRATE
//            .blockEntity("void_motor", MotorBlockEntity::new)
//            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
//            .validBlocks(CBMBlocks.VOID_MOTOR)
//            .renderer(() -> MotorRenderer::new)
//            .register();

//    public static final BlockEntityEntry<AccumulatorBlockEntity> VOID_BATTERY = REGISTRATE
//            .blockEntity("void_battery", AccumulatorBlockEntity::new)
//            .validBlocks(CBMBlocks.VOID_BATTERY)
//            .renderer(() -> AccumulatorRenderer::new)
//            .register();

    public static final BlockEntityEntry<CreativeEnergyBlockEntity> CREATIVE_ENERGY = REGISTRATE
            .blockEntity("creative_energy", CreativeEnergyBlockEntity::new)
            .validBlocks(CBMBlocks.CREATIVE_ENERGY)
            .register();

    public static final BlockEntityEntry<AlternatorBlockEntity> ALTERNATOR = REGISTRATE
            .blockEntity("alternator", AlternatorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.ALTERNATOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();
    public static final BlockEntityEntry<MultiMeterBlockEntity> MULTIMETER = REGISTRATE
            .blockEntity("multimeter", MultiMeterBlockEntity::new)
            .visual(() -> ShaftVisual::new, false)
            .validBlocks(CBMBlocks.MULTIMETER)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static void load() {  }
}
