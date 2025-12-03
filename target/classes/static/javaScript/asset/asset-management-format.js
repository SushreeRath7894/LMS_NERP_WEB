//		shrink and expand js
document.addEventListener("DOMContentLoaded", function() {
	let targetSectionR = document.querySelector('#collapseWidthR');
	let targetSectionL = document.querySelector('#collapseWidthL');
	let collapseButtonR = document.querySelector('#collapseButtonR');
	let collapseButtonL = document.querySelector('#collapseButtonL');
	let expandIconR = document.querySelector('#expandIconR');
	let expandIconL = document.querySelector('#expandIconL');
	let shrinkIconL = document.querySelector('#shrinkIconL');
	let shrinkIconR = document.querySelector('#shrinkIconR');

	collapseButtonR.addEventListener('click', function() {
		if (targetSectionR.classList.contains('expanded')) {
			targetSectionR.classList.remove('expanded');
			targetSectionL.classList.remove('hidden');
			expandIconR.classList.remove('d-none');
			shrinkIconR.classList.add('d-none');
		} else {
			targetSectionR.classList.add('expanded');
			targetSectionL.classList.add('hidden');
			expandIconR.classList.add('d-none');
			shrinkIconR.classList.remove('d-none');
		}
	});
	collapseButtonL.addEventListener('click', function() {
		if (targetSectionL.classList.contains('expanded')) {
			targetSectionL.classList.remove('expanded');
			targetSectionR.classList.remove('hidden');
			expandIconL.classList.remove('d-none');
			shrinkIconL.classList.add('d-none');
		} else {
			targetSectionL.classList.add('expanded');
			targetSectionR.classList.add('hidden');
			expandIconL.classList.add('d-none');
			shrinkIconL.classList.remove('d-none');
		}
	});
});

/*				Grid Detailsss				*/

// 			main 		 grid			details
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: 'Asset Id',
		field: "assetId",
		pinned: 'left',
		/*cellRenderer : function(params) {
			return '<a id="assetId" onclick=editAsset("'
			+params.data.assetId
			+ '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> '
			+ params.data.assetId + '</a>';
		}*/
	}, {
		headerName: 'Asset Code',
		field: "assetcode",
	}, {
		headerName: 'Asset Model',
		field: "assetmodel",
	}, {
		headerName: 'Asset Name',
		field: "assetname",
	}, {
		headerName: 'Asset Type',
		field: "assettype",
	}, {
		headerName: 'Asset Category',
		field: "assetcat",
	}, {
		headerName: 'Asset Category',
		field: "assetcatId",
		hide: true
	}, {
		headerName: 'Asset Sub-Category',
		field: "assetsubcat",
	}, {
		headerName: 'Asset Sub-Category',
		field: "assetsubcatId",
		hide: true
	}, {
		headerName: 'GRN No',
		field: "purchaseno",
	}, {
		headerName: 'Lifespan(Days)',
		field: "lifespan",
	}, {
		headerName: 'Working Status',
		field: "workingstatus",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.workingstatus == "Working") {
				return '<div style="color:#0642f5">Working</div>';
			} else if (params.data.workingstatus == "Scrapped") {
				return '<div style="color:orange">Scrapped</div>';
			} else if (params.data.workingstatus == "Damaged") {
				return '<div style="color:yellow">Damaged</div>';
			} else if (params.data.workingstatus == "Other") {
				return '<div style="color:pink">Other</div>';
			} else if (params.data.workingstatus == "Disposed") {
				return '<div style="color:#D2042D">Disposed</div>';
			} else {
				return '<div style="color:#a9a9a9">Not Working</div>';
			}
		}
	}, {
		headerName: 'Approve Status',
		field: "approvests",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.approvests == "Approved") {
				return '<div style="color:#0642f5">Approved</div>';
			} else {
				return '<div style="color:#a9a9a9">Pending</div>';
			}
		}
	}, {
		headerName: 'Assign Status',
		field: "assignname",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.assignname == "Assigned") {
				return '<div style="color:#0642f5">Assigned</div>';
			} else if (params.data.assignname == "Partially Assigned") {
				return '<div style="color:orange">Partially Assigned</div>';
			} else {
				return '<div style="color:#a9a9a9">Not Assigned</div>';
			}
		}
	},/*
		{
			headerName : 'Log Book',
			field : "logbook",
			cellStyle : {
				textAlign : 'center'
			},
				cellRenderer : function(params) {
				return '<a id="id" onclick=viewAssignModal("'
				+ params.data.assetId + '") href="javascript:void(0)">'
				+ '<i class="bi bi-clock-history"> View Log</i>'
				+ '</a>';
			}
		}, {
			headerName : 'QR Code',
			field : "qrcode",
			cellStyle : {
				textAlign : 'center'
			},cellRenderer : function(params) {
				if (params.data.qr_code == "" || params.data.qr_code == null) {
					return '<div style="color:#ff8242">Not Available</div>';
				} else {
					return '<a id="id" onclick=viewQr("'
					+ params.data.qr_code + '") href="javascript:void(0)">'
					+ '<i class="bi bi-qr-code-scan"> View QR</i>'
					+ '</a>';
				}
			},
		},*/ /*{
		headerName: 'Verification Status',
		field: "verifysts",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.verifysts == "Matched") {
				return '<div style="color:green">Matched</div>';
			} else if (params.data.verifysts == "Mismatched") {
				return '<div style="color:red">Mismatched</div>';
			} else if (params.data.verifysts == "Not Exist") {
				return '<div style="color:orange">Not Exist</div>';
			} else {
				return '<div style="color:#a9a9a9">Pending</div>';
			}
		}
	}*/, {
		headerName: 'Asset Purchase Date',
		field: "purchaseOn",
	}, {
		headerName: 'Registered On',
		field: "createdOn",
	},];
var gridOptions = {
	columnDefs: columnDefs,
	rowSelection: 'single',
//	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 15,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150
	},
	onSelectionChanged: onSelectionChanged,
/*	onRowDataUpdated: function() {
		var id = $("#assetId").val();
        if (id != null) {
            asset = id;
            gridOptions.api.forEachNode(function(node) {
                if (node.data && node.data.assetId === id) {
                    node.setSelected(true);
                    editAssetDetails(id);
                }
            });
        }else{
			asset = '';		
				var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}
		}
    }*/
};

