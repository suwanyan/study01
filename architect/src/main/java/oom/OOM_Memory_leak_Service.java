package oom;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

// -Xmx8m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/temp/202008.hprof
public class OOM_Memory_leak_Service {

    // 系统用户一万以内，【日志CSV文件】 2G，找出这段日志内，出现锅的用户
    public ArrayList distinct() throws InterruptedException {
        ArrayList userList = new ArrayList(); // 预计 1W条数据 --- 谁往里面放数据
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {// 创建了新的线程去执行具体的代码逻辑
            try {
                for (int j = 0; j < 100; j++) {
                    for (int i = 0; i < 10000; i++) {
                        User user = new User(String.valueOf(i)); // 读取文件---> 转变为 JAVA对象 ---> 判断 --> 存入list

                        // 如果cache中没有，则存入cache集合
                        if (!userList.contains(user)) { // 理论上：存储不超过1W条 用户对象
                            userList.add(user); // 往里面放数据，导致User对象 堆积太多了
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }).start();

        countDownLatch.await(); // 等待 计数器 归 0 -- 没有归零 -- 阻塞等待
        return userList;
    }

    public static void main(String[] args) throws InterruptedException {
        new OOM_Memory_leak_Service().distinct();
    }
}
