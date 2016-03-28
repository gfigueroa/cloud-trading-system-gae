// JavaScript Document
$(document).ready(function() {
	$(".TypeTitle").click(function() {
		$(this).next("div").slideToggle("fast");
	});
	if($(".itemStatus").text() == "已接受")
	{
		$(".itemStatus").css({"color":"blue"});
	}
	else
	{
		$(".itemStatus").css({"color":"green"});
	}
	$(".WantItemClass").click(function() {
		var ItemName = $(this).parents("tr").children("td#GetItemName").text();
		var ItemId = $(this).attr("item_id");
		$("#inputMemberId tr td#ShowItemName").text(ItemName);
		$("#acceptItemId").val(ItemId);
		$("#inputMemberId").show("slow");
	});
	$(".WantServiceClass").click(function() {
		var GetServiceDescription = $(this).parents("tr").find("td.GetServiceDescription").text();
		var service_id = $(this).attr("service_id");
		alert(service_id);
		$("#ShowServiceName").text(GetServiceDescription);
		$("#acceptServiceId").val(service_id);
		$("#inputMemberId").show("slow");
	});
	
	$("#buttonClose").click(function(){ 
		$("#inputMemberId tr td#ShowItemName").text("");
		$("#acceptItemId").val("");
		$("#inputMemberId").hide("slow");
	});
});