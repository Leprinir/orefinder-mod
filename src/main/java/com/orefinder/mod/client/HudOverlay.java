package com.orefinder.mod.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.orefinder.mod.OreFinderMod;
import com.orefinder.mod.registry.ModItems.OreFinderItem;
import com.orefinder.mod.registry.OreTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = OreFinderMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HudOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        ItemStack stack = findOreFinder(mc.player);
        if (stack.isEmpty()) return;

        OreFinderItem finder = (OreFinderItem) stack.getItem();
        if (!finder.isActive()) return;

        OreTypes targetOre = finder.getSelectedOre();
        OreTypes[] oresToScan = targetOre != null ? new OreTypes[]{ targetOre } : finder.getDetectableOres();

        BlockPos nearest = findNearestOre(mc.player, finder.getRadius(), oresToScan);
        if (nearest == null) return;

        int dist = (int) Math.sqrt(mc.player.distanceToSqr(nearest.getX(), nearest.getY(), nearest.getZ()));

        GuiGraphics graphics = event.getGuiGraphics();
        int screenW = mc.getWindow().getGuiScaledWidth();
        int screenH = mc.getWindow().getGuiScaledHeight();
        int centerX = screenW / 2;
        int centerY = screenH - 50;

        // Compass arrow pointing to ore
        double angle = Math.atan2(nearest.getZ() - mc.player.getZ(), nearest.getX() - mc.player.getX());
        double yaw = Math.toRadians(-mc.player.getYRot());
        double relAngle = angle - yaw;

        int arrowLen = 40;
        int ax = centerX + (int)(Math.cos(relAngle) * arrowLen);
        int ay = centerY + (int)(Math.sin(relAngle) * arrowLen);

        // Draw arrow
        graphics.fill(ax - 2, ay - 2, ax + 2, ay + 2, 0xFFFFFF00);

        // Distance text
        Font font = mc.font;
        String text = dist + "m to " + (targetOre != null ? targetOre.getName() : "ore");
        int textW = font.width(text);
        graphics.drawString(font, text, centerX - textW / 2, centerY - 20, 0xFFFF00, true);

        // Ore name
        String oreName = targetOre != null
            ? Character.toUpperCase(targetOre.getName().charAt(0)) + targetOre.getName().substring(1)
            : "Nearest ore";
        int oreW = font.width(oreName);
        graphics.drawString(font, oreName, centerX - oreW / 2, centerY - 32, 0xFFFFFF, true);

        // Tier indicator
        String tier = "§6[ TIER " + finder.getTier() + " ]";
        int tierW = font.width(tier);
        graphics.drawString(font, tier, centerX - tierW / 2, centerY + 5, 0xAAAAAA, true);
    }

    private static ItemStack findOreFinder(Player player) {
        for (ItemStack s : player.getInventory().items) {
            if (!s.isEmpty() && s.getItem() instanceof OreFinderItem) return s;
        }
        return ItemStack.EMPTY;
    }

    public static BlockPos findNearestOre(Player player, int radius, OreTypes[] ores) {
        BlockPos playerPos = BlockPos.containing(player.getX(), player.getY(), player.getZ());
        AABB searchBox = new AABB(
            playerPos.getX() - radius, playerPos.getY() - radius / 2, playerPos.getZ() - radius,
            playerPos.getX() + radius, playerPos.getY() + radius / 2, playerPos.getZ() + radius
        );

        BlockPos nearest = null;
        double nearestDist = Double.MAX_VALUE;

        for (OreTypes ore : ores) {
            List<BlockPos> blocks = new ArrayList<>();
            int minX = playerPos.getX() - radius, maxX = playerPos.getX() + radius;
            int minY = playerPos.getY() - radius / 2, maxY = playerPos.getY() + radius / 2;
            int minZ = playerPos.getZ() - radius, maxZ = playerPos.getZ() + radius;
            for (int x = minX; x <= maxX; x++)
                for (int y = minY; y <= maxY; y++)
                    for (int z = minZ; z <= maxZ; z++) {
                        BlockPos pos = new BlockPos(x, y, z);
                        if (player.level().getBlockState(pos).is(ore.getBlock()))
                            blocks.add(pos);
                    }

            for (BlockPos orePos : blocks) {
                double d = player.distanceToSqr(orePos.getX(), orePos.getY(), orePos.getZ());
                if (d < nearestDist) {
                    nearestDist = d;
                    nearest = orePos;
                }
            }
        }
        return nearest;
    }
}
