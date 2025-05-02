package com.NerFeed.NerFeedMod.potion;

import com.NerFeed.NerFeedMod.Main;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, Main.MOD_ID);

    public static final RegistryObject<Potion> ALCOHOL = POTIONS.register("alcohol", 
        () -> new Potion(new MobEffectInstance(ModEffects.ALCOHOL.get(), 3600, 0)));
    public static final RegistryObject<Potion> VODKA = POTIONS.register("vodka", 
        () -> new Potion(new MobEffectInstance(ModEffects.VODKA.get(), 3600, 0)));
    public static final RegistryObject<Potion> VINO = POTIONS.register("vino", 
        () -> new Potion(new MobEffectInstance(ModEffects.VINO.get(), 3600, 0)));

    public static void registerPotions() {
        POTIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}