package com.bdbk.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端处理
 * @author little_eight
 * @since 2021/4/23
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private Object response;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // 因为netty处理都是异步的，为了达成 接收服务器消息 -> 获取消息，加个锁
        synchronized (this) {
            this.response = msg;
            this.notify();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("连接发生异常");
        ctx.close();
    }

    /**
     * 获取返回体
     */
    public Object getResponse() {
        // 因为netty处理都是异步的，为了达成 接收服务器消息 -> 获取消息，加个锁
        synchronized (this){
            try {
                this.wait(10000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return response;
        }
    }
}
