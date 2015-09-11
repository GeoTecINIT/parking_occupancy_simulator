function createCO2Chart(div_id, color1, color2) {
	var charDetails = {};
	
	charDetails.data = new google.visualization.arrayToDataTable([
		['State', 'Guided', 'Explorer'],
		["Last Step", 0, 0],
		["Accumulated", 0, 0]
	]);
	
	charDetails.options = {
		'title':'CO2 emissions per agent type',
		'width':350,
		'height':180,
		'chartArea': {'width': '50%', 'height': '70%'},
		legend: {position: 'right', alignment :'left', textStyle: {color: 'black', fontSize: 12}},
		series: {
			0: { color: color1 },
			1: { color: color2 }
		},
		isStacked: 'percent',
		is3D: true,
		animation:{
			duration: 200,
			easing: 'out',
		}
	};
	var el = document.getElementById('chart_div');
	charDetails.chart = new google.visualization.ColumnChart(el);
	
	return charDetails;
}