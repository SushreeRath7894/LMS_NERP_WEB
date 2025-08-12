
//Multiple Document Upload Ends

$(document).ready(function() {
	$("#demo").hide();
	
	
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions1);
	
	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);
	
	$('#docTbl').on('click', '.rmv1', function() {
		openDeleteConfirm();
		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
	});
	
	let x = []
	gridOptions1.api.setRowData(x);

	 agGrid.simpleHttpRequest({
			url : "create-project-view"
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.viewProjectData;
			var len = allData.length;
			console.log('MAT',len)
			$('#totalReq').find('span').html(len);
			gridOptions1.api.setRowData(allData);
			
		});
	
	
});


function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}


var columnDefs = [
		{
			headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true
		},
		{
			headerName : "Project ID",
			field : "projectId",
			type : 'leftAligned',
			hide : true,
			
		}, {
			headerName : "Project Name",
			field : "projectName",
			type : 'leftAligned',
			cellRenderer : function(params) {
				return '<a onclick=editPage("' + params.data.projectId
						+ '") href="javascript:void(0)">'
						+ params.data.projectName + '</a>';
			}
		}, {
			headerName : "Creation Date",
			field : "creationDate",
			type : 'leftAligned',
			width : 150
		}, {
			headerName : 'Location',
			field : "location",
			type : 'leftAligned',
			width : 130
		}, {
			headerName : 'Country',
			field : "country2",
			type : 'leftAligned',
			width : 130
		}, {	
			headerName : "State",
			field : "stateid2",
			type : 'leftAligned',
			width : 150
		}, {
			headerName : 'Pin',
			field : "pPin",
			type : 'leftAligned',
		}, {
			headerName : 'Project Incharge',
			field : "pIncharge",
			type : 'leftAligned',
		}, {
			headerName : 'Billing Name',
			field : "cName",
			type : 'leftAligned',
		}, {
			headerName : 'Billing Address',
			field : "cAddress",
			type : 'leftAligned',
			hide:true
		}, {
			headerName : 'Billing Country',
			field : "country",
			width : 150,
			type : 'leftAligned',
		}, {
			headerName : 'Billing State',
			field : "stateid",
			width : 150,
			type : 'leftAligned',
		}, {
			headerName : 'Billing Pin',
			field : "cPin",
			width : 150,
			hide : true,
			type : 'leftAligned',

		}, {
			headerName : 'Billing Email',
			field : "email",
			width : 150,
			type : 'leftAligned',

		}, {
			headerName : 'Billing Mobile',
			field : "mobile",
			width : 150,
			type : 'leftAligned',

		}, {
			headerName : 'Remarks',
			field : "remark",
			width : 150,
			type : 'leftAligned',

		}, {
			headerName : 'status',
			field : "status",
			width : 150,
			type : 'leftAligned',

		} ];
		
		
//for shipping table
var activityDefs = [
		{
			//headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			sortable : false,
			filter : false,
			resizable : true,
			width : 30
		},
		{
			headerName : "SlNo",
			field : "slNo",
			width : 70,
			cellRenderer : function(params) {
				if (params.data.slNo) {
					return '<a onclick=rowEdit("' + params.data.slNo
							+ '") href="javascript:void(0)">'
							+ params.data.slNo + '</a>';
				} else {
					return '<a onclick=rowEdit("' + params.data.slNo
							+ '") href="javascript:void(0)">'
							+ params.data.slNo + '</a>';
				}
			}
		}, {
			headerName : "Shipping Name",
			field : "shippingName",
			width : 500,
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "Shipping Address",
			field : "shippingAddress",
			width : 200,
			cellStyle : {
				textAlign : 'center'
			},
			hide:true
		},
				{
					headerName : 'Shipping Country',
					field : "country1Name",
					width : 120,
				}, {
					headerName : 'Country',
					field : "country1",
					hide : true,
				},
				
				{
					headerName : 'Shipping State',
					field : "stateid1Name",
					width : 120,
				}, {
					headerName : 'State',
					field : "stateid1",
					hide : true,
				},
				{
						headerName : "Shipping City",
						field : "shippingCity",
						width : 200,
						cellStyle : {
							textAlign : 'center'
						},
						},{
								headerName : "Street1",
								field : "shippingStreet1",
								width : 200,
								cellStyle : {
									textAlign : 'center'
								},
								
								},{
									headerName : "Street2",
									field : "shippingStreet2",
									width : 200,
									cellStyle : {
										textAlign : 'center'
									},
								
									},{
										headerName : "Gst No",
										field : "shippingGstNo",
										width : 200,
										cellStyle : {
											textAlign : 'center'
										},
								
									},{
										headerName : "Shipping Pin",
										field : "shippingPin",
										width : 200,
										cellStyle : {
											textAlign : 'center'
										},
										
										},{
											headerName : "Shipping Email",
											field : "shippingEmail",
											width : 200,
											cellStyle : {
												textAlign : 'center'
											},
											},{
												headerName : "Shipping Mobile No.",
												field : "shippingMobileNo",
												width : 200,
												cellStyle : {
													textAlign : 'center'
												},
												
				
		},];


