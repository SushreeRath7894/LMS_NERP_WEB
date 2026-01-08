
function getOperationalCRMChart() {		
	var executive = $("#executiveOprId").val();
	if(executive == 'all'){
		getCRMHeadData();
	}else{
		getOperationalCountDataWithExecutive(executive);
	}
	var id = "totalLeads";
	getAllCRMData(id);
	
	
}

function getOperationalSearchCRMChart() {		
	var executive = $("#executiveOprId").val();
	if(executive == 'all'){
		getCRMHeadData();
	}else{
		getOperationalCountDataWithExecutive(executive);
	}
	
	var id = $("#tabId").val();
	getAllCRMData(id);
	
	
}



function getCRMHeadData() {
	var org = $("#organizationCrmOpr").find('option:selected').text();
	var orgDiv = $("#divisionCrmOpr").find('option:selected').text();
	var loc = $("#locationCrmOpr").val();
	
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	
	var allKeyRoles = $("#allKeyRoles").val();

	
	if (!fromDate || !toDate) {
	   var today = new Date();
   		if (today.getMonth() < 2
   				|| (today.getMonth() === 2 && today.getDate() < 31)) {
   			var fromYear = today.getFullYear() - 1;
   		} else {
   			var fromYear = today.getFullYear();
   		}

   		 fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
   				+ fromYear;
   	     toDate = ('0' + today.getDate()).slice(-2) + '-'
   				+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
   				+ today.getFullYear();
   				
   			
	}

	$.ajax({
		type: "GET",
		url: "crm-dashboard-getAllHeadCount",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			allKeyRoles, allKeyRoles
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;				
				$("#table_lead_count_id").text(allData[0].leadCount);
				$("#total_sql_count_id").text(allData[0].sqlCount);
				$("#total_opportunities_count_id").text(allData[0].opportunitiesCount);
				$("#total_proposal_count_id").text(allData[0].proposalCount);
				$("#total_nigotation_count_id").text(allData[0].negotiationCount);
				$("#total_wins_count_id").text(allData[0].winCount);

			}
		},
		error: function(data) {
			console.log(data);
		}
	});
}

