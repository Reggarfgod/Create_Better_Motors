package com.reggarf.mods.create_better_motors.content.electricity.network;



import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;

import java.util.*;

public class ElectricalNetworkPathManager {
    private NetworkPathConductivityContext context = new NetworkPathConductivityContext();

    protected void addConnection(ElectricalConnectorBlockEntity node, ElectricalConnectorBlockEntity node1) {
        context.addConnection(node, node1);
    }

    protected NetworkPath findConductiblePath(ElectricalConnectorBlockEntity a, ElectricalConnectorBlockEntity b) {
        List<ElectricalConnectorBlockEntity> visited = new ArrayList<>();
        Queue<QueueElement> queue = new LinkedList<>();
        queue.add(new QueueElement(a, null, 0));
        visited.add(a);

        while (!queue.isEmpty()) {
            var element = queue.poll();

            if (element.connector.equals(b)) {
                NetworkPath path = unwrapConductiblePath(element);
                if (path != null && context.calculatePathConductivity(path) > 0)
                    return path;
            }

            for (ElectricalConnectorBlockEntity connector : element.connector.getConnectors().keySet()) {
                if (!visited.contains(connector) && element.depth < CBMConfig.getCommon().maxPathfindingDepth.get()) {
                    visited.add(connector);
                    queue.add(new QueueElement(connector, element, element.depth + 1));
                }
            }
        }

        return null;
    }

    private NetworkPath unwrapConductiblePath(QueueElement element) {
        NetworkPath path = new NetworkPath();

        while (element != null) {
            if (path.getLength() != 0 && context.getConnectionConductivity(new NetworkPathKey<>(element.connector, path.getFirstNode())) <= 0)
                return null;

            path.addNodeToBeginning(element.connector);
            element = element.parent;
        }

        if (path.getLength() < 2)
            return null;

        return path;
    }

    protected NetworkPathConductivityContext getConductivityContext() {
        return context;
    }
    
    protected void setConductivityContext(NetworkPathConductivityContext context) {
        this.context = context;
    }

    protected void tick() {
        context.updateConductivity();
    }

    private record QueueElement(ElectricalConnectorBlockEntity connector, QueueElement parent, int depth) { }
}
