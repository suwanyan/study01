package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author suwanyan
 * @date 2021/2/4 16:17
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        //绑定端口，在服务器监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);//非阻塞

        //把serverSocketChannel注册到selector，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端连接
        while (true) {
            if (selector.select(1000) == 0) { //等待1s，没有事件发生，返回
                System.out.println("服务器等待1s，无连接");
                continue;
            }

            //如果返回>0标表示已经获取到关注事件
            //selector.selectedKeys()返回关注事件的集合
            //通过selectionKeySet反向获取通道
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            //迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeySet.iterator();
            while (keyIterator.hasNext()) {
                //获取到selectionKey
                SelectionKey selectionKey = keyIterator.next();
                //根据selectionKey对应的通道发生的事件做相应的处理
                if (selectionKey.isAcceptable()) { //如果是OP_ACCEPT，有新的客户端连接
                    //给该客户端生成一个socketChannel
                    //accept()本身是阻塞的，但是此时执行的时候我们已经确确实实知道已经有一个客户端连接了，所以实际上不阻塞
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //将socketChannel注册到selector，关注事件为OP_ACCEPT，同时给该socketChannel关联一个buffer
                    socketChannel.register(selector, SelectionKey.OP_ACCEPT, ByteBuffer.allocate(1024));
                }

                if (selectionKey.isReadable()) { //发生OP_READ
                    //通过key反向获取对应的channel
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    //把当前的数据读到byteBuffer里面
                    socketChannel.read(byteBuffer);
                    System.out.println("从客户端 " + new String(byteBuffer.array()));
                }

                //手动从集合中移除selectionKey，因为是多线程，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
