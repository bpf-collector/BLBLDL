package com.bpf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bpf.mapper")
public class BlbldlDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlbldlDemoApplication.class, args);
	}

}
