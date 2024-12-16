package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class BasicMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return CBMConfig.getCommon().basicMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().basicMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().basicMotorStress.get().floatValue();
    }
}
