package com.ryze.netty.chat.client.command;

import com.ryze.netty.chat.ChatInfo;
import io.netty.channel.Channel;

/**
 * Created by xueLai on 2019/7/30.
 */
public abstract class AbstractCommand implements Command {
    private Channel channel;

    public AbstractCommand(Channel channel) {
        this.channel = channel;
    }

    public abstract ChatInfo.Chat parseInput(String input);

    @Override
    public void execute(String input) {
        ChatInfo.Chat chat = parseInput(input);
        if (chat == null) {
            return;
        }
        channel.writeAndFlush(chat);

    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
