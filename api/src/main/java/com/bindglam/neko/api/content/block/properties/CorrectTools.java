package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.item.ItemStackReference;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public record CorrectTools(@NotNull @Unmodifiable List<Tag<Material>> tags, @NotNull @Unmodifiable Multimap<ListType, ItemStackReference> items) {

    public boolean isCorrectTool(@Nullable ItemStack itemStack) {
        if(itemStack == null) return false;

        if (tags().stream().anyMatch((tag) -> tag.isTagged(itemStack.getType())) && items().get(ListType.BLACKLIST).stream().noneMatch((item) -> item.isSame(itemStack)))
            return true;

        return items().get(ListType.WHITELIST).stream().filter(Objects::nonNull).anyMatch((item) -> item.isSame(itemStack));
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<CorrectTools> {
        private List<Tag<Material>> tags = new ArrayList<>();
        private final Multimap<ListType, ItemStackReference> items = ArrayListMultimap.create();

        private Builder() {
        }


        public Builder tags(List<Tag<Material>> tags) {
            this.tags = tags;
            return this;
        }

        @SafeVarargs
        public final Builder tags(Tag<Material>... tags) {
            this.tags = Arrays.stream(tags).toList();
            return this;
        }

        public Builder whitelist(List<ItemStackReference> items) {
            this.items.putAll(ListType.WHITELIST, items);
            return this;
        }

        public Builder whitelist(ItemStackReference... items) {
            this.items.putAll(ListType.WHITELIST, Arrays.stream(items).toList());
            return this;
        }

        public Builder blacklist(List<ItemStackReference> items) {
            this.items.putAll(ListType.BLACKLIST, items);
            return this;
        }

        public Builder blacklist(ItemStackReference... items) {
            this.items.putAll(ListType.BLACKLIST, Arrays.stream(items).toList());
            return this;
        }


        @Override
        public @NotNull CorrectTools build() {
            return new CorrectTools(tags, items);
        }
    }

    enum ListType {
        WHITELIST,
        BLACKLIST
    }
}
