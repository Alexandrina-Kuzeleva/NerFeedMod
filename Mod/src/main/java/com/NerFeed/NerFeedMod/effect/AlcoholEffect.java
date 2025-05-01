package com.NerFeed.NerFeedMod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class AlcoholEffect extends MobEffect {
    public AlcoholEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFFFF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide && entity.getRandom().nextInt(100) == 0) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}