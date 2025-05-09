package com.NerFeed.NerFeedMod.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class CucumberItem extends Item { 
    public CucumberItem(Properties properties) {
        super(new Properties()
            .food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.6F)
                .build())
            .stacksTo(64)
        ); 
    }
}