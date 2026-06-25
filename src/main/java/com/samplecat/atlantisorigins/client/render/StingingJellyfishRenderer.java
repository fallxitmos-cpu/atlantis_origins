package com.samplecat.atlantisorigins.client.render;

import net.minecraft.client.model.SquidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.world.entity.animal.Squid;

public class StingingJellyfishRenderer extends SquidRenderer<Squid> {
    public StingingJellyfishRenderer(EntityRendererProvider.Context context) {
        super(context, new SquidModel<>(context.bakeLayer(ModelLayers.SQUID)));
    }
}
