package net.pl3x.bighead.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.pl3x.bighead.BigHead;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BigHeadPacket implements IMessage {
    private Set<UUID> playersWithBigHeadEnabled = new HashSet<>();

    public BigHeadPacket() {
    }

    public BigHeadPacket(Set<UUID> playersWithBigHeadEnabled) {
        this.playersWithBigHeadEnabled = playersWithBigHeadEnabled;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        playersWithBigHeadEnabled.clear();
        NBTTagCompound tag = ByteBufUtils.readTag(buf);
        if (tag != null && tag.hasKey("bighead")) {
            NBTTagList bighead = tag.getTagList("bighead", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < bighead.tagCount(); i++) {
                NBTTagCompound entry = bighead.getCompoundTagAt(i);
                if (entry.hasKey("uuid")) {
                    playersWithBigHeadEnabled.add(UUID.fromString(entry.getString("uuid")));
                }
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagList tagList = new NBTTagList();
        for (UUID uuid : playersWithBigHeadEnabled) {
            NBTTagCompound entry = new NBTTagCompound();
            entry.setString("uuid", uuid.toString());
            tagList.appendTag(entry);
        }
        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("bighead", tagList);
        ByteBufUtils.writeTag(buf, tag);
    }

    public static class Handler implements IMessageHandler<BigHeadPacket, IMessage> {
        @Override
        public IMessage onMessage(BigHeadPacket packet, MessageContext context) {
            BigHead.proxy.handleBigHeadPacket(packet.playersWithBigHeadEnabled);
            return null;
        }
    }
}
