package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import com.mojang.blaze3d.vertex.PoseStack;
import net.spookly.moanimals.client.model.CrocodileModel;
import net.spookly.moanimals.entity.Crocodile;

public class CrocodileRenderer extends MobRenderer<Crocodile, CrocodileModel<Crocodile>> {
    public CrocodileRenderer(EntityRendererProvider.Context context) {
        super(context, new CrocodileModel<>(context.bakeLayer(CrocodileModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(Crocodile entity) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/duck/crocodile.png");
    }

    @Override
    public void render(Crocodile livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity.isBaby()) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
