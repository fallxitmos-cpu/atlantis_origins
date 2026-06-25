package com.samplecat.atlantisorigins.client.model;

import com.samplecat.atlantisorigins.AtlantisOrigins;
import com.samplecat.atlantisorigins.common.entity.PoseidonsBlade;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class PoseidonsBladeModel extends DefaultedEntityGeoModel<PoseidonsBlade> {

    public PoseidonsBladeModel() {
        super(ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "poseidons_blade"));
    }
}
