package com.NerFeed.NerFeedMod.client;

import com.NerFeed.NerFeedMod.ModEntity;
import com.NerFeed.NerFeedMod.client.renderer.AlcoholicRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = ModEntity.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NerFeedModClient {
    
    @SubscribeEvent
    public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.ALCOHOLIC.get(), AlcoholicRenderer::new);
    }
}