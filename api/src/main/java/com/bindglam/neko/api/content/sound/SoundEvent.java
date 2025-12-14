package com.bindglam.neko.api.content.sound;

import com.bindglam.neko.api.content.sound.properties.Sound;
import com.bindglam.neko.api.content.sound.properties.SoundEventProperties;
import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.Packable;
import com.bindglam.neko.api.pack.minecraft.sound.Sounds;
import com.bindglam.neko.api.utils.GsonUtils;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundEvent implements Keyed, Packable {
    private final NamespacedKey key;
    private final SoundEventProperties properties;

    public SoundEvent(NamespacedKey key, SoundEventProperties properties) {
        this.key = key;
        this.properties = properties;
    }

    @Override
    public void pack(@NotNull PackZipper zipper) {
        String filePath = "assets/" + key.namespace() + "/sounds.json";

        PackFile soundsFile = zipper.file(filePath);

        Map<String, com.bindglam.neko.api.pack.minecraft.sound.SoundEvent> sounds;
        if(soundsFile != null)
            sounds = new HashMap<>(GsonUtils.GSON.fromJson(new String(soundsFile.bytes()), Sounds.class).events());
        else
            sounds = new HashMap<>();

        sounds.put(key.value().replace("/", "."), new com.bindglam.neko.api.pack.minecraft.sound.SoundEvent(
                properties.subtitle(),
                List.copyOf(properties.sounds().stream().map(Sound::toResourcePack).toList())
        ));

        zipper.addComponent(filePath, new Sounds(sounds));
    }

    public @NotNull SoundEventProperties properties() {
        return properties;
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return key;
    }
}
