package com.samplecat.atlantisorigins.common.menu;

import com.samplecat.atlantisorigins.common.blockentity.AlchemicalReactorBlockEntity;

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

public class AlchemicalReactorMenu extends AbstractContainerMenu {

    public static final int INPUT_SLOT_X = 44;
    public static final int INPUT_SLOT_Y = 17;
    public static final int OUTPUT_SLOT_X = 116;
    public static final int OUTPUT_SLOT_Y = 35;

    private static final int INPUT_OFFSET_X = 18;
    private static final int INPUT_OFFSET_Y = 18;

    private final IItemHandler itemHandler;
    private final ContainerData data;
    private final ContainerLevelAccess access;

    public AlchemicalReactorMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, new ItemStackHandler(AlchemicalReactorBlockEntity.INVENTORY_SIZE), new SimpleContainerData(2), ContainerLevelAccess.NULL);
    }

    public AlchemicalReactorMenu(int containerId, Inventory playerInventory, IItemHandler itemHandler, ContainerData data, ContainerLevelAccess access) {
        super(ModMenus.ALCHEMICAL_REACTOR.get(), containerId);
        this.itemHandler = itemHandler;
        this.data = data;
        this.access = access;

        checkContainerDataCount(data, 2);

        // Input slots in staggered 2x2 layout:
        //   A B
        //
        // C   D
        // where B aligns with D and C is one slot before A, with a one-slot gap between rows.
        addSlot(new SlotItemHandler(itemHandler, AlchemicalReactorBlockEntity.SLOT_INPUT_0, INPUT_SLOT_X, INPUT_SLOT_Y));
        addSlot(new SlotItemHandler(itemHandler, AlchemicalReactorBlockEntity.SLOT_INPUT_1, INPUT_SLOT_X + INPUT_OFFSET_X, INPUT_SLOT_Y));
        addSlot(new SlotItemHandler(itemHandler, AlchemicalReactorBlockEntity.SLOT_INPUT_2, INPUT_SLOT_X - INPUT_OFFSET_X, INPUT_SLOT_Y + INPUT_OFFSET_Y * 2));
        addSlot(new SlotItemHandler(itemHandler, AlchemicalReactorBlockEntity.SLOT_INPUT_3, INPUT_SLOT_X + INPUT_OFFSET_X, INPUT_SLOT_Y + INPUT_OFFSET_Y * 2));

        // Output slot
        addSlot(new SlotItemHandler(itemHandler, AlchemicalReactorBlockEntity.SLOT_OUTPUT, OUTPUT_SLOT_X, OUTPUT_SLOT_Y) {
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

    public int getCookProgress() {
        int total = data.get(1);
        if (total <= 0) return 0;
        return data.get(0) * 24 / total;
    }

    public int getCookTime() {
        return data.get(0);
    }

    public int getCookTimeTotal() {
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

        if (index < AlchemicalReactorBlockEntity.INVENTORY_SIZE) {
            if (!moveItemStackTo(original, AlchemicalReactorBlockEntity.INVENTORY_SIZE, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            if (!moveItemStackTo(original, 0, AlchemicalReactorBlockEntity.INVENTORY_SIZE, false)) {
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
        return AbstractContainerMenu.stillValid(access, player, com.samplecat.atlantisorigins.common.block.ModBlocks.ALCHEMICAL_REACTOR.get());
    }
}
