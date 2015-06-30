<html lang="en">
<head>
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
  padding-top: 10px;
  padding-bottom: 20px;
}

/* Everything but the jumbotron gets side spacing for mobile first views */
.header,
.footer {
  padding-right: 15px;
  padding-left: 15px;
}

/* Custom page header */
.header {
  border-bottom: 1px solid #e5e5e5;
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

/* Responsive: Portrait tablets and up */
@media screen and (min-width: 768px) {
  /* Remove the padding we set earlier */
  .header,
  .footer {
    padding-right: 0;
    padding-left: 0;
  }
  /* Space out the masthead */
  .header {
    margin-bottom: 10px;
  }
  /* Remove the bottom border on the jumbotron for visual effect */
  .jumbotron {
    border-bottom: 0;
  }
}
.header h3 {
  padding-bottom: 0px;
  margin-top: 0;
  margin-bottom: 0;
  padding-top: 0px;
  padding-bottom: 10px;
}
.jumbotron .container{
	margin-bottom:0px;
	margin-top:0px;
	padding-top: 0px;
	padding-bottom: 0px;
}
.headertext{
	text-align: center;
}
.footer{
	text-align: center;
}

.mapimage{
	width:100%;
	height:80%;
}
#log{
	text-align: left;
	width:100%;
	height:200px;
}
</style>

<script>
	var timer;
	var UPDATE_TIME = 3000; 	// 3.0 sec
	var lastTime = 0;
	
	var registry = [];
	var getlogsUrl = "${pageContext.request.contextPath}/services/controller/getlogs";
	
	// When page has loaded
	$(init);
	
	function init(){
		timer = window.setInterval(update, UPDATE_TIME);
	}
	
	function update(){
		var url = getlogsUrl + "?time=" + lastTime;
		doAjax(url, 
			function(data){
			var size = Number(data.size);
				if(size != 0){
					var lines = $("#log").html();
					if (size == 1){
						var entrytime = Number(data.entries.time);
						var printETime = ((entrytime - lastTime) == entrytime)? 0 : (entrytime - lastTime);
						lines += data.entries.text + " time:" + printETime + "\n";
					}
					else{
						$.each(data.entries, function(index, value){
							var valtime = Number(value.time);
							var printTime = ((valtime - lastTime) == valtime)? 0 : (valtime - lastTime);
							lines += value.text + " time:" + printTime + "\n";
						});
					}
					$("#log").html(lines);
					lastTime = Number(data.time);
				}
			}, 
			null,
			function(){ }
		);
	}
  </script>

</head>

<body role="document">
  <div class="header">
    <h3 class="headertext">Comparison</h3>
  </div>

  <div class="jumbotron" style="padding-top: 5px;">
  	<div class="container">
    	<div class="row">
	        <div class="col-md-6">
            	<h4>From simulator data</h4>
                <p>
            		<iframe src="${pageContext.request.contextPath}/map.jsp" class="mapimage"></iframe>
                </p>
            </div>
            <div class="col-md-6">
            	<h4>From SmartParking data</h4>
                <p>
	            	<iframe src="${pageContext.request.contextPath}/scmap.html" class="mapimage"></iframe>
                </p>
            </div>
     	</div>
     	<div class="row">
     		<div class="col-md-12">
     			<textarea id="log" readonly></textarea>
            </div>
     	</div>
     	
	</div>
  </div> <!-- /jumbotron -->      
  <footer class="footer">
    <p>&copy; Geotec Research Group 2015</p>
  </footer>
</body>
</html>
