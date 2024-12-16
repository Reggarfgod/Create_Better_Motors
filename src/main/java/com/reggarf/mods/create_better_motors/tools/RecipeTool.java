package com.reggarf.mods.create_better_motors.tools;

import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.reggarf.mods.create_better_motors.Create_better_motors.MOD_ID;


public class RecipeTool {

    public static final DeferredRegister<RecipeType<?>> register_type = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> register = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static <S extends RecipeSerializer<?>> IRecipeTypeInfo createIRecipeTypeInfo(String name, S serializer) {
        var rt = new RecipeType() {
            @Override
            public String toString() {
                return name;
            }
        };

        register.register(name, () -> serializer);

        register_type.register(name, () -> rt);

        return new IRecipeTypeInfo() {
            @Override
            public ResourceLocation getId() {
                return new ResourceLocation(Create_better_motors.MOD_ID, name);
            }

            @Override
            public <T extends RecipeSerializer<?>> T getSerializer() {
                return (T) serializer;
            }

            @Override
            public <T extends RecipeType<?>> T getType() {
                return (T) rt;
            }
        };
    }
}
