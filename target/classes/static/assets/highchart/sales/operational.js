function operationalHighChart(){
	getOperationalCount();	
	getAllInvoice('rec_inv');
}

    

function getAllInvoice(id){
	var org = $("#oprSalesOrganization").find('option:selected').text();
	var orgDiv = $("#oprSalesDivision").find('option:selected').text();
	var loc = $("#operationalHrmsLocation").find('option:selected').text();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val(); 
	
	if (id === undefined || id === null) {
        id = $("#tabIdVal").text();
    }
	//alert("id-----------"+id);
	
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
   		var toDateFinancialYear = ('0' + 31).slice(-2) + '-'
   				+ ('0' + 3).slice(-2) + '-' + (fromYear + 1);
   	     toDate = ('0' + today.getDate()).slice(-2) + '-'
   				+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
   				+ today.getFullYear();
   					
	}
	
	
	 $.ajax({
        type: "GET",
        url: "customer-dashboard-getAllInvoice",
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
            console.log(response);
            if (response.code == "success") {
                console.log('---------', JSON.parse(response.body));
                var jsonData = JSON.parse(response.body);
                var allData = jsonData.AllData;
		     	    
                if(id=="rec_inv"){          
					$("#recurringInvoice").hide().empty();
					$("#recurrInvoice").hide();                    	
                	var gridDiv = document.querySelector('#recurringInvoice');
        			new agGrid.Grid(gridDiv, gridOptions6);
                	
						// Clear the existing data in the grid
						gridOptions6.api.setRowData([])
						if (!allData || allData === null) {

							gridOptions6.api.setRowData([]);
						} else {

							gridOptions6.api.setRowData(allData);
							
						}
        			
        			$("#recurringInvoice").show();
					$("#recurrInvoice").show();
					
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#invoiceTable").hide().empty();
					$("#invoiTable").hide();
					$("#deliveryChallans").hide().empty();
					$("#deliveryChallan").hide();
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#receiveds").hide().empty();
					$("#received").hide();
					$("#outststanInvloice").hide().empty();
					$("#outstInvloice").hide();
					
				}
				
				if(id=="del_cha"){
					$("#deliveryChallans").hide().empty();
					$("#recurrInvoice").hide();		      
                	$("#deliveryChallan").hide();
                	$("#invoiTable").hide();
                	$("#perfInvoice").hide();
                	$("#received").hide();
                	$("#outstInvloice").hide();
                	
                	var gridDiv = document.querySelector('#deliveryChallans');
        			new agGrid.Grid(gridDiv, gridOptions1);
                	
						if (!allData || allData === null) {

							gridOptions1.api.setRowData([]);
						} else {

							gridOptions1.api.setRowData(allData);
							
						}
        			
        			$("#deliveryChallans").show();
					$("#deliveryChallan").show();

					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#invoiceTable").hide().empty();
					$("#invoiTable").hide();
					$("#recurringInvoice").hide().empty();
					$("#recurrInvoice").hide();
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#receiveds").hide().empty();
					$("#received").hide();
					$("#outststanInvloice").hide().empty();
					$("#outstInvloice").hide();
					
				}
				
				if(id=="all_inv"){
					$("#invoiceTable").hide().empty();
					$("#recurrInvoice").hide();		      
                	$("#deliveryChallan").hide();
                	$("#invoiTable").hide();
                	$("#perfInvoice").hide();
                	$("#received").hide();
                	$("#outstInvloice").hide();
					
                	var gridDiv = document.querySelector('#invoiceTable');
        			new agGrid.Grid(gridDiv, gridOptions2);
                	
						// Clear the existing data in the grid
						gridOptions2.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions2.api.setRowData([]);
						} else {

							gridOptions2.api.setRowData(allData);
							
						}
        			
        			$("#invoiceTable").show();
					$("#invoiTable").show();
					
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#recurringInvoice").hide().empty();
					$("#recurrInvoice").hide();
					$("#deliveryChallans").hide().empty();
					$("#deliveryChallan").hide();
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#outststanInvloice").hide().empty();
					$("#outstInvloice").hide();
					
					$("#receiveds").hide().empty();
					$("#received").hide();
					
				}
				
				if(id=="per_inv"){
										
					$("#proforInvoice").hide().empty();
					$("#recurrInvoice").hide();		      
                	$("#deliveryChallan").hide();
                	$("#invoiTable").hide();
                	$("#perfInvoice").hide();
                	$("#received").hide();
                	$("#outstInvloice").hide();
                	
                	var gridDiv = document.querySelector('#proforInvoice');
        			new agGrid.Grid(gridDiv, gridOptions3);
                		if (!allData || allData === null) {

							gridOptions3.api.setRowData([]);
						} else {

							gridOptions3.api.setRowData(allData);
							
						}
        			
        			$("#proforInvoice").show();
					$("#perfInvoice").show();
					$("#recurringInvoice").hide().empty();
					$("#recurrInvoice").hide();
					$("#deliveryChallans").hide().empty();
					$("#deliveryChallan").hide();
					$("#invoiceTable").hide().empty();
					$("#invoiTable").hide();
					$("#receiveds").hide().empty();
					$("#received").hide();
					$("#outststanInvloice").hide().empty();
					$("#outstInvloice").hide();
					
				}
				
				if(id=="received_inv"){
					$("#receiveds").hide().empty();
					$("#recurrInvoice").hide();		      
                	$("#deliveryChallan").hide();
                	$("#invoiTable").hide();
                	$("#perfInvoice").hide();
                	//$("#received").hide();
                	$("#outstInvloice").hide();
                	
                	var gridDiv = document.querySelector('#receiveds');
        			new agGrid.Grid(gridDiv, gridOptions4);
        				if (!allData || allData === null) {

							gridOptions4.api.setRowData([]);
							
						} else {

							gridOptions4.api.setRowData(allData);
							
						}
        			
        			
					$("#received").show();
					$("#receiveds").show();
					
					$("#recurringInvoice").hide().empty();
					$("#recurrInvoice").hide();
					$("#deliveryChallans").hide().empty();
					$("#deliveryChallan").hide();
					$("#invoiceTable").hide().empty();
					$("#invoiTable").hide();
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
					$("#outststanInvloice").hide().empty();
					$("#outstInvloice").hide();
					
				}
				
				if(id=="out_stand"){
					
					$("#outststanInvloice").hide().empty();
					$("#recurrInvoice").hide();		      
                	$("#deliveryChallan").hide();
                	$("#invoiTable").hide();
                	$("#perfInvoice").hide();
                	$("#received").hide();
                	$("#outstInvloice").hide();
                	
                	var gridDiv = document.querySelector('#outststanInvloice');
        			new agGrid.Grid(gridDiv, gridOptions5);
                	
        			
						if (!allData || allData === null) {

							gridOptions5.api.setRowData([]);
						} else {

							gridOptions5.api.setRowData(allData);
							
						}
        			
        			$("#outststanInvloice").show();
					$("#outstInvloice").show();
					
					$("#receiveds").hide().empty();
					$("#received").hide();
					
					$("#recurringInvoice").hide().empty();
					$("#recurrInvoice").hide();
					$("#deliveryChallans").hide().empty();
					$("#deliveryChallan").hide();
					$("#invoiceTable").hide().empty();
					$("#invoiTable").hide();
					$("#proforInvoice").hide().empty();
					$("#perfInvoice").hide();
				}
				
				if(allData[0].invoice=="rec_inv"){

                	var gridDiv = document.querySelector('#recurringInvoice');
        			new agGrid.Grid(gridDiv, gridOptions6);
                	
        			
						// Clear the existing data in the grid
						gridOptions6.api.setRowData([])

						if (!allData || allData === null) {

							gridOptions6.api.setRowData([]);
						} else {

							gridOptions6.api.setRowData(allData);
						
						}
						
					} else{
						//quoatations();
					}  
            }
        },
        error: function(data) {
            console.log(data);
        }
    });
}

 function getOperationalCount(){	
	var org = $("#oprSalesOrganization").find('option:selected').text();
	var orgDiv = $("#oprSalesDivision").find('option:selected').text();
	var loc = $("#operationalHrmsLocation").find('option:selected').text();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val(); 
	
	
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
   		var toDateFinancialYear = ('0' + 31).slice(-2) + '-'
   				+ ('0' + 3).slice(-2) + '-' + (fromYear + 1);
   	     toDate = ('0' + today.getDate()).slice(-2) + '-'
   				+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
   				+ today.getFullYear();
   					
	}
	$.ajax({
		type	: "GET",				
			url : "customer-dashboard-oprationalHeadData",
			data: {
				fromDate: fromDate,
				toDate: toDate,
				org: org,
				orgDiv: orgDiv,
				loc: loc			
			},
			async:true,
			success	: function(response){
				console.log(response);
				if(response.code=="success"){
					var jsonData = JSON.parse(response.body);
					var allData=jsonData.AllData;
							
					$("#recuring_invoice").text(allData[0].recuring_invoice);			
					$("#performance_invoice").text(allData[0].performance_invoice);                          
					$("#delivery_challan").text(allData[0].delivery_challan);
					$("#total_invoice").text(allData[0].total_invoice);
					$("#total_received").text(allData[0].received_invoice);
					$("#total_Outstanding").text(allData[0].outstanding);
					$("#total_cust").text(allData[0].total_cust);
					if (allData[0].valueInvoiced !== null) {
					    $("#valueInvoiced").text(allData[0].valueInvoiced.toFixed(2));
					}else{
						$("#valueInvoiced").text("0.00");
					}
					$("#valueReceived").text(allData[0].valueReceived.toFixed(2));
					$("#valueOutstanding").text(allData[0].valueOutstanding.toFixed(2));

					$("#valueperformanceIn").text(allData[0].valueperformanceIn);
					if (allData[0].valueInvoiced !== null) {
					    $("#valueDelivery").text(allData[0].valueDelivery.toFixed(2));
					}else{
						$("#valueDelivery").text("0.00");
					}						
					 getAllInvoice(tabId);
					
				}
		},error	: function(data){
			console.log(data);	
		}
	})
 }
 
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
					headerName: 'Customer Name',
					field: 'customer_name',
					width: 250,
					cellStyle: { textAlign: 'left' },
					pinned: 'left',
				},
				{
					headerName: 'PO Id',
					field: 'po_id',
					width: 200,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'

				}
				,
				{
					headerName: 'Shipment Date',
					field: 'shippment_date',
					cellStyle: { textAlign: 'left' },
					width: 190
				},
				{
					headerName: 'Amount',
					field: 'total_amount',
					width: 190,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				/*{
					headerName: 'Shipment Status',
					field: 'Order_status',
					width: 190,
					cellStyle: { textAlign: 'left' }
				},*/
				{
					headerName: 'Invoice Status',
					field: 'Order_status',
					width: 190,
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
				onSelectionChanged: rowSelect6,
				getRowNodeId: function(data) {
					return data.bankId6;
				}
			};


			function rowSelect6() {
				var selectedRows = gridOptions6.api.getSelectedRows();
				var deleteId = selectedRows.map(row => row.bankId6).join(',');
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
					cellStyle: { textAlign: 'left' },
					pinned: 'left'
				},
				{
					headerName: 'PO Id',
					field: 'challan_id',
					cellStyle: { textAlign: 'left' },
					width: 200,
					pinned: 'left'
				},
				{
					headerName: 'Shipment Date'
					, field: 'challan_date',
					cellStyle: { textAlign: 'left' },
					width: 190
				},
				{
					headerName: 'Amount',
					field: 'total_amount',
					width: 190,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)

				},
				{
					headerName: 'Order Status',
					field: 'Order_status',
					width: 190,
					cellStyle: { textAlign: 'left' }, 
					
				}
				
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
					return data.invoice_id; 
				}
			};
			function rowSelect() {
				var selectedRows = gridOptions.api.getSelectedRows();
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
					headerName: 'Customer Name',
					field: 'customer_name',
					width: 250,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'

				},
				{
					headerName: 'PO Id',
					field: 'inv_id',
					width: 200,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'

				},
				{
					headerName: 'Invoice Date'
					, field: 'invoice_date',
					cellStyle: { textAlign: 'left' },
					width: 190
				},
				{
					headerName: 'Due Date',
					field: 'due_date',
					cellStyle: { textAlign: 'left' },
					width: 190,

				},
				{
					headerName: 'Amount',
					field: 'total_amount',
					cellStyle: { textAlign: 'right' }, 
					valueFormatter: params => parseFloat(params.value).toFixed(2),
					width: 190
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
					return data.invoice_id1; 
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
					headerName: 'Customer Name',
					field: 'customer_name',
					width: 250,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'
				},
				{
					headerName: 'Invoice Id',
					field: 'inv_id',
					cellStyle: { textAlign: 'left' },
					width: 200,
					pinned: 'left'
				},
				{
					headerName: 'Invoice Date'
					, field: 'invoice_date',
					cellStyle: { textAlign: 'left' },
					width: 150
				},
				{
					headerName: 'Due Date',
					field: 'due_date',
					width: 150,
					cellStyle: { textAlign: 'left' }

				},
				{
					headerName: 'Total Amount',
					field: 'total_amount',
					width: 150,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				{
					headerName: 'Invoicing',
					field: 'invoicing',
					//field: 'invoice',
					width: 150,
					cellStyle: { textAlign: 'left' }, 
					
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
					return data.invoice_id3; 
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
					headerName: 'Payment ID',
					field: 'payment_id',
					width: 250,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'

				},
				{
					headerName: 'Invoice ID',
					field: 'invoice_id',
					width: 200,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'

				},
				{
					headerName: 'PO ID'
					, field: 'po_id',
					width: 150,
					cellStyle: { textAlign: 'left' },
				},
				{
					headerName: 'Total Amount',
					field: 'total_amount',
					width: 150,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				{
					headerName: 'Paid Amount',
					field: 'paid_amount',
					width: 150,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				{
					headerName: 'Payment Type',
					field: 'payment_type',
					width: 150,
					cellStyle: { textAlign: 'left' }, 
					
				},
				{
					headerName: 'Payment Date',
					field: 'payment_date',
					width: 150,
					cellStyle: { textAlign: 'left' },
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
					return data.invoice_id4; 
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
					headerName: 'Invoice Id',
					field: 'invoice_id',
					width: 250,
					pinned: 'left',
					cellStyle: { textAlign: 'left' },

				},
				{
					headerName: 'PO ID',
					field: 'po_id',
					width: 200,
					cellStyle: { textAlign: 'left' },
					pinned: 'left'

				},
				{
					headerName: 'Customer Name'
					, field: 'customerName',
					cellStyle: { textAlign: 'left' },
					width: 180
				},
				{
					headerName: 'Total Amount',
					field: 'total_amount',
					width: 180,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				{
					headerName: 'Total Paid',
					field: 'paid_amount',
					width: 180,
					cellStyle: { textAlign: 'right' },
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				{
					headerName: 'Total Outstanding',
					field: 'outstanding_amount',
					width: 180,
					cellStyle: { textAlign: 'right' }, 
					valueFormatter: params => parseFloat(params.value).toFixed(2)
				},
				{
					headerName: 'Due Date',
					field: 'due_date',
					cellStyle: { textAlign: 'left' },
					width: 180
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
					return data.invoice_id5; 
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
 