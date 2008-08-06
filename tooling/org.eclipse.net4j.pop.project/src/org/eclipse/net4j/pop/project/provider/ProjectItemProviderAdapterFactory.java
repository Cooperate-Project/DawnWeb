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
 * $Id: ProjectItemProviderAdapterFactory.java,v 1.13 2008-08-06 10:32:46 estepper Exp $
 */
package org.eclipse.net4j.pop.project.provider;

import org.eclipse.net4j.pop.project.ProjectPackage;
import org.eclipse.net4j.pop.project.util.ProjectActivator;
import org.eclipse.net4j.pop.project.util.ProjectAdapterFactory;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ChildCreationExtenderManager;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IChildCreationExtender;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemFontProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers. The adapters generated by this
 * factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}. The adapters
 * also support Eclipse property sheets. Note that most of the adapters are shared among multiple instances. <!--
 * begin-user-doc --> <!-- end-user-doc -->
 * 
 * @generated
 */
public class ProjectItemProviderAdapterFactory extends ProjectAdapterFactory implements ComposeableAdapterFactory,
    IChangeNotifier, IDisposable, IChildCreationExtender
{
  /**
   * This keeps track of the root adapter factory that delegates to this adapter factory.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected ComposedAdapterFactory parentAdapterFactory;

  /**
   * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  protected IChangeNotifier changeNotifier = new ChangeNotifier();

  /**
   * This helps manage the child creation extenders.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected ChildCreationExtenderManager childCreationExtenderManager = new ChildCreationExtenderManager(
      ProjectActivator.INSTANCE, ProjectPackage.eNS_URI);

  /**
   * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected Collection<Object> supportedTypes = new ArrayList<Object>();

  /**
   * This constructs an instance.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public ProjectItemProviderAdapterFactory()
  {
    supportedTypes.add(IEditingDomainItemProvider.class);
    supportedTypes.add(IStructuredItemContentProvider.class);
    supportedTypes.add(ITreeItemContentProvider.class);
    supportedTypes.add(IItemLabelProvider.class);
    supportedTypes.add(IItemPropertySource.class);
    supportedTypes.add(IItemColorProvider.class);
    supportedTypes.add(IItemFontProvider.class);
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.PopProject} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected PopProjectItemProvider popProjectItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.PopProject}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createPopProjectAdapter()
  {
    if (popProjectItemProvider == null)
    {
      popProjectItemProvider = new PopProjectItemProvider(this);
    }

    return popProjectItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Repository} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected RepositoryItemProvider repositoryItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Repository}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createRepositoryAdapter()
  {
    if (repositoryItemProvider == null)
    {
      repositoryItemProvider = new RepositoryItemProvider(this);
    }

    return repositoryItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.PrimaryModule} instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PrimaryModuleItemProvider primaryModuleItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.PrimaryModule}.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createPrimaryModuleAdapter()
  {
    if (primaryModuleItemProvider == null)
    {
      primaryModuleItemProvider = new PrimaryModuleItemProvider(this);
    }

    return primaryModuleItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Checkout} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected CheckoutItemProvider checkoutItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Checkout}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createCheckoutAdapter()
  {
    if (checkoutItemProvider == null)
    {
      checkoutItemProvider = new CheckoutItemProvider(this);
    }

    return checkoutItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Committer} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected CommitterItemProvider committerItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Committer}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createCommitterAdapter()
  {
    if (committerItemProvider == null)
    {
      committerItemProvider = new CommitterItemProvider(this);
    }

    return committerItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Tag} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected TagItemProvider tagItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Tag}. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   */
  @Override
  public Adapter createTagAdapter()
  {
    if (tagItemProvider == null)
    {
      tagItemProvider = new TagItemProvider(this);
    }

    return tagItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.MainBranch} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected MainBranchItemProvider mainBranchItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.MainBranch}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createMainBranchAdapter()
  {
    if (mainBranchItemProvider == null)
    {
      mainBranchItemProvider = new MainBranchItemProvider(this);
    }

    return mainBranchItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.SubBranch} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected SubBranchItemProvider subBranchItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.SubBranch}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createSubBranchAdapter()
  {
    if (subBranchItemProvider == null)
    {
      subBranchItemProvider = new SubBranchItemProvider(this);
    }

    return subBranchItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.TaskStream} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected TaskStreamItemProvider taskStreamItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.TaskStream}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createTaskStreamAdapter()
  {
    if (taskStreamItemProvider == null)
    {
      taskStreamItemProvider = new TaskStreamItemProvider(this);
    }

    return taskStreamItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.MaintenanceStream} instances.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected MaintenanceStreamItemProvider maintenanceStreamItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.MaintenanceStream}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createMaintenanceStreamAdapter()
  {
    if (maintenanceStreamItemProvider == null)
    {
      maintenanceStreamItemProvider = new MaintenanceStreamItemProvider(this);
    }

    return maintenanceStreamItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.RootStream} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected RootStreamItemProvider rootStreamItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.RootStream}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createRootStreamAdapter()
  {
    if (rootStreamItemProvider == null)
    {
      rootStreamItemProvider = new RootStreamItemProvider(this);
    }

    return rootStreamItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Release} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected ReleaseItemProvider releaseItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Release}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createReleaseAdapter()
  {
    if (releaseItemProvider == null)
    {
      releaseItemProvider = new ReleaseItemProvider(this);
    }

    return releaseItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Milestone} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected MilestoneItemProvider milestoneItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Milestone}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createMilestoneAdapter()
  {
    if (milestoneItemProvider == null)
    {
      milestoneItemProvider = new MilestoneItemProvider(this);
    }

    return milestoneItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Delivery} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected DeliveryItemProvider deliveryItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Delivery}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createDeliveryAdapter()
  {
    if (deliveryItemProvider == null)
    {
      deliveryItemProvider = new DeliveryItemProvider(this);
    }

    return deliveryItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.project.Merge} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected MergeItemProvider mergeItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.project.Merge}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createMergeAdapter()
  {
    if (mergeItemProvider == null)
    {
      mergeItemProvider = new MergeItemProvider(this);
    }

    return mergeItemProvider;
  }

  /**
   * This returns the root adapter factory that contains this factory.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public ComposeableAdapterFactory getRootAdapterFactory()
  {
    return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
  }

  /**
   * This sets the composed adapter factory that contains this factory.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory)
  {
    this.parentAdapterFactory = parentAdapterFactory;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object type)
  {
    return supportedTypes.contains(type) || super.isFactoryForType(type);
  }

  /**
   * This implementation substitutes the factory itself as the key for the adapter.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter adapt(Notifier notifier, Object type)
  {
    return super.adapt(notifier, this);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object adapt(Object object, Object type)
  {
    if (isFactoryForType(type))
    {
      Object adapter = super.adapt(object, type);
      if (!(type instanceof Class) || (((Class<?>)type).isInstance(adapter)))
      {
        return adapter;
      }
    }

    return null;
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public List<IChildCreationExtender> getChildCreationExtenders()
  {
    return childCreationExtenderManager.getChildCreationExtenders();
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public Collection<?> getNewChildDescriptors(Object object, EditingDomain editingDomain)
  {
    return childCreationExtenderManager.getNewChildDescriptors(object, editingDomain);
  }

  /**
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public ResourceLocator getResourceLocator()
  {
    return childCreationExtenderManager;
  }

  /**
   * This adds a listener.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void addListener(INotifyChangedListener notifyChangedListener)
  {
    changeNotifier.addListener(notifyChangedListener);
  }

  /**
   * This removes a listener.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void removeListener(INotifyChangedListener notifyChangedListener)
  {
    changeNotifier.removeListener(notifyChangedListener);
  }

  /**
   * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  public void fireNotifyChanged(Notification notification)
  {
    changeNotifier.fireNotifyChanged(notification);

    if (parentAdapterFactory != null)
    {
      parentAdapterFactory.fireNotifyChanged(notification);
    }
  }

  /**
   * This disposes all of the item providers created by this factory. 
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public void dispose()
  {
    if (popProjectItemProvider != null)
      popProjectItemProvider.dispose();
    if (repositoryItemProvider != null)
      repositoryItemProvider.dispose();
    if (primaryModuleItemProvider != null)
      primaryModuleItemProvider.dispose();
    if (committerItemProvider != null)
      committerItemProvider.dispose();
    if (tagItemProvider != null)
      tagItemProvider.dispose();
    if (mainBranchItemProvider != null)
      mainBranchItemProvider.dispose();
    if (subBranchItemProvider != null)
      subBranchItemProvider.dispose();
    if (checkoutItemProvider != null)
      checkoutItemProvider.dispose();
    if (taskStreamItemProvider != null)
      taskStreamItemProvider.dispose();
    if (maintenanceStreamItemProvider != null)
      maintenanceStreamItemProvider.dispose();
    if (rootStreamItemProvider != null)
      rootStreamItemProvider.dispose();
    if (releaseItemProvider != null)
      releaseItemProvider.dispose();
    if (milestoneItemProvider != null)
      milestoneItemProvider.dispose();
    if (deliveryItemProvider != null)
      deliveryItemProvider.dispose();
    if (mergeItemProvider != null)
      mergeItemProvider.dispose();
  }

}
