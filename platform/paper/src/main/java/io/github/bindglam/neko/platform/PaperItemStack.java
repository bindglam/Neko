package io.github.bindglam.neko.platform;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public final class PaperItemStack implements PlatformItemStack {
    private final ItemStack itemStack;

    PaperItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public @NotNull PlatformItemType type() {
        return PlatformItemType.get(itemStack.getType().key()).orElseThrow();
    }

    @Override
    public void type(@NotNull PlatformItemType type) {
        itemStack.setType((Material) type.unwrap());
    }

    @Override
    public void name(@Nullable Component component) {
        itemStack.editMeta(meta -> meta.displayName(component));
    }

    @Override
    public void lore(@Nullable List<Component> components) {
        itemStack.editMeta(meta -> meta.lore(components));
    }

    @Override
    public void persistentDataContainer(Consumer<PlatformPersistentDataContainer> consumer) {
        if(this.itemStack.getItemMeta() != null)
            itemStack.editMeta(meta -> consumer.accept(new PaperPersistentDataContainer(meta.getPersistentDataContainer())));
    }

    @Override
    public @NotNull PlatformItemStack clone() {
        return new PaperItemStack(itemStack.clone());
    }

    @Override
    public @NotNull Object unwrap() {
        return itemStack;
    }

    @Override
    public @NotNull String translationKey() {
        return itemStack.translationKey();
    }
}
