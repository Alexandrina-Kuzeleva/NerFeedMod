package com.NerFeed.NerFeedMod.item;

import java.util.List;

import com.NerFeed.NerFeedMod.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BeerItem extends PotionItem {
    public BeerItem(Item.Properties properties) {
        super(properties);
    }

    private static final String BEER_COUNT_KEY = "BeerCount";
    private static final String LAST_BEER_TIME_KEY = "LastBeerTime";
    private static final int METABOLISM_RATE = 1200; // 1 минута в тиках

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            CompoundTag data = entity.getPersistentData();
            long currentTime = level.getGameTime();

            if (currentTime - data.getLong(LAST_BEER_TIME_KEY) > METABOLISM_RATE * 2) {
                data.putInt(BEER_COUNT_KEY, 0); // сброс, если прошло много времени
            }

            int beerCount = data.getInt(BEER_COUNT_KEY) + 1;
            data.putInt(BEER_COUNT_KEY, beerCount);
            data.putLong(LAST_BEER_TIME_KEY, currentTime);

            applyWineEffects(entity, beerCount);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    public static void handleMetabolismTick(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            CompoundTag data = entity.getPersistentData();
            long currentTime = entity.level().getGameTime();

            if (currentTime - data.getLong(LAST_BEER_TIME_KEY) >= METABOLISM_RATE) {
                int beerLevel = data.getInt(BEER_COUNT_KEY);
                if (beerLevel > 0) {
                    beerLevel--;
                    data.putInt(BEER_COUNT_KEY, beerLevel);
                    data.putLong(LAST_BEER_TIME_KEY, currentTime);
                    applyWineEffects(entity, beerLevel);
                }
            }
        }
    }

    private static void applyWineEffects(LivingEntity entity, int beerLevel) {

        for (MobEffect effect : List.of(
                MobEffects.MOVEMENT_SPEED, MobEffects.REGENERATION, MobEffects.DAMAGE_RESISTANCE,
                MobEffects.CONFUSION, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.BLINDNESS,
                MobEffects.WEAKNESS, MobEffects.HUNGER, MobEffects.POISON)) {
            entity.removeEffect(effect);
        }

        switch (beerLevel) {
            case 1 -> {
                // Легкое расслабление: небольшое ускорение и восстановление
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 0)); // Скорость I, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1200, 0)); // Регенерация I, 1 минута
            }
            case 2 -> {
                // Умеренное опьянение: защита и легкая тошнота
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0)); // Сопротивление урону I, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0)); // Тошнота I, 30 секунд
            }
            case 3 -> {
                // Начало проблем: регенерация, но с замедлением и тошнотой
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 1800, 0)); // Регенерация I, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 1800, 0)); // Замедление I, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 900, 0)); // Тошнота I, 45 секунд
            }
            case 4 -> {
                // Сильное опьянение: слабость и сильная тошнота
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 0)); // Слабость I, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1200, 1)); // Тошнота II, 1 минута
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 1)); // Замедление II, 3 минуты
            }
            case 5 -> {
                // Тяжелое опьянение: слабость, отравление, слепота
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 1)); // Слабость II, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 0)); // Отравление I, 30 секунд
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800, 1)); // Тошнота II, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 0)); // Слепота I, 30 секунд
            }
            case 6 -> {
                // Критическое опьянение: сильное отравление и урон
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 900, 1)); // Отравление II, 45 секунд
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 2)); // Слабость III, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800, 2)); // Тошнота III, 1.5 минуты
                entity.hurt(entity.damageSources().magic(), 2.0F); // Урон 1 сердце
            }
            case 7 -> {
                // Очень критическое: добавляем голод и слепоту
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 900, 1)); // Отравление II, 45 секунд
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 2)); // Слабость III, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800, 2)); // Тошнота III, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1800, 1)); // Голод II, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 900, 0)); // Слепота I, 45 секунд
                entity.hurt(entity.damageSources().magic(), 3.0F); // Урон 1.5 сердца
            }
            case 8 -> {
                // Предсмертное состояние: сильный урон
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 1)); // Отравление II, 1 минута
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 3)); // Слабость IV, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800, 3)); // Тошнота IV, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 1800, 2)); // Голод III, 1.5 минуты
                entity.hurt(entity.damageSources().magic(), 5.0F); // Урон 2.5 сердца
            }
            case 9 -> {
                // Последний шанс: максимальный урон
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 1200, 2)); // Отравление III, 1 минута
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 3)); // Слабость IV, 3 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1800, 3)); // Тошнота IV, 1.5 минуты
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200, 1)); // Слепота II, 1 минута
                entity.hurt(entity.damageSources().magic(), 8.0F); // Урон 4 сердца
            }
            default -> {
                // Смерть от передозировки
                entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
                entity.getPersistentData().putInt(BEER_COUNT_KEY, 0);
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos placePos = pos.relative(context.getClickedFace());
        ItemStack stack = context.getItemInHand();

        if (level.isEmptyBlock(placePos)) {
            if (!level.isClientSide) {
                level.setBlock(placePos, ModBlocks.MUG_OF_BEER.get().defaultBlockState(), 3);
                if (!context.getPlayer().isCreative()) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }

    @Override
    public String getDescriptionId() {
        return "item.nerfeedmod.beer";
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }
}