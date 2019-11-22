package com.example.demo.service;

import com.example.demo.Customer;
import com.example.demo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceA {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    @Override
    public String toString() {
        Customer s = Customer.builder().id(1L).firstName("f1").lastName("L1").build();
        customerRepository.save(s);
        return super.toString();
    }
}
