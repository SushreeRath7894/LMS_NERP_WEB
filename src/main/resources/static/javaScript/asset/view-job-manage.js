 
	/* -------------------search bar for mygrid------------------------ */

	var timeout;
/*	function onQuickFilterChanged() {
	 var id1 = document.getElementById("closeKey");
		id1.style.display = "block"; 
		
		clearTimeout(timeout); 
		timeout = setTimeout(function() {
		var value = $("#quickFilter").val();

			if(value == "" || value == null || value == 'null'){
				$("#currentPageno").val("1");
				getAllJobView();
				id1.style.display = "none";
			}else{
				$('.loader').show();
				agGrid.simpleHttpRequest({
					url : "view-job-manage-all-data-search?search="+value,
				}).then(function(data) {
					var jsonData = JSON.parse(data.body[0]);
					
					if (jsonData.viewTicket === null) {
						data = [];
						gridOptions.api.setRowData(data);
						$('.loader').hide();
					} else {
						var allData = jsonData.viewTicket;
						
						if(allData != "" || allData != null || allData != 'null'){
							var len = allData.length;
							$('#totalReq').find('span').html(len);
						}
						
						gridOptions.api.setRowData(allData);
						$('.loader').hide();
					}

				});
				
			}
			}, 500);
	}
	function cancelBar() {
		$("#quickFilter").val("");
		onQuickFilterChanged();
	
	}*/
//for activity table
var activityDefs = [
	{
		headerName : "Status",
		field : "status",
		width : 95,
		cellStyle : {
			textAlign : 'center'
		},
		cellRenderer : function(params) {
			if(params.data.status==0){
				return '<input class="checkbox" type="checkbox" name="checkBoxName" id="'+"status"+params.rowIndex+'">';
			}else{
				return '<input class="checkbox" type="checkbox" name="checkBoxName" id="'+"status"+params.rowIndex+'" checked>';
			}
				
		}
	},
 	{
			headerName : "Policy Name",
			field : "policyName",
			width : 130,
			cellStyle : {
				textAlign : 'center'
			},
		},
		{
			headerName : "Policy Description",
			field : "description",
			width : 300,
			cellStyle : {
				textAlign : 'center'
			},
		},{
			headerName : "Priority",
			field : "priority",
			width :100,
			cellStyle : {
				textAlign : 'center'
			}
		},{
			headerName : "Type",
			field : "tasktype",
			width :80,

		},{
			headerName : "Unit",
			field : "uom",
			width :80,
		},{
			headerName : "Min Range",
			field : "minrange",
			width :90,
			cellStyle : {
				textAlign : 'center'
			}
		},{
			headerName : "Max Range",
			field : "maxrange",
			width :90,
			cellStyle : {
				textAlign : 'center'
			}
		},{
			headerName : "Result",
			field : "result",
			width :90,
			cellRenderer : function(params) {
				if(params.data.tasktype=="Quantitative"){
					return '<input type="number" class="aprvQty" value="' + params.data.result + '" min=0 id="aprvQty' + params.rowIndex + '">';
		        }else{
					return '';
				}
					
			}
		}];

// let the grid know which columns and what data to use product table
var activityOptions = {
	columnDefs : activityDefs,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 200
	},

};
var activityEqDefs = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		sortable : false,
		filter : false,
		resizable : true,
		width : 30,
		pinned : 'left'
	},
	{
		headerName : "Asset Id",
		field : "assetId",
		hide : true,
	}, {
		headerName : "Equipement Category",
		field : "equipementCat",
		cellStyle : {
			textAlign : 'center'
		},
	//	hide: true,
	}, {
		headerName : "Equipement Subcategory",
		field : "equipementScat",
		cellStyle : {
			textAlign : 'center'
		},
	//	hide: true,
	}, {
		headerName : "Equipement Category",
		field : "equipementCat1",
		cellStyle : {
			textAlign : 'center'
		},
		hide: true,
	}, {
		headerName : "Equipement Subcategory",
		field : "equipementScat1",
		cellStyle : {
			textAlign : 'center'
		},
		hide: true,
	}, {
		headerName : "Equipement Quantity",
		field : "equipementQty",
		cellStyle : {
			textAlign : 'center'
		},
	},{
		headerName : "Equipement Remark",
		field : "description",
		cellStyle : {
		textAlign : 'center'
	},
	}];


//let the grid know which columns and what data to use product table
var activityEquipOptions = {
columnDefs : activityEqDefs,
rowSelection : 'single',
groupSelectsChildren : true,
suppressRowClickSelection : true,
suppressAggFuncInHeader : true,
defaultColDef : {
	sortable : true,
	filter : true,
	resizable : true,
	width : 170
}

};

