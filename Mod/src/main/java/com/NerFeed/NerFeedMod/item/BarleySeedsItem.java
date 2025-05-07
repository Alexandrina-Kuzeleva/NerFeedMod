package com.NerFeed.NerFeedMod.item;

import com.NerFeed.NerFeedMod.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class BarleySeedsItem extends Item {
    public BarleySeedsItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Механика посадки на farmland (как у пшеницы)
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        if (level.getBlockState(pos).getBlock() == Blocks.FARMLAND && level.isEmptyBlock(pos.above())) {
            level.setBlock(pos.above(), ModBlocks.BARLEY_CROP.get().defaultBlockState(), 3);
            context.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}