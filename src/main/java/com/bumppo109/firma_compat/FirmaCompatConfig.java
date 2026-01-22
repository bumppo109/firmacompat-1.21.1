package com.bumppo109.firma_compat;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

public class FirmaCompatConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<List<? extends String>> ALLOWED_NAMESPACES = BUILDER
            .comment("Namespaces whose recipes should NOT be removed")
            .defineList("allowed_namespaces",
                    List.of("firma_compat", "everycomp", "stonezone"),
                    obj -> obj instanceof String);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static List<String> getAllowedNamespaces() {
        return ALLOWED_NAMESPACES.get().stream()
                .map(String::valueOf)
                .toList();
    }
}
