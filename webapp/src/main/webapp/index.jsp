<%
	simulation.data.configs.common.PropertiesLoader pl = simulation.data.configs.common.PropertiesLoader.getInstance();
	int moment = (int)(simulation.consumers.web.MasonModelWebSController.getInstance().getStepTime());
	int guidedprop = (int)(simulation.consumers.web.MasonModelWebSController.getInstance().getGuidedAgentProportion() * 100);
	int explorerprop = 100 - guidedprop;
%>

<!DOCTYPE html>
<html>
<head>
	<title>Create a Map</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
	<!-- ================================================================= -->
	<meta http-equiv="cache-control" content="max-age=0" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
	<meta http-equiv="pragma" content="no-cache" />
    <!-- ================================================================= -->
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- ================================================================= -->
	<link rel="stylesheet" href="http://js.arcgis.com/3.11/dijit/themes/claro/claro.css">
	<link rel="stylesheet" href="http://js.arcgis.com/3.11/esri/css/esri.css">
	<!-- ================================================================= -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<!-- ================================================================= -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
	<link href='https://fonts.googleapis.com/css?family=Orbitron' rel='stylesheet' type='text/css'>
	<!-- ================================================================= -->
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<!-- ================================================================= -->
	<script type="text/javascript" src="common.js"></script>
	<link rel="stylesheet" href="clock.css">
	<link rel="stylesheet" href="collapsable.css">
	<script type="text/javascript" src="collapsable.js"></script>
	<link rel="stylesheet" href="coloredrangeinput.css">
	<script type="text/javascript" src="coloredrangeinput.js"></script>
	<script type="text/javascript" src="co2chart.js"></script>
	<script type="text/javascript" src="gaugechart.js"></script>
	<!-- ================================================================= -->
	<style>
		/* ---------------------- */
		html, body, #mapDiv{
		  padding: 0;
		  margin: 0;
		  height: 100%;
		  width: 100%;
		  font: 10px sans-serif;
		}
		/* ---------------------- */
		/* Start by setting display:none to make this hidden.
		   Then we position it in relation to the viewport window
		   with position:fixed. Width, height, top and left speak
		   speak for themselves. Background we set to 80% white with
		   our animation centered, and no-repeating */
		.modal {
		    display:    none;
		    position:   fixed;
		    z-index:    1000;
		    top:        0;
		    left:       0;
		    height:     100%;
		    width:      100%;
		    background: rgba( 255, 255, 255, .8 ) 
		                url('http://i.stack.imgur.com/FhHRx.gif') 
		                50% 50% 
		                no-repeat;
		}
		
		/* When the body has the loading class, we turn
		   the scrollbar off with overflow:hidden */
		body.loading {
		    overflow: hidden;   
		}
		
		/* Anytime the body has the loading class, our
		   modal element will be visible */
		body.loading.modal {
		    display: block;
		}
		/* ---------------------- */
		.disabledwhilerunning{
		}
		.disabledwhilenotrunning{
		}
		/* ---------------------- */
		.righdivision{
			border-right: 1px solid gray;
		}
		.bottomdivision{
			text-align:center;
			display:inline-block;
			float:none;
			border-bottom: 1px solid gray;
			padding-bottom: 7px;
			padding-top: 7px;
		}

		/* Make equal height with table styles */
		@media (min-width: 768px) {
		  .table-row {
			display: table;
			table-layout: fixed;
		  }
		  .table-row [class^="col-"] {
			display: table-cell;
			float: none;
		  }
		}

		#agentproportion{
			width:200px;
		}
		.toright{
			text-align: right;
			float: right !important;
			padding-right:7px;
		}
		.toleft{
			text-align: left;
			float: left !important;
		}
		.tor{
		}
		.tol{
		}
		.vcenter{
			display: inline-block;
			vertical-align: middle;
			horizontal-align: middle;
			float: none;
		}
		.text-center {
			text-align: center !important;
		}
		.oupt{
			float: none;
			margin: 0 auto;
			padding-right: 17px;
			padding-bottom: 7px;
		}
		table td {
			border-top: none !important;
		}
	</style>
	<!-- ================================================================= -->
	<script>  
		var dojoConfig = {  
			parseOnLoad: true  
		};  
	</script>
	<script src="http://js.arcgis.com/3.11/"></script>
	<script>
	
	// PATHS
	// --------------------------------------------------------------

	var baseAppUrl = "${pageContext.request.contextPath}";
	var baseUrl = "${pageContext.request.contextPath}/services/controller/";


  
  	google.load('visualization', '1.0', {'packages':['corechart', 'gauge']});
	google.setOnLoadCallback(initAll);
	
	function initAll(){
		dojo.ready(initDojo);
		jQuery(function ($) {
			createCollapsable();
			makeColorRange("agentproportion", "#6b486b", "#ab88ab", "#ffcc40", "#ff8c00", Post);
			initMainControls();
		});
		createGoogleCharts();
	}
	
	var CO2chartDetails;
	var gaugeChartDetails;
	
	function createGoogleCharts(){
		CO2chartDetails = createCO2Chart('chart_div', '#6b486b', '#ff8c00');
		drawChart(CO2chartDetails);
		gaugeChartDetails = createGaugeChart('gauge_div', 'guageplusid', 'guageminusid', Post);
		drawChart(gaugeChartDetails);
	}
  
	dojo.require("esri.map");
	dojo.require("esri.tasks.query");
	
	var serverTime = 0;
	var hashMap = {};
	var count = 0;
	var prevState = "0";		// NotStarted
	var timer;
	var stateTimer;
	var co2timer;
	var map;
	var layer;
	var objectIds;
	var UPDATE_TIME = 2000;
	var STATE_UPDATE_TIME = 3000;
	var CO2_UPDATE_TIME = 3000;
	var SAFE_AMOUNT = 1000;
	var FEATURES_URL = "http://smartcampus.sg.uji.es:6080/arcgis/rest/services/SmartWays/SmartWaysParking/FeatureServer/0";
	var SIMULATION_UPDATES_URL = "${pageContext.request.contextPath}/services/controller/getslotsupdate";
	var CO2_UPDATES_URL = "${pageContext.request.contextPath}/services/controller/getco2";
	
	
	function initDojo() {
		map = new esri.Map("mapDiv", {
		  basemap: "topo",
		  center: [-0.0700, 39.9945],
		  zoom: 17
		});
		map.disableClientCaching = true;
		map.on("load", mapLoadHandler);
	}
	
	function mapLoadHandler(){
		layer = new esri.layers.FeatureLayer(FEATURES_URL, {
			mode: esri.layers.FeatureLayer.MODE_SELECTION
		});
        map.addLayer(layer);
		var query = new esri.tasks.Query();
		query.where = "1=1";
		layer.queryIds(query, function(ids) {
			objectIds = ids;
			doRecursiveLoad(0, SAFE_AMOUNT);
		});
	}
	
	function doRecursiveLoad(start, safeAmount){
		var end = (start + safeAmount < objectIds.length)?start + safeAmount:objectIds.length;
		var idsToRequest = objectIds.slice(start, end);
		var query = new esri.tasks.Query();
		query.objectIds = idsToRequest;
		query.outFields = [ "*" ];
		layer.queryFeatures(query, function(featureSet) {
			$.each(featureSet.features, function(index, value){
				hashMap[value.attributes.OBJECTID] = value;
				layer.add(value);
			});
			if (end < objectIds.length) {
				doRecursiveLoad(end, safeAmount);
			}
			else{
				updateEnd();
			}
		});
	}
	
	function updateEnd(){
		timer = window.setInterval(update, UPDATE_TIME);
		co2timer = window.setInterval(updateCO2, CO2_UPDATE_TIME);
	}
	
	function update(){
		window.clearInterval(timer);
		var updateUrl = SIMULATION_UPDATES_URL + "?time=" + serverTime;
		$("#debugtime").text(serverTime);
		doAjax(updateUrl, 
			function(data){
				if (data.time != 0){
					serverTime = data.time;
				}
				if (data.slots != null){
					if (data.slots.length != null){
						$.each(data.slots, function(index, value){
							var feature = hashMap[value.id];
							feature.getLayer().remove(feature);
							feature.attributes.ESTADO = value.state;
							feature.getLayer().add(feature);
						});
					}
					else{
						var feature = hashMap[data.slots.id];
						feature.getLayer().remove(feature);
						feature.attributes.ESTADO = data.slots.state;
						feature.getLayer().add(feature);
					}
				}
				timer = window.setInterval(update, UPDATE_TIME);
			}, 
			showErrorMsg,
			function(){ }
		);
	}
	
	function updateCO2(){
		window.clearInterval(co2timer);
		var co2UpdateUrl = CO2_UPDATES_URL;
		doAjax(co2UpdateUrl, 
			function(data){
				CO2chartDetails.data.setValue(0, 1, data.guidedCO2);
				CO2chartDetails.data.setValue(0, 2, data.explorerCO2);
				CO2chartDetails.data.setValue(1, 1, data.accumulatedGuidedCO2);
				CO2chartDetails.data.setValue(1, 2, data.accumulatedExplorerCO2);
				drawChart(CO2chartDetails);
				co2timer = window.setInterval(updateCO2, CO2_UPDATE_TIME);
			}, 
			showErrorMsg,
			function(){ }
		);
	}
	
	function showErrorMsg(xhr, ajaxOptions, thrownError){
		$('.modal').hide();
		/*$("#stateMessage").html("Error: " + xhr.status + "\n" +
			   "Message: " + xhr.statusText + "\n" +
				"Response: " + xhr.responseText + "\n" + thrownError);*/
		$("#stateMessage").html("Conection problems...");
	}
	
	// ----------------------------------------------------------------
	
	function initMainControls(){
		$("#buttonRun").click(Run);
		$("#buttonStop").click(Stop);
		$("#comparisonlink").addClass("disabled");
		$(".disabledwhilerunning").attr("disabled", "disabled");
		$(".disabledwhilenotrunning").attr("disabled", "disabled");
		var resource = "state";
		var url = baseUrl + resource;
		doAjax(url, 
				function(data){
					adjustToState(data);
					$('.modal').hide();
					stateTimer = window.setInterval(stateUpdate, STATE_UPDATE_TIME);
				},
				function (xhr, ajaxOptions, thrownError) {
					showErrorMsg(xhr, ajaxOptions, thrownError);
					$(".modal").hide();
					$(".disabledwhilerunning").removeAttr("disabled", "");
				},
				function(){ $('.modal').show(); }
		);
	}
	
	function stateUpdate(){
		var resource = "state";
		var url = baseUrl + resource;
		doAjax(url, 
				function(data){
					adjustToState(data);
					$("#timeMessage").html(data.time.substring(3));
				},
				function (xhr, ajaxOptions, thrownError) {
					showErrorMsg(xhr, ajaxOptions, thrownError);
					$(".disabledwhilerunning").removeAttr("disabled", "");
				},
				function(){  }
		);
	}

	function adjustToState(data){
		var msg;
		if (prevState != data.state){
			prevState = data.state;
			switch(data.state) {
			    case 1:		// NotStared
			    	msg = "Not Started";
			    	adjustToNotRunning();
			    	break;
			    case 2:		// Running
			    	msg = "Running";
			    	adjustToRunning();
			        break;
			    case 3:		// Canceled
			    	msg = "Canceled";
			    	adjustToNotRunning();
					break;
			    case 4: 	// Finished
					msg = "Finished";
					adjustToNotRunning();
					break;
				default:	// Error
					msg = "Error";
					adjustToNotRunning();
					break;
			}
			$("#stateMessage").html(msg);	
		}
	}

	function adjustToRunning(){
		$("#comparisonlink").removeClass("disabled");
		$(".disabledwhilerunning").attr("disabled", "disabled");
		$(".disabledwhilenotrunning").removeAttr("disabled", "");
	}

	function adjustToNotRunning(){
		$(".disabledwhilenotrunning").attr("disabled", "disabled");
		$(".disabledwhilerunning").removeAttr("disabled", "");
	}
	
	function Post(){
		if (gaugeChartDetails != null){
			var speedVal = gaugeChartDetails.data.getValue(0, 1);
			var guidedVal = parseFloat($("#agentproportion").val())/100;
			var params = "guidedAgentProportion=" + guidedVal + "&moment=" + speedVal;
			var resource = "post";
			var url = baseUrl + resource + "?" + params;
			doAjax(url, function(data){
						var msg = (data.result == 1)?"Posted":"ERROR";
						$("#stateMessage").html(msg);
						$('.modal').hide();
						$("input[type='range']").each(function() {
							var value = $( this ).val();
							$( this ).attr("data-defaultValue", value);
						});
					},
					showErrorMsg,
					function(){$('.modal').show();}
			);
		}
	}
	
	function Run(){
		$(".disabledwhilerunning").attr("disabled", "disabled");
		$(".disabledwhilenotrunning").removeAttr("disabled", "");
		var resource = "run";
		var url = baseUrl + resource;
			doAjax(url, 
				function(data){
					var msg;
					if (data.result == "true"){
						msg = "Running";
						adjustToRunning();
					}
					else{
						msg = "Error";
						adjustToNotRunning();
					}
					$("#stateMessage").html(msg);
					$('.modal').hide();
				},
				function (xhr, ajaxOptions, thrownError) {
					showErrorMsg(xhr, ajaxOptions, thrownError);
					$(".modal").hide();
					adjustToNotRunning();
				},
				function(){ $('.modal').show(); }
		);
	}
	
	function Stop(){
		$(".disabledwhilerunning").removeAttr("disabled", "");
		$(".disabledwhilenotrunning").attr("disabled", "disabled");
		$("#comparisonlink").addClass("disabled");
		var resource = "stop";
		var url = baseUrl + resource;
			doAjax(url, 
				function(data){
					var msg = (data.result == "true") ? "Canceled" : "Error";
					$("#stateMessage").html(msg);
					$('.modal').hide();
				},
				function (xhr, ajaxOptions, thrownError) {
					showErrorMsg(xhr, ajaxOptions, thrownError);
					$(".modal").hide();
					$(".disabledwhilenotrunning").removeAttr("disabled", "");
				},
				function(){	$('.modal').show();	}
		);
	}

  </script>

