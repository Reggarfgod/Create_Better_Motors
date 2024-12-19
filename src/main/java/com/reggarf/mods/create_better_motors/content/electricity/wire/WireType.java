package com.reggarf.mods.create_better_motors.content.electricity.wire;

import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.registry.CBMItems;
import net.minecraft.world.item.ItemStack;


public enum WireType {
    COPPER(2024, new int[] { 158, 88, 75, 255 }, new int[] { 173, 108, 92, 255 }, CBMItems.COPPER_WIRE::asStack),
    IRON(4048, new int[] { 210, 213, 216, 255 }, new int[] { 253, 254, 254, 255 }, CBMItems.IRON_WIRE::asStack),
    GOLD(8096, new int[] { 244, 184, 28, 255 }, new int[] { 254, 240, 90, 255 }, CBMItems.GOLDEN_WIRE::asStack),
    DIAMOND(18192, new int[] { 45, 196, 178, 255 }, new int[] { 107, 243, 227, 255 }, CBMItems.DIAMOND_WIRE::asStack);

    private final long conductivity;
    private final int[] color1;
    private final int[] color2;
    private final IRegistrateIsAFuckingShitNeverUseIt dropProvider;

    WireType(int conductivity, int[] color1, int[] color2, IRegistrateIsAFuckingShitNeverUseIt dropProvider) {
        this.conductivity = conductivity;
        this.color1 = color1;
        this.color2 = color2;
        this.dropProvider = dropProvider;
    }

    public long getConductivity() {
        return (long) (conductivity * CBMConfig.getCommon().conductivityMultiplier.get());
    }

    public int[] getColor1() {
        return color1.clone();
    }

    public int[] getColor2() {
        return color2.clone();
    }

    public ItemStack getDroppedItem() {
        return dropProvider.getDroppedItem();
    }
}
