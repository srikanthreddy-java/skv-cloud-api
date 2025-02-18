package com.skv.cloud.api.repository;

import com.skv.cloud.api.model.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
}
