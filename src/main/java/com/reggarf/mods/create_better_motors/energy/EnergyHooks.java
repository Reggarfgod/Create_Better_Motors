package com.reggarf.mods.create_better_motors.energy;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.botarium.common.energy.EnergyApi;
import earth.terrarium.botarium.common.energy.base.BotariumEnergyBlock;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Comparator;
import java.util.List;

public class EnergyHooks {
    public static long distributeEnergyNearby(BlockEntity energyBlock, long amount) {
        BlockPos blockPos = energyBlock.getBlockPos();
        Level level = energyBlock.getLevel();
        if (level == null || !(energyBlock instanceof BotariumEnergyBlock<?> block)) return 0;
        EnergyContainer internalEnergy = block.getEnergyStorage();
        long amountToDistribute = internalEnergy.extractEnergy(amount, true);
        if (amountToDistribute == 0) return 0;
        List<EnergyContainer> list = Direction.stream()
                .map(direction -> Pair.of(direction, level.getBlockEntity(blockPos.relative(direction))))
                .filter(pair -> pair.getSecond() != null)
                .filter(pair -> EnergyApi.isEnergyBlock(pair.getSecond(), pair.getFirst().getOpposite()))
                .map(pair -> EnergyApi.getBlockEnergyContainer(pair.getSecond(), pair.getFirst().getOpposite()))
                .sorted(Comparator.comparingLong(energy -> energy.insertEnergy(amount, true)))
                .toList();
        int receiverCount = list.size();
        for (EnergyContainer energy : list) {
            if (energy == null) continue;
            long inserted = EnergyApi.moveEnergy(internalEnergy, energy, amountToDistribute / receiverCount, false);
            amountToDistribute -= inserted;
            receiverCount--;
        }
        return amount - amountToDistribute;
    }
}
