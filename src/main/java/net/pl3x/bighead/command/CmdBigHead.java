package net.pl3x.bighead.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.pl3x.bighead.util.PlayerUtil;
import net.pl3x.bighead.config.BigHeadConfig;
import net.pl3x.bighead.network.BigHeadPacket;
import net.pl3x.bighead.network.PacketHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CmdBigHead extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "bighead";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    @Nonnull
    public String getUsage(@Nonnull ICommandSender sender) {
        return "commands.bighead.usage";
    }

    @Override
    @Nonnull
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return PlayerUtil.getOfflinePlayerNames().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, @Nonnull String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return;
        }

        if (args.length < 1) {
            throw new WrongUsageException("commands.effect.usage");
        }

        EntityPlayerMP target = PlayerUtil.getPlayer(args[0]);
        UUID uuid;
        String name;
        if (target == null) {
            uuid = PlayerUtil.getUUIDFromName(args[0]);
            if (uuid == null) {
                // player not found
                return;
            }
            name = args[0];
        } else {
            uuid = target.getUniqueID();
            name = target.getName();
        }

        Set<UUID> bigHeads = BigHeadConfig.INSTANCE.data.getBigHeads();
        if (bigHeads.contains(uuid)) {
            bigHeads.remove(uuid);
            notifyCommandListener(sender, this, "commands.bighead.disabled", name);
        } else {
            bigHeads.add(uuid);
            notifyCommandListener(sender, this, "commands.bighead.enabled", name);
        }

        BigHeadConfig.INSTANCE.data.setBighead(bigHeads);
        BigHeadConfig.INSTANCE.save();

        PacketHandler.INSTANCE.sendToAll(new BigHeadPacket(bigHeads));
    }
}
