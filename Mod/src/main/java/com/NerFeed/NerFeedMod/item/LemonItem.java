package com.NerFeed.NerFeedMod.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class LemonItem extends Item {
    public LemonItem() {
        super(new Properties()
            .food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.3F)
                .build())
            .stacksTo(64)
        );
    }
}