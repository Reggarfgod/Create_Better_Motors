package com.reggarf.mods.create_better_motors.ponder;


import com.reggarf.mods.create_better_motors.Create_better_motors;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class CECPonderPlugin implements PonderPlugin {
    @Override
    public String getModId() {
        return Create_better_motors.MOD_ID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        CECPonderIndex.register(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        CECPonderTags.register(helper);
    }
}
