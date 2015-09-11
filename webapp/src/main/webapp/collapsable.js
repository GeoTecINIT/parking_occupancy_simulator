// Collapsible panel handlers
function createCollapsable(){
	function collapsing(toolbar, collpin, collcontent){
		if ($("#collpin").hasClass('glyphicon-chevron-down')) {
				$("#toolbar").addClass('expand');
				$("#collpin").removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
		}
		else {
			$("#toolbar").removeClass('expand');
			$("#collpin").removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
		}
	}
	$("#collcontent").on("show.bs.collapse", collapsing);
	$("#collcontent").on("hidden.bs.collapse", collapsing);
}
