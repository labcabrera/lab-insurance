package org.lab.insurance.engine.core.domain.repository;

import java.io.Serializable;

import org.lab.insurance.engine.core.domain.ExecutionReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExecutionReportRepository extends MongoRepository<ExecutionReport, Serializable> {

}
