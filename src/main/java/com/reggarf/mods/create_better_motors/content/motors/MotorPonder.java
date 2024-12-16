package com.reggarf.mods.create_better_motors.content.motors;

import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import com.reggarf.mods.create_better_motors.content.electricity.wire.WireType;

public class MotorPonder {
    public static void motor(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("motor", "Motors!");
        scene.configureBasePlate(0, 0, 5);

        scene.world.showSection(util.select.layer(0), Direction.DOWN);
        scene.idle(15);

        scene.world.showSection(util.select.layer(1), Direction.DOWN);
        scene.idle(15);

        scene.world.showSection(util.select.layer(2), Direction.DOWN);
        scene.idle(15);


        scene.overlay.showText(65)
                .text("Motors allow you to generate rotational force from electricity.")
                .placeNearTarget()
                .pointAt(util.vector.of(2.5, 2, 2.5));

        scene.idle(75);

        scene.world.showSection(util.select.position(2, 3, 2), Direction.DOWN);
        scene.world.showSection(util.select.position(4, 3, 2), Direction.EAST);

        scene.idle(20);

        scene.world.modifyBlockEntity(util.grid.at(2, 3, 2), ElectricalConnectorBlockEntity.class, 
                (en) -> en.connect((ElectricalConnectorBlockEntity) en.getLevel().getBlockEntity(util.grid.at(4, 3, 2)), WireType.IRON));

        scene.idle(5);

        scene.world.setKineticSpeed(util.select.position(2, 2, 2), 16);
        scene.effects.rotationSpeedIndicator(util.grid.at(2, 2, 2));

        scene.idle(40);

        scene.addKeyframe();

        scene.idle(10);

        scene.overlay.showText(100)
                .text("You can configure its speed using a wrench, however the amount of SU it outputs will stay the same.")
                .placeNearTarget()
                .pointAt(util.vector.of(2.5, 2.5, 2.25));

        scene.idle(110);

        Vec3 pos = util.vector.of(2.5, 2.5, 2.3);
        scene.overlay.chaseBoundingBoxOutline(PonderPalette.BLUE, pos, new AABB(pos, pos).inflate(0.08), 80);
        scene.overlay.showControls(new InputWindowElement(util.vector.of(2.5, 2.5, 2.25), Pointing.DOWN).leftClick().withWrench(), 80);

        scene.idle(10);

        scene.world.setKineticSpeed(util.select.position(2, 2, 2), -16);
        scene.effects.rotationSpeedIndicator(util.grid.at(2, 2, 2));

        scene.idle(20);

        scene.world.setKineticSpeed(util.select.position(2, 2, 2), -4);
        scene.effects.rotationSpeedIndicator(util.grid.at(2, 2, 2));

        scene.idle(20);

        scene.world.setKineticSpeed(util.select.position(2, 2, 2), -32);
        scene.effects.rotationSpeedIndicator(util.grid.at(2, 2, 2));

        scene.idle(20);

        scene.world.setKineticSpeed(util.select.position(2, 2, 2), 16);
        scene.effects.rotationSpeedIndicator(util.grid.at(2, 2, 2));

        scene.idle(40);

        scene.addKeyframe();

        scene.idle(10);

        scene.overlay.showText(50)
                .text("By powering it with redstone you can disable it.")
                .placeNearTarget()
                .pointAt(util.vector.of(2.5, 3.2, 2.5));


        scene.idle(5);
        scene.world.setBlock(util.grid.at(2, 2, 3), Blocks.REDSTONE_TORCH.defaultBlockState(), true);
        scene.idle(5);
        scene.world.setKineticSpeed(util.select.position(2, 2, 2), 0);
        scene.idle(50);

        scene.overlay.showText(70)
                .text("You can also inverse that mechanic by clicking it with a wrench.")
                .placeNearTarget()
                .pointAt(util.vector.of(2.5, 3.2, 2.5));

        scene.idle(5);

        scene.overlay.showControls(new InputWindowElement(util.vector.of(2.5, 3.0, 2.5), Pointing.DOWN).rightClick(), 70);

        scene.idle(5);

        scene.world.setKineticSpeed(util.select.position(2, 2, 2), 16);

        scene.idle(40);

        scene.world.destroyBlock(util.grid.at(2, 2, 3));
        scene.idle(5);
        scene.world.setKineticSpeed(util.select.position(2, 2, 2), 0);

        scene.idle(50);
    }

}
