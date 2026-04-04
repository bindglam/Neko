package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.content.ContentsPack;
import io.github.bindglam.neko.content.ContentsPackImpl;
import io.github.bindglam.neko.content.PackLoader;
import io.github.bindglam.neko.content.feature.builtin.HelloWorldFeature;
import io.github.bindglam.neko.content.item.Item;
import io.github.bindglam.neko.event.RegistryInitializeEvent;
import io.github.bindglam.neko.platform.PlatformItemStack;
import io.github.bindglam.neko.utils.Constants;
import it.unimi.dsi.fastutil.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.logging.Logger;

public final class ContentManagerImpl implements ContentManager, Managerial, Reloadable {
    private static final Logger LOGGER = Logger.getLogger(ContentManagerImpl.class.getName());
    private static final File PACKS_FOLDER = new File(Constants.DATA_FOLDER, "packs");

    @Override
    public void preload(@NotNull Context context) {
        context.eventBus().subscribe(RegistryInitializeEvent.class, event -> {
            RegistryManager.GlobalRegistries.registries().features().register(HelloWorldFeature.KEY, new HelloWorldFeature.Factory());

            loadPacks(event.registries());
        });
    }

    private void loadPacks(@NotNull RegistryManager.GlobalRegistries registries) {
        LOGGER.info("Loading packs...");

        if (!PACKS_FOLDER.exists()) {
            PACKS_FOLDER.mkdirs();
        }

        File[] packFolders = PACKS_FOLDER.listFiles();
        if (packFolders != null) {
            int loadedPacksCnt = 0;
            for (File packFolder : packFolders) {
                ContentsPack pack = PackLoader.loadPack(packFolder);
                if (pack == null) {
                    LOGGER.warning("Failed to load " + packFolder.getName());
                    continue;
                }

                registries.contentsPacks().register(ContentsPackImpl.createKey(pack.id()), pack);
                registries.mergeAll(pack.registries());
                loadedPacksCnt++;
            }
            LOGGER.info("Loaded " + loadedPacksCnt + " packs!");
        }

        LOGGER.info("Successfully loaded packs!");
    }

    @Override
    public void start(@NotNull Context context) {
    }

    @Override
    public void end(@NotNull Context context) {
    }

    @Override
    public @NotNull Optional<Item> getNekoItemByStack(@NotNull PlatformItemStack itemStack) {
        return Neko.platform().registryManager().registries().item().entries().stream()
                .map(Pair::value)
                .filter(item -> item.isSimilar(itemStack))
                .findAny();
    }
}
