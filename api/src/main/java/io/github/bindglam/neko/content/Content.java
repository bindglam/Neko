package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.content.feature.FeatureEventBus;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;

public interface Content extends Keyed {
    @Unmodifiable
    @NotNull Collection<Feature> features();

    @NotNull FeatureEventBus featureEventBus();
}
