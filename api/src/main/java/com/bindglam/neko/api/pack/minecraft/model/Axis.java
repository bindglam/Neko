package com.bindglam.neko.api.pack.minecraft.model;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public enum Axis {
    @SerializedName("x")
    X,

    @SerializedName("y")
    Y,

    @SerializedName("z")
    Z
}
