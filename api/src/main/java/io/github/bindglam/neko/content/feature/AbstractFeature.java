package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractFeature<T extends Content<T>> implements Feature<T> {
    protected final Key key;
    protected final T content;

    protected AbstractFeature(@NotNull Key key, @NotNull T content) {
        this.key = key;
        this.content = content;
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public @NotNull T content() {
        return content;
    }
}
