package io.github.bindglam.neko.platform;

import net.kyori.adventure.text.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public final class FabricItemStack implements PlatformItemStack {
    private final ItemStack itemStack;

    public FabricItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public @NotNull PlatformItemType type() {
    }

    @Override
    public void type(@NotNull PlatformItemType type) {
    }

    @Override
    public void name(@Nullable Component component) {

    }

    @Override
    public void lore(@Nullable List<Component> components) {

    }

    @Override
    public void persistentDataContainer(Consumer<PlatformPersistentDataContainer> consumer) {

    }

    @Override
    public @NotNull PlatformItemStack clone() {
        return new FabricItemStack(itemStack.copy());
    }

    @Override
    public @NotNull Object unwrap() {
        return itemStack;
    }

    @Override
    public @NotNull String translationKey() {
        return Items.BAMBOO_DOOR.getDescriptionId()
    }
}
