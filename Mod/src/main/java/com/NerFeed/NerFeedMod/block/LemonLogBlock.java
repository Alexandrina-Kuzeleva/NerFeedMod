package com.NerFeed.NerFeedMod.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class LemonLogBlock extends RotatedPillarBlock {
    public LemonLogBlock() {
        super(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW) // Цвет для карты
            .strength(2.0F) // Прочность и время разрушения
            .sound(SoundType.WOOD)); // Звук дерева
    }
}