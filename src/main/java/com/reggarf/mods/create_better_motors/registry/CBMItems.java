package com.reggarf.mods.create_better_motors.registry;

import com.reggarf.mods.create_better_motors.content.electricity.wire.ElectricWireItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;



public class CBMItems {

    public static final ItemEntry<ElectricWireItem> COPPER_WIRE =
            REGISTRATE.item("copper_wire", ElectricWireItem::newCopperWire)
                    .register();

    public static final ItemEntry<ElectricWireItem> IRON_WIRE =
            REGISTRATE.item("iron_wire", ElectricWireItem::newIronWire)
                    .register();

    public static final ItemEntry<ElectricWireItem> GOLDEN_WIRE =
            REGISTRATE.item("golden_wire", ElectricWireItem::newGoldenWire)
                    .register();

    public static final ItemEntry<ElectricWireItem> DIAMOND_WIRE =
            REGISTRATE.item("diamond_wire", ElectricWireItem::newDiamondWire)
                    .register();
    public static final ItemEntry<Item> REGGARFONITE_SHEET =
            REGISTRATE.item("reggarfonite_sheet", Item::new)
                    .register();
    public static final ItemEntry<Item> REGGARFONITE_NUGGET =
            REGISTRATE.item("reggarfonite_nugget", Item::new)
                    .register();
    public static final ItemEntry<Item> REGGARFONITE_GEM =
            REGISTRATE.item("reggarfonite_gem", Item::new)
                    .register();
    public static final ItemEntry<Item> EMPTY_COIL =
            REGISTRATE.item("empty_coil", Item::new)
                    .register();
    public static final ItemEntry<Item> IRON_COIL =
            REGISTRATE.item("iron_coil", Item::new)
                    .register();
    public static final ItemEntry<Item> GOLDEN_COIL =
            REGISTRATE.item("golden_coil", Item::new)
                    .register();
    public static final ItemEntry<Item> COPPER_COIL =
            REGISTRATE.item("copper_coil", Item::new)
                    .register();

    public static final ItemEntry<Item> REGGARFONITE_COIL =
            REGISTRATE.item("reggarfonite_coil", Item::new)
                    .register();
        public static final ItemEntry<Item> LINK_TUBE =
            REGISTRATE.item("link_tube", Item::new)
                    .register();
    public static final ItemEntry<Item> LAVA_QUARTZ =
            REGISTRATE.item("lava_quartz", Item::new)
                    .register();
    public static final ItemEntry<Item> POLISHED_LAVA_QUARTZ =
            REGISTRATE.item("polished_lava_quartz", Item::new)
                    .register();


    public static void load() {

    }
}