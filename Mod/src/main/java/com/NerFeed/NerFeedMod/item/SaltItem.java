package com.NerFeed.NerFeedMod.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class SaltItem extends Item {
    public SaltItem(Properties properties) {
        super(new Properties()
        .food(new FoodProperties.Builder()
            .nutrition(1)
            .saturationMod(0.1F)
            .build())
        .stacksTo(64)
        );
    }
}
