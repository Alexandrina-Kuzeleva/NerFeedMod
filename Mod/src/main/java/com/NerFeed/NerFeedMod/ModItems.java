package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.armor.AlcoholicArmor;
import com.NerFeed.NerFeedMod.entity.AlcoholicEntity;
import com.NerFeed.NerFeedMod.item.BarleyItem;
import com.NerFeed.NerFeedMod.item.BarleySeedsItem;
import com.NerFeed.NerFeedMod.item.BeerBrewingMachineItem;
import com.NerFeed.NerFeedMod.item.BeerItem;
import com.NerFeed.NerFeedMod.item.CucumberItem;
import com.NerFeed.NerFeedMod.item.CucumberSeedsItem;
import com.NerFeed.NerFeedMod.item.DriedBarleyItem;
import com.NerFeed.NerFeedMod.item.GrapeItem;
import com.NerFeed.NerFeedMod.item.LemonItem;
import com.NerFeed.NerFeedMod.item.PickledCucumbersItem;
import com.NerFeed.NerFeedMod.item.SaltItem;
import com.NerFeed.NerFeedMod.item.VinoPotionItem;
import com.NerFeed.NerFeedMod.item.VodkaPotionItem;
import com.NerFeed.NerFeedMod.material.AlcoholicArmorMaterial;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final RegistryObject<Item> VODKA_POTION = ITEMS.register("vodka_potion", () -> 
        new VodkaPotionItem(new Item.Properties().stacksTo(1))
    );

    public static final RegistryObject<Item> VINO_POTION = ITEMS.register("vino_potion", () -> 
        new VinoPotionItem(new Item.Properties().stacksTo(1))
    );

    public static final RegistryObject<Item> ALCOHOLIC_SPAWN_EGG = ITEMS.register("alcoholic_spawn_egg",
        () -> new Item(new Item.Properties()) {
            @Override
            public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
                if (!level.isClientSide) {
                    AlcoholicEntity entity = new AlcoholicEntity(ModEntity.ALCOHOLIC.get(), level);
                    entity.moveTo(player.getX(), player.getY(), player.getZ());
                    level.addFreshEntity(entity);
                    if (!player.isCreative()) {
                        player.getItemInHand(hand).shrink(1);
                    }
                }
                return InteractionResultHolder.success(player.getItemInHand(hand));
            }
        }
    );

    public static final RegistryObject<Item> GRAPE = ITEMS.register("grape", GrapeItem::new);
    public static final RegistryObject<Item> GRAPE_BUSH_ITEM = ITEMS.register("grape_bush", 
        () -> new BlockItem(ModBlocks.GRAPE_BUSH.get(), new Item.Properties()));

    public static final RegistryObject<Item> BARLEY_SEEDS = ITEMS.register("barley_seeds",
        () -> new BarleySeedsItem(new Item.Properties()));
    public static final RegistryObject<Item> BARLEY = ITEMS.register("barley", 
        () -> new BarleyItem(new Item.Properties()
    ));
    
    public static final RegistryObject<Item> BEER = ITEMS.register("beer", 
        () -> new BeerItem(new Item.Properties().stacksTo(1))
    );

    public static final RegistryObject<Item> DRIED_BARLEY = ITEMS.register("dried_barley",
        () -> new Item(new Item.Properties())
    );

    public static final RegistryObject<Item> DRYING_TABLE = ITEMS.register("drying_table",
        () -> new BlockItem(ModBlocks.DRYING_TABLE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds",
        () -> new CucumberSeedsItem(new Item.Properties()));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", 
        () -> new CucumberItem(new Item.Properties())
    );
    public static final RegistryObject<Item> PICKLED_CUCUMBERS = ITEMS.register("pickled_cucumbers", 
        () -> new PickledCucumbersItem(new Item.Properties())
    );
    public static final RegistryObject<Item> SALT = ITEMS.register("salt", 
        () -> new SaltItem(new Item.Properties())
    );
    public static final RegistryObject<Item> BEER_BREWING_MACHINE_ITEM = ITEMS.register("beer_brewing_machine_item", 
        () -> new BeerBrewingMachineItem(new Item.Properties())
    );
    public static final RegistryObject<Item> LEMON = ITEMS.register("lemon", LemonItem::new);

    // Регистрация всех частей брони
    public static final RegistryObject<Item> ALCOHOLIC_HELMET = ITEMS.register("alcoholic_helmet",
            () -> new AlcoholicArmor(new AlcoholicArmorMaterial(), ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ALCOHOLIC_CHESTPLATE = ITEMS.register("alcoholic_chestplate",
            () -> new AlcoholicArmor(new AlcoholicArmorMaterial(), ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ALCOHOLIC_LEGGINGS = ITEMS.register("alcoholic_leggings",
            () -> new AlcoholicArmor(new AlcoholicArmorMaterial(), ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ALCOHOLIC_BOOTS = ITEMS.register("alcoholic_boots",
            () -> new AlcoholicArmor(new AlcoholicArmorMaterial(), ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}