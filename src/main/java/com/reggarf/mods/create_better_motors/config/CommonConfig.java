package com.reggarf.mods.create_better_motors.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonConfig {

    public static final String CATAGORY_ALTERNATOR = "alternator";
    public static final String CATAGORY_BASIC = "basic motor";
    public static final String CATAGORY_STARTER = "starter motor";
    public static final String CATAGORY_HARDENED = "hardened motor";
    public static final String CATAGORY_BLAZING = "blazing motor";
    public static final String CATAGORY_NIOTIC = "niotic motor";
    public static final String CATAGORY_SPIRITED = "spirited motor";
    public static final String CATAGORY_NITRO = "nitro motor";

    public static ForgeConfigSpec.IntValue FE_RPM;
    public static ForgeConfigSpec.IntValue MAX_STRESS;

    public static ForgeConfigSpec.BooleanValue AUDIO_ENABLED;

    public static ForgeConfigSpec.IntValue ALTERNATOR_MAX_OUTPUT;
    public static ForgeConfigSpec.IntValue ALTERNATOR_CAPACITY;
    public static ForgeConfigSpec.DoubleValue ALTERNATOR_EFFICIENCY;


    public final ForgeConfigSpec.ConfigValue<Double> suToEnergy;

    public final ForgeConfigSpec.ConfigValue<Double> conductivityMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> maxPathfindingDepth;
    public final ForgeConfigSpec.ConfigValue<Integer> maxWireLength;
    public final ForgeConfigSpec.ConfigValue<Double> motorSUMultiplier;
    public final ForgeConfigSpec.ConfigValue<Integer> starterMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> starterMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> starterMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> blazingMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> blazingMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> blazingMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> basicMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> basicMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> basicMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> hardenedMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> hardenedMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> hardenedMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> nioticMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> nioticMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> nioticMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> spiritedMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> spiritedMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> spiritedMotorSpeed;
    public final ForgeConfigSpec.ConfigValue<Integer> nitroMotorCapacity;
    public final ForgeConfigSpec.ConfigValue<Double> nitroMotorStress;
    public final ForgeConfigSpec.ConfigValue<Double> nitroMotorSpeed;

    public CommonConfig(ForgeConfigSpec.Builder builder) {

        suToEnergy = builder
                .comment(
                        "Responsible for how much energy is generated per 1 stress unit in a tick",
                        "Default value is supposed to be compatible with default configuration of Create: Better Motors"
                ).defineInRange("suToEnergy", 0.029296875, 0, Double.MAX_VALUE);

        conductivityMultiplier = builder
                .comment("Multiplier of wire conductivity")
                .defineInRange("conductivityMultiplier", 1.0, 0, Double.MAX_VALUE);

        maxPathfindingDepth = builder
                .comment("Maximum depth of network pathfinding")
                .defineInRange("maxPathfindingDepth", 32, 1, Integer.MAX_VALUE);

        maxWireLength = builder
                .comment("Maximum wire length")
                .defineInRange("maxWireLength", 16, 1, Integer.MAX_VALUE);

        builder.comment("Make sure config changes are duplicated on both Clients and the Server when running a dedicated Server,")
                .comment(" as the config isnt synced between Clients and Server.");

        builder.comment("Alternator").push(CATAGORY_ALTERNATOR);
        ALTERNATOR_MAX_OUTPUT = builder.comment("Alternator max input in ⚡ (Energy transfer, not generation).")
                .defineInRange("generator_max_output", 5000, 0, Integer.MAX_VALUE);

        ALTERNATOR_CAPACITY = builder.comment("Alternator internal capacity in ⚡.")
                .defineInRange("generator_capacity", 5000, 0, Integer.MAX_VALUE);

        ALTERNATOR_EFFICIENCY = builder.comment("Alternator efficiency relative to base conversion rate.")
                .defineInRange("generator_efficiency", 0.75d, 0.01d, 3.0d);

        FE_RPM = builder.comment("Forge Energy conversion rate (in ⚡/t at 256 RPM, value is the ⚡/t generated and consumed is at 256rpm).")
                .defineInRange("⚡_at_max_rpm", 609, 0, Integer.MAX_VALUE);

        MAX_STRESS = builder.comment("Max stress for the Alternator and Electric Motor (in SU at 256 RPM).")
                .defineInRange("max_stress", 10000, 0, Integer.MAX_VALUE);

        AUDIO_ENABLED = builder.comment("If audio should be enabled or not.")
                .define("audio_enabled", true);

        builder.pop();
        builder.push("Motors");

        motorSUMultiplier = builder
                .comment("Maximum motor SU multiplier")
                .defineInRange("motorSuMultiplier", 1.0, 0.0, Double.MAX_VALUE);

        builder.comment("General Settings").push(CATAGORY_BASIC);
        basicMotorCapacity = builder
            .comment("Internal energy capacity of an basic motor")
            .defineInRange("basic MotorCapacity", 64000, 1, Integer.MAX_VALUE);
        basicMotorStress = builder
                .comment("Generated SU of an basic motor")
                .defineInRange("basicMotorStress", 3024, 1, Double.MAX_VALUE);

        basicMotorSpeed = builder
            .comment("Top Speed of an basic motor")
            .defineInRange("basicMotorSpeed", 32, 1, Double.MAX_VALUE);
        builder.pop();
        builder.comment("General Settings").push(CATAGORY_STARTER);
        starterMotorCapacity = builder
                .comment("Internal energy capacity of a starter motor")
                .defineInRange("starterMotorCapacity", 44000, 1, Integer.MAX_VALUE);

        starterMotorStress = builder
            .comment("Generated SU of a starter motor")
            .defineInRange("starterMotorStress", 1028, 1, Double.MAX_VALUE);
        starterMotorSpeed = builder
                .comment("Top Speed of a starter motor")
                .defineInRange("starterMotorSpeed", 16, 1, Double.MAX_VALUE);
        builder.pop();
        builder.comment("General Settings").push(CATAGORY_BLAZING);
        blazingMotorCapacity = builder
                .comment("Internal energy capacity of a blazing motor")
                .defineInRange("blazingMotorCapacity", 124000, 1, Integer.MAX_VALUE);
        blazingMotorStress = builder
                .comment("Generated SU of a blazing motor")
                .defineInRange("blazingMotorStress", 10024, 1, Double.MAX_VALUE);
        blazingMotorSpeed = builder
                .comment("Top Speed of a blazing motor")
                .defineInRange("blazingMotorSpeed", 124, 1, Double.MAX_VALUE);
        builder.pop();
        builder.comment("General Settings").push(CATAGORY_HARDENED);
        hardenedMotorCapacity = builder
                .comment("Internal energy capacity of a hardened motor")
                .defineInRange("hardenedMotorCapacity", 84000, 1, Integer.MAX_VALUE);
        hardenedMotorStress = builder
                .comment("Generated SU of a hardened motor")
                .defineInRange("hardenedMotorStress", 8024, 1, Double.MAX_VALUE);
        hardenedMotorSpeed = builder
                .comment("Top Speed of a hardened motor")
                .defineInRange("hardenedMotorSpeed", 64, 1, Double.MAX_VALUE);
        builder.pop();
        builder.comment("General Settings").push(CATAGORY_NIOTIC);
        nioticMotorCapacity = builder
                .comment("Internal energy capacity of a niotic motor")
                .defineInRange("nioticMotorCapacity", 164000, 1, Integer.MAX_VALUE);
        nioticMotorStress = builder
                .comment("Generated SU of a niotic motor")
                .defineInRange("nioticMotorStress", 100024, 1, Double.MAX_VALUE);
        nioticMotorSpeed = builder
                .comment("Top Speed of a niotic motor")
                .defineInRange("nioticMotorSpeed", 148, 1, Double.MAX_VALUE);
        builder.pop();
        builder.comment("General Settings").push(CATAGORY_SPIRITED);
        spiritedMotorCapacity = builder
                .comment("Internal energy capacity of a spirited motor")
                .defineInRange(" spiritedMotorCapacity", 224000, 1, Integer.MAX_VALUE);
        spiritedMotorStress = builder
                .comment("Generated SU of a spirited motor")
                .defineInRange(" spiritedMotorStress", 300024, 1, Double.MAX_VALUE);
        spiritedMotorSpeed = builder
                .comment("Top Speed of a spirited motor")
                .defineInRange("spiritedMotorSpeed", 200, 1, Double.MAX_VALUE);
        builder.pop();
        builder.comment("General Settings").push(CATAGORY_NITRO);
        nitroMotorCapacity = builder
                .comment("Internal energy capacity of a nitro motor")
                .defineInRange(" nitroMotorCapacity", 524000, 1, Integer.MAX_VALUE);
        nitroMotorStress = builder
                .comment("Generated SU of a nitro motor")
                .defineInRange(" spiritedMotorStress", 600024, 1, Double.MAX_VALUE);
        nitroMotorSpeed = builder
                .comment("Top Speed of a nitro motor")
                .defineInRange("nitroMotorSpeed", 256, 1, Double.MAX_VALUE);
        builder.pop();

    }
    public static void loadConfig(ForgeConfigSpec spec, java.nio.file.Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.setConfig(configData);
    }
}

