package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import org.jetbrains.annotations.NotNull;

public interface FeatureFactory<T extends Feature> {
    @NotNull T create();

    record Context(
            @NotNull Content content
    ) {
    }
}
