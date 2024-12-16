package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class NitroMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return CBMConfig.getCommon().nitroMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().nitroMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().nitroMotorStress.get().floatValue();
    }
}
