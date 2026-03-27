package io.github.bindglam.neko.content;

import io.github.bindglam.neko.content.feature.FeatureFactory;
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
import java.util.Map;
import java.util.logging.Logger;

public final class PackLoader {
    private PackLoader() {
    }

    public static LoadResult loadPack(@NotNull File folder) {
        File settingsFile = new File(folder, "pack.yml");
        if (!settingsFile.exists()) {
            return LoadResult.failure("Missing pack.yml");
        }
        YamlConfiguration settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);

        String id = settingsConfig.getString("id");
        if (id == null) {
            return LoadResult.failure("Missing id in pack.yml");
        }
        String version = settingsConfig.getString("version");
        if (version == null) {
            return LoadResult.failure("Missing version in pack.yml");
        }
        String author = settingsConfig.getString("author");
        if (author == null) {
            return LoadResult.failure("Missing author in pack.yml");
        }

        Registries registries = new RegistriesImpl();

        File configsFolder = new File(folder, "configs");
        if (configsFolder.exists()) {
            loadConfigs(configsFolder, registries);
        }

        ContentsPack pack = new ContentsPackImpl(id, version, author, folder, registries);
        return LoadResult.success(id, pack);
    }

    private static void loadConfigs(@NotNull File folder, @NotNull Registries registries) {
        Logger logger = Plugins.logger();
        Files.listFilesRecursively(folder)
                .map(YamlConfiguration::loadConfiguration)
                .forEach(config -> {
                    for (String key : config.getKeys(false)) {
                        ConfigurationSection contentConfig = config.getConfigurationSection(key);
                        if (contentConfig == null) {
                            continue;
                        }

                        String typeId = contentConfig.getString("type");
                        if (typeId == null) {
                            logger.warning("Failed to load " + contentConfig.getName() + ". ( Missing type )");
                            continue;
                        }

                        ContentType<?> type = RegistryManager.GlobalRegistries.registries().types()
                                .get(Key.key(Constants.PLUGIN_ID, typeId))
                                .orElse(null);

                        if (type == null) {
                            logger.warning("Failed to load " + contentConfig.getName() + ". ( Unknown type )");
                            continue;
                        }

                        ContentType.LoadResult result = type.load(registries, contentConfig);
                        if (result.isFailure()) {
                            logger.warning("Failed to load " + contentConfig.getName() + ". ( " + result.errorMessage() + " )");
                        }
                    }
                });
    }

    public static final class LoadResult {
        private final String id;
        private final ContentsPack pack;
        private final String errorMsg;

        private LoadResult(@Nullable String id, @Nullable ContentsPack pack, @Nullable String errorMsg) {
            this.id = id;
            this.pack = pack;
            this.errorMsg = errorMsg;
        }

        public static LoadResult success(@NotNull String id, @NotNull ContentsPack pack) {
            return new LoadResult(id, pack, null);
        }

        public static LoadResult failure(@NotNull String errorMsg) {
            return new LoadResult(null, null, errorMsg);
        }

        public boolean isSuccess() {
            return id != null && pack != null;
        }

        public boolean isFailure() {
            return errorMsg != null;
        }

        @Nullable
        public String id() {
            return id;
        }

        @Nullable
        public ContentsPack pack() {
            return pack;
        }

        @Nullable
        public String errorMsg() {
            return errorMsg;
        }
    }
}
