	$(document).ready(function() {
	
		var gridDiv = document.querySelector('#appraisalGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		gridOptions.api.setRowData([]);
	
		getFinancialYear();
	
		getEmpDetails();
	
		/*	const userIdLogin = document.getElementById("userId").value;
			
			var selectedRows = gridOptions.api.getSelectedRows();
			const datas = selectedRows[0];
			const empId = datas.empId;
		
			if (userIdLogin != empId) {
				  $("#saveSelfApp").hide();
			}*/
	
	
	
	});
	
	
	/*getting list of all employee*/
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
	
	const userIdLogin = document.getElementById("userId").value;
	var selectedRows = gridOptions.api.getSelectedRows();
	const datas = selectedRows[0];
	const empId = datas.empId;
	
	if (userIdLogin === empId) {
		$("#saveSelfApp").show();
	} else {
		$("#saveSelfApp").hide();
	}
	
	
	function getAllDesignationGoal() {
		/*	var selectedRows = gridOptions.api.getSelectedRows();
			const datas = selectedRows[0];
			const bandId = (datas.bandId);
			const empId = (datas.empId);
			$("#empNames").text(datas.empName);
			getEmployeBandGoalDetails(bandId, empId);*/
		getEmpDetails();
	}
	
	
	function getDesigGoalById(id, empId) {
	
		const finYear = $("#financialYear").val();
	
		$("input[name='rating']").prop("checked", false);
		$("#summary1").val('');
		$("#requirementDescription").val('');
		$("#managerResponse").val('');
		$("input[name='ratingMan']").prop("checked", false);
		$("#summary2").val('');
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
		const tableBodyMan = $("#kra-table-dep-man tbody");
		tableBody.empty();
		tableBodyMan.empty();
		$("#kra-table-dep").show();
		$("#kra-table-dep-man").hide();
	
		const userIdLogin = document.getElementById("userId").value;
		var selectedRows = gridOptions.api.getSelectedRows();
		const datas = selectedRows[0];
		const empId = datas.empId;
	
		if (!depGoalDetails || depGoalDetails.length === 0) {
			$("#kra-table-dep tbody").empty();
			$("#saveSelfApp").hide();
			return;
		} else {
			$("#saveSelfApp").show();
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
		                          
		                        </tr>`;
						tableBody.append(kpiHtml);
	
						$(`#${CSS.escape(selectId)}`).select2({ placeholder: "Select Rating", allowClear: true });
						$(`#${CSS.escape(managerSelectId)}`).select2({ placeholder: "Select Rating", allowClear: true });
	
						// Disable based on user role
						if (userIdLogin === empId) {
							$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", false);
							$("input[name='ratingMan']").prop("disabled", false);
							$("#summary2").prop("disabled", false);
							$(`.ratings-dropdown, .notes-input`).prop("disabled", false);
							$("input[name='rating']").prop("disabled", false);
							$("#summary1").prop("disabled", false);
							$("#requirementDescription").prop("disabled", false);
							$("#managerResponse").prop("disabled", false);
	
							const finYear = $("#financialYear").val();
							const currentYear = new Date().getFullYear();
	
							if (parseInt(finYear) === currentYear) {
								console.log("Financial year is current year.");
								$("#saveSelfApp").show();
							} else {
								console.log("Financial year is different from current year.");
								$("#saveSelfApp").hide();
								$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", true);
								$("input[name='ratingMan']").prop("disabled", true);
								$("#summary2").prop("disabled", true);
								$(`.ratings-dropdown, .notes-input`).prop("disabled", true);
								$("input[name='rating']").prop("disabled", true);
								$("#summary1").prop("disabled", true);
								$("#requirementDescription").prop("disabled", true);
								$("#managerResponse").prop("disabled", true);
							}
						} else {
							$(`#${CSS.escape(selectId)}`).prop("disabled", true);
							$(`#${CSS.escape(notesId)}`).prop("disabled", true);
							$("input[name='rating']").prop("disabled", true);
							$("#summary1").prop("disabled", true);
							$("#requirementDescription").prop("disabled", true);
							$("#managerRating").hide();
							$("#managerSummary").hide();
							$("#managerResponseDiv").hide();
							$("#saveSelfApp").hide();
						}
					});
				});
			});
		});
	}
	
	
	
	function generateAppraisalJSONEmp() {
		const finYear = $("#financialYear").val();
	
		const userIdLogin = document.getElementById("userId").value;
	
		var selectedRows = gridOptions.api.getSelectedRows();
		const datas = selectedRows[0];
		const empId = datas.empId;
	
		if (userIdLogin != empId) {
			toastr.error("Opps ! Something went wrong");
			return false;
		}
	
		const empband = datas.bandId;
		const selfAppId = $("#selfAppId").val();
		const remarks = $("#summary1").val();
		const employeeReq = $("#requirementDescription").val();
		const overallRatings = $("input[name='rating']:checked").val();
	
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
	
						let kpiData = {
							kpiName: kpiName,
							weightage: weightage || "0",
							rating: rating || "",
							note: note || ""
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
			employeeReq: employeeReq || "",
			appraisalData: appraisalData,
			finYear: finYear
		};
	
		console.log(depGoalData);
	
	
		for (const desig of depGoalData.appraisalData) {
			for (const kra of desig.kras) {
				for (const kpi of kra.kpis) {
					if (!kpi.rating || kpi.rating.trim() === "") {
						toastr.error(`Rating is required for KPI: ${kpi.kpiName}`);
						return;
					}
					if (!kpi.note || kpi.note.trim() === "") {
						toastr.error(`Note is required for KPI: ${kpi.kpiName}`);
						return;
					}
				}
			}
		}
	
	
	
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
	
		const finYear = $("#financialYear").val();
	
		agGrid.simpleHttpRequest({
			url: "get-all-desig-goal-by-emp?id=" + id + "&empId=" + empId + "&finYear=" + finYear,
		}).then(function(response) {
			if (response.code === "Success" && response.body && Array.isArray(response.body)) {
				const parsedBody = JSON.parse(response.body[0]);
	
				if (parsedBody.desigGoalDetailsEmp && parsedBody.desigGoalDetailsEmp.length > 0) {
					parsedBody.desigGoalDetailsEmp.forEach(goal => {
	
	
						$("input[name='rating'][value='" + goal.ratings + "']").prop("checked", true);
						$("input[name='ratingMan'][value='" + goal.ratingMan + "']").prop("checked", true);
						$("#summary1").val(goal.goalRemark);
						$("#summary2").val(goal.goalRemarkMan);
						$("#selfAppId").val(goal.appraisalId);
						$("#requirementDescription").val(goal.empRequirement);
						$("#managerResponse").val(goal.manResponse);
	
					});
	
					populateResponse(parsedBody.desigGoalDetailsEmp);
				} else {
					getDesigGoalById(id, empId);
					$("#saveSelfApp").hide();
				}
			}
		});
	}
	
	
	
	function populateResponse(empGoalDetails) {
		$("#saveSelfApp").hide();
		const userIdLogin = document.getElementById("userId").value;
		var selectedRows = gridOptions.api.getSelectedRows();
		const datas = selectedRows[0];
		const empId = datas.empId;
	
		if (!empGoalDetails || !Array.isArray(empGoalDetails)) {
			console.error("Invalid employee goal details format", empGoalDetails);
			return;
		}
	
		const tableBody = $("#kra-table-dep tbody");
		const tableBodyMan = $("#kra-table-dep-man tbody");
		tableBody.empty();
		tableBodyMan.empty();
	
		let hasManagerData = false;
		let goalCounter = 0;
	
		empGoalDetails.forEach((emp, empIndex) => {
			if (!emp || !Array.isArray(emp.employeeAppDetails)) return;
	
			emp.employeeAppDetails.forEach((designation, desigIndex) => {
				if (!designation || !designation.designationId || !designation.designationName) return;
	
				goalCounter++;
				let desigHtml = `<tr class="designation-header"><td colspan="6" class="designation-style">
		                                ${goalCounter}. ${designation.designationName} (${designation.designationId})</td></tr>`;
				tableBody.append(desigHtml);
				tableBodyMan.append(desigHtml);
	
				let kraCounter = 0;
				if (!designation.kras || !Array.isArray(designation.kras)) return;
	
				designation.kras.forEach((kra, kraIndex) => {
					if (!kra || !kra.kraId || !kra.kraName) return;
	
					kraCounter++;
					let kraHtml = `<tr class="kra-header"><td colspan="6" class="kra-style">
		                                ${goalCounter}.${kraCounter} ${kra.kraName} (${kra.kraId})</td></tr>`;
					tableBody.append(kraHtml);
					tableBodyMan.append(kraHtml);
	
					let kpiCounter = 0;
					if (!kra.kpis || !Array.isArray(kra.kpis)) return;
	
					kra.kpis.forEach((kpi, kpiIndex) => {
						if (!kpi || !kpi.kpiName) return;
	
						kpiCounter++;
						let kpiRating = kpi.rating || "";
						let managerRating = kpi.manRating || "";
						let managerNote = kpi.manNote || "";
	
						if (managerRating || managerNote) {
							hasManagerData = true;
						}
	
						let selectId = `rating-${desigIndex}-${kraIndex}-${kpiIndex}`;
						let managerSelectId = `manager-rating-${desigIndex}-${kraIndex}-${kpiIndex}`;
						let managerNoteId = `manager-note-${desigIndex}-${kraIndex}-${kpiIndex}`;
	
						let kpiHtml = `<tr class="kpi-row">
		                        <td>${goalCounter}.${kraCounter}.${kpiCounter} ${kpi.kpiName}</td>
		                        <td><input type="number" class="form-control weightage-input" value="${kpi.weightage || 0}" disabled></td>
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
		                        <td><input type="text" class="form-control notes-input" value="${kpi.note || ''}"></td>
		                    </tr>`;
						tableBody.append(kpiHtml);
	
						let kpiHtmlMan = `<tr class="kpi-row">
		                        <td>${goalCounter}.${kraCounter}.${kpiCounter} ${kpi.kpiName}</td>
		                        <td><input type="number" class="form-control weightage-input" value="${kpi.weightage || 0}" disabled></td>
		                        <td>
		                            <select class="form-control ratings-dropdown" id="${selectId}-man">
		                                <option value="">Select Rating</option>
		                                <option value="1">1 - Poor</option>
		                                <option value="2">2 - Fair</option>
		                                <option value="3">3 - Good</option>
		                                <option value="4">4 - Very Good</option>
		                                <option value="5">5 - Excellent</option>
		                            </select>
		                        </td>
		                        <td><input type="text" class="form-control notes-input" value="${kpi.note || ''}"></td>`;
	
						if (managerRating || managerNote) {
							kpiHtmlMan += `<td>
		                            <select class="form-control manager-ratings-dropdown" id="${managerSelectId}">
		                                <option value="">Select Rating</option>
		                                <option value="1">1 - Poor</option>
		                                <option value="2">2 - Fair</option>
		                                <option value="3">3 - Good</option>
		                                <option value="4">4 - Very Good</option>
		                                <option value="5">5 - Excellent</option>
		                            </select>
		                        </td>
		                        <td><input type="text" class="form-control manager-notes-input" id="${managerNoteId}" value="${managerNote}"></td>`;
						}
	
						kpiHtmlMan += `</tr>`;
						tableBodyMan.append(kpiHtmlMan);
	
						// **SET SELECTED VALUES AFTER APPENDING**
						$(`#${CSS.escape(selectId)}`).val(kpiRating).trigger("change");
						$(`#${CSS.escape(selectId)}-man`).val(kpiRating).trigger("change");
	
						if (managerRating) {
							$(`#${CSS.escape(managerSelectId)}`).val(managerRating).trigger("change");
						}
					});
				});
			});
		});
	
		if (hasManagerData) {
			$("#kra-table-dep-man").show();
			$("#kra-table-dep").hide();
			$("#managerRating").show();
			$("#managerSummary").show();
			$("#managerResponseDiv").show();
		} else {
			$("#kra-table-dep").show();
			$("#kra-table-dep-man").hide();
			$("#managerRating").hide();
			$("#managerResponseDiv").hide();
			$("#managerSummary").hide();
		}
	
		if (userIdLogin === empId) {
			$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", true);
			$("input[name='ratingMan']").prop("disabled", true);
			$("#summary2").prop("disabled", true);
			$(`.ratings-dropdown, .notes-input`).prop("disabled", true);
			$("input[name='rating']").prop("disabled", true);
			$("#summary1").prop("disabled", true);
			$("#requirementDescription").prop("disabled", true);
			$("#managerResponse").prop("disabled", true);
		} else {
			$(`.ratings-dropdown, .notes-input`).prop("disabled", true);
			$("input[name='rating']").prop("disabled", true);
			$("input[name='ratingMan']").prop("disabled", true);
			$("#summary1").prop("disabled", true);
			$(`.manager-ratings-dropdown, .manager-notes-input`).prop("disabled", true);
			$("input[name='ratingMan']").prop("disabled", true);
			$("#summary2").prop("disabled", true);
			$("#requirementDescription").prop("disabled", true);
			$("#managerResponse").prop("disabled", true);
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
	
	
