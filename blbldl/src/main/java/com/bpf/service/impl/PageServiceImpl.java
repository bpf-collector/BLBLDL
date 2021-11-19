package com.bpf.service.impl;

import com.bpf.dao.PageDao;
import com.bpf.dao.impl.PageDaoImpl;
import com.bpf.pojo.Course;
import com.bpf.pojo.FileName;
import com.bpf.pojo.Page;
import com.bpf.service.PageService;
import com.bpf.utils.FileUtil;
import com.bpf.utils.WebUtil;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PageServiceImpl implements PageService {

    private PageDao pageDao = new PageDaoImpl();

    @Override
    public JSONObject getPageInfo(String bvId, String cid) {
        String urls = "http://api.bilibili.com/x/player/playurl?bvid=" + bvId + "&cid=" + cid + "&qn=80&fnval=16";

        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urls);
            URLConnection connection = url.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str = null;
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }
        } catch (ConnectException e) {
            System.out.println("[PageServiceImpl] getPageInfo 重新连接中...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            getPageInfo(bvId, cid);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return JSONObject.fromObject(builder.toString());
    }

    @Override
    public int insertPage(Page page) {
        return pageDao.insertPage(page);
    }

    @Override
    public Map<String, Object> getPageInfoMap(String bvId, String aId, int pageNo) {
        String cid = getCidByBvIdAndPageNo(bvId, aId, pageNo);
        JSONObject pageInfo = getPageInfo(bvId, cid);

        Map<String, Object> map = new HashMap<>();

        JSONObject data = pageInfo.getJSONObject("data");
        JSONObject dash = data.getJSONObject("dash");
        JSONObject video = dash.getJSONArray("video").getJSONObject(0);

        map.put("pageNo", pageNo);
        map.put("bvId", bvId);
        map.put("quality", data.getJSONArray("accept_description").get(0));
        map.put("duration", dash.get("duration"));
        map.put("width", video.get("width"));
        map.put("height", video.get("height"));
        map.put("videoUrl", video.getJSONArray("baseUrl").get(0));
        map.put("audioUrl", dash.getJSONArray("audio").getJSONObject(0).getJSONArray("baseUrl").get(0));

        return map;
    }

    @Override
    public List<FileName> getFileNameByPageNo(Course course, int[] pageNo, String tmpPath, String outPath, int sleep) {
        Map<Integer, Page> pages = course.getPages();
        List<FileName> fileNames = new LinkedList<>();
        for (int num: pageNo) {
            Map<String, String> map = pageDao.queryUrlsByBvIdAndPageNo(course.getBvId(), num);
            String videoUrl = map.get("videoUrl");
            String audioUrl = map.get("audioUrl");
            String title = FileUtil.replaceSign(course.getTitle());
            String videoPath = Paths.get(tmpPath,  title + "_" + pages.get(num).getPart() + "_v.mp4").toString();
            String audioPath = Paths.get(tmpPath, title + "_" + pages.get(num).getPart() + "_a.mp3").toString();
            String outputPath = Paths.get(outPath, title, pages.get(num).getPart() + ".mp4").toString();

            FileName fileName = new FileName(WebUtil.BASEURL + course.getBvId(), videoUrl, videoPath, audioUrl, audioPath, outputPath, sleep);
            fileNames.add(fileName);
        }

        return fileNames;
    }

    @Override
    public String getCidByBvIdAndPageNo(String bvId, String aid, int pageNo) {
        String cid = sqlGetCidByBvIdAndPageNo(bvId, pageNo);
        if (cid == null || "null".equals(cid) || cid.isEmpty()) {
            cid = httpGetCidByBvIdAndPageNo(aid, pageNo);
        }

        return cid;
    }

    public String sqlGetCidByBvIdAndPageNo(String bvId, int pageNo) {
        return pageDao.queryCidByBvIdAndPageNo(bvId, pageNo);
    }

    public String httpGetCidByBvIdAndPageNo(String aId, int pageNo) {
        String urls = "http://api.bilibili.cn/view?id=" + aId + "&page=" + pageNo + "&appkey=ba02c181c8820321";

        BufferedReader br = null;
        StringBuilder builder = new StringBuilder();
        try {
            URL url = new URL(urls);
            URLConnection connection = url.openConnection();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String str = null;
            while ((str = br.readLine()) != null) {
                builder.append(str);
            }
        } catch (ConnectException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            httpGetCidByBvIdAndPageNo(aId, pageNo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return (String) JSONObject.fromObject(builder.toString()).get("cid");
    }
}
