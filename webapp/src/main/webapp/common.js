function doAjax(url, funcionexito, funcionerror, funcionbeforeSend){
	var errorFunc;
	if (funcionerror != null) { errorFunc = funcionerror; }
	else {
		errorFunc = function (xhr, ajaxOptions, thrownError) {
			$('.modal').hide();
			alert("Error: " + xhr.status + "\n" +
				   "Message: " + xhr.statusText + "\n" +
				   	"Response: " + xhr.responseText + "\n" + thrownError);
		};
	}
	$.ajax({
		timeout: 30000,	// milliseconds
		type: 'GET',
		url: url,
		contentType: "application/json",
		beforeSend: funcionbeforeSend,
		success: funcionexito,
		error: errorFunc
	});
}

function doAjaxP(url, funcionexito, funcionerror, funcionbeforeSend){
	var errorFunc;
	if (funcionerror != null) { errorFunc = funcionerror; }
	else {
		errorFunc = function (xhr, ajaxOptions, thrownError) {
			alert("Error: " + xhr.status + "\n" +
				   "Message: " + xhr.statusText + "\n" +
					"Response: " + xhr.responseText + "\n" + thrownError);
		};
	}
	$.ajax({
		timeout: 30000,	// milliseconds
		type: 'GET',
		jsonp: "smartparking",
		dataType: 'jsonp',
		jsonpCallback: 'smartparking',
		url: url,
		contentType: "application/jsonp",
		beforeSend: funcionbeforeSend,
		success: funcionexito,
		error: errorFunc
	});
}

function drawChart(charDetails){
	charDetails.chart.draw(charDetails.data, charDetails.options);
}

function pad(n) {
    return (n < 10) ? ("0" + n) : n;
}
