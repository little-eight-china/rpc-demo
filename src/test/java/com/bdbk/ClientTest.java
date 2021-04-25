package com.bdbk;

import com.bdbk.api.HelloService;
import com.bdbk.proxy.ClientProxyFactory;

/**
 * 客户端启动
 * @author little_eight
 * @since 2021/4/23
 */
public class ClientTest {
    public static void main(String[] args) {
        HelloService helloService = ClientProxyFactory.create(HelloService.class);
        String result = helloService.hello("你好呀");
        System.out.println("返回结果：" + result);

    }
}
