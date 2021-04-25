package com.bdbk.server;

import com.bdbk.serialization.HessianSerializer;
import com.bdbk.serialization.RpcDecoder;
import com.bdbk.serialization.RpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端
 * @author little_eight
 * @since 2021/4/23
 */
public class NettyServer {
    /**
     * 启动服务
     */
    public void start() {
        //负责处理客户端连接的线程池
        EventLoopGroup boss = new NioEventLoopGroup(1);
        //负责处理读写操作的线程池
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        //添加编码器
                        pipeline.addLast(new RpcEncoder(new HessianSerializer()));
                        //添加解码器
                        pipeline.addLast(new RpcDecoder(new HessianSerializer()));
                        //添加请求处理器
                        pipeline.addLast(new ServerHandler());

                    }
                });
        try {
            ChannelFuture future = serverBootstrap.bind(8080).sync();
            System.out.println("服务端启动成功");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("启动服务端失败");
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
