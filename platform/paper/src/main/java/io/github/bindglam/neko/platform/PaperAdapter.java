package io.github.bindglam.neko.platform;

import net.kyori.adventure.key.Key;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Optional;

public final class PaperAdapter implements PlatformAdapter {
    @Override
    public @NotNull PlatformItemStack itemStack(@NotNull Object itemStack) {
        if(!(itemStack instanceof ItemStack))
            throw new IllegalStateException("Not ItemStack!");

        return new PaperItemStack((ItemStack) itemStack);
    }

    @Override
    public @NotNull Optional<PlatformItemType> itemType(@NotNull Key key) {
        Material material = Material.getMaterial(key.value().toUpperCase(Locale.ROOT));
        if(material == null)
            return Optional.empty();
        return Optional.of(new PaperItemType(material));
    }

    @Override
    public @NotNull <P, C> Optional<PlatformPersistentDataContainer.Type<P, C>> persistentDataType(@NotNull Class<P> clazz) {
        return Optional.empty();
    }
}