// let the grid know which columns and what data to use product table
var activityOptions = {
	columnDefs : activityDefs,
	rowSelection : 'multiple',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 200
	},
	onSelectionChanged : onSelectionChangeChild,
	getRowNodeId : function(data) {
		return data.slNo;
	}
};



//function for row select parents
var id = "";
	var creq="";
	function onSelectionChangeChild() {
		 var selectedNodes = activityOptions.api.getSelectedNodes();
		 var selectedData = selectedNodes.map(node => node.data);
		 creq= selectedData.map(node => node.shippingId);
		 
		var selectedRows = activityOptions.api.getSelectedRows();
		id = "";
		for (var i = 0; i < selectedRows.length; i++) {

			id = id + '"' + selectedRows[i].projectId + '",';
			console.log(selectedRows[i].status);
		}
		id = id.substring(0, id.length - 1);

		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			$('#newchild').attr('disabled', true);
			$('#deleteChild').attr('disabled', false);
		
	}else{
		$('#newchild').attr('disabled', false);
		$('#deleteChild').attr('disabled', true);
		
	}
	}
	
	
	

//function for openNav child	
function openNav() {
	$("#shippingId").val('');
	$("#shippingName").val('');
	$("#shippingAddress").val('');
	$("#shippingCountry").val('');
	$("#shippingState").val('');
	$("#shippingCity").val('');
	$("#shippingStreet1").val('');
	$("#shippingStreet2").val('');
	$("#shippingPin").val('');
	$("#shippingEmail").val('');
	$("#shippingMobileNo").val('');
	$("#country1").val('');
	$("#stateid1").val('');
	$("#rowEdit").val("");
	
	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto; top:430px;";
	document.getElementById("main").style.width = "75%";
}

//function for closeNav child
function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main").style.width = "100%";
	$("#shippingId").val('');
	$("#shippingName").val('');
	$("#shippingAddress").val('');
	$("#shippingCountry").val('');
	$("#shippingState").val('');
	$("#shippingCity").val('');
	$("#shippingStreet1").val('');
	$("#shippingStreet2").val('');
	$("#shippingPin").val('');
	$("#shippingEmail").val('');
	$("#shippingMobileNo").val('');
	$("#country1").val('');
	$("#stateid1").val('');
	$("#rowEdit").val(null);
}

//master save data

function addCreation() {
	if (validFormData() && validProductData()) {
		var datas = [];
	    var imageValid=true;
	    var uploadList = [];
	    $("#doctbodyData > tr").each(function() {
	        var uFile = $(this).find(".document")[0].files[0];
	        var fileName = $(this).find(".document").val();
	        var data = [];
	        var x = [];
	        if (fileName != '' && fileName != 'undefined' && fileName != null) {
	            var lastIndex = fileName.lastIndexOf("\\");
	            if (lastIndex >= 0) {
	                fileName = fileName.substring(lastIndex + 1);
	            }
	            var reader = new FileReader();
	            reader.readAsDataURL(uFile);

	            reader.onload = function() {
	                data = reader.result.split(",");
	                x.push(data[1]);
	            };
	        } else {
	            if ($("#projectId").val()) {
	                fileName = $(this).find(".uploadHidCls").val();
	            } else {
	                x = [];
	            }

	        }
	        uploadData = {};
	        uploadData['projectId'] = $("#projectId").val();
	        uploadData['documnentName'] = $(this).find(".docNoclss").val();
	        uploadData['documentFile'] = x;
	        uploadData['fileName'] = fileName;
	        uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
	        if($(this).find(".docNoclss").val()!="" && $(this).find(".docNoclss").val()!='null' && fileName!="" && fileName!="null"){
	            uploadList.push(uploadData);
	            console.log(uploadData)
	        } 
	        console.log('DOC', uploadData)
	    });
	    
	     setTimeout(function() {
        var obj = {};
        var item_data = [];
        
        activityOptions.api.forEachNode(function(rowNode, index) {
            console.log(rowNode)
            var item_obj = {};
            item_obj.shippingId = rowNode.data.shippingId;
            item_obj.shippingName =  rowNode.data.shippingName;
            item_obj.shippingAddress =  rowNode.data.shippingAddress;
            item_obj.country1 =  rowNode.data.country1;
            item_obj.stateid1 =  rowNode.data.stateid1;
            item_obj.shippingCity =  rowNode.data.shippingCity;
            item_obj.shippingStreet1 =  rowNode.data.shippingStreet1;
            item_obj.shippingStreet2 =  rowNode.data.shippingStreet2;
            item_obj.shippingPin =  rowNode.data.shippingPin;
            item_obj.shippingEmail =  rowNode.data.shippingEmail;
            item_obj.shippingMobileNo =  rowNode.data.shippingMobileNo;
            item_obj.shippingGstNo =  rowNode.data.shippingGstNo;
            item_obj.shippingContact =  rowNode.data.shippingContact;
            
            item_data.push(item_obj);
       
         });
        console.log('Shipping', item_data)
        
        obj.projectId = $("#projectId").val();
        obj.projectName = $("#projectname").val();
        obj.creationDate = $("#creationID").val();
        obj.location = $("#locationID").val();
        obj.country2 = $("#country2").val();
        obj.stateid2 = $("#stateid2").val();
        obj.pPin = $("#pinID").val();
        obj.pIncharge = $("#pIncharge").val();
        obj.cName = $("#cname").val();
        obj.cAddress = $("#caddress").val();
        obj.country = $("#country").val();
        obj.stateid = $("#stateid").val();
        obj.cityb = $("#cityb").val();
        obj.street1b = $("#street1b").val();
        obj.street2b = $("#street2b").val();
        obj.cPin = $("#cPin").val();
        obj.email = $("#email").val();
        obj.mobile = $("#mobile").val();
        obj.remark = $("#remarks").val();
        obj.status = $("#status").val();
        obj.billingStreet1 = $("#street1").val();
        obj.billingStreet2 = $("#street2").val();
        obj.billingGstNo = $("#gstNo").val();
        obj.documentList = uploadList;
        obj.shippingList = item_data;
        
        datas.push(obj);
        console.log("dataset-------------------" + JSON.stringify(datas));

        saveAllQuotation(datas);
    }, 1000)
	}
    
}

