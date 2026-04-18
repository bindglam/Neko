package io.github.bindglam.neko.platform;

import net.kyori.adventure.key.Key;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class FabricAdapter implements PlatformAdapter {
    @Override
    public @NotNull PlatformItemStack itemStack(@NotNull Object itemStack) {
        if(!(itemStack instanceof ItemStack))
            throw new IllegalStateException("Not ItemStack!");

        return new FabricItemStack((ItemStack) itemStack);
    }

    @Override
    public @NotNull Optional<PlatformItemType> itemType(@NotNull Key key) {
        return Optional.empty();
    }

    @Override
    public @NotNull <P, C> Optional<PlatformPersistentDataContainer.Type<P, C>> persistentDataType(@NotNull Class<P> clazz) {
        return Optional.empty();
    }
}
