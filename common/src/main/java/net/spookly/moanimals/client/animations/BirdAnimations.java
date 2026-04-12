package net.spookly.moanimals.client.animations;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 5.1.3
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class BirdAnimations {
    public static final AnimationDefinition idle = AnimationDefinition.Builder.withLength(4.5F)
        .addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.125F, KeyframeAnimations.degreeVec(1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.375F, KeyframeAnimations.degreeVec(-1.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.degreeVec(-11.7765F, 29.4028F, 1.7359F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.25F, KeyframeAnimations.degreeVec(-14.8398F, 29.963F, -4.6654F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(-1.6627F, -16.2408F, -0.9505F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.5F, KeyframeAnimations.degreeVec(-9.1627F, -16.2408F, -0.9505F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.75F, KeyframeAnimations.degreeVec(8.4402F, -31.239F, -1.204F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.5F, KeyframeAnimations.degreeVec(4.9586F, -31.383F, -4.1254F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.75F, KeyframeAnimations.degreeVec(-7.2842F, -1.2125F, -0.3985F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.25F, KeyframeAnimations.degreeVec(0.2158F, -1.2125F, -0.3985F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.125F, KeyframeAnimations.degreeVec(2.462F, -0.434F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.375F, KeyframeAnimations.degreeVec(2.462F, -0.434F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.625F, KeyframeAnimations.degreeVec(2.462F, -0.434F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.75F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.375F, KeyframeAnimations.degreeVec(2.462F, -0.434F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.625F, KeyframeAnimations.degreeVec(2.462F, -0.434F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.75F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.875F, KeyframeAnimations.degreeVec(2.462F, -0.434F, 15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("wing_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.125F, KeyframeAnimations.degreeVec(2.5F, -0.434F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.375F, KeyframeAnimations.degreeVec(2.462F, -0.434F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.625F, KeyframeAnimations.degreeVec(2.462F, -0.434F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.75F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.25F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.375F, KeyframeAnimations.degreeVec(2.5F, -0.434F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.5F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.625F, KeyframeAnimations.degreeVec(2.462F, -0.434F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.75F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.875F, KeyframeAnimations.degreeVec(2.462F, -0.434F, -15.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.0F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.5833F, KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.125F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(1.7083F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.2083F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(2.8333F, KeyframeAnimations.degreeVec(-18.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.375F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(3.9583F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(4.5F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();

    public static final AnimationDefinition walk = AnimationDefinition.Builder.withLength(0.375F).looping()
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 1.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.2083F, KeyframeAnimations.degreeVec(0.0F, -7.5F, -5.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 15.0F, 92.5F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1.7538F, -9.8466F, 10.151F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-37.5F, 15.0F, 92.5F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("wing_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, -15.0F, -92.5F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(-1.7538F, 9.8466F, -10.151F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-37.5F, -15.0F, -92.5F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.2083F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(2.4F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(42.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .build();

    public static final AnimationDefinition death = AnimationDefinition.Builder.withLength(0.4583F)
        .addAnimation("root", new AnimationChannel(AnimationChannel.Targets.POSITION,
            new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.posVec(1.0F, 3.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
            new Keyframe(0.4583F, KeyframeAnimations.posVec(2.0F, 2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
        ))
        .addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(-9.57F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-9.5684F, 26.1279F, -8.8967F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-0.0685F, -5.0764F, 0.2361F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(-0.3839F, -14.922F, 2.0407F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("wing_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-52.3384F, 60.4447F, 53.0111F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-14.3206F, 2.5514F, -10.1096F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -10.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("wing_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.25F, KeyframeAnimations.degreeVec(-52.3384F, -60.4447F, -53.0111F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.375F, KeyframeAnimations.degreeVec(-14.3206F, 2.5514F, 10.1096F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 10.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("leg_right", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 20.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 12.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(5.8547F, -13.8345F, -3.211F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("leg_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 32.5F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.4583F, KeyframeAnimations.degreeVec(12.5F, 0.0F, -20.0F), AnimationChannel.Interpolations.LINEAR)
        ))
        .addAnimation("foot_left", new AnimationChannel(AnimationChannel.Targets.ROTATION,
            new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
            new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-7.4365F, 0.9762F, 7.4365F), AnimationChannel.Interpolations.LINEAR)
        ))
        .build();
}
