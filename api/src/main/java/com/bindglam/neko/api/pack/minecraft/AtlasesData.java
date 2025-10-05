package com.bindglam.neko.api.pack.minecraft;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public record AtlasesData(List<Source> sources) {
    public record Source(Type type, String resource) {
        public enum Type {
            @SerializedName("single")
            SINGLE
        }
    }
}
