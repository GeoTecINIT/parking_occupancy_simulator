<!DOCTYPE html>
<html>
<head>
  <title>Create a Map</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <!-- ================================================================= -->
  <meta http-equiv="cache-control" content="max-age=0" />
  <meta http-equiv="cache-control" content="no-cache" />
  <meta http-equiv="expires" content="0" />
  <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
  <meta http-equiv="pragma" content="no-cache" />
  <!-- ================================================================= -->
  <meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
  <link rel="stylesheet" href="http://js.arcgis.com/3.11/dijit/themes/claro/claro.css">
  <link rel="stylesheet" href="http://js.arcgis.com/3.11/esri/css/esri.css">
  <script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
  <style>
    html, body, #mapDiv{
      padding: 0;
      margin: 0;
      height: 100%;
    }
	.fixed{
			position:fixed;
			top:0px;
			right:0px;
			width:250px;
			z-index:1000;
			background-color:#FFF;
			border:1px solid #000;
			text-align:center;
		}
	.fixed2{
			position:fixed;
			top:0px;
			right:250px;
			width:500px;
			z-index:1000;
			background-color:#FFF;
			border:1px solid #000;
			text-align:center;
		}
  </style>
  <!-- ================================================================= -->
  <script src="common.js"></script>
  <!-- ================================================================= -->
  <script>  
      var dojoConfig = {  
        parseOnLoad: true  
      };  
  </script>
  <script src="http://js.arcgis.com/3.11/"></script>
  <script>
	dojo.require("esri.map");
	dojo.require("esri.tasks.query");
	
	var serverTime = 0;
	var hashMap = {};
	var count = 0;
	var timer;
	var co2timer;
	var map;
	var layer;
	var objectIds;
	var UPDATE_TIME = 2000;
	var CO2_UPDATE_TIME = 1000;
	var SAFE_AMOUNT = 1000;
	var FEATURES_URL = "http://smartcampus.sg.uji.es:6080/arcgis/rest/services/SmartWays/SmartWaysParking/FeatureServer/0";
	var SIMULATION_UPDATES_URL = "${pageContext.request.contextPath}/services/controller/getslotsupdate";
	var CO2_UPDATES_URL = "${pageContext.request.contextPath}/services/controller/getco2";
	
	dojo.ready(init);
	
	function init() {
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
			null,
			function(){ }
		);
	}
	
	function updateCO2(){
		window.clearInterval(co2timer);
		var co2UpdateUrl = CO2_UPDATES_URL;
		doAjax(co2UpdateUrl, 
			function(data){
				var text = "guidedCO2: " + data.guidedCO2 + " explorerCO2: " + data.explorerCO2 + " accumulatedGuidedCO2: " + data.accumulatedGuidedCO2 + " accumulatedExplorerCO2: " + data.accumulatedExplorerCO2;
				$("#co2values").text(text);
				co2timer = window.setInterval(updateCO2, CO2_UPDATE_TIME);
			}, 
			null,
			function(){ }
		);
	}
	
  </script>

</head>
<body class="claro">
  <div id="mapDiv"></div>
  <div id="debugtime" class="fixed"><strong>debug time</strong></div>
  <div id="co2values" class="fixed2"><strong>CO2</strong></div>
</body>
</html>
