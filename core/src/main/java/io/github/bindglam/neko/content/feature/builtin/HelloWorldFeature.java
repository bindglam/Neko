package io.github.bindglam.neko.content.feature.builtin;

import io.github.bindglam.neko.content.feature.*;
import io.github.bindglam.neko.content.feature.event.ItemStackGenerationEvent;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Plugins;
import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class HelloWorldFeature implements Feature {
    public static final Key KEY = Key.key(Constants.PLUGIN_ID, "hello_world");

    private HelloWorldFeature(@NotNull FeatureFactory.Context context) {
        Plugins.logger().info("Hello " + context.content().key().asString() + "!");
        Plugins.logger().info("Argument 'msg' : " + context.arguments().getOrDefault("msg", "null"));

        context.eventBus().subscribe(ItemStackGenerationEvent.class, event -> {
            event.itemStack().setType(Material.BROWN_DYE);
        });
    }

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    public static final class Factory implements FeatureFactory<HelloWorldFeature> {
        @Override
        public @NotNull HelloWorldFeature create(@NotNull Context context) {
            return new HelloWorldFeature(context);
        }
    }
}
