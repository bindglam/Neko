package io.github.bindglam.neko.content;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.registry.RegistriesImpl;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Files;
import io.github.bindglam.neko.utils.Platforms;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class PackLoader {
    private static final ConfigSchema CONTENTS_PACK_CONFIG_SCHEMA = new ContentsPackConfigSchema();
    private static final ConfigSchema CONTENT_CONFIG_SCHEMA = new ContentConfigSchema();

    private PackLoader() {
    }

    public static @Nullable ContentsPack loadPack(@NotNull File folder) {
        Logger logger = Platforms.logger();

        File settingsFile = new File(folder, "pack.yml");
        if (!settingsFile.exists()) {
            logger.warning("Failed to load '" + folder.getName() + "' pack ( Missing pack.yml )");
            return null;
        }

        try {
            var settingsConfig = YamlConfigurationLoader.builder().path(settingsFile.toPath()).build().load();

            ConfigSchema.Result validationResult = CONTENTS_PACK_CONFIG_SCHEMA.validate(settingsConfig);
            if (validationResult.isSuccess()) {
                String id = Objects.requireNonNull(settingsConfig.node("id").getString());
                String version = Objects.requireNonNull(settingsConfig.node("version").getString());
                String author = Objects.requireNonNull(settingsConfig.node("author").getString());

                Registries registries = new RegistriesImpl();

                File configsFolder = new File(folder, "configs");
                if (configsFolder.exists()) {
                    loadConfigs(configsFolder, registries);
                }

                return new ContentsPackImpl(id, version, author, folder, registries);
            } else {
                logger.warning("Failed to load '" + folder.getName() + "' pack");
                validationResult.getErrors().forEach(error -> logger.warning("  " + error));
                return null;
            }
        } catch (ConfigurateException e) {
            logger.warning("Failed to load '" + folder.getName() + "' pack");
            return null;
        }
    }

    private static void loadConfigs(@NotNull File folder, @NotNull Registries registries) {
        Logger logger = Platforms.logger();

        Files.listFilesRecursively(folder)
                .map(file -> {
                    try {
                        return YamlConfigurationLoader.builder().path(file.toPath()).build().load();
                    } catch (ConfigurateException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(config -> {
                    config.childrenMap().forEach((key, contentConfig) -> {
                        ConfigSchema.Result validationResult = CONTENT_CONFIG_SCHEMA.validate(contentConfig);
                        if(validationResult.isSuccess()) {
                            ContentType<?> type = RegistryManager.GlobalRegistries.registries().types()
                                    .get(Key.key(Constants.PLUGIN_ID, Objects.requireNonNull(contentConfig.node("type").getString())))
                                    .orElseThrow(() -> new IllegalStateException("그 사이에 인젝션은 말이 안됨!!!!"));

                            if (!type.load(registries, contentConfig)) {
                                logger.warning("Failed to load " + contentConfig.key());
                            }
                        } else {
                            logger.warning("Failed to load '" + key + "'");
                            validationResult.getErrors().forEach(error -> logger.warning("  " + error));
                        }
                    });
                });
    }
}
