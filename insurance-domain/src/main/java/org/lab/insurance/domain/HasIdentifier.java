package org.lab.insurance.domain;

import java.io.Serializable;

public interface HasIdentifier<T extends Serializable> {

	T getId();

	void setId(T t);

}
