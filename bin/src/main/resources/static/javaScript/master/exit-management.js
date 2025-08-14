function cancelResign() {
	$(".formValidation").remove();
	$("#cancelResign, #submitResign, #draftResign, #resignDateCalendar, #releaseDateCalendar, #clickImg_0, #dltImage_0, #uploadFor_0").hide();
	$("#empNameList, #empIDAuto, #subject, #resignDate, #releaseDate").prop('disabled', true);
	
	CKEDITOR.instances.commentck?.on('instanceReady', function() {
		this.setReadOnly(true);
	});
	CKEDITOR.instances.commentck?.setReadOnly(true);
	disableSelect('multipleResignationTo');
	disableSelect('multipleResignationCc');
	$("#clickImg_0").removeClass("ti-pencil");
	$("#doctbodyData").css("pointer-events", "none");
	$("#newResign").show();
	
}
function editResign() {
	$(".formValidation").remove();
	$("#cancelResign, #submitResign, #draftResign, #resignDateCalendar, #releaseDateCalendar, #clickImg_0, #dltImage_0, #uploadFor_0").show();
	$("#newResign, #deleteOnClick, #editResign, #inactiveEmployee, #next1").hide();
	$("#empNameList, #empIDAuto, #subject, #resignDate, #releaseDate").prop('disabled', false);
	CKEDITOR.instances.commentck?.setReadOnly(false);
	enableSelect('multipleResignationTo');
	enableSelect('multipleResignationCc');
	$("#clickImg_0").addClass("ti-pencil");
	$("#doctbodyData").css("pointer-events", "all");

	let isDocAvail = $("#uploadHidden_0").val();

	console.log("isDocAvail-->", isDocAvail);
	if (isDocAvail != "") {
		$("#clickImg_0").addClass("ti-pencil");
		$("#clickImg_0").removeClass("ti-plus");

		console.log("docs present");
	}
	else {
		$("#clickImg_0").removeClass("ti-pencil");
		$("#clickImg_0").addClass("ti-plus");
		console.log("Docs not present");
	}
}
function cancelExit() {
	$(".formValidation").remove();
	$("#employeeIdModal, #employeeNameModal, #resignationDtModal, #releaseDtModal, #expNoticePeriodModal, #fromDateCalenderNew, #noticePeriodModal, #resType, #remarks, #filterFromDateNew").prop('disabled', true);
	$(" #initiateResign").show();
	$("#cancelExit, #initiateExit, #fromDateCalenderNew, #clearanceBeforeCalender").hide();
	disableSelect('multipleClearance');
}
function initiateResign() {
	$(".formValidation").remove();
	$("#employeeIdModal, #employeeNameModal, #resignationDtModal, #releaseDtModal, #expNoticePeriodModal, #fromDateCalenderNew, #noticePeriodModal, #resType, #remarks, #filterFromDateNew").prop('disabled', false);
	$(" #initiateResign").hide();
	$("#cancelExit, #initiateExit, #fromDateCalenderNew, #clearanceBeforeCalender").show();
	$('#typeOfProceed').val("INITIATED");
	enableSelect('multipleClearance');
}
function cancelFinal() {
	$(".formValidation").remove();
	$("#basicAmount, #hraAmount, #conAllowanceAmount, #washAllowanceAmount, #epfAmount, #esicAmount, #professionalTax, #salaryAdvance, #bonusAmount, #leaveAmount, #otherAmount").prop('disabled', true);
	$(" #finalSettle, #downLoadFinal").show();
	$("#saveFinal, #saveFinal1, #cancelFinal").hide();
}
function finalSettle() {
	$(".formValidation").remove();
	$("#basicAmount, #hraAmount, #conAllowanceAmount, #washAllowanceAmount, #epfAmount, #esicAmount, #professionalTax, #salaryAdvance, #bonusAmount, #leaveAmount, #otherAmount").prop('disabled', false);
	$(" #finalSettle, #downLoadFinal").hide();
	$("#saveFinal, #saveFinal1, #cancelFinal").show();
}
function cancelModalBtn() {
	$('.modal').modal('hide');
}
function enableSelect(id) {
	const selectElement = document.getElementById(id);
	selectElement.disabled = false;
	$('.chosen-select').trigger('chosen:updated'); // Update Chosen
}
function disableSelect(id) {
	const selectElement = document.getElementById(id);
	selectElement.disabled = true;
	$('.chosen-select').trigger('chosen:updated'); // Update Chosen
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


function onchangeResignType(){
    	var resignTypeSelectData = $("#resType").val();
    	var reasonForResignation = "";
    	if(resignTypeSelectData == "RESIGN"){
    		$("#resTypeAttritionResign").val(null);
    		$("#attritionReasonTypeResign").show();
    		$("#attritionReasonTypeTerminate").hide();
    		reasonForResignation = $("#resTypeAttritionResign").val();
    		
    		
    	}
    	
    	if(resignTypeSelectData == "TERMINATE"){
    		$("#resTypeAttritionTerminate").val(null);
    		$("#attritionReasonTypeTerminate").show();
    		$("#attritionReasonTypeResign").hide();
    		reasonForResignation = $("#resTypeAttritionTerminate").val();
    	}
    }

function newResign() {
	
	//	gridOptions.api.deselectAll();
	var $chosenSelect = $("#multipleResignationCc");
	$chosenSelect.find('option').prop('disabled', false);
	$chosenSelect.val(null);
	$chosenSelect.trigger("chosen:updated");
	var $chosenSelectTo = $("#multipleResignationTo");
	$chosenSelectTo.find('option').prop('disabled', false);
	$chosenSelectTo.val(null);
	$chosenSelectTo.trigger("chosen:updated");

	$(".formValidation").remove();
	$("#multipleResignationTo").val('').trigger('chosen:updated');
	$("#multipleResignationCc").val('').trigger('chosen:updated');

	$("#empIDAuto").val("");
	$("#empNameList").val("");
	$("#empIdAutoPopup").val("");
	$('#idDiv').find('span').html("");
	$("#empNameList").val("");
	$("#empIdAuto").val("");
	$("#employeeNameTop1").text("");
	$("#employeeNameTop2").text("");
	$("#employeeNameTop3").text("");
	$("#employeeNameTop4").text("");
	$("#employeeNameTop5").text("");
	$("#employeeNameTop6").text("");
	$("#suggesstion-box_").hide();
	var userid = $("#sessionId").val();
	var userrole = $("#sessionRole").val();
	$('#empNameList').prop('disabled', false);
	$('#empNameList').prop('readonly', false);
	var rolesArray = userrole.match(/rol\d{3}/g);
	var hasRol001 = rolesArray.includes('rol001');
	var hasRol010 = rolesArray.includes('rol010');
	if (hasRol010 || hasRol001) {
	} else {
		getEmployeeList(userid);
	}
	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
		+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
		+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
		+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
		+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
		+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
		+ '</tr>';
	$("#doctbodyData").html(tbl);
	var sm = $("#emplManagerAutoSearch").val();
	var date = (new Date()).toISOString().split('T')[0];
	var newDate = changeDateFormat(date);
	$("#leaveApplyDate").val(newDate);
	$("#empID").val($("#sessionId").val());
	$("#empName").val($("#sessionName").val());
	$("#subject").val("Letter Of Resignation");
	$("#resignationDraftId").val("");
	$("#multipleResignationCc").val("");
	$("#subject").val("");
	$("#commentck").val("");
	CKEDITOR.instances.commentck.setData("");
	CKEDITOR.instances.commentck.setReadOnly(false);
	$("#resignDate").val("");
	$("#releaseDate").val("");

	$(" #initiateBtn, #clearanceBtn, #finalBtn, #downloadBtn").hide();
	
	
	if(type) {
				$("#empNameList").attr("disabled",true);
				let empid = $("#sessionId").val();
				let empName = $("#sessionName").val();
				selectAutocompleteValueItem1(empid, null, null, window.btoa(empName))
			} else {
				$("#empNameList").removeAttr("disabled");
			}
}
/* Employee AutoSearch */
// for resign
function getEmployeeList(userid) {
	if (userid) {
		var search = userid;
	} else {
		var search = $("#empNameList").val();
	}
	if (search) {
		$.ajax({
			type: "POST",
			url: "employee-management-get-employee-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.code == "success") {
					if (response.body != null) {
						if (userid) {
							selectAutocompleteValueItem1(response.body[0].key, response.body[0].data, response.body[0].code, window.btoa(response.body[0].name),
								response.body[0].key, response.body[0].name)
							$('#empNameList').prop('disabled', true);
						} else {
							$('#empNameList').prop('disabled', false);
							$("#empNameList").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" style="margin-left:-32px; font-weight:100; font-size:14px; color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueItem1(\''
									+ response.body[i].key
									+ '\',\''
									+ response.body[i].data
									+ '\',\''
									+ response.body[i].code
									+ '\',\''
									+ window.btoa(response.body[i].name)
									+ '\')">'
									+ response.body[i].key
									+ " - "
									+ response.body[i].name
									+ '</li>';
							}
							/* var div = '<li class="search-choice search-choice1 default"><span>' + response.body[i].data + ' </span></li>';*/
							content += '</ul>';
							$("#suggesstion-box_").show();
							$("#suggesstion-box_").html(content);
						}
					} else {
						$("#empNameList").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<font-weight:100; font-size:14px; color:#ccc; background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box_").show();
						$("#suggesstion-box_").html(content);
					}
				}
			},
			error: function(data) {
			}
		})
	} else {
		$("#empIDAuto").val("");
		$("#empNameList").val("");
		$("#suggesstion-box_").hide();
	}
}
function selectAutocompleteValueItem1(empid, manager, managerId, empName) {
	if (empid) {
		$("#emplManagerAutoSearch").val(managerId);
		$("#empNameList").val(window.atob(empName));
		$("#empIDAuto").val(empid);
		$("#empIdAutoPopup").val(empid);
		$("#empNameList").attr('data-procat', empid);
		$("#suggesstion-box_").hide();
	} else {
		$("#empNameList").val("");
		$("#empIDAuto").val("");
		$("#empIdAutoPopup").val("");
		$("#empNameList").attr('data-procat', "");
		$("#suggesstion-box_").hide();
	}
}
// for clearance
function getEmployeeList1(userid) {
	if (userid) {
		var search = userid;
	} else {
		var search = $("#empNameList1").val();
	}
	if (search) {
		$.ajax({
			type: "POST",
			url: "exit-management-employee-list-clear",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.code == "success") {
					if (response.body != null) {
						if (userid) {
							selectAutocompleteValueItem2(response.body[0].key, response.body[0].data, response.body[0].code, window.btoa(response.body[0].name),
								response.body[0].key, response.body[0].name);
							$('#empNameList1').prop('disabled', true);
						} else {
							$('#empNameList1').prop('disabled', false);
							$("#empNameList1").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" style="margin-left:-32px; font-weight:100; font-size:14px; color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueItem2(\''
									+ response.body[i].key
									+ '\',\''
									+ window.btoa(response.body[i].name)
									+ '\')">'
									+ response.body[i].key
									+ " - "
									+ response.body[i].name
									+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box_1").show();
							$("#suggesstion-box_1").html(content);
						}
					} else {
						$("#empNameList1").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<font-weight:100; font-size:14px; color:#ccc; background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
							+ "No Data Found" + '</li>';
						content += '</ul>';
						$("#suggesstion-box_1").show();
						$("#suggesstion-box_1").html(content);
						$("#empIdAuto1").val("");
					}
				}
			},
			error: function(data) {
			}
		})
	} else {
		$("#empNameList1").val("");
		$("#empIdAuto1").val("");
		$("#suggesstion-box_1").hide();
	}
}
function selectAutocompleteValueItem2(empid, empName) {
	if (empid && empName) {
		$("#empNameList1").val(window.atob(empName));
		$("#empIdAuto1").val(empid);
		$("#empNameList1").attr('data-procat', empid);
		$("#suggesstion-box_1").hide();
	} else {
		$("#empIdAuto1").val("");
		$("#empNameList1").val("");
		$("#empNameList1").attr('data-procat', "");
		$("#suggesstion-box_1").hide();
	}
}
function dateChange() {
	var fromdate = $('#resignDate').val();
	var todate = $('#releaseDate').val();
	var fd = fromdate.split("-");
	var td = todate.split("-");
	if (fromdate != '' && todate != '') {
		if (fd[2] <= td[2]) {
			if (fd[1] == td[1]) {
				if (fd[0] <= td[0]) {
				} else {
					setFromToDate();
				}
			} else if (fd[1] < td[1]) {

			} else {
				setFromToDate();
			}
		} else {
			setFromToDate();
		}
	} else {
	}
}
function setFromToDate() {
	toastr.error("Please choose release date greater than or equal to resign date ");

	$("#releaseDate").val("");
}
function disableOptions(id, optionsToDisable) {
	var selectElement = document.getElementById(id);
	Array.from(selectElement.options).forEach(option => {
		option.disabled = false;
	});
	optionsToDisable.forEach(value => {
		var option = selectElement.querySelector(`option[value="${value}"]`);
		if (option) {
			option.disabled = true;
		}
	});
	$(selectElement).trigger("chosen:updated");
}
function setFinalSettlementData(data) {
	if (data.length > 0 && data[0]) {
		let selectedData = data[0];
		$("#exitIdFinancial").val(selectedData.employeeExit);
		$("#empIdFS").val(selectedData.empID);
		$("#empNameFS").val(selectedData.empName);
		$("#empDesignationFS").val(selectedData.designation);
		$("#empDeptFS").val(selectedData.deptName);
		$("#dojFS").val(selectedData.empJoiningDate);
		$("#dorFS").val(selectedData.resignDate);
		$("#dolFS").val(selectedData.releaseDate);
		let dateStr = selectedData.releaseDate;
		let parts = dateStr.split("-");
		let date = new Date(parts[2], parts[1] - 1, parts[0]);
		let monthName = date.toLocaleString('default', { month: 'long' });
		$("#headerFs").text(monthName);
	} else {
	}
}
//Function for view Exit
function viewExitsAgGrig() {
	var employeeExit = $("#exitModalId").text();
	var userid = $("#sessionId").val();
	var userrole = $("#sessionRole").val();

	var navTabs = $(".operational-nav-links");
	console.log("navTabs-->", navTabs);
	var activeTab = navTabs.filter(".active");
	
	var roleid = "";
	for (var i = 6; i <= userrole.length; i = i + 6) {
		roleid = roleid + '"' + userrole.slice(i - 6, i) + '",';
	}
	roleid = roleid.substring(0, roleid.length - 1);
	//exit Management
	agGrid.simpleHttpRequest({
		url: "exit-management-view-through-ajax?userid=" + userid + "&roleid=" + roleid + "&selftype=" + type,
	}).then(function(data) {
		var allData = JSON.parse(data.body);
		gridOptions.api.setRowData(allData);
		if (employeeExit) {
			// Iterate through all rows to find the matching ID
			gridOptions.api.forEachNode(function(node) {
				if (node.data && node.data.employeeExit === employeeExit) {
					node.setSelected(true); // Select the matching row
					return; // Stop further iteration once found
				}
			});
		} else {
			// If no employeeExitId, select the first row
			var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		}
		
		if(allData == null) {
			newResign();
		}
		
		handelNavTabs(activeTab);
	});
}
//Function for edit Exit
function editResignationApplyDraft(id) {
	var imagesdiv = "";
	var scat = "";
	var tbl = "";
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "exit-management-draft-edit?id=" + id,
		async: false,
		success: function(response) {
			if (response.code == "success") {
				$(".loader").hide();
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.editData[0];
				$(".formValidation").remove();
				$("#empIDAuto").val("");
				$("#empNameList").val("");
				$("#empIdAutoPopup").val("");
				$("#empIDAuto").val("");
				$("#empNameList").val("");
				$("#empIdAutoPopup").val("");
				$("#suggesstion-box_").hide();
				$("#resignationDraftId").val(allData.resignationDraftId);
				CKEDITOR.instances['commentck'].setData(allData.reason);
				$('#idDiv').find('span').html(id);
				$("#resignDate").val(allData.resignDate);
				$("#releaseDate").val(allData.releaseDate);
				$("#subject").val(allData.subject);
				$("#emplManagerAutoSearch").val(allData.empId);
				$('#empNameList').prop('readonly', true);
				$("#empIDAuto").val(allData.empId);
				$("#empNameList").val(allData.empName);
				$("#employeeNameTop1").text(allData.empName);
				$("#employeeNameTop2").text(allData.empName);
				$("#employeeNameTop3").text(allData.empName);
				$("#employeeNameTop4").text(allData.empName);
				$("#employeeNameTop5").text(allData.empName);
				$("#employeeNameTop6").text(allData.empName);

				var releaseDate = getFormattedDate(allData.releaseDate);
				console.log("release date-->", releaseDate);
				$('#releaseDateCalendar').datetimepicker({
					format: 'd-m-Y',
					timepicker: false,
					closeOnDateSelect: true,
					value: releaseDate
				});


				var resignDate = getFormattedDate(allData.resignDate);
				console.log("resignDate -->", resignDate);
				$('#resignDateCalendar').datetimepicker({
					format: 'd-m-Y',
					timepicker: false,
					closeOnDateSelect: true,
					value: resignDate
				});

				var allData2 = allData.docList;
				var disableTo = allData.regTo;
				var disableCc = allData.regCC;

				disableTo = disableTo.split(',');
				disableCc = disableCc.split(',');
				disableOptions('multipleResignationTo', disableCc)
				disableOptions('multipleResignationCc', disableTo)
				$('#multipleResignationTo').val(disableTo);
				$('#multipleResignationCc').val(disableCc);
				$('#multipleResignationTo').trigger("chosen:updated");
				$('#multipleResignationCc').trigger("chosen:updated");
				var tbl1 = '';
				if (allData2 != null && allData2.length != 0) {
					$("#doctbodyData").empty();
					allData2.forEach(function(rowNode, index) {
						var filename = extractFilename(rowNode.docurl);
						var iurl = "/document/resign/" + filename;
						var fileExt = getFileExtension(filename);
						var fileIcon = getFileIcon(fileExt);
						if (getFileExtension(filename) == "jpg" || getFileExtension(filename) == "png" || getFileExtension(filename) == "jpeg") {
							imagesdiv += `<div class="col-lg-2 col-md-8 mb-4 mb-lg-0">
								  <div class="bg-image hover-overlay ripple shadow-1-strong rounded" data-ripple-color="light">
								  <img src=${iurl} class="w-100" /> </div></div>`;
						}
						tbl1 = tbl1 + `<tr>
						           <td style="display:none" align="center" class="pdb-24">
						               <input class="checkCls" type="checkbox" id="check2"><label for="check2"></label>
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
						                   <input type="text" value="${rowNode.docName}" class="form-control docNoclss" id="docnoid_${index}">
						               </div>
						           </td>
						           <td class="d-flex align-items-center">
						               <div class="control-group">
						                   <label class="custom-file-upload m-0" for="uploadDoc_${index}" id="uploadFor_${index}">
						                       <i class="ti-pencil" id="clickImg_${index}"></i>
						                   </label>
						                   <div class="controls">
						                       <input type="file" class="document" id="uploadDoc_${index}" name="userImage" onchange="saveMultiFile(event)" value="${rowNode.fileName}" />
						                   </div>
						               </div>
						               <input type="hidden" id="uploadHidden_${index}" value="${rowNode.docurl}" class="uploadHidCls">
						               <div id="uploadedBillDiv_${index}" align="center" class="uploadedBillCls">
						                   <div class="m-0 mx-2 p-0 uploadicon">
						                       <i class="${fileIcon}" style="color:#BF05FF;    font-size: 17px;" onclick="viewDocuemntFile('${window.btoa(rowNode.docurl)}')"></i>
						                   </div>
						               </div>
						               <div id="imageName_${index}" class="imageName">${rowNode.fileName}</div>
						               <input type="hidden" id="editId_${index}" value="${rowNode.exitId}">
						               <div id="dltImage_${index}" class="custom-file-delete">
						                   <i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i>
						               </div>
						           </td>
						       </tr>`;
					});
					$("#doctbodyData").append(tbl1);
				} else {
					var tbl = `<tr>
					       <td style="display:none" align="center" class="pdb-24">
					           <input class="checkCls" type="checkbox" id="check2"><label for="check2"></label>
					       </td>
					       <td style="display:none">
					           <div class="form-group">
					               <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);">
					                   <option value="">Select</option>
					               </select>
					           </div>
					       </td>
					       <td>
					           <div class="form-group">
					               <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)">
					           </div>
					       </td>
					       <td class="d-flex align-items-center">
					           <div class="control-group">
					               <label class="custom-file-upload mt-0" for="uploadDoc_0" id="uploadFor_0">
					                   <i class="ti-plus" id="clickImg_0"></i>
					               </label>
					               <div class="controls">
					                   <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" />
					               </div>
					           </div>
					           <input type="hidden" id="uploadHidden_0" class="uploadHidCls">
					           <div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div>
					           <div id="imageName_0" class="imageName"></div>
					           <div id="dltImage_0" class="dltImage"></div>
					           <input type="hidden" id="editId_0">
					       </td>
					   </tr>`;
					$("#doctbodyData").html(tbl);
				}
			}
		},
		error: function(data) {
		}
	});
}


