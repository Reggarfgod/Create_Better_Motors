package com.reggarf.mods.create_better_motors.content.electricity.network;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.energy.IEnergyStorage;

public record EnergyStorageWrapper(BlockEntity entity, IEnergyStorage storage) {
    public long insert(long maxAmount, boolean simulate) {
        return storage.receiveEnergy((int) Math.min(Math.max(maxAmount, Integer.MIN_VALUE), Integer.MAX_VALUE), simulate);
    }
    
    public long extract(long maxAmount, boolean simulate) {
        return storage.extractEnergy((int) Math.min(Math.max(maxAmount, Integer.MIN_VALUE), Integer.MAX_VALUE), simulate);
    }
}
