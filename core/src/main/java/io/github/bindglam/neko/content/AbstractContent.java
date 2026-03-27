package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.content.feature.FeatureBuilder;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractContent implements Content {
    protected final Key key;
    protected final List<Feature> features;

    protected AbstractContent(@NotNull Key key, @NotNull List<FeatureBuilder> features) {
        this.key = key;
        this.features = Collections.unmodifiableList(
                features.stream()
                        .map(builder -> builder.build(this))
                        .toList()
        );
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public @NotNull @Unmodifiable List<Feature> features() {
        return features;
    }
}
