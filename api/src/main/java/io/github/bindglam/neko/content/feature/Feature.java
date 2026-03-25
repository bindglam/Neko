package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;

public interface Feature<T extends Content<T>> extends Keyed {
    @NotNull T content();
}
