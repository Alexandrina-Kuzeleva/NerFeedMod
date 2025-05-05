package com.NerFeed.NerFeedMod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    private static final String VODKA_COUNT_KEY = "VodkaCount";
    private static final String WINE_COUNT_KEY = "WineCount";

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            CompoundTag data = player.getPersistentData();
            data.putInt(VODKA_COUNT_KEY, 0);
            data.putInt(WINE_COUNT_KEY, 0);
        }
    }
}
