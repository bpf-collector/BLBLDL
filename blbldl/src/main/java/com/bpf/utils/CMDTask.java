package com.bpf.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CMDTask{

    private String command;
    private String esStr;
    private String isStr;

    public CMDTask() {}

    public CMDTask(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getEsStr() {
        return esStr;
    }

    public String getIsStr() {
        return isStr;
    }

    public void run() throws InterruptedException, IOException {
        Process process = Runtime.getRuntime().exec(command);
        int exitVal = process.waitFor();
        this.esStr = getResult(process.getErrorStream());
        this.isStr = getResult(process.getInputStream());
        if (exitVal != 0) {
            throw new RuntimeException("命令执行失败");
        }

    }

    private String getResult(InputStream is) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ( (line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
