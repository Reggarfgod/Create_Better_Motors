package com.reggarf.mods.create_better_motors.content.electricity.network;

import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.base.EnergySnapshot;
import earth.terrarium.botarium.common.energy.impl.SimpleEnergySnapshot;
import earth.terrarium.botarium.util.Updatable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;


public class NetworkEnergyContainer implements EnergyContainer, Updatable<BlockEntity> {
    private final ElectricalConnectorBlockEntity connector;
    private ElectricalNetwork network;

    public NetworkEnergyContainer(ElectricalConnectorBlockEntity connector, ElectricalNetwork network) {
        this.connector = connector;
        this.network = network;
    }
    
    public ElectricalNetwork getNetwork() {
        return network;
    }
    
    public void setNetwork(ElectricalNetwork network) {
        this.network = network;
    }

    @Override
    public long insertEnergy(long maxAmount, boolean simulate) {
        if (network == null)
            return 0;
        
        return network.insert(connector, maxAmount, simulate);
    }

    @Override
    public long extractEnergy(long maxAmount, boolean simulate) {
        return 0;
    }

    @Override
    public long getStoredEnergy() {
        return 0;
    }

    @Override
    public long getMaxCapacity() {
        return Long.MAX_VALUE;
    }

    @Override
    public long maxInsert() {
        return Long.MAX_VALUE;
    }

    @Override
    public long maxExtract() {
        return 0;
    }

    @Override
    public boolean allowsInsertion() {
        return true;
    }

    @Override
    public boolean allowsExtraction() {
        return false;
    }

    @Override
    public EnergySnapshot createSnapshot() {
        if (network == null)
            return new SimpleEnergySnapshot(this);
        
        return new NetworkSnapshot(network);
    }

    @Override
    public void setEnergy(long energy) {

    }

    @Override
    public void clearContent() {

    }

    @Override
    public void deserialize(CompoundTag nbt) {

    }

    @Override
    public CompoundTag serialize(CompoundTag nbt) {
        return new CompoundTag();
    }

    
    @Override
    public void update(BlockEntity be) {
        be.setChanged();
        be.getLevel().sendBlockUpdated(be.getBlockPos(), be.getBlockState(), be.getBlockState(), Block.UPDATE_ALL);
    }
}
