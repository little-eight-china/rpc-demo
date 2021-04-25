# 简介

## 背景
学习netty所用的rpc demo，代码仅供学习参考，没有上生产的价值。

## 流程
一共有2个对象，客户端跟服务端，其中rpc的调用流程为
```
client -> cliet-proxy -> netwoke(netty) -> server-proxy -> server
```

## 关键逻辑
### 序列化
自定义协议的序列化采用的是hessian

## 如何演示
### 先执行服务端
```java
public class ServerTest {
    public static void main(String[] args) {
        new NettyServer().start();
    }
}
```
### 再执行客户端端
```java
public class ClientTest {
    public static void main(String[] args) {
        HelloService helloService = ClientProxyFactory.create(HelloService.class);
        String result = helloService.hello("你好呀");
        System.out.println("返回结果：" + result);

    }
}
```
### 查看控制台打印即可
