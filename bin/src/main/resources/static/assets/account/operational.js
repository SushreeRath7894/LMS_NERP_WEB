function operational() {
	projectAccountData();
	var id = "acc_paybles";
	getAllReport(id);

	$('.newbx').removeClass('active');
	$("#payablesDiv").addClass('active');

}

function projectAccountData() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var org = $("#accOrgOper").find('option:selected').text();
	var orgDiv = $("#accOrgDivOper").find('option:selected').text();
	var loc = $("#accOperLoc").find('option:selected').text();


	if (fromDate === "" || toDate === "") {
		var today = new Date();
		if (today.getMonth() < 2 || (today.getMonth() === 2 && today.getDate() < 31)) {
			var fromYear = today.getFullYear() - 1;
		} else {
			var fromYear = today.getFullYear();
		}
		fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-' + fromYear;
		toDate = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();


	}
	$.ajax({
		type: "GET",
		url: "dashboard-account-getAllCounts",
		data: {
			fromDate: fromDate,
			toDate: toDate,
			org: org,
			orgDiv: orgDiv,
			loc: loc
		},
		async: true,
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				$("#acc_paybles").text(allData[0].acc_paybles);
				$("#acc_receiveable").text(allData[0].acc_receiveable);
				$("#acc_expenses").text(allData[0].acc_expenses);
				$("#acc_outstndg").text(allData[0].acc_outstndg);
				$("#acc_rec_outstndg").text(allData[0].acc_rec_outstndg);
				$("#acc_purchase_gstin").text(allData[0].acc_purchase_gstin);
				$("#acc_sales_gstin").text(allData[0].acc_sales_gstin);
				$("#acc_incometax").text(allData[0].acc_incometax);

				$("#total_Clients").text(allData[0].total_Clients);
				$("#total_Invoived").text(allData[0].total_Invoived.toFixed(2));
				$("#total_Received").text(allData[0].total_Received.toFixed(2));
				$("#total_Outstanding").text(allData[0].total_Outstanding.toFixed(2));
				$("#total_Indented").text(allData[0].total_Indented);
				$("#total_Ordered").text(allData[0].total_Ordered.toFixed(2));
				$("#val_indented").text(allData[0].val_indented);
				$("#incometax").text(allData[0].total_IncomeTaxCount);

			}
		}, error: function(data) {
			console.log(data);
		}
	})
}


