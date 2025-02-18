package com.skv.cloud.api.services;

import com.skv.cloud.api.model.Policy;
import com.skv.cloud.api.repository.PolicyRepository;
import com.skv.cloud.api.services.aws.S3ClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@AllArgsConstructor
@Slf4j
public class PolicyService {

    private PolicyRepository policyRepository;
    private S3ClientService s3ClientService;


    public Policy getPolicy(Long id) {
        return policyRepository.findById(id).orElse(null);
    }

    public void savePolicyXmlIntoS3(Long id) {
        MDC.put("id", String.valueOf(id));
        log.info("Saving policy XML into S3 for policy id: {}", id);
        // Save the policy XML into S3
        Policy policy = policyRepository.findById(id).orElse(null);
        if (policy != null && policy.getXmlData() != null && policy.getFileKey() != null) {
            try {
                // Create a temporary file
                Path tempFile = Files.createTempFile("policy-", ".xml");
                // Write the xml_data to the temporary file
                Files.write(tempFile, policy.getXmlData());
                // Upload the file to S3 using the file_key
                s3ClientService.uploadFile(policy.getFileKey(), tempFile);
                log.info("Policy XML saved into S3 successfully for policy id: {} and file key {}", id, policy.getFileKey());
                // Delete the temporary file
                Files.delete(tempFile);
            } catch (IOException e) {
                log.error("Error occurred while saving policy XML into S3: {}", e.getMessage());
            }
            finally {
                MDC.clear();
            }
        }


    }
}
