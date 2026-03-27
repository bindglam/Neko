package io.github.bindglam.neko.content.feature;

import io.github.bindglam.neko.content.Content;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

public abstract class Feature {
    @Getter @Accessors(fluent = true)
    protected final Content content;

    protected Feature(@NotNull FeatureFactory.Context context) {
        this.content = context.content();
    }
}
