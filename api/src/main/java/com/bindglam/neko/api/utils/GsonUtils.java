package com.bindglam.neko.api.utils;

import com.bindglam.neko.api.pack.minecraft.Float2;
import com.bindglam.neko.api.pack.minecraft.Float3;
import com.bindglam.neko.api.pack.minecraft.Float4;
import com.bindglam.neko.api.pack.minecraft.sound.Sounds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class GsonUtils {
    public static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Float2.class, Float2.SERIALIZER).registerTypeAdapter(Float2.class, Float2.DESERIALIZER)
            .registerTypeAdapter(Float3.class, Float3.SERIALIZER).registerTypeAdapter(Float3.class, Float3.DESERIALIZER)
            .registerTypeAdapter(Float4.class, Float4.SERIALIZER).registerTypeAdapter(Float4.class, Float4.DESERIALIZER)
            .registerTypeAdapter(Sounds.class, Sounds.SERIALIZER).registerTypeAdapter(Sounds.class, Sounds.DESERIALIZER)
            .create();

    private GsonUtils() {
    }
}
