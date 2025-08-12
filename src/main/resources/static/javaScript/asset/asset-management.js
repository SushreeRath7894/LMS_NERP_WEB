$(()=>{
	$('#assettype').select2();  //#assettype
	$('#assetcat').select2();  //#assetcat
	$('#assetsubcat').select2();  //#assetsubcat
	$('#owntype').select2();  //#owntype
	$('#wstatus').select2();  //#wstatus
	$('#assetemployee').select2();  //#assetemployee
	$('#unit').select2();  //#unit
	$('#assetlocation').select2();  //#assetlocation
	$('#workstatus').select2(); //initialize '#workstatus' with select2 dropdowns.
});

$('#assetPrice').on('keypress keydown', function (e) {
    // Block minus key (keyCode 45 or key === '-')
    if (e.key === '-' || e.keyCode === 45) {
        e?.preventDefault();
    }
});

$('#assetPrice').on('input', function () {
    let value = parseInt($(this).val(), 10);
    if (isNaN(value) || value < 1) {
        $(this).val('');
    }
});

//		on refresh call

$(document).ready(function() {
	let roleIds = $("#userRolesId").val();
	console.log('roleIds', roleIds)
	let roleIdLists = roleIds?.replaceAll("[", "").replaceAll("]", "").replaceAll(" ", "").split(",");

	if (roleIdLists.includes('rol001')) {
		$(".role-wise-btn").show();
	} else {
		$(".role-wise-btn").hide();
	}

	var gridDiv1 = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv1, gridOptions);
	var gridDiv2 = document.querySelector('#myWarrentyGrid');
	new agGrid.Grid(gridDiv2, activityOptions);
	var gridDiv3 = document.querySelector('#myInsuranceGrid');
	new agGrid.Grid(gridDiv3, InsuranceOptions);
	var gridDiv4 = document.querySelector('#myComplainceGrid');
	new agGrid.Grid(gridDiv4, complianceOptions);
	var gridDiv5 = document.querySelector('#myGridAllocatedPolicy');
	new agGrid.Grid(gridDiv5, allocatedPolicyOptions);
	var gridDiv6 = document.querySelector('#myGridPolicy');
	new agGrid.Grid(gridDiv6, policyOptions);

	document.getElementById("quickFilter").addEventListener("keydown", function(event) {
		if (event.key === "Enter") {
			onQuickFilterChanged()
		}
	});
	
	$("#assetemployee").val("").trigger('change');
	$("#assetlocation").val("").trigger('change');
	$("#assigndate").hide();
	getDates();
//	viewAsset();
	cancelAsset();
	cancelWarrenty();
	cancelInsure();
	cancelDocs();
	CancelPolicyAllcate();
	assetAllocationChange1();
	assetAllocationChange();
});

function nextTab(id) {
	console.error(id);
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	console.log(tabElement)
	console.log(tab)
	tab.show();
}


var allocId = "";
function assetAllocationChange( selectedValue = null) {	
//	$("#prev4, #next5").prop('disabled', true);
	if (selectedValue == "Employee") {
		$("#assetAllocate").prop('disabled', false);
		$("#emplist").show();
		$("#loclist").hide();
		$("#assetlist").hide();
		$("#assetlocation").val("");
		$("#assetAsset").val("");
		$("#assigndate").show();
		$("#reasonDiv").hide();
		allocId = "Employee";
	} else if (selectedValue == "Room") {
		$("#assetAllocate").prop('disabled', false);
		$("#emplist").hide();
		$("#assetlist").hide();
		$("#loclist").show();
		$("#assetemployee").val("").trigger('change');
		$("#assetAsset").val("");
		$("#assigndate").show();
		$("#reasonDiv").hide();
		allocId = "Room";
	} /*else if (selectedValue == "Asset") {
		$("#assetAllocate").prop('disabled', false);
		$("#emplist").hide();
		$("#assetlist").show();
		$("#loclist").hide();
		$("#assetemployee").val("").trigger('change');
		$("#assetlocation").val("").trigger('change');
		$("#assigndate").show();
		$("#reasonDiv").hide();
		allocId = "Asset";
	}*/ else {	
		$("#assetAllocate").prop('disabled', true);
		$("#emplist").hide();
		$("#assetlist").hide();
		$("#loclist").hide();
		$("#assetemployee").val("").trigger('change');
		$("#assetlocation").val("").trigger('change');
		$("#assigndate").hide();
		$("#reasonDiv").hide();
		const radioButtons = document.querySelectorAll('input[name="options"]');
			radioButtons.forEach((radio) => {
				radio.checked = false;				
			});
		assetAllocate();
	}
	//e.preventDefault();
}
var poliAlloc = "";
function assetAllocationChange1(e, selectedValue = null) {
	if (selectedValue == "in-house") {
		$("#emplist1").show();
		$("#loclist1").hide();
		$("#assetlocation1").val("");
		$("#assigndate1").show();
		$("#allocatePolicyVendor").removeClass('active');
		$("#allocatePolicyHouse").addClass('active');
		poliAlloc = "in-house";
	} else if (selectedValue == "vendor") {
		$("#emplist1").hide();
		$("#loclist1").show();
		$("#assetemployee1").val("");
		$("#assigndate1").show();
		$("#allocatePolicyHouse").removeClass('active');
		$("#allocatePolicyVendor").addClass('active');
		poliAlloc = "vendor";
	} else {
		$("#emplist1").hide();
		$("#loclist1").hide();
		$("#assetlocation1").val("");
		$("#assigndate1").hide();
		$("#allocatePolicyVendor").removeClass('active');
		$("#allocatePolicyHouse").addClass('active');
		poliAlloc = "in-house";
	}
	e?.preventDefault();
}
// dates
function getDates() {
	var dateFormat = localStorage.getItem("dateFormat");
	var today = new Date();
	$("#DateCalendarAssign").datetimepicker({
	    format: dateFormat,
	    closeOnDateSelect: true,
	    timepicker: false,
	 //   maxDate: today,
	}).on("change", function () {
	    $('#asdate').val($(this).val());
	});
	
	$('#asdate').blur(function () {
	    $("#DateCalendarAssign").val($(this).val());
	});

	$("#DateCalendarAssign1").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
//	    maxDate: today,
		/*minDate: new Date().toLocaleDateString(undefined, { year: 'numeric', month: '2-digit', day: '2-digit' }),*/
	}).on("change", function() {
		$('#asdate1').val($(this).val());
	})
	$('#asdate1').blur(function() {
		$("#DateCalendarAssign1").val($(this).val());
	})

	$("#DateCalendar1").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
