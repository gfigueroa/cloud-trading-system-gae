// JavaScript Document
$(document).ready(function() {
	$(".listTitle_CSS").click(function() {
		$(this).parent().siblings("tr").toggle("slow");	
	});
});