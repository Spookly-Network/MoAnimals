package net.spookly.moanimals.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class CrocodileRenderState extends LivingEntityRenderState {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimation = new AnimationState();
    public final AnimationState swimAnimation = new AnimationState();
    public final AnimationState walkAnimation = new AnimationState();
}
