package com.NerFeed.NerFeedMod.entity;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class AlcoholicEntity extends Zombie {
    public AlcoholicEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    
        // Устанавливаем предмет в любом случае
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.VODKA_POTION.get()));
    }
    

    public static AttributeSupplier.Builder createAttributes() {
        return Zombie.createAttributes()
                .add(Attributes.MAX_HEALTH, 8.0) // 4 сердца = 8 единиц здоровья
                .add(Attributes.MOVEMENT_SPEED, 0.23F) // Скорость как у зомби
                .add(Attributes.ATTACK_DAMAGE, 3.0); // Урон как у зомби
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        if (random.nextFloat() < 1.0F) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.VODKA_POTION.get()));
        }
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }


    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (this.random.nextFloat() < 1.0F) { // 100% шанс дропа
            this.spawnAtLocation(ModItems.VODKA_POTION.get()); // Замени на твой предмет водки
        }
    }

    // Предполагаем, что у тебя есть кастомный предмет "vodka_potion"
    public static class ModItems {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "nerfeedmod");
        public static final RegistryObject<Item> VODKA_POTION = ITEMS.register("vodka_potion", () -> new Item(new Item.Properties()));
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor level, MobSpawnType spawnType) {
        // Разрешаем спавн через яйцо или команду в любых условиях
        if (spawnType == MobSpawnType.SPAWN_EGG || spawnType == MobSpawnType.COMMAND) {
            return true;
        }
        
        // Для естественного спавна - только ночью/под землей
        long time = level.getLevelData().getDayTime() % 24000;
        boolean isNight = time >= 13000 && time <= 23000;
        boolean isUnderground = !level.canSeeSky(blockPosition());
        
        return (isNight || isUnderground) && 
               level.getBrightness(LightLayer.BLOCK, blockPosition()) <= 7;
    }
}