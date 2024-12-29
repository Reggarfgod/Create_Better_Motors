package com.reggarf.mods.create_better_motors.mixin;

import com.reggarf.mods.create_better_motors.content.motor.LinkMotorNetworkHandler;
import com.simibubi.create.content.contraptions.MountedStorage;
import com.simibubi.create.foundation.utility.NBTHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MountedStorage.class)
public class MountedStorageMixin {

	@Shadow
	ItemStackHandler handler;
	@Shadow boolean valid;
	@Shadow boolean noFuel;


	@Inject(method = "serialize()Lnet/minecraft/nbt/CompoundTag;", at = @At("HEAD"), cancellable = true, remap = false)
	private void serializeVoidChest(CallbackInfoReturnable<CompoundTag> cir) {


		if (!valid) {
			cir.setReturnValue(null);
			return;
		}

		CompoundTag tag = handler.serializeNBT();
		if (noFuel) NBTHelper.putMarker(tag, "NoFuel");


	}

	@Inject(method = "deserialize(Lnet/minecraft/nbt/CompoundTag;)Lcom/simibubi/create/content/contraptions/MountedStorage;", at = @At("HEAD"), cancellable = true, remap = false)
	private static void deserializeVoidChest(CompoundTag nbt, CallbackInfoReturnable<MountedStorage> cir) {

		if (nbt == null) return;
		if (!nbt.contains("VoidChest")) return;

		MountedStorageAccessor storage = (MountedStorageAccessor) new MountedStorage(null);
		LinkMotorNetworkHandler.NetworkKey key = LinkMotorNetworkHandler.NetworkKey.deserialize(nbt.getCompound("NetworkKey"));
		storage.setValid(true);
		storage.setNoFuel(nbt.contains("NoFuel"));

		cir.setReturnValue((MountedStorage) storage);

	}

	@Inject(method = "canUseAsStorage(Lnet/minecraft/world/level/block/entity/BlockEntity;)Z", at = @At("HEAD"), cancellable = true, remap = false)
	private static void canUseVoidChestAsStorage(BlockEntity be, CallbackInfoReturnable<Boolean> cir) {
			cir.setReturnValue(true);
	}

}
