package org.lab.insurance.services.insurance;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetGuaranteeService {

	private static final Logger LOG = LoggerFactory.getLogger(AssetGuaranteeService.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	

}
