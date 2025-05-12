package com.NerFeed.NerFeedMod;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.levelgen.GenerationStep;
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
import com.NerFeed.NerFeedMod.screen.DryingTableScreen;
import com.NerFeed.NerFeedMod.worldgen.ModConfiguredFeatures;
import com.NerFeed.NerFeedMod.worldgen.ModPlacedFeatures;




@Mod(Main.MOD_ID)
public class Main {
    public static final String MOD_ID = "nerfeedmod";
    private static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        // Основные регистрации
        ModTabs.register(modEventBus);
        ModBlocks.register(modEventBus);  // Блоки должны быть первыми
        ModItems.register(modEventBus);
        ModEntity.register(modEventBus);

        // Генерация мира (должна быть после блоков)
        //ModConfiguredFeatures.register(modEventBus);
        //ModPlacedFeatures.register(modEventBus);
        
        // Эффекты и зелья
        ModEffects.registerEffects(modEventBus);  // Сначала эффекты
        ModPotions.registerPotions();             // Потом зелья
        
        // Остальные регистрации
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenus.MENUS.register(modEventBus);
        
        // Event listeners
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::buildCreativeTabContents);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new AlcoholBrewingRecipe());
            BrewingRecipeRegistry.addRecipe(new BrewingRecipeHandler());
        });
        LOGGER.info("Мод NerFeedMod успешно инициализирован!");
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenus.DRYING_TABLE.get(), DryingTableScreen::new);
        });
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