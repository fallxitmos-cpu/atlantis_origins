package com.samplecat.atlantisorigins.common.blockentity;

import java.util.List;
import java.util.Optional;

import com.samplecat.atlantisorigins.common.menu.SeparationTowerMenu;
import com.samplecat.atlantisorigins.common.menu.recipe.ModRecipes;
import com.samplecat.atlantisorigins.common.menu.recipe.SeparationTowerRecipe;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SeparationTowerBlockEntity extends BlockEntity {

    public static final int SLOT_INPUT = 0;
    public static final int SLOT_OUTPUT_0 = 1;
    public static final int SLOT_OUTPUT_1 = 2;
    public static final int SLOT_OUTPUT_2 = 3;
    public static final int INVENTORY_SIZE = 4;

    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == SLOT_INPUT;
        }
    };

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> processTime;
                case 1 -> processTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> processTime = value;
                case 1 -> processTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    private int processTime = 0;
    private int processTimeTotal = 200;

    public SeparationTowerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.SEPARATION_TOWER.get(), pos, state);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public ContainerData getData() {
        return data;
    }

    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        return new SeparationTowerMenu(containerId, playerInventory, itemHandler, data, access);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        processTime = tag.getInt("ProcessTime");
        processTimeTotal = tag.getInt("ProcessTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.putInt("ProcessTime", processTime);
        tag.putInt("ProcessTimeTotal", processTimeTotal);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SeparationTowerBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        Optional<RecipeHolder<SeparationTowerRecipe>> recipe = entity.getMatchingRecipe();
        boolean canProcess = recipe.isPresent() && entity.canCraft(recipe.get().value());

        if (canProcess) {
            entity.processTimeTotal = recipe.get().value().processTime;
            entity.processTime++;
            if (entity.processTime >= entity.processTimeTotal) {
                entity.craft(recipe.get().value());
                entity.processTime = 0;
            }
            entity.setChanged();
        } else {
            if (entity.processTime > 0) {
                entity.processTime = 0;
                entity.setChanged();
            }
        }
    }

    private Optional<RecipeHolder<SeparationTowerRecipe>> getMatchingRecipe() {
        if (level == null) return Optional.empty();
        return level.getRecipeManager().getRecipeFor(ModRecipes.SEPARATION_TOWER_TYPE.get(), new SimpleItemHandlerRecipeInput(itemHandler), level);
    }

    private boolean canCraft(SeparationTowerRecipe recipe) {
        List<ItemStack> results = recipe.outputs;
        if (results.isEmpty()) return false;

        int outputSlots = INVENTORY_SIZE - 1;
        for (int i = 0; i < results.size() && i < outputSlots; i++) {
            ItemStack result = results.get(i);
            ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT_0 + i);
            if (result.isEmpty()) continue;
            if (!output.isEmpty() && (!ItemStack.isSameItemSameComponents(output, result) || output.getCount() + result.getCount() > output.getMaxStackSize())) {
                return false;
            }
        }
        return true;
    }

    private void craft(SeparationTowerRecipe recipe) {
        ItemStack input = itemHandler.getStackInSlot(SLOT_INPUT);
        if (!input.isEmpty()) {
            input.shrink(1);
        }

        int outputSlots = INVENTORY_SIZE - 1;
        for (int i = 0; i < recipe.outputs.size() && i < outputSlots; i++) {
            ItemStack result = recipe.outputs.get(i).copy();
            if (result.isEmpty()) continue;
            ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT_0 + i);
            if (output.isEmpty()) {
                itemHandler.setStackInSlot(SLOT_OUTPUT_0 + i, result);
            } else {
                output.grow(result.getCount());
            }
        }
    }
}
