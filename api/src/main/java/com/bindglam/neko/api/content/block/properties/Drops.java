package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.item.ItemStackReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public record Drops(@NotNull @Unmodifiable List<DropData> data) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<Drops> {
        private List<DropData> data = new ArrayList<>();

        private Builder() {
        }


        public Builder data(List<DropData> data) {
            this.data = data;
            return this;
        }

        public Builder data(DropData... data) {
            this.data = Arrays.stream(data).toList();
            return this;
        }


        @Override
        public @NotNull Drops build() {
            return new Drops(data);
        }
    }

    public sealed interface DropData {
        float chance();


        static @NotNull DropData of(@NotNull ItemStackReference item, int amount, float chance) {
            return new Item(item, amount, chance);
        }

        static @NotNull DropData of(@NotNull ItemStackReference item, float chance) {
            return of(item, 1, chance);
        }

        static @NotNull DropData of(int exp, float chance) {
            return new Experience(exp, chance);
        }

        record Item(
                @NotNull ItemStackReference item,
                int amount,
                float chance
        ) implements DropData {
        }

        record Experience(
                int experience,
                float chance
        ) implements DropData {
        }
    }
}
