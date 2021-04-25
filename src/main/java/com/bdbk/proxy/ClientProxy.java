package com.bdbk.proxy;

import com.bdbk.client.NettyClient;
import com.bdbk.protocol.RequestProtocol;
import com.bdbk.protocol.ResponseProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 客户端代理
 * @author little_eight
 * @since 2021/4/23
 */
public class ClientProxy<T> implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        RequestProtocol requestProtocol = new RequestProtocol();

        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();

        requestProtocol.setClassName(className);
        requestProtocol.setMethodName(methodName);
        requestProtocol.setParameterTypes(parameterTypes);
        requestProtocol.setParameters(args);
        System.out.println(String.format("请求内容: %s",requestProtocol));

        //开启Netty 客户端，直连
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8080);
        nettyClient.connect();
        ResponseProtocol responseProtocol = (ResponseProtocol) nettyClient.send(requestProtocol);
        return responseProtocol.getResult();
    }
}
