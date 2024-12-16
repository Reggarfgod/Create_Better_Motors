package com.reggarf.mods.create_better_motors.energy;

import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.base.EnergySnapshot;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergySnapshot;
import earth.terrarium.botarium.util.Updatable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ExtractOnlyResizableEnergyContainer implements EnergyContainer, Updatable<BlockEntity> {
    private long capacity;
    private long energy;

    public ExtractOnlyResizableEnergyContainer(long capacity) {
        this.capacity = capacity;
    }

    @Override
    public long insertEnergy(long maxAmount, boolean simulate) {
        return 0;
    }

    @Override
    public long extractEnergy(long maxAmount, boolean simulate) {
        return internalExtract(maxAmount, simulate);
    }

    @Override
    public long internalInsert(long maxAmount, boolean simulate) {
        long inserted = (long) Mth.clamp(maxAmount, 0, getMaxCapacity() - getStoredEnergy());
        if (simulate) return inserted;
        this.setEnergy(this.energy + inserted);
        return inserted;
    }

    @Override
    public long internalExtract(long maxAmount, boolean simulate) {
        long extracted = (long) Mth.clamp(maxAmount, 0, getStoredEnergy());
        if (simulate) return extracted;
        this.setEnergy(this.energy - extracted);
        return extracted;
    }

    @Override
    public void setEnergy(long energy) {
        this.energy = energy;
    }

    @Override
    public long getStoredEnergy() {
        return energy;
    }

    public void setMaxCapacity(long capacity) {
        this.capacity = capacity;

        if (energy > capacity)
            energy = capacity;
    }

    @Override
    public long getMaxCapacity() {
        return capacity;
    }

    @Override
    public long maxInsert() {
        return 0;
    }

    @Override
    public long maxExtract() {
        return Long.MAX_VALUE;
    }

    @Override
    public CompoundTag serialize(CompoundTag root) {
        CompoundTag tag = root.getCompound(Botarium.BOTARIUM_DATA);
        tag.putLong("Energy", this.energy);
        root.put(Botarium.BOTARIUM_DATA, tag);
        return root;
    }

    @Override
    public void deserialize(CompoundTag root) {
        CompoundTag tag = root.getCompound(Botarium.BOTARIUM_DATA);
        this.energy = tag.getLong("Energy");
    }

    @Override
    public boolean allowsInsertion() {
        return false;
    }

    @Override
    public boolean allowsExtraction() {
        return true;
    }

    @Override
    public EnergySnapshot createSnapshot() {
        return new SimpleEnergySnapshot(this);
    }

    @Override
    public void clearContent() {
        this.energy = 0;
    }

    @Override
    public void update(BlockEntity object) {
        object.setChanged();
        object.getLevel().sendBlockUpdated(object.getBlockPos(), object.getBlockState(), object.getBlockState(), Block.UPDATE_ALL);
    }
}
