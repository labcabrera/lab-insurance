package org.lab.insurance.core.math;

import java.math.BigDecimal;

public class BigMath {

	public static final BigDecimal CIEN = new BigDecimal("100.00");

	public static BigDecimal safeNull(BigDecimal value) {
		return value != null ? value : BigDecimal.ZERO;
	}

}
