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

import java.io.IOException;
import java.net.ConnectException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PageServiceImpl implements PageService {

    private PageDao pageDao = new PageDaoImpl();

    @Override
    public JSONObject getPageInfo(String url) {
        String data = null;
        try {
            Document doc = Jsoup.connect(url).get();
            String html = doc.head().html();
            String patt1 = "window.__playinfo__=";
            String patt2 = "window.__INITIAL_STATE__=";
            int start = html.indexOf(patt1);
            int end = html.indexOf(patt2);
            data = html.substring(start + patt1.length(), end);
            data = data.replace("<script>", "").replace("</script>", "").trim();
        } catch (ConnectException e) {
            System.out.println("getPageInfo 重新连接中...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            getPageInfo(url);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }
        return JSONObject.fromObject(data);
    }

    @Override
    public int insertPage(Page page) {
        return pageDao.insertPage(page);
    }

    @Override
    public Map<String, Object> getPageInfoMap(String url) {
        JSONObject pageInfo = null;
        try {
            pageInfo = getPageInfo(url);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new RuntimeException(e);
        }
        Map<String, Object> map = new HashMap<>();

        int pageNo = 1;
        if (url.contains("p=")) {
            pageNo = Integer.parseInt(url.split("p=")[1]);
        }
        String bvId = url.split("\\?")[0].split("/")[url.split("/").length-1];

        JSONObject data = pageInfo.getJSONObject("data");
        JSONObject dash = data.getJSONObject("dash");
        JSONObject video = dash.getJSONArray("video").getJSONObject(0);

        map.put("pageNo", pageNo);
        map.put("bvId", bvId);
        map.put("quality", data.getJSONArray("accept_description").get(0));
        map.put("duration", dash.get("duration"));
        map.put("width", video.get("width"));
        map.put("height", video.get("height"));
        map.put("videoUrl", video.getJSONArray("backupUrl").get(0));
        map.put("audioUrl", dash.getJSONArray("audio").getJSONObject(0).getJSONArray("backupUrl").get(0));

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
}
