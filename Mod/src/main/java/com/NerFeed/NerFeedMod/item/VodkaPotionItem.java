package com.NerFeed.NerFeedMod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.ChatFormatting;

import java.util.List;

public class VodkaPotionItem extends PotionItem {

    private static final String VODKA_COUNT_KEY = "VodkaCount";
    private static final String LAST_DRINK_TIME_KEY = "LastDrinkTime";
    private static final int METABOLISM_RATE = 1200; // 1 минута (в тиках)

    public VodkaPotionItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player) {
            CompoundTag data = entity.getPersistentData();
            long currentTime = level.getGameTime();

            // Сброс, если прошло слишком много времени
            if (currentTime - data.getLong(LAST_DRINK_TIME_KEY) > METABOLISM_RATE * 2) {
                data.putInt(VODKA_COUNT_KEY, 0);
            }

            int vodkaCount = data.getInt(VODKA_COUNT_KEY) + 1;
            data.putInt(VODKA_COUNT_KEY, vodkaCount);
            data.putLong(LAST_DRINK_TIME_KEY, currentTime);

            updateEffects(entity, vodkaCount);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    public static void handleMetabolismTick(LivingEntity entity) {
        if (entity.level().isClientSide) return;

        CompoundTag data = entity.getPersistentData();
        long currentTime = entity.level().getGameTime();
        int vodkaCount = data.getInt(VODKA_COUNT_KEY);
        long lastDrink = data.getLong(LAST_DRINK_TIME_KEY);

        if (vodkaCount > 0 && currentTime - lastDrink >= METABOLISM_RATE) {
            vodkaCount--;
            data.putInt(VODKA_COUNT_KEY, vodkaCount);
            data.putLong(LAST_DRINK_TIME_KEY, currentTime);
            updateEffects(entity, vodkaCount);
        }
    }

    private static void updateEffects(LivingEntity entity, int vodkaCount) {
        entity.removeEffect(MobEffects.DAMAGE_BOOST);
        entity.removeEffect(MobEffects.MOVEMENT_SPEED);
        entity.removeEffect(MobEffects.CONFUSION);
        entity.removeEffect(MobEffects.REGENERATION);
        entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);

        if (vodkaCount == 1) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2400, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 0));
        } else if (vodkaCount == 2) {
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2400, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200, 0));
        } else if (vodkaCount == 3) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 0));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 2400, 1));
        } else if (vodkaCount >= 4) {
            entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
            entity.getPersistentData().putInt(VODKA_COUNT_KEY, 0);
        }
    }

    @Override
    public String getDescriptionId() {
        return "item.nerfeedmod.vodka_potion";
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Effects vary based on number consumed").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }
}