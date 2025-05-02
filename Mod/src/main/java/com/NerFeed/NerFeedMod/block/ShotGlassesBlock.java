package com.NerFeed.NerFeedMod.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShotGlassesBlock extends Block {
    // Форма первой рюмки (4x4x4 единицы)
    private static final VoxelShape SHAPE_RUMKA_1 = Block.box(5.0D, 0.0D, 6.0D, 9.0D, 4.0D, 10.0D);
    // Форма второй рюмки с расстоянием (2 единицы по X)
    private static final VoxelShape SHAPE_RUMKA_2 = Block.box(11.0D, 0.0D, 6.0D, 15.0D, 4.0D, 10.0D);
    private static final VoxelShape SHAPE = Shapes.or(SHAPE_RUMKA_1, SHAPE_RUMKA_2);

    public ShotGlassesBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(0.3F) // Прочность как у стекла
                .sound(SoundType.GLASS)
                .noOcclusion() // Прозрачность для рендера
                .dynamicShape()); // Позволяет рендерить сложную форму
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}