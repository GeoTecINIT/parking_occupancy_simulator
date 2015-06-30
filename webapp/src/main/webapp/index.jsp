<html lang="en">
<head>
<!-- ================================================================= -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="German Mendoza Silva">
<!-- ================================================================= -->
<meta http-equiv="cache-control" content="max-age=0" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />
<!-- ================================================================= -->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="http://getbootstrap.com/examples/dashboard/dashboard.css">
<!-- ================================================================= -->
<script src="common.js"></script>
<!-- ================================================================= -->
<style>
/* Space out content a bit */
body {
  padding-top: 20px;
  padding-bottom: 20px;
}

/* Everything but the jumbotron gets side spacing for mobile first views */
.header,
.marketing,
.footer {
  padding-right: 15px;
  padding-left: 15px;
}

/* Custom page header */
.header {
  border-bottom: 1px solid #e5e5e5;
}
/* Make the masthead heading the same height as the navigation */
.header h3 {
  padding-bottom: 19px;
  margin-top: 0;
  margin-bottom: 0;
  line-height: 40px;
}

/* Custom page footer */
.footer {
  padding-top: 19px;
  color: #777;
  border-top: 1px solid #e5e5e5;
}

/* Customize container */
@media (min-width: 768px) {
  .container {
    max-width: 730px;
  }
}
.container-narrow > hr {
  margin: 30px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
  text-align: center;
  border-bottom: 1px solid #e5e5e5;
}
.jumbotron .btn {
  padding: 14px 24px;
  font-size: 21px;
}

/* Supporting marketing content */
.marketing {
  margin: 40px 0;
}
.marketing p + h4 {
  margin-top: 28px;
}

/* Responsive: Portrait tablets and up */
@media screen and (min-width: 768px) {
  /* Remove the padding we set earlier */
  .header,
  .marketing,
  .footer {
    padding-right: 0;
    padding-left: 0;
  }
  /* Space out the masthead */
  .header {
    margin-bottom: 30px;
  }
  /* Remove the bottom border on the jumbotron for visual effect */
  .jumbotron {
    border-bottom: 0;
  }
}
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

.disabledwhilerunning{
}
.disabledwhilenotrunning{
}
a.disabled {
   pointer-events: none;
   cursor: default;
   color:Gray;
}
.mapimage{
	width:100%;
}
</style>

<script type="text/javascript" language="javascript">

// PATHS
// --------------------------------------------------------------

var baseAppUrl = "${pageContext.request.contextPath}";
var baseUrl = "${pageContext.request.contextPath}/services/controller/";


// STATE UPDATES
//--------------------------------------------------------------

var timer;
var UPDATE_TIME = 3000; 	// milliseconds
var prevState = "0";		// NotStarted

function update(){
	var resource = "state";
	var url = baseUrl + resource;
	doAjax(url, 
			function(data){
				adjustToState(data);
				$("#timeMessage").html(data.time);
			},
			function (xhr, ajaxOptions, thrownError) {
				var msg = "Error: " + xhr.status + "\n" +
					   "Message: " + xhr.statusText + "\n" +
					   "Response: " + xhr.responseText + "\n" + thrownError;
				$("#stateMessage").html(msg);
				$("#lead").html(image);
				$(".disabledwhilerunning").removeAttr("disabled", "");
			},
			function(){  }
	);
}

// INITIALIZATION
//--------------------------------------------------------------

$(function(){
	$("#buttonRun").click(Run);
	$("#buttonStop").click(Stop);
	$("#buttonPost").click(Post);
	$("#lead").html(image);
	$("#comparisonlink").addClass("disabled");
	$(".disabledwhilerunning").attr("disabled", "disabled");
	$(".disabledwhilenotrunning").attr("disabled", "disabled");
	var resource = "state";
	var url = baseUrl + resource;
	doAjax(url, 
			function(data){
				adjustToState(data);
				$('.modal').hide();
				timer = window.setInterval(update, UPDATE_TIME);
			},
			function (xhr, ajaxOptions, thrownError) {
				var msg = "Error: " + xhr.status + "\n" +
					   "Message: " + xhr.statusText + "\n" +
					   "Response: " + xhr.responseText + "\n" + thrownError;
				$("#stateMessage").html(msg);
				$(".modal").hide();
				$("#lead").html(image);
				$(".disabledwhilerunning").removeAttr("disabled", "");
			},
			function(){ $('.modal').show(); }
	);
});

