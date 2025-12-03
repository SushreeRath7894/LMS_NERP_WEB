	$(document).ready(function() {
	    var GridDiv = document.querySelector('#foodGrid');
	    new agGrid.Grid(GridDiv, foodgridOptions);
	    viewBuilding();
	
	    var secGridDiv = document.querySelector('#foodsecGrid');
	    new agGrid.Grid(secGridDiv, secgridOptionsfood);
		getAllRestaurantFoodDetails();
		
		 var secGridDiv = document.querySelector('#timesecGrid');
	    new agGrid.Grid(secGridDiv, secgridOptionstime);
		getAllRestaurantFoodTimeDetails();
		
	});
	
	
	
	
	
	function prevStep() {
		document.getElementById("step2").style.display = "none";
		document.getElementById("step1").style.display = "block";
	}
	
	function nextBtnFunction(tablink) {
		/*const tabElement2 = document.querySelector('#rateType');*/
		const tabElement = document.querySelector(`#${tablink}`);
		console.log(tabElement)
	
		if (tabElement) {
			
			tabElement.click();
		}
	}
	function addRestaurantFoodDetails() {
    document.getElementById("foodsecGrid").style.display = "none";
    const detailsSection = document.getElementById("hiddenDetails");
    detailsSection.style.display = "block";
    const inputs = detailsSection.querySelectorAll("input, textarea, select");
    inputs.forEach(input => {
        if (input.type === "checkbox" || input.type === "radio") {
            input.checked = false;
        } else {
            input.value = "";
        }
    });
    
    $("#addRestaurantFoodDetails").addClass("d-none");
    $("#cancelFoodItems").removeClass("d-none");
    $("#editRestaurantBtn").addClass("d-none");
    $("#saveRestaurant").removeClass("d-none");
}

function cancelRestaurantFood(){
	$("#addRestaurantFoodDetails").removeClass("d-none");
    $("#cancelFoodItems").addClass("d-none");
    $("#saveRestaurant").addClass("d-none");
    $("#hiddenDetails").hide();
    $("#foodsecGrid").show();
    $("#editRestaurantBtn").removeClass("d-none");
}


	function prevBtnFunction(tablink) {
		const tabElement = document.querySelector(`#${tablink}`);
		console.log(tabElement)
	
		if (tabElement) {
			tabElement.click();
		}
	}
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
	        headerName: "PropertyID",
	        field: "locationId",
			pinned: 'left',
			
	    },
	    {
	        headerName: "Code",
	        field: "locationCode",
	        hide: true,
	        cellRenderer: function(params) {
	            return 'Location';
	        }
	    },{
	        headerName: "FloorId",
	        field: "FloodId",
			
	    }, {
	        headerName: "Name",
	        field: "locationName",
			
	    }, {
	        headerName: "Property",
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
	var foodgridOptions = {
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
    var selectedNodes = foodgridOptions.api.getSelectedNodes();
    if (selectedNodes.length > 0) {
        const locationId = selectedNodes[0].data.locationId;
        console.log("Selected Location ID:", locationId);
        getAllRestaurantFoodDetails(locationId);
    }
}
	

	var seccolumnDefs = [
    {
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        pinned: 'left',
        resizable: true
    },
      { headerName: "Item_Code", field: "item_Code" },
        { headerName: "Item_Name", field: "item_Name" },
    { headerName: "Quantity", field: "quantity", sortable: true },
    { headerName: "UOM", field: "uom" },
 { headerName: "Cost", field: "cost" },
 { headerName: "Type", field: "type" },
 { headerName: "Availability", field: "availability" }
    
];

	var secgridOptionsfood = {
	    columnDefs: seccolumnDefs,
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
	   
	};
	function onSecGridSelectionChanged() {
    var selectedNodes = secgridOptionsfood.api.getSelectedNodes();
    if (selectedNodes.length > 0) {
        const selectedData = selectedNodes[0].data;
        populateFormFields(selectedData);
    }
}
function populateFormFields(data) {
    // Set values for the form fields
    $('#item_Code').val(data.item_Code);
    $('#item_Name').val(data.item_Name);
    $('#quantity').val(data.quantity);
    $('#uom').val(data.uom);
    $('#cost').val(data.cost);
    $('#type').val(data.type);
    $('#availability').val(data.availability);
    
}
secgridOptionsfood.onSelectionChanged = function() {
    const selectedNodes = secgridOptionsfood.api.getSelectedNodes();
    if (selectedNodes.length > 0) {
        $('#editResturantBtn').prop('disabled', false); // enable edit button
    } else {
        $('#editResturantBtn').prop('disabled', true);
    }
};

	
	// view Data Functions
	function viewBuilding(id=null,tab=null){
		agGrid.simpleHttpRequest({
			url : 'hotel-management-view'
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			var allData=jsonData.Property;
		 	if(allData!=null){
			 	var len = allData.length;
				$('#totalReq').find('span').html(len);
				foodgridOptions.api.setRowData(allData);
				if (id != null) {
					locSel = id;
				    foodgridOptions.api.forEachNode(function(node) {
					 if (String(node.data.locationId) === String(id)) {					
				           node.setSelected(true);
				        }
				    });
				} else {	
					console.log("else")
					locSel = '';		
					var firstRowNode = foodgridOptions.api.getDisplayedRowAtIndex(0);
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
				foodgridOptions.api.setRowData("");
			}
		});
	
	}
	
	//edit function call and row select 
	
	
	function onCheckboxClick() {
	  
	 


  
  var selectedNodes = foodgridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const locationId = selectedData.map(node => node.locationId);
	var selectedRows = foodgridOptions.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].locationId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		
	} else {
	}
}
function onEditRestaurantButtonClick() {
    const selectedNodes = secgridOptionsfood.api.getSelectedNodes();
    if (selectedNodes.length > 0) {
        const selectedData = selectedNodes[0].data;
        const item_Code = selectedData.item_Code;

        if (item_Code) {
           editRestaurantFoodDetails(item_Code);

        
            document.getElementById("foodsecGridContainer").style.display = "none";
            document.getElementById("hiddenDetails").style.display = "block";

           
            $("#addRestaurantFoodDetails").addClass("d-none");
            $("#cancelFoodItems").removeClass("d-none");
            $("#editRestaurantBtn").addClass("d-none");
            $("#saveRestaurant").removeClass("d-none");
        }
    } else {
        toastr.error("Please select a row to edit.");
    }
}

