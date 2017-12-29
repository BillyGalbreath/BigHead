package net.pl3x.bighead.model.layer;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;

public class LayerBigHeadCustomHead implements LayerRenderer<EntityLivingBase> {
    private final ModelRenderer modelRenderer;

    public LayerBigHeadCustomHead(ModelRenderer renderer) {
        this.modelRenderer = renderer;
    }

    public void doRenderLayer(@Nonnull EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

        if (!itemstack.isEmpty()) {
            Item item = itemstack.getItem();
            Minecraft minecraft = Minecraft.getMinecraft();
            GlStateManager.pushMatrix();

            if (entity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            boolean flag = entity instanceof EntityVillager || entity instanceof EntityZombieVillager;

            this.modelRenderer.postRender(0.0625F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            if (item == Items.SKULL) {
                GlStateManager.scale(1.1875F, -1.1875F, -1.1875F);
                if (flag) {
                    GlStateManager.translate(0.0F, 0.0625F, 0.0F);
                }
                GameProfile gameprofile = null;
                if (itemstack.hasTagCompound()) {
                    NBTTagCompound nbttagcompound = itemstack.getTagCompound();
                    if (nbttagcompound.hasKey("SkullOwner", 10)) {
                        gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
                    } else if (nbttagcompound.hasKey("SkullOwner", 8)) {
                        String s = nbttagcompound.getString("SkullOwner");
                        if (!StringUtils.isBlank(s)) {
                            gameprofile = TileEntitySkull.updateGameprofile(new GameProfile(null, s));
                            nbttagcompound.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
                        }
                    }
                }

                GlStateManager.pushMatrix();
                GlStateManager.scale(1.7F, 1.7F, 1.7F);
                TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.getMetadata(), gameprofile, -1, limbSwing);
                GlStateManager.popMatrix();
            } else if (!(item instanceof ItemArmor) || ((ItemArmor) item).getEquipmentSlot() != EntityEquipmentSlot.HEAD) {
                GlStateManager.translate(0.0F, -0.25F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(0.625F, -0.625F, -0.625F);

                if (flag) {
                    GlStateManager.translate(0.0F, 0.1875F, 0.0F);
                }

                GlStateManager.pushMatrix();
                GlStateManager.scale(1.7F, 1.7F, 1.7F);
                minecraft.getItemRenderer().renderItem(entity, itemstack, ItemCameraTransforms.TransformType.HEAD);
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures() {
        return false;
    }
}

