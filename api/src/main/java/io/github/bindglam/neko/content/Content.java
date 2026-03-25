package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.Feature;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;

public interface Content<SELF extends Content<SELF>> extends Keyed {
    @Unmodifiable
    @NotNull Collection<Feature<SELF>> features();
}
