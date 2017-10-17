/**
 * The controller for the accessible editor view. In the legacy variant, a lot of this code was located at the server renderer.
 * @type {{render: Accessible.render, validateDiagram: Accessible.validateDiagram, appendDiagram: Accessible.appendDiagram, printDiagram: Accessible.printDiagram, isGroup: Accessible.isGroup, isReference: Accessible.isReference, printGroup: Accessible.printGroup, printValue: Accessible.printValue, printReference: Accessible.printReference, getModifiers: Accessible.getModifiers, appendClusters: Accessible.appendClusters, initDawnWeb: Accessible.initDawnWeb, registerFeatureIds: Accessible.registerFeatureIds}}
 */
var Accessible = {
    render: function () {

        var project = $.urlParam('project');
        var model = $.urlParam('model');

        Accessible.validateDiagram(project, model);

        // Printing diagram (#SyntaxHierarchy)
        Accessible.appendDiagram(project, model, "", $("#SyntaxHierarchy"));

        // Printing clusters (#ClusterHierarchies)
        Accessible.appendClusters(project, model, suffix, $("#ClusterHierarchies"));

        Accessible.registerFeatureIds(project, model);

        Accessible.initDawnWeb(project, model);

    },
    /**
     * Checks if the input project and model a correct an do exist.
     * @param projectId the project name
     * @param modelId the model name
     */
    validateDiagram: function (projectId, modelId) {

        // Check if diagram exists first
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.validateDiagram({projectId: projectId, modelId: modelId});
        }, function (err) {
            changeStatus("Unable to connect to the server.");
        })
            .then(function (result) {

                // Warn if no diagram was found
                if (result.data == "false") {
                    changeStatus('Model "' + modelId + '" not found in project "' + projectId + '".');
                }

            });
    },
    /**
     * Appends a diagram to the given DOM element.
     * @param projectId the project name
     * @param modelId the model name
     * @param suffix the suffix for the diaram elements
     * @param domElement the base DOM element
     */
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
    /**
     * Returns a new diagram treeview element with the diagram content.
     * @param diagram the diagram json object
     * @param suffix the suffix of the new diagram
     * @returns {*|jQuery|HTMLElement}
     */
    printDiagram: function (diagram, suffix) {

        var content = $('<div role="application">');

        var title = $('<h2 id="Diagram' + diagram.id + 'Title' + suffix + '" data-coord-x="' + diagram.x +
            '" data-coord-y="' + diagram.y + '" tabindex="-1">' + DawnWeb.define(diagram.value) + '</h2>');

        var elementList = $('<ul id="Elem' + diagram.id +
            'Tree' + suffix + '" class="tree root-level" role="tree" aria-labelledby="Diagram' + diagram.id +
            'Title' + suffix + '">');

        $.each(diagram.children, function (i, child) {

            if (Accessible.isGroup(child)) {
                $(elementList).append(Accessible.printGroup(child, suffix, true));
            } else {
                $(elementList).append(Accessible.printValue(child, suffix, true));
            }
        });

        // Create modified treeView with the given dom element
        new treeview(elementList);

        $(content).append(title);
        $(content).append(elementList);

        return content;
    },
    /**
     * Returns true if the element is a group (more than zero children).
     * @param elem the element to test
     * @returns {boolean}
     */
    isGroup: function (elem) {
        return elem.children.length > 0;
    },
    /**
     * Returns true if the element is a reference.
     * @param elem
     * @returns {boolean}
     */
    isReference: function (elem) {
        return elem.referencedObject != null;
    },
    /**
     * Returns a new diagram group element with the given information.
     * @param elem the base element
     * @param suffix the group suffix
     * @param isFirst true, if this is the first group in the given context
     * @returns {*|jQuery|HTMLElement}
     */
    printGroup: function (elem, suffix, isFirst) {

        var tabIndex = -1;
        if (isFirst) {
            tabIndex = 0;
        }

        var content = $('<li id="Elem' + elem.id + suffix + '" class="tree-parent ' + Accessible.getModifiers(elem) +
            '" role="treeitem" aria-expanded="true" tabindex="' + tabIndex + '" data-cdo-id="' + elem.id +
            '">' + DawnWeb.define(elem.value) + '</li>');

        var childList = $('<ul id="Elem' + elem.id + 'Tree' + suffix + '" role="group" tabindex="-1">');

        $.each(elem.children, function (i, child) {

            if (Accessible.isGroup(child) || Accessible.isReference(child)) {
                $(childList).append(Accessible.printGroup(child, suffix, false));
            } else {
                $(childList).append(Accessible.printValue(child, suffix, false));
            }
        });

        if (Accessible.isReference(elem)) {
            $(childList).append(Accessible.printReference(elem.id, elem.referencedObject, suffix));
        }

        content.append(childList);
        return content;
    },
    /**
     * Returns a value element, created from the given information.
     * @param elem the base element
     * @param suffix the value suffix
     * @param isFirst true, if this is the first element in the given context
     * @returns {*|jQuery|HTMLElement}
     */
    printValue: function (elem, suffix, isFirst) {

        var tabIndex = -1;
        if (isFirst) {
            tabIndex = 0;
        }

        var modifiers = Accessible.getModifiers(elem);

        return $('<li id="Elem' + elem.id + suffix + '" role="treeitem" tabindex="' + tabIndex + '" class="' + modifiers +
            '" data-cdo-id="' + elem.id + '">' + DawnWeb.define(elem.value) + '</li>');


    },
    /**
     * Returns a new reference element, created from the given information.
     * @param parentId the parentId of the connection
     * @param referencedValue the referenced value
     * @param suffix the connection suffix value
     * @returns {*|jQuery|HTMLElement}
     */
    printReference: function (parentId, referencedValue, suffix) {

        return $('<li id="Elem' + parentId + 'Link' + suffix + '" class="reference" data-referenced-element-id="Elem' +
            referencedValue.id + suffix + '" role="treeitem" tabindex="-1">' +
            DawnWeb.define(referencedValue.value) + ' (Reference) </li>');

    },
    /**
     * Returns the modifier information converted as string.
     * @param elem the element to test
     * @returns {string}
     */
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
    /**
     * Appends the cluster view from the connected server to the given DOM element.
     * @param projectId the project name
     * @param modelId the model name
     * @param suffix the cluster element suffix
     * @param domElement the base DOM element
     */
    appendClusters: function (projectId, modelId, suffix, domElement) {

        // Get cluster collection from server
        DawnWeb.getClient().then(function (server) {
            return server.apis.accessible.getClusters({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                // JSON Object
                var clusters = $.parseJSON(result.text);

                $.each(clusters, function (i, cluster) {

                    domElement.append(Accessible.printDiagram(cluster, suffix));

                });

            });

    },
    /**
     * Initializes the DawnWeb connection with the last state of the server-side diagram.
     * @param projectId the project name
     * @param modelId the model name
     */
    initDawnWeb: function (projectId, modelId) {

        // Get last changed date
        DawnWeb.getClient().then(function (server) {
            return server.apis.diagram.getLastChanged({projectId: projectId, modelId: modelId});
        })
            .then(function (result) {

                var lastChanged = result.text;

                DawnWebUtil.init(projectId, modelId, lastChanged);

            });

    },
    /**
     * Registers the feature ids for correct manipulation of diagram element properties.
     * @param projectId the project name
     * @param modelId the model name
     */
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