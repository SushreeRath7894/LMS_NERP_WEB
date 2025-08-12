$(()=>{
	$('#locationType').select2();  //#locationType
	$('#locCountry').select2();  //#locCountry
	$('#locState').select2();  //#locState
	$('#locCity').select2();  //#locCity
	$('#locOwnership').select2();  //#locOwnership
	$('#ownerCountry').select2();  //#ownerCountry
	$('#ownerState').select2();  //#ownerState
	$('#ownerCity').select2();  //#ownerCity
	$('#rentStatus').select2();  //#rentStatus
});

$(document).ready(function() {
    var dateFormat = localStorage.getItem("dateFormat");
    $("#DateCalendar1").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
 /*       minDate: new Date().toLocaleDateString(undefined, {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
        }),*/
    }).on("change", function() {
        $('#sdate').val($(this).val());
    })
    $('#sdate').blur(function() {
        $("#DateCalendar1").val($(this).val());
    })
    $("#DateCalendar2").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
    /*    timepicker: false,
        minDate: new Date().toLocaleDateString(undefined, {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
		}),*/
    }).on("change", function() {
        $('#edate').val($(this).val());
    })
    $('#edate').blur(function() {
        $("#DateCalendar2").val($(this).val());
    })
    $("#DateCalendar3").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
       /* minDate: new Date().toLocaleDateString(undefined, {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
		}),*/
    }).on("change", function() {
        $('#pdate').val($(this).val());
    })
    $('#pdate').blur(function() {
        $("#DateCalendar3").val($(this).val());
    })
    $("#DateS").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    /*    minDate: new Date().toLocaleDateString(undefined, {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
		}),*/
    }).on("change", function() {
        $('#rentSDate').val($(this).val());
    })
    $('#rentSDate').blur(function() {
        $("#DateS").val($(this).val());
    })
    $("#DateE").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
     /*   minDate: new Date().toLocaleDateString(undefined, {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit'
		}),*/
    }).on("change", function() {
        $('#rentEDate').val($(this).val());
    })
    $('#rentEDate').blur(function() {
        $("#DateE").val($(this).val());
    })
    
    $('#quickFilter').on('keypress', function(e) {
		    if (e.which === 13) { 
		    	onQuickFilterChanged();
		    }
		});

    var gridDivBuild = document.querySelector('#myGridBuild');
    new agGrid.Grid(gridDivBuild, gridOptionsBuild);
	var gridDivRent = document.querySelector('#myGridLeaseRent');
	new agGrid.Grid(gridDivRent, gridRentOptions);
	var gridDivOwner = document.querySelector('#myGridOwner');
	new agGrid.Grid(gridDivOwner, gridOwnerOptions);
	var gridDivBuildingDocs = document.querySelector('#myGridBuildingDocs');
	new agGrid.Grid(gridDivBuildingDocs, gridOptionsBuildingDocs);
 	var gridDivFloor = document.querySelector('#myGridFloorDets');
	new agGrid.Grid(gridDivFloor, myGridFloorDetsDets);
 	var gridDivFloor = document.querySelector('#myPaymentGrid');
	new agGrid.Grid(gridDivFloor, gridOptionsPayment);
  	   /*	var gridDivPreventive = document.querySelector('#myGridPreventive');
		new agGrid.Grid(gridDivPreventive, gridOptionsPreventive);
		var gridDivAsset = document.querySelector('#myGridAsset');
		new agGrid.Grid(gridDivAsset, gridOptionsAsset);
		*/
  //  ShowAgGrid('buildingManage');
    ownershipChange();
 //   BuildingEdit('');
	viewBuilding();	
	cancelProp();
});


/*				Grid Detailsss				*/
// 			Building Details grid	starts
var columnDefs = [{
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
		pinned: 'left',
        resizable: true
    },
    {
        headerName: "ID",
        field: "locationId",
		pinned: 'left',
        /*cellRenderer: function(params) {
            return '<a onclick=openDetails("' + params.data.locationId +
                '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> ' +
                params.data.locationId + '</a>';
        }*/
    },
    {
        headerName: "Code",
        field: "locationCode",
        hide: true,
        cellRenderer: function(params) {
            return 'Location';
        }
    }, {
        headerName: "Name",
        field: "locationName",
		pinned: 'left',
    }, {
        headerName: "Type",
        field: "locationType"
    }, {
        headerName: "Address",
        field: "locStreet"
    }, {
        headerName: "City",
        field: "locCity"
    }, {
        headerName: "Ownership",
        field: "locOwnership"
    }, {
        headerName: "State",
        field: "locState"
    }, {
        headerName: "Country",
        field: "locCountry"
    }, {
        headerName: "Height",
        field: "locHeight"
    }, {
        headerName: "Width",
        field: "locWidth"
    }, {
        headerName: "Length",
        field: "locLength"
    }, {
        headerName: "Status",
        field: "locStatus",
        cellStyle: {
            textAlign: 'center'
        }
    }, {
        headerName: "Create Date",
        field: "createdDate",
        cellStyle: {
            textAlign: 'center'
        }
    }
];

