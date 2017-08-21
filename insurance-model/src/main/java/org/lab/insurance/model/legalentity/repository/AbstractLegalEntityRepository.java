package org.lab.insurance.model.legalentity.repository;

import org.lab.insurance.model.legalentity.AbstractLegalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbstractLegalEntityRepository extends MongoRepository<AbstractLegalEntity, String> {

	AbstractLegalEntity findByIdCardNumber(String number);

}
