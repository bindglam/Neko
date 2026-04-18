package io.github.bindglam.neko.platform;

import net.kyori.adventure.key.Key;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public final class FabricPersistentDataContainer implements PlatformPersistentDataContainer {
    private final ItemStack itemStack;

    FabricPersistentDataContainer(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public @Nullable <P, C> C get(@NotNull Key key, @NotNull Type<P, C> type) {
        if(!(type instanceof TypeImpl<P, C> typeImpl))
            throw new IllegalStateException("Not TypeImpl!");
        var customData = itemStack.get(DataComponents.CUSTOM_DATA);
        if(customData == null)
            return null;
        var nbt = customData.copyTag();
        return nbt.get(key.asString());
    }

    @Override
    public <P, C> void set(@NotNull Key key, @NotNull Type<P, C> type, @NonNull C value) {

    }

    public final class TypeImpl<P, C> implements Type<P, C> {
        public @NotNull C

        @Override
        public @NotNull Object unwrap() {
            throw new UnsupportedOperationException();
        }
    }
}
