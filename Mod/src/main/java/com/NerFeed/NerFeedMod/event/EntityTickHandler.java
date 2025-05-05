package com.NerFeed.NerFeedMod.event;

import com.NerFeed.NerFeedMod.item.VinoPotionItem;
import com.NerFeed.NerFeedMod.item.VodkaPotionItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityTickHandler {

    @SubscribeEvent
    public static void onLivingTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            VodkaPotionItem.handleMetabolismTick(event.player);
            VinoPotionItem.handleMetabolismTick(event.player);

        }
    }
}
