package org.example.tree.global.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.example.tree.global.feign.exception.FeignExceptionErrorDecoder;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class NcpFeignConfiguration {
    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return new ColonInterceptor();
    }
    public static class ColonInterceptor implements RequestInterceptor {
        @Override
        public void apply(RequestTemplate template) {
            template.uri(template.path().replaceAll("%3A", ":"));
        }
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return  new FeignExceptionErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
