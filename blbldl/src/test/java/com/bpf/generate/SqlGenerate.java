package com.bpf.generate;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SqlGenerate {

    @Test
    public void generate() throws Exception {
        List<String> warnings = new ArrayList<>();
        File file = new File("./src/main/resources/mybatis-config.xml");
        Configuration config = new ConfigurationParser(warnings).parseConfiguration(file);
        DefaultShellCallback callback = new DefaultShellCallback(true);
        MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
        generator.generate(null);
    }

    @Test
    public void testDownload() throws IOException {
        String url = "https://cn-gdgz-fx-bcache-13.bilivideo.com/upgcxcode/78/33/375203378/375203378-1-64.flv?e=ig8euxZM2rNcNbRVhwdVhwdlhWdVhwdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEV4NC8xNEV4N03eN0B5tZlqNxTEto8BTrNvNeZVuJ10Kj_g2UB02J0mN0B5tZlqNCNEto8BTrNvNC7MTX502C8f2jmMQJ6mqF2fka1mqx6gqj0eN0B599M=&uipk=5&nbs=1&deadline=1637310582&gen=playurlv2&os=bcache&oi=3725592578&trid=0000d475d653075b434eb04d009c449dafe8u&platform=pc&upsig=509107dd20ebeb11edd82bbe6b3fa2bc&uparams=e,uipk,nbs,deadline,gen,os,oi,trid,platform&cdnid=42413&mid=0&bvc=vod&nettype=0&orderid=0,3&agrr=1&bw=38191&logo=80000000";
        String path = "E:\\test.flv";
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("referer", "https://player.bilibili.com/");

        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path));
        byte [] buffer = new byte[1024];
        int len = 0;
        while ((len = bis.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.flush();

    }
}