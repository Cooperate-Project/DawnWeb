/**
 * Copyright (c) 2004 - 2010 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Fluegge - initial API and implementation
 */

timer = 500;
interval = window.setInterval(function () {
    getDiagramData();
}, timer);

var DawnWebUtil = {};

DawnWebUtil.init = function (projectId, modelId, lastChanged) {
    this.projectId = projectId;
    this.modelId = modelId;
    this.resourceLastChanged = lastChanged;
};

function getDiagramData() {
    if (DawnWebUtil.projectId != undefined && DawnWebUtil.modelId != undefined) {

        getVersionFromProject().then(function (result) {

            if (DawnWebUtil.resourceLastChanged != result.text) {
                DawnWebUtil.resourceLastChanged = result.text;

                changeStatus('The resource has changed by another user. Please reload the window to apply the changes.');

            }

        });

    }
}

function getVersionFromProject() {

    return DawnWeb.getClient().then(function (server) {
        return server.apis.diagram.getLastChanged({projectId: DawnWebUtil.projectId, modelId: DawnWebUtil.modelId});
    });

}