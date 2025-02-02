package com.reggarf.mods.create_better_motors.mixin;

import com.simibubi.create.foundation.ponder.PonderScene;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SceneBuilder.class)
public interface SceneBuilderAccessor {
	@Accessor PonderScene getScene();
}
