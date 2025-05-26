package com.NerFeed.NerFeedMod;

import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import javax.annotation.Nullable;

public class EdgeGrateBlock extends IronBarsBlock {
    public EdgeGrateBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction facing = context.getHorizontalDirection();
        
        BlockState state = super.getStateForPlacement(context);
        if (state == null) return null;

        boolean north = shouldConnectTo(world, pos, state, Direction.NORTH, facing);
        boolean south = shouldConnectTo(world, pos, state, Direction.SOUTH, facing);
        boolean east = shouldConnectTo(world, pos, state, Direction.EAST, facing);
        boolean west = shouldConnectTo(world, pos, state, Direction.WEST, facing);

        return state.setValue(NORTH, north)
                   .setValue(SOUTH, south)
                   .setValue(EAST, east)
                   .setValue(WEST, west);
    }

    private boolean shouldConnectTo(Level world, BlockPos pos, BlockState state, Direction dir, Direction facing) {
        BlockPos adjacentPos = pos.relative(dir);
        BlockState adjacentState = world.getBlockState(adjacentPos);
        
        if (adjacentState.getBlock() instanceof IronBarsBlock) {
            return true;
        }
        
        return dir == facing.getOpposite();
    }
}