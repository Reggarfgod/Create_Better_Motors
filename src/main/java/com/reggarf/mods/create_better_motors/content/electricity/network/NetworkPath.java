package com.reggarf.mods.create_better_motors.content.electricity.network;


import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkPath {
    private final List<ElectricalConnectorBlockEntity> nodes = new ArrayList<>();

    protected NetworkPath() {

    }

    protected void addNodeToBeginning(ElectricalConnectorBlockEntity node) {
        if (!nodes.contains(node))
            nodes.add(0, node);
    }

    protected int getLength() {
        return nodes.size();
    }

    public ElectricalConnectorBlockEntity getFirstNode() {
        return nodes.get(0);
    }

    public ElectricalConnectorBlockEntity getLastNode() {
        return nodes.get(nodes.size() - 1);
    }

    public List<ElectricalConnectorBlockEntity> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public int hashCode() {
        return nodes.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NetworkPath path)
            return nodes.equals(path.nodes);

        return false;
    }
}
