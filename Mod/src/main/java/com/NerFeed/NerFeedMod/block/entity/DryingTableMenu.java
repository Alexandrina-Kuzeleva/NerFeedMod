package com.NerFeed.NerFeedMod.block.entity;

import com.NerFeed.NerFeedMod.ModBlocks;
import com.NerFeed.NerFeedMod.ModMenus;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class DryingTableMenu extends AbstractContainerMenu {
    private final DryingTableBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    public DryingTableMenu(int id, Inventory playerInventory, FriendlyByteBuf data) {
        this(id, playerInventory, getBlockEntity(playerInventory, data.readBlockPos()));
    }

    public DryingTableMenu(int id, Inventory playerInventory, DryingTableBlockEntity blockEntity) {
        super(ModMenus.DRYING_TABLE.get(), id);
        this.blockEntity = blockEntity;
        this.levelAccess = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Слот ввода (слева)
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 0, 56, 35)); // Координаты: x=56, y=35

        // Слот вывода (справа)
        addSlot(new SlotItemHandler(blockEntity.getInventory(), 1, 116, 35) { // Координаты: x=116, y=35
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false; // Выходной слот только для извлечения
            }
        });

        // Инвентарь игрока (3 ряда по 9 слотов)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Хотбар игрока (1 ряд, 9 слотов)
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }
    }

    private static DryingTableBlockEntity getBlockEntity(Inventory playerInventory, BlockPos pos) {
        BlockEntity be = playerInventory.player.level().getBlockEntity(pos);
        if (be instanceof DryingTableBlockEntity) {
            return (DryingTableBlockEntity) be;
        }
        throw new IllegalStateException("Block entity at " + pos + " is not a DryingTableBlockEntity!");
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(levelAccess, player, ModBlocks.DRYING_TABLE.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            stack = originalStack.copy();

            if (index < 2) { // Слоты сушильного стола (ввод и вывод)
                if (!moveItemStackTo(originalStack, 2, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // Слоты инвентаря игрока
                if (!moveItemStackTo(originalStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return stack;
    }

    // Методы для синхронизации прогресса с клиентом
    public int getProgress() {
        return blockEntity.getProgress();
    }

    public int getMaxProgress() {
        return DryingTableBlockEntity.getMaxProgress();
    }
}