function validFormData() {
	var allValid = true;

	if (!validationUpdated("Project Name Required", 'projectname'))
		allValid = false;
	if (!validationUpdated("Creation Date Required",
			'creationID'))
		allValid = false;
	return allValid;
}

function validProductData() {
	var item = null;
	activityOptions.api.forEachNode(function(rowNode, index) {
		item = item + rowNode.data;
	});
	if (item) {
		return true;
	} else {
		$("#messageParagraph").text("Please Add Shipping and Billing Details");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return false;
	}
}


function saveAllQuotation(datas) {

    console.log("dastssss"+datas)
	$.ajax({
		type : "POST",
		url : "create-project-add",
		contentType : "application/json",
		data : JSON.stringify(datas),
		success : function(response) {
			if (response.message == "Success") {
				
				$("#messageParagraph").text("Data Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
								
				//$("#projectId").html(response.body[0].projectId);
				closeNav1();
				
				//location.reload();
				  agGrid.simpleHttpRequest({
					url : "create-project-view"
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData = jsonData.viewProjectData;
					var len = allData.length;
					console.log('MAT',len)
					$('#totalReq').find('span').html(len);
					gridOptions1.api.setRowData(allData);
					
				});  
			}

		},
		error : function(datas) {
			console.log(datas)
		}
	})

}

function addCandidate() {

	$('#hideTbl').hide();
	$('#buttonDetails').hide();
	$('#myGrid').hide();
	$('#demo').show();
	$("#btn1").show();
	$('#candidateId').val("");

	$("#collapseOne").collapse({
		toggle : false
	}).collapse('show');
}

$("#budgetId").click(function() {
	$("#a").css("display", "block");
	$("#b").css("display", "block");
});

var gridOptions1 = {
	columnDefs : columnDefs,
	//rowData : rowData,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 200,
		height : 20
	},
	rowSelection : 'single',
	onSelectionChanged : onSelectionChanged
};

$("#budgetId").click(function() {
	$("#a").css("display", "block");
	$("#b").css("display", "block");
});

/* APPLY FOR REQUISITION ENDS */

// setup the grid after the page has finished loading
$(document).ready(function() {
	closeNav1();
	activityOptions.api.setRowData();

	$("#cancelFridTrial").attr('disabled', true);
	$("#transportvalue").hide();

	$('#delete').attr('disabled', true);

	$("#reject").attr('disabled', true);
	$("#approve").attr('disabled', true);
	var today = new Date();
	var dd = String(today.getDate()).padStart(2, '0');
	var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
	var yyyy = today.getFullYear();

	//today = dd + '-' + mm + '-' + yyyy;

	today = dd + '-' + mm + '-' + yyyy;

	$('#creationID').val(today);
	$('#toDate').val(today);

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		//format : dateFormat,
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#creationID').val($(this).val());
	})

	$('#creationID').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

});

function search() {
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var fromDate = fromDate.split('-');
	var fromDate = fromDate[2] + '-' + fromDate[1] + '-' + fromDate[0];
	var toDate = toDate.split('-');
	var toDate = toDate[2] + '-' + toDate[1] + '-' + toDate[0];
	var vendor = $("#vendorCode").val();

	agGrid.simpleHttpRequest(
			{
				url : "processingcost-setdateview?fromDate=" + fromDate
						+ "&toDate=" + toDate + "&vendor=" + vendor,
			}).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);
		if (len > 0) {
			gridOptions.api.setRowData(data);
		} else {
			gridOptions.api.setRowData();
		}
	});

}

function cancelbtn() {
	$('#reqDltBtn').attr('disabled', true);
	$("#myGrid").show();
	$("#btn1").hide();
	$("#demo").hide();
	$("#hideTbl").show();
	$("#buttonDetails").show();
}

