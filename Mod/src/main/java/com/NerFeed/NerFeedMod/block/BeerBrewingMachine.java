package com.NerFeed.NerFeedMod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BeerBrewingMachine extends Block {
    public BeerBrewingMachine() {
        super(BlockBehaviour.Properties.of()
            .strength(1.5F, 6.0F)
            .requiresCorrectToolForDrops()
        ); 
    }
}