//edit


function editRestaurantFoodDetails(item_Code) {
	
	agGrid.simpleHttpRequest({
		
		url: "hotel-restaurantfood-edit?item_Code=" + item_Code,
	}).then(function(response) {
		if (response.code === "Success") {
			console.log(response.body[0]);
			const responseBody = JSON.parse(response.body[0]);
			
			$("#item_Code").val(responseBody[0].item_Code);
			$("#item_Name").val(responseBody[0].item_Name);
			$("#quantity").val(responseBody[0].quantity);
			$("#uom").val(responseBody[0].uom);
			$("#cost").val(responseBody[0].cost);
			$("#type").val(responseBody[0].type);
			$("#availability").val(responseBody[0].availability)
			
	
    
			
		
		} else {
			console.log("Failed to fetch data");
		}
	});
	
}



	function restaurantfoodData() {
		var selectedNodes = foodgridOptions.api.getSelectedNodes();
	    if (selectedNodes.length === 0) {
	        toastr.error("Please select a building from the list.");
	        return;
	    }
	 
	    var selectedData = selectedNodes[0].data;
	    var locationId = selectedData.locationId;
	    let resturantFoodLists = {};
	

	    resturantFoodLists['item_Code'] = $("#item_Code").val();
        resturantFoodLists['locationId'] = locationId;
		resturantFoodLists['item_Name'] = $("#item_Name").val();
 		resturantFoodLists['quantity'] = $("#quantity").val();
 		resturantFoodLists['uom'] = $("#uom").val();
		resturantFoodLists['cost'] = $("#cost").val();
		resturantFoodLists['type'] = $("#type").val();
		resturantFoodLists['availability'] = $("#availability").val();
console.log(resturantFoodLists);
 if($("#item_Name").val() == "" ||  $("#item_Name").val() == null){
		toastr.error("Item_Name is Required");
		return;
	} 
	if($("#quantity").val() == "" ||  $("#quantity").val() == null){
		toastr.error("Quantity% is Required");
		return;
	} if($("#uom").val() == "" ||  $("#uom").val() == null){
		toastr.error("UOM is Required");
		return;
	} 
	if($("#cost").val() == "" ||  $("#cost").val() == null){
		toastr.error("Cost is Required");
		return;
	} 
	if($("#type").val() == "" ||  $("#type").val() == null){
		toastr.error("Type is Required");
		return;
	} 
	if($("#availability").val() == "" ||  $("#availability").val() == null){
		toastr.error("Availability is Required");
		return;
	} 
	

	 saverestaurantfoodData(resturantFoodLists);
	 // Show 
	    document.getElementById("foodsecGridContainer").style.display = "block";
	
	    // Hide the details section
	    document.getElementById("hiddenDetails").style.display = "none";
	
	}
	    
	function saverestaurantfoodData(resturantFoodLists) {
				$.ajax({
				type: "POST",
					url: "save-restaurant-food-details",
				contentType: "application/json",
				data: JSON.stringify(resturantFoodLists),
				success: function(response) {
				if (response.code === "Success") {
				$('.loader').hide();
					toastr.success(response.message);
					viewBuilding();
					cancelRestaurantFood();
				} else {
				toastr.error(response.message);
				$('.loader').hide();
				}
				},
				error: function(datas) {
	 			$('.loader').hide();
				toastr.error("Something went wrong. Please try again.");
	 			}
	 		});
		 
		
	
		}
		
		var sectimecolumnDefs = [
    {
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        pinned: 'left',
        resizable: true
    },
     // { headerName: "TableId", field: "tableId" },
        { headerName: "Availability", field: "availabilityfoodtime" },
    { headerName: "Start Time", field: "startTime", sortable: true },
    { headerName: "End Time", field: "endTime" }
 
];
		
		var secgridOptionstime = {
	    columnDefs: sectimecolumnDefs,
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
	   
	};
	
	
	function onSecGridSelectionChanged() {
    var selectedNodes = secgridOptionstime.api.getSelectedNodes();
    if (selectedNodes.length > 0) {
        const selectedData = selectedNodes[0].data;
        populateFormFields(selectedData);
    }
}
		
	
	

