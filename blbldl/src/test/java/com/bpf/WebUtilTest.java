package com.bpf;

import com.bpf.utils.WebUtil;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class WebUtilTest {

    @Test
    public void connectURL() throws IOException {
        String url = "https://upos-sz-mirrorks3.bilivideo.com/upgcxcode/11/26/52432611/52432611_da2-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1619435232&gen=playurlv2&os=ks3bv&oi=3085848335&trid=ff1d2188d1744bddb3d098aca8145aaeu&platform=pc&upsig=219fb205694afe08ea571565f423061a&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0&orderid=1,3&agrr=1&logo=40000000";
               url = "https://upos-sz-mirrorks3.bilivideo.com/upgcxcode/11/26/52432611/52432611_da2-1-30080.m4s?e=ig8euxZM2rNcNbdlhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1619446825&gen=playurlv2&os=ks3bv&oi=3401598977&trid=f4f72e02c8654d4e81279aaddbf495b6u&platform=pc&upsig=aecaa96239ac4b3a8723d50e44c156c9&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&mid=0&orderid=1,3&agrr=0&logo=40000000";
        String refererUrl = "http://bilibili.com/BV1PW411X75p?p=3";
        BufferedInputStream bis = WebUtil.connectURL(url, refererUrl);
        System.out.println(bis);
    }

    @Test
    public void deleteFile() {
        String filepath = "E:\\tmp\\13_尚硅谷_修改及自定义模板_.mp4";
        WebUtil.deleteFile(filepath);
    }
}