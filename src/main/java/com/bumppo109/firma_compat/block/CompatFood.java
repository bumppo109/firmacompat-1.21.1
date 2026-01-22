package com.bumppo109.firma_compat.block;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum CompatFood implements StringRepresentable {
    MELON_SLICE(true),
    BARLEY
    ;

    private final boolean hasJam;
    private final String serializedName;

    private CompatFood() {
        this(false);
    }

    private CompatFood(boolean hasJam) {
        this.serializedName = this.name().toLowerCase(Locale.ROOT);
        this.hasJam = hasJam;
    }

    public String getSerializedName() {
        return this.serializedName;
    }

    public boolean hasJam() {
        return this.hasJam;
    }
}
