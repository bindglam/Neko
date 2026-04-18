package io.github.bindglam.neko.content;

import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.utils.Constants;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Getter @Accessors(fluent = true)
public final class ContentsPackImpl implements ContentsPack {
    private final String id;
    private final String version;
    private final String author;
    private final File packFolder;
    private final Registries registries;
    private final Key key;

    public ContentsPackImpl(@NotNull @KeyPattern.Value String id,
                            @NotNull String version,
                            @NotNull String author,
                            @NotNull File packFolder,
                            @NotNull Registries registries) {
        this.id = id;
        this.version = version;
        this.author = author;
        this.packFolder = packFolder;
        this.registries = registries;
        this.key = createKey(id);
    }

    public static Key createKey(@NotNull @KeyPattern.Value String id) {
        return Key.key(Constants.MOD_ID, id);
    }
}
