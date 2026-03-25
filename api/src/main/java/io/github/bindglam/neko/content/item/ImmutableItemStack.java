package io.github.bindglam.neko.content.item;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class ImmutableItemStack {
    private final ItemStack itemStack;

    private ImmutableItemStack(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public @NotNull ItemStack get() {
        return itemStack.clone();
    }

    public static @NotNull ImmutableItemStack of(@NotNull ItemStack itemStack) {
        return new ImmutableItemStack(Objects.requireNonNull(itemStack, "ItemStack cannot be null"));
    }
}