function getAllRestaurantFoodDetails(locationId = null) {
    console.log("Getting service details for:", locationId);

    agGrid.simpleHttpRequest({
        url: "hotel-restaurant-food-view"
    }).then(function(response) {
        var jsonData = JSON.parse(response.body);
        var allData = jsonData.restaurantfoodDetails || [];

        // Fallback filter (if backend does not support ?locationId)
        if (locationId) {
            allData = allData.filter(item => item.locationId == locationId);
        }

        console.log("Filtered service data:", allData);
        secgridOptionsfood.api.setRowData(allData);
    });

}
//<======================================================================================================>
function addRestaurantTimeDetails() {
    document.getElementById("timesecGrid").style.display = "none";
    const detailsSection = document.getElementById("hiddenDetails2");
    detailsSection.style.display = "block";
    const inputs = detailsSection.querySelectorAll("input, textarea, select");
    inputs.forEach(input => {
        if (input.type === "checkbox" || input.type === "radio") {
            input.checked = false;
        } else {
            input.value = "";
        }
    });
    
    $("#addRestaurantTimeDetails").addClass("d-none");
    $("#cancelFoodTime").removeClass("d-none");
    $("#editRestaurantTimeBtn").addClass("d-none");
    $("#saveRestaurantTime").removeClass("d-none");
}

function cancelFoodTime(){
	$("#addRestaurantTimeDetails").removeClass("d-none");
    $("#cancelFoodTime").addClass("d-none");
    $("#saveRestaurantTime").addClass("d-none");
    $("#hiddenDetails2").hide();
    $("#timesecGrid").show();
    $("#editRestaurantTimeBtn").removeClass("d-none");
}


