package com.reggarf.mods.create_better_motors.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import static com.reggarf.mods.create_better_motors.Create_better_motors.MOD_ID;

@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonConfig {

    private static final ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
    public static ModConfigSpec COMMON_CONFIG;

    // ====== General Categories ======
    public static final String CATAGORY_MESSAGES = "Messages";
    public static final String CATAGORY_WIRES = "wires";
    public static final String CATAGORY_ACCUMULATOR = "accumulator";

    // ====== Motor Categories ======
    public static final String CATAGORY_STARTER_MOTOR = "starter_motor";
    public static final String CATAGORY_BASIC_MOTOR = "basic_motor";
    public static final String CATAGORY_HARDENED_MOTOR = "hardened_motor";
    public static final String CATAGORY_BLAZING_MOTOR = "blazing_motor";
    public static final String CATAGORY_NIOTIC_MOTOR = "niotic_motor";
    public static final String CATAGORY_SPIRITED_MOTOR = "spirited_motor";
    public static final String CATAGORY_NITRO_MOTOR = "nitro_motor";

    // ====== Alternator Categories ======
    public static final String CATAGORY_ANDESITE_ALTERNATOR = "andesite_alternator";
    public static final String CATAGORY_COPPER_ALTERNATOR = "copper_alternator";
    public static final String CATAGORY_BRASS_ALTERNATOR = "brass_alternator";

    // ====== Message Config ======
    public static ModConfigSpec.BooleanValue MESSAGES_ENABLED;

    // ====== Wires ======
    public static ModConfigSpec.IntValue HEAVY_CONNECTOR_MAX_INPUT;
    public static ModConfigSpec.IntValue HEAVY_CONNECTOR_MAX_OUTPUT;
    public static ModConfigSpec.IntValue HEAVY_CONNECTOR_MAX_LENGTH;

    // ====== Accumulator ======
    public static ModConfigSpec.IntValue ACCUMULATOR_MAX_INPUT;
    public static ModConfigSpec.IntValue ACCUMULATOR_MAX_OUTPUT;

    // ====== Motor Configs (Grouped Struct) ======
    public static class MotorConfig {
        public ModConfigSpec.IntValue RPM_RANGE;
        public ModConfigSpec.IntValue MIN_CONSUMPTION;
        public ModConfigSpec.IntValue MAX_INPUT;
        public ModConfigSpec.IntValue CAPACITY;
        public ModConfigSpec.IntValue FE_RPM;
        public ModConfigSpec.IntValue MAX_STRESS;
        public ModConfigSpec.BooleanValue AUDIO_ENABLED;
    }

    public static MotorConfig STARTER_MOTOR;
    public static MotorConfig BASIC_MOTOR;
    public static MotorConfig HARDENED_MOTOR;
    public static MotorConfig BLAZING_MOTOR;
    public static MotorConfig NIOTIC_MOTOR;
    public static MotorConfig SPIRITED_MOTOR;
    public static MotorConfig NITRO_MOTOR;

    // ====== Alternator Config Struct ======
    public static class AlternatorConfig {
        public ModConfigSpec.IntValue MAX_OUTPUT;
        public ModConfigSpec.IntValue CAPACITY;
        public ModConfigSpec.DoubleValue EFFICIENCY;
        public ModConfigSpec.IntValue FE_RPM;
        public ModConfigSpec.IntValue MAX_STRESS;
        public ModConfigSpec.BooleanValue AUDIO_ENABLED;
    }

    public static AlternatorConfig ANDESITE_ALTERNATOR;
    public static AlternatorConfig COPPER_ALTERNATOR;
    public static AlternatorConfig BRASS_ALTERNATOR;

    static {
        builder.comment("General configuration for Create: Better Motors");

        // ===== Messages =====
        builder.comment("Messages").push(CATAGORY_MESSAGES);
        MESSAGES_ENABLED = builder.comment("Enable or disable update messages.")
                .define("messages_enabled", true);
        builder.pop();

        // ===== Accumulator =====
        builder.comment("Accumulator").push(CATAGORY_ACCUMULATOR);
        ACCUMULATOR_MAX_INPUT = builder.comment("Max input in FE/t").defineInRange("max_input", 800000, 0, Integer.MAX_VALUE);
        ACCUMULATOR_MAX_OUTPUT = builder.comment("Max output in FE/t").defineInRange("max_output", 800000, 0, Integer.MAX_VALUE);
        builder.pop();

        // ===== Wires =====
        builder.comment("Wires").push(CATAGORY_WIRES);
        HEAVY_CONNECTOR_MAX_INPUT = builder.comment("Heavy Connector max input in FE/t").defineInRange("max_input", 90000, 0, Integer.MAX_VALUE);
        HEAVY_CONNECTOR_MAX_OUTPUT = builder.comment("Heavy Connector max output in FE/t").defineInRange("max_output", 90000, 0, Integer.MAX_VALUE);
        HEAVY_CONNECTOR_MAX_LENGTH = builder.comment("Heavy Connector max length in blocks").defineInRange("max_length", 48, 0, 256);
        builder.pop();

        // ===== Motors =====
        STARTER_MOTOR = motor(builder, CATAGORY_STARTER_MOTOR, 256, 8, 80000, 100000, 960, 35768);
        BASIC_MOTOR = motor(builder, CATAGORY_BASIC_MOTOR, 256, 8, 80000, 100000, 1920, 75768);
        HARDENED_MOTOR = motor(builder, CATAGORY_HARDENED_MOTOR, 256, 8, 80000, 100000, 3840, 161536);
        BLAZING_MOTOR = motor(builder, CATAGORY_BLAZING_MOTOR, 256, 8, 80000, 500000, 7680, 253072);
        NIOTIC_MOTOR = motor(builder, CATAGORY_NIOTIC_MOTOR, 256, 8, 80000, 600000, 19360, 710144);
        SPIRITED_MOTOR = motor(builder, CATAGORY_SPIRITED_MOTOR, 256, 8, 80000, 700000, 41720, 1298288);
        NITRO_MOTOR = motor(builder, CATAGORY_NITRO_MOTOR, 256, 8, 85000, 800000, 72440, 2499576);

        // ===== Alternators =====
        ANDESITE_ALTERNATOR = alternator(builder, CATAGORY_ANDESITE_ALTERNATOR, 5000, 5000, 0.85d, 1260, 35768);
        COPPER_ALTERNATOR = alternator(builder, CATAGORY_COPPER_ALTERNATOR, 5000, 5000, 0.90d, 2400, 75384);
        BRASS_ALTERNATOR = alternator(builder, CATAGORY_BRASS_ALTERNATOR, 10000, 10000, 0.90d, 4240, 159708);

        COMMON_CONFIG = builder.build();
    }

    /** Helper for motor config creation */
    private static MotorConfig motor(ModConfigSpec.Builder builder, String category,
                                     int rpmRange, int minConsumption, int maxInput,
                                     int capacity, int feRpm, int maxStress) {
        builder.comment(category).push(category);
        MotorConfig config = new MotorConfig();
        config.RPM_RANGE = builder.comment("Motor RPM range").defineInRange("rpm_range", rpmRange, 1, Integer.MAX_VALUE);
        config.MIN_CONSUMPTION = builder.comment("Minimum FE/t consumption").defineInRange("min_consumption", minConsumption, 0, Integer.MAX_VALUE);
        config.MAX_INPUT = builder.comment("Max FE/t input").defineInRange("max_input", maxInput, 0, Integer.MAX_VALUE);
        config.CAPACITY = builder.comment("Internal FE capacity").defineInRange("capacity", capacity, 0, Integer.MAX_VALUE);
        config.FE_RPM = builder.comment("FE/t at 256 RPM").defineInRange("fe_at_max_rpm", feRpm, 0, Integer.MAX_VALUE);
        config.MAX_STRESS = builder.comment("Max stress in SU at 256 RPM").defineInRange("max_stress", maxStress, 0, Integer.MAX_VALUE);
        config.AUDIO_ENABLED = builder.comment("Enable motor audio").define("audio_enabled", true);
        builder.pop();
        return config;
    }

    /** Helper for alternator config creation */
    private static AlternatorConfig alternator(ModConfigSpec.Builder builder, String category,
                                               int maxOutput, int capacity, double efficiency,
                                               int feRpm, int maxStress) {
        builder.comment(category).push(category);
        AlternatorConfig config = new AlternatorConfig();
        config.MAX_OUTPUT = builder.comment("Alternator max FE/t output").defineInRange("max_output", maxOutput, 0, Integer.MAX_VALUE);
        config.CAPACITY = builder.comment("Alternator FE capacity").defineInRange("capacity", capacity, 0, Integer.MAX_VALUE);
        config.EFFICIENCY = builder.comment("Efficiency (0.0 - 1.0)").defineInRange("efficiency", efficiency, 0.01d, 1.0d);
        config.FE_RPM = builder.comment("FE/t at 256 RPM").defineInRange("fe_at_max_rpm", feRpm, 0, Integer.MAX_VALUE);
        config.MAX_STRESS = builder.comment("Max stress at 256 RPM").defineInRange("max_stress", maxStress, 0, Integer.MAX_VALUE);
        config.AUDIO_ENABLED = builder.comment("Enable alternator audio").define("audio_enabled", true);
        builder.pop();
        return config;
    }

    // ===== Config Loading =====
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        loadConfig(CommonConfig.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("create_better_motors-common.toml"));
    }

    public static void loadConfig(ModConfigSpec spec, java.nio.file.Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync().autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();
        spec.correct(configData);
    }
}
