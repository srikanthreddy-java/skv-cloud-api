package com.skv.cloud.api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "policy")
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coverage_amount")
    private Long coverageAmount;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "file_key")
    private String fileKey;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "policy_number")
    private String policyNumber;

    @Column(name = "policy_type")
    private String policyType;

    @Column(name = "premium_amount")
    private Double premiumAmount;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status")
    private String status;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Column(name = "xml_data", length = 55500)
    private byte[] xmlData;
}