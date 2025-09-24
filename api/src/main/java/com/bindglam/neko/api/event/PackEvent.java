package com.bindglam.neko.api.event;

import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.FileUtils;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackEvent extends Event {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final PackZipper zipper;

    @ApiStatus.Internal
    public PackEvent(PackZipper zipper) {
        this.zipper = zipper;
    }

    public void addPluginPack(JavaPlugin plugin) {
        try(JarFile jarFile = FileUtils.jar(plugin).orElseThrow()) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while(entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if(!entry.getName().startsWith("assets/") || entry.isDirectory()) continue;

                byte[] data = jarFile.getInputStream(entry).readAllBytes();
                zipper.addFile(entry.getName(), new PackFile(() -> data, entry.getSize()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
