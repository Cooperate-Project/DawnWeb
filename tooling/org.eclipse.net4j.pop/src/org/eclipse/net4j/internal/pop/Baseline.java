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
package org.eclipse.net4j.internal.pop;

import org.eclipse.net4j.internal.pop.util.Element;
import org.eclipse.net4j.pop.IBaseline;
import org.eclipse.net4j.pop.IStream;
import org.eclipse.net4j.pop.code.ITag;

import java.text.MessageFormat;

/**
 * @author Eike Stepper
 */
public class Baseline extends Element implements IBaseline
{
  private IStream stream;

  private ITag tag;

  public Baseline(IStream stream, String tagName)
  {
    checkArgument(stream, "stream");
    checkArgument(tagName, "tagName");
    this.stream = stream;
    tag = stream.getBranch().addTag(tagName);
  }

  public IStream getStream()
  {
    return stream;
  }

  public ITag getTag()
  {
    return tag;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("Baseline[tag={0}]", tag.getName());
  }
}
