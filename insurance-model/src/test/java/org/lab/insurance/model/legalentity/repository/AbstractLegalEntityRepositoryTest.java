package org.lab.insurance.model.legalentity.repository;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.model.config.InsuranceModelConfig;
import org.lab.insurance.model.legalentity.IdCard;
import org.lab.insurance.model.legalentity.LegalEntity;
import org.lab.insurance.model.legalentity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsuranceModelConfig.class)
public class AbstractLegalEntityRepositoryTest {

	@Autowired
	private AbstractLegalEntityRepository repository;

	@Test
	public void test() {
		String personIdCardNumber = UUID.randomUUID().toString();
		String legalEntityIdCardNumber = UUID.randomUUID().toString();

		Person person = Person.builder().firstSurname("Doe").secondSurname("Smith").build();
		person.setName("John");
		person.setIdCard(IdCard.builder().number(personIdCardNumber).build());
		person.setFirstSurname("firstsurname");
		Person personSaved = repository.save(person);

		LegalEntity legalEntity = LegalEntity.builder().dummy("dummy").build();
		legalEntity.setName("Lab Insurance");
		person.setIdCard(IdCard.builder().number(legalEntityIdCardNumber).build());
		LegalEntity legalEntitySaved = repository.save(legalEntity);

		Person resultPerson = repository.findByIdCardNumber(personIdCardNumber).as(Person.class);
		LegalEntity resultLegalEntity = repository.findByIdCardNumber(legalEntityIdCardNumber).as(LegalEntity.class);

		System.out.println(personSaved);
		System.out.println(resultPerson);
		System.out.println(personSaved.equals(resultPerson));
		System.out.println(legalEntitySaved);
		System.out.println(resultLegalEntity);

	}

}
