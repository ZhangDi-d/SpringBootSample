package com.ryze.netty.chat.server.repository;

import com.ryze.netty.chat.ChatInfo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xueLai on 2019/7/29.
 * 使用容器存储模拟的user数据,并且模拟查询
 */
public class UserRepository {
    private static Map<String, ChatInfo.User> users = new ConcurrentHashMap<>();

    static {
        ChatInfo.User user1 = ChatInfo.User.newBuilder().setUserId("1001")
                .setUsername("1001").setPassword("1001").build();
        ChatInfo.User user2 = ChatInfo.User.newBuilder().setUserId("1002")
                .setUsername("1002").setPassword("1002").build();
        ChatInfo.User user3 = ChatInfo.User.newBuilder().setUserId("1003")
                .setUsername("1003").setPassword("1003").build();
        users.put(user1.getUserId(), user1);
        users.put(user2.getUserId(), user2);
        users.put(user2.getUserId(), user2);
    }

    public ChatInfo.User findUserByUsernameAndPassword(String username, String password) {
        Set<Map.Entry<String, ChatInfo.User>> entries = users.entrySet();
        Iterator<Map.Entry<String, ChatInfo.User>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, ChatInfo.User> next = iterator.next();
            ChatInfo.User user = next.getValue();
            if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
