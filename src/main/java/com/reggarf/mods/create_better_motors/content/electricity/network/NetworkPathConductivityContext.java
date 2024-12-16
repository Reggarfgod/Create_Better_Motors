package com.reggarf.mods.create_better_motors.content.electricity.network;

import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import net.minecraft.util.Tuple;

import java.util.HashMap;
import java.util.Map;

public class NetworkPathConductivityContext {
    private final Map<NetworkPathKey<ElectricalConnectorBlockEntity>, Tuple<Long, Long>> connections;

    public NetworkPathConductivityContext() {
        connections = new HashMap<>();
    }

    public NetworkPathConductivityContext(NetworkPathConductivityContext context) {
        connections = new HashMap<>();

        for (Map.Entry<NetworkPathKey<ElectricalConnectorBlockEntity>, Tuple<Long, Long>> e : context.connections.entrySet())
            connections.put(e.getKey(), new Tuple<>(e.getValue().getA(), e.getValue().getB()));
    }

    public void addConnection(ElectricalConnectorBlockEntity node, ElectricalConnectorBlockEntity node1) {
        if (!node.equals(node1) && !connections.containsKey(new NetworkPathKey<>(node, node1)))
            connections.put(new NetworkPathKey<>(node, node1), new Tuple<>(node.getConnectors().get(node1).getConductivity(), 0L));
    }

    protected long calculatePathConductivity(NetworkPath path) {
        ElectricalConnectorBlockEntity prevNode = null;
        long conductivity = Long.MAX_VALUE;

        for (ElectricalConnectorBlockEntity node : path.getNodes()) {
            if (prevNode == null) {
                prevNode = node;
                continue;
            }

            NetworkPathKey<ElectricalConnectorBlockEntity> key = new NetworkPathKey<>(prevNode, node);

            if (!connections.containsKey(key))
                return 0;

            long connectionConductivity = connections.get(key).getB();
            conductivity = Math.min(connectionConductivity, conductivity);
            prevNode = node;
        }

        return conductivity;
    }

    protected void decreasePathConductivity(NetworkPath path, long amount) {
        ElectricalConnectorBlockEntity prevNode = null;

        for (ElectricalConnectorBlockEntity node : path.getNodes()) {
            if (prevNode == null) {
                prevNode = node;
                continue;
            }

            NetworkPathKey<ElectricalConnectorBlockEntity> key = new NetworkPathKey<>(prevNode, node);
            long connectionConductivity = connections.get(key).getB();
            connections.get(key).setB(connectionConductivity - amount);
            prevNode = node;
        }
    }

    protected long getConnectionConductivity(NetworkPathKey<ElectricalConnectorBlockEntity> key) {
        return connections.get(key).getB();
    }

    protected void updateConductivity() {
        for (Map.Entry<NetworkPathKey<ElectricalConnectorBlockEntity>, Tuple<Long, Long>> e : connections.entrySet())
            connections.get(e.getKey()).setB(e.getValue().getA());
    }
}
