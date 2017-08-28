package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.domain.insurance.OrderType;
import org.lab.insurance.engine.camel.predicates.OrderTypePredicate;
import org.lab.insurance.engine.processors.orders.InitialPaymentReceptionProcessor;
import org.lab.insurance.engine.processors.orders.InitialPaymentValuedProcessor;
import org.lab.insurance.engine.processors.orders.MarketOrderGeneratorProcessor;
import org.lab.insurance.engine.processors.orders.OrderAccountProcessor;
import org.lab.insurance.engine.processors.orders.OrderFeesProcessor;
import org.lab.insurance.engine.processors.orders.OrderProcessor;
import org.lab.insurance.engine.processors.orders.OrderResolverProcessor;
import org.lab.insurance.engine.processors.orders.OrderValorizationProcessor;
import org.lab.insurance.engine.processors.orders.OrderValueDateProcessor;
import org.lab.insurance.engine.processors.orders.PaymentReceptionDateProcessor;
import org.lab.insurance.engine.processors.orders.ScheduleOrderAccount;
import org.lab.insurance.engine.processors.orders.ScheduleOrderValorization;

public class OrderRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/**
		 * Accion de procesar una orden.
		 */
		from("direct:order_process") //
				.bean(OrderResolverProcessor.class) //
				.bean(OrderFeesProcessor.class) //
				.bean(OrderValueDateProcessor.class) //
				.bean(OrderProcessor.class) //
				.bean(MarketOrderGeneratorProcessor.class) //
				.bean(ScheduleOrderValorization.class) //
		// .bean(MergeEntityProcessor.class);
		;

		/**
		 * Accion de valorizar una orden.
		 */
		from("direct:order_valorizarion") //
				.bean(OrderResolverProcessor.class) //
				.bean(OrderValorizationProcessor.class) //
				.bean(ScheduleOrderAccount.class) //
				.choice() //
				.when(OrderTypePredicate.withType(OrderType.INITIAL_PAYMENT)).bean(InitialPaymentValuedProcessor.class) //
				.end() //
		// .bean(MergeEntityProcessor.class);
		;

		/**
		 * Accion de generar la contabilidad de una orden.
		 */
		from("direct:order_accounting") //
				.bean(OrderResolverProcessor.class) //
				.bean(OrderAccountProcessor.class) //
		// .bean(MergeEntityProcessor.class);
		;

		/**
		 * Accion de procesar la recepcion de un pago.
		 */
		from("direct:payment_reception") //
				.bean(OrderResolverProcessor.class) //
				.bean(PaymentReceptionDateProcessor.class) //
				.choice() //
				.when(OrderTypePredicate.withType(OrderType.INITIAL_PAYMENT))
				.bean(InitialPaymentReceptionProcessor.class) //
				.end() //
				.to("direct:order_process");

		// TODO cambiar
		from("direct:switch_persist") //
				.bean(OrderProcessor.class);
	}
}