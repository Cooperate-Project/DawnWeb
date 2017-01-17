package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

import java.util.ArrayList

/**
 * @author Shengjia Feng
 */
public class DawnAccessibleRenderer {
	/**
	 * Renders the web page using template.
	 * 
	 * @param JSScripts
	 * 			Contains additional includes to insert into the <head>-area.
	 * @param JSRenderScripts
	 * 			Contains JavaScript to include in the <script>-section.
	 * @param diagram
	 * 			Contains the diagram as syntax hierarchy.
	 * @param clusters
	 * 			Contains the diagram as clusters (multiple syntax hierarchies).
	 * @param JSVariables
	 * 			Key-value pairs defining preset JavaScript variables.
	 */
	def String renderPage(ArrayList<String> JSScripts, ArrayList<String> JSRenderScripts, 
		DiagramExchangeObject diagram, ArrayList<DiagramExchangeObject> clusters, 
		ArrayList<String[]> JSVariables) {

		var suffix = "Cluster";

		return '''
				<!DOCTYPE html>
				<html>
				
				<head>
				
				<title>Accessible Dawn Web Viewer</title>
				
				<style>
				body {
					font: 12px/16px normal Helvetica, Calibri, Arial, sans-serif;	
				}
				
				.headerImg {
					margin-right: 10px;
				}
				
				#HierarchyArea {
					position: absolute;
					top: 52px;
					left: 20px;
					width: 50%;
				}
				
				#StatusDisplay {
					position: absolute;
					top: 20px;
					left: 20px;
					right: 20px;
					height: 32px;
				}
				
				ul li {
					list-style: none;
				}
				
				[aria-hidden=true] {
					display: none;
				}
				
				</style>
				
				«FOR script : JSScripts»
					<script src="«script»"></script>
				«ENDFOR»
				
				<script src="https://cdn.jsdelivr.net/jquery.hotkeys/0.8b/jquery.hotkeys.min.js"></script>
				
				</head>
				
				<body>
				
				<!--<div id="paintarea" style="position: absolute; left: 0px; top: 0px; width: 50%; height: 100%" aria-hidden="true">
				</div>-->
				
				<div id="StatusDisplay" role="status" aria-live="polite">
				Welcome to the Accessible Dawn Editor!
				</div>
				
				<div id="HierarchyArea">
				
					<div id="SyntaxHierarchy">
				
						<div role="application">
						«printDiagram(diagram, "")»
						</div>
					
					</div>
					
					<div id="ClusterHierarchies" aria-hidden="true">
					
					«FOR c : clusters»
						
						<div role="application">
						
						«printDiagram(c, suffix)»
						
						</div>
						
					«ENDFOR»
					
					</div>
					
				</div>
					
			<script>
			
			var syntaxHierarchy = new treeview('Elem«diagram.getId()»Tree');
			
			«FOR c : clusters»
				new treeview('Elem«c.getId()»Tree«suffix»');
			«ENDFOR»			
			
			var clusterSuffix = '«suffix»';
			«FOR v : JSVariables»
				var «v.get(0)» = «v.get(1)»;
			«ENDFOR»
						
			«FOR row : JSRenderScripts»
				«row»
			«ENDFOR»
			
				</script>
				</body>
			</html>
		''';
	}

	private def printDiagram(DiagramExchangeObject diagram, String suffix) {
		return '''
			<h2 id="Diagram«diagram.getId()»Title«suffix»" data-coord-x="«diagram.getX()»" data-coord-y="«diagram.getY()»" tabindex="-1">«diagram.getValue()»</h2>
			<ul id="Elem«diagram.getId()»Tree«suffix»" class="tree root-level" role="tree"
			 aria-labelledby="Diagram«diagram.getId()»Title«suffix»">
			 «FOR e : diagram.getChildren()»
			 	«IF e.isGroup()»
			 		«printGroup(e, suffix)»
			 	«ELSE»
			 		«printValue(e, suffix)»
			 	«ENDIF»
			 «ENDFOR»
			</ul>
		'''
	}

	private def printGroup(DiagramExchangeObject elem, String suffix) {
		return '''
		<li id="Elem«elem.getId()»«suffix»" class="tree-parent «printModifiers(elem)»" role="treeitem" aria-expanded="true" 
			tabindex="-1" data-cdo-id="«elem.getId()»">«elem.getValue()»
			<ul id="Elem«elem.getId()»«suffix»Tree" role="group" tabindex="-1">
			«FOR e : elem.getChildren()»
				«IF e.isGroup() || e.isReference()»
					«printGroup(e, suffix)»
				«ELSE»
					«printValue(e, suffix)»
				«ENDIF»
			«ENDFOR»
			
			«IF elem.isReference()»
				«printReference(elem.getId(), elem.getReferencedObject(), suffix)»
			«ENDIF»
			</ul>
		</li>'''
	}

	private def printValue(DiagramExchangeObject elem,
		String suffix) {
		return '''
		<li id="Elem«elem.getId()»«suffix»" role="treeitem" tabindex="-1" class="«printModifiers(elem)»" data-cdo-id="«elem.getId()»">
			«elem.getValue()»
		</li>''';
	}

	private def printReference(String parentId, DiagramExchangeObject referencedValue, String suffix) {
		return '''
		<li id="Elem«parentId»Link«suffix»" class="reference" data-referenced-element-id="Elem«referencedValue.getId()»«suffix»" role="treeitem" tabindex="-1">
		«referencedValue.getValue()» (Reference)
		</li>''';
	}

	private def printModifiers(DiagramExchangeObject elem) {
		var returnString = '''''';
		if(elem.getMutable()) returnString += '''mutable '''
		if(elem.getRemovable()) returnString += '''removable'''
		return returnString;
	}

}
