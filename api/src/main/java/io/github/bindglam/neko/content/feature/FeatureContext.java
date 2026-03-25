package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;

public sealed interface FeatureContext {
    @NotNull Content content();

    record Init(
            @NotNull Content content
    ) implements FeatureContext {
    }

    record Pack(
            @NotNull Content content,
            @NotNull ResourcePack resourcePack
    ) implements FeatureContext {
    }
}
