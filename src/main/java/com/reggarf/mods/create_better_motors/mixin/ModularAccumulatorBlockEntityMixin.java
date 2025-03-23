package com.reggarf.mods.create_better_motors.mixin;


import com.mrh0.createaddition.blocks.modular_accumulator.ModularAccumulatorBlockEntity;
import com.mrh0.createaddition.energy.InternalEnergyStorage;
import com.reggarf.mods.create_better_motors.config.CommonConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.mrh0.createaddition.blocks.modular_accumulator.ModularAccumulatorBlockEntity.getCapacityMultiplier;

@Mixin(value = ModularAccumulatorBlockEntity.class, remap = false)
public class ModularAccumulatorBlockEntityMixin {

    @Inject(method = "createEnergyStorage", at = @At("HEAD"), cancellable = true)
    private void onCreateEnergyStorage(CallbackInfoReturnable<InternalEnergyStorage> cir) {
        // Custom capacity/input/output values
        int customCapacity = getCapacityMultiplier();
        int customInput = CommonConfig.ACCUMULATOR_MAX_INPUT.get();
        int customOutput = CommonConfig.ACCUMULATOR_MAX_OUTPUT.get();

        // Create a custom InternalEnergyStorage
        InternalEnergyStorage customStorage = new InternalEnergyStorage(customCapacity, customInput, customOutput);

        // Return your custom storage, cancelling the original method
        cir.setReturnValue(customStorage);
    }

}