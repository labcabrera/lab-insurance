package org.lab.insurance.bdd.common;

import java.util.Calendar;

import org.lab.insurance.bdd.TestConstants;
import org.lab.insurance.domain.core.common.audit.AuditData;
import org.lab.insurance.domain.core.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.AssetType;
import org.lab.insurance.domain.core.insurance.repository.AssetRepository;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.lab.insurance.domain.core.legalentity.IdCard;
import org.lab.insurance.domain.core.legalentity.LegalEntity;
import org.lab.insurance.domain.core.legalentity.Person;
import org.lab.insurance.domain.core.legalentity.repository.LegalEntityRepository;
import org.lab.insurance.domain.core.legalentity.repository.PersonRepository;
import org.lab.insurance.domain.core.product.Agreement;
import org.lab.insurance.domain.core.product.repository.AgreementRepository;
import org.lab.insurance.engine.core.domain.repository.InsuranceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MongoTestOperations {

	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private AssetRepository baseAssetRepository;
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ContractPersonRelationRepository policyEntityRelationRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private LegalEntityRepository legalEntityRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private InsuranceTaskRepository taskRepo;

	public void resetDataBase() {
		log.warn("###############################################################");
		log.warn("# Cleaning mongo test contract data                           #");
		log.warn("###############################################################");

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
		baseAssetRepository.insert(Asset.builder().isin(TestConstants.Assets.ASSET_01).name("Asset 1")
				.type(AssetType.INTERNAL_FUND).build());
		baseAssetRepository.insert(Asset.builder().isin(TestConstants.Assets.ASSET_02).name("Asset 2")
				.type(AssetType.INTERNAL_FUND).build());
		baseAssetRepository.insert(Asset.builder().isin(TestConstants.Assets.GUARANTEE_01).name("Garantizado 1")
				.type(AssetType.GUARANTEE).build());
		baseAssetRepository.insert(Asset.builder().isin(TestConstants.Assets.GUARANTEE_02).name("Garantizado 2")
				.type(AssetType.GUARANTEE).build());
		baseAssetRepository
				.insert(Asset.builder().isin(TestConstants.Assets.CASH_EURO).name("Euro").type(AssetType.CASH).build());
		contractRepository.deleteAll();

		policyEntityRelationRepository.deleteAll();
		orderRepository.deleteAll();

		taskRepo.deleteAll();
	}

}
