package com.reggarf.mods.create_better_motors.content.electricity.network;

import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlock;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import earth.terrarium.botarium.common.energy.base.PlatformEnergyManager;
import earth.terrarium.botarium.common.energy.util.EnergyHooks;
import earth.terrarium.botarium.forge.energy.ForgeEnergyManager;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;


import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ElectricalNetwork {
    private final List<ElectricalConnectorBlockEntity> nodes = new ArrayList<>();
    private final Map<ElectricalConnectorBlockEntity, EnergyStorageWrapper> consumers = new HashMap<>();
    private final Map<ElectricalConnectorBlockEntity, EnergyStorageWrapper> pulledSources = new HashMap<>();

    private final ElectricalNetworkPathManager pathManager = new ElectricalNetworkPathManager();

    public ElectricalNetwork(ElectricalConnectorBlockEntity base) {
        nodes.add(base);
    }

    public void addNode(ElectricalConnectorBlockEntity node) {
        addNode(node, new ArrayList<>());
        updateConsumersAndSources();
    }

    private void addNode(ElectricalConnectorBlockEntity node, List<ElectricalConnectorBlockEntity> processedNodes) {
        if (!nodes.contains(node))
            nodes.add(node);

        processedNodes.add(node);

        if (node.getNetwork() != this) {
            if (node.getNetwork() != null)
                node.getNetwork().destroy();

            node.setNetwork(this);

            for (ElectricalConnectorBlockEntity connector : node.getConnectors().keySet()) {
                if (!processedNodes.contains(connector))
                    addNode(connector, processedNodes);
            }
        }

        for (ElectricalConnectorBlockEntity connectedNode : node.getConnectors().keySet())
            pathManager.addConnection(node, connectedNode);
    }

    public void updateConsumersAndSources() {
        consumers.clear();
        pulledSources.clear();

        for (ElectricalConnectorBlockEntity node : nodes) {
            if (node.getLevel() != null) {
                Direction dir = node.getBlockState().getValue(BlockStateProperties.FACING);
                BlockEntity entity = node.getLevel().getBlockEntity(node.getSupportingBlockPos());

                if (entity != null && !(entity instanceof ElectricalConnectorBlockEntity) && EnergyHooks.isEnergyContainer(entity, dir)) {
                    PlatformEnergyManager storage = EnergyHooks.getBlockEnergyManager(entity, dir);
                    if (storage instanceof ForgeEnergyManager fem) {
                        if (storage.supportsInsertion())
                            consumers.put(node, new EnergyStorageWrapper(entity, fem.energy()));
                        
                        if (storage.supportsExtraction() && node.getBlockState().getValue(ElectricalConnectorBlock.MODE).pull)
                            pulledSources.put(node, new EnergyStorageWrapper(entity, fem.energy()));
                    }
                }
            }
        }

        if (!getWorld().isClientSide())
            ElectricalNetworkTicker.addNetwork(this);
    }

    public long insert(ElectricalConnectorBlockEntity from, long amount, boolean simulate) {
        if (consumers.isEmpty())
            return 0;

        AtomicLong inserted = new AtomicLong();
        long perConsumer = (long) Math.ceil(amount / (double) consumers.size());

        NetworkPathConductivityContext context = simulate ?
                new NetworkPathConductivityContext(pathManager.getConductivityContext()) :
                pathManager.getConductivityContext();

        consumers.entrySet().stream()
                .sorted(Comparator.comparingDouble(e ->
                        e.getValue().storage().getEnergyStored() / (double) e.getValue().storage().getMaxEnergyStored()))
                .takeWhile(e -> inserted.get() < amount)
                .forEach(e -> inserted.addAndGet(insertInto(from, e, context, Math.min(perConsumer, amount - inserted.get()), simulate)));

        return inserted.get();
    }

    private long insertInto(ElectricalConnectorBlockEntity from,
                            Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper> to,
                            NetworkPathConductivityContext context,
                            long amount,
                            boolean simulate) {
        NetworkPath path;
        long inserted = 0;

        while ((path = pathManager.findConductiblePath(from, to.getKey())) != null && inserted < amount) {
            long pathConductivity = context.calculatePathConductivity(path);
            long insertedThroughThisPath = to.getValue().insert(Math.min(pathConductivity, amount - inserted), simulate);

            if (insertedThroughThisPath == 0)
                break;

            if (!simulate && insertedThroughThisPath > 0) {
                BlockEntity be = to.getValue().entity();

                be.setChanged();
                if (be.getLevel() instanceof ServerLevel serverLevel)
                    serverLevel.getChunkSource().blockChanged(be.getBlockPos());
            }

            inserted += insertedThroughThisPath;

            if (pathConductivity != 0)
                context.decreasePathConductivity(path, insertedThroughThisPath);
        }

        return inserted;
    }

    public void destroy() {
        ElectricalNetworkTicker.removeNetwork(this);
        for (ElectricalConnectorBlockEntity node : nodes)
            node.setNetwork(new ElectricalNetwork(node));
    }

    protected void tick() {
        for (Map.Entry<ElectricalConnectorBlockEntity, EnergyStorageWrapper> e : pulledSources.entrySet()) {
            long maxExtracted = e.getValue().extract(Long.MAX_VALUE, true);
            long inserted = insert(e.getKey(), maxExtracted, false);
            e.getValue().extract(inserted, false);
        }
        
        pathManager.tick();
    }

    public Level getWorld() {
        return nodes.get(0).getLevel();
    }

    public List<ElectricalConnectorBlockEntity> getNodes() {
        return Collections.unmodifiableList(nodes);
    }
    
    public ElectricalNetworkPathManager getPathManager() {
        return pathManager;
    }
}
