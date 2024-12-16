package com.reggarf.mods.create_better_motors.content.electricity.network;

import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import earth.terrarium.botarium.common.energy.base.EnergySnapshot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashMap;
import java.util.Map;

public class NetworkSnapshot implements EnergySnapshot {
    private final Map<ElectricalConnectorBlockEntity, EnergySnapshot> snapshots = new HashMap<>();
    private final NetworkPathConductivityContext context;
    
    public NetworkSnapshot(ElectricalNetwork network) {
        context = new NetworkPathConductivityContext(network.getPathManager().getConductivityContext());
        
        for (ElectricalConnectorBlockEntity connector : network.getNodes()) {
            BlockPos pos = connector.getSupportingBlockPos();
            BlockEntity be = connector.getLevel().getBlockEntity(pos);
            
            if (be instanceof BotariumEnergyBlock<?> bb && !(be instanceof ElectricalConnectorBlockEntity)) {
                snapshots.put(connector, bb.getEnergyStorage().createSnapshot());
            }
        }
    }
    
    @Override
    public void loadSnapshot(EnergyContainer container) {
        if (container instanceof NetworkEnergyContainer networkContainer) {
            networkContainer.getNetwork().getPathManager().setConductivityContext(new NetworkPathConductivityContext(context));
            
            for (Map.Entry<ElectricalConnectorBlockEntity, EnergySnapshot> e : snapshots.entrySet()) 
                e.getValue().loadSnapshot(e.getKey().getEnergyStorage());
        }
    }
}