var id = "";
var apstatus = "";
var assetname = "";
var purchaseno = "";
var pdate = "";
var assettype = "";
var assignname = "";
var wstatus = "";
function onSelectionChanged() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	console.log(selectedData);
	id = selectedData.map(node => node.assetId);
	apstatus = selectedData.map(node => node.approvests);
	assetname = selectedData.map(node => node.assetname);
	purchaseno = selectedData.map(node => node.purchaseno);
	pdate = selectedData.map(node => node.pdate);
	assettype = selectedData.map(node => node.assettype);
	apstatus = selectedData.map(node => node.approvests);
	assignname = selectedData.map(node => node.assignname);
	wstatus = selectedData.map(node => node.workingstatus);
	var selectedRows = gridOptions.api.getSelectedRows();
	assetGSubCat = '';

			$("#assetId").val(id);
			$("#assetId1").val(id);
			$("#assetId2").val(id);
			$("#assetId3").val(id);
			$("#assetId4").val(id);
			$("#assetId5").val(id);
			$("#assetId6").val(id);
			$("#assetId7").val(id);
	let rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		if(asset != id ){
			nextTab('assetDetsId');
		}	
		getDets();
//		getFeildsView(apstatus,wstatus,assignname);
	} else {
		$(".formValidation").remove();
		$("#assettype, #assetcat, #assetcode, #assetmodel, #assetname, #lifespan, #purchaseno, #assetPrice, #pdate,#DateCalendar3, #owntype, #wstatus, #assetDescription").prop('disabled', false);
		document.getElementById("DateCalendar3").style.display = "block";
		$("#cancelAsset, #saveAsset").show();
		$("#editAsset, #addAsset, #deleteAsset, #approveAsset,#nextSecDiv").hide();
		$("#assetbox, #assetId, #assetname, #assetmodel, #assetcode, #lifespan, #purchaseno").prop('readonly', false);
		$("#warrantyDetsId").hide();
		$("#insuranceDetsId").hide();
		$("#complianceDetsId").hide();	
		nextTab('assetDetsId');
		activityOptions.api.setRowData("");
		InsuranceOptions.api.setRowData("");
		complianceOptions.api.setRowData("");
		assetGSubCat = '';
		$("#next1").hide();
		$("#nextSecDiv").hide();
		$("#assetcat").val("").trigger('change');
		$("#assetsubcat").val("").trigger('change');
		$("#assetId").val("");
		$("#assetId1").val("");
		$("#assetId2").val("");
		$("#assetId3").val("");
		$("#assetId4").val("");
		$("#assetId5").val("");
		$("#assetName1").html("");
		$("#assetName2").html("");
		$("#assetName3").html("");
		$("#assetName4").html("");
		$("#assetName5").html("");
		$("#assetName6").html("");
		$("#assetName7").html("");
		$("#assetbox").val("");
		$("#wstatus").val("").trigger('change');
		$("#owntype").val("").trigger('change');
		$("#assettype").val("").trigger('change');
		$("#assetbox").text("");
		$("#assetname").val("");
		$("#assetmodel").val("");
		$("#assetcode").val("");
		$("#lifespan").val("");
		$("#purchaseno").val("");
		$("#pdate").val("");
		$("#assetPrice").val("");
		$("#assetDescription").val("");
		$("#reqCapacity").val("");
		$("#unit").val("").trigger('change');
		$("#warrantyDetsId").hide();
		$("#insuranceDetsId").hide();
		$("#complianceDetsId").hide();
		$("#policyAllocationId").hide();
		$("#workingStsId").hide();
		$("#assetAllocationId").hide();		
	}
}
function getFeildsView(apstatus,wstatus,assignname){
	console.log("apstatus=====",apstatus,"   wstatus=====",wstatus,"   assignname=====",assignname);
	
	var selectedRows = gridOptions.api.getSelectedRows();
	var data = selectedRows[0];
	if (apstatus == "Approved") {
			if (wstatus == "Working") {
				if (assignname == "Assigned") {
					assetDeAllocate();
				} else if (assignname == "Partially Assigned") {
					assetDeAllocate();
				} else {
					assetAllocate();
				}
			} else {			
		//		assetDeAllocate();
				$('#assetAllocate, #assetDissociate').hide(); 
			/*	if (wstatus === "Disposed" || wstatus === "Scrapped" || wstatus === "Damaged") {
				    $('#assetAllocate, #assetDissociate').hide(); 
				    if (assignname === "Assigned") {
				//        alert('Assigned');
				        assetDeAllocate();
				//        $('#assetDissociate').show();	
				    } else {
				//        alert('not Assigned');
				    $('#assetAllocate, #assetDissociate').hide();
				    }
				} else {
				    $('#assetAllocate, #assetDissociate').show();
				}*/
			}
			$('#approveAsset, #deleteAsset, #editAsset').attr("disabled", true);
			$('#workingStsId').show();
			$('#assetAllocationId').show();
			$('#policyAllocationId').show();
			$('#next1').show();
			$('#approveAsset').hide();
			$('#deleteAsset').hide();
			$('#editAsset').hide();
			$("#warrantyDetsId").show();
			$("#insuranceDetsId").show();
			$("#complianceDetsId").show();
			$("#policyAllocationId").show();
			$("#workingStsId").show();
			$("#assetAllocationId").show();
			$('#next4').show();
		} else {
			if (wstatus == "Working") {
				if (assignname == "Assigned") {
					assetDeAllocate();
				} else if (assignname == "Partially Assigned") {
					assetDeAllocate();
				} else {
					assetAllocate();
				}
			} else {			
		//		alert('else');
				assetDeAllocate();
				$('#assetAllocate, #assetDissociate').hide();
			}
			/*	if (wstatus == "Disposed" || wstatus == "Scrapped" || wstatus == "Damaged") {
					$('#assetAllocate,#assetDissociate').hide();
					if (assignname == "Assigned") {
						$('#assetDissociate').show();
					}
				} else {
					$('#assetAllocate,#assetDissociate').show();
				}*/
			$('#workingStsId').hide();
			$('#assetAllocationId').hide();
			$('#policyAllocationId').hide();
			$('#next4').hide();
			$('#approveAsset, #deleteAsset, #editAsset').attr("disabled", false);
			$('#next1').show();
			$('#approveAsset').show();
			$('#deleteAsset').show();
			$('#editAsset').show();
			$("#warrantyDetsId").show();
			$("#insuranceDetsId").show();
			$("#complianceDetsId").show();
			$("#policyAllocationId").hide();
			$("#workingStsId").hide();
			$("#assetAllocationId").hide();
		}
}
// 			warrenty		 grid 		details
var activityDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30,
		pinned: 'left'
	},
	{
		headerName: "Warrenty Tbl Id",
		field: "warId",
		hide: true,
	},
	{
		headerName: "Asset Id",
		field: "assetId",
		hide: true,
	}, {
		headerName: "documentFileBase",
		field: "documentFileBase",
		hide: true,

	}, {
		headerName: "Warranty ID",
		field: "warrantyid",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Service Provider",
		field: "serviceprovider",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Starts Date",
		field: "sdate",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "End Date",
		field: "edate",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Remark",
		field: "remark",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Status',
		field: "status",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			return checkWarranty(params.data.sdate, params.data.edate);
		},
	}, {
		headerName: 'Attachment',
		field: "fileName",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.documentURL != null && params.data.documentURL != "null") {

				return '<a href="' + params.data.documentURL + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
			} else {/*
				if (params.data.dURL != null && params.data.dURL != "null") {
					return '<a href="' + params.data.dURL + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
				} else {*/
					return "Not Available";
		//		}
			}
		},
	}
];
var activityOptions = {
	columnDefs: activityDefs,
	rowSelection: 'single',
//	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onSelectionChanged: onSelectionChangeWarrenty,

};
var childid = "";
var warId = "";
function onSelectionChangeWarrenty() {
	var selectedNodes = activityOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	childid = selectedData.map(node => node.warId);
	warId = selectedData.map(node => node.warId);
	var selectedRows = activityOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newWarrenty').attr('disabled', true);
		$(".br-dis").attr("disabled", false);
		$('#deleteWarrenty').attr('disabled', false);


	} else {
		$('#newWarrenty').attr('disabled', false);
		$(".br-dis").attr("disabled", true);
		$('#deleteWarrenty').attr('disabled', true);

	}
}

