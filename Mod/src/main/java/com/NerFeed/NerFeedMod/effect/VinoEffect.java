package com.NerFeed.NerFeedMod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class VinoEffect extends MobEffect {
    public VinoEffect() {
        super(MobEffectCategory.NEUTRAL, 0xE0E0E0);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1));
            if (entity.getRandom().nextInt(200) == 0) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}