let storedId = "";
function getAllCRMData(id) {
	storedId = id;
	
	var org = $("#organizationCrmOpr").find('option:selected').text();
	var orgDiv = $("#divisionCrmOpr").find('option:selected').text();
	var loc = $("#locationCrmOpr").val();
	
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var executive = $("#executiveOprId").val();
	var allKeyRoles = $("#allKeyRoles").val();
	
	
	if (!fromDate || !toDate) {
	   var today = new Date();
   		if (today.getMonth() < 2
   				|| (today.getMonth() === 2 && today.getDate() < 31)) {
   			var fromYear = today.getFullYear() - 1;
   		} else {
   			var fromYear = today.getFullYear();
   		}

   		 fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
   				+ fromYear;
   	     toDate = ('0' + today.getDate()).slice(-2) + '-'
   				+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
   				+ today.getFullYear();
   				
   				
   			
	}
	
	//alert("executive----------"+executive);
	
	$("#myGrid1").hide();
	$("#myGrid2").hide();
	$("#myGrid3").hide();
	$("#myGrid5").hide();
	$("#myGrid4").hide();
	$("#myGrid6").hide();


	$(function() {


		if (id == "totalLeads") {
			$("#myGrid1").hide().empty();
			var gridDiv = document.querySelector('#myGrid1');
			new agGrid.Grid(gridDiv, gridOptions1);
			if (executive == "all") {
		        $.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive,
					allKeyRoles: allKeyRoles
					
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {						
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#table_lead_count_id").text(length);
						if (!allData || allData === null) {
							gridOptions1.api.setRowData([]);
						} else {
							gridOptions1.api.setRowData(allData);
						}
					}
				
					
					
					$("#myGrid1").show();
					$("#totalLead").show();
					$("#totalLeadExport").show();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();
					
				},
				error: function(data) {
					console.log(data);
				}
			});
		    }else{
			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {						
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#table_lead_count_id").text(length);
						if (!allData || allData === null) {
							gridOptions1.api.setRowData([]);
						} else {
							gridOptions1.api.setRowData(allData);
						}
					}
					$("#myGrid1").show();
					$("#totalLead").show();
					$("#totalLeadExport").show();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();
				},
				error: function(data) {
					console.log(data);
				}
			});
		}
			

			
			
			
		}
		
	
		else if (id == "totalSQLs") {

			$("#myGrid2").hide().empty();
			var gridDiv = document.querySelector('#myGrid2');
			new agGrid.Grid(gridDiv, gridOptions2);
			if (executive == "all") {
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive,
					allKeyRoles: allKeyRoles
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_sql_count_id").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions2.api.setRowData([]);
						} else {
							gridOptions2.api.setRowData(allData);
						}
					}
										
					
					$("#myGrid2").show();
					$("#totalSQl").show();
					$("#totalSQlExport").show();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid1").hide().empty();					
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
			}else{
				
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_sql_count_id").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions2.api.setRowData([]);
						} else {
							gridOptions2.api.setRowData(allData);
						}
					}
					$("#myGrid2").show();
					$("#totalSQl").show();
					$("#totalSQlExport").show();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid1").hide().empty();					
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
					
			}
			

		}
	
		else if (id == "totalOpportunities") {

			$("#myGrid3").hide().empty();
			var gridDiv = document.querySelector('#myGrid3');
			new agGrid.Grid(gridDiv, gridOptions3);
			if (executive == "all") {
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive,
					allKeyRoles: allKeyRoles
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_opportunities_count_id").text(length);
										
						if (!jsonData || jsonData === null) {
							gridOptions3.api.setRowData([]);
						} else {
							gridOptions3.api.setRowData(allData);
						}
					}
								
					
					$("#myGrid3").show();
					$("#totalOpportunity").show();
					$("#totalOpportunityExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
			}else{
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_opportunities_count_id").text(length);
										
						if (!jsonData || jsonData === null) {
							gridOptions3.api.setRowData([]);
						} else {
							gridOptions3.api.setRowData(allData);
						}
					}
					
					$("#myGrid3").show();
					$("#totalOpportunity").show();
					$("#totalOpportunityExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
			}
			

		}
		
		else if (id == "totalProposals") {

			$("#myGrid4").hide().empty();
			var gridDiv = document.querySelector('#myGrid4');
			new agGrid.Grid(gridDiv, gridOptions4);
			if (executive == "all") {
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive,
					allKeyRoles: allKeyRoles
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_proposal_count_id").text(length);
										
						if (!jsonData || jsonData === null) {
							gridOptions4.api.setRowData([]);
						} else {
							gridOptions4.api.setRowData(allData);
						}
					}
					
					$("#myGrid4").show();
					$("#totalProposal").show();
					$("#totalProposalExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
			}else{
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_proposal_count_id").text(length);
										
						if (!jsonData || jsonData === null) {
							gridOptions4.api.setRowData([]);
						} else {
							gridOptions4.api.setRowData(allData);
						}
					}
					
					$("#myGrid4").show();
					$("#totalProposal").show();
					$("#totalProposalExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
				
			}
			

		}
		
		else if (id == "totalNigotations") {

			$("#myGrid5").hide().empty();
			var gridDiv = document.querySelector('#myGrid5');
			new agGrid.Grid(gridDiv, gridOptions5);
			//alert("executive----"+executive);
			if (executive == "all") {
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive,
					allKeyRoles: allKeyRoles
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_nigotation_count_id").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions5.api.setRowData([]);
						} else {
							gridOptions5.api.setRowData(allData);
						}
					}
									
					
					$("#myGrid5").show();
					$("#totalNegotiation").show();
					$("#totalNegotiationExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();
				},
				error: function(data) {
					console.log(data);
				}
			});
			}else{
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_nigotation_count_id").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions5.api.setRowData([]);
						} else {
							gridOptions5.api.setRowData(allData);
						}
					}
					
					$("#myGrid5").show();
					$("#totalNegotiation").show();
					$("#totalNegotiationExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});
			}
			

		}
		
		else if (id == "totalWins") {

			$("#myGrid6").hide().empty();
			var gridDiv = document.querySelector('#myGrid6');
			new agGrid.Grid(gridDiv, gridOptions6);
			if (executive == "all") {
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive,
					allKeyRoles: allKeyRoles
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#total_wins_count_id").text(length);
						if (!jsonData || jsonData === null) {
							gridOptions6.api.setRowData([]);
						} else {
							gridOptions6.api.setRowData(allData);
							//	alert("jsonData------------" + jsonData)
						}
					}
					
					
					$("#myGrid6").show();
					$("#totalWin").show();
					$("#totalWinExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();

				}, error: function(data) {
					console.log(data);
				}
			});
				
			}else{
				$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#total_wins_count_id").text(length);
						if (!jsonData || jsonData === null) {
							gridOptions6.api.setRowData([]);
						} else {
							gridOptions6.api.setRowData(allData);
							//	alert("jsonData------------" + jsonData)
						}
					}
					
					$("#myGrid6").show();
					$("#totalWin").show();
					$("#totalWinExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();

				}, error: function(data) {
					console.log(data);
				}
			});
				
			}
			
		}
		
		});

}


