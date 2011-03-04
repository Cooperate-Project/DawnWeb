/**
 * Copyright (c) 2004 - 2011 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */
package org.eclipse.emf.cdo.threedee.ui.views;

import org.eclipse.emf.cdo.threedee.common.ElementDescriptor;
import org.eclipse.emf.cdo.threedee.ui.ThreeDeeWorldView;
import org.eclipse.emf.cdo.threedee.ui.ThreeDeeWorldViewer;

import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Martin Fluegge
 */
public class FilterView extends ViewPart
{
  public static final String ID = "org.eclipse.emf.cdo.threedee.ui.views.FilterView";

  private CheckboxTreeViewer viewer;

  @Override
  public void createPartControl(Composite parent)
  {
    viewer = new CheckboxTreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
    viewer.setContentProvider(new ViewContentProvider());
    viewer.setLabelProvider(new ViewLabelProvider());
    viewer.setSorter(new NameSorter());
    viewer.setInput(ElementDescriptor.Registry.INSTANCE);
    viewer.setAllChecked(true);

    viewer.addCheckStateListener(new ICheckStateListener()
    {
      private Set<String> elementsToBeHidden = new HashSet<String>();

      public void checkStateChanged(CheckStateChangedEvent event)
      {
        ElementDescriptor descriptor = (ElementDescriptor)event.getElement();

        if (event.getChecked())
        {
          changeElementsToBeHidden(descriptor, false);
          viewer.setSubtreeChecked(descriptor, true);
        }
        else
        {
          changeElementsToBeHidden(descriptor, true);
          viewer.setSubtreeChecked(descriptor, false);

        }
        ThreeDeeWorldView threeDeeWorldView = (ThreeDeeWorldView)getSite().getPage().findView(ThreeDeeWorldView.ID);
        ThreeDeeWorldViewer threeDeeWorldViewViewer = threeDeeWorldView.getViewer();
        threeDeeWorldViewViewer.filter(new ArrayList<String>(elementsToBeHidden));
      }

      private void changeElementsToBeHidden(ElementDescriptor descriptor, boolean addFilter)
      {
        String name = descriptor.getName();
        System.out.println(name);
        if (addFilter)
        {
          elementsToBeHidden.add(name);
        }
        else
        {
          elementsToBeHidden.remove(name);
        }

        for (ElementDescriptor child : descriptor.getSubDescriptors())
        {
          changeElementsToBeHidden(child, addFilter);
        }
      }

      // private void addElementsToBeHidden(String name)
      // {
      // elementsToBeHidden.add(name);
      // }
    });

    PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "org.eclipse.emf.cdo.threedee.ui.viewer");
  }

  @Override
  public void setFocus()
  {
    viewer.getControl().setFocus();
  }

  private class ViewContentProvider implements ITreeContentProvider
  {
    public void inputChanged(Viewer v, Object oldInput, Object newInput)
    {
    }

    public void dispose()
    {
    }

    public Object[] getElements(Object parent)
    {
      return getChildren(parent);
    }

    public Object[] getChildren(Object parentElement)
    {
      if (parentElement instanceof ElementDescriptor)
      {
        ElementDescriptor descriptor = (ElementDescriptor)parentElement;
        return descriptor.getSubDescriptors().toArray();
      }

      if (parentElement == ElementDescriptor.Registry.INSTANCE)
      {
        return ElementDescriptor.Registry.INSTANCE.getRootDescriptors().toArray();
      }

      return new Object[0];
    }

    public Object getParent(Object element)
    {
      if (element instanceof ElementDescriptor)
      {
        ElementDescriptor descriptor = (ElementDescriptor)element;
        return descriptor.getSuperDescriptor();
      }

      return null;
    }

    public boolean hasChildren(Object element)
    {
      Object[] children = getChildren(element);
      return children != null && children.length != 0;
    }
  }

  /**
   * @author Martin Fluegge
   */
  private class ViewLabelProvider extends LabelProvider
  {
    @Override
    public String getText(Object element)
    {
      if (element instanceof ElementDescriptor)
      {
        return ((ElementDescriptor)element).getName();
      }
      return element.toString();
    }
  }

  /**
   * @author Martin Fluegge
   */
  private class NameSorter extends ViewerSorter
  {
  }
}
