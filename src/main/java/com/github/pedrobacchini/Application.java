package com.github.pedrobacchini;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.TomcatHttpHandlerAdapter;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.adapter.WebHttpHandlerBuilder;

import javax.servlet.Servlet;

@Configuration
@ComponentScan
@EnableWebFlux
public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);  // (1)

        Tomcat tomcatServer = context.getBean(Tomcat.class);
        tomcatServer.start();

        System.out.println("Press ENTER to exit.");
        System.in.read();
    }

    @Bean
    public Tomcat embededTomcatServer(ApplicationContext context) {
        HttpHandler handler = WebHttpHandlerBuilder.applicationContext(context).build();

        // Tomcat and Jetty (also see notes below)
        Servlet servlet = new TomcatHttpHandlerAdapter(handler);

        Tomcat tomcatServer = new Tomcat();
        tomcatServer.setHostname("localhost");
        tomcatServer.setPort(8080);
        Context rootContext = tomcatServer.addContext("", System.getProperty("java.io.tmpdir"));
        Tomcat.addServlet(rootContext, "httpHandlerServlet", servlet);
        rootContext.addServletMappingDecoded("/", "httpHandlerServlet");

        return tomcatServer;
    }

}
