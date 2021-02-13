package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author suwanyan
 * @date 2021/2/2 19:44
 */
public class ScatteringAndGathering {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = serverSocketChannel.accept();
        int messageLen = 8; //假定从客户端读取8个字节

        while (true) { //循环读取
            int byteRead = 0;

            while (byteRead < messageLen) {
                long l = socketChannel.read(byteBuffers);
                byteRead += l;
                System.out.println("byteRead = " + byteRead);
                Arrays.asList(byteBuffers).stream().map(buffer -> "positon=" + buffer.position() + "，" +
                        "limit=" + buffer.limit()).forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());
            //将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLen) {
                long l = socketChannel.write(byteBuffers);
                byteWrite += l;
            }

            //将所有buffer进行clear
            Arrays.asList(byteBuffers).forEach(byteBuffer -> {
                byteBuffer.clear();
            });

            System.out.println("byteRead=" + byteRead + " byteWrite=" + byteWrite + " messageLen=" + messageLen);
        }
    }
}
