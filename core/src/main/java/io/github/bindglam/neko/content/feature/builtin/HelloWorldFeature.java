package io.github.bindglam.neko.content.feature.builtin;

import io.github.bindglam.neko.content.feature.*;
import io.github.bindglam.neko.content.feature.event.ItemStackGenerationEvent;
import io.github.bindglam.neko.platform.PlatformItemType;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Platforms;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public final class HelloWorldFeature extends Feature {
    public static final Key KEY = Key.key(Constants.MOD_ID, "hello_world");

    private HelloWorldFeature(@NotNull FeatureFactory.Context context) {
        super(context);
        Platforms.logger().info("Hello " + context.content().key().asString() + "!");
        Platforms.logger().info("Argument 'msg' : " + context.arguments().getOrDefault("msg", "null"));

        context.eventBus().subscribe(ItemStackGenerationEvent.class, event -> {
            event.itemStack().type(PlatformItemType.get(Key.key(Key.MINECRAFT_NAMESPACE, "brown_dye")).orElseThrow());
        });
    }

    public static final class Factory implements FeatureFactory<HelloWorldFeature> {
        @Override
        public @NotNull HelloWorldFeature create(@NotNull Context context) {
            return new HelloWorldFeature(context);
        }
    }
}