//	    maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#sdate').val($(this).val());
	})
	$('#sdate').blur(function() {
		$("#DateCalendar1").val($(this).val());
	})

	$("#DateCalendar2").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
//		minDate: today,
		timepicker: false,
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
//		maxDate: new Date().toLocaleDateString(undefined, { year: 'numeric', month: '2-digit', day: '2-digit' }),
	}).on("change", function() {
		$('#pdate').val($(this).val());
	})
	$('#pdate').blur(function() {
		$("#DateCalendar3").val($(this).val());
	})

	$("#DateCalendar4").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
//		maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#isdate').val($(this).val());
	})
	$('#isdate').blur(function() {
		$("#DateCalendar4").val($(this).val());
	})

	$("#DateCalendar5").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
//		maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#iedate').val($(this).val());
	})
	$('#iedate').blur(function() {
		$("#DateCalendar5").val($(this).val());
	})

	$("#fromDateCalendarComp").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
//		maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#csdate').val($(this).val());
	})
	$('#csdate').blur(function() {
		$("#fromDateCalendarComp").val($(this).val());
	})

	$("#fromDateCalendarComp1").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
//		maxDate: today,
		timepicker: false,
	}).on("change", function() {
		$('#crdate').val($(this).val());
	})
	$('#crdate').blur(function() {
		$("#fromDateCalendarComp1").val($(this).val());
	})

}
// file save functions
// warenty filr
function saveFile() {
	var uFile = $(uploadWarDoc_0)[0].files[0];
	var fileName = $('#uploadWarDoc_0').val();
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedWarrDiv_0").html("");
	$("#cancelWarDoc").html("");

			var cancel = "<span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openWarDelete()'></i></span>";
	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-pdf custom-file-icon'></i> </a>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
			var cancel = "";
	}
	$("#cancelWarDoc").html(cancel);
	$("#uploadedWarrDiv_0").html(LightImg);
	$("#imageWarrName_0").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

}
function openWarDelete() {
	var war = `<div class="form-group">
  <label>Attach</label>
  <div class="d-flex gap-2 align-items-center">
    <div class="control-group">
      <label class="custom-file-upload m-0" for="uploadWarDoc_0"> <i class="ti-plus"></i>
      </label>
      <div class="controls">
        <input type="file" class="wardocument" id="uploadWarDoc_0" accept=".jpeg, .jpg, .png, .pdf" onchange="saveFile(event)">
      </div>
    </div> <input type="hidden" id="warrenDocUrl"> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">
         <div id="uploadedWarrDiv_0" align="center" class="uploadedBillCls"></div>
         <div id="imageWarrName_0" class="imageName"></div> <div id="cancelWarDoc" ></div>
  </div>
</div>`;
	$("#attachWar").html(war);
}
// insurance documents
function saveFile1() {
	var uFile = $(uploadWarDoc1_0)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedWarrDiv1_0").html("");
	$("#cancelInsDoc").html("");

	var cancel = "<span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openInsDelete()'></i></span>";
	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-pdf custom-file-icon'></i> </a>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
			var cancel = "";
	}
	$("#cancelInsDoc").html(cancel);
	$("#uploadedWarrDiv1_0").html(LightImg);
	$("#imageWarrName1_0").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

}
function openInsDelete() {
	var insu = `<div class="form-group">
  <label>Attach</label>
  <div class="d-flex align-items-center gap-2">
    <div class="control-group">
      <label class="custom-file-upload" for="uploadWarDoc1_0"> <i class="ti-plus"></i>
      </label>
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

// comp document
function saveFileComp() {

	var uFile = $(uploadCompDoc_0)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedCompDiv_0").html("");
	$("#cancelDoc").html("");

			var cancel = "<span><i class='ti-close red close_sec1 deleteFileDoc' onclick='openCompDelete()'></i></span>";
	if (extension[1] == "jpg" || extension[1] == "png" || extension[1] == "jpeg") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-image custom-file-icon'></i></a>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-pdf custom-file-icon'></i> </a>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-excel custom-file-icon'></i></a>";
	} else if (extension[1] == "doc" || extension[1] == "dox"
		|| extension[1] == "docx") {
		var LightImg = "<a style='margin-left: 10px' class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa-solid fa-file-word custom-file-icon'></i></a>";
	} else {
		var LightImg = "";
			var cancel = "";
	}
	$("#cancelDoc").html(cancel);
	$("#uploadedDivComp_0").html(LightImg);
	$("#imageCompName_0").html(fileName);
	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

}
function openCompDelete() {
	var comp = `<div class="form-group">
  <label>Attach</label>
  <div class="d-flex align-items-center gap-2">
    <div class="control-group">
      <label class="custom-file-upload" for="uploadCompDoc_0"> <i class="ti-plus"></i></label>
      <div class="controls">
        <input type="file" class="compdocument" id="uploadCompDoc_0"
               accept=".jpeg, .jpg, .png, .pdf"
               onchange="saveFileComp(event)">
      </div>
    </div>
                                    <input type="hidden" id="docDocUrl">
    <input type="hidden" id="uploadHiddenComp_0" class="uploadHidCls">
    <div id="uploadedDivComp_0" align="center" class="uploadedCompCls"></div>
    <div id="imageCompName_0" class="imageName"></div><div id="cancelDoc" ></div>
  </div>
</div>
`;
	$("#attachComp").html(comp);
}
// DROP DOWN FUNCTIONSS
function getsubcategory() {
	var catid = $("#assetcat").val();
	var options = '<option value="">Select</option>';

	if (catid != "" && catid != null) {
//		$("#assetsubcat").prop('disabled', false);
		console.log("CAT ID:::", catid);
		agGrid.simpleHttpRequest(
			{
				url: 'asset-management-subcategory?id=' + catid
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);

				var allData = jsonData.Asset;
				if (allData != null) {
					allData.forEach(function(rowNode) {
						options += '<option value="' + rowNode.subcatid + '">' + rowNode.subcatname + '</option>';
					});
				}
				$("#assetsubcat").html(options);
				
				if(assetGSubCat) {
					$("#assetsubcat").val(assetGSubCat);
				}
				
			});
	} else {
		$("#assetsubcat").html(options);
	}
}
function getPolicyList(id = null) {
	var selectedRows = gridOptions.api.getSelectedRows();
	// 	var id = selectedRows[0].assetId;
	if (selectedRows.length > 0) {
	    var cat = selectedRows[0].assetcatId;
		var subcat = selectedRows[0].assetsubcatId;
		if (subcat != '') {
			agGrid.simpleHttpRequest({
				url: 'asset-management-policy-list?cat=' + cat + '&subcat=' + subcat,
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.Policy;
			//	console.log("allData ===", allData);
				if (id) {
					var allAllocatedData = [];
					allocatedPolicyOptions.api.forEachNode(function(node) {
					    allAllocatedData.push(node.data);
					});
					var filteredData;
					var allocatedPolicyIds = allAllocatedData.map(item => item.policyid);
					console.log("allocatedPolicyIds",allocatedPolicyIds);
					if(allocatedPolicyIds.length >0){
						filteredData = allData.filter(item => !allocatedPolicyIds.includes(item.policyId));
						console.log("filteredData ===", filteredData);
				    	policyOptions.api.setRowData(filteredData);
					}else{
				    	policyOptions.api.setRowData(allData);
					}							
				} else {
				    policyOptions.api.setRowData(allData);
				}
			});
		} else {
		}
	}
}
// FUNCTIONALITIES
// view all asset
var asset='';
function viewAsset(id=null, tab=null) {
	agGrid.simpleHttpRequest({
		url: 'asset-management-view?type=' + "Asset"
	}).then(function(data) {
		var jsonData = JSON.parse(data?.body);
		var allData = jsonData?.Asset;
		console.log("allData===",allData)
		if (allData) {
			var len = allData.length;			
			gridOptions.api.setRowData(allData);
			if (id != null) {
				asset=id;
			    gridOptions.api.forEachNode(function(node) {
				 if (String(node.data.assetId) === String(id)) {					
			           node.setSelected(true);
			        	
					}
			    });
			} else {	
				console.log("else")
					asset='';
				var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}
			}
			if(tab){
				nextTab(tab);
			}else{
				nextTab('assetDetsId');
			}
		} else {
			gridOptions.api.setRowData([]);
			activityOptions.api.setRowData([]);
			InsuranceOptions.api.setRowData([]);
			complianceOptions.api.setRowData([]);
			allocatedPolicyOptions.api.setRowData([]);
			policyOptions.api.setRowData([]);
	//		editAsset();
			nextTab('assetDetsId');
		}

	});
}
// edit asset
var allData3 = "";
var allData1 = "";
var allData4 = "";
let assetGSubCat = '';
function editAssetDetails(assetId) {
	$("#saveAttachmentBtn").hide();
	$("#editAttachmentBtn").show();
	$("#assetimageview").show();
	$("#docTbl").hide();
	var options = "";
	var imagesdiv = "";
	var scat = "";
	var tbl = "";
	$('.loader').show(); 
	agGrid.simpleHttpRequest({
			url: 'asset-management-edit?id=' + assetId
		}).then(function(data) {
			$('.loader').hide();
			getPolicyList(assetId);
			var jsonData = JSON.parse(data.body);
			var allData = jsonData.Asset;
			console.log("alldata----->>>>>>>>>>>>>>",allData);
			if (allData[0].Warranty != null) {
				allData3 = allData[0].Warranty;
				activityOptions.api.setRowData(allData3);
			} else {
				activityOptions.api.setRowData('');
			}
			if (allData[0].AssetInsurance != null) {
				allData1 = allData[0].AssetInsurance;
				InsuranceOptions.api.setRowData(allData1);
			} else {
				InsuranceOptions.api.setRowData('');
			}
			if (allData[0].Compliance != null) {
				allData4 = allData[0].Compliance;
				complianceOptions.api.setRowData(allData4);
			} else {
				complianceOptions.api.setRowData('');
			}
			if (allData[0].policyAssigned != null) {
				var policy = allData[0].policyAssigned;
				allocatedPolicyOptions.api.setRowData(policy);
			} else {
				allocatedPolicyOptions.api.setRowData('');
			}
			if (allData[0].assetAssigned != null) {
				
				if(allData[0].workingstatus != "Working"){	
				console.error("if approvests")
					$('#assetAllocate, #assetDissociate').hide();
					document.getElementById("empAllocate1").disabled = true;
					document.getElementById("propAllocate1").disabled = true;
				}else{
				console.error("else approvests")
					$('#assetAllocate, #assetDissociate').show();
					document.getElementById("empAllocate1").disabled = false;
					document.getElementById("propAllocate1").disabled = false;
				}
				var assigned = allData[0].assetAssigned;
				const radioButtons = document.querySelectorAll('input[name="options"]');
				if (allData[0].assignname == 'Assigned') {
				$('#assetDissociate').show();
					var j = assigned.length - 1;
					var category = assigned[0].category;
					radioButtons.forEach((radio) => {
						if (radio.value === category) {
							radio.checked = true;							
						} else {
						}
						radio.disabled = true;					
					});
					if (category == "Employee") {
						$("#assetemployee").val(assigned[j].assignTo).trigger('change');
					} else {
						$("#assetlocation").val(assigned[j].assignTo).trigger('change');
					}
					$("#asdate").val(assigned[j].assignDate);
					$("#assignedId").val(assigned[j].assignId);
					//	$("#assetemployee").val(assigned[0].assignTo);
					assetAllocationChange(category);
					assetDeAllocate();
				} else {
					assetAllocationChange('');
				$('#assetAllocate').show();
				}
			}else{
				console.error("else ")
				if(allData[0].workingstatus != "Working"){					
				console.error("else if")
					$('#assetAllocate').hide();
					document.getElementById("empAllocate1").disabled = true;
					document.getElementById("propAllocate1").disabled = true;
					$("#assetAllocate").prop('disabled', true);
					$("#emplist").hide();
					$("#assetlist").hide();
					$("#loclist").hide();
					$("#assetemployee").val("").trigger('change');
					$("#assetlocation").val("").trigger('change');
					$("#assigndate").hide();
					$("#reasonDiv").hide();
					const radioButtons = document.querySelectorAll('input[name="options"]');
					radioButtons.forEach((radio) => {
						radio.checked = false;
						radio.disabled = true;		
					});
				}else{
				console.error("else else")
					$('#assetAllocate').show();
					document.getElementById("empAllocate1").disabled = false;
					document.getElementById("propAllocate1").disabled = false;
					$("#assetAllocate").prop('disabled', true);
					$("#emplist").hide();
					$("#assetlist").hide();
					$("#loclist").hide();
					$("#assetemployee").val("").trigger('change');
					$("#assetlocation").val("").trigger('change');
					$("#assigndate").hide();
					$("#reasonDiv").hide();
					const radioButtons = document.querySelectorAll('input[name="options"]');
					radioButtons.forEach((radio) => {
						radio.checked = false;
						radio.disabled = false;		
					});
				}
			}
			if (allData[0].AssetImages != null) {
				var allData2 = allData[0].AssetImages;
				if (allData2 != null && allData2.length != 0) {
					$("#doctbodyData").empty();
					allData2.forEach(function(rowNode, index) {
						var filename = extractFilename(rowNode.docurl);
						var iurl = "/document/assetDocUrl/" + filename;
						if (getFileExtension(filename) == "jpg" || getFileExtension(filename) == "png" || getFileExtension(filename) == "jpeg") {
							imagesdiv += `<div class="col-lg-2 col-md-8 mb-4 mb-lg-0">
								  <div class="bg-image hover-overlay ripple shadow-1-strong rounded" data-ripple-color="light">
								  <img src=${iurl} class="w-100"  /> </div></div>`;
						}
						tbl = '<tr>'
							+ '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
							+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="' + rowNode.docname + '" class="form-control docNoclss" id="docnoid_' + index + '"> </div></td>'
							+ '<td><div class="form-group"> <input type="text" value="' + rowNode.docnotes + '" class="form-control docnotesCls" id="docnotes_' + index + '"> </div></td>'
							+ '<td><div class="control-group"> <label class="custom-file-upload" for="uploadDoc_' + index + '" id="uploadFor_' + index + '"> <i class="ti-pencil" id="clickImg_' + index + '"></i> </label>'
							+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_'
							+ index
							+ '"  onchange="saveMultiFile(event)" value="' + rowNode.filename + '" /> </div>'
							+ '</div> <input type="hidden" id="uploadHidden_' + index + '" value="' + rowNode.docurl + '" class="uploadHidCls">'
							+ '<div id="uploadedBillDiv_' + index + '" align="center" class="uploadedBillCls"><div class="uploadicon"><i class="bi bi-file-earmark" onclick="viewDocuemntFile(&quot;' + window.btoa(rowNode.docurl) + '&quot;)"></i></div></div>'
							+ '<div id="imageName_' + index + '" class="imageName">'
							+ rowNode.filename
							+ '</div>'
							+ '<input type="hidden" id="editId_' + index + '" value="' + rowNode.assetid + '">'
							+ '<div id="dltImage_' + index + '" class="custom-file-delete"><i class="ti-close rmv1" onclick="openDeleteConfirm()"></i></div> </td>'
							+ '</tr>';
						$("#doctbodyData").append(tbl);
					});
				} else {
					var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
						+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
						+ '<td><div class="form-group"> <input type="text" value="" class="form-control docnotesCls" id="docnotes_0"></div></td>'
						+ '<td> <div class="control-group"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
						+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0"   onchange="saveMultiFile(event)" /></div> </div><input type="hidden" id="warrenDocUrl"> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
						+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
						+ '</tr>';
					$("#doctbodyData").html(tbl);
					$("#viewAttachmentBtn").hide();
				}
				$("#assetimageenter").html(imagesdiv);
			}
			console.log("edit allData[0]-->",allData[0]);

			$("#assetName1").html(allData[0].assetname);
			$("#assetName2").html(allData[0].assetname);
			$("#assetName3").html(allData[0].assetname);
			$("#assetName4").html(allData[0].assetname);
			$("#assetName5").html(allData[0].assetname);
			$("#assetName6").html(allData[0].assetname);
			$("#assetName7").html(allData[0].assetname);
			
			$("#assetbox").val(allData[0].assetId);
			assetGSubCat = allData[0].assetsubcat;
			
			$("#assettype").val(allData[0].assettype).trigger('change');
			$("#assetcat").val(allData[0].assetcat).trigger('change');
			
	//		getFeildsView(apstatus,wstatus,assignname);
			
			$("#owntype").val(allData[0].owntype).trigger('change');
			$("#wstatus").val(allData[0].workingstatus).trigger('change');
			$("#workstatus").val(allData[0].workingstatus).trigger('change');
			
			$("#assetId").val(assetId);
			$("#assetbox").text(assetId);
			$("#assetname").val(allData[0].assetname);
			$("#assetmodel").val(allData[0].assetmodel);
			$("#assetcode").val(allData[0].assetcode);
			$("#lifespan").val(allData[0].lifespan);
			$("#purchaseno").val(allData[0].purchaseno);
			$("#pdate").val(allData[0].pdate);
			$("#assetPrice").val(allData[0].assetPrice);
			$("#assetDescription").val(allData[0].assetDescription);
			$("#reqCapacity").val(allData[0].capacity);
			$("#unit").val(allData[0].unit);
			$("#assetDescsts").val(allData[0].assetDescsts);
			scat = allData[0].assetsubcat;
			$("#tdiv").hide();
			$("#searchRowDiv").hide();
			$("#totalAsset").hide();
			$("#ttbtn").hide();
		//	getPolicyList();
		});
	$('.loader').hide();
}
// save asset data
function saveAsset() {
	//$(".formValidation").remove();
	var valid = true;
	var datas = [];
	var item = {};
	item.assetId = $("#assetId").val();
	item.assettype = $("#assettype").val();
	item.assetcat = $("#assetcat").val();
	item.assetsubcat = $("#assetsubcat").val();
	item.assetname = $("#assetname").val();
	item.assetcode = $("#assetcode").val();
	item.assetmodel = $("#assetmodel").val();
	item.pdate = $("#pdate").val();
	item.purchaseno = $("#purchaseno").val();
	item.lifespan = $("#lifespan").val();
	item.assetPrice = $("#assetPrice").val();
	item.assetDescription = $("#assetDescription").val();
	item.type = "Asset";
	item.wstatus = $("#wstatus").val();
	item.owntype = $("#owntype").val();
	item.capacity = $("#reqCapacity").val();
	item.unit = $("#unit").val();

	if (item.assettype == null || item.assettype == "") {
		toastr.error('Asset Type required');
		return;
	}

	if (item.assetcat == null || item.assetcat == "") {
		toastr.error("Asset Category is Required");
		return
	}
	if (item.assetsubcat == null || item.assetsubcat == "") {
		toastr.error("Asset Sub Category is Required");
		return;
	}

	if (item.assetcode == null || item.assetcode == "") {
		toastr.error("Asset Code is Required");
		return;
	}
	if (item.assetmodel == null || item.assetmodel == "") {
		toastr.error("Asset Model is Required");
		return;
	}
	if (item.assetname == null || item.assetname == "") {
		toastr.error("Asset Name is Required");
		return;
	}
	if (item.lifespan == null || item.lifespan == "") {
		toastr.error("Lifespan Type is Required");
		return;
	}
	if (item.assetPrice == null || item.assetPrice == "") {
		toastr.error("Asset Price is Required");
		return;
	}
	if (item.owntype == null || item.owntype == "") {
		toastr.error("Ownership Type is Required");
		return;
	}
	if (item.wstatus == null || item.wstatus == "") {
		toastr.error("Working Status is Required");
		return;
	}

	if (item.assetDescription == null || item.assetDescription == "") {
		toastr.error("Asset Description is Required");
		return;
	}



	if (valid) {
		datas.push(item);
		console.log("SAVING DATA:::::::::::", item);
		savePlanDetails($("#assetId").val(),datas);
	}
}
function savePlanDetails(id,datas) {
	$('.loader').show();
	setTimeout(function() {
		$.ajax({
			type: "POST",
			url: "asset-management-add",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				$('.loader').hide();
				var itemName = $("#itemname option:selected").text();
				console.log("response====", response)
				if (response.code == "success") {
					// $("#messageParagraph").text(response.message);
					// $("#msgOkModal").removeClass("btn btn-primary edit-btn");
					// $("#msgOkModal").addClass("btn btn-primary edit-btn");
					// $("#msgModal").modal('show');
					toastr.success(response.message)
					viewAsset();
					gridOptions.api.paginationGoToFirstPage();
				} else {
					$('.loader').hide();
					// $("#messageParagraph").text("Something Went Wrong");
					// $("#msgOkModal").removeClass("btn btn-primary edit-btn");
					// $("#msgOkModal").addClass("btn btn-primary edit-btn");
					// $("#msgModal").modal('show');
					toastr.error("Something Went Wrong")
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}, 1000)
}
// save warrenty/ insurance/ documentation
function saveWarrenty() {
	$(".formValidation").remove();
	var valid = true;
	var item = {};

//	var uFile = $(uploadWarDoc_0)[0].files[0];
	var uFile = $(".wardocument")[0].files[0];
	var fileName = $("#imageWarrName_0").html();
	var data = [];
	var x = [];
	if(uFile){
		if (fileName != '' && fileName != 'undefined' && fileName != null) {
			item.fileName = fileName;
			console.log($(".uploadedWarrCls1").val())
			var reader = new FileReader();
			reader.readAsDataURL(uFile);
			var iURL = URL.createObjectURL(uFile);
			reader.onload = function() {
				data = reader.result.split(",");
				item.documentFileBase = data[1];
				item.documentURL = $(".uploadedWarrCls").val();
				item.dURL = iURL;
			};
		}
	}else{	
		if (fileName != '' && fileName != 'undefined' && fileName != null) {
			item.fileName = fileName;
			item.documentURL= $("#warrenDocUrl").val();
		}
	}
	
		
	item.assetId = $("#assetId2").val();
	item.serviceprovider = $("#serviceprovider").val();
	item.warrantyid = $("#warrantyid").val();
	item.sdate = $("#sdate").val();
	item.edate = $("#edate").val();
	item.remark = $("#remark").val();
	item.category = "Warenty";
	item.warId = $("#wardId").val();

	if (item.serviceprovider == null || item.serviceprovider == "") {
		toastr.error("Service Provider is Required");
		return;
	}
	if (item.warrantyid == null || item.warrantyid == "") {
		toastr.error("Warranty ID is Required")
		return;
	}
	if (item.remark == null || item.remark == "") {
		toastr.error("Remark is Required");
		return;
	}
	if (item.sdate == null || item.sdate == "") {
		toastr.error("Start Date is Required");
		return;
	}
	if (item.edate == null || item.edate == "") {
		toastr.error("End Date is Required");
		return;
	}

	if (valid) {
		console.log("datas===", item);
		saveDetails(item,'warrantyDetsId');
	}
}
function saveInsure() {
	$(".formValidation").remove();
	var valid = true;
	var item = {};

	var uFile = $(".wardocument1")[0].files[0];
	var fileName = $("#imageWarrName1_0").html();
	var data = [];
	var x = [];
	if(uFile){
		if (fileName != '' && fileName != 'undefined'
			&& fileName != null) {
			item.fileName = fileName;
			console.log($(".uploadedWarrCls1").val())
			var reader = new FileReader();
			reader.readAsDataURL(uFile);
			var iURL = URL.createObjectURL(uFile);
			reader.onload = function() {
				data = reader.result.split(",");
				item.documentFileBase = data[1];
				item.documentURL = $(this).find(".uploadedWarrCls1").val();
				item.dURL = iURL;
			};
		}
	}else{	
		if (fileName != '' && fileName != 'undefined' && fileName != null) {
			item.fileName = fileName;
			item.documentURL= $("#insDocUrl").val();
		}
	}
	item.assetId = $("#assetId2").val();
	item.insurancename = $("#insurancename").val();
	item.insuranceno = $("#insuranceno").val();
	item.isdate = $("#isdate").val();
	item.iedate = $("#iedate").val();
	item.category = "Insurance";
	item.slNoId = $("#slNoId").val();

	if (item.insuranceno == null || item.insuranceno == "") {
		toastr.error("Insurance No is Required");
		return;
	}
	if (item.insurancename == null || item.insurancename == "") {
		toastr.error("Insurance Name is Required");
		return;
	}
	if (item.isdate == null || item.isdate == "") {
		toastr.error("Start Date is Required");
		return;
	}
	if (item.iedate == null || item.iedate == "") {
		toastr.error("End Date is Required");
		return;
	}
	if (valid) {
		console.log("datas===", item);
		saveDetails(item, 'insuranceDetsId');
	}
}
function saveDocs() {
	$(".formValidation").remove();
	var valid = true;
	var item = {};

	var uFile = $(".compdocument")[0].files[0];
	var fileName = $("#imageCompName_0").html();
	var data = [];
	var x = [];
	if(uFile){
		if (fileName != '' && fileName != 'undefined'
			&& fileName != null) {
			item.fileName = fileName;
			var reader = new FileReader();
			reader.readAsDataURL(uFile);
			var iURL = URL.createObjectURL(uFile);
			reader.onload = function() {
				data = reader.result.split(",");
				item.documentFileBase = data[1];
				item.documentURL = $(this).find(".uploadedCompCls").val();
				item.dURL = iURL;
			};
		}
	}else{	
		if (fileName != '' && fileName != 'undefined' && fileName != null) {
			item.fileName = fileName;
			item.documentURL= $("#docDocUrl").val();
		}
	}
	
	item.assetId = $("#assetId2").val();
	item.sdate = $("#csdate").val();
	item.rdate = $("#crdate").val();
	item.docName = $("#docName").val();
	item.category = "Compliance";
	item.warId = $("#compId").val();
	/*if (item.sdate == null || item.sdate == "") {
		toastr.error("Start Date is Required");
		return;
	}*/
	if (item.docName == null || item.docName == "") {
		toastr.error("Document Name is Required");
		return;
	}
	if (valid) {
		console.log("datas===", item);
		saveDetails(item,'complianceDetsId');
	}
}
function saveDetails(datas, tab) {
	
	console.log("saveDetails====>>>",datas)
	$('.loader').show();
	setTimeout(function() {
		$.ajax({
			type: "POST",
			url: "asset-management-add-details",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				$('.loader').hide();
				var itemName = $("#itemname option:selected").text();
				console.log("response====", response)
				if (response.code == "success") {
					toastr.success(response.message);
					/*if () {
					    const assetIdVal = $("#assetId1").val();
					    gridOptions.api.forEachNode(function(node) {
					       if (node.data && node.data.assetId === assetIdVal) {
					           node.setSelected(true);
					           onSelectionChanged();
					           return;
					        }
					    });
					} else {
						gridOptions.api.forEachNode(function(node) {
							if (node.rowIndex === 0) {
								node.setSelected(true);
								onSelectionChanged()
							}
						});
					}*/
					viewAsset($("#assetId1").val(), tab);
					cancelWarrenty();
					cancelInsure();
					cancelDocs();
				} else {
					$('.loader').hide();
					$("#messageParagraph").text("Something Went Wrong");
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}, 1000)
}
// delete Functions
/*
function deleteOnClick() {
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



}*/
function deleteOnClickWarrenty() {
	var id = warId;
	var type = 'Warrenty';
	var assetId = $("#assetId2").val();
	console.log("warrenty doc id=" + id + "&type=" + type + "&assetId=" + assetId);
	detsDelete(id, type, assetId);
}
function deleteOnClickInsure() {
	var id = insId;
	var type = 'Insurance';
	var assetId = $("#assetId3").val();
	console.log("delete insure id=" + id + "&type=" + type + "&assetId=" + assetId);
	detsDelete(id, type, assetId);
}
function deleteOnClickDoc() {
	var id = compId;
	var type = 'Compliance';
	var assetId = $("#assetId4").val();
	console.log("delete doc id=" + id + "&type=" + type + "&assetId=" + assetId);
	detsDelete(id, type, assetId);
}
function detsDelete(id, type, assetId) {
	$.ajax({
		type: "POST",
		url: "asset-management-delete-details?id=" + id + "&type=" + type + "&assetId=" + assetId,
		success: function(response) {
			if (response.code == "success") {
				editAssetDetails(assetId);
				cancelModalBtn();

				onSelectionChangeWarrenty();
				onSelectionChangeInsure();
				onSelectionChangeChildCompliance();

			} else {

			}
		},
		error: function(data) {
			console.log(data)
		}
	})
}

//function for approve asset  
/*function approveOnclick() {
	if (id) {
		$('.loader').show();
		$.ajax({
			type: "GET",
			url: "asset-management-approve?id=" + id + "&assetname=" + assetname[0] + "&purchaseno=" + purchaseno[0] + "&assettype=" + assettype[0] + "&pdate=" + pdate[0],
			success: function(response) {
				if (response.code == "success") {
					$('.loader').hide();
					toastr.success("Asset Approved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#approveModal').modal('hide');
					viewAsset();

				} else {
					$('.loader').hide();
					toastr.success("Something went to wrong!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function() {
				$('.loader').hide();
			}
		})
	} else {
		$('.loader').hide();
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}

}*/
function approveAsset() {
	var id = $("#assetId").val();
	if (id) {
		Swal.fire({
			title: 'Are you sure?',
			text: 'Do you want to Approve this?',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonText: 'Yes, Approve it!',
			cancelButtonText: 'No, keep it',
			confirmButtonColor: 'var(--mainColor)',
		}).then((result) => {
			if (result?.value) {
				$('.loader').show();

				$.ajax({
					type: "GET",
					url: "asset-management-approve?id=" + id + "&assetname=" + assetname[0] + "&purchaseno=" + purchaseno[0] + "&assettype=" + assettype[0] + "&pdate=" + pdate[0],
					success: function(response) {
						if (response.code == "success") {
							$('.loader').hide();
							toastr.success("Asset Approved Successfully");
							/*$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");
							$("#msgModal").modal('show');*/
							$('#approveModal').modal('hide');
							viewAsset(id,'assetDetsId');

						} else {
							$('.loader').hide();
							toastr.success("Something went to wrong!");
							/*$("#msgOkModal").removeClass("btn3");
							$("#msgOkModal").addClass("btn1");*/
							$("#msgModal").modal('show');
						}
					},
					error: function() {
						$('.loader').hide();
					}
				})
			}
		});



	} else {
		$('.loader').hide();
		$("#alert").modal('show');
		document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
	}
}



function policyAllcate() {
	var valid = true;
	var policyData = [];
	policyOptions.api.forEachNode(function(rowNode, index) {
		if (rowNode.isSelected()) {
			var item = rowNode.data;
			policyData.push(item);
		}
	});
	var selectedRows = gridOptions.api.getSelectedRows();
	var cat = selectedRows[0].assetcatId;
	var subCat = selectedRows[0].assetsubcatId;
	/*if (poliAlloc == "in-house") {
		if ($("#assetemployee1").val() == null || $("#assetemployee1").val() == "") {
			toastr.error("employee Name is Required");
			return;
		}
		var assetemp = $("#assetemployee1").val();
	} else if (poliAlloc == "vendor") {
		if ($("#assetlocation1").val() == null || $("#assetlocation1").val() == "") {
			toastr.error("location Name is Required");
			return;
		}
		var assetemp = $("#assetlocation1").val();
	} else {
		$("body").removeClass("overlay");
		$("#messageParagraph").text("Please Select At least one");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}
	if ($("#asdate1").val() == null || $("#asdate1").val() == "") {
		toastr.error("Assign Date is Required");
		return;
	}*/

	var assetList = selectedRows[0].assetId;
	/*	var assigndate = $("#asdate1").val();
		var assetcat = poliAlloc;
		var assetGrp = "Asset";
		var type = "Asset Preventive";
		var selectedNodes1 = policyData;*/
	var datas = [];
	var obj = {};
	obj.allocationId = $("#allocationId").val();
	obj.assetcat = selectedRows[0].assetcatId;
	obj.assetsubcat = selectedRows[0].assetsubcatId;
	//obj.poliAlloc = poliAlloc;
	//obj.assetemp = assetemp
	obj.assetGrp = "Asset";
	obj.type = "Asset Preventive";
	//obj.assigndate = $("#asdate1").val();
	obj.assetId = selectedRows[0].assetId;
	obj.policyDataList = policyData;

	if (valid) {
		datas.push(obj);
		console.log("JSON.stringify(datas)====",datas)
		$.ajax({
			type: "POST",
			url: "asset-management-allocate-policy",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "success") {
					viewAsset(assetList, 'policyAllocationId');
					CancelPolicyAllcate();
				} else {
					$("#messageParagraph").text("Something went to wrong!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) {
				console.log(data)
			}
		})
	} else {
		$('#warningAssign').html("Please Fill The Required Info !");
		$('#warningAssign').show();
	}

}
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}
function reset() {
	$('#quickFilter').val("");
	onQuickFilterChanged();
}


function closeKey() {
	gridOptions.api.setQuickFilter(null); $('#quickFilter').val('');
	document.getElementById('closeKey').style.display = 'none';
	$('#totalAsset').find('span').html(gridOptions.api.getModel().getRowCount());
}