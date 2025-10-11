package com.bindglam.neko.api.content.block.populator;

import com.bindglam.neko.api.content.block.Block;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public record PopulatorSettings(Block block, int maxLevel, int minLevel, double chance, List<Material> replace, List<Biome> biomes, int iterations, int veinSize, double clusterChance) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements com.bindglam.neko.api.utils.Builder<PopulatorSettings> {
        private Block block;
        private int maxLevel = 0;
        private int minLevel = 0;
        private double chance = 0.0;
        private List<Material> replace = new ArrayList<>();
        private List<Biome> biomes = new ArrayList<>();
        private int iterations = 0;
        private int veinSize = 0;
        private double clusterChance = 0.0;

        private Builder() {
        }


        public Builder block(Block block) {
            this.block = block;
            return this;
        }

        public Builder maxLevel(int maxLevel) {
            this.maxLevel = maxLevel;
            return this;
        }

        public Builder minLevel(int minLevel) {
            this.minLevel = minLevel;
            return this;
        }

        public Builder chance(double chance) {
            this.chance = chance;
            return this;
        }

        public Builder replace(List<Material> replace) {
            this.replace = replace;
            return this;
        }

        public Builder biomes(List<Biome> biomes) {
            this.biomes = biomes;
            return this;
        }

        public Builder iterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public Builder veinSize(int veinSize) {
            this.veinSize = veinSize;
            return this;
        }

        public Builder clusterChance(double clusterChance) {
            this.clusterChance = clusterChance;
            return this;
        }


        @Override
        public @NotNull PopulatorSettings build() {
            return new PopulatorSettings(block, maxLevel, minLevel, chance, replace, biomes, iterations, veinSize, clusterChance);
        }
    }
}
