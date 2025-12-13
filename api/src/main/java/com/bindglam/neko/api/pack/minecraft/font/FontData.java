package com.bindglam.neko.api.pack.minecraft.font;

import com.bindglam.neko.api.pack.PackFile;
import com.bindglam.neko.api.pack.PackZipper;
import com.bindglam.neko.api.pack.PackComponent;
import com.bindglam.neko.api.pack.minecraft.Float2;
import com.bindglam.neko.api.utils.GsonUtils;
import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Map;

@ApiStatus.Internal
public record FontData(
        List<Provider> providers
) implements PackComponent {

    @Override
    public void apply(String path, PackZipper zipper) {
        zipper.addFile(path, new PackFile(() -> GsonUtils.GSON.toJson(this).getBytes(), -1));
    }

    public static class Provider {
        private final Type type;

        public Provider(Type type) {
            this.type = type;
        }

        public Type type() {
            return type;
        }

        public enum Type {
            @SerializedName("bitmap")
            BITMAP,

            @SerializedName("space")
            SPACE,

            @SerializedName("ttf")
            TTF,
        }
    }

    public static class Bitmap extends Provider {
        private final String file;
        private final int height;
        private final int ascent;
        private final List<Character> chars;

        public Bitmap(String file, int height, int ascent, List<Character> chars) {
            super(Type.BITMAP);
            this.file = file;
            this.height = height;
            this.ascent = ascent;
            this.chars = chars;
        }

        public String file() {
            return file;
        }

        public int height() {
            return height;
        }

        public int ascent() {
            return ascent;
        }

        public List<Character> chars() {
            return chars;
        }
    }

    public static class Space extends Provider {
        private final Map<Character, Integer> advances;

        public Space(Map<Character, Integer> advances) {
            super(Type.SPACE);
            this.advances = advances;
        }

        public Map<Character, Integer> advances() {
            return advances;
        }
    }

    public static class TTF extends Provider {
        private final String file;
        private final Float2 shift;
        private final float size;
        private final float oversample;
        private final String skip;

        public TTF(String file, Float2 shift, float size, float oversample, String skip) {
            super(Type.TTF);
            this.file = file;
            this.shift = shift;
            this.size = size;
            this.oversample = oversample;
            this.skip = skip;
        }

        public String file() {
            return file;
        }

        public Float2 shift() {
            return shift;
        }

        public float size() {
            return size;
        }

        public float oversample() {
            return oversample;
        }

        public String skip() {
            return skip;
        }
    }
}
