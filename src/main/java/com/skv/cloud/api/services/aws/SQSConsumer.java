package com.skv.cloud.api.services.aws;

import com.amazonaws.xray.AWSXRay;
import com.amazonaws.xray.entities.Segment;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Component
@Slf4j
public class SQSConsumer {

    @Autowired
    private SqsClient sqsClient;

    @Value(value = "${aws.sqs.url}")
    private String queueUrl;

    @PostConstruct
    public void startListening() {

        new Thread(() -> {
            while (true) {
                Segment segment = AWSXRay.beginSegment("SQSMessageProcessing");
            MDC.put("id", "1234");
                try {

                    ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .maxNumberOfMessages(10) // Adjust batch size as needed
                            .waitTimeSeconds(5)
                            .build();

                    List<Message> messages = sqsClient.receiveMessage(request).messages();

                    for (Message message : messages) {
                        processMessage(message);
                        deleteMessage(message);
                    }
                } catch (Exception e) {
                    log.error("Error occurred while processing messages: {}", e.getMessage());
                }
                finally {
                    AWSXRay.endSegment();
                }
            }
        }).start();
    }

    private void processMessage(Message queueMessage) {
        log.info("Received message: {}", queueMessage.body());
        // read the message body and process it
        JSONObject jsonObject = new JSONObject(queueMessage.body());
        String message = jsonObject.getString("Message");

        JSONObject messageObject = new JSONObject(message);
        JSONArray records = messageObject.getJSONArray("Records");
        JSONObject s3 = records.getJSONObject(0).getJSONObject("s3");
        String bucketName = s3.getJSONObject("bucket").getString("name");
        String objectKey = s3.getJSONObject("object").getString("key");


        log.info("Bucket Name: {}", bucketName);
        log.info("Object Key: {}", objectKey);
    }

    private void deleteMessage(Message message) {
        sqsClient.deleteMessage(builder -> builder
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle()));
        log.info("Deleted message: " + message.messageId());
    }
}


