package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author suwanyan
 * @date 2021/1/25 12:17
 */
public class BIOServer {
    public static void main(String[] args) throws IOException {

        //1、 创建一个线程池
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

        //创建serversocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动");

        while (true) {
            System.out.println("线程 id = " + Thread.currentThread().getId() + " 名称 = " + Thread.currentThread().getName() + "等待连接......");
            final Socket socket = serverSocket.accept();
            System.out.println("客户端连接");
            //2、如果有客户端连接，就为它创建一个线程与之通讯（）
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //可以和客户端通讯
                    System.out.println("执行线程信息 id = " + Thread.currentThread().getId() + " 名称 = " + Thread.currentThread().getName());
                    handler(socket);
                }
            });
        }

    }
    public static void handler (Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            //通过socket获取一个输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端发送的数据
            while (true) {
                System.out.println("线程信息 id = " + Thread.currentThread().getId() + " 名称 = " + Thread.currentThread().getName() + "read...");
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read)); //输出客户端数据
                } else {
                    break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("关闭socket连接");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
