package org.lab.insurance.common.services;

import java.io.Serializable;

import org.lab.insurance.domain.core.HasIdentifier;
import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.engine.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class StateMachineService {

	@Autowired(required = false)
	private TimestampProvider timestampProvider;
	@Autowired
	private MongoTemplate template;

	@SuppressWarnings("unchecked")
	public void createTransition(HasState hasState, String newState, boolean persist) {
		// TODO validacion maquina estados transicion
		State state = new State();
		state.setCode(newState);
		state.setEntered(timestampProvider.getCurrentDate());
		hasState.setCurrentState(state);
		if (persist && HasIdentifier.class.isAssignableFrom(hasState.getClass())) {
			Assert.notNull(template, "Missing MongoTemplate");
			HasIdentifier<Serializable> hasId = (HasIdentifier<Serializable>) hasState;
			Class<?> entityClass = hasState.getClass();
			Update update = new Update();
			update.set("currentState", state);
			Query query = new Query(Criteria.where("id").is(hasId.getId()));
			template.updateFirst(query, update, entityClass);
			// TODO control historico
		}
	}

}
