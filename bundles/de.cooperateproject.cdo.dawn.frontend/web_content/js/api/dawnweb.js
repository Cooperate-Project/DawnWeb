var DawnWeb = {
		swaggerServer : object = null,
		
		init: function() {
			this.getClient();
		},
		
		getClient: function() {
			if (this.swaggerServer == null) {
				console.log("Initializing server connection.");
				
				// Create new connection
				this.swaggerServer = new SwaggerClient(Consts.serverBaseURL + Consts.serverSwaggerName)
				
				this.swaggerServer.then(function(server) {
					console.log("Connected.");
				});
			}
				return this.swaggerServer;
			}
}