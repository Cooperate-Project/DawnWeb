var Accessible = {
    init: function () {
        $.urlParam = function (name) {
            var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
            if (results == null) {
                return null;
            }
            else {
                return decodeURI(results[1]) || 0;
            }
        };

        var projectId = $.urlParam('project');
        var modelId = $.urlParam('model');

        this.validateDiagram(projectId, modelId);
        this.printDiagram(projectId, modelId);
    },
    validateDiagram: function (projectId, modelId) {

        // Check if diagram exists first
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.validateDiagram({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // Warn if no diagram was found
                if (result.data == "false") {
                    $("#DiagramWarning").html('Model "' + modelId + '" not found in project "' + projectId + '".').show();
                }

            });
    },
    printDiagram: function (projectId, modelId) {

        // Get syntax hierarchy from server
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.getSyntaxHierarchy({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // JSON Object
                var diagram = $.parseJSON(result.text);
                console.log(diagram);

                var content = $('<h2 id="Diagram' + diagram.id + 'Title" data-coord-x="' + diagram.x +
                    '" data-coord-y="' + diagram.y + '" tabindex="-1">' + DawnWeb.define(diagram.value) + '</h2>');

                $(content).append('<ul id="Elem' + diagram.id +
                    'Tree" class="tree root-level" role="tree" aria-labelledby="Diagram' + diagram.id + 'Title">');

                $.each(diagram.children, function (i, child) {

                    if (Accessible.isGroup(child)) {
                        $(content).append(Accessible.printGroup(child, ""));
                    } else {
                        $(content).append(Accessible.printValue(child, ""));
                    }
                });

                $("#InnerSyntaxHierarchy").html(content);
            });

    },
    isGroup: function (element) {
        return element.children.length > 0;
    },
    printGroup: function (elem, suffix) {

    },
    printValue: function (elem, suffix) {

        var modifiers = Accessible.getModifiers(elem);

        return $('<li id="Elem' + elem.id + suffix + '" role="treeitem" tabindex="-1" class="' + modifiers +
            '" data-cdo-id="' + elem.id + '">' + DawnWeb.define(elem.value) + '</li>');


    },
    getModifiers: function (elem) {
        var modifiers = "";
        if (elem.mutable) {
            modifiers += "mutable ";
        }
        if (elem.removable) {
            modfifiers += "removable";
        }
        return modifiers;
    }
}