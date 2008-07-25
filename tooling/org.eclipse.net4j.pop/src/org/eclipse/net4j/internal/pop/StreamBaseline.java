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

import org.eclipse.net4j.internal.pop.code.Tag;
import org.eclipse.net4j.internal.pop.util.Element;
import org.eclipse.net4j.pop.IStream;
import org.eclipse.net4j.pop.IStreamBaseline;
import org.eclipse.net4j.pop.code.ITag;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @author Eike Stepper
 */
public class StreamBaseline extends Element implements IStreamBaseline
{
  private IStream stream;

  private ITag tag;

  public StreamBaseline(IStream stream, String tagName, Date date)
  {
    this.stream = stream;
    tag = new Tag(stream.getBranch(), tagName, date);
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
    return MessageFormat.format("StreamBaseline[stream={0}, tag={1}]", stream, tag);
  }
}
