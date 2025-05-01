package com.NerFeed.NerFeedMod;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.NerFeed.NerFeedMod.potion.ModEffects;
import com.NerFeed.NerFeedMod.potion.ModPotions;

@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "nerfeedmod";
    private static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::buildCreativeTabContents);
        ModTabs.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModEffects.registerEffects(modEventBus);
        ModPotions.registerPotions();
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new AlcoholBrewingRecipe());
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeHandler());
        });
        LOGGER.info("Мод NerFeedMod успешно инициализирован!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Клиент NerFeedMod настроен!");
    }

    private void buildCreativeTabContents(final BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModTabs.MYMOD_TAB.get()) {
            event.accept(new ItemStack(ModBlocks.LIGHT_CUBE_ITEM.get()));
            event.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.ALCOHOL.get()));
            event.accept(new ItemStack(ModItems.VODKA_POTION.get()));
        }
    }
}