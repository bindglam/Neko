package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.item.ItemStackHolder;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public sealed interface CorrectTools {
    @NotNull List<Tag<Material>> tags();

    @NotNull List<ItemStackHolder> items();

    default boolean isCorrectTool(@Nullable ItemStack itemStack) {
        if(itemStack == null) return false;

        if (tags().stream().anyMatch((tag) -> tag.isTagged(itemStack.getType())))
            return true;

        return items().stream().anyMatch((item) -> item.isSame(itemStack));
    }


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements CorrectTools {
        private List<Tag<Material>> tags = new ArrayList<>();
        private List<ItemStackHolder> items = new ArrayList<>();

        public Builder tags(List<Tag<Material>> tags) {
            this.tags = tags;
            return this;
        }

        @SafeVarargs
        public final Builder tags(Tag<Material>... tags) {
            this.tags = Arrays.stream(tags).toList();
            return this;
        }

        public Builder items(List<ItemStackHolder> items) {
            this.items = items;
            return this;
        }

        public Builder items(ItemStackHolder... items) {
            this.items = Arrays.stream(items).toList();
            return this;
        }

        @Override
        public @NotNull List<Tag<Material>> tags() {
            return tags;
        }

        @Override
        public @NotNull List<ItemStackHolder> items() {
            return items;
        }
    }
}
