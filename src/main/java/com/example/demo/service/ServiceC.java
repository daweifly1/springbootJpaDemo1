package com.example.demo.service;

import com.example.demo.Customer;
import com.example.demo.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Slf4j
@Service
public class ServiceC {

    @Autowired
    private ServiceA serviceA;

    @Autowired
    private ServiceB serviceB;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public String toString() {
        serviceA.toString();
        try {
            serviceB.toString();
        } catch (Exception e) {
            log.info("ServiceC 中 cach ServiceB 异常", e);
        }
        Customer s = Customer.builder().id(3L).firstName("f3").lastName("L3").build();
        customerRepository.save(s);
        return super.toString();
    }

    public String showAll() {
        Iterable<Customer> ll = customerRepository.findAll();
        Iterator<Customer> it = ll.iterator();
        while (it.hasNext()) {
            log.info("==={}", it.next());
        }
        return null;
    }
}
