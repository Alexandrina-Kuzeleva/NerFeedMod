package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.entity.AlcoholicEntity;
import com.NerFeed.NerFeedMod.item.VinoPotionItem;
import com.NerFeed.NerFeedMod.item.VodkaPotionItem;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}