function onSelectionChanged() {
	var selectedRows = gridOptions1.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		$('#delete').attr('disabled', false);
		$("#reject").attr('disabled', false);
		$("#approve").attr('disabled', false);
		$("#add").attr('disabled', true);
	} else {
		$('#delete').attr('disabled', true);
		$("#reject").attr('disabled', true);
		$("#approve").attr('disabled', true);
		$("#add").attr('disabled', false);
	}

}

function deleteCustonClick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		selectedRowsString += selectedRow.budgetId;
	});
	var item = {};
	item.budgetId = selectedRowsString;
	//alert(JSON.stringify(item));
	$.ajax({
		type : "POST",
		url : "create-project-delete",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(item),
		success : function(response) {
			if (response.message == "Success") {
				cancelModalBtn();
				//location.reload();
				 agGrid.simpleHttpRequest({
						url : "create-project-view"
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData = jsonData.viewProjectData;
						var len = allData.length;
						console.log('MAT',len)
						$('#totalReq').find('span').html(len);
						gridOptions1.api.setRowData(allData);
						
					});

			}
		}

	});
	$('#delete').attr("disabled", true);
}

function onQuickFilterChanged() {
	gridOptions1.api
			.setQuickFilter(document.getElementById('quickFilter').value);
}

function addId() {
	 $("#projectId").val("");
	$("#projectname").val("");
	$("#creationID").val("");
	$("#locationID").val("");
	$("#stateID").val("");
	$("#pinID").val("");
	$("#pIncharge").val("");
	$("#cname").val("");
	$("#caddress").val("");
	$("#cstate").val("");
	$("#cPin").val("");
	$("#email").val("");
	$("#mobile").val("");
	$("#remarks").val("");
	$("#status").val("");
	
	$("#stateid2").val("");
	$("#country2").val("");
	$("#country").val("");
	$("#stateid").val("");
	$("#cityb").val("");
	$("#street1").val("");
	$("#street2").val("");
	$("#gstNo").val(""); 
	//
	 $("#shippingName").val("");
	$("#shippingEmail").val("");
	$("#shippingMobileNo").val("");
	$("#shippingContact").val("");
	$("#country1").val("");
	$("#stateid1").val("");
	$("#shippingCity").val("");
	$("#shippingStreet1").val("");
	$("#shippingStreet2").val("");
	$("#shippingPin").val("");
	$("#shippingGstNo").val(""); 
	//$("#activity").empty();
	
	//
	$("#mainPage").show();
	$("#cancel").show();
	$("#saveId").show();
	$("#total").hide();
	$("#searchRowDiv").hide();
	$("#add").hide();
	$("#delete").hide();
	$("#myGrid").hide();
	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
			+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
			+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
			+ '</tr>';
	$("#doctbodyData").append(tbl);
}

/* function cancel() {
	location.reload();
} */

function closeNav1() {
	$("#myGrid").show();
	$("#add").show();
	$("#delete").show();
	$("#total").show();
	$("#searchRowDiv").show();
	$("#cancel").hide();
	$("#saveId").hide();
	$("#mainPage").hide();
	$("#doctbodyData").empty();
	$("#projectId").val("");
	$("#projectname").val("");
	$("#creationID").val("");
	$("#locationID").val("");
	$("#stateID").val("");
	$("#pinID").val("");
	$("#pIncharge").val("");
	$("#cname").val("");
	$("#caddress").val("");
	$("#cstate").val("");
	$("#cPin").val("");
	$("#email").val("");
	$("#mobile").val("");
	$("#remarks").val("");
	$("#status").val("");
	
	$("#stateid2").val("");
	$("#country2").val("");
	$("#country").val("");
	$("#stateid").val("");
	$("#cityb").val("");
	$("#street1").val("");
	$("#street2").val("");
	$("#gstNo").val("");
	
	$("#shippingName").val("");
	$("#shippingEmail").val("");
	$("#shippingMobileNo").val("");
	$("#shippingContact").val("");
	$("#country1").val("");
	$("#stateid1").val("");
	$("#shippingCity").val("");
	$("#shippingStreet1").val("");
	$("#shippingStreet2").val("");
	$("#shippingPin").val("");
	$("#shippingGstNo").val("");
	$("#projectIdvalue").html("");
	//$("#activity").empty();

}

function checkEmpty() {
	var infofileName = true;
	$(".formValidation").remove();
	$('.docNoclss').each(function() {
		/* if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			validationModal("Document Name Required", $(this).attr('id'));
		} */
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				$("#messageParagraph").text("Please Choose a File ");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				return false;
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMore1()
	}
}

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
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");
	if (extension[1] != null && extension[1] != "") {
		$("#uploadHidden_" + counter).val('');
	}
	if (extension[1] == "jpg" || extension[1] == "png"
			|| extension[1] == "jpeg") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
			|| extension[1] == "docx") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	} else {
		var LightImg = "<div class='uploadicon position-l '> </div>";
	}
//	var dltImg = "<i class='ti-close position-l rmv1' ></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
//	$("#dltImage_" + counter).html(dltImg);

	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");

}


