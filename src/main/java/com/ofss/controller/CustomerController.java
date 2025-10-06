package com.ofss.controller;

import com.ofss.dto.ResponseEntityDto;
import com.ofss.model.Customer;
import com.ofss.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<ResponseEntityDto> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        if(customers.isEmpty()){
            return ResponseEntity.ok(new ResponseEntityDto("404","No customers found"));
        } else {
            return ResponseEntity.ok(new ResponseEntityDto("200","Customers retrieved successfully", customers));
        }
    }

    @GetMapping("/customers/id/{customerId}")
    public ResponseEntity<ResponseEntityDto> getCustomer(@PathVariable("customerId") Long customerId) {
        Optional<Customer> customer = customerService.getCustomer(customerId);
        if (customer.isPresent()) {
            return ResponseEntity.ok(new ResponseEntityDto("200", "Customer retrieved successfully", customer.get()));
        } else {
            return ResponseEntity.ok(new ResponseEntityDto("404", "No customer found"));
        }
    }

    @PostMapping("/customers")
    public ResponseEntity<ResponseEntityDto> createCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);
            return ResponseEntity.status(201)
                    .body(new ResponseEntityDto("201", "Customer created successfully", savedCustomer));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new ResponseEntityDto("400", "Failed to create customer"));
        }
    }

    @PutMapping("/customers/id/{customerId}")
    public ResponseEntity<ResponseEntityDto> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
        Optional<Customer> existingCustomer = customerService.getCustomer(customerId);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = customerService.updateCustomer(customerId,customer);
            return ResponseEntity.ok(new ResponseEntityDto("200", "Customer updated successfully", updatedCustomer));
        } else {
            return ResponseEntity.status(404)
                    .body(new ResponseEntityDto("404", "Customer not found"));
        }
    }

    @PatchMapping("/customers/id/{customerId}")
    public ResponseEntity<ResponseEntityDto> patchCustomer(@PathVariable Long customerId, @RequestBody Customer customerUpdates) {
        Optional<Customer> existingCustomer = customerService.getCustomer(customerId);
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = customerService.updateCustomerFields(existingCustomer.get(), customerUpdates);
            return ResponseEntity.ok(new ResponseEntityDto("200", "Customer updated successfully", updatedCustomer));
        } else {
            return ResponseEntity.status(404)
                    .body(new ResponseEntityDto("404", "Customer not found"));
        }
    }

}
