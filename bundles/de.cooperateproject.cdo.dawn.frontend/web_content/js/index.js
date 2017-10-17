/**
 * Controller of the index view with general information of the server connection.
 * @type {{init: Index.init}}
 */
var Index = {
    init: function () {

        // Connect to the server
        DawnWeb.getClient().then(function (server) {
            return server.apis.util.getCurrentServerTimestamp();
        })
            .then(function (result) {
                $("#clienttime").html(Date.now());
                $("#servertime").html(result.obj);
                $("#time").show(400);
                $("#serverconnect").html("TRUE").attr("class", "boldgreen");
            });
    }
}