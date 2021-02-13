package oom;

import java.util.TimerTask;

/**
 * @author suwanyan
 * @date 2020/12/13 20:17
 */
public class TaskByTimer extends TimerTask {
    boolean pan = false;

    public void run() {
        try {
            if (pan) {
              System.out.println("耶耶耶");
            } else {
                pan = true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
