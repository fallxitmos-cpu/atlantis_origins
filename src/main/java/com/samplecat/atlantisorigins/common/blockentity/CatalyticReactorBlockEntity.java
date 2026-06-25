package com.samplecat.atlantisorigins.common.blockentity;

import java.util.Optional;

import com.samplecat.atlantisorigins.common.menu.CatalyticReactorMenu;
import com.samplecat.atlantisorigins.common.menu.recipe.CatalyticReactorRecipe;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class CatalyticReactorBlockEntity extends BlockEntity {

    // A B C D = main inputs
    public static final int SLOT_INPUT_0 = 0;
    public static final int SLOT_INPUT_1 = 1;
    public static final int SLOT_INPUT_2 = 2;
    public static final int SLOT_INPUT_3 = 3;
    // E F = catalyst slots
    public static final int SLOT_CATALYST_0 = 4;
    public static final int SLOT_CATALYST_1 = 5;
    // G H = fuel slots (only blaze powder / blaze rod / lava bucket)
    public static final int SLOT_FUEL_0 = 6;
    public static final int SLOT_FUEL_1 = 7;
    // I = output
    public static final int SLOT_OUTPUT = 8;
    public static final int INVENTORY_SIZE = 9;

    private final ItemStackHandler itemHandler = new ItemStackHandler(INVENTORY_SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if (slot == SLOT_OUTPUT) {
                return false;
            }
            if (slot == SLOT_FUEL_0 || slot == SLOT_FUEL_1) {
                return stack.is(Items.BLAZE_POWDER) || stack.is(Items.BLAZE_ROD) || stack.is(Items.LAVA_BUCKET);
            }
            return true;
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

    public CatalyticReactorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.CATALYTIC_REACTOR.get(), pos, state);
    }

    public IItemHandler getItemHandler() {
        return itemHandler;
    }

    public ContainerData getData() {
        return data;
    }

    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        return new CatalyticReactorMenu(containerId, playerInventory, itemHandler, data, access);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        CompoundTag invTag = tag.getCompound("Inventory");
        // Old worlds saved this inventory with Size=5; force it to the current 9-slot layout.
        if (invTag.contains("Size", net.minecraft.nbt.Tag.TAG_INT)) {
            invTag.putInt("Size", INVENTORY_SIZE);
        }
        itemHandler.deserializeNBT(registries, invTag);
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

    public static void tick(Level level, BlockPos pos, BlockState state, CatalyticReactorBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

        Optional<RecipeHolder<CatalyticReactorRecipe>> recipe = entity.getMatchingRecipe();
        boolean hasFuel = entity.hasFuel();
        boolean canProcess = recipe.isPresent() && hasFuel && entity.canCraft(recipe.get().value());

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

    private boolean hasFuel() {
        return !itemHandler.getStackInSlot(SLOT_FUEL_0).isEmpty()
                || !itemHandler.getStackInSlot(SLOT_FUEL_1).isEmpty();
    }

    private void consumeFuel() {
        for (int slot : new int[]{SLOT_FUEL_0, SLOT_FUEL_1}) {
            ItemStack stack = itemHandler.getStackInSlot(slot);
            if (!stack.isEmpty()) {
                stack.shrink(1);
                return;
            }
        }
    }

    private Optional<RecipeHolder<CatalyticReactorRecipe>> getMatchingRecipe() {
        if (level == null) return Optional.empty();
        return level.getRecipeManager().getRecipeFor(ModRecipes.CATALYTIC_REACTOR_TYPE.get(), new SimpleItemHandlerRecipeInput(itemHandler), level);
    }

    private boolean canCraft(CatalyticReactorRecipe recipe) {
        ItemStack result = recipe.getResultItem(level.registryAccess());
        ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT);
        if (result.isEmpty()) return false;
        if (output.isEmpty()) return true;
        return ItemStack.isSameItemSameComponents(output, result) && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void craft(CatalyticReactorRecipe recipe) {
        for (int i = SLOT_INPUT_0; i <= SLOT_INPUT_3; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }
        }
        consumeFuel();
        ItemStack result = recipe.getResultItem(level.registryAccess()).copy();
        ItemStack output = itemHandler.getStackInSlot(SLOT_OUTPUT);
        if (output.isEmpty()) {
            itemHandler.setStackInSlot(SLOT_OUTPUT, result);
        } else {
            output.grow(result.getCount());
        }
    }
}
