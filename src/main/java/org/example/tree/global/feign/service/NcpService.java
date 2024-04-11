package org.example.tree.global.feign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.example.tree.domain.member.dto.MemberResponseDTO;
import org.example.tree.domain.member.entity.redis.PhoneAuth;
import org.example.tree.global.exception.BaseErrorCode;
import org.example.tree.global.exception.GlobalErrorCode;
import org.example.tree.global.exception.PhoneAuthException;
import org.example.tree.global.feign.NcpFeignClient;
import org.example.tree.global.feign.dto.NcpDto;
import org.example.tree.global.redis.repository.PhoneAuthRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NcpService {

    private final PhoneAuthRepository phoneAuthRepository;
    private final NcpFeignClient ncpFeignClient;

    @Value("${naver-sms.accessKey}")
    private String accessKey;

    @Value("${naver-sms.secretKey}")
    private String secretKey;

    @Value("${naver-sms.serviceId}")
    private String serviceId;

    @Value("${naver-sms.senderPhone}")
    private String phone;

    public String makeSignature(Long time) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ this.serviceId+"/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);
        return encodeBase64String;
    }


    @Transactional
    public MemberResponseDTO.checkPhoneAuth authNumber(Integer authNum, String phoneNum) {

        PhoneAuth phoneAuth = phoneAuthRepository.findById(phoneNum).orElseThrow(() -> new PhoneAuthException(GlobalErrorCode.PHONE_AUTH_NOT_FOUND));

        if (!phoneAuth.getAuthNum().equals(authNum)) {
            throw new PhoneAuthException(GlobalErrorCode.PHONE_AUTH_WRONG);
        } else{
            LocalDateTime nowTime = LocalDateTime.now();

            long timeCheck = ChronoUnit.MINUTES.between(phoneAuth.getAuthNumTime(), nowTime);
            if (timeCheck >= 5) {
                throw new PhoneAuthException(GlobalErrorCode.PHONE_AUTH_TIMEOUT);
            }

        }
        phoneAuthRepository.deleteById(phoneAuth.getPhoneNum());

        return MemberResponseDTO.checkPhoneAuth.builder()
                .authenticated(true)
                .build();
    }

    @Transactional
    public MemberResponseDTO.checkSentSms sendSms(String targetNumber) throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Long time = System.currentTimeMillis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", accessKey);
        headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

        List<NcpDto.MessageDto> messages = new ArrayList<>();
        String randomNumber = getRandomNumber();
        StringBuilder sb = new StringBuilder();
        sb.append("[TreeHouse] 인증번호 : ["+randomNumber+"]를 입력해주세요");
        String content = String.valueOf(sb);
        messages.add(NcpDto.MessageDto.builder()
                .to(targetNumber)
                .content(content)
                .build());

        NcpDto.SmsRequestDto request = NcpDto.SmsRequestDto.builder()
                .type("SMS")
                .contentType("COMM")
                .countryCode("82")
                .from(phone)
                .content(content)
                .messages(messages)
                .build();


        ncpFeignClient.sendSms(serviceId, headers,request);

        PhoneAuth phoneAuth = PhoneAuth.builder()
                .phoneNum(targetNumber)
                .authNumTime(LocalDateTime.now())
                .authNum(Integer.valueOf(randomNumber))
                .build();
        phoneAuthRepository.save(phoneAuth);

        return MemberResponseDTO.checkSentSms.builder()
                .messageSent(true)
                .build();
    }

    public String getRandomNumber() {
        Random random = new Random();
        int rand= random.nextInt(900000) + 100000;
        return String.valueOf(rand);
    }
}