var activityEqDefsView = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		sortable : false,
		filter : false,
		resizable : true,
		width : 30,
		pinned : 'left'
	},
	{
		headerName : "Asset Id",
		field : "assetId",
		hide : true,
	}, {
		headerName : "Equipement Category",
		field : "equipementCat",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Equipement Subcategory",
		field : "equipementScat",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Equipement Quantity",
		field : "equipementQty",
		cellStyle : {
			textAlign : 'center'
		},
	},{
		headerName : "Equipement Remark",
		field : "description",
		cellStyle : {
		textAlign : 'center'
	},
	}];


//let the grid know which columns and what data to use product table
var activityEquipOptionsView = {
columnDefs : activityEqDefsView,
rowSelection : 'multiple',
groupSelectsChildren : true,
suppressRowClickSelection : true,
suppressAggFuncInHeader : true,
defaultColDef : {
	sortable : true,
	filter : true,
	resizable : true,
	width : 170
}

};
var activityEqDefs1 = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		sortable : false,
		filter : false,
		resizable : true,
		width : 30,
		pinned : 'left'
	},
	{
		headerName : "Asset Id",
		field : "assetId",
		hide : true,
	}, {
		headerName : "Equipement Category",
		field : "equipementCat",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Equipement Subcategory",
		field : "equipementScat",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Equipement Quantity",
		field : "equipementQty",
		cellStyle : {
			textAlign : 'center'
		},
	},{
		headerName : "Equipement Remark",
		field : "description",
		cellStyle : {
		textAlign : 'center'
	},
	}];


//let the grid know which columns and what data to use product table
var activityEquipOptions1 = {
columnDefs : activityEqDefs1,
rowSelection : 'multiple',
groupSelectsChildren : true,
suppressRowClickSelection : true,
suppressAggFuncInHeader : true,
defaultColDef : {
	sortable : true,
	filter : true,
	resizable : true,
	width : 170
}

};

//for activity table
function showResultModal(){
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var assetid= selectedData.map(node => node.assetid);
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRow = selectedRows[0]; 
	console.log("selectedData selectedRow====",selectedRow)
	if(assignmentType=="MAINTAIN"){
		//$('input[name="checkBoxName"]').prop('disabled', false);
		var toDayDate = (new Date()).toISOString().split('T')[0];
		var newDate = changeDateFormat(toDayDate);
		$("#filterFromDate").val(newDate);
		var baseid= selectedData.map(node => node.baseid);
		$("#allocationid").val(baseid);
		/*
		var today = new Date();
		today.setDate(today.getDate() + 1);
		var toDayDate = today.toISOString().split('T')[0];
		var dateParts = toDayDate.split("-");
		var newDate = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];*/
	//	alert(newDate);
		var fromDate= newDate;
		var aid = $("#allocationid").val();
     var shift = $("#shiftType1").val();
	agGrid.simpleHttpRequest(
			{
				url : 'view-job-manage-maintenance-policylist?aid='+ aid+'&pid='+fromDate+'&shift='+shift
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData=jsonData.Asset;
					var equipe=jsonData.Equipement;
					console.log("allData====",allData)
					if(allData!=null){
						activityOptions1.api.setRowData(allData);
					}else{
						activityOptions1.api.setRowData("");
					}
					//	activityEquipOptions1.api.setRowData("");
				});
		$('#savechangebtn').show();
		$('#fdate').hide();
		$('#ttbtn').hide();
		$('#myModalForAsset').modal('show');
		$("#newchild1").show();
		$("#deleteChild1").show();
	}else{
		if(assetid[0]!=''){
			$('#main').show();
		}else{
			$('#main').hide();
		}
		activityEquipOptions.api.setRowData("");
		$('#resultModal').modal('show');
		$('#docTbl').on('click', '.rmv1', function() {
			openDeleteConfirm();
			var value = $(this).parent("div").attr("id");

			$("#dltValue").val(value);
		});
		var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
				+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
				+ '</tr>';
		$("#doctbodyData").html(tbl);
	}
	

}
function maintainModalView(aid){
	
	//$('input[name="checkBoxName"]').prop('disabled', true);
	
	$('#savechangebtn').hide();
	$('#fdate').show();
	$('#ttbtn').show();
	var toDayDate = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(toDayDate);
	$("#filterFromDate").val(newDate);
	$("#allocationid").val(aid);
	filterData();
	$('#myModalForAsset').modal('show');
	$("#newchild1").hide();
	$("#deleteChild1").hide();
}
function filterData(){
	
	activityEquipOptions.api.setRowData("");
	activityOptions1.api.setRowData("");
	var fromDate = $("#filterFromDate").val();
	var aid = $("#allocationid").val();
     var shift = $("#shiftType1").val();
	agGrid.simpleHttpRequest(
			{
				url : 'view-job-manage-maintenance-policylist-search?aid='+ aid+'&pid='+fromDate+'&shift='+shift
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				console.log("DATA:::",jsonData);
				var allData=jsonData.Asset;
				var equipe=jsonData.Equipement;
				if(allData!=null){
					activityOptions1.api.setRowData(allData);
				}else{
					activityOptions1.api.setRowData("");
				}
				if(equipe!=null){
				//	activityEquipOptions1.api.setRowData(equipe);
				}else{
		//			activityEquipOptions1.api.setRowData("");
				}
			});

	
}
function changeDateFormat(inputDate) { // expects Y-m-d
	var splitDate = inputDate.split('-');
	if (splitDate.count == 0) {
		return null;
	}
	var year = splitDate[0];
	var month = splitDate[1];
	var day = splitDate[2];

	return day + '-' + month + '-' + year;
}
	
