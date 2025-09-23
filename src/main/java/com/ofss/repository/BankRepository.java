package com.ofss.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ofss.model.Bank;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long>{

}