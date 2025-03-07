//package com.reggarf.mods.create_better_motors.tools;
//
//
//
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.world.level.saveddata.SavedData;
//import org.jetbrains.annotations.NotNull;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.BiConsumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//public abstract class StorageData<T> extends SavedData {
//
//    protected final Map<MotorNetworkHandler.NetworkKey, T> storages = new HashMap<>();
//
//    public T computeStorageIfAbsent(MotorNetworkHandler.NetworkKey key, Function<MotorNetworkHandler.NetworkKey, T> function) {
//        return storages.computeIfAbsent(key, function);
//    }
//
//    public @NotNull CompoundTag save(@NotNull CompoundTag tag,
//                                     Function<T, Boolean> isEmpty,
//                                     Function<T, CompoundTag> serializeNBT) {
//        storages.forEach( (key, inventory) -> {
//            if (!isEmpty.apply(inventory))
//                tag.put(key.toString(), serializeNBT.apply(inventory));
//        } );
//        return tag;
//    }
//
//    public static <T, S extends StorageData<T>> S load(CompoundTag tag,
//                                                       Supplier<S> storageDataSupplier,
//                                                       Function<MotorNetworkHandler.NetworkKey, T> storageSupplier,
//                                                       BiConsumer<T, CompoundTag> deserializeNBT) {
//        S data = storageDataSupplier.get();
//        tag.getAllKeys().forEach(k -> {
//            MotorNetworkHandler.NetworkKey key = MotorNetworkHandler.NetworkKey.fromString(k);
//            T inventory = storageSupplier.apply(key);
//            deserializeNBT.accept(inventory, tag.getCompound(k));
//            data.storages.put(key, inventory);
//        });
//        return data;
//    }
//
//}