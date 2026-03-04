package net.spookly.moanimals.client.renderer;

import static net.spookly.moanimals.Moanimals.MOD_ID;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.NotNull;

import net.spookly.moanimals.client.model.DuckModel;
import net.spookly.moanimals.client.renderer.entity.state.DuckRenderState;
import net.spookly.moanimals.entity.Duck;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DuckRenderer extends MobRenderer<Duck, DuckRenderState, DuckModel> {
    public DuckRenderer(EntityRendererProvider.Context context) {
        super(context, new DuckModel(context.bakeLayer(DuckModel.LAYER_LOCATION)), 0.25f);
    }

    @Override
    public @NotNull DuckRenderState createRenderState() {
        return new DuckRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DuckRenderState livingEntityRenderState) {
        if (livingEntityRenderState.isBaby) {
            return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/duck/duckling.png");
        }
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/duck/duck_mallard.png");
    }

    @Override
    public void render(DuckRenderState livingEntityRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
        if (livingEntityRenderState.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
        }
        super.render(livingEntityRenderState, poseStack, multiBufferSource, i);
    }
}
//https://youtu.be/xBG1jWHSxrU?si=E8xk-_wt3j9O9u4i&t=1595
