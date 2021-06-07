package com.bpf;

import com.bpf.utils.FileUtil;
import org.junit.Test;

public class FileUtilTest {

    @Test
    public void replaceSign() {
        System.out.println(FileUtil.replaceSign("01:12?|>m/n 34"));
    }
}
