package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.potion.ModEffects;
import com.NerFeed.NerFeedMod.potion.ModPotions;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "nerfeedmod";
    private static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        ModTabs.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModBlocks.register();
        ModEffects.registerEffects();
        ModPotions.registerPotions();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // Добавляем рецепт для зелья Alcohol
            BrewingRecipeRegistry.addRecipe(new AlcoholBrewingRecipe());
            // Отключаем взрывные и туманные версии
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeHandler());
        });
        LOGGER.info("Мод NerFeedMod успешно инициализирован!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Клиент NerFeedMod настроен!");
    }
}