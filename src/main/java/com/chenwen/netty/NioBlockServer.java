package com.chenwen.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

/**
 * @author chen.jw
 * @date 2021/9/6 15:23
 */
public class NioBlockServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel server=ServerSocketChannel.open();
        server.bind(new InetSocketAddress(3333));
        server.configureBlocking(false);
        Selector open = Selector.open();
        server.register(open, SelectionKey.OP_ACCEPT);
        while(open.select() > 0){
            Iterator<SelectionKey> iterator = open.selectedKeys().iterator();
            if (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    System.out.println(client.getRemoteAddress());
                    client.register(open, SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    FileChannel outChannel = FileChannel.open(Paths.get("123.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                    while (client.read(buffer)>0){
                        buffer.flip();
                        outChannel.write(buffer);
                        buffer.clear();
                    }
                }

            }
            iterator.remove();
        }
    }

}
