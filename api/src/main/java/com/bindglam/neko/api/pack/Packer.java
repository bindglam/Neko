package com.bindglam.neko.api.pack;

import org.jetbrains.annotations.NotNull;

public interface Packer<T> {
    void pack(@NotNull PackZipper zipper, T object);
}
