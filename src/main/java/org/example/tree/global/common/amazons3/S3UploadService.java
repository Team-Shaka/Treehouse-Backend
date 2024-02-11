package org.example.tree.global.common.amazons3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    private  final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String uploadImage(MultipartFile image) throws Exception {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        String originalFilename = image.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "images/" + storeFileName; // 폴더명+파일이름

        try (InputStream inputStream = image.getInputStream()) {
            amazonS3Client.putObject(
                    new PutObjectRequest(bucket, key, inputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        return storeFileUrl;
    }
}