function getFormattedDate(date) {
	let releaseDateStr = date;
	let dateParts = releaseDateStr.split("-");
	let formattedDate = new Date(dateParts[2], dateParts[1] - 1, dateParts[0]);

	return formattedDate;
}
// file functions
function getFileExtension(filename) {
	return filename.split('.').pop();
}
function extractFilename(url) {
	var urlParts = url.split('/');
	var filename = urlParts[urlParts.length - 1];
	return filename;
}
function viewDocuemntFile(iurl) {
	window.open(window.atob(iurl), '_blank', 'width=800,height=600,top=100,left=100');
}
function openDeleteConfirm() {
	//$("#dltValue").val("");
	//$('#deleteAttachment').modal('show');
	let index = 0;
	document.getElementById("uploadedBillDiv_" + index).innerHTML = "";
	document.getElementById("uploadHidden_" + index).value = "";
	document.getElementById("uploadDoc_" + index).value = "";
	document.getElementById("imageName_" + index).innerText = "";
	document.getElementById("dltImage_" + index).style.display = "none";
	$("#clickImg_0").removeClass("ti-pencil");
	$("#clickImg_0").addClass("ti-plus");

}
// calculation functions
function calculateNoticePeriod(resignDate, releaseDate) {
	let resignDateParts = resignDate.split("-");
	let releaseDateParts = releaseDate.split("-");
	let resignDateObj = new Date(resignDateParts[2], resignDateParts[1] - 1, resignDateParts[0]);
	let releaseDateObj = new Date(releaseDateParts[2], releaseDateParts[1] - 1, releaseDateParts[0]);
	let timeDifference = releaseDateObj - resignDateObj;
	let differenceInDays = timeDifference / (1000 * 3600 * 24);
	return differenceInDays;
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
	var extension = fileName.split(".").pop();
	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");


	console.log("extension is-->", extension);
	if (extension == 'pdf' || extension == 'jpg' || extension == 'png' || extension == 'xls' || extension == 'doc' || extension == 'webp' || extension == 'xlsx' || extension == 'docx' || extension == 'txt') {
		window.st = 1;
	} else {
		toastr.error("Invalid file type! Please upload a valid document file. Supported formats: PDF, JPG, PNG, XLS, DOC, WEBP, XLSX, DOCX, TXT.");
		openDeleteConfirm();
		/*$("#messageParagraph").text("Pick A Valid Document File");
		$("#msgModal").modal('show');*/
		window.st = 0;

		var lengthOfTableRow1 = 0;
		$("#docTbl > #doctbodyData > tr").each(function() {
			lengthOfTableRow1 = lengthOfTableRow1 + 1;
		})
		var id = $("#dltValue").val();
		$("#" + id).closest('tr').remove();
		closeDeleteConfirm();
		if (lengthOfTableRow1 == 1) {
			var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0"  onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)"></div></td>'
				+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"  ></div> </td>'
				+ '</tr>';
			$("#doctbodyData").append(tbl);
		}
	}
	if (extension != null && extension != "") {
		$("#uploadHidden_" + counter).val('');
	}
	var LightImg = "";

	if (["jpg", "png", "jpeg", "webp"].includes(extension.toLowerCase())) {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-image'></i></a></span>";
	} else if (extension.toLowerCase() === "pdf") {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon bdr-n'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-pdf'></i></a></span>";
	} else if (["xls", "xlsx"].includes(extension.toLowerCase())) {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-excel'></i></a></span>";
	} else if (["doc", "docx"].includes(extension.toLowerCase())) {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-word'></i></a></span>";
	} else if (["mp4", "mov"].includes(extension.toLowerCase())) {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-video'></i></a></span>";
	} else if (["mp3", "wav", "aac"].includes(extension.toLowerCase())) {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-audio'></i></a></span>";
	} else {
		LightImg = "<span class='m-0 mx-2 p-0 uploadicon'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file'></i></a></span>";
	}

	var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm()'></i>";
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	$("#dltImage_" + counter).html(dltImg);
	$("#dltImage_" + counter).addClass("custom-file-delete");
	$("#clickImg_" + counter).removeClass("ti-plus");
	$("#clickImg_" + counter).addClass("ti-pencil");
	$("#dltImage_0").show();
}
// save resignation draft
var datas = [];
function draftResign() {
	$(".formValidation").remove();
	obj = {};
	var validation = true;
	var reason = CKEDITOR.instances.commentck.getData();
	var to = $('#multipleResignationTo').val();
	var cc = $('#multipleResignationCc').val();
	var mailTo = to.join(",");
	var mailCc = cc.join(",");
	var participants = [];
	var uploadList = [];
	obj['resignationDraftId'] = $('#resignationDraftId').val();
	obj['regTo'] = mailTo;
	obj['regCC'] = mailCc;
	obj['subject'] = $('#subject').val();
	obj['reason'] = reason;
	obj['resignDate'] = $('#resignDate').val();
	obj['releaseDate'] = $('#releaseDate').val();
	obj['empId'] = $('#empIDAuto').val();


	var allValid = true;
	var reason = CKEDITOR.instances.commentck.getData();


	// Validate other required fields
	if ($('#empNameList').val().trim() === "") {
		toastr.error('Employee name is required');
		allValid = false;
		return;
	}

	if ($('#empIDAuto').val().trim() === "") {
		toastr.error('Employee ID is required');
		allValid = false;
		return;
	}

	// Validate "To" field
	var mailTo = $('#multipleResignationTo').val();
	if (mailTo == null || mailTo === "" || mailTo.length == 0) {
		toastr.error('To field is required');
		allValid = false;
		return;
	}
	if ($('#subject').val().trim() === "") {
		toastr.error('Subject is required');
		allValid = false;
		return;
	}
	// Validate "Reason"
	if (reason == null || reason.trim() === "") {
		toastr.error('Reason is required');
		allValid = false;
		return;
	}



	if ($('#resignDate').val().trim() === "") {
		toastr.error('Resign Date is required');
		allValid = false;
		return;
	}

	if ($('#releaseDate').val().trim() === "") {
		toastr.error('Release Date is required');
		allValid = false;
		return;
	}


	let docName = $("#docnoid_0").val();
	let attachment = document.getElementById(`uploadDoc_0`).files.length > 0;
	let imageName = $("#imageName_0").text();

	if (docName != "" && imageName == "") {
		toastr.error("Attachment is required");
		return;
	} else if (docName == "" && imageName != "") {
		toastr.error("Document name is required");
		return;
	}
	if (allValid) {
		$("#doctbodyData > tr").each(function(i) {
			var uFile = $(this).find(".document")[0].files[0];
			var fileName = $(this).find(".document").val();
			var fileNametxt = $(this).find('.imageName').text();
			var data = [];
			var x = [];
			if (fileNametxt != '' && fileNametxt != 'undefined' && fileNametxt != null) {
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
				}
				uploadData = {};
				uploadData['documnentName'] = $("#docnoid_" + i).val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = $(this).find('.imageName').text();
				uploadData['documentURL'] = $(this).find(".uploadHidCls").val();
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				uploadList.push(uploadData);
			}
			obj.documentList = uploadList;
		});
		saveResignApplyDetailsdraft(obj);
	}
}
function saveResignApplyDetailsdraft(datas) {
	$(".loader").show();
	setTimeout(() => {
		$.ajax({
			type: "POST",
			url: "exit-management-draft-apply",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "success") {
					$(".loader").hide();
					toastr.success(response.message);
					cancelResign();
					viewExitsAgGrig();
					
				} else if (response.code == "duplicate") {
					$(".loader").hide();
					toastr.error(response.message);
					//$("newResign").hide();
					/*$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
					cancelResign();
					viewExitsAgGrig();
				} else {
					$(".loader").hide();
					toastr.error("Something Went Wrong.");
					/*$("#messageParagraph").text("Something Went Wrong.");
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
					cancelResign();
					viewExitsAgGrig();
				}
			},
			error: function(datas) {
			}
		})
	}, 1000)
}
// save resignation submit
function submitResign() {
	/*var allValid = true;
	 var reason = CKEDITOR.instances.commentck.getData();
	if (reason == null || reason == "") {
		allValid = validationUpdated("Reason", "commentckdreason");
	}
	var mailTo = $('#multipleResignationTo').val();
	if(mailTo == null || mailTo == ""){
		allValid = validationUpdated(toastr.error('To Required'), "validationForTo")
	}
	if (!validationUpdated("Employee Name Required", 'empNameList'))
		allValid = false;
	if (!validationUpdated("Employee ID Required", 'empIDAuto'))
		allValid = false;
	if (!validationUpdated("Subject Required", 'subject'))
		allValid = false;
	if (!validationUpdated("Resign Date Required", 'resignDate'))
		allValid = false;
	if (!validationUpdated("Release Date Required", 'releaseDate'))
		allValid = false;
	if(allValid){
		$('#confirmExitModal').modal('show');
	}*/

	var allValid = true;
	var reason = CKEDITOR.instances.commentck.getData();


	// Validate other required fields
	if ($('#empNameList').val().trim() === "") {
		toastr.error('Employee name is required');
		allValid = false;
		return;
	}

	if ($('#empIDAuto').val().trim() === "") {
		toastr.error('Employee ID is required');
		allValid = false;
		return;
	}

	// Validate "To" field
	var mailTo = $('#multipleResignationTo').val();
	if (mailTo == null || mailTo === "" || mailTo.length == 0) {
		toastr.error('To field is required');
		allValid = false;
		return;
	}
	if ($('#subject').val().trim() === "") {
		toastr.error('Subject is required');
		allValid = false;
		return;
	}
	// Validate "Reason"
	if (reason == null || reason.trim() === "") {
		toastr.error('Reason is required');
		allValid = false;
		return;
	}



	if ($('#resignDate').val().trim() === "") {
		toastr.error('Resign Date is required');
		allValid = false;
		return;
	}

	if ($('#releaseDate').val().trim() === "") {
		toastr.error('Release Date is required');
		allValid = false;
		return;
	}

	let docName = $("#docnoid_0").val();
	let attachment = document.getElementById(`uploadDoc_0`).files.length > 0;
	let imageName = $("#imageName_0").text();

	if (docName != "" && imageName == "") {
		toastr.error("Attachment is required");
		return;
	} else if (docName == "" && imageName != "") {
		toastr.error("Document name is required");
		return;
	}

	// Show modal if all validations pass
	if (allValid) {
		//$('#confirmExitModal').modal('show');
		Swal.fire({
			title: 'Are you sure?',
			text: 'Do you really want to apply for resignation? This process cannot be undone.',
			icon: 'warning',
			showCancelButton: true,
			confirmButtonText: 'Yes, apply',
			cancelButtonText: 'Cancel',
			confirmButtonColor: 'var(--mainColor)',
		}).then((result) => {
			if (result?.value) {
				saveResignApply();
				
			}
			else {
				console.log("non ");
			}
		});
	}

}
var data = [];
function saveResignApply() {
	$(".formValidation").remove();
	$(".loader").show();
	var validation = true;
	obj = {};
	var reason = CKEDITOR.instances.commentck.getData();
	var participants = [];
	var uploadList = [];
	var to = $('#multipleResignationTo').val();
	var cc = $('#multipleResignationCc').val();
	var mailTo = to.join(",");
	var mailCc = cc.join(",");
	obj['resignationDraftId'] = $('#resignationDraftId').val();
	obj['regTo'] = mailTo;
	obj['regCC'] = mailCc;
	obj['subject'] = $('#subject').val();
	obj['reason'] = reason;
	obj['resignDate'] = $('#resignDate').val();
	obj['releaseDate'] = $('#releaseDate').val();
	obj['empId'] = $('#empIDAuto').val();
	if (validation) {
		$("#doctbodyData > tr").each(function(i) {
			var uFile = $(this).find(".document")[0].files[0];
			var fileName = $(this).find(".document").val();
			var fileNametxt = $(this).find('.imageName').text();
			var data = [];
			var x = [];
			if (fileNametxt != '' && fileNametxt != 'undefined' && fileNametxt != null) {
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
				}
				uploadData = {};
				uploadData['documnentName'] = $("#docnoid_" + i).val();
				uploadData['documentFile'] = x;
				uploadData['fileName'] = $(this).find('.imageName').text();
				uploadData['documentURL'] = $(this).find(".uploadHidCls").val();
				uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
				uploadList.push(uploadData);
			}
			obj.documentList = uploadList;
		});

		console.log("object-->", obj);
		saveResignApplyDetails(obj);
	}
}
function saveResignApplyDetails(data) {
	setTimeout(() => {
		$.ajax({
			type: "POST",
			url: "exit-management-apply",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response.code == "success") {
					$(".loader").hide();
					toastr.success(response.message);
					
					cancelResign();
					viewExitsAgGrig();
				
					
				} else if (response.code == "duplicate") {
					$(".loader").hide();
					toastr.error(response.message);
					/*$("#messageParagraph").text(response.message);
				  $("#msgOkModal").removeClass("btn btn-primary edit-btn");
				  $("#msgOkModal").addClass("btn btn-primary edit-btn");
				  $("#msgModal").modal('show');*/
					cancelResign();
					viewExitsAgGrig();
					//$("newResign").hide();
				} else {
					$(".loader").hide();
					toastr.error("Something Went Wrong");
					/*$("#messageParagraph").text("Something Went Wrong");
				  $("#msgOkModal").removeClass("btn btn-primary edit-btn");
				  $("#msgOkModal").addClass("btn btn-primary edit-btn");
				  $("#msgModal").modal('show');*/
					cancelResign();
					viewExitsAgGrig();
				}
			},
			error: function(datas) {
			}
		})
	}, 1000)
}
// exit initiate
function getNoticePeriod() {
	var fromdate = $('#resignationDtModal').val();
	var todate = $('#filterFromDateNew').val();
	var fd = fromdate.split("-");
	var td = todate.split("-");
	if (fromdate != '' && todate != '') {
		if (fd[2] <= td[2]) {
			if (fd[1] == td[1]) {
				if (fd[0] <= td[0]) {
					var days = calculateNoticePeriod(fromdate, todate);
					$('#noticePeriodModal').val(days);
				} else {
					setFromToDate();
					return
				}
			} else if (fd[1] < td[1]) {
				var days = calculateNoticePeriod(fromdate, todate);
				$('#noticePeriodModal').val(days);
			} else {
				setFromToDate();
				return
			}
		} else {
			setFromToDate();
			return
		}
	} else {
	}
}
function initiateExit() {
	type = $('#typeOfProceed').val();
	var details = {};
	var allValid = true;
	var deptId;
	if (type == "INITIATED") {
		var clrncDept = $('#multipleClearance').val();
		console.log("clr dept-->", clrncDept);
		var departments = clrncDept.join(",");
	
		var resignTypeSelectData = $("#resType").val();
      	var reasonForResignation = "";
      	if(resignTypeSelectData == "RESIGN"){
      		reasonForResignation = $("#resTypeAttritionResign").val();
      		
      		if (!validationUpdated("Attrition Reason Type Can't Be Blank", 'resTypeAttritionResign'))
                allValid = false;
      		
      	}
      	
      	if(resignTypeSelectData == "TERMINATE"){
      		reasonForResignation = $("#resTypeAttritionTerminate").val();
      		
      		if (!validationUpdated("Attrition Reason Type Can't Be Blank", 'resTypeAttritionTerminate'))
                allValid = false;
      	}

		if ($('#filterFromDateNew').val().trim() === "") {
			toastr.error("Actual release date can't be blank");
			allValid = false;
			return;
		}

		if ($('#remarks').val().trim() === "") {
			toastr.error("Remarks can't be blank");
			allValid = false;
			return;
		}

		if ($('#resType').val().trim() === "") {
			toastr.error("Resign type can't be blank");
			allValid = false;
			return;
		}

		if ($('#clearanceBefore').val().trim() === "") {
			toastr.error("Clearance deadline cannot be blank");
			allValid = false;
			return;
		}

		if (departments == null || departments == "") {
			toastr.error("Clearance departments cannot be blank");
			allValid = false;
			return;
		}

		var details;
		//details.employeeExit = $("#exitModalId").html();
		details.employeeExit =  $("#exitModalId1").text();
		details.releaseDate = $("#filterFromDateNew").val();
		details.noticePeriod = $("#noticePeriodModal").val();
		details.noticeRemarks = $("#remarks").val();
		details.resignType = $("#resType").val();
		details.reasonForResignationAttrition =reasonForResignation;
		details.type = type;
		details.deptId = departments;
		details.clearanceBefore = $("#clearanceBefore").val();
	} else {
		if (!validationUpdated("Remarks Can't Be Blank", 'remarks'))
			allValid = false;
		var details;
		//details.employeeExit = $("#exitModalId").html();
		details.employeeExit = $("#exitModalId1").text();
		details.noticeRemarks = $("#remarks").val();
		details.type = type;
	}
	if (allValid) {
		saveProceedDetails1(details);
	}
}
function saveProceedDetails1(data) {
	$('.loader').show();
	setTimeout(function() {
		$.ajax({
			type: "POST",
			url: "exit-management-proceed-notice",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				$('.loader').hide();
				if (response.code == "success") {
					$('#demo').hide();
					toastr.success(response.message);
					viewExitsAgGrig();
					$('#next2').show();
				} else {
					//	$('.loader').hide();
					toastr.error(response.message);
					/*$("#messageParagraph").text("Something Went Wrong")
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
				}
			},
			error: function(data) {
				//	$('.loader').hide();
			}
		})
	}, 1000)
}
// delete resign
function deleteResign() {
	$('#deletResignModal').modal('show');
}
function deleteOnClick() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var resignationDraftId = selectedData.map(node => node.employeeExit);
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you really want to delete this room? This process cannot be undone.',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			if (resignationDraftId) {
				$.ajax({
					type: "GET",
					url: "exit-management-delete?deleteId=" + resignationDraftId,
					async: false,
					success: function(response) {
						if (response.code == "success") {
							toastr.success("Data Deleted Successfully");
							/*$("#messageParagraph").text("Data Deleted Successfully");
							$("#msgOkModal").removeClass("btn btn-primary edit-btn");
							$("#msgOkModal").addClass("btn btn-primary edit-btn");
							$("#msgModal").modal('show');*/
							$("#exitModalId").text('');
							cancelModalBtn();
							viewExitsAgGrig();
						}
					},
					error: function(data) {
					}
				});
			}
		}
	});
}
function getInitateClearData() {

	console.log("intial function called");

	var selectedRows = gridOptions.api.getSelectedRows();
	var firstRow = selectedRows[0];
	$("#releaseDtModal").val(firstRow.releaseDate);
	$("#resignationDtModal").val(firstRow.resignDate);
	$("#employeeNameModal").val(firstRow.empName);
	$("#employeeIdModal").val(firstRow.empID);
	$("#filterFromDateNew").val("");
	$("#noticePeriodModal").val("");
	let noticePeriod = calculateNoticePeriod(firstRow.resignDate, firstRow.releaseDate);
	$("#expNoticePeriodModal").val(noticePeriod);
	$("#remarks").val("");
	$("#resType").val("");
	$("#clearanceBefore").val("");
	$('#multipleClearance').val("");
	var $chosenSelectTo = $("#multipleClearance");
	$chosenSelectTo.find('option').prop('disabled', false);
	$chosenSelectTo.val(null);
	$chosenSelectTo.trigger("chosen:updated");
	disableSelect('multipleClearance');
	let resignDateStr = firstRow.resignDate;
	let parts = resignDateStr.split('-');
	let actualDate = new Date(parts[2], parts[1] - 1, parts[0]);

	console.log("Parsed Date:", actualDate);
	var deptString = firstRow.depts;
	if (deptString != null) {
		getEmployeeDeptDetails(deptString);
	}

	let formattedDate =
		("0" + actualDate.getDate()).slice(-2) + "-" +
		("0" + (actualDate.getMonth() + 1)).slice(-2) + "-" +
		actualDate.getFullYear();

	$("#fromDateCalenderNew").datetimepicker('destroy');

	$("#fromDateCalenderNew").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		value: formattedDate,
		timepicker: false,
		minDate: actualDate
	});
	$("#clearanceBeforeCalender").datetimepicker('destroy');
	$("#clearanceBeforeCalender").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		value: formattedDate,
		timepicker: false,
		minDate: actualDate
	});
}


