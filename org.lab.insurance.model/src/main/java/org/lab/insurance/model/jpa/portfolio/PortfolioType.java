package org.lab.insurance.model.jpa.portfolio;

/**
 * Representa cada uno de los tipos de carteras del sistema:
 * <ul>
 * <li><b>PASIVO</b>: contabilidad interna de las polizas.</li>
 * <li><b>ACTIVO</b>: contabilidad externa de las polizas utilizado en los ajustes con los brokers.</li>
 * <li><b>BANK</b>: cartera para llevar la contabilidad del broker.</li>
 * <li><b>VOID</b>: entradas y salidas del sistema (cheques por ejemplo).</li>
 * </ul>
 */
public enum PortfolioType {

	ACTIVE, PASSIVE, BANK, VOID, FEES

}
