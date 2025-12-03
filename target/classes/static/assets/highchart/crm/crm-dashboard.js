function tabView(type) {
	if (type == "operational") {
		operationalHighChat();
	} else if (type == "conversion") {
		conversionHighChat();
	} else{
		//reportsChat();
	} 
		
	
}



let previousDate = '';
let previousToDate = '';



$(document).ready(function() {
	function adjustNavbarClass() {
		const sideNavbar = document.getElementById('side_bar_menu');
		const screenWidth = window.innerWidth;

		if (screenWidth <= 768) {
			sideNavbar.classList.remove('active-nav');
		} else {
			sideNavbar.classList.add('active-nav');
		}
	}

	adjustNavbarClass();

	window.addEventListener('resize', adjustNavbarClass);

	var currentDate = new Date();

	var currentYear = currentDate.getFullYear();

	var financialYearStartDate = new Date(currentYear, 3, 1);
	if (currentDate < financialYearStartDate) {
		financialYearStartDate.setFullYear(currentYear - 1);
	}

	var formattedFinancialYearStartDate = formatDate(financialYearStartDate);

	var formattedCurrentDate = formatDate(currentDate);

	$('#fromdate').val(formattedFinancialYearStartDate);
	$('#todate').val(formattedCurrentDate);
	
	$('#fromdate, #todate').click(function () {
				previousDate = $('#fromdate').val();
				previousToDate = $('#todate').val();

			});



	fileterByDate();
	getAllCrm('table_lead');
	gatePassHeadingData('');
	getLeadDetails(id);

	$("#conversion").removeClass("active show");


});

function formatDate(date) {
	var year = date.getFullYear();
	var month = padZeros(date.getMonth() + 1);
	var day = padZeros(date.getDate());
	return year + '-' + month + '-' + day;
}

function padZeros(num) {
	return (num < 10 ? '0' : '') + num;
}


function dateValidation() {

	var fromDateStr = $('#fromdate').val();
	var toDateStr = $('#todate').val();


	if (fromDateStr > toDateStr) {
		$("#messageParagraph").text("From date should be less than to date");
		$("#msgOkModal").removeClass("btn3").addClass("btn1");
		$("#msgModal").modal('show');
		$("#myGrid").show();
		$('#fromdate').val(previousDate);
		$('#todate').val(previousToDate);
		return false;
	}
}

let fromDate = '';
let toDate = '';
function fileterByDate() {


	fromDate = $('#fromdate').val();
	toDate = $('#todate').val();
	gatePassHeadingData($("#executive").val());
	getAllCrm(filterById);
	conversionHighChat();
	
		  /*for activityHeadData*/
	  filterActivityByExecutive();
   
}

function restDate() {

	$("#executive").val('');

	var currentDate = new Date();

	var currentYear = currentDate.getFullYear();

	var financialYearStartDate = new Date(currentYear, 3, 1);
	if (currentDate < financialYearStartDate) {
		financialYearStartDate.setFullYear(currentYear - 1);
	}

	var formattedFinancialYearStartDate = formatDate(financialYearStartDate);

	var formattedCurrentDate = formatDate(currentDate);

	$('#fromdate').val(formattedFinancialYearStartDate);
	$('#todate').val(formattedCurrentDate);

	//getLeadDetails(executive);
	fileterByDate();
	getAllCrm(filterById);
		gatePassHeadingData('');
	  /*for activityHeadData*/
	  filterActivityByExecutive();
    
}

function getLeadDetails(id) {
	getAllCrm(filterById);
	gatePassHeadingData(id);
    conversionHighChat();
    /*for activityHeadData*/
      filterActivityByExecutive();
   
}




