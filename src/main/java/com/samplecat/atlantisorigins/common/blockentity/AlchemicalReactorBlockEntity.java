package com.samplecat.atlantisorigins.common.blockentity;

import com.samplecat.atlantisorigins.common.menu.AlchemicalReactorMenu;
import com.samplecat.atlantisorigins.common.menu.recipe.AlchemicalReactorRecipe;
import com.samplecat.atlantisorigins.common.menu.recipe.ModRecipes;

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
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.Optional;

public class AlchemicalReactorBlockEntity extends BlockEntity {

    public static final int SLOT_INPUT_0 = 0;
    public static final int SLOT_INPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_INPUT_3 = 3;
    public static final int SLOT_OUTPUT = 4;
    public static final int INVENTORY_SIZE = 5;

    public static final int COOK_TIME_TOTAL = 100;

    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot != SLOT_OUTPUT;
        }
    };

    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> cookTime;
                case 1 -> cookTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> cookTime = value;
                case 1 -> cookTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };

    private int cookTime = 0;
    private int cookTimeTotal = COOK_TIME_TOTAL;

    public AlchemicalReactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ALCHEMICAL_REACTOR.get(), pos, state);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public ContainerData getData() {
        return data;
    }

    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        return new AlchemicalReactorMenu(containerId, playerInventory, itemHandler, data, access);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        cookTime = tag.getInt("CookTime");
        cookTimeTotal = tag.getInt("CookTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.putInt("CookTime", cookTime);
        tag.putInt("CookTimeTotal", cookTimeTotal);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, AlchemicalReactorBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        Optional<RecipeHolder<AlchemicalReactorRecipe>> recipe = entity.getMatchingRecipe();
        boolean canCook = recipe.isPresent() && entity.canCraft(recipe.get().value());

        if (canCook) {
            entity.cookTime++;
            if (entity.cookTime >= entity.cookTimeTotal) {
                entity.craft(recipe.get().value());
                entity.cookTime = 0;
            }
            entity.setChanged();
        } else {
            if (entity.cookTime > 0) {
                entity.cookTime = 0;
                entity.setChanged();
            }
        }
    }

    private Optional<RecipeHolder<AlchemicalReactorRecipe>> getMatchingRecipe() {
        if (level == null) return Optional.empty();
        return level.getRecipeManager().getRecipeFor(ModRecipes.ALCHEMICAL_REACTOR_TYPE.get(), new SimpleItemHandlerRecipeInput(itemHandler), level);
    }

    private boolean canCraft(AlchemicalReactorRecipe recipe) {
        ItemStack result = recipe.getResultItem(level.registryAccess());
        ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT);
        if (result.isEmpty()) return false;
        if (output.isEmpty()) return true;
        return ItemStack.isSameItemSameComponents(output, result) && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void craft(AlchemicalReactorRecipe recipe) {
        for (int i = SLOT_INPUT_0; i <= SLOT_INPUT_3; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }
        }
        ItemStack result = recipe.getResultItem(level.registryAccess()).copy();
        ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT);
        if (output.isEmpty()) {
            itemHandler.setStackInSlot(SLOT_OUTPUT, result);
        } else {
            output.grow(result.getCount());
        }
    }

    private record SimpleItemHandlerRecipeInput(ItemStackHandler handler) implements RecipeInput {
        @Override
        public int size() {
            // Only the four input slots participate in recipe matching.
            return SLOT_OUTPUT;
        }

        @Override
        public ItemStack getItem(int slot) {
            return handler.getStackInSlot(slot);
        }
    }
}
