package com.reggarf.mods.create_better_motors.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import com.reggarf.mods.create_better_motors.content.electricity.wire.ElectricWireItem;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;


public class CBMItems {

    public static final ItemEntry<ElectricWireItem> COPPER_WIRE =
            REGISTRATE.item("copper_wire", ElectricWireItem::newCopperWire)
                    .register();

    public static final ItemEntry<ElectricWireItem> IRON_WIRE =
            REGISTRATE.item("overcharged_iron_wire", ElectricWireItem::newIronWire)
                    .register();

    public static final ItemEntry<ElectricWireItem> GOLDEN_WIRE =
            REGISTRATE.item("overcharged_golden_wire", ElectricWireItem::newGoldenWire)
                    .register();

    public static final ItemEntry<ElectricWireItem> DIAMOND_WIRE =
            REGISTRATE.item("overcharged_diamond_wire", ElectricWireItem::newDiamondWire)
                    .register();

    public static void load() {  }
}