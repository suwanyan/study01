package nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用缓冲区和通道向文件写数据
 *
 * @author suwanyan
 * @date 2021/1/26 9:59
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception{
        String str = "hello netty";
        //创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D:\\swy\\yx\\四川火警应急\\test\\hello.txt");
        //FileChannel是一个接口
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //写入缓冲区
        byteBuffer.put(str.getBytes());
        //读写反转
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
