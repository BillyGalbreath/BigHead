package net.pl3x.bighead.model.layer;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.bighead.model.ModelBigHeadBiped;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class LayerBigHeadBipedArmor extends LayerArmorBase<ModelBigHeadBiped> {
    public LayerBigHeadBipedArmor(RenderLivingBase<?> rendererIn) {
        super(rendererIn);
    }

    protected void initArmor() {
        this.modelLeggings = new ModelBigHeadBiped(0.5F);
        this.modelArmor = new ModelBigHeadBiped(1.0F);
    }

    @SuppressWarnings("incomplete-switch")
    protected void setModelSlotVisible(@Nonnull ModelBigHeadBiped model, @Nonnull EntityEquipmentSlot slot) {
        this.setModelVisible(model);

        switch (slot) {
            case HEAD:
                model.bipedHead.showModel = true;
                model.bipedHeadwear.showModel = true;
                break;
            case CHEST:
                model.bipedBody.showModel = true;
                model.bipedRightArm.showModel = true;
                model.bipedLeftArm.showModel = true;
                break;
            case LEGS:
                model.bipedBody.showModel = true;
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
        }
    }

    public void setModelVisible(ModelBiped model) {
        model.setVisible(false);
    }

    @Override
    @Nonnull
    protected ModelBigHeadBiped getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, @Nonnull ModelBigHeadBiped model) {
        return (ModelBigHeadBiped) ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
    }
}
