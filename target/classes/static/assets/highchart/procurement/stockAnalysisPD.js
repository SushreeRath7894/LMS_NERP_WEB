function stockAnalysisChat() {

	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	var org = $("#purcAnalysisOrg").find('option:selected').text();
	var orgDiv = $("#purcAnalysOrgDiv").find('option:selected').text();
	var loc = $("#analysisLocation").find('option:selected').text();

	/*Head Data for view */

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-analysis-head-data",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});


	/*Head Data for stock*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-analysis-stock-head-data",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});


	/*for mostViewed*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-analysis-most-viewed",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*LEAST VIEWED*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-analysis-least-viewed",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*HIGH INVENTORY*/

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-analysis-highest-inventory",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*PREDICTED DAYS OUT OF STOCK*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-prideced-day-outofstock",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});


	/*BOTTOM SALES*/

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-analysis-bottom-sales",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*TOP SALES SUB CATEGORY RUNNING OUT OF STOCK*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-top-sales-running-outofstock",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*TOP SALES SUB CATEGORY  OUT OF STOCK*/

	$.ajax({
		type: "GET",
		url: "procurement-dashboard-top-sales-outofstock",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});

	/*SIMULATED DAYS OUT OF STOCK*/
	$.ajax({
		type: "GET",
		url: "procurement-dashboard-simulated-day-outofstock",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			if (response.code === "success") {

			} else {
				console.log("Error response from server.");
			}
		},
		error: function(data) {
			console.log("Error occurred during AJAX request.");
		}
	});
}
/*for search button*/
function getCostAnalysisFilter() {
	stockAnalysisChat();
}

/*for reset button*/
function resetCostAnalysisFilter() {

	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate7").val(fromDate);
	$("#toDate7").val(toDate);
	stockAnalysisChat();
}

/*function for change org & div*/

function purcAnalysChsngeOrg() {
	stockAnalysisChat();

}
function purcAnalysChsngeOrgDiv() {
	stockAnalysisChat();
}
