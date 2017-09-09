package org.lab.insurance.engine.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceTaskError {

	String message;

}
