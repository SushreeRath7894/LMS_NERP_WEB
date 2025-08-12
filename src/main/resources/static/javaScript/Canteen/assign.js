$(document).ready(function() {
	
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData();
		agGrid.simpleHttpRequest({
				url: 'assign-view-dtls' 
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.assignDetails;
				console.log(allData)
				gridOptions.api.setRowData(allData);
						var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0); // Get the first row node
		         if (firstRowNode) {
			firstRowNode.setSelected(true); // Set the row as selected
		}


			});

	$("#myGrid").show();
	$("#delete").attr("disabled", true);
		
  var dateFormat = localStorage.getItem("dateFormat");
							$("#receiveDateCalendar1").datetimepicker({
								format : 'd-m-Y',
								closeOnDateSelect : true,
			                     timepicker : false,
			                    minDate : new Date(),
								
							}).on("change", function() {
								$('#receiveDate').val($(this).val());
							})
							$('#receiveDate').blur(function() {					
								$("#receiveDateCalendar1").val($(this).val());
							})

});


	// for new button
	function newBtn() {
		
			if (gridOptions.api) {
        gridOptions.api.deselectAll();
    }
    
                    $("#assignId").text('');
                    $("#itemId").val('');
					$("#name").val('');
					$("#assignprice").val('');
					$("#receiveDate").val('');
					$("#categry").val('');
					$("#subcategry").val('');
					$("#variant").val('');
					$("#receiveDate").val('');
				$("#t_draggable1 tbody tr").each(function() {
    $(this).find("td").text(''); // Clears the text in each <td>
});
				$("#t_draggable2 tbody tr").each(function() {
    $(this).find("td").text(''); // Clears the text in each <td>
});
//$("#t_draggable1 tbody tr").remove();					
    

	}
	
		function onQuickFilterChanged() {
		gridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}
//******variable dec */
let totalPrice = 0;
var deleteId = "";

//****************onRowClickedAkm*********** ******/   
function onRowClickedAkm(param) {
	var totalPrice = 0;
	var selectedRows = param.api.getSelectedRows();

	if (selectedRows) {
		const fruits = [];
		fruits.push(selectedRows);


	}

	console.log("selectedRows" + selectedRows);

	selectedRows.forEach(item => {
		if (item.hasOwnProperty('price')) {
			const priceValue = parseFloat(item.price);
			if (!isNaN(priceValue)) {
				totalPrice += priceValue;
			}
		}
	});
	document.getElementById("allPrice").value = totalPrice.toFixed(2);
}



//************************Delete Function**********************************************		
function deleteIncentive() {
	$.ajax({
		type: "GET",
		url: "assign-delete-id?id=" + deleteId,
		success: function(response) {
			if (response.message == "Success") {
				//alert("Hii")
				swal("Menu delete successfully!", " ", "success");
				agGrid.simpleHttpRequest({
					url: "assign-all-throughAjax"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});
				
				
			}
		}

	});

	$('#delete').attr("disabled", true);
}

//***********************Subcategry function**********************/

function clubMemberGetDetail() {
	var clubMemberId = $('#clubmember').val();
	$.ajax({
		type: "GET",
		url: "assign-getMemberDetails?id=" + subcategry,
		async: false,
		success: function(response) {
			//console.log("response------" + JSON.stringify(response));
			if (response.message == "Success") {
				$("#rangefrom").val(response.body[0].memberRangeFrom);
				$("#rangeto").val(response.body[0].memberRangeTo);
			}
		}
	})
}
//************************categry********************* */

function getIncentiveStatus() {
	
	var categry = $('#categry').val();
	
	$.ajax({
		type: "GET",
		url: "assign-getIncentiveDetails?id=" + categry,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#incentivecode").val(response.body[0].itemId);
			}
		}
	})
}

//add
	function addIncentiveInfo() {

		var obj = {};

		obj.assignId = $('#assignId').text();
		obj.itemId = $('#itemId').val();
		obj.name = $('#itemName').val();
		obj.price = $('#price').val();
		obj.receiveDate = $('#receiveDate').val();
		
		console.log("object on add-----------" + JSON.stringify(obj));

	var validation = true;
		if (validation) {
			$.ajax({
				type : "POST",
				url : "assign-add-dtls",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					if (response.message == "Success") {
						swal("Order Assign Successfully!", " ", "success");
						cancelBtn();
			
			agGrid.simpleHttpRequest({
				url: 'assign-view-dtls' 
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.assignDetails;
				console.log(allData)
				gridOptions.api.setRowData(allData);


			});
					}
				},
				error : function(data) {

					console.log(data);
				}
			})
		}

	}
	
	  // edit
