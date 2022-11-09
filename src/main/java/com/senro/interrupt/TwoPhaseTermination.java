package com.senro.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 两阶段终止模式：
 *      1.正常状态被打断：isInterrupted为true
 *      2.睡眠状态被打断：isInterrupted为false
 * 目标：设计一个监控线程，使它能够优雅的停止（有时间完成资源的释放）。
 */
public class TwoPhaseTermination {

    private static Logger logger = LoggerFactory.getLogger("l.TwoPhaseTermination");

    private Thread monitor;

    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTermination tpt = new TwoPhaseTermination();
        tpt.start();
        Thread.sleep(3500);
        tpt.stop();
    }

    /**
     * 开启监控线程
     */
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                if (current.isInterrupted()) {
                    logger.debug("释放资源~");
                    break;
                }
                try {
                    // 1.睡眠状态被打断
                    Thread.sleep(1000); // 每隔一秒执行一次监控操作
                    // 2.正常状态被打断
                    logger.debug("执行监控记录");
                } catch (InterruptedException e) {
                    // 1.1.正常状态被打断进入catch块，重新设置打断标记为true
                    // 以便下次循环进入if块退出
                    e.printStackTrace();
                    current.interrupt();
                }
            }
        }, "monitor");

        monitor.start();
    }

    /**
     * 停止监控线程
     */
    public void stop() {
        monitor.interrupt();
    }
}
