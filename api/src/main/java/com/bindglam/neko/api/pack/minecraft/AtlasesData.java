package com.bindglam.neko.api.pack.minecraft;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public record AtlasesData(List<Source> sources) {
    public record Source(Type type, String resource) {
        public enum Type {
            @SerializedName("single")
            SINGLE
        }
    }
}