function gatePassHeadingData(executive) {
	var executive = $("#executive").val();
	$.ajax({
		type: "GET",
		url: "crm-dashboard-oprationalHeadData?fromDate=" + fromDate + "&toDate=" + toDate + "&executiveId=" + executive,
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				$('.spinner').hide();
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				$("#gate_details").text(allData[0].gate_details);
				$("#table_lead").text(allData[0].table_lead);
				var leadCount = allData[0].table_lead;
				var opportunityCount = allData[0].total_opportunities;
				$("#total_mql").text(allData[0].total_mql);
				$("#total_sql").text(allData[0].total_sql);
				$("#total_campaign").text(allData[0].total_campaign);
				$("#total_opportunities").text(allData[0].total_opportunities);
				$("#total_nigotation").text(allData[0].total_nigotation);
				$("#total_proposal").text(allData[0].total_proposal);
				$("#total_wins").text(allData[0].total_wins);
				$("#total_leads").text(allData[0].table_lead);
				$("#opportunities").text(allData[0].total_opportunities);
				//$("#opportunitesForFunnel").text(allData[0].total_opportunities);
				$("#negotiation").text(allData[0].total_nigotation);
				$("#proposal").text(allData[0].total_proposal);
				$("#wins").text(allData[0].total_wins);



				function gcd(a, b) {
					return b === 0 ? a : gcd(b, a % b);
				}

				var leadCount = allData[0].table_lead;
				var opportunityCount = allData[0].total_opportunities;
				var convertedLead = allData[0].total_converted_lead;
				var totalWinCount = allData[0].total_wins;

				// For Lead to opportunity Ratio
				if (opportunityCount !== 0 && leadCount !== 0) {
					var ratio;
					var divisor = gcd(leadCount, opportunityCount);
					var simplifiedLeadCount = leadCount / divisor;
					var simplifiedOpportunityCount = opportunityCount / divisor;
					$('#leadOpportunity').text(simplifiedLeadCount + ' : ' + simplifiedOpportunityCount);
				} else {
					$('#leadOpportunity').text('N/A');
				}

				// For Lead Conversion Ratio

				if (convertedLead !== 0 && leadCount !== 0) {
					var ratio1;
					var divisor1 = gcd(leadCount, convertedLead);
					var simplifiedLeadCount1 = leadCount / divisor1;
					var simplifiedOpportunityCount1 = convertedLead / divisor1;
					$('#leadconvertra').text(simplifiedLeadCount1 + ' : ' + simplifiedOpportunityCount1);
					$('#leadconvertra1').text(simplifiedLeadCount1 + ' : ' + simplifiedOpportunityCount1);
				} else {
					$('#leadconvertra').text('N/A');
					$('#leadconvertra1').text('N/A');
				}


				// For Opportunity to win Ratio

				if (totalWinCount !== 0 && opportunityCount !== 0) {
					var ratio2;
					var divisor2 = gcd(opportunityCount, totalWinCount);
					var simplifiedLeadCount2 = opportunityCount / divisor2;
					var simplifiedOpportunityCount2 = totalWinCount / divisor2;
					$('#leadwinra').text(simplifiedLeadCount2 + ' : ' + simplifiedOpportunityCount2);
				} else {
					$('#leadwinra').text('N/A');
				}

				//$("#leadOpportunity").text(allData[0].leadOpportunity);
				//$("#leadwinra").text(allData[0].leadwinra);
				$("#costpercus").text(allData[0].costpercus);
				$("#costperlead").text(allData[0].costperlead);
				$("#costper").text(allData[0].costper);


			}
		}, error: function(data) {
			console.log(data);
		}
	})
}