function cancelMain(){
	$("#description, #resultStatus , #saveAttachmentBtn , #deleteChild , #newchild , #corrDate , #corrTime , #corrCost").prop('disabled', true);
	const tiPlusElements = document.querySelectorAll(".ti-plus");
	// Loop through and disable each element
	tiPlusElements.forEach(element => {
	  element.style.pointerEvents = "none"; // Prevent clicks
	  element.style.opacity = "0.5"; // Optional: Visual cue that it's disabled
	});
	$('#saveBtn, #cancel, #AddEquip1, #saveAttachmentBtn, #dateCalender, #timeCalender').hide();
	$('#rejectBtn, #acceptBtn, #saveInspection').show();
}	
	
function cancelMain1(){
	$(" #assetemployee, #fromDateCalender, #shiftType1, #search").prop('disabled', true);
	$('#saveBtn1, #cancel1').hide();
	$('#saveInspection1').show();
}	
function newMain1(){
	$(".formValidation").remove();
	$("#assetemployee ,#fromDateCalender, #shiftType1, #search").prop('disabled', false);
	$('#saveBtn1, #cancel1,#search').show();
	$('#saveInspection1').hide();
}

function newMain(){
	$(".formValidation").remove();
	$("#description, #resultStatus , #saveAttachmentBtn , #doctbodyData , #deleteChild , #newchild , #corrDate , #corrTime , #corrCost").prop('disabled', false);
	$('#saveBtn, #cancel, #saveAttachmentBtn, #dateCalender, #timeCalender').show();
	$('#rejectBtn, #acceptBtn, #saveInspection').hide();
}

