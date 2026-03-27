package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractContent implements Content {
    @Getter @Accessors(fluent = true)
    protected final Key key;
    @Getter @Accessors(fluent = true)
    protected final List<Feature> features;
    @Getter @Accessors(fluent = true)
    protected final FeatureEventBus featureEventBus = new FeatureEventBusImpl();

    protected AbstractContent(@NotNull Key key, @NotNull List<FeatureBuilder> features) {
        this.key = key;
        this.features = features.stream()
                .map(builder -> builder.build(this, featureEventBus))
                .toList();
    }
}
