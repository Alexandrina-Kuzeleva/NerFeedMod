package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.potion.ModPotions;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MYMOD_TAB = TABS.register("mymod_tab", () -> 
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mymod"))
            .icon(() -> new ItemStack(ModBlocks.LIGHT_CUBE_ITEM.get()))
            .displayItems((parameters, output) -> {
                output.accept(ModBlocks.LIGHT_CUBE_ITEM.get());
                output.accept(PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.ALCOHOL.get()));
                output.accept(new ItemStack(ModItems.VODKA_POTION.get())); // Заменяем на кастомный предмет
                output.accept(new ItemStack(ModItems.VINO_POTION.get()));
                output.accept(new ItemStack(ModItems.ALCOHOLIC_SPAWN_EGG.get())); 
                output.accept(ModBlocks.SHOT_GLASSES_ITEM.get());
                output.accept(ModItems.GRAPE.get());
                output.accept(ModItems.BARLEY.get());
                output.accept(ModItems.BARLEY_SEEDS.get());
                output.accept(ModItems.DRIED_BARLEY.get());
                output.accept(ModItems.BEER.get());
                output.accept(ModBlocks.DRYING_TABLE.get());
                output.accept(ModItems.CUCUMBER.get());
                output.accept(ModItems.CUCUMBER_SEEDS.get());
                output.accept(ModItems.PICKLED_CUCUMBERS.get());
                output.accept(ModItems.SALT.get());
                output.accept(ModItems.BEER_BREWING_MACHINE_ITEM.get());
                output.accept(ModItems.LEMON.get());
                output.accept(ModBlocks.LEMON_LOG_ITEM.get());
                output.accept(ModBlocks.LEMON_LEAVES_ITEM.get());
                output.accept(ModBlocks.LEMON_SAPLING_ITEM.get());
                output.accept(ModItems.ALCOHOLIC_HELMET.get());
                output.accept(ModItems.ALCOHOLIC_CHESTPLATE.get());
                output.accept(ModItems.ALCOHOLIC_LEGGINGS.get());
                output.accept(ModItems.ALCOHOLIC_BOOTS.get());
                output.accept(ModItems.EDGE_GRATE.get());
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}