package com.bpf.servlet;

import com.bpf.pojo.Course;
import com.bpf.pojo.FileName;
import com.bpf.pojo.Owner;
import com.bpf.service.CourseService;
import com.bpf.service.OwnerService;
import com.bpf.service.PageService;
import com.bpf.service.impl.CourseServiceImpl;
import com.bpf.service.impl.OwnerServiceImpl;
import com.bpf.service.impl.PageServiceImpl;
import com.bpf.thread.Download;
import com.bpf.thread.FileNameListQueue;
import com.bpf.thread.Merge;
import com.bpf.utils.WebUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CourseServlet extends BaseServlet {

    private static FileNameListQueue queue;
    private static int downloadThread = 4;

    private CourseService courseService = new CourseServiceImpl();
    private OwnerService ownerService = new OwnerServiceImpl();
    private PageService pageService = new PageServiceImpl();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // 开始之前，清空缓存的数据
        String tmpPath = WebUtil.getProp().getProperty("tmpPath");
        File tmpDirectory = new File(tmpPath);
        if (!tmpDirectory.exists()) {
            tmpDirectory.mkdirs();
        } else {
            for (String file: tmpDirectory.list()) {
                WebUtil.deleteFile(file);
            }
        }

        String dlt = WebUtil.getProp().getProperty("downloadThread");
        if (dlt.length() != 0) {
            this.downloadThread = Integer.parseInt(dlt);
        }

        // 初始化下载线程、合成线程
        queue = new FileNameListQueue();
        new Merge(queue, "Merge").start();
        for (int i = 1; i <= this.downloadThread; i++) {
            new Download(queue, "Download_" + i).start();
        }
    }


    /**
     * 用户查询课程
     *
     * 1. 将课程信息保存到数据库
     *  1.1 获取url
     *  1.2 获取json数据，提取课程信息，保存到数据库
     * 2. 将课程信息回显到jsp网页
     *  2.1 从数据库查询并回显
     */
    protected void searchCourse(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String url = req.getParameter("url");
        String bvId = WebUtil.getBvIdFromUrl(url);

        courseService.insertCourse(url);
        Course course = courseService.getCourseByBvId(bvId);
        Owner owner = ownerService.getOwnerById(course.getOwnerId());

        req.getSession().setAttribute("course", course);
        req.getSession().setAttribute("owner", owner);

        // 使用重定向避免重复提交表单，造成一直访问外部数据和读写数据库
        resp.sendRedirect(req.getContextPath() + "/jsp/result.jsp");
    }

    /**
     * 下载选中的视频
     */
    protected void downloadPages(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String[] downloads = req.getParameterValues("download");
        System.out.println("downloads = " + Arrays.toString(downloads));
        String tmpPath = WebUtil.getProp().getProperty("tmpPath");
        String outPath = req.getParameter("outPath");


        int[] pageNos = Arrays.stream(downloads).mapToInt(Integer::parseInt).toArray();
        Course course = (Course) req.getSession().getAttribute("course");
        List<FileName> fileNameList = pageService.getFileNameByPageNo(course, pageNos, tmpPath, outPath, 5000);

        queue.addReadyList(fileNameList);

        resp.sendRedirect(req.getContextPath() + "/jsp/result.jsp");

    }
}
