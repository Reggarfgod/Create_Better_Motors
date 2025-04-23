package com.reggarf.mods.create_better_motors.ponder;

import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.simibubi.create.content.kinetics.gauge.GaugeBlock;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class upgrade {
    private static void tier(SceneBuilder builder, SceneBuildingUtil util, boolean speed) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);

        BlockPos gaugePos = util.grid().at(2, 1, 3);

        for (int x = 6; x >= 0; x--) {
            scene.idle(2);
            scene.world().showSection(util.select().position(x, 1, 3), Direction.DOWN);
        }
        scene.idle(10);

        scene.world().setBlock(gaugePos, (CBMBlocks.BASIC_MOTOR).getDefaultState()
                .setValue(GaugeBlock.FACING, Direction.UP), true);
        scene.world().setKineticSpeed(util.select().position(gaugePos), 32);
        scene.idle(10);



    }
}
