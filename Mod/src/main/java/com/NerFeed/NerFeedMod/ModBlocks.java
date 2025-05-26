package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.block.BarleyCropBlock;
import com.NerFeed.NerFeedMod.block.BeerBrewingMachine;
import com.NerFeed.NerFeedMod.block.CucumberBushBlock;
import com.NerFeed.NerFeedMod.block.DryingTableBlock;
import com.NerFeed.NerFeedMod.block.GrapeBushBlock;
import com.NerFeed.NerFeedMod.block.LemonLeavesBlock;
import com.NerFeed.NerFeedMod.block.LemonLogBlock;
import com.NerFeed.NerFeedMod.block.LemonSaplingBlock;
import com.NerFeed.NerFeedMod.block.LemonTreeGrower;
import com.NerFeed.NerFeedMod.block.LightCube;
import com.NerFeed.NerFeedMod.block.MugOfBeerBlock;
import com.NerFeed.NerFeedMod.block.PickledCucumbersBlock;
import com.NerFeed.NerFeedMod.block.ShotGlassesBlock;
import com.NerFeed.NerFeedMod.block.VinoBottleBlock;
import com.NerFeed.NerFeedMod.block.VodkaBottleBlock;
import com.NerFeed.NerFeedMod.item.GrapeItem;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
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

    public static final RegistryObject<Block> VODKA_BOTTLE_BLOCK = BLOCKS.register("vodka_bottle_block", VodkaBottleBlock::new);
    public static final RegistryObject<Item> VODKA_BOTTLE_BLOCK_ITEM = BLOCK_ITEMS.register("vodka_bottle_block", () -> 
        new BlockItem(VODKA_BOTTLE_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> VINO_BOTTLE_BLOCK = BLOCKS.register("vino_bottle_block", VinoBottleBlock::new);
    public static final RegistryObject<Item> VINO_BOTTLE_BLOCK_ITEM = BLOCK_ITEMS.register("vino_bottle_block", () -> 
        new BlockItem(VINO_BOTTLE_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> SHOT_GLASSES = BLOCKS.register("shot_glasses", ShotGlassesBlock::new);
    public static final RegistryObject<Item> SHOT_GLASSES_ITEM = BLOCK_ITEMS.register("shot_glasses",
            () -> new BlockItem(SHOT_GLASSES.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> GRAPE_BUSH = BLOCKS.register("grape_bush", GrapeBushBlock::new);

    public static final RegistryObject<Block> BARLEY_CROP = BLOCKS.register("barley_crop",
    () -> new BarleyCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<Block> MUG_OF_BEER = BLOCKS.register("mug_of_beer", MugOfBeerBlock::new);
    public static final RegistryObject<Item> MUG_OF_BEER_ITEM = BLOCK_ITEMS.register("mug_of_beer", () -> 
        new BlockItem(MUG_OF_BEER.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> DRYING_TABLE = BLOCKS.register("drying_table",
        () -> new DryingTableBlock()
    );

    public static final RegistryObject<Block> CUCUMBER_BUSH = BLOCKS.register("cucumber_bush",
    () -> new CucumberBushBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT))
    );

    public static final RegistryObject<Block> PICKLED_CUCUMBERS_BLOCK = BLOCKS.register("pickled_cucumbers_block", PickledCucumbersBlock::new);
    public static final RegistryObject<Item> PICKLED_CUCUMBERS_BLOCK_ITEM = BLOCK_ITEMS.register("pickled_cucumbers_block", () -> 
        new BlockItem(PICKLED_CUCUMBERS_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> BEER_BREWING_MACHINE = BLOCKS.register("beer_brewing_machine",
        () -> new BeerBrewingMachine()
    );

    public static final RegistryObject<Block> LEMON_LOG = BLOCKS.register("lemon_log", LemonLogBlock::new);
    public static final RegistryObject<Item> LEMON_LOG_ITEM = BLOCK_ITEMS.register("lemon_log",
        () -> new BlockItem(LEMON_LOG.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> LEMON_LEAVES = BLOCKS.register("lemon_leaves",
        () -> new LemonLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Item> LEMON_LEAVES_ITEM = BLOCK_ITEMS.register("lemon_leaves",
        () -> new BlockItem(LEMON_LEAVES.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> LEMON_SAPLING = BLOCKS.register("lemon_sapling",
        () -> new LemonSaplingBlock(new LemonTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Item> LEMON_SAPLING_ITEM = BLOCK_ITEMS.register("lemon_sapling",
        () -> new BlockItem(LEMON_SAPLING.get(), new Item.Properties())
    );

    public static final RegistryObject<Block> EDGE_GRATE = BLOCKS.register("edge_grate",
        () -> new EdgeGrateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BARS)));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ITEMS.register(eventBus);
    }

}