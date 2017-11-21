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
    accessibleEditorURL: string = "/frontend_accessible/accessible.html",

    /**
     * The relative URL to the graphical editor
     */
    draw2DEditorURL: string = "/frontend_draw2d/draw2d.html"
}