package com.example.demo.service;

import com.example.demo.Customer;
import com.example.demo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceB {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public String toString() {
        Customer s = Customer.builder().id(2L).firstName("f2").lastName("L2").build();
        customerRepository.save(s);
        if (s.getId().longValue() == 2L) {
            throw new RuntimeException("ServiceB 抛出运行时异常");
        }
        return super.toString();
    }
}
