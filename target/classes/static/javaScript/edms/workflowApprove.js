$(document).ready(function() {
	
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	viewWOrkspaceData();
	$('#approve').attr('disabled', true);
});

function viewWOrkspaceData(){
		var createdBy=localStorage.getItem("createdBy");
		var notificationId = localStorage.getItem("notificationId");
	/*if(createdBy != ""){
		//api call from notification status update
		$.ajax({
			type: "GET",
			url: "workflow-approval-notificationUpdate?id=" + notificationId,
			success: function(response) {
				if (response.message == "Success") {
					localStorage.removeItem("docId");
					localStorage.removeItem("createdBy");
					localStorage.removeItem("notificationId");
					viewWOrkspaceData();
				} else{
					("error" + console.message)
				}
					
			}
		});
		}*/
		agGrid.simpleHttpRequest({
			url : 'workflow-approval-view'
		}).then(function(data) {
			var jsonData = JSON.parse(data.body[0]);
			var allData=jsonData.view;
			if(allData == "" || allData == null || allData == "null"){
				var len = 0;
			}else{
				var len = allData.length;
			}
			$('#totalReq').find('span').html(len);
			gridOptions.api.setRowData(allData);
			
			 setTimeout(() => {
            if (createdBy) {
                let rowNodeFound = false;
                gridOptions.api.forEachNode((node) => {
                    if (node.data.workFlowEmployee === createdBy) { 
                        rowNodeFound = true;
                        node.setSelected(true);
                        gridOptions.api.ensureIndexVisible(node.rowIndex); // Ensure the row is visible
                    }
                });
                if (!rowNodeFound) {
                    console.warn("Row with CreatedBy not found.");
                }
            }
        }, 100); // Timeout to ensure grid is fully rendered
			$('.loader').hide();
		});
}

function cancelBars() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";

	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true

	},
	{
		headerName: 'Id',
		width: 200,
		field: "id",
		hide:true

	},
	{
		headerName: 'Document Id',
		width: 200,
		field: "docId"

	}, {
		headerName: 'Original Document',
		width: 200,
		field: "originalDoc",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Original Document Url',
		width: 100,
		field: "originalDocUrl",
		cellStyle: {
			textAlign: 'center'
		},cellRenderer : function(params) {
					var div = "";
					if (params.data.originalDocUrl) {
						var ext = params.data.originalDocUrl.split(".");
						if (ext[1] == "pdf") {
							div = div
							+ " "
							+ '<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImageDoc("'+ params.data.originalDocUrl + '")> </div>';
						}else{
							div = div
							+ " "
							+ '<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImageDoc("'+ params.data.originalDocUrl + '")> </div>';
						}
					}
					return div;
				}
	}, {
		headerName: 'Modified Image',
		field: "modifiedFile",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Modified Image Url',
		field: "modifiedUrl",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		},cellRenderer : function(params) {
					var div = "";
					if (params.data.modifiedUrl) {
						var ext = params.data.modifiedUrl.split(".");
						if (ext[1] == "pdf") {
							div = div
							+ " "
							+ '<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewModifyImageDoc("'+ params.data.modifiedUrl + '")> </div>';
						}else{
							div = div
							+ " "
							+ '<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewModifyImageDoc("'+ params.data.modifiedUrl + '")> </div>';
						}
					}
					return div;
				}
	}, {
		headerName: 'Version',
		field: "version",
		width: 90,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'Created By',
		field: "accessBy",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: 'WorkFlow Employee',
		field: "workFlowEmployee",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		hide:true
		//hide:true
	}, {
		headerName: 'WorkFlow Employee',
		field: "workFlowEmployeeName",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		//hide:true
	}, {
		headerName: 'Status',
		field: "status",
		width: 200,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer : function(params) {
					 if (params.data.status == "1") {
					        return '<div style="color:#008000">Approved</div>';
					    } else if (params.data.status == "2") {
					        return '<div style="color:#a9a9a9">Rejected</div>';
					    } else {
					        return '<div style="color:#a9a9a9">Pending</div>';
					    }
				}
	
	}];

// let the grid know which columns and what data to use
var rowdata = [];
var gridOptions = {
	columnDefs: columnDefs,
	rowData: rowdata,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 120
	},
	onSelectionChanged: onSelectionChanged,

};

function viewImageDoc(id){
		window.open(id,'_blank');
	}
function viewModifyImageDoc(id){
		window.open(id,'_blank');
	}
//function for row select parents
var id = "";

function onSelectionChanged() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	id = selectedData.map(node => node.qcId);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#approve').attr('disabled', false);
	} else {
		$('#approve').attr('disabled', true);
	}
}


//function for cancel
function cancel() {
	hideShowMultipleElement(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], true);
	hideShowMultipleElement(["#demo"], false);

	//validationFile.hideShowMultipleElements(["#myGrid", ".btn-hs", "#reqTable", "#searchDiv", "#btndiv", "#listdiv", "#totalReq", "#searchRowDiv", "#ttbtn"], true);
	closeNav();
	//validationFile.hideShowMultipleElements(["#demo"], false);


	$(".formValidation").remove();
	$("#reqDltBtn").attr('disabled', true);
	$("#newBtn").attr('disabled', false);

	$("#qcId").val('');
	$("#itemname").val('');
	$("#itemid").val('');
	$("#description").val();
	closeNav();
}




//for closeing modal box for dlt  product
function cancelModalProductBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteDetails').modal('hide');
}

function deleteFun() {
	$('#delete').modal('show');
}

function deleteDetails() {
	$('#deleteDetails').modal('show');
	closeNav();
}

function cancelModalBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
}
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#searchBar').val() == null || $('#searchBar').val() == "") {
		id.style.display = "none";
	}
}
//function for download
function downloadDetails() {
	var dataset = [];
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		dataset.push(rowNode.data);
	});
	gridOptions.api.exportDataAsCsv(dataset);
}

function approve(){
	$("#documentModal").modal('show');
}

function approveDocument(id){
	$(".loader").show();
	var selectedRows=gridOptions.api.getSelectedRows();
	console.log('selectedRows',selectedRows)
	var item={};
	item.docid=selectedRows[0].docId;
	item.version=selectedRows[0].version;
	item.status=id;
	item.id=selectedRows[0].id;
	item.empId=selectedRows[0].workFlowEmployee;
	setTimeout(function() {
		$.ajax({
			type : "POST",
			url : "workflow-approval-save",
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(item),
			success : function(response) {
				 $(".loader").hide();
				 viewWOrkspaceData();
				 $("#totalReq").show();
				 $("#searchRowDiv").show();
				 $("#ttbtn").show();
				 $("#documentModal").modal('hide');
				 $("#messageParagraph").text(response.message);
				 $("#msgOkModal").removeClass("btn3");
				 $("#msgOkModal").addClass("btn1");
				 $("#msgModal").modal('show');
				 $("#demo").hide();
				 $("#myGrid").show();
				 
				
			},
			error : function(data) {
				$('.loader').hide();
			}
		});
	}, 1000)
}