function showResultModalView(asnid) {
    agGrid.simpleHttpRequest({
        url: 'view-job-manage-result-view?id=' + asnid
    }).then(function (data) {
        const jsonData = JSON.parse(data.body[0]);
        const allData = jsonData.attachment;

        if (allData && allData.length > 0) {
            const details = allData[0];

            $("#description").val(details.desc || "");
            $("#resultStatus").val(details.status || "");
            $("#ticketBoxTrck").html(details.ticketno || "");
            $("#corrDate").val(details.actualDate || "");
            $("#corrTime").val(details.actualTime || "");
            $("#corrCost").val(details.actualCost || "");

            // Equipments
            if (details.equipement) {
                activityEquipOptions.api.setRowData(details.equipement);
            } else {
                activityEquipOptions.api.setRowData([]);
            }

            // Documents
            const documents = details.documents || [];
            let imagesdiv = "";

            if (documents.length > 0) {
                documents.forEach((doc, index) => {
                    const fileName = doc.fileName || "";
                    const fileUrl = doc.docurl || "";
                    const ext = getFileExtension(fileName);
                    const encodedUrl = window.btoa(fileUrl);

                    // File icon
                    let icon = "bi-file-earmark";
                    if (["jpg", "jpeg", "png", "webp"].includes(ext)) icon = "fa fa-file-image";
                    else if (ext === "pdf") icon = "fa fa-file-pdf";
                    else if (["xls", "xlsx"].includes(ext)) icon = "fa fa-file-excel";
                    else if (["doc", "docx"].includes(ext)) icon = "fa fa-file-word";

                    imagesdiv += `<tr>
                        <td style="display:none" align="center" class="pdb-24">
                            <input class="checkCls" type="checkbox" id="check${index}"><label for="check${index}"></label>
                        </td>
                        <td style="display:none">
                            <div class="form-group">
                                <select class="form-control documentclss" id="docid_${index}" onblur="removeValid(event);">
                                    <option value="">Select</option>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <input type="text" value="${doc.documnentName || ''}" class="form-control docNoclss" id="docnoid_${index}">
                            </div>
                        </td>
                        <td class="d-flex align-items-center">
                            <div class="control-group position-r">
                                <label class="custom-file-upload" for="uploadDoc_${index}" id="uploadFor_${index}">
                                    <i class="ti-pencil" id="clickImg_${index}"></i>
                                </label>
                                <div class="controls">
                                    <input type="file" class="document" id="uploadDoc_${index}" onchange="saveMultiFile(event)" />
                                </div>
                            </div>
                            <input type="hidden" id="uploadHidden_${index}" class="uploadHidCls" value="${fileUrl}">
                            <div id="uploadedBillDiv_${index}" align="center" class="uploadedBillCls">
                                <a class="example-image-link" href="${fileUrl}" target="_blank" title="${fileName}">
                                    <i class="${icon} img-color"></i>
                                </a>
                            </div>
                            <div id="imageName_${index}" class="imageName">${fileName}</div>
                            <div id="dltImage_${index}" class="dltImage custom-file-delete">
                                <i class="ti-close position-l rmv1" onclick="openDeleteConfirm('${index}')"></i>
                            </div>
                        </td>
                    </tr>`;
                });
            } else {
                imagesdiv = generateEmptyUploadRow(0);
            }

            $("#doctbodyData").html(imagesdiv);
        } else {
            // Reset fields
            $("#description").val("");
            $("#resultStatus").val("");
            $("#ticketBoxTrck").html("");
            $("#corrDate").val("");
            $("#corrTime").val("");
            $("#corrCost").val("");
            activityEquipOptions.api.setRowData([]);
            $("#doctbodyData").html(generateEmptyUploadRow(0));
        }

        //$('#resultModalViewTrack').modal('show'); // Enable this if using modal
    });
}

// Helper to get extension
function getFileExtension(fileName) {
    return fileName.split('.').pop().toLowerCase();
}

// Generates a single empty document upload row
function generateEmptyUploadRow(index) {
    return `<tr>
        <td style="display:none" align="center" class="pdb-24">
            <input class="checkCls" type="checkbox" id="check${index}"><label for="check${index}"></label>
        </td>
        <td style="display:none">
            <div class="form-group">
                <select class="form-control documentclss" id="docid_${index}" onblur="removeValid(event);">
                    <option value="">Select</option>
                </select>
            </div>
        </td>
        <td>
            <div class="form-group">
                <input type="text" value="" class="form-control docNoclss" id="docnoid_${index}" onblur="checkForDuplicateEntry(event)">
            </div>
        </td>
        <td class="d-flex align-items-center">
            <div class="control-group position-r">
                <label class="custom-file-upload" for="uploadDoc_${index}" id="uploadFor_${index}">
                    <i class="ti-plus" id="clickImg_${index}"></i>
                </label>
                <div class="controls">
                    <input type="file" class="document" id="uploadDoc_${index}" name="userImage" onchange="saveMultiFile(event)" />
                </div>
            </div>
            <input type="hidden" id="uploadHidden_${index}" class="uploadHidCls">
            <div id="uploadedBillDiv_${index}" align="center" class="uploadedBillCls"></div>
            <div id="imageName_${index}" class="imageName"></div>
            <div id="dltImage_${index}" class="dltImage custom-file-delete"></div>
        </td>
    </tr>`;
}



function extractFilename(url) {
    // Split the URL by '/'
    var urlParts = url.split('/');
    // Get the last part of the URL, which is the filename
    var filename = urlParts[urlParts.length - 1];
    return filename;
}
//Document upload section 

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
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></span>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></span>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></span>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
			|| extension[1] == "docx") {
		var LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></span>";
	} else {
		var LightImg = "<span class='uploadicon position-l '> </div>";
	}
	var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm()'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);
	$("#dltImage_" + counter).addClass("custom-file-delete");

	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");

}
//checkEmpty()  

