package com.bindglam.neko.api.pack;

import java.util.function.Supplier;

public record PackFile(
        Supplier<byte[]> bytes,
        long size
) {
}
