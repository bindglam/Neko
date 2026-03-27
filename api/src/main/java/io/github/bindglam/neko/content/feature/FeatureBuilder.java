package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import org.jetbrains.annotations.NotNull;

public record FeatureBuilder(
        @NotNull FeatureFactory<?> factory,
        @NotNull FeatureArguments arguments
) {
    public @NotNull Feature build(@NotNull Content content, @NotNull FeatureEventBus eventBus) {
        return factory.create(new FeatureFactory.Context(content, arguments, eventBus));
    }
}
