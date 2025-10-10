package com.bindglam.neko.api.pack.minecraft;

import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.minecraft.model.ModelData;
import com.bindglam.neko.api.utils.GsonUtils;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;

@ApiStatus.Internal
public final class AtlasesMaker {
    private static final AtlasesData BLOCK_ATLASES = new AtlasesData(new ArrayList<>());

    private AtlasesMaker() {
    }

    public static void pack(PackZipper zipper) {
        createAtlasFile("blocks", BLOCK_ATLASES, zipper);
    }

    private static void createAtlasFile(String name, AtlasesData atlasesData, PackZipper zipper) {
        zipper.addFile("assets/minecraft/atlases/" + name + ".json", new PackFile(() -> GsonUtils.GSON.toJson(atlasesData).getBytes()));
    }

    public static void addBlock(AtlasesData.Source source) {
        BLOCK_ATLASES.sources().add(source);
    }

    public static void addAllFromModel(Key key, PackZipper zipper) {
        PackFile modelFile = zipper.file("assets/" + key.namespace() + "/models/" + key.value() + ".json");
        if(modelFile != null) {
            byte[] data = modelFile.bytes().get();
            ModelData model = GsonUtils.GSON.fromJson(new String(data), ModelData.class);

            model.textures().forEach((name, path) -> AtlasesMaker.addBlock(new AtlasesData.Source(AtlasesData.Source.Type.SINGLE, path)));
        }
    }
}
