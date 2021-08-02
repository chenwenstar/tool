package com.chenwen.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author chen.jw
 * @date 2021/7/28 11:09
 */
public class ClientTest {
    static final String HOST = "127.0.0.1";
    static final String PORT = "9999";

    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);

        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
