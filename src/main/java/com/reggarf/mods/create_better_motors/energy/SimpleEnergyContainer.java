package com.reggarf.mods.create_better_motors.energy;

public class SimpleEnergyContainer extends earth.terrarium.botarium.common.energy.impl.SimpleEnergyContainer {
    public SimpleEnergyContainer(long maxCapacity) {
        super(maxCapacity);
    }

    @Override
    public long maxInsert() {
        return Long.MAX_VALUE;
    }

    @Override
    public long maxExtract() {
        return Long.MAX_VALUE;
    }
}
