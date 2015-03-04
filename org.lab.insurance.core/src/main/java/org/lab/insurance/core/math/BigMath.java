package org.lab.insurance.core.math;

import java.math.BigDecimal;

public class BigMath {

	public static final BigDecimal HUNDRED = new BigDecimal("100.00");

	public static BigDecimal safeNull(BigDecimal value) {
		return value != null ? value : BigDecimal.ZERO;
	}

	public static boolean isNotZero(BigDecimal units) {
		return units != null && units.compareTo(BigDecimal.ZERO) != 0;
	}

}
