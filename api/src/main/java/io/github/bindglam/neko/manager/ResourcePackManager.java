package io.github.bindglam.neko.manager;

import java.util.concurrent.CompletableFuture;

public interface ResourcePackManager {
    CompletableFuture<Void> generateResourcePack();
}
