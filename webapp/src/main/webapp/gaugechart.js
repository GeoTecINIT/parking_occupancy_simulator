var increase = 50;

function createGaugeChart(gauge_div, guageplusid, guageminusid, postFunction) {
	var charDetails = {};
	
	var defValue = parseInt($("#" + gauge_div).html());
	
	charDetails.data = google.visualization.arrayToDataTable([
		['Label', 'Value'],
		['Delay', defValue]
	]);

	charDetails.options = {
		width: 400, height: 120,
		animation: {
			duration: 200,
			easing: 'inAndOut'
		},
		minorTicks: 10,
		majorTicks: ['0', '500', '1000'],
		max:1000,
		min:0
	};

	charDetails.chart = new google.visualization.Gauge(document.getElementById(gauge_div));
	
	$('#' + guageplusid).click(function(e){
		var prev = charDetails.data.getValue(0, 1);
		if (prev < 1000){
			charDetails.data.setValue(0, 1, prev + increase);
		}
		charDetails.chart.draw(charDetails.data, charDetails.options);
		postFunction();
	});
	
	$('#' + guageminusid).click(function(e){
		var prev = charDetails.data.getValue(0, 1);
		if (prev > 0){
			charDetails.data.setValue(0, 1, prev - increase);
		}
		charDetails.chart.draw(charDetails.data, charDetails.options);
		postFunction();
	});
	
	return charDetails;
}