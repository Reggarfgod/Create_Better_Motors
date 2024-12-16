package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class BlazingMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() { return CBMConfig.getCommon().blazingMotorCapacity.get(); }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().blazingMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().blazingMotorStress.get().floatValue();
    }
}
