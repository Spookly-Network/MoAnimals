package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.PenguinModel;
import net.spookly.moanimals.entity.Penguin;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PenguinRenderer extends MobRenderer<Penguin, PenguinModel<Penguin>> {
    public PenguinRenderer(EntityRendererProvider.Context context) {
        super(context, new PenguinModel<>(context.bakeLayer(PenguinModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Penguin entity) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/penguin.png");
    }

    @Override
    public void render(Penguin livingEntity, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntity.isBaby()) {
            poseStack.scale(0.55f, 0.55f, 0.55f);
        }
        super.render(livingEntity, f, g, poseStack, multiBufferSource, i);
    }
}