//			 insurance		 grid 		details
var InsuranceDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30,
		pinned: 'left'
	},
	{
		headerName: "Asset Id",
		field: "assetId",
		hide: true,
		pinned: 'left',
		cellRenderer: function(params) {
			return '<a onclick=editRow("' + params.data.parameterId + '") href="javascript:void(0)">'
				+ params.data.parameterId + '</a>';
		}
	}, {
		headerName: "Insurance No",
		field: "insuranceno",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Insurance Name",
		field: "insurancename",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "Start Date",
		field: "isdate",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: "End Date",
		field: "iedate",
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Status',
		field: "status",
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			return checkWarranty(params.data.isdate, params.data.iedate);
		},
	}, {
		headerName: 'Attachment',
		field: "fileName",
		width: 90,
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.documentURL != null && params.data.documentURL != "null") {

				return '<a href="' + params.data.documentURL + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
			} else {/*
				if (params.data.dURL != null && params.data.dURL != "null") {
					return '<a href="' + params.data.dURL + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
				} else {*/
					return "Not Available";
			//	}
			}
		},
	}];
var InsuranceOptions = {
	columnDefs: InsuranceDefs,
	rowSelection: 'single',
//	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onSelectionChanged: onSelectionChangeInsure,

};
var childid = "";
var insId = "";
var slNo = "";
function onSelectionChangeInsure() {
	var selectedNodes = InsuranceOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	childid = selectedData.map(node => node.insuranceno);
	insId = selectedData.map(node => node.insuranceno);
	slNo = selectedData.map(node => node.slNo);
	var selectedRows = InsuranceOptions.api.getSelectedRows();
	var rowCount = 0;
	var insList = "";
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newInsure').attr('disabled', true);
		$(".br-dis-i").attr("disabled", false);
	} else {
		$('#newInsure').attr('disabled', false);
		$(".br-dis-i").attr("disabled", true);
	}
}

// 			complaince / documentation 		grid 		details
var complianceDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30,
		pinned: 'left'
	},
	{
		headerName: "Warrenty Tbl Id",
		field: "compId",
		hide: true,
	}, {
		headerName: "Starts Date",
		field: "sdate",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		hide: true
	}, {
		headerName: "Renewal Date",
		field: "rdate",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
		hide: true
	}, {
		headerName: "Document Name",
		field: "docName",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		},
	}, {
		headerName: 'Attachment',
		field: "fileName",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		}, cellRenderer: function(params) {
			if (params.data.documentURL != null && params.data.documentURL != "null") {

				return '<a href="' + params.data.documentURL + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
			} else {/*
				if (params.data.dURL != null && params.data.dURL != "null") {
					return '<a href="' + params.data.dURL + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
				} else {*/
					return "Not Available";
				//}
			}
		},
	}];
var complianceOptions = {
	columnDefs: complianceDefs,
	rowSelection: 'single',
//	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		flex: 1
	},
	onSelectionChanged: onSelectionChangeChildCompliance,
	onGridReady: function(params) {
		complianceOptions.api = params.api;
		complianceOptions.columnApi = params.columnApi;
		//    params.api.setRowData(initialData);  // Set initial data
	}
};
var childcomp = "";
var compId = "";
function onSelectionChangeChildCompliance() {
	var selectedNodes = complianceOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	childcomp = selectedData.map(node => node.compId);
	compId = selectedData.map(node => node.compId);
	var selectedRows = complianceOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newDocs').attr('disabled', true);
		$(".br-dis-d").attr("disabled", false);
		$('#deleteDocs').attr('disabled', false);

	} else {
		$('#newDocs').attr('disabled', false);
		$(".br-dis-d").attr("disabled", true);
		$('#deleteDocs').attr('disabled', true);
	}
}

// policy grid
var policyDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 25,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',

	},
	{
		headerName: "Policy Id",
		field: "policyId",
		cellStyle: {
			textAlign: 'left'
		},
		width: 125,
	}, {
		headerName: "Policy Name",
		field: "policyName",
		cellStyle: {
			textAlign: 'left'
		},
		width: 230,
	}, {
		headerName: "Category",
		field: "category",
		cellStyle: {
			textAlign: 'left'
		},
		width: 110
	}, {
		headerName: "Subcategory",
		field: "subcategory",
		cellStyle: {
			textAlign: 'left'
		},
		width: 110
	}, {
		headerName: "Frequency",
		field: "frequency",
		cellStyle: {
			textAlign: 'left'
		},
		width: 110
	}];

