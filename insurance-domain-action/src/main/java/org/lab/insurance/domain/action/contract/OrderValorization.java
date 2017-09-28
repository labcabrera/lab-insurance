package org.lab.insurance.domain.action.contract;

import java.util.Date;

import org.lab.insurance.domain.action.InsuranceAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderValorization implements InsuranceAction {

	String orderId;
	Date execution;

}
