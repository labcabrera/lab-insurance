package org.lab.insurance.model.engine;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interfaz que define determinada informacion relativa al procesamiento de una accion del sistema.
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ActionDefinition {

	/**
	 * Indica el endpoint encargado de procesar esta accion.
	 * 
	 * @return
	 */
	String endpoint();

}