var policyOptions = {
	columnDefs: policyDefs,
	rowSelection: 'multiple',
//	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 228
	},
	onSelectionChanged: onSelectionChangePolicy,

};
var policyId = "";
function onSelectionChangePolicy() {
	var selectedNodes = policyOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var selectedRows = policyOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$("#first").show();
		$('input[name="options"]').prop('checked', false);
		$('input[name="options1"]').prop('checked', false);
		$("#assetlist").val('');
		$("#groupList").val('');
		$("#assetemployee").val("").trigger('change');
		$("#asdate").val("");
		$("#assetlocation").val("").trigger('change');
		$("#second").hide();
		$("#astList").hide();
		/*$("#assigndate").hide();
		$("#grplist").hide();
		$("#emplist").hide();
		$("#loclist").hide();*/
		$("#policyAllcate").attr("disabled", false);
	} else {
		$("#first").hide();
		$("#second").hide();
		$("#astList").hide();
	/*	$("#grplist").hide();
		$("#emplist").hide();
		$("#loclist").hide();
		$("#assigndate").hide();*/
		$("#policyAllcate").attr("disabled", true);
	}
}
// policy allocated grid details
var allocatedColumnDefs = [
	/* {
		headerCheckboxSelection : false,
		headerCheckboxSelectionFilteredOnly : false,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true,
		pinned : 'left',

	}, */
	{
		headerName: 'Allot Id',
		field: "allocid",
		pinned: 'left',
	//	width : 110,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Asset or Group Name',
		field: "assetname",
//		width : 155,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Policy ID',
		field: "policyid",
//		width : 120,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Policy Name',
		field: "policyName",
//		width : 155,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Policy Category',
		field: "category",
		hide: true,
//		width : 150,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Policy Sub-Category',
		field: "subcategory",
//		width : 140,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Assign Type',
		field: "type",
		hide: true,
	}, {
		headerName: 'Asset ID',
		field: "assetid",
		hide: true,
	}, {
		headerName: 'Asset Name',
		field: "assetname",
		hide: true,

	}, {
		headerName: 'Frequency',
		field: "frequency",
//		width : 95,
    	cellStyle: { textAlign: 'left' }
	}, {
		headerName: 'Assigned To',
		field: "allotedto",
		hide : true

	},
];

// let the grid know which columns and what data to use
var allocatedPolicyOptions = {
	columnDefs: allocatedColumnDefs,
	rowSelection: 'single',
//	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150
	},
	//	onSelectionChanged : onSelectionChanged,
};
// 		BUTTON 		FUNCTIONSSS
// 	edit buttonss

function editAsset() {
	$("#assettype, #assetcat, #assetsubcat, #assetcode, #assetmodel, #assetname, #lifespan, #purchaseno, #assetPrice, #pdate,#DateCalendar3, #owntype, #wstatus, #assetDescription").prop('disabled', false);
	document.getElementById("DateCalendar3").style.display = "block";
	$("#cancelAsset, #saveAsset").show();
	$("#editAsset, #addAsset, #deleteAsset, #approveAsset,#nextSecDiv, #next1").hide();
}
function editWarrenty() {
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$("#prev1, #next2").prop('disabled', true);
	//	$("#cancelWarrenty, #saveWarrenty , #warentySaveDiv , #warentyForm").show();
	$("#newWarrenty, #deleteWarrenty").hide();

	console.log(allData3, 'allData3')
	var warEditData = allData3.filter(f => f.warId === String(warId));

	console.log('ddddd', warEditData);
	if(warEditData){
	var assetIds = warEditData[0].assetId;
	var serviceproviders = warEditData[0].serviceprovider;
	var warrantyids = warEditData[0].warrantyid;
	var sdates = warEditData[0].sdate;;
	var edates = warEditData[0].edate;
	var remarks = warEditData[0].remark;
	var fileName = warEditData[0].fileName;
	var docUrl = warEditData[0].documentURL;

	if (fileName != null && fileName != 'null') {
		$("#imageWarrName_0").html(fileName);
		$("#warrenDocUrl").val(docUrl);
		
		var extension = fileName.split(".");
		var cancel = "<span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openWarDelete()'></i></span>";
	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-image custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0'  target='_blank'><i class='fa-solid fa-file-image custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-word custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"} else {
		var LightImg = "";
			var cancel = "";
	}
	$("#cancelWarDoc").html(cancel);
		$("#uploadedWarrDiv_0").html(LightImg);
	}
	fileName = "";
	$("#serviceprovider").val(serviceproviders);
	$("#warrantyid").val(warrantyids);
	$("#sdate").val(sdates);
	$("#edate").val(edates);
	$("#remark").val(remarks);
	$("#wardId").val(warId);
	}

}

