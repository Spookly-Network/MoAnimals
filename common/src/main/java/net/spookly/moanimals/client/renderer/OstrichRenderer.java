package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.OstrichModel;
import net.spookly.moanimals.entity.Ostrich;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class OstrichRenderer extends MobRenderer<Ostrich, OstrichModel<Ostrich>> {

    public OstrichRenderer(EntityRendererProvider.Context context) {
        super(context, new OstrichModel<>(context.bakeLayer(OstrichModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Ostrich entity) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/ostrich.png");
    }

    @Override
    public void render(Ostrich livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity.isBaby()) {
            poseStack.scale(0.55f, 0.55f, 0.55f);
        }
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
