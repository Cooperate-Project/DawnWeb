function getServerTime() {
	$.get(SERVER_BASE_ADRESS + 'util/timestamp', function(data, status){
		$('#servertime').html('Server timestamp: ' + data);
    });
}