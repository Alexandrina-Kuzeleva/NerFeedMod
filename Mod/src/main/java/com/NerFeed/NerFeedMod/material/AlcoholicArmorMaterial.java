package com.NerFeed.NerFeedMod.material;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class AlcoholicArmorMaterial implements ArmorMaterial {
    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11}; // Долговечность (шлем, нагрудник, поножи, ботинки)
    private static final int[] PROTECTION_VALUES = new int[]{3, 6, 5, 2}; // Защита для каждой части

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> BASE_DURABILITY[0] * 15;
            case CHESTPLATE -> BASE_DURABILITY[1] * 15;
            case LEGGINGS -> BASE_DURABILITY[2] * 15;
            case BOOTS -> BASE_DURABILITY[3] * 15;
        };
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> PROTECTION_VALUES[0];
            case CHESTPLATE -> PROTECTION_VALUES[1];
            case LEGGINGS -> PROTECTION_VALUES[2];
            case BOOTS -> PROTECTION_VALUES[3];
        };
    }

    @Override
    public int getEnchantmentValue() {
        return 10;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.DIAMOND);
    }

    @Override
    public String getName() {
        return "nerfeedmod:alcoholic_armor"; // Должно совпадать с префиксом файлов текстур
    }

    @Override
    public float getToughness() {
        return 2.0F;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F;
    }
}