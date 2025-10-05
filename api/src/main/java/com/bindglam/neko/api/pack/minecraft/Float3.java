package com.bindglam.neko.api.pack.minecraft;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public record Float3(
        float x,
        float y,
        float z
) {
    public static final JsonSerializer<Float3> SERIALIZER = (src, typeOfT, context) -> {
        JsonArray array = new JsonArray();

        array.add(src.x);
        array.add(src.y);
        array.add(src.z);

        return array;
    };

    public static final JsonDeserializer<Float3> DESERIALIZER = (json, typeOfT, context) -> {
        var array = json.getAsJsonArray();
        return new Float3(
                array.get(0).getAsFloat(),
                array.get(1).getAsFloat(),
                array.get(2).getAsFloat()
        );
    };
}
