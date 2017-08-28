package org.lab.insurance.commons.services;

import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.engine.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class StateMachineService {

	@Autowired
	private TimestampProvider timestampProvider;
	@Autowired
	private MongoTemplate template;

	public void createTransition(HasState hasState, String newStateId) {
		//TODO validacion maquina estados transicion
		State state = new State();
		state.setCode(newStateId);
		state.setEntered(timestampProvider.getCurrentDate());
		Class<?> entityClass = hasState.getClass();
		Update update = new Update();
		update.set("currentState", state);
		Query query = new Query(Criteria.where("id").is(hasState.getId()));
		template.updateFirst(query, update, entityClass);
		// TODO control historico
	}

}
