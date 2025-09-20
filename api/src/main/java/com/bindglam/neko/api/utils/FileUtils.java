package com.bindglam.neko.api.utils;

import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.jar.JarFile;

@ApiStatus.Internal
public final class FileUtils {
    private static final String JAR_URI_PREFIX = "jar:file:";

    private FileUtils() {
    }

    public static Optional<JarFile> jar(final Object object) throws IOException {
        final Class<?> clazz = object.getClass();
        final String path =
                String.format("/%s.class", clazz.getName().replace('.', '/'));
        final URL url = clazz.getResource(path);
        Optional<JarFile> optional = Optional.empty();
        if (url != null) {
            final String jar = url.toString();
            final int bang = jar.indexOf('!');
            if (jar.startsWith(JAR_URI_PREFIX) && bang != -1) {
                optional = Optional.of(
                        new JarFile(jar.substring(JAR_URI_PREFIX.length(), bang))
                );
            }
        }
        return optional;
    }
}
