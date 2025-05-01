package com.NerFeed.NerFeedMod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import java.util.List;

public class VodkaPotionItem extends PotionItem {
    private static final String VODKA_COUNT_KEY = "VodkaCount";

    public VodkaPotionItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            // Получаем persistentData игрока
            CompoundTag persistentData = entity.getPersistentData();
            int vodkaCount = persistentData.getInt(VODKA_COUNT_KEY);
            vodkaCount++; // Увеличиваем счётчик
            persistentData.putInt(VODKA_COUNT_KEY, vodkaCount);

            // Удаляем все предыдущие эффекты, чтобы избежать наложения
            entity.removeEffect(MobEffects.DAMAGE_BOOST);
            entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            entity.removeEffect(MobEffects.CONFUSION);
            entity.removeEffect(MobEffects.WEAKNESS);
            entity.removeEffect(MobEffects.HUNGER);
            entity.removeEffect(MobEffects.DIG_SLOWDOWN);

            // Применяем эффекты в зависимости от количества выпитых бутылок
            if (vodkaCount == 1) {
                // 1 бутылка: Strength I (3 минуты)
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 0));
            } else if (vodkaCount == 2) {
                // 2 бутылки: Slowness I (3 минуты)
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 0));
            } else if (vodkaCount == 3) {
                // 3 бутылки: Nausea, Weakness, Hunger, Mining Fatigue (3 минуты)
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 3600, 0));
            } else if (vodkaCount >= 4) {
                // 4+ бутылок: смерть
                entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
                // Сбрасываем счётчик после смерти
                persistentData.putInt(VODKA_COUNT_KEY, 0);
            }
        }
        return super.finishUsingItem(stack, level, entity);
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
        tooltip.add(Component.literal("Effects vary based on number consumed"));
    }

    @Override
    public ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }
}