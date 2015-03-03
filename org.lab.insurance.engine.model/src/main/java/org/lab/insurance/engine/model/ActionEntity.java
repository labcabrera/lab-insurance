package org.lab.insurance.engine.model;

import java.io.Serializable;
import java.util.Date;

public interface ActionEntity<T> extends Serializable {

	Date getActionDate();

}
