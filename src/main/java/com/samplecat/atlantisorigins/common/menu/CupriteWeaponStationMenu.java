package com.samplecat.atlantisorigins.common.menu;

import com.samplecat.atlantisorigins.common.attachment.PlayerAttachments;
import com.samplecat.atlantisorigins.common.item.ModItems;
import com.samplecat.atlantisorigins.common.menu.recipe.CupriteWeaponStationRecipe;
import com.samplecat.atlantisorigins.common.menu.recipe.ModRecipes;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CupriteWeaponStationMenu extends AbstractContainerMenu {

    public static final int GRID_WIDTH = 5;
    public static final int GRID_HEIGHT = 5;
    public static final int GRID_SIZE = GRID_WIDTH * GRID_HEIGHT;

    public static final int GRID_START_X = 30;
    public static final int GRID_START_Y = 17;
    public static final int RESULT_SLOT_X = 143;
    public static final int RESULT_SLOT_Y = 53;

    private final SimpleCraftingContainer craftSlots;
    private final ResultContainer resultSlots = new ResultContainer();
    private final ContainerLevelAccess access;
    private final Player player;

    public CupriteWeaponStationMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public CupriteWeaponStationMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.CUPRITE_WEAPON_STATION.get(), containerId);
        this.access = access;
        this.player = playerInventory.player;
        this.craftSlots = new SimpleCraftingContainer(this, GRID_WIDTH, GRID_HEIGHT);

        // 5x5 crafting grid
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                addSlot(new Slot(craftSlots, col + row * GRID_WIDTH, GRID_START_X + col * 18, GRID_START_Y + row * 18));
            }
        }

        // Result slot
        addSlot(new ResultSlot(playerInventory.player, craftSlots, resultSlots, 0, RESULT_SLOT_X, RESULT_SLOT_Y));

        // Player inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 120 + row * 18));
            }
        }

        // Hotbar
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 178));
        }
    }

    public SimpleCraftingContainer getCraftSlots() {
        return craftSlots;
    }

    public ResultContainer getResultSlots() {
        return resultSlots;
    }

    @Override
    public void slotsChanged(Container container) {
        super.slotsChanged(container);
        if (container == craftSlots) {
            updateResult();
        }
    }

    private void updateResult() {
        access.evaluate((level, pos) -> {
            CraftingInput input = CraftingInput.of(GRID_WIDTH, GRID_HEIGHT, craftSlots.getItems());
            Optional<RecipeHolder<CupriteWeaponStationRecipe>> recipe = level.getRecipeManager().getRecipeFor(ModRecipes.CUPRITE_WEAPON_STATION_TYPE.get(), input, level);
            if (recipe.isPresent()) {
                ItemStack result = recipe.get().value().assemble(input, level.registryAccess());
                if (isOrichalcumGear(result) && !PlayerAttachments.isEvolved(player)) {
                    resultSlots.setItem(0, ItemStack.EMPTY);
                } else {
                    if (isOrichalcumGear(result)) {
                        result = result.copy();
                        CompoundTag tag = new CompoundTag();
                        tag.putBoolean("unfinished", true);
                        result.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
                    }
                    resultSlots.setItem(0, result);
                }
            } else {
                resultSlots.setItem(0, ItemStack.EMPTY);
            }
            return ItemStack.EMPTY;
        });
    }

    private static boolean isOrichalcumGear(ItemStack stack) {
        return stack.is(ModItems.ORICHALCUM_PICKAXE.get())
                || stack.is(ModItems.ORICHALCUM_AXE.get())
                || stack.is(ModItems.ORICHALCUM_SHOVEL.get())
                || stack.is(ModItems.ORICHALCUM_HOE.get())
                || stack.is(ModItems.ORICHALCUM_TRIDENT.get())
                || stack.is(ModItems.ORICHALCUM_HELMET.get())
                || stack.is(ModItems.ORICHALCUM_CHESTPLATE.get())
                || stack.is(ModItems.ORICHALCUM_LEGGINGS.get())
                || stack.is(ModItems.ORICHALCUM_BOOTS.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = slots.get(index);
        if (slot == null || !slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        ItemStack original = slot.getItem();
        ItemStack copy = original.copy();

        if (index == GRID_SIZE) {
            // Result slot
            if (!moveItemStackTo(original, GRID_SIZE + 1, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(original, copy);
        } else if (index < GRID_SIZE) {
            // Grid slot
            if (!moveItemStackTo(original, GRID_SIZE + 1, slots.size(), true)) {
                return ItemStack.EMPTY;
            }
        } else {
            // Player inventory
            if (!moveItemStackTo(original, 0, GRID_SIZE, false)) {
                return ItemStack.EMPTY;
            }
        }

        if (original.isEmpty()) {
            slot.set(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (original.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, original);
        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return AbstractContainerMenu.stillValid(access, player, com.samplecat.atlantisorigins.common.block.ModBlocks.CUPRITE_WEAPON_STATION.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        clearContainer(player, craftSlots);
    }
}
