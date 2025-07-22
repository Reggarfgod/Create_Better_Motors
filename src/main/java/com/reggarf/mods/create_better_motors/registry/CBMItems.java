package com.reggarf.mods.create_better_motors.registry;


import com.simibubi.create.foundation.data.recipe.CompatMetals;
import com.simibubi.create.foundation.item.TagDependentIngredientItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.reggarf.mods.create_better_motors.Create_better_motors.REGISTRATE;
import static com.simibubi.create.AllTags.AllItemTags.CRUSHED_RAW_MATERIALS;
import static com.simibubi.create.AllTags.commonItemTag;



public class CBMItems {
    public static final ItemEntry<Item> STARTER_TIER_UPGRADE = REGISTRATE.item("starter_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> BASIC_TIER_UPGRADE = REGISTRATE.item("basic_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> HARDENED_TIER_UPGRADE = REGISTRATE.item("hardened_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> BLAZING_TIER_UPGRADE = REGISTRATE.item("blazing_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> NIOTIC_TIER_UPGRADE = REGISTRATE.item("niotic_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> SPIRITED_TIER_UPGRADE = REGISTRATE.item("spirited_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> NITRO_TIER_UPGRADE = REGISTRATE.item("nitro_tier_upgrade", Item::new).register();

    public static final ItemEntry<Item> ANDESITE_ALTERNATOR_TIER_UPGRADE = REGISTRATE.item("andesite_alternator_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> COPPER_ALTERNATOR_TIER_UPGRADE = REGISTRATE.item("copper_alternator_tier_upgrade", Item::new).register();
    public static final ItemEntry<Item> BRASS_ALTERNATOR_TIER_UPGRADE = REGISTRATE.item("brass_alternator_tier_upgrade", Item::new).register();


    public static final ItemEntry<Item> RAW_REGGARFONITE = taggedIngredient("raw_reggarfonite", commonItemTag("raw_materials/reggarfonite"), commonItemTag("raw_materials"));
    public static final ItemEntry<Item> CRUSHED_REGGARFONITE = taggedIngredient("crushed_raw_reggarfonite", CRUSHED_RAW_MATERIALS.tag);
    public static final ItemEntry<Item> REGGARFONITE_GEM = REGISTRATE.item("reggarfonite_gem", Item::new).register();
    public static final ItemEntry<Item> REGGARFONITE_NUGGET = REGISTRATE.item("reggarfonite_nugget", Item::new).register();
    public static final ItemEntry<Item> REGGARFONITE_SHEET = REGISTRATE.item("reggarfonite_sheet", Item::new).register();

    public static final ItemEntry<Item> LAVA_TUBE = REGISTRATE.item("lava_tube", Item::new).register();
    public static final ItemEntry<Item> LAVA_QUARTZ = REGISTRATE.item("lava_quartz", Item::new).register();
    public static final ItemEntry<Item> POLISHED_LAVA_QUARTZ = REGISTRATE.item("polished_lava_quartz", Item::new).register();


    public static void load() {}
    @SafeVarargs
    private static ItemEntry<Item> taggedIngredient(String name, TagKey<Item>... tags) {
        return REGISTRATE.item(name, Item::new)
                .tag(tags)
                .register();
    }

    private static ItemEntry<TagDependentIngredientItem> compatCrushedOre(CompatMetals metal) {
        String metalName = metal.getName();
        return REGISTRATE
                .item("crushed_raw_" + metalName,
                        props -> new TagDependentIngredientItem(props, commonItemTag("ores/" + metalName)))
                .tag(CRUSHED_RAW_MATERIALS.tag)
                .register();
    }
}