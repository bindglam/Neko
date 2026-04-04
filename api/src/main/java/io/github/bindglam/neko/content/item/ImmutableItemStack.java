package io.github.bindglam.neko.content.item;

import io.github.bindglam.neko.platform.PlatformItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ImmutableItemStack {
    private final PlatformItemStack itemStack;

    private ImmutableItemStack(@NotNull PlatformItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public @NotNull PlatformItemStack get() {
        return itemStack.clone();
    }

    public static @NotNull ImmutableItemStack of(@NotNull PlatformItemStack itemStack) {
        return new ImmutableItemStack(Objects.requireNonNull(itemStack, "ItemStack cannot be null"));
    }
}
