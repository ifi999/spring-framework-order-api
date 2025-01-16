package com.example.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

// web.xml 대신 애플리케이션 초기화
public class WebInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(WebInitializer.class);

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        // 애너테이션 기반 설정 사용
        final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.example.spring.config");

        // DispatcherServlet 생성
        final DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        final String servletName = "dispatcher";
        if (servletContext.getServletRegistration(servletName) == null) {
            // 컨텍스트에 DispatcherServlet 등록
            final ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
            // 서블릿 초기화 우선순위 설정 - 1 : 높은 우선 순위
            registration.setLoadOnStartup(1);
            // 루트 경로는 "/"
            registration.addMapping("/");
        } else {
            logger.info("## Servlet '" + servletName + "' is already registered.");
        }
    }

}
