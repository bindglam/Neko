package com.bindglam.neko.api.pack.minecraft;

import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.minecraft.model.ModelData;
import com.bindglam.neko.api.utils.CollectionUtils;
import com.bindglam.neko.api.utils.GsonUtils;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public final class AtlasesMaker {
    private static AtlasesData BLOCK_ATLASES = new AtlasesData(List.of());

    private AtlasesMaker() {
    }

    public static void pack(PackZipper zipper) {
        createAtlasFile("blocks", BLOCK_ATLASES, zipper);
    }

    private static void createAtlasFile(String name, AtlasesData atlasesData, PackZipper zipper) {
        zipper.addComponent("assets/minecraft/atlases/" + name + ".json", atlasesData);
    }

    public static void addBlock(AtlasesData.Source source) {
        BLOCK_ATLASES = new AtlasesData(CollectionUtils.copyAndAdd(BLOCK_ATLASES.sources(), source));
    }

    public static void addAllFromModel(Key key, PackZipper zipper) {
        PackFile modelFile = zipper.file("assets/" + key.namespace() + "/models/" + key.value() + ".json");
        if(modelFile != null) {
            byte[] data = modelFile.bytes();
            ModelData model = GsonUtils.GSON.fromJson(new String(data), ModelData.class);

            model.textures().forEach((name, path) -> AtlasesMaker.addBlock(new AtlasesData.Source(AtlasesData.Source.Type.SINGLE, path)));
        }
    }
}
