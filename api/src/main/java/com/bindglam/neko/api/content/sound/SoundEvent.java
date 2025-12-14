package com.bindglam.neko.api.content.sound;

import com.bindglam.neko.api.content.sound.properties.SoundEventProperties;
import org.bukkit.Keyed;
import org.jetbrains.annotations.NotNull;

public interface SoundEvent extends Keyed {
    @NotNull SoundEventProperties properties();
}
