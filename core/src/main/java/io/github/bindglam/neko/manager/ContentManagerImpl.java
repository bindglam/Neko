package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.Neko;
import io.github.bindglam.neko.content.ContentsPack;
import io.github.bindglam.neko.content.ContentsPackImpl;
import io.github.bindglam.neko.content.PackLoader;
import io.github.bindglam.neko.content.feature.builtin.HelloWorldFeature;
import io.github.bindglam.neko.content.item.Item;
import io.github.bindglam.neko.event.RegistryInitializeEvent;
import io.github.bindglam.neko.registry.Registry;
import io.github.bindglam.neko.utils.Constants;
import io.github.bindglam.neko.utils.Plugins;
import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public final class ContentManagerImpl implements ContentManager, Managerial, Reloadable, Listener {
    private static final Logger LOGGER = Logger.getLogger(ContentManagerImpl.class.getName());
    private static final File PACKS_FOLDER = new File(Constants.DATA_FOLDER, "packs");

    private ContentManagerImpl() {
    }

    public static final ContentManagerImpl INSTANCE = new ContentManagerImpl();

    @Override
    public void preload(@NotNull Context context) {
        Bukkit.getPluginManager().registerEvents(this, context.plugin());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegistryInitialize(RegistryInitializeEvent event) {
        RegistryManagerImpl.GlobalRegistriesImpl.features()
                .register(HelloWorldFeature.KEY, HelloWorldFeature.FACTORY);

        loadPacks(event.getRegistries());
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
                PackLoader.LoadResult result = PackLoader.loadPack(packFolder);
                if (result.isFailure()) {
                    LOGGER.warning("Failed to load " + packFolder.getName() + ". ( " + result.errorMsg() + " )");
                    continue;
                }

                String packId = result.id();
                ContentsPack pack = result.pack();
                if (packId != null && pack != null) {
                    registries.contentsPacks().register(ContentsPackImpl.createKey(packId), pack);
                    registries.mergeAll(pack.registries());
                    loadedPacksCnt++;
                }
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
        HandlerList.unregisterAll(this);
    }

    @Override
    public @NotNull Optional<Item> getNekoItemByStack(@NotNull ItemStack itemStack) {
        return Neko.plugin().registryManager().registries().item().entries().stream()
                .map(Pair::value)
                .filter(item -> item.isSimilar(itemStack))
                .findAny();
    }
}
