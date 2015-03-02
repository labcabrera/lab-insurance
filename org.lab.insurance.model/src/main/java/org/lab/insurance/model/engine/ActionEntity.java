package org.lab.insurance.model.engine;

import java.io.Serializable;
import java.util.Date;

public interface ActionEntity<T> extends Serializable {

	Date getActionDate();

}
