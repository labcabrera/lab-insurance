package org.lab.insurance.domain.legalentity.repository;

import java.io.Serializable;

import org.lab.insurance.domain.legalentity.LegalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LegalEntityRepository extends MongoRepository<LegalEntity, Serializable> {

}
