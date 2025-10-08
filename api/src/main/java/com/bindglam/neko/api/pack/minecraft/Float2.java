package com.bindglam.neko.api.pack.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record Float2(
        float x,
        float y
) {
    public static final JsonSerializer<Float2> SERIALIZER = (src, typeOfT, context) -> {
        JsonArray array = new JsonArray();

        array.add(src.x);
        array.add(src.y);

        return array;
    };

    public static final JsonDeserializer<Float2> DESERIALIZER = (json, typeOfT, context) -> {
        var array = json.getAsJsonArray();
        return new Float2(
                array.get(0).getAsFloat(),
                array.get(1).getAsFloat()
        );
    };
}