function viewImage(id) {
	const fileName = id.split('/').pop();
	console.log(fileName);
	window.open("/document/assetDocUrl/" + fileName, '_blank');
}
function editInsure() {
	$(".br-m-btn-i").hide();
	$(".br-s-btn-i").show();
	$("#prev2, #next3").prop('disabled', true);
	$("#cancelInsure, #saveInsure , #insureSaveDiv , #insureForm").show();
	$("#newInsure, #deleteInsure").hide();
	console.log(allData1, 'allData1');
	var insEditData = allData1.filter(f => f.slNo === parseInt(slNo));
	console.log(insEditData, 'insEditData');

	var assetIds = insEditData[0].assetId;
	var insurancenames = insEditData[0].insurancename;
	var insurancenos = insEditData[0].insuranceno;
	var isdates = insEditData[0].isdate;
	var iedates = insEditData[0].iedate;
	var fileName = insEditData[0].fileName;
	var docUrl = insEditData[0].documentURL;

	if (fileName != null && fileName != 'null') {
		$("#insDocUrl").val(docUrl);
		$("#imageWarrName1_0").html(fileName);
		var extension = fileName.split(".");
		var cancel = "<span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openInsDelete()'></i></span>";
	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-image custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0'  target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-word custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"} else {
		var LightImg = "";
			var cancel = "";
	}
	$("#cancelInsDoc").html(cancel);
		$("#uploadedWarrDiv1_0").html(LightImg);
	}
	fileName = "";

	$("#assetId2").val(assetIds);
	$("#insurancename").val(insurancenames);
	$("#insuranceno").val(insurancenos);
	$("#isdate").val(isdates);
	$("#iedate").val(iedates);
	$("#slNoId").val(slNo);


}
function editDocs() {
	$(".br-m-btn-d").hide();
	$(".br-s-btn-d").show();
	$("#prev3, #next4").prop('disabled', true);
	$("#cancelDocs, #saveDocs , #docsSaveDiv , #docsForm").show();
	$("#newDocs, #deleteDocs").hide();


	console.log(allData4, 'allData4');
	var docEditData = allData4.filter(f => f.compId === String(compId));
	console.log(docEditData, 'docEditData');

	var assetIds = docEditData[0].assetId;
	var sdates = docEditData[0].sdate;
	var rdates = docEditData[0].rdate;
	var docNames = docEditData[0].docName;
	var fileName = docEditData[0].fileName;
	var docUrl = docEditData[0].documentURL;

	if (fileName != null && fileName != 'null') {
		$("#docDocUrl").val(docUrl);
		$("#imageCompName_0").html(fileName);
		
		var extension = fileName.split(".");
		var cancel = "<span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openCompDelete()'></i></span>";
	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-image custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0'  target='_blank'><i class='fa-solid fa-file-pdf custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-excel custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link uploadicon m-0 p-0' target='_blank'><i class='fa-solid fa-file-word custom-file-icon' onclick=viewImage('" +
			 docUrl + "')></i></a>"} else {
		var LightImg = "";
			var cancel = "";
	}
	$("#cancelDoc").html(cancel);
		$("#uploadedDivComp_0").html(LightImg);
	}
	fileName = "";
	$("#csdate").val(sdates);
	$("#crdate").val(rdates);
	$("#docName").val(docNames);
	$("#compId").val(compId);



}
// get details
function getDets(tab){
	var id = $("#assetId").val();
	$(".formValidation").remove();
	$("#assettype, #assetcat, #assetsubcat, #assetcode, #assetmodel, #assetname, #lifespan, #purchaseno, #assetPrice, #pdate,#DateCalendar3, #owntype, #wstatus, #assetDescription").prop('disabled', true);
	$("#cancelAsset, #saveAsset").hide();
	$("#editAsset, #addAsset, #deleteAsset, #approveAsset,#next1").show();
	$("#pdate").prop('disabled', true);
	document.getElementById("DateCalendar3").style.display = "none";
	if(id){
		editAssetDetails(id);
	}else{
		viewAsset();
	}
//	editAssetDetails(id);
	//nextTab(tab);
	cancelWarrenty();
	cancelInsure();
	cancelDocs();
	CancelPolicyAllcate();
//	assetAllocationChange('');
	getFeildsView(apstatus,wstatus,assignname);	
}
//	cancel buttons
function cancelAsset() {
	$(".formValidation").remove();
	//onSelectionChanged();
	$("#assettype, #assetcat, #assetsubcat, #assetcode, #assetmodel, #assetname, #lifespan, #purchaseno, #assetPrice, #pdate,#DateCalendar3, #owntype, #wstatus, #assetDescription").prop('disabled', true);
	$("#cancelAsset, #saveAsset").hide();
	$("#editAsset, #addAsset, #deleteAsset, #approveAsset,#nextSecDiv, #next1").show();
	$("#pdate").prop('disabled', true);
	document.getElementById("DateCalendar3").style.display = "none";
	$("#assetcat").val("").trigger('change');
	$("#assetsubcat").val("").trigger('change');
/*	$("#assetId").html("");
	$("#assetId1").html("");
	$("#assetId2").html("");
	$("#assetId3").html("");
	$("#assetId4").html("");
	$("#assetId5").html("");
	$("#assetName1").html("");
	$("#assetName2").html("");
	$("#assetName3").html("");
	$("#assetName4").html("");
	$("#assetName5").html("");
	$("#assetName6").html("");
	$("#assetName7").html("");*/
	$("#assetbox").val("");
	$("#wstatus").val("").trigger('change');;
	$("#owntype").val("").trigger('change');
	$("#assettype").val("").trigger('change');
	$("#assetId").val("");
	$("#assetbox").text("");
	$("#assetname").val("");
	$("#assetmodel").val("");
	$("#assetcode").val("");
	$("#lifespan").val("");
	$("#purchaseno").val("");
	$("#pdate").val("");
	$("#assetPrice").val("");
	$("#assetDescription").val("");
	$("#reqCapacity").val("");
	$("#unit").val("").trigger('change');
	$("#warrantyDetsId").show();
	$("#insuranceDetsId").show();
	$("#complianceDetsId").show();
	if ($("#assetId1").val()) {
	    const assetIdVal = $("#assetId1").val();
	    viewAsset(assetIdVal, 'assetDetsId');
	} else {		
	    viewAsset();
	}
}
function cancelWarrenty() {
	$("#prev1, #next2").prop('disabled', false);
	$(".formValidation").remove();
	$("#cancelWarrenty, #saveWarrenty, #warentySaveDiv , #warentyForm").hide();
	activityOptions.api.deselectAll();
	$("#newWarrenty").show();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
	$(".br-dis").attr("disabled", true);
	$("#newWarrenty").attr("disabled", false);
	$("#serviceprovider").val("");
	$("#warrantyid").val("");
	$("#remark").val("");
	$("#sdate").val("");
	$("#edate").val("");
	$('#wardId').val("");
	$('#uploadWarDoc_0').val('');
	$('#imageWarrName_0').empty();
	$('#uploadedWarrDiv_0').empty();
}
function cancelInsure() {
	$("#prev2, #next3").prop('disabled', false);
	$(".formValidation").remove();
	$("#cancelInsure, #saveInsure , #insureSaveDiv , #insureForm").hide();
	InsuranceOptions.api.deselectAll();
	$("#newInsure").show();
	$(".br-m-btn-i").show();
	$(".br-s-btn-i").hide();
	$(".br-dis-i").attr("disabled", true);
	$("#newInsure").attr("disabled", false);
	$("#insurancename").val('');
	$("#insuranceno").val('');
	$("#iedate").val('');
	$("#isdate").val('');
	$("#slNoId").val('');
	$('#uploadWarDoc1_0').val('');

	$('#imageWarrName1_0').empty();
	$('#uploadedWarrDiv1_0').empty();
}
function cancelDocs() {
	$("#prev3, #next4").prop('disabled', false);
	$(".formValidation").remove();
	$("#cancelDocs, #saveDocs , #docsSaveDiv , #docsForm").hide();
	complianceOptions.api.deselectAll();
	$("#newDocs").show();
	$(".br-m-btn-d").show();
	$(".br-s-btn-d").hide();
	$(".br-dis-d").attr("disabled", true);
	$("#newDocs").attr("disabled", false);

	$("#crdate").val('');
	$("#csdate").val('');
	$("#docName").val('');
	$("#compId").val('');
	$('#uploadCompDoc_0').val('');
	$('#imageCompName_0').empty();
	$('#uploadedDivComp_0').empty();
}
// 	new buttons
function newAsset() {
	$(".formValidation").remove();
	$("#assettype, #assetcat, #assetsubcat, #assetcode, #assetmodel, #assetname, #lifespan, #purchaseno, #assetPrice, #pdate,#DateCalendar3, #owntype, #wstatus, #assetDescription").prop('disabled', false);
	document.getElementById("DateCalendar3").style.display = "block";
	$("#cancelAsset, #saveAsset").show();
	$("#editAsset, #addAsset, #deleteAsset, #approveAsset,#nextSecDiv, #next1").hide();
	$("#assetbox, #assetId, #assetname, #assetmodel, #assetcode, #lifespan, #purchaseno").prop('readonly', false);
	$("#warrantyDetsId").hide();
	$("#insuranceDetsId").hide();
	$("#complianceDetsId").hide();

	activityOptions.api.setRowData("");
	InsuranceOptions.api.setRowData("");
	complianceOptions.api.setRowData("");
	assetGSubCat = '';
	gridOptions.api.deselectAll();
	$("#nextSecDiv").hide();
	$("#assetcat").val("").trigger('change');
	$("#assetsubcat").val("").trigger('change');
	$("#assetId").val("");
	$("#assetId1").val("");
	$("#assetId2").val("");
	$("#assetId3").val("");
	$("#assetId4").val("");
	$("#assetId5").val("");
	$("#assetName1").html("");
	$("#assetName2").html("");
	$("#assetName3").html("");
	$("#assetName4").html("");
	$("#assetName5").html("");
	$("#assetName6").html("");
	$("#assetName7").html("");
	$("#assetbox").val("");
	$("#wstatus").val("").trigger('change');
	$("#owntype").val("").trigger('change');
	$("#assettype").val("").trigger('change');
	$("#assetId").val("");
	$("#assetbox").text("");
	$("#assetname").val("");
	$("#assetmodel").val("");
	$("#assetcode").val("");
	$("#lifespan").val("");
	$("#purchaseno").val("");
	$("#pdate").val("");
	$("#assetPrice").val("");
	$("#assetDescription").val("");
	$("#reqCapacity").val("");
	$("#unit").val("").trigger('change');


}
function newWarrenty() {
	$("#cancelWarDoc").html("");
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$(".formValidation").remove();
	editWarrenty();
	$("#rowEdit1").val("");
	$("#serviceprovider").val("");
	$("#warrantyid").val("");
	$("#remark").val("");
	$("#sdate").val("");
	$("#edate").val("");
	$('#wardId').val("");
	$('#uploadWarDoc_0').val('');
	$('#imageWarrName_0').empty();
	$('#uploadedWarrDiv_0').empty();
	var war = `								<div class="form-group">
		<label>Attach</label>
		<div class="d-flex align-items-center mt-20">
		<div class="control-group">
						<label class="custom-file-upload" for="uploadWarDoc_0"> <i class="ti-plus"></i>
						</label>
						<div class="controls">
							<input type="file" class="wardocument" id="uploadWarDoc_0"
								accept=".jpeg, .jpg, .png, .pdf"  
								 onchange="saveFile(event)">
						</div>
    				</div> <input type="hidden" id="warrenDocUrl"> 
					<input type="hidden" id="uploadHidden_0" class="uploadHidCls">
         			<div id="uploadedWarrDiv_0" align="center" class="uploadedBillCls"></div>
         			<div id="imageWarrName_0" class="imageName"></div> <div id="cancelWarDoc" ></div>					
					</div>
	</div>`;
	$("#attachWar").html(war);

}
function newInsure() {
	$("#cancelInsDoc").html("");
	$("#prev2, #next3").prop('disabled', true);
	$(".br-m-btn-i").hide();
	$(".br-s-btn-i").show();
	$(".formValidation").remove();
	editInsure();
	$("#rowEdit").val("");
	$("#insurancename").val('');
	$("#insuranceno").val('');
	$("#iedate").val('');
	$("#isdate").val('');
	$("#slNoId").val('');
	$('#uploadWarDoc1_0').val('');
	$('#imageWarrName1_0').empty();
	$('#uploadedWarrDiv1_0').empty();
	var insu = `<div class="form-group">
  <label>Attach</label>
  <div class="d-flex align-items-center gap-2">
    <div class="control-group">
      <label class="custom-file-upload" for="uploadWarDoc1_0"> <i class="ti-plus"></i></label>
      <div class="controls">
        <input type="file" class="wardocument1" id="uploadWarDoc1_0" accept=".jpeg, .jpg, .png, .pdf" onchange="saveFile1(event)">
      </div>
    </div><input type="hidden" id="insDocUrl">
	<input type="hidden" id="uploadHidden1_0" class="uploadHidCls1">
	<div id="uploadedWarrDiv1_0" align="center"  class="uploadedWarrCls1"></div>
	<div id="imageWarrName1_0" class="imageName"></div> <div id="cancelInsDoc" ></div>
  </div>
</div>`;
	$("#attachIn").html(insu);

}
function newDocs() {
	$("#cancelDoc").html("");
	$("#prev3, #next4").prop('disabled', true);
	$(".br-m-btn-d").hide();
	$(".br-s-btn-d").show();
	$(".formValidation").remove();
	editDocs();

	$("#crdate").val('');
	$("#csdate").val('');
	$("#docName").val('');
	$('#uploadCompDoc_0').val('');
	$('#imageCompName_0').empty();
	$('#uploadedDivComp_0').empty();
	var comp = `<div class="form-group">
  <label>Attach</label>
  <div class="d-flex align-items-center gap-2">
    <div class="control-group">
      <label class="custom-file-upload" for="uploadCompDoc_0"> <i class="ti-plus"></i>
      </label>
      <div class="controls">
        <input type="file" class="compdocument" id="uploadCompDoc_0" accept=".jpeg, .jpg, .png, .pdf" onchange="saveFileComp(event)">
      </div>
    </div>
                                    <input type="hidden" id="docDocUrl">
    <input type="hidden" id="uploadHiddenComp_0" class="uploadHidCls">
    <div id="uploadedDivComp_0" align="center" class="uploadedCompCls"></div>
    <div id="imageCompName_0" class="imageName"></div><div id="cancelDoc" ></div>
  </div>
</div>`;
	$("#attachComp").html(comp);
}
function newPolicyAllcate() {
	$("#prev6").prop('disabled', true);
	$("#assetemployee1").val();
	$("#assetlocation1").val();
	$("#asdate1").val();
	$("#policyAllocForm").show();
	$("#policyAllocSaveDiv").show();
	$("#newPolicyAllcate").hide();
	$("#myGridAllocatedPolicy").hide();
	$("#myGridAllocatedPolicy").parent().hide();
	$("#policyAllcate, #CancelPolicyAllcate").show();
	$("#policyAllcate").attr("disabled", true);
	$("#asdate1").val("");
	$("#assetlocation1").val("");
	$("#assetemployee1").val("");
	getPolicyList($("#assetId7").val());
	//assetAllocationChange1();
}
function CancelPolicyAllcate() {
	$("#prev6").prop('disabled', false);	
	policyOptions.api.deselectAll();
	$("#policyAllocForm").hide();
	$("#policyAllocSaveDiv").hide();
	$("#newPolicyAllcate").show();
	$("#policyAllcate, #CancelPolicyAllcate").hide();
	$("#myGridAllocatedPolicy").parent().show();
	$("#myGridAllocatedPolicy").show();
	$("#asdate1").val("");
	$("#assetlocation1").val("");
	$("#assetemployee1").val("");
}
// 	delete buttons
function deleteAsset() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].assetId;
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to delete this?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {


			$.ajax({
				type: "POST",
				url: "asset-management-delete?id=" + id,
				success: function(response) {
					if (response.code == "success") {
						toastr.success("Deleted Successfully");
						viewAsset();
						cancelModalBtn();
					} else {
					}
				},
				error: function(data) {
					console.log(data)
				}
			})
		}
	});


}
function deleteWarrenty() {
	$('#warrentydelete').modal('show');
}
function deleteInsure() {
	$('#insuredelete').modal('show');
}
function deleteDocs() {
	$('#documentdelete').modal('show');
}
function cancelModalBtn() {
	$('.modal').modal('hide');
}
// approve functions
/*function approveAsset() {
	//$('#approveModal').modal('show');
}*/
// dissociate assets
function assetDissociate() {
	document.getElementById("dissociateAsset1").style.display = "block";
	document.getElementById("cancelDissociation").style.display = "block";
	$("#assetDissociate").prop('disabled', true);	
	$("#prev4, #next5").prop('disabled', true);
	$("#reasonDiv").show();
}
function cancelDissociate(){	
	document.getElementById("dissociateAsset1").style.display = "none";
	document.getElementById("cancelDissociation").style.display = "none";
	$("#assetDissociate").prop('disabled', false);
	$("#prev4, #next5").prop('disabled', false);
	$("#reasonDiv").hide();	
}
function deallocateAsset() {
    $('.loader').show();  // Show loader

    var assignid = $("#assignedId").val();
    var assetid = $("#assetId5").val();
    var dreason = $("#resaon").val();
    if (!dreason) {
        toastr.error("Reason required !");
        $('.loader').hide();
        return;
    }
console.log("assignid===",assignid,"   assetid===",assetid,"   dreason=====",dreason);
   $.ajax({
        type: "POST",
        url: "asset-management-dissociate",
        data: {
            assignid: assignid,
            assetid: assetid,
            dreason: dreason
        },
        success: function (response) {
            if (response.code === "success") {
                toastr.success("Deallocated Successfully");
                cancelDissociate();
				assetAllocationChange();
                viewAsset(assetid,'assetAllocationId');
            } else {
                toastr.error("Something went wrong!");
            }
            $('.loader').hide();  // Hide loader after completion
        },
        error: function (xhr, status, error) {
            console.error("AJAX error:", error);  // Log the error
            toastr.error("Error in processing request.");
            $('.loader').hide();
        }
    })
}


