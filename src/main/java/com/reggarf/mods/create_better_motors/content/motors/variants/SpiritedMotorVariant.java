package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class SpiritedMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return CBMConfig.getCommon().spiritedMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().spiritedMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().spiritedMotorStress.get().floatValue();
    }
}
