package com.reggarf.mods.create_better_motors.content.electricity;

import com.reggarf.mods.create_better_motors.registry.CBMItems;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;


import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import com.reggarf.mods.create_better_motors.content.electricity.wire.WireType;

public class ElectricityPonder {

    public static void ponder(SceneBuilder scene, SceneBuildingUtil util) {
        scene.configureBasePlate(0, 0, 6);
        scene.title("wires", "Wires");
        scene.addKeyframe();
        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(5);

        scene.world.setKineticSpeed(util.select.fromTo(3, 2, 3, 5, 2, 3), 32);

        scene.world.showSection(util.select.fromTo(4, 1, 0, 5, 5, 5), Direction.DOWN);
        scene.idle(2);
        scene.world.showSection(util.select.position(3, 2, 3), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 1, 2), Direction.DOWN);
        scene.idle(5);

        scene.world.showSection(util.select.position(3, 2, 2), Direction.SOUTH);
        scene.world.showSection(util.select.position(1, 2, 2), Direction.DOWN);

        scene.idle(5);

        scene.overlay.showText(100)
                .pointAt(util.vector.centerOf(3, 2, 2))
                .text("");

        scene.idle(75);
        
        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(3, 2, 2), Pointing.DOWN)
                .withItem(CBMItems.GOLDEN_WIRE.asStack()), 20);

        scene.idle(25);

        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(1, 2, 2), Pointing.DOWN)
                .withItem(CBMItems.GOLDEN_WIRE.asStack()), 20);

        scene.idle(20);

        scene.world.modifyBlockEntity(new BlockPos(1, 2, 2), ElectricalConnectorBlockEntity.class, ElectricityPonder::connect);
        scene.world.setKineticSpeed(util.select.position(1, 1, 2), 16);
        scene.effects.rotationSpeedIndicator(util.grid.at(1, 1, 2));

        scene.idle(30);
        scene.addKeyframe();
        scene.idle(10);

        scene.overlay.chaseBoundingBoxOutline(PonderPalette.GREEN, new Object(), new AABB(3, 2, 3, 4, 3, 4), 100);

        scene.overlay.showText(100)
                .pointAt(util.vector.centerOf(3, 2, 3))
                .text("");

        scene.idle(100);

        scene.overlay.chaseBoundingBoxOutline(PonderPalette.RED, new Object(), new AABB(3, 2, 3, 4, 3, 4), 120);
        scene.world.setKineticSpeed(util.select.position(1, 1, 2), 0);

        scene.idle(10);

        scene.overlay.showText(120)
                .pointAt(util.vector.centerOf(3, 2, 3))
                .text("");
        
        scene.idle(120);

        scene.overlay.showControls(new InputWindowElement(util.vector.centerOf(3, 2, 2), Pointing.DOWN).rightClick()
                .withWrench(), 20);
        
        scene.idle(5);

        scene.world.setKineticSpeed(util.select.position(1, 1, 2), 16);
        scene.effects.rotationSpeedIndicator(util.grid.at(1, 1, 2));

        scene.idle(30);
        scene.addKeyframe();
        scene.idle(10);
        
        scene.world.hideSection(util.select.fromTo(4, 1, 1, 5, 4, 5), Direction.UP);
        
        scene.world.hideSection(util.select.position(3, 2, 3), Direction.UP);
        scene.idle(15);

        scene.world.showSection(util.select.position(3, 2, 3), Direction.DOWN);
        
        scene.world.setKineticSpeed(util.select.position(1, 1, 2), 0);

        scene.overlay.showText(150)
                .pointAt(util.vector.centerOf(3, 2, 3))
                .text("");
        
        scene.idle(50);
    }

    public static void connect(ElectricalConnectorBlockEntity en) {
        en.connect((ElectricalConnectorBlockEntity) en.getLevel().getBlockEntity(en.getBlockPos().offset(2, 0, 0)), WireType.GOLD);
    }
}
