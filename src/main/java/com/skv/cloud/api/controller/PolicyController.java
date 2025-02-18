package com.skv.cloud.api.controller;

import com.skv.cloud.api.services.PolicyService;
import com.skv.cloud.api.services.aws.S3ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class PolicyController {

    private S3ClientService s3ClientService;
    private PolicyService policyService;


    @GetMapping("/listFiles")
    public List<String> listFiles() {
        return s3ClientService.listFiles();
    }

    @DeleteMapping("/deleteFile")
    public void listFiles(String fileKey) {
        s3ClientService.deleteFile(fileKey);
        log.info("File deleted successfully: {}", fileKey);
    }

    @GetMapping("/savePolicyXmlIntoS3")
    public String savePolicyXmlIntoS3(Long id) {
        MDC.put("id", String.valueOf(id));
        // Save the policy XML into S3
       policyService.savePolicyXmlIntoS3(id);
       MDC.clear();
       return "Policy XML saved into S3 successfully";
    }
}
