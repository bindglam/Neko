package io.github.bindglam.neko.utils;

import io.github.bindglam.neko.Neko;

import java.util.logging.Logger;

public final class Platforms {
    public static Logger logger() {
        return Neko.platform().logger();
    }

    private Platforms() {
    }
}
