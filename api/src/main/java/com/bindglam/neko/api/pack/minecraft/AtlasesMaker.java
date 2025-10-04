package com.bindglam.neko.api.pack.minecraft;

import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.utils.GsonUtils;

import java.util.ArrayList;

public final class AtlasesMaker {
    private static final AtlasesData BLOCK_ATLASES = new AtlasesData(new ArrayList<>());

    private AtlasesMaker() {
    }

    public static void pack(PackZipper zipper) {
        createAtlasFile("blocks", BLOCK_ATLASES, zipper);
    }

    private static void createAtlasFile(String name, AtlasesData atlasesData, PackZipper zipper) {
        byte[] data = GsonUtils.GSON.toJson(atlasesData).getBytes();

        zipper.addFile("assets/minecraft/atlases/" + name + ".json", new PackFile(() -> data, data.length));
    }

    public static void addBlock(AtlasesData.Source source) {
        BLOCK_ATLASES.sources().add(source);
    }
}
