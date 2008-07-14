/***************************************************************************
 * Copyright (c) 2004 - 2008 Eike Stepper, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package org.eclipse.net4j.template.internal.ui.actions;

import org.eclipse.net4j.internal.template.nature.TemplateNature;

import java.util.List;

public class RemoveTemplateNatureAction extends TemplateNatureAction
{
  public RemoveTemplateNatureAction()
  {
  }

  @Override
  protected void modifyNatureIds(List<String> natureIds)
  {
    natureIds.remove(TemplateNature.NATURE_ID);
  }
}