function addMore1() {

	var lengthOfTableRow = $("#doctbodyData").children('tr').length;
	var cloneHtml = $("#docTbl tbody tr:first").clone();

	$("#docTbl tbody").append($("#docTbl tbody tr:first").clone());
	$("#myTable tbody tr td:last").html("");
	//var addMore = '<button type="button" class="btn btn-primary tr_clone_add" name="add" onclick="addMore1();"><span class="ti-plus"></span></button>&nbsp;'

	$("#docTbl tbody tr:last").find(".documentclss").val("");
	$("#docTbl tbody tr:last").find(".docNoclss").val("");
	$("#docTbl tbody tr:last").find(".documentclssup").val("");
	$("#docTbl tbody tr:last").find(".uploadedBillCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").empty();
	$("#docTbl tbody tr:last").find(".uploadHidCls").val('');
	$("#docTbl tbody tr:last").find(".imageName").empty();
	var j = 0;
	$("#docTbl > #doctbodyData > tr").each(function(i) {

		var selectInput = $(this).find('select');
		var textInput = $(this).find('input');
		var fileInput = $(this).find('file');
		var divInput = $(this).find('div');
		var label = $(this).find('label');
		var iInput = $(this).find('i');
		selectInput.eq(0).attr('id', "docid_" + i);

		textInput.eq(1).attr('id', "docnoid_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		label.eq(1).attr('for', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(4).attr('id', "uploadedBillDiv_" + i);
		divInput.eq(5).attr('id', "imageName_" + i);
		//divInput.eq(6).attr('id', "dltImage_" + i);
		iInput.eq(0).attr('id', "clickImg_" + i);
		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

}

function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
}

//for closing modal box for delete ind product
function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
}

function deletAttachmentRow() {
	var lengthOfTableRow1 = 0;
	$("#docTbl > #doctbodyData > tr").each(function() {
		lengthOfTableRow1 = lengthOfTableRow1 + 1;
	})
	var id = $("#dltValue").val();
	$("#" + id).closest('tr').remove();
	closeDeleteConfirm();
	if (lengthOfTableRow1 == 1) {
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
				+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0"></div> </td>'
				+ '</tr>';
		$("#doctbodyData").append(tbl);
	}
}


//FOR EDIT 

function editPage(id) {
	var editId = id.split(",");

    var projectId = editId[0];
    
    var shippingId = editId[1];
    
    $("#demo").show();
	$('#delete1').attr("disabled", true);
	$("#searchRowDiv").hide();
	$("#myGrid").hide();
	$("#reqTable").hide();
	//$(".btn-hs").hide();
	$("#add").hide();
	$("#save").show();
	$("#Cancel").show();
	$("#delete").hide();
	$("#totalReq").hide();
    //
     $("#mainPage").show();
	$("#cancel").show();
	$("#saveId").show();
	$("#total").hide();
	$("#searchRowDiv").hide(); 
	
    
	$.ajax({
				type : "GET",
				url : "create-project-edit?id=" + projectId,
				success : function(response) {
                	
					$("#projectId").val(response.body.projectId);					
					$("#projectIdvalue").html(response.body.projectId);
					$("#projectname").val(response.body.projectName);
					$("#creationID").val(response.body.creationDate);
					$("#locationID").val(response.body.location);
                    $("#country2").val(response.body.country2);
					$("#stateid2").val(response.body.stateid2);
					$("#pinID").val(response.body.pPin);
					$("#pIncharge").val(response.body.pIncharge);					
					$("#cname").val(response.body.cName);
					$("#caddress").val(response.body.cAddress);
					$("#country").val(response.body.country);
					$("#stateid").val(response.body.stateid);
					$("#cityb").val(response.body.cityb);
					$("#street1b").val(response.body.street1b);
					$("#street2b").val(response.body.street2b);
					$("#cPin").val(response.body.cPin);
					$("#email").val(response.body.email);
					$("#mobile").val(response.body.mobile);
					$("#remarks").val(response.body.remark);
					$("#status").val(response.body.status);
					$("#street1").val(response.body.billingStreet1);
					$("#street2").val(response.body.billingStreet2);
					$("#gstNo").val(response.body.billingGstNo);
					//alert(response.body.stateid1);
					getStateDataOnEdit(response.body.stateid2);
					getBillingStateDataOnEdit(response.body.stateid);
					getShippingStateDataOnEdit(response.body.stateid1);
					activityOptions.api.setRowData(response.body.shippingList);
					
					console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@",response.body);
					//addId();

					$("#doctbodyData").empty();
					var documentList = response.body.documentList;
					if (documentList != null
							&& documentList != "") {

						for (var i = 0; i <documentList.length; i++) {
							//console.log(JSON.stringify(data));

							//console.log('in for loop documentList========>>>>'+JSON.stringify(data[0].documentList[i].documnentName));

							var tbl = '<tr>'
									+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
									+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
									+ '<td><div class="form-group"> <input type="text" value="'+documentList[i].documnentName+'" class="form-control docNoclss" id="docnoid_'+i+'"> </div></td>'
									+ '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_'+i+'" id="uploadFor_'+i+'"> <i class="ti-pencil" id="clickImg_'+i+'"></i> </label>'
									+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
									+ i
									+ '"name="userImage" onchange="saveMultiFile(event)" /> </div>'
									+ '</div> <input type="hidden" id="uploadHidden_'+i+'" value="'+documentList[i].fileName+'" class="uploadHidCls">'
									+ '<div id="uploadedBillDiv_'+i+'" align="center" class="uploadedBillCls"><div class="uploadicon position-l">'
									+ documentList[i].action
									+ '</div></div>'
									+ '<div id="imageName_'+i+'" class="imageName">'
									+ documentList[i].fileName
									+ '</div>'
									+ '<input type="hidden" id="editId_'+i+'" value="'+ documentList[i].vendorRfqId+'">'
									+ '<div id="dltImage_'+i+'" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
									+ '</tr>';

							$("#doctbodyData").append(tbl);
						}
					} else {
						var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
								+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
								+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_"></div></td>'
								+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
								+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
								+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" ></div><input type="hidden" id="editId_0></td>'
								+ '</tr>';
						$("#doctbodyData").append(tbl);
					}
				}
			});
}



function deleteProject() {

	$('#deleteProject').modal('show');
}

function cancelModalBtn() {
	$('#deleteProject').modal('hide');

}

//FOR DELETE

function deleteOnclick() {
	var selectedRows = gridOptions1.api.getSelectedRows();
	var id = selectedRows[0].projectId;
	$.ajax({
		type : "POST",
		url : "create-project-delete?id=" + id,
		success : function(response) {
			if (response.message == "Success") {

				$('#deleteProject').modal('hide');

				$('#delete').attr("disabled", true);
				 agGrid.simpleHttpRequest({
						url : "create-project-view"
					}).then(function(data) {
						var jsonData = JSON.parse(data.body);
						var allData = jsonData.viewProjectData;
						var len = allData.length;
						console.log('MAT',len)
						$('#totalReq').find('span').html(len);
						gridOptions1.api.setRowData(allData);
						
					});

			} else {

			}
		},
		error : function(data) {
			console.log(data)
		}
	})
}

function isNumberKey(evt) {
	  var charCode = (evt.which) ? evt.which : evt.keyCode
	  if (charCode > 31 && (charCode < 48 || charCode > 57))
	    return false;
	  return true;
	}



// save data in aggrid table sidenav save

function saveTableData() {
	    var rowEdit = $("#rowEdit").val();
	    var item = {};
	    var data = 1;

	    item.slNo = data;
	    activityOptions.api.forEachNode(function(rowNode, index) {
	        if (!rowEdit) {
	            data = data + 1;
	            item.slNo = data;
	        }
	        else {
	            item.slNo = rowEdit;
	        }
	    });

	    item.shippingName = $("#shippingName").val();
	    item.shippingAddress = $("#shippingAddress").val();
	    item.country1 = $('#country1').val();
	    //item.country1Name = $("#country1 option:selected").text();
	    item.stateid1 = $('#stateid1').val();
	   // item.stateid1Name = $("#stateid1 option:selected").text();

	    item.shippingCity = $("#shippingCity").val();
	    item.shippingStreet1 = $("#shippingStreet1").val();
	    item.shippingStreet2 = $("#shippingStreet2").val();
	    item.shippingGstNo = $("#shippingGstNo").val();
	    item.shippingPin = $("#shippingPin").val();
	    item.shippingEmail = $("#shippingEmail").val();
	    item.shippingMobileNo = $("#shippingMobileNo").val();
	    item.shippingContact = $("#shippingContact").val();

	    var quot = [];

	    if (rowEdit) {
	        var rowNode = activityOptions.api.getRowNode(rowEdit);
	        rowNode.setData(item);
	    } else {
	        activityOptions.api.forEachNode(function(rowNode, index) {
	            quot.push(rowNode.data);
	        });

	        quot.push(item);
	        activityOptions.api.setRowData(quot);
	    }

	    closeNav();
	    $("#shippingName").val('');
	    $("#shippingAddress").val('');
	    $("#shippingCountry").val('');
	    $("#shippingState").val('');
	    $("#shippingCity").val('');
	    $("#shippingStreet1").val('');
	    $("#shippingStreet2").val('');
	    $("#shippingPin").val('');
	    $("#shippingEmail").val('');
	    $("#shippingMobileNo").val('');
	    $("#country1").val('');
	    $("#stateid1").val('');
	    $("#shippingGstNo").val('');
	    $("#shippingContact").val('');

	    $("#rowEdit").val(null);
}


		/* 
		Edit Shipping Details
		 */
		 
		function rowEdit(slNo) {
			
			var rowNode = activityOptions.api.getRowNode(slNo);
			//console.log(rowNode)
			openNav();
			$("#rowEdit").val(slNo);
			$("#shippingId").val(rowNode.data.shippingId);
			$("#shippingName").val(rowNode.data.shippingName);
			$("#shippingAddress").val(rowNode.data.shippingAddress);
			$("#shippingCountry").val(rowNode.data.shippingCountry);
			
			var sta=rowNode.data.stateid1;
			var count=rowNode.data.country1;
					
			getShippingStateDataOnEdit11(sta,count);
			
			//$("#shippingState").val(rowNode.data.shippingState);
			$("#shippingCity").val(rowNode.data.shippingCity);
			$("#shippingStreet1").val(rowNode.data.shippingStreet1);
			$("#shippingStreet2").val(rowNode.data.shippingStreet2);
			$("#shippingGstNo").val(rowNode.data.shippingGstNo);
			$("#shippingPin").val(rowNode.data.shippingPin);
			$("#shippingEmail").val(rowNode.data.shippingEmail);
			$("#shippingMobileNo").val(rowNode.data.shippingMobileNo);
			$("#shippingContact").val(rowNode.data.shippingContact);
			$("#country1").val(rowNode.data.country1);
			$("#stateid1").val(rowNode.data.stateid1);
		}




//for billing country dropdown

	function stateList() {
	    var countryId = $("#country").val();

	    if (countryId) {
	        $.ajax({
	            type: "POST",
	            url: "create-project-billing-state-list",
	            dataType: 'json',
	            contentType: 'application/json',
	            data: countryId,
	            success: function(response) {
	            	 console.log("Response data:", response);
	                if (response.message == "success") {
	                    console.log(response);
	                    $("#stateid").empty();
	                    $("#stateid").append("<option value=''>Select</option>");

	                    for (var i = 0; i < response.body.length; i++) {
	                        var option = $("<option></option>");
	                        $(option).val(response.body[i].key);
	                        $(option).html(response.body[i].name);
	                        $("#stateid").append(option);
	                    }
	                }
	            },
	            error: function(data) {
	                console.log(data);
	                $("#stateid").empty();
	                $("#stateid").append("<option value=''>Select</option>");
	            }
	        })
	    } else {
	        $("#stateid").empty();
	        $("#stateid").append("<option value=''>Select</option>");
	    }
	}

	//for shipping country dropdown

	function stateList1() {
	    var countryId1 = $("#country1").val();
	    if (countryId1) {
	        $.ajax({
	            type: "POST",
	            url: "create-project-shipping-state-list",
	            dataType: 'json',
	            contentType: 'application/json',
	            data: countryId1,
	            success: function(response) {
	            	 console.log("Response data:", response);
	                if (response.message == "success") {
	                    console.log(response);
	                    $("#stateid1").empty();
	                    $("#stateid1").append("<option value=''>Select</option>");

	                    for (var i = 0; i < response.body.length; i++) {
	                        var option = $("<option></option>");
	                        $(option).val(response.body[i].key);
	                        $(option).html(response.body[i].name);
	                        $("#stateid1").append(option);
	                    }
	                }
	            },
	            error: function(data) {
	                console.log(data);
	                $("#stateid1").empty();
	                $("#stateid1").append("<option value=''>Select</option>");
	            }
	        })
	    } else {
	        $("#stateid1").empty();
	        $("#stateid1").append("<option value=''>Select</option>");
	    }
	}
	
	//for project country dropdown
	
function stateList2() {
	    var countryId2 = $("#country2").val();
	    if (countryId2) {
	        $.ajax({
	            type: "POST",
	            url: "create-project-state-list",
	            dataType: 'json',
	            contentType: 'application/json',
	            data: countryId2,
	            success: function(response) {
	            	 console.log("Response data:", response);
	                if (response.message == "success") {
	                    console.log(response);
	                    $("#stateid2").empty();
	                    $("#stateid2").append("<option value=''>Select</option>");

	                    for (var i = 0; i < response.body.length; i++) {
	                        var option = $("<option></option>");
	                        $(option).val(response.body[i].key);
	                        $(option).html(response.body[i].name);
	                        $("#stateid2").append(option);
	                    }
	                }
	            },
	            error: function(data) {
	                console.log(data);
	                $("#stateid2").empty();
	                $("#stateid2").append("<option value=''>Select</option>");
	            }
	        })
	    } else {
	        $("#stateid2").empty();
	        $("#stateid2").append("<option value=''>Select</option>");
	    }
	}
	
//FOR PROJECT STATE EDIT 

function getStateDataOnEdit(stateid2) {
	var country2 = $("#country2").val();
	if (country2) { 
		$.ajax({
			type : "GET",  
			url : "create-project-state-list-project?id=" + country2,
			success : function(response) {
				if (response.message == "success") {
					$("#stateid2").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#stateid2").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid2").append(option);
					}
					$("#stateid2").val(stateid2);
				}
			},
			error : function(e) { 
			}
		});
	} else {
		$("#stateid2").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#stateid2").append(option);
	}
}