function getOperationalCountDataWithExecutive(executive){
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organizationCrmOpr").find('option:selected').text();
	var orgDiv = $("#divisionCrmOpr").find('option:selected').text();
	var loc = $("#locationCrmOpr").val();
	var executive = $("#executiveOprId").val();
	
	if (!fromDate || !toDate) {
	   var today = new Date();
   		if (today.getMonth() < 2
   				|| (today.getMonth() === 2 && today.getDate() < 31)) {
   			var fromYear = today.getFullYear() - 1;
   		} else {
   			var fromYear = today.getFullYear();
   		}

   		 fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
   				+ fromYear;
   	     toDate = ('0' + today.getDate()).slice(-2) + '-'
   				+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
   				+ today.getFullYear();
   				
   			
	}

	$.ajax({
		type: "GET",
		url: "crm-dashboard-getAllHeadCount-with-executive",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc,
			executive: executive
			
			},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				$("#table_lead_count_id").text(allData[0].leadCount);
				$("#total_sql_count_id").text(allData[0].sqlCount);
				$("#total_opportunities_count_id").text(allData[0].opportunitiesCount);
				$("#total_proposal_count_id").text(allData[0].proposalCount);
				$("#total_nigotation_count_id").text(allData[0].negotiationCount);
				$("#total_wins_count_id").text(allData[0].winCount);

			}
		},
		error: function(data) {
			console.log(data);
		}
	});
};

