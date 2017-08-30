var DawnWeb = {
    swaggerServer: object = null,

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

        $.reduce = function (obj1, obj2) {
            for (var k in obj2) {
                if (obj1.hasOwnProperty(k) && obj2.hasOwnProperty(k)) {
                    if (typeof obj1[k] == "object" && typeof obj2[k == "object"]) {
                        $.reduce(obj1[k], obj2[k]);
                    } else {
                        delete obj1[k];
                    }
                }
            }
        };

        this.getClient();
    },

    getClient: function () {
        if (this.swaggerServer == null) {
            console.log("Initializing server connection.");

            // Create new connection
            this.swaggerServer = new SwaggerClient(Consts.serverBaseURL + Consts.serverSwaggerName);

            this.swaggerServer.then(function (server) {
                console.log("Connected.");
            });
        }
        return this.swaggerServer;
    },
    define: function (value) {
        if (value == null) {
            return "";
        } else {
            return value;
        }
    }
}