package com.orefinder.mod.client;

import com.orefinder.mod.OreFinderMod;
import com.orefinder.mod.registry.ModItems.OreFinderItem;
import com.orefinder.mod.registry.OreTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import java.util.*;

@Mod.EventBusSubscriber(modid = OreFinderMod.MOD_ID, value = Dist.CLIENT)
public class OreFinderEventHandler {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof OreFinderItem finder)) return;
        Player player = event.getEntity();

        finder.setActive(!finder.isActive());
        boolean now = finder.isActive();

        if (player.level().isClientSide) {
            player.playSound(SoundEvents.COMPARATOR_CLICK, 0.5F, now ? 1.2F : 0.8F);
            if (now) {
                BlockPos nearest = HudOverlay.findNearestOre(player, finder.getRadius(),
                    finder.getSelectedOre() != null
                        ? new OreTypes[]{ finder.getSelectedOre() }
                        : finder.getDetectableOres());
                if (nearest != null) {
                    int dist = (int) Math.sqrt(player.distanceToSqr(nearest.getX(), nearest.getY(), nearest.getZ()));
                    String oreName = finder.getSelectedOre() != null ? finder.getSelectedOre().getName() : "ore";
                    player.displayClientMessage(
                        Component.literal("§a[OreFinder] §fScanning for §e" + oreName + "§f... Nearest: §6" + dist + "m"),
                        true);
                } else {
                    player.displayClientMessage(
                        Component.literal("§c[OreFinder] §fNo ore found in range."),
                        true);
                }
            } else {
                player.displayClientMessage(
                    Component.literal("§7[OreFinder] §fScanner §cdisabled§f."),
                    true);
            }
        }
        event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onShiftRightClick(PlayerInteractEvent.RightClickItem event) {
        if (!event.getEntity().isShiftKeyDown()) return;
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof OreFinderItem finder)) return;
        Player player = event.getEntity();

        OreTypes[] ores = finder.getDetectableOres();
        OreTypes current = finder.getSelectedOre();
        int idx = current == null ? -1 : Arrays.asList(ores).indexOf(current);
        int next = (idx + 1) % (ores.length + 1);

        if (next == ores.length) {
            finder.setSelectedOre(null);
            if (player.level().isClientSide)
                player.displayClientMessage(Component.literal("§b[OreFinder] §fMode: §aAll ores"), true);
        } else {
            OreTypes sel = ores[next];
            finder.setSelectedOre(sel);
            if (player.level().isClientSide)
                player.displayClientMessage(Component.literal("§b[OreFinder] §fTarget: §e" + sel.getName()), true);
        }
        event.setCanceled(true);
    }
}
