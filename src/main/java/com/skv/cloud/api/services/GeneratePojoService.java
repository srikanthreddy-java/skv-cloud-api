package com.skv.cloud.api.services;

import com.skv.cloud.api.repository.GenericRepository;
import com.skv.cloud.api.repository.TableNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneratePojoService {

    private static final String SCHEMA = "pocdb";

    @Autowired
    private TableNameRepository tableNameRepository;

    public void generatePojo() {
        tableNameRepository.findTableNamesBySchema(SCHEMA).forEach(tableName -> {
            System.out.println("Generating POJO for table: " + tableName);
        });
    }

}
