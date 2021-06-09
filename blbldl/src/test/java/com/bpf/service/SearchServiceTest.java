package com.bpf.service;

import com.bpf.pojo.Course;
import com.bpf.pojo.Owner;
import com.bpf.service.impl.SearchServiceImpl;
import org.junit.Test;

import java.util.List;

public class SearchServiceTest {

    private SearchService searchService = new SearchServiceImpl();

    @Test
    public void getAllCourse() {
        searchService.getAllCourse().forEach(System.out::println);
    }

    @Test
    public void getCourseLikeTitle() {
        searchService.getCourseLikeTitle("尚硅谷").forEach(System.out::println);
    }

    @Test
    public void getAllOwner() {
        searchService.getAllOwner().forEach(System.out::println);
    }
}
