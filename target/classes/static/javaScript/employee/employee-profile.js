	
	
	let nameIcon = '<i class="fa-solid fa-user"></i> ';
	let mobileIcon = '<i class="fa-solid fa-mobile-retro"></i> ';
	let locationIcon = '<i class="fa-solid fa-location-dot"></i> ';
	let departmentIcon = '<i class="fa-solid fa-user-secret"></i> ';
	let statusIcon = '<i class="fa-solid fa-clock"></i> ';
	let emailIcon = '<i class="fa-sharp fa-solid fa-at"></i> ';
	let typee = '';
	$(document).ready(function() {
		const urlParams = new URLSearchParams(window.location.search);
	    typee = urlParams.get('id');
	
	    if (typee == null || typee == 'null') {
	        typee = '';
	    }
		getHideDuringEmployee();
		var dateFormat = localStorage.getItem("dateFormat");
		var currentDate = new Date();
		var maxBirthdayDate = new Date();
		maxBirthdayDate.setFullYear(maxBirthdayDate.getFullYear() - 18);
		
		const myear = maxBirthdayDate.getFullYear();      
		const mmonth = maxBirthdayDate.getMonth();        
		const mday = maxBirthdayDate.getDate();
		
		$("#dobidCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: new Date(myear, mmonth, mday),
			defaultDate: maxBirthdayDate
		}).on("change", function() {
			$('#dobid').val($(this).val());
		})
		$('#dobid').blur(function() {
			$("#dobidCalendar").val($(this).val());
		})
	
		$("#joiningdateCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			defaultDate: currentDate
		}).on("change", function() {
			$('#joiningdate').val($(this).val());
		})
	
		$('#joiningdate').blur(function() {
			$("#joiningdateCalendar").val($(this).val());
		})
		$("#mrgDateCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: 0,
			scrollMonth: false
	
		}).on("change", function() {
			$('#mrgDate').val($(this).val());
		})
	
		$('#mrgDate').blur(function() {
			$("#mrgDateCalendar").val($(this).val());
		})
	
		//Work Details
		$("#fromdateidCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
	
		}).on("change", function() {
			$('#fromdateid_').val($(this).val());
		})
	
		$('#fromdateid_').blur(function() {
			$("#fromdateidCalendar").val($(this).val());
		})
	
		$("#todateidCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
	
		}).on("change", function() {
			$('#todateid_').val($(this).val());
		})
	
		$('#todateid_').blur(function() {
			$("#todateidCalendar").val($(this).val());
		})
	
		//Dependent Date DOB
		$("#typeDOBiddCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
			maxDate: 0,
			scrollMonth: false
		}).on("change", function() {
			$('#typeDOBidd_').val($(this).val());
		})
	
		$('#typeDOBidd_').blur(function() {
			$("#typeDOBiddCalendar").val($(this).val());
		})
	
		//Insurance Details
		$("#pfdateidCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#pfdateid_').val($(this).val());
		})
	
		$('#pfdateid_').blur(function() {
			$("#pfdateidCalendar").val($(this).val());
		})
	
		$("#todatesCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#todates').val($(this).val());
		})
	
		$('#todates').blur(function() {
			$("#todatesCalendar").val($(this).val());
		})
	
		$("#effectiveDateToCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#effectiveDateTo').val($(this).val());
		})
		$('#effectiveDateTo').blur(function() {
			$("#effectiveDateToCalendar").val($(this).val());
		})
	
		$("#effectiveDateFromCalendar").datetimepicker({
			format: dateFormat,
			closeOnDateSelect: true,
			timepicker: false,
		}).on("change", function() {
			$('#effectiveDateFrom').val($(this).val());
		})
		$('#effectiveDateFrom').blur(function() {
			$("#effectiveDateFromCalendar").val($(this).val());
		})
		
		getEmployeeList(typee);
		var selectYear = document.getElementById("passyear");
		var currentYear = new Date().getFullYear();
		var numberOfYears = 50;
		for (var i = 0; i < numberOfYears; i++) {
			var yearOption = document.createElement("option");
			yearOption.text = currentYear - i;
			yearOption.value = currentYear - i;
			selectYear.add(yearOption);
		}
		$("#editProfile").click(function() {
			getEnabledFields();
		})
		$(".offerLetter").hide();
		$("#saveProfile").prop('disabled', true);
		$("#saveProfile").hide();
		$("#profileClose").hide();
	
		//Previous And Next Button
		document.getElementById("profilePrev").addEventListener("click", function() {
			navigateTabs("prev");
			getCurrentTab();
		});
	
		document.getElementById("profileNext").addEventListener("click", function() {
			navigateTabs("next");
			getCurrentTab();
		});
	
		let isEmployeeIdChecked = false;
		function navigateTabs(direction) {
			var employeeId = $("#employeeId").val();
			editemppersonal(employeeId);
	
			// Filter out hidden tabs
			const tabs = Array.from(document.querySelectorAll("#tabMenu .nav-link"))
				.filter(tab => !tab.closest(".d-none"));
	
			const activeTab = document.querySelector("#tabMenu .nav-link.active");
			let currentIndex = tabs.indexOf(activeTab);
			let newIndex = direction === "next" ? currentIndex + 1 : currentIndex - 1;
	
	
	
			if (newIndex >= 0 && newIndex < tabs.length) {
				// Remove active and tab-active classes from the current tab if it exists
				if (activeTab) {
					activeTab.classList.remove("active", "tab-active");
					activeTab.setAttribute("aria-selected", "false");
				}
	
				const newTab = tabs[newIndex];
				// Add active and tab-active classes to the new tab
				newTab.classList.add("active", "tab-active");
				newTab.setAttribute("aria-selected", "true");
	
				const targetPaneId = newTab.getAttribute("href");
				const activePane = document.querySelector(".tab-pane.active");
				if (activePane) activePane.classList.remove("show", "active");
	
				const targetPane = document.querySelector(targetPaneId);
				if (targetPane) targetPane.classList.add("show", "active");
	
				updateButtonVisibility(newIndex, tabs.length);
			}
		}
	
		function updateButtonVisibility(newIndex, totalTabs) {
			const prevButton = $("#profilePrev");
			const nextButton = $("#profileNext");
			const editButton = $("#editProfile");
			const saveButton = $("#saveProfile");
			const addButton = $("#profileAdd");
	
			if (newIndex === 0) {
				prevButton.hide();
				nextButton.show();
				editButton.show();
				addButton.show();
				saveButton.show();
			} else if (newIndex === totalTabs - 1) {
				prevButton.show();
				saveButton.hide();
				editButton.show();
				nextButton.hide();
			} else {
				prevButton.show();
				nextButton.show();
				editButton.show();
				addButton.hide();
				saveButton.hide();
			}
		}
	
		// Initialize button visibility on page load
		const tabs = Array.from(document.querySelectorAll("#tabMenu .nav-link"))
			.filter(tab => !tab.closest(".d-none"));
	
		const activeTabIndex = tabs.findIndex(tab => tab.classList.contains("active"));
		updateButtonVisibility(activeTabIndex, tabs.length);
	
	
		$("#departmentid_").change(function() {
	
			var department = $("#departmentid_").val();
	
			if (department) {
				$.ajax({
					type: "GET",
					url: "view-manage-employee-subdepartment-list?department=" + department,
					dataType: 'json',
					contentType: 'application/json',
					data: department,
					success: function(
						response) {
						if (response.message == "Success") {
							console
								.log(response);
							$(
								"#subdepartmentid_")
								.empty();
							$(
								"#subdepartmentid_")
								.append(
									"<option value=''>Select</option>");
	
							for (var i = 0; i < response.body.length; i++) {
								var option = $("<option></option>");
								$(option)
									.val(
										response.body[i].key);
								$(
									option)
									.html(
										response.body[i].name);
								$(
									"#subdepartmentid_")
									.append(
										option);
							}
						}
					},
					error: function(
						data) {
						console
							.log(data);
						$("#subdepartmentid_")
							.empty();
						$("#subdepartmentid_")
							.append("<option value=''>Select</option>");
	
					}
				})
			} else {
				$("#subdepartmentid_").empty();
				$("#subdepartmentid_")
					.append(
						"<option value=''>Select</option>");
			}
	
		})
	
	});
	/*document ready end here */
	
	function downloadEmployeeCSV() {
	
		const today = new Date();
    	const day = String(today.getDate()).padStart(2, '0');
    	const month = String(today.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    	const year = today.getFullYear();
	
		gridOptionsEmployee.api.exportDataAsCsv({
		    columnKeys: gridOptionsEmployee.columnDefs.slice(1).map(col => col.field),
		    fileName: 'Employee-List'+'-'+day+'-'+month+'-'+year+'.csv'
		});
	}
	
	var adharaValid;
	function adharaVal() {
	
		var adhara = $('#aadhaar').val();
	
		var adharaid = /^\d{12}$/;
		if (adhara != '') {
			if (adharaid.test(adhara)) {
	
				$("#error7").hide();
				adharaValid = true;
				return true;
			} else {
				$("#error7").show();
				$("#error7").html("Please enter a valid aadhaar no");
				adharaValid = false;
				return false;
			}
	
		} else {
			$("#error7").hide();
			adharaValid = true;
			return true;
		}
	
	}
	
	//Email validation
	var wmailValid;
	function wmailVal() {
		var mail2 = $('#workmailid').val();
		var mid2 = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
	
		if (mail2 != "") {
			if (mid2.test(mail2)) {
				$("#error5").hide();
				wmailValid = true;
				return true;
			} else {
				$("#error5").show();
				$("#error5").html("Please enter a valid email id.");
				wmailValid = false;
				return false;
			}
	
		} else {
			$("#error5").hide();
			wmailValid = true;
			return true;
		}
	
	}
	
	//pancard card validation 
	var pancardValid;
	function pancardVal() {
	
		var pancard = $('#panno').val();
	
		var pancardid = /([A-Z]){5}([0-9]){4}([A-Z]){1}$/;
		var format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
	
		if (pancard != '' && pancard.toUpperCase() === pancard && !format.test(pancard)) {
			if (pancardid.test(pancard)) {
				$("#error4").hide();
				pancardValid = true;
				return true;
			} else {
	
				$("#error4").show();
				$("#error4").html("Please enter a valid pan card no.");
				//  $('#panno').val("");
				pancardValid = false;
				return false;
			}
	
		} else {
			$('#panno').val("");
			pancardValid = false;
			return false;
	
		}
	
	}
	
	//Personal Email validation
	var pmailValid;
	
	function pmailVal() {
		var mail = $('#personalmailid').val();
		var mid = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;
	
		if (mail != "") {
			if (mid.test(mail)) {
				$("#error3").hide();
				pmailValid = true;
				return true;
			} else {
				$("#error3").show();
				$("#error3").html("Please enter a valid email id.");
				pmailValid = false;
				return false;
			}
	
		} else {
			$("#error3").hide();
			pmailValid = true;
			return true;
		}
	
	}
	
	
	//Mobile Number validation
	var mobValid1;
	function mobVal1() {
		var mob = $('#mobilenoid').val().trim();
		var phonenoPattern = /^\d{10}$/;
		if (mob === '') {
			$("#error9").html("Mobile No is required").show();
			mobValid1 = false;
			return false;
		}
		if (!phonenoPattern.test(mob)) {
			$("#error9").html("Please enter a valid 10-digit mobile number").show();
			mobValid1 = false;
			return false;
		}
		$("#error9").hide();
		mobValid1 = true;
		return true;
	}
	
	
	//EPF Number validation
	var epfValid;
	function epfVal() {
		var epf = $('#epfno').val();
	
		var epfno = /^\d{12}$/;
	
		if (epf != '') {
	
			if (epfno.test(epf)) {
				$("#error12").hide();
	
				epfValid = true;
				return true;
			} else {
				$("#error12").html(
					"please enter  12 digit UAN PF No");
				$("#error12").show();
				epfValid = false;
				return false;
			}
		} else {
			$("#error12").hide();
	
			epfValid = true;
			return true;
		}
	
	}
	
	//ESIC  Number validation
	var esicValid;
	
	function esicVal() {
		var esic = $('#esicno').val();
	
		var esicno = /^\d{10}$/;
	
		if (esic != '' || esic != null) {
			if (esicno.test(esic)) {
				$("#error11").hide();
	
				esicValid = true;
				return true;
			} else {
				$("#error11").html(
					"Please Enter 10 Digit ESIC Number");
				$("#error11").show();
				esicValid = false;
				return false;
			}
		} else {
			$("#error11").hide();
			esicValid = true;
			return true;
		}
	}
	
	function deleteFile() {
		$('#fileUpload').val("");
		$('#imgemp').attr('src', '');
		$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	
		var fileData = new FormData();
	
		fileData.append('file', 'none');
		fileData.append('path', 'none');
	
		$("#empImage").val("");
		$.ajax({
			type: "POST",
			url: "view-manage-employee-delete-file",
			enctype: "multipart/form-data",
			contentType: false,
			/* data        : fileData, */
			processData: false,
			cache: false,
			success: function(response) {
			}
		});
	}
	//Table For Employee Details Starts
	var columnDefsEmployee = [{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: "Employee Id",
		field: "employeeId",
		width: 110,
		pinned: 'left',
	}, {
		headerName: "Employee Name",
		field: "firstName",
		pinned: 'left',
	
	},
	{
		headerName: "Department",
		field: "department",
		width: 110,
	
	},
	{
		headerName: "Sub Department",
		field: "subDepartment",
		hide: true,
	
	},
	{
		headerName: "Designation",
		field: "designation",
		width: 110,
	},
	{
		headerName: "Staff Type",
		field: "stafftype",
		width: 80,
	},
	{
		headerName: "Band",
		field: "bandname",
		width: 110,
	}, {
		headerName: "Manager",
		field: "manager",
		width: 110,
	}, {
		headerName: "Gender",
		field: "gender",
		width: 100,
	}, {
		headerName: "Date Of Birth",
		field: "dob",
		cellStyle: {
			textAlign: 'center'
		},
		width: 135,
	}, {
		headerName: "Mobile No",
		field: "mobileNo",
		type: "rightAligned",
		width: 110,
	}, {
		headerName: "	Personal Mail",
		field: "personalMail"
	}, {
		headerName: "Father Name",
		field: "fatherName"
	}, {
		headerName: "Employed By",
		field: "employedBy",
		width: 100,
	}, {
		headerName: "Blood Group",
		field: "bloodGroup",
		width: 100,
	}, {
		headerName: 'ID Card',
		width: 100,
		cellRenderer: function(params) {
			if (params.data.qrCode == "" || params.data.qrCode == null) {
				return '<div style="color:#ff8242">Not Available</div>';
			} else {
				return '<a id="id" onclick=viewQr("' +
					params.data.qrCode + '","' +
					params.data.employeeId + '") href="javascript:void(0)">' +
					'<div style="color:#034694"><i class="bi bi-person-badge"> Download</i></div>' +
					'</a>';
			}
		},
		cellStyle: {
			textAlign: 'center'
		},
	}
	];
	
	var gridOptionsEmployee = {
		columnDefs: columnDefsEmployee,
		rowSelection: 'single',
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		/*onFirstDataRendered: function(params) {
			params.api.selectIndex(0, true, true);
		},*/
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 185,
			height: 10
		},
		pagination: true,
		paginationPageSize: 15,
		onSelectionChanged: onSelectionChanged,
	};
	let empid = "";
	let manager = "";
	function onSelectionChanged() {
		setTabAsDefault();
	
		var selectedNodes = gridOptionsEmployee.api.getSelectedNodes();
		var selectedData = selectedNodes.map(node => node.data);
		empid = selectedData.map(data => data.employeeId);
		manager = selectedData.map(data => data.manager);
		const employeeName = selectedData.map(data => data.firstName);
		$("#managerDtls").text(manager);
		// $("#employeeNameTop").text(employeeName);
		$("#employeeNameTop").text(employeeName[0]?.toLowerCase()?.replace(/\b\w/g, char => char.toUpperCase()));
		
		var selectedRows = gridOptionsEmployee.api.getSelectedRows();
		$("#existingEmpId").val('');
		if (selectedRows.length > 0) {
			$("#existingEmpId").val(empid);
			getEmployeeDetailsById(empid);
			getManagerListById(empid);
			getDirectReport(empid);
			$("#managerDtls").text(manager);
			showAllTabs();
			$("#saveProfile").hide();
		} else {
			$("#profileNext").prop('disabled', true);
			$("#managerDtls").text('');
			clearvalueOnDeselect('');
			getDirectReport('');
			clearImage();
			$(".offerLetter").show();
			$("#candidateDetailsBar").hide();
			getHideAllTabs();
	
			// Trigger click event on the tab
			$("#personalInformation-tab").click();
			$("#personalInformation-tab a").tab('show');
			$("#saveProfile").prop('disabled', false);
			$("#saveProfile").show();
			$("#profileClose").show();
			$("#profileAdd").hide();
			$("#employeeNameTop").text('');
			$("#profileAdd,#editProfile,#profileNext").hide();
		}
	
	}
	
	function getHideAllTabs() {
		$("#address-tab").hide();
		$("#educational-tab").hide();
		$("#workDetails-tab").hide();
		$("#bankDetails-tab").hide();
		$("#salaryDetails-tab").hide();
		$("#document-tab").hide();
		$("#ccrDetails-tab").hide();
	}
	
	function showAllTabs() {
		$("#address-tab").show();
		$("#educational-tab").show();
		$("#workDetails-tab").show();
		$("#bankDetails-tab").show();
		$("#salaryDetails-tab").show();
		$("#document-tab").show();
		$("#ccrDetails-tab").show();
	}
	function viewQr(qr, id) {
		var qrId = "";
		qrId = qr;
		var path = "/document/staffQrCode/";
		var selectedRowsString = '';
		selectedRowsString += '"' + id + '"';
		var showPath = path + qr;
		window.open("/employee/view-idcard-pdf-downloads?dcId=" + window.btoa(selectedRowsString), '_blank');
	}
	
	function getTemEmplDtls(id) {
		$("#emplList").text("");
		$("#forwardto").append("");
		editemppersonal(id);
	}
	function disableAllField() {
		$("#tabContent").find("input, select, textarea").prop("disabled", true);
		const buttonIds = [
			"saveCcr",
			"submitDoc",
			"save",
			"bsave",
			"submitworkdetails",
			"savAddress",
			"saveEducation",
			"saveProfile"
		];
		buttonIds.forEach(function(id) {
			$("#" + id).attr("disabled", true);
		});
		$("#dobidNew").prop("disabled", true);
		//$("#dobidCalendarNew").css("pointer-events", "none").addClass("disabled");
	}
	
	function editemppersonal(id) {
		disableAllField();
		$("#emplList").text("");
		$("#forwardto").empty();
		var sid = $("#sessionId").val();
		var srole = $("#adRole").val();
		var sessionRole = $("#sessionRole").val();
		var empRole = $("#empRole").val();
		if (id == sid) {
			$("#headingTen").show();
		} else {
			$("#headingTen").hide();
		}
	
		if (sessionRole.includes('rol001') || sessionRole.includes('rol010')) {
			$("#headingCCR").show();
		} else {
			$("#headingCCR").hide();
		}
		if ($("#empRole").val() != "") {
			//gridOptions1.columnApi.setColumnVisible('check1', false);
			$("#ccrDelete").attr('disabled', true);
	
			if (id == sid) {
				//gridOptions5.columnApi.setColumnVisible('checkDoc', true);
				$("#newadd").show();
				$("#dadd").show();
				$("#ddelete").show();
				$("#docadd").show();
				$("#docdelete").show();
			} else {
				$("#newadd").hide();
				$("#dadd").hide();
				$("#ddelete").hide();
				$("#docadd").hide();
				$("#docdelete").hide();
			}
		}
		if ($("#mrRole").val() != "") {
			$("#wadd").hide();
			$("#wdelete").hide();
			$("#submitworkdetails").hide();
			$("#savebenifit").hide();
			$("#bdelete").hide();
			$("#badd").hide();
			$("#bsave").hide();
			$("#iadd").hide();
			$("#submitinsurance").hide();
			$("#idelete").hide();
			$("#epfno").attr('disabled', true);
			$("#esicno").prop('disabled', true);
			$("#joiningdate").attr('disabled', true);
			$("#joiningdateCalendar").hide();
			$("#saladd").hide();
			$("#save1").hide();
			$("#approve").hide();
		}
		if ($("#adRole").val() != "") {
	
			$("#wadd").show();
			$("#wdelete").show();
			$("#submitworkdetails").show();
			$("#savebenifit").show();
			$("#bdelete").show();
			$("#badd").show();
			$("#bsave").show();
			$("#iadd").show();
			$("#submitinsurance").show();
			$("#idelete").show();
			$("#epfno").attr('disabled', false);
			$("#esicno").attr('disabled', true);
			$("#joiningdate").attr('disabled', false);
			$("#joiningdateCalendar").show();
			$("#saladd").show();
			$("#save1").show();
			$("#approve").show();
			$("#newadd").show();
			$("#dadd").show();
			$("#ddelete").show();
			$("#docadd").show();
			$("#docdelete").show();
		}
	
	
		/*
			$.ajax({
				type: "GET",
				url: "view-manage-employee-manager-list?id=" + id,
				success: function(response) {
					if (response.message == "Success") {
		
						$("#managerid_").empty();
						$("#managerid_").append("<option value=''>Select</option>");
						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#managerid_").append(option);
						}
					}
				},
				error: function(response) {
				}
			});*/
	
		$("#tracking").text("");
	
		getAddressByEmpId(id);
		viewEducation(id);
	
	
	}
	
	
	function saveEdu() {
	
	
		data = {};
	
		data.empId = $("#employeeId").val();
		data.eduId = $("#eduId").val();
		data.qualification = $("#qualification").val();
		data.instiname = $("#instiname").val();
		data.passyear = $("#passyear").val();
		data.docName = $("#imageName_1").html();
		var doc = $("#imageName_1").html();
	
	
		if (data.qualification == "" || data.qualification == null) {
			toastr.error("Qualification Required");
			return;
		}
		if (data.instiname == "" || data.instiname == null) {
			toastr.error("Institution  Required");
			return;
		}
		if (data.passyear == "" || data.passyear == null) {
			toastr.error("Pass Year Required");
			return;
		}
		if (doc == "" || doc == null) {
			toastr.error("Document Required");
			return;
		}
	
	
		console.log("data==", JSON.stringify(data))
		var id = $("#employeeId").val();
	
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-education-save",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response.code == "Success") {
					closeNavEdu();
					viewEducation(id);
					toastr.success(response.message);
				} else {
					toastr.success(response.message);
				}
			},
			error: function(response) { }
		})
	
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
	function openNavCCR() {
		if ($("#adRole").val() != "") {
			
		$("#profilePrev").hide();
		$("#profileNext").hide();
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
		enableAllField();
		$(".formValidation").remove();
		$('#saveEmployee').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", true);
		$('#ccrAdd').attr("disabled", true);
		getCCRTypeList();
		$("#reviewId").val("");
		var currentDate = (new Date()).toISOString().split('T')[0];
		var newDate = changeDateFormat(currentDate);
		$('#dateCCR').val(newDate);
		$('#type').val("");
		$('#subject').val("");
		$("#remark").val("");
		$('#expectedResult').val("");
		$('#guidedBy').val("");
		$("#documentDiv1").val("");
		$("#documentDiv1").empty();
		$('.br-s-btn-p').show();
		$('.br-m-btn-p').hide();
		$('#ccrDiv').show();
		$('#myGridCCR').hide();
	
		var docDet = `
					<div class="control-group">
					  <label class="custom-file-upload btn go-btn h-auto" for="uploadDoc_CCR">
						<i class="ti-plus"></i>
					  </label>
					  <div class="controls">
						<input type="file" class="document1" id="uploadDoc_CCR"
							   accept=".jpeg, .jpg, .png, .pdf" name="userImage"
							   onchange="saveMultiFileDocCCR(event)"/>
					  </div>
					</div>
					<input type="hidden" id="uploadHiddenCCR" class="uploadHidCls">
					<div id="uploadedBillDiv_CCR" align="center" class="uploadedBillCls order-3"></div>
					<div id="imageName_CCR" class="imageName"></div>
					<div id="validationDivCCR"></div>
					`;
		$("#documentDiv1").html(docDet);
	
	}else{
		toastr.error("Only Admin can add CCR details!")
	}
	}
	function closeNavCCR() {
		
		$("#profilePrev").show();
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
		$('#saveEmployee').attr("disabled", false);
		$('#profilecancelbtn').attr("disabled", false);
		$('#ccrAdd').attr("disabled", false);
		$('#cancelBtn').attr("disabled", false);
		$("#newItem").show();
		$("#delete1").show();
		$("#reviewId").val("");
		$('#dateCCR').val("");
		$('#type').val("");
		$('#subject').val("");
		$("#remark").val("");
		$('#expectedResult').val("");
		$("#reviewId").empty();
		$('#dateCCR').empty();
		$('#type').empty();
		$('#subject').empty();
		$("#remark").empty();
		$('#expectedResult').empty();
	
		$('.br-s-btn-p').hide();
		$('.br-m-btn-p').show();
		$('#ccrDiv').hide();
		$('#myGridCCR').show();
	}
	
	function saveCCR() {
		var item = {};
	
		item.reviewId = $("#reviewId").val();
		item.empId = $("#employeeId").val();
		item.date = $('#dateCCR').val();
		item.type = $('#type').val();
		item.subject = $('#subject').val();
		item.remark = window.btoa($("#remark").val());
		item.expectedResult = window.btoa($("#expectedResult").val());
		item.guidedBy = $('#guidedBy').val();
		var docName = $("#imageName_CCR").html();
		if (docName.includes()) {
			// Extract the file name
			item.docName = docName.split('/').pop();
			console.log("if==", docName);
		} else {
			item.docName = docName;
			console.log("else==", docName);
		}
		console.log("item====" + JSON.stringify(item));
	
		if (item.date == null || item.date == "") {
			toastr.error("Date is Required");
			return;
		}
		if (item.type == null || item.type == "") {
			toastr.error("Type is Required");
			return;
		}
		if (item.remark == null || item.remark == "") {
			toastr.error("Remark is Required");
			return;
		}
		saveCcrDetails(item);
	
	}
	
	function saveCcrDetails(item) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "view-manage-employee-ccr-add",
			contentType: "application/json",
			data: JSON.stringify(item),
			success: function(response) {
				if (response.code == "Success") {
					closeNavCCR();
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success(response.message);
					getCcrDetailsByEmpId($("#employeeId").val());
				}
			},
			error: function(data) {
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})
	}
	
	
	function openNavEducation() {
		$("#profilePrev").hide();
		$("#profileNext").hide();
	
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
		enableAllField();
		$("#eduId").val("");
		$('#qualification').val("");
		$('#instiname').val("");
		$('#passyear').val("");
		$('#uploadDoc_1').empty();
		$('#imageName_1').empty();
		$('#uploadedBillDiv_1').empty();
		$("#imageName_1").html("");
		$(".formValidation").remove();
		$('.br-s-btn').show();
		$('.br-m-btn').hide();
		$('#eduDiv').show();
		$('#myGridEducation').hide();
		deleteFile1();
		getQualifyTypeList();
		var docDet = `<div class="control-group">
											<label class="custom-file-upload btn go-btn h-auto" for="uploadDoc_1"> 
												<i class="ti-plus"></i>
											</label>
											<div class="controls">
												<input type="file" class="document" id="uploadDoc_1"
													accept=".jpeg, .jpg, .png, .pdf" name="userImage"
													onchange="saveMultiFileDoc1(event)" />
											</div>
										</div>
										<input type="hidden" id="uploadHidden1" class="uploadHidCls">
										<div id="uploadedBillDiv_1" class="uploadedBillCls"></div>
										<div id="imageName_1" class="imageName"></div>
										<div id="validationDiv1"></div>`;
		$("#documentDiv").html(docDet);
	
	}
	
	function closeNavEdu() {
		$("#profilePrev").show();
		$("#profileNext").show();
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
		$("#eduId").val("");
		$('#qualification').val("");
		$('#instiname').val("");
		$('#passyear').val("");
		$('#uploadDoc_1').empty();
		$('#imageName_1').empty();
		$('#uploadedBillDiv_1').empty();
		$("#imageName_1").html("");
		$('.br-s-btn').hide();
		$('.br-m-btn').show();
	
		$('#eduDiv').hide();
		$('#myGridEducation').show();
	}
	function enableAllField() {
		$("#tabContent").find("input, select, textarea").prop("disabled", false);
		const buttonIds = [
			"saveCcr",
			"submitDoc",
			"save",
			"bsave",
			"submitworkdetails",
			"savAddress",
			"saveEducation"
		];
		buttonIds.forEach(function(id) {
			$("#" + id).attr("disabled", false);
		});
		$("#dobidNew").prop("disabled", false);
	}
	function openNav() {
		$("#profilePrev").hide();
		$("#profileNext").hide();
		//enableAllField();
		$('#typeid_').attr("disabled", false);
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
		$(".formValidation").remove();
		$('#saveEmployee').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", true);
		//$("#typeid_").attr('disabled', false);
		$("#satype").addClass("select");
		$('#addressIdh').val("");
		$('#typeid_').val("");
		$('#addressid_').val("");
		$('#countryid_').val("");
		$('#stateid_').val("");
		$('#cityid_').val("");
		$('#zipcodeid_').val("");
		$('#Status_').val("");
	
		$('#checkboxDivAdd').hide();
	
		$('.br-s-btn').show();
		$('.br-m-btn').hide();
		$('#addressDiv').show();
		$('#myGrid').hide();
	
		$('#ad1').parent().addClass('d-none');
	}
	
	function hideAgData(fieldName, visible) {
		if (visible != 'E') {
			gridOptions6.columnApi.setColumnVisible(fieldName, false);
		}
	}
	
	function closeNav() {
	     $("#profilePrev").show();
		$("#profileNext").show();
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
	
	
		$('#addressid_').attr("disabled", false);
		$('#countryid_').attr("disabled", false);
		$('#stateid_').attr("disabled", false);
		$('#cityid_').attr("disabled", false);
		$('#zipcodeid_').attr("disabled", false);
		//$(".formValidation").remove();
		$('#saveEmployee').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", true);
		//$("#typeid_").attr('disabled', false);
		$("#satype").addClass("select");
		$('#addressIdh').val("");
		$('#typeid_').val("");
		$('#addressid_').val("");
		$('#countryid').val("");
		$('#stateid_').val("");
		$('#cityid_').val("");
		$('#zipcodeid_').val("");
		$('#Status_').val("");
		$('#checkboxDivAdd').hide();
	
		$('.br-s-btn').hide();
		$('.br-m-btn').show();
		$('#addressDiv').hide();
		$('#myGrid').hide();
	
		$('#ad1').parent().removeClass('d-none');
	}
	//Zip Code  validation 
	var zipcodeValid;
	function zipcodeVal() {
		var zipcode = $('#zipcodeid_').val();
		var zipcodeid = /^\d{6}(-\d{6})?$/;
		var zipcodeid1 = /^\d{6}(-\d{5})?$/;
		if (zipcode != '') {
			if (zipcodeid.test(zipcode) || zipcodeid1.test(zipcode)) {
				$("#error10").hide();
				zipcodeValid = true;
				return true;
			} else {
				$("#error10").show();
				$("#error10").html("Please enter a valid Zip Code No.");
				zipcodeValid = false;
				return false;
			}
		} else {
			$("#error10").hide();
			zipcodeValid = true;
			return true;
		}
	}
	
	function saveAddress() {
	
		data = {};
	
		data.employeeId = $("#employeeId").val();
		data.addressId = $("#addressIdh").val();
		data.type = $("#typeid_").val();
		data.address = $("#addressid_").val();
		data.city = $("#cityid_").val();
		data.state = $("#stateid_").val();
		data.country = $("#countryid_").val();
		data.zipCode = $("#zipcodeid_").val();
		data.status = '1';
		data.checkBox = $("#dynamicCheckbox").is(":checked") ? "1" : "0";
		var type = $("#typeid_").val();
		$('#locationDtls').html(locationIcon + data.address);
		zipcodeVal();
	
		if (data.type == "" || data.type == null) {
			toastr.error("Address Type Required");
			return;
		}
	
		if (data.address == "" || data.address == null) {
			toastr.error("Address Required");
			return;
		}
		if (data.country == "" || data.country == null) {
			toastr.error("Country Required");
			return;
		}
		if (data.state == "" || data.state == null) {
			toastr.error("State Required");
			return;
		}
		if (data.city == "" || data.city == null) {
			toastr.error("City Required");
			return;
		}
	
		if (data.zipCode == "" || data.zipCode == null) {
			toastr.error("Zip Code Required");
			return;
		}
	
		var id = $("#employeeId").val();
		if (id != null && id != "" && zipcodeValid) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-address-save",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(response) {
					if (response.code == "Success") {
						//$("#newadd").hide();
						if (type == 'ATM0001') {
							$("#savePermanent").show();
						} else {
							$("#savePermanent").hide();
						}
	
						closeNav();
						getAddressByEmpId(id);
						if ($("#addressIdh").val()) {
							toastr.success(response.message)
						} else {
							toastr.success(response.message);
						}
					} else {
						toastr.error(response.code);
					}
				},
				error: function(response) { }
			})
		}
	}
	
	
	//EMPLOYEE WORK DETAILS STARTS
	var columnDefs1 = [{
		headerName: "",
		field: "check1",
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30
	}, {
		headerName: "Designation",
		field: "degination",
		/*cellRenderer: function(params) {
			return '<a onclick=editWorkDetails("' +
				params.data.employeeworkId +
				'") href="javascript:void(0)"> ' +
				params.data.degination + '  ' + '<i class="bi bi-pencil-square"></i></a>';
		}*/
	},
	{
		headerName: "Job Title",
		field: "jobTitle"
	},
	{
		headerName: "Staff Type",
		field: "stafftype",
	}, {
		headerName: "Department",
		field: "department",
	}, {
		headerName: "Sub Department",
		field: "subdepartment",
		width: "200",
	}, {
		headerName: "Band",
		field: "band",
	}, {
		headerName: "Manager",
		field: "managerName",
	},
	{
		headerName: "Employment Status",
		field: "employmentStatus",
		width: "200",
	},];
	
	var gridOptions1 = {
		columnDefs: columnDefs1,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 149,
			height: 10
		},
		rowSelection: 'single',
		suppressRowClickSelection: true,
		onSelectionChanged: onSelectionChangedwork
	};
	
	function openNav1() {
		
		
		if ($("#adRole").val() != "") {
			
			$("#profilePrev").hide();
		$("#profileNext").hide();
			
	
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
	
		$("#workIdss").val('');
		enableAllField();
		$(".formValidation").remove();
		$("#workId").val("")
		$('#fromdateid_').val("");
		$('#todateid_').val("");
		$('#jobtitleid_').val("");
		$('#departmentid_').val("");
		$('#deginationid_').val("");
		$('#managerid_').val("");
		$('#bandid_').val("");
		$('#stafftypeid_').val("");
	
		$('#subdepartmentid_').val("");
		$('#employmentstatusid_').val("");
	
		$('.br-s-btn').show();
		$('.br-m-btn').hide();
		$('#workDiv').show();
		$('#myGrid1').hide();
	}else{
		toastr.error("Only Admin can add work details!")
	}
	}
	
	function closeNav1() {
	    $("#profilePrev").show();
		$("#profileNext").show();
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
		$("#workId").val("")
		$('#fromdateid_').val("");
		$('#todateid_').val("");
		$('#jobtitleid_').val("");
		$('#departmentid_').val("");
		$('#deginationid_').val("");
		$('#managerid_').val("");
		$('#bandid_').val("");
		$('#stafftypeid_').val("");
		$('#subdepartmentid_').val("");
		$('#employmentstatusid_').val("");
	
		$('.br-s-btn').hide();
		$('.br-m-btn').show();
		$('#workDiv').hide();
		$('#myGrid1').show();
	
	}
	
	function onSelectionChangedwork() {
		var selectedRows = gridOptions1.api.getSelectedRows();
		console.log("selectedRows================", selectedRows);
		var rowCount = selectedRows.length;
		
		if (rowCount > 0) {
			if ($("#adRole").val() != "") {
				$('#wdelete').removeClass("d-none");
				//$('#wadd').attr("disabled", true);
				$('.br-dis').attr("disabled", false);
			} else {
				$('#wdelete').addClass("d-none");
				//$('#wadd').attr("disabled", false);
				$('.br-dis').attr("disabled", true);
			}
		} 
	}
	
	function closeAllNav() {
		closeNav();
		closeNav1();
		closeNav3();
		closeNav5();
		closeNavEdu();
	}
	
	function deletework() {
		var selectedRows = gridOptions1.api.getSelectedRows();
		var selectedRowsString = selectedRows[0].employeeworkId;
		
	
		if (selectedRowsString) {
			var id = $('#employeeId').val();
	
			$.ajax({
				type: "GET",
				url: "view-manage-employee-work-delete?id=" + selectedRowsString,
				success: function(response) {
					if (response.message == "Success") {
						closeNav1();
						/*agGrid.simpleHttpRequest({
							url: 'view-manage-employee-work-ajax?id=' + id
						}).then(function(data) {
		
							gridOptions1.api.setRowData(data);
						});*/
						viewWorkDetails(id);
						editemppersonal(id);
						toastr.rror("Data deleted successfully");
					}
				},
				error: function(data) { }
			})
		} else {
			$('#reqwork').modal('toggle');
		}
	}
	
	//Functin for Edit work Details
	
	
	//EMPLOYEE BENIFITS STARTS
	
	function savebenifit(dataset) {
		$('.loader').show();
		$("body").addClass("overlay");
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-benifit",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success("Benefits Saved Successfully");
				}
	
			},
			error: function(data) {
			}
		}) //ajax ends
	}
	
	//EMPLOYEE BENIFITS ENDS
	//EMPLOYEE DEPENDENT STARTS
	var columnDefs2 = [{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30
	},
	{
		headerName: "First Name",
		field: "dfirstName",
		cellRenderer: function(params) {
			return '<a onclick=editDependent("' +
				params.data.dependentId +
				'") href="javascript:void(0)"> ' +
				params.data.dfirstName + '  ' + '<i class="bi bi-pencil-square"></i></a>';
		}
	}, {
		headerName: "Last Name",
		field: "dlastName",
	}, {
		headerName: "Date Of Birth",
		field: "depdob",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Gender",
		field: "gender",
	}, {
		headerName: "Relationship",
		field: "relationshipName",
	},];
	
	var gridOptions2 = {
		columnDefs: columnDefs2,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 250,
			height: 10
		},
		rowSelection: 'multiple',
		suppressRowClickSelection: true,
		onSelectionChanged: onSelectionChangeddependent
	};
	
	function openNav2() {
		$('#saveEmployee').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", true);
		$('#dadd').attr("disabled", true);
		$('.formValidation').remove();
		$('#deptId').val("");
		$('#typednmaeid_').val("");
		$('#typelnameid').val("");
		$('#typeDOBidd_').val("");
		$('#genderidd_').val("");
		$('#relationshipdid_').val("");
		document.getElementById("mySidenav2").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto;bottom: 100px;";
		document.getElementById("main").style.width = "70%";
	}
	
	function closeNav2() {
	
		var id = $("#employeeId").val();
		$('#saveEmployee').attr("disabled", false);
		$('#saveEmployee').attr("disabled", false);
		$('#dadd').attr("disabled", false);
		$('#ddelete').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", false);
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-dependent-ajax?id=' + id
		}).then(function(data) {
		});
	}
	
	function submitdependent(dataset) {
		closeNav2();
		var id = $("#employeeId").val();
		$.ajax({
			type: "POST",
			url: "view-manage-employee-dependent-save",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.code == "success") {
					agGrid.simpleHttpRequest({
						url: 'view-manage-employee-dependent-ajax?id=' + id
					}).then(function(data) { });
					toastr.rror("Data saved successfully");
				}
			},
			error: function(response) { }
		})
	}
	
	function onSelectionChangeddependent() {
		var selectedRows = gridOptions2.api.getSelectedRows();
		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			$('#ddelete').attr("disabled", false);
			$('#dadd').attr("disabled", true);
		} else {
			$('#ddelete').attr("disabled", true);
			$('#dadd').attr("disabled", false);
		}
	}
	
	function deleteDEPENDENT() {
		var selectedRows = gridOptions2.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += '' + selectedRow.dependentId + '';
		});
		if (selectedRowsString) {
			var id = $('#employeeId').val();
			$.ajax({
				type: "GET",
				url: "view-manage-employee-dependent-delete?id=" + selectedRowsString,
				success: function(response) {
					if (response.code == "success") {
						closeNav2();
						agGrid.simpleHttpRequest({
							url: 'view-manage-employee-dependent-ajax?id=' + id
						}).then(function(data) {
						});
						toastr.rror("Data deleted successfully");
					}
				},
				error: function(data) { }
			})
		} else {
			$('#reqDEPDNT').modal('toggle');
		}
	}
	
	//Functin for Edit Dependent  Details
	function editDependent(id2) {
		var sid = $("#sessionId").val();
		var empid = $('#employeeId').val();
		var srole = $("#adRole").val();
		if (empid == sid) {
			editDependentDetails(id2)
		} else {
			if (srole != "") {
				editDependentDetails(id2)
			} else {
				toastr.rror("It is eligible for self and Admin!");
			}
		}
	}
	
	function editDependentDetails(id2) {
		$.ajax({
			type: "GET",
			url: "view-manage-employee-dependant-edit?id=" + id2,
			async: false,
			success: function(response) {
				if (response.message == "Success") {
					openNav2();
					$("#myGrid2").show();
					$("#submitdependent").show();
					$("#deptId").val(id2);
					$("#typednmaeid_").val(response.body.dfirstName);
					$("#typelnameid").val(response.body.dlastName);
					$("#typeDOBidd_").val(response.body.depdob);
					$("#typeDOBiddCalendar").val(response.body.depdob);
					$("#genderidd_").val(response.body.gender);
					$("#relationshipdid_").val(response.body.drelationship);
	
				}
	
			}
		});
	}
	
	//EMPLOYEE DEPENDENT ENDS
	
	//EMPLOYEE BAN DETAILS STARTS
	
	var columnDefs3 = [{
		headerCheckboxSelection: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	}, {
		headerName: "Account No",
		field: "ebankAccountNo",
		width: 150,
		/*cellRenderer: function(params) {
			return '<a onclick=editBankDetails("' +
				params.data.ebankId +
				'") href="javascript:void(0)"> ' +
				params.data.ebankAccountNo + '  ' + '<i class="bi bi-pencil-square"></i></a>';
		}*/
	}, {
		headerName: "Bank Name",
		field: "ebankName",
		width: 200,
	}, {
		headerName: "BRANCH NAME",
		field: "ebankAddress",
		width: 150,
	}, {
		headerName: "IFSC CODE",
		field: "eIfic",
		width: 100,
	}, {
		headerName: "Document",
		field: "ebankDocument",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			var div = "";
			if (params.data.ebankDocument) {
				var ext = params.data.ebankDocument.split(".");
				if (ext[1] == "pdf") {
					div = div +
						" " +
						'<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("' + params.data.ebankDocument + '")> </div>';
				} else {
					div = div +
						" " +
						'<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImage("' + params.data.ebankDocument + '")> </div>';
				}
			}
			return div;
		},
	},];
	
	var gridOptions3 = {
		columnDefs: columnDefs3,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 185,
			height: 10
		},
		rowSelection: 'single',
		suppressRowClickSelection: true,
		onSelectionChanged: onSelectionChangedBank
	};
	function onSelectionChangedBank() {
		var selectedRows = gridOptions3.api.getSelectedRows();
		var id = selectedRows.map(row => row.ebankId);
	
		var rowCount = selectedRows.length;
		if (rowCount > 0) {
			$(".br-dis").attr("disabled", false);
	
		} else {
			$(".br-dis").attr("disabled", true);
		}
	}
	
	function openNav3() {
		
		if ($("#adRole").val() != "") {
	     $("#profilePrev").hide();
		$("#profileNext").hide();
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
		enableAllField();
		$(".formValidation").remove();
		$('#saveEmployee').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", true);
		$('#bankid').val("");
		$('#banknameid').val("");
		$('#addressid_b').val("");
		$('#addressid_b').val("");
		$('#countryid_b').val("");
		$('#stateid_b').val("");
		$('#cityid').val("");
		$('#accountNOb').val("");
		$('#ificb').val("");
		$('#uploadDoc_2').empty();
		$('#imageName_2').empty();
		$('#uploadedBillDiv_2').empty();
		$("#bankDiv").show();
		$('#myGrid3').hide();
		$('.br-s-btn').show();
		$('.br-m-btn').hide();
		}else{
		toastr.error("Only Admin can Add bank details!")	
		}
	}
	
	function closeNav3() {
		
		$("#profilePrev").show();
		$("#profileNext").show();
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
		$("#saveBank").hide();
		$("#cancelBank").hide();
		$("#editProfile").hide();
		$("#saveProfile").hide();
		$("#profileAdd").hide();
		$("#profileNext").show();
		$("#profilePrev").show();
		$("#profileClose").hide();
	
		$('#bankid').val("");
		$('#banknameid').val("");
		$('#addressid_b').val("");
		$('#addressid_b').val("");
		$('#countryid_b').val("");
		$('#stateid_b').val("");
		$('#cityid').val("");
		$('#accountNOb').val("");
		$('#ificb').val("");
		$('#uploadDoc_2').empty();
		$('#imageName_2').empty();
		$('#uploadedBillDiv_2').empty();
		$("#bankDiv").hide();
		$('#myGrid3').show();
		$('.br-s-btn').hide();
		$('.br-m-btn').show();
	}
	
	//IFSC Validation
	
	var ifscValid;
	function ifscVal() {
		var ifsc = $('#ificb').val();
	
		var ifscid = /[A-Z]{4}[0][A-Z0-9]{6}$/;
		var format = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
		if (ifsc != '' && ifsc.toUpperCase() === ifsc && !format.test(ifsc)) {
			if (ifscid.test(ifsc)) {
	
				$("#error13").hide();
				ifscValid = true;
				return true;
			} else {
				$("#error13").show();
				$("#error13").html("Please enter a valid IFSC code");
				ifscValid = false;
				return false;
			}
		} else {
			$('#ificb').val("");
			$("#error13").hide();
			ifscValid = false;
			return false;
		}
	}
	
	
	
	
	//EMPLOYEE BANK DETAILS ENDS
	
	//EMPLOYEE INSURANCE DETAILS STARTS
	
	var columnDefs4 = [{
		headerName: "Insurance Type",
		field: "einsurancetype",
		cellRenderer: function(params) {
			return '<a onclick=editInsuranceDetails("' +
				params.data.einsuraneId +
				'") href="javascript:void(0)"> ' +
				params.data.einsurancetype + '  ' + '<i class="bi bi-pencil-square"></i></a>';
		}
	}, {
		headerName: "Insurance Company",
		field: "einsurancecompany",
	}, {
		headerName: "Insurance Policy No",
		field: "policyno",
		type: "rightAligned"
	
	}, {
		headerName: "Insurance From Date",
		field: "eifromdate",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Insurance To Date",
		field: "eitodate",
		cellStyle: {
			textAlign: 'center'
		}
	},
	];
	
	var gridOptions4 = {
		columnDefs: columnDefs4,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 249,
			height: 10
		},
		rowSelection: 'single',
		onSelectionChanged: onSelectionChangedinsurance,
		suppressRowClickSelection: true,
	};
	
	function closeNav4() {
		
		$("#profilePrev").show();
		$("#profileNext").show();
		var id = $("#employeeId").val();
	
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
		$("#salSavebtn").prop('disabled', false);
	
	
		$("#salDept").hide();
		$('#saveEmployee').attr("disabled", false);
		$('#profilecancelbtn').attr("disabled", false);
		$("#salaryGridDiv").show();
		$("#salaryModal").hide();
		$('.br-s-btn').hide();
		$('.br-m-btn').show();
	}
	
	function openNav4() {
		
		
		
		if ($("#adRole").val() != "") {
			
		$("#profilePrev").hide();
		$("#profileNext").hide();
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
		enableAllField();
		$(".formValidation").remove();
		$("#editId").val("");
		$('#save1').attr("disabled", false);
		$("#salDept").hide();
		$("#salaryModal").show();
		$("#salaryGridDiv").hide();
		$('.br-s-btn').show();
		$('.br-m-btn').hide();
		var id = $("#employeeId").val();
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-work-ajax?id=' + id
		}).then(function(data) {
			$("#doj").attr('disabled', true);
			$("#dojDateCalendar").attr('disabled', true);
			$("#empId").val(data[0].employeeId);
			$("#band").val(data[0].bandid);
			$("#ndesg").val(data[0].deginationid);
			$("#semp").removeClass("select");
			$("#name").val(data[0].createdBy);
			$("#pdesg").val(data[0].deginationid);
			$("#ndesg").val(data[0].deginationid);
			$("#dept").val(data[0].departmentid);
			$("#subDept").val(data[0].subdepartmentid);
			$("#doj").val($("#joiningdate").val());
			getBandData();
		});
		$("#effectiveDateFrom").val("");
		$("#effectiveDateTo").val("");
		$("#ctc").val("");
		$("#basic").val("");
		$("#providentFund").val("");
		$("#hra").val("");
		$("#esi").val("");
		$("#convAllow").val("");
		$("#pTax").val("");
		$("#specialallowance").val("");
		$("#salAdv").val("");
		$("#skillDev").val("");
		$("#wFund").val("");
		$("#medAllow").val("");
		$("#insAmt").val("");
		$("#washAllow").val("");
		$("#lic").val("");
		$("#bonus").val("");
		$("#socy").val("");
		$("#overTime").val("");
		$("#fine").val("");
		$("#misc").val("");
		$("#damage").val("");
		$("#da").val("");
		$("#tds").val("");
		$("#otherEarn").val("");
		$("#otherDeduct").val("");
		$("#esicWage").val("");
		$("#mEmployerPf").val("");
	
		$("#totalEarn").val("");
		$("#totalDeduct").val("");
		$("#totalContribution").val("");
		$("#netPay").val("");
	
	}else{
		toastr.error("Only Admin can add salary details!");
	}
	}
	function submitinsurance(dataset) {
	
		var id = $("#employeeId").val();
		$.ajax({
			type: "POST",
			url: " view-manage-employee-insurancedetails-save",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.message == "Success") {
					agGrid.simpleHttpRequest({
						url: 'view-manage-employee-insurancedetails-ajax?id=' + id
					}).then(function(data) {
					});
					toastr.rror("Data saved successfully");
				}
			},
			error: function(response) { }
		})
	}
	
	function onSelectionChangedinsurance() {
		var selectedRows = gridOptions4.api.getSelectedRows();
	
		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
		if (rowCount > 0) {
			if ($("#adRole").val() != "") {
				$('#idelete').attr("disabled", false);
				$('#iadd').attr("disabled", true);
			} else {
				$('#idelete').attr("disabled", true);
				$('#iadd').attr("disabled", false);
			}
	
		}
	}
	
	function deleteinsurance() {
		var selectedRows = gridOptions4.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += '"' + selectedRow.einsuraneId + '"';
		});
		if (selectedRowsString) {
			var id = $('#employeeId').val();
			$.ajax({
				type: "GET",
				url: "view-manage-employee-insurancedetails-delete?id=" + selectedRowsString,
				success: function(response) {
					if (response.message == "Success") {
						agGrid.simpleHttpRequest({
							url: 'view-manage-employee-insurancedetails-ajax?id=' + id
						}).then(function(data) {
						});
						toastr.rror("Data deleted successfully");
					}
				},
				error: function(data) { }
			})
		} else {
			$('#reqinsu').modal('toggle');
		}
	}
	
	//Functin for Edit Insurance Details
	function editInsuranceDetails(id) {
	
		if ($("#adRole").val() != "") {
			$.ajax({
				type: "GET",
				url: "view-manage-employee-insurance-edit?Id=" + id,
				async: false,
				success: function(response) {
					if (response.message == "Success") {
						openNav4();
						$("#myGrid4").show();
						$("#submitinsurance").show();
						$("#insuranceId").val(id);
						$("#healthinsuranceid").val(response.body.einsurancetype);
						$("#incurancecompanyid").val(response.body.einsurancecompany);
						$("#ploicyNo_").val(response.body.policyno);
						$("#pfdateid_").val(response.body.eifromdate);
						$("#todates").val(response.body.eitodate);
					}
	
				}
			});
		} else {
			toastr.error("Only Admin can modify bank details!");
		}
	
	}
	
	
	function openNav5() {
		$("#profilePrev").hide();
		$("#profileNext").hide();
		$('#profilePrev').attr("disabled", true);
		$('#profileNext').attr("disabled", true);
		enableAllField();
		$('#statusDoc').hide();
		getDocumentTypeList();
		$('#docUploadNameDiv').hide();
		$('#saveEmployee').attr("disabled", true);
		$('#profilecancelbtn').attr("disabled", true);
		$('#docUploadName').val("");
		$('#documentType').val("");
		$('#imageName_0').html("");
		$('#docStatus').val('1');
		$("#uploadedBillDiv_0").html("");
		$(".formValidation").remove();
		$("#documentType").attr('disabled', false);
		$("#addDocTypeIcon").show();
		$("#ddtype").addClass("select");
		$("#docDiv").show();
		$('#myGrid5').hide();
		$('.br-s-btn-p').show();
		$('.br-m-btn-p').hide();
	}
	
	function closeNav5() {
		
		$("#profilePrev").show();
		$("#profileNext").show();
	
		$('#profilePrev').attr("disabled", false);
		$('#profileNext').attr("disabled", false);
		$('#docUploadName').val("");
		$('#documentType').val("");
		$('#imageName_0').html("");
		$('#docStatus').val('1');
		$("#uploadedBillDiv_0").html("");
		$("#docDiv").hide();
		$('#myGrid5').show();
		//gridOptions5.api.deselectAll();
		$('.br-s-btn-p').hide();
		$('.br-m-btn-p').show();
	}
	
	
	function deleteDocument() {
		var selectedRows = gridOptions5.api.getSelectedRows();
		var docType = selectedRows[0].documentType;
		if (docType) {
			var id = $('#employeeId').val();
			$.ajax({
				type: "GET",
				url: "view-manage-employee-document-delete?docType=" + docType + "&empid=" + id,
				success: function(response) {
					if (response.message == "Success") {
						$('#docdelete').attr("disabled", true);
						$('#docadd').attr("disabled", false);
						
						getAllDocsByEmpId(id);
						toastr.rror("Data deleted successfully");
					}
				},
				error: function(data) { }
			})
		} else {
			$('#reqinsu').modal('toggle');
		}
	}
	
	
	function deleteFileDocument() {
		$('#fileUpload').val("");
		$('#imgemp').attr('src', '');
		$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	
		var fileData = new FormData();
	
		fileData.append('file', 'none');
		fileData.append('path', 'none');
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-delete-file",
			enctype: "multipart/form-data",
			contentType: false,
			/* data        : fileData, */
			processData: false,
			cache: false,
			success: function(response) {
	
				$('#uploadDoc_0').empty();
				$('#imageName_0').empty();
				$('#uploadedBillDiv_0').empty();
				$("#imageName_0").html("");
	
			}
		});
	}
	
	function getDocumentTypeList() {
		var empid = $("#employeeId").val();
		if (empid) {
			$.ajax({
				type: "GET",
				url: "view-manage-employee-documenttype-list?empid=" +
					empid,
				contentType: false,
				success: function(response) {
					if (response.message == "success") {
						$("#documentType").empty();
						$("#documentType")
							.append("<option value=''>Select</option>");
						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#documentType").append(option);
						}
					}
				},
				error: function(data) {
					$("#documentType").empty();
					$("#documentType").append("<option value=''>Select</option>");
	
				}
			})
		} else {
			$("#documentType").empty();
			$("#documentType").append("<option value=''>Select</option>");
		}
	
	}
	
	function deleteFile2() {
		$('#fileUpload').val("");
		$('#imgemp').attr('src', '');
		$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	
		var fileData = new FormData();
	
		fileData.append('file', 'none');
		fileData.append('path', 'none');
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-delete-file",
			enctype: "multipart/form-data",
			contentType: false,
			/* data        : fileData, */
			processData: false,
			cache: false,
			success: function(response) {
	
				$('#uploadDoc_2').empty();
				$('#imageName_0').empty();
				$('#imageName_2').empty();
				$('#uploadedBillDiv_2').empty();
				$("#imageName_2").html("");
				$(".image-delete-icon2").hide();
	
			}
		});
	}
	
	function viewImage(id) {
		window.open("/document/employee/" + id, '_blank');
	}
	
	function viewImageCCR(id) {
		window.open(id, '_blank');
	}
	
	//setup the grid after the page has finished loading
	document.addEventListener('DOMContentLoaded', function() {
	
		var gridDivm = document.querySelector('#myGridEmployee');
		new agGrid.Grid(gridDivm, gridOptionsEmployee);
	
		/*var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);*/
	
		var gridDiv1 = document.querySelector('#myGrid1');
		new agGrid.Grid(gridDiv1, gridOptions1);
	
		/*var gridDiv2 = document.querySelector('#myGrid2');
		new agGrid.Grid(gridDiv2, gridOptions2);*/
	
		var gridDiv3 = document.querySelector('#myGrid3');
		new agGrid.Grid(gridDiv3, gridOptions3);
	
		/*var gridDiv4 = document.querySelector('#myGrid4');
		new agGrid.Grid(gridDiv4, gridOptions4);*/
	
	});
	
	
	function downloadEmpDetails() {
		var dataset = [];
		gridOptionsEmployee.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			dataset.push(rowNode.data);
	
		});
		gridOptionsEmployee.api.exportDataAsCsv(dataset);
	}
	
	function downloadEmpWorkDetails() {
		var dataset = [];
		gridOptions1.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			dataset.push(rowNode.data);
	
		});
		gridOptions1.api.exportDataAsCsv(dataset);
	}
	
	function downloadEmpAddressDetails() {
		var dataset = [];
		gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode,
			index) {
			var item = rowNode.data;
			item.employeeId = item.employeeId;
			dataset.push(item);
		});
		gridOptions.api.exportDataAsCsv(dataset);
	}
	
	function downloadEmpBankDetails() {
		var dataset = [];
		gridOptions3.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			dataset.push(rowNode.data);
	
		});
		gridOptions3.api.exportDataAsCsv(dataset);
	}
	
	function downloadInsuranceDetails() {
		var dataset = [];
		gridOptions4.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			dataset.push(rowNode.data);
	
		});
		gridOptions4.api.exportDataAsCsv(dataset);
	}
	
	function downloadSalayDetails() {
		var dataset = [];
		gridOptions6.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			dataset.push(rowNode.data);
	
		});
		gridOptions6.api.exportDataAsCsv(dataset);
	}
	
	function setFromToDate() {
		toastr.rror("Please choose to date greater than or equal to from date ");
		$("#fromdateid_").val("");
		$("#todateid_").val("");
	}
	
	function dateChange() {
	
		var fromdate = $('#fromdateid_').val();
		var todate = $('#todateid_').val();
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
	
	function cancelResetPass() {
		$("#oldPassword").val('');
		$("#newPassword").val('');
		$("#confirmPassword").val('');
		$("#collapseTen").removeClass('show');
		$(".accordion-button").addClass('collapsed');
		$("button").attr("aria-expanded", "false");
	}
	
	/ function for confirm password /
	var cpassValid;
	
	function cpassVal() {
	
		var password = $('#newPassword').val();
		var confPassword = $('#confirmPassword').val();
	
		if (confPassword != '') {
			if (confPassword.match(password)) {
				$("#error1").hide();
				cpassValid = true;
				return true;
			} else {
				toastr.rror("New password and confirm password should be same");
			}
		} else {
			$("#error1").html(" Confirm Password is Required");
			cpassValid = false;
			return false;
		}
	
	}
	/ function for Password Submit /
	
	function pwdSubmit() {
		$(".formValidation").remove();
		$('#modalSms').text('');
	
		var validation = true;
		var data = {};
		data.name = $("#oldPassword").val();
		data.code = $("#newPassword").val();
		var validation = true;
	
		if (data.name == null || data.name == "") {
			validation = validationUpdated("Old Password Required",
				"oldPassword");
		}
		if (data.code == null || data.code == "") {
			validation = validationUpdated("New Password Required",
				"newPassword");
		}
	
		cpassVal();
		if (validation && cpassValid) {
			$(".formValidation").remove();
			$.ajax({
				type: "POST",
				url: "view-manage-employee-resetPassword",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(response) {
					$('#modalSms1').empty();
					$('#modalSms2').empty();
					if (response.code == "success") {
						toastr.rror(response.message);
					
					} else {
	
						toastr.rror(response.message);
				
					}
				},
				error: function(response) { }
			})
		}
	}
	//function to close modal after message
	function okMessage() {
		$('#myModal').modal('hide');
		$('#modalSms1').text('');
		$('#modalSms2').text('');
		//$('#modalSms3').text('');
		cancel();
	}
	
	function okMessage1() {
		window.location.href = '/login';
	}
	
	function checkAlphabet(fieldId) {
	
		var tempVal = $("#" + fieldId).val().replace(/[^a-zA-Z. ]/g, '');
		tempVal = tempVal.replace(/^\w/, c => c.toUpperCase());
	
		const input = document.getElementById(fieldId);
		const position = input.selectionStart;
		if (position == 1 && tempVal.charAt(0) == ' ') {
			$("#" + fieldId).empty();
			tempVal = '';
		}
		$("#" + fieldId).val(tempVal);
	}
	
	
	function checkNumeric(fieldId) {
	
		var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	
		const input = document.getElementById(fieldId);
		const position = input.selectionStart;
		if (tempVal.slice(-1) == ' ') {
			$("#" + fieldId).empty();
			tempVal = '';
		}
		$("#" + fieldId).val(tempVal);
	}
	
	function dateChangee() {
	
		var fromdate1 = $('#pfdateid_').val();
		var todate1 = $('#todates').val();
	
		if (fromdate1 != '' && todate1 != '') {
			if (fromdate1 <= todate1) { } else {
				toastr.rror("Please choose greater than or equal todate");
				$('#todates').val("");
			}
		} else { }
	}
	
	function dloadQr() {
		var x = '/document/staffQrCode/';
		let url = x + qrId;
		let fileName = qrId;
		qrDownload(url, fileName);
	}
	
	function qrDownload(url, fileName) {
		var xhr = new XMLHttpRequest();
		xhr.open("GET", url, true);
		xhr.responseType = "blob";
		xhr.onload = function() {
			var urlCreator = window.URL || window.webkitURL;
			var imageUrl = urlCreator.createObjectURL(this.response);
			var tag = document.createElement('a');
			tag.href = imageUrl;
			tag.download = fileName;
			document.body.appendChild(tag);
			tag.click();
			document.body.removeChild(tag);
		}
		xhr.send();
	}
	
	function generateid() {
	
		var selectedRows = gridOptionsEmployee.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString += '"' + selectedRow.employeeId + '"';
		});
	
		window.open("/employee/view-idcard-pdf-downloads?dcId=" + window.btoa(selectedRowsString), '_blank');
	}
	
	function redirectToNewEmp() {
		sessionStorage.setItem('activity', 'ACT0005');
		window.location.href = "/employee/new-employee";
	}
	
	const columnSalDefs = [{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	
	}, {
		headerName: 'Employee Id',
		field: "empId",
		pinned: 'left',
		width: 120,
		
	
	}, {
		headerName: 'Name',
		field: "name",
		pinned: 'left',
		width: 150,
	},
	{
		headerName: ' Designation',
		field: "ndesg",
		width: 120,
	}, {
		headerName: ' Joining Date',
		field: "doj",
		width: 100,
		//$("#joiningdate").val()
	}, {
		headerName: 'Effective Date From ',
		field: "effectiveFromDate",
		width: 150
	}, {
		headerName: 'Effective Date To',
		field: "effectiveToDate",
		width: 100
	}, {
		headerName: 'Band/Grade',
		field: "band",
		width: 100
	}, {
		headerName: 'Basic',
		field: "basic",
		//	type : "rightAligned",
		width: 100
	}, {
		headerName: 'House Rent Allowance(HRA)',
		field: "hra",
		//	type : "rightAligned",
		width: 150
	}, {
		headerName: 'Conveyance Allowance',
		field: "convAllow",
		//	type : "rightAligned",
		width: 150
	}, {
		headerName: 'Washing Allowance',
		field: "washAllow",
		//	type : "rightAligned",
		width: 150
	}, {
		headerName: 'Special Allowance',
		field: "specialallowance",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'Medical Allowance',
		field: "medAllow",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'Skill Development',
		field: "skillDev",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'Gross Salary',
		field: "totalEarn",
		//	type : "rightAligned",
		width: 100
	}, {
		headerName: 'ESI Employee',
		field: "esi",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'Provident Fund',
		field: "providentFund",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'Professional Tax',
		field: "pTax",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'LIC/Insurance',
		field: "lic",
		//	type : "rightAligned",
		width: 130
	}, {
		headerName: 'Wel Fund',
		field: "wFund",
		//	type : "rightAligned",
		width: 100
	}, {
		headerName: 'Total Deduction',
		field: "totalDeduct",
		//	type : "rightAligned",
		width: 150
	}, {
		headerName: 'Net Salary',
		field: "netPay",
		//	type : "rightAligned",
		width: 120
	}, {
		headerName: 'ESI Employer',
		field: "esicWage",
		//	type : "rightAligned",
		width: 120
	}, {
		headerName: 'Employer Provident Fund',
		field: "mEmployerPf",
		//	type : "rightAligned",
		width: 150
	}, {
		headerName: 'Status',
		field: "status",
		//	type : "rightAligned",
		width: 150,
		hide: true
	}
	];
	
	const gridOptions6 = {
		columnDefs: columnSalDefs,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 180,
			height: 10,
			rowSelection: 'single',
		},
	
		onSelectionChanged: SalaryRowSelect,
	}
	
	function SalaryRowSelect() {
		var selectedRows = gridOptions6.api.getSelectedRows();
		var selectedData = selectedRows.map(node => node.data);
		//	var empid = selectedData.map(node => node.employeeId);
		console.log("DATA>>>=======", selectedRows);
		var rowCount = 0;
		selectedRows.forEach(function(i) {
			rowCount = rowCount + 1;
		});
	
		if (rowCount > 0) {
			var sts = selectedRows[0].status;
			if (sts != 0) {
				$('#approve').attr("disabled", true);
	
			} else {
				$('#approve').attr("disabled", false);
	
			}
			$('#saladd').attr("disabled", true);
			$('.br-dis').attr("disabled", false);
	
		} else {
			$('.br-dis').attr("disabled", true);
			$('#approve').attr("disabled", true);
		}
	
	}
	
	function approve() {
		$("#approveModal").removeClass('d-none');
	}
	
	function approveClose() {
		$("#approveModal").addClass('d-none');
	}
	
	function approveSalary(data) {
	
		var status = 0;
		if (data == 1) {
			status = 1;
		} else if (data == 2) {
			status = 2;
		}
		var selectedRows = gridOptions6.api.getSelectedRows();
		var id = selectedRows[0].editId;
		var id1 = selectedRows[0].empId;
		$.ajax({
			type: "GET",
			url: "view-manage-employee-approve?id=" + id + "&sts=" + status,
			success: function(response) {
				if (response.code == "success") {
					toastr.rror(response.message);
					$("#approveModal").addClass('d-none');
					$('#approve').attr("disabled", true);
					
					getSalaryDetailsByEmpId(id);
					approveClose()
				} else {
					$("#messageParagraph").text("Something went to wrong!");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(data) { }
		})
	}
	
	function getBandData() {
		var band = $("#band").val();
		var emmpid = $("#employeeId").val();
		if (band) {
			agGrid.simpleHttpRequest({
				url: 'view-manage-employee-bandcalc?band=' + band+'&empid='+emmpid,
				async: false
			}).then(function(data) {
				var jsonData = JSON.parse(data.body);
				var allData = jsonData.bandData;
				var ptData = jsonData.ptData;
				console.log("ptData===",ptData)
				allData.forEach(function(rowNode, index) {
	
					if (rowNode.component == 'EEM007') {
						putData('#providentFund', rowNode.amount, rowNode.calctype, rowNode.visibility,'');
					} else if (rowNode.component == 'EEM008') {
						putData('#esi', rowNode.amount, rowNode.calctype, rowNode.visibility,'');
					} else if (rowNode.component == 'EEM010') {
						putData('#mEmployerPf', rowNode.amount, rowNode.calctype, rowNode.visibility,'');
					} else if (rowNode.component == 'EEM011') {
						putData('#esicWage', rowNode.amount, rowNode.calctype, rowNode.visibility,'');
					} else if (rowNode.component == 'EEM006') {
						putData('#pTax', rowNode.amount, rowNode.calctype, rowNode.visibility,ptData);
						
					} else if (rowNode.component == 'EEM040') {
						putData('#wFund', rowNode.amount, rowNode.calctype, rowNode.visibility,'');
					}
				});
			});
		} else {
			var validation = validationUpdated("Band is required", "band");
		}
	}
	
	function putData(dataId, amount, calctype, visible, ptdatas) {
		console.log("ptdatas",ptdatas)
		if (visible == 'E') {
			$(dataId + 'div').show();
		} else {
			$(dataId + 'div').hide();
			$(dataId).val('0');
		}
	
		if (calctype == '3') {
			var ctc = $("#ctc").val();
			var total = (parseFloat(ctc) / 100) * amount;
			$(dataId).val(parseFloat(total).toFixed(2));
		} else if (calctype == '2') {
	
			var basic = $("#basic").val();
			var earings = $("#totalEarn").val();
			if ($("#basic").val()) {
				if (dataId == '#providentFund' || dataId == '#mEmployerPf') {
					if (basic <= 15000) {
						var total = (parseFloat(basic) / 100) * amount;
						$(dataId).val(parseFloat(total).toFixed(0));
					} else {
						$(dataId).val(parseFloat(1800).toFixed(0));
					}
				} else if (dataId == '#esi' || dataId == '#esicWage') {
					if (earings <= 21000) {
						var total = (parseFloat(earings) / 100) * amount;
						$(dataId).val(parseFloat(total).toFixed(0));
					} else {
						$(dataId).val(parseFloat(0).toFixed(0));
					}
				} else if (dataId == '#pTax') {
					console.log("ptdatas",ptdatas);
					const firstEntry = ptdatas[0];
					const secondEntry = ptdatas[1];
					const thirdEntry = ptdatas[2];

					var earings1 = $("#totalEarn").val();
					if (earings1 <= firstEntry.range2) {
						$(dataId).val(parseFloat(firstEntry.amount).toFixed(0));
					} else if (earings1 >= secondEntry.range1 && earings <= secondEntry.range2) {
						$(dataId).val(parseFloat(secondEntry.amount).toFixed(0));
					} else if (earings1 >= thirdEntry.range1) {
						$(dataId).val(parseFloat(thirdEntry.amount).toFixed(0));
					} else {
						$(dataId).val(parseFloat(0).toFixed(0));
					}
				} else if (dataId == '#wFund') {
					var earings = $("#totalEarn").val();
					var total = (parseFloat(earings) / 100) * amount;
					$(dataId).val(parseFloat(total).toFixed(0));
				} else {
					var total = (parseFloat(basic) / 100) * amount;
					$(dataId).val(parseFloat(total).toFixed(0));
				}
			} else {
				$(dataId).val("");
			}
	
		} else if (calctype == '1') {
			$(dataId).val(parseFloat(amount).toFixed(2));
		} else if (calctype == '4') {
			var total = parseInt($("#ctc").val()) - (parseInt($("#basic").val()) + parseInt($("#hra").val()) + parseInt($("#addAllow").val()) + parseInt($("#conve").val()) + parseInt($("#medical").val()) + parseInt($("#washAllow").val()) + parseInt($("#lta").val()) + parseInt($("#skillDev").val()));
			if (total > 0) {
				$(dataId).val(parseFloat(total).toFixed(2));
			} else {
				$(dataId).val('0.0');
			}
		}
		updateTotalEarning();
		updateTotalDeducts();
		updateTotalContribution();
		updateNetPay();
	}
	
	function updateTotalEarning() {
		var bandSalary = $("#band").val();
		if (bandSalary == '' || bandSalary == null) {
			toastr.rror("Please Choose Band First!!");
		} else {
			var basic = parseFloat(document.getElementById("basic").value) || 0;
			var hra = parseFloat(document.getElementById("hra").value) || 0;
			var convAllow = parseFloat(document.getElementById("convAllow").value) || 0;
			var specialallowance = parseFloat(document.getElementById("specialallowance").value) || 0;
			var skillDev = parseFloat(document.getElementById("skillDev").value) || 0;
			var medAllow = parseFloat(document.getElementById("medAllow").value) || 0;
			var washAllow = parseFloat(document.getElementById("washAllow").value) || 0;
	
			var totalEarnings = basic + hra + convAllow + specialallowance + skillDev +
				medAllow + washAllow;
	
			$("#totalEarn").val(totalEarnings);
		}
	}
	
	function updateTotalDeducts() {
		var bandSalary = $("#band").val();
		if (bandSalary == '' || bandSalary == null) {
			toastr.rror("Please Choose Band First!!");
		} else {
			var providentFund = parseFloat(document.getElementById("providentFund").value) || 0;
			var esi = parseFloat(document.getElementById("esi").value) || 0;
			var pTax = parseFloat(document.getElementById("pTax").value) || 0;
			var wFund = parseFloat(document.getElementById("wFund").value) || 0;
			var lic = parseFloat(document.getElementById("lic").value) || 0;
	
			var totalDeductss = providentFund + esi + pTax + lic + wFund;
			$("#totalDeduct").val(totalDeductss);
		}
	}
	
	function updateTotalContribution() {
		var esicWage = parseFloat(document.getElementById("esicWage").value) || 0;
		var mEmployerPf = parseFloat(document.getElementById("mEmployerPf").value) || 0;
	
		var totalContribution = esicWage + mEmployerPf;
		$("#totalContribution").val(totalContribution);
	}
	
	function updateNetPay() {
		var bandSalary = $("#band").val();
		if (bandSalary == '' || bandSalary == null) {
			toastr.rror("Please Choose Band First!!");
		} else {
			var basic = parseFloat(document.getElementById("basic").value) || 0;
			var hra = parseFloat(document.getElementById("hra").value) || 0;
			var convAllow = parseFloat(document.getElementById("convAllow").value) || 0;
			var specialallowance = parseFloat(document.getElementById("specialallowance").value) || 0;
			var skillDev = parseFloat(document.getElementById("skillDev").value) || 0;
			var medAllow = parseFloat(document.getElementById("medAllow").value) || 0;
			var washAllow = parseFloat(document.getElementById("washAllow").value) || 0;
	
			var totalEarnings = basic + hra + convAllow + specialallowance + skillDev +
				medAllow + washAllow;
	
			var providentFund = parseFloat(document.getElementById("providentFund").value) || 0;
			var esi = parseFloat(document.getElementById("esi").value) || 0;
			var pTax = parseFloat(document.getElementById("pTax").value) || 0;
			var lic = parseFloat(document.getElementById("lic").value) || 0;
			var wFund = parseFloat(document.getElementById("wFund").value) || 0;
	
			var totalDeductss = providentFund + esi + pTax + lic + wFund;
	
			var netpayss = totalEarnings - totalDeductss;
			$("#netPay").val(netpayss);
	
		}
	}
	
	function saveSalData() {
	
		var data1 = {};
		var datas = [];
		var validation = true;
		data1.editId = $("#editId").val();
		data1.empId = document.getElementById("empId").value;
		data1.name = document.getElementById("name").value;
		data1.subdept = $("#subDept").val();
		data1.dept = $("#dept").val();
		data1.ndesg = document.getElementById("ndesg").value;
		data1.band = $("#band").val();
		data1.joiningDate = $("#doj").val();
	
		var fromDate = document.getElementById("effectiveDateFrom").value;
		if (fromDate == '' || fromDate == null) {
			toastr.error("Add Effective From Date First!!");
			validation = false;
			return;
		} else {
			data1.effectiveFromDate = document.getElementById("effectiveDateFrom").value;
		}
		var toDate = document.getElementById("effectiveDateTo").value;
		if (toDate == '' || toDate == null) {
			toastr.error("Add Effective To Date !!");
			validation = false;
		} else {
			data1.effectiveToDate = document.getElementById("effectiveDateTo").value;
		}
		data1.basic = parseFloat(document.getElementById("basic").value) || 0;
		data1.providentFund = parseFloat(document.getElementById("providentFund").value) || 0;
	
		data1.hra = parseFloat(document.getElementById("hra").value) || 0;
		data1.esi = parseFloat(document.getElementById("esi").value) || 0;
	
		data1.convAllow = parseFloat(document.getElementById("convAllow").value) || 0;
		data1.pTax = parseFloat(document.getElementById("pTax").value) || 0;
	
		data1.specialallowance = parseFloat(document.getElementById("specialallowance").value) || 0;
	
		data1.skillDev = parseFloat(document.getElementById("skillDev").value) || 0;
		data1.wFund = parseFloat(document.getElementById("wFund").value) || 0;
	
		data1.medAllow = parseFloat(document.getElementById("medAllow").value) || 0;
	
		data1.washAllow = parseFloat(document.getElementById("washAllow").value) || 0;
		data1.lic = parseFloat(document.getElementById("lic").value) || 0;
	
		data1.mEmployerPf = parseFloat(document.getElementById("mEmployerPf").value) || 0;
		data1.esicWage = parseFloat(document.getElementById("esicWage").value) || 0;
	
		data1.totalContribution = parseFloat(document.getElementById("totalContribution").value) || 0;
		data1.totalEarn = parseFloat(document.getElementById("totalEarn").value) || 0;
		data1.totalDeduct = parseFloat(document.getElementById("totalDeduct").value) || 0;
	
		data1.netPay = parseFloat(document.getElementById("netPay").value) || 0;
	
		if (validation) {
	
			$.ajax({
				type: "POST",
				url: "view-manage-employee-salary-revision-save",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(data1),
				success: function(response) {
					if (response.code == "Success") {
						closeNav4();
						toastr.success(response.message);
						getSalaryDetailsByEmpId($("#empId").val());
					}
				},
				error: function(response) { }
			})
		}
	}
	
	function putDataShow(dataId, amount, calctype, visible) {
		if (visible == 'E') {
			$(dataId + 'div').show();
		} else {
			$(dataId + 'div').hide();
			$(dataId).val('0');
		}
	}
	
	function addOtherDocs() {
		var documentType = $("#documentType").val();
		if (documentType == 'Others') {
			$('#docUploadNameDiv').show();
		}
	}
	
	function savePermanent() {
		$("#permanemtSame").modal('show');
	}
	
	function addNewDocument() {
		$("#newParameterName").val('');
		$('#shippingInfo').removeClass("d-none");
	}
	
	function cancelBtn2() {
		$("#newParameterName").val('');
		$('#shippingInfo').addClass("d-none");
	}
	
	function addParameterInfo() {
		var obj = {};
		obj.documentName = $("#newParameterName").val();
		var validation = true;
		if (obj.newParameterName == null || obj.newParameterName == "") {
			validation = validationUpdated("Parameter Name Required", "newParameterName");
		}
		if (validation) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-add-document",
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(response) {
					if (response.message == "Success") {
						$('.loader').hide();
						$(document).ready(function() {
							getDocumentTypeList();
						});
						toastr.rror("Document Type Added Successfully");
						$('#shippingInfo').addClass("d-none");
	
					} else { }
				},
				error: function(data) {
					$('.loader').hide();
				}
			})
		}
	
	}
	
	function addNewDocument1() {
	
		$(".formValidation").remove();
		$("#newQualify").val('');
		$('#shippingInfo1').removeClass("d-none");
	}
	
	function cancelBtn21() {
		$("#newQualify").val('');
		$('#shippingInfo1').addClass("d-none");
	}
	
	function addQualificationInfo() {
		var obj = {};
		obj.qualification = $("#newQualify").val();
		var validation = true;
		if (obj.qualification == null || obj.qualification == "") {
			validation = validationUpdated("Parameter Name Required", "newQualify");
		}
		if (validation) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-add-qualify",
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(response) {
					if (response.message == "Success") {
						$('.loader').hide();
						$(document).ready(function() {
							getQualifyTypeList();
							cancelBtn21();
						});
						toastr.rror("Qualification Type Added Successfully");
						$('#shippingInfo').addClass("d-none");
	
					} else { }
				},
				error: function(data) {
					$('.loader').hide();
				}
			})
		}
	
	}
	
	function getQualifyTypeList(id = null) {
		$.ajax({
			type: "GET",
			url: "view-manage-employee-qualify-list",
			success: function(response) {
				if (response.message == "Success") {
					$("#qualification").empty();
					$("#qualification")
						.append("<option value=''>Select</option>");
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#qualification").append(option);
					}
					if (id != null) {
						$('#qualification').val(id);
					}
				}
			},
			error: function(data) {
				$("#qualification").empty();
				$("#qualification").append("<option value=''>Select</option>");
	
			}
		})
	
	}
	
	function addNewCCRType() {
	
		$(".formValidation").remove();
		$("#newCCRType").val('');
		$('#addTypeField').removeClass("d-none");
	}
	
	function cancelBtnCCRType() {
		$("#newCCRType").val('');
		$('#addTypeField').addClass("d-none");
	}
	
	function getCCRTypeList(id = null) {
		$.ajax({
			type: "GET",
			url: "view-manage-employee-ccr-type-list",
			success: function(response) {
				if (response.message == "Success") {
					$("#type").empty();
					$("#type")
						.append("<option value=''>Select</option>");
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#type").append(option);
					}
					if (id != null) {
						$('#type').val(id);
					}
				}
			},
			error: function(data) {
				$("#type").empty();
				$("#type").append("<option value=''>Select</option>");
	
			}
		})
	
	}
	
	
	function addCCRType() {
		var obj = {};
		obj.documentName = $("#newCCRType").val();
		var validation = true;
		if (obj.newParameterName == null || obj.newParameterName == "") {
			validation = validationUpdated("Parameter Name Required", "newCCRType");
		}
		if (validation) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-add-ccrType",
				contentType: "application/json",
				data: JSON.stringify(obj),
				success: function(response) {
					if (response.message == "Success") {
						$('.loader').hide();
						$(document).ready(function() {
							cancelBtnCCRType();
							getCCRTypeList();
						});
						toastr.rror("Type Added Successfully");
						$('#shippingInfo').addClass("d-none");
	
					} else { }
				},
				error: function(data) {
					$('.loader').hide();
				}
			})
		}
	}
	
	
	function deleteFile1() {
		$('#fileUpload').val("");
		$('#imgemp').attr('src', '');
		$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	
		var fileData = new FormData();
	
		fileData.append('file', 'none');
		fileData.append('path', 'none');
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-delete-file",
			enctype: "multipart/form-data",
			contentType: false,
			/* data        : fileData, */
			processData: false,
			cache: false,
			success: function(response) {
	
				$('#uploadDoc_1').empty();
				$('#imageName_1').empty();
				$('#uploadedBillDiv_1').empty();
				$("#imageName_1").html("");
	
			}
		});
	}
	
	
	function deleteFileCCR() {
		$('#fileUpload').val("");
		$('#imgemp').attr('src', '');
		$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	
		var fileData = new FormData();
	
		fileData.append('file', 'none');
		fileData.append('path', 'none');
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-delete-file",
			enctype: "multipart/form-data",
			contentType: false,
			/* data        : fileData, */
			processData: false,
			cache: false,
			success: function(response) {
	
				$('#uploadDoc_CCR').empty();
				$('#imageName_CCR').empty();
				$('#uploadedBillDiv_CCR').empty();
				$("#imageName_CCR").html("");
	
			}
		});
	}
	
	
	function viewEducation(id) {
	
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-education-through-ajax?id=' + id
		}).then(function(data) {
			data.sort((a, b) => parseInt(b.passyear) - parseInt(a.passyear));
	
			let container = document.getElementById("myGridEducation");
			container.innerHTML = "";
	
			if (data.length > 0) {
				let heading = document.createElement("h6");
				heading.className = "mb-2";
				heading.innerText = "Education Details";
				container.appendChild(heading);
			} else {
				let noDataMessage = document.createElement("div");
				noDataMessage.className = "no-education-message";
				noDataMessage.innerHTML = `<i class="fa fa-info-circle"></i>  Education Details Not Available`;
				container.appendChild(noDataMessage);
				return;
			}
	
			data.forEach((edu, index) => {
				let isLast = index === data.length - 1;
				let card = document.createElement("div");
				card.className = "d-flex position-relative";
	
				card.innerHTML = `
				                <div class="position-relative pe-3">
				                    <div class="timeline-circle"></div>
				                    ${!isLast ? '<div class="timeline-line"></div>' : ''}
				                </div>
				
				              
				                <div class="education-card">
				                   
				                    <a href="javascript:void(0);" class="edit-icon" onclick="editEducation('${edu.eduId}')">
				                        <i class="fa fa-pen"></i>
				                    </a>
				
				                    <div class="mb-2">
				                        <span class="fw-bold">Qualification:</span> <span>${edu.qualification}</span>
				                    </div>
				
				                    <div class="mb-2">
				                        <span class="fw-bold">Institution Name:</span> <span>${edu.instiname}</span>
				                    </div>
				
				                    <div class="mb-2">
				                        <span class="fw-bold">Passing Year:</span> <span>${edu.passyear}</span>
				                    </div>
				
				                    <div class="mb-2">
				                        <span class="fw-bold">Document:</span>
				                        <a href="${edu.docName ? edu.docName : '#'}" target="_blank" class="document-link">
				                            ${edu.docName ? "View Document" : "No Document"}
				                        </a>
				                    </div>
				                </div> `;
	
				container.appendChild(card);
			});
		});
	}
	
	
	function editEducation(id) {
	
		var empid = $('#employeeId').val();
		$('#myGridEducation').hide();
		$('.br-s-btn').show();
		$('.br-m-btn').hide();
		editEducationDetails(id, empid);
	
	}
	
	function deleteEducation() {
		var selectedRows = gridOptionsEducation.api.getSelectedRows();
		var selectedRowsString = '';
		selectedRows.forEach(function(selectedRow, index) {
			if (index > 0) {
				selectedRowsString += ',';
			}
			selectedRowsString = selectedRow.eduId;
		});
		if (selectedRowsString) {
			var id = $('#employeeId').val();
			$.ajax({
				type: "GET",
				url: "view-manage-employee-education-delete?id=" + selectedRowsString,
				success: function(response) {
					if (response.message == "Success") {
						closeNavEdu();
						viewEducation(id);
						toastr.success("Data deleted successfully");
					}
				},
				error: function(data) { }
			})
		} else {
			$('#reqwork').modal('toggle');
		}
	}
	//
	
	function updateCheckboxMessage() {
		var type = $("#typeid_").val();
		if (type == 'ATM0001') {
			document.getElementById("dynamicMessage").textContent = "Same As Permanent Address";
		} else {
			document.getElementById("dynamicMessage").textContent = "Same As Present Address";
		}
	
		$('#checkboxDivAdd').show();
	
	}
	
	document.addEventListener("DOMContentLoaded", function() {
		updateCheckboxMessage("Please accept the terms and conditions.");
	
		document.getElementById("dynamicCheckbox").addEventListener("change", function() {
			if (this.checked) {
				updateCheckboxMessage("Thank you for accepting the terms!");
			} else {
				updateCheckboxMessage("Please accept the terms and conditions.");
			}
		});
	});
	
	function clearEmployeeForm() {
		cancleBtn();
		enableAllField();
		$("#profileClose").removeClass("d-none");
	
		$("#personalInformation-tab").addClass("d-none");
		$("#address-tab").addClass("d-none");
		$("#profileNext").addClass("d-none");
		$("#educational-tab").addClass("d-none");
		$("#workDetails-tab").addClass("d-none");
		$("#bankDetails-tab").addClass("d-none");
		$("#salaryDetails-tab").addClass("d-none");
		$("#document-tab").addClass("d-none");
		$("#ccrDetails-tab").addClass("d-none");
		// Hide all tab contents by their IDs
		$("#personalInformation").removeClass("active show").addClass("d-none");
		$("#address").removeClass("active show").addClass("d-none");
		$("#educational").removeClass("active show").addClass("d-none");
		$("#workDetails").removeClass("active show").addClass("d-none");
		$("#bankDetails").removeClass("active show").addClass("d-none");
		$("#salaryDetails").removeClass("active show").addClass("d-none");
		$("#document").removeClass("active show").addClass("d-none");
		$("#ccrDetails").removeClass("active show").addClass("d-none");
		$("#newInformation-tab").removeClass("d-none");
		$("#newInformation").addClass("active show").removeClass("d-none");
		$("#personalInformation-tab").find("input, select, textarea").val("");
		$("#personalInformaionEdit").find("input, select, textarea").val("");
		$("#profileAdd").hide();
		$("#saveProfile").show();
		$("#profilePrev").hide();
		$("#editProfile").hide();
		$("#employeeId").val("");
		$('#candidateDetailsBar').hide();
	
	
		$("#tabContent").find("input, select, textarea").prop("disabled", false);
		const buttonIds = [
			"saveCcr",
			"submitDoc",
			"save",
			"bsave",
			"submitworkdetails",
			"savAddress",
			"saveEducation",
			"saveProfile"
		];
		buttonIds.forEach(function(id) {
			$("#" + id).attr("disabled", false);
		});
		$("#dobidNew").prop("disabled", false);
	}
	
	function viewWorkDetails(id) {
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-work-ajax?id=' + id
		}).then(function(data) {
			let container = document.getElementById("workDetailsContainer");
			container.innerHTML = "";
	
			if (data.length === 0) {
				container.innerHTML = `<div class="no-education-message"><i class="fa fa-info-circle"></i> Work Details Not Available</div>`;
				return;
			}
	
			data.forEach((work, index) => {
				let isLast = index === data.length - 1;
				let card = document.createElement("div");
				card.className = "d-flex position-relative";
	
				card.innerHTML = `
									                <div class="position-relative pe-3">
									                    <div class="timeline-circle"></div>
									                    ${!isLast ? '<div class="timeline-line"></div>' : ''}
									                </div>
									                <div class="education-card p-2 rounded work-card">
									                    <div class="d-flex justify-content-between">
									                        <h6 class="fw-bold">${work.degination}</h6>
									                        <a href="javascript:void(0);" class="edit-icon" onclick="editWorkDetails('${work.employeeworkId}')">
									                            <i class="fa fa-pen"></i>
									                        </a>
									                    </div>
									                    <div class="mb-2"><span class="fw-bold">Job Title:</span> ${work.jobTitle}</div>
									                    <div class="mb-2"><span class="fw-bold">Staff Type:</span> ${work.stafftype}</div>
									                    <div class="mb-2"><span class="fw-bold">Department:</span> ${work.department}</div>
									                    <div class="mb-2"><span class="fw-bold">Sub Department:</span> ${work.subdepartment ? work.subdepartment : ''}</div>
									                    <div class="mb-2"><span class="fw-bold">Band:</span> ${work.band}</div>
									                    <div class="mb-2"><span class="fw-bold">Manager:</span> ${work.managerName}</div>
									                    <div class="mb-2"><span class="fw-bold">Employment Status:</span> ${work.employmentStatus}</div>
									                </div>`;
	
				container.appendChild(card);
			});
		});
	}
	
	
	function editWorkDetails(id) {
	
		$("workId").val(id);
	
		if ($("#adRole").val() != "") {
			$.ajax({
				type: "GET",
				url: "view-manage-employee-work-edit?id=" + id,
				async: false,
				success: function(response) {
					if (response.message == "Success") {
						$("#myGrid1").show();
						openNav1();
						$("#submitworkdetails").show();
						$("#workId").val(id);
						$("#fromdateid_").val(response.body.startDate);
						$("#todateid_").val(response.body.endDate);
						$("#jobtitleid_").val(response.body.jobTitle);
						$("#departmentid_").val(response.body.department);
						$("#employmentstatusid_").val(response.body.employmentStatus);
						$("#deginationid_").val(response.body.degination);
						$("#bandid_").val(response.body.band);
						$("#managerid_").val(response.body.manager);
						$("#stafftypeid_").val(response.body.workStatus);
						var department = response.body.department;
						var sdept = response.body.subdepartment
						if (department) {
							$.ajax({
								type: "GET",
								url: "view-manage-employee-subdepartment-list?department=" + department,
								dataType: 'json',
								contentType: 'application/json',
								data: department,
								success: function(
									response) {
									if (response.message == "Success") {
										$("#subdepartmentid_").empty();
										$("#subdepartmentid_").append("<option value=''>Select</option>");
	
										for (var i = 0; i < response.body.length; i++) {
											var option = $("<option></option>");
											$(option).val(response.body[i].key);
											$(option).html(response.body[i].name);
											$("#subdepartmentid_").append(option);
										}
										$('#subdepartmentid_').val(sdept);
									}
								},
								error: function(
									data) {
									console
										.log(data);
									$("#subdepartmentid_").empty();
									$("#subdepartmentid_").append("<option value=''>Select</option>");
	
								}
							})
						} else {
							$("#subdepartmentid_").empty();
							$("#subdepartmentid_").append("<option value=''>Select</option>");
						}
					}
				}
			});
		} else {
			toastr.error("Only Admin can modify work details!");
		}
	}
	
	
	function getBankDetailsByEmpId(id) {
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-bankdetails-ajax?id=' + id
		}).then(function(data) {
			let container = document.getElementById("bankDetailsContainer");
			container.innerHTML = "";
	
			if (data.length === 0) {
				container.innerHTML = `<div class="no-education-message"><i class="fa fa-info-circle"></i> Bank Details Not Available</div>`;
				return;
			}
	
			data.forEach((bank) => {
				let card = document.createElement("div");
				card.className = "border p-2 mb-2 rounded education-card";
	
				card.innerHTML = `
									                    <div class="d-flex justify-content-between">
									                        <h5 class="fw-bold">Account No: ${bank.ebankAccountNo}</h5>
									                        <a href="javascript:void(0);" class="edit-icon" onclick="editBankDetails('${bank.ebankId}')">
									                            <i class="fa fa-pen"></i>
									                        </a>
									                    </div>
									                    <div class="mb-2"><span class="fw-bold">Bank Name:</span> ${bank.ebankName}</div>
									                    <div class="mb-2"><span class="fw-bold">Branch Name:</span> ${bank.ebankAddress}</div>
									                    <div class="mb-2"><span class="fw-bold">IFSC Code:</span> ${bank.eIfic}</div>
									                    <div class="mb-2"><span class="fw-bold">Document:</span> 
									                        ${bank.ebankDocument ?
						`<a href="${bank.ebankDocument}" target="_blank" class="document-link">View Document</a>` :
						"No Document Available"}
															                    </div>
															                `;
	
				container.appendChild(card);
			});
		});
	}
	
	
	
	function getSalaryDetailsByEmpId(id) {
	
	
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-salary-revision-view?userid=' + id
		}).then(function(data) {
			let container = document.getElementById("salaryDetailsContainer");
			container.innerHTML = "";
	
			if ($("#adRole").val() != "") {
				if (data.length > 0) {
					$("#saladd").hide();
					$("#approve").show();
					$('#approve').attr("disabled", true);
				} else if (data.length == 0) {
					$("#saladd").show();
					$("#approve").hide();
				}
			} else {
				$("#saladd").hide();
				$("#approve").hide();
			}
	
			if (data.length === 0) {
				container.innerHTML = `<div class="no-education-message"><i class="fa fa-info-circle"></i> Salary Details Not Available</div>`;
				return;
			}
			
			data.forEach((salary,index) => {
				let isLast = index === data.length - 1;
				let card = document.createElement("div");
				// card.className = "border p-3 mb-3 rounded education-card";
	
				card.innerHTML = `
				<div class="d-flex position-relative">
				                <div class="position-relative pe-3">
				                    <div class="timeline-circle"></div>
				                    ${!isLast ? '<div class="timeline-line"></div>' : ''}
				                </div>
				
				              
				                <div class="education-card">
				                   
				                    <a href="javascript:void(0);" class="edit-icon" onclick="editSalaryRevision('${salary.editId}','${salary.status}')">
	                <i class="fa fa-pen"></i>
	            </a>
				
				                    <div class="mb-2"><span class="fw-bold">Designation:</span> ${salary.ndesg}</div>
				                    <div class="mb-2"><span class="fw-bold">Joining Date:</span> ${salary.doj}</div>
	        <div class="mb-2"><span class="fw-bold">Gross Salary:</span> ${parseFloat(salary.totalEarn).toFixed(2)}</div>
	        <div class="mb-2"><span class="fw-bold">Net Salary:</span> ${parseFloat(salary.netPay).toFixed(2)}</div>
				                </div> </div>
				
	    `;
	
				container.appendChild(card);
			});
	
		});
	}
	
	function editSalaryRevision(id, sts) {
		if (sts == "2" || sts == "1") {
			editingSalary(id);
			$("#salSavebtn").prop('disabled', true);
		} else {
			editingSalary(id);
			$("#salSavebtn").prop('disabled', false);
		}
	}
	function editingSalary(id) {
	
		if ($("#adRole").val() != "") {
	
			$.ajax({
				type: "GET",
				url: "view-manage-employee-salary-revision-edit?Id=" + id,
				success: function(response) {
					if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
						var allData = jsonData.editData;
						//	var len = allData.length;
						console.log("ALLDATA==========", allData);
	
						openNav4();
						$("#salDept").hide();
						//$("#salaryModal").modal('show');
	
						$("#doj").attr('disabled', true);
						$("#dojDateCalendar").attr('disabled', true);
						$("#dept").attr('disabled', true);
						$("#subDept").attr('disabled', true);
						$("#empId").attr('disabled', true);
						$("#pdesg").attr('disabled', true);
						$("#sdept").removeClass("select");
						$("#ssdept").removeClass("select");
						$("#semp").removeClass("select");
						$("#empId").val(allData[0].empId);
						$("#editId").val(allData[0].editId);
						$("#name").val(allData[0].empName);
						$("#pdesg").val(allData[0].pdesg);
						$("#ndesg").val(allData[0].ndesg);
						$("#effectiveDateFrom").val(allData[0].effectiveDateFrom);
						$("#effectiveDateTo").val(allData[0].effectiveDateTo);
						$("#doj").val($("#joiningdate").val());
						$("#band").val(allData[0].band);
						$("#dept").val(allData[0].dept);
						$("#subDept").val(allData[0].subdept);
						$("#ctc").val(allData[0].ctc);
						$("#basic").val(allData[0].basic);
						$("#providentFund").val(allData[0].providentFund);
						$("#hra").val(allData[0].hra);
						$("#esi").val(allData[0].esi);
						$("#convAllow").val(allData[0].convAllow);
						$("#pTax").val(allData[0].pTax);
						$("#specialallowance").val(allData[0].specialallowance);
						$("#salAdv").val(allData[0].salAdv);
						$("#skillDev").val(allData[0].skillDev);
						$("#wFund").val(allData[0].wFund);
						$("#medAllow").val(allData[0].medAllow);
						$("#insAmt").val(allData[0].insAmt);
						$("#washAllow").val(allData[0].washAllow);
						$("#lic").val(allData[0].lic);
						$("#bonus").val(allData[0].bonus);
						$("#socy").val(allData[0].socy);
						$("#overTime").val(allData[0].overTime);
						$("#fine").val(allData[0].fine);
						$("#misc").val(allData[0].misc);
						$("#damage").val(allData[0].damage);
						$("#da").val(allData[0].da);
						$("#tds").val(allData[0].tds);
						$("#otherEarn").val(allData[0].otherEarn);
						$("#otherDeduct").val(allData[0].otherDeduct);
						$("#totalEarn").val(allData[0].totalEarn);
						$("#totalDeduct").val(allData[0].totalDeduct);
						$("#netPay").val(allData[0].netPay);
						$("#mEmployerPf").val(allData[0].mEmployerPf);
						$("#esicWage").val(allData[0].esicWage);
						$("#totalContribution").val(allData[0].totalContribution);
					}
	
				},
				error: function(data) {
					$("#empId").val("");
					$("#name").val("");
					$("#pdesg").val("");
					$("#ndesg").val("");
					$("#effectiveDate").val("");
					$("#band").val("");
					$("#basic").val("");
					$("#hra").val("");
					$("#addAllow").val("");
					$("#lta").val("");
					$("#medical").val("");
					$("#other").val("");
					$("#specialallowance").val("");
					$("#editId").val("");
				}
			});
		} else {
			toastr.error("Only Admin can modify salary details!")
		}
	}
	
	function getAllDocsByEmpId(id) {
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-document-ajax?id=' + id
		}).then(function(data) {
			let container = document.getElementById("documentDetailsContainer");
			container.innerHTML = "";
	
			if (data.length === 0) {
				container.innerHTML = `<div class="no-education-message"><i class="fa fa-info-circle"></i> Documents Not Available</div>`;
				return;
			}
	
			data.forEach((doc) => {
				let card = document.createElement("div");
				card.className = "border p-2 mb-2 rounded education-card";
	
				card.innerHTML = `
										                 <div class="d-flex justify-content-between">
											                       
											                        <a href="javascript:void(0);" class="edit-icon" onclick="editDoc('${doc.documentType}')">
											                            <i class="fa fa-pen"></i>
											                        </a>
											                    </div>
										                    <div class="mb-2"><span class="fw-bold">Document Type:</span> ${doc.documentTypeName}</div>
										                    <div class="mb-2"><span class="fw-bold">Employee Name:</span> ${doc.employeeName}</div>
										                     <div class="mb-2"><span class="fw-bold">Document:</span> 
											                        ${doc.documentName ?
						`<a href="${doc.documentName}" target="_blank" class="document-link">View Document</a>` :
						"No Document Available"}
															                    </div>
														                `;
				container.appendChild(card);
			});
		});
	}
	
	
	function getCcrDetailsByEmpId(id) {
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-ccr-details?id=' + id
		}).then(function(data) {
			let container = document.getElementById("ccrDetailsContainer");
			container.innerHTML = "";
	
			if (!data.body) {
				container.innerHTML = `<div class="no-education-message"><i class="fa fa-info-circle"></i> CCR Details Not Available</div>`;
				return;
			}
	
			let jsonData = JSON.parse(data.body);
			let allData = jsonData.editData;
			let data1 = allData[0].reviewlist;
	
			// Check if reviewlist is null or an empty array
			if (!data1 || data1.length === 0) {
				container.innerHTML = `<div class="no-education-message"><i class="fa fa-info-circle"></i> No CCR Records Found</div>`;
				return;
			}
	
			data1.forEach((ccr) => {
				let card = document.createElement("div");
				card.className = "border p-2 mb-2 rounded education-card";
	
				card.innerHTML = `
												<div class="d-flex justify-content-between">
													<a href="javascript:void(0);" class="edit-icon" onclick="editCCR('${ccr.reviewId}')">
														<i class="fa fa-pen"></i>
													</a>
												</div>
												<div class="mb-2"><span class="fw-bold">Date:</span> ${ccr.date}</div>
												<div class="mb-2"><span class="fw-bold">Type:</span> ${ccr.type}</div>
												<div class="mb-2"><span class="fw-bold">Subject:</span> ${ccr.subject}</div>
												<div class="mb-2"><span class="fw-bold">Remark:</span> ${window.atob(ccr.remarks || '')}</div>
												<div class="mb-2"><span class="fw-bold">Expected Result:</span> ${window.atob(ccr.expectedResult || '')}</div>
												<div class="mb-2"><span class="fw-bold">Guided By:</span> ${ccr.guidedBy}</div>
												<div class="mb-2"><span class="fw-bold">Document:</span> 
													${ccr.docName ?
						`<a href=${ccr.docName} target="_blank" class="document-link">View Document</a>` :
						"No Document Available"}
												</div>`;
				container.appendChild(card);
			});
		});
	}
	
	
	function processUpload() {
		var chosenFile = $('#selectFile')[0].files[0];
		var fileLocation = $('#selectFile').val();
	
		var fileTitle = fileLocation.substring(fileLocation.lastIndexOf("\\") + 1);
		var imagePreviewURL = URL.createObjectURL(chosenFile);
	
		$('#displayImage').attr('src', imagePreviewURL);
		$("#storedImage").val(fileTitle);
	
		var uploadData = new FormData();
		uploadData.append('file', chosenFile);
		uploadData.append('path', 'none');
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-upload-file",
			enctype: "multipart/form-data",
			contentType: false,
			data: uploadData,
			processData: false,
			cache: false,
			success: function(response) {
				console.log("File uploaded successfully.");
			}
		});
	}
	
	function clearImage() {
	
		$('#employeeFrofileName').val("");
		$('#selectFile').val("");
		$('#displayImage').attr('src', '../assets/images/noimage.jpg');
	
		var deleteData = new FormData();
		deleteData.append('file', 'none');
		deleteData.append('path', 'none');
	
		let imageName = $("#storedImage").val();
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-delete-file?imageName=" + imageName,
			enctype: "multipart/form-data",
			contentType: false,
			processData: false,
			cache: false,
			success: function(response) {
				$("#storedImage").val("");
				console.log("File deleted successfully.");
			}
		});
	}
	
	function getAddressByEmpId(id) {
		agGrid.simpleHttpRequest({
			url: 'view-manage-employee-address-through-ajax?id=' + id
		}).then(function(data) {
			console.log("Data received: ", data);
	
			let hasPresent = false;
			let hasPermanent = false;
	
			if (data.length > 0) {
				data.forEach(item => {
					if (item.type === "Present") {
						hasPresent = true;
						setTextValue('address1', item.address || "");
						setTextValue('country1', item.country || "");
						setTextValue('state1', item.state || "");
						setTextValue('city1', item.city || "");
						setTextValue('addtype1', item.type || "");
						setTextValue('zipcode1', item.zipCode || "");
						setTextValue('addId1', item.addressId || "");
						setTextValue('typeId1', item.typeid || "");
					} else if (item.type === "Permanent") {
						hasPermanent = true;
						setTextValue('address2', item.address || "");
						setTextValue('country2', item.country || "");
						setTextValue('state2', item.state || "");
						setTextValue('city2', item.city || "");
						setTextValue('addtype2', item.type || "");
						setTextValue('zipcode2', item.zipCode || "");
						setTextValue('addId2', item.addressId || "");
						setTextValue('typeId2', item.typeid || "");
					}
				});
			}
			if (!hasPresent) {
				clearPresentAddress();
			}
			if (!hasPermanent) {
				clearPermanentAddress();
			}
		});
	}
	
	function clearPresentAddress() {
		setTextValue('address1', "");
		setTextValue('country1', "");
		setTextValue('state1', "");
		setTextValue('city1', "");
		setTextValue('addtype1', "");
		setTextValue('zipcode1', "");
		setTextValue('addId1', "");
		setTextValue('typeId1', "");
	}
	
	function clearPermanentAddress() {
		setTextValue('address2', "");
		setTextValue('country2', "");
		setTextValue('state2', "");
		setTextValue('city2', "");
		setTextValue('addtype2', "");
		setTextValue('zipcode2', "");
		setTextValue('addId2', "");
		setTextValue('typeId2', "");
	}
	
	
	
	function getEmployeeList(typee=null) {
	
		agGrid.simpleHttpRequest({
			url: "view-manage-employee-master-through-ajax?type="+typee
		}).then(function(data) {
			var len = data.length;
			$('#totalEmp').find('span').html(len);
			$('.loader').hide();
			if(data && data.length > 0) {
				gridOptionsEmployee.api.setRowData(data);
				const existEmpId = $("#existingEmpId").val();
				if (existEmpId) {
					gridOptionsEmployee.api.forEachNode(function(node) {
						if (node.data && node.data.employeeId === existEmpId) {
							node.setSelected(true);
							return;
						}
					});
				} else {
					gridOptionsEmployee.api.forEachNode(function(node) {
						if (data && data.length > 0) {
							gridOptionsEmployee.api.forEachNode(function(node) {
								if (node.rowIndex === 0) {
									node.setSelected(true);
								}
							});
						}
					});
				}
			} else {
				gridOptionsEmployee.api.setRowData([]);
				//addEmployee()
			}
			$("#saveProfile").hide();
		});
	}
	
	function setTabAsDefault() {
	
		$("#profilePrev").hide();
		$("#profileNext").show();
		$("#editProfile").show();
		$("#saveProfile").show();
		$("#profileAdd").show();
	
	
	}
	
	
	function getEmployeeDetailsById(id) {
	
		$("#editProfile").prop('disabled', false);
		$("#saveProfile").prop('disabled', true);
		$("#saveProfile").hide();
		$("#profileNext").prop('disabled', false);
		$("#selectFile").prop('disabled', true);
		disableTrashIcon();
	
	
		$(".offerLetter").hide();
		$("#candidateDetailsBar").show();
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-master-edit?employeeId=" + id,
			contentType: 'application/json',
			success: function(response) {
				if (response.message == "Success") {
					$("#dobidCalendar").css("pointer-events", "none").removeClass("disabled");
					$("#main").show();
					$("#employeeId").val(response.body.employeeId).prop('disabled', true);
					$('#firstnameid').val(response.body.firstName).prop('disabled', true);;
					$('#lastnameid').val(response.body.lastName).prop('disabled', true);;
					$('#genderid').val(response.body.gender).prop('disabled', true);;
					$('#dobid').val(response.body.dob).prop('disabled', true);;
					$('#dobidCalendar').val(response.body.dob).prop('disabled', true);;
					$('#bloodgroupid').val(response.body.bloodGroup).prop('disabled', true);;
					$('#maritalstatusid').val(response.body.maritalStatus).prop('disabled', true);;
					$('#nationalityid').val(response.body.nationality).prop('disabled', true);;
					$('#fatherid').val(response.body.fatherName).prop('disabled', true);;
					$('#motherid').val(response.body.motherName).prop('disabled', true);;
					$('#mobilenoid').val(response.body.mobileNo).prop('disabled', true);;
					$('#personalmailid').val(response.body.personalMail).prop('disabled', true);;
					$('#workmailid').val(response.body.workMail).prop('disabled', true);;
					$('#panno').val(response.body.panno).prop('disabled', true);;
					$('#epfno').val(response.body.epfno).prop('disabled', true);;
					$('#esicno').val(response.body.esicno).prop('disabled', true);;
					$('#aadhaar').val(response.body.aadhaar).prop('disabled', true);;
					$('#joiningdate').val(response.body.joiningdate).prop('disabled', true);;
					$('#joiningdateCalendar').val(response.body.joiningdate).prop('disabled', true);;
					$('#ememobilenoid').val(response.body.ememobilenoid).prop('disabled', true);
					$('#spouseName').val(response.body.spouseName).prop('disabled', true);
					getCurrentTab();
	
					if (response.body.maritalStatus == "TMM/0002") {
						$("#spouseDiv").removeClass('d-none');
					} else {
						$("#spouseDiv").addClass('d-none');
					}
					if (response.body.maritalStatus == "TMM/0001") {
						$('#mrgDate').attr("disabled", true);
						$('#mrgDateDiv').hide();
						$('#mrgDateCalendar').hide();
						$('#mrgDate').val("");
					} else {
						$('#mrgDate').attr("disabled", true);
						$('#mrgDateCalendar').show();
						$('#mrgDateDiv').show();
						$('#mrgDate').val(response.body.mrgdate);
						$('#mrgDateCalendar').val(response.body.mrgdate);
					}
					const fileName = response.body.fileEmployeeimg;
					$("#employeeFrofileName").val(response.body.fileEmployeeimg);
					$('#displayImage').attr('src', '');
					if (fileName != null && fileName !== "") {
						$('#displayImage').attr('src', fileName);
						const file = fileName.split('/').pop();
						$('#storedImage').val(file);
					} else {
						$('#displayImage').attr('src', '../assets/images/noimage.jpg');
						$('#storedImage').val("");
					}
					$('#nameDtls').html(nameIcon + response.body.firstName + " " + response.body.lastName + " (" + id + ")");
					
					if(response.body.mobileNo) {
						$('#mobileNoDtls').html(mobileIcon + response.body.mobileNo);
						$('#mobileNoDtls').show();
					} else {
						$('#mobileNoDtls').hide();
					}
					
					$('#emailDtls').html(response.body.personalMail ? emailIcon + response.body.personalMail : response.body.personalMail);
					//$('#managerDtls').text(response.body.manager);
	
	
					$.ajax({
						type: "GET",
						url: "view-manage-employee-benifit-ajax?id=" + id,
						success: function(response) {
							if (response.message == "Success") {
								if ($("#empRole").val() != "") {
									for (var i = 0; i < response.body.length; i++) {
										$("#" + response.body[i].ebenifitId).prop("checked", true);
										$('.benefitChk').attr('disabled', true);
									}
								}
								if ($("#mrRole").val() != "") {
									for (var i = 0; i < response.body.length; i++) {
										$("#" + response.body[i].ebenifitId).prop("checked", true);
										$('.benefitChk').attr('disabled', true);
									}
								}
								if ($("#adRole").val() != "") {
									for (var i = 0; i < response.body.length; i++) {
										$("#" + response.body[i].ebenifitId).prop("checked", true);
										$('.benefitChk').attr('disabled', false);
									}
								}
							}
						},
						error: function(response) {
						}
					});
					viewWorkDetails(response.body.employeeId)
					agGrid.simpleHttpRequest({
						url: 'view-manage-employee-dependent-ajax?id=' + id
					}).then(function(data) {
					});
	
					agGrid.simpleHttpRequest({
						url: ' view-manage-employee-insurancedetails-ajax?id=' + id
					}).then(function(data) {
					});
					getAllDocsByEmpId(id);
					getSalaryDetailsByEmpId(id);
					getCcrDetailsByEmpId(id);
					getAddressByEmpId(id);
					viewEducation(id);
					getEmployeeManagerById(id);
					getBankDetailsByEmpId(id);
				}
			},
			error: function(data) { }
		});
	}
	
	
	function getStateList() {
	
	
		var country = $("#countryid_").val();
		if (country) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-state-list",
				dataType: 'json',
				contentType: 'application/json',
				data: country,
				success: function(
					response) {
					if (response.message == "success") {
						console
							.log(response);
						$("#stateid_").empty();
						$("#stateid_").append("<option value=''>Select</option>");
						$("#cityid_").empty();
						$("#cityid_").append("<option value=''>Select</option>");
	
						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#stateid_").append(option);
						}
					}
				},
				error: function(
					data) {
					console
						.log(data);
					$("#stateid_").empty();
					$("#stateid_").append("<option value=''>Select</option>");
					$("#cityid_").empty();
					$("#cityid_").append("<option value=''>Select</option>");
				}
			})
		} else {
			$("#stateid_").empty();
			$("#stateid_").append("<option value=''>Select</option>");
			$("#cityid_").empty();
			$("#cityid_").append("<option value=''>Select</option>");
		}
	
	}
	
	function getCityList() {
		var state = $("#stateid_").val();
		if (state) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-city-list",
				dataType: 'json',
				contentType: 'application/json',
				data: state,
				success: function(
					response) {
					if (response.message == "success") {
						console
							.log(response);
						$("#cityid_").empty();
						$("#cityid_").append("<option value=''>Select</option>");
	
						for (var i = 0; i < response.body.length; i++) {
							var option = $("<option></option>");
							$(option).val(response.body[i].key);
							$(option).html(response.body[i].name);
							$("#cityid_").append(option);
						}
					}
				},
				error: function(data) {
					$("#cityid_").empty();
					$("#cityid_").append("<option value=''>Select</option>");
				}
			})
		} else {
			$("#cityid_").empty();
			$("#cityid_").append("<option value=''>Select</option>");
		}
	
	}
	function setFieldValue(fieldId, value) {
		const fields = document.querySelectorAll(`#${fieldId}`);
		fields.forEach(field => {
			const parentDiv = field.closest('div');
			if (parentDiv && !parentDiv.classList.contains('d-none')) {
				field.value = value;
			}
		});
	}
	function setTextValue(fieldId, value) {
		const elements = document.querySelectorAll(`#${fieldId}`);
		elements.forEach(element => {
			const parentDiv = element.closest('div');
			if (parentDiv && !parentDiv.classList.contains('d-none')) {
				element.textContent = value;
			}
		});
	}
	
	
	function editAddress(event, id1, id2) {
		if (event) event.preventDefault();
		var sid = $("#sessionId").val();
		var empid = $('#employeeId').val();
		var srole = $("#adRole").val();
		if (empid == sid) {
			editAddressDetails(id1, id2)
		} else {
			if (srole != "") {
				editAddressDetails(id1, id2)
			} else {
				toastr.error("It is eligible for self and Admin!");
			}
		}
	}
	
	
	
	
	function editAddressDetails(id1, id2) {
	
		$.ajax({
			type: "GET",
			url: "view-manage-employee-address-edit?addressId=" + id1 + "&addressType=" + id2,
			async: false,
			success: function(response) {
				if (response.message == "Success") {
	
					
					//$('#typeid_').attr("disabled", true);
					console.log("response===", response.body)
					if(response.body.address===null){
						toastr.error("Add Address Details First !");
						return;
					}else{
					openNav();
					$("#myGrid").hide();
					$("#typeid_").attr('disabled', true);
					$("#satype").removeClass("select");
					$('#addressIdh').val(response.body.addressId);
					$('#typeid_').val(response.body.typeid);
					updateCheckboxMessage();
					$('#addressid_').val(response.body.address);
					$('#countryid_').val(response.body.countryid);
					$('#zipcodeid_').val(response.body.zipCode);
					$('#Status_').val(response.body.status);
					setFieldValue('city1', response.body.city1);
					setFieldValue('zipcode1', response.body.zipcode1);
					setFieldValue('addtype1', response.body.addtype1);
					if (response.body.checkBox == 1) {
						$("#dynamicCheckbox").prop("checked", true);
					} else {
						$("#dynamicCheckbox").prop("checked", false);
					}
	
					var cntryId1 = response.body.countryid;
					var stsId1 = response.body.stateid;
					var ctId1 = response.body.cityid;
					$.ajax({
						type: "POST",
						url: "view-manage-employee-state-list",
						dataType: 'json',
						contentType: 'application/json',
						data: cntryId1,
						success: function(response) {
							if (response.message == "success") {
								$("#stateid_").empty();
								$("#stateid_").append("<option value=''>Select</option>");
								$("#cityid_").empty();
								$("#cityid_").append("<option value=''>Select</option>");
	
								for (var i = 0; i < response.body.length; i++) {
									var option = $("<option></option>");
									$(option).val(response.body[i].key);
									$(option).html(response.body[i].name);
									$("#stateid_").append(option);
								}
								$('#stateid_').val(stsId1);
	
								$.ajax({
									type: "POST",
									url: "view-manage-employee-city-list",
									dataType: 'json',
									contentType: 'application/json',
									data: stsId1,
									success: function(response) {
										if (response.message == "success") {
											$("#cityid_").empty();
											$("#cityid_").append("<option value=''>Select</option>");
											for (var i = 0; i < response.body.length; i++) {
												var option = $("<option></option>");
												$(option).val(response.body[i].key);
												$(option).html(response.body[i].name);
												$("#cityid_").append(option);
											}
											$('#cityid_').val(ctId1);
										}
									},
									error: function(data) {
										$("#cityid_").empty();
										$("#cityid_").append("<option value=''>Select</option>");
									}
								});
							}
						},
						error: function(data) {
							$("#stateid_").empty();
							$("#stateid_").append("<option value=''>Select</option>");
							$("#cityid_").empty();
							$("#cityid_").append("<option value=''>Select</option>");
						}
					})
				}
				}
			}
		});
	}
	
	
	
	
	
	
	function getEmployeeManagerById(id) {
		$.ajax({
			type: "GET",
			url: "view-manage-employee-manager-list?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
	
					$("#managerid_").empty();
					$("#managerid_").append("<option value=''>Select</option>");
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#managerid_").append(option);
					}
				}
			},
			error: function(response) {
	
			}
		});
	}
	
	function addEmployee() {
		$("#existingEmpId").val('');
		$("#candidateDetailsBar").hide();
		$("#editProfile").prop('disabled', true);
		$("#saveProfile").prop('disabled', false);
		$("#saveProfile").show();
		$("#profileNext").prop('disabled', true);
		$("#offerLetterId").prop('disabled', false);
		$("#profileClose").show();
		$("#profileAdd,#editProfile,#profileNext").hide();
		$("#spouseDiv").addClass('d-none');
		clearImage();
		clearValue();
		$(".offerLetter").show();
		enableTrashIcon();
		
	}
	
	function clearValue() {
		gridOptionsEmployee.api.deselectAll();
	
		var currentDate = new Date();
		var eighteenYearsAgo = new Date();
    	eighteenYearsAgo.setFullYear(currentDate.getFullYear() - 18);
    	let dobid = eighteenYearsAgo.toISOString().split('T')[0];
		
	
		$("#selectFile").val('').prop('disabled', false);
		$("#employeeId").val('').prop('disabled', false);
		$("#firstnameid").val('').prop('disabled', false);
		$("#lastnameid").val('').prop('disabled', false);
		$("#genderid").val('').prop('disabled', false);
		$("#dobid").val('').prop('disabled', false);
		$("#dobidCalendar").val('').prop('disabled', false);
		
		$("#bloodgroupid").val('').prop('disabled', false);
		$("#maritalstatusid").val('').prop('disabled', false);
		$("#nationalityid").val('').prop('disabled', false);
		$("#fatherid").val('').prop('disabled', false);
		$("#motherid").val('').prop('disabled', false);
		$("#mobilenoid").val('').prop('disabled', false);
		$("#personalmailid").val('').prop('disabled', false);
		$("#workmailid").val('').prop('disabled', false);
		$("#panno").val('').prop('disabled', false);
		$("#epfno").val('').prop('disabled', false);
		$("#esicno").val('').prop('disabled', false);
		$("#aadhaar").val('').prop('disabled', false);
		$("#joiningdate").val('').prop('disabled', false);
		$("#joiningdateCalendar").val('').prop('disabled', false);
		$("#mrgDate").val('').prop('disabled', false);
		$("#ememobilenoid").val('').prop('disabled', false);
		$("#dobidCalendar").prop('disabled', false);
		$("#dobidCalendar").css("pointer-events", "auto").removeClass("disabled");
		$("#spouseName").prop('disabled', false);
	}
	
	function clearvalueOnDeselect() {
		$("#selectFile").val('').prop('disabled', false);
		$("#employeeId").val('').prop('disabled', false);
		$("#firstnameid").val('').prop('disabled', false);
		$("#lastnameid").val('').prop('disabled', false);
		$("#genderid").val('').prop('disabled', false);
		$("#dobid").val('').prop('disabled', false);
		$("#bloodgroupid").val('').prop('disabled', false);
		$("#maritalstatusid").val('').prop('disabled', false);
		$("#nationalityid").val('').prop('disabled', false);
		$("#fatherid").val('').prop('disabled', false);
		$("#motherid").val('').prop('disabled', false);
		$("#mobilenoid").val('').prop('disabled', false);
		$("#personalmailid").val('').prop('disabled', false);
		$("#workmailid").val('').prop('disabled', false);
		$("#panno").val('').prop('disabled', false);
		$("#epfno").val('').prop('disabled', false);
		$("#esicno").val('').prop('disabled', false);
		$("#aadhaar").val('').prop('disabled', false);
		$("#joiningdate").val('').prop('disabled', false);
		$("#mrgDate").val('').prop('disabled', false);
		$("#ememobilenoid").val('').prop('disabled', false);
		$("#dobidCalendar").prop('disabled', false);
		$("#spouseName").prop('disabled', false);
		$("#dobidCalendar").css("pointer-events", "auto").removeClass("disabled");
	}
	
	function saveEmployeeProfile() {
		var employeeId = $("#employeeId").val();
		if (!employeeId) {
	
			saveEmployeeNew();
		} else {
			saveEmployeeProfileEdit();
		}
	}
	
	
	function saveEmployeeProfileEdit() {
	
	
		$("#existingEmpId").val($("#employeeId").val());
	
	
		const imgName = $("#employeeFrofileName").val().split('/').pop().trim();
		const imgName2 = $("#selectFile").val().trim();
	
		const orgImgName = imgName ? imgName : imgName2;
	
		var data = {
			fileEmployeeimg: orgImgName,
			employeeId: $("#employeeId").val(),
			firstName: $("#firstnameid").val(),
			lastName: $("#lastnameid").val(),
			gender: $("#genderid").val(),
			dob: $("#dobid").val(),
			bloodGroup: $("#bloodgroupid").val(),
			maritalStatus: $("#maritalstatusid").val(),
			nationality: $("#nationalityid").val(),
			fatherName: $("#fatherid").val(),
			motherName: $("#motherid").val(),
			mobileNo: $("#mobilenoid").val(),
			personalMail: $("#personalmailid").val(),
			workMail: $("#workmailid").val(),
			panno: $("#panno").val(),
			epfno: $("#epfno").val(),
			esicno: $("#esicno").val(),
			aadhaar: $("#aadhaar").val(),
			joiningdate: $("#joiningdate").val(),
			mrgdate: $("#mrgDate").val(),
			ememobilenoid: $("#ememobilenoid").val(),
			spouseName: $("#spouseName").val(),
		};
		console.log("Employee Data:", data);
	
		if (data.firstName == "" || data.firstName == null) {
			toastr.error("First Name Required");
			return;
		}
	
		if (data.lastName == "" || data.lastName == null) {
			toastr.error("Last Name Required");
			return;
		}
	
		if (data.gender == "" || data.gender == null) {
			toastr.error("Gender Required");
			return;
		}
	
		if (data.dob == "" || data.dob == null) {
			toastr.error("Date Of Birth Required");
			return;
		}
	
		if (data.joiningdate == "" || data.joiningdate == null) {
			toastr.error("Joining Date Required");
			return;
		}
	
	
		submitemployeeee(data);
	}
	
	function submitemployeeee(dataset) {
		adharaVal();
		pmailVal();
		pancardVal();
		mobVal1();
		epfVal();
		$.ajax({
			type: "POST",
			url: "view-manage-employee-master-save",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.message == "Success") {
	
					$("#profileNext").show();
					$("#profileAdd").show();
					$("#editProfile").show();
					$("#profileClose").hide();
					getEmployeeDetailsById(empid);
					getEmployeeManagerById(id);
	
					getEmployeeList();
	
					$('.loader').hide();
					$("body").removeClass("overlay");
					toastr.success("Profile Updated Successfully")
					$("#employeeId").val("");
					$("#employeeId").val(response.body.employeeId);
	
					var id = $("#employeeId").val();
	
	
				} else {
					swal({
						title: response.code,
						text: response.message,
						type: "warning"
					})
				}
			},
			error: function(response) { }
		})
	}
	
	function saveEmployeeNew() {
	
		var data = {
			fileEmployeeimg: $("#selectFile").val(),
			employeeId: $("#employeeId").val(),
			firstName: $("#firstnameid").val(),
			lastName: $("#lastnameid").val(),
			gender: $("#genderid").val(),
			dob: $("#dobid").val(),
			bloodGroup: $("#bloodgroupid").val(),
			maritalStatus: $("#maritalstatusid").val(),
			nationality: $("#nationalityid").val(),
			fatherName: $("#fatherid").val(),
			motherName: $("#motherid").val(),
			mobileNo: $("#mobilenoid").val(),
			personalMail: $("#personalmailid").val(),
			workMail: $("#workmailid").val(),
			panno: $("#panno").val(),
			epfno: $("#epfno").val(),
			esicno: $("#esicno").val(),
			aadhaar: $("#aadhaar").val(),
			joiningdate: $("#joiningdate").val(),
			mrgdate: $("#mrgDate").val(),
			ememobilenoid: $("#ememobilenoid").val(),
			spouseName: $("#spouseName").val(),
		};
	
		if (data.firstName == "" || data.firstName == null) {
			toastr.error("First Name Required");
			return;
		}
	
		if (data.lastName == "" || data.lastName == null) {
			toastr.error("Last Name Required");
			return;
		}
	
		if (data.gender == "" || data.gender == null) {
			toastr.error("Gender Required");
			return;
		}
	
		if (data.dob == "" || data.dob == null) {
			toastr.error("Date Of Birth Required");
			return;
		}
	
		if (data.joiningdate == "" || data.joiningdate == null) {
			toastr.error("Joining Date Required");
			return;
		}
	
		submitemployee(data);
	}
	function submitemployee(dataset) {
	
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "new-employee-save-details",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.code == "success") {
					console.log("response", response.body.employeeId);
					getEmployeeList();
					$('.loader').hide();
					$("body").removeClass("overlay");
	
					$('#candMsg').text("");
					$('#candValid').modal('toggle');
					toastr.success("Employee ID is generated Add more information");
					var employeeId = response.body.employeeId;
					cancleBtn();
					
				} else {
					$('.loader').hide();
					toastr.error(response.message);
				}
			},
			error: function(response) {
				console.log(response);
			}
		});
	
	}
	
	function getEnabledFields() {
	
		$("#selectFile").prop('disabled', false);
		$("#employeeId").prop('disabled', false);
		$("#firstnameid").prop('disabled', false);
		$("#lastnameid").prop('disabled', false);
		$("#genderid").prop('disabled', false);
		$("#dobid").prop('disabled', false);
		$("#bloodgroupid").prop('disabled', false);
		$("#maritalstatusid").prop('disabled', false);
		$("#nationalityid").prop('disabled', false);
		$("#fatherid").prop('disabled', false);
		$("#motherid").prop('disabled', false);
		$("#mobilenoid").prop('disabled', false);
		$("#personalmailid").prop('disabled', false);
		$("#workmailid").prop('disabled', false);
		$("#panno").prop('disabled', false);
		$("#epfno").prop('disabled', false);
		$("#esicno").prop('disabled', false);
		$("#aadhaar").prop('disabled', false);
		$("#joiningdate").prop('disabled', false);
		$("#mrgDate").prop('disabled', false);
		$("#ememobilenoid").prop('disabled', false);
		$("#dobidCalendar").prop('disabled', false);
		$("#dobidCalendar").css("pointer-events", "auto").removeClass("disabled");
		$(".offerLetter").hide();
		$("#saveProfile").prop('disabled', false);
		$("#saveProfile").show();
	
		$("#profileNext").hide();
		$("#profileAdd").hide();
		$("#editProfile").hide();
		$("#profileClose").show();
		$("#spouseName").prop('disabled', false);
		enableTrashIcon();
	}
	function cancelAddEmployee() {
		$("#profileClose").hide();
	
		// getEmployeeList();
		let existEmpId = $("#existingEmpId").val();
			if (existEmpId) {
				gridOptionsEmployee.api.forEachNode(function(node) {
					if (node.data && node.data.employeeId === existEmpId) {
						node.setSelected(true);
						onSelectionChanged()
						return;
					}
					
				});
			} else {
				gridOptionsEmployee.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
						onSelectionChanged()
					}
					
				});
			}
			
	}
	
	function getManagerListById(id) {
		$.ajax({
			type: "GET",
			url: "view-manage-employee-manager-list?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
	
					$("#managerid_").empty();
					$("#managerid_").append("<option value=''>Select</option>");
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#managerid_").append(option);
					}
				}
			},
			error: function(response) {
			}
		});
	}
	
	function saveWorkDetails() {
		$("#workIdss").val($("#workId").val());
		data = {};
		data.employeeId = $("#employeeId").val();
		data.employeeworkId = $("#workId").val();
		data.startDate = $("#fromdateid_").val();
		data.endDate = $("#todateid_").val();
		data.jobTitle = $("#jobtitleid_").val();
		data.jobType = $("#deginationid_").val();
		data.department = $("#departmentid_").val();
		data.subdepartmentid = $("#subdepartmentid_").val();
		data.employmentStatus = $("#employmentstatusid_").val();
		data.degination = $("#deginationid_").val();
		data.band = $("#bandid_").val();
		data.manager = $("#managerid_").val();
		data.stafftype = $("#stafftypeid_").val();
	
	
		if (data.stafftype == "" || data.stafftype == null) {
			toastr.error("Staff Type Required");
			return;
		}
	
		if (data.department == "" || data.department == null) {
			toastr.error("Department Required");
			return;
		}
	
		if (data.subdepartmentid == "" || data.subdepartmentid == null) {
			toastr.error(" Sub Department Required");
			return;
		}
	
		if (data.employmentStatus == "" || data.employmentStatus == null) {
			toastr.error("Employment Status Required");
			return;
		}
		if (data.jobType == "" || data.jobType == null) {
			toastr.error("Designation Required");
			return;
		}
	
		if (data.manager == "" || data.manager == null) {
			toastr.error("Manager Required");
			return;
		}
		if (data.band == "" || data.band == null) {
			toastr.error("Band Required");
			return;
		}
		var id = $("#employeeId").val();
	
		if (id != null && id != "") {
			submitworkdetails(data);
		} else {
			toatr.error("Opps Something went wrong !")
		}
	
	};
	
	function submitworkdetails(dataset) {
	
	
		$.ajax({
			type: "POST",
			url: "view-manage-employee-workdetails-save",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.code == "Success") {
					closeNav1();
					var id = $("#employeeId").val();
					toastr.success(response.message);
					viewWorkDetails(id);
				}
			},
		})
	}
	
	
	
	function getCurrentTab() {
		const activeTab = document.querySelector(".nav-link.active");
	
		const href = activeTab.getAttribute("href");
		const activeTabId = href.startsWith("#") ? href.substring(1) : href;
	
		if (activeTabId == 'personalInformation') {
			$("#editProfile").show();
			$("#profileAdd").show();
			$("#profileNext").show();
			$("#profilePrev").hide();
			$("#profileClose").hide();
			$("#saveProfile").hide();
			disabledField();
		} else if (activeTabId == 'address') {
			closeNav();
	
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").show();
			$("#profilePrev").show();
			$("#profileClose").hide();
		} else if (activeTabId == 'educational') {
			closeNavEdu();
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").show();
			$("#profilePrev").show();
			$("#profileClose").hide();
		} else if (activeTabId == 'workDetails') {
			closeNav1();
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").show();
			$("#profilePrev").show();
			$("#profileClose").hide();
		} else if (activeTabId == 'bankDetails') {
			closeNav3();
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").show();
			$("#profilePrev").show();
			$("#profileClose").hide();
		} else if (activeTabId == 'salaryDetails') {
			closeNav4();
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").show();
			$("#profilePrev").show();
			$("#profileClose").hide();
		} else if (activeTabId == 'document') {
			closeNav5();
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").show();
			$("#profilePrev").show();
			$("#profileClose").hide();
		} else if (activeTabId == 'ccrDetails') {
			closeNavCCR();
			$("#editProfile").hide();
			$("#saveProfile").hide();
			$("#profileAdd").hide();
			$("#profileNext").hide();
			$("#profilePrev").show();
			$("#profileClose").hide();
		}
	
	}
	
	document.addEventListener("DOMContentLoaded", function() {
		$(".nav-pills .nav-item .nav-link").on("click", function() {
			const activeTabId = $(this).attr("href").substring(1);
			if (activeTabId == 'personalInformation') {
				$("#profileNext").prop('disabled', false);
				$("#editProfile").show();
				$("#saveProfile").show();
				$("#profileAdd").show();
				$("#profileNext").show();
				$("#profilePrev").hide();
				$("#profileClose").hide();
				disabledField();
			} else if (activeTabId == 'address') {
				closeNav();
	
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").show();
				$("#profilePrev").show();
				$("#profileClose").hide();
			} else if (activeTabId == 'educational') {
				closeNavEdu();
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").show();
				$("#profilePrev").show();
				$("#profileClose").hide();
			} else if (activeTabId == 'workDetails') {
				closeNav1();
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").show();
				$("#profilePrev").show();
				$("#profileClose").hide();
			} else if (activeTabId == 'bankDetails') {
				closeNav3();
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").show();
				$("#profilePrev").show();
				$("#profileClose").hide();
			} else if (activeTabId == 'salaryDetails') {
				closeNav4();
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").show();
				$("#profilePrev").show();
				$("#profileClose").hide();
			} else if (activeTabId == 'document') {
				closeNav5();
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").show();
				$("#profilePrev").show();
				$("#profileClose").hide();
			} else if (activeTabId == 'ccrDetails') {
				closeNavCCR();
				$("#editProfile").hide();
				$("#saveProfile").hide();
				$("#profileAdd").hide();
				$("#profileNext").hide();
				$("#profilePrev").show();
				$("#profileClose").hide();
			}
		});
	});
	
	
	function saveDocument() {
	
		$(".formValidation").remove();
		data = {};
	
		data.employeeId = $("#employeeId").val();
		data.documentType = $("#documentType").val();
		data.documentName = $("#imageName_0").html();
		data.status = $("#docStatus").val();
	
		/*var valid = true;*/
		if (data.documentType == null || data.documentType == "") {
			toastr.error("Document Type Required");
			return;
		}
	
		if (data.documentName == null || data.documentName == "") {
			toastr.error("Document  Required");
			return;
		}
		submitDocument(data);
	};
	
	$("#maritalstatusid").on('change', function() {
		var reqtype = $(this).val();
	
		if (reqtype == "TMM/0001") {
			$('#mrgDate').attr("disabled", true);
			$('#mrgDateDiv').hide();
			$('#mrgDateCalendar').hide();
			$('#mrgDate').val("");
		} else {
			$('#mrgDate').attr("disabled", false);
			$('#mrgDateDiv').show();
			$('#mrgDateCalendar').show();
		}
	
	});
	
	
	function submitDocument(dataset) {
	
		var id = $("#employeeId").val();
		$.ajax({
			type: "POST",
			url: "view-manage-employee-document-save",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(dataset),
			success: function(response) {
				if (response.code == "Success") {
					closeNav5();
					getAllDocsByEmpId(id);
					toastr.success(response.message);
	
				}
			},
			error: function(response) { }
		})
	}
	
	function submitbankdetils() {
	
		data = {};
		var id = $("#employeeId").val();
		data.employeeId = $("#employeeId").val();
		data.ebankId = $("#bankId").val();
		data.ebankName = $("#banknameid").val();
		data.ebankAddress = $("#addressid_b").val();
		data.ebankCountry = $("#countryid_b").val();
		data.ebankState = $("#stateid_b").val();
		data.ebankCity = $("#cityid").val();
		data.ebankAccountNo = $("#accountNOb").val();
		data.ebankDocument = $("#imageName_2").html();
		data.eIfic = $("#ificb").val();
	
		ifscVal();
		var ifscno = $("#ificb").val();
	
		if (data.ebankName == null || data.ebankName == "") {
			toastr.error("Bank Name Required");
			return;
		}
	
		if (data.ebankAddress == null || data.ebankAddress == "") {
			toastr.error("Branch Name Required");
			return;
		}
	
		if (ifscno == null || ifscno == "") {
			ifscValid = true;
			toastr.error("IFSC  Required");
			return;
	
		}
	
		if (data.ebankAccountNo == null || data.ebankAccountNo == "") {
			toastr.error("Account No Required");
			return;
		}
	
		if (ifscValid) {
			$.ajax({
				type: "POST",
				url: "view-manage-employee-bankdetails-save",
				dataType: "json",
				contentType: "application/json",
				data: JSON.stringify(data),
				success: function(response) {
					if (response.code == "Success") {
						closeNav3();
						toastr.success(response.message);
						getBankDetailsByEmpId(id);
	
	
					} else {
						toastr.error(response.message);
					}
				},
				error: function(response) { }
			})
		}
	}
	
	function disableTrashIcon() {
		let icon = document.getElementById("deleteImg");
		icon.style.pointerEvents = "none";
		icon.style.opacity = "0.5";
		icon.style.cursor = "not-allowed";
	}
	
	function enableTrashIcon() {
		let icon = document.getElementById("deleteImg");
		icon.style.pointerEvents = "auto";
		icon.style.opacity = "1";
		icon.style.cursor = "pointer";
	}
	
	
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
	
	
	function onQuickFilterChanged() {
		gridOptionsEmployee.api.setQuickFilter(document.getElementById('quickFilter').value);
		$('#totalReq').find('span').html(gridOptionsEmployee.api.getModel().getRowCount());
		getMostClosestRow();
	}
	
	function getMostClosestRow() {
		let searchValue = document.getElementById('quickFilter').value;
		gridOptionsEmployee.api.setQuickFilter(searchValue);
	
		let rowCount = gridOptionsEmployee.api.getModel().getRowCount();
	
		gridOptionsEmployee.api.forEachNodeAfterFilter((node, index) => {
			if (index === 0) {
				node.setSelected(true);
				gridOptionsEmployee.api.ensureIndexVisible(node.rowIndex);
			}
		});
	}
	
	function resetBtn() {
		$("#quickFilter").val('');
		gridOptionsEmployee.api.setQuickFilter('');
		gridOptionsEmployee.api.refreshCells({ force: true });
		setTimeout(() => {
			gridOptionsEmployee.api.forEachNode((node, index) => {
				if (index === 0) {
					node.setSelected(true);
	
					gridOptionsEmployee.api.ensureIndexVisible(node.rowIndex);
				}
			});
		}, 50);
	}
	
	function getDirectReport(id) {
		//Employee Team
		$.ajax({
			type: "GET",
			url: "view-manage-employee-team-list?id=" + id,
			success: function(response) {
				if (response.message == "Success") {
					if (response.body.length > 0) {
						let wrapDivStart = `<div class="row row-cols-3 g-3">`;
						let div = "";
						let wrapDivEnd = `</div>`;
						$("#forwardto").empty();
						for (var i = 0; i < response.body.length; i++) {
							var name = response.body[i].name;
							$("#emplList").text(response.body.length);
							// div = div + "<div><a href='javascript:void(0)'>" + response.body[i].name + "</a></div>";
							div = `${div}<div class="col text-center"><a class="p-1 border rounded w-100 d-block" href='javascript:void(0)'>${response.body[i].name}</a></div>`;
						}
						div = `${wrapDivStart} ${div} ${wrapDivEnd}`
						$("#forwardto").append(div);
					} else {
						$("#emplList").text("0");
						$("#forwardto").empty();
					}
				}
			},
			error: function(response) {
			}
		});
	}
	
	
	function getCandidateDetails() {
		var id = $("#offerLetterId").val();
		if (id) {
			$.ajax({
				type: 'GET',
				url: 'new-employee-getCandDetails?id=' + id,
				contentType: false,
				success: function(response) {
					if (response.message == "Success") {
						console.log("response.body===", response.body[0]);
						console.log('response>>>', response);
	
						// Use a function to set values in the visible container
						setFieldValue('firstnameid', response.body[0].firstName);
						setFieldValue('lastnameid', response.body[0].lastName);
						setFieldValue('genderid', response.body[0].gender);
						setFieldValue('dobid', response.body[0].dob);
						setFieldValue('dobCalendar', response.body[0].dob);
						setFieldValue('bloodgroupid', response.body[0].bloodGroup);
						setFieldValue('maritalstatusid', response.body[0].maritalStatus);
						setFieldValue('nationalityid', response.body[0].nationality);
						setFieldValue('fatherid', response.body[0].fatherName);
						setFieldValue('motherid', response.body[0].motherName);
						setFieldValue('mobilenoid', response.body[0].mobileNo);
						setFieldValue('personalmailid', response.body[0].personalEmail);
						setFieldValue('workmailid', response.body[0].workEmail);
						setFieldValue('aadhaar', response.body[0].aadharNo);
						setFieldValue('panno', response.body[0].panNo);
						setFieldValue('ememobilenoid', response.body[0].emergencyMob);
						setFieldValue('joiningdate', response.body[0].joiningDate);
	
						setTextValue('nameDtls', response.body[0].firstName + " " + response.body[0].lastName);
						setTextValue('locationDtls', response.body[0].location);
						
						if(response.body[0].mobileNo) {
							$('#mobileNoDtls').show();
							setTextValue('mobileNoDtls', response.body[0].mobileNo);
						} else {
							$('#mobileNoDtls').hide();
						}
						
						
						if (response.body[0].maritalStatus == "TMM/0002") {
							$("#spouseDiv").removeClass('d-none');
							$("#spouseName").prop('disabled', false);
						} else {
							$("#spouseDiv").addClass('d-none');
						}
						
						setTextValue('emailDtls', response.body[0].personalEmail);
						setFieldValue('canimghid', response.body[0].fileUpload);
	
						$(".mainSecond").show();
	
						$('#imgemp').attr('src', '');
						if (response.body[0].fileUpload == null || response.body[0].fileUpload == "") {
							$('#imgemp').attr('src', '../assets/images/noimage.jpg');
						} else {
							$('#imgemp').attr('src', response.body[0].fileUploadFile);
						}
					}
				},
				error: function(e) {
					console.error("Error fetching candidate details:", e);
				}
			});
		} else {
			clearVisibleFields();
		}
	}
	
	function setFieldValue(fieldId, value) {
		const fields = document.querySelectorAll(`#${fieldId}`);
		fields.forEach(field => {
			const parentDiv = field.closest('div');
			if (parentDiv && !parentDiv.classList.contains('d-none')) {
				field.value = value;
			}
		});
	}
	
	
	function SearchUserInput(event) {
		if (event.key === "Enter" || event.keyCode === 13) {
			onQuickFilterChanged();
			
		}
	}
	
	function disabledField() {
	
		$("#selectFile").prop('disabled', true);
		$("#employeeId").prop('disabled', true);
		$("#firstnameid").prop('disabled', true);
		$("#lastnameid").prop('disabled', true);
		$("#genderid").prop('disabled', true);
		$("#dobid").prop('disabled', true);
		$("#bloodgroupid").prop('disabled', true);
		$("#maritalstatusid").prop('disabled', true);
		$("#nationalityid").prop('disabled', true);
		$("#fatherid").prop('disabled', true);
		$("#motherid").prop('disabled', true);
		$("#mobilenoid").prop('disabled', true);
		$("#personalmailid").prop('disabled', true);
		$("#workmailid").prop('disabled', true);
		$("#panno").prop('disabled', true);
		$("#epfno").prop('disabled', true);
		$("#esicno").prop('disabled', true);
		$("#aadhaar").prop('disabled', true);
		$("#joiningdate").prop('disabled', true);
		$("#mrgDate").prop('disabled', true);
		$("#ememobilenoid").prop('disabled', true);
		$("#dobidCalendar").prop('disabled', true);
		$("#dobidCalendar").css("pointer-events", "auto").addClass("disabled");
		$("#spouseName").prop('disabled', true);
	
	
	}
	
	
	
	function saveMultiFileDoc1(event) {
	    $(".formValidation").remove();
	
	    var currentFldId = event.currentTarget.getAttribute("id"); 
	    var counter = currentFldId.split("_")[1];
	    var fileInput = $("#" + currentFldId)[0];
	    var uFile = fileInput.files[0];
	
	    if (!uFile) {
	        toastr.error("No file selected!");
	        return;
	    }
	
	    var fileName = fileInput.value.split("\\").pop();
	    var extension = fileName.split(".").pop().toLowerCase();
	    var iURL = URL.createObjectURL(uFile);
	
	    var fileIcons = {
	        "jpg": "fa-file-image",
	        "png": "fa-file-image",
	        "jpeg": "fa-file-image",
	        "pdf": "fa-file-pdf",
	        "xls": "fa-file-excel",
	        "xlsx": "fa-file-excel",
	        "doc": "fa-file-word",
	        "docx": "fa-file-word",
	        "mp4": "fa-file-video",
	        "mov": "fa-file-video",
	        "mp3": "fa-file-audio",
	        "wav": "fa-file-audio",
	        "aac": "fa-file-audio",
	        "txt": "fa-file-alt"
	    };
	
	    var iconClass = fileIcons[extension] || "fa-file"; // Default fallback
	
	    var LightImg = `
	        <div class='d-flex gap-2'>
	            <a class='example-image-link' href='${iURL}' title='${fileName}' target='_blank'>
	                <i class='fa ${iconClass} custom-file-icon'></i>
	            </a>
	            <span class="position-relative"><i class='ti-close red deleteFile' data-counter='${counter}'></i></span>
	        </div>
	    `;
	
	    $("#uploadedBillDiv_" + counter).html(LightImg);
	    $("#imageName_" + counter).html(fileName);
	
	    $(".deleteFile").off("click").on("click", function () {
	        var counterToDelete = $(this).data("counter");
	        $("#uploadedBillDiv_" + counterToDelete).html("");
	        $("#imageName_" + counterToDelete).html("");
	        $("#" + currentFldId).val("");
	    });
	
	    var fileData = new FormData();
	    fileData.append("file", uFile);
	    fileData.append("path", "none");
	
	    console.log("Uploading file:", fileName, "Extension:", extension);
	    $.ajax({
	        type: "POST",
	        url: "view-manage-employee-doc-upload-file",
	        enctype: "multipart/form-data",
	        contentType: false,
	        data: fileData,
	        processData: false,
	        cache: false,
	        success: function (response) {
	            console.log("File uploaded successfully:", response);
	        },
	        error: function (error) {
	            console.error("File upload failed:", error);
	        }
	    });
	}
	
	function saveMultiFileDoc2(event) {
	    $(".formValidation").remove();
	
	    var currentFldId = event.currentTarget.getAttribute("id"); 
	    var counter = currentFldId.split("_")[1];
	    var fileInput = $("#" + currentFldId)[0];
	    var uFile = fileInput.files[0];
	
	    if (!uFile) {
	        toastr.error("No file selected!");
	        return;
	    }
	
	    var fileName = fileInput.value.split("\\").pop();
	    var extension = fileName.split(".").pop().toLowerCase();
	    var iURL = URL.createObjectURL(uFile);
	
	    var fileIcons = {
	        "jpg": "fa-file-image",
	        "png": "fa-file-image",
	        "jpeg": "fa-file-image",
	        "pdf": "fa-file-pdf",
	        "xls": "fa-file-excel",
	        "xlsx": "fa-file-excel",
	        "doc": "fa-file-word",
	        "docx": "fa-file-word",
	        "mp4": "fa-file-video",
	        "mov": "fa-file-video",
	        "mp3": "fa-file-audio",
	        "wav": "fa-file-audio",
	        "aac": "fa-file-audio",
	        "txt": "fa-file-alt"
	    };
	
	    var iconClass = fileIcons[extension] || "fa-file"; 
	
	    var LightImg = `
	        <div class='d-flex gap-2'>
	            <a class='example-image-link' href='${iURL}' title='${fileName}' target='_blank'>
	                <i class='fa ${iconClass} custom-file-icon1'></i>
	            </a>
	            <span><i class='ti-close red deleteFileDoc' data-counter='${counter}'></i></span>
	        </div>
	    `;
	
	    $("#uploadedBillDiv_" + counter).html(LightImg);
	    $("#imageName_" + counter).html(fileName);
	
	
	    $(".deleteFile1").off("click").on("click", function () {
	        var counterToDelete = $(this).data("counter");
	        $("#uploadedBillDiv_" + counterToDelete).html("");
	        $("#imageName_" + counterToDelete).html("");
	        $("#" + currentFldId).val(""); 
	    });
	
	    // Prepare file upload
	    var fileData = new FormData();
	    fileData.append("file", uFile);
	    fileData.append("path", "none");
	
	    console.log("Uploading file:", fileName, "Extension:", extension);
	
	    // Send file via AJAX
	    $.ajax({
	        type: "POST",
	        url: "view-manage-employee-doc-upload-file",
	        enctype: "multipart/form-data",
	        contentType: false,
	        data: fileData,
	        processData: false,
	        cache: false,
	        success: function (response) {
	            console.log("File uploaded successfully:", response);
	        },
	        error: function (error) {
	            console.error("File upload failed:", error);
	        }
	    });
	}
	function saveMultiFileDoc(event) {
	    $(".formValidation").remove();
	
	    var currentFldId = event.currentTarget.getAttribute("id"); 
	    var counter = currentFldId.split("_")[1];
	    var fileInput = $("#" + currentFldId)[0];
	    var uFile = fileInput.files[0];
	
	    if (!uFile) {
	        toastr.error("No file selected!");
	        return;
	    }
	
	    var fileName = fileInput.value.split("\\").pop();
	    var extension = fileName.split(".").pop().toLowerCase();
	    var iURL = URL.createObjectURL(uFile);
	
	    var fileIcons = {
	        "jpg": "fa-file-image",
	        "png": "fa-file-image",
	        "jpeg": "fa-file-image",
	        "pdf": "fa-file-pdf",
	        "xls": "fa-file-excel",
	        "xlsx": "fa-file-excel",
	        "doc": "fa-file-word",
	        "docx": "fa-file-word",
	        "mp4": "fa-file-video",
	        "mov": "fa-file-video",
	        "mp3": "fa-file-audio",
	        "wav": "fa-file-audio",
	        "aac": "fa-file-audio",
	        "txt": "fa-file-alt"
	    };
	
	    var iconClass = fileIcons[extension] || "fa-file";
	
	    var LightImg = `
	        <div class='d-flex gap-2'>
	            <a class='example-image-link' href='${iURL}' title='${fileName}' target='_blank'>
	                <i class='fa ${iconClass} custom-file-icon2'></i>
	            </a>
	            <span><i class='ti-close red deleteFileDoc' data-counter='${counter}'></i></span>
	        </div>
	    `;
	
	    $("#uploadedBillDiv_" + counter).html(LightImg);
	    $("#imageName_" + counter).html(fileName);
	    
	    $(".deleteFile1").off("click").on("click", function () {
	        var counterToDelete = $(this).data("counter");
	        $("#uploadedBillDiv_" + counterToDelete).html("");
	        $("#imageName_" + counterToDelete).html("");
	        $("#" + currentFldId).val(""); // Clear file input
	    });
	
	    // Prepare file upload
	    var fileData = new FormData();
	    fileData.append("file", uFile);
	    fileData.append("path", "none");
	
	    console.log("Uploading file:", fileName, "Extension:", extension);
	
	    // Send file via AJAX
	    $.ajax({
	        type: "POST",
	        url: "view-manage-employee-doc-upload-file",
	        enctype: "multipart/form-data",
	        contentType: false,
	        data: fileData,
	        processData: false,
	        cache: false,
	        success: function (response) {
	            console.log("File uploaded successfully:", response);
	        },
	        error: function (error) {
	            console.error("File upload failed:", error);
	        }
	    });
	}
	
	
	function saveMultiFileDocCCR(event) {
	    $(".formValidation").remove();
	
	    var currentFldId = event.currentTarget.getAttribute("id");
	    var fileInput = $("#" + currentFldId)[0];
	    var uFile = fileInput.files[0];
	
	    if (!uFile) {
	        toastr.error("No file selected!");
	        return;
	    }
	
	    var fileName = fileInput.value.split("\\").pop();
	    var extension = fileName.split(".").pop().toLowerCase();
	    var iURL = URL.createObjectURL(uFile);
	
	    var fileIcons = {
	        "jpg": "fa-file-image",
	        "png": "fa-file-image",
	        "jpeg": "fa-file-image",
	        "pdf": "fa-file-pdf",
	        "xls": "fa-file-excel",
	        "xlsx": "fa-file-excel",
	        "doc": "fa-file-word",
	        "docx": "fa-file-word",
	        "mp4": "fa-file-video",
	        "mov": "fa-file-video",
	        "mp3": "fa-file-audio",
	        "wav": "fa-file-audio",
	        "aac": "fa-file-audio",
	        "txt": "fa-file-alt"
	    };
	
	    var iconClass = fileIcons[extension] || "fa-file";
	
	    var LightImg = `
	        <div class='hstack gap-2 order-3'>
	            <a class='example-image-link uploadicon m-0 p-0' href='${iURL}' title='${fileName}' target='_blank'>
	                <i class='fa ${iconClass} custom-file-iconCCR'></i>
	            </a>
	            <span class="uploadicon m-0 p-0"><i class='ti-close red deleteFileCCR2'></i></span>
	        </div>
	    `;
	
	    $("#uploadedBillDiv_CCR").html(LightImg);
	    $("#imageName_CCR").html(fileName);
	
	    $(".deleteFileCCR2").off("click").on("click", function () {
	        $("#uploadedBillDiv_CCR").html("");
	        $("#imageName_CCR").html("");
	        $("#" + currentFldId).val(""); 
	    });
	
	    // Prepare file upload
	    var fileData = new FormData();
	    fileData.append("file", uFile);
	    fileData.append("path", "none");
	
	    console.log("Uploading file:", fileName, "Extension:", extension);
	
	    // Send file via AJAX
	    $.ajax({
	        type: "POST",
	        url: "view-manage-employee-doc-upload-file",
	        enctype: "multipart/form-data",
	        contentType: false,
	        data: fileData,
	        processData: false,
	        cache: false,
	        success: function (response) {
	            console.log("File uploaded successfully:", response);
	        },
	        error: function (error) {
	            console.error("File upload failed:", error);
	        }
	    });
	}
	function editEducationDetails(id, empid) {
	    $('#profilePrev').attr("disabled", true);
	    $('#profileNext').attr("disabled", true);
	
	    $.ajax({
	        type: "GET",
	        url: "view-manage-employee-education-edit?eduid=" + id + "&empid=" + empid,
	        async: false,
	        success: function(response) {
	            if (response.message == "Success") {
	                $("#eduDiv").show();
	                $("#profilePrev").hide();
					$("#profileNext").hide();
	
	                $("#eduId").val(response.body.eduId);
	                getQualifyTypeList(response.body.qualification);
	                $("#qualification").val(response.body.qualification);
	                $("#instiname").val(response.body.instiname);
	                $("#passyear").val(response.body.passyear);
	                $("#docStatus").val(response.body.status);
	
	                var fileName = response.body.docName;
	                var docDet = `
	                    <div class="control-group">
	                        <label class="custom-file-upload btn go-btn h-auto" for="uploadDoc_1">
	                            <i class="ti-plus"></i>
	                        </label>
	                        <div class="controls">
	                            <input type="file" class="document" id="uploadDoc_1"
	                                accept=".jpeg, .jpg, .png, .pdf, .doc, .docx"
	                                name="userImage" onchange="saveMultiFileDoc1(event)" />
	                        </div>
	                    </div>
	                    <input type="hidden" id="uploadHidden1" class="uploadHidCls">
	                    <div id="uploadedBillDiv_1" class="uploadedBillCls"></div>
	                    <div id="imageName_1" class="imageName"></div>
	                    <div id="validationDiv1"></div>`;
	
	                $("#documentDiv").html(docDet);
	
	                if (fileName != null) {
	                    var extension = fileName.split(".").pop().toLowerCase();
	                    $("#imageName_1").html(fileName);
	
	                    var fileIcons = {
	                        "jpg": "fa-file-image",
	                        "png": "fa-file-image",
	                        "jpeg": "fa-file-image",
	                        "pdf": "fa-file-pdf",
	                        "doc": "fa-file-word",
	                        "docx": "fa-file-word"
	                    };
	
	                    var iconClass = fileIcons[extension] || "fa-file"; 
	
	                    var LightImg = `
	                        <div class="d-flex gap-2">
	                            <a class="example-image-link" target="_blank">
	                                <i class="fa-solid ${iconClass} custom-file-icon" onclick=viewImage('${fileName}')></i>
	                            </a>
	                            <span><i class="ti-close red close_sec1 deleteFileEd"></i></span>
	                        </div>
	                    `;
						if(fileName) {
	                    	$("#uploadedBillDiv_1").html(LightImg);
	                    }
	                    $(".deleteFileEd").off("click").on("click", function () {
	                        $("#uploadedBillDiv_1").html("");
	                        $("#imageName_1").html("");
	                        $("#uploadDoc_1").val(""); 
	                    });
	                }
	            }
	        }
	    });
	}
	
	function editBankDetails(id) {
	    if ($("#adRole").val() != "") {
	        $.ajax({
	            type: "GET",
	            url: "view-manage-employee-bank-edit?id=" + id,
	            async: false,
	            success: function(response) {
	                if (response.message == "Success") {
	                    openNav3();
	                    $("#myGrid3").hide();
	                    $("#bsave").show();
	
	                    $("#ebankId").val(id);
	                    $('#bankId').val(response.body.ebankId);
	                    $('#banknameid').val(response.body.ebankNameid);
	                    $('#addressid_b').val(response.body.ebankAddress);
	                    $('#countryid_b').val(response.body.ebankCountryid);
	                    $('#accountNOb').val(response.body.ebankAccountNo);
	                    $('#ificb').val(response.body.eIfic);
	
	                    var fileName = response.body.ebankDocument;
	                    var docDet = `
	                        <div class="control-group">
	                            <label class="custom-file-upload btn go-btn h-auto" for="uploadDoc_2"> 
	                                <i class="ti-plus"></i>
	                            </label>
	                            <div class="controls">
	                                <input type="file" class="document" id="uploadDoc_2"
	                                    accept=".jpeg, .jpg, .png, .pdf, .doc, .docx"
	                                    name="userImage" onchange="saveMultiFileDoc2(event)" />
	                            </div>
	                        </div>
	                        <input type="hidden" id="uploadHidden2" class="uploadHidCls">
	                        <div id="uploadedBillDiv_2" class="uploadedBillCls"></div>
	                        <div id="imageName_2" class="imageName"></div>
	                        <div id="validationDiv2"></div>`;
							$("#documentDivBank").html(docDet);
	                    
	
	                    if (fileName != null) {
	                        var extension = fileName.split(".").pop().toLowerCase();
	                        $("#imageName_2").html(fileName);
	
	                        var fileIcons = {
	                            "jpg": "fa-file-image",
	                            "png": "fa-file-image",
	                            "jpeg": "fa-file-image",
	                            "pdf": "fa-file-pdf",
	                            "doc": "fa-file-word",
	                            "docx": "fa-file-word"
	                        };
	
	                        var iconClass = fileIcons[extension] || "fa-file"; 
	
	                        var LightImg = `
	                            <div class="d-flex gap-2">
	                                <a class="example-image-link" target="_blank">
	                                    <i class="fa-solid ${iconClass} custom-file-iconBank" onclick=viewImage('${fileName}')></i>
	                                </a>
	                                <span><i class="ti-close red close_sec1 deleteFileBank"></i></span>
	                            </div>
	                        `;
							if(fileName) {
	                        	$("#uploadedBillDiv_2").html(LightImg);
	                        }
	                        $(".deleteFileBank").off("click").on("click", function () {
	                            $("#uploadedBillDiv_2").html("");
	                            $("#imageName_2").html("");
	                            $("#uploadDoc_2").val("");
	                        });
	                    }
	                }
	            }
	        });
	    } else {
	        toastr.error("Only Admin can modify bank details!");
	    }
	}
	
	function editDoc(id) {
		
		var sid = $("#sessionId").val();
		var empid = $('#employeeId').val();
		var srole = $("#adRole").val();
		if (empid == sid) {
			editDocumentDetails(id)
		} else {
			if (srole != "") {
				editDocumentDetails(id)
			} else {
				toastr.error("It is eligible for self and Admin!");
			}
		}
	}
	
	function editDocumentDetails(id) {
	    let empId = $("#employeeId").val();
	
	    $.ajax({
	        type: "GET",
	        url: `view-manage-employee-document-edit?docType=${id}&empid=${empId}`,
	        async: false,
	        success: function(response) {
	            if (response.message === "Success") {
	                openNav5();
	                $("#myGrid5").hide();
	                $("#documentType").attr("disabled", true);
	                $("#addDocTypeIcon").hide();
	                $("#ddtype").removeClass("select");
	                $("#docStatus").val(response.body.status);
	
	                var fileName = response.body.documentName;
	                var docDet = `
	                    <div class="control-group">
	                        <label class="custom-file-upload btn go-btn h-auto" for="uploadDoc_0"> 
	                            <i class="ti-plus"></i>
	                        </label>
	                        <div class="controls">
	                            <input type="file" class="document" id="uploadDoc_0"
	                                accept=".jpeg, .jpg, .png, .pdf, .doc, .docx"
	                                name="userImage" onchange="saveMultiFileDoc(event)" />
	                        </div>
	                    </div>
	                    <input type="hidden" id="uploadHidden_0" class="uploadHidCls">
	                    <div id="uploadedBillDiv_0" class="uploadedBillCls"></div>
	                    <div id="imageName_0" class="imageName"></div>
	                    <div id="validationDiv_0"></div>`;
	
	                $("#documentDivBank").html(docDet);
	
	                if (fileName) {
	                    var extension = fileName.split(".").pop().toLowerCase();
	                    $("#imageName_0").html(fileName);
	
	                    var fileIcons = {
	                        "jpg": "fa-file-image",
	                        "png": "fa-file-image",
	                        "jpeg": "fa-file-image",
	                        "pdf": "fa-file-pdf",
	                        "doc": "fa-file-word",
	                        "docx": "fa-file-word"
	                    };
	
	                    var iconClass = fileIcons[extension] || "fa-file";
	
	                    var LightImg = `
	                        <div class="d-flex gap-2">
	                            <a class="example-image-link" target="_blank">
	                                <i class="fa-solid ${iconClass} custom-file-iconDoc" onclick=viewImage('${fileName}')></i>
	                            </a>
	                            <span><i class="ti-close red close_sec1 deleteFileDoc"></i></span>
	                        </div>`;
					if(fileName) {
	                    $("#uploadedBillDiv_0").html(LightImg);
					}
	                    $(".deleteFileDoc").off("click").on("click", function () {
	                        $("#uploadedBillDiv_0").html("");
	                        $("#imageName_0").html("");
	                        $("#uploadDoc_0").val("");
	                    });
	                }
	
	                var doctype = response.body.documentType;
	                $.ajax({
	                    type: "GET",
	                    url: "view-manage-employee-documenttype-list?empid=edit",
	                    success: function(response) {
	                        if (response.message === "success") {
	                            $("#documentType").empty().append("<option value=''>Select</option>");
	                            response.body.forEach(item => {
	                                var option = $("<option></option>").val(item.key).html(item.name);
	                                $("#documentType").append(option);
	                            });
	                            $("#documentType").val(doctype);
	                        }
	                    },
	                    error: function() {
	                        $("#documentType").empty().append("<option value=''>Select</option>");
	                    }
	                });
	            }
	        }
	    });
	}
	
	
	function editCCR(id) {
	    if ($("#adRole").val() != "") {
	        let empId = $("#employeeId").val();
	
	        $.ajax({
	            type: "GET",
	            url: `view-manage-employee-ccr-edit?id=${id}&empId=${empId}`,
	            async: false,
	            success: function(response) {
	                if (response.code === "success") {
	                    openNavCCR();
	                    $("#myGridCCR").hide();
	
	                    let jsonData = JSON.parse(response.body);
	                    let allData = jsonData.reviewlist;
	
	                    if (allData.length > 0) {
	                        let review = allData[0];
	
	                        $("#reviewId").val(review.reviewId);
	                        $('#dateCCR').val(review.date);
	                        getCCRTypeList(review.type);
	                        $('#subject').val(review.subject);
	                        $("#remark").val(window.atob(review.remark));
	                        $('#expectedResult').val(window.atob(review.expectedResult));
	                        $('#guidedBy').val(review.guidedBy);
	
	                        var fileName = review.docName;
	                        var docDet = `
	                            <div class="control-group">
	                                <label class="custom-file-upload btn go-btn h-auto" for="uploadDoc_CCR"> 
	                                    <i class="ti-plus"></i>
	                                </label>
	                                <div class="controls">
	                                    <input type="file" class="document" id="uploadDoc_CCR"
	                                        accept=".jpeg, .jpg, .png, .pdf, .doc, .docx"
	                                        name="userImage" onchange="saveMultiFileDocCCR(event)" />
	                                </div>
	                            </div>
	                            <input type="hidden" id="uploadHiddenCCR" class="uploadHidCls">
	                            <div id="uploadedBillDiv_CCR" class="uploadedBillCls order-3"></div>
	                            <div id="imageName_CCR" class="imageName"></div>
	                            <div id="validationDiv_CCR"></div>`;
	
	                        $("#documentDivCCR").html(docDet);
	
	                        if (fileName != null) {
	                            var extension = fileName.split(".").pop().toLowerCase();
	                            $("#imageName_CCR").html(fileName);
	
	                            var fileIcons = {
	                                "jpg": "fa-file-image",
	                                "png": "fa-file-image",
	                                "jpeg": "fa-file-image",
	                                "pdf": "fa-file-pdf",
	                                "doc": "fa-file-word",
	                                "docx": "fa-file-word"
	                            };
	
	                            var iconClass = fileIcons[extension] || "fa-file"; 
	
	                            var LightImg = `
	                                <div class="d-flex gap-2">
	                                    <a class="example-image-link" target="_blank">
	                                        <i class="fa-solid ${iconClass} custom-file-iconCCR1" onclick=viewImage('${fileName}')></i>
	                                    </a>
	                                    <span><i class="ti-close red close_sec1 deleteFileCCR1"></i></span>
	                                </div>
	                            `;
							if(fileName) {
	                            $("#uploadedBillDiv_CCR").html(LightImg);
	                        }
	                            $(".deleteFileCCR1").off("click").on("click", function () {
	                                $("#uploadedBillDiv_CCR").html("");
	                                $("#imageName_CCR").html("");
	                                $("#uploadDoc_CCR").val("");
	                            });
	                        }
	                    }
	                }
	            }
	        });
	    } else {
	        toastr.error("Only Admin can modify CCR details!");
	    }
	}
	
	function getHideDuringEmployee(){
		
		var role=$("#adRole").val();
		

		if (role != "") {
			
			$("#addWork").prop('disabled',false);
			$("#addBank").prop('disabled',false);
			$("#addSalary").prop('disabled',false);
			$("#addCCR").prop('disabled',false);
			}else{
				$("#addWork").prop('disabled',true);
				$("#addBank").prop('disabled',true);
				$("#addSalary").prop('disabled',true);
				$("#addCCR").prop('disabled',true);
			}
	}
	
function spouseFun(){
	var maritalid = $("#maritalstatusid").val();
	if(maritalid == 'TMM/0002'){
		$("#spouseDiv").removeClass('d-none');
	}else{
		$("#spouseDiv").addClass('d-none');
	}
}