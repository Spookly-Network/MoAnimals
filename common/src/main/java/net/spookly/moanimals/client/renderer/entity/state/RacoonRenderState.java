package net.spookly.moanimals.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;

public class RacoonRenderState extends LivingEntityRenderState {
    private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/racoon/default.png");

    public ResourceLocation texture;

    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();

    public RacoonRenderState() {
        this.texture = DEFAULT_TEXTURE;
    }
}
