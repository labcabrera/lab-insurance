package org.lab.insurance.model.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.legalentity.LegalEntity;
import org.lab.insurance.model.system.AppFile;

@Entity
@Table(name = "CTR_CONTRACT_LETTER")
public class ContractLetter implements HasIdentifier<String>, HasContract {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	private Contract contract;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FILE_ID", nullable = false)
	private AppFile appFile;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TARGET_ID", nullable = false)
	private LegalEntity letterTarget;

	@Column(name = "GENERATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date generated;

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

	public AppFile getAppFile() {
		return appFile;
	}

	public void setAppFile(AppFile appFile) {
		this.appFile = appFile;
	}

	public LegalEntity getLetterTarget() {
		return letterTarget;
	}

	public void setLetterTarget(LegalEntity letterTarget) {
		this.letterTarget = letterTarget;
	}

	public Date getGenerated() {
		return generated;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
	}
}
