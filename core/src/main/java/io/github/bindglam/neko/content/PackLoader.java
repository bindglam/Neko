package io.github.bindglam.neko.content;

import io.github.bindglam.neko.config.ConfigSchema;
import io.github.bindglam.neko.manager.RegistryManager;
import io.github.bindglam.neko.registry.Registries;
import io.github.bindglam.neko.registry.RegistriesImpl;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Files;
import io.github.bindglam.neko.utils.Plugins;
import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public final class PackLoader {
    private static final ConfigSchema CONTENTS_PACK_CONFIG_SCHEMA = new ContentsPackConfigSchema();
    private static final ConfigSchema CONTENT_CONFIG_SCHEMA = new ContentConfigSchema();

    private PackLoader() {
    }

    public static @Nullable ContentsPack loadPack(@NotNull File folder) {
        Logger logger = Plugins.logger();

        File settingsFile = new File(folder, "pack.yml");
        if (!settingsFile.exists()) {
            logger.warning("Failed to load '" + folder.getName() + "' pack ( Missing pack.yml )");
            return null;
        }

        YamlConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);

        ConfigSchema.Result validationResult = CONTENTS_PACK_CONFIG_SCHEMA.validate(settingsConfig);
        if(validationResult.isSuccess()) {
            String id = Objects.requireNonNull(settingsConfig.getString("id"));
            String version = Objects.requireNonNull(settingsConfig.getString("version"));
            String author = Objects.requireNonNull(settingsConfig.getString("author"));

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
    }

    private static void loadConfigs(@NotNull File folder, @NotNull Registries registries) {
        Logger logger = Plugins.logger();

        Files.listFilesRecursively(folder)
                .map(YamlConfiguration::loadConfiguration)
                .forEach(config -> {
                    for (String key : config.getKeys(false)) {
                        ConfigurationSection contentConfig = config.getConfigurationSection(key);
                        if (contentConfig == null)
                            continue;

                        ConfigSchema.Result validationResult = CONTENT_CONFIG_SCHEMA.validate(contentConfig);
                        if(validationResult.isSuccess()) {
                            ContentType<?> type = RegistryManager.GlobalRegistries.registries().types()
                                    .get(Key.key(Constants.PLUGIN_ID, contentConfig.getString("type")))
                                    .orElseThrow(() -> new IllegalStateException("그 사이에 인젝션은 말이 안됨!!!!"));

                            if (!type.load(registries, contentConfig)) {
                                logger.warning("Failed to load " + contentConfig.getName());
                            }
                        } else {
                            logger.warning("Failed to load '" + key + "'");
                            validationResult.getErrors().forEach(error -> logger.warning("  " + error));
                        }
                    }
                });
    }
}
