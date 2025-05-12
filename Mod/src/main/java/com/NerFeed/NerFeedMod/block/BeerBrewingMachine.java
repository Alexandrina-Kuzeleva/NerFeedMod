package com.NerFeed.NerFeedMod.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BeerBrewingMachine extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public BeerBrewingMachine() {
        super(BlockBehaviour.Properties.of()
            .strength(1.5F, 6.0F)
            .requiresCorrectToolForDrops()
        ); 
    }
}