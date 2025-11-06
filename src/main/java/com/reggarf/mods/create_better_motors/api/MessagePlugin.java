package com.reggarf.mods.create_better_motors.api;

import com.reggarf.mods.better_lib.message.api.JoinMessagePlugin;
import com.reggarf.mods.better_lib.message.api.JoinMessagePlugins;
import com.reggarf.mods.better_lib.message.event.JoinMessageSet;
import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.reggarf.mods.create_better_motors.config.CommonConfig;


import java.util.List;

public class MessagePlugin implements JoinMessagePlugin {

    @Override
    public String getModId() {
        return Create_better_motors.MOD_ID;
    }

    @Override
    public boolean enabled() {
        return CommonConfig.MESSAGES_ENABLED.get();
    }

    @Override
    public List<JoinMessageSet> getMessageSets() {
        return List.of(
            new JoinMessageSet()
                .addText("Hello, thank you for downloading Create: Better Motors Check Changelog!", "FFD700")
                .addBlankLine()
                .addLink("(support, updates)", "https://discord.gg/kb6BntpcYq", "5599FF", "Changelog")
                    .addLink("(ZAP-Hosting)", "https://zap-hosting.com/reggarf", "00FFFF", "20% off with code Reggarf-1047")
                    .addLink("(github/wiki)", "https://github.com/Reggarfgod/Create_Better_Motors/issues", "A9A9A9", "Issue Tracker")
        );
    }

    public static void register() {
        JoinMessagePlugins.register(new MessagePlugin());
    }
}