package io.github.bindglam.neko.content.feature.builtin;

import io.github.bindglam.neko.content.feature.Feature;
import io.github.bindglam.neko.content.feature.FeatureContext;
import io.github.bindglam.neko.content.feature.FeatureFactory;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Plugins;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public final class HelloWorldFeature implements Feature {
    public static final Key KEY = Key.key(Constants.PLUGIN_ID, "hello_world");
    public static final Factory FACTORY = new Factory();

    private HelloWorldFeature() {
    }

    @Override
    public void init(@NotNull FeatureContext.Init context) {
        String itemKey = context.content().key().asString();
        String msg = context.arguments().getOrDefault("msg", "null");
        Plugins.logger().info("Hello " + itemKey + "!");
        Plugins.logger().info("Argument 'msg' : " + msg);
    }

    @Override
    public @NotNull Key key() {
        return KEY;
    }

    public static final class Factory implements FeatureFactory<HelloWorldFeature> {
        private Factory() {
        }

        @Override
        public @NotNull HelloWorldFeature create() {
            return new HelloWorldFeature();
        }
    }
}
