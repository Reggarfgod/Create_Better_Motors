package com.reggarf.mods.create_better_motors.registry;




import com.mrh0.createaddition.energy.NodeMovementBehaviour;
import com.mrh0.createaddition.index.CABlocks;
import com.reggarf.mods.create_better_motors.Create_better_motors;


import com.reggarf.mods.create_better_motors.config.CBMStress;
import com.reggarf.mods.create_better_motors.content.alternator.blocks.AndesiteAlternatorBlock;
import com.reggarf.mods.create_better_motors.content.alternator.blocks.BrassAlternatorBlock;
import com.reggarf.mods.create_better_motors.content.alternator.blocks.CopperAlternatorBlock;
import com.reggarf.mods.create_better_motors.content.heavy_connector.HeavyConnectorBlock;
import com.reggarf.mods.create_better_motors.content.motors.blocks.*;
import com.reggarf.mods.create_better_motors.content.multimeter.MultiMeterBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllDisplaySources;
import com.simibubi.create.AllTags;

import com.simibubi.create.content.kinetics.gauge.GaugeGenerator;
import com.simibubi.create.foundation.data.ModelGen;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;



import net.minecraft.world.level.material.MapColor;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;

import static com.simibubi.create.api.behaviour.display.DisplaySource.displaySource;
import static com.simibubi.create.api.behaviour.movement.MovementBehaviour.movementBehaviour;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;
import static com.tterrag.registrate.providers.RegistrateRecipeProvider.has;


public class CBMBlocks {
    static {
        REGISTRATE.defaultCreativeTab(Create_better_motors.CREATIVE_TAB_KEY);
    }

    public static final BlockEntry<HeavyConnectorBlock> HEAVY_CONNECTOR =
            REGISTRATE.block("heavy_connector",  HeavyConnectorBlock::new)
            .initialProperties(SharedProperties::stone)
            .onRegister(movementBehaviour(new NodeMovementBehaviour()))
            .item()
            .transform(customItemModel())
                    .transform(axeOrPickaxe())
                    .recipe((c, p) ->
                            ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, c.get(), 1)
                                    .pattern(" C ")
                                    .pattern("CSC")
                                    .pattern(" C ")
                                    .define('S', CABlocks.LARGE_CONNECTOR.get())
                                    .define('C', CBMItems.REGGARFONITE_NUGGET.get())
                                    .unlockedBy("has_compass", has(CABlocks.LARGE_CONNECTOR.get()))
                                    .save(p, Create_better_motors.asResource("crafting/heavy_connector"))
                    )
                    .register();

    public static final BlockEntry<StarterMotorBlock> STARTER_MOTOR =
            REGISTRATE.block("starter_motor", StarterMotorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();


    public static final BlockEntry<BasicMotorBlock> BASIC_MOTOR =
            REGISTRATE.block("basic_motor", BasicMotorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.5f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();


    public static final BlockEntry<HardenedMotorBlock> HARDENED_MOTOR =
            REGISTRATE.block("hardened_motor", HardenedMotorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(4.0f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();

    public static final BlockEntry<BlazingMotorBlock> BLAZING_MOTOR =
            REGISTRATE.block("blazing_motor", BlazingMotorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();

    public static final BlockEntry<NioticMotorBlock> NIOTIC_MOTOR =
            REGISTRATE.block("niotic_motor", NioticMotorBlock::new)
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();

    public static final BlockEntry<SpiritedMotorBlock> SPIRITED_MOTOR =
            REGISTRATE.block("spirited_motor", SpiritedMotorBlock::new )
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();

    public static final BlockEntry<NitroMotorBlock> NITRO_MOTOR =
            REGISTRATE.block("nitro_motor", NitroMotorBlock::new )
                    .properties(BlockBehaviour.Properties::requiresCorrectToolForDrops)
                    .properties(BlockBehaviour.Properties::noOcclusion)
                    .properties(properties -> properties.strength(3.0f))
                    .simpleItem()
                    .transform(axeOrPickaxe())
                    .register();



    public static final BlockEntry<MultiMeterBlock> MULTIMETER = REGISTRATE.block("multimeter", MultiMeterBlock::new)
            .initialProperties(SharedProperties::wooden)
            .properties(p -> p.mapColor(MapColor.PODZOL))
            .transform(axeOrPickaxe())
            .transform(CBMStress.setNoImpact())
            .blockstate(new GaugeGenerator()::generate)
            .transform(displaySource(AllDisplaySources.KINETIC_SPEED))
            .transform(displaySource(AllDisplaySources.KINETIC_STRESS))
            .recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, c.get(), 2)
                    .requires(AllBlocks.STRESSOMETER.get())
                    .requires(AllBlocks.SPEEDOMETER.get())
                    .unlockedBy("has_compass", has(Items.COMPASS))
                    .save(p, Create_better_motors.asResource("crafting/multimeter")))
            .item()
            .transform(ModelGen.customItemModel("gauge", "_", "item"))
            .register();


    public static final BlockEntry<AndesiteAlternatorBlock> ANDESITE_ALTERNATOR =
            REGISTRATE.block("andesite_alternator", AndesiteAlternatorBlock::new)
            .initialProperties(SharedProperties::softMetal)
            .transform(CBMStress.setImpact(256f))
            .tag(AllTags.AllBlockTags.SAFE_NBT.tag) //Dono what this tag means (contraption safe?).
            .item()
            .transform(customItemModel())
                    .transform(axeOrPickaxe())
            .register();
    public static final BlockEntry<CopperAlternatorBlock> COPPER_ALTERNATOR =
            REGISTRATE.block("copper_alternator", CopperAlternatorBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(CBMStress.setImpact(256f))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag) //Dono what this tag means (contraption safe?).
                    .item()
                    .transform(customItemModel())
                    .transform(axeOrPickaxe())
                    .register();

    public static final BlockEntry<BrassAlternatorBlock> BRASS_ALTERNATOR =
            REGISTRATE.block("brass_alternator", BrassAlternatorBlock::new)
                    .initialProperties(SharedProperties::softMetal)
                    .transform(CBMStress.setImpact(256f))
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag) //Dono what this tag means (contraption safe?).
                    .item()
                    .transform(customItemModel())
                    .transform(axeOrPickaxe())
                    .register();


    public static final BlockEntry<Block> REGGARFONITEBLOCK =
            REGISTRATE.block("reggarfoniteblock", Block::new)
                    .initialProperties(SharedProperties::softMetal)
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag) // Don't know what this tag means (contraption safe?).
                    .item()
                    .transform(customItemModel())
                    .transform(pickaxeOnly())
                    .register();

    public static final BlockEntry<Block> REGGARFONITEORE =
            REGISTRATE.block("reggarfoniteore", Block::new)
                    .initialProperties(SharedProperties::softMetal)
                    .tag(AllTags.AllBlockTags.SAFE_NBT.tag) // Don't know what this tag means (contraption safe?).
                    .item()
                    .transform(customItemModel())
                    .transform(pickaxeOnly())
                    .register();



        public static void load() {  }
}
