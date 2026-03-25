package io.github.bindglam.neko.content.feature;

import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Feature extends Keyed {
    default void init(@NotNull FeatureContext.Init context) {
    }
}
