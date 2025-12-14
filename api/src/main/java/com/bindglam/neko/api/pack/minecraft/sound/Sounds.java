package com.bindglam.neko.api.pack.minecraft.sound;

import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Unmodifiable;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public record Sounds(@Unmodifiable Map<String, SoundEvent> events) implements PackComponent {
    public static final JsonSerializer<Sounds> SERIALIZER = (src, typeOfT, context) -> {
        JsonObject obj = new JsonObject();

        src.events().forEach((id, event) -> {
            obj.add(id, GsonUtils.GSON.toJsonTree(event));
        });

        return obj;
    };

    public static final JsonDeserializer<Sounds> DESERIALIZER = (json, typeOfT, context) -> {
        JsonObject obj = json.getAsJsonObject();

        Map<String, SoundEvent> events = new HashMap<>();

        obj.entrySet().forEach((entry) -> {
            events.put(entry.getKey(), GsonUtils.GSON.fromJson(entry.getValue(), SoundEvent.class));
        });

        return new Sounds(Map.copyOf(events));
    };

    @Override
    public void apply(String path, PackZipper zipper) {
        zipper.addFile(path, new PackFile(() -> GsonUtils.GSON.toJson(this).getBytes(), -1));
    }
}