//FOR BILLING STATE EDIT 

function getBillingStateDataOnEdit(stateid) {
	var country = $("#country").val();

	if (country) { 
		$.ajax({
			type : "GET",  
			url : "create-project-state-list-billing?id=" + country,
			success : function(response) {
				if (response.message == "success") {
					$("#stateid").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#stateid").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid").append(option);
					}
					$("#stateid").val(stateid);
				}
			},
			error : function(e) { 
			}
		});
	} else {
		$("#stateid").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#stateid").append(option);
	}
}


//FOR SHIPPING STATE EDIT 

function getShippingStateDataOnEdit(stateid1) {
	var country1 = $("#country1").val();

	if (country1) { 
		$.ajax({
			type : "GET",  
			url : "create-project-state-list-shipping?id=" + country1,
			success : function(response) {
				if (response.message == "success") {
					$("#stateid1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#stateid1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid1").append(option);
					}
					$("#stateid1").val(stateid1);
				}
			},
			error : function(e) { 
			}
		});
	} else {
		$("#stateid1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#stateid1").append(option);
	}
}



function getShippingStateDataOnEdit11(sta,countryid) {

	if (countryid) { 
		$.ajax({
			type : "GET",  
			url : "create-project-state-list-shipping?id=" + countryid,
			success : function(response) {
				if (response.message == "success") {
					$("#stateid1").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#stateid1").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#stateid1").append(option);
					}
					$("#stateid1").val(sta);
				}
			},
			error : function(e) { 
			}
		});
	} else {
		$("#stateid1").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#stateid1").append(option);
	}
}