function checkEmpty(event) {
	
	
	var infofileName = true;
	$('.docNoclss').each(function() {
		if ($(this).val() == null || $(this).val() == "") {
			infofileName = false;
			toastr.error("Document Name Required", $(this).attr('id'));
		}
		event.preventDefault();
	});

	var mulDocInfo = true;
	if (infofileName) {
		$('#doctbodyData > tr').each(function() {
			if ($(this).closest('tr').find('.imageName').text()) {
				mulDocInfo = true;
			} else {
				mulDocInfo = false;
				toastr.error("Please Choose a File!");
			}

		});
	}
	if (infofileName && mulDocInfo) {
		addMore1()
	}
	
	
}
//addmore doc fun for notice
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
	$("#docTbl tbody tr:last").find(".dltImage").empty();

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
		label.eq(1).attr('for', "uploadDoc_" + i);
		iInput.eq(0).attr('id', "clickImg_" + i);
		textInput.eq(2).attr('id', "uploadDoc_" + i);
		textInput.eq(3).attr('id', "uploadHidden_" + i);
		divInput.eq(4).attr('id', "uploadedBillDiv_" + i);
		divInput.eq(5).attr('id', "imageName_" + i);
		divInput.eq(6).attr('id', "dltImage_" + i);

		j++;
	})
	$("#clickImg_" + (j - 1)).removeClass("ti-pencil");
	$("#clickImg_" + (j - 1)).addClass("ti-plus");

}

function openDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('show');
}

//for closing modal box for dlt Notice 

function closeDeleteConfirm() {
	$("#dltValue").val("");
	$('#deleteAttachment').modal('hide');
}


uploadList=[];
//function to check duplicate entry of item name
function checkForDuplicateEntry(event){
	var document = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	 var count=0;
	 
	/*  $(".documentTypeCls").each(function(){
		 if(document == $(this).val()){
			 alert(count)
			 count++; 
		 }
	 }) */
	/*  uploadData = {};
	 var x = [];
	
					uploadData['documnentName'] = $("#docnoid_" + 0).val();
					
					uploadData['documentFile'] = x;
					uploadData['fileName'] = fileName;
					uploadData['imageNameEdit'] = $(this).find(
							".uploadHidCls").val();
					uploadList.push(uploadData); */
					
	/*  if(count>=2){ 
		 alert(count)
		    $("#messageParagraph").text("Document Type Already Selected");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$(currentFldId).val("");	 
			$("#documentType_" + counter).val("");	 
		    $(currentFldId).val("");	 
		    $("#documentType_" + counter).val("");	 
	  
		 return false;
	 }else{
		 return true;
	 }
  */
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
			+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)"></div></td>'
			+ '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
			+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
			+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
			+ '</tr>';
	$("#doctbodyData").append(tbl);
	}
}

function uploadResult() {
	var obj = {};
	var valid = true;
	var uploadList = [];
	if (valid) {
		$("#doctbodyData > tr").each(
				function(i) {
					var uFile = $(this).find(".document")[0].files[0];
					var fileName = $(this).find(".document").val();
					var data = [];
					var x = [];
					if (fileName != '' && fileName != 'undefined'
							&& fileName != null) {
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
					}
					uploadData = {};
					uploadData['documnentName'] = $("#docnoid_" + i).val();
					uploadData['documentFile'] = x;
					uploadData['fileName'] = fileName;
					uploadData['imageNameEdit'] = $(this).find(
							".uploadHidCls").val();
					uploadList.push(uploadData);
					
				});
			obj.assignId = $('#asgnId').val()
			obj.ticketId = $("#tcktId1").html();
			obj.assignType = $("#assignTId").val();
			obj.status = $("#resultStatus").val();
			obj.assignTypeId=$('#assignTypeId').val();
			//obj.assignTypeId=assignmentTypeId;
			obj.documentList = uploadList;
			obj.resultDescription = $("#description").val();
			
			obj.actualDate = $("#corrDate").val();
			obj.actualTime = $("#corrTime").val();
			obj.actualCost=$('#corrCost').val();
			
			if (obj.status == null || obj.status == "") {
				toastr.error('Status Required');
				return;				
			 }

			if (obj.resultDescription == null || obj.resultDescription == "") {
				toastr.error('Description Required');
				return;					
			 }
			var dataOfEquip = [];
			activityEquipOptions.api.forEachNode(function(rowNode, index) {
				dataOfEquip.push(rowNode.data);
			  });
			
			obj.equipementList = dataOfEquip;
			$("body").removeClass("overlay");
			if(valid){
				saveTicket(obj);
			}
	}

}