let filterById = '';
let selectedBoxId = '';
function getAllCrm(id) {
	var executive = $("#executive").val();
	
	
	if (id == '') {
		return false;
	}
	filterById = id;

	$.ajax({
		type: "GET",
		url: "dashboard-getAllCrm?id=" + id + "&fromDate=" + fromDate + "&toDate=" + toDate + "&executiveId=" + executive,
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				console.log('---------', JSON.parse(response.body));
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				$("#gateDetailsTable").hide();
				$("#totalGateTable").hide();
				$("#gateMsqlTable").hide();
				$("#totalEntryTable").hide();
				$("#totalSqlTable").hide();
				$("#totalcampaignTable").hide();
				$("#totalOpportunTable").hide();
				$("#totalNigotationTable").hide();
				$("#totalProposalTable").hide();
				$("#totalWindsTable").hide();
				$("#totalGateTableEmpty").hide();
				$("#gateMsqlTableEmpty").hide();
				$("#totalSqlTableEmpty").hide();
				$("#totalcampaignTableEmpty").hide();
				$("#totalcampaignTableEmpty").hide();
				$("#totalProposalTableEmpty").hide();
				$("#totalOpportunTableEmpty").hide();
				$("#totalNigotationTableEmpty").hide();
				$("#totalWindsTableEmpty").hide();



				if (id === 'table_lead' && allData === null) {

					$("#totalGateTableEmpty").show();

				} else {
					$("#totalGateTableEmpty").hide();
				}


				if (id === 'total_mql' && allData === null) {

					$("#gateMsqlTableEmpty").show();

				} else {
					$("#gateMsqlTableEmpty").hide();
				}

				if (id === 'total_sql' && allData === null) {

					$("#totalSqlTableEmpty").show();

				} else {
					$("#totalSqlTableEmpty").hide();
				}


				if (id === 'total_campaign' && allData === null) {

					$("#totalcampaignTableEmpty").show();

				} else {
					$("#totalcampaignTableEmpty").hide();
				}

				if (id === 'total_opportunities' && allData === null) {

					$("#totalOpportunTableEmpty").show();

				} else {
					$("#totalOpportunTableEmpty").hide();
				}

				if (id === 'total_nigotation' && allData === null) {

					$("#totalNigotationTableEmpty").show();

				} else {
					$("#totalNigotationTableEmpty").hide();
				}

				if (id === 'total_proposal' && allData === null) {

					$("#totalProposalTableEmpty").show();

				} else {
					$("#totalProposalTableEmpty").hide();
				}

				if (id === 'total_wins' && allData === null) {

					$("#totalWindsTableEmpty").show();

				} else {
					$("#totalWindsTableEmpty").hide();
				}

				if (allData[0].status == "table_lead") {
					$("#gateDetailsTable").show();
					$("#gateDetailsData").empty();
					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr style="width: 100%;">'


							+ '<td style="width: 15%;">' + allData[i].leadName + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leadCompany + '</td>'
							+ '<td style="width: 25%;">' + allData[i].leadMail + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leads_phone + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leads_status + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leadExecutive + '</td>'
							+ '<td style="width: 15%;">' + allData[i].createdOn + '</td>'
							+ '</tr>';
						$("#gateDetailsData").append(abc);
					}

				} else if (allData[0].status == "total_mql") {

					$("#gateMsqlTable").show();
					$("#gateMqlData").empty();
					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr style="width: 100%;">'
							+ '<td style="width: 15%;">' + allData[i].leadName + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leadCompany + '</td>'
							+ '<td style="width: 25%;">' + allData[i].leadMail + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leads_phone + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leads_status + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leadExecutive + '</td>'
							+ '<td style="width: 15%;">' + allData[i].createdOn + '</td>'
							+ '</tr>';
						$("#gateMqlData").append(abc);
					}

				} else if (allData[0].status == "total_sql") {

					$("#totalSqlTable").show();
					$("#totalSqlData").empty();
					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr style="width: 100%;">'
							+ '<td style="width: 15%;">' + allData[i].leadName + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leadCompany + '</td>'
							+ '<td style="width: 25%;">' + allData[i].leadMail + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leads_phone + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leads_status + '</td>'
							+ '<td style="width: 15%;">' + allData[i].leadExecutive + '</td>'
							+ '<td style="width: 15%;">' + allData[i].createdOn + '</td>'
						$("#totalSqlData").append(abc);
					}

				} else if (allData[0].status == "total_campaign") {

					$("#totalcampaignTable").show();
					$("#totalcampaignData").empty();
					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr>'
							+ '<td>' + allData[i].leadName + '</td>'
							+ '<td>' + allData[i].campaign_name + '</td>'
							+ '<td>' + allData[i].campaign_type + '</td>'
							+ '<td>' + allData[i].campaign_owner + '</td>'
							+ '<td>' + allData[i].campaign_status + '</td>'
							+ '<td>' + allData[i].createdOn + '</td>'
							+ '<td style="text-align: right;">' + allData[i].campaign_cost + '</td>'
							+ '</tr>';
						$("#totalcampaignData").append(abc);
					}

				} else if (allData[0].status == "total_opportunities") {

					$("#totalOpportunTable").show();
					$("#totalOpportunData").empty();
					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr>'
							+ '<td>' + allData[i].leads_name + '</td>'
							+ '<td>' + allData[i].deal_name + '</td>'
							+ '<td>' + allData[i].deal_stage + '</td>'
							+ '<td>' + allData[i].deal_closingDate + '</td>'
							+ '<td>' + allData[i].deal_createDate + '</td>'
							+ '<td style="text-align: right;">' + allData[i].deal_ammount + '</td>'
							+ '</tr>';
						$("#totalOpportunData").append(abc);
					}

				} else if (allData[0].status == "total_nigotation") {

					$("#totalNigotationTable").show();
					$("#totalNigotationData").empty();

					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr>'
							+ '<td>' + allData[i].leadName + '</td>'
							+ '<td>' + allData[i].deal_name + '</td>'
							+ '<td>' + allData[i].deal_stage + '</td>'
							+ '<td>' + allData[i].deal_closingDate + '</td>'
							+ '<td>' + allData[i].deal_createDate + '</td>'
							+ '<td style="text-align: right;">' + allData[i].deal_ammount + '</td>'
							+ '</tr>';
						$("#totalNigotationData").append(abc);
					}

				} else if (allData[0].status == "total_proposal") {

					$("#totalProposalTable").show();
					$("#totalProposalData").empty();

					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr>'
							+ '<td>' + allData[i].leadName + '</td>'
							+ '<td>' + allData[i].deal_name + '</td>'
							+ '<td>' + allData[i].deal_stage + '</td>'
							+ '<td>' + allData[i].deal_closingDate + '</td>'
							+ '<td>' + allData[i].deal_createDate + '</td>'
							+ '<td style="text-align: right;">' + allData[i].deal_ammount + '</td>'
							+ '</tr>';
						$("#totalProposalData").append(abc);

					}

				} else if (allData[0].status == "total_wins") {

					$("#totalWindsTable").show();
					$("#totalWindsData").empty();

					for (var i = 0; i < allData.length; i++) {
						var abc = '<tr>'
							+ '<td>' + allData[i].leadName + '</td>'
							+ '<td>' + allData[i].deal_name + '</td>'
							+ '<td>' + allData[i].deal_stage + '</td>'
							+ '<td>' + allData[i].deal_closingDate + '</td>'
							+ '<td>' + allData[i].deal_createDate + '</td>'
							+ '<td style="text-align: right;">' + allData[i].deal_ammount + '</td>'
							+ '</tr>';
						$("#totalWindsData").append(abc);
					}

				} else {
					return false;
				}

			} else {

				return false;
			}
		},
		error: function(data) {
			console.log(data);
		}
	})



}
