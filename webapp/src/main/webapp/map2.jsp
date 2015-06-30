<!DOCTYPE html>
<html>
<head>
<title>Create a Map</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=1,user-scalable=no">
<link rel="stylesheet" href="http://js.arcgis.com/3.11/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="http://js.arcgis.com/3.11/esri/css/esri.css">
<script src="http://code.jquery.com/jquery-1.11.0.min.js"></script>
<!-- ================================================================= -->
<script src="common.js"></script>
<!-- ================================================================= -->
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
  </style>
  <script>  
      var dojoConfig = {  
        parseOnLoad: true  
      };  
  </script>
  <script src="http://js.arcgis.com/3.11/"></script>
  <script>	
	dojo.require("esri.map");
	dojo.require("esri.graphic");
	dojo.require("esri.symbols.SimpleMarkerSymbol");
	dojo.require("esri.symbols.SimpleLineSymbol");
	
	var map;
	var timer;
	var UPDATE_TIME = 3000; 	// 1.5 sec
	var lastTime = 0;
	
	var pointGraphicSize = 5;
	var registry = [];
	var stateSymbols = [];
	var stateOccupied = 1, stateAvailable = 2, stateRequested = 3;
	var getslotsUrl = "${pageContext.request.contextPath}/services/controller/getslots";
	var getslotsupdateUrl = "${pageContext.request.contextPath}/services/controller/getslotsupdate";
	var wkidValue = 102100;
		
	function init() {
		map = new esri.Map("mapDiv", {
		  basemap: "topo",
		  center: [-0.0700, 39.9945],
		  zoom: 17
		});
		map.on("load", mapLoadHandler);
	}
	
	function mapLoadHandler(){
		doAjax(getslotsUrl, 
			function(data){
				lastTime = 0;
				$.each(data.slots, function(index, value){
					addDataPoint(value);
				});	
				timer = window.setInterval(update, UPDATE_TIME);
			}, 
			null,
			function(){ }
		);
		
		stateSymbols[stateOccupied] = new esri.symbol.SimpleMarkerSymbol();
		stateSymbols[stateOccupied].setSize(pointGraphicSize);
		stateSymbols[stateOccupied].setColor(new dojo.Color([255, 0, 0, 1.0]));
		
		stateSymbols[stateAvailable] = new esri.symbol.SimpleMarkerSymbol();
		stateSymbols[stateAvailable].setSize(pointGraphicSize);
		stateSymbols[stateAvailable].setColor(new dojo.Color([0, 255, 0, 1.0]));
		
		stateSymbols[stateRequested] = new esri.symbol.SimpleMarkerSymbol();
		stateSymbols[stateRequested].setSize(pointGraphicSize);
		stateSymbols[stateRequested].setColor(new dojo.Color([200, 180, 0, 1.0]));
	}
	
	function update(){
		var url = getslotsupdateUrl + "?time=" + lastTime;
		//alert(url);
		doAjax(url, 
			function(data){
				lastTime = data.time;
				$("#debugtime").text(data.time);
				$.each(data.slots, function(index, value){
					updateDataPoint(value.id, value.state);
				});
			}, 
			null,
			function(){ }
		);
	}
	
	function addDataPoint(dataPoint){
		var graphic = addGraphic(dataPoint.lon, dataPoint.lat, /*{id: dataPoint.id}*/null, stateSymbols[stateAvailable]);
		map.graphics.add(graphic);
		registry[dataPoint.id] = {graphic:graphic, dataPoint:dataPoint};
	}
	
	function addGraphic(lon, lat, attr, symbol){
		var point = new esri.geometry.Point(lon, lat, new esri.SpatialReference({wkid:wkidValue}));
		var graphic = new esri.Graphic(point, symbol, attr);
		return graphic;
	}
	
	function updateDataPoint(id, state){
		registry[id].graphic = replaceGraphic(registry[id].graphic, registry[id].dataPoint, stateSymbols[state]);
	}
	
	function replaceGraphic(graphic, dataPoint, symbol){
		map.graphics.remove(graphic);
		graphic = addGraphic(dataPoint.lon, dataPoint.lat, /*{id: dataPoint.id}*/null, symbol);
		map.graphics.add(graphic);
		return graphic;
	}
	
	dojo.ready(init);
  </script>

</head>
<body class="claro">
  <div id="mapDiv"></div>
  <div id="debugtime" class="fixed"><strong>debug time</strong></div>
</body>
</html>