function fixClearenceDate() {
	let resignDateStr = $("#resignationDtModal").val(); // Resignation Date
	let releaseDateStr = $("#filterFromDateNew").val(); // Release Date

	let resignParts = resignDateStr.split('-');
	let resignDate = new Date(resignParts[2], resignParts[1] - 1, resignParts[0]);

	let releaseParts = releaseDateStr.split('-');
	let releaseDate = new Date(releaseParts[2], releaseParts[1] - 1, releaseParts[0]);


	let formattedDate =
		("0" + resignDate.getDate()).slice(-2) + "-" +
		("0" + (resignDate.getMonth() + 1)).slice(-2) + "-" +
		resignDate.getFullYear();

	$("#clearanceBeforeCalender").datetimepicker('destroy');
	$("#clearanceBeforeCalender").datetimepicker({
		format: 'd-m-Y',
		closeOnDateSelect: true,
		value: formattedDate,
		timepicker: false,
		minDate: resignDate
	});
}

function getClearanceData(exitId) {
	var selectedRows = gridOptions.api.getSelectedRows();
	var firstRow = selectedRows[0];
	console.log("row--->", firstRow);
	$("#releaseDtModal").val(firstRow.releaseDate);
	$("#resignationDtModal").val(firstRow.resignDate);
	$("#employeeNameModal").val(firstRow.empName);
	$("#employeeIdModal").val(firstRow.empID);
	$("#filterFromDateNew").val(firstRow.actualReleaseDate);
	$("#noticePeriodModal").val(firstRow.noticePeriod);
	let noticePeriod = calculateNoticePeriod(firstRow.resignDate, firstRow.releaseDate);
	$("#expNoticePeriodModal").val(noticePeriod);
	$("#remarks").val(firstRow.initateRemark);
	$("#resType").val(firstRow.resnType);
	var resignTypeData = firstRow.resnType;
	
	if(resignTypeData == "RESIGN"){
		$("#attritionReasonTypeResign").show();
		$("#attritionReasonTypeTerminate").hide();
		$("#resTypeAttritionResign").val(firstRow.resnAttrType);
	}
	
	if(resignTypeData == "TERMINATE"){
		$("#attritionReasonTypeTerminate").show();
		$("#attritionReasonTypeResign").hide();
		$("#resTypeAttritionTerminate").val(firstRow.resnAttrType);
	}
	
	$("#clearanceBefore").val(firstRow.creaBeforeDt);
	var depts = firstRow.depts;
	depts = depts.split(',');
	disableOptions('multipleClearance', depts)
	
	console.log("deptss-->", depts);	
	$('#multipleClearance').val(depts);
	$('#multipleClearance').trigger("chosen:updated");
	var deptString = firstRow.depts;
	if (deptString != null) {
		getEmployeeDeptDetails(deptString);
	}
	$.ajax({
		type: 'GET',
		url: 'exit-management-get-clearanceStatus?exitId=' + exitId,
		contentType: false,
		async: false,
		success: function(response) {
			if (response.code == "success") {
				if (response.body[0] != null) {
					var jsonData = JSON.parse(response.body);
					console.log("jsonData==", jsonData)
					gridOptionsClearance.api.setRowData(jsonData);
				} else {
					$('#clearanceGrid').html(`
                		<div class="no-clearance-message">
                			<i class="bi bi-exclamation-triangle"></i>
                				No Clearance Department Available
                		</div>
                	`);
				}
				$('#empIdCl').text(firstRow.empID);
				$('#empNameCl').text(firstRow.empName);
				$('#clearanceIdModal').text(firstRow.employeeExit)
			}
		},
		error: function(e) {
		}
	});




}
function cancelClearanceEmp1() {
	$("#clearanceForm, #clearanceFormMain").hide();
	$("#empNameList1").val("");
	$("#empIdAuto1").val("");
	$("#suggesstion-box_1").hide();
}
function addClearanceEmp(data) {
	let allData = JSON.parse(decodeURIComponent(window.atob(data)));
	$("#clearanceIdClM").val(allData.clearanceId);
	$("#clearanceForm").show();
	$("#empNameList1").val("");
	$("#empIdAuto1").val("");
	$("#suggesstion-box_1, #clearanceFormMain").hide();
}
function clearResg() {
	$("#clearanceFormMain").show();
	$("#clearanceRemarks").val("");
	$("#recoveryAmount").val("");
	$("#clearanceForm").hide();
}
function saveClearanceEmp1() {
	var empId = $("#empIdAuto1").val();
	var clearanceId = $("#clearanceIdClM").val();
	if (empId == "") {
		toastr.error("Employee name cannot be blank.");
		return;
	}
	$.ajax({
		type: 'GET',
		url: 'exit-management-update-clearanceBy?empId=' + empId + "&clearanceId=" + clearanceId,
		contentType: false,
		async: false,
		success: function(response) {
			if (response.code == "success") {
				toastr.success(response.message);
				cancelClearanceEmp1();
				viewExitsAgGrig();
			}
		},
		error: function(e) {
		}
	});
}
function validClearanceDate() {
	var actualReleaseDt = $("#filterFromDateNew").val(); // Release Date
	var resignationDt = $("#resignationDtModal").val();  // Resignation Date
	var clearanceBefore = $("#clearanceBefore").val();   // Clearance Date
	if (actualReleaseDt && clearanceBefore && resignationDt) {
		var fd = actualReleaseDt.split("-").map(Number);   // Actual Release Date
		var td = clearanceBefore.split("-").map(Number);   // Clearance Before Date
		var rd = resignationDt.split("-").map(Number);     // Resignation Date
		var actualReleaseTime = new Date(fd[2], fd[1] - 1, fd[0]).getTime();
		var clearanceBeforeTime = new Date(td[2], td[1] - 1, td[0]).getTime();
		var resignationTime = new Date(rd[2], rd[1] - 1, rd[0]).getTime();
		if (resignationTime > actualReleaseTime) {
			//showErrorMessage("Resignation Date should be less than or equal to Actual Release Date.");
			setFromToDateClearance();
			return;
		}
		if (clearanceBeforeTime < resignationTime) {
			//showErrorMessage("Clearance Date should be greater than or equal to Resignation Date.");
			setFromToDateClearance();
			return;
		}
		if (clearanceBeforeTime > actualReleaseTime) {
			//showErrorMessage("Clearance Date should be less than or equal to Actual Release Date.");
			setFromToDateClearance();
			return;
		}
	} else {
		//showErrorMessage("Please Fill In All Required Dates.");
		$("#clearanceBefore").val("");
		return;
	}
}
function setFromToDateClearance() {
	$("#clearanceBefore").val("");
}
function showErrorMessage(message) {
	toastr.success(message);
	/*$("#messageParagraph").text(message);
	$("#msgOkModal").removeClass("btn btn-primary edit-btn");
	$("#msgOkModal").addClass("btn btn-primary edit-btn");
	$("#msgModal").modal('show');*/
	$("#clearanceBefore").val("");  // Clear the invalid clearance date input
}
function getMonthFirstAndLastDate(releaseDate) {
	var [day, month, year] = releaseDate.split('-').map(Number);
	var date = new Date(year, month - 1, day); // month is 0-indexed in JavaScript
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	function formatDate(date) {
		var yyyy = date.getFullYear();
		var mm = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
		var dd = String(date.getDate()).padStart(2, '0');
		return `${yyyy}-${mm}-${dd}`;
	}
	return {
		firstDate: formatDate(firstDay),
		lastDate: formatDate(lastDay)
	};
}
function finalSettlementModal() {
	var selectedData = '';
	var selectedRows = gridOptions.api.getSelectedRows();
	var employeeId = selectedRows[0].empID;
	var releaseDt = selectedRows[0].actualReleaseDate;
	if (releaseDt) {
		var allDate = getMonthFirstAndLastDate(releaseDt);
		$.ajax({
			type: 'GET',
			url: 'exit-management-final-settlement?employeeId=' + employeeId + "&fromDate=" + allDate.firstDate + "&toDate="
				+ allDate.lastDate + "&releaseDt=" + releaseDt,
			contentType: false,
			async: false,
			success: function(response) {
				if (response.code == "success") {
					if (response.body[0] != null) {
						var jsonData = JSON.parse(response.body);
						selectedData = jsonData;
					} else {
					}
					console.log("selectedData final===", selectedData)
					$("#exitIdFinancial").val(selectedData.employeeExit);
					$("#empIdFS").val(selectedData.empID);
					$("#empNameFS").val(selectedData.empName);
					$("#empDesignationFS").val(selectedData.designation);
					$("#empDeptFS").val(selectedData.deptName);
					$("#dojFS").val(selectedData.joiningDate);
					$("#dorFS").val(selectedData.resignDate);
					$("#dolFS").val(selectedData.releaseDate);
					$("#exitIdFinancial").val(selectedData.exitIdF);
					$("#financialId").val(selectedData.settlementID);
					$("#basicAmount").val(parseFloat(selectedData.basic).toFixed(2));
					$("#hraAmount").val(parseFloat(selectedData.hra).toFixed(2));
					$("#conAllowanceAmount").val(parseFloat(selectedData.coAllowance).toFixed(2));
					$("#washAllowanceAmount").val(parseFloat(selectedData.washAllowance).toFixed(2));
					$("#epfAmount").val(parseFloat(selectedData.epfAmount).toFixed(2));
					$("#esicAmount").val(parseFloat(selectedData.esicAmount).toFixed(2));
					$("#professionalTax").val(parseFloat(selectedData.professionalTax).toFixed(2));
					$("#salaryAdvance").val(parseFloat(selectedData.salaryAdvance).toFixed(2));
					$("#bonusAmount").val(parseFloat(selectedData.bonus).toFixed(2));
					var totalEarning = parseFloat(parseFloat(selectedData.basic) + parseFloat(selectedData.hra) +
						parseFloat(selectedData.coAllowance) + parseFloat(selectedData.washAllowance)).toFixed(2);
					var totalDeduction = parseFloat(parseFloat(selectedData.epfAmount) + parseFloat(selectedData.esicAmount) +
						parseFloat(selectedData.professionalTax) + parseFloat(selectedData.salaryAdvance)).toFixed(2);
					var netAmount = parseFloat(totalEarning - totalDeduction).toFixed(2);
					$("#totalEarning").val(totalEarning);
					$("#totalDeduction").val(totalDeduction);
					$("#netAmount").val(netAmount);
					$("#inactiveBtn").attr('disabled', true);
					calculateNetResult();
					calculateTotalAmountPayable();
				}
			},
			error: function(e) {
			}
		});
	}
}
function openFinalSettlementView(id) {
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedData = selectedRows[0];
	$.ajax({
		type: 'GET',
		url: 'exit-management-final-settlement-ByID?settlementId=' + id,
		contentType: false,
		async: false,
		success: function(response) {
			console.log("response finallll=====", response)
			if (response.code == "success") {
				if (response.body[0] != null) {
					var jsonData = JSON.parse(response.body);
					var data = jsonData[0];
					//Disabled Seaction
					$("#exitIdFinancial").val(selectedData.employeeExit);
					$("#empIdFS").val(selectedData.empID);
					$("#empNameFS").val(selectedData.empName);
					$("#empDesignationFS").val(selectedData.designation);
					$("#empDeptFS").val(selectedData.deptName);
					$("#dojFS").val(selectedData.empJoiningDate);
					$("#dorFS").val(selectedData.resignDate);
					$("#dolFS").val(selectedData.releaseDate);
					$("#financialId").val(data.settlementID);
					$("#basicAmount").val(data.basic);
					$("#hraAmount").val(data.hra);
					$("#conAllowanceAmount").val(data.coAllowance);
					$("#washAllowanceAmount").val(data.washAllowance);
					$("#totalEarning").val(data.totalEarning);
					$("#epfAmount").val(data.epfAmount);
					$("#esicAmount").val(data.esicAmount);
					$("#professionalTax").val(data.professionalTax);
					$("#salaryAdvance").val(data.salaryAdvance);
					$("#totalDeduction").val(data.totalDeduction);
					$("#netAmount").val(data.netAmount);
					$("#bonusAmount").val(data.bonus);
					$("#leaveAmount").val(data.leaveAmount);
					$("#otherAmount").val(data.otherAmount);
					$("#totalAmount").val(data.totalAmount);
					/*calculateNetResult();
					calculateTotalAmountPayable();*/
				} else {
				}
			}
		},
		error: function(e) {
		}
	});
}
function checkNumeric(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9.]/g, '');
	if (tempVal.split('.').length > 2 || (tempVal.indexOf('.') != -1 && tempVal.split('.')[1].length > 2)) {
		tempVal = tempVal.substring(0, tempVal.length - 1);
	}
	const input = document.getElementById(fieldId);
	const position = input.selectionStart;
	$("#" + fieldId).val(tempVal);
	input.setSelectionRange(position, position);

}
function calculateTotalEarning() {
	let basicAmount = parseFloat($("#basicAmount").val()) || 0;
	let hraAmount = parseFloat($("#hraAmount").val()) || 0;
	let conAllowanceAmount = parseFloat($("#conAllowanceAmount").val()) || 0;
	let washAllowanceAmount = parseFloat($("#washAllowanceAmount").val()) || 0;
	let totalEarning = basicAmount + hraAmount + conAllowanceAmount + washAllowanceAmount;
	$("#totalEarning").val(parseFloat(totalEarning).toFixed(2) || 0);
}
function calculateTotalDeduction() {
	let epfAmount = parseFloat($("#epfAmount").val()) || 0;
	let esicAmount = parseFloat($("#esicAmount").val()) || 0;
	let professionalTax = parseFloat($("#professionalTax").val()) || 0;
	let salaryAdvance = parseFloat($("#salaryAdvance").val()) || 0;
	let totalDeduction = epfAmount + esicAmount + professionalTax + salaryAdvance;
	$("#totalDeduction").val(parseFloat(totalDeduction).toFixed(2) || 0);
}
function calculateNetResult() {
	calculateTotalEarning();
	calculateTotalDeduction();
	let totalEarning = parseFloat($("#totalEarning").val()) || 0;
	let totalDeduction = parseFloat($("#totalDeduction").val()) || 0;
	let netResult = totalEarning - totalDeduction;
	$("#netAmount").val(parseFloat(netResult).toFixed(2) || 0);
}
function calculateTotalAmountPayable() {
	let netAmount = parseFloat($("#netAmount").val()) || 0;
	let bonusAmount = parseFloat($("#bonusAmount").val()) || 0;
	let leaveAmount = parseFloat($("#leaveAmount").val()) || 0;
	let otherAmount = parseFloat($("#otherAmount").val()) || 0;
	let totalAmountPayable = netAmount + bonusAmount + leaveAmount + otherAmount;
	$("#totalAmount").val(parseFloat(totalAmountPayable).toFixed(2) || 0);
}
function saveFinal(status) {
	var details = {};
	var allValid = true;
	details.settlementId = $("#financialId").val();
	details.exitId = $("#exitIdFinancial").val();
	details.basicAmount = $("#basicAmount").val();
	details.hraAmount = $("#hraAmount").val();
	details.conAllowanceAmount = $("#conAllowanceAmount").val();
	details.washAllowanceAmount = $("#washAllowanceAmount").val();
	details.totalEarning = $("#totalEarning").val();
	details.epfAmount = $("#epfAmount").val();
	details.esicAmount = $("#esicAmount").val();
	details.professionalTax = $("#professionalTax").val();
	details.salaryAdvance = $("#salaryAdvance").val();
	details.totalDeduction = $("#totalDeduction").val();
	details.netAmount = $("#netAmount").val();
	details.bonusAmount = $("#bonusAmount").val();
	details.leaveAmount = $("#leaveAmount").val();
	details.otherAmount = $("#otherAmount").val();
	details.totalAmount = $("#totalAmount").val();
	details.financeStatus = status;
	console.log("details===", details)
	if (allValid) {
		SaveFinancialSettlement(details, status)
	}
}
function SaveFinancialSettlement(data, status) {
	//	$('.loader').show();
	setTimeout(function() {
		$.ajax({
			type: "POST",
			url: "exit-management-add-final-settlement",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response.code == "success") {
					toastr.success(response.message);
					
					if (status == 'CLEARED') {
						cancelFinal();
					}
					viewExitsAgGrig()
					$("#saveFinal").hide();
				} else {
					//	$('.loader').hide();
					toastr.error("Something Went Wrong");
				}
			},
			error: function(data) {
				//	$('.loader').hide();
			}
		})
	}, 1000)
}
function inactiveEmployee() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var employeeId = selectedRows[0].empID;
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you really want to inactive the employee? This process cannot be undone.',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, inactive',
		cancelButtonText: 'Cancel',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			$.ajax({
				type: 'GET',
				url: 'exit-management-inactive-employee?empId=' + employeeId,
				contentType: false,
				async: false,
				success: function(response) {
					if (response.message == "Success") {
						toastr.success("You have successfully inactive the employee -" + employeeId);
						$("#rejectBtn").attr('disabled', true);
						$("#fSettlement-btn").attr('disabled', true);
						$("#proceedBtn").attr('disabled', true);
						$("#inactiveBtn").attr('disabled', true);
						viewExitsAgGrig();

					}
				},
				error: function(e) {
					console.log("error");
				}
			});
		}
		else {
			console.log("No")
		}
	});




}
function travelPrev(tab) {
	if (tab == 1) {
		$("#resignTab").addClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").addClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
	} else if (tab == 2) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").addClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").addClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
	} else if (tab == 3) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").addClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").addClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
	} else if (tab == 4) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").addClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").addClass('active');
		$("#downloadTab").removeClass('active');
	} else {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").addClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").addClass('active');
	}
}
function travelNext(tab) {
	if (tab == 1) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").addClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").addClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
		if(type == 'self-service') {
			$("#initiateExit").hide()
		}
		   
	} else if (tab == 2) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").addClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").addClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
		
			if(type == 'self-service') {
			$("#clearResg").hide()
		}
		
	} else if (tab == 3) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").addClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").addClass('active');
		$("#downloadTab").removeClass('active');
		
			if(type == 'self-service') {
			$("#saveFinal,#saveFinal1").hide()
			}else{
				$("#saveFinal1").hide()
			}
		
		
	} else if (tab == 4) {
		$("#resignTab").removeClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").addClass('active');

		$("#resignInfoTab").removeClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").addClass('active');
	} else {
		$("#resignTab").addClass('active');
		$("#initiateBtn").removeClass('active');
		$("#clearanceBtn").removeClass('active');
		$("#finalBtn").removeClass('active');
		$("#downloadBtn").removeClass('active');

		$("#resignInfoTab").addClass('active');
		$("#initiateTab").removeClass('active');
		$("#clearanceTab").removeClass('active');
		$("#finalSettleTab").removeClass('active');
		$("#downloadTab").removeClass('active');
	}
}
function showFormatNoDue(type) {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var exitId = selectedData.map(node => node.employeeExit);
	if (type == "SHOW") {
		window.open("/employee/exit-management-no-due-certificate?exitId=" +
			window.btoa(exitId) + '&type=' + window.btoa(type), '_blank');
	} else {
		window.open("/employee/exit-management-no-due-certificate?exitId=" +
			window.btoa(exitId) + '&type=' + window.btoa(type));
	}
	event.preventDefault();
}
function openFinalSettlementPdf() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var settlementId = selectedData.map(node => node.settlementId);
	var type = "SHOW";
	window.open("/employee/exit-management-finalSettlement-pdf?settlementId="
		+ window.btoa(settlementId) + '&type=' + window.btoa(type), '_blank');
}
function showFormatFinalSet(settlementId, type) {
	window.open("/employee/exit-management-finalSettlement-pdf?settlementId="
		+ window.btoa(settlementId) + '&type=' + window.btoa(type), '_blank');
	event.preventDefault();
}
function showFormat(exitId, type) {
	window.open("/employee/exit-management-experince-letter?exitId=" +
		window.btoa(exitId) + '&type=' + window.btoa(type), '_blank');
	event.preventDefault();
}
function openFinalModal() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var allData = selectedRows[0];
	$("#expLetterBtn").html('');
	$("#expLetDownloadBtn").html('');
	$("#noDueBtn").html('');
	$("#noDueDownloadBtn").html('');
	$("#finalSetDownloadBtn").html('');
	$("#finalSetBtn").html('');
	$("#finalLetterModal").modal('show');
	console.log("allData==", allData)
	$("#expLetterBtn").append(`<button class="btn go-btn" onclick="showFormat('${allData.employeeExit}', 'SHOW');" style="display:block">View</button>`);
	$("#expLetDownloadBtn").append(`<button class="btn go-btn" onclick="showFormat('${allData.employeeExit}' , 'DOWNLOAD');" style="display:block">Download</button>`);
	$("#noDueBtn").append(`<button class="btn go-btn" onclick="showFormatNoDue('SHOW');" style="display:block">View</button>`);
	$("#noDueDownloadBtn").append(`<button class="btn go-btn" onclick="showFormatNoDue('DOWNLOAD');" style="display:block">Download</button>`);
	$("#finalSetBtn").append(`<button class="btn go-btn" onclick="showFormatFinalSet('${allData.settlementId}', 'SHOW');" style="display:block">View</button>`);
	$("#finalSetDownloadBtn").append(`<button class="btn go-btn" onclick="showFormatFinalSet('${allData.settlementId}' , 'DOWNLOAD');" style="display:block">Download</button>`);

	$("#exitIdF").text(allData.employeeExit);
	$("#empNameF").text(allData.empName);
	$("#empIdF").text(allData.empID);
	var releaseDate = allData.releaseDate;
	releseDateGolb = allData.releaseDate;
	empIDGolb = allData.empID;

	var dateRange = calculateDateRange(releaseDate);
	viewPlaySlipDetails(allData.empID, dateRange);
}
function calculateDateRange(releaseDateStr) {
	var parts = releaseDateStr.split('-');
	var day = parseInt(parts[0], 10);
	var month = parseInt(parts[1], 10) - 1;
	var year = parseInt(parts[2], 10);
	var releaseDate = new Date(year, month, day);
	// Set to the first day of the release month to avoid issues with non-existing dates
	var startDate = new Date(releaseDate);
	startDate.setDate(1);
	startDate.setMonth(startDate.getMonth() - 4);
	var fromDate = getFirstDayOfMonth(startDate);
	var fromDateStr = ('0' + fromDate.getDate()).slice(-2) + '-' + ('0' + (fromDate.getMonth() + 1)).slice(-2) + '-' + fromDate.getFullYear();
	var toDateStr = ('0' + releaseDate.getDate()).slice(-2) + '-' + ('0' + (releaseDate.getMonth() + 1)).slice(-2) + '-' + releaseDate.getFullYear();
	return {
		fromDate: fromDateStr,
		toDate: toDateStr
	};
}
function getFirstDayOfMonth(date) {
	return new Date(date.getFullYear(), date.getMonth(), 1);
}
function viewPlaySlipDetails(empId, dateRange) {
	//	$('.loader').show();
	var fromDate = dateRange.fromDate;
	var toDate = dateRange.toDate;
	console.log("exit-management-payslip-listing?empId=" + empId + "&fromDate=" + fromDate
		+ "&toDate=" + toDate);
	agGrid.simpleHttpRequest({
		url: "exit-management-payslip-listing?empId=" + empId + "&fromDate=" + fromDate
			+ "&toDate=" + toDate,
	}).then(function(data) {
		//	$('.loader').hide();
		var jsonData = JSON.parse(data.body);
		var len = data.length;
		if (len > 0) {
			gridOptionsPaySlip.api.setRowData(jsonData);
		} else {
			gridOptionsPaySlip.api.setRowData("");
		}
	});
}
function payslipPdfDownload(empId, fromDate, toDate) {
	if (empId.slice(0, 1) != '(') {
		var emplist = '("' + empId + '")';
	} else {
		var emplist = empId;
	}
	if (toDate.slice(0, 1) != '(') {
		let parts = toDate.split("-");
		let formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];
		toDate = '("' + formattedDate + '")';
	}
	window.open("/employee/exit-management-payslip-pdf-download?fromDate="
		+ window.btoa(fromDate) + "&toDate=" + window.btoa(toDate)
		+ "&empId=" + window.btoa(emplist), '_blank');
}
function downloadPayslipListCSV() {
	var dateRange = calculateDateRange(releseDateGolb);
	var selectedRows = gridOptionsPaySlip.api.getSelectedRows();
	var emplist = "";
	gridOptionsPaySlip.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		emplist += '"' + rowNode.data.empId + '",';
	});
	var dateList = "";
	selectedRows.forEach(function(rowNode) {
		let parts = rowNode.toDate.split("-");
		let formattedDate = parts[2] + "-" + parts[1] + "-" + parts[0];
		dateList += '"' + formattedDate + '",';
	});
	dateList = '(' + dateList.substring(0, dateList.length - 1) + ')';
	emplist = '(' + emplist.substring(0, emplist.length - 1) + ')';
	payslipPdfDownload(emplist, dateRange.fromDate, dateList);
}

