package com.samplecat.atlantisorigins.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

/**
 * Orichalcum trident.
 * <p>
 * Deals 20 attack damage (12 more than a vanilla trident) and comes pre-enchanted
 * with Loyalty III and Riptide III.
 */
public class OrichalcumTridentItem extends TridentItem {

    // Vanilla trident: 8 damage, -2.9 attack speed.
    private static final float ATTACK_DAMAGE = 20.0F;
    private static final float ATTACK_SPEED = -2.9F;
    // Netherite-tier durability with Unbreaking II effective multiplier.
    private static final int DURABILITY = 6093;

    public OrichalcumTridentItem(Item.Properties properties) {
        super(properties.durability(DURABILITY).attributes(createOrichalcumAttributes()));
    }

    private static ItemAttributeModifiers createOrichalcumAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_DAMAGE,
                        new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                Item.BASE_ATTACK_DAMAGE_ID, ATTACK_DAMAGE, net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE),
                        net.minecraft.world.entity.EquipmentSlotGroup.MAINHAND)
                .add(
                        net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED,
                        new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                                Item.BASE_ATTACK_SPEED_ID, ATTACK_SPEED, net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADD_VALUE),
                        net.minecraft.world.entity.EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && !EnchantmentHelper.hasAnyEnchantments(stack)) {
            level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.LOYALTY)
                    .ifPresent(holder -> stack.enchant(holder, 3));
            level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.RIPTIDE)
                    .ifPresent(holder -> stack.enchant(holder, 3));
        }
        super.inventoryTick(stack, level, entity, slotId, isSelected);
    }
}
