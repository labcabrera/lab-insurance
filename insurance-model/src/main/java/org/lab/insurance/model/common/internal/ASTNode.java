/*******************************************************************************
 * Copyright (c) 2004, 2012 Kotasoft S.L.
 * All rights reserved. This program and the accompanying materials
 * may only be used prior written consent of Kotasoft S.L.
 ******************************************************************************/
package org.lab.insurance.model.common.internal;

/**
 * Nodo de un AST (Abstract Syntax Tree) utilizado para parsear las expresiones FIQL.
 */
public interface ASTNode<T> {

	T build();
}