function getOperationalTableDataWithExecutive(executive){
	id = $("#tabId").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#organizationCrmOpr").find('option:selected').text();
	var orgDiv = $("#divisionCrmOpr").find('option:selected').text();
	var loc = $("#locationCrmOpr").val();
	var executive = $("#executiveOprId").val();


	
	$("#myGrid1").hide();
	$("#myGrid2").hide();
	$("#myGrid3").hide();
	$("#myGrid5").hide();
	$("#myGrid4").hide();
	$("#myGrid6").hide();


	$(function() {


		if (id == "totalLeads") {

			$("#myGrid1").hide().empty();
			var gridDiv = document.querySelector('#myGrid1');
			new agGrid.Grid(gridDiv, gridOptions1);

			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
						
						
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#table_lead_count_id").text(length);


						if (!allData || allData === null) {
							gridOptions1.api.setRowData([]);
						} else {
							gridOptions1.api.setRowData(allData);
						}
					}
					$("#myGrid1").show();
					$("#totalLead").show();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();


				},
				error: function(data) {
					console.log(data);
				}
			});
		}
		
	
		else if (id == "totalSQLs") {

			$("#myGrid2").hide().empty();
			var gridDiv = document.querySelector('#myGrid2');
			new agGrid.Grid(gridDiv, gridOptions2);
			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_sql_count_id").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions2.api.setRowData([]);
						} else {
							gridOptions2.api.setRowData(allData);
						}
					}
					$("#myGrid2").show();
					$("#totalSQl").show();
					$("#totalSQlExport").show();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid1").hide().empty();					
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();


				},
				error: function(data) {
					console.log(data);
				}
			});

		}
	
		else if (id == "totalOpportunities") {

			$("#myGrid3").hide().empty();
			var gridDiv = document.querySelector('#myGrid3');
			new agGrid.Grid(gridDiv, gridOptions3);
			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_opportunities_count_id").text(length);
										
						if (!jsonData || jsonData === null) {
							gridOptions3.api.setRowData([]);
						} else {
							gridOptions3.api.setRowData(allData);
						}
					}
					
					$("#myGrid3").show();
					$("#totalOpportunity").show();
					$("#totalOpportunityExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();


				},
				error: function(data) {
					console.log(data);
				}
			});

		}
		
		else if (id == "totalProposals") {

			$("#myGrid4").hide().empty();
			var gridDiv = document.querySelector('#myGrid4');
			new agGrid.Grid(gridDiv, gridOptions4);
			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_proposal_count_id").text(length);
										
						if (!jsonData || jsonData === null) {
							gridOptions4.api.setRowData([]);
						} else {
							gridOptions4.api.setRowData(allData);
						}
					}
					
					$("#myGrid4").show();
					$("#totalProposal").show();
					$("#totalProposalExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();
					
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});

		}
		
		else if (id == "totalNigotations") {

			$("#myGrid5").hide().empty();
			var gridDiv = document.querySelector('#myGrid5');
			new agGrid.Grid(gridDiv, gridOptions5);
			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData = jsonData.dashboardData;
					
					var length = allData.length;
					$("#total_nigotation_count_id").text(length);
					
					
						if (!jsonData || jsonData === null) {
							gridOptions5.api.setRowData([]);
						} else {
							gridOptions5.api.setRowData(allData);
						}
					}
					
					$("#myGrid5").show();
					$("#totalNegotiation").show();
					$("#totalNegotiationExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid6").hide().empty();
					$("#totalWin").hide();
					$("#totalWinExport").hide();

				},
				error: function(data) {
					console.log(data);
				}
			});

		}
		
		else if (id == "totalWins") {

			$("#myGrid6").hide().empty();
			var gridDiv = document.querySelector('#myGrid6');
			new agGrid.Grid(gridDiv, gridOptions6);
			$.ajax({
				type: "GET",
				url: "crm-dashboard-getAllOperationalRecord-with-executive",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc,
					executive: executive
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.dashboardData;
					
						var length = allData.length;
						$("#total_wins_count_id").text(length);
						if (!jsonData || jsonData === null) {
							gridOptions6.api.setRowData([]);
						} else {
							gridOptions6.api.setRowData(allData);
							//	alert("jsonData------------" + jsonData)
						}
					}
					
					$("#myGrid6").show();
					$("#totalWin").show();
					$("#totalWinExport").show();
					$("#myGrid1").hide().empty();
					$("#totalLead").hide();
					$("#totalLeadExport").hide();
					$("#myGrid2").hide().empty();					
					$("#totalSQl").hide();
					$("#totalSQlExport").hide();
					$("#myGrid3").hide().empty();
					$("#totalOpportunity").hide();
					$("#totalOpportunityExport").hide();
					$("#myGrid4").hide().empty();
					$("#totalProposal").hide();
					$("#totalProposalExport").hide();
					$("#myGrid5").hide().empty();
					$("#totalNegotiation").hide();
					$("#totalNegotiationExport").hide();

				}, error: function(data) {
					console.log(data);
				}
			});
		}
		
		});
};


