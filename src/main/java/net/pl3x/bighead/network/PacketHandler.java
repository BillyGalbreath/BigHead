package net.pl3x.bighead.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.bighead.BigHead;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(BigHead.modId);

    public static void init() {
        INSTANCE.registerMessage(BigHeadPacket.Handler.class, BigHeadPacket.class, 0, Side.CLIENT);
    }
}
