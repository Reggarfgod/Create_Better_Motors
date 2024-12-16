package com.reggarf.mods.create_better_motors.content.electricity.network;

import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectricalNetworkTicker {
    private static final Map<Level, List<ElectricalNetwork>> networks = new HashMap<>();

    public static void addNetwork(ElectricalNetwork network) {
        if (network.getWorld() == null)
            return;

        List<ElectricalNetwork> networkList = networks.getOrDefault(network.getWorld(), new ArrayList<>());

        if (!networkList.contains(network))
            networkList.add(network);

        networks.put(network.getWorld(), networkList);
    }

    public static void removeNetwork(ElectricalNetwork network) {
        if (!networks.containsKey(network.getWorld()))
            return;

        networks.get(network.getWorld()).remove(network);
    }

    public static void tickWorld(Level world) {
        if (!networks.containsKey(world))
            return;

        for (ElectricalNetwork network : networks.get(world))
            network.tick();
    }
}
