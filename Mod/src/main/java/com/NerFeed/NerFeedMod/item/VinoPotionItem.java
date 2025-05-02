package com.NerFeed.NerFeedMod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import java.util.List;

import com.NerFeed.NerFeedMod.ModBlocks;

public class VinoPotionItem extends PotionItem {
    private static final String VODKA_COUNT_KEY = "VodkaCount";

    public VinoPotionItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide) {
            CompoundTag persistentData = entity.getPersistentData();
            int vodkaCount = persistentData.getInt(VODKA_COUNT_KEY);
            vodkaCount++;
            persistentData.putInt(VODKA_COUNT_KEY, vodkaCount);

            entity.removeEffect(MobEffects.DAMAGE_BOOST);
            entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
            entity.removeEffect(MobEffects.CONFUSION);
            entity.removeEffect(MobEffects.WEAKNESS);
            entity.removeEffect(MobEffects.HUNGER);
            entity.removeEffect(MobEffects.DIG_SLOWDOWN);

            if (vodkaCount == 1) {
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 3600, 0));
            } else if (vodkaCount == 2) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 3600, 0));
            } else if (vodkaCount == 3) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 3600, 0));
                entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 3600, 0));
            } else if (vodkaCount >= 4) {
                entity.hurt(entity.damageSources().genericKill(), Float.MAX_VALUE);
                persistentData.putInt(VODKA_COUNT_KEY, 0);
            }
        }
        return super.finishUsingItem(stack, level, entity);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos placePos = pos.relative(context.getClickedFace());
        ItemStack stack = context.getItemInHand();

        // Проверяем, можно ли разместить блок
        if (level.isEmptyBlock(placePos)) {
            if (!level.isClientSide) {
                level.setBlock(placePos, ModBlocks.VINO_BOTTLE_BLOCK.get().defaultBlockState(), 3);
                if (!context.getPlayer().isCreative()) {
                    stack.shrink(1); // Уменьшаем количество предметов в руке
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