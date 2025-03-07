package com.reggarf.mods.create_better_motors.mixin;


import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.foundation.PonderScene;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(SceneBuilder.class)
public interface SceneBuilderAccessor {

	PonderScene getScene();
}
