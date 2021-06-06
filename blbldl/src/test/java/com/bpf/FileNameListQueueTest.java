package com.bpf;

import com.bpf.pojo.FileName;
import com.bpf.thread.Download;
import com.bpf.thread.FileNameListQueue;
import com.bpf.thread.Merge;
import org.junit.Test;

import java.util.ArrayList;

public class FileNameListQueueTest {

    @Test
    public void test() {
        /**
         * 第一次测试：测试代码的可行性
         */
        // ArrayList<FileName> list = new ArrayList<>();
        // for (int i = 0; i < 5; i++) {
        //     list.add(new FileName(i));
        // }
        // FileNameListQueue queue = new FileNameListQueue(list);
        //
        // Merge merge = new Merge(queue, "Merge");
        // Download down1 = new Download(queue, "Down1");
        // Download down2 = new Download(queue, "Down2");
        //
        // merge.start();
        // down1.start();
        // down2.start();

        /**
         * 第二次测试：测试下载、合成的可行性
         */
        ArrayList<FileName> list = new ArrayList<>();
        String tmpPath = "E:\\tmp";
        FileName fileName = new FileName();
        fileName.setAudioUrl("https://upos-sz-mirrorks3.bilivideo.com/upgcxcode/82/29/52432982/52432982-1-30280.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1619435246&gen=playurlv2&os=ks3bv&oi=3085848335&trid=3c405af71d0d458fbb034c4818f5b7fbu&platform=pc&upsig=cae27cd1f13401737f0200fef0d90c31&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0&orderid=1,3&agrr=1&logo=40000000");
        fileName.setAudioPath(tmpPath + "\\13_尚硅谷_修改及自定义模板_.mp3");
        fileName.setVideoUrl("https://upos-sz-mirrorkodo.bilivideo.com/upgcxcode/82/29/52432982/52432982-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1619435246&gen=playurlv2&os=kodobv&oi=3085848335&trid=3c405af71d0d458fbb034c4818f5b7fbu&platform=pc&upsig=b944a134b1aa3db042b15a03f2810e6b&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0&orderid=1,3&agrr=1&logo=40000000");
        fileName.setVideoPath(tmpPath + "\\13_尚硅谷_修改及自定义模板_.mp4");
        fileName.setSourceUrl("https://www.bilibili.com/BV1PW411X75p?p=13");
        fileName.setOutputPath(tmpPath + "\\13_尚硅谷_修改及自定义模板.mp4");
        fileName.setSleep(5000);
        list.add(fileName);

        FileNameListQueue queue = new FileNameListQueue();
        queue.addReadyList(list);

        new Merge(queue, "Merge").start();
        new Download(queue, "Down").start();
    }
}
