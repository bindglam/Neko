package com.bindglam.neko.api.content.block.properties;

import com.bindglam.neko.api.content.item.ItemStackHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public sealed interface Drops {
    @NotNull List<DropData> data();


    static Builder builder() {
        return new Builder();
    }

    final class Builder implements Drops {
        private List<DropData> data = new ArrayList<>();

        public Builder data(List<DropData> data) {
            this.data = data;
            return this;
        }

        public Builder data(DropData... data) {
            this.data = Arrays.stream(data).toList();
            return this;
        }

        @Override
        public @NotNull List<DropData> data() {
            return data;
        }
    }

    sealed interface DropData {
        float chance();


        static @NotNull DropData of(@NotNull ItemStackHolder item, int amount, float chance) {
            return new Item(item, amount, chance);
        }

        static @NotNull DropData of(@NotNull ItemStackHolder item, float chance) {
            return of(item, 1, chance);
        }

        static @NotNull DropData of(int exp, float chance) {
            return new Experience(exp, chance);
        }

        record Item(
                @NotNull ItemStackHolder item,
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
