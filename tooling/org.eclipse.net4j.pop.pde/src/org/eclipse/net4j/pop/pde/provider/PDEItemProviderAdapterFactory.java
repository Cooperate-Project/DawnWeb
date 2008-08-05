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
 * $Id: PDEItemProviderAdapterFactory.java,v 1.3 2008-08-05 14:47:45 estepper Exp $
 */
package org.eclipse.net4j.pop.pde.provider;

import org.eclipse.net4j.pop.pde.PDEFactory;
import org.eclipse.net4j.pop.pde.PDEPackage;
import org.eclipse.net4j.pop.pde.util.PDEActivator;
import org.eclipse.net4j.pop.pde.util.PDEAdapterFactory;
import org.eclipse.net4j.pop.product.PopProduct;
import org.eclipse.net4j.pop.product.ProductPackage;
import org.eclipse.net4j.pop.product.util.ProductSwitch;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CommandParameter;
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
public class PDEItemProviderAdapterFactory extends PDEAdapterFactory implements ComposeableAdapterFactory,
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
      PDEActivator.INSTANCE, PDEPackage.eNS_URI);

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
  public PDEItemProviderAdapterFactory()
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
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.TargetPlatformChecker} instances.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  protected TargetPlatformCheckerItemProvider targetPlatformCheckerItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.TargetPlatformChecker}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createTargetPlatformCheckerAdapter()
  {
    if (targetPlatformCheckerItemProvider == null)
    {
      targetPlatformCheckerItemProvider = new TargetPlatformCheckerItemProvider(this);
    }

    return targetPlatformCheckerItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.ApiBaseline} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected ApiBaselineItemProvider apiBaselineItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.ApiBaseline}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createApiBaselineAdapter()
  {
    if (apiBaselineItemProvider == null)
    {
      apiBaselineItemProvider = new ApiBaselineItemProvider(this);
    }

    return apiBaselineItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.TargetPlatform} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected TargetPlatformItemProvider targetPlatformItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.TargetPlatform}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createTargetPlatformAdapter()
  {
    if (targetPlatformItemProvider == null)
    {
      targetPlatformItemProvider = new TargetPlatformItemProvider(this);
    }

    return targetPlatformItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.PDEProject} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected PDEProjectItemProvider pdeProjectItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.PDEProject}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createPDEProjectAdapter()
  {
    if (pdeProjectItemProvider == null)
    {
      pdeProjectItemProvider = new PDEProjectItemProvider(this);
    }

    return pdeProjectItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.Feature} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected FeatureItemProvider featureItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.Feature}. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   */
  @Override
  public Adapter createFeatureAdapter()
  {
    if (featureItemProvider == null)
    {
      featureItemProvider = new FeatureItemProvider(this);
    }

    return featureItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.Plugin} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected PluginItemProvider pluginItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.Plugin}. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   */
  @Override
  public Adapter createPluginAdapter()
  {
    if (pluginItemProvider == null)
    {
      pluginItemProvider = new PluginItemProvider(this);
    }

    return pluginItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.Fragment} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected FragmentItemProvider fragmentItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.Fragment}. <!-- begin-user-doc --> <!-- end-user-doc
   * -->
   * 
   * @generated
   */
  @Override
  public Adapter createFragmentAdapter()
  {
    if (fragmentItemProvider == null)
    {
      fragmentItemProvider = new FragmentItemProvider(this);
    }

    return fragmentItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.ZipDistribution} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected ZipDistributionItemProvider zipDistributionItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.ZipDistribution}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createZipDistributionAdapter()
  {
    if (zipDistributionItemProvider == null)
    {
      zipDistributionItemProvider = new ZipDistributionItemProvider(this);
    }

    return zipDistributionItemProvider;
  }

  /**
   * This keeps track of the one adapter used for all {@link org.eclipse.net4j.pop.pde.PopDistribution} instances. <!--
   * begin-user-doc --> <!-- end-user-doc -->
   * 
   * @generated
   */
  protected PopDistributionItemProvider popDistributionItemProvider;

  /**
   * This creates an adapter for a {@link org.eclipse.net4j.pop.pde.PopDistribution}.
   * <!-- begin-user-doc --> <!--
   * end-user-doc -->
   * @generated
   */
  @Override
  public Adapter createPopDistributionAdapter()
  {
    if (popDistributionItemProvider == null)
    {
      popDistributionItemProvider = new PopDistributionItemProvider(this);
    }

    return popDistributionItemProvider;
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
    if (targetPlatformCheckerItemProvider != null)
      targetPlatformCheckerItemProvider.dispose();
    if (apiBaselineItemProvider != null)
      apiBaselineItemProvider.dispose();
    if (targetPlatformItemProvider != null)
      targetPlatformItemProvider.dispose();
    if (pdeProjectItemProvider != null)
      pdeProjectItemProvider.dispose();
    if (featureItemProvider != null)
      featureItemProvider.dispose();
    if (pluginItemProvider != null)
      pluginItemProvider.dispose();
    if (fragmentItemProvider != null)
      fragmentItemProvider.dispose();
    if (zipDistributionItemProvider != null)
      zipDistributionItemProvider.dispose();
    if (popDistributionItemProvider != null)
      popDistributionItemProvider.dispose();
  }

  /**
   * A child creation extender for the {@link ProductPackage}.
   * <!-- begin-user-doc --> <!-- end-user-doc -->
   * @generated
   */
  public static class ProductChildCreationExtender implements IChildCreationExtender
  {
    /**
     * The switch for creating child descriptors specific to each extended class.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     */
    protected static class CreationSwitch extends ProductSwitch<Object>
    {
      /**
       * The child descriptors being populated.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * @generated
       */
      protected List<Object> newChildDescriptors;

      /**
       * The domain in which to create the children.
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * @generated
       */
      protected EditingDomain editingDomain;

      /**
       * Creates the a switch for populating child descriptors in the given domain.
       * <!-- begin-user-doc --> <!--
       * end-user-doc -->
       * @generated
       */
      CreationSwitch(List<Object> newChildDescriptors, EditingDomain editingDomain)
      {
        this.newChildDescriptors = newChildDescriptors;
        this.editingDomain = editingDomain;
      }

      /**
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * @generated
       */
      @Override
      public Object casePopProduct(PopProduct object)
      {
        newChildDescriptors.add(createChildParameter(ProductPackage.Literals.POP_PRODUCT__PROJECTS,
            PDEFactory.eINSTANCE.createPDEProject()));

        newChildDescriptors.add(createChildParameter(ProductPackage.Literals.POP_PRODUCT__CONFIGURATORS,
            PDEFactory.eINSTANCE.createTargetPlatformChecker()));

        newChildDescriptors.add(createChildParameter(ProductPackage.Literals.POP_PRODUCT__CONFIGURATORS,
            PDEFactory.eINSTANCE.createApiBaseline()));

        newChildDescriptors.add(createChildParameter(ProductPackage.Literals.POP_PRODUCT__CONFIGURATORS,
            PDEFactory.eINSTANCE.createTargetPlatform()));

        return null;
      }

      /**
       * <!-- begin-user-doc --> <!-- end-user-doc -->
       * @generated
       */
      protected CommandParameter createChildParameter(Object feature, Object child)
      {
        return new CommandParameter(null, feature, child);
      }

    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Collection<Object> getNewChildDescriptors(Object object, EditingDomain editingDomain)
    {
      ArrayList<Object> result = new ArrayList<Object>();
      new CreationSwitch(result, editingDomain).doSwitch((EObject)object);
      return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ResourceLocator getResourceLocator()
    {
      return PDEActivator.INSTANCE;
    }
  }

}
