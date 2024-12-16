package com.reggarf.mods.create_better_motors.registry;



import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.reggarf.mods.create_better_motors.content.alternator.AlternatorBlockEntity;
import com.reggarf.mods.create_better_motors.content.alternator.AlternatorRenderer;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorRenderer;
import com.reggarf.mods.create_better_motors.content.motors.MotorBlockEntity;
import com.reggarf.mods.create_better_motors.content.motors.variants.*;
import com.reggarf.mods.create_better_motors.tools.HalfShaftRendererThing;
import com.simibubi.create.content.kinetics.base.HalfShaftInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;


public class CBMBlockEntityTypes {

    public static final BlockEntityEntry<ElectricalConnectorBlockEntity> ELECTRICAL_CONNECTOR = REGISTRATE
            .blockEntity("electrical_connector", ElectricalConnectorBlockEntity::new)
            .validBlocks(CBMBlocks.ELECTRICAL_CONNECTOR)
            .renderer(() -> ElectricalConnectorRenderer::new)
            .register();


    public static final BlockEntityEntry<MotorBlockEntity> BASIC_MOTOR = REGISTRATE
            .blockEntity("basic_motor", MotorBlockEntity.create(new BasicMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.BASIC_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();


    public static final BlockEntityEntry<MotorBlockEntity> STARTER_MOTOR = REGISTRATE
            .blockEntity("starter_motor", MotorBlockEntity.create(new StarterMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.STARTER_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();



    public static final BlockEntityEntry<MotorBlockEntity> HARDENED_MOTOR = REGISTRATE
            .blockEntity("hardened_motor", MotorBlockEntity.create(new HardenedMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.HARDENED_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();


    public static final BlockEntityEntry<MotorBlockEntity> BLAZING_MOTOR = REGISTRATE
            .blockEntity("blazing_motor", MotorBlockEntity.create(new BlazingMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.BLAZING_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();

    public static final BlockEntityEntry<MotorBlockEntity> NIOTIC_MOTOR = REGISTRATE
            .blockEntity("niotic_motor", MotorBlockEntity.create(new NioticMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.NIOTIC_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();

    public static final BlockEntityEntry<MotorBlockEntity> SPIRITED_MOTOR = REGISTRATE
            .blockEntity("spirited_motor", MotorBlockEntity.create(new SpiritedMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.SPIRITED_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();

    public static final BlockEntityEntry<MotorBlockEntity> NITRO_MOTOR = REGISTRATE
            .blockEntity("nitro_motor", MotorBlockEntity.create(new NitroMotorVariant()))
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.NITRO_MOTOR)
            .renderer(() -> HalfShaftRendererThing::new)
            .register();

    public static final BlockEntityEntry<AlternatorBlockEntity> ALTERNATOR = REGISTRATE
            .blockEntity("alternator", AlternatorBlockEntity::new)
            .instance(() -> HalfShaftInstance::new)
            .validBlocks(CBMBlocks.ALTERNATOR)
            .renderer(() -> AlternatorRenderer::new)
            .register();


    public static void load() {  }
}
