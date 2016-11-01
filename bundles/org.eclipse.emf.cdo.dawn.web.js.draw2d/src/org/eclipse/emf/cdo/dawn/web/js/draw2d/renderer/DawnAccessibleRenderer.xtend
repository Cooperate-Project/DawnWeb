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
	 * @param String diagram
	 * 			Contains the diagram as JSON.
	 */
	def String renderPage(ArrayList<String> JSScripts, ArrayList<String> JSRenderScripts, String diagram) {
		return '''
			<!DOCTYPE html>
			<html>
			
			<head>
			
			«FOR script : JSScripts»
				<script src="«script»"></script>
		    «ENDFOR»
			
			</head>
			
			<body>
			
			<div id="paintarea" style="position: absolute; left: 0px; top: 0px; width: 70%; height: 100%" aria-hidden="true">
				<!-- The information help text -->
			</div>
			
			<div id="SyntaxHierarchy" style="position: absolute; right: 0px; top: 0px; width: 30%; height: 100%;" aria-hidden="false">
			
			«diagram»
			
			</div>
			
			<script>
			
			«FOR row : JSRenderScripts»
				«row»
		    «ENDFOR»

			</script>
			
			</body>
			
			</html>
		''';
	}
	
	
}
