package com.bindglam.neko.pack.block

import org.bukkit.Instrument

enum class VanillaInstruments(val bukkit: Instrument) {
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

    companion object {
        private val BY_BUKKIT = hashMapOf<Instrument, VanillaInstruments>().apply {
            VanillaInstruments.entries.forEach { this[it.bukkit] = it }
        }

        fun getByBukkit(instrument: Instrument): VanillaInstruments = BY_BUKKIT[instrument]!!
    }
}