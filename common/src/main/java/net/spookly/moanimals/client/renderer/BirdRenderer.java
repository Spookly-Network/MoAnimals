package net.spookly.moanimals.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.BirdModel;
import net.spookly.moanimals.entity.Bird;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BirdRenderer extends MobRenderer<Bird, BirdModel<Bird>> {
    public BirdRenderer(EntityRendererProvider.Context context) {
        super(context, new BirdModel<>(context.bakeLayer(BirdModel.LAYER_LOCATION)), 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Bird entity) {
        return entity.getTexture();
    }

    @Override
    public void render(Bird livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
