package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.item.ItemStackHolder;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public sealed interface CorrectTools {
    @NotNull List<Tag<Material>> tags();

    @NotNull Multimap<ListType, ItemStackHolder> items();

    default boolean isCorrectTool(@Nullable ItemStack itemStack) {
        if(itemStack == null) return false;

        if (tags().stream().anyMatch((tag) -> tag.isTagged(itemStack.getType())) && items().get(ListType.BLACKLIST).stream().noneMatch((item) -> item.isSame(itemStack)))
            return true;

        return items().get(ListType.WHITELIST).stream().anyMatch((item) -> item.isSame(itemStack));
    }


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements CorrectTools {
        private List<Tag<Material>> tags = new ArrayList<>();
        private final Multimap<ListType, ItemStackHolder> items = ArrayListMultimap.create();

        public Builder tags(List<Tag<Material>> tags) {
            this.tags = tags;
            return this;
        }

        @SafeVarargs
        public final Builder tags(Tag<Material>... tags) {
            this.tags = Arrays.stream(tags).toList();
            return this;
        }

        public Builder whitelist(List<ItemStackHolder> items) {
            this.items.putAll(ListType.WHITELIST, items);
            return this;
        }

        public Builder whitelist(ItemStackHolder... items) {
            this.items.putAll(ListType.WHITELIST, Arrays.stream(items).toList());
            return this;
        }

        public Builder blacklist(List<ItemStackHolder> items) {
            this.items.putAll(ListType.BLACKLIST, items);
            return this;
        }

        public Builder blacklist(ItemStackHolder... items) {
            this.items.putAll(ListType.BLACKLIST, Arrays.stream(items).toList());
            return this;
        }

        @Override
        public @NotNull List<Tag<Material>> tags() {
            return tags;
        }

        @Override
        public @NotNull Multimap<ListType, ItemStackHolder> items() {
            return items;
        }
    }

    enum ListType {
        WHITELIST,
        BLACKLIST
    }
}
