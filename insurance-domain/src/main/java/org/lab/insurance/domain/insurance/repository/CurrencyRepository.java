package org.lab.insurance.domain.insurance.repository;

import java.io.Serializable;

import org.lab.insurance.domain.insurance.Currency;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyRepository extends MongoRepository<Currency, Serializable> {

	Currency findByIso(String isoCode);
}
