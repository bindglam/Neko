package io.github.bindglam.neko.manager;

import io.github.bindglam.neko.NekoPaperPlugin;
import io.github.bindglam.neko.content.feature.event.ResourcePackGenerationEvent;
import io.github.bindglam.neko.event.AsyncResourcePackGenerationEvent;
import io.github.bindglam.neko.utils.Constants;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import team.unnamed.creative.ResourcePack;
import team.unnamed.creative.metadata.pack.PackFormat;
import team.unnamed.creative.metadata.pack.PackMeta;
import team.unnamed.creative.serialize.minecraft.MinecraftResourcePackWriter;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

public final class ResourcePackManagerImpl implements ResourcePackManager, Managerial<NekoPaperPlugin>, Reloadable<NekoPaperPlugin> {
    private static final Logger LOGGER = Logger.getLogger(ResourcePackManagerImpl.class.getName());
    private static final File GENERATED_PACK_FILE = new File(Constants.DATA_FOLDER, "generated.zip");

    @Override
    public void preload(@NotNull Context<NekoPaperPlugin> context) {
    }

    @Override
    public void start(@NotNull Context<NekoPaperPlugin> context) {
        generateResourcePack();
    }

    @Override
    public void end(@NotNull Context<NekoPaperPlugin> context) {
    }

    @Override
    public @NotNull CompletableFuture<Void> generateResourcePack() {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Generating resource pack...");

            ResourcePack resourcePack = ResourcePack.resourcePack();
            resourcePack.packMeta(PackMeta.of(
                    PackFormat.format(99, 1, Integer.MAX_VALUE),
                    Component.text("Created by Neko")
            ));

            RegistryManager.GlobalRegistries.registries().item().forEach(content ->
                    content.featureEventBus().call(new ResourcePackGenerationEvent(resourcePack)));

            Bukkit.getPluginManager().callEvent(new AsyncResourcePackGenerationEvent(resourcePack));

            MinecraftResourcePackWriter.minecraft().writeToZipFile(GENERATED_PACK_FILE, resourcePack);

            LOGGER.info("Successfully generated resource pack!");
        });
    }
}
