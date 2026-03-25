package io.github.bindglam.neko.content.item.properties;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record ItemProperties(@NotNull Material type, @Nullable Component name, @Nullable @Unmodifiable List<Component> lore) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Material type = Material.NETHER_BRICK;
        private Component name;
        private List<Component> lore;

        private Builder() {
        }

        public Builder type(Material type) {
            this.type = type;
            return this;
        }

        public Builder name(Component name) {
            this.name = name;
            return this;
        }

        public Builder lore(List<Component> lore) {
            this.lore = new ArrayList<>(lore);
            return this;
        }

        public Builder lore(Component... lore) {
            this.lore = Arrays.stream(lore).toList();
            return this;
        }

        public @NotNull ItemProperties build() {
            return new ItemProperties(type, name, lore);
        }
    }
}
