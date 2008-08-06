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

import org.eclipse.net4j.internal.pop.bundle.OM;
import org.eclipse.net4j.pop.base.PopElement;
import org.eclipse.net4j.pop.project.Checkout;
import org.eclipse.net4j.pop.project.CheckoutDiscriminator;
import org.eclipse.net4j.pop.project.Module;
import org.eclipse.net4j.pop.project.PopProject;
import org.eclipse.net4j.pop.project.ProjectFactory;
import org.eclipse.net4j.pop.project.Repository;
import org.eclipse.net4j.pop.project.impl.CheckoutImpl;
import org.eclipse.net4j.pop.project.impl.ICheckoutManager;
import org.eclipse.net4j.pop.repository.IRepositoryAdapter;
import org.eclipse.net4j.pop.repository.IRepositoryFolder;
import org.eclipse.net4j.pop.repository.IRepositorySession;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.container.Container;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import java.io.File;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class CheckoutManager extends Container<Checkout> implements ICheckoutManager
{
  private static final IWorkspace WS = ResourcesPlugin.getWorkspace();

  private static final IWorkspaceRoot ROOT = WS.getRoot();

  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, CheckoutManager.class);

  private Pop pop;

  private IPath location;

  private Map<CheckoutDiscriminator, Checkout> checkouts = new HashMap<CheckoutDiscriminator, Checkout>();

  public CheckoutManager(Pop pop)
  {
    this.pop = pop;
  }

  public Pop getPop()
  {
    return pop;
  }

  public PopProject getPopProject()
  {
    return pop.getPopProject();
  }

  public void addCheckout(Checkout checkout)
  {
    CheckoutDiscriminator discriminator = checkout.getDiscriminator();
    synchronized (checkouts)
    {
      if (checkouts.containsKey(discriminator))
      {
        throw new IllegalStateException("Duplicate  ID: " + discriminator);
      }

      checkouts.put(discriminator, checkout);
    }

    if (TRACER.isEnabled())
    {
      TRACER.trace("Added checkout: " + checkout);
    }

    if (isActive())
    {
      fireElementAddedEvent(checkout);
    }
  }

  public Checkout removeCheckout(CheckoutDiscriminator discriminator)
  {
    Checkout checkout = null;
    synchronized (checkouts)
    {
      checkout = checkouts.remove(discriminator);
    }

    if (checkout != null)
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace("Removed checkout: " + checkout);
      }

      if (isActive())
      {
        fireElementRemovedEvent(checkout);
      }

      return checkout;
    }

    return null;
  }

  public boolean hasCheckout(CheckoutDiscriminator discriminator)
  {
    synchronized (checkouts)
    {
      return checkouts.containsKey(discriminator);
    }
  }

  public Checkout getCheckout(CheckoutDiscriminator discriminator)
  {
    synchronized (checkouts)
    {
      return checkouts.get(discriminator);
    }
  }

  public Checkout[] getCheckouts()
  {
    synchronized (checkouts)
    {
      return checkouts.values().toArray(new Checkout[checkouts.size()]);
    }
  }

  public Checkout checkout(CheckoutDiscriminator discriminator)
  {
    checkActive();
    IProgressMonitor monitor = new NullProgressMonitor();
    IProject project = createProject(discriminator.getId(), monitor);
    Repository repository = discriminator.getRepository();
    Module module = repository.getPrimaryModule();

    IRepositoryAdapter adapter = repository.getAdapter();
    IRepositorySession session = adapter.openSession(repository.getDescriptor(), project, false, monitor);

    try
    {
      // IFolder target = createFolder(project, module.getName(), monitor);
      IRepositoryFolder folder = session.getFolder(discriminator.getRepositoryTag(), module.getDescriptor());
      folder.checkoutInto(project, module.getName(), true, new NullProgressMonitor());

      return createCheckout(discriminator, project.getLocation());
    }
    finally
    {
      session.close();
    }
  }

  private IProject createProject(String name, IProgressMonitor monitor)
  {
    try
    {
      IProject project = ROOT.getProject(name);
      if (project.exists())
      {
        throw new IllegalStateException("Project already exists: " + name);
      }

      IProjectDescription projectDescription = WS.newProjectDescription(name);
      projectDescription.setComment("POP Process Tooling checkout project");
      projectDescription.setLocation(location.append(name));

      project.create(projectDescription, monitor);
      project.open(monitor);
      return project;
    }
    catch (CoreException ex)
    {
      throw WrappedException.wrap(ex);
    }
  }

  private IFolder createFolder(IContainer container, String name, IProgressMonitor monitor)
  {
    try
    {
      IFolder folder = container.getFolder(new Path(name));
      if (folder.exists())
      {
        throw new IllegalStateException("Folder already exists: " + folder.getFullPath());
      }

      folder.create(true, true, monitor);
      return folder;
    }
    catch (CoreException ex)
    {
      throw WrappedException.wrap(ex);
    }
  }

  public Checkout[] getElements()
  {
    return getCheckouts();
  }

  @SuppressWarnings("unchecked")
  public Object getAdapter(Class adapter)
  {
    return Platform.getAdapterManager().getAdapter(this, adapter);
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("CheckoutManager[{0}]", pop.getProject().getName());
  }

  public void checkoutLocationChanged()
  {
    // TODO Fire clear event
    checkouts.clear();
    init();
  }

  @Override
  protected void doActivate() throws Exception
  {
    super.doActivate();
    init();
  }

  @Override
  protected void doDeactivate() throws Exception
  {
    checkouts.clear();
    super.doDeactivate();
  }

  private void init()
  {
    PopProject popProject = pop.getPopProject();
    location = PopManager.INSTANCE.getCheckoutLocation().append(popProject.getName());
    File locationFolder = location.toFile();
    if (locationFolder.exists() && locationFolder.isDirectory())
    {
      for (File folder : locationFolder.listFiles())
      {
        String checkoutName = folder.getName();
        PopElement popElement = pop.getPopElement(checkoutName);
        if (popElement instanceof CheckoutDiscriminator)
        {
          CheckoutDiscriminator discriminator = (CheckoutDiscriminator)popElement;
          createCheckout(discriminator, location.append(discriminator.getId()));
        }
      }
    }
  }

  private Checkout createCheckout(CheckoutDiscriminator discriminator, IPath checkoutLocation)
  {
    CheckoutImpl checkout = (CheckoutImpl)ProjectFactory.eINSTANCE.createCheckout();
    checkout.setDiscriminator(discriminator);
    checkout.setLocation(checkoutLocation);

    addCheckout(checkout);
    return checkout;
  }
}
