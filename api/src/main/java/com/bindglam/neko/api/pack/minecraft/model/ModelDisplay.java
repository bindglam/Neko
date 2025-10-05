package com.bindglam.neko.api.pack.minecraft.model;

import com.bindglam.neko.api.pack.minecraft.Float3;
import com.google.gson.annotations.SerializedName;

public record ModelDisplay(
    Float3 rotation,
    Float3 translation,
    Float3 scale
) {
    public enum Type {
        @SerializedName("thirdperson_righthand")
        THIRDPERSON_RIGHT_HAND,

        @SerializedName("thirdperson_lefthand")
        THIRDPERSON_LEFT_HAND,

        @SerializedName("firstperson_righthand")
        FIRSTPERSON_RIGHT_HAND,

        @SerializedName("firstperson_lefthand")
        FIRSTPERSON_LEFT_HAND,

        @SerializedName("ground")
        GROUND,

        @SerializedName("gui")
        GUI,

        @SerializedName("head")
        HEAD,

        @SerializedName("fixed")
        FIXED,
    }
}
