/**
 * Controller for the browse view. Contains server calls to get the project hierarchy.
 * @type {{init: Browse.init, createHierarchy: Browse.createHierarchy, generateEditors: Browse.generateEditors}}
 */
var Browse = {
    /**
     * Initializes the server connection and browse view.
     */
    init: function () {

        // Load project hierarchy
        DawnWeb.getClient().then(function (server) {
            return server.apis.browse.getProjects();
        })
            .then(function (result) {
                $("#serverconnect").html("TRUE").attr("class", "boldgreen");

                Browse.createHierarchy(result);
                $("#structure").show(400);
            });
    },
    /**
     * Creates the browse view site hierarchy of the server data.
     * @param structureJson json with information about projects and models
     */
    createHierarchy: function (structureJson) {

        var projects = $("<ul>");

        // for each project
        $.each($.parseJSON(structureJson.text), function (i, project) {

            var projectEntry = $("<li>" + project.name + "</li>");
            var models = $("<ul>");

            // for each model
            $.each(project.models, function (i, model) {

                var modelEntry = $("<li>" + model.name + '<span id="path' + project.name + model.name + '"></span></li>');

                // Get Path using API
                DawnWeb.getClient().then(function (server) {
                    return server.apis.diagram.getAbsolutePath({projectId: project.name, modelId: model.name});
                })
                    .then(function (result) {
                        // Append later
                        $("#path" + project.name + model.name).html(" <small>(" + result.text + ")</small>");
                    });

                modelEntry.append(Browse.generateEditors(project.name, model.name));
                models.append(modelEntry);
            });

            $(projectEntry).append(models);
            $(projects).append(projectEntry);
        });

        $("#structure").append(projects);
    },
    /**
     * Generates the links to accessible and draw2d editor.
     * @param project the project name
     * @param model the model name
     * @returns JQuery DOM Elements with custom editor links
     */
    generateEditors: function (project, model) {

        var queryParams = "?project=" + project + "&model=" + model;

        var editors = $('<ul class="editorList">');

        editors.append('<li><a href="' + Consts.accessibleEditorURL + queryParams + '">Accessible Editor</a></li>');
        editors.append('<li><a href="' + Consts.draw2DEditorURL + queryParams + '">Draw2D Editor</a></li>');

        return editors;
    }
}