function saveTicket(data) {
$('.loader').show();
setTimeout(function() {
	$.ajax({
		type : "POST",
		url : "view-job-manage-save-result",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(response) {
			console.log("RESPONSE::::",response);
			if (response.code == "success") {
				cancelMain();
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#resultModal").modal('hide');
				toastr.success('Result Upload Sucessfully');
				getAllJobView();

			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				$("#resultModal").modal('hide');
				toastr.error('Something Went Wrong');				
			}
		},
		error : function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			toastr.error('Something Went Wrong');	
		}
	})
}, 1000)
}

//function for delete qc  
function acceptOnclick(op) {
		var assignId = $('#asgnId').val()
		if(assignId == null || assignId == '') {
		toastr.error('There is no assignId selected to '+ op);
		return;
		}
	Swal.fire({
	    title: 'Are you sure?',
	    text: 'Do you want to ' + op + ' this?',
	    icon: 'warning',
	    showCancelButton: true,
	    confirmButtonText: 'Yes, proceed!',
        cancelButtonText: 'No, keep it',
        confirmButtonColor: 'var(--mainColor)',
    }).then((result) => {
        if (result?.value) {
        	$.ajax({
					type : "POST",
					url : "view-job-manage-save-accept?id="+ assignId+"&operation="+op,
					success : function(response) {
					 if (response.code == "success") {
						 $('.loader').hide();
						 toastr.success("Job " + op + " Successfully");
							$('#delete').modal('hide');
							getAllJobView();
							} else {
							$('.loader').hide();
							toastr.error('Something went to wrong!');
						}
					},
					error : function(data) {
						$('.loader').hide();
						}

    		});
        } 
    });

}

//master save data
function submitProgress() {
	activityOptions1.api.stopEditing();
	var selectedRows = gridOptions.api.getSelectedRows();
	var ticketIdEx = selectedRows[0].tcktNo;

	$(".formValidation").remove();
	var createdBy=$("#assetemployee").val();
	
	var valid= true;
	if (createdBy == null || createdBy == "") {
		valid = validationUpdated("Uploaded By is Required", "assetemployee");
	 }
	var datas = [];
	var rowCount = activityOptions1.api.getDisplayedRowCount();
	if(rowCount > 0){
	activityOptions1.api.forEachNode(function(rowNode, index) {
		var item = rowNode.data;
		item.ticketid =ticketIdEx;
		item.allocid =selectedRows[0].id;
		item.createdBy =createdBy;
		if(item.reviewSts==null){
			item.reviewSts ='';
		}
		if(item.result==null || typeof item.result === 'undefined'){
			item.result ='';
		}
		if(item.remark==null || typeof item.remark === 'undefined'){
			item.remark ='';
		}
		
			var dataOfEquip = [];
			
			obj.equipementList = dataOfEquip;
        item.status =1;
        if(valid){
        	if(item.taskStatus!="COMPLETED"){
        		datas.push(item);
    		}
         }
	});
	if (valid) {
		console.log("SAVE DATA::::",datas);
		savePlanDetailsForProgress(datas);
		
		}
	} else {
		$("#messageParagraph").text(
		"Please Add Atleast One Plan Details");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}
}
function savePlanDetailsForProgress(datas) {
	$('.loader').show();
	$.ajax({
		type : "POST",
		url : "view-job-manage-maintenance-progress",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(datas),
		success : function(response) {
			$('.loader').hide();
			var itemName = $("#itemname option:selected").text();
			if (response.code == "success") {
				cancelModalBtn();
				cancelMain1();
				toastr.success(response.message);
				getAllJobView();
				
			} else {
				$('.loader').hide();
			toastr.error('Something Went Wrong');
				
				$("#itemid").val('');
				$("#itemname").val('');
				$("#description").val('');
			}
		},
		error : function(data) {
			$('.loader').hide();
		}
	})

}
function cancelModalBtn() {
	$('#myModalForAsset').modal('hide');

}
function cancelDeleteModalBtn() {
	$('#deleteDetails').modal('hide');
	$('#deleteDetailsMaintain').modal('hide');

}
 
function openNav() {
	$("#edate").val('');
	$("#sdate").val('');
	$("#equipmentRemark").val('');
	$("#equipmentName").val('');
	$("#equipmentQty").val('');
	$("#equipmentScat").val('');
	$("#equipmentCat").val('');
	$("#rowEdit").val(null);
	$("#newchild").hide();
	$("#saveBtn").hide();
	$("#cancel").hide();
	$("#equipmentGrid").hide();
	$("#deleteChild").hide();
	$("#AddEquip1").hide();
	$("#equipmentForm").show();
}

