package com.ofss.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "CUSTOMER")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUST_ID")
    private Long custId;
    
    @Column(name = "FIRST_NAME", nullable = false, length = 100)
    private String firstName;
    
    @Column(name = "LAST_NAME", length = 100)
    private String lastName;
    
    @Column(name = "PHONE_NUMBER", length = 15)
    private String phoneNumber;
    
    @Column(name = "EMAIL_ID", unique = true, length = 150)
    private String emailId;
}