	$(document).ready(function() {
	
		var gridDiv = document.querySelector('#appraisalGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		gridOptions.api.setRowData([]);
		
		getFinancialYear();
	
		getEmpDetails();
	
	
	});
	
	
	/*getting list of all employee*/
/*	function getEmpDetails() {
		agGrid.simpleHttpRequest({
			url: "get-all-employee-list"
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body);
				const goalDetails = responseBody.employees;
				var newRowData = goalDetails.reverse();
				gridOptions.api.setRowData(newRowData);
	
	
				if (newRowData && newRowData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
	
			} else {
				console.error("Failed to fetch data");
			}
		});
	
	}*/
	
	
	function getEmpDetails() {

	const finYear = $("#financialYear").val();

	agGrid.simpleHttpRequest({
		url: "get-all-employee-list?finYear=" + finYear,
	}).then(function(response) {
		if (response.code === "Success") {
			const responseBody = JSON.parse(response.body);
			const goalDetails = responseBody.employees;
			if (goalDetails == null) {
				gridOptions.api.setRowData([]);
				getEmployeBandGoalDetails('', '');
				//$("#saveSelfApp").hide();		
			} else {
				var newRowData = goalDetails.reverse();
				gridOptions.api.setRowData(newRowData);


				if (newRowData && newRowData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
			}

		} else {
			console.error("Failed to fetch data");
		}
	});

}
	
	
	const appraisalColumnDefs = [
		{ headerCheckboxSelection: true, checkboxSelection: true, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },
		{ headerName: "Employee ID", field: "empId" },
		{ headerName: "Name", field: "empName" },
		{ headerName: "Designation", field: "designationName" },
		{ headerName: "Band", field: "bandName" },
		{ headerName: "Email ID", field: "personalMail" },
		{ headerName: "Bamd Id", field: "bandId", hide: true }
	];
	
	const gridOptions = {
		columnDefs: appraisalColumnDefs,
		defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
		rowSelection: 'single',
		pagination: true,
		paginationPageSize: 15,
		rowData: [],
		onSelectionChanged: rowSelect
	};
	
	
	function rowSelect() {
	
		var selectedRows = gridOptions.api.getSelectedRows();
		if (selectedRows.length > 0) {
			const datas = selectedRows[0];
			const bandId = (datas.bandId);
			const empId = (datas.empId);
			$("#empNames").text(datas.empName);
			getEmployeBandGoalDetails(bandId, empId);
	
		} else {
			$("#saveSelfApp").hide();
	
			getEmployeBandGoalDetails('', '');
	
		}
	}
	
	
	function getDesigGoalById(id, empId) {
		
		const finYear=$("#financialYear").val();
		
		$("input[name='rating']").prop("checked", false);
		$("#summary1").val('');
		$("#requirementDescription").val('');
		$("#getEmpDiv").hide();
	
		$("input[name='ratingMan']").prop("checked", false);
		$("#summary2").val('');
		$("#managerResponse").val('');
		$("#saveSelfApp").hide();
		agGrid.simpleHttpRequest({
			url: "get-all-desig-goal-details-self?id=" + id + "&empId=" + empId + "&finYear=" + finYear,
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body[0]);
				const depGoalDetails = responseBody.desigGoalDetails;
	
				populateDepGoalTable(depGoalDetails);
			}
		});
	}
	
	function populateDepGoalTable(depGoalDetails) {
		$("#appraisalId").val('');
		const tableBody = $("#kra-table-dep tbody");
		tableBody.empty();
	
		const userIdLogin = document.getElementById("userId").value;
		var selectedRows = gridOptions.api.getSelectedRows();
		const datas = selectedRows[0];
		const empId = datas.empId;
	
		if (!depGoalDetails || depGoalDetails.length === 0) {
			$("#kra-table-dep tbody").empty();
			return;
		}
	
	
	
		let goalCounter = 0;
		const groupedGoals = {};
	
		depGoalDetails.forEach(goal => {
			const desigKey = `${goal.desigGoalId}::${goal.desigGoalName}`;
			if (!groupedGoals[desigKey]) {
				groupedGoals[desigKey] = {};
			}
	
			const kraKey = `${goal.kraList.kraId}::${goal.kraList.kraName}`;
			if (!groupedGoals[desigKey][kraKey]) {
				groupedGoals[desigKey][kraKey] = [];
			}
	
			groupedGoals[desigKey][kraKey].push({
				kpiList: goal.kpiList || [],
				weightage: goal.weightage || 0
			});
		});
	
		Object.entries(groupedGoals).forEach(([desigKey, kraGroup]) => {
			goalCounter++;
			const [desigGoalId, desigGoalName] = desigKey.split("::");
	
			let desigRowHtml = `
		            <tr class="designation-header">
		                   <td colspan="6" class="designation-style">
		                    ${goalCounter}. Designation Name: ${desigGoalName} (${desigGoalId})
		                </td>
		            </tr>`;
			tableBody.append(desigRowHtml);
	
			let kraCounter = 0;
			Object.entries(kraGroup).forEach(([kraKey, kpiList]) => {
				kraCounter++;
				const [kraId, kraName] = kraKey.split("::");
	
				let kraRowHtml = `
		                <tr class="kra-header">
		                       <td colspan="6" class="kra-style">
		                        ${goalCounter}.${kraCounter} KRA: ${kraName} (${kraId})
		                    </td>
		                </tr>`;
				tableBody.append(kraRowHtml);
	
				let kpiCounter = 0;
				kpiList.forEach((kpiData) => {
					kpiData.kpiList.forEach((kpi, kpiSubIndex) => {
						kpiCounter++;
						let safeDesigGoalId = desigGoalId.replace(/\W/g, "_");
						let safeKraId = kraId.replace(/\W/g, "_");
						let selectId = `ratingsDropdown-${safeDesigGoalId}-${safeKraId}-${kpiSubIndex}`;
						let managerSelectId = `managerRatingsDropdown-${safeDesigGoalId}-${safeKraId}-${kpiSubIndex}`;
						let notesId = `notes-${safeDesigGoalId}-${safeKraId}-${kpiSubIndex}`;
						let managerNotesId = `managerNotes-${safeDesigGoalId}-${safeKraId}-${kpiSubIndex}`;
	
						let kpiHtml = `
		                        <tr class="kpi-row">
		                            <td class="kpi-style" style="width: 20%;">
		                                ${goalCounter}.${kraCounter}.${kpiCounter} KPI: ${kpi.kpiName}
		                            </td>
		                            <td style="width: 15%;">
		                                <input type="number" class="form-control weightage-input" value="${kpiData.weightage}" disabled>
		                            </td>
		                            <td style="width: 15%;">
		                                <select class="form-control ratings-dropdown" id="${selectId}">
		                                    <option value="">Select Rating</option>
		                                    <option value="1">1 - Poor</option>
		                                    <option value="2">2 - Fair</option>
		                                    <option value="3">3 - Good</option>
		                                    <option value="4">4 - Very Good</option>
		                                    <option value="5">5 - Excellent</option>
		                                </select>
		                            </td>
		                            <td style="width: 20%;">
		                                <input type="text" class="form-control notes-input" id="${notesId}" placeholder="Enter Notes">
		                            </td>
		                            <td style="width: 15%;">
		                                <select class="form-control manager-ratings-dropdown" id="${managerSelectId}">
		                                    <option value="">Select Rating</option>
		                                    <option value="1">1 - Poor</option>
		                                    <option value="2">2 - Fair</option>
		                                    <option value="3">3 - Good</option>
		                                    <option value="4">4 - Very Good</option>
		                                    <option value="5">5 - Excellent</option>
		                                </select>
		                            </td>
		                            <td style="width: 20%;">
		                                <input type="text" class="form-control manager-notes-input" id="${managerNotesId}" placeholder="Enter Notes">
		                            </td>
		                        </tr>`;
						tableBody.append(kpiHtml);
	
						$(`#${CSS.escape(selectId)}`).select2({ placeholder: "Select Rating", allowClear: true });
						$(`#${CSS.escape(managerSelectId)}`).select2({ placeholder: "Select Rating", allowClear: true });
	
						// Disable based on user role
						if (userIdLogin === empId) {
							$(`#${CSS.escape(managerSelectId)}`).prop("disabled", true);
							$(`#${CSS.escape(managerNotesId)}`).prop("disabled", true);
							$("input[name='ratingMan']").prop("disabled", true);
							$("#summary2").prop("disabled", true);
							$("#managerResponse").prop("disabled", true);
						} else {
							$(`#${CSS.escape(selectId)}`).prop("disabled", true);
							$(`#${CSS.escape(notesId)}`).prop("disabled", true);
							$("input[name='rating']").prop("disabled", true);
							$("#summary1").prop("disabled", true);
							$("#requirementDescription").prop("disabled", true);
						}
					});
				});
			});
		});
	}
	
	
	
	function generateAppraisalJSONEmp() {
		
		const finYear = $("#financialYear").val();
	
		let getToggled = $("#flexSwitchCheckDefault").prop("checked");
		let reviewStatus = getToggled ? 1 : 0;
	
		let reviewMan = [];
		let selectedIds = Object.keys(selectedEmployees);
	
		reviewMan.push(...selectedIds);
	
		console.log(reviewMan);
	
		var selectedRows = gridOptions.api.getSelectedRows();
		const datas = selectedRows[0];
	
		const empId = datas.empId;
		const empband = datas.bandId;
		const selfAppId = $("#selfAppId").val();
		const remarks = $("#summary1").val();
		const manRemarks = $("#summary2").val();
		const employeeReq = $("#requirementDescription").val();
		const manResponse = $("#managerResponse").val();
		
		const overallRatings = $("input[name='rating']:checked").val();
		const overallManRatings = $("input[name='ratingMan']:checked").val();
	
		let appraisalData = [];
	
		$("#kra-table-dep tbody tr.designation-header").each(function() {
			let desigRow = $(this);
			let desigText = desigRow.find("td").text().trim();
			let desigMatch = desigText.match(/^(\d+)\.\s(.+)\s\((.+?)\)$/);
	
			if (!desigMatch) return;
	
			let desigGoalName = desigMatch[2];
			let desigGoalId = desigMatch[3];
	
			let desigData = {
				designationId: desigGoalId,
				designationName: desigGoalName,
				kras: []
			};
	
			let nextRow = desigRow.next();
	
			while (nextRow.length && !nextRow.hasClass("designation-header")) {
				if (nextRow.hasClass("kra-header")) {
					let kraText = nextRow.find("td").text().trim();
					let kraMatch = kraText.match(/^(\d+\.\d+)\s(.+)\s\((.+?)\)$/);
	
					if (!kraMatch) {
						nextRow = nextRow.next();
						continue;
					}
	
					let kraName = kraMatch[2];
					let kraId = kraMatch[3];
	
					let kraData = {
						kraId: kraId,
						kraName: kraName,
						kpis: []
					};
	
					nextRow = nextRow.next();
	
					while (nextRow.length && nextRow.hasClass("kpi-row")) {
						let kpiText = nextRow.find("td:first").text().trim();
						let kpiMatch = kpiText.match(/^(\d+\.\d+\.\d+)\s(.+)$/);
						if (!kpiMatch) {
							nextRow = nextRow.next();
							continue;
						}
	
						let kpiName = kpiMatch[2];
						let weightage = nextRow.find(".weightage-input").val().trim();
						let rating = nextRow.find(".ratings-dropdown").val();
						let note = nextRow.find(".notes-input").val().trim();
						let manRating = nextRow.find(".manager-ratings-dropdown").val();
						let manNote = nextRow.find(".manager-notes-input").val().trim();
	
						let kpiData = {
							kpiName: kpiName,
							weightage: weightage || "0",
							rating: rating || "",
							note: note || "",
							manRating: manRating || "",
							manNote: manNote || ""
						};
	
						kraData.kpis.push(kpiData);
						nextRow = nextRow.next();
					}
	
					desigData.kras.push(kraData);
				} else {
					nextRow = nextRow.next();
				}
			}
	
			appraisalData.push(desigData);
		});
	
		let depGoalData = {
			selfAppId: selfAppId,
			empId: empId,
			empband: empband,
			remarks: remarks,
			overallRatings: overallRatings,
			manRemarks: manRemarks,
			overallManRatings: overallManRatings,
			appraisalData: appraisalData,
			reviewStatus: reviewStatus,
			reviewMan: reviewMan,
			employeeReq: employeeReq,
			manResponse: manResponse,
			finYear: finYear
			
		};
		
		for (const desig of depGoalData.appraisalData) {
			for (const kra of desig.kras) {
				for (const kpi of kra.kpis) {
					if (!kpi.manRating || kpi.manRating.trim() === "") {
						toastr.error(`Manager Rating is required for KPI: ${kpi.kpiName}`);
						return;
					}
					if (!kpi.manNote || kpi.manNote.trim() === "") {
						toastr.error(`Manager Note is required for KPI: ${kpi.kpiName}`);
						return;
					}
				}
			}
		}
		
		
		if(depGoalData.overallManRatings==null ||depGoalData.overallManRatings==""){
				toastr.error(`Manager Overall Ratings Required !`);
						return;
		}
	
		console.log(depGoalData);
		saveSelfAppraisal(depGoalData);
	}
	
	function saveSelfAppraisal(depGoalData) {
		console.log("Sending Data:", depGoalData);
	
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-self-appraisal-goal",
			contentType: "application/json",
			data: JSON.stringify(depGoalData),
			success: function(response) {
				if (response.code === "Success") {
					toastr.success(response.message);
					$(".formValidation").remove();
					$('.loader').hide();
					var selectedRows = gridOptions.api.getSelectedRows();
					const datas = selectedRows[0];
					const bandId = (datas.bandId);
					const empId = (datas.empId);
					getEmployeBandGoalDetails(bandId, empId);
				} else {
					$('.loader').show();
				}
			},
			error: function(error) {
				console.log("Error:", error);
			}
		});
	}
	
	
	function getEmployeBandGoalDetails(id, empId) {


		const finYear=$("#financialYear").val();
		agGrid.simpleHttpRequest({
			url: "get-all-desig-goal-by-emp?id=" + id + "&empId=" + empId + "&finYear=" + finYear,
		}).then(function(response) {
			if (response.code === "Success" && response.body && Array.isArray(response.body)) {
				const parsedBody = JSON.parse(response.body[0]);
	
				if (parsedBody.desigGoalDetailsEmp && parsedBody.desigGoalDetailsEmp.length > 0) {
	
					populateResponse(parsedBody.desigGoalDetailsEmp);
					parsedBody.desigGoalDetailsEmp.forEach(goal => {
						$("input[name='rating'][value='" + goal.ratings + "']").prop("checked", true);
						$("input[name='ratingMan'][value='" + goal.ratingMan + "']").prop("checked", true);
						$("#summary1").val(goal.goalRemark);
						$("#requirementDescription").val(goal.empRequirement);
						$("#managerResponse").val(goal.manResponse);
						$("#summary2").val(goal.goalRemarkMan);
						$("#selfAppId").val(goal.appraisalId);
	
						$("#flexSwitchCheckDefault").prop("checked", goal.reviewStatus == 1);
						let reviewStatus = $("#flexSwitchCheckDefault").prop("checked") ? 1 : 0;
						$("#getEmpDiv").hide();
						console.log("Reviewer List:", goal.reviewerList);
	
						selectedEmployees = {};
						$("#selectedEmployees").empty();
	
						/*goal.reviewerList.forEach(reviewer => {
							selectAutocompleteValue5(reviewer.empId, reviewer.empName);
						});
						*/
						if( goal.ratingMan==null || goal.ratingMan==""){
							$("#saveSelfApp").show();
						}else{
							$("#saveSelfApp").hide();
						}
					});
	
	
				} else {
					getDesigGoalById(id, empId);
					$("#flexSwitchCheckDefault").prop("checked", false);
					$("#getEmpDiv").hide();
				}
			}
		});
	}
	
	
	function populateResponse(empGoalDetails) {
		//$("#saveSelfApp").hide();
		const userIdLogin = document.getElementById("userId").value;
		var selectedRows = gridOptions.api.getSelectedRows();
		const datas = selectedRows[0];
		const empId = datas.empId;
	
		if (!empGoalDetails || !Array.isArray(empGoalDetails)) {
			console.error("Invalid employee goal details format", empGoalDetails);
			return;
		}
	
		const tableBody = $("#kra-table-dep tbody");
		tableBody.empty();
		let goalCounter = 0;
	
		empGoalDetails.forEach((emp, empIndex) => {
			if (!emp || !Array.isArray(emp.employeeAppDetails)) {
				console.warn(`Skipping invalid employee data at index ${empIndex}`, emp);
				return;
			}
	
			emp.employeeAppDetails.forEach((designation, desigIndex) => {
				if (!designation || !designation.designationId || !designation.designationName) {
					console.warn(`Skipping invalid designation at index ${desigIndex}`, designation);
					return;
				}
	
				goalCounter++;
				let desigHtml = `
		                <tr class="designation-header">
		                    <td colspan="6" class="designation-style">
		                        ${goalCounter}. ${designation.designationName} (${designation.designationId})
		                    </td>
		                </tr>`;
				tableBody.append(desigHtml);
	
				let kraCounter = 0;
				if (!designation.kras || !Array.isArray(designation.kras)) {
					console.warn(`No valid KRAs for Designation ${designation.designationId}`);
					return;
				}
	
				designation.kras.forEach((kra, kraIndex) => {
					if (!kra || !kra.kraId || !kra.kraName) {
						console.warn(`Skipping invalid KRA at index ${kraIndex}`, kra);
						return;
					}
	
					kraCounter++;
					let kraHtml = `
		                    <tr class="kra-header">
		                        <td colspan="6" class="kra-style">
		                            ${goalCounter}.${kraCounter} ${kra.kraName} (${kra.kraId})
		                        </td>
		                    </tr>`;
					tableBody.append(kraHtml);
	
					let kpiCounter = 0;
					if (!kra.kpis || !Array.isArray(kra.kpis)) {
						console.warn(`No valid KPIs for KRA ${kra.kraId}`);
						return;
					}
	
					kra.kpis.forEach((kpi, kpiIndex) => {
						if (!kpi || !kpi.kpiName) {
							console.warn(`Skipping invalid KPI at index ${kpiIndex}`, kpi);
							return;
						}
	
						kpiCounter++;
						let selectId = `rating-${desigIndex}-${kraIndex}-${kpiIndex}`;
						let managerSelectId = `manager-rating-${desigIndex}-${kraIndex}-${kpiIndex}`;
						let managerNoteId = `manager-note-${desigIndex}-${kraIndex}-${kpiIndex}`;
						let notesId = `notes-${desigIndex}-${kraIndex}-${kpiIndex}`;
	
						let kpiHtml = `
		                        <tr class="kpi-row">
		                            <td>${goalCounter}.${kraCounter}.${kpiCounter} ${kpi.kpiName}</td>
		                            <td>
		                                <input type="number" class="form-control weightage-input" value="${kpi.weightage || 0}" disabled>
		                            </td>
		                            <td>
		                                <select class="form-control ratings-dropdown" id="${selectId}">
		                                    <option value="">Select Rating</option>
		                                    <option value="1">1 - Poor</option>
		                                    <option value="2">2 - Fair</option>
		                                    <option value="3">3 - Good</option>
		                                    <option value="4">4 - Very Good</option>
		                                    <option value="5">5 - Excellent</option>
		                                </select>
		                            </td>
		                            <td>
		                                <input type="text" class="form-control notes-input" value="${kpi.note || ''}">
		                            </td>
		                            <td>
		                                <select class="form-control manager-ratings-dropdown" id="${managerSelectId}">
		                                    <option value="">Select Rating</option>
		                                    <option value="1">1 - Poor</option>
		                                    <option value="2">2 - Fair</option>
		                                    <option value="3">3 - Good</option>
		                                    <option value="4">4 - Very Good</option>
		                                    <option value="5">5 - Excellent</option>
		                                </select>
		                            </td>
		                            <td>
		                                <input type="text" class="form-control manager-notes-input" id="${managerNoteId}" value="${kpi.manNote || ''}">
		                            </td>
		                        </tr>`;
						tableBody.append(kpiHtml);
	
						$(`#${CSS.escape(selectId)}`).select2({
							placeholder: "Select Rating",
							allowClear: true
						});
						$(`#${selectId}`).val(kpi.rating || "").trigger('change');
	
						$(`#${CSS.escape(managerSelectId)}`).select2({
							placeholder: "Select Rating",
							allowClear: true
						});
						if (kpi.manRating) {
							$(`#${managerSelectId}`).val(kpi.manRating).trigger('change');
						} else {
							$("input[name='ratingMan']").prop("checked", false);
						}
	
					});
				});
			});
		});
	
		// Disable fields based on user role
		if (userIdLogin === empId) {
			$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", true);
			$("input[name='ratingMan']").prop("disabled", true);
			$("#summary2").prop("disabled", true);
			$("#managerResponse").prop("disabled", true);
		} else {
			$(`.ratings-dropdown, .notes-input`).prop("disabled", true);
			$("input[name='rating']").prop("disabled", true);
			$("#summary1").prop("disabled", true);
			$("#requirementDescription").prop("disabled", true);
		}
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
		
		getFinancialYear();
		getEmpDetails();
	}
	
	function SearchUserInput(event) {
		if (event.key === "Enter" || event.keyCode === 13 || event.key === "Backspace" || event.keyCode === 8) {
			onQuickFilterChanged();
		}
	}
	
	function handleToggle(el) {
		if (el.checked) {
			$("#getEmpDiv").hide();
			$("#selectedEmployees").empty();
		} else {
			$("#getEmpDiv").hide();
			$("#selectedEmployees").empty();
		}
	}
	
	let selectedEmployees = {};
	function getEmployeeList() {
		const search = $("#autoSearchInput").val();
	
		if (search === "") {
			$("#suggesstion-box5").hide();
			return;
		}
	
		$.ajax({
			type: "GET",
			url: "get-all-employee-list-auto-search?searchValue=" + search,
			success: function(response) {
				if (response.message === "success") {
					if (response.body.length !== 0) {
						let content = '<ul id="autocomplete-list" class="list-group">';
						for (let i = 0; i < response.body.length; i++) {
							const item = response.body[i];
							const isSelected = selectedEmployees.hasOwnProperty(item.key);
	
							if (isSelected) {
								content += `<li class="list-group-item disabled text-muted">${item.name} (selected)</li>`;
							} else {
								content += `<li class="list-group-item list-group-item-action cp" onclick="selectAutocompleteValue5('${item.key}', '${item.name}')">${item.name}</li>`;
							}
						}
						content += '</ul>';
						$("#suggesstion-box5").show().html(content);
					} else {
						$("#suggesstion-box5").show().html('<div class="text-muted">No Data Found</div>');
					}
				}
			},
			error: function() {
				console.error("Error fetching employee list");
			}
		});
	}
	
	function selectAutocompleteValue5(key, name) {
		if (!selectedEmployees[key]) {
			selectedEmployees[key] = name;
	
			const chip = `
				<span class="badge text-white d-flex align-items-center me-2 chipBox">
					${name}
					<button type="button" class="btn-close btn-close-white btn-sm ms-2" onclick="removeSelectedEmployee('${key}')"></button>
				</span>
			`;
			$("#selectedEmployees").append(chip);
	
			$("#assignedToMain").val(Object.keys(selectedEmployees).join(","));
		}
		$("#autoSearchInput").val('');
		$("#suggesstion-box5").hide();
	}
	
	function removeSelectedEmployee(key) {
		delete selectedEmployees[key];
	
		$("#selectedEmployees").empty();
		for (let k in selectedEmployees) {
			const name = selectedEmployees[k];
			const chip = `
				<span class="badge text-white d-flex align-items-center me-2 chipBox">
					${name}
					<button type="button" class="btn-close btn-close-white btn-sm ms-2" onclick="removeSelectedEmployee('${k}')"></button>
				</span>
			`;
			$("#selectedEmployees").append(chip);
		}
	
		$("#assignedToMain").val(Object.keys(selectedEmployees).join(","));
	}
	
		function getFinancialYear() {
		const currentYear = new Date().getFullYear();
		const dropdowns = [
			document.getElementById('financialYear'),
		];
	
		dropdowns.forEach(dropdown => {
			dropdown.innerHTML = '<option value="">Select</option>';
		});
	
		for (let i = currentYear - 5; i <= currentYear + 5; i++) {
			const financialYear = `${i}-${i + 1}`;
	
			dropdowns.forEach(dropdown => {
				const option = document.createElement('option');
				option.value = financialYear;
				option.textContent = financialYear;
				if (i === currentYear) {
					option.selected = true;
				}
	
				dropdown.appendChild(option);
			});
		}
		$("#financialYear").select2({
			placeholder: "Select",
			allowClear: true
		});
	
	}
	
	function getAllDesignationGoal(){
		getEmpDetails();
	}
	