package org.lab.insurance.model.jpa.engine;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "SYS_STATE")
@SuppressWarnings("serial")
public class State implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
	@JoinColumn(name = "STATE_DEFINITION_ID")
	private StateDefinition stateDefinition;

	@Column(name = "ENTERED", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date entered;

	@Column(name = "HAS_STATE_ID", nullable = false)
	private String hasStateId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StateDefinition getStateDefinition() {
		return stateDefinition;
	}

	public void setStateDefinition(StateDefinition stateDefinition) {
		this.stateDefinition = stateDefinition;
	}

	public Date getEntered() {
		return entered;
	}

	public void setEntered(Date entered) {
		this.entered = entered;
	}

	public String getHasStateId() {
		return hasStateId;
	}

	public void setHasStateId(String hasStateId) {
		this.hasStateId = hasStateId;
	}
}
