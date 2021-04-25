package com.bdbk.server.service;

import com.bdbk.api.HelloService;

/**
 * 逻辑层
 * @author little_eight
 * @since 2021/4/23
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return String.format("你发送的是：【%s】", msg);
    }
}
