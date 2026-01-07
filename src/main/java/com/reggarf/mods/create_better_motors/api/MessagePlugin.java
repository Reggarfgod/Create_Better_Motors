package com.reggarf.mods.create_better_motors.api;

import com.reggarf.mods.better_lib.message.api.JoinMessagePlugin;
import com.reggarf.mods.better_lib.message.api.JoinMessagePlugins;
import com.reggarf.mods.better_lib.message.api.JoinMessageSet;

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
                        .addText(
                                "Thank you for using Create: Better Motors! Recent updates improve stability, syncing, and goggles accuracy.",
                                0xFFFFFF
                        )
                        .addLink(
                                "(support & updates)",
                                "https://discord.gg/kb6BntpcYq",
                                0x5599FF,
                                "Discord server • Announcements • Help"
                        )
                        .addLink(
                                "(ZAP-Hosting)",
                                "https://zap-hosting.com/reggarf",
                                0x00FFFF,
                                "20% off game servers with code Reggarf-1047"
                        )
                        .addLink(
                                "(github / issues)",
                                "https://github.com/Reggarfgod/Create_Better_Motors/issues",
                                0xA9A9A9,
                                "Bug reports, suggestions, and tracking"
                        )
                        .addLink(
                                "(config)",
                                "",
                                0xF7C742,
                                "This message shows once and can be disabled in the config"
                        )
        );
    }

    public static void register() {
        JoinMessagePlugins.register(new MessagePlugin());
    }
}
