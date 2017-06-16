package de.cooperateproject.cdo.dawn.rest.accessible.util;

import org.eclipse.uml2.uml.TypedElement;
import org.eclipse.uml2.uml.util.UMLSwitch;

/**
 * Used to extract the type of TypedElements.
 *
 * @author Shengjia Feng
 */
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
