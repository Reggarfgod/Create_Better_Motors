package com.reggarf.mods.create_better_motors.registry;




import com.mrh0.createaddition.blocks.connector.base.ConnectorRenderer;
import com.reggarf.mods.create_better_motors.content.heavy_connector.HeavyConnectorBlockEntity;
import com.reggarf.mods.create_better_motors.content.motors.blocksentity.*;
import com.reggarf.mods.create_better_motors.content.multimeter.MultiMeterBlockEntity;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.content.kinetics.base.ShaftRenderer;
import com.simibubi.create.content.kinetics.base.ShaftVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;


public class CBMBlockEntityTypes {

    public static final BlockEntityEntry<HeavyConnectorBlockEntity> HEAVY_CONNECTOR = REGISTRATE
            .blockEntity("heavy_connector", HeavyConnectorBlockEntity::new)
            .validBlocks(CBMBlocks.HEAVY_CONNECTOR)
            .renderer(() -> ConnectorRenderer::new)
            .register();

    public static final BlockEntityEntry<StarterMotorBlockEntity> STARTER_MOTOR = REGISTRATE
            .blockEntity("starter_motor", StarterMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.STARTER_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();


    public static final BlockEntityEntry<BasicMotorBlockEntity> BASIC_MOTOR = REGISTRATE
            .blockEntity("basic_motor", BasicMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.BASIC_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<HardenedMotorBlockEntity> HARDENED_MOTOR = REGISTRATE
            .blockEntity("hardened_motor", HardenedMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.HARDENED_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();


    public static final BlockEntityEntry<BlazingMotorBlockEntity> BLAZING_MOTOR = REGISTRATE
            .blockEntity("blazing_motor", BlazingMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.BLAZING_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<NioticMotorBlockEntity> NIOTIC_MOTOR = REGISTRATE
            .blockEntity("niotic_motor", NioticMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.NIOTIC_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<SpritedMotorBlockEntity> SPIRITED_MOTOR = REGISTRATE
            .blockEntity("spirited_motor", SpritedMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.SPIRITED_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<NitroMotorBlockEntity> NITRO_MOTOR = REGISTRATE
            .blockEntity("nitro_motor", NitroMotorBlockEntity::new)
            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
            .validBlocks(CBMBlocks.NITRO_MOTOR)
            .renderer(() -> CBMHalfShaftRenderer::new)
            .register();


//    public static final BlockEntityEntry<AlternatorBlockEntity> ALTERNATOR = REGISTRATE
//            .blockEntity("alternator", AlternatorBlockEntity::new)
//            .visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF))
//            .validBlocks(CBMBlocks.ALTERNATOR)
//            .renderer(() -> CBMHalfShaftRenderer::new)
//            .register();

    public static final BlockEntityEntry<MultiMeterBlockEntity> MULTIMETER = REGISTRATE
            .blockEntity("multimeter", MultiMeterBlockEntity::new)
            .visual(() -> ShaftVisual::new, false)
            .validBlocks(CBMBlocks.MULTIMETER)
            .renderer(() -> ShaftRenderer::new)
            .register();

    public static void load() {  }
}
