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
import org.eclipse.net4j.pop.internal.ui.actions.CheckoutAction;
import org.eclipse.net4j.pop.project.Branch;
import org.eclipse.net4j.pop.project.MaintenanceStream;
import org.eclipse.net4j.pop.project.RootStream;
import org.eclipse.net4j.pop.project.Stream;
import org.eclipse.net4j.pop.project.TaskStream;
import org.eclipse.net4j.pop.project.provider.ProjectItemProviderAdapterFactory;
import org.eclipse.net4j.util.ObjectUtil;
import org.eclipse.net4j.util.ui.views.MasterDetailsView;

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
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

  private static final String BRANCHES = "Branches";

  private static final String TARGETS = "Targets";

  private static final String DELIVERIES = "Deliveries";

  private static final String MERGES = "Merges";

  private static final String CHECKOUTS = "Checkouts";

  private static final String[] ROOT_STREAM_DETAILS = { CHECKOUTS, BRANCHES, MERGES, TARGETS, COMMITTERS };

  private static final String[] MAINTENANCE_STREAM_DETAILS = { CHECKOUTS, BRANCHES, MERGES, TARGETS };

  private static final String[] TASK_STREAM_DETAILS = { CHECKOUTS, BRANCHES, MERGES, DELIVERIES };

  private ComposedAdapterFactory adapterFactory;

  private TreeViewer checkoutsViewer;

  private TreeViewer branchesViewer;

  private CheckoutAction checkoutAction;

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
    checkoutAction = new CheckoutAction(getSite().getPage());

    TreeViewer viewer = createViewer(parent, new PopContentProvider.Streams(adapterFactory));
    viewer.setInput(IPopManager.INSTANCE);
    return viewer;
  }

  @Override
  protected StructuredViewer createDetail(Composite parent, String title)
  {
    if (ObjectUtil.equals(title, CHECKOUTS))
    {
      PopContentProvider contentProvider = new PopContentProvider(adapterFactory)
      {
        @Override
        public void dispose()
        {
          checkoutsViewer = null;
          super.dispose();
        }
      };

      checkoutsViewer = createViewer(parent, contentProvider);
      return checkoutsViewer;
    }

    if (ObjectUtil.equals(title, BRANCHES))
    {
      PopContentProvider contentProvider = new PopContentProvider.Branches(adapterFactory)
      {
        @Override
        public void dispose()
        {
          branchesViewer = null;
          super.dispose();
        }
      };

      branchesViewer = createViewer(parent, contentProvider);
      return branchesViewer;
    }

    if (ObjectUtil.equals(title, MERGES))
    {
      return createViewer(parent, new PopContentProvider.Merges(adapterFactory));
    }

    if (ObjectUtil.equals(title, TARGETS))
    {
      return createViewer(parent, new PopContentProvider.Targets(adapterFactory));
    }

    if (ObjectUtil.equals(title, DELIVERIES))
    {
      return createViewer(parent, new PopContentProvider.Deliveries(adapterFactory));
    }

    if (ObjectUtil.equals(title, COMMITTERS))
    {
      return createViewer(parent, new PopContentProvider.Committers(adapterFactory));
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

  @Override
  protected void setDetailInput(StructuredViewer viewer, Object input)
  {
    checkoutAction.setDiscriminator(null);
    if (input instanceof Stream)
    {
      Stream stream = (Stream)input;
      if (viewer == checkoutsViewer)
      {
        super.setDetailInput(viewer, stream.getPopProject());
        Branch branch = stream.getBranch();
        if (branch != null && branch.hasCheckout())
        {
          selectAndReveal(viewer, branch.getCheckout());
          checkoutAction.setDiscriminator(branch);
        }

        return;
      }

      if (viewer == branchesViewer)
      {
        super.setDetailInput(viewer, stream.getPopProject());
        selectAndReveal(viewer, stream.getBranch());
        return;
      }
    }

    super.setDetailInput(viewer, input);
  }

  @Override
  protected void fillCoolBar(IContributionManager manager)
  {
    if (ObjectUtil.equals(getCurrentDetailTitle(), CHECKOUTS))
    {
      manager.add(checkoutAction);
    }
  }

  private void selectAndReveal(StructuredViewer viewer, Object object)
  {
    if (object != null)
    {
      if (viewer instanceof TreeViewer)
      {
        ((TreeViewer)viewer).expandToLevel(object, 0);
      }

      viewer.setSelection(new StructuredSelection(object));
    }
    else
    {
      viewer.setSelection(StructuredSelection.EMPTY);
    }
  }

  private TreeViewer createViewer(Composite parent, IContentProvider contentProvider)
  {
    return createViewer(parent, SWT.H_SCROLL | SWT.V_SCROLL, contentProvider);
  }

  private TreeViewer createViewer(Composite parent, int style, IContentProvider contentProvider)
  {
    TreeViewer viewer = new TreeViewer(parent, style);
    viewer.setContentProvider(contentProvider);
    viewer.setLabelProvider(new PopLabelProvider(adapterFactory));
    return viewer;
  }
}
