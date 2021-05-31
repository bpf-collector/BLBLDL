package com.bpf.thread;

/**
 * 合成视频
 */
public class Merge extends Thread {

    private FileNameListQueue queue;

    public Merge(FileNameListQueue queue, String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            queue.merge();
        }
    }
}
