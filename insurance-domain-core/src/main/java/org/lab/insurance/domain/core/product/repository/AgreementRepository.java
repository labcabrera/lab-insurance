package org.lab.insurance.domain.core.product.repository;

import org.lab.insurance.domain.core.product.Agreement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgreementRepository extends MongoRepository<Agreement, String> {

	Agreement findByCode(String agreementCode);

}
