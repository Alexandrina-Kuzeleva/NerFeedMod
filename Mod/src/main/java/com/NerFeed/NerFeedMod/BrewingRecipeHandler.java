package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.potion.ModPotions;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BrewingRecipeHandler implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        Potion potion = PotionUtils.getPotion(input);
        return potion == ModPotions.ALCOHOL.get() || input.getItem() == ModItems.VODKA_POTION.get();
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == Items.GUNPOWDER || ingredient.getItem() == Items.DRAGON_BREATH;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        return ItemStack.EMPTY;
    }
}