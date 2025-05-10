package com.NerFeed.NerFeedMod.item;

import com.NerFeed.NerFeedMod.ModBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class BeerBrewingMachineItem extends Item {
    public BeerBrewingMachineItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos placePos = pos.relative(context.getClickedFace());
        ItemStack stack = context.getItemInHand();

        if (level.isEmptyBlock(placePos)) {
            if (!level.isClientSide) {
                level.setBlock(placePos, ModBlocks.BEER_BREWING_MACHINE.get().defaultBlockState(), 3);
                if (!context.getPlayer().isCreative()) {
                    stack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }

}