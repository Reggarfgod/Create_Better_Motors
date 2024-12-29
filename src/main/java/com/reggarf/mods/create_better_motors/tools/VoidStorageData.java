package com.reggarf.mods.create_better_motors.tools;


import com.reggarf.mods.create_better_motors.content.motor.LinkMotorNetworkHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class VoidStorageData<T> extends SavedData {

    protected final Map<LinkMotorNetworkHandler.NetworkKey, T> storages = new HashMap<>();

    public T computeStorageIfAbsent(LinkMotorNetworkHandler.NetworkKey key, Function<LinkMotorNetworkHandler.NetworkKey, T> function) {
        return storages.computeIfAbsent(key, function);
    }

    public @NotNull CompoundTag save(@NotNull CompoundTag tag,
                                     Function<T, Boolean> isEmpty,
                                     Function<T, CompoundTag> serializeNBT) {
        storages.forEach( (key, inventory) -> {
            if (!isEmpty.apply(inventory))
                tag.put(key.toString(), serializeNBT.apply(inventory));
        } );
        return tag;
    }

    public static <T, S extends VoidStorageData<T>> S load(CompoundTag tag,
                                                           Supplier<S> storageDataSupplier,
                                                           Function<LinkMotorNetworkHandler.NetworkKey, T> storageSupplier,
                                                           BiConsumer<T, CompoundTag> deserializeNBT) {
        S data = storageDataSupplier.get();
        tag.getAllKeys().forEach(k -> {
            LinkMotorNetworkHandler.NetworkKey key = LinkMotorNetworkHandler.NetworkKey.fromString(k);
            T inventory = storageSupplier.apply(key);
            deserializeNBT.accept(inventory, tag.getCompound(k));
            data.storages.put(key, inventory);
        });
        return data;
    }

}