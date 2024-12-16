package com.reggarf.mods.create_better_motors.content.electricity.network;

import java.util.Objects;

public class NetworkPathKey<T> {
    private final T a, b;

    public NetworkPathKey(T a, T b) {
        if (a.hashCode() > b.hashCode()) {
            this.a = a;
            this.b = b;
        } else {
            this.a = b;
            this.b = a;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NetworkPathKey<?> key)
            return key.a.equals(a) && key.b.equals(b);

        return false;
    }
}
