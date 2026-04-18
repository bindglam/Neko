package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.content.item.Item;
import io.github.bindglam.neko.platform.PlatformItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface ContentManager {
    @NotNull Optional<Item> getNekoItemByStack(@NotNull PlatformItemStack itemStack);
}