let storedId = "";
function getAllReport(id) {

	storedId = id;

	$("#myGrid").hide();
	$("#myGrid1").hide();
	$("#myGrid2").hide();
	$("#myGrid3").hide();
	$("#myGrid4").hide();
	$("#myGrid5").hide();




	$(function() {

		var fromDate = $("#fromDate").val();
		var toDate = $("#toDate").val();
		var org = $("#accOrgOper").find('option:selected').text();
		var orgDiv = $("#accOrgDivOper").find('option:selected').text();
		var loc = $("#accOperLoc").find('option:selected').text();

		// Making AJAX call to fetch the data
		if (id == "acc_paybles") {
			$("#myGrid").show().empty();
			var gridDiv = document.querySelector('#myGrid');
			new agGrid.Grid(gridDiv, gridOptions);

			$.ajax({
				type: "GET",
				url: "dashboard-account-getAllReport",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					if (response.code === "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.AllData;
						//alert("allData-------"+allData)
						// Clear the existing data in the grid
						gridOptions.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions.api.setRowData([]);
						} else {

							gridOptions.api.setRowData(allData);
						}
					}

					$("#myGrid1").hide().empty();
					$("#myGrid2").hide().empty();
					$("#myGrid3").hide().empty();
					$("#myGrid4").hide().empty();
					$("#myGrid5").hide().empty();

					$("#receivableListH6").hide();
					$("#expense").hide();
					$("#productTestedTableass").hide();
					$("#payableListH6").show();
					$("#gstinSal").hide();
					$("#purchaseGs").hide();
					$("#incomeTax").hide();

				},
				error: function(data) {
					console.log(data);
				}

			});
		} else if (id == "acc_receiveable") {
			$("#myGrid1").show().empty();
			var gridDiv = document.querySelector('#myGrid1');
			new agGrid.Grid(gridDiv, gridOptions1);
			$.ajax({
				type: "GET",
				url: "dashboard-account-getAllReport",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					if (response.code === "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.AllData;
						// Clear the existing data in the grid
						gridOptions1.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions1.api.setRowData([]);
						} else {

							gridOptions1.api.setRowData(allData);
						}
					}
					$("#myGrid").hide().empty();
					$("#payableListH6").hide();
					$("#myGrid2").hide().empty();
					$("#expense").hide();
					$("#productTestedTableass").hide();
					$("#receivableListH6").show();
					$("#myGrid4").hide().empty();
					$("#gstinSal").hide();
					$("#myGrid3").hide().empty();
					$("#purchaseGs").hide();
					$("#myGrid5").hide().empty();
					$("#incomeTax").hide();

				},
				error: function(data) {
					console.log(data);
				}

			});

		} else if (id == "acc_expenses") {
			$("#myGrid2").show().empty();
			var gridDiv = document.querySelector('#myGrid2');
			new agGrid.Grid(gridDiv, gridOptions2);
			$.ajax({
				type: "GET",
				url: "dashboard-account-getAllReport",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					if (response.code === "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.AllData;
						// Clear the existing data in the grid

						gridOptions2.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions2.api.setRowData([]);
						} else {

							gridOptions2.api.setRowData(allData);

						}
					}

					$("#expense").show();
					$("#myGrid").hide().empty();
					$("#payableListH6").hide();
					$("#productTestedTableass").hide();
					$("#myGrid1").hide().empty();
					$("#receivableListH6").hide();
					$("#myGrid4").hide().empty();
					$("#gstinSal").hide();
					$("#myGrid3").hide().empty();
					$("#purchaseGs").hide();
					$("#myGrid5").hide().empty();
					$("#incomeTax").hide();

				},
				error: function(data) {
					console.log(data);
				}

			});

		} else if (id == "acc_purchase_gstin") {
			$("#myGrid3").show().empty();
			var gridDiv = document.querySelector('#myGrid3');
			new agGrid.Grid(gridDiv, gridOptions3);

			$.ajax({
				type: "GET",
				url: "dashboard-account-getAllReport",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						//console.log('---------', JSON.parse(response.body));
						var jsonData = JSON.parse(response.body);
						console.log("jsonData" + jsonData);
						var allData = jsonData.AllData;

						gridOptions3.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions3.api.setRowData([]);
						} else {

							gridOptions3.api.setRowData(allData);
						}

						$("#purchaseGs").show();
						$("#myGrid").hide().empty();
						$("#payableListH6").hide();
						$("#productTestedTableass").hide();
						$("#myGrid1").hide().empty();
						$("#receivableListH6").hide();
						$("#myGrid2").hide().empty();
						$("#expense").hide();
						$("#myGrid4").hide().empty();
						$("#gstinSal").hide();
						$("#myGrid5").hide().empty();
						$("#incomeTax").hide();

					}
				}, error: function(data) {
					console.log(data);
				}
			});
		} else if (id == "acc_sales_gstin") {
			$("#myGrid4").show().empty();
			var gridDiv = document.querySelector('#myGrid4');
			new agGrid.Grid(gridDiv, gridOptions4);
			$.ajax({
				type: "GET",
				url: "dashboard-account-getAllReport",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						//console.log('---------', JSON.parse(response.body));
						var jsonData = JSON.parse(response.body);
						console.log("jsonData" + jsonData);
						var allData = jsonData.AllData;

						gridOptions4.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions4.api.setRowData([]);
						} else {

							gridOptions4.api.setRowData(allData);
						}

						$("#gstinSal").show();
						$("#myGrid3").hide().empty();
						$("#purchaseGs").hide();
						$("#myGrid").hide().empty();
						$("#payableListH6").hide();
						$("#productTestedTableass").hide();
						$("#myGrid1").hide().empty();
						$("#receivableListH6").hide();
						$("#myGrid2").hide().empty();
						$("#expense").hide();
						$("#myGrid5").hide().empty();
						$("#incomeTax").hide();
					}
				}, error: function(data) {
					console.log(data);
				}
			});
		}
		else if (id == "acc_incometax") {
			$("#myGrid5").show().empty();
			var gridDiv = document.querySelector('#myGrid5');
			new agGrid.Grid(gridDiv, gridOptions5);
			$.ajax({
				type: "GET",
				url: "dashboard-account-getAllReport",
				data: {
					id: id,
					fromDate: fromDate,
					toDate: toDate,
					org: org,
					orgDiv: orgDiv,
					loc: loc
				},
				async: true,
				success: function(response) {
					//console.log(response);
					if (response.code == "success") {
						//console.log('---------', JSON.parse(response.body));
						var jsonData = JSON.parse(response.body);
						console.log("jsonData" + jsonData);
						var allData = jsonData.AllData;

						//gridOptions4.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions5.api.setRowData([]);
						} else {

							gridOptions5.api.setRowData(allData);
						}


						$("#incomeTax").show();
						$("#myGrid4").hide().empty();
						$("#gstinSal").hide();
						$("#myGrid3").hide().empty();
						$("#purchaseGs").hide();
						$("#myGrid").hide().empty();
						$("#payableListH6").hide();
						$("#productTestedTableass").hide();
						$("#myGrid1").hide().empty();
						$("#receivableListH6").hide();
						$("#myGrid2").hide().empty();
						$("#expense").hide();
					}
				}, error: function(data) {
					console.log(data);
				}
			});
		}

		$("#delete").attr("disabled", true);
	});



	const columnDefs = [
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
			headerName: 'Vendor Name',
			field: 'customer_name',
			width: 250,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Purchase Order',
			field: 'po_id',
			width: 150,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Invoice No',
			field: 'invoice_id',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Invoice Amount',
			field: 'total_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2) // Format value to display floating point numbers
		},
		{
			headerName: 'Paid Amount',
			field: 'total_paid_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Payable Amount',
			field: 'total_outstanding_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Invoice Date',
			field: 'invoice_date',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Due Date',
			field: 'due_date',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},


	];

	const gridOptions = {
		columnDefs: columnDefs,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 187,
			height: 10
		},
		rowSelection: 'multiple',
		suppressRowClickSelection: true,
		onSelectionChanged: rowSelect,
		getRowNodeId: function(data) {
			return data.bankId;
		}
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
			headerName: 'Customer Name',
			field: 'customer_name',
			width: 250,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Sale Order Id',
			field: 'sale_order',
			width: 150,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Invoice Id'
			, field: 'invoice_id',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Invoice Amount',
			field: 'total_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Received Amount',
			field: 'total_paid_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Receivable Amount',
			field: 'total_outstanding_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Invoice Date',
			field: 'invoice_date',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Due Date',
			field: 'due_date',
			width: 150,
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
		onSelectionChanged: rowSelect,
		getRowNodeId: function(data) {
			return data.invoice_id; // Ensure a unique field is used for the row node ID
		}
	};
	function rowSelect() {
		var selectedRows = gridOptions1.api.getSelectedRows();
		var deleteId = selectedRows.map(row => row.invoice_id).join(',');
		console.log(deleteId);

		var rowCount = selectedRows.length;

		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		} else {
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", false);
		}
	}



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
			headerName: 'Customer',
			field: 'customer_name',
			width: 250,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Purchase Order',
			field: 'po_id',
			width: 150,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Invoice No'
			, field: 'invoice_id',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'Expense Amount',
			field: 'total_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Invoice Date',
			field: 'invoice_date',
			width: 150,
			cellStyle: { textAlign: 'left' }
		}, {
			headerName: 'Due Date',
			field: 'due_date',
			width: 150,
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
		onSelectionChanged: rowSelect2,
		getRowNodeId: function(data) {
			return data.invoice_id1; // Ensure a unique field is used for the row node ID
		}
	};

	function rowSelect2() {

		var selectedRows = gridOptions2.api.getSelectedRows();
		var deleteId = selectedRows.map(row => row.invoice_id1).join(',');
		console.log(deleteId);

		var rowCount = selectedRows.length;

		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		} else {
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", false);
		}
	}


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
			headerName: 'Vendor Name',
			field: 'vendor_name',
			width: 250,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Invoice Id',
			field: 'invoice_id',
			width: 150,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Taxable Amount'
			, field: 'taxable_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'CGST Amount',
			field: 'cgst_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'SGST Amount',
			field: 'sgst_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'IGST Amount',
			field: 'igst_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'TOTAL Amount',
			field: 'total_invoice_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'GSTIN(Received)',
			field: 'receivedGstIn_Id',
			width: 150,
			cellStyle: { textAlign: 'left' }

		},
		,
		{
			headerName: 'GSTIN(Payment)',
			field: 'paymentGstIn_Id',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		,
		{
			headerName: 'Invoice Date',
			field: 'invoice_date',
			width: 150,
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
		onSelectionChanged: rowSelect3,
		getRowNodeId: function(data) {
			return data.invoice_id3; // Ensure a unique field is used for the row node ID
		}
	};

	function rowSelect3() {

		var selectedRows = gridOptions3.api.getSelectedRows();
		var deleteId = selectedRows.map(row => row.invoice_id3).join(',');
		console.log(deleteId);

		var rowCount = selectedRows.length;

		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		} else {
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", false);
		}
	}




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
			headerName: 'Customer Name',
			field: 'customer_name',
			width: 250,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Invoice Id',
			field: 'invoice_id',
			width: 150,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Taxable Amount',
			field: 'taxable_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'CGST Amount',
			field: 'cgst_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'SGST Amount',
			field: 'sgst_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'IGST Amount',
			field: 'igst_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'TOTAL Amount',
			field: 'total_invoice_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'GSTIN(Received)',
			field: 'receivedGstIn_Id',
			width: 150,
			cellStyle: { textAlign: 'left' }

		},
		,
		{
			headerName: 'GSTIN(Payment)',
			field: 'paymentGstIn_Id',
			width: 150,
			cellStyle: { textAlign: 'left' }

		},
		,
		{
			headerName: 'Invoice Date',
			field: 'invoice_date',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},

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
		onSelectionChanged: rowSelect4,
		getRowNodeId: function(data) {
			return data.invoice_id4; // Ensure a unique field is used for the row node ID
		}
	};

	function rowSelect4() {

		var selectedRows = gridOptions4.api.getSelectedRows();
		var deleteId = selectedRows.map(row => row.invoice_id4).join(',');
		console.log(deleteId);

		var rowCount = selectedRows.length;

		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		} else {
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", false);
		}
	}


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
			headerName: 'Customer/Vendor Name',
			field: 'customer_vendor_name',
			width: 250,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Invoice Id',
			field: 'invoice_id',
			width: 150,
			pinned: 'left',
			cellStyle: { textAlign: 'left' }

		},
		{
			headerName: 'Invoice Date'
			, field: 'invoice_date',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
		{
			headerName: 'TotalAmount',
			field: 'total_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'TDS Amount',
			field: 'tds_value_amount',
			width: 150,
			cellStyle: { textAlign: 'right' }, // Align content to the left
			valueFormatter: params => parseFloat(params.value).toFixed(2)
		},
		{
			headerName: 'Record Type',
			field: 'record_type',
			width: 150,
			cellStyle: { textAlign: 'left' }
		},
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
		onSelectionChanged: rowSelect5,
		getRowNodeId: function(data) {
			return data.invoice_id5; // Ensure a unique field is used for the row node ID
		}
	};

	function rowSelect5() {

		var selectedRows = gridOptions5.api.getSelectedRows();
		var deleteId = selectedRows.map(row => row.invoice_id5).join(',');
		console.log(deleteId);

		var rowCount = selectedRows.length;

		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		} else {
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", false);
		}
	}

	var deleteId = "";
	function rowSelect() {
		var selectedRows = gridOptions.api.getSelectedRows();
		deleteId = "";

		for (var i = 0; i < selectedRows.length; i++) {
			deleteId = deleteId + '"' + selectedRows[i].bankId + '",';
		}
		deleteId = deleteId.substring(0, deleteId.length - 1);
		console.log(deleteId);

		var rowCount = selectedRows.length;

		if (rowCount > 0) {
			$('#delete').attr("disabled", false);
			$('#add').attr("disabled", true);
		} else {
			$('#delete').attr("disabled", true);
			$('#add').attr("disabled", false);
		}
	}


}

/*For search button*/
function getOperationalCountWithPayables() {

	getAllReport(storedId);
	projectAccountData();
}

/*For reset button*/
function resetOperation() {
	var today = new Date();
	if (today.getMonth() < 2
		|| (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}
	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
		+ fromYear;
	var toDate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();
	$("#fromDate").val(fromDate);
	$("#toDate").val(toDate);

	getAllReport(storedId);
	projectAccountData();
}


/*For onchange of org & div */

function getAccOperData() {
	getAllReport(storedId);

	projectAccountData();
}

function getAccOperOrgDivData() {
	getAllReport(storedId);


	projectAccountData();
}
