package com.gwf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.gwf.*"})
public class GwfAutoWorkTFApplication {

    public static void main(String[] args) {
        SpringApplication.run(GwfAutoWorkTFApplication.class, args);
    }

}
