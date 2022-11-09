package com.senro.interrupt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterruptedDemo {

    private static Logger logger = LoggerFactory.getLogger("l.InterruptedDemo");

    /**
     * 结论：运行状态多次调用interrupt方法，打断标记始终为true，
     *      且不会影响线程的正常执行
     */
    public static void main(String[] args) {
        logger.debug("start");
        Thread.currentThread().interrupt();
        logger.debug(Thread.currentThread().isInterrupted() + "");
        Thread.currentThread().interrupt();
        logger.debug(Thread.currentThread().isInterrupted() + "");
        Thread.currentThread().interrupt();
        logger.debug(Thread.currentThread().isInterrupted() + "");
    }
}