function editPage(assignId) {
	

	$.ajax({
		type: "GET",
		url: "assign-edit-dtls?id=" + assignId,
		async: false,
		success: function(response) {
			if (response.message === "Success") {
			const allData = JSON.parse(response.body).editCombo;
               console.log('allData',allData);

			/*$("#assignId").text(allData.id);
			$("#name").val(allData.name);
			$("#price").val(allData.price);*/
			$("#receiveDate").val(allData[0].date);
				$("#categry").val(allData[0].category_id);
				getIncentiveStatus(allData[0].category_id);
			$("#subcategry").val(allData[0].sub_category_id);
			$("#variant").val(allData[0].variant_id);
	var items = allData;		
	 var tbody = $("#t_draggable2 tbody");

		for (var i = 0; i < items.length; i++) {
			var item = items[i];
				var newRow = $("<tr>");

				newRow.append("<td>" + item.id + "</td>");
				newRow.append("<td>" + item.name + "</td>");
				newRow.append("<td>" + item.price + "</td>");
				console.log(newRow);
				
				tbody.append(newRow);
				$("#suggesstion-box11_").hide();
			}
	
			}
		},
		
	});
}

//***********************Combo Function call dropDown view  ***********************************/	

var addedItemIds = [];

function getIncentiveStatus() {
	
	var catId = $("#categry").val();
	var subCatId = $("#subcategry").val();
	var variant = $("#variant").val();
	$.ajax({
		type: "GET",
		url: "assign/canteen-item-list?catId=" + catId + "&subCatId=" + subCatId + "&variant=" + variant,
		async: false,
		
		success: function(response) {
			
			
			var items = response;
			var tbody = $("#t_draggable1 tbody");

			// Remove existing rows if any
			tbody.find('td').remove();
			for (var i = 0; i < items.length; i++) {
				
				var item = items[i];
				var newRow = $("<tr>");

				newRow.append("<td>" + item.itemId + "</td>");
				newRow.append("<td>" + item.itemName + "</td>");
				newRow.append("<td>" + item.price + "</td>");
				tbody.append(newRow);

				addedItemIds.push(item.itemId);		
			}
		}
	});
}



//***********************Combo Function call view  ***********************************/	

function getCombo() {
	var comboId = $("#comboId").val();
	var addedComboIds = new Set(); // Use a Set to keep track of added combos

	$.ajax({
		type: "GET",
		url: "assign/canteen-combo-list?comboId=" + comboId,
		async: false,
		success: function(response) {
			var items = response;
			var tbody = $("#t_draggable2 tbody");

			// Loop through the items in the response
			for (var i = 0; i < items.length; i++) {
				var item = items[i];

				// Check if the comboId has not been added to the table
				if (!addedComboIds.has(item.comboId)) {
					// Create a new row for each unique item
					var newRow = $("<tr>");

					newRow.append("<td>" + item.comboId + "</td>");
					newRow.append("<td>" + item.comboName + "</td>");
					newRow.append("<td>" + item.allPrice + "</td>");
					tbody.append(newRow);

					// Add the comboId to the Set of added combos
					addedComboIds.add(item.comboId);
				} else {
					// Handle the case where the combo is already in the table (show an error message, for example)
					swal("Combo already selected!");
				}
			}
		}
	});
}




//******************************View Function***********************	
function getIncentiveStatusall() {
	$.ajax({
		type: "GET",
		url: "assign-all-throughAjax",
		async: false,
		success: function(response) {
			//console.log("response------" + JSON.stringify(response));
			if (response.body != "") {

				gridOptions.api.setRowData();
				gridOptions.api.setRowData(response);
			}

		}
	})
}


//******************Searching  Combo*****************/  		
function getComboList() {
	$("#comboId").val("");
	var search = $("#comboName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "assign-combo-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				//console.log(response)
				if (response.code == "success") {
					if (response.body.length != 0) {
						comboDataOnSearch = [];
						comboDataOnSearch = response.body;
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" >';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:0px; font-weight:400; font-size:14px; color:#343a40;   background-color: #dbdbdb;"  class="autocompletedata cp" onClick="selectAutocompleteValue2(\''
								+ response.body[i].comboId + '\',\''
								+ response.body[i].comboName + '\')">'
								+ response.body[i].comboName
								+ '</li>';
						}
						content += '<li  >'
							+ '</li>';
						content += '</ul>';
						////console.log("content " + content)
						$("#suggesstion-box21_").show();
						$("#suggesstion-box21_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;">'
							+ "No Data Found" + '</li>';
						content += '<li style="margin-left:-30px;" '
							+ '</li>';
						content += '</ul>';
						$("#suggesstion-box21_").show();
						$("#suggesstion-box21_").html(content);

					}
				}
			},
			error: function(data) {
				//console.log(data);
			}
		})
	}
}


