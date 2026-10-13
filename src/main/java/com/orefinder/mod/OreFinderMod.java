package com.orefinder.mod;

import com.mojang.logging.LogUtils;
import com.orefinder.mod.registry.ModItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServingGraphEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(OreFinderMod.MOD_ID)
public class OreFinderMod {
    public static final String MOD_ID = "orefinder";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OreFinderMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("OreFinder mod initialized!");
        LOGGER.info("Difficulty: {} | max health: {}", 
            net.minecraft.world.Difficulty.PEACEFUL, 
            net.minecraft.world.entity.player.Player.MAX_HEALTH);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("OreFinder client setup complete.");
    }

    @SubscribeEvent
    public void onServerStarting(ServingGraphEvent event) {
        LOGGER.info("OreFinder: server is starting.");
    }
}
