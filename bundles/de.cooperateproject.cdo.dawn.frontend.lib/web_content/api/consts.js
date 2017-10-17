/**
 * Contains the most important base constants and URLs.
 * @type {{serverBaseURL: string, serverSwaggerName: string, accessibleEditorURL: string, draw2DEditorURL: string}}
 */
var Consts = {
    /**
     * the base url for all services to connect to.
     */
    serverBaseURL: string = "http://localhost:9090/services/",

    /**
     * the filename of the generated server interface documentation
     */
    serverSwaggerName: string = "swagger.json",

    /**
     * The relative URL to the accessible editor
     */
    accessibleEditorURL: string = "../../de.cooperateproject.cdo.dawn.frontend.accessible/web_content/accessible.html",

    /**
     * The relative URL to the graphical editor
     */
    draw2DEditorURL: string = "../../../../DawnWeb-Draw2d/bundles/de.cooperateproject.cdo.dawn.frontend.draw2d/web_content/draw2d.html"
}