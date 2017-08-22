package org.lab.insurance.bdd.contract;

import java.util.Calendar;

import org.lab.insurance.bdd.TestConstants;
import org.lab.insurance.model.common.audit.AuditData;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.lab.insurance.model.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.model.insurance.AssetType;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.repository.BaseAssetRepository;
import org.lab.insurance.model.legalentity.IdCard;
import org.lab.insurance.model.legalentity.LegalEntity;
import org.lab.insurance.model.legalentity.Person;
import org.lab.insurance.model.legalentity.repository.LegalEntityRepository;
import org.lab.insurance.model.legalentity.repository.PersonRepository;
import org.lab.insurance.model.product.Agreement;
import org.lab.insurance.model.product.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MongoTestOperations {

	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private BaseAssetRepository baseAssetRepository;
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ContractPersonRelationRepository policyEntityRelationRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private LegalEntityRepository legalEntityRepository;

	public void resetDataBase() {
		log.warn("=======================================================");
		log.warn("= resetDataBase()                                     =");
		log.warn("=======================================================");

		Agreement agreement = Agreement.builder().code(TestConstants.Agreements.DEFAULT)
				.name("Test integration agreement").build();
		agreementRepository.deleteAll();
		agreementRepository.insert(agreement);

		Person alice = new Person();
		alice.setIdCard(IdCard.builder().number(TestConstants.Persons.DEFAULT_SUBSCRIPTOR).build());
		alice.setName("Alice");
		alice.setFirstSurname("Smith");
		alice.setAuditData(AuditData.builder().created(Calendar.getInstance().getTime()).build());

		Person bob = new Person();
		bob.setIdCard(IdCard.builder().number(TestConstants.Persons.DEFAULT_RECIPIENT).build());
		bob.setName("Bob");
		bob.setFirstSurname("Doe");
		bob.setAuditData(AuditData.builder().created(Calendar.getInstance().getTime()).build());

		personRepository.deleteAll();
		personRepository.insert(alice);
		personRepository.insert(bob);

		LegalEntity insuranceInc = new LegalEntity();
		insuranceInc.setIdCard(IdCard.builder().number(TestConstants.LegalEntities.DEFAULT).build());
		insuranceInc.setName("Insurance Inc");
		insuranceInc.setAuditData(AuditData.builder().created(Calendar.getInstance().getTime()).build());

		legalEntityRepository.deleteAll();
		legalEntityRepository.insert(insuranceInc);

		baseAssetRepository.deleteAll();
		baseAssetRepository
				.insert(BaseAsset.builder().isin(TestConstants.Assets.ASSET_01).type(AssetType.INTERNAL_FUND).build());
		baseAssetRepository
				.insert(BaseAsset.builder().isin(TestConstants.Assets.ASSET_02).type(AssetType.INTERNAL_FUND).build());
		baseAssetRepository
				.insert(BaseAsset.builder().isin(TestConstants.Assets.GUARANTEE_01).type(AssetType.GUARANTEE).build());
		baseAssetRepository
				.insert(BaseAsset.builder().isin(TestConstants.Assets.GUARANTEE_02).type(AssetType.GUARANTEE).build());
		baseAssetRepository
				.insert(BaseAsset.builder().isin(TestConstants.Assets.CASH_EURO).type(AssetType.CASH).build());
		contractRepository.deleteAll();

		policyEntityRelationRepository.deleteAll();

	}

}
