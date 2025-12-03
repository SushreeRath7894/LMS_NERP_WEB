	$(document).ready(function() {
	
		$("#saveGoal").hide();
		$("#cancelGoal").hide();
	
		$("#saveDepGoal").hide();
		$("#cancelDepGoal").hide();
	
		$("#saveDesigGoal").hide();
		$("#cancelDesigGoal").hide();
		$("#save-kra").hide();
	
	
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		gridOptions.api.setRowData([]);
	
		var gridDiv = document.querySelector('#myGridDep');
		new agGrid.Grid(gridDiv, gridOptionsDep);
		gridOptionsDep.api.setRowData([]);
	
		var gridDiv = document.querySelector('#myGridDeg');
		new agGrid.Grid(gridDiv, gridOptionsDeg);
		gridOptionsDeg.api.setRowData([]);
	
		var gridDiv = document.querySelector('#myGriGoalDetails');
		new agGrid.Grid(gridDiv, gridOptionsGoalDetails);
		gridOptionsGoalDetails.api.setRowData([]);
	
	
		var gridDiv = document.querySelector('#myGriDepGoalDetails');
		new agGrid.Grid(gridDiv, gridOptionsDepGoalDetails);
		gridOptionsDepGoalDetails.api.setRowData([]);
	
		var gridDiv = document.querySelector('#myGriDesigGoalDetails');
		new agGrid.Grid(gridDiv, gridOptionsDesigGoalDetails);
		gridOptionsDesigGoalDetails.api.setRowData([]);
	
		getAllGoal();
		getFinancialYear();
	
		$("#orgGoalList,#orgGoalListDesig,#desigList,#depList,#depListD,#depGoalListD").select2({
			placeholder: "Select ",
			allowClear: true
		});
	
	
	});
	
	function addGoal() {
	
		$("#goalId").text('');
		$("#goalDiv").show();
		$("#saveGoal").show();
		$("#cancelGoal").show();
		$("#addGoal").hide();
		$("#editGoal").hide();
		getFinancialYear();
		$("#financialYear").prop('disabled', false);
		$("#goalName").val('').prop('disabled', false);
		gridOptions.api.deselectAll();
	}
	
	function cancelGoal() {
		$("#saveGoal").hide();
		$("#cancelGoal").hide();
		$("#addGoal").show();
		$("#editGoal").show();
		getAllGoal();
	}
	
	
	function getFinancialYear() {
		const currentYear = new Date().getFullYear();
		const dropdowns = [
			document.getElementById('financialYear'),
			document.getElementById('financialYearDep'),
			document.getElementById('financialYearDesig')
		];
	
		// Clear existing options
		dropdowns.forEach(dropdown => {
			dropdown.innerHTML = '<option value="">Select</option>';
		});
	
		// Generate financial years (-5 to +5 range)
		for (let i = currentYear - 5; i <= currentYear + 5; i++) {
			const financialYear = `${i}-${i + 1}`;
	
			dropdowns.forEach(dropdown => {
				const option = document.createElement('option');
				option.value = financialYear;
				option.textContent = financialYear;
	
				// Set the current financial year as selected
				if (i === currentYear) {
					option.selected = true;
				}
	
				dropdown.appendChild(option);
			});
		}
		$("#financialYear, #financialYearDep, #financialYearDesig").select2({
			placeholder: "Select",
			allowClear: true
		});
	
		$("#financialYearDep, #financialYearDep, #financialYearDesig").select2({
			placeholder: "Select",
			allowClear: true
		});
	
		$("#financialYearDesig, #financialYearDep, #financialYearDesig").select2({
			placeholder: "Select",
			allowClear: true
		});
	}
	
	function saveGoal() {
	
		const goalObj = {};
		goalObj.goalId = $("#goalId").text();
		goalObj.goalName = $("#goalName").val();
		goalObj.financialYear = $("#financialYear").val();
	
		if (goalObj.goalName == null || goalObj.goalName == "") {
			toastr.error("Goal Name Required");
			return;
		}
	
		saveApprGoal(goalObj);
	}
	
	function saveApprGoal(goalObj) {
		console.log("for save -------->", goalObj);
	
		$.ajax({
			type: "POST",
			url: "save-appraisal-details",
			contentType: "application/json",
			data: JSON.stringify(goalObj),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					getAllGoal();
				} else {
					toastr.error(response.message)
				}
	
				$('.loader').hide();
				$("body").removeClass("overlay");
			},
			error: function(data) {
				console.log(data)
				toastr.error('Something went wrong')
	
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})
	
	}
	
	function getAllGoal() {
	
		agGrid.simpleHttpRequest({
			url: "get-appraisal-details"
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body);
				const goalDetails = responseBody.goalDetails;
				var newRowData = goalDetails.reverse();
				gridOptions.api.setRowData(newRowData);
	
				$("#addGoal").show();
				$("#editGoal").show();
				$("#saveGoal").hide();
				$("#cancelGoal").hide();
	
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
	
	}
	/*column defination for project creation*/
	var columnDefs = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Goal Id",
			field: "goalId",
			flex: 1
	
		},
		{
			headerName: "Goal",
			field: "goalName",
			flex: 1
		},
		{
			headerName: "Financial Year",
			field: "financialYear",
			flex: 1
	
		}];
	
	// Define grid options
	var gridOptions = {
		columnDefs: columnDefs,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 100
		},
		pagination: true,
		paginationPageSize: 15,
	
		onSelectionChanged: rowSelect
	};
	
	
	function rowSelect() {
		var selectedRows = gridOptions.api.getSelectedRows();
		if (selectedRows.length > 0) {
			const datas = selectedRows[0];
			const goalId = (datas.goalId);
	
			$("#goalId").text(goalId);
			$("#organizationGoalNames").text(datas.goalName);
			$("#organizationGoalName").text(datas.goalName);
			$("#goalNames").text(datas.goalName);
			$("#goalName").val(datas.goalName).prop('disabled', true);
			$("#financialYear").val(datas.financialYear).trigger('change').prop('disabled', true);
			getGoalDetails(goalId);
			
			$("#add-kra").show();
		} else {
			$("#goalId").text('');
			$("#organizationGoalNames").text('');
			$("#goalNames").text('');
			$("#organizationGoalName").text('');
			$("#goalDiv").show();
			$("#saveGoal").show();
			$("#addGoal").hide();
			getFinancialYear();
			$("#financialYear").prop('disabled', false);
			$("#goalName").val('').prop('disabled', false);
			$("#editGoal").hide();
			$("#cancelGoal").show();
			getGoalDetails('');
			$("#add-kra").hide();
			$("#save-kra").hide();
		}
	}
	
	function editGoal() {
	
		$("#addGoal").hide();
		$("#editGoal").hide();
		$("#saveGoal").show();
		$("#cancelGoal").show();
		$("#financialYear").prop('disabled', false);
		$("#goalName").prop('disabled', false);
	}
	
	
	
	/*All function for kra and kpi*/
	let kraCount = 0;
	function addKra() {

		var selectedRows = gridOptions.api.getSelectedRows();
		if (selectedRows.length > 0) {
			$("#save-kra").show();
		} else {
			$("#save-kra").hide();
		}
	
		let lastRow = $('#kra-table tbody tr:last');
		if (lastRow.length > 0 && !lastRow.hasClass('read-only')) {
			toastr.error("Please save the current KRA before adding a new one.");
			return;
		}
	
		let lastKraId = 0;
		$('#kra-table tbody tr').each(function() {
			let kraText = $(this).attr('data-kra-id');
			if (kraText) {
				let kraNumber = parseFloat(kraText.split('.')[1]);
				if (!isNaN(kraNumber) && kraNumber > lastKraId) {
					lastKraId = kraNumber;
				}
			}
		});
	
		const newKraId = `1.${lastKraId + 1}`;
	
		const newRow = `
							    <tr data-kra-id="${newKraId}">
							        <td>
							            <strong>${newKraId}</strong><br>
							            <input type="text" class="form-control kra-name" placeholder="Enter KRA Name">
							        </td>
							        <td>
							            <ul class="kpi-list"></ul>
							            <div style="display: flex; gap: 5px;">
							                <input type="text" class="form-control kpi-input" placeholder="Enter KPI">
							                <button class="btn go-btn add-kpi-btn"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
							            </div>
							        </td>
							        <td>
							            <input type="number" class="form-control weightage-input" placeholder="Enter Weightage" min="0" max="100">
							        </td>
							        <td>
							            <div class="btn-container">
							                <button class="btn go-btn save-kra">
							                    <i class="fa-solid fa-floppy-disk"></i>
							                </button>
							                <button class="btn go-btn edit-kra" style="display: none;">
							                        <i class="fa fa-pencil-square"></i> 
							                    </button>
							                <button class="btn go-btn remove-kra">
							                    <i class="fas fa-trash"></i>
							                </button>
							            </div>
							        </td>
							    </tr>
							`;
	
		$('#kra-table tbody').append(newRow);
	}
	
	
	$(document).on('click', '.add-kpi-btn', function() {
		let kpiInput = $(this).closest('td').find('.kpi-input');
		let kpiList = $(this).closest('td').find('.kpi-list');
		let kpiValue = kpiInput.val().trim();
	
		if (kpiValue === '') {
			toastr.error('KPI cannot be empty!');
			return;
		}
	
		if (kpiValue !== '') {
			const kpiItem = `
			                <li>
			                    ${kpiValue}<span class=" remove-kpi" style="cursor:pointer;"><i class="fa fa-window-close" aria-hidden="true"></i></span>
			                </li>
																		            `;
			kpiList.append(kpiItem);
			kpiInput.val('');
		}
	});
	
	$(document).on('click', '.remove-kpi', function() {
		$(this).parent('li').remove();
	});
	
	$(document).on('click', '.save-kra', function() {
		const row = $(this).closest('tr');
		const kraName = row.find('.kra-name').val().trim();
		const weightage = row.find('.weightage-input').val().trim();
		const kpiCount = row.find('.kpi-list li').length;
	
		// Validation: Check if KRA Name, Weightage, and at least one KPI are provided
		if (kraName === '') {
			toastr.error('KRA Name cannot be empty!');
			return;
		}
		if (kpiCount === 0) {
			toastr.error('At least one KPI is required!');
			return;
		}
		if (weightage === '' || isNaN(weightage) || weightage < 0 || weightage > 100) {
			toastr.error('Please enter a valid weightage between 0 and 100!');
			return;
		}
	
		row.addClass('read-only');
		row.find('.save-kra').hide();
		row.find('.edit-kra').show();
	
		row.find('.kra-name, .weightage-input').prop('disabled', true);
		row.find('.kpi-input, .add-kpi-btn').hide();
		row.find('.remove-kpi').hide();
	});
	
	$(document).on('click', '.edit-kra', function() {
		const row = $(this).closest('tr');
		row.removeClass('read-only');
		row.find('.edit-kra').hide();
		row.find('.save-kra').show();
	
		row.find('.kra-name, .weightage-input').prop('disabled', false);
		row.find('.kpi-input-container').show();
		row.find('.kpi-input, .add-kpi-btn').show();
		row.find('.remove-kpi').show();
	});
	
	
	$(document).on('click', '.remove-kra', function() {
		$(this).closest('tr').remove();
	});
	
	function generateAppraisalJSON() {
		const goalId = $("#goalId").text();
		const kraList = [];
		let totalWeightage = 0;
	
		$('#kra-table tbody tr').each(function() {
			const kraId = $(this).data('kra-id');
			const kraName = $(this).find('.kra-name').val();
			const weightage = parseFloat($(this).find('.weightage-input').val());
			const kpiList = [];
	
			$(this).find('.kpi-list li').each(function() {
				let kpiText = $(this).text().trim();
				kpiText = kpiText.replace(/❌$/, '').trim();
				kpiList.push({ kpiName: kpiText });
			});
	
			kraList.push({
				kraId: parseFloat(kraId),
				kraName: kraName,
				weightage: weightage,
				kpiList: kpiList
			});
	
			totalWeightage += weightage;
		});
	
		if (totalWeightage !== 100) {
			toastr.error(`Total weightage must be exactly 100%! Currently: ${totalWeightage}%`);
			return;
		}
	
		const appraisalData = {
			goalId: goalId,
			kraList: kraList
		};
	
		saveAllKraDetails(appraisalData);
	}
	
	
	function saveAllKraDetails(appraisalData) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-appraisal-kra-details",
			contentType: "application/json",
			data: JSON.stringify(appraisalData),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					$(".formValidation").remove();
					$('.loader').hide();
					const id = $("#goalId").text();
					getGoalDetails(id);
				} else {
					$('.loader').show();
				}
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
	
	
	function getGoalDetails(id) {
		agGrid.simpleHttpRequest({
			url: "get-all-goal-details?id=" + id,
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body[0]);
				const goalDetails = responseBody.goalDetails;
	
				populateGoalTable(goalDetails);
				populateDetailedGoalTable(goalDetails);
			}
		});
	}
	
	
	function populateGoalTable(goalDetails) {
		const tableBody = $("#kra-table tbody");
		tableBody.empty();
	
		if (!goalDetails || goalDetails.length === 0) {
			return;
		}
	
		goalDetails.forEach((goal, index) => {
			const kraData = goal.kraList;
			const kpiData = goal.kpiList;
			const weightage = goal.weightage;
	
			const kraId = kraData.kraId || `1.${index + 1}`;
	
			let kpiHtml = kpiData.map(kpi => `
		            <li>
		                ${kpi.kpiName} <span class=" remove-kpi" style="cursor:pointer; display:none;"><i class="fa fa-window-close" aria-hidden="true"></i></span>
		            </li>
		        `).join('');
	
			let rowHtml = `
		            <tr class="read-only" data-kra-id="${kraId}">
		                <td>
		                    <strong>${kraId}</strong><br>
		                    <input type="text" class="form-control kra-name" value="${kraData.kraName}" disabled>
		                </td>
		                <td>
		                    <ul class="kpi-list">
		                        ${kpiHtml}
		                    </ul>
		                    <div class="kpi-input-container" style="display: none; flex; gap: 5px;">
		                        <input type="text" class="form-control kpi-input" placeholder="Enter KPI">
		                         <button class="btn go-btn add-kpi-btn"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
		                    </div>
		                </td>
		                <td>
		                    <input type="number" class="form-control weightage-input" value="${weightage}" disabled>
		                </td>
		                <td>
		                    <button class="btn go-btn save-kra" style="display: none;">
		                        <i class="fas fa-save"></i>
		                    </button>
		                    <button class="btn go-btn edit-kra">
		                        <i class="fa fa-pencil-square"></i> 
		                    </button>
		                    <button class="btn go-btn remove-kra">
		                        <i class="fa fa-trash"></i> 
		                    </button>
		                </td>
		            </tr>
		        `;
	
			tableBody.append(rowHtml);
		});
	}
	
	
	
	var columnDefsDep = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Dep Goal Id",
			field: "depGoalId",
			flex: 1
	
		},
		{
			headerName: "Organization Goal",
			field: "goalName",
			flex: 1
		},
		{
			headerName: "Department Goal",
			field: "depGoalName",
			flex: 1
		},
		{
			headerName: "Department Name",
			field: "departmentName",
			flex: 1
		},
		{
			headerName: "Financial Year",
			field: "financialYear",
			flex: 1
	
		}, {
			headerName: "Department Id",
			field: "departmentId",
			flex: 1,
			hide: true
	
		}];
	
	// Define grid options
	var gridOptionsDep = {
		columnDefs: columnDefsDep,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 100
		},
		pagination: true,
		paginationPageSize: 15,
	
		onSelectionChanged: rowSelectDep
	};
	
	
	
	var columnDefsDeg = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Deg Goal Id",
			field: "desigGoalId",
			flex: 1
	
		}, {
			headerName: "Org Goal Id",
			field: "goalId",
			flex: 1
	
		}, {
			headerName: "Dep Goal Id",
			field: "depGoalId",
			flex: 1
	
		},
		{
			headerName: "Designation Goal",
			field: "desigGoalName",
			flex: 1
		},
		{
			headerName: "Financial Year",
			field: "financialYear",
			flex: 1
	
		}, {
			headerName: "Department Id",
			field: "departmentId",
			flex: 1,
			hide: true,
	
		}, {
			headerName: "Designation Id",
			field: "bandId",
			flex: 1,
			hide: true,
	
		}, {
			headerName: "Org Name",
			field: "goalName",
			flex: 1,
			hide: true,
	
		}, {
			headerName: "Department Goal Id",
			field: "depGoalId",
			flex: 1,
			hide: true,
	
		}];
	
	// Define grid options
	var gridOptionsDeg = {
		columnDefs: columnDefsDeg,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 100
		},
		pagination: true,
		paginationPageSize: 15,
	
		onSelectionChanged: rowSelectDesig
	};
	
	
	var columnDefsGoalDetls = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Goal Name",
			field: "goalName",
			flex: 1
	
		},
		{
			headerName: "KRA",
			field: "kraNames",
			flex: 1
		},
		{
			headerName: "KPI",
			field: "kpiNames",
			flex: 1
	
		},
		{
			headerName: "Weightage",
			field: "weightage",
			flex: 1
	
		}];
	
	// Define grid options
	var gridOptionsGoalDetails = {
		columnDefs: columnDefsGoalDetls,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 100
		},
		pagination: true,
		paginationPageSize: 15,
	
	};
	
	function populateDetailedGoalTable(goalDetails) {
	
		gridOptionsGoalDetails.api.setRowData([]);
		let rowData = [];
		goalDetails.forEach((goal, goalIndex) => {
			const kraData = goal.kraList;
			const kpiData = goal.kpiList;
			const weightage = goal.weightage;
			const kraId = kraData.kraId || `1.${goalIndex + 1}`;
	
			kpiData.forEach((kpi) => {
				rowData.push({
					goalName: goal.goalName,
					kraNames: `${kraId} - ${kraData.kraName}`,
					kpiNames: kpi.kpiName,
					weightage: weightage
				});
			});
		});
		gridOptionsGoalDetails.api.setRowData(rowData);
	}
	
	
	
	document.addEventListener("DOMContentLoaded", function() {
		document.querySelectorAll(".nav-link").forEach(button => {
			button.addEventListener("click", function() {
				const target = this.getAttribute("data-bs-target");
				handleTabClick(target);
			});
		});
	});
	
	function handleTabClick(target) {
		console.log(`${target.replace('#', '')} tab clicked`);
	}
	
	
	
	function addDepGoal() {
	
		$("#saveDepGoal").show();
		$("#cancelDepGoal").show();
		$("#addDepGoal").hide();
		$("#editDepGoal").hide();
		$("#depGoalId").text('');
		$("#orgGoalList").val('').prop('disabled', false);
		$("#depGoalName").val('').prop('disabled', false);
		$("#depList").val('').prop('disabled', false);
		$("#financialYearDep").val('').prop('disabled', false);
		getFinancialYear();
		gridOptionsDep.api.deselectAll();
	}
	
	function cancelDepGoal() {
		$("#saveDepGoal").hide();
		$("#cancelDepGoal").hide();
		$("#addDepGoal").show();
		$("#editDepGoal").show();
		getAllDepGoal();
	
	}
	function editDepGoal() {
	
		$("#addDepGoal").hide();
		$("#editDepGoal").hide();
		$("#saveDepGoal").show();
		$("#cancelDepGoal").show();
		$("#financialYearDep").prop('disabled', false);
		$("#orgGoalList").prop('disabled', false);
		$("#depGoalName").prop('disabled', false);
		$("#depList").prop('disabled', false);
		$("#financialYearDep").prop('disabled', false);
	}
	
	
	function saveDepGoal() {
	
		const depGoalObj = {};
		depGoalObj.depGoalId = $("#depGoalId").text();
		depGoalObj.goalId = $("#orgGoalList").val();
		depGoalObj.depGoalName = $("#depGoalName").val();
	
		depGoalObj.depList = $("#depList").val();
		depGoalObj.financialYear = $("#financialYearDep").val();
	
		if (depGoalObj.goalId == null || depGoalObj.goalId == "") {
			toastr.error("Org Goal Required");
			return;
		}
	
		if (depGoalObj.depGoalName == null || depGoalObj.depGoalName == "") {
			toastr.error("Department Goal Name Required");
			return;
		}
	
		if (depGoalObj.depList == null || depGoalObj.depList == "") {
			toastr.error("Department Required");
			return;
		}
	
		saveApprDepGoal(depGoalObj);
	}
	
	function saveApprDepGoal(depGoalObj) {
		console.log("for save -------->", depGoalObj);
	
		$.ajax({
			type: "POST",
			url: "save-appraisal-dep-details",
			contentType: "application/json",
			data: JSON.stringify(depGoalObj),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					getAllDepGoal();
				} else {
					toastr.error(response.message)
				}
	
				$('.loader').hide();
				$("body").removeClass("overlay");
			},
			error: function(data) {
				console.log(data)
				toastr.error('Something went wrong')
	
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})
	
	}
	
	
	function getAllDepGoal() {
	
		agGrid.simpleHttpRequest({
			url: "get-appraisal-dep-details"
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body);
				const goalDetails = responseBody.goalDetails;
				var newRowData = goalDetails.reverse();
				gridOptionsDep.api.setRowData(newRowData);
				$("#addDepGoal").show();
				$("#editDepGoal").show();
				$("#saveDepGoal").hide();
				$("#cancelDepGoal").hide();
				if (newRowData && newRowData.length > 0) {
					gridOptionsDep.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
	
			} else {
				console.error("Failed to fetch data");
			}
		});
	
	}
	
	function rowSelectDep() {
		var selectedRows = gridOptionsDep.api.getSelectedRows();
		if (selectedRows.length > 0) {
			const datas = selectedRows[0];
			const depGoalId = (datas.depGoalId);
			$("#depGoalId").text(depGoalId);
			$("#orgGoalList").val(datas.goalId).trigger('change').prop('disabled', true);
			$("#depGoalName").val(datas.depGoalName).prop('disabled', true);
			$("#depList").val(datas.departmentId).trigger('change').prop('disabled', true);
			$("#financialYearDep").val(datas.financialYear).trigger('change').prop('disabled', true);
	
			$("#depGoalNames").text(datas.depGoalName);
			$("#departmentsGoalName").text(datas.depGoalName);
			$("#departmentsGoalNames").text(datas.depGoalName);
			const goalNames = $("#goalNames").text();
			$("#orgDepGoalName").text(goalNames);
			getDepGoalDetails(depGoalId);
		} else {
			$("#depGoalId").text('');
			$("#orgGoalList").val('').trigger('change').prop('disabled', false);
			$("#depGoalName").val('').prop('disabled', false);
			$("#depList").val('').trigger('change').prop('disabled', false);
			$("#financialYearDep").val('').trigger('change').prop('disabled', false);
			$("#depGoalNames").text('');
			$("#orgDepGoalName").text('');
			$("#departmentsGoalName").text('');
			$("#departmentsGoalNames").text('');
			getDepGoalDetails('');
			getFinancialYear();
	
		}
	}
	
	/* All functions for KRA and KPI for Department Goals */
	function addDepKra() {
		let lastRow = $('#kra-table-dep tbody tr:last');
		if (lastRow.length > 0 && !lastRow.hasClass('read-only')) {
			toastr.error("Please save the current KRA before adding a new one.");
			return;
		}
	
		let lastDepKraId = 0;
		$('#kra-table-dep tbody tr').each(function() {
			let kraText = $(this).attr('data-kra-id');
			if (kraText) {
				let kraNumber = parseFloat(kraText.split('.')[1]);
				if (!isNaN(kraNumber) && kraNumber > lastDepKraId) {
					lastDepKraId = kraNumber;
				}
			}
		});
	
		const newDepKraId = `1.${lastDepKraId + 1}`;
	
		const newRow = `
					    <tr data-kra-id="${newDepKraId}">
					        <td>
					            <strong>${newDepKraId}</strong><br>
					            <input type="text" class="form-control dept-kra-name" placeholder="Enter KRA Name">
					        </td>
					        <td>
					            <ul class="dept-kpi-list"></ul>
					            <div style="display: flex; gap: 5px;">
					                <input type="text" class="form-control dept-kpi-input" placeholder="Enter KPI">
					                <button class="btn go-btn add-dept-kpi-btn"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
					            </div>
					        </td>
					        <td>
					            <input type="number" class="form-control dept-weightage-input" placeholder="Enter Weight" min="0" max="100">
					        </td>
					        <td>
					            <div class="btn-container">
					                <button class="btn go-btn save-dept-kra">
					                    <i class="fa-solid fa-floppy-disk"></i>
					                </button>
					                <button class="btn go-btn edit-dept-kra" style="display: none;">
					                    <i class="fa fa-pencil-square"></i> 
					                </button>
					                <button class="btn go-btn remove-dept-kra">
					                    <i class="fas fa-trash"></i>
					                </button>
					            </div>
					        </td>
					    </tr>
					`;
	
		$('#kra-table-dep tbody').append(newRow);
	}
	
	
	/* Add KPI to Department KRA */
	$(document).on('click', '.add-dept-kpi-btn', function() {
		let kpiInput = $(this).closest('td').find('.dept-kpi-input');
		let kpiList = $(this).closest('td').find('.dept-kpi-list');
		let kpiValue = kpiInput.val().trim();
	
		if (kpiValue === '') {
			toastr.error('KPI cannot be empty!');
			return;
		}
	
		if (kpiValue !== '') {
			const kpiItem = `
					            <li>
					                ${kpiValue} <span class="remove-kpi-dep" style="cursor:pointer;"><i class="fa fa-window-close" aria-hidden="true"></i></span>
					            </li>
							        `;
			kpiList.append(kpiItem);
			kpiInput.val('');
			row.find('.remove-kpi-dep').show();
		}
	});
	
	/* Remove KPI from Department KRA */
	$(document).on('click', '.remove-dept-kpi', function() {
		$(this).parent('li').remove();
	});
	
	$(document).on('click', '.remove-kpi-dep', function() {
		$(this).parent('li').remove();
	});
	
	/* Save Department KRA */
	$(document).on('click', '.save-dept-kra', function() {
		const row = $(this).closest('tr');
		const kraName = row.find('.dept-kra-name').val().trim();
		const weightage = row.find('.dept-weightage-input').val().trim();
		const kpiCount = row.find('.dept-kpi-list li').length;
	
		if (kraName === '') {
			toastr.error('KRA Name cannot be empty!');
			return;
		}
		if (kpiCount === 0) {
			toastr.error('At least one KPI is required!');
			return;
		}
		if (weightage === '' || isNaN(weightage) || weightage < 0 || weightage > 100) {
			toastr.error('Please enter a valid weightage between 0 and 100!');
			return;
		}
	
		row.addClass('read-only');
		row.find('.save-dept-kra').hide();
		row.find('.edit-dept-kra').show();
	
		row.find('.dept-kra-name, .dept-weightage-input').prop('disabled', true);
		row.find('.dept-kpi-input, .add-dept-kpi-btn').hide();
		row.find('.remove-dept-kpi').hide();
		row.find('.remove-kpi-dep').hide();
	});
	
	/* Edit Department KRA */
	
	$(document).on('click', '.edit-dept-kra', function() {
		const row = $(this).closest('tr');
		row.removeClass('read-only');
		row.find('.edit-dept-kra').hide();
		row.find('.save-dept-kra').show();
		row.find('.dept-kra-name, .dept-weightage-input').prop('disabled', false);
		row.find('.kpi-input-container').show();
		row.find('.dept-kpi-input, .add-dept-kpi-btn').show();
		//row.find('.remove-kpi').show();
		row.find('.remove-kpi-dep').show();
	});
	
	$(document).on('click', '.remove-dept-kra', function() {
		$(this).closest('tr').remove();
	});
	
	
	
	function generateDeptAppraisalJSON() {
	
		const goalId = $("#goalId").text();
		const depGoalId = $("#depGoalId").text();
		const deptKraList = [];
		let totalWeightage = 0;
	
		$('#kra-table-dep tbody tr').each(function() {
			const kraId = $(this).data('kra-id');
			const kraName = $(this).find('.dept-kra-name').val();
			const weightage = parseFloat($(this).find('.dept-weightage-input').val());
			const kpiList = [];
	
			$(this).find('.dept-kpi-list li').each(function() {
				let kpiText = $(this).text().trim();
				kpiText = kpiText.replace(/❌$/, '').trim();
				kpiList.push({ kpiName: kpiText });
			});
	
			deptKraList.push({
				kraId: kraId,
				kraName: kraName,
				weightage: weightage,
				kpiList: kpiList
			});
	
			totalWeightage += weightage;
		});
	
		if (totalWeightage !== 100) {
			toastr.error(`Total weightage must be exactly 100%! Currently: ${totalWeightage}%`);
			return;
		}
	
		const deptAppraisalData = {
			goalId: goalId,
			depGoalId: depGoalId,
			kraList: deptKraList
		};
	
		console.log(deptAppraisalData)
	
		saveDeptKraDetails(deptAppraisalData);
	}
	function saveDeptKraDetails(deptAppraisalData) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-appraisal-dep-kra-details",
			contentType: "application/json",
			data: JSON.stringify(deptAppraisalData),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					$(".formValidation").remove();
					$('.loader').hide();
					const id = $("#depGoalId").text();
					getDepGoalDetails(id);
				} else {
					$('.loader').show();
				}
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
	
	function getDepGoalDetails(id) {
		agGrid.simpleHttpRequest({
			url: "get-all-dep-goal-details?id=" + id,
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body[0]);
				const depGoalDetails = responseBody.depGoalDetails;
	
				populateDepGoalTable(depGoalDetails);
				populateDepGridDetails(depGoalDetails);
			}
		});
	}
	
	function populateDepGoalTable(depGoalDetails) {
		if (!depGoalDetails || depGoalDetails.length === 0) {
			$("#kra-table-dep tbody").empty();
			return;
		}
	
		const tableBody = $("#kra-table-dep tbody");
		tableBody.empty();
	
		depGoalDetails.forEach((goal, index) => {
			const kraData = goal.kraList || {};
			const kpiData = goal.kpiList || [];
			const weightage = goal.weightage || 0;
	
			const kraId = kraData.kraId ? kraData.kraId : `1.${index + 1}`;
			const kraName = kraData.kraName ? kraData.kraName : '';
	
			let kpiHtml = kpiData.map(kpi => `
								            <li>
								                ${kpi.kpiName}<span class="remove-kpi-dep" style="cursor:pointer; display:none;"><i class="fa fa-window-close" aria-hidden="true"></i></span>
								            </li>
								        `).join('');
	
			let rowHtml = `
				            <tr class="read-only" data-kra-id="${kraId}">
				                <td>
				                    <strong>${kraId}</strong><br>
				                    <input type="text" class="form-control dept-kra-name" value="${kraName}" disabled>
				                </td>
				                <td>
				                    <ul class="dept-kpi-list">
				                        ${kpiHtml}
				                    </ul>
				                    <div class="kpi-input-container" style="display: none; flex; gap: 5px;">
				                        <input type="text" class="form-control dept-kpi-input" placeholder="Enter KPI">
	
				                         <button class="btn go-btn add-dept-kpi-btn"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
				                    </div>
				                </td>
				                <td><input type="number" class="form-control dept-weightage-input" value="${weightage}" disabled></td>
	
				                 <td>
		                    <button class="btn go-btn save-dept-kra" style="display: none;">
		                        <i class="fas fa-save"></i>
		                    </button>
		                    <button class="btn go-btn edit-dept-kra">
		                        <i class="fa fa-pencil-square"></i> 
		                    </button>
		                    <button class="btn go-btn remove-dept-kra">
		                        <i class="fa fa-trash"></i> 
		                    </button>
		                </td>
				                
				            </tr>
				        `;
	
			tableBody.append(rowHtml);
		});
	}
	
	var columnDefsDepGoalDetls = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Dep. Goal Name",
			field: "depGoalName",
			flex: 1
	
		},
		{
			headerName: "KRA",
			field: "kraNames",
			flex: 1
		},
		{
			headerName: "KPI",
			field: "kpiNames",
			flex: 1
	
		},
		{
			headerName: "Weightage",
			field: "weightage",
			flex: 1
	
		}];
	
	// Define grid options
	var gridOptionsDepGoalDetails = {
		columnDefs: columnDefsDepGoalDetls,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 100
		},
		pagination: true,
		paginationPageSize: 15,
	
	};
	
	
	function populateDepGridDetails(depGoalDetails) {
		if (!Array.isArray(depGoalDetails) || depGoalDetails.length === 0) {
			gridOptionsDepGoalDetails.api.setRowData([]);
			return;
		}
	
		let rowData = [];
	
		depGoalDetails.forEach((goal, goalIndex) => {
			const weightage = goal.weightage || 0;
			const depGoalName = goal.goalName || "N/A";
			const kraData = goal.kraList || {};
	
			const kraId = kraData.kraId || `1.${goalIndex + 1}`;
			const kraName = kraData.kraName || "Unnamed KRA";
	
			if (Array.isArray(goal.kpiList) && goal.kpiList.length > 0) {
				goal.kpiList.forEach((kpi) => {
					rowData.push({
						depGoalName: depGoalName,
						kraNames: `${kraId} - ${kraName}`,
						kpiNames: kpi.kpiName || "Unnamed KPI",
						weightage: weightage
					});
				});
			} else {
				rowData.push({
					depGoalName: depGoalName,
					kraNames: `${kraId} - ${kraName}`,
					kpiNames: "No KPI",
					weightage: weightage
				});
			}
		});
	
		gridOptionsDepGoalDetails.api.setRowData(rowData);
	
	}
	
	function handleTabClick(target) {
		if (target === "#orgGoal") {
			document.querySelector('[data-bs-target="#goal-v2"]').closest("li").style.display = "block";
			document.querySelector('[data-bs-target="#goal-v3"]').closest("li").style.display = "block";
			document.querySelector('[data-bs-target="#goal-v4"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v5"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v6"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v7"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v2"]').click();
		} else if (target === "#depGoal") {
			document.querySelector('[data-bs-target="#goal-v2"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v3"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v6"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v7"]').closest("li").style.display = "none";
			let depGoalTab = document.querySelector('[data-bs-target="#goal-v4"]').closest("li");
			depGoalTab.style.display = "block";
			let depGoalTabDet = document.querySelector('[data-bs-target="#goal-v5"]').closest("li");
			depGoalTabDet.style.display = "block";
	
			document.querySelector('[data-bs-target="#goal-v4"]').click();
			getAllDepGoal();
			getAllOrgGoalList();
		} else if (target === "#gegGoal") {
			document.querySelector('[data-bs-target="#goal-v2"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v3"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v4"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v5"]').closest("li").style.display = "none";
			document.querySelector('[data-bs-target="#goal-v6"]').closest("li").style.display = "block";
			document.querySelector('[data-bs-target="#goal-v7"]').closest("li").style.display = "block";
			document.querySelector('[data-bs-target="#goal-v6"]').click();
			getAllDesigGoal();
	
		}
	}
	
	/*All functio for designation tab starts from here*/
	function getDepAndDepGoal(depId, depGoalId) {
	
		const goalId = $("#orgGoalListDesig").val();
	
		agGrid.simpleHttpRequest({
			url: "get-appraisal-dep-goal-details?id=" + goalId
		}).then(function(response) {
			console.log("API Response:", response);
	
			$("#depListD").empty().append('<option value="">Select</option>');
			$("#depGoalListD").empty().append('<option value="">Select</option>');
	
			if (response.code === "Success" && response.body.length > 0) {
				const responseBody = JSON.parse(response.body[0]);
				console.log("Parsed Response Body:", responseBody);
	
				const depGoalDetails = responseBody.depGoalDetails;
				if (!Array.isArray(depGoalDetails) || depGoalDetails.length === 0) {
					console.warn("No department data found.");
					return;
				}
	
				let depGoalMap = {};
				let addedDeps = new Set();
	
				depGoalDetails.forEach(depGoal => {
					if (depGoal.depId && depGoal.depName && !addedDeps.has(depGoal.depId)) {
						$("#depListD").append(
							`<option value="${depGoal.depId}">${depGoal.depName}</option>`
						);
						addedDeps.add(depGoal.depId);
					}
	
					if (depGoal.depId && depGoal.depGoalId && depGoal.depGoalName) {
						if (!depGoalMap[depGoal.depId]) {
							depGoalMap[depGoal.depId] = [];
						}
	
						if (!depGoalMap[depGoal.depId].some(g => g.id === depGoal.depGoalId)) {
							depGoalMap[depGoal.depId].push({
								id: depGoal.depGoalId,
								name: depGoal.depGoalName
							});
						}
					}
				});
	
				$("#depListD").data("depGoalMap", depGoalMap);
	
				if (depId) {
					$("#depListD").val(depId).trigger("change");
	
					setTimeout(() => {
						if (depGoalId) {
							let goalIds = Array.isArray(depGoalId) ? depGoalId : [depGoalId];
							$("#depGoalListD").val(goalIds).trigger("change");
						}
					}, 100);
				}
			} else {
				console.error("Failed to fetch data or empty response.");
			}
		});
	
		$("#depListD").on("change", function() {
			let selectedDepId = $(this).val();
			let depGoalMap = $(this).data("depGoalMap");
	
			$("#depGoalListD").empty().append('<option value="">Select</option>');
	
			if (selectedDepId && depGoalMap[selectedDepId]) {
				let depGoals = depGoalMap[selectedDepId];
	
				depGoals.forEach(depGoal => {
					$("#depGoalListD").append(
						`<option value="${depGoal.id}">${depGoal.name}</option>`
					);
				});
			}
		});
	
		$("#depListD, #depGoalListD").select2({
			placeholder: "Select",
			allowClear: true
		});
	}
	$(document).on("change", "#depListD", function() {
		let selectedDepId = $(this).val();
		let depGoalMap = $("#depListD").data("depGoalMap");
	
		$("#depGoalListD").empty().append('<option value="">Select</option>');
	
		if (depGoalMap && depGoalMap[selectedDepId]) {
			let depGoals = depGoalMap[selectedDepId];
	
			if (depGoals.length === 1) {
	
				$("#depGoalListD").append(
					`<option value="${depGoals[0].id}" selected>${depGoals[0].name}</option>`
				);
			} else {
	
				depGoals.forEach(depGoal => {
					$("#depGoalListD").append(
						`<option value="${depGoal.id}">${depGoal.name}</option>`
					);
				});
				$("#depGoalListD").val("");
			}
		}
	});
	
	function addDesigGoal() {
	
		$("#addDesigGoal").text('');
		$("#addDesigGoal").val('');
		$("#saveDesigGoal").show();
		$("#cancelDesigGoal").show();
		$("#addDeGoal").hide();
		$("#editDesigGoal").hide();
		$("#desigGoalId").text('')
		$("#orgGoalListDesig").val('').prop('disabled', false);
		$("#depListD").val('').prop('disabled', false);
		$("#desigList").val('').prop('disabled', false);
		$("#depGoalListD").val('').prop('disabled', false);
		$("#desigGoalName").val('').prop('disabled', false);
		$("#financialYearDesig").val('').prop('disabled', false);
		$("#cancelDesigGoal").show();
		$("#editDesigGoal").hide();
		gridOptionsDeg.api.deselectAll();
	
	}
	
	function cancelDesigGoal() {
	
		$("#saveDesigGoal").hide();
		$("#cancelDesigGoal").hide();
	
		$("#addDeGoal").show();
		$("#editDesigGoal").show();
	
		getAllDesigGoal();
	}
	
	function editDesigGoal() {
		$("#saveDesigGoal").show();
		$("#cancelDesigGoal").show();
		$("#addDeGoal").hide();
		$("#editDesigGoal").hide();
		$("#orgGoalListDesig").prop('disabled', false);
		$("#depListD").prop('disabled', false);
		$("#desigList").prop('disabled', false);
		$("#depGoalListD").prop('disabled', false);
		$("#desigGoalName").prop('disabled', false);
		$("#financialYearDesig").prop('disabled', false);
	}
	
	function saveDesigGoal() {
	
		const desigGoalObj = {};
		desigGoalObj.desigGoalId = $("#desigGoalId").text();
		desigGoalObj.depGoalId = $("#depGoalListD").val();
		desigGoalObj.goalId = $("#orgGoalListDesig").val();
		desigGoalObj.depList = $("#depListD").val();
		desigGoalObj.financialYear = $("#financialYearDesig").val();
		desigGoalObj.desigGoalName = $("#desigGoalName").val();
		desigGoalObj.desigList = $("#desigList").val();
		console.log(desigGoalObj);
	
		if (desigGoalObj.goalId == null || desigGoalObj.goalId == "") {
			toastr.error("Organization goal Name Required");
			return;
		}
	
		if (desigGoalObj.depList == null || desigGoalObj.depList == "") {
			toastr.error("Department  Required");
			return;
		}
	
		if (desigGoalObj.depGoalId == null || desigGoalObj.depGoalId == "") {
			toastr.error("Department Goal Name Required");
			return;
		}
	
		if (desigGoalObj.desigList == null || desigGoalObj.desigList == "") {
			toastr.error("Designation Required");
			return;
		}
	
		if (desigGoalObj.desigGoalName == null || desigGoalObj.desigGoalName == "") {
			toastr.error("Designation Goal Name Required");
			return;
		}
	
	
		saveApprDesigGoal(desigGoalObj);
	}
	
	function saveApprDesigGoal(desigGoalObj) {
		console.log("for save -------->", desigGoalObj);
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-appraisal-desig-details",
			contentType: "application/json",
			data: JSON.stringify(desigGoalObj),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					toastr.success(response.message);
					getAllDesigGoal();
				} else {
					toastr.error(response.message)
				}
	
				$('.loader').hide();
				$("body").removeClass("overlay");
			}
		})
	
	}
	
	
	function getAllDesigGoal() {
	
		agGrid.simpleHttpRequest({
			url: "get-appraisal-desig-details"
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body);
				const desigGoalDetails = responseBody.desigGoalDetails;
				var newRowData = desigGoalDetails.reverse();
				gridOptionsDeg.api.setRowData(newRowData);
				$("#saveDesigGoal").hide();
				$("#cancelDesigGoal").hide();
				$("#addDeGoal").show();
				$("#editDesigGoal").show();
				if (newRowData && newRowData.length > 0) {
					gridOptionsDeg.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
	
			} else {
				console.error("Failed to fetch data");
			}
		});
	
	}
	
	
	function rowSelectDesig() {
		var selectedRows = gridOptionsDeg.api.getSelectedRows();
		if (selectedRows.length > 0) {
			const datas = selectedRows[0];
			const goalId = (datas.goalId);
			$("#desigGoalId").text(datas.desigGoalId);
			$("#orgGoalListDesig").val(goalId).trigger('change').prop('disabled', true);
			$("#depListD").val(datas.departmentId).trigger('change').prop('disabled', true);
			$("#depGoalListD").prop('disabled', true);
			$("#desigList").val(datas.bandId).trigger('change').prop('disabled', true);
			$("#desigGoalName").val(datas.desigGoalName).prop('disabled', true);
			$("#financialYearDesig").val(datas.financialYear).prop('disabled', true);
			$("#desigGoalNamesD").text(datas.desigGoalName);
			$("#designationGoalName").text(datas.desigGoalName);
			$("#designationGoalNames").text(datas.desigGoalName);
			$("#depGoalNamesD").text(datas.depGoalName);
			$("#orgDepGoalNameD").text(datas.goalName);
			getDesigGoalDetails(datas.desigGoalId);
			$("#depGoalIdDes").val(datas.depGoalId);
	
			getDepAndDepGoal(datas.departmentId, datas.depGoalId);
	
		} else {
			$("#orgGoalListDesig").val('').trigger('change').prop('disabled', false);
			$("#depListD").val('').trigger('change').prop('disabled', false);
			$("#desigList").val('').trigger('change').prop('disabled', false);
			$("#depGoalListD").val('').trigger('change').prop('disabled', false);
			$("#desigGoalName").val('').prop('disabled', false);
			$("#financialYearDesig").val('').trigger('change').prop('disabled', false);
			getDepAndDepGoal('');
			getFinancialYear();
			$("#saveDesigGoal").show();
			$("#cancelDesigGoal").show();
			$("#addDeGoal").hide();
			$("#editDesigGoal").hide();
			$("#desigGoalId").text('');
			getDesigGoalDetails('');
			$("#orgDepGoalNameD").text('');
			$("#depGoalNamesD").text('');
			$("#desigGoalNamesD").text('');
			$("#designationGoalName").text('');
			$("#designationGoalNames").text('');
		}
	}
	
	
	function addDesigKra() {
		let lastRow = $('#kra-table-desig tbody tr:last');
		if (lastRow.length > 0 && !lastRow.hasClass('read-only')) {
			toastr.error("Please save the current KRA before adding a new one.");
			return;
		}
	
		let lastDesigKraId = 0;
		$('#kra-table-desig tbody tr').each(function() {
			let kraText = $(this).attr('data-kra-id');
			if (kraText) {
				let kraNumber = parseFloat(kraText.split('.')[1]);
				if (!isNaN(kraNumber) && kraNumber > lastDesigKraId) {
					lastDesigKraId = kraNumber;
				}
			}
		});
	
		const newDesigKraId = `1.${lastDesigKraId + 1}`;
	
		const newRow = `
			        <tr data-kra-id="${newDesigKraId}">
			            <td>
			                <strong>${newDesigKraId}</strong><br>
			                <input type="text" class="form-control desig-kra-name" placeholder="Enter KRA Name">
			            </td>
			            <td>
			                <ul class="desig-kpi-list"></ul>
			                <div class="kpi-input-container" style="display: flex; gap: 5px;">
			                    <input type="text" class="form-control desig-kpi-input" placeholder="Enter KPI">
	
			                     <button class="btn go-btn add-desig-kpi-btn"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
			                </div>
			            </td>
			            <td><input type="number" class="form-control desig-weightage-input" placeholder="Enter Weightage" min="0" max="100"></td>
			            <td>
			                <div class="btn-container">
					                <button class="btn go-btn save-desig-kra">
					                    <i class="fa-solid fa-floppy-disk"></i>
					                </button>
					                <button class="btn go-btn edit-desig-kra" style="display: none;">
					                    <i class="fa fa-pencil-square"></i> 
					                </button>
					                <button class="btn go-btn remove-desig-kra">
					                    <i class="fas fa-trash"></i>
					                </button>
					            </div>
			            </td>
			        </tr>
			    `;
	
		$('#kra-table-desig tbody').append(newRow);
	}
	
	$(document).on('click', '.add-desig-kpi-btn', function() {
		const row = $(this).closest('tr');
		const kpiInput = row.find('.desig-kpi-input');
		const kpiText = kpiInput.val().trim();
		const kpiList = row.find('.desig-kpi-list');
	
		if (kpiText === '') {
			toastr.error('KPI cannot be empty!');
			return;
		}
	
		const kpiItem = `<li>${kpiText}<span class="remove-desig-kpi" style="cursor:pointer;"><i class="fa fa-window-close" aria-hidden="true"></i></span> </li>`;
		kpiList.append(kpiItem);
		kpiInput.val('');
	});
	
	$(document).on('click', '.save-desig-kra', function() {
		const row = $(this).closest('tr');
		const kraName = row.find('.desig-kra-name').val().trim();
		const weightage = row.find('.desig-weightage-input').val().trim();
		const kpiCount = row.find('.desig-kpi-list li').length;
	
		if (kraName === '') {
			toastr.error('KRA Name cannot be empty!');
			return;
		}
		if (kpiCount === 0) {
			toastr.error('At least one KPI is required!');
			return;
		}
		if (weightage === '' || isNaN(weightage) || weightage < 0 || weightage > 100) {
			toastr.error('Please enter a valid weightage between 0 and 100!');
			return;
		}
	
		row.addClass('read-only');
		row.find('.save-desig-kra').hide();
		row.find('.edit-desig-kra').show();
	
		row.find('.desig-kra-name, .desig-weightage-input').prop('disabled', true);
		row.find('.desig-kpi-input, .add-desig-kpi-btn').hide();
		row.find('.remove-desig-kpi').hide();
	});
	
	$(document).on('click', '.remove-desig-kpi', function() {
		$(this).parent('li').remove();
	});
	
	$(document).on('click', '.edit-desig-kra', function() {
		const row = $(this).closest('tr');
		row.removeClass('read-only');
		row.find('.edit-desig-kra').hide();
		row.find('.save-desig-kra').show();
		row.find('.desig-kra-name, .desig-weightage-input').prop('disabled', false);
		row.find('.kpi-input-container').show();
		row.find('.desig-kpi-input, .add-desig-kpi-btn').show();
		row.find('.remove-desig-kpi').show();
	});
	
	/* Remove Designation KRA */
	$(document).on('click', '.remove-desig-kra', function() {
		$(this).closest('tr').remove();
	});
	
	/* Generate JSON for Designation KRAs */
	function generateDesigAppraisalJSON() {
	
	
		const goalId = $("#goalId").text();
		const depGoalId = $("#depGoalIdDes").val();
		const desigGoalId = $("#desigGoalId").text();
		const desigKraList = [];
		let totalWeightage = 0;
	
		$('#kra-table-desig tbody tr').each(function() {
			const kraId = $(this).data('kra-id');
			const kraName = $(this).find('.desig-kra-name').val();
			const weightage = parseFloat($(this).find('.desig-weightage-input').val());
			const kpiList = [];
	
			$(this).find('.desig-kpi-list li').each(function() {
				let kpiText = $(this).text().trim();
				kpiText = kpiText.replace(/❌$/, '').trim();
				kpiList.push({ kpiName: kpiText });
			});
	
			desigKraList.push({
				kraId: kraId,
				kraName: kraName,
				weightage: weightage,
				kpiList: kpiList
			});
	
			totalWeightage += weightage;
		});
	
		if (totalWeightage !== 100) {
			toastr.error(`Total weightage must be exactly 100%! Currently: ${totalWeightage}%`);
			return;
		}
	
		const desigAppraisalData = {
			goalId: goalId,
			depGoalId: depGoalId,
			desigGoalId: desigGoalId,
			kraList: desigKraList
		};
	
		console.log(desigAppraisalData);
	
		saveDesigKraDetails(desigAppraisalData);
	}
	
	/* Save Designation KRA Details */
	function saveDesigKraDetails(desigAppraisalData) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-appraisal-desig-kra-details",
			contentType: "application/json",
			data: JSON.stringify(desigAppraisalData),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					$(".formValidation").remove();
					$('.loader').hide();
					const id = $("#desigGoalId").text();
	
					getDesigGoalDetails(id);
				} else {
					$('.loader').show();
				}
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
	
	/* Fetch Designation Goal Details */
	function getDesigGoalDetails(id) {
		agGrid.simpleHttpRequest({
			url: "get-all-desig-goal-details?id=" + id,
		}).then(function(response) {
			if (response.code === "Success") {
				const responseBody = JSON.parse(response.body[0]);
				const desigGoalDetails = responseBody.desigGoalDetails;
	
				populateDesigGoalTable(desigGoalDetails);
				populateDesigGridDetails(desigGoalDetails);
			}
		});
	}
	
	/* Populate Designation Goal Table */
	function populateDesigGoalTable(desigGoalDetails) {
		if (!desigGoalDetails || desigGoalDetails.length === 0) {
			$("#kra-table-desig tbody").empty();
			return;
		}
	
		const tableBody = $("#kra-table-desig tbody");
		tableBody.empty();
	
		desigGoalDetails.forEach((goal, index) => {
			const kraData = goal.kraList || {};
			const kpiData = goal.kpiList || [];
			const weightage = goal.weightage || 0;
	
			const kraId = kraData.kraId ? kraData.kraId : `1.${index + 1}`;
			const kraName = kraData.kraName ? kraData.kraName : '';
	
			let kpiHtml = kpiData.map(kpi => `
			            <li>
			                ${kpi.kpiName}<span class="remove-desig-kpi" style="cursor:pointer; display:none;"><i class="fa fa-window-close" aria-hidden="true"></i></span>
			            </li>
			        `).join('');
	
			let rowHtml = `
			            <tr class="read-only" data-kra-id="${kraId}">
			                <td>
			                    <strong>${kraId}</strong><br>
			                    <input type="text" class="form-control desig-kra-name" value="${kraName}" disabled>
			                </td>
			                <td>
			                    <ul class="desig-kpi-list">
			                        ${kpiHtml}
			                    </ul>
			                    <div class="kpi-input-container" style="display: none; flex; gap: 5px;">
			                        <input type="text" class="form-control desig-kpi-input" placeholder="Enter KPI">
			                         <button class="btn go-btn add-desig-kpi-btn"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
			                    </div>
			                </td>
			                <td><input type="number" class="form-control desig-weightage-input" value="${weightage}" disabled></td>
			            
			             <td>
			                 <button class="btn go-btn save-desig-kra" style="display: none;"><i class="fas fa-save"></i></button>
		                    <button class="btn go-btn edit-desig-kra"><i class="fa fa-pencil-square"></i> </button>
		                    <button class="btn go-btn remove-desig-kra"><i class="fa fa-trash"></i></button>
		                </td>
			                
			            </tr>
			        `;
	
			tableBody.append(rowHtml);
		});
	}
	
	var columnDefsDesigGoalDetls = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Dep. Goal Name",
			field: "depGoalName",
			flex: 1
	
		},
		{
			headerName: "KRA",
			field: "kraNames",
			flex: 1
		},
		{
			headerName: "KPI",
			field: "kpiNames",
			flex: 1
	
		},
		{
			headerName: "Weightage",
			field: "weightage",
			flex: 1
	
		}];
	
	// Define grid options
	var gridOptionsDesigGoalDetails = {
		columnDefs: columnDefsDesigGoalDetls,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 100
		},
		pagination: true,
		paginationPageSize: 15,
	
	};
	
	function populateDesigGridDetails(desigGoalDetails) {
		if (!Array.isArray(desigGoalDetails) || desigGoalDetails.length === 0) {
			gridOptionsDesigGoalDetails.api.setRowData([]);
			return;
		}
	
		let rowData = [];
	
		desigGoalDetails.forEach((goal, goalIndex) => {
			const weightage = goal.weightage || 0;
			const desigGoalName = goal.desigGoalName || "N/A";
			const kraData = goal.kraList || {};
	
			const kraId = kraData.kraId || `1.${goalIndex + 1}`;
			const kraName = kraData.kraName || "Unnamed KRA";
	
			if (Array.isArray(goal.kpiList) && goal.kpiList.length > 0) {
				goal.kpiList.forEach((kpi) => {
					rowData.push({
						depGoalName: desigGoalName,
						kraNames: `${kraId} - ${kraName}`,
						kpiNames: kpi.kpiName || "Unnamed KPI",
						weightage: weightage
					});
				});
			} else {
				rowData.push({
					depGoalName: desigGoalName,
					kraNames: `${kraId} - ${kraName}`,
					kpiNames: "No KPI",
					weightage: weightage
				});
			}
		});
	
		gridOptionsDesigGoalDetails.api.setRowData(rowData);
	
	}
	
	
	function onQuickFilterChanged() {
		let searchValue = document.getElementById('quickFilter').value;
	
		gridOptions.api.setQuickFilter(searchValue);
		gridOptionsDep.api.setQuickFilter(searchValue);
		gridOptionsDeg.api.setQuickFilter(searchValue);
	
		$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	
		getMostClosestRow();
	}
	
	function getMostClosestRow() {
		let searchValue = document.getElementById('quickFilter').value;
	
		[gridOptions, gridOptionsDep, gridOptionsDeg].forEach(grid => {
			grid.api.setQuickFilter(searchValue);
	
			grid.api.forEachNodeAfterFilter((node, index) => {
				if (index === 0) {
					node.setSelected(true);
					grid.api.ensureIndexVisible(node.rowIndex);
				}
			});
		});
	}
	
	function resetBtn() {
		$("#quickFilter").val('');
	
		[gridOptions, gridOptionsDep, gridOptionsDeg].forEach(grid => {
			grid.api.setQuickFilter('');
			grid.api.refreshCells({ force: true });
	
			setTimeout(() => {
				grid.api.forEachNode((node, index) => {
					if (index === 0) {
						node.setSelected(true);
						grid.api.ensureIndexVisible(node.rowIndex);
					}
				});
			}, 50);
		});
	}
	
	function SearchUserInput(event) {
		if (event.key === "Enter" || event.keyCode === 13 || event.key === "Backspace" || event.keyCode === 8) {
			onQuickFilterChanged();
		}
	}
	
	function getAllOrgGoalList() {
		agGrid.simpleHttpRequest({
			url: "get-all-org-goal-list"
		}).then(function(response) {
			const dropdownIds = ["orgGoalList", "orgGoalListDesig"];
	
			dropdownIds.forEach(function(dropdownId) {
				var select = document.getElementById(dropdownId);
	
				select.innerHTML = '<option value="">Select</option>';
				response.forEach(function(item) {
					var option = document.createElement("option");
					option.value = item.key;
					option.text = item.name;
					select.appendChild(option);
				});
			});
		});
	}
	
	
