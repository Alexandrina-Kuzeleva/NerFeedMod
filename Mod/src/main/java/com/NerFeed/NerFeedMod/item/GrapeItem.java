package com.NerFeed.NerFeedMod.item;

import com.NerFeed.NerFeedMod.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class GrapeItem extends Item {
    public GrapeItem() {
        super(new Properties()
            .food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .build())
            .stacksTo(64)
        );
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        
        // Проверяем, можно ли поставить куст на этот блок
        if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.FARMLAND)) {
            // Ставим куст винограда на верхний блок
            level.setBlock(pos.above(), ModBlocks.GRAPE_BUSH.get().defaultBlockState(), 3);
            
            // Уменьшаем стек предмета (если не в творческом режиме)
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getItemInHand().shrink(1);
            }
            
            // Проигрываем звук посадки
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        
        return super.useOn(context);
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
}