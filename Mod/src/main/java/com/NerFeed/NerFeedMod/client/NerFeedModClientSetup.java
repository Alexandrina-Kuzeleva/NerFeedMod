package com.NerFeed.NerFeedMod.client;

import com.NerFeed.NerFeedMod.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = "nerfeedmod", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NerFeedModClientSetup {

    @SubscribeEvent
    public static void onClientSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Это то, что нужно для прозрачных текстур кустов
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.GRAPE_BUSH.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.BARLEY_CROP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CUCUMBER_BUSH.get(), RenderType.cutout());
        });
    }
}
