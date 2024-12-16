package com.reggarf.mods.create_better_motors.content.electricity.connector;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum ElectricalConnectorMode implements StringRepresentable {
    INERT(false),
    PULL(true);

    public final boolean pull;

    ElectricalConnectorMode(boolean pull) {
        this.pull = pull;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }
}
