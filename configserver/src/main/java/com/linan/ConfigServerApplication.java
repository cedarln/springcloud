package com.linan;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
@EnableDiscoveryClient
public class ConfigServerApplication {
    public static void main( String[] args ){
        System.out.println( "Hello ConfigServerApplication!" );
        int port = 8030;
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d is occupied, CANNOT start it.", port);
            System.exit(1);
        }
        new SpringApplicationBuilder(ConfigServerApplication.class)
                .properties("server.port=" + port)
                .run(args);
    }
}
