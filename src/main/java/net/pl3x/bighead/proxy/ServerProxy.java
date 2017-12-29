package net.pl3x.bighead.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.pl3x.bighead.command.CmdBigHead;
import net.pl3x.bighead.config.BigHeadConfig;
import net.pl3x.bighead.network.BigHeadPacket;
import net.pl3x.bighead.network.PacketHandler;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class ServerProxy {
    public void preInit(FMLPreInitializationEvent event) {
        BigHeadConfig.INSTANCE.init();
    }

    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        PacketHandler.init();
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdBigHead());
    }

    public void handleBigHeadPacket(Set<UUID> list) {
    }

    @SubscribeEvent
    public void on(PlayerEvent.PlayerLoggedInEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                PacketHandler.INSTANCE.sendToAll(new BigHeadPacket(BigHeadConfig.INSTANCE.data.getBigHeads()));
            }
        }, 1000L);
    }
}
