package com.ryze.netty.chat.client.command;

import com.ryze.netty.chat.ChatInfo;
import io.netty.channel.Channel;

/**
 * Created by xueLai on 2019/7/30.
 */
public class LoginCommand extends AbstractCommand {
    public LoginCommand(Channel channel) {
        super(channel);
    }

    @Override
    public ChatInfo.Chat parseInput(String input) {
        return null;
    }
}
