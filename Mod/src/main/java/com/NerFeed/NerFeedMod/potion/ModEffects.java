package com.NerFeed.NerFeedMod.potion;

import com.NerFeed.NerFeedMod.Main;
import com.NerFeed.NerFeedMod.effect.AlcoholEffect;
import com.NerFeed.NerFeedMod.effect.VinoEffect;
import com.NerFeed.NerFeedMod.effect.VodkaEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, Main.MOD_ID);

    public static final RegistryObject<MobEffect> ALCOHOL = EFFECTS.register("alcohol", AlcoholEffect::new);
    public static final RegistryObject<MobEffect> VODKA = EFFECTS.register("vodka", VodkaEffect::new);
    public static final RegistryObject<MobEffect> VINO = EFFECTS.register("vino", VinoEffect::new);

    public static void registerEffects(IEventBus modEventBus) {
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}