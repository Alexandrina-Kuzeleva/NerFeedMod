package com.NerFeed.NerFeedMod.block.entity;

import com.NerFeed.NerFeedMod.ModBlockEntities;
import com.NerFeed.NerFeedMod.ModItems;
import com.NerFeed.NerFeedMod.block.DryingTableBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.Containers;
import net.minecraftforge.items.ItemStackHandler;

public class DryingTableBlockEntity extends BaseContainerBlockEntity {
    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private final ItemStackHandler inventory = new ItemStackHandler(2);
    private int progress = 0;
    private static final int MAX_PROGRESS = 200; // 10 секунд (200 тиков), можно увеличить до 24000 для 1 игрового дня

    public DryingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DRYING_TABLE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.nerfeedmod.drying_table");
    }

    @Override
    public Component getDisplayName() {
        return getDefaultName();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory playerInventory) {
        return new DryingTableMenu(id, playerInventory, this);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean isEmpty() {
        return inventory.getStackInSlot(INPUT_SLOT).isEmpty() && inventory.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return inventory.extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return inventory.extractItem(slot, inventory.getStackInSlot(slot).getCount(), false);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        inventory.setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.level != null && this.level.getBlockEntity(this.worldPosition) == this
                && player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        for (int i = 0; i < getContainerSize(); i++) {
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void dropItems() {
        if (this.level != null) {
            Containers.dropContents(this.level, this.worldPosition, this);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Inventory", inventory.serializeNBT());
        tag.putInt("Progress", progress);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        progress = tag.getInt("Progress");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, DryingTableBlockEntity blockEntity) {
        // Проверяем наличие редстоун-сигнала
        boolean isPowered = state.getValue(DryingTableBlock.ACTIVE);
        if (!isPowered || !blockEntity.canProcess()) {
            blockEntity.progress = 0;
            return;
        }

        blockEntity.progress++;
        if (blockEntity.progress >= MAX_PROGRESS) {
            blockEntity.processItem();
            blockEntity.progress = 0;
        }
        blockEntity.setChanged();
    }

    private boolean canProcess() {
        ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);

        if (input.isEmpty()) return false;

        ItemStack result = getResult(input);
        if (result.isEmpty()) return false;

        if (output.isEmpty()) return true;
        return output.getItem() == result.getItem() && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private ItemStack getResult(ItemStack input) {
        if (input.getItem() == ModItems.BARLEY.get()) {
            return new ItemStack(ModItems.DRIED_BARLEY.get(), 1);
        }
        return ItemStack.EMPTY;
    }

    private void processItem() {
        ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
        ItemStack output = inventory.getStackInSlot(OUTPUT_SLOT);
        ItemStack result = getResult(input);

        if (!result.isEmpty()) {
            if (output.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT, result.copy());
            } else {
                output.grow(result.getCount());
            }
            input.shrink(1);
        }
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public int getProgress() {
        return progress;
    }

    public static int getMaxProgress() {
        return MAX_PROGRESS;
    }
}