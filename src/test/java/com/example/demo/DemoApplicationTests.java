package com.example.demo;

import com.example.demo.service.ServiceC;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ServiceC serviceC;

    @Test
    void tranTest() {
        try {
            serviceC.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        serviceC.showAll();
    }

}