/* 
Delete quotation
 */
function deleteQuotation() {

	$('#deleteQuot').modal('show');
}
function deleteDetails() {

	$('#deleteProduct').modal('show');
}

function cancelModalBtn() {
	$('#deleteQuot').modal('hide');
	//$("#deleteModalBtn").removeAttr("disabled");
	
}

function cancelModalProductBtn() {

	//$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteProduct').modal('hide');
}

function rowSelect() {
	var selectedRows = activityOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});
	 var selectedNodes = activityOptions.api.getSelectedNodes();
	 var selectedData = selectedNodes.map(node => node.data);
	 var approveStatus= selectedData.map(node => node.approveStatus);
	 
	if (rowCount > 0) {
		//$('#approveQuotation').attr("disabled", false);
		$('#add').attr("disabled", true);
		
		if(approveStatus=='Approved'){
			$('#approveQuotation').attr("disabled", true);
			if(selectedData.map(node => node.qutStatus) == "Not Generated"){
			$('#delete').attr("disabled", true);
			$('#porder').attr("disabled", false);
			$('#worder').attr("disabled", false);
			}
			else{
				$('#delete').attr("disabled", true);
				$('#porder').attr("disabled", true);
				$('#worder').attr("disabled", true);
			}
		}else{
			$('#delete').attr("disabled", false);
			$('#approveQuotation').attr("disabled", false);
			$('#porder').attr("disabled", true);
			$('#worder').attr("disabled", true);
		}
	} else {
		$('#approveQuotation').attr("disabled", true);
		$('#add').attr("disabled", false);
		$('#delete').attr("disabled", true);
		$('#reqAppvBtn').attr("disabled", true);
		$('#porder').attr("disabled", true);
		$('#worder').attr("disabled", true);
	}
}


