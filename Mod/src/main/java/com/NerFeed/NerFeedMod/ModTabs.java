package com.NerFeed.NerFeedMod;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
                output.accept(ModBlocks.LIGHT_CUBE_ITEM.get()); // Добавляем предмет в вкладку
            })
            .build()
    );

    public static void register(IEventBus eventBus) {
        TABS.register(eventBus);
    }
}