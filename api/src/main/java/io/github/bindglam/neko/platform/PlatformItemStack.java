package io.github.bindglam.neko.platform;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PlatformItemStack extends Translatable {
    @NotNull PlatformItemType type();

    void type(@NotNull PlatformItemType type);

    void name(@Nullable Component component);

    void lore(@Nullable List<Component> components);

    @Nullable PlatformPersistentDataContainer persistentDataContainer();

    @NotNull PlatformItemStack clone();

    @NotNull Object unwrap();
}
