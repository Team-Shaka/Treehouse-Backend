package org.example.tree.global.feign.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class NcpService {
    @Value("${naver-sms.accessKey}")
    private String accessKey;

    @Value("${naver-sms.secretKey}")
    private String secretKey;

    @Value("${naver-sms.serviceId}")
    private String serviceId;

    @Value("${naver-sms.senderPhone}")
    private String phone;

}