function adjustToState(data){
	var msg;
	if (prevState != data.state){
		prevState = data.state;
		switch(data.state) {
		    case 1:		// NotStared
		    	msg = ": Not Started";
		    	adjustToNotRunning();
		    	break;
		    case 2:		// Running
		    	msg = ": Running";
		    	adjustToRunning();
		        break;
		    case 3:		// Canceled
		    	msg = ": Canceled";
		    	adjustToNotRunning();
				break;
		    case 4: 	// Finished
				msg = ": Finished";
				adjustToNotRunning();
				break;
			default:	// Error
				msg = ": Error";
				adjustToNotRunning();
				break;
		}
		$("#stateMessage").html(msg);	
	}
}

function adjustToRunning(){
	$("#comparisonlink").removeClass("disabled");
	$("#lead").html(getIframeHtml());
	$(".disabledwhilerunning").attr("disabled", "disabled");
	$(".disabledwhilenotrunning").removeAttr("disabled", "");
}

function adjustToNotRunning(){
	$("#lead").html(image);
	$(".disabledwhilenotrunning").attr("disabled", "disabled");
	$(".disabledwhilerunning").removeAttr("disabled", "");
}

// RUN
// --------------------------------------------------------------
var countDummy = 0;	// For avoiding caching
var image = '<img id="stateimg" src="' + baseAppUrl + '/availability.png" class="img-thumbnail" alt="Availability Image" style="height:150px;">';

function getIframeHtml(){
	return '<iframe src="' + baseAppUrl + '/map.jsp?dummy=' + (countDummy++) + '" class="mapimage" style="height:300px;"></iframe>';
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
					msg = ": Running";
					adjustToRunning();
				}
				else{
					msg = ": Error";
					adjustToNotRunning();
				}
				$("#stateMessage").html(msg);
				$('.modal').hide();
			},
			function (xhr, ajaxOptions, thrownError) {
				var msg = "Error: " + xhr.status + "\n" +
					   "Message: " + xhr.statusText + "\n" +
					   "Response: " + xhr.responseText + "\n" + thrownError;
				$("#stateMessage").html(msg);
				$(".modal").hide();
				adjustToNotRunning();
			},
			function(){ $('.modal').show(); }
	);
}



// IMAGE
// --------------------------------------------------------------

function Image(){
	var resource = "image";
	var url = baseUrl + resource;
	d = new Date();
	$("#stateimg").attr("src", url + "?"+d.getTime());
}

// STOP
// --------------------------------------------------------------

function Stop(){
	$(".disabledwhilerunning").removeAttr("disabled", "");
	$(".disabledwhilenotrunning").attr("disabled", "disabled");
	$("#comparisonlink").addClass("disabled");
	var resource = "stop";
	var url = baseUrl + resource;
		doAjax(url, 
			function(data){
				var msg = (data.result == "true") ? ": Canceled" : ": Error";
				$("#stateMessage").html(msg);
				$("#lead").html(image);
				$('.modal').hide();
			},
			function (xhr, ajaxOptions, thrownError) {
				var msg = "Error: " + xhr.status + "\n" +
					   "Message: " + xhr.statusText + "\n" +
					   "Response: " + xhr.responseText + "\n" + thrownError;
				$("#stateMessage").html(msg);
				$("#lead").html(image);
				$(".modal").hide();
				$(".disabledwhilenotrunning").removeAttr("disabled", "");
			},
			function(){	$('.modal').show();	}
	);
}

// POST
// --------------------------------------------------------------

