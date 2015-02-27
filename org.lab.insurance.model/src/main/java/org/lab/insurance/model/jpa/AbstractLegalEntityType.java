package org.lab.insurance.model.jpa;

/**
 * Representa un tipo de entidad legal (de momento solo personas y entidades juridicas).
 */
public enum AbstractLegalEntityType {

	PERSON('P'), LEGAL('L');

	private char value;

	AbstractLegalEntityType(char value) {
		this.value = value;
	}

	public char getValue() {
		return value;
	}
}
