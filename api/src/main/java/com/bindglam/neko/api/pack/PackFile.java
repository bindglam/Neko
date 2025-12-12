package com.bindglam.neko.api.pack;

import java.util.function.Supplier;

public record PackFile(Supplier<byte[]> data, long size) {
    public PackFile(byte[] cachedData) {
        this(() -> cachedData, cachedData.length);
    }

    public byte[] bytes() {
        return data.get();
    }
}
