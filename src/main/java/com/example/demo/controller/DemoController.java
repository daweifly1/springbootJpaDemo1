package com.example.demo.controller;

import com.example.demo.service.ServiceC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {
    @Autowired
    private ServiceC serviceC;

    @GetMapping(value = "/test")
    public Map<String, Object> list(@RequestParam("s") String s) {
        String ss = serviceC.toString();

        serviceC.deleteAll();
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("data", ss);
        return map;
    }

}