/*Column Def Starts*/

const columnDefs6 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'
	},
		{
		headerName: 'SALES ORDER NO',
		field: 'soId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'PURCHASE ORDER NO',
		field: 'poId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'PROJECT/DEAL NAME',
		field: 'projectDealName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'CUSTOMER NAME',
		field: 'customerName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	
	
	{
		headerName: "EXECUTIVE NAME",
		field: 'executiveName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "PRODUCT NAME",
		field: 'productSKU',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "SKU NAME",
		field: 'skuName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "SERVE TYPE",
		field: 'serveType',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	
	
	{
		headerName: 'QUANTITY',
		field: 'qty',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'PRICE',
		field: 'unitPrice',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'DISCOUNT %',
		field: 'discountPercentage',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	
	
	{
		headerName: 'LINE TOTAL',
		field: 'lineTotal',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'GST RATE',
		field: 'gstRate',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'CGST',
		field: 'cgst',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	
	{
		headerName: 'SGST',
		field: 'sgst',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'IGST',
		field: 'igst',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'TAXABLE AMOUNT',
		field: 'taxableAmount',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'SALES ORDER STATUS',
		field: 'orderStatus',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'LEAD STATUS',
		field: 'leadStatus',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},
	
];

// Grid Options
const gridOptions6 = {
	columnDefs: columnDefs6,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};



const columnDefs1 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'
	},
	{
		headerName: 'LEAD ID',
		field: 'leadId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'LEAD NAME',
		field: 'fullName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'LEAD EMAIL',
		field: 'mailId',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'LEAD MOBILE',
		field: 'mobileNo',
		width: 180,
		cellStyle: { textAlign: 'left' },


	},
	
	{
		headerName: "Customer Name",
		field: 'customerName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	
	
	{
		headerName: "LEAD'S EXECUTIVE",
		field: 'leadOwner',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "DEAL/PROJECT NAME",
		field: 'projectName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: 'LEAD STATUS',
		field: 'leadStatus',
		cellStyle: { textAlign: 'left' },
		width: 150,
		cellRenderer : function(params) {
			if (params.data.leadStatus == 'TLSM00001') { 
				return '<div style="color:#0642f5">Created</div>';
			} else if (params.data.leadStatus == 'TLSM00002') {
				return '<div style="color:#0642f5">Attempted to contact</div>';
			} else if (params.data.leadStatus == 'TLSM00003') {
				return '<div style="color:#0642f5">Contacted</div>';
			} else if (params.data.leadStatus == 'TLSM00004') {
				return '<div style="color:#0642f5">Qualified</div>';
			} else if (params.data.leadStatus == 'TLSM00005') {
				return '<div style="color:#0642f5">Proposed</div>';
			} else if (params.data.leadStatus == 'TLSM00006') {
				return '<div style="color:#0642f5">Negotiated</div>';
			} else if (params.data.leadStatus == 'TLSM00007') {
				return '<div style="color:#0642f5">Order Booked</div>';
			} else if (params.data.leadStatus == 'TLSM00008') {
				return '<div style="color:#0642f5">Junk Lead</div>';
			} else if (params.data.leadStatus == 'TLSM00009') {
				return '<div style="color:#0642f5">Lost Lead</div>';
			} else if (params.data.leadStatus == 'TLSM00010') {
				return '<div style="color:#0642f5">Approval Pending</div>';
			} else if (params.data.leadStatus == 'TLSM00011') {
				return '<div style="color:#0642f5">Lead Won</div>';
			}
		}
	},
	
	
	

	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},

	
];

const gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187,

	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

const columnDefs2 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
		{
		headerName: 'LEAD ID',
		field: 'leadId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'LEAD NAME',
		field: 'fullName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'LEAD EMAIL',
		field: 'mailId',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'LEAD MOBILE',
		field: 'mobileNo',
		width: 180,
		cellStyle: { textAlign: 'left' },


	},
	
	{
		headerName: "Customer Name",
		field: 'customerName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "LEAD'S EXECUTIVE",
		field: 'leadOwner',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "DEAL/PROJECT NAME",
		field: 'projectName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: 'LEAD STATUS',
		field: 'leadStatus',
		cellStyle: { textAlign: 'left' },
		width: 150,
		cellRenderer : function(params) {
			if (params.data.leadStatus == 'TLSM00001') { 
				return '<div style="color:#0642f5">Created</div>';
			} else if (params.data.leadStatus == 'TLSM00002') {
				return '<div style="color:#0642f5">Attempted to contact</div>';
			} else if (params.data.leadStatus == 'TLSM00003') {
				return '<div style="color:#0642f5">Contacted</div>';
			} else if (params.data.leadStatus == 'TLSM00004') {
				return '<div style="color:#0642f5">Qualified</div>';
			} else if (params.data.leadStatus == 'TLSM00005') {
				return '<div style="color:#0642f5">Proposed</div>';
			} else if (params.data.leadStatus == 'TLSM00006') {
				return '<div style="color:#0642f5">Negotiated</div>';
			} else if (params.data.leadStatus == 'TLSM00007') {
				return '<div style="color:#0642f5">Order Booked</div>';
			} else if (params.data.leadStatus == 'TLSM00008') {
				return '<div style="color:#0642f5">Junk Lead</div>';
			} else if (params.data.leadStatus == 'TLSM00009') {
				return '<div style="color:#0642f5">Lost Lead</div>';
			} else if (params.data.leadStatus == 'TLSM00010') {
				return '<div style="color:#0642f5">Approval Pending</div>';
			} else if (params.data.leadStatus == 'TLSM00011') {
				return '<div style="color:#0642f5">Lead Won</div>';
			}
		}
	},
	
	
	

	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},
];

