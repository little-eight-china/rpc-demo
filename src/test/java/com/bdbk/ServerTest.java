package com.bdbk;

import com.bdbk.server.NettyServer;

/**
 * 服务端启动
 * @author little_eight
 * @since 2021/4/23
 */
public class ServerTest {
    public static void main(String[] args) {
        new NettyServer().start();
    }
}
