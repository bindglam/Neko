package com.bindglam.neko.api;

import com.bindglam.neko.api.manager.CacheManager;
import com.bindglam.neko.api.manager.ContentManager;
import com.bindglam.neko.api.manager.PackManager;
import org.jetbrains.annotations.NotNull;

public interface Neko {
    ReloadInfo reload();

    @NotNull CacheManager cacheManager();

    @NotNull ContentManager contentManager();

    @NotNull PackManager packManager();

    enum ReloadInfo {
        SUCCESS,
        FAIL
    }
}
