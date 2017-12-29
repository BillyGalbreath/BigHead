package net.pl3x.bighead.model.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerEntityOnShoulder;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.pl3x.bighead.model.ModelBigHeadPlayer;
import net.pl3x.bighead.model.layer.LayerBigHeadBipedArmor;
import net.pl3x.bighead.model.layer.LayerBigHeadCustomHead;

public class RenderBigHeadPlayer extends RenderPlayer {
    public boolean isNew = false;

    public RenderBigHeadPlayer() {
        this(Minecraft.getMinecraft().getRenderManager());
        isNew = true;
    }

    public RenderBigHeadPlayer(RenderManager renderManager) {
        this(renderManager, false);
    }

    public RenderBigHeadPlayer(RenderManager renderManager, boolean useSmallArms) {
        super(renderManager, useSmallArms);
        mainModel = new ModelBigHeadPlayer(0.0F, useSmallArms);
        layerRenderers.clear();
        addLayer(new LayerBigHeadBipedArmor(this));
        addLayer(new LayerHeldItem(this));
        addLayer(new LayerArrow(this));
        addLayer(new LayerDeadmau5Head(this));
        addLayer(new LayerCape(this));
        addLayer(new LayerBigHeadCustomHead(this.getMainModel().bipedHead));
        addLayer(new LayerElytra(this));
        addLayer(new LayerEntityOnShoulder(renderManager));
    }
}
