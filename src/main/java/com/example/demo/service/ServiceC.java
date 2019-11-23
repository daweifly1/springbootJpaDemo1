package com.example.demo.service;

import com.example.demo.Customer;
import com.example.demo.CustomerRepository;
import com.example.demo.core.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
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
        HttpServletRequest request = CommonUtils.getHttpRequest();
        if (request != null) {
            String expression = request.getParameter("s");
            log.info("iiii " + expression);
        }
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(requestAttributes, true);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    serviceB.toString();
                } catch (Exception e) {
                    log.info("ServiceC 中 cach ServiceB 异常", e);
                }
            }
        };
        new Thread(r).start();
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

    public void deleteAll() {
        customerRepository.deleteAll();
    }
}
