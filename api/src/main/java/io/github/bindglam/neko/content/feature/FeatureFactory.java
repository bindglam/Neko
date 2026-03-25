package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

public interface FeatureFactory<CONTENT extends Content<CONTENT>, T extends Feature<CONTENT>> {
    @NotNull Class<CONTENT> contentClass();

    @NotNull T create(@NotNull CONTENT content);
}
