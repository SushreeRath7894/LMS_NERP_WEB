function pendingChat() {

	var fromDate = $("#fromDate8").val();
	var toDate = $("#toDate8").val();
	var org = $("#purcPendOrdOrg").find('option:selected').text();
	var orgDiv = $("#purcPendOrdOrgDiv").find('option:selected').text();
	var loc = $("#pendOrdLoc").find('option:selected').text();

	//Ajax for Pending head data
	$.ajax({
		url: "view-dashboard-pending-head-count",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});



	//Ajax for Pending Order Details
	$.ajax({
		url: "view-dashboard-pending-order-details",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});

	//Ajax for Pending  Stock Movement Details
	$.ajax({
		url: "view-dashboard-pending-stock-movement-details",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});

	//Ajax for Pending Sale and Stock Details
	$.ajax({
		url: "view-dashboard-pending-sale-stock-details",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);

		},
		error: function(error) {
			console.error(error);
		}
	});
}

function getPendingFilterData() {
	pendingChat()
}

function resetPendingDashboardData() {
	var today = new Date();
	if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
	$("#fromDate8").val(fromDate);
	$("#toDate8").val(toDate);
	getPendingFilterData();
}

/*for onchange of org & div*/

function purPendOrdChangeOrg() {
	getPendingFilterData();
}

function purPendOrdOrgDiv() {
	getPendingFilterData();
}