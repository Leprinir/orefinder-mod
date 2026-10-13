package com.orefinder.mod.client;

import com.orefinder.mod.OreFinderMod;
import com.orefinder.mod.registry.ModItems.OreFinderItem;
import com.orefinder.mod.registry.OreTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = OreFinderMod.MOD_ID, value = Dist.CLIENT)
public class OreFinderParticleHandler {

    private int particleTickCounter = 0;
    private BlockPos lastTargetPos = null;
    private OreTypes lastTargetOre = null;
    private static final int PARTICLE_INTERVAL = 8;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        ItemStack stack = findOreFinder(mc.player);
        if (stack.isEmpty()) return;

        OreFinderItem finder = (OreFinderItem) stack.getItem();
        if (!finder.isActive()) {
            lastTargetPos = null;
            lastTargetOre = null;
            return;
        }

        OreTypes[] oresToScan = finder.getSelectedOre() != null
            ? new OreTypes[]{ finder.getSelectedOre() }
            : finder.getDetectableOres();

        BlockPos nearest = HudOverlay.findNearestOre(mc.player, finder.getRadius(), oresToScan);
        if (nearest == null) return;

        particleTickCounter++;
        if (particleTickCounter >= PARTICLE_INTERVAL) {
            particleTickCounter = 0;
            spawnOreParticles(mc, nearest);
        }

        // Ping when target changes
        if (!nearest.equals(lastTargetPos) || lastTargetOre != finder.getSelectedOre()) {
            float dist = (float) Math.sqrt(mc.player.distanceToSqr(nearest.getX(), nearest.getY(), nearest.getZ()));
            float pitch = Math.max(1.5f, Math.min(2.5f, 1.5f + (1.0f - Math.min(dist, 64f) / 64f) * 1.0f));
            mc.player.playSound(SoundEvents.NOTE_BLOCK_PLING.get(), 0.15f, pitch);
            lastTargetPos = nearest;
            lastTargetOre = finder.getSelectedOre();
        }
    }

    private void spawnOreParticles(Minecraft mc, BlockPos orePos) {
        Random rand = new Random();
        int count = 5;

        for (int i = 0; i < count; i++) {
            double ox = orePos.getX() + 0.5 + (rand.nextDouble() - 0.5) * 0.6;
            double oy = orePos.getY() + 0.2 + rand.nextDouble() * 0.6;
            double oz = orePos.getZ() + 0.5 + (rand.nextDouble() - 0.5) * 0.6;

            double mx = (rand.nextDouble() - 0.5) * 0.02;
            double my = 0.03 + rand.nextDouble() * 0.02;
            double mz = (rand.nextDouble() - 0.5) * 0.02;

            mc.level.addParticle(ParticleTypes.END_ROD, ox, oy, oz, mx, my, mz);
        }
    }

    private ItemStack findOreFinder(Player player) {
        for (ItemStack s : player.getInventory().items) {
            if (!s.isEmpty() && s.getItem() instanceof OreFinderItem) return s;
        }
        return ItemStack.EMPTY;
    }
}
