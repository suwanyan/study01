package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用一个buffer完成文件拷贝
 * @author suwanyan
 * @date 2021/1/26 16:24
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("D:\\swy\\study\\OneHundredMillion\\socket\\src\\1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("D:\\swy\\study\\OneHundredMillion\\socket\\src\\2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {
            byteBuffer.clear(); //不复位会循环读取，一直read=0
            int read = fileChannel01.read(byteBuffer);

            if (read == -1) {
                break;
            }
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
