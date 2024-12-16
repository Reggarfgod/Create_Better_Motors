package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class StarterMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return CBMConfig.getCommon().starterMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().starterMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().starterMotorStress.get().floatValue();
    }
}