function deleteProductOnclick() {
	var selectedRows = activityOptions.api.getSelectedRows();
	activityOptions.api.applyTransaction({
		remove : selectedRows

	});
	cancelModalProductBtn();
	//DELETE GST,SUBTOTAL,GRANDTOTAL,SGST,CGST,IGST
	var sum = $("#subTotal").val();
	var gstRate = $("#gstRate").val();
	var qCGST = $("#qCGST").val();
	var qSGST = $("#qSGST").val();
	var qIGST = $("#qIGST").val();
	var grandTotal = $("#grandTotal").val();
	var itemIgst = $("#itemIgst").val();
	var itemCgst = $("#itemCgst").val();
	var itemSgst = $("#itemSgst").val();
	var len = selectedRows.length;
	for (var i = 0; i < len; i++) {

		sum = sum - selectedRows[i].lineTotal;
		var taxType = $("#taxType").val()
		if (taxType == "true") {

			selectedRows[i].itemIgst = selectedRows[i].lineTotal
					* selectedRows[i].gstRate / 100;
			qIGST = qIGST - selectedRows[i].itemIgst;
			grandTotal = sum + qIGST;
		} else {
			selectedRows[i].itemCgst = selectedRows[i].lineTotal
					* selectedRows[i].gstRate / 200;
			selectedRows[i].itemSgst = selectedRows[i].lineTotal
					* selectedRows[i].gstRate / 200;

			qCGST = qCGST - selectedRows[i].itemCgst;
			qSGST = qSGST - selectedRows[i].itemSgst;
			grandTotal = sum + qCGST + qSGST;
		}

	}

	$("#itemIgst").val(itemIgst);
	$("#itemCgst").val(itemCgst);
	$("#itemSgst").val(itemSgst);
	$("#subTotal").val(sum);
	$("#qIGST").val(qIGST);
	$("#qCGST").val(qCGST);
	$("#qSGST").val(qSGST);
	$("#grandTotal").val(grandTotal)
}