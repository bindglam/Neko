package io.github.bindglam.neko.content.feature.event;

import io.github.bindglam.neko.content.Content;
import io.github.bindglam.neko.content.feature.FeatureArguments;
import org.jetbrains.annotations.NotNull;

public record InitializationEvent(
        @NotNull Content content,
        @NotNull FeatureArguments arguments
) implements FeatureEvent {
}