function Post(){
	var params = $("form").serialize();
	var resource = "post";
	var url = baseUrl + resource + "?" + params;
	doAjax(url, function(data){
				var msg = (data.result == 1)?"Posted":"ERROR";
				$("#postingresult").html(msg);
				$('.modal').hide();
				$("input[type='range']").each(function() {
					var value = $( this ).val();
					$( this ).attr("data-defaultValue", value);
				});
			},
			function (xhr, ajaxOptions, thrownError) {
				var msg = "Error: " + xhr.status + "\n" +
					   "Message: " + xhr.statusText + "\n" +
					   "Response: " + xhr.responseText + "\n" + thrownError;
				$("#postingresult").html(msg);
				$(".modal").hide();
			},
			function(){$('.modal').show();}
	);
}

// --------------------------------------------------------------

</script>

</head>

<body role="document">
   <div class="container">
      <div class="header">
        <nav>
          <ul class="nav nav-pills pull-right">
            <button id="buttonRun" type="button" class="btn btn-lg btn-success disabledwhilerunning">Run</button>
            <button id="buttonStop" type="button" class="btn btn-lg btn-danger disabledwhilenotrunning">Stop</button>
          </ul>
        </nav>
        <h3 class="text-muted" >Simulation Controls</h3>
      </div>
	<form role="form">
      <div class="jumbotron">
        <h4>Current State <span id="stateMessage"></span></h4>
       	<p>
        	<div id="lead" class="mapimage"></div>
        </p>
        <%
        	simulation.data.configs.common.PropertiesLoader pl = simulation.data.configs.common.PropertiesLoader.getInstance();
                                	String moment = String.valueOf((simulation.consumers.web.MasonModelWebSController.getInstance().getStepTime()));
                        	        String maxmoment = pl.getProperty("maxmoment");
                        	        String minmoment = pl.getProperty("minmoment");
                        	        String stepmoment = pl.getProperty("stepmoment");
                        	        String defaultBehavior = String.valueOf((simulation.consumers.web.MasonModelWebSController.getInstance().getGuidedAgentProportion()));
        %>
        <div class="table-responsive">
        	
            <table class="table">
              <thead>
                <tr>
                  <th>Param</th>
                  <th>Value</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>Step Time</td>
                  <td><input name="moment" type="range" min="<%= minmoment %>" max="<%= maxmoment %>" step="<%= stepmoment %>" value="<%= moment %>" data-defaultValue="<%= moment %>" class="form-control" onchange="Param1rangevalue.value=value" required autofocus></td>
                  <td><output id="Param1rangevalue"><%= moment %></output></td>
                </tr>
                <tr>
                  <td>Percentage of Guided to Explorer</td>
                  <td><input name="guidedAgentProportion" type="range" min="0" max="1" step="0.05" value="<%= defaultBehavior %>" data-defaultValue="<%= defaultBehavior %>" class="form-control" onchange="Param2rangevalue.value=value" required autofocus></td>
                  <td><output id="Param2rangevalue"><%= defaultBehavior %></output></td>
                </tr>
              </tbody>
            </table>
            
          </div>
          <div class="row">
          	<ul class="nav nav-pills pull-right">
          		<h4><a id="comparisonlink" class="disabledwhileinit disabledwhilenotrunning" href="${pageContext.request.contextPath}/comparison.jsp" target="_blank">Open Comparison Window</a></h4>
            </ul>
          </div>
      </div> <!-- /jumbotron -->
	  
      <div class="header">
        <nav>
          <ul class="nav nav-pills pull-right">
            <button id="buttonReset" type="reset" class="btn btn-lg btn-info">Reset</button>
            <button id="buttonPost"  type="button" class="btn btn-lg btn-warning">Post</button>
          </ul>
        </nav>
        <h3 class="text-muted text-left">Params Controls</h3><span id="postingresult"></span>
      </div>
      <h4>Current State <span id="timeMessage"></span></h4>
     </form>
     <footer class="footer">
        <p>&copy; Geotec Research Group 2015</p>
     </footer>

    </div> <!-- /container -->
    <div class="modal"><!-- Place at bottom of page --></div>
</body>
</html>