function closeNav() {
	$("#equipmentGrid").show();
	$("#edate").val('');
	$("#sdate").val('');
	$("#equipmentRemark").val('');
	$("#equipmentName").val('');
	$("#equipmentQty").val('');
	$("#equipmentScat").val('');
	$("#equipmentCat").val('');
	$("#rowEdit").val(null);
	$("#newchild").show();
	$("#deleteChild").show();
	$("#equipmentForm").hide();
	$("#saveBtn").show();
	$("#cancel").show();
	$("#AddEquip1").show();
}
function openNav2() {
	$("#edate1").val('');
	$("#sdat1e").val('');
	$("#equipmentRemark1").val('');
	$("#equipmentName1").val('');
	$("#equipmentQty1").val('');
	$("#rowEdit1").val(null);
	$("#newchild1").hide();
	document.getElementById("mySidenav2").style.cssText = "width: 35%; position: relative; right:-10px; height:230px; top:0px;";
	document.getElementById("main2").style.width = "100%";
}

function closeNav2() {
	document.getElementById("mySidenav2").style.width = "0%";
	document.getElementById("main2").style.width = "100%";
	$("#edate1").val('');
	$("#sdate1").val('');
	$("#equipmentRemark1").val('');
	$("#equipmentName1").val('');
	$("#equipmentQty1").val('');
	$("#rowEdit1").val(null);
	$("#newchild1").show();
	$("#deleteChild1").show();
}
function saveTableData() {
	  var datas = [];
	  var item = {};
	  var valid = true;
	  var rowEdit = $("#rowEdit").val();
	  var found = false;

	  activityEquipOptions.api.forEachNode(function(rowNode, index) {
	    var iid = rowNode.data.parameterId;
	    datas.push(rowNode.data);
	  });
	  if (!found) {
		  item.equipementCat = $("#equipmentCat").val();
		    item.equipementScat = $("#equipmentScat").val();
		  	item.equipementCat1 =  $("#equipmentScat option:selected").text();
		    item.equipementScat1 = $("#equipmentQty option:selected").text();
		    item.equipementQty = $("#equipmentQty").val();
		    item.description = $("#equipmentRemark").val();

		    if (item.equipementCat == null || item.equipementCat == "") {
					toastr.error('Category Required');
					return;			
			 }
		    if (item.equipementScat == null || item.equipementScat == "") {
					toastr.error('Subcategory Required');
					return;					
			 }
			if (item.equipementQty == null || item.equipementQty == "") {
					toastr.error('Quantity Required');
					return;						
			 }
			if (item.description == null || item.description == "") {
					toastr.error('Remark Required');
					return;						
			 }
		
	    if (valid) {
	      if (rowEdit) {
	    	  activityEquipOptions.api.forEachNode(function(rowNode, index) {
	          var iid = rowNode.data.parameterId;
	          if (iid == rowEdit) {
	            rowNode.setData(item);
	            return;
	          }
	        });
	        closeNav();
	      } else {
	        var rowCount = activityEquipOptions.api.getDisplayedRowCount();
	        if (rowCount > 0) {
	          var foundId = false;
	          if (!foundId) {
	            datas.push(item);
	          }
	        } else {
	          datas = [item];
	        }
	        activityEquipOptions.api.setRowData(datas);
	        closeNav();
	      }
	    } else {
	      $("#mySidenav").show();
	    }
	  }
	}

function saveTableData2() {
	  var datas = [];
	  var item = {};
	  var valid = true;
	  var rowEdit = $("#rowEdit").val();
	  var found = false;

	  activityEquipOptions1.api.forEachNode(function(rowNode, index) {
	    var iid = rowNode.data.parameterId;
	    datas.push(rowNode.data);
	  });
	  if (!found) {
	    item.equipementCat = $("#equipmentCat1").val();
	    item.equipementScat = $("#equipmentScat1").val();
	    item.equipementQty = $("#equipmentQty1").val();
	    item.description = $("#equipmentRemark1").val();

	    if (item.equipementCat == null || item.equipementCat == "") {
					toastr.error('Category Required');
					return;			
		 }
	    if (item.equipementScat == null || item.equipementScat == "") {
					toastr.error('Subcategory Required');
					return;			
		 }
		if (item.equipementQty == null || item.equipementQty == "") {
					toastr.error('Quantity Required');
					return;				
		 }
		if (item.description == null || item.description == "") {
					toastr.error('Remark Required');
					return;				
		 }
		
	    if (valid) {
	    	
	      if (rowEdit) {
	    	  activityEquipOptions1.api.forEachNode(function(rowNode, index) {
	          var iid = rowNode.data.parameterId;
	          if (iid == rowEdit) {
	            rowNode.setData(item);
	            return;
	          }
	        });
	        closeNav2();
	      } else {
	        var rowCount = activityEquipOptions1.api.getDisplayedRowCount();
	        if (rowCount > 0) {
	          var foundId = false;
	          if (!foundId) {
	            datas.push(item);
	          }
	        } else {
	          datas = [item];
	        }
	        activityEquipOptions1.api.setRowData(datas);
	        closeNav2();
	      }
	    } else {
	      $("#mySidenav").show();
	    }
	  }
	}
	
