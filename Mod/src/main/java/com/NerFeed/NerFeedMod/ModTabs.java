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
                output.accept(new ItemStack(ModItems.ALCOHOLIC_SPAWN_EGG.get())); 
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}