package io.github.bindglam.neko.utils;

import java.io.File;
import java.util.stream.Stream;

public final class Files {
    public static Stream<File> listFilesRecursively(File file) {
        try {
            return java.nio.file.Files.walk(file.toPath(), java.nio.file.FileVisitOption.FOLLOW_LINKS)
                    .filter(java.nio.file.Files::isRegularFile)
                    .map(java.nio.file.Path::toFile);
        } catch (java.io.IOException e) {
            return Stream.empty();
        }
    }

    private Files() {
    }
}
