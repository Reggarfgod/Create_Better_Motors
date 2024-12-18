package com.reggarf.mods.create_better_motors.registry;




import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import com.reggarf.mods.create_better_motors.content.alternator.AlternatorBlock;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlock;
import com.reggarf.mods.create_better_motors.content.motors.MotorBlock;
import com.reggarf.mods.create_better_motors.content.motors.variants.*;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;


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
