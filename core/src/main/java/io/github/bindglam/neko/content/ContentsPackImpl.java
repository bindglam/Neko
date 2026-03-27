package io.github.bindglam.neko.content;

import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class ContentsPackImpl implements ContentsPack {
    private final String id;
    private final String version;
    private final String author;
    private final File packFolder;
    private final Registries registries;
    private final Key key;

    public ContentsPackImpl(@NotNull String id,
                            @NotNull String version,
                            @NotNull String author,
                            @NotNull File packFolder,
                            @NotNull Registries registries) {
        this.id = id;
        this.version = version;
        this.author = author;
        this.packFolder = packFolder;
        this.registries = registries;
        this.key = Key.key(Constants.PLUGIN_ID, id);
    }

    public static Key createKey(@NotNull String id) {
        return Key.key(Constants.PLUGIN_ID, id);
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public @NotNull String id() {
        return id;
    }

    @Override
    public @NotNull String version() {
        return version;
    }

    @Override
    public @NotNull String author() {
        return author;
    }

    @Override
    public @NotNull File packFolder() {
        return packFolder;
    }

    @Override
    public @NotNull Registries registries() {
        return registries;
    }
}
