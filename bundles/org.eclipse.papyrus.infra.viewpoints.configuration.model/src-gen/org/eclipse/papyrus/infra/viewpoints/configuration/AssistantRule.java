/**
 * Copyright (c) 2015 CEA LIST and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *  CEA LIST - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.viewpoints.configuration;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Assistant Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A rule that permits or forbids one or more modeling assistants by element type ID.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.viewpoints.configuration.AssistantRule#getElementTypeID <em>Element Type ID</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.viewpoints.configuration.ConfigurationPackage#getAssistantRule()
 * @model
 * @generated
 */
public interface AssistantRule extends Rule {
	/**
	 * Returns the value of the '<em><b>Element Type ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * <p>
	 * Modeling assistants are identified by the ID of the element type that they create.
	 * This element type ID supports very simple wildcard patterns:  an asterisk (<tt>*</tt>)
	 * may be used to match any substring, including the empty substring.  This is
	 * particularly useful to match an entire family of related element types by namespace
	 * prefix, using a terminal asterisk.
	 * </p><p>
	 * If the element type ID is omitted, then the rule applies to all element types
	 * (universal wildcard).
	 * </p>
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Element Type ID</em>' attribute.
	 * @see #setElementTypeID(String)
	 * @see org.eclipse.papyrus.infra.viewpoints.configuration.ConfigurationPackage#getAssistantRule_ElementTypeID()
	 * @model
	 * @generated
	 */
	String getElementTypeID();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.viewpoints.configuration.AssistantRule#getElementTypeID <em>Element Type ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Element Type ID</em>' attribute.
	 * @see #getElementTypeID()
	 * @generated
	 */
	void setElementTypeID(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Queries whether I match a given <tt>elementType</tt>.
	 * <!-- end-model-doc -->
	 * @model required="true" elementTypeDataType="org.eclipse.papyrus.infra.viewpoints.configuration.ElementType" elementTypeRequired="true"
	 * @generated
	 */
	boolean matches(IElementType elementType);

} // AssistantRule