var gridOptionsBuild = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 149,
        height: 10
    },
    pagination: true,
	  paginationPageSize: 15,
    onSelectionChanged: onSelectionChanged,
	paginationAutoPageSize: true,
	pagination: true,
    getRowNodeId: function(data) {
        return data.locationId;
    }
};
var locSel='';
function onSelectionChanged() {
    var selectedNodes = gridOptionsBuild.api.getSelectedNodes();
    var selectedData = selectedNodes.map(node => node.data);
    treq = selectedData.map(node => node.locationId);
	stateId = '';
	cityId = '';
	if(treq != locSel ){
		nextTab('detsTab');
	}	
    var selectedRows = gridOptionsBuild.api.getSelectedRows();
    id = "";
    for (var i = 0; i < selectedRows.length; i++) {
        id = selectedRows[0].locationId ;
    }
    var rowCount = 0;
    selectedRows.forEach(function(i) {
        rowCount = rowCount + 1;
    });
    var uid = $("#sessionId").val();
    if (rowCount > 0) {
		$('#locDataId').val(id);
		getDets();
		onSelectionChangedOfOwner();
		onSelectionChangedOfRent();
		onSelectionChangedOfBuildDocs();
		onSelectionChangedOfFloor();
		
        if (selectedRows[0].locOwnership == "Own") {
			$('#owner').show(); 
			$('#leaseRent').hide();
        } else if (selectedRows[0].locOwnership == "Rent" || selectedRows[0].locOwnership == "Lease") {
			$('#owner').hide(); 
			$('#leaseRent').show();
        } else {
        }

    } else { 
		clearBuildForm();
	}
}
// 			Building Details grid	ends


// owner grid starts
	var activityOwnerDefs = [
		{
			headerCheckboxSelection : false,
			headerCheckboxSelectionFilteredOnly : false,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			pinned : 'left',
			resizable : true,
			checkboxSelection: function (params) {
                const status = params.data.status;
                return status!=1;
            }

		},
		{
			headerName : "Owner ID",
			field : "OwnerId",
			cellStyle : {
			textAlign : 'center'
		},/*cellRenderer : function(params) {
			return '<a onclick=editOwnerDetails("' + params.data.OwnerId
			+ '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> '
			+ params.data.OwnerId + '</a>';
			}*/
		},{
			headerName : "Owner Name",
			field : "ownerName",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "Address",
			field : "ownerStreet",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "City",
			field : "ownerCityName",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "State",
			field : "ownerStateName",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "Country",
			field : "ownerCountryName",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "Pincode",
			field : "ownerPincode",
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "Contact Number",
			field : "ownerContact",
			cellStyle : {
				textAlign : 'center'
			}
		}, {
			headerName : "Email",
			field : "ownerEmail",
			cellStyle : {
				textAlign : 'center'
			}
	/*	}, {
			headerName : "Attachments",
			field : "attachment",
			cellStyle : {
				textAlign : 'center'
			},cellRenderer : function(params) {
				if (params.data.attachment == "NA") {
					return '<div style="color:#ff8242">Not Available</div>';
					
				} else {
					return '<a id="id" onclick=viewAttachmentModal("'
					+ params.data.attachment + '") href="javascript:void(0)">'
					+ '<i class="bi bi-file-zip"> VIEW</i>'
					+ '</a>';
				}
			},*/
		},{
			headerName : 'Status',
			field : "ownerStatus",
			cellStyle : {
				textAlign : 'center'
			},cellRenderer : function(params) {
				if(params.data.ownerStatus=="Active"){
					return '<div style="color:#0642f5">Active</div>';
				}else if(params.data.ownerStatus=="Inactive"){
					return '<div style="color:#D2042D">Inactive</div>';
				}
			}
		}
		];


// let the grid know which columns and what data to use product table
var gridOwnerOptions = {
	columnDefs : activityOwnerDefs,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		width : 202
	},
	onSelectionChanged : onSelectionChangedOfOwner

};

var stateId1 = '';
var cityId1 = '';
function onSelectionChangedOfOwner(){
	 var selectedRows = gridOwnerOptions.api.getSelectedRows();
	 var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	stateId1 = '';
	cityId1 = '';
	if (rowCount > 0) {
		$(".br-dis").attr("disabled", false);
		//$(".br-m-btn").attr("disabled", true);
		$('#newOwner').attr('disabled', true);
		$('#editOwner').attr('disabled', false);
		$('#deleteOwner').attr('disabled', false);
	} else {
		$(".br-dis").attr("disabled", true);
		$(".br-m-btn").attr("disabled", false);
		$('#newOwner').attr('disabled', false);
		$('#editOwner').attr('disabled', true);
		$('#deleteOwner').attr('disabled', true);
	}
}
// owner grid ends
// rent grid starts

var activityRentDefs = [
	{
		headerCheckboxSelection : false,
		headerCheckboxSelectionFilteredOnly : false,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		pinned : 'left',
		resizable : true,
		checkboxSelection: function (params) {
            const status = params.data.status;
            return status!=1;
        }

	},
	{
		headerName : "Lease/Rent ID",
		field : "OwnerId",
		cellStyle : {
		textAlign : 'center'
	},/*cellRenderer : function(params) {
		if(params.data.type=="Rent"){
			return '<a onclick=editRentDetails("' + params.data.OwnerId
			+ '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> '
			+ params.data.OwnerId + '</a>';
		}else if(params.data.type=="Lease"){
			return '<a onclick=editLeaseDetails("' + params.data.OwnerId
			+ '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> '
			+ params.data.OwnerId + '</a>';
		}
		
		}*/
	},{
			headerName : "Owner Name",
			field : "ownerName",
			cellStyle : {
				textAlign : 'center'
			},
		},{
		headerName : "Start Date",
		field : "rentSDate",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "End Date",
		field : "rentEDate",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Security  Deposit",
		field : "rentSecDeposit",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Rental/Month",
		field : "rentRentPMonth",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Rent Due Date",
		field : "rentDueDate",
		hide:true,
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Bank Name",
		field : "rentBankName",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Account No",
		field : "RentAcNo",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "IFSC Code",
		field : "rentIFSC",
		cellStyle : {
			textAlign : 'center'
		}
	}, {
		headerName : "Attachments",
		field : "attachment",
		cellStyle : {
			textAlign : 'center'
		},cellRenderer : function(params) {
			if (params.data.attachment == "NA") {
				return '<div style="color:#ff8242">Not Available</div>';
				
			} else {
				return '<a id="id" onclick=viewAttachmentModal("'
				+ params.data.attachment + '") href="javascript:void(0)">'
				+ '<i class="bi bi-file-zip"> VIEW</i>'
				+ '</a>';
			}
		},
	}, {
			headerName : 'Status',
			field : "ownerStatus",
			cellStyle : {
				textAlign : 'center'
			},cellRenderer : function(params) {
				if(params.data.ownerStatus=="Active"){
					return '<div style="color:#0642f5">Active</div>';
				}else if(params.data.ownerStatus=="Inactive"){
					return '<div style="color:#D2042D">Inactive</div>';
				}
			}
		}
	];


