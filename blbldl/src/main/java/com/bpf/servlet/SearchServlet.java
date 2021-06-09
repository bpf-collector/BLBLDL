package com.bpf.servlet;

import com.bpf.pojo.Course;
import com.bpf.pojo.Owner;
import com.bpf.service.SearchService;
import com.bpf.service.impl.SearchServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchServlet extends BaseServlet {

    private SearchService searchService = new SearchServiceImpl();


    protected void getAllCourse(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Course> courseList = searchService.getAllCourse();
        List<Owner> ownerList = searchService.getAllOwner();
        Map<Integer, Owner> ownerMap = new HashMap<>();
        for (Owner owner: ownerList) {
            ownerMap.put(owner.getId(), owner);
        }

        req.getSession().setAttribute("courseList", courseList);
        req.getSession().setAttribute("ownerMap", ownerMap);
        req.getSession().removeAttribute("ownerList");

        resp.sendRedirect(req.getContextPath() + "/jsp/search.jsp");
    }

    protected void getAllOwner(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Owner> ownerList = searchService.getAllOwner();

        req.getSession().setAttribute("ownerList", ownerList);

        resp.sendRedirect(req.getContextPath() + "/jsp/search.jsp");
    }

    protected void getCourseLikeTitle(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String title = req.getParameter("title");
        List<Course> courseList = searchService.getCourseLikeTitle(title);

        req.getSession().setAttribute("courseList", courseList);
        req.getSession().removeAttribute("ownerList");

        resp.sendRedirect(req.getContextPath() + "/jsp/search.jsp");
    }

    protected void getCourseByOwnerId(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int ownerId = Integer.parseInt(req.getParameter("ownerId"));
        List<Course> courseList = searchService.getCourseByOwnerId(ownerId);

        req.getSession().setAttribute("courseList", courseList);
        req.getSession().removeAttribute("ownerList");

        resp.sendRedirect(req.getContextPath() + "/jsp/search.jsp");
    }
}
