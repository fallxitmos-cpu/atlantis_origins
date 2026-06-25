package com.samplecat.atlantisorigins.client.render;

import com.samplecat.atlantisorigins.client.model.PoseidonsBladeModel;
import com.samplecat.atlantisorigins.common.entity.PoseidonsBlade;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PoseidonsBladeRenderer extends GeoEntityRenderer<PoseidonsBlade> {

    public PoseidonsBladeRenderer(EntityRendererProvider.Context context) {
        super(context, new PoseidonsBladeModel());
        this.shadowRadius = 0.6F;
    }
}
