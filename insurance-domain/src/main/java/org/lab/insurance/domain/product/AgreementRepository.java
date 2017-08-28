package org.lab.insurance.domain.product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AgreementRepository extends MongoRepository<Agreement, String> {

	Agreement findByCode(String agreementCode);

}
