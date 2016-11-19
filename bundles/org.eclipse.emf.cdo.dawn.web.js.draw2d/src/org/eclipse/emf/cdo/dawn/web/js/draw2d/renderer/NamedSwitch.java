package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class NamedSwitch extends UMLSwitch<String>
{
  @Override
  public java.lang.String caseNamedElement(NamedElement obj)
  {
    return obj.getName();
  }
}