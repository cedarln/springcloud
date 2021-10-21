package com.linan;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NetUtil;
import cn.hutool.core.util.NumberUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ProductDataServiceApplication {
    public static void main( String[] args ) {
        System.out.println( "Hello World ProductDataServiceApplication!" );
        int port = 0;
        int defaultPort = 8001;
        Future<Integer> future = ThreadUtil.execAsync(() ->{
                int p = 0;
                System.out.printf("please input port in 5 seconds or it'll use %d. 8001~8003 are recommended.%n", defaultPort);
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    String strPort = scanner.nextLine();
                    if(!NumberUtil.isInteger(strPort)) {
                        System.err.println("Only Numbers!");
                        continue;
                    } else {
                        p = Convert.toInt(strPort);
                        scanner.close();
                        break;
                    }
                }
               return p;
        });
        try{
            port = future.get(5, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            port = defaultPort;
        }

        if (!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("port %d is occupied, cannot start.", port);
            System.exit(1);
        }

        new SpringApplicationBuilder(ProductDataServiceApplication.class)
                .properties("server.port=" + port)
                .run(args);
    }

    @Bean
    public Sampler defaultSampler() {//服务链路追踪
        return Sampler.ALWAYS_SAMPLE; //持续抽样
    }
}
