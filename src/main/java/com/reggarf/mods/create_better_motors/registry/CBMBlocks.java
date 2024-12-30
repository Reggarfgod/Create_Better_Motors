package com.reggarf.mods.create_better_motors.registry;




import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.reggarf.mods.create_better_motors.content.battery.LinkAccumulatorBlock;
import com.reggarf.mods.create_better_motors.content.motor.LinkMotorBlock;
import com.reggarf.mods.create_better_motors.content.multimeter.MultiMeterBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.gauge.GaugeGenerator;
import com.simibubi.create.content.redstone.displayLink.source.KineticSpeedDisplaySource;
import com.simibubi.create.content.redstone.displayLink.source.KineticStressDisplaySource;
import com.simibubi.create.foundation.data.ModelGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.reggarf.mods.create_better_motors.content.alternator.AlternatorBlock;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlock;
import com.reggarf.mods.create_better_motors.content.motors.MotorBlock;
import com.reggarf.mods.create_better_motors.content.motors.variants.*;
import net.minecraft.world.level.material.MapColor;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;
import static com.simibubi.create.content.redstone.displayLink.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;


public class CBMBlocks {
    static {
        REGISTRATE.defaultCreativeTab(Create_better_motors.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<ElectricalConnectorBlock> ELECTRICAL_CONNECTOR =
            REGISTRATE.block("electrical_connector", ElectricalConnectorBlock::new)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(0.45f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> STARTER_MOTOR =
            REGISTRATE.block("starter_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.STARTER_MOTOR, new StarterMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> BASIC_MOTOR =
            REGISTRATE.block("basic_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.BASIC_MOTOR, new BasicMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.5f))
                    .simpleItem()
                    .register();


    public static final BlockEntry<MotorBlock> HARDENED_MOTOR =
            REGISTRATE.block("hardened_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.HARDENED_MOTOR, new HardenedMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(4.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> BLAZING_MOTOR =
            REGISTRATE.block("blazing_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.BLAZING_MOTOR, new BlazingMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> NIOTIC_MOTOR =
            REGISTRATE.block("niotic_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.NIOTIC_MOTOR, new NioticMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> SPIRITED_MOTOR =
            REGISTRATE.block("spirited_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.SPIRITED_MOTOR, new SpiritedMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .register();

    public static final BlockEntry<MotorBlock> NITRO_MOTOR =
            REGISTRATE.block("nitro_motor", (p) -> new MotorBlock(p,
                            CBMBlockEntityTypes.NITRO_MOTOR, new NitroMotorVariant()))
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .register();
    public static final BlockEntry<LinkMotorBlock> VOID_MOTOR = REGISTRATE.block("void_motor", LinkMotorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK))
            .properties(p -> p.strength(3.0F, 600.0F))
            .transform(pickaxeOnly())
            .transform(BlockStressDefaults.setNoImpact())
            .item()
            .transform(customItemModel())
            .register();
    public static final BlockEntry<MultiMeterBlock> MULTIMETER = REGISTRATE.block("multimeter", MultiMeterBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .transform(BlockStressDefaults.setNoImpact())
            .blockstate(new GaugeGenerator()::generate)
            .onRegister(assignDataBehaviour(new KineticSpeedDisplaySource(), "kinetic_speed"))
            .onRegister(assignDataBehaviour(new KineticStressDisplaySource(), "kinetic_stress"))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                    .requires(AllBlocks.STRESSOMETER.get())
                    .requires(AllBlocks.SPEEDOMETER.get())
                    .unlockedBy("has_compass", has(Items.COMPASS))
                    .save(p, Create_better_motors.asResource("crafting/multimeter")))
            .item()
            .transform(ModelGen.customItemModel("gauge", "_", "item"))
            .register();

    public static final BlockEntry<LinkAccumulatorBlock> VOID_BATTERY = REGISTRATE.block("void_battery", LinkAccumulatorBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.mapColor(MapColor.COLOR_BLACK))
            .properties(p -> p.strength(3.0F, 600.0F))
            .transform(pickaxeOnly())
            .item()
            .transform(customItemModel())
            .register();
    public static final BlockEntry<AlternatorBlock> ALTERNATOR =
            REGISTRATE.block("alternator", AlternatorBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(BlockStressDefaults.setImpact(256f))
            .tag(AllTags.AllBlockTags.SAFE_NBT.tag) //Dono what this tag means (contraption safe?).
            .item()
            .transform(customItemModel())
            .register();

    public static final BlockEntry<Block> REGGARFONITEBLOCK =
            REGISTRATE.block("reggarfoniteblock", Block::new)
                    .initialProperties(SharedProperties::softMetal)
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag) // Don't know what this tag means (contraption safe?).
                    .item()
                    .transform(customItemModel())
                    .register();
    public static final BlockEntry<Block> REGGARFONITEORE =
            REGISTRATE.block("reggarfoniteore", Block::new)
                    .initialProperties(SharedProperties::softMetal)
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag) // Don't know what this tag means (contraption safe?).
                    .item()
                    .transform(customItemModel())
                    .register();



        public static void load() {  }
}
