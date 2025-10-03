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

    record DropData(
            @Nullable ItemStackHolder item,
            int experience,
            float chance
    ) {
        public static @NotNull DropData of(@NotNull ItemStackHolder item, float chance) {
            return new DropData(item, 0, chance);
        }

        public static @NotNull DropData of(int exp, float chance) {
            return new DropData(null, exp, chance);
        }
    }
}
