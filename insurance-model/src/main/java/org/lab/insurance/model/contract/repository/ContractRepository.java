package org.lab.insurance.model.contract.repository;

import java.io.Serializable;

import org.lab.insurance.model.contract.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractRepository extends MongoRepository<Contract, Serializable> {

	Contract findByNumber(String number);

}