var accumulatedComboNames = [];

function selectAutocompleteValue2(comboId, comboName) {
	
	//console.log(comboId+"")
	var selectedItems = document.getElementById('selected-items');
	$("#comboId").val(comboId);

	var currentComboName = $("#comboName").val();

	// Check if the item is already in accumulatedComboNames
	if (accumulatedComboNames.some(item => item.comboId === comboId)) {
		swal("This Combo already selected.");
		$("#suggesstion-box21_").hide();
		return;
	}
    
	if (currentComboName) {
		accumulatedComboNames.push({ comboName, comboId });

		const selectedItem = document.createElement('div');
		selectedItem.className = 'selected-item';
		selectedItem.innerHTML = `
							            <span hidden>${comboId}</span>
							            <span>${comboName}</span>
							            <span class="remove-button" onclick="removeSelectedItem(this, '${comboName}', '${comboId}')">X</span>
							        `;
		selectedItems.appendChild(selectedItem);

		$("#comboName").val("");
	} else {
		accumulatedComboNames = [{ comboName, comboId }];
		$("#comboName").val("");
	}

	var row = "<tr><td>" + comboId + "</td>" +
		"<td>" + comboName + "</td>" +
		//"<td>" + allPrice + "</td>" +
		"<td><span class='remove-button' onclick='removeSelectedItem(this, " + comboId + ")'>50</span></td>";

	$("#t_draggable2 tbody").append(row);
	$("#suggesstion-box21_").hide();
}

function removeSelectedItem(element, comboName, comboId) {
	const selectedItems = document.getElementById('selected-items');
	selectedItems.removeChild(element.parentNode);

	const index = accumulatedComboNames.findIndex(item => item.comboName === comboName && item.comboId === comboId);
	if (index !== -1) {
		accumulatedComboNames.splice(index, 1);
	}

	// Remove the corresponding row from the table
	$("#t_draggable2 tbody tr td:first-child:contains('" + comboId + "')").parent().remove();
}






//*******************************Auto Search *************************/            
let itemDataOnSearch = [];
let comboDataOnSearch = [];

//searching
function getProductList() {
	$("#itemId").val("");
	var search = $("#itemName").val();
	if (search) {
		$.ajax({
			type: "POST",
			url: "assign-menu-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.code == "success") {
					if (response.body.length != 0) {
						itemDataOnSearch = [];
						itemDataOnSearch = response.body;
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" >';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li style="margin-left:0px; font-weight:40; font-size:14px; color:#343a40;     background-color: #dbdbdb;"  class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
								+ response.body[i].itemId + '\',\'' + response.body[i].itemName + '\',\'' + response.body[i].price +'\')">'
								+ response.body[i].itemName
								+ '</li>';
						}
						content += '<li  >'
							+ '</li>';
						content += '</ul>';
						////console.log("content " + content)
						$("#suggesstion-box11_").show();
						$("#suggesstion-box11_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;">'
							+ "No Data Found" + '</li>';
						content += '<li style="margin-left:-30px;" '
							+ '</li>';
						content += '</ul>';
						$("#suggesstion-box11_").show();
						$("#suggesstion-box11_").html(content);

					}
				}

			},
			error: function(data) {
				//console.log(data);
			}
		})
	}
}


var addedItemIds = []; 					
function selectAutocompleteValue1(itemid, itemname,price) {

		// Add the item to the table
		$("#itemName").val(itemname);
		$("#price").val(price);
		$("#itemId").val(itemid);
		
		console.log("Search", itemDataOnSearch);
		console.log("Items" + itemDataOnSearch);

		var items = itemDataOnSearch;
		var tbody = $("#t_draggable2 tbody");

		for (var i = 0; i < items.length; i++) {
			var item = items[i];
			if (item.itemId === itemid) {
				// Create a new row for the selected item
				var newRow = $("<tr>");

				newRow.append("<td>" + item.itemId + "</td>");
				newRow.append("<td>" + item.itemName + "</td>");
				newRow.append("<td>" + item.price + "</td>");
				tbody.append(newRow);

				addedItemIds.push(itemid);
				$("#suggesstion-box11_").hide();
			}
		}
}


