package org.eclipse.emf.cdo.dawn.web.js.draw2d.renderer;

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
	def String renderPage(String presetIncludes, String presetJS, String diagram) {
		return '''
			<!DOCTYPE html>
			<html>
			
			<head>
			
				«renderHead()»
				
				«presetIncludes»
			
			</head>
			
			<body>
			
			<div id="paintarea" style="position:absolute;left:0px;top:0px;width:1400px;height:1000px" aria-hidden="true">
				       <!-- The information help text -->
			</div>
				
				<script>
				
				var workflow  = new draw2d.Canvas("paintarea");
				
				«presetJS»
				
				workflow.getCommandStack().addEventListener(new org.eclipse.emf.cdo.dawn.web.basic.DawnCommandListener(DawnWebUtil.moveNode,DawnWebUtil.deleteNode));
				
				</script>
			
			</body>
			
			</html>
		''';

	}

	private def String renderHead() {
		return '''
			<title>
				Accessible UML Diagram Editor
			</title>
			
			<meta charset="UTF-8">
			
			«addJSLibrary("https://code.jquery.com/jquery-3.1.1.min.js")»
			«addJSLibrary("https://code.jquery.com/ui/1.12.1/jquery-ui.min.js")»
			«addJSLibrary("draw2d/with_namespace/dist/jquery.autoresize.js")»
			«addJSLibrary("draw2d/with_namespace/dist/jquery-touch_punch.js")»
			«addJSLibrary("draw2d/with_namespace/dist/jquery.contextmenu.js")»
			«addJSLibrary("draw2d/with_namespace/dist/shifty.js")»
			
			«addJSLibrary("draw2d/with_namespace/dist/patched_raphael.js")»
			«addJSLibrary("draw2d/with_namespace/dist/rgbcolor.js")»
			«addJSLibrary("draw2d/with_namespace/dist/patched_canvg.js")»
			
			«addJSLibrary("draw2d/with_namespace/dist/patched_Class.js")»
			
			«addJSLibrary("draw2d/with_namespace/dist/pathfinding-browser.min.js")»
			
			«addJSLibrary("draw2d/with_namespace/dist/draw2d.js")»
			
			«addJSLibrary("renderer/draw2d/javaScript/dawnDiagramLib.js")»
		''';
	}

	private def String addJSLibrary(String url) {
		return '''<script src="«url»"></script>''';
	}
}
