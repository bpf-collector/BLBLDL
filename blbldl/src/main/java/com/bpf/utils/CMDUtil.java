package com.bpf.utils;

import java.io.IOException;

public class CMDUtil {

    /**
     * 执行命令
     * @param command
     * @param sleep
     * @return
     */
    public static String execute(String command, long sleep) {
        CMDTask task = new CMDTask(command);

        try {
            task.run();
            Thread.sleep(sleep);
            return task.getIsStr();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return task.getEsStr();
    }
}
