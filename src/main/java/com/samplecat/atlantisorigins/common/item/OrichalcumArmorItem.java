package com.samplecat.atlantisorigins.common.item;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.client.render.OrichalcumArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.NeoForgeMod;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

/**
 * Orichalcum armor piece.
 * <p>
 * No special buffs; the evolution from Poseidon's flesh provides the bonuses.
 * Rendered with a GeckoLib armor model to avoid the flat-plane inflation issues
 * of the vanilla {@link HumanoidModel} approach.
 */
public class OrichalcumArmorItem extends ArmorItem implements GeoItem {

    private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("成年");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public OrichalcumArmorItem(Type type, Item.Properties properties) {
        super(
                OrichalcumArmorMaterial.ORICHALCUM,
                type,
                properties.durability(type.getDurability(OrichalcumArmorMaterial.DURABILITY_MULTIPLIER))
        );
        GeoItem.registerSyncedAnimatable(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "armor_controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<OrichalcumArmorItem> event) {
        event.getController().setAnimation(IDLE_ANIMATION);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private OrichalcumArmorRenderer renderer;

            @Override
            public <T extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(
                    T livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot,
                    HumanoidModel<T> original) {
                if (this.renderer == null) {
                    this.renderer = new OrichalcumArmorRenderer();
                }
                return this.renderer;
            }
        });
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot,
                                            ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID,
                "textures/models/armor/orichalcum_layer.png");
    }

    private static final ResourceLocation ORICHALCUM_BOOTS_SWIM_SPEED_ID = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "orichalcum_boots_swim_speed");
    private static final ResourceLocation ORICHALCUM_BOOTS_WATER_EFFICIENCY_ID = ResourceLocation.fromNamespaceAndPath(
            AtlantisOrigins.MOD_ID, "orichalcum_boots_water_efficiency");

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers base = super.getDefaultAttributeModifiers();
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : base.modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }

        if (this.type == Type.BOOTS) {
            builder.add(NeoForgeMod.SWIM_SPEED,
                            new AttributeModifier(ORICHALCUM_BOOTS_SWIM_SPEED_ID, 1.0D,
                                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                            EquipmentSlotGroup.FEET)
                    .add(Attributes.WATER_MOVEMENT_EFFICIENCY,
                            new AttributeModifier(ORICHALCUM_BOOTS_WATER_EFFICIENCY_ID, 0.5D,
                                    AttributeModifier.Operation.ADD_VALUE),
                            EquipmentSlotGroup.FEET);
        }

        return builder.build();
    }
}
