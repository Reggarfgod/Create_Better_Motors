package com.reggarf.mods.create_better_motors.content.motors.variants;

import com.reggarf.mods.create_better_motors.config.CBMConfig;

public class NioticMotorVariant implements IMotorVariant {
    @Override
    public long getMaxCapacity() {
        return CBMConfig.getCommon().nioticMotorCapacity.get();
    }

    @Override
    public float getSpeed() {
        return CBMConfig.getCommon().nioticMotorSpeed.get().floatValue();
    }

    @Override
    public float getStress() {
        return CBMConfig.getCommon().nioticMotorStress.get().floatValue();
    }
}
