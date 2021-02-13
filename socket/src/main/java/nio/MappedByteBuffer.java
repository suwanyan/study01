package nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * MappedByteBuffer 可以让文件直接在内存（堆外内存）修改，操作系统不需要再拷贝一次
 * @author suwanyan
 * @date 2021/2/2 18:57
 */
public class MappedByteBuffer {
    public static void main(String[] args) throws IOException {
        /**
         * 平常创建流对象关联文件,开始读文件或者写文件都是从头开始的,
         * 不能从中间开始,如果是开多线程下载一个文件我们之前学过的FileWriter或者FileReader等等都无法完成,
         * 而当前介绍的RandomAccessFile他就可以解决这个问题,因为它可以指定位置读,
         * 指定位置写的一个类,通常开发过程中,多用于多线程下载一个大文件
         */
        RandomAccessFile randomAccessFile = new RandomAccessFile("D:\\swy\\study\\OneHundredMillion\\socket\\src\\1.txt", "rw");

        FileChannel fileChannel = randomAccessFile.getChannel();

        /**
         * 参数1：读写模式
         * 参数2:0，可以直接修改的起始位置
         * 参数3:3，映射到内存的大小，即将1.txt映射多少字节映射到内存
         * 可以直接修改的范围是0-5
         */
        java.nio.MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte)'H');
        mappedByteBuffer.put(3, (byte)'S');
        randomAccessFile.close();
        System.out.println("修改成功");
    }
}
