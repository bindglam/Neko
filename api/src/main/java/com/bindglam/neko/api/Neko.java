package com.bindglam.neko.api;

import com.bindglam.neko.api.manager.CacheManager;
import com.bindglam.neko.api.manager.ContentManager;
import com.bindglam.neko.api.manager.PackManager;
import com.bindglam.neko.api.manager.PlayerNetworkManager;
import com.bindglam.neko.api.nms.NMSHook;
import org.jetbrains.annotations.NotNull;

public interface Neko {
    ReloadInfo reload();

    @NotNull CacheManager cacheManager();

    @NotNull ContentManager contentManager();

    @NotNull PackManager packManager();

    @NotNull PlayerNetworkManager playerNetworkManager();

    @NotNull NMSHook nms();

    enum ReloadInfo {
        SUCCESS,
        FAIL
    }
}
