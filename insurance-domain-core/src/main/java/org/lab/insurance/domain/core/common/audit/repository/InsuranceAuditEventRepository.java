package org.lab.insurance.domain.core.common.audit.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.common.audit.InsuranceAuditEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InsuranceAuditEventRepository extends MongoRepository<InsuranceAuditEvent, Serializable> {

}
