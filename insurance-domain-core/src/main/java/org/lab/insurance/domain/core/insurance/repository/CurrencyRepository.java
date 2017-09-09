package org.lab.insurance.domain.core.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.core.insurance.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyRepository extends MongoRepository<Currency, Serializable> {

	Currency findByIso(String isoCode);
}