</head>
<body class="claro">
	<div id="mapDiv"></div>
	<div id="toolbar" class="panel panel-default fixed">
	<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
		<div id="collcontent" class="collapse">
			<div class="container-fluid">
				<div class="row table-row">
					<div class="col-md-8 vcenter righdivision">
						<!-- --------------- AGENT PROPORTION RANGE INPUT CONTROL ---------------  -->
						<div class="row bottomdivision center-block">
							<table class="table">
								<tr><td>Guided</td><td></td><td>Explorer</td></tr>
								<tr>
									<td><output id="leftValue" class="oupt"><%= guidedprop %>%</output></td>
									<td><input id="agentproportion" type="range" min="0" max="100" step="1" data-defaultValue="<%= guidedprop %>" value="<%= guidedprop %>" class="range oupt" style="padding-top:10px;" onchange="leftValue.value=value+'%';rightValue.value=(100-value)+'%'" required autofocus></td>
									<td><output id="rightValue" class="oupt"><%= explorerprop %>%</output></td>
								</tr>
							</table>
						</div>
						<!-- --------------- DELAY GUAGE INPUT CONTROL ---------------  -->
						<div class="row bottomdivision center-block">
							<div class="col-md-1 vcenter">
								<div id="gauge_div"><%= moment %></div>
							</div>
							<div class="col-md-1 vcenter">
								<div class="row">
									<button id="guageplusid" type="button" class="btn btn-default btn-number">
										<span class="glyphicon glyphicon-plus"></span>
									</button>
								</div>
								<div class="row">
									<button id="guageminusid" type="button" class="btn btn-default btn-number">
										<span class="glyphicon glyphicon-minus"></span>
									</button>
								</div>
							</div>
							<div class="col-md-12"></div>
						</div>
						<div class="row bottomdivision center-block">
							<table class="table">
								<tr>
									<td><button id="buttonRun" type="button" class="btn btn-md btn-success disabledwhilerunning">Run</button></td>
									<td><button id="buttonStop" type="button" class="btn btn-md btn-danger disabledwhilenotrunning">Stop</button></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="col-md-4 vcenter righdivision">
						<!-- --------------- CO2 EMISSIONS CHART VISUALIZATION CONTROL ---------------  -->
						<div id="chart_div"></div>
					</div>
					<div class="col-md-1 righdivision">
						<h4><div id="stateMessage"></div></h4>
						<div id="clock" class="light">
							<div id="timeMessage" class="display">
								00:00:00
							</div>
						</div>
						<a id="comparisonlink" class="disabledwhileinit disabledwhilenotrunning" href="${pageContext.request.contextPath}/comparison.jsp" target="_blank">Open Comparison Window</a>
					</div>
					<div class="col-md-1 "></div>
				 </div>
			</div>
		</div>
		</div>
		<!-- --------------- PIN CONTROL FOR FOLDING ---------------  -->
		<div class="col-md-12">
			<span class="pull-right clickable">
				<i id="collpin" class="glyphicon glyphicon-chevron-down" data-toggle="collapse" href="#collcontent" aria-expanded="false" aria-controls="collcontent"></i>
			</span>
		</div>
	</div>
	</div>
	</div>
</body>
</html>
