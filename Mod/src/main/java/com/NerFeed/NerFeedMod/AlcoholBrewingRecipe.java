package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.potion.ModPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class AlcoholBrewingRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        return PotionUtils.getPotion(input) == Potions.WATER && input.getItem() == Items.POTION;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == Items.WHEAT;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.ALCOHOL.get());
        }
        return ItemStack.EMPTY;
    }
}