function deleteDetails() {
	$('#deleteDetails').modal('show');
	closeNav();
}
function deleteDetailsMaintain() {
	$('#deleteDetailsMaintain').modal('show');
	closeNav();
}
function deleteDetailsOnclick() {
	var selectedRows = activityEquipOptions.api.getSelectedRows();
	activityEquipOptions.api.applyTransaction({
		remove : selectedRows
	});
	$('#deleteDetails').modal('hide');
}
function deleteDetailsOnclickMaintain() {
	var selectedRows = activityEquipOptions1.api.getSelectedRows();
	activityEquipOptions1.api.applyTransaction({
		remove : selectedRows
	});
	$('#deleteDetailsMaintain').modal('hide');
}
function cancelModalProductBtn() {
	$("#deleteModalBtn").removeAttr("disabled");
	$('#deleteDetails').modal('hide');
}

function getsubcategory(){
	var catid=$("#equipmentCat1").val();
	var options='<option value="">Select</option>';
	if(catid!="" && catid!=null){
	agGrid.simpleHttpRequest(
			{
				url : 'view-job-manage-maintenance-subcategory?id='+ catid
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData=jsonData.Asset;
				if(allData!=null){
				 allData.forEach(function(rowNode){
					options += '<option value="'+rowNode.subcatid+'" data-subQty="' + rowNode.assetqty + '">'+rowNode.subcatname+'</option>';
					  });
				}
				 $("#equipmentScat1").html(options);
			});
	
	}else{
		$("#equipmentScat1").html(options);
	}

}
function getQtyBySub(){
	var selectedOption = $("#equipmentScat1 option:selected");
	var customData = selectedOption.attr("data-subQty");
	$("#stockQty").val(customData);
	$("#equipmentQty1").val("0");
}
function getsubcategory1(){
	var catid=$("#equipmentCat").val();
	var options='<option value="">Select</option>';
	if(catid!="" && catid!=null){
	agGrid.simpleHttpRequest(
			{
				url : 'view-job-manage-maintenance-subcategory?id='+ catid
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData=jsonData.Asset;
				if(allData!=null){
				 allData.forEach(function(rowNode){
					options += '<option value="'+rowNode.subcatid+'" data-subQty="' + rowNode.assetqty + '">'+rowNode.subcatname+'</option>';
					  });
				}
				 $("#equipmentScat").html(options);
			});
	
	}else{
		$("#equipmentScat").html(options);
	}

}
function getQtyBySub1(){
	var selectedOption = $("#equipmentScat option:selected");
	var customData = selectedOption.attr("data-subQty");
	$("#stockQty1").val(customData);
	$("#equipmentQty").val("0");
}
function travelNext(tab){
		if(tab == 1){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").addClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").addClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 2){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").addClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").addClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 3){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").addClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").addClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 4){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").addClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").addClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 5){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").addClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").addClass('active');
		}else{
			$("#ticketInfo").addClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").addClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}
	}
	function travelPrev(tab){
		if(tab == 1){
			$("#ticketInfo").addClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").addClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 2){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").addClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").addClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 3){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").addClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").addClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 4){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").addClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").addClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").removeClass('active');
		}else if(tab == 5){
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").addClass('active');
			$("#ticketChat").removeClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").addClass('active');
			$("#Chatwithus").removeClass('active');
		}else{
			$("#ticketInfo").removeClass('active');
			$("#ticketAssign").removeClass('active');
			$("#ticketAction").removeClass('active');
			$("#ticketHistory").removeClass('active');
			$("#ticketSts").removeClass('active');
			$("#ticketChat").addClass('active');
			
			$("#TicketInformation").removeClass('active');
			$("#Assign").removeClass('active');
			$("#Action").removeClass('active');
			$("#history").removeClass('active');
			$("#TicketStatus").removeClass('active');
			$("#Chatwithus").addClass('active');
		}
	}