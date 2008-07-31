/**
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *
 * $Id: RootStream.java,v 1.2 2008-07-31 13:35:41 estepper Exp $
 */
package org.eclipse.net4j.pop.project;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Root Stream</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.net4j.pop.project.RootStream#getPopProject <em>Pop Project</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.net4j.pop.project.ProjectPackage#getRootStream()
 * @model
 * @generated
 */
public interface RootStream extends DevelopmentStream
{
  /**
   * Returns the value of the '<em><b>Pop Project</b></em>' reference. It is bidirectional and its opposite is '
   * {@link org.eclipse.net4j.pop.project.PopProject#getRootStream <em>Root Stream</em>}'. <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Pop Project</em>' reference isn't clear, there really should be more of a description
   * here...
   * </p>
   * <!-- end-user-doc -->
   * 
   * @return the value of the '<em>Pop Project</em>' reference.
   * @see #setPopProject(PopProject)
   * @see org.eclipse.net4j.pop.project.ProjectPackage#getRootStream_PopProject()
   * @see org.eclipse.net4j.pop.project.PopProject#getRootStream
   * @model opposite="rootStream" required="true"
   * @generated
   */
  PopProject getPopProject();

  /**
   * Sets the value of the '{@link org.eclipse.net4j.pop.project.RootStream#getPopProject <em>Pop Project</em>}'
   * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
   * 
   * @param value
   *          the new value of the '<em>Pop Project</em>' reference.
   * @see #getPopProject()
   * @generated
   */
  void setPopProject(PopProject value);

} // RootStream
