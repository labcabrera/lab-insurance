package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.ContractMessageConverter;
import org.lab.insurance.engine.processors.contract.ContractResolverProcessor;
import org.lab.insurance.engine.processors.contract.ContractStartProcessor;
import org.lab.insurance.engine.processors.contract.InitializeContractNumber;
import org.lab.insurance.engine.processors.contract.InitializeContractPortfolios;
import org.lab.insurance.engine.processors.contract.NewContractProcessor;
import org.lab.insurance.engine.processors.orders.ScheduleFeesOrder;

public class ContractRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		onException(Exception.class).bean(ContractMessageConverter.class);

		/**
		 * Endpoint que procesa la accion de dar de alta un contrato. En ese momento el contrato no esta activo (lo estara cuando se procese
		 * la el pago inicial).
		 */
		from("direct:new_contract_action") //
				.bean(InitializeContractNumber.class) //
				.bean(InitializeContractPortfolios.class) //
				.bean(NewContractProcessor.class) //
				.bean(ContractMessageConverter.class);

		/**
		 * Endpoint que procesa el evento de entrada en vigor de un contrato (se inicializan los servicios financieros, se programan los
		 * gastos, etc).
		 */
		from("direct:contract_start") //
				.bean(ContractResolverProcessor.class) //
				.bean(ContractStartProcessor.class) //
				.bean(ScheduleFeesOrder.class);
	}
}
