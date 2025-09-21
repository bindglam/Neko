package com.bindglam.neko.api.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;

public interface Cache {
    File CACHE_FOLDER = new File("plugins/Neko/cache");

    void save();

    void set(@NotNull String key, @Nullable Object value);

    @Nullable Object get(@NotNull String key);

    @Nullable Boolean getBoolean(@NotNull String key);
    default @NotNull Boolean getBoolean(@NotNull String key, @NotNull Boolean defaultValue) {
        Boolean value = getBoolean(key);
        if(value == null)
            return defaultValue;
        return value;
    }

    @Nullable String getString(@NotNull String key);
    default @NotNull String getString(@NotNull String key, @NotNull String defaultValue) {
        String value = getString(key);
        if(value == null)
            return defaultValue;
        return value;
    }


    @Nullable Character getCharacter(@NotNull String key);
    default @NotNull Character getCharacter(@NotNull String key, @NotNull Character defaultValue) {
        Character value = getCharacter(key);
        if(value == null)
            return defaultValue;
        return value;
    }


    @Nullable Number getNumber(@NotNull String key);
    default @NotNull Number getNumber(@NotNull String key, @NotNull Number defaultValue) {
        Number value = getNumber(key);
        if(value == null)
            return defaultValue;
        return value;
    }

    default @Nullable Integer getInteger(@NotNull String key) {
        Number number = getNumber(key);
        if(number == null)
            return null;
        return number.intValue();
    }
    default @NotNull Integer getInteger(@NotNull String key, @NotNull Integer defaultValue) {
        Integer value = getInteger(key);
        if(value == null)
            return defaultValue;
        return value;
    }

    default @Nullable Byte getByte(@NotNull String key) {
        Number number = getNumber(key);
        if(number == null)
            return null;
        return number.byteValue();
    }
    default @NotNull Byte getByte(@NotNull String key, @NotNull Byte defaultValue) {
        Byte value = getByte(key);
        if(value == null)
            return defaultValue;
        return value;
    }
}