function restaurantfoodTimeData() {
		var selectedNodes = foodgridOptions.api.getSelectedNodes();
	    if (selectedNodes.length === 0) {
	        toastr.error("Please select a building from the list.");
	        return;
	    }
	 
	    var selectedData = selectedNodes[0].data;
	    var locationId = selectedData.locationId;
	    let resturantFoodTimeLists = {};
	

	    resturantFoodTimeLists['tableId'] = $("#tableId").val();
        resturantFoodTimeLists['locationId'] = locationId;
		resturantFoodTimeLists['availabilityfoodtime'] = $("#availabilityfoodtime").val();
 		resturantFoodTimeLists['startTime'] = $("#startTime").val();
 		resturantFoodTimeLists['endTime'] = $("#endTime").val();
		
console.log(resturantFoodTimeLists);
 if($("#availabilityfoodtime").val() == "" ||  $("#availabilityfoodtime").val() == null){
		toastr.error("Availabilityfoodtime is Required");
		return;
	} 
	if($("#startTime").val() == "" ||  $("#startTime").val() == null){
		toastr.error("StartTime is Required");
		return;
	} if($("#endTime").val() == "" ||  $("#endTime").val() == null){
		toastr.error("EndTime is Required");
		return;
	} 
	
	

	 saverestaurantfoodTimeData(resturantFoodTimeLists);
	 // Show 
	    document.getElementById("timeGridContainer").style.display = "block";
	
	    // Hide the details section
	    document.getElementById("hiddenDetails2").style.display = "none";
	
	}
	    
	function saverestaurantfoodTimeData(resturantFoodTimeLists) {
				$.ajax({
				type: "POST",
					url: "save-restaurant-food-time-details",
				contentType: "application/json",
				data: JSON.stringify(resturantFoodTimeLists),
				success: function(response) {
				if (response.code === "Success") {
				$('.loader').hide();
					toastr.success(response.message);
					cancelFoodTime();
				} else {
				toastr.error(response.message);
				$('.loader').hide();
				}
				},
				error: function(datas) {
	 			$('.loader').hide();
				toastr.error("Something went wrong. Please try again.");
	 			}
	 		});
		 }
		
function getAllRestaurantFoodTimeDetails(locationId = null) {
    console.log("Getting service details for:", locationId);

    agGrid.simpleHttpRequest({
        url: "hotel-restaurant-food-time-view"
    }).then(function(response) {
        var jsonData = JSON.parse(response.body);
        var allData = jsonData.restaurantfoodtimeDetails || [];

        // Fallback filter (if backend does not support ?locationId)
        if (locationId) {
            allData = allData.filter(item => item.locationId == locationId);
        }

        console.log("Filtered service data:", allData);
        secgridOptionstime.api.setRowData(allData);
    });

}

	

	
function onEditTimeButtonClick() {
    const selectedNodes = secgridOptionstime.api.getSelectedNodes();
    if (selectedNodes.length > 0) {
        const selectedData = selectedNodes[0].data;
        const tableId = selectedData.tableId;

        if (tableId) {
          editRestaurantTimeDetails(tableId);

        
            document.getElementById("timeGridContainer").style.display = "none";
            document.getElementById("hiddenDetails2").style.display = "block";

           
            $("#addRestaurantTimeDetails").addClass("d-none");
            $("#cancelFoodTime").removeClass("d-none");
            $("#editRestaurantTimeBtn").addClass("d-none");
            $("#saveRestaurantTime").removeClass("d-none");
        }
    } else {
        toastr.error("Please select a row to edit.");
    }
}
function editRestaurantTimeDetails(tableId) {
	
	agGrid.simpleHttpRequest({
		
		url: "hotel-restauranttime-edit?tableId=" + tableId,
	}).then(function(response) {
		if (response.code === "Success") {
			console.log(response.body[0]);
			const responseBody = JSON.parse(response.body[0]);
			
			$("#tableId").val(responseBody[0].tableId);
			$("#availabilityfoodtime").val(responseBody[0].availabilityfoodtime);
			$("#startTime").val(responseBody[0].startTime);
			$("#endTime").val(responseBody[0].endTime)
			
	
    
		
		} else {
			console.log("Failed to fetch data");
		}
	});
	
}
 
function openAssetManagement() {
	callActivity('ACT0667','/asset/asset-management').click();
  }
	 
	
	


