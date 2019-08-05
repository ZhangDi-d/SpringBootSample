package com.ryze.netty.chat.client.handler;

import com.ryze.netty.chat.ChatInfo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by xueLai on 2019/8/5.
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<ChatInfo.Chat> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatInfo.Chat msg) throws Exception {
        ChatInfo.Chat.MessageType msgType = msg.getMsgType();
        if(ChatInfo.Chat.MessageType.CREATE_GROUP_RESPONSE == msgType) {
            ChatInfo.Group group = msg.getCreateGroupResponse().getGroup();

            System.out.println("群组"+group.getGroupId()+" 创建成功");

        }else {
            ctx.fireChannelRead(msg);
        }
    }
}
