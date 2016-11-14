package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.util.UMLSwitch;

public class TypedSwitch extends UMLSwitch<String>
{
  @Override
  public String caseTypedElement(TypedElement obj)
  {
    if (obj.getType() != null)
    {
      return obj.getType().getName();
    }

    return "undefined";
  }
}
