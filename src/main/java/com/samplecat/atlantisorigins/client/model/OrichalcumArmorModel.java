package com.samplecat.atlantisorigins.client.model;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.item.OrichalcumArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

/**
 * GeckoLib model for the orichalcum armor set.
 * <p>
 * The model data lives at {@code assets/atlantis_origins/geo/item/orichalcum_armor.geo.json}
 * and the texture is kept in the existing armor texture path.
 */
public class OrichalcumArmorModel extends DefaultedItemGeoModel<OrichalcumArmorItem> {

    public OrichalcumArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "orichalcum_armor"));
    }

    @Override
    public ResourceLocation getTextureResource(OrichalcumArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID,
                "textures/models/armor/orichalcum_layer.png");
    }
}
