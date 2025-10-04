package com.bindglam.neko.api.pack.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public record Float4(
        float x,
        float y,
        float z,
        float w
) {
    public static final JsonSerializer<Float4> SERIALIZER = (src, typeOfT, context) -> {
        JsonArray array = new JsonArray();

        array.add(src.x);
        array.add(src.y);
        array.add(src.z);
        array.add(src.w);

        return array;
    };

    public static final JsonDeserializer<Float4> DESERIALIZER = (json, typeOfT, context) -> {
        var array = json.getAsJsonArray();
        return new Float4(
                array.get(0).getAsFloat(),
                array.get(1).getAsFloat(),
                array.get(2).getAsFloat(),
                array.get(3).getAsFloat()
        );
    };
}
