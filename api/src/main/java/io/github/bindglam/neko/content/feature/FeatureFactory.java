package io.github.bindglam.neko.content.feature;

import org.jetbrains.annotations.NotNull;

public interface FeatureFactory<T extends Feature> {
    @NotNull T create();
}
