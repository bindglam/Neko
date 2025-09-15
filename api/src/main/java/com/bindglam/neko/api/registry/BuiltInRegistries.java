package com.bindglam.neko.api.registry;

import com.bindglam.neko.api.content.MechanismFactory;
import com.bindglam.neko.api.content.glyph.Glyph;
import com.bindglam.neko.api.content.item.CustomItem;
import com.bindglam.neko.api.content.item.block.CustomBlock;

public interface BuiltInRegistries {
    Registry<CustomItem> ITEMS = empty();
    Registry<CustomBlock> BLOCKS = empty();
    Registry<MechanismFactory<?>> MECHANISMS = empty();
    Registry<Glyph> GLYPHS = empty();

    private static <T> Registry<T> empty() {
        return new ScalableRegistry<>();
    }
}
