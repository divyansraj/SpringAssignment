package com.ofss.service;

import com.ofss.model.Customer;
import com.ofss.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer updateCustomer(Long customerId,Customer customer) {
        customer.setCustId(customerId);
        return customerRepository.save(customer);
    }

    public Customer updateCustomerFields(Customer existingCustomer, Customer customerUpdates) {
        if (customerUpdates.getFirstName() != null) {
            existingCustomer.setFirstName(customerUpdates.getFirstName());
        }
        if (customerUpdates.getLastName() != null) {
            existingCustomer.setLastName(customerUpdates.getLastName());
        }
        if (customerUpdates.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(customerUpdates.getPhoneNumber());
        }
        if (customerUpdates.getEmailId() != null) {
            existingCustomer.setEmailId(customerUpdates.getEmailId());
        }
        return customerRepository.save(existingCustomer);
    }
}
