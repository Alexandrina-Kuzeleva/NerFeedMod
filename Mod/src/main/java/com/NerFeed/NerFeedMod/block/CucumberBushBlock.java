package com.NerFeed.NerFeedMod.block;

import com.NerFeed.NerFeedMod.ModItems;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;

public class CucumberBushBlock extends CropBlock {
    public CucumberBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.CUCUMBER_SEEDS.get(); 
    }
}