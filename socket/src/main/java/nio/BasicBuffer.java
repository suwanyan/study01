package nio;

import java.nio.IntBuffer;

/**
 * @author suwanyan
 * @date 2021/1/25 16:52
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //创建一个buffer，大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        //如何从buffer读取数据
        //读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