function assetDeAllocate() {
	$('#assetAllocate').hide();
	$('#assetDissociate').show();
	$('#changeSts').hide();
	$("#assetemployee, #asdate, #assetlocation").prop('disabled', true);
	document.getElementById("DateCalendarAssign").style.display = "none";
	const radioButtons = document.querySelectorAll('input[name="options"]');
	radioButtons.forEach((radio) => {
		radio.disabled = true;
	});
}
// allocate assets
function assetAllocate() {
	$(".formValidation").remove();
	$('#assetAllocate').show();
	$('#changeSts').show();
	$('#assetDissociate').hide();
	$("#assetemployee, #asdate, #assetlocation").prop('disabled', false);
	$("#assetemployee").val("").trigger('change');
	$("#assetlocation").val("").trigger('change');
	$('#asdate').val("");
	$('#asdate').empty();
	document.getElementById("DateCalendarAssign").style.display = "block";
	const radioButtons = document.querySelectorAll('input[name="options"]');
	radioButtons.forEach((radio) => {
		radio.disabled = false;
		radio.checked = false;
	});
}
// FUNCTIONS
function checkEmptyAssign() {
	var selectedValue = $('input[name="options"]:checked').val();
	if (selectedValue == "Employee") {
		var assetemp = $("#assetemployee").val();
	} else if (selectedValue == "Room") {
		var assetemp = $("#assetlocation").val();
	} else if (selectedValue == "Asset") {
		var assetemp = $("#assetAsset").val();
	}
	var assetcat = $('input[name="options"]:checked').val();
	var assigndate = $("#asdate").val();
	if (assetemp && assigndate) {
		$('#warningAssign').hide();
	}
}
function Warrantyfilter() {
	var fromdatex = $("#sdate").val();
	var fd = fromdatex.split("-");
	var todatex = $("#edate").val();
	var td = todatex.split("-");
	var valid = true;

	if (fromdatex != '' && todatex != '') {
		if (fd[2] == td[2]) {
			if (fd[1] == td[1]) {
				if (fd[0] <= td[0]) {
					valid = validationUpdated("", "edate");
				} else {

					$("#sdate").val("");
					$("#edate").val("");
					valid = validationUpdated("End Date should be after Start Date", "edate");
				}
			} else if (fd[1] < td[1]) {
				valid = validationUpdated("", "edate");
			} else {

				$("#sdate").val("");
				$("#edate").val("");
				valid = validationUpdated("End Date should be after Start Date", "edate");
			}
		} else if (fd[2] < td[2]) {
			valid = validationUpdated("", "edate");
		} else {

			$("#sdate").val("");
			$("#edate").val("");
			valid = validationUpdated("End Date should be after Start Date", "edate");
		}
	}
}
function checkWarranty(startDateStr, endDateStr) {
	if (startDateStr && endDateStr) {
		var startDate = new Date(startDateStr.split("-").reverse().join("-"));
		var endDate = new Date(endDateStr.split("-").reverse().join("-"));
		var today = new Date();

		if (today >= startDate && today <= endDate) {
			return "Under Warranty";
		} else if (today > endDate) {
			return "Expired";
		} else {
			return "Upcoming";
		}
	}
	return "Invalid Dates";
}
function Insurancefilter() {
	var fromdatex = $("#isdate").val();
	var fd = fromdatex.split("-");
	var todatex = $("#iedate").val();
	var td = todatex.split("-");
	var valid = true;

	if (fromdatex != '' && todatex != '') {
		if (fd[2] == td[2]) {
			if (fd[1] == td[1]) {
				if (fd[0] <= td[0]) {
					valid = validationUpdated("", "iedate");
				} else {

					$("#isdate").val("");
					$("#iedate").val("");
					valid = validationUpdated("End Date should be after Start Date", "iedate");
				}
			} else if (fd[1] < td[1]) {
				valid = validationUpdated("", "iedate");
			} else {

				$("#isdate").val("");
				$("#iedate").val("");
				valid = validationUpdated("End Date should be after Start Date", "iedate");
			}
		} else if (fd[2] < td[2]) {
			valid = validationUpdated("", "iedate");
		} else {

			$("#isdate").val("");
			$("#iedate").val("");
			valid = validationUpdated("End Date should be after Start Date", "iedate");
		}
	}
}

