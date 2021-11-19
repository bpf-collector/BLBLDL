package com.bpf.thread;

import com.bpf.pojo.FileName;
import com.bpf.utils.WebUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件名队列
 */
public class FileNameListQueue {
    private int MAX_COUNT = 4;
    // 存储待下载的文件名
    private List<FileName> readyList = new ArrayList<>();
    // 存储已下载的文件名
    private List<FileName> doneList = new ArrayList<>();

    // public static ThreadLocal<FileName> threadLocal = new ThreadLocal<>();
    protected static Map<String, FileName> map = new HashMap<>();

    public FileNameListQueue() {}

    public synchronized void addReadyList(List<FileName> readyList) {
        System.out.println("[FileNameListQueue] 添加了" + readyList.size() + "个下载任务！");
        this.readyList.addAll(readyList);
        notifyAll();
    }

    /**
     * 下载
     */
    public void download() {
        synchronized (this) {
            if (!readyList.isEmpty()) {
                // System.out.println("readyList = " + readyList);
                // 取出第一个
                FileName fileName = readyList.remove(0);
                // threadLocal.set(fileName);
                map.put(Thread.currentThread().getName(), fileName);
                // System.out.println("取出一个存入 map = " + map);
            }

            if (!map.containsKey(Thread.currentThread().getName())) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (readyList.size() > 1) {
                notifyAll();
            }
        }


        if (map.get(Thread.currentThread().getName()) != null) {
            // 下载视频、音频
            /*
            System.out.println(Thread.currentThread().getName() + ": 下载中... " + map.get(Thread.currentThread().getName()));
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": 下载完成 " + map.get(Thread.currentThread().getName()));
             */
            downloadAV(map.get(Thread.currentThread().getName()));
        }

        synchronized (this) {
            // 将map添加到已下载队列
            if (map.get(Thread.currentThread().getName()) != null) {
                doneList.add(map.get(Thread.currentThread().getName()));
                map.remove(Thread.currentThread().getName());
                // System.out.println(">> doneList = " + doneList);

                notifyAll();
            }
        }
    }

    /**
     * 合成
     */
    public synchronized void merge() {
        System.out.println(Thread.currentThread().getName() + ": doneList{" + doneList.size() + "} = " + doneList);
        if (doneList.isEmpty() || doneList.contains(null)) {
            while (doneList.contains(null)) {
                doneList.remove(null);
                // System.out.println("移除一个null");
            }
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            // System.out.println("开始合成");
            // 取出第一个
            FileName fileName = doneList.get(0);
            doneList.remove(0);

            // 调用程序进行整合
            /*
            System.out.println("\t" + Thread.currentThread().getName() + ": 整合中... " + fileName);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("\t" + Thread.currentThread().getName() + ": 整合完成 " + fileName);
            */
            WebUtil.mergeFile(fileName.getAudioPath(), fileName.getVideoPath(), fileName.getOutputPath(), fileName.getSleep());

            System.out.println("[FileNameListQueue] 文件 " + fileName.getOutputPath() + " 下载成功！");
        }
    }

    /**
     * 下载视频、音频
     */
    public void downloadAV(FileName fileName) {

        int count = 0;

        while (!WebUtil.downloadFile(fileName.getVideoUrl(), fileName.getVideoPath(), fileName.getSourceUrl())) {
            count++;
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == MAX_COUNT) {
                break;
            }
            WebUtil.downloadFile(fileName.getVideoUrl(), fileName.getVideoPath(), fileName.getSourceUrl());
        }

        count = 0;
        while (!WebUtil.downloadFile(fileName.getAudioUrl(), fileName.getAudioPath(), fileName.getSourceUrl())) {
            count++;
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == MAX_COUNT) {
                break;
            }
            WebUtil.downloadFile(fileName.getAudioUrl(), fileName.getAudioPath(), fileName.getSourceUrl());
        }
    }
}
