package com.orefinder.mod.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public enum OreTypes {
    COAL(Blocks.COAL_ORE, "coal", 5, 0),
    COPPER(Blocks.COPPER_ORE, "copper", 10, 1),
    IRON(Blocks.IRON_ORE, "iron", 15, 2),
    GOLD(Blocks.GOLD_ORE, "gold", 20, 3),
    LAPIS(Blocks.LAPIS_ORE, "lapis", 20, 4),
    REDSTONE(Blocks.REDSTONE_ORE, "redstone", 20, 5),
    EMERALD(Blocks.EMERALD_ORE, "emerald", 30, 6),
    DIAMOND(Blocks.DIAMOND_ORE, "diamond", 50, 7),
    NETHER_GOLD(Blocks.NETHER_GOLD_ORE, "nether_gold", 5, 8),
    NETHER_QUARTZ(Blocks.NETHER_QUARTZ_ORE, "nether_quartz", 10, 9),
    DEBRIS(Blocks.ANCIENT_DEBRIS, "netherite_scrap", 100, 10);

    private final Block block;
    private final String name;
    private final int rarity;
    private final int sortOrder;

    OreTypes(Block block, String name, int rarity, int sortOrder) {
        this.block = block;
        this.name = name;
        this.rarity = rarity;
        this.sortOrder = sortOrder;
    }

    public Block getBlock() { return block; }
    public String getName() { return name; }
    public int getRarity() { return rarity; }
    public int getSortOrder() { return sortOrder; }

    public static OreTypes[] getByMinTier(int tier) {
        return switch (tier) {
            case 1 -> new OreTypes[]{ COAL };
            case 2 -> new OreTypes[]{ COAL, COPPER, IRON, GOLD };
            case 3 -> new OreTypes[]{ COAL, COPPER, IRON, GOLD, LAPIS, REDSTONE };
            case 4 -> new OreTypes[]{ COAL, COPPER, IRON, GOLD, LAPIS, REDSTONE, EMERALD, DIAMOND };
            default -> values();
        };
    }
}
