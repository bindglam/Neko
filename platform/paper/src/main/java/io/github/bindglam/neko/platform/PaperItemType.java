package io.github.bindglam.neko.platform;

import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class PaperItemType implements PlatformItemType {
    private final Material material;

    PaperItemType(Material material) {
        this.material = material;
    }

    @Override
    public @NotNull PlatformItemStack createItemStack() {
        return new PaperItemStack(new ItemStack(material));
    }

    @Override
    public @NotNull Object unwrap() {
        return material;
    }

    @Override
    public @NotNull Key key() {
        return material.key();
    }

    @Override
    public @NotNull String translationKey() {
        return material.translationKey();
    }
}
