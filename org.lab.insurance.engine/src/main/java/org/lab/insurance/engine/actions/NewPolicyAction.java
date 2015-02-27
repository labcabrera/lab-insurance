package org.lab.insurance.engine.actions;

import org.lab.insurance.engine.ActionDefinition;
import org.lab.insurance.model.HasPolicy;
import org.lab.insurance.model.engine.ActionEntity;
import org.lab.insurance.model.jpa.Policy;

@ActionDefinition(getEndpoint = "direct:new_policy_action")
public class NewPolicyAction implements ActionEntity<Policy>, HasPolicy {

	private Policy policy;

	@Override
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}
}
