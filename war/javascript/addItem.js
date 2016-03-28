// JavaScript Document
$(document).ready(function(){
	
	$("input[name='newItemType']").change(function() {
		window.location.href = location.pathname+"?"+$(this).val();
	});
});