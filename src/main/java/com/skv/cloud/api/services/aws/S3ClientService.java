package com.skv.cloud.api.services.aws;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3ClientService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;



    public void uploadFile(String key, Path filePath) {
        s3Client.putObject(b -> b.bucket(this.bucketName).key(key), filePath);
    }

    public void downloadFile(String key, Path destinationPath) {
        s3Client.getObject(b -> b.bucket(this.bucketName).key(key), ResponseTransformer.toFile(destinationPath));
    }

    public void deleteFile(String key) {
        s3Client.deleteObject(b -> b.bucket(this.bucketName).key(key));
    }

    public List<String> listFiles() {
        ListObjectsResponse response = s3Client.listObjects(b -> b.bucket(this.bucketName));
        return response.contents().stream().map(S3Object::key).collect(Collectors.toList());
    }


}
