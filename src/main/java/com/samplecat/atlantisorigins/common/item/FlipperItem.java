package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.NeoForgeMod;

public class FlipperItem extends ArmorItem {

    private static final ResourceLocation SWIM_SPEED_ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "flipper_swim_speed");
    private static final ResourceLocation WATER_EFFICIENCY_ID = ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "flipper_water_efficiency");

    public FlipperItem(Properties properties) {
        super(ArmorMaterials.LEATHER, Type.BOOTS, properties);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(NeoForgeMod.SWIM_SPEED,
                        new AttributeModifier(SWIM_SPEED_ID, 1.2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                        EquipmentSlotGroup.FEET)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY,
                        new AttributeModifier(WATER_EFFICIENCY_ID, 1.0D, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.FEET)
                .build();
    }
}