const gridOptions2 = {
	columnDefs: columnDefs2,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};



const columnDefs3 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
		{
		headerName: 'LEAD ID',
		field: 'leadId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'LEAD NAME',
		field: 'fullName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'LEAD EMAIL',
		field: 'mailId',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	{
		headerName: 'LEAD MOBILE',
		field: 'mobileNo',
		width: 180,
		cellStyle: { textAlign: 'left' },


	},
	
	{
		headerName: "Customer Name",
		field: 'customerName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "LEAD'S EXECUTIVE",
		field: 'leadOwner',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "DEAL/PROJECT NAME",
		field: 'projectName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: 'LEAD STATUS',
		field: 'leadStatus',
		cellStyle: { textAlign: 'left' },
		width: 150,
		cellRenderer : function(params) {
			if (params.data.leadStatus == 'TLSM00001') { 
				return '<div style="color:#0642f5">Created</div>';
			} else if (params.data.leadStatus == 'TLSM00002') {
				return '<div style="color:#0642f5">Attempted to contact</div>';
			} else if (params.data.leadStatus == 'TLSM00003') {
				return '<div style="color:#0642f5">Contacted</div>';
			} else if (params.data.leadStatus == 'TLSM00004') {
				return '<div style="color:#0642f5">Qualified</div>';
			} else if (params.data.leadStatus == 'TLSM00005') {
				return '<div style="color:#0642f5">Proposed</div>';
			} else if (params.data.leadStatus == 'TLSM00006') {
				return '<div style="color:#0642f5">Negotiated</div>';
			} else if (params.data.leadStatus == 'TLSM00007') {
				return '<div style="color:#0642f5">Order Booked</div>';
			} else if (params.data.leadStatus == 'TLSM00008') {
				return '<div style="color:#0642f5">Junk Lead</div>';
			} else if (params.data.leadStatus == 'TLSM00009') {
				return '<div style="color:#0642f5">Lost Lead</div>';
			} else if (params.data.leadStatus == 'TLSM00010') {
				return '<div style="color:#0642f5">Approval Pending</div>';
			} else if (params.data.leadStatus == 'TLSM00011') {
				return '<div style="color:#0642f5">Lead Won</div>';
			}
			
		}
	},
	
	
	

	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},
];

