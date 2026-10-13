package com.orefinder.mod.registry;

import com.orefinder.mod.OreFinderMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, OreFinderMod.MOD_ID);

    // ─── Register items FIRST (before OREFINDER_TAB) ────────────────
    public static final RegistryObject<Item> STONE_ORE_FINDER = ITEMS.register("stone_ore_finder",
        () -> new OreFinderItem(1, 8, OreTypes.getByMinTier(1)));

    public static final RegistryObject<Item> IRON_ORE_FINDER = ITEMS.register("iron_ore_finder",
        () -> new OreFinderItem(2, 16, OreTypes.getByMinTier(2)));

    public static final RegistryObject<Item> GOLD_ORE_FINDER = ITEMS.register("gold_ore_finder",
        () -> new OreFinderItem(3, 32, OreTypes.getByMinTier(3)));

    public static final RegistryObject<Item> DIAMOND_ORE_FINDER = ITEMS.register("diamond_ore_finder",
        () -> new OreFinderItem(4, 64, OreTypes.getByMinTier(4)));

    public static final RegistryObject<Item> EMERALD_MASTER_FINDER = ITEMS.register("emerald_master_finder",
        () -> new OreFinderItem(5, 128, OreTypes.getByMinTier(5)));

    public static final RegistryObject<Item> ORE_FINDER_UPGRADE = ITEMS.register("ore_finder_upgrade",
        OreFinderUpgradeItem::new);

    // ─── Creative tab (uses already-declared items) ─────────────────
    public static final CreativeModeTab OREFINDER_TAB = CreativeModeTab.builder(CreativeModeTab.Row.BOTTOM, 3)
        .title(Component.translatable("itemGroup.orefinder.ores"))
        .icon(() -> new ItemStack(STONE_ORE_FINDER.get()))
        .displayItems((params, output) -> {
            ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
        })
        .build();

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    // ─── OreFinder Item Logic ───────────────────────────────────────
    public static class OreFinderItem extends Item {
        private final int tier;
        private final int radius;
        private final OreTypes[] detectableOres;
        private boolean active = false;
        private OreTypes selectedOre = null;

        public OreFinderItem(int tier, int radius, OreTypes[] detectableOres) {
            super(new Properties()
                .durability(256)
                .stacksTo(1));
            this.tier = tier;
            this.radius = radius;
            this.detectableOres = detectableOres;
        }

        public int getTier() { return tier; }
        public int getRadius() { return radius; }
        public OreTypes[] getDetectableOres() { return detectableOres; }
        public boolean isActive() { return active; }
        public void setActive(boolean v) { this.active = v; }
        public OreTypes getSelectedOre() { return selectedOre; }
        public void setSelectedOre(OreTypes o) { this.selectedOre = o; }

        @Override
        public boolean isBarVisible(ItemStack stack) { return true; }

        @Override
        public int getBarWidth(ItemStack stack) {
            return Math.round(13.0F * (float) stack.getDamageValue() / (float) stack.getMaxDamage());
        }

        @Override
        public int getBarColor(ItemStack stack) {
            float pct = 1.0F - (float) stack.getDamageValue() / (float) stack.getMaxDamage();
            return (int)(40 + 215 * pct) << 16 | 0xFF00;
        }

        @Override
        public boolean isEnchantable(ItemStack stack) { return false; }

        @Override
        @OnlyIn(Dist.CLIENT)
        public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, net.minecraft.world.item.TooltipFlag flag) {
            tooltip.add(Component.literal("§6Tier " + tier + " | Radius: " + radius + " blocks"));
            tooltip.add(Component.literal("§7Detects: §f" + getDetectableNames()));
            tooltip.add(Component.literal(""));
            tooltip.add(Component.literal("§eRight-click: §7Toggle scanner"));
            tooltip.add(Component.literal("§eShift+Right-click: §7Select ore type"));
            tooltip.add(Component.literal("§7Durability: " + (stack.getMaxDamage() - stack.getDamageValue()) + "/" + stack.getMaxDamage()));
        }

        private String getDetectableNames() {
            StringBuilder sb = new StringBuilder();
            for (OreTypes ore : detectableOres) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(ore.getName().substring(0, 1).toUpperCase() + ore.getName().substring(1));
            }
            return sb.toString();
        }
    }

    // ─── Upgrade Item Logic ─────────────────────────────────────────
    public static class OreFinderUpgradeItem extends Item {
        public OreFinderUpgradeItem() {
            super(new Properties().stacksTo(64));
        }
    }
}
