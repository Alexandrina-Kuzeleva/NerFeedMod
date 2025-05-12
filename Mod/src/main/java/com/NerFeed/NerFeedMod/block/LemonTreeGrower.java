package com.NerFeed.NerFeedMod.block;

import com.NerFeed.NerFeedMod.worldgen.ModConfiguredFeatures;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;

public class LemonTreeGrower extends AbstractTreeGrower {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected ResourceKey getConfiguredFeature(RandomSource random, boolean largeHive) {
        return (ResourceKey) ModConfiguredFeatures.LEMON_TREE.getHolder().orElseThrow(() 
        -> new IllegalStateException("Lemon tree feature not registered"));
    }
}