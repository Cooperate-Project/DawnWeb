package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList

/**
 * @author Shengjia Feng
 */
public class DawnAccessibleRenderer {
	/**
	 * Renders the web page using template.
	 * 
	 * @param String presetIncludes
	 * 			Contains additional includes to insert into the <head>-area.
	 * @param String presetJS
	 * 			Contains JavaScript to include in the <script>-section.
	 * @param DiagramExchangeObject diagram
	 * 			Contains the diagram as JSON.
	 */
	def String renderPage(ArrayList<String> JSScripts, ArrayList<String> JSRenderScripts,
		DiagramExchangeObject diagram, ArrayList<DiagramExchangeObject> clusters) {

		return '''
				<!DOCTYPE html>
				<html>
				
				<head>
				
				<title>Accessible Dawn Web Viewer</title>
				
				<style>
				.headerImg {
				margin-right: 10px;
				}
				ul li {
					list-style: none;
				}
				</style>
				
				«FOR script : JSScripts»
				<script src="«script»"></script>
				«ENDFOR»
				
				<script src="https://cdn.jsdelivr.net/jquery.hotkeys/0.8b/jquery.hotkeys.min.js"></script>
				
				</head>
				
				<body>
				
				<!--<div id="paintarea" style="position: absolute; left: 0px; top: 0px; width: 70%; height: 100%" aria-hidden="true">
				</div>-->
				
				<div id="SyntaxHierarchy" style="position: absolute; right: 0px; top: 0px; width: 30%; height: 100%;" aria-hidden="false">
			
				<div role="application">
				«printDiagram(diagram)»
				</div>
				
				</div>
				
				<div id="ClusterHierarchies" style="position: absolute; left: 0px; top: 0px; width: 70%; height: 100%">
				
				«FOR c : clusters»
				
				<div role="application">
				
				«printDiagram(c)»
				
				</div>
				
				«ENDFOR»
				
				</div>
					
			<script>
			
			$(document).ready(function() {
						
				new treeview('«diagram.getId()»Tree');
				
				«FOR c : clusters»
					new treeview('«c.getId()»Tree');
				«ENDFOR»						
			});
			
			«««FOR row : JSRenderScripts»
				«««row»
			«««ENDFOR»
						
			var tabindexCounter = 0;
			
			$('.root-level').each(function() {
				$(this).find('li').first().attr('tabindex', tabindexCounter);
				++tabindexCounter;
			});
			
				</script>
				
				</body>
				
				</html>
		''';
	}
	
	private def printDiagram(DiagramExchangeObject diagram) {
		return '''
			<h2 id="«diagram.getId()»">«diagram.getValue()»</h2>
			<ul id="«diagram.getId()»Tree" class="tree root-level" role="tree"
			 aria-labelledby="Diagram«diagram.getId()»Title">
			 «FOR e : diagram.getChildren()»
			 	«IF e.isGroup()»
			 		«printGroup(e)»
			 	«ELSE»
			 		«printValue(e)»
			 	«ENDIF»
			 «ENDFOR»
			</ul>
		'''
	}

	private def printGroup(DiagramExchangeObject elem) {
		return '''
		<li id="«elem.getId()»" class="tree-parent" role="treeitem" aria-expanded="true"">«elem.getValue()»
			<ul id="«elem.getId()»" role="group" tabindex="-1">
			«FOR e : elem.getChildren()»
				«IF e.isGroup() || e.isReference()»
					«printGroup(e)»
				«ELSE»
					«printValue(e)»
				«ENDIF»
			«ENDFOR»
			
			«IF elem.isReference()»
				«printReference(elem.getReferencedObject())»
			«ENDIF»
			</ul>
		</li>'''
	}

	private def printValue(DiagramExchangeObject elem) {
		return '''<li id="«elem.getId()»" role="treeitem" tabindex="-1">«elem.getValue()»</li>'''
	}

	private def printReference(DiagramExchangeObject referencedValue) {
		// No ID here because this value is never jumped to
		return '''<li class="reference" data-referenced-element-id="«referencedValue.getId()»" role="treeitem" tabindex="-1">
		«referencedValue.getValue()» (Reference)
		</li>'''
	}

}
