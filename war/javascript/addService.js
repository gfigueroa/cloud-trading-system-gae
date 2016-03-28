// JavaScript Document
$(document).ready(function(){
	
	$("input[name='newServiceType']").change(function() {
		window.location.href = location.pathname+"?"+$(this).val();
	});
});