function showModifyButton(){	
	$('#changeSts').show();
	var workstatus = $("#workstatus").val();

	if(workstatus == 'Damaged' || workstatus == 'Scrapped'){
		$("#stsdescDiv").show();	
	}else{		
		$("#stsdescDiv").hide();	
		$("#assetDescsts").val("");		
	}
}
function assignOnclick() {
	var selectedValue = allocId;
	if (selectedValue == "Employee") {
		var assetemp = $("#assetemployee").val();
		if ( assetemp == null ||  assetemp == "") {
			toastr.error("Assigned Employee is Required");
			return
		}
	} else if (selectedValue == "Room") {
		var assetemp = $("#assetlocation").val();
		if ( assetemp == null ||  assetemp == "") {
			toastr.error("Assigned Property is Required");
			return
		}
	} else if (selectedValue == "Asset") {
		var assetemp = $("#assetAsset").val();
		if ( assetemp == null ||  assetemp == "") {
			toastr.error("Assigned Asset is Required");
			return
		}
	}
	var assetcat = $('input[name="options"]:checked').val();
	var assigndate = $("#asdate").val();
	
	
	if ( assigndate == null ||  assigndate == "") {
		toastr.error("Asset Assign Date is Required");
		return;
	}
	if (assetemp && assigndate) {
		$('#warningAssign').hide();
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "asset-management-assign?id=" + id + "&assetcat=" + assetcat + "&assetemp=" + assetemp + "&assigndate=" + assigndate,
			success: function(response) {
				console.log("response allocate===",response)
				if (response.code == "success") {
					$('.loader').hide();
                toastr.success("Allocated Successfully");
					var id1 = $("#assetId").val();
					assetDeAllocate();
					viewAsset(id1, 'assetAllocationId');
				} else {
					$('.loader').hide();
					$("#messageParagraph").text("Something went to wrong!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	} else {
		$('#warningAssign').html("Please Fill The Required Info !");
		$('#warningAssign').show();

	}

}
function validatePositiveNumber(input) {
    let value = input.value;

    // Remove all characters except digits and a single dot
    value = value.replace(/[^0-9.]/g, '');

    // Only allow one decimal point
    const parts = value.split('.');
    if (parts.length > 2) {
        value = parts[0] + '.' + parts[1]; // Ignore extra dots
    }

    // Remove leading zeroes unless it's "0." format
    if (value !== '' && !value.startsWith('0.')) {
        value = value.replace(/^0+/, '');
    }

    input.value = value;
}
/*function assetDissociate() {
	Swal.fire({
	    title: 'Are you sure?',
	    text: 'Do you want to Deallocate this?',
	    icon: 'warning',
	    showCancelButton: true,
	    confirmButtonText: 'Yes, Deallocate it!',
	    cancelButtonText: 'No, keep it',
	    confirmButtonColor: 'var(--mainColor)',
	    input: 'textarea',
	    inputPlaceholder: 'Enter the reason here',
	    inputAttributes: {
	        'aria-label': 'Enter your reason here'
	    },
	    inputValidator: (value) => {
	        if (!value || $.trim(value) === '') {
	            return 'Reason is required!';
	        }
	    }
	}).then(function (result) {
	    console.log(result);  // Log the result to see what is returned
	    if (result.value) {
	        console.log('Confirmed!');  // Check if this is reached
	        var dreason = result.value;
	        var assignid = $("#assignedId").val();
	        var assetid = $("#assetId5").text();

	        // Ensure all necessary parameters are available
	        console.log("assignid=" + assignid + "&assetid=" + assetid + "&dreason=" + dreason);

	        if (!assignid || !assetid || !dreason) {
	            toastr.error("Missing required parameters!");
	            return;  // Exit if parameters are missing
	        }

	        
	    }
	});

}*/




//function for scarp Asset
function changeSts() {
	workStatus = $("#workstatus").val();
	assetDescsts = $("#assetDescsts").val();
	var id = $("#assetId6").val();

	if (id && workStatus) {

		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "asset-management-scrap?id=" + id + "&status=" + workStatus + "&assetDescsts=" + assetDescsts,
			success: function(response) {
				if (response.code == "success") {
					$('.loader').hide();
					toastr.success("Status Changed Successfully");
					
					viewAsset(id, 'workingStsId');
				} else {
					$('.loader').hide();
					/*$("#messageParagraph").text("Something went wrong");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');*/
					toastr.success("Something went wrong");
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	} else {
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Choose atleast one status";
		$('.loader').hide();
	}

}