package org.lab.insurance.domain.core.contract.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.contract.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContractRepository extends MongoRepository<Contract, Serializable> {

	Contract findByNumber(String number);

}
