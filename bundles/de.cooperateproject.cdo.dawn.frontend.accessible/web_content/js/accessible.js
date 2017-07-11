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
    },
    render: function () {

        var project = $.urlParam('project');
        var model = $.urlParam('model');

        Accessible.validateDiagram(project, model, $("#DiagramWarning"));

        // Printing diagram (#SyntaxHierarchy)
        Accessible.appendDiagram(project, model, "", $("#SyntaxHierarchy"));

        // Printing clusters (#ClusterHierarchies)
        Accessible.appendClusters(project, model, suffix, $("#ClusterHierarchies"));

        Accessible.registerFeatureIds(project, model);

        Accessible.initDawnWeb(project, model);

    },
    validateDiagram: function (projectId, modelId, domElement) {

        // Check if diagram exists first
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.validateDiagram({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // Warn if no diagram was found
                if (result.data == "false") {
                    $(domElement).html('Model "' + modelId + '" not found in project "' + projectId + '".').show();
                }

            });
    },
    appendDiagram: function (projectId, modelId, suffix, domElement) {

        // Get syntax hierarchy from server
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.getSyntaxHierarchy({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // JSON Object
                var diagram = $.parseJSON(result.text);

                domElement.append(Accessible.printDiagram(diagram, suffix));

            });

    },
    printDiagram: function (diagram, suffix) {

        var content = $('<div role="application">');

        var title = $('<h2 id="Diagram' + diagram.id + 'Title' + suffix + '" data-coord-x="' + diagram.x +
            '" data-coord-y="' + diagram.y + '" tabindex="-1">' + DawnWeb.define(diagram.value) + '</h2>');

        var elementList = $('<ul id="Elem' + diagram.id +
            'Tree' + suffix + '" class="tree root-level" role="tree" aria-labelledby="Diagram' + diagram.id +
            'Title' + suffix + '">');

        $.each(diagram.children, function (i, child) {

            if (Accessible.isGroup(child)) {
                $(elementList).append(Accessible.printGroup(child, suffix));
            } else {
                $(elementList).append(Accessible.printValue(child, suffix));
            }
        });

        // Create modified treeView with the given dom element
        new treeview(elementList);

        $(content).append(title);
        $(content).append(elementList);

        return content;
    },
    isGroup: function (elem) {
        return elem.children.length > 0;
    },
    isReference: function (elem) {
        return elem.referencedObject != null;
    },
    printGroup: function (elem, suffix) {

        var content = $('<li id="Elem' + elem.id + suffix + '" class="tree-parent ' + Accessible.getModifiers(elem) +
            '" role="treeitem" aria-expanded="true" tabindex="-1" data-cdo-id="' + elem.id +
            '">' + DawnWeb.define(elem.value) + '</li>');

        var childList = $('<ul id="Elem' + elem.id + 'Tree' + suffix + '" role="group" tabindex="-1">');

        $.each(elem.children, function (i, child) {

            if (Accessible.isGroup(child) || Accessible.isReference(child)) {
                $(childList).append(Accessible.printGroup(child, suffix));
            } else {
                $(childList).append(Accessible.printValue(child, suffix));
            }
        });

        if (Accessible.isReference(elem)) {
            $(childList).append(Accessible.printReference(elem.id, elem.referencedObject, suffix));
        }

        content.append(childList);
        return content;
    },
    printValue: function (elem, suffix) {

        var modifiers = Accessible.getModifiers(elem);

        return $('<li id="Elem' + elem.id + suffix + '" role="treeitem" tabindex="-1" class="' + modifiers +
            '" data-cdo-id="' + elem.id + '">' + DawnWeb.define(elem.value) + '</li>');


    },
    printReference: function (parentId, referencedValue, suffix) {

        return $('<li id="Elem' + parentId + 'Link' + suffix + '" class="reference" data-referenced-element-id="Elem' +
            referencedValue.id + suffix + '" role="treeitem" tabindex="-1">' +
            DawnWeb.define(referencedValue.value) + ' (Reference) </li>');

    },
    getModifiers: function (elem) {
        var modifiers = "";
        if (elem.mutable) {
            modifiers += "mutable ";
        }
        if (elem.removable) {
            modifiers += "removable";
        }
        return modifiers;
    },
    appendClusters: function (projectId, modelId, suffix, domElement) {

        // Get cluster collection from server
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.getClusters({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // JSON Object
                var clusters = $.parseJSON(result.text);

                $.each(clusters, function (i, cluster) {

                    Accessible.appendDiagram(projectId, modelId, suffix, domElement);

                });

            });

    },
    initDawnWeb: function (projectId, modelId) {

        var url = "";

        // Get URL
        DawnWeb.getClient().then(function (server) {
            return server.apis.diagram.getPath({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {
                url = result.obj;

                // Get Date (aka last changed fake)
                DawnWeb.getClient().then(function (server) {
                    return server.apis.util.getCurrentServerTimestamp();
                })
                    .then(function (result) {

                        var lastChanged = result.text;

                        //FIXME: DawnWebUtil not working correctly (rewrite to work with new api)
                        //FIXME: URL should be: "cdo://repoX/" + url
                        //DawnWebUtil.init(url, lastChanged);

                    });
            });
    },
    registerFeatureIds: function (projectId, modelId) {

        // Get feature IDs from server
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.getFeatureIdMap({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // JSON Object
                var features = $.parseJSON(result.text);

                // Iterate over attributes and add them to the top level scope
                $.each(features, function (name, value) {
                    eval('window.' + name + ' = "' + value + '";');
                });

            });
    }
}