//*********************Edit function ****************//
/*function editId(id) {
	var editId = id;
	$("#demo").show();
	$("#add").hide();
	$("#copy").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#myGridActivity").hide();

	$.ajax({
		type: "GET",
		url: "assign-edit?id=" + editId,
		async: false,
		success: function(response) {
			if (response.message == "Success") {

				const stringifyData = JSON.stringify(response.body);
				const jsondata = JSON.parse(stringifyData);
				console.log("Edit data" + jsondata)

				gridOptionschaildview.api.setRowData(jsondata);


				$("#add").hide();
				$("#copy").hide();
				$("#delete").hide();
				$("#myGrid").hide();
				$("#searchRowDiv").hide();
				$("#totalReq").hide();
				$("#statusDiv").hide();
				$("#idDiv").hide();
				$("#collapseFour").hide();
				$("#headingFour").hide();
				$("#myGridActivity").hide();

				$("#demo").show();

				$(".container").hide();

				$("#comboId").text(response.body[0].comboId);
				$("#comboName").val(response.body[0].comboName);
				$("#allPrice").val(response.body[0].allPrice);
			}
		}
	})
}*/

// for cancel button
function cancelBtn() {
	$("#add").show();
	$("#copy").show();
	$("#delete").show();
	$("#totalReq").show();
	$("#myGrid").show();
	$("#searchRowDiv").show();
	$("#demo").hide();

	$('#itemId').val("");
	$('#itemName').val("");
	$('#price').val("");
	$('#categry').val("");
	$('#subcategry').val("");
	$('#variant').val("");
	$('#status').val("");

      		agGrid.simpleHttpRequest({
				url: 'assign-view-dtls' 
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.assignDetails;
				console.log(allData)
				gridOptions.api.setRowData(allData);


			});

}

//************************************columnDefs *************************
var columnDefs = [{
		headerCheckboxSelection: true,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Assign Id',
		field: "assignId",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		}
	},

	{
		headerName: "Name",
		field: "name",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Price",
		field: "price",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		}
	},
	{
		headerName: "Date",
		field: "receiveDate",
		flex: 1,
		cellStyle: {
			textAlign: 'center'
		}
	}

];

const gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true
	},
	//rowSelection : 'multiple',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelect,
	
};


	var assignId = '';
	function rowSelect() {
		var selectedRows = gridOptions.api.getSelectedRows();
        var selectedData = selectedRows.map(node => node.data);
		var rowCount = 0;
	selectedData.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
		
	});

	for (var i = 0; i < selectedRows.length; i++) {
		assignId = assignId + selectedRows[i].assignId;
       
        
	}
	
	if (rowCount > 0) {
		editPage(assignId)
	}else {
		$("#assignId").text('');
		$("#itemId").val('');
		$("#name").val('');
		$("#assignprice").val('');
		$("#receiveDate").val('');
		$("#categry").val('');
		$("#subcategry").val('');
		$("#variant").val('');
		$("#receiveDate").val('');
		$("#t_draggable1 tbody tr").each(function() {
			$(this).find("td").text(''); // Clears the text in each <td>
		});
		$("#t_draggable2 tbody tr").each(function() {
			$(this).find("td").text(''); // Clears the text in each <td>
		});
		
	}
assignId = '';

	}


function next() {
	$("#orderList").removeClass('hidden');
	$("#assignInformation").addClass('hidden');
	

	$(".nav-link").removeClass("active");
	$(".nav-link[href='#orderList']").addClass("active");
			
			$("#tab-orderList").addClass("active").attr("aria-selected", "true");
			$("#tab-assignInformation").removeClass("active").attr("aria-selected", "false");

			// Show the "Doctor Details" tab content
			$("#orderList").addClass("show active");
			$("#assignInformation").removeClass("show active");
		}
		
function prev() {
	$("#assignInformation").removeClass('hidden');
	$("#orderList").addClass('hidden');
	

	$(".nav-link").removeClass("active");
	$(".nav-link[href='#assignInformation']").addClass("active");
		
			$("#tab-assignInformation").addClass("active").attr("aria-selected", "true");
			$("#tab-orderList").removeClass("active").attr("aria-selected", "false");
			
			
			$("#assignInformation").addClass("show active");
			$("#orderList").removeClass("show active");
		}
		
		
		function activityTabs(activityId) {
		if(activityId == "assignInformation"){
			$("#assignInformation").removeClass('hidden');
			$("#orderList").addClass('hidden');
		} else if(activityId == "orderList"){
			$("#assignInformation").addClass('hidden');
			$("#orderList").removeClass('hidden');
		}
}