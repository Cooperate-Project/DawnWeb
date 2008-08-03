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
package org.eclipse.net4j.pop.internal.ui.views;

import org.eclipse.net4j.pop.IPopManager;
import org.eclipse.net4j.pop.base.provider.BaseItemProviderAdapterFactory;
import org.eclipse.net4j.pop.project.MaintenanceStream;
import org.eclipse.net4j.pop.project.RootStream;
import org.eclipse.net4j.pop.project.TaskStream;
import org.eclipse.net4j.pop.project.provider.ProjectItemProviderAdapterFactory;
import org.eclipse.net4j.util.ObjectUtil;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Eike Stepper
 */
public class StreamsView extends MasterDetailsView
{
  private static final String COMMITTERS = "Committers";

  private static final String TARGETS = "Targets";

  private static final String DELIVERIES = "Deliveries";

  private static final String MERGES = "Merges";

  private static final String CHECKOUTS = "Checkouts";

  private static final String[] ROOT_STREAM_DETAILS = { COMMITTERS, TARGETS, MERGES, CHECKOUTS };

  private static final String[] MAINTENANCE_STREAM_DETAILS = { TARGETS, MERGES, CHECKOUTS };

  private static final String[] TASK_STREAM_DETAILS = { DELIVERIES, MERGES, CHECKOUTS };

  private ComposedAdapterFactory adapterFactory;

  public StreamsView()
  {
    adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
    adapterFactory.addAdapterFactory(new BaseItemProviderAdapterFactory());
    adapterFactory.addAdapterFactory(new ProjectItemProviderAdapterFactory());
    adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
  }

  @Override
  protected StructuredViewer createMaster(Composite parent)
  {
    TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.setContentProvider(new PopContentProvider.Streams(adapterFactory));
    viewer.setLabelProvider(new PopLabelProvider(adapterFactory));
    viewer.setInput(IPopManager.INSTANCE);
    return viewer;
  }

  @Override
  protected StructuredViewer createDetail(Composite parent, String title)
  {
    if (ObjectUtil.equals(title, COMMITTERS))
    {
      return createViewer(parent, new PopContentProvider(adapterFactory));
    }

    if (ObjectUtil.equals(title, TARGETS))
    {
      return createViewer(parent, new PopContentProvider.Targets(adapterFactory));
    }

    if (ObjectUtil.equals(title, DELIVERIES))
    {
      return createViewer(parent, new PopContentProvider(adapterFactory));
    }

    if (ObjectUtil.equals(title, MERGES))
    {
      return createViewer(parent, new PopContentProvider(adapterFactory));
    }

    if (ObjectUtil.equals(title, CHECKOUTS))
    {
      return createViewer(parent, new PopContentProvider(adapterFactory));
    }

    throw new IllegalArgumentException("title: " + title);
  }

  @Override
  protected String[] getDetailTitles(Object masterElement)
  {
    if (masterElement instanceof RootStream)
    {
      return ROOT_STREAM_DETAILS;
    }

    if (masterElement instanceof MaintenanceStream)
    {
      return MAINTENANCE_STREAM_DETAILS;
    }

    if (masterElement instanceof TaskStream)
    {
      return TASK_STREAM_DETAILS;
    }

    return new String[0];
  }

  private StructuredViewer createViewer(Composite parent, IContentProvider contentProvider)
  {
    TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.setContentProvider(contentProvider);
    viewer.setLabelProvider(new PopLabelProvider(adapterFactory));
    return viewer;
  }
}