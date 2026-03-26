package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import org.jetbrains.annotations.NotNull;

public record FeatureBuilder(
        @NotNull FeatureFactory<?> factory,
        @NotNull FeatureArguments arguments
) {
    public @NotNull Feature build(@NotNull Content content) {
        Feature feature = factory.create();

        feature.init(new FeatureContext.Init(content, arguments));

        return feature;
    }
}
