package oom;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author suwanyan
 * @date 2020/12/13 19:52
 */
public class TaskTest {

    public static void main(String[] args) {
        /**
         * Runnable：实现了Runnable接口，jdk就知道这个类是一个线程
         */
        Runnable runnable = new Runnable() {
            //创建 run 方法
            public void run() {
                // task to run goes here
                System.out.println("Hello, stranger");
            }
        };
        // ScheduledExecutorService:是从Java SE5的java.util.concurrent里，
        // 做为并发工具类被引进的，这是最理想的定时任务实现方式。
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(runnable, 10, TimeUnit.SECONDS);
    }

}