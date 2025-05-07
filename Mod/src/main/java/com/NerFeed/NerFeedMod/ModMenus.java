package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.block.entity.DryingTableMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);

    public static final RegistryObject<MenuType<DryingTableMenu>> DRYING_TABLE =
            MENUS.register("drying_table",
                    () -> IForgeMenuType.create((id, inv, data) -> new DryingTableMenu(id, inv, data)));
}