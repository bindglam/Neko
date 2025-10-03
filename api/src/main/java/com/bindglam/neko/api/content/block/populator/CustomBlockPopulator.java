package com.bindglam.neko.api.content.block.populator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CustomBlockPopulator extends BlockPopulator {
    private final PopulatorSettings settings;

    public CustomBlockPopulator(PopulatorSettings settings) {
        this.settings = settings;
    }

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        if(settings.iterations() > 0)
            generateOre(worldInfo, random, chunkX, chunkZ, limitedRegion);
        else
            replaceBlocks(worldInfo, random, chunkX, chunkZ, limitedRegion);
    }

    private void replaceBlocks(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion){
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;
        boolean replaceAir = !settings.replace().contains(Material.AIR);

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = worldInfo.getMinHeight(); y <= (replaceAir ? limitedRegion.getHighestBlockYAt(x, z) : worldInfo.getMaxHeight()); y++) {
                    if (!limitedRegion.isInRegion(x, y, z)) continue;
                    if (!settings.biomes().isEmpty() && !settings.biomes().contains(limitedRegion.getBiome(x, y, z))) continue;

                    Material currentMaterial = limitedRegion.getType(x, y, z);

                    if (settings.replace().contains(currentMaterial)) {
                        Position position = new Position(worldInfo, x, y, z, currentMaterial, limitedRegion.getBiome(x, y, z), limitedRegion);

                        limitedRegion.setBlockData(position.x, position.y, position.z, settings.block().blockState().getBlockData());
                    }
                }
            }
        }
    }

    private void generateOre(WorldInfo worldInfo, Random random, int x, int z, LimitedRegion limitedRegion) {
        if(random.nextDouble() > settings.chance()) return;

        int successfulPlacements = 0;
        int totalAttempts = 0;
        int maxRetries = settings.iterations() * 80;

        while(successfulPlacements < settings.iterations() && totalAttempts < maxRetries) {
            totalAttempts++;
            Position position = getRandomPlacementPosition(x, z, limitedRegion, random, worldInfo);

            if(position == null) continue;

            if(random.nextDouble() <= settings.clusterChance() && settings.veinSize() > 0 && settings.clusterChance() > 0.0) {
                successfulPlacements += generateVein(worldInfo, random, limitedRegion, position);
            }
        }
    }

    private int generateVein(WorldInfo worldInfo, Random random, LimitedRegion limitedRegion, Position start) {
        int placedBlocks = 0;

        for (int i = 0; i < settings.veinSize(); i++) {
            Position nextPosition = getAdjacentPlacementPosition(start, random, limitedRegion, placedBlocks > 0);

            if(nextPosition == null) break;

            //Bukkit.broadcast(Component.text(limitedRegion.getCenterBlockX() + " " + limitedRegion.getCenterBlockZ()));
            //Bukkit.broadcast(Component.text(nextPosition.x + " " + nextPosition.y + " " + nextPosition.z));

            if(settings.replace().contains(nextPosition.type) && (settings.biomes().isEmpty() || settings.biomes().contains(nextPosition.biome))) {
                if(!limitedRegion.isInRegion(new Location(Bukkit.getWorld(worldInfo.getUID()), nextPosition.x(), nextPosition.y(), nextPosition.z())))
                    continue;
                limitedRegion.setBlockData(nextPosition.x, nextPosition.y, nextPosition.z, settings.block().blockState().getBlockData());
                placedBlocks++;
            } else {
                break;
            }

            start = nextPosition;
        }

        return placedBlocks;
    }

    private Position getAdjacentPlacementPosition(Position start, Random random, LimitedRegion limitedRegion, boolean below) {
        Set<String> checkedLocations = new HashSet<>();
        int attempts = 0;

        try {
            for (int i = 0; i < 10 && attempts < 100; i++) {
                attempts++;
                int xOffset = random.nextInt(3) - 1;
                int yOffset = below ? -1 : (random.nextInt(3) - 1);
                int zOffset = random.nextInt(3) - 1;

                int x = start.x() + xOffset;
                int y = start.y() + yOffset;
                int z = start.z() + zOffset;

                String key = x + "," + y + "," + z;
                if (checkedLocations.contains(key)) continue;
                checkedLocations.add(key);
                if (!limitedRegion.isInRegion(x, y, z))
                    continue;

                Material blockType = limitedRegion.getType(x, y, z);

                if (settings.replace().contains(blockType)) {
                    return new Position(start.worldInfo, x, y, z, blockType, start.biome(), limitedRegion);
                }
            }
            return null;
        }catch(Exception ignored){
            return null;
        }
    }

    private Position getRandomPlacementPosition(int chunkX, int chunkZ, LimitedRegion limitedRegion, Random random, WorldInfo worldInfo) {
        try {
            int x = (chunkX << 4) + random.nextInt(16);
            int z = (chunkZ << 4) + random.nextInt(16);
            int y = settings.minLevel() + random.nextInt(settings.maxLevel() - settings.minLevel() + 1);

            if (!limitedRegion.isInRegion(x, y, z))
                return null;

            Material blockType = limitedRegion.getType(x, y, z);
            Biome biome = limitedRegion.getBiome(x, y, z);

            return new Position(worldInfo, x, y, z, blockType, biome, limitedRegion);
        } catch(Exception ignored) {
            return null;
        }
    }

    private record Position(WorldInfo worldInfo, int x, int y, int z, Material type, Biome biome, LimitedRegion region) {
    }
}
