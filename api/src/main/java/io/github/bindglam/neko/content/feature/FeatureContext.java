package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import org.jetbrains.annotations.NotNull;

public sealed interface FeatureContext {
    @NotNull Content content();

    record Init(
            @NotNull Content content
    ) implements FeatureContext {
    }
}
