package com.NerFeed.NerFeedMod;

import com.NerFeed.NerFeedMod.block.entity.DryingTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);

    public static final RegistryObject<BlockEntityType<DryingTableBlockEntity>> DRYING_TABLE =
            BLOCK_ENTITIES.register("drying_table",
                    () -> BlockEntityType.Builder.of(DryingTableBlockEntity::new, ModBlocks.DRYING_TABLE.get()).build(null));
}