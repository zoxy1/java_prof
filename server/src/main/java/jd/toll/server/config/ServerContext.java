package jd.toll.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({"classpath*:/applicationContext.xml", "classpath*:/spring-security.xml"})
public class ServerContext {

}