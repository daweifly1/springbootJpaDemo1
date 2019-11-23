package com.example.demo.service;

import com.example.demo.Customer;
import com.example.demo.CustomerRepository;
import com.example.demo.core.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class ServiceB {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Override
    public String toString() {
        HttpServletRequest request = CommonUtils.getHttpRequest();
        if (request != null) {
            String expression = request.getParameter("s");
            log.info("---- " + expression);
        }
        Customer s = Customer.builder().id(2L).firstName("f2").lastName("L2").build();
        customerRepository.save(s);
        if (s.getId().longValue() == 2L) {
            throw new RuntimeException("ServiceB 抛出运行时异常");
        }
        return super.toString();
    }
}
