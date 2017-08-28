package org.lab.insurance.domain.product.repository;

import org.lab.insurance.domain.product.Agreement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgreementRepository extends MongoRepository<Agreement, String> {

	Agreement findByCode(String agreementCode);

}
