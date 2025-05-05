package com.NerFeed.NerFeedMod.item;

import com.NerFeed.NerFeedMod.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class VinoPotionItem extends PotionItem {
    private static final String WINE_COUNT_KEY = "WineCount";
    private static final String LAST_WINE_TIME_KEY = "LastWineTime";
    private static final int METABOLISM_RATE = 1200; // 1 минута в тиках

    public VinoPotionItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            CompoundTag data = entity.getPersistentData();
            long currentTime = level.getGameTime();

            if (currentTime - data.getLong(LAST_WINE_TIME_KEY) > METABOLISM_RATE * 2) {
                data.putInt(WINE_COUNT_KEY, 0); // сброс, если прошло много времени
            }

            int wineCount = data.getInt(WINE_COUNT_KEY) + 1;
            data.putInt(WINE_COUNT_KEY, wineCount);
            data.putLong(LAST_WINE_TIME_KEY, currentTime);

            applyWineEffects(entity, wineCount);
        }

        return super.finishUsingItem(stack, level, entity);
    }

    public static void handleMetabolismTick(LivingEntity entity) {
        if (!entity.level().isClientSide) {
            CompoundTag data = entity.getPersistentData();
            long currentTime = entity.level().getGameTime();

            if (currentTime - data.getLong(LAST_WINE_TIME_KEY) >= METABOLISM_RATE) {
                int wineLevel = data.getInt(WINE_COUNT_KEY);
                if (wineLevel > 0) {
                    wineLevel--;
                    data.putInt(WINE_COUNT_KEY, wineLevel);
                    data.putLong(LAST_WINE_TIME_KEY, currentTime);
                    applyWineEffects(entity, wineLevel);
                }
            }
        }
    }

    private static void applyWineEffects(LivingEntity entity, int wineLevel) {
        // Удаляем только эффекты вина, чтобы не затронуть другие
        for (MobEffect effect : List.of(
                MobEffects.MOVEMENT_SPEED, MobEffects.DIG_SPEED, MobEffects.DAMAGE_BOOST,
                MobEffects.CONFUSION, MobEffects.REGENERATION, MobEffects.BLINDNESS,
                MobEffects.WEAKNESS, MobEffects.HUNGER, MobEffects.POISON)) {
            entity.removeEffect(effect);
        }

        switch (wineLevel) {
            case 1 -> {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 0));
            }
            case 2 -> {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 0));
            }
            case 3 -> {
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
            }
            case 4 -> {
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 2));
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3600, 0));
            }
            case 5 -> { 
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 2));
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 3));
                entity.hurt(entity.damageSources().magic(), 4.0F);
            }
            default -> {
                entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
                entity.getPersistentData().putInt(WINE_COUNT_KEY, 0);
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
                level.setBlock(placePos, ModBlocks.VINO_BOTTLE_BLOCK.get().defaultBlockState(), 3);
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
        return "item.nerfeedmod.vino_potion";
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
