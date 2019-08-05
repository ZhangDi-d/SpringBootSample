package com.ryze.netty.chat.server.handler;

import com.ryze.netty.chat.ChatInfo;
import com.ryze.netty.chat.util.SessionUtil;
import io.netty.channel.*;

/**
 * Created by xueLai on 2019/7/29.
 */
public class SingleChatHandler extends SimpleChannelInboundHandler<ChatInfo.Chat> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatInfo.Chat msg) throws Exception {
        ChatInfo.Chat.MessageType msgType = msg.getMsgType();
        if (msgType == ChatInfo.Chat.MessageType.SINGLE_CHAT) {
            ChatInfo.SingleChat singleChat = msg.getSingleChat();

            ChatInfo.User fromUser = singleChat.getFromUser();
            ChatInfo.User toUser = singleChat.getToUser();

            System.out.println("服务端：" + fromUser.getUserId() + " 发送给 " + toUser.getUserId() + " 的消息：" + singleChat.getMsgContent());

            Channel toUserChannel = SessionUtil.getChannel(toUser.getUserId());
            if (toUserChannel == null) {
                System.out.println("用户 " + toUser.getUserId() + " 不在线");
                // TODO 如果用户不不在线，可以先将消息存储在DB或Redis中，待接收用户上线时推送给用户。
                return;
            }

            toUserChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    System.out.println("发送结果 = " + future.isSuccess());
                }
            });

        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
