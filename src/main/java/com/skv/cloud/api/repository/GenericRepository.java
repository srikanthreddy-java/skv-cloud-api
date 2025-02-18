package com.skv.cloud.api.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends CrudRepository<T, ID> {

    @Query(value = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?1", nativeQuery = true)
    List<String> findTableNamesBySchema(String schema);
}