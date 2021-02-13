package nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 从文件读数据（缓冲区、通道）
 * @author suwanyan
 * @date 2021/1/26 12:57
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws Exception {
        //定义
        File file = new File("D:\\swy\\yx\\四川火警应急\\test\\hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //操作
        fileChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}
