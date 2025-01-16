package com.example.spring;

import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpringApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringApplication.class);

    public static void main(String[] args) throws Exception {
        final Tomcat tomcat = new Tomcat();
        // 포트 설정
        tomcat.setPort(8080);

        // 웹 애플리케이션 등록 - 임시 디렉터리 경로 (src/main/webapp 로 변경?)
        tomcat.addWebapp("", System.getProperty("java.io.tmpdir"));

        logger.info("## Tomcat Start");
        tomcat.start();
        logger.info("## Tomcat server started on port: {}", tomcat.getConnector().getPort());

        tomcat.getServer().await();
    }

}
