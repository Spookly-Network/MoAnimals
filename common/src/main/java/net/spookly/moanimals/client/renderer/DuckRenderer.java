package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;
import net.spookly.moanimals.client.model.DuckModel;
import net.spookly.moanimals.entity.Duck;

public class DuckRenderer extends MobRenderer<Duck, DuckModel<Duck>> {
    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel<>(context.bakeLayer(DuckModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Duck entity) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/duck/duck.png");
    }

    @Override
    public void render(Duck livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
//https://youtu.be/xBG1jWHSxrU?si=E8xk-_wt3j9O9u4i&t=1595