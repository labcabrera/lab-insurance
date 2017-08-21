package org.lab.insurance.model.jpa.contract;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.jpa.insurance.OrderDistribution;
import org.lab.insurance.model.jpa.system.TriggerDefinition;

@Entity
@Table(name = "CTR_FINANCIAL_SERVICE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@SuppressWarnings("serial")
public abstract class FinancialService implements Serializable, HasIdentifier<String>, HasContract {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contract contract;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "TRIGGER_ID", nullable = false)
	private TriggerDefinition trigger;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "CTR_FINANCIAL_SERVICE_SOURCE", joinColumns = { @JoinColumn(name = "FINANCIAL_SERVICE_ID", referencedColumnName = "ID") }, inverseJoinColumns = @JoinColumn(name = "DISTRIBUTION_ID", referencedColumnName = "ID"))
	private List<OrderDistribution> sourceDistribution;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "CTR_FINANCIAL_SERVICE_TARGET", joinColumns = { @JoinColumn(name = "FINANCIAL_SERVICE_ID", referencedColumnName = "ID") }, inverseJoinColumns = @JoinColumn(name = "DISTRIBUTION_ID", referencedColumnName = "ID"))
	private List<OrderDistribution> targetDistribution;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Contract getContract() {
		return contract;
	}

	@Override
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public List<OrderDistribution> getSourceDistribution() {
		return sourceDistribution;
	}

	public void setSourceDistribution(List<OrderDistribution> sourceDistribution) {
		this.sourceDistribution = sourceDistribution;
	}

	public List<OrderDistribution> getTargetDistribution() {
		return targetDistribution;
	}

	public void setTargetDistribution(List<OrderDistribution> targetDistribution) {
		this.targetDistribution = targetDistribution;
	}
}
