package com.NerFeed.NerFeedMod.block;

import com.NerFeed.NerFeedMod.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class GrapeBushBlock extends SweetBerryBushBlock {
    public GrapeBushBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH).noOcclusion());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return belowState.is(Blocks.GRASS_BLOCK) || belowState.is(Blocks.DIRT) || belowState.is(Blocks.FARMLAND);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && state.getValue(AGE) > 0) {
            entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75D, 0.8F)); // Легкое замедление без урона
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.GRAPE.get());
    }

    public void performBonemeal(Level level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age < 3) {
            level.setBlock(pos, state.setValue(AGE, age + 1), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int age = state.getValue(AGE);
        boolean fullyGrown = age == 3;

        if (!fullyGrown && player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS; // позволяем костной муке сработать
        }

        if (fullyGrown) {
            int count = 1 + level.random.nextInt(2); // 1–2 виноградины
            popResource(level, pos, new ItemStack(ModItems.GRAPE.get(), count));
            level.setBlock(pos, state.setValue(AGE, 1), 2); // куст "обновляется"
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> drops = new ArrayList<>();
        
        if (state.getValue(AGE) >= 3) { // Проверяем зрелость куста
            RandomSource random = builder.getLevel().getRandom();
            int count = 1 + random.nextInt(3); // 1-3 виноградин
            drops.add(new ItemStack(ModItems.GRAPE.get(), count));
        }
        
        return drops;
    }
}