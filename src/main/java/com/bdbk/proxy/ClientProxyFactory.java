package com.bdbk.proxy;

import java.lang.reflect.Proxy;

/**
 * 客户端代理工厂
 * @author little_eight
 * @since 2021/4/23
 */
public class ClientProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),new Class<?>[] {interfaceClass}, new ClientProxy<>());
    }
}
