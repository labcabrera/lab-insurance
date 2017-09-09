package org.lab.insurance.domain.core.legalentity.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.legalentity.LegalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LegalEntityRepository extends MongoRepository<LegalEntity, Serializable> {

}