/*function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
}*/

function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	getMostClosestRow();
}

function getMostClosestRow() {
	let searchValue = document.getElementById('quickFilter').value;
	gridOptions.api.setQuickFilter(searchValue);

	let rowCount = gridOptions.api.getModel().getRowCount();

	gridOptions.api.forEachNodeAfterFilter((node, index) => {
		if (index === 0) {
			node.setSelected(true);
			gridOptions.api.ensureIndexVisible(node.rowIndex);
		}
	});
}
function resetBtn() {
	$("#quickFilter").val('');
	gridOptions.api.setQuickFilter('');
	gridOptions.api.refreshCells({ force: true });
	setTimeout(() => {
		gridOptions.api.forEachNode((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				gridOptions.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}, 50);
}

function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		onQuickFilterChanged()
	}
}
function cancelBar() {
	//	var id = document.getElementById("closeKey");
	//	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
function addProceedClearance1() {
	var details = {};
	var allValid = true;
	/*if (!validationUpdated("Remarks Can't Be Blank", 'clearanceRemarks'))
		allValid = false;*/
	var details;

	var clearanceId = $("#exitModalId4").html();
	var remarks = $("#clearanceRemarks").val();
	var recoveryAmount = $("#recoveryAmount").val();


	if (remarks == "") {
		toastr.error("Remarks can not be blank");
		return;
	}

	if (allValid) {
		saveProceedDetails(clearanceId, remarks, recoveryAmount)
	}
}

function saveProceedDetails(clearanceId, remarks, recoveryAmount) {
	$('.loader').show();
	$.ajax({
		type: 'GET',
		url: 'exit-management-clearance-update?id=' + clearanceId + "&remarks=" + remarks + "&recoveryAmount=" + recoveryAmount,
		contentType: false,
		async: false,
		success: function(response) {

			if (response.code == "success") {
				$('.loader').hide();
				$('#demo').hide();
				toastr.success(response.message);
				/*$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn btn-primary edit-btn");
				$("#msgOkModal").addClass("btn btn-primary edit-btn");
				$("#msgModal").modal('show');*/
				cancelClearanceEmp1();
				viewExitsAgGrig();

			} else {
				$('.loader').hide();
				toastr.error("Something Went Wrong");
				/*$("#messageParagraph").text("Something Went Wrong");
				$("#msgOkModal").removeClass("btn btn-primary edit-btn");
				$("#msgOkModal").addClass("btn btn-primary edit-btn");
				$("#msgModal").modal('show');*/
				cancelClearanceEmp1();
				viewExitsAgGrig();
			}
		},
		error: function(e) {
			console.log("error");
		}
	});
}


function getFileExtension(filename) {
	return filename.split('.').pop().toLowerCase();
}

function getFileIcon(extension) {
	switch (extension) {
		case "jpg":
		case "jpeg":
		case "png":
		case "gif":
			return "bi bi-file-earmark-image"; // Image icon
		case "pdf":
			return "bi bi-file-earmark-pdf"; // PDF icon
		case "doc":
		case "docx":
			return "bi bi-file-earmark-word"; // Word icon
		case "xls":
		case "xlsx":
			return "bi bi-file-earmark-excel"; // Excel icon
		case "ppt":
		case "pptx":
			return "bi bi-file-earmark-ppt"; // PowerPoint icon
		case "txt":
			return "bi bi-file-earmark-text"; // Text file icon
		case "zip":
		case "rar":
			return "bi bi-file-earmark-zip"; // Compressed file icon
		default:
			return "bi bi-file-earmark"; // Default file icon
	}
}


function getEmployeeDeptDetails(deptList) {
	var deptmentList = [];
	$.ajax({
		type: "GET",
		url: "exit-management-emp-dept",
		async: false,
		success: function(response) {
			console.log("response-->", response);
			deptmentList = response.body;
		}
	});
	var dropdown = $("#multipleClearance");
	dropdown.empty();
	$.each(deptmentList, function(index, item) {
		dropdown.append($('<option>', {
			value: item.key,
			text: item.name
		}));
	});
	dropdown.trigger("chosen:updated");

	var firstRow = { depts: deptList };
	var deptString = firstRow.depts;
	var selectedDepts = deptString.split(", ");
	console.log("Selected Depts -->", selectedDepts);
	dropdown.val(selectedDepts);
	dropdown.trigger("chosen:updated");
}