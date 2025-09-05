package com.bindglam.neko.api.pack;

import org.jetbrains.annotations.NotNull;

public interface Packable {
    void pack(@NotNull PackZipper zipper);
}
