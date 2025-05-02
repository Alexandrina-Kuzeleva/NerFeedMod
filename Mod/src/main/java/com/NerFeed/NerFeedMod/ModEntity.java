package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.entity.AlcoholicEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntity {
    public static final String MOD_ID = "nerfeedmod";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = 
        DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);

    public static final RegistryObject<EntityType<AlcoholicEntity>> ALCOHOLIC = 
        ENTITY_TYPES.register("alcoholic",
            () -> EntityType.Builder.of(AlcoholicEntity::new, MobCategory.MONSTER)
                .sized(0.6F, 1.95F)
                .build(MOD_ID + ":alcoholic"));  // Ключевое изменение здесь

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);  // Используем переданный eventBus
    }
}