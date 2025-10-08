package com.bindglam.neko.api.pack.minecraft.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public enum UVFace {
    @SerializedName("north")
    NORTH,

    @SerializedName("east")
    EAST,

    @SerializedName("south")
    SOUTH,

    @SerializedName("west")
    WEST,

    @SerializedName("up")
    UP,

    @SerializedName("down")
    DOWN,
}
