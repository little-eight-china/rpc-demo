package com.bdbk.server;

import com.bdbk.protocol.RequestProtocol;
import com.bdbk.protocol.ResponseProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Method;

/**
 * 服务处理
 * @author little_eight
 * @since 2021/4/23
 */
public class ServerHandler extends SimpleChannelInboundHandler<RequestProtocol> {

    /**
     * 目前写死服务端的逻辑层地址
     */
    private final String prefixClassName = "com.bdbk.server.service.";

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接成功");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端断开连接");
        ctx.fireChannelInactive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestProtocol requestProtocol) {
        System.out.println("服务端接收到客户端数据");
        ResponseProtocol responseProtocol = new ResponseProtocol();
        try {
            Object handler = handler(requestProtocol);
            responseProtocol.setResult(handler);
        } catch (Throwable throwable) {
            responseProtocol.setError(throwable.toString());
            throwable.printStackTrace();
        }
        ctx.writeAndFlush(responseProtocol);
    }

    /**
     * 服务端处理请求
     */
    private Object handler(RequestProtocol requestProtocol) {
        // 使用java反射
        try{
            String[] interfaceNameArr = requestProtocol.getClassName().split("\\.");
            // 取最后一个类，然后找对应实现类（暴力处理了）
            String clazz = interfaceNameArr[interfaceNameArr.length - 1];
            Class<?> clazzName = Class.forName(prefixClassName + clazz + "Impl");
            Method method = clazzName.getMethod(requestProtocol.getMethodName(),requestProtocol.getParameterTypes());
            return method.invoke(clazzName.newInstance(), requestProtocol.getParameters());
        } catch (Exception e){
            System.out.println("处理请求异常");
        }
        return null;
    }
}

