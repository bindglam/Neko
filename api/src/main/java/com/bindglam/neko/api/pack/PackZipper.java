package com.bindglam.neko.api.pack;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public interface PackZipper extends AutoCloseable {
    void addFile(String path, PackFile file);

    default void addFile(File file) {
        try {
            addFile(file.getName(), new PackFile(() -> {
                try(FileInputStream stream = new FileInputStream(file)) {
                    return stream.readAllBytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, Files.size(file.toPath())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void addDirectory(File directory);

    @Nullable PackFile file(String path);

    void build();
}
