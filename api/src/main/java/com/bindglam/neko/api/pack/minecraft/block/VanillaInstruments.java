package com.bindglam.neko.api.pack.minecraft.block;

import org.bukkit.Instrument;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public enum VanillaInstruments {
    BANJO(Instrument.BANJO),
    BASS(Instrument.BASS_GUITAR),
    BELL(Instrument.BELL),
    BIT(Instrument.BIT),
    CHIME(Instrument.CHIME),
    COW_BELL(Instrument.COW_BELL),
    CREEPER(Instrument.CREEPER),
    CUSTOM_HEAD(Instrument.CUSTOM_HEAD),
    DIDGERIDOO(Instrument.DIDGERIDOO),
    DRAGON(Instrument.DRAGON),
    FLUTE(Instrument.FLUTE),
    GUITAR(Instrument.GUITAR),
    HARP(Instrument.PIANO),
    HAT(Instrument.STICKS),
    IRON_XYLOPHONE(Instrument.IRON_XYLOPHONE),
    PIGLIN(Instrument.PIGLIN),
    PLING(Instrument.PLING),
    SKELETON(Instrument.SKELETON),
    SNARE(Instrument.SNARE_DRUM),
    WITHER_SKELETON(Instrument.WITHER_SKELETON),
    XYLOPHONE(Instrument.XYLOPHONE),
    ZOMBIE(Instrument.ZOMBIE),
    ;

    private static final Map<Instrument, VanillaInstruments> BY_BUKKIT = new HashMap<>() {{
        for (VanillaInstruments instrument : VanillaInstruments.values()) {
            put(instrument.getBukkit(), instrument);
        }
    }};

    final Instrument bukkit;

    VanillaInstruments(Instrument bukkit) {
        this.bukkit = bukkit;
    }

    public Instrument getBukkit() {
        return bukkit;
    }

    public static @NotNull VanillaInstruments getByBukkit(Instrument instrument) {
        return BY_BUKKIT.get(instrument);
    }
}
