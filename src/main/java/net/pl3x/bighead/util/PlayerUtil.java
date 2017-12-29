package net.pl3x.bighead.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

public class PlayerUtil {
    private static final MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
    private static final PlayerList playerList = serverInstance.getPlayerList();

    public static EntityPlayerMP getPlayer(String name) {
        for (String realName : getOnlinePlayerNames()) {
            if (realName.equals(name)) {
                return playerList.getPlayerByUsername(realName);
            }
        }
        return null;
    }

    public static Collection<String> getOnlinePlayerNames() {
        return Arrays.asList(playerList.getOnlinePlayerNames());
    }

    public static Collection<String> getOfflinePlayerNames() {
        Collection<String> collection = new HashSet<>();
        for (Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet()) {
            collection.add(entry.getValue());
        }
        return collection;
    }

    public static UUID getUUIDFromName(String name) {
        for (Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet()) {
            if (entry.getValue().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
