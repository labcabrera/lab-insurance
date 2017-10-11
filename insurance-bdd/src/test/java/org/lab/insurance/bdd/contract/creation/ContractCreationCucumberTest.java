package org.lab.insurance.bdd.contract.creation;

import org.junit.Ignore;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@Ignore("Spring dsl errors using spring 5")
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/creation")
public class ContractCreationCucumberTest {

}
