var BrowseController = {
    init: function () {

        // Load project hierarchy
        DawnWeb.getClient().then(function (server) {
            return server.apis.browse.getProjects();
        })
            .then(function (result) {
                $("#serverconnect").html("TRUE");
                $("#serverconnect").attr("class", "boldgreen");

                BrowseController.createHierarchy(result);
                $("#structure").show(400);
            });
    },
    createHierarchy: function (structureJson) {

        var projects = $("<ul>");

        // for each project
        $.each($.parseJSON(structureJson.text), function (i, project) {

            var projectEntry = $("<li>" + project.name + "</li>");
            var models = $("<ul>");

            // for each model
            $.each(project.models, function (i, model) {

                var modelEntry = "<li>" + model.name + '<span id="path' + project.name + model.name + '"></span></li>';

                // Get Path using API
                DawnWeb.getClient().then(function (server) {
                    return server.apis.diagram.getPath({projectId: project.name, modelId: model.name});
                })
                    .then(function (result) {
                        // Append later
                        $("#path" + project.name + model.name).html(" <small>(" + result.text + ")</small>");
                    });

                models.append(modelEntry);
            });

            $(projectEntry).append(models);
            $(projects).append(projectEntry);
        });

        $("#structure").append(projects);
    }
}