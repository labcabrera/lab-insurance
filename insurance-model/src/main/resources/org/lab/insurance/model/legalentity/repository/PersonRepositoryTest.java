package org.lab.insurance.model.legalentity.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.model.config.InsuranceModelConfig;
import org.lab.insurance.model.legalentity.IdCard;
import org.lab.insurance.model.legalentity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsuranceModelConfig.class)
public class PersonRepositoryTest {

	@Autowired
	private PersonRepository repository;

	@Test
	public void test() {
		Person person = new Person();
		person.setIdCard(IdCard.builder().number("11222333A").build());
		person.setFirstSurname("firstsurname");
		Person saved = repository.save(person);
		System.out.println(saved);

	}

}
