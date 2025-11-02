package com.bindglam.neko.api.pack;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public record PackFile(byte @Nullable [] cachedData, @Nullable Supplier<byte[]> data, long size) {
    public PackFile(byte[] cachedData, long size) {
        this(cachedData, null, size);
    }

    public PackFile(Supplier<byte[]> data, long size) {
        this(null, data, size);
    }

    public byte[] bytes() {
        if(cachedData == null)
            return Objects.requireNonNull(data).get();
        return cachedData;
    }
}
