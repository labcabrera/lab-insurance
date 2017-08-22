package org.lab.insurance.model.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.model.insurance.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyRepository extends MongoRepository<CurrencyRepository, Serializable> {

	Currency findByIso(String isoCode);
}
