package com.skv.cloud.api.controller;

import com.skv.cloud.api.services.GeneratePojoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneratePojoController {

    @Autowired
    private GeneratePojoService generatePojoService;

    @GetMapping("/generatePojo")
    public void generatePojo() {
        generatePojoService.generatePojo();
    }
}
