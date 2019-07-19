package com.ryze.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by xueLai on 2019/7/19.
 */
public class ProtobufCodecTest {
    /**
     * 编码
     *
     * @param req 
     * @return
     */
    public static byte[] encode(ChatInfo.Chat req) {
        return req.toByteArray();
    }

    /**
     * 解码
     *
     * @param buffer
     * @return
     * @throws InvalidProtocolBufferException
     */
    public static ChatInfo.Chat decode(byte[] buffer) throws InvalidProtocolBufferException {
        return ChatInfo.Chat.parseFrom(buffer);
    }

    public static void main(String[] args) throws Exception {

        ChatInfo.Chat chat = ChatInfo.Chat.newBuilder()
                .setMsg("感谢这么优秀的你还关注我！")
                .build();

        byte[] buffer = encode(chat);
        ChatInfo.Chat resp = decode(buffer);

        System.out.println("msg content = " + resp.getMsg());
    }
}
