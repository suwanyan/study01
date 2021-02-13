package nio;

import java.nio.ByteBuffer;

/**
 * @author suwanyan
 * @date 2021/2/2 18:50
 */
public class NIOFileChannelPutGet {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        for (int i = 1; i < 64; i++) {
            byteBuffer.put((byte)i);
        }

        byteBuffer.flip();

        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
