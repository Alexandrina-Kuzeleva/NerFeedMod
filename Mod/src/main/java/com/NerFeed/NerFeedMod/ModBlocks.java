package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.block.LightCube;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Block> LIGHT_CUBE = BLOCKS.register("light_cube", LightCube::new);
    public static final RegistryObject<Item> LIGHT_CUBE_ITEM = BLOCK_ITEMS.register("light_cube", () -> 
        new BlockItem(LIGHT_CUBE.get(), new Item.Properties())
    );

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}