//let the grid know which columns and what data to use product table
var gridRentOptions = {
	columnDefs : activityRentDefs,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		width : 202
	},
	onSelectionChanged : onSelectionChangedOfRent
};
function onSelectionChangedOfRent(){
	 var selectedRows = gridRentOptions.api.getSelectedRows();
	 var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#newRent').attr('disabled', true);
		$(".br-dis").attr("disabled", false);
		//$(".br-m-btn").attr("disabled", true);
		$('#newRent').attr('disabled', true);
		$('#editRent').attr('disabled', false);
		$('#deleteRent').attr('disabled', false);
	} else {
		$(".br-m-btn").attr("disabled", false);
		$('#newRent').attr('disabled', false);
		$(".br-dis").attr("disabled", true);
		$('#editRent').attr('disabled', true);
		$('#deleteRent').attr('disabled', true);
	}
}
// rent grid endss
// BuildDocs grid starts
function viewImage(id) {
    window.open(id, '_blank');
}
var activityBuildDocsDefs = [
	{
		headerCheckboxSelection : false,
		headerCheckboxSelectionFilteredOnly : false,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		pinned : 'left',
		resizable : true,
		checkboxSelection: function (params) {
            const status = params.data.status;
            return status!=1;
        }
	}, {
		headerName : "Property ID",
		field : "locationId",
		cellStyle : {
			textAlign : 'center'
		},
		hide : true,
	}, {
		headerName : "slNo Name",
		field : "slNo",
		hide : true,
	}, {
		headerName : "Document Name",
		field : "docName",
	}, {
		headerName : "Document",
		field : "docUrl",
		cellStyle : {
			textAlign : 'center'
		},
		cellRenderer: function(params) {
			if (params.data.docUrl != null && params.data.docUrl != "null") {
				return '<a href="' + params.data.docUrl + '" target="_blank"><i class="bi bi-file-earmark-image">View</i></a>';
			} else {
					return "Not Available";
			}
		},
	}
	];


//let the grid know which columns and what data to use product table
var gridOptionsBuildingDocs = {
columnDefs : activityBuildDocsDefs,
rowSelection : 'single',
groupSelectsChildren : true,
suppressRowClickSelection : true,
suppressAggFuncInHeader : true,
defaultColDef : {
	flex : 1
},
onSelectionChanged : onSelectionChangedOfBuildDocs

};
function onSelectionChangedOfBuildDocs(){
	
	 var selectedRows = gridOptionsBuildingDocs.api.getSelectedRows();
	 var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	//cancelDocument();
	if (rowCount > 0) {false
		$(".br-dis").attr("disabled", false);
		$(".br-m-btn-d").attr("disabled", true);
		$('#newDocument').attr('disabled', true);
		$('#deleteDocument').attr('disabled', false);
	} else {
		$(".br-dis").attr("disabled", true);
		$(".br-m-btn-d").attr("disabled", false);
		$('#newDocument').attr('disabled', false);
		$('#deleteDocument').attr('disabled', true);
	}
}
// bildDocuments grid ends

// floor grid starts
	var activityfloorDefs = [
		{
			headerCheckboxSelection : false,
			headerCheckboxSelectionFilteredOnly : false,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			pinned : 'left',
			resizable : true,
			checkboxSelection: function (params) {
                const status = params.data.status;
                return status!=1;
            }

		}, {
			headerName : "SL No.",
			field : "floorSlNo",
			cellStyle : {
				textAlign : 'center'
			},
	//		hide:true
	//		width:80,
		},{
			headerName : "Floor ID",
			field : "floorId",
			cellStyle : {
				textAlign : 'center'
			},
	//		width:100,
			/*cellRenderer : function(params) {
			return '<a onclick=editfloorDetails("' + params.data.floorId
			+ '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i> '
			+ params.data.floorId + '</a>';
			}*/
		}, { 	
			headerName : "Floor Code",
			field : "floorCode",
			cellStyle : {
				textAlign : 'center'
			},
	//		width:100,
		},{
			headerName : "Floor Name",
			field : "floorName",
			cellStyle : {
				textAlign : 'center'
			},
	//		width:180,
		}, { 	
			headerName : "Floor Height",
			field : "height",
			cellStyle : {
				textAlign : 'center'
			},
	//		width:110,
		},{
			headerName : "Floor Width",
			field : "width",
			cellStyle : {
				textAlign : 'center'
			},
	//		width:110,
		}, { 	
			headerName : "Floor Length",
			field : "length",
			cellStyle : {
				textAlign : 'center'
			},
	//		width:110,
		},
		];


// let the grid know which columns and what data to use product table
var myGridFloorDetsDets = {
	columnDefs : activityfloorDefs,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		width : 202
	},
	onSelectionChanged : onSelectionChangedOfFloor

};

function onSelectionChangedOfFloor(){
	 var selectedRows = myGridFloorDetsDets.api.getSelectedRows();
	 var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$(".br-dis").attr("disabled", false);
		$(".br-m-btn").attr("disabled", true);
		$('#newFloor').attr('disabled', true);
		$('#editRent').attr('disabled', false);
		$('#deleteFloor').attr('disabled', false);
	} else {
		$(".br-dis").attr("disabled", true);
		$(".br-m-btn").attr("disabled", false);
		$('#newFloor').attr('disabled', false);
		$('#editRent').attr('disabled', true);
		$('#deleteFloor').attr('disabled', true);
	}
}
// floor grid ends
// payment grid

var coldesPayment = [
	{
		headerCheckboxSelection : false,
		headerCheckboxSelectionFilteredOnly : false,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		pinned : 'left',
		resizable : true,
		checkboxSelection: function (params) {
            const status = params.data.status;
            return status!=1;
        }

	},
	{
		headerName : "Rent Date",
		field : "rentDate",
		cellStyle : {
		textAlign : 'center'
		},
	}, {
		headerName : "Rent Paid Date",
		field : "payDate",
	}, {
		headerName : "Ammont",
		field : "rentAmt",
	}, {
		headerName : "Status",
		field : "status",
		cellStyle : {
			textAlign : 'center'
		},
	}
	];


//let the grid know which columns and what data to use product table
var gridOptionsPayment = {
columnDefs : coldesPayment,
rowSelection : 'single',
groupSelectsChildren : true,
suppressRowClickSelection : true,
suppressAggFuncInHeader : true,
defaultColDef : {
	width : 202
},
onSelectionChanged : onSelectionChangedOfBuildDocs

};
// document sec

	function saveMultiFile(event) {
		var AssignItemQty = event.currentTarget.value;
		var currentFldId = event.currentTarget.getAttribute('id');
		var l = currentFldId.split("_");
		var counter = l[1];
		var currentFldId = "#" + currentFldId;

		var uFile = $(currentFldId)[0].files[0];
		var fileName = event.currentTarget.value;
		var lastIndex = fileName.lastIndexOf("\\");
		if (lastIndex >= 0) {
			fileName = fileName.substring(lastIndex + 1);
		}
		var extension = fileName.split(".").pop();

		var iURL = URL.createObjectURL(uFile);
		$("#uploadedBillDiv_" + counter).html("");

			if (extension == 'pdf' || extension == 'jpg' || extension == 'png'
					|| extension == 'xls' || extension == 'doc') {
				window.st = 1;
			} else {
				$("#messageParagraph").text("Pick A Valid Document File");
				$("#msgModal").modal('show');
				window.st = 0;

				var lengthOfTableRow1 = 0;
				$("#docTbl > #doctbodyData > tr").each(function() {
					lengthOfTableRow1 = lengthOfTableRow1 + 1;
				})
				var id = $("#dltValue").val();
				$("#" + id).closest('tr').remove();
				if (lengthOfTableRow1 == 1) {
					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0"  onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" ></div></td>'
							+ '<td class="d-flex gap-2 align-items-center"> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
							+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
							+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"  ></div> </td>'
							+ '</tr>';
					$("#doctbodyData").append(tbl);
				}
			}

		
		if (extension != null && extension != "") {
			$("#uploadHidden_" + counter).val('');
		}
		if (extension == "jpg" || extension == "png" || extension == "jpeg") {
			var LightImg = "<span class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-image'></i></a></span>";
		} else if (extension == "pdf") {
			var LightImg = "<span class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-pdf'></i> </a></span>";
		} else if (extension == "xls" || extension == "xlsx") {
			var LightImg = "<span class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-excel'></i></a></span>";
		} else if (extension == "doc" || extension == "dox"
				|| extension == "docx") {
			var LightImg = "<span class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-word'></i></a></span>";
		} else if (extension == "mp4" || extension == "MOV"
				|| extension == "MP4") {
			var LightImg = "<span class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-solid fa-video-camera'></i></a></span>";
		} else if (extension == "mp3" || extension == "MP3"
				|| extension == "WAV" || extension == "AAC") {
			var LightImg = "<span class='uploadicon m-0 p-0'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa-solid fa-file-music'></i></a></span>";
		} else {
			var LightImg = "<span class='uploadicon m-0 p-0'> </div>";
		}
		var dltImg = "<i class='ti-close rmv1' onclick='openDeleteConfirm()'></i>";
		$("#uploadedBillDiv_" + counter).html(LightImg);
		$("#imageName_" + counter).html(fileName);
		$("#dltImage_" + counter).html(dltImg);
		$("#dltImage_" + counter).addClass("custom-file-delete m-0 p-0 lh-sm");

		$("#clickImg_" + counter).removeClass("ti-plus");
		$("#clickImg_" + counter).addClass("ti-pencil");
		
//		savePropertyDocumentation();

	}
	function openDeleteConfirm() {
		
		var lengthOfTableRow1 = 0;
 		$("#docTbl > #doctbodyData > tr").each(function() {

			lengthOfTableRow1 = lengthOfTableRow1 + 1;
		})
		var id = $("#dltValue").val();
		$("#"+id).closest('tr').remove();
		$("#doctbodyData").empty();
		if (lengthOfTableRow1 == 1) {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" ></div></td>'
				+ '<td class="d-flex gap-2 align-items-center"> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
		}
	}

	// document sec ends
// address drop-down list

function stateList(sid = null) {
    var country = $("#locCountry").val();
    if (country) {
        $.ajax({
            type: "POST",
            url: "building-management-get-state-list",
            dataType: 'json',
            contentType: 'application/json',
            data: country,
            success: function(response) {
                console.log("Response state list:", response);
                if (response.message == "success") {
                    console.log(response);
                    $("#locState").empty();
                    $("#locState").append("<option value=''>Select</option>");
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#locState").append(option);
                    }
                }
                if (stateId != null) {
                    $("#locState").val(stateId).trigger('change');
                }
            },
            error: function(data) {
                console.log(data);
                $("#locState").empty();
            }
        })
    } else {
        $("#locState").empty();
    }
}

function cityList(sid = null, cid = null) {
    if (sid != null) {
        var city = sid
    } else {
        var city = $("#locState").val();
    }
    if (city) {
        $.ajax({
            type: "POST",
            url: "building-management-get-city-list",
            dataType: 'json',
            contentType: 'application/json',
            data: city,
            success: function(response) {
                console.log("Response city List:", response);
                if (response.message == "success") {
                    console.log(response);
                    $("#locCity").empty();
                    $("#locCity").append("<option value=''>Select</option>");
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#locCity").append(option);
                    }
                }
                if (cityId != null) {
                    $("#locCity").val(cityId);
                }
            },
            error: function(data) {
                console.log(data);
                $("#locCity").empty();
            }
        })
    } else {
        $("#locCity").empty();
    }
}
// owner drop-down
function stateListForOwner(sid = null) {
    var country = $("#ownerCountry").val();
    if (country) {
        $.ajax({
            type: "POST",
            url: "building-management-get-state-list",
            dataType: 'json',
            contentType: 'application/json',
            data: country,
            success: function(response) {
                console.log("Response data:", response);
                if (response.message == "success") {
                    console.log(response);
                    $("#ownerState").empty();
                    $("#ownerState").append("<option value=''>Select</option>");
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#ownerState").append(option);
                    }
                }
                if (stateId1 != null) {
                    $("#ownerState").val(stateId1).trigger('change');
                }
            },
            error: function(data) {
                console.log(data);
                $("#ownerState").empty();
                $("#ownerState").append("<option value=''>Select</option>");
            }
        })
    } else {
        $("#ownerState").empty();
        $("#ownerState").append("<option value=''>Select</option>");
    }
}
function cityListForOwner(sid = null, cid = null) {
    if (sid != null) {
        var city = sid
    } else {
        var city = $("#ownerState").val();
    }
    if (city) {
        $.ajax({
            type: "POST",
            url: "building-management-get-city-list",
            dataType: 'json',
            contentType: 'application/json',
            data: city,
            success: function(response) {
                console.log("Response data:", response);
                if (response.message == "success") {
                    console.log(response);
                    $("#ownerCity").empty();
                    $("#ownerCity").append("<option value=''>Select</option>");
                    for (var i = 0; i < response.body.length; i++) {
                        var option = $("<option></option>");
                        $(option).val(response.body[i].key);
                        $(option).html(response.body[i].name);
                        $("#ownerCity").append(option);
                    }
                }
                if (cityId1 != null) {
                    $("#ownerCity").val(cityId1).trigger('change');
                }
            },
            error: function(data) {
                console.log(data);
                $("#ownerCity").empty();
                $("#ownerCity").append("<option value=''>Select</option>");
            }
        })
    } else {
        $("#ownerCity").empty();
        $("#ownerCity").append("<option value=''>Select</option>");
    }
}

// view Data Functions
function viewBuilding(id=null,tab=null){
	agGrid.simpleHttpRequest({
		url : 'building-management-view'
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData=jsonData.Property;
	 	if(allData!=null){
		 	var len = allData.length;
			$('#totalReq').find('span').html(len);
			gridOptionsBuild.api.setRowData(allData);
			if (id != null) {
				locSel = id;
			    gridOptionsBuild.api.forEachNode(function(node) {
				 if (String(node.data.locationId) === String(id)) {					
			           node.setSelected(true);
			        }
			    });
			} else {	
				console.log("else")
				locSel = '';		
				var firstRowNode = gridOptionsBuild.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}
			}
			if(tab){
				nextTab(tab);
			}else{
				nextTab('detsTab');
			}
		}else{
			$('#totalReq').find('span').html("0");
			gridOptionsBuild.api.setRowData("");
		}
		
	});
	
	cancelOwner();
	cancelRent();
	cancelFloor();
	cancelDocument();
	
}
function nextTab(id) {
	console.log(id);
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
	getDets();
}
function viewRentOwnerDets(type,id){
		agGrid.simpleHttpRequest(
				{
					url : "building-management-report-list?type="+ type+"&id="+id
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					console.log("jsonData",jsonData)
					var allData=jsonData.Asset;
					var allOwner=jsonData.Owner;
					var allRent=jsonData.Rent;
					var allLease=jsonData.Lease;
					var selectedRows = gridOptionsBuild.api.getSelectedRows();
						if(selectedRows[0].locOwnership=="Own"){
							if(allOwner!=null){
								gridOwnerOptions.api.setRowData(allOwner);
								gridRentOptions.api.setRowData("");
							}else{
								gridOwnerOptions.api.setRowData("");
							}
						}else if(selectedRows[0].locOwnership=="Rent" || selectedRows[0].locOwnership=="Lease"){
							if(allRent!=null){
								gridRentOptions.api.setRowData(allRent);
								gridOwnerOptions.api.setRowData("");
							}else{
								gridRentOptions.api.setRowData("");
							}
						}
				});
	}
function viewPropertyDocs(id){
		agGrid.simpleHttpRequest(
				{
					url : "building-management-view-document?id="+id
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					console.log("jsonData",jsonData)
					var allData=jsonData.documents;
					console.log("doc data=====",allData);
				//	var selectedRows = gridOptionsBuild.api.getSelectedRows();
							if(allData!=null){
								gridOptionsBuildingDocs.api.setRowData(allData);
							}else{
								gridOptionsBuildingDocs.api.setRowData("");
							}
							onSelectionChangedOfBuildDocs();
				});
	}
	// view floor data start
	function showFloorDetss(id) {
		$.ajax({
			type : "POST",
			url : "building-management-floor-details",
			dataType : 'json',
			contentType : 'application/json',
			data : id,
			success : function(response) {
				if (response.code == "success") {
					var jsonData = JSON.parse(response.body);
					var allData=jsonData.Property;
					console.log("MY RESPONSE DATA::",allData)
					if(allData!=null){
						myGridFloorDetsDets.api.setRowData(allData);	
					}else{
						myGridFloorDetsDets.api.setRowData("");
					}
				}},
				error : function(data) {
				}
		});
		addFloor();
		cancelFloor();
	}
	
	// view flooe data ends
function editRentDetails(){
		addRent();
		var selectedRows1 = gridRentOptions.api.getSelectedRows();
		id = selectedRows1[0].OwnerId;
		var selectedRow="";
		gridRentOptions.api.forEachNode(function(rowNode, index) {
			if (rowNode.data.OwnerId ==id) {
				selectedRow = rowNode;
			}
		 });
		console.log("selectedRow",selectedRow);
		$("#leaseRentId").html(selectedRow.data.OwnerId);
		$("#ownerId").val(selectedRow.data.OwnerId);
		$("#ownerName1").val(selectedRow.data.ownerName);
		$("#rentSDate").val(selectedRow.data.rentSDate);
		$("#rentEDate").val(selectedRow.data.rentEDate);
		$("#rentSecDeposit").val(selectedRow.data.rentSecDeposit);
		$("#rentRentPMonth").val(selectedRow.data.rentRentPMonth);
		$("#rentBankName").val(selectedRow.data.rentBankName);
		$("#rentIFSC").val(selectedRow.data.rentIFSC);
		$("#rentStatus").val(selectedRow.data.ownerStatus).trigger('change');
		$("#rentAcNo").val(selectedRow.data.RentAcNo);
	}
function editOwnerDetails(){
		addOwner();
		var selectedRows1 = gridOwnerOptions.api.getSelectedRows();
		id = selectedRows1[0].OwnerId;
		var selectedRow="";
		gridOwnerOptions.api.forEachNode(function(rowNode, index) {
			if (rowNode.data.OwnerId ==id) {
				selectedRow = rowNode;
			}
		 });
		console.log("selectedRow",selectedRow)
		$("#ownerId").val(selectedRow.data.OwnerId);
		$("#ownerName").val(selectedRow.data.ownerName);
		$("#ownerCountry").val(selectedRow.data.ownerCountry).trigger('change');
		$("#ownerStreet").val(selectedRow.data.ownerStreet).trigger('change');
		$("#ownerPincode").val(selectedRow.data.ownerPincode).trigger('change');
		$("#ownerContact").val(selectedRow.data.ownerContact);
		$("#ownerEmail").val(selectedRow.data.ownerEmail);
		$("#ownerStatus").val(selectedRow.data.ownerStatus);
		stateId1=selectedRow.data.ownerState;
		cityId1=selectedRow.data.ownerCity;
	//	stateListForOwner(selectedRow.data.ownerState);
	//	cityListForOwner(selectedRow.data.ownerState,selectedRow.data.ownerCity);
	}

function editfloorDetails(id){
		editFloor();
		var selectedRows1 = myGridFloorDetsDets.api.getSelectedRows();
		id = selectedRows1[0].floorId;
		var selectedRow="";
		myGridFloorDetsDets.api.forEachNode(function(rowNode, index) {
			if (rowNode.data.floorId == id) {
				selectedRow = rowNode;
			}
		 });
		console.log("selectedRow==",selectedRow.data)
		$("#floorIdData").html(selectedRow.data.floorId);
		$("#floorId").val(selectedRow.data.floorCode);
		$("#floorName").val(selectedRow.data.floorName);
		$("#floorDBVal").val(selectedRow.data.floorId);
		$("#floorSlNoDBVal").val(selectedRow.data.floorId);
		$("#height").val(selectedRow.data.height);
		$("#width").val(selectedRow.data.width);
		$("#length").val(selectedRow.data.length);
}

// delete modal functionssss
function deleteProp1(){
	$('#deleteLocation').modal('show');
}

function canceldeleteProp() {
	$('.modal').modal('hide');
}
function deleteOwner(type){
 	if(type=="rent"){
		 $("#typeToDelete").val("Rent");
	}else{
		 $("#typeToDelete").val("Owner");
	}
	$("#deleteOwnerModal").modal('show');
}
function deleteDocument(){
	$('#deleteDocumentModal').modal('show');
}
function deleteFloor(){
	$('#deleteFloorModal').modal('show');
}
// deleete main functions
function deleteProperty() {
		 
	 	var selectedRows = gridOptionsBuild.api.getSelectedRows();
	 	var id = selectedRows[0].locationId;
	 	$.ajax({
	 		type : "POST",
	 		url : "building-management-delete?id=" + id,
	 		success : function(response) {
	 			if (response.code == "success") {

					toastr.success(response.message)
	 				$('#deleteLocation').modal('hide');

	 			//	$('#deleteProp').attr("disabled", true);
	 				viewBuilding();

	 			} else {

	 			}
	 		},
	 		error : function(data) {
	 			console.log(data)
	 		}
	 	})
	 }
function deleteOwnerOnclick(type) {
	//		 var type=$("#typeToDelete").val();
			 if(type=="owner"){
				 var selectedRows = gridOwnerOptions.api.getSelectedRows();
				 var id=selectedRows[0].OwnerId;
			 }else{
				 var selectedRows = gridRentOptions.api.getSelectedRows();
				 var id=selectedRows[0].OwnerId;
			 }
				if (id) {
					$('.loader').show();
						$.ajax({
				type : "POST",
				url : "building-management-delete-owner?id="+ id,
				success : function(response) {
				 if (response.code == "success") {
					 	$('.loader').hide();
						toastr.success(response.message);
						var selectedRows = gridOptionsBuild.api.getSelectedRows();
						viewRentOwnerDets(type,selectedRows[0].locationId);
						nextTab('ownerTab')
						} else {
						$('.loader').hide();
						toastr.success("Something Went Wrong");
					}
				},
				error : function(data) {
					$('.loader').hide();}
			})
				} else {
					$("#alert").modal('show');
					document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
					$('.loader').hide();}

		}
function deleteDocsOnclick() {
		 
	 	var selectedRows = gridOptionsBuildingDocs.api.getSelectedRows();
	 	var id = selectedRows[0].slNo;
		/*for (var i = 0; i < selectedRows.length; i++) {
	        id = id + '"' + selectedRows[0].slNo + '",';
	    }*/
	 	if (id) {
			$.ajax({
		 		type : "POST",
		 		url : "building-management-delete-documents?id=" + id,
		 		success : function(response) {
		 			if (response.code == "success") {
					 	$('.loader').hide();
					 	
					    var selectedRows1 = gridOptionsBuild.api.getSelectedRows();
						viewPropertyDocs(selectedRows1[0].locationId);
						canceldeleteProp();
						toastr.success(response.message)
						$('#deleteOwnerModal').modal('hide');
						
						} else {
						$('.loader').hide();
						toastr.success("Something Went Wrong")
						$('#deleteOwnerModal').modal('hide');
					}
		 		},
		 		error : function(data) {
		 			console.log(data)
		 		}
		 	})
		} else {
			$("#alert").modal('show');
			document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
			$('.loader').hide();
		}	
	}


function deleteFloorOnclick(){
	 	var selectedRows = myGridFloorDetsDets.api.getSelectedRows();
	
	 	var id = selectedRows[0].floorId;
	if (id) {
			$.ajax({
		 		type : "POST",
		 		url : "building-management-delete-floor?id=" + id,
		 		success : function(response) {
		 			if (response.code == "success") {
					 	$('.loader').hide();  
						var selectedRows = gridOptionsBuild.api.getSelectedRows();
					 	showFloorDetss(selectedRows[0].locationId);
					//	canceldeleteProp();
						toastr.success(response.message)
						$('#deleteOwnerModal').modal('hide');
						
					} else {
						$('.loader').hide();
						toastr.success("Something Went Wrong")
						$('#deleteOwnerModal').modal('hide');
					}
		 		},
		 		error : function(data) {
		 			console.log(data)
		 		}
		 	})
		} else {
			$("#alert").modal('show');
			document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
			$('.loader').hide();
		}
}
// button functionss
function getDets(){
	var id=$('#locDataId').val();
	if(id){
		BuildingEdit(id);
		viewRentOwnerDets('Location',id);
		viewPropertyDocs(id);
		showFloorDetss(id);
	}
	cancelProp();
	cancelOwner();
	cancelRent();
	cancelFloor();
	cancelDocument();
}
function cancelProp(){
	$('#locationName, #locationType, #locCountry, #locState, #locCity, #locStreet, #locPincode, #locHeight, #locLength, #locWidth, #locOwnership,  #locStatus, #locDescription, #locRent, #edate, #sdate, #pdate').attr('disabled', true);
	$('#DateCalendar1').hide();
	$('#DateCalendar2').hide();
	$('#DateCalendar3').hide();
	$('#delFileImg').hide();
	$('#addFileImg').hide();
	$('#newProp').show();
	$('#deleteProp').show();
	$('#saveProp').hide();
	$('#cancelProp').hide();
	$('#editProp').show();
	$('#next1').show();
	
	$('#ownerTab').show();
	$('#documentTab').show(); 
	$('#floorlayTab').show();
}
function editProp(){
	$('#locationName, #locationType, #locCountry, #locState, #locCity, #locStreet, #locPincode, #locHeight, #locLength, #locWidth, #locOwnership,  #locStatus, #locDescription, #locRent, #edate, #sdate, #pdate').attr('disabled', false);
	$('#DateCalendar1').show();
	$('#DateCalendar2').show();
	$('#DateCalendar3').show();
	$('#delFileImg').show();
	$('#addFileImg').show();
	$('#newProp').hide();
	$('#deleteProp').hide();
	$('#editProp').hide();
	$('#saveProp').show();
	$('#cancelProp').show();
	$('#next1').hide();
}
function clearBuildForm(){
	deleteLocFile();
		$('#ownerTab').hide();
		$('#documentTab').hide(); 
		$('#floorlayTab').hide();
        $("#viewLocDivId").empty();
        $("#viewLocDivId").text("Property ID: ");
        $("#locationName").val("");
        $("#locationCode").val("");
        $("#locStreet").val("");
        $("#locWidth").val("");
        $("#locLength").val("");
        $("#locArea").val("");
        $("#locHeight").val("");
        $("#locationId").val("");
        var typeVal = $("#locationType option:first").val();
        $("#locationType").val(typeVal).trigger('change');
        $("#locCountry").val("").trigger('change');
        $("#locCountry").val("").trigger('change');
        $("#locCountry").val("").trigger('change');
        $("#locPincode").val("");
        $("#locDescription").val("");
        $("#locOwnership").val("").trigger('change');
        $('#locVirtual').prop('checked', false).trigger('change');
        $('#locStatus').prop('checked', false).trigger('change');
        $("#locState").val("").trigger('change');
        $("#locCity").val("").trigger('change');
		$("#sdate").val("");
    	$("#edate").val("");
    	$("#locRent").val("");
    	$("#pdate").val(new Date().toISOString().split('T')[0]);
        $('#imgLoc').attr('src', '../assets/images/noimage.jpg');
        $("#docUrlEdit").val('');
		editProp();
}
function newProp(){	
	gridOptionsBuild.api.deselectAll();
		
}
function cancelFloor(){	
	myGridFloorDetsDets.api.deselectAll();
	$(".br-m-btn-f").show();
	$(".br-s-btn-f").hide();
	$('#floorId, #floorName').attr('disabled', true);
	$('#newFloor').show();
	$('#deleteFloor').show();
//	$('#editFloor').hide();
	$('#floorForm').hide();
	/*$('#addFloor').show();
	$('#editFloor').show();
	$('#deleteFloor').show();
	$('#saveFloor').hide();
	$('#cancelFloor').hide();*/
	$('#floorGrid').show();
	$('#prev4').attr('disabled', false);
}
function editFloor(){
	
	$(".br-m-btn-f").hide();
	$(".br-s-btn-f").show();
	$('#floorId, #floorName').attr('disabled', false);
	$('#floorGrid').hide();
	$('#floorForm').show();
	$('#newFloor').hide();
	$('#editFloor').hide();
	
	
	
	$('#prev4').attr('disabled', true);
}
function addFloor(){
	    $(".formValidation").remove();
		$("#floorIdData").html("");
		$("#floorId").val("");
		$("#floorName").val("");
		$("#floorDBVal").val("");
		$("#floorSlNoDBVal").val("");
		$("#height").val("");
		$("#width").val("");
		$("#length").val("");
		editFloor();
}
function cancelDocument(){
	gridOptionsBuildingDocs.api.deselectAll();
	$(".br-m-btn-d").show();
	$(".br-s-btn-d").hide();
	$('#documentForm').hide();
	$('#docGrid').show();
	$('#prev3, #next4').attr('disabled', false);
}
function editDocument(){
	$(".br-m-btn-d").hide();
	$(".br-s-btn-d").show();
	$('#documentForm').show();
	$('#docGrid').hide();
	$('#prev3, #next4').attr('disabled', true);
}
function newDocument(){
	$("#doctbodyData").empty();
	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" ></div></td>'
				+ '<td class="d-flex gap-2 align-items-center"> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls order-3"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
			editDocument();
}

function cancelOwner(){
	gridOwnerOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
	$('#prev1, #next2').attr('disabled', false);
	$('#ownerName, #ownerCountry, #ownerState, #ownerEmail, #ownerContact, #ownerPincode, #ownerStreet, #ownerCity, #ownerStatus').attr('disabled', true);
	$('#ownerForm').hide();
	$('#ownergrid').show();
}
function editOwner(){
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$('#prev1, #next2').attr('disabled', true);
	$('#ownerName, #ownerCountry, #ownerState, #ownerEmail, #ownerContact, #ownerPincode, #ownerStreet, #ownerCity, #ownerStatus').attr('disabled', false);
	$('#ownerForm').show();
	$('#ownergrid').hide();
}
function addOwner(){
	    $(".formValidation").remove();
		$("#ownerId").val("");
		$("#ownerName").val("");
		$("#ownerCountry").val("").trigger('change');
		$("#ownerState").val("").trigger('change');
		$("#ownerCity").val("").trigger('change');
		$("#ownerStreet").val("");
		$("#ownerPincode").val("");
		$("#ownerContact").val("");
		$("#ownerEmail").val("");
		$("#ownerStatus").val("").trigger('change');
		editOwner();
}
function cancelRent(){
	gridRentOptions.api.deselectAll();
	$(".br-m-btn").show();
	$(".br-s-btn").hide();
	$('#prev1, #next2').attr('disabled', false);
	$('#rentSDate, #rentEDate, #rentSecDeposit, #rentRentPMonth, #rentRentPMonth, #rentBankName, #rentAcNo, #rentIFSC, #rentStatus').attr('disabled', true);
	$('#DateS').hide();
	$('#DateE').hide();
	$('#newRent').show();
	$('#deleteRent').show();
	$('#editRent').show();
	$('#saveRent').hide();
	$('#cancelRent').hide();
	$('#leaseRentForm').hide();
	$('#rentgrid').show();
}
function editRent(){
	
	$(".br-m-btn").hide();
	$(".br-s-btn").show();
	$('#prev1, #next2').attr('disabled', true);
	$('#rentSDate, #rentEDate, #rentSecDeposit, #rentRentPMonth, #rentRentPMonth, #rentBankName, #rentAcNo, #rentIFSC, #rentStatus').attr('disabled', false);
	$('#DateS').show();
	$('#DateE').show();
	$('#newRent').hide();
	$('#editRent').hide();
	$('#deleteRent').hide();
	$('#saveRent').show();
	$('#cancelRent').show();
	$('#leaseRentForm').show();
	$('#rentgrid').hide();
}
function addRent(){
	    $(".formValidation").remove();
		$("#leaseRentId").html("");
		$("#ownerId").val("");
		$("#ownerName1").val("");
		$("#rentSDate").val("");
		$("#rentEDate").val("");
		$("#rentSecDeposit").val("");
		$("#rentRentPMonth").val("");
		$("#rentBankName").val("");
		$("#rentIFSC").val("");
		$("#rentStatus").val("").trigger('change');
		$("#rentAcNo").val("");
		editRent();
}
	function onQuickFilterChanged() {
		gridOptionsBuild.api
				.setQuickFilter(document.getElementById('quickFilter').value);
		
		const firstRowNode = gridOptionsBuild.api.getDisplayedRowAtIndex(0);

		if (firstRowNode) {
		    firstRowNode.setSelected(true);
		}
		// $('#totalReq').find('span').html(gridOptionsBuild.api.getModel().getRowCount());
	}
	
	function reset() {
		let data = $("#quickFilter").val();
		if(data != null && data != ""){
			$("#quickFilter").val("");
			onQuickFilterChanged();
		}
	}

	function cancelBar() {
		var id = document.getElementById("closeKey");
		id.style.display = "block";

		if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
			id.style.display = "none";
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