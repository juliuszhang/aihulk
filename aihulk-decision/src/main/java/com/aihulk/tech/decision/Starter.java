package com.aihulk.tech.decision;

import com.aihulk.tech.decision.component.mvc.core.NettyRequestDispatcher;
import com.aihulk.tech.decision.config.DecisionConfig;
import com.aihulk.tech.decision.config.DecisionConfigEntity;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangyibo
 * @version 1.0
 * @ClassName:Starter
 * @Description: Starter
 * @date 2019/8/1
 */
@Slf4j
@AllArgsConstructor
public class Starter {

    private final int port;

    public static void main(String[] args) {
        DecisionConfigEntity.ServerConfig config = DecisionConfig.getServerConfig();
        int port = config.getPort();
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        try {
            new Starter(port).start();
        } catch (InterruptedException e) {
            log.info("Error occurs while starting the server:", e);
            System.exit(1);
        }
    }

    private void start() throws InterruptedException {
        // start the server here.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) {
                            channel.pipeline()
                                    .addLast("request-decoder", new HttpRequestDecoder())
                                    // transfer different http message
                                    .addLast("post", new HttpObjectAggregator(1024 * 1024))
                                    .addLast("response-encoder", new HttpResponseEncoder())
                                    .addLast("handler", new NettyRequestDispatcher());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true);

            ChannelFuture future = serverBootstrap.bind(this.port).sync();
            log.info("Server starts at port:" + port);
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
