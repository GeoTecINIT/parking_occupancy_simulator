function makeColorRange(rangeInputId, color1, color2, color3, color4, postFunction){
	var $rangeInput = $('#' + rangeInputId);
	var prefs = ['webkit-slider-runnable-track', 'moz-range-track', 'ms-track'];
	var sheet = document.createElement('style');
	
	$rangeInput.append(sheet);
	
	var getTrackStyle = function (el) {
		var curVal = el.value;
		var style = '';
		for (var i = 0; i < prefs.length; i++) {
			style += '#' + rangeInputId + '::-' + prefs[i] + '{background: linear-gradient(to right, ' + color1 + ' 0%, ' + color2 + ' ' + curVal + '%, ' + color3 + ' ' + curVal + '%, ' + color4 + ' 100%)}';
		}
		return style;
	}

	$rangeInput.on('input', function () {
		sheet.textContent = getTrackStyle(this);
		postFunction();
	});
	
	$rangeInput.trigger('input');
}
