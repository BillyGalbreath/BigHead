package net.pl3x.bighead.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.bighead.model.ModelBigHeadPlayer;
import net.pl3x.bighead.model.renderer.RenderBigHeadPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    public static final Map<UUID, RenderBigHeadPlayer> playersWithBigHeadEnabled = new HashMap<>();

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    @SubscribeEvent
    public void on(RenderPlayerEvent.Pre event) {
        RenderBigHeadPlayer renderer = playersWithBigHeadEnabled.get(event.getEntityPlayer().getUniqueID());
        if (renderer == null) {
            return;
        }
        if (event.getRenderer().getMainModel() instanceof ModelBigHeadPlayer) {
            return;
        }

        event.setCanceled(true);

        AbstractClientPlayer player = (AbstractClientPlayer) event.getEntityPlayer();

        if (renderer.isNew) {
            boolean smallArms = player.getSkinType().equals("slim");
            renderer = new RenderBigHeadPlayer(Minecraft.getMinecraft().getRenderManager(), smallArms);
            playersWithBigHeadEnabled.put(player.getUniqueID(), renderer);
        }

        float yaw = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * event.getPartialRenderTick();
        renderer.doRender(player, event.getX(), event.getY(), event.getZ(), yaw, event.getPartialRenderTick());
    }

    public void handleBigHeadPacket(Set<UUID> list) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            playersWithBigHeadEnabled.clear();
            for (UUID uuid : list) {
                playersWithBigHeadEnabled.put(uuid, new RenderBigHeadPlayer());
            }
        });
    }
}
