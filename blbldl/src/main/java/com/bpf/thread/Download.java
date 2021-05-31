package com.bpf.thread;

/**
 * 下载视频、音频
 */
public class Download extends Thread {

    private FileNameListQueue queue;

    public Download(FileNameListQueue queue, String name) {
        super(name);
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            queue.download();
        }
    }
}
