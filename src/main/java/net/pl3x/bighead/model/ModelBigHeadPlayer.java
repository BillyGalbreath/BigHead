package net.pl3x.bighead.model;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class ModelBigHeadPlayer extends ModelPlayer {
    public ModelBigHeadPlayer(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }

    public void render(@Nonnull Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entity);

        GlStateManager.pushMatrix();

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
        GlStateManager.scale(2.7F, 2.7F, 2.7F);
        bipedHeadwear.render(scale);
        GlStateManager.popMatrix();

        GlStateManager.popMatrix();
    }
}