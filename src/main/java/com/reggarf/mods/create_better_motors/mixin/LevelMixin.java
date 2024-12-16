package com.reggarf.mods.create_better_motors.mixin;

import net.minecraft.world.level.Level;
import com.reggarf.mods.create_better_motors.content.electricity.network.ElectricalNetworkTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Level.class)
public abstract class LevelMixin {
    @Shadow public abstract boolean isClientSide();

    @Inject(method = "tickBlockEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V"))
    private void tickBlockEntities(CallbackInfo ci) {
        if (!this.isClientSide())
            ElectricalNetworkTicker.tickWorld((Level) (Object) this);
    }
}