const gridOptions3 = {
	columnDefs: columnDefs3,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

const columnDefs4 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'QUOTATION NO.',
		field: 'quotationId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'PROJECT/DEAL NAME',
		field: 'projectDealName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	
	
	{
		headerName: "CUSTOMER NAME",
		field: 'customerName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: "EXECUTIVE NAME",
		field: 'executiveName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	
	{
		headerName: "PRODUCT/SKU NAME",
		field: 'productSKU',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: 'QUANTITY',
		field: 'qty',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'PRICE',
		field: 'unitPrice',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'LINE TOTAL',
		field: 'lineTotal',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	

	
	{
		headerName: 'QUOTATION APPROVAL STATUS',
		field: 'approvalStatus',
		cellStyle: { textAlign: 'left' },
		width: 150,
		cellRenderer : function(params) {
			if (params.data.approvalStatus == '0') { 
				return '<div style="color:#0642f5">Pending</div>';
			} else if (params.data.approvalStatus == '1') {
				return '<div style="color:#0642f5">Approved</div>';
			} else if (params.data.approvalStatus == '2') {
				return '<div style="color:#0642f5">Revised</div>';
			}
		}
	},
	
	{
		headerName: 'LEAD STATUS',
		field: 'leadStatus',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},
	
	/*{
		headerName : 'Requisition Status',
		field : "status",
		width : 130,
		cellStyle: { textAlign: 'left' },
		cellRenderer : function(params) {
			if (params.data.status == 0) { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 1) {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
	},*/

];

const gridOptions4 = {
	columnDefs: columnDefs4,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};

const columnDefs5 = [
	{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8, sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left'

	},
	{
		headerName: 'PO NO.',
		field: 'poId',
		width: 150,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'PROJECT/DEAL NAME',
		field: 'projectDealName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left',
	},
	
	{
		headerName: 'CUSTOMER NAME',
		field: 'customerName',
		width: 180,
		cellStyle: { textAlign: 'left' },
		pinned: 'left'
	},
	
	{
		headerName: "EXECUTIVE NAME",
		field: 'executiveName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	
	{
		headerName: "PRODUCT NAME",
		field: 'productSKU',
		width: 180,
		cellStyle: { textAlign: 'right' }

	},
	
	{
		headerName: "SKU NAME",
		field: 'skuName',
		width: 180,
		cellStyle: { textAlign: 'left' }

	},
	
	{
		headerName: 'QUANTITY',
		field: 'qty',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'PRICE',
		field: 'unitPrice',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'DISCOUNT %',
		field: 'discountPercentage',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'LINE AMOUNT',
		field: 'lineTotal',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'GST %',
		field: 'gstRate',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'CGST',
		field: 'cgst',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'SGST',
		field: 'sgst',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	
	{
		headerName: 'IGST',
		field: 'igst',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'TAXABLE AMOUNT',
		field: 'taxableAmount',
		cellStyle: { textAlign: 'right' },
		width: 150
	},
	
	{
		headerName: 'ORDER STATUS',
		field: 'orderStatus',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'LEAD STATUS',
		field: 'leadStatus',
		cellStyle: { textAlign: 'left' },
		width: 150
	},
	
	{
		headerName: 'CREATED DATE',
		field: 'createdOn',
		width: 120,
		cellStyle: { textAlign: 'left' }

	},
	
	/*{
		headerName : 'Requisition Status',
		field : "status",
		width : 130,
		cellStyle: { textAlign: 'left' },
		cellRenderer : function(params) {
			if (params.data.status == 0) { 
				return '<div style="color:#a9a9a9">Pending</div>';
			} else if (params.data.status == 1) {
				return '<div style="color:#0642f5">Approved</div>';
			}
		}
	},*/
];

const gridOptions5 = {
	columnDefs: columnDefs5,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 187
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,

};