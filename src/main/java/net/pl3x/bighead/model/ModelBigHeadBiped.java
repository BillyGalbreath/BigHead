package net.pl3x.bighead.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

import javax.annotation.Nonnull;

public class ModelBigHeadBiped extends ModelBiped {
    public ModelBigHeadBiped(float modelSize) {
        this(modelSize, 0.0F, 64, 32);
    }

    public ModelBigHeadBiped(float modelSize, float yOffset, int textureWidthIn, int textureHeightIn) {
        super(modelSize, yOffset, textureWidthIn, textureHeightIn);
    }

    public void render(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        if (entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.7F, 1.7F, 1.7F);
        bipedHead.render(scale);
        GlStateManager.popMatrix();

        bipedBody.render(scale);
        bipedRightArm.render(scale);
        bipedLeftArm.render(scale);
        bipedRightLeg.render(scale);
        bipedLeftLeg.render(scale);

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.7F, 1.7F, 1.7F);
        bipedHeadwear.render(scale);
        GlStateManager.popMatrix();

    }
}
