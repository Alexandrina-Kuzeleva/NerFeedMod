package com.NerFeed.NerFeedMod.block;

import com.NerFeed.NerFeedMod.ModItems;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

// BarleyCropBlock.java
public class BarleyCropBlock extends CropBlock {
    public BarleyCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.BARLEY_SEEDS.get(); // Указываем семена для посадки
    }
}