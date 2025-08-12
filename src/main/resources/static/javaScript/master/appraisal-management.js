// view functions
function fetchData() {
	$(".loader").show();
	const promise1 = agGrid.simpleHttpRequest({
		url: 'appraisal-management-view'
	}).then(function(data) {
		const responseData = JSON.parse(data.body);
		if (!responseData || !responseData.appraisalData || responseData.appraisalData.length === 0) {
			gridOptions.api.setRowData([]);
			return;
		}
		var length = responseData.appraisalData.length;
		$("#totalReq").find('span').html(length);
		gridOptions.api.setRowData(responseData.appraisalData);
		var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	});
	Promise.all([promise1]).then(function() {
		$(".loader").hide();
	})
		.catch(function(error) {
			$(".loader").hide();
		});
}
function keyFactorDetails() {
	$.ajax({
		type: "GET",
		url: "appraisal-management-data",
		async: false,
		success: function(response) {
			var responsedata = JSON.parse(response.body);
			gridOptions2.api.setRowData(responsedata.grid1);
		}
	});
	var totalRow = [{
		factors: 'Total',
		maxmark: "100",
		assementMark: "",
		editable: false
	}];
	gridOptions2.api.setPinnedBottomRowData(totalRow);

}
// edit keyfactor function 
function editAppraisal(id) {
	$("#categoryId").val(id);
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "appraisal-management-edit?id=" + id,
		async: false,
		success: function(response) {
			if (response.code == "200") {
				$("#demo").show();
				$(".panel-body").hide();
				var responsedata = JSON.parse(response.body);
				console.log("editable data====", responsedata)
				var appraisalReponseData = responsedata.appraisalEditData;
				$("#factorCategory").val(appraisalReponseData[0].categoryName);
				$("#fromDate").val(appraisalReponseData[0].fromDate);
				$("#toDate").val(appraisalReponseData[0].toDate);
				$("#attndyear").val(appraisalReponseData[0].fincialyear);
				$("#categoryidPara").show();
				$("#factorId1").text(appraisalReponseData[0].categoryid);
				$("#factorId2").text(appraisalReponseData[0].categoryid);
				if (appraisalReponseData[0].AssignStatus === "1") {
					$("#save").attr("disabled", true);
					$("#newFactor").attr("disabled", true);
				} else {
					$("#save").attr("disabled", false);
					$("#newFactor").attr("disabled", false);
				}
				var gridData = appraisalReponseData[0].grid1;
				let assesementMark = 0;
				let totalmark = 0
				gridData.forEach((item) => {
					item.factors = item.factors.replace(/=#/g, "'").replace(/=\$/g, '"');
					assesementMark += parseInt(item.assementMark || 0, 10);
					totalmark += parseInt(item.maxmark || 0, 10);
				})
				gridOptions2.api.setRowData(gridData);
				var totalRow = [{
					factors: 'Total',
					maxmark: totalmark,
					assementMark: assesementMark,
					editable: false
				}];
				gridOptions2.api.setPinnedBottomRowData(totalRow);
				$('.loader').hide();
			}
		}
	});
}
// button functions
function cancelKeyFact() {
	$(".formValidation").remove();
	$("#cancelKeyFact, #saveKeyFact").hide();
	$("#newKeyFact, #deleteKeyFact, #editKeyFact, #approveKeyFact, #next1, #assignBtn, #selfAppBtn").show();
	$("#factorCategory, #attndyear, #newFactor, #deleteFactor").prop('disabled', true);
}
function editKeyFact() {
	$(".formValidation").remove();
	$("#cancelKeyFact, #saveKeyFact").show();
	$("#newKeyFact, #deleteKeyFact, #editKeyFact, #approveKeyFact, #next1, #assignBtn, #selfAppBtn").hide();
	$("#factorCategory, #attndyear, #newFactor").prop('disabled', false);
}
function newKeyFact() {
	var curDate = new Date();
	var curYear = curDate.getFullYear();
	var financialYearStart = curYear;
	var financialYearEnd = curYear + 1;
	if (curDate.getMonth() < 3) {
		financialYearStart -= 1;
		financialYearEnd -= 1;
	}
	var financialYear = financialYearStart + '-' + financialYearEnd;
	$("#attndyear").val(financialYear);
	changeMonthYear();
	$("#factorCategory").val("");
	$("#categoryId").val("");
	$("#factorId1").text("");
	keyFactorDetails();

}
function cancelFactor1() {
	$(".formValidation").remove();
	$("#factorDetailForm").hide();
	$("#childGridKey").show();
	$("#newFactor").prop('disabled', false);
	$("#factorName").val('');
	$("#maxmark").val('');
}
function editFactor() {
	$("#categoryid").val('');
	$("#factorName").val('');
	$("#maxmark").val('');
	$(".formValidation").remove();
	$("#childGridKey").hide();
	$("#factorDetailForm").show();
	$("#newFactor").prop('disabled', true);
}
// save data key factor child
function saveFactor1() {
	let allRowData = [];
	gridOptions2.api.forEachNode((node) => {
		allRowData.push(node.data);
	});

	console.log("factors-->", $("#factorName").val());
	console.log("max mark-->", $("#maxmark").val());

	if ($("#factorName").val() == null || $("#factorName").val() == "") {
		toastr.error('Factory  Name Required.');
		return;
	}
	if ($("#maxmark").val() == null || $("#maxmark").val() == "") {
		toastr.error('Max Mark Required');
		return;
	}
	const newSlno = allRowData.length + 1;
	const newRow = {
		slno: newSlno,
		factors: $("#factorName").val(),
		maxmark: $("#maxmark").val()
	};
	allRowData.push(newRow);
	let totalMark = 0;
	allRowData.forEach((row, index) => {
		totalMark += parseInt(row.maxmark);
	});
	var totalRow = [{
		factors: 'Total',
		maxmark: totalMark,
		editable: false
	}];
	gridOptions2.api.setPinnedBottomRowData(totalRow);
	gridOptions2.api.setRowData(allRowData);
	toastr.success('Factor Create Successfully');

	cancelFactor1();
}
function fetchAppraisalData(callback) {
	agGrid.simpleHttpRequest({
		url: 'appraisal-management-view'
	}).then(function(data) {
		var responseData = JSON.parse(data.body);
		callback(responseData);
	});
}
// save data key factor main
function saveKeyFact() {
	gridOptions2.api.stopEditing(); // stop editing when the save button is click
	var obj = {};
	var allValid = true;
	var datas = [];
	obj.factorCategory = $("#factorCategory").val();
	obj.fromDate = $("#fromDate").val();
	obj.toDate = $("#toDate").val();
	obj.categoryId = $("#categoryId").val();
	obj.financialyear = $("#attndyear").val();
	if (obj.factorCategory == null || obj.factorCategory == "") {
		//allValid = validationUpdated("Factor Category Required", "factorCategory");
		toastr.error("Factor Category Required");
		return;
	}
	/*if (obj.fromDate == null || obj.fromDate == "") {
		allValid = validationUpdated("From Date Required", "fromDate");
	}
	if (obj.toDate == null || obj.toDate == "") {
		allValid = validationUpdated("To Date Required", "toDate");
	}*/
	var dataOfGrid1 = [];
	gridOptions2.api.forEachNode(function(rowNode, index) {
		dataOfGrid1.push(rowNode.data);
	});
	dataOfGrid1.forEach(item => {
		item.factors = item.factors.replace(/'/g, '=#').replace(/"/g, '=$');
	});
	obj.grid1List = dataOfGrid1;
	if (allValid) {
		datas.push(obj);
		console.log("datas addedd====", datas)
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "appraisal-management-add",
			async: true,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "201") {
					fetchAppraisalData(function(responseData) {
						gridOptions.api.setRowData(responseData.appraisalData);
						var length = responseData.appraisalData.length;
						toastr.success("Appraisal Factor Create Successfully");
						/*$("#messageParagraph").text("Appraisal Factor Create Successfully");
						$("#msgOkModal").removeClass("btn btn-primary edit-btn");
						$("#msgOkModal").addClass("btn btn-primary edit-btn");
						$("#msgModal").modal('show');*/
						fetchData();
						cancelKeyFact();
						$('.loader').hide();
					});
				} else if (response.code == "200") {
					fetchAppraisalData(function(responseData) {
						gridOptions.api.setRowData(responseData.appraisalData);
						var length = responseData.appraisalData.length;
						toastr.success("Appraisal Factor  modified  Successfully");
						/*$("#messageParagraph").text("Appraisal Factor  modified  Successfully");
						$("#msgOkModal").removeClass("btn btn-primary edit-btn");
						$("#msgOkModal").addClass("btn btn-primary edit-btn");
						$("#msgModal").modal('show');*/
						fetchData();
						cancelKeyFact();
						$('.loader').hide();
					});
				} else if (response.code == "1062" || message == "Duplicate entry 'x' for  'tbl_appraisalKeyFactor_master.unique__factor'") {
					toastr.error("You cannot create multiple appraisal factors with the same factor name in a single year.");
					/*$("#messageParagraph").text("You cannot create multiple appraisal factors with the same factor name in a single year.");
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
					$('.loader').hide();
					fetchData();
					cancelKeyFact();
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}
}
function approveKeyFact() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var categoryId = selectedRows[0].categoryid;
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "appraisal-management-approve?id=" + categoryId,
		success: function(response) {
			fetchData();
			$("#approveKeyFact").attr("disabled", true);
			toastr.success("Appraisal Factor Approved Successfully");
			/*$("#messageParagraph").text("Appraisal Factor Approved Successfully");
			$("#msgOkModal").removeClass("btn btn-primary edit-btn");
			$("#msgOkModal").addClass("btn btn-primary edit-btn");
			$("#msgModal").modal('show');*/
			$('.loader').hide();
		},
		error: function() {
			$('.loader').hide();
		}
	});
}
function fetchAssignData(id) {
	$(".loader").show();
	console.log("inside the function")
	const promise2 = agGrid.simpleHttpRequest({
		url: 'appraisal-management-assignView?id=' + id
	}).then(function(data) {
		const responseData = JSON.parse(data.body);
		if (!responseData || !responseData.appraisalData || responseData.appraisalData.length === 0) {
			gridOptions1.api.setRowData([]);
			return;
		}
		var length = responseData.appraisalData.length;
		$("#totalReq2").find('span').html(length);
		gridOptions1.api.setRowData(responseData.appraisalData);
	});
	Promise.all([promise2]).then(function() {
		$(".loader").hide();
	}).catch(function(error) {
		$(".loader").hide();
	});
}
function assignKeyFact() {
	$("#assignId").attr("disabled", true);
	$("#approveKeyFact").attr("disabled", true);

	Swal.fire({
		title: 'Confirm Employee Assignment?',
		text: 'Are you sure you want to assign this employee? Once approved, this action cannot be undone.',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, assign',
		cancelButtonText: 'Cancel',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {

		if (result?.value) {
			savePatientDetails();
		}
		else {
			console.log("non ")
		}

		console.log("result-->", result);

	})
}
function savePatientDetails() {
	$("#assignId").attr("disabled", true);
	$("#assignKeyFact").attr("disabled", true);
	EmployeeAssign();
}
function EmployeeAssign() {
	var assignValue = [];
	var selectedRows = gridOptions.api.getSelectedRows();
	var categoryId = selectedRows[0].categoryid;
	var staffId = selectedRows[0].factorid;
	assignValue.push(staffId);
	assignValue.push(categoryId);
	console.log("assignValue-->", JSON.stringify(assignValue));
	$.ajax({
		type: "POST",
		url: "appraisal-management-employeeAssign",
		async: false,
		contentType: "application/json",
		dataType: "json",
		data: JSON.stringify(assignValue),
		success: function(response) {
			if (categoryId) {
				async function handleCategoryData() {
					try {
						await fetchAssignData(categoryId);
						closeEmployeeeList();
						$("#confirmModal").modal('hide');
						toastr.success('Appraisal factors assigned to employees successfully!');
						$("#assignKeyFact").attr("disabled", true);
						$("#next2").show();
						editAppraisalSelf(categoryId);
						cancelKeyFact();
					} catch (error) {
						console.error('Error fetching assign data:', error);
					}
				}
				handleCategoryData();
				gridOptions.api.refreshCells({ force: true });
			} else {
				console.error('categoryId is undefined or null');
			}
		}
	});
}
function closeEmployeeeList() {
	$("#employeeListModal").modal('hide');
}
function closeSuccessModal() {
	$("#assignKeyFact").attr("disabled", true);
	$("#successMOdal").modal('hide');
}
function travelNext(tab) {
	if (tab == 1) {
		$("#keyFactBtn").removeClass('active');
		$("#assignBtn").addClass('active');
		$("#selfAppBtn").removeClass('active');

		$("#keyFactTab").removeClass('active');
		$("#assignTab").addClass('active');
		$("#selfAppTab").removeClass('active');
	} else if (tab == 2) {
		$("#keyFactBtn").removeClass('active');
		$("#assignBtn").removeClass('active');
		$("#selfAppBtn").addClass('active');

		$("#keyFactTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#selfAppTab").addClass('active');
	} else {
		$("#keyFactBtn").addClass('active');
		$("#assignBtn").removeClass('active');
		$("#selfAppBtn").removeClass('active');

		$("#keyFactTab").addClass('active');
		$("#assignTab").removeClass('active');
		$("#selfAppTab").removeClass('active');
	}
}
function travelPrev(tab) {
	if (tab == 1) {
		$("#keyFactBtn").addClass('active');
		$("#assignBtn").removeClass('active');
		$("#selfAppBtn").removeClass('active');

		$("#keyFactTab").addClass('active');
		$("#assignTab").removeClass('active');
		$("#selfAppTab").removeClass('active');
	} else if (tab == 2) {
		$("#keyFactBtn").removeClass('active');
		$("#assignBtn").addClass('active');
		$("#selfAppBtn").removeClass('active');

		$("#keyFactTab").removeClass('active');
		$("#assignTab").addClass('active');
		$("#selfAppTab").removeClass('active');
	} else {
		$("#keyFactBtn").removeClass('active');
		$("#assignBtn").removeClass('active');
		$("#selfAppBtn").addClass('active');

		$("#keyFactTab").removeClass('active');
		$("#assignTab").removeClass('active');
		$("#selfAppTab").addClass('active');
	}
}
function deleteFactor1() {
	var selectedRows = gridOptions2.api.getSelectedRows();
	var selectedRowsString = '';
	let allRowData = [];
	let totalMark = 0;
	gridOptions2.api.forEachNode((node) => {
		allRowData.push(node.data);
	});
	const updatedData = allRowData.filter(row => !selectedRows.includes(row));
	updatedData.forEach((row, index) => {
		row.slno = index + 1;
		totalMark += parseInt(row.maxmark);
	});
	console.log("y", updatedData);
	gridOptions2.api.setRowData(updatedData);
	var totalRow = [{
		factors: 'Total',
		maxmark: totalMark,
		editable: false
	}];
	gridOptions2.api.setPinnedBottomRowData(totalRow);
	toastr.success('Factor Delete  Successfully');
	/*$("#messageParagraph").text('Factor Delete  Successfully'); 
	$("#msgOkModal").removeClass("btn btn-primary edit-btn");
	$("#msgOkModal").addClass("btn btn-primary edit-btn");
	$("#msgModal").modal('show');*/
	cancelFactor1();
	$("#deleteFactor").attr("disabled", true);
	$("#newFactor").attr("disabled", false);
}//editAppraisal(empid, assignid,categoryid)
function editAppraisalSelf(cateoryId) {
	var empid = $("#sessionId").val();
	$("#categoryId").val(cateoryId);
	$("#empid").val(empid);
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "appraisal-management-self-appraisal-edit?id=" + $("#sessionId").val() + "&factorId=" + cateoryId,
		success: function(response) {
			console.log("response edit apprisal self===", response)
			if (response.code == "200") {
				$('.loader').hide();
				var responsedata = JSON.parse(response.body);
				var appraisalReponseData = responsedata.appraisalData;
				console.log("appraisalReponseData=====", appraisalReponseData);
				if (appraisalReponseData) {
					$("#assignedId").text(appraisalReponseData[0].assignid);
					$("#userAssignId").val(appraisalReponseData[0].assignid);
					$("#empName").val(appraisalReponseData[0].empName);
					$("#assignId").text(appraisalReponseData[0].assignid);
					$("#categoryidPara").show();
					$("#empDesg").val(appraisalReponseData[0].designation);
					$("#empjoinDate").val(appraisalReponseData[0].joiningDate);
					if (appraisalReponseData[0].remarks != null && appraisalReponseData[0].remarks != "null") {
						$("#txtAreaSumN").val(appraisalReponseData[0].remarks.replace(/=#/g, "'").replace(/=\$/g, '"'));
						$("#charNumSN").find('span').html(appraisalReponseData[0].remarks.replace(/=#/g, "'").replace(/=\$/g, '"').length);
					}
					var gridData = appraisalReponseData;
					let assesementMark = 0;
					let selfAssessmentMark = 0;
					gridData.forEach((item) => {
						if (item.selfRemark) {
							item.selfRemark = item.selfRemark.replace(/=#/g, "'").replace(/=\$/g, '"');
						}
						item.factors = item.factor.replace(/=#/g, "'").replace(/=\$/g, '"');
						assesementMark += parseInt(item.assementMark || 0, 10);
						selfAssessmentMark += parseInt(item.selfAssesment || 0, 10);
					});
					gridOptions3.api.setRowData(gridData);
					var totalRow = [{
						factors: 'Total',
						maxmark: "100",
						selfAssesment: selfAssessmentMark,
						assementMark: assesementMark > 0 ? assesementMark : "",
						editable: false
					}];
					gridOptions3.api.setPinnedBottomRowData(totalRow);
					if (appraisalReponseData[0].status == 1) {
						// Disable buttons and textarea
						$("#saveSelfApp").attr("disabled", true);
						$("#txtAreaSumN").attr("disabled", true);

						// Set all editable columns to non-editable
						gridOptions3.columnDefs.forEach(column => {
							column.editable = false; // Force uneditable
						});

					} else {
						// Enable buttons and textarea
						$("#saveSelfApp").attr("disabled", false);
						$("#txtAreaSumN").attr("disabled", false);
						$("#txtAreaSumN").val("");

						// Set editable columns back to their original logic
						gridOptions3.columnDefs.forEach(column => {
							column.editable = function(params) {
								return !params.data.selfAssesment; // Restore original editable logic
							};
						});
					}

					// Apply changes to the grid
					gridOptions3.api.setColumnDefs(gridOptions3.columnDefs);
				} else {
					$("#assignedId").text('');
					$("#userAssignId").val('');
					$("#empName").val('');
					$("#assignId").text('');
					$("#categoryidPara").show();
					$("#empDesg").val('');
					$("#empjoinDate").val('');
					gridOptions3.api.setRowData('');
				}
			}
		},
		error: function(xhr, status, error) {
			$('.loader').hide();
			console.log("AJAX Error:", error);
		}
	});
}
function saveSelfApp() {
	gridOptions3.api.stopEditing(); // stop editing when the save button is click
	var obj = {};
	var allValid = true;
	var datas = [];
	var remarksLength = $("#txtAreaSumN").val().length;
	console.log("length", remarksLength);
	obj.factorCategory = $("#factorCategory").val();
	obj.fromDate = $("#fromDate").val();
	obj.toDate = $("#toDate").val();
	obj.categoryId = $("#categoryId").val();
	obj.remarks = $("#txtAreaSumN").val().replace(/'/g, '=#').replace(/"/g, '=$');
	obj.empid = $("#empid").val();
	obj.assignid = $("#userAssignId").val();
	if (remarksLength > 2000) {
		toastr.error('Please write the Self Assessment Remarks in up to 2000 Characters.');
		/*$("#messageParagraph").text('Please write the Self Assessment Remarks in up to 2000 Characters.'); 
		$("#msgOkModal").removeClass("btn btn-primary edit-btn");
		$("#msgOkModal").addClass("btn btn-primary edit-btn");
		$("#msgModal").modal('show');*/
		allValid = false;
	}
	var dataOfGrid1 = [];
	gridOptions3.api.forEachNode(function(rowNode, index) {
		dataOfGrid1.push(rowNode.data);
	});
	dataOfGrid1.forEach(item => {
		item.factors = item.factors.replace(/'/g, '=#').replace(/"/g, '=$');
		if (item.selfRemark) {
			item.selfRemark = item.selfRemark.replace(/'/g, '=#').replace(/"/g, '=$');
		}
	});
	obj.grid1List = dataOfGrid1;
	console.log("obj-->", obj);
	if (allValid) {
		datas.push(obj);
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "appraisal-management-self-add",
			async: false,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datas),
			success: function(response) {
				if (response.code == "200") {
					fetchAppraisalData(function(responseData) {
						console.log("data-->", responseData);
						$('.loader').hide();
						//$("#selfAppModal").modal('show');
						toastr.success('Self-appraisal has been saved successfully!');
						$("#saveSelfApp").attr("disabled", true);

					});
				} else {
					toastr.error('Something went wrong please try after some time!');
					/*$("#messageParagraph").text('Something went wrong please try after some time!'); 
					$("#msgOkModal").removeClass("btn btn-primary edit-btn");
					$("#msgOkModal").addClass("btn btn-primary edit-btn");
					$("#msgModal").modal('show');*/
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}
}