package com.bdbk.client;

import com.bdbk.protocol.RequestProtocol;
import com.bdbk.protocol.ResponseProtocol;
import com.bdbk.serialization.HessianSerializer;
import com.bdbk.serialization.RpcDecoder;
import com.bdbk.serialization.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 客户端
 * @author little_eight
 * @since 2021/4/23
 */
public class NettyClient {
    private Channel channel;
    private ClientHandler clientHandler;
    private final String host;
    private final Integer port;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 启动连接
     */
    public void connect() {
        clientHandler = new ClientHandler();
        //启动类
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        try{
            bootstrap.group(loopGroup)
                    //指定传输使用的Channel
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,5000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加编码器
                            pipeline.addLast(new RpcEncoder(new HessianSerializer()));
                            //添加解码器
                            pipeline.addLast(new RpcDecoder(new HessianSerializer()));
                            //请求处理类
                            pipeline.addLast(clientHandler);
                        }
                    });
            // 开始连接
            ChannelFuture f = bootstrap.connect(host, port).sync();
            channel = f.channel();
            System.out.println("客户端启动成功");
            // 必须注释掉，不然会阻塞在这里。
            // f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("gg，客户端连接异常");
        }
    }

    public Object send(final RequestProtocol request) {
        try {
            channel.writeAndFlush(request).await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientHandler.getResponse();
    }
}
