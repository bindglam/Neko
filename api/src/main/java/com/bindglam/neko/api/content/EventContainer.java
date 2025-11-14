package com.bindglam.neko.api.content;

import org.jetbrains.annotations.NotNull;

public interface EventContainer {
    @NotNull EventHandler eventHandler();
}
