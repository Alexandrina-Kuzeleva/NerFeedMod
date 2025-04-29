package com.NerFeed.NerFeedMod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class LightCube extends Block {
    public LightCube() {
        super(BlockBehaviour.Properties.of()
            .strength(1.5F, 6.0F)
            .lightLevel(state -> 15)
            .requiresCorrectToolForDrops()
        ); 
    }
}