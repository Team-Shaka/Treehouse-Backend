package org.example.tree.global.feign;

import org.example.tree.global.feign.config.NcpFeignConfiguration;
import org.example.tree.global.feign.dto.NcpDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feign", url = "https://sens.apigw.ntruss.com/sms/v2/services", configuration = NcpFeignConfiguration.class)
public interface NcpFeignClient {
    @PostMapping(value = "/{serviceId}/messages")
    void sendSms(@PathVariable(value = "serviceId") String serviceId,
                 @RequestHeader HttpHeaders headers,
                 @RequestBody NcpDto.SmsRequestDto request);
}
