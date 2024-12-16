package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class HardenedMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return CBMConfig.getCommon().hardenedMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().hardenedMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().hardenedMotorStress.get().floatValue();
    }
}
