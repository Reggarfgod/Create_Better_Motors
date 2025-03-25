package com.reggarf.mods.create_better_motors.ponder;


import com.mrh0.createaddition.energy.WireType;
import com.mrh0.createaddition.index.CAItems;
import com.reggarf.mods.create_better_motors.content.heavy_connector.HeavyConnectorBlockEntity;
import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;




public class ElectricityPonder {

    public static void Electricity(SceneBuilder builder, SceneBuildingUtil util) {
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.configureBasePlate(0, 0, 6);
        scene.title("wires", "Wires");
        scene.addKeyframe();
        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(5);

        scene.world().setKineticSpeed(util.select().fromTo(3, 2, 3, 5, 2, 3), 32);

        scene.world().showSection(util.select().fromTo(4, 1, 0, 5, 5, 5), Direction.DOWN);
        scene.idle(2);
        scene.world().showSection(util.select().position(3, 2, 3), Direction.DOWN);
        scene.idle(5);
        scene.world().showSection(util.select().position(1, 1, 2), Direction.DOWN);
        scene.idle(5);

        scene.world().showSection(util.select().position(3, 2, 2), Direction.SOUTH);
        scene.world().showSection(util.select().position(1, 2, 2), Direction.DOWN);

        scene.idle(5);

        scene.overlay().showText(100)
                .pointAt(util.vector().centerOf(3, 2, 2))
                .text("");

        scene.idle(75);

        scene.overlay().showControls(util.vector().centerOf(3, 2, 2), Pointing.DOWN, 20)
                .withItem(CAItems.ELECTRUM_SPOOL.asStack());

        scene.idle(25);

        scene.overlay().showControls(util.vector().centerOf(1, 2, 2), Pointing.DOWN, 20)
                .withItem(CAItems.ELECTRUM_SPOOL.asStack());

        scene.idle(20);

       //scene.world().modifyBlockEntity(new BlockPos(1, 2, 2), HeavyConnectorBlockEntity.class, ElectricityPonder::connect);
       scene.world().setKineticSpeed(util.select().position(1, 1, 2), 16);
        scene.effects().rotationSpeedIndicator(util.grid().at(1, 1, 2));

        scene.idle(30);
        scene.addKeyframe();
        scene.idle(10);

        scene.overlay().chaseBoundingBoxOutline(PonderPalette.GREEN, new Object(), new AABB(3, 2, 3, 4, 3, 4), 100);

        scene.overlay().showText(100)
                .pointAt(util.vector().centerOf(3, 2, 3))
                .text("");

        scene.idle(100);

        scene.overlay().chaseBoundingBoxOutline(PonderPalette.RED, new Object(), new AABB(3, 2, 3, 4, 3, 4), 120);
        scene.world().setKineticSpeed(util.select().position(1, 1, 2), 0);

        scene.idle(10);

        scene.overlay().showText(120)
                .pointAt(util.vector().centerOf(3, 2, 3))
                .text("");

        scene.idle(120);

        scene.overlay().showControls(util.vector().centerOf(3, 2, 2), Pointing.DOWN, 20).rightClick()
                .withItem(AllItems.WRENCH.asStack());

        scene.idle(5);

        scene.world().setKineticSpeed(util.select().position(1, 1, 2), 16);
        scene.effects().rotationSpeedIndicator(util.grid().at(1, 1, 2));

        scene.idle(30);
        scene.addKeyframe();
        scene.idle(10);


    }

//    public static void connect(HeavyConnectorBlockEntity en) {
//        en.connect((HeavyConnectorBlockEntity) en.getLevel().getBlockEntity(en.getBlockPos().offset(2, 0, 0)), WireType.GOLD);
//    }

}
