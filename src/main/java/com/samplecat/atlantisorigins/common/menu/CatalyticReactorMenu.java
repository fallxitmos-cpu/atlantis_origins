package com.samplecat.atlantisorigins.common.menu;

import com.samplecat.atlantisorigins.common.blockentity.CatalyticReactorBlockEntity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CatalyticReactorMenu extends AbstractContainerMenu {

    // Main inputs A B C D
    public static final int INPUT_SLOT_X = 35;
    public static final int INPUT_SLOT_Y = 20;
    public static final int INPUT_OFFSET_X = 18;

    // Catalysts E F
    public static final int CATALYST_SLOT_X = 35;
    public static final int CATALYST_SLOT_Y = 44;

    // Fuel G H
    public static final int FUEL_SLOT_X = 35;
    public static final int FUEL_SLOT_Y = 68;

    // Output I
    public static final int OUTPUT_SLOT_X = 125;
    public static final int OUTPUT_SLOT_Y = 55;

    private final IItemHandler itemHandler;
    private final ContainerData data;
    private final ContainerLevelAccess access;

    public CatalyticReactorMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, new ItemStackHandler(CatalyticReactorBlockEntity.INVENTORY_SIZE), new SimpleContainerData(2), ContainerLevelAccess.NULL);
    }

    public CatalyticReactorMenu(int containerId, Inventory playerInventory, IItemHandler itemHandler, ContainerData data, ContainerLevelAccess access) {
        super(ModMenus.CATALYTIC_REACTOR.get(), containerId);
        this.itemHandler = itemHandler;
        this.data = data;
        this.access = access;

        checkContainerDataCount(data, 2);

        // Main inputs A-D (slots 0-3)
        for (int i = 0; i < 4; i++) {
            addSlot(new SlotItemHandler(itemHandler, CatalyticReactorBlockEntity.SLOT_INPUT_0 + i,
                    INPUT_SLOT_X + i * INPUT_OFFSET_X, INPUT_SLOT_Y));
        }

        // Catalysts E-F (slots 4-5)
        for (int i = 0; i < 2; i++) {
            addSlot(new SlotItemHandler(itemHandler, CatalyticReactorBlockEntity.SLOT_CATALYST_0 + i,
                    CATALYST_SLOT_X + i * INPUT_OFFSET_X, CATALYST_SLOT_Y));
        }

        // Fuel G-H (slots 6-7)
        for (int i = 0; i < 2; i++) {
            addSlot(new SlotItemHandler(itemHandler, CatalyticReactorBlockEntity.SLOT_FUEL_0 + i,
                    FUEL_SLOT_X + i * INPUT_OFFSET_X, FUEL_SLOT_Y));
        }

        // Output I (slot 8)
        addSlot(new SlotItemHandler(itemHandler, CatalyticReactorBlockEntity.SLOT_OUTPUT, OUTPUT_SLOT_X, OUTPUT_SLOT_Y) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        addDataSlots(data);
    }

    public int getProcessProgress() {
        int total = data.get(1);
        if (total <= 0) return 0;
        return data.get(0) * 24 / total;
    }

    public int getProcessTime() {
        return data.get(0);
    }

    public int getProcessTimeTotal() {
        return data.get(1);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack original = slot.getItem();
        ItemStack copy = original.copy();

        if (index < CatalyticReactorBlockEntity.INVENTORY_SIZE) {
            // From machine to player inventory
            if (!moveItemStackTo(original, CatalyticReactorBlockEntity.INVENTORY_SIZE, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // From player to machine; skip output slot
            if (!moveItemStackTo(original, 0, CatalyticReactorBlockEntity.SLOT_OUTPUT, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (original.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        slot.onTake(player, original);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(access, player, com.samplecat.atlantisorigins.common.block.ModBlocks.CATALYTIC_REACTOR.get());
    }
}
