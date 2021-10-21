package com.linan.util;

import cn.hutool.http.HttpUtil;

import java.util.HashMap;

public class FreshConfigUtil {
    public static void main(String[] args) {
        HashMap<String, String> headers =new HashMap<>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        System.out.println("因为要去git获取，还要刷新config-server, 会比较卡，所以一般会要好几秒才能完成，请耐心等待");

        //让config-server去git获取最新的配置信息，并把此信息广播给集群里的视图微服务
        String result = HttpUtil.createPost("http://localhost:8012/actuator/bus-refresh").addHeaders(headers).execute().body();
        System.out.println("result:"+result);
        System.out.println("refresh 完成");

//        因为视图服务进行了改造，支持了 rabbitMQ, 那么在默认情况下，它的信息就不会进入 Zipkin了。 在Zipkin 里看不到视图服务的资料了。
//        为了解决这个问题，在启动 Zipkin 的时候 带一个参数就好了：--zipkin.collector.rabbitmq.addresses=localhost
        //java -jar zipkin-server-2.10.1-exec.jar --zipkin.collector.rabbitmq.addresses=localhost
    }
}
