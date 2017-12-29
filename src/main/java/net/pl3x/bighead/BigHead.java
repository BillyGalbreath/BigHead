package net.pl3x.bighead;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.pl3x.bighead.proxy.ServerProxy;

import java.io.File;

@Mod(modid = BigHead.modId, name = BigHead.name, version = BigHead.version)
public class BigHead {
    public static final String modId = "bighead";
    public static final String name = "BigHead";
    public static final String version = "@DEV_BUILD@";

    @SidedProxy(serverSide = "net.pl3x.bighead.proxy.ServerProxy", clientSide = "net.pl3x.bighead.proxy.ClientProxy")
    public static ServerProxy proxy;

    public static File configDir;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configDir = new File(event.getModConfigurationDirectory(), BigHead.name);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }
}
