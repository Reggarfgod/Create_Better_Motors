package com.reggarf.mods.create_better_motors.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {
    public final ForgeConfigSpec.ConfigValue<Integer> wireSectionsPerMeter;
    public final ForgeConfigSpec.ConfigValue<Double> wireThickness;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        wireSectionsPerMeter = builder
                .comment(
                        "Choose how many wire sections are rendered in one meter (block).",
                        "Decreasing this value can theoretically improve performance"
                ).defineInRange("wireSectionsPerMeter", 10, 1, Integer.MAX_VALUE);

        wireThickness = builder
                .comment("...wire thickness...")
                .defineInRange("wireThickness", 0.03, 0, Double.MAX_VALUE);
    }
}
