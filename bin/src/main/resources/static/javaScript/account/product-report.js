	$(document)
			.ready(
					function() {
						

						var dateFormat = localStorage.getItem("dateFormat");
						$("#fromDateCalender").datetimepicker({
							format : dateFormat,
							closeOnDateSelect : true,
							timepicker : false,
						}).on("change", function() {
							$('#filterFromDate').val($(this).val());
						})

						$('#filterFromDate').blur(function() {
							$("#fromDateCalender").val($(this).val());
						})

						$("#toDateCalendar").datetimepicker({
							format : dateFormat,
							closeOnDateSelect : true,
							timepicker : false,
						}).on("change", function() {
							$('#filterToDate').val($(this).val());
						})

						$('#filterToDate').blur(function() {
							$("#toDateCalendar").val($(this).val());
						})

						var toDayDate = (new Date()).toISOString().split('T')[0];
						var newDate = changeDateFormat(toDayDate);

						$("#filterFromDate").val(newDate);
						$("#filterToDate").val(newDate);

						$("#fromDateCalender").val(newDate);
						$("#toDateCalendar").val(newDate);
						
						var today = new Date();
						var fromDateString  = ('01').slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
						var toDateString  = ('0' + today.getDate()).slice(-2) + '-' + ('0' + (today.getMonth() + 1)).slice(-2) + '-' + today.getFullYear();
						var fromDate = fromDateString .toString();
						var todate = toDateString .toString();
							
						$("#filterToDate").val(todate);
						$("#filterFromDate").val(fromDate);

						var gridDiv = document.querySelector('#myGrid');
						new agGrid.Grid(gridDiv, gridOptions);

						var gridDiv1 = document.querySelector('#myGrid1');
						new agGrid.Grid(gridDiv1, gridOptions1);

						var rowData = [];
						gridOptions.api.setRowData(rowData);
						gridOptions1.api.setRowData(rowData);
						//activityOptions.api.setRowData(rowData);
						viewPurchaseDetails();
						

						$("#currsec").addClass("active");
						 getCategoryList();
					});

	const gridOptions = {
		    columnDefs: [
		        {
		            headerCheckboxSelection: false,
		            headerCheckboxSelectionFilteredOnly: false,
		            checkboxSelection: true,
		            width: 10,
		            pinned: 'left',
		            sortable: true,
		            filter: true,
		            resizable: true
		        },
		        {
		            headerName: 'INVOICE ID',
		            field: 'purchaseInvoiceId',
		            width: 120,
		            /* pinned: 'left', */
		            resizable: true,
		            sortable: true,  
		            cellRenderer: function(params) {
		                return '<a onclick=getSaleDetails("' + params.data.purchaseInvoiceId + ',' + '0' + '") href="javascript:void(0)">' + params.data.purchaseInvoiceId + '</a>';
		            }
		        },
		        {
		            headerName: 'GRN ID',
		            field: 'purchaseGrnId',
		            resizable: true,
		            sortable: true,
		            width:150,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'INVOICE DATE',
		            field: 'purchaseInvoiceDate',
		            resizable: true,
		            sortable: true,
		            width:120,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'VENDOR NAME',
		            field: 'purchaseVendorName',
		            resizable: true,
		            sortable: true,
		            width:250,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'SKU',
		            field: 'purchaseSkuId',
		            resizable: true,
		            sortable: true,
		            width:100,
		            cellStyle: { textAlign: 'left' }
		        },
		        
		        {
		            headerName: 'HSN Code',
		            field: 'purchaseHsnId',
		            resizable: true,
		            sortable: true,
		            width:100,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'Item Name',
		            field: 'purchaseItemName',
		            resizable: true,
		            sortable: true,
		            width:280,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'TYPE',
		            field: 'purchaseType',
		            resizable: true,
		            sortable: true,
		            width:100,
		            cellStyle: { textAlign: 'left' }
		        },
		        
		        {
		            headerName: 'Quantity&Unit',
		            field: 'purchaseQuantity',
		            resizable: true,
		            sortable: true, 
		            width:100,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return valueFormatForQN(value);
				    }
		        },
		        {
		            headerName: 'GST Rate',
		            field: 'purchaseGst',
		            width:100,
		            resizable: true,
		            sortable: true,
		            cellStyle: { textAlign: 'right' }
		        },
		        {
		            headerName: 'CGST',
		            field: 'purchaseCgst',
		            width:100,
		            resizable: true,
		            sortable: true,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'IGST',
		            field: 'purchaseIgst',
		            width:100,
		            resizable: true,
		            sortable: true,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'SGST',
		            field: 'purchaseSgst',
		            width:100,
		            resizable: true,
		            sortable: true,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'Taxable Amount',
		            field: 'purchaseTaxableAmount',
		            resizable: true,
		            width:150,
		            sortable: true, 
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        
		        
		        {
		            headerName: 'Total Amount',
		            field: 'purchaseTotalAmount',
		            resizable: true,
		            width:150,
		            sortable: true,  
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        }
		    ],
		    defaultColDef: {
		        sortable: true,  
		        filter: true,
		        resizable: true,
		        width: 200
		    },
		    rowSelection: 'single',
		    suppressRowClickSelection: true,
		    onSelectionChanged: rowSelect,
		    getRowNodeId: function(data) {
		        return data.purchaseInvoiceId;
		    }
		};



	const gridOptions1 = {
		    columnDefs: [
		        {
		            headerCheckboxSelection: false,
		            headerCheckboxSelectionFilteredOnly: false,
		            checkboxSelection: true,
		            width: 10,
		            pinned: 'left',
		            sortable: false,
		            filter: false,
		            resizable: true
		        },
		        {
		        	  headerName: 'INVOICE ID',
		        	  field: 'salesInvoiceId',
		        	  width: 200,
		        	  /* pinned: 'left', */
		        	  resizable: true,
		        	  cellRenderer : function(params) {
							return '<a onclick=getSaleDetails("' + params.data.salesInvoiceId
									+ ',' + '0' + '") href="javascript:void(0)">'
									+ params.data.salesInvoiceId + '</a>';
						}
		        	},
		        	{
			            headerName: 'SAP ID',
			            field: 'salesSapId',
			            resizable: true,
			            width: 130,
			            cellStyle: { textAlign: 'left' }
			        },
                   {
		            headerName: 'INVOICE DATE',
		            field: 'salesInvoiceDate',
		            resizable: true,
		            width: 130,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'CUSTOMER NAME',
		            field: 'customerName',
		            resizable: true,
		            width: 240,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'SKU',
		            field: 'salesSkuId',
		            resizable: true,
		            width: 100,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'HSN',
		            field: 'salesHsnId',
		            resizable: true,
		            width: 100,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'Item Name',
		            field: 'salesItemName',
		            resizable: true,
		            width: 270,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'TYPE',
		            field: 'salesType',
		            resizable: true,
		            width: 100,
		            cellStyle: { textAlign: 'left' }
		        },
		        {
		            headerName: 'Quantity&Unit',
		            field: 'salesQuantity',
		            resizable: true,
		            width: 120,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return valueFormatForQN(value);
				    }
		        },
		         
		        {
		            headerName: 'GST Rate',
		            field: 'salesGst',
		            width: 100,
		            resizable: true,
		            cellStyle: { textAlign: 'right' }
		        },
		        {
		            headerName: 'CGST',
		            field: 'salesCgst',
		            resizable: true,
		            width: 120,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'SGST',
		            field: 'salesSgst',
		            resizable: true,
		            width: 120,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'IGST',
		            field: 'salesIgst',
		            resizable: true,
		            width: 120,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'Taxable Amount',
		            field: 'salesTaxableAmount',
		            resizable: true,
		            width: 150,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        },
		        {
		            headerName: 'Total Amount',
		            field: 'salesTotalAmount',
		            resizable: true,
		            width: 150,
		            cellStyle: { textAlign: 'right' },
		            cellRenderer: function(params) {
				        var value = params.value;
				        return amountFormatter(value);
				    }
		        }
		    ],
		    defaultColDef: {
		        sortable: true,
		        filter: true,
		        resizable: true,
		        width: 200
		    },
		    rowSelection: 'single',
		    suppressRowClickSelection: true,
		    onSelectionChanged: rowSelect,
		    getRowNodeId: function(data) {
		        return data.salesSkuId;
		    }
		};

		 
		function rowSelect() {
		    var selectedNodes = gridOptions1.api.getSelectedNodes();
		    var selectedData = selectedNodes.map(node => node.data);
		     
		}

			
	
	function onQuickFilterChanged() {
			gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
		var displayedRowCount = gridOptions.api.getDisplayedRowCount();

		var len = displayedRowCount;
		$('#totalReq').find('span').html(len);
		
	}

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";

		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
		}
	}
	function onQuickFilterChanged1() {
		gridOptions1.api
		.setQuickFilter(document.getElementById('quickFilter1').value);	
		
		var displayedRowCount = gridOptions1.api.getDisplayedRowCount();

		var len = displayedRowCount;
		$('#totalReq').find('span').html(len);
		
		
	}

	function cancelBar1() {
		var id = document.getElementById("closeKey1");
		id.style.display = "block";

		if ($('#quickFilter1').val() == null || $('#quickFilter1').val() == "") {
			id.style.display = "none";
		}
	}

	function amountFormatter(value) {
	    if (value !== null && value !== undefined) {
	        var parts = value.toString().split('.');
	        var integerPart = parts[0];
	        var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
	        var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
	        return formattedIntegerPart + decimalPart;
	    } else {
	        return '';
	    }
	}
	
	function valueFormatForQN(value) {
		 
	    if (value !== null && value !== undefined) {
	        var parts = value.toString().split(' ');
	        var numberPart = parts[0];
	        var suffix = parts.length > 1 ? ' ' + parts[1] : '';

	        var numberParts = numberPart.split('.');
	        var integerPart = numberParts[0];
	        var decimalPart = numberParts.length > 1 ? '.' + numberParts[1] : '';

	        var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);

	        return formattedIntegerPart + decimalPart + suffix;
	    } else {
	        return '';
	    }
	}

	/*  function filter() {
		var fromdatex = $("#filterFromDate").val();
		var fd = fromdatex.split("-");
		var todatex = $("#filterToDate").val();
		var td = todatex.split("-");

		if (fromdatex != '' && todatex != '') {
			if (fd[2] == td[2]) {
				if (fd[1] == td[1]) {
					if (fd[0] <= td[0]) {
						//filterData(); 
						viewPurchaseDetails();
					} else {
						setFromToDate();
					}
				} else if (fd[1] < td[1]) {
					//filterData();
					viewPurchaseDetails();
				} else {
					setFromToDate();
				}
			} else if (fd[2] < td[2]) {
				//filterData();
				
				viewPurchaseDetails();
				
			} else {
				setFromToDate();
			}
		}
	} */
	
	
	
	function filter() {
		
		var categoryId = $("#selectedProductCategory").val();
		console.log("categoryiD-->" , categoryId);
		
		if(categoryId != "PCAT000004"){
			viewPurchaseDetails();
		}
		else{
			viewSalesDetails();
		}
		
	}
	function setFromToDate() {
		$("#messageParagraph").text(
				"Please choose to date greater than or equal to from date ");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		var date = (new Date()).toISOString().split('T')[0];
		var newToDate = changeDateFormat(date);
		var newFromDate = changeDateFormat(date);
		$("#filterFromDate").val(newToDate);
		$("#filterToDate").val(newToDate);

		$("#fromDateCalender").val(newDate);
		$("#toDateCalendar").val(newDate);
	}

	function filterData() {
		var len;
		var type = $("#type").val();
		var fromDate = $("#filterFromDate").val();
		var toDate = $("#filterToDate").val();
		var purchaseTypeId=$("#purchaseType").val();
		var salesTypeId=$("#salesType").val();
		var totalIGSTAmount = 0.00;
		var totalCGSTAmount = 0.00;
		var totalSGSTAmount = 0.00;
		var totalPayableAmount = 0.00;
		$(".loader").show();
		
		  $.ajax({                                
		        type: "GET",
		        url: "product-report-getFilterData?type="+ type + "&fromDate=" + fromDate + "&todate=" + toDate + "&purchaseTypeId=" + purchaseTypeId+"&salesTypeId="+salesTypeId,
		        success: function(response) {
		           
		            if (response.code === "success") {
		            	$(".loader").hide();
		             var datas = JSON.parse(response.body);
		             if ( type == "Purchase") {
		            	 var purchaseData= datas.purchaseData
		            	 if(purchaseData == "" || purchaseData == "null" || purchaseData == null){
		 					$('#totalReq').find('span').html('0');
		 					gridOptions.api.setRowData();
		 					$("#totalAmountFooter").val("0.00");
		 					$("#totalIGSTAmount").val("0.00");
		 					$("#totalCGSTAmount").val("0.00");
		 					$("#totalSGSTAmount").val("0.00");
		 					$("#totalPayableAmount").val("0.00");
		 				}else{
		            	  len = purchaseData.length;
		                    $('#totalReq').find('span').html(len);
		                    gridOptions.api.setRowData(purchaseData);

		                    var totalSum = purchaseData.reduce((acc, curr) => acc + parseFloat(curr.purchaseTaxableAmount), 0);

		                    purchaseData.forEach(item => {
		                        totalIGSTAmount += parseFloat(item.purchaseIgst);
		                        totalCGSTAmount += parseFloat(item.purchaseCgst);
		                        totalSGSTAmount += parseFloat(item.purchaseSgst);
		                        totalPayableAmount += parseFloat(item.purchaseTotalAmount);
		                    });

		                    $("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
		                    $("#totalIGSTAmount").val(amountFormatter(totalIGSTAmount.toFixed(2)));
		                    $("#totalCGSTAmount").val(amountFormatter(totalCGSTAmount.toFixed(2)));
		                    $("#totalSGSTAmount").val(amountFormatter(totalSGSTAmount.toFixed(2)));
		                    $("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
		                }
		             }
		             
		             else if( type == "Sales"){
		            	 var salesData= datas.salesData 
		            	 if(salesData == "" || salesData == "null" || salesData == null){
			 					$('#totalReq').find('span').html('0');
			 					gridOptions1.api.setRowData();
			 					$("#totalAmountFooter").val("0.00");
			 					$("#totalIGSTAmount").val("0.00");
			 					$("#totalCGSTAmount").val("0.00");
			 					$("#totalSGSTAmount").val("0.00");
			 					$("#totalPayableAmount").val("0.00");
			 				}
		            	 else{
		            	 len = salesData.length;
		            	 $('#totalReq').find('span').html(len);
		            	 gridOptions1.api.setRowData(salesData);
		            	 var totalSum = salesData.reduce((acc, curr) => acc + parseFloat(curr.salesTaxableAmount), 0);
		            	 
		            	 salesData.forEach(item => {
		                        totalIGSTAmount += parseFloat(item.salesIgst);
		                        totalCGSTAmount += parseFloat(item.salesCgst);
		                        totalSGSTAmount += parseFloat(item.salesSgst);
		                        totalPayableAmount += parseFloat(item.salesTotalAmount);
		                    });
		            	    $("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
		                    $("#totalIGSTAmount").val(amountFormatter(totalIGSTAmount.toFixed(2)));
		                    $("#totalCGSTAmount").val(amountFormatter(totalCGSTAmount.toFixed(2)));
		                    $("#totalSGSTAmount").val(amountFormatter(totalSGSTAmount.toFixed(2)));
		                    $("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
		                } 
		             }
		             
		            } else {
		            	$(".loader").hide();
		                console.error("Failed to retrieve purchase data:", response.message);
		            }
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		        	$(".loader").hide();
		            console.error("AJAX request failed:", textStatus, errorThrown);
		        }
		    });
		
	}

	function changeDateFormat(inputDate) {  
		var splitDate = inputDate.split('-');
		if (splitDate.count == 0) {
			return null;
		}
		var year = splitDate[0];
		var month = splitDate[1];
		var day = splitDate[2];

		return day + '-' + month + '-' + year;
	}

	function viewPurchaseDetails() {
		$("#purchaseGrid").show();
		console.log("Calling viewPurchaseDetails()");
		$("#purchaseSection").show();
		$("#salesSection").hide();
	    $("#salesRegisterExcel").hide();
	    $("#purchaseRegisterExcel").show();
	    $("#purchaseDetails").addClass("active");
	    $("#salesDetails").removeClass("active");
	    $("#myGrid1").hide();
	    $("#myGrid").show();
	    $("#type").val($("#purchaseDetails").val());
	    $("#searchRowDiv").show();
	    $("#searchRowDiv1").hide();
	  //  var purchaseTypeId=$("#purchaseType").val();
	    var purchaseTypeId= "";
	    var fromDate = $("#filterFromDate").val();
	    var toDate = $("#filterToDate").val();
	    var productCategory = $("#selectedProductCategory").val();
	    var totalIGSTAmount = 0.00;
	    var totalCGSTAmount = 0.00;
	    var totalSGSTAmount = 0.00;
	    var totalPayableAmount = 0.00;

	    $(".loader").show();

	    $.ajax({
	        type: "GET",
	        url: "product-report-getPurchaseData?fromDate=" + fromDate + "&toDate=" + toDate +"&purchaseTypeId="+purchaseTypeId+"&productCategory="+productCategory ,
	        success: function(response) {
	            $(".loader").hide();       
	            
	            if (response.code === "success") {
	            	 var datas = JSON.parse(response.body);
		             var purchaseData = datas.purchaseData;
		             
		             if(purchaseData == null || purchaseData == "null"){
		            	gridOptions.api.setRowData([]);
	            		$("#totalAmountFooter").val('');
	 	                $("#totalIGSTAmount").val('');
	 	                $("#totalCGSTAmount").val('');
	 	                $("#totalSGSTAmount").val('');
	 	                $("#totalPayableAmount").val('');
	 	               $('#totalReq').find('span').html('0');
		             }
		             else{
		            	 var len = purchaseData.length;
			                $('#totalReq').find('span').html(len);
			                gridOptions.api.setRowData(purchaseData);

			                var totalSum = purchaseData.reduce((acc, curr) => acc + parseFloat(curr.purchaseTaxableAmount), 0);

			                purchaseData.forEach(item => {
			                    totalIGSTAmount += parseFloat(item.purchaseIgst);
			                    totalCGSTAmount += parseFloat(item.purchaseCgst);
			                    totalSGSTAmount += parseFloat(item.purchaseSgst);
			                    totalPayableAmount += parseFloat(item.purchaseTotalAmount);
			                });

			                $("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
			                $("#totalIGSTAmount").val(amountFormatter(totalIGSTAmount.toFixed(2)));
			                $("#totalCGSTAmount").val(amountFormatter(totalCGSTAmount.toFixed(2)));
			                $("#totalSGSTAmount").val(amountFormatter(totalSGSTAmount.toFixed(2)));
			                $("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
			               
			            }
	            }
	            
	            else {
	                console.error("Failed to retrieve purchase data:", response.message);
	            }
	            	
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            $(".loader").hide();
	            console.error("AJAX request failed:", textStatus, errorThrown);
	        }
	    });
	}
	
	function viewSalesDetails() {
		//$("#purchaseGrid").hide();
		console.log("Calling viewSalesDetails()");
	    $("#purchaseSection").hide();
	    $("#salesSection").show();
	    $("#salesRegisterExcel").show();
	    $("#purchaseRegisterExcel").hide();
		 $("#totalAmountFooter").val("");
		 $("#totalIGSTAmount").val("");
		 $("#totalCGSTAmount").val("");
		 $("#totalSGSTAmount").val("");
		 $("#totalPayableAmount").val("");
		$("#salesDetails").addClass("active");
		$("#purchaseDetails").removeClass("active");
		$("#type").val($("#salesDetails").val());
		$("#myGrid1").show();
		$("#myGrid").hide();
		$("#searchRowDiv").hide();
		$("#searchRowDiv1").show();
		var fromDate = $("#filterFromDate").val();
		var toDate = $("#filterToDate").val();
		//var salesTypeId=$("#salesType").val();
		var salesTypeId= "";
	    var totalIGSTAmount = 0.00;
		var totalCGSTAmount = 0.00;
		var totalSGSTAmount = 0.00;
		var totalPayableAmount = 0.00;
	    $.ajax({
	        type: "GET",
	        url: "product-report-getSalesData?fromDate="+fromDate +"&toDate="+toDate+"&salesTypeId="+salesTypeId,
	        success: function(response) {
	            
	            if (response.code === "success") {
	                
	             var datas = JSON.parse(response.body);
	             var salesData= datas.salesData
	             var len = salesData.length;
            	 $('#totalReq').find('span').html(len);
            	 gridOptions1.api.setRowData(salesData);
            	 var totalSum = salesData.reduce((acc, curr) => acc + parseFloat(curr.salesTaxableAmount), 0);
            	 
            	 salesData.forEach(item => {
                        totalIGSTAmount += parseFloat(item.salesIgst);
                        totalCGSTAmount += parseFloat(item.salesCgst);
                        totalSGSTAmount += parseFloat(item.salesSgst);
                        totalPayableAmount += parseFloat(item.salesTotalAmount);
                    });
            	  $("#totalAmountFooter").val(amountFormatter(totalSum.toFixed(2)));
                    $("#totalIGSTAmount").val(amountFormatter(totalIGSTAmount.toFixed(2)));
                    $("#totalCGSTAmount").val(amountFormatter(totalCGSTAmount.toFixed(2)));
                    $("#totalSGSTAmount").val(amountFormatter(totalSGSTAmount.toFixed(2)));
                    $("#totalPayableAmount").val(amountFormatter(totalPayableAmount.toFixed(2)));
	            } else {
	                console.error("Failed to retrieve purchase data:", response.message);
	            }
	        },
	        error: function(jqXHR, textStatus, errorThrown) {
	            console.error("AJAX request failed:", textStatus, errorThrown);
	        }
	    });
	}
	
	function downloadExcelFromGrid(type, gridOptions) {
	    var selectedHeaders;
	    var fileName;

	    if (type == 'Purchase') {
	        selectedHeaders = ['INVOICE ID', 'GRN ID', 'INVOICE DATE','VENDOR NAME','TYPE','SKU', 'HSN Code', 
	                           'Item Name', 'Quantity&Unit', 'GST Rate', 'CGST', 'IGST', 'SGST', 
	                           'Taxable Amount', 'Total Amount'];

	        var fromDate = $("#filterFromDate").val();
	        var toDate = $("#filterToDate").val();
	        fileName = 'Purchase_Report_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';
	    } else if (type == 'Sales') {
	        selectedHeaders = ['INVOICE ID', 'SAP ID', 'INVOICE DATE', 'CUSTOMER NAME','TYPE','SKU', 'HSN', 
	                           'Item Name', 'Quantity&Unit', 'GST Rate', 'CGST', 'SGST', 'IGST', 
	                           'Taxable Amount', 'Total Amount'];

	        var fromDate = $("#filterFromDate").val();
	        var toDate = $("#filterToDate").val();
	        fileName = 'Sales_Report_' + '(' + fromDate + ' To ' + toDate + ')' + '.xlsx';
	    } else {
	        console.error("Invalid type specified");
	        return;
	    }

	    var totalTaxableAmount = 0;
	    var totalIGST = 0;
	    var totalCGST = 0;
	    var totalSGST = 0;
	    var totalAmount = 0;

	    var rowData = [];

	     
	    var fieldMappings = {
	        'Purchase': {
	            'Taxable Amount': 'purchaseTaxableAmount',
	            'IGST': 'purchaseIgst',
	            'CGST': 'purchaseCgst',
	            'SGST': 'purchaseSgst',
	            'Total Amount': 'purchaseTotalAmount'
	        },
	        'Sales': {
	            'Taxable Amount': 'salesTaxableAmount',
	            'IGST': 'salesIgst',
	            'CGST': 'salesCgst',
	            'SGST': 'salesSgst',
	            'Total Amount': 'salesTotalAmount'
	        }
	    };

	    var columns = gridOptions.columnApi.getAllColumns();
	    var fieldMap = {};
	    columns.forEach(function (col) {
	        var colDef = col.getColDef();
	        fieldMap[colDef.headerName] = colDef.field;
	    });

	    gridOptions.api.forEachNodeAfterFilterAndSort(function (node) {
	        var data = {};
	        selectedHeaders.forEach(function (header) {
	            var field = fieldMap[header];
	            var value = node.data[field] || '';
	            if (field === fieldMappings[type]['Taxable Amount'] || field === fieldMappings[type]['IGST'] || field === fieldMappings[type]['CGST'] || field === fieldMappings[type]['SGST'] || field === fieldMappings[type]['Total Amount']) {
	                value = amountFormatter(parseFloat(value).toFixed(2));
	            }
	            data[header] = value;
	        });

	        rowData.push(data);

	        var taxableAmount = parseFloat(node.data[fieldMappings[type]['Taxable Amount']]) || 0;
	        var IGST = parseFloat(node.data[fieldMappings[type]['IGST']]) || 0;
	        var CGST = parseFloat(node.data[fieldMappings[type]['CGST']]) || 0;
	        var SGST = parseFloat(node.data[fieldMappings[type]['SGST']]) || 0;
	        var tAmount = parseFloat(node.data[fieldMappings[type]['Total Amount']]) || 0;
            
	        totalTaxableAmount += taxableAmount;
	        totalIGST += IGST;
	        totalSGST += SGST;
	        totalCGST += CGST;
	        totalAmount += tAmount;
	    });

	    totalTaxableAmount = amountFormatter(totalTaxableAmount.toFixed(2));
	    totalIGST = amountFormatter(totalIGST.toFixed(2));
	    totalSGST = amountFormatter(totalSGST.toFixed(2));
	    totalCGST = amountFormatter(totalCGST.toFixed(2));
	    totalAmount = amountFormatter(totalAmount.toFixed(2));
	    
	    var totalRow = {
	        [selectedHeaders[0]]: "Total",
	        [selectedHeaders[1]]: "",
	        [selectedHeaders[2]]: "",
	        [selectedHeaders[3]]: "",
	        [selectedHeaders[4]]: "",
	        [selectedHeaders[5]]: "",
	        [selectedHeaders[6]]: "",
	        [selectedHeaders[7]]: "",
	        [selectedHeaders[8]]: "",
	        [selectedHeaders[9]]: "",
	        [selectedHeaders[10]]: "",
	        [selectedHeaders[11]]: "",
	        [selectedHeaders[12]]: "",
	        'Taxable Amount': totalTaxableAmount,
	        'IGST': totalIGST,
	        'CGST': totalCGST,
	        'SGST': totalSGST,
	        'Total Amount': totalAmount
	    };

	    rowData.push(totalRow);
	    var ws = XLSX.utils.json_to_sheet(rowData, { header: selectedHeaders });
	    var wb = XLSX.utils.book_new();
	    XLSX.utils.book_append_sheet(wb, ws, type + ' Register');
	    XLSX.writeFile(wb, fileName);
	}

	downloadExcelFromGrid('Purchase', gridOptions);  
	downloadExcelFromGrid('Sales', gridOptions1);  
	

   
function getCategoryList() {
		$("#productDiv").modal("show");
		$.ajax({
	        type: "POST",
	        url: "view-product-category-list",
	        dataType: "json",
	        contentType: "application/json",
	        success: function(response) {
	        	if (response.message == "Success") {
	        		$("#costCeneterCBDiv").empty();
	            	for(var i = 0; i < response.body.length; i++) {
	            		var row = "";
	            		if(response.body[i].categoryId == response.body[i].parentId) {
	            			if(response.body[i].nodeCount > 0) {
	            				row = '<tr data-node-id="'+response.body[i].categoryId+'" class="rowNode" id="'+response.body[i].categoryId+'">'+
	    						'<td class="firstnode" id=lbl_'+response.body[i].categoryId+'>'+response.body[i].categoryName+'</td></tr>';
	            			} else {
	            				row = '<tr data-node-id="'+response.body[i].categoryId+'" class="rowNode" id="'+response.body[i].categoryId+'">'+
	    						'<td class="firstnode" id=lbl_'+response.body[i].categoryId+'><input class="benefitChk" type="checkbox" id="ccCheck_'+ response.body[i].categoryId +'" value="'+ response.body[i].categoryId +
	    						'" name="'+ response.body[i].categoryName +'" onchange=selectCheckBox("'+response.body[i].categoryId+'","'+response.body[i].catLevel+'")>'+response.body[i].categoryName+'</td></tr>';
	            			}
	            			
	            		} else {
	            			if(response.body[i].nodeCount > 0) {
	            				row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="rowNode" id="'+response.body[i].categoryId+'">'+
	    						'<td class="firstnode" id=lbl_'+response.body[i].categoryId+'>'+response.body[i].categoryName+'</td></tr>';
	            			} else {
	            				
	            					row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="rowNode" id="'+response.body[i].categoryId+'">'+
	        						'<td class="firstnode" id=lbl_'+response.body[i].categoryId+'><input class="benefitChk" type="checkbox" id="ccCheck_'+ response.body[i].categoryId +'" value="'+ response.body[i].categoryId +'" name="'+
	        						response.body[i].categoryName +'" onchange=selectCheckBox("'+response.body[i].categoryId+'","'+response.body[i].catLevel+'")>'+response.body[i].categoryName+'</td></tr>';
	            			}
	            		}
						$("#costCeneterCBDiv").append(row);
						
	            	}
	        		
	        		var pcat = $("#prCategoryId").val();
	        		$(".benefitChk").prop("checked", false);
	        		$("#ccCheck_"+pcat).prop("checked", true);
	            	
	            	$('.loader').hide();
	            	$("body").removeClass("overlay");
	            	
	            	$('#basic').simpleTreeTable({
	            	    expander: $('#expander'),
	            	    collapser: $('#collapser'),
	            	    store: 'session',
	            		storeKey: 'simple-tree-table-basic'
	            	});
	            }
	        }, error: function(data) {
	        	console.log(data)
	        	$('.loader').hide();
	        	$("body").removeClass("overlay");
	        	$("#productDiv").modal("show");
	        }
		});
	}	


function selectCheckBox(id,lvl) {
	
	 $('.benefitChk').each(function () {
	        if ($(this).val() !== id) {
	            $(this).prop('checked', false);
	        }
	    });
	 if ($('.benefitChk:checked').length > 0) {
         $("#selectedProductCategory").val(id);
     } else {
         $("#selectedProductCategory").val('');
     }

	//viewPurchaseDetails();
	if(id != "PCAT000004"){
		viewPurchaseDetails();
	}
	else{
		viewSalesDetails();
	}
	 
}