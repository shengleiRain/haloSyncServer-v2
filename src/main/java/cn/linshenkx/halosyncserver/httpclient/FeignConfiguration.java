package cn.linshenkx.halosyncserver.httpclient;

import cn.linshenkx.halosyncserver.manager.HaloAuthManager;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

public class FeignConfiguration {

    @Resource
    private HaloAuthManager haloAuthManager;

    @Bean
    Logger.Level feignLoggerLevel() {
        //这里记录所有，根据实际情况选择合适的日志level
        return Logger.Level.FULL;
    }

    @Bean
    RequestInterceptor getRequestInterceptor() {
        return template -> {
            template.header("Authorization", haloAuthManager.getBaseToken());
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new HaloErrorDecoder(haloAuthManager);
    }


}
