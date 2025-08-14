
let templateSectionOptions;
let templateSectionMainOptions;
let mainGridData;
document.addEventListener('DOMContentLoaded', function() {
	$("#publishTender").attr("disabled", true);
	var now = new Date();
	var year = now.getFullYear();
	CKEDITOR.replace('landingPageEditor');
	templateSectionMainOptions = {
		columnDefs: [

			{ headerCheckboxSelection: true, checkboxSelection: true, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },

			{
				headerName: "Tender Id",
				field: "templateId",
				/*cellRenderer: function(params) {
					return `<a onclick="tenderDetails(${params.rowIndex})" href="javascript:void(0)">
						${params.data.templateId}
					</a>`;
				}*/
			},
			{
				headerName: "Tender Name",
				field: "templateName",
			},
			{
				headerName: "Description",
				field: "templateDescription",
				hide: true,
			},
			{
				headerName: "Created Date",
				field: "creationDate",
			}, {
				headerName: "Effective Date",
				field: "effectiveDate",
			}, {
				headerName: "status",
				field: "status",
				cellRenderer: function(params) {
					return params.value == "1" ? 'Active' : 'Inactive';
				}
			}, {
				headerName: "Published Pdf",
				field: "tenderPdf",
				cellStyle: {
					textAlign: 'center'
				},
				cellRenderer: function(params) {
					const pdfUrl = params.value;
					if (pdfUrl && pdfUrl !== "null") {
						return `
					                <a href="${pdfUrl}" target="_blank" title="Open PDF">
					                    <img src="../assets/images/pdf_demo.png" alt="PDF" style="width: 15px; height: 18px;"/>
					                </a>
					            `;
					} else {
						return '';
					}
				}
			}, {
				headerName: "Published Status",
				field: "publishStatus",
				cellStyle: {
					textAlign: 'center'
				},
				cellRenderer: function(params) {
					const status = params.value;
					if (status && status == "1") {
						return `
					                <a title="Published" style="color: green; text-decoration: none;">Published</a>`;
					} else {
						return `<a title="Published" style="color: #f83237; text-decoration: none;">Not Published</a>`;
					}
				}
			},
			{
				headerName: "Evalution Details",
				field: "EvalutionDetails",
				hide: true,
				width: 230,
				cellStyle: {
					textAlign: 'center'
				}
			}
		],
		defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
		pagination: true,
		paginationPageSize: 15,
		rowData: null,
		onSelectionChanged: function() {
			const selectedRows = this.api.getSelectedRows();
			console.log("selected rows-^^^^->", selectedRows);
			var rowCount = 0;
			selectedRows.forEach(function(selectedRow, index) {
				rowCount = rowCount + 1;
			});

			if (rowCount > 0) {
				$("#tenderSave-btn").hide();
				$("#tender-cancelbtn").hide();
				$("#add-btn").show();
				var tendorName = selectedRows[0].templateName;
				var tenderid = selectedRows[0].templateId;
				$("#headertenderid").text(tenderid);
				$("#headertenderName").text(tendorName);
			} else {
				$("#tenderSave-btn").show();
				$("#tender-cancelbtn").show();
				$("#add-btn").hide();
				$("#headertenderid").text(" ");
				$("#headertenderName").text('');
			}

			if (selectedRows.length > 0) {

				selectedRows.forEach(function(row) {
					console.log("row-->", row);
					ApplyTenderData(row);
					const rowIndex = row.rowIndex;
				});

				let evalutionDetails = JSON.parse(selectedRows[0].EvalutionDetails);
				console.log("evDetails--->", evalutionDetails);
				evalutionEditDetails(evalutionDetails);
			}
			else {
				$("#headertenderid").text(" ");
				$("#headertenderName").text('');
			}
		},
		onGridReady: function(params) {
			params.api.sizeColumnsToFit();
			window.gridApi = params.api;
			window.templateSectionMainApi = params.api;
			params.api.setRowData([]);
			var fromDate = '';
			var toDate = '';
			agGrid.simpleHttpRequest({
				url: `tender-data?fromDate=${fromDate}&toDate=${toDate}`
			}).then(function(response) {
				if (response.code === "Success") {
					const parsedResponse = JSON.parse(response.body);
					//params.api.setRowData(parsedResponse);
					var rowData = [];
					params.api.setRowData(rowData);
					params.api.setRowData(parsedResponse);
					mainGridData = parsedResponse;
					if (parsedResponse && parsedResponse.length > 0) {
						params.api.forEachNode(function(node) {
							if (node.rowIndex === 0) {
								node.setSelected(true); // Select the first row
							}
						});
					}
				}
			});
			//params.api.setRowData(mainData.tenders);

			params.api.sizeColumnsToFit();
			window.templateSectionMainApi = params.api;

			params.api.addEventListener('selectionChanged', function() {
				const selectedRows = params.api.getSelectedRows();
				console.log("selected rowss---^^^^->", selectedRows);

				if (selectedRows.length > 0) {
					const selectedTemplate = selectedRows[0];
					$("#tenderId").val(selectedTemplate.templateId);
					$("#tenderSave-btn").hide();
					$("#next-btn-1").show();
					$("#publishTender").show();
					$("#voucherclassTab").show();

					if (selectedTemplate.publishStatus == "1") {
						$("#publishTender").attr("disabled", true);
						$("#addSection").attr("disabled", true);
						$("#saveStatus").attr("disabled", true);
					} else {
						$("#publishTender").attr("disabled", false);
						$("#addSection").attr("disabled", false);
						$("#saveStatus").attr("disabled", false);
					}

					// Populate templateDetailsOptions grid
					const templateDetailsData = [{
						templateId: selectedTemplate.templateId,
						templateName: selectedTemplate.templateName,
						templateDescription: selectedTemplate.templateDescription,
						creationDate: selectedTemplate.creationDate
					}];
					window.templateDetailsApi.setRowData(templateDetailsData);

					// Populate templateSectionOptions grid
					const templateSectionsData = selectedTemplate.sections.map((section, index) => ({
						sectionNo: section.sectionId,
						name: section.name,
						description: section.description,
						sectionStatus: section.sectionStatus,
						data: section.data,
						users: section.users
					}));
					window.templateSectionApi.setRowData(templateSectionsData);

					// Show/hide grids
					const templateDetailsGrid = document.getElementById('templateDetailsGrid');
					const templateSectionGrid = document.getElementById('templateSectionGrid');
					const templateDetailsMainGrid = document.getElementById('templateDetailsMainGrid');
					const addSection = document.getElementById('addSection');
					const previewSection = document.getElementById('previewSection');
					const downloadPdf = document.getElementById('downloadPdf');
					//	const addTender = document.getElementById('addTender');
					const saveStatus = document.getElementById('saveStatus');
					//const searchRowDiv = document.getElementById('searchRowDiv');
					const cancelTender = document.getElementById('cancelTender');

					//templateDetailsMainGrid.classList.add('hidden-height2');
					templateDetailsGrid.classList.remove('hidden');
					cancelTender.classList.remove('hidden');
					templateSectionGrid.classList.remove('hidden');
					addSection.classList.remove('hidden');
					saveStatus.classList.remove('hidden');
					//addTender.classList.add('hidden');
					//searchRowDiv.classList.add('hidden');
					previewSection.classList.remove('hidden');
					downloadPdf.classList.remove('hidden');
					templateDetailsGrid.classList.add('hidden-height');
					templateSectionGrid.classList.add('hidden-height1');

					loadSections(selectedRows[0].sections);


					//check-Effective tender dates
					let tenderEffectiveDate = selectedTemplate.effectiveDate;
					let updatedEffectiveDate = new Date(tenderEffectiveDate.replace(" ", "T"));
					let currentDate = new Date();

					if (currentDate > updatedEffectiveDate) {
						$("#next-btn-2").show();
						$("#evalutionTeam").show();
					}
					else {
						$("#next-btn-2").hide();
						$("#evalutionTeam").hide();
					}
				} else {
					$("#publishTender").attr("disabled", true);
					// Hide details and sections grids
					const templateDetailsGrid = document.getElementById('templateDetailsGrid');
					const templateSectionGrid = document.getElementById('templateSectionGrid');
					const templateDetailsMainGrid = document.getElementById('templateDetailsMainGrid');
					const addSection = document.getElementById('addSection');
					const previewSection = document.getElementById('previewSection');
					const downloadPdf = document.getElementById('downloadPdf');
					//const addTender = document.getElementById('addTender');
					const saveStatus = document.getElementById('saveStatus');
					//	const searchRowDiv = document.getElementById('searchRowDiv');
					const cancelTender = document.getElementById('cancelTender');

					//templateDetailsMainGrid.classList.remove('hidden-height2');
					templateDetailsGrid.classList.add('hidden');
					templateSectionGrid.classList.add('hidden');
					cancelTender.classList.add('hidden');
					addSection.classList.add('hidden');
					//addTender.classList.remove('hidden');
					previewSection.classList.add('hidden');
					downloadPdf.classList.add('hidden');
					saveStatus.classList.add('hidden');
					//searchRowDiv.classList.remove('hidden');
					templateDetailsGrid.classList.remove('hidden-height');
					templateSectionGrid.classList.remove('hidden-height1');
				}
			});
		}
	};


	const templateDetailsOptions = {
		columnDefs: [
			{
				headerName: 'Template Details',
				children: [
					{
						headerName: "Template Id",
						field: "templateId",
						width: 280
					},
					{
						headerName: "Template Name",
						field: "templateName",
						width: 448
					},
					{
						headerName: "Description",
						field: "templateDescription",
						width: 650
					},
					{
						headerName: "Creation Date",
						field: "creationDate",
						width: 250
					}
				]
			}
		],
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,

		},
		rowData: null,

		onGridReady: function(params) {
			params.api.sizeColumnsToFit();
			window.templateDetailsApi = params.api;

		}
	};

	templateSectionOptions = {
		columnDefs: [
			{
				headerName: 'Template Section',
				children: [
					{
						headerName: "Section No",
						children: [
							{
								headerCheckboxSelection: true,
								headerCheckboxSelectionFilteredOnly: true,
								checkboxSelection: true,
								sortable: false,
								filter: false,
								resizable: true,
								width: 50,

							},
							{
								headerName: "No",
								field: "sectionNo",
								width: 100
							}
						]
					},
					{
						headerName: "Section Name",
						children: [
							{
								headerName: "Name",
								field: "name",
								width: 348
							}
						]
					},
					{
						headerName: "Description",
						children: [
							{
								field: "description",
								width: 530
							}
						]
					},
					{
						headerName: "Active/Inactive",
						children: [
							{
								field: "sectionStatus",
								width: 300,
								cellRenderer: DropdownCellRendererStatus
							}
						]
					},
					{
						headerName: "Assign Users",
						children: [
							{
								headerName: "Users",
								field: "users",
								width: 300,
								cellRenderer: DropdownCellRenderer

							}
						]
					}, {
						headerName: "Data",
						hide: true,
						children: [
							{
								headerName: "Content",
								field: "data",
								width: 400
							}
						]
					}
				]
			}
		],
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true
		},
		rowSelection: 'multiple',
		onGridReady: function(params) {
			params.api.sizeColumnsToFit();
			window.templateSectionApi = params.api;
		},
		onCellValueChanged: function(event) {
			const rowId = event.node.id;
			const newStatus = event.newValue;
			saveGridData();
		},
	};


	const templateDetailsDiv = document.querySelector('#templateDetailsGrid');
	new agGrid.Grid(templateDetailsDiv, templateDetailsOptions);

	const templateSectionDiv = document.querySelector('#templateSectionGrid');
	new agGrid.Grid(templateSectionDiv, templateSectionOptions);

	const templateSectionMainDiv = document.querySelector('#templateDetailsMainGrid');
	new agGrid.Grid(templateSectionMainDiv, templateSectionMainOptions);

	// Ensure the grid API is available before adding event listener
	/*window.onQuickFilterChanged = function() {
		if (templateSectionOptions.api) {
			templateSectionOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
			var displayedRowCount = templateSectionOptions.api.getDisplayedRowCount();
			var len = displayedRowCount;
			$('#totalReq').find('span').html(len);
		} else {
		}
	};*/

	window.driverAllocation = function() {
	};

});


function saveGridData() {
	const allRowData = [];
	templateSectionOptions.api.forEachNode(node => allRowData.push(node.data));
}

function tenderDetails(rowIndex) {
	gridApi.selectIndex(rowIndex, false, false);
}

function cancelTender() {
	gridApi.deselectAll();
}


class DropdownCellRendererStatus {
	init(params) {
		this.eGui = document.createElement('div');
		this.eGui.style.position = 'relative';

		// Create the dropdown
		this.select = document.createElement('select');
		this.select.classList.add('custom-dropdown');
		this.select.setAttribute('aria-label', 'Assign status');

		// Create placeholder option
		const placeholder = document.createElement('option');
		placeholder.value = '';
		placeholder.textContent = '---Select status---';
		placeholder.disabled = true;
		placeholder.selected = true;
		this.select.appendChild(placeholder);

		// Static options for the status dropdown
		const statuses = [
			{ value: 'active', text: 'Active' },
			{ value: 'inactive', text: 'Inactive' }
		];

		statuses.forEach(status => {
			const option = document.createElement('option');
			option.value = status.value;
			option.textContent = status.text;
			this.select.appendChild(option);
		});

		this.eGui.appendChild(this.select);

		this.checkmark = document.createElement('i');
		this.checkmark.classList.add('fa', 'fa-check', 'checkmarkDrop');
		this.checkmark.style.display = 'none';
		this.eGui.appendChild(this.checkmark);

		// Initialize the select value
		this.select.value = params.value || '';
		this.updateCheckmarkVisibility();
		this.setDropdownDisabled(params.value);

		// Handle dropdown changes
		this.select.addEventListener('change', (event) => {
			this.updateCheckmarkVisibility();
			const selectedValue = event.target.value;
			if (selectedValue === '') {
			}

			const newStatus = this.select.value;

			// Update the row data with the new status
			params.node.setDataValue(params.colDef.field, newStatus);

			// Get the row ID and new status value
			const rowId = params.node.id;
		});
	}

	// Update the visibility of the checkmark icon
	updateCheckmarkVisibility() {
		const hasSelection = this.select.value !== '';
		this.checkmark.style.display = hasSelection ? 'inline' : 'none';
	}

	// Disable dropdown if a value is set
	setDropdownDisabled(value) {
		this.select.disabled = !!value;
		if (this.select.disabled) {
			this.select.style.backgroundColor = '#e9ecef';
			this.select.style.color = '#6c757d';
		} else {
			this.select.style.backgroundColor = '';
			this.select.style.color = '';
		}
	}

	getGui() {
		return this.eGui;
	}
}



class DropdownCellRenderer {
	init(params) {

		this.eGui = document.createElement('div');
		this.eGui.style.position = 'relative';

		// Create the dropdown
		this.select = document.createElement('select');
		this.select.classList.add('custom-dropdown');
		this.select.setAttribute('aria-label', 'Assign users');

		// Create placeholder option
		const placeholder = document.createElement('option');
		placeholder.value = '';
		placeholder.textContent = '---Select a user---';
		placeholder.disabled = true;
		placeholder.selected = true;
		this.select.appendChild(placeholder);

		this.eGui.appendChild(this.select);

		this.checkmark = document.createElement('i');
		this.checkmark.classList.add('fa', 'fa-check', 'checkmarkDrop');
		this.checkmark.style.display = 'none';
		this.eGui.appendChild(this.checkmark);

		agGrid.simpleHttpRequest({
			url: 'get-users-details'
		}).then(response => {
			this.select.innerHTML = '';
			this.select.appendChild(placeholder);

			response.forEach(employee => {
				const opt = document.createElement('option');
				opt.value = employee.empId;
				opt.textContent = employee.name;
				this.select.appendChild(opt);
			});

			this.select.value = params.value || '';
			this.updateCheckmarkVisibility();
			this.setDropdownDisabled(params.value);

		});

		// Handle dropdown changes
		this.select.addEventListener('change', (event) => {
			this.updateCheckmarkVisibility();
			const selectedValue = event.target.value;
			if (selectedValue === '') {
				console.error('No user selected');
			}

			const users = this.select.value;

			// Update the row data with the new status
			params.node.setDataValue(params.colDef.field, users);

			// Get the row ID and new status value
			const rowId = params.node.id;
		});
	}

	// Update the visibility of the checkmark icon
	updateCheckmarkVisibility() {
		const hasSelection = this.select.value !== '';
		this.checkmark.style.display = hasSelection ? 'inline' : 'none';
	}

	// Disable dropdown if a value is set
	setDropdownDisabled(value) {
		this.select.disabled = !!value;

		if (this.select.disabled) {
			this.select.style.backgroundColor = '#e9ecef';
			this.select.style.color = '#6c757d';
		} else {
			this.select.style.backgroundColor = '';
			this.select.style.color = '';
		}
	}


	getGui() {
		return this.eGui;
	}
}

function initializeCKEditor() {
	document.querySelectorAll('.ckeditor').forEach((element) => {
		CKEDITOR.replace(element.id);
	});
	document.querySelectorAll('.ckeditorCriteria').forEach((element) => {
		CKEDITOR.replace(element.id);
	});
}

/*function addSection() {
	//TO Hide THe Aggrids-->

	$("#templateSectionGrid").hide();
	$("#templateDetailsGrid").hide();
	$(".templateSection-content").show();
	$("#saveStatus").hide();

	const selectedRows = templateSectionOptions.api.getSelectedRows();
	const tbody = document.querySelector('#dynamicSectionTable tbody');
	tbody.innerHTML = '';

	selectedRows.forEach((row, index) => {
		let sectionHeaderHtml = "";
		if (row.name == "Criteria") {

			if (row.data != "" && row.data != "null" && row.data != null) {
				var parser = new DOMParser();
				var doc = parser.parseFromString(row.data, 'text/html');

				// Extract the <tbody> element's HTML content
				var tbodyElement = doc.querySelector('tbody');

				if (tbodyElement) {
					// Get all rows (<tr>) inside the <tbody>
					var rows = tbodyElement.querySelectorAll('tr');

					// Iterate over each row and add a new <td> with buttons
					rows.forEach(function(row) {
						// Create a new <td> element
						var newTd = doc.createElement('td');

						newTd.innerHTML = `
							<button type="button" class="btn btn-success save-btn-criteria d-none" data-id="${index}">Save</button>
							<button type="button" class="btn btn-warning edit-btn-criteria" data-id="${index}">Edit</button>
							<button type="button" class="btn btn-danger delete-btn-criteria" data-id="${index}">Delete</button>
						`;

						row.appendChild(newTd);
					});

					var updatedTbodyContent = tbodyElement.innerHTML;

				}

				sectionHeaderHtml = `<tr class="section-header">
					  <td class="section-content section-no-column" style="width: 100px;text-align: center;">No</td>
					  <td class="section-content">${row.name}</td>
					</tr>
					<tr>
					  <td class="countTd">${row.sectionNo}</td>
					  <td>
						<div class="card-body" id="criteriaBody">
							<table class="table table-bordered invoice-items"
								id="dynamic_field">
								<thead>
									<tr class="text-center">
										<th>#</th>
										<th>Criteria</th>
										<th>Description</th>
										<th style="width: 96px;">Score</th>
										<th style="width: 142px;">Action</th>
									</tr>
								</thead>
								<tbody>${updatedTbodyContent}
								</tbody>
								</table>
						</div>
						<div class="card-footer text-right">
							<button type="button" id="add" class="btn btn-primary">+ Add
								More</button>
						</div>
					  </td>
					</tr>`;
			} else {
				sectionHeaderHtml = `<tr class="section-header">
				  <td class="section-content section-no-column" style="width: 100px;text-align: center;">No</td>
				  <td class="section-content">${row.name}</td>
				</tr>
				<tr>
				  <td class="countTd">${row.sectionNo}</td>
				  <td>
					<div class="card-body" id="criteriaBody">
						<table class="table table-bordered invoice-items"
							id="dynamic_field">
							<thead>
								<tr class="text-center">
									<th>#</th>
									<th>Criteria</th>
									<th>Description</th>
									<th style="width: 96px;">Score</th>
									<th style="width: 142px;">Action</th>
								</tr>
							</thead>
							<tbody>
								<tr id="row1">
									<td>
										<h4>1</h4>
									</td>
									<td><textarea id="ckeditorCriteria1_${index + 1}" rows="10" cols="80"
											class="ckeditorCriteria"></textarea>
									</td>
									<td><textarea id="ckeditorCriteria2_${index + 1}" rows="10" cols="80"
											class="ckeditorCriteria"></textarea></td>
									<td><input type="number" class="form-control score-input square-input" id="score_${index + 1}" min="0" max="100" step="1" placeholder="Enter score"></td>
									<td>
										<button type="button"
											class="btn btn-success save-btn-criteria" data-id="${index + 1}">Save</button>
										<button type="button"
											class="btn btn-warning edit-btn-criteria d-none" data-id="${index + 1}">Edit</button>
										<button type="button"
											class="btn btn-danger delete-btn-criteria" data-id="${index + 1}">Delete</button>
										</td>
									</tr>
								</tbody>
							</table>
					</div>
					<div class="card-footer text-right">
						<button type="button" id="add" class="btn btn-primary"> + Add
							More</button>
					</div>
				  </td>
				</tr>`;
			}



		} else {
			sectionHeaderHtml = `
				<tr class="section-header">
				  <td class="section-content section-no-column" style="width: 100px;text-align: center;">No</td>
				  <td class="section-content">${row.name}</td>
				</tr>
				<tr>
				  <td class="countTd">${row.sectionNo}</td>
				  <td>
					<textarea id="editor-${index}" class="ckeditor">${row.data ? row.data : ''}</textarea>
				  </td>
				</tr>
			  `;
		}

		tbody.insertAdjacentHTML('beforeend', sectionHeaderHtml);
	});

	// Show the modal
	$("#sectionModal").modal("show");

	initializeCKEditor();
}*/



function addSection() {
	//TO Hide THe Aggrids-->

	$("#templateSectionGrid").hide();
	$("#templateDetailsGrid").hide();
	$(".templateSection-content").show();
	$("#saveStatus").hide();

	const selectedRows = templateSectionOptions.api.getSelectedRows();
	const tbody = document.querySelector('#dynamicSectionTable tbody');
	tbody.innerHTML = '';

	selectedRows.forEach((row, index) => {
		let sectionHeaderHtml = "";
		if (row.name == "Criteria") {

			if (row.data != "" && row.data != "null" && row.data != null) {
				var parser = new DOMParser();
				var doc = parser.parseFromString(row.data, 'text/html');

				// Extract the <tbody> element's HTML content
				var tbodyElement = doc.querySelector('tbody');

				if (tbodyElement) {
					// Get all rows (<tr>) inside the <tbody>
					var rows = tbodyElement.querySelectorAll('tr');

					// Iterate over each row and add a new <td> with buttons
					rows.forEach(function(row) {
						// Create a new <td> element
						var newTd = doc.createElement('td');

						newTd.innerHTML = `
				            <button type="button" class="btn btn-success save-btn-criteria d-none" data-id="${index}">Save</button>
				            <button type="button" class="btn btn-warning edit-btn-criteria" data-id="${index}">Edit</button>
				            <button type="button" class="btn btn-danger delete-btn-criteria" data-id="${index}">Delete</button>
				        `;

						row.appendChild(newTd);
					});

					var updatedTbodyContent = tbodyElement.innerHTML;

				}

				sectionHeaderHtml = `<tr class="section-header">
			          <td class="section-content section-no-column" style="width: 100px;text-align: center;">No</td>
			          <td class="section-content">${row.name}</td>
			        </tr>
			        <tr>
			          <td class="countTd">${row.sectionNo}</td>
			          <td>
			            <div class="card-body" id="criteriaBody">
							<table class="table table-bordered invoice-items dynamic-table""
								>
								<thead>
									<tr class="text-center">
										<th>#</th>
										<th>Criteria</th>
										<th>Description</th>
										<th style="width: 96px;">Score</th>
										<th style="width: 142px;">Action</th>
									</tr>
								</thead>
								<tbody>${updatedTbodyContent}
								</tbody>
								</table>
						</div>
						<div class="card-footer text-right">
							<button type="button" class="btn btn-primary add-more-btn"> + Add More</button>
						</div>
			          </td>
			        </tr>`;
			} else {
				sectionHeaderHtml = `<tr class="section-header">
		          <td class="section-content section-no-column" style="width: 100px;text-align: center;">No</td>
		          <td class="section-content">${row.name}</td>
		        </tr>
		        <tr>
		          <td class="countTd">${row.sectionNo}</td>
		          <td>
		            <div class="card-body" id="criteriaBody">
						<table class="table table-bordered invoice-items"
							id="dynamic_field">
							<thead>
								<tr class="text-center">
									<th>#</th>
									<th>Criteria</th>
									<th>Description</th>
									<th style="width: 96px;">Score</th>
									<th style="width: 142px;">Action</th>
								</tr>
							</thead>
							<tbody>
								<tr id="row1">
									<td>
										<h4>1</h4>
									</td>
									<td><textarea id="ckeditorCriteria1_${index + 1}" rows="10" cols="80"
											class="ckeditorCriteria"></textarea>
									</td>
									<td><textarea id="ckeditorCriteria2_${index + 1}" rows="10" cols="80"
											class="ckeditorCriteria"></textarea></td>
									<td><input type="number" class="form-control score-input square-input" id="score_${index + 1}" min="0" max="100" step="1" placeholder="Enter score"></td>
									<td>
										<button type="button"
											class="btn btn-success save-btn-criteria" data-id="${index + 1}">Save</button>
										<button type="button"
											class="btn btn-warning edit-btn-criteria d-none" data-id="${index + 1}">Edit</button>
										<button type="button"
											class="btn btn-danger delete-btn-criteria" data-id="${index + 1}">Delete</button>
										</td>
									</tr>
								</tbody>
							</table>
					</div>
					<div class="card-footer text-right">
						<button type="button" id="add" class="btn btn-primary"> + Add
							More</button>
					</div>
		          </td>
		        </tr>`;
			}



		} else {
			sectionHeaderHtml = `
		        <tr class="section-header">
		          <td class="section-content section-no-column" style="width: 100px;text-align: center;">No</td>
		          <td class="section-content">${row.name}</td>
		        </tr>
		        <tr>
		          <td class="countTd">${row.sectionNo}</td>
		          <td>
		            <textarea id="editor-${index}" class="ckeditor">${row.data ? row.data : ''}</textarea>
		          </td>
		        </tr>
		      `;
		}

		tbody.insertAdjacentHTML('beforeend', sectionHeaderHtml);
	});

	// Show the modal
	$("#sectionModal").modal("show");

	initializeCKEditor();
}

function saveSections() {
	const selectedRows = templateSectionOptions.api.getSelectedRows();




	selectedRows.forEach((row, index) => {
		if (row.name == "Criteria") {
			const tableData = [];

			$('#dynamic_field tbody tr').each(function() {
				const row = $(this);
				const tender = $("#tenderId").val();
				const rowData = {
					tenderId: tender,
					criteria: row.find('#criteria_' + row.find('button').data('id')).text().trim(),
					description: row.find('#criteriaDescription_' + row.find('button').data('id')).text().trim(),
					score: row.find('#score_' + row.find('button').data('id')).text().trim()
				};
				tableData.push(rowData);
				saveCriteriaData(tableData);

			});
			var criteriaBodyContent = $('#criteriaBody').html();
			// Define your inline CSS styles
			var headerStyle = 'text-align: center; background-color: #f2f2f2; border: 1px solid black; padding: 8px;';
			var cellStyle = 'border: 1px solid black; padding: 8px;';

			// Replace table header and cell tags with styled versions
			criteriaBodyContent = criteriaBodyContent.replace(/<th>/g, `<th style="${headerStyle}">`);
			criteriaBodyContent = criteriaBodyContent.replace(/<th style="width: 96px;">/g, `<th style="${headerStyle}">`);
			criteriaBodyContent = criteriaBodyContent.replace(/<td>/g, `<td style="${cellStyle}">`);

			// Optionally, you can also style other tags or elements
			criteriaBodyContent = criteriaBodyContent.replace(/<h4>/g, '<h4 style="margin: 0;">');
			criteriaBodyContent = criteriaBodyContent.replace(/<p>/g, '<p style="margin: 0;">');

			var $tempDiv = $('<div>').html(criteriaBodyContent);
			$tempDiv.find('#dynamic_field thead th:last-child').remove();
			$tempDiv.find('#dynamic_field tbody tr').each(function() {
				$(this).find('td:last-child').remove();
			});
			row.data = $tempDiv.html();
		} else {
			const editorContent = CKEDITOR.instances[`editor-${index}`].getData();
			const content = editorContent;
			row.data = content;
			//saveStatus();

		}
	});

	templateSectionOptions.api.refreshCells({ force: true });
	$("#sectionModal").modal("hide");
	saveGridData();
	cancelTemplateSection();
}

function saveCriteriaData(data) {
	const payload = { criteriaData: data };

	$.ajax({
		type: "POST",
		url: "update-criteria-tender-data",
		contentType: "application/json",
		data: JSON.stringify(payload),
		success: function(response) {
			if (response.code == "Success") {
			} else {
			}
		},
		error: function(datas) {
			console.log(datas);
		}
	});

}

/*function openReviewData() {
	const gridApi = templateSectionOptions.api;
	const rowData = [];

	gridApi.forEachNode((node) => {
		rowData.push(node.data);
	});

	const modalBody = document.querySelector('#reviewModal .modal-body');
	modalBody.innerHTML = '';

	rowData.forEach(row => {
		var $tempDiv = $('<div>').html(row.data);
		$tempDiv.find('#dynamic_field thead th:last-child').remove();
		$tempDiv.find('#dynamic_field tbody tr').each(function() {
			$(this).find('td:last-child').remove();
		});
		const bodyContent = $tempDiv.html();
		function stripHtmlTags(html) {
			const doc = new DOMParser().parseFromString(html, 'text/html');
			return doc.body.textContent || "";
		}

		<h3 class="section-title1">${row.sectionNo}. ${row.name}</h3>
		const cleanData = stripHtmlTags(row.data);
		const rowHtml = `
		<div class="review-section">
			<div class="section-details1">
			 <h3 class="section-title1" style="text-align: center;">${row.name}</h3>
				<div class="section-content1">${bodyContent}</div>
			</div>
		</div>`;
		modalBody.insertAdjacentHTML('beforeend', rowHtml);
	});

}*/

function openReviewData() {
	const gridApi = templateSectionOptions.api;
	const rowData = [];

	gridApi.forEachNode((node) => {
		rowData.push(node.data);
	});

	console.log("aggrid review --->", rowData);

	const modalBody = document.querySelector('#reviewModal .modal-body');
	modalBody.innerHTML = '';

	rowData.forEach(row => {
		const $tempDiv = $('<div>').html(row.data);

		// Remove <h3> tags (especially the one you don't want)
		$tempDiv.find('h3').remove();

		const $table = $tempDiv.find('#dynamic_field');

		$table.find('thead tr').each(function() {
			const $ths = $(this).find('th');
			if ($ths.length === 5) {
				$ths.last().remove();
			}
		});

		$table.find('tbody tr').each(function() {
			const $tds = $(this).find('td');
			if ($tds.length === 5) {
				$tds.last().remove();
			}
		});

		const bodyContent = $tempDiv.html();

		const rowHtml = `
        <div class="review-section">
            <div class="section-details1">
                <div class="section-content1">${bodyContent}</div>
            </div>
        </div>`;

		modalBody.insertAdjacentHTML('beforeend', rowHtml);
	});
}



/*function openReviewModal(pathValue) {
	const tenderId = $("#tenderId").val();
	console.log("tenderId-->", tenderId);
	openReviewData();
	$(".loader").show();
	const content = document.querySelector('#reviewModal .modal-body').innerHTML;

	console.log("content-->", content);
	fetch(`get-pdf-data/${pathValue}?tenderId=` + tenderId, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			pdfContent: content
		})

	}).then(response => {
		if (response.ok) {
			if (isClicked) {
				$("#messageParagraph").html(`Tender published successfully!`);
				$("#msgOkModal").removeClass("btn3").addClass("btn1");
				$("#msgModal").modal('show');
				isClicked = false;
				$(".loader").hide();
				fetchAndUpdateGrid();
				cancelTender();
				openReviewModal('previewPdf');
				resolve();
			} else {
				return response.blob();
			}
		} else {
			throw new Error('Error generating PDF');
		}

	}).then(blob => {
		const url = window.URL.createObjectURL(blob);
		window.open(url);
		window.URL.revokeObjectURL(url);
		$(".loader").hide();
	});
	$(".loader").hide();
}*/


/*
function openReviewModal(pathValue) {

	var tenderId = $("#headertenderid").text();
	var tenderHeaderText = "";
	$(".loader").show();
	openReviewData();
	var content = document.querySelector("#reviewModal .modal-body");
	console.log("content-->", content.innerHTML);
	var firstTitle = $("h3.section-title1").first().text();
	console.log(firstTitle);

	if (!content) {
		console.error("Error: Content not found!");
		return;
	}

	var htmlContent = `
			<html>
			<head>
				<title>Tender_${firstTitle}</title>
				<style>
					body { font-family: Arial, sans-serif; padding: 20px; }
					.container { width: 100%; max-width: 800px; margin: auto; }
					table { width: 100%; border-collapse: collapse; margin-top: 10px; }
					th, td { border: 1px solid black; padding: 8px; text-align: left; }
				</style>
			</head>
			<body>
				<div class="container">
					${content.innerHTML}
				</div>
			</body>
			</html>
		`;

	fetch(`tender-management-contentpdf?pathvalue=${pathValue}&tenderid=${tenderId}`, {
		method: "POST",
		headers: { "Content-Type": "text/plain" },
		body: htmlContent
	})
		.then(response => {
			$(".loader").hide();
			console.log("response-->", response);
			if (!response.ok) {
				toastr.error('Something went wrong.');
				return;
			}

			else if (response.ok) {
				toastr.success('Tender publish successfully.');
				fetchAndUpdateGrid();
				//cancelTender();
				//openReviewModal('previewPdf');
				return response.blob();
			}

		})
		.then(blob => {
			$(".loader").hide();
			var blobUrl = URL.createObjectURL(blob);
			window.open(blobUrl, '_blank');  // Open in new tab
		})
		.catch(error => console.error("Error downloading PDF:", error));



}
*/

function generateTenderSectionsHtml() {
	const gridApi = templateSectionOptions.api; // Assuming this is your AG Grid instance
	const rowData = [];

	gridApi.forEachNode((node) => {
		rowData.push(node.data);
	});

	let sectionsHtml = '';

	rowData.forEach(row => {
		// Skip the row if the name is "Criteria"
		if (row.name && row.name.trim().toLowerCase() === "criteria") {
			return;
		}

		const sectionName = row.name || '';
		const rawDescription = row.data || '';

		// Convert HTML to plain text
		const tempDiv = document.createElement('div');
		tempDiv.innerHTML = rawDescription;
		const cleanedDescription = tempDiv.textContent.trim();

		sectionsHtml += `
			<div class="section">
				<table>
					<tr><th>Section Name</th><td>${sectionName}</td></tr>
					<tr><th>Description</th><td>${cleanedDescription}</td></tr>
				</table>
			</div>
		`;
	});

	return sectionsHtml;
}



function formatDateTime(datetimeStr) {
	if (!datetimeStr) return '';

	const [datePart, timePart] = datetimeStr.split('T');
	const [year, month, day] = datePart.split('-');

	return `${day}-${month}-${year} ${timePart}`;
}

function openReviewModal(pathValue) {

	var tenderId = $("#headertenderid").text();
	var tenderHeaderText = "";
	$(".loader").show();
	openReviewData();
	var content = document.querySelector("#reviewModal .modal-body");
	console.log("content-->", content.innerHTML);
	var firstTitle = $("h3.section-title1").first().text();

	if (!content) {
		console.error("Error: Content not found!");
		return;
	}


	let tenderName = $("#templateName").val();
	let effectiveDateRaw = $("#effectiveDate").val();
	let createdDateRaw = $("#date").val();

	let effectiveDate = formatDateTime(effectiveDateRaw);
	let createdDate = formatDateTime(createdDateRaw);

	let tenderDescription = CKEDITOR.instances.tenderEditor.getData();
	console.log()
	var sectionDetails = generateTenderSectionsHtml();

	var htmlContent = `
	<html lang="en">
	<head>
	  <meta charset="UTF-8">
	  <title>Tender Details PDF</title>
	  <style>
	    body {
	      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	      padding: 20px;
	      color: #2c3e50;
	    }

	    #pdfContent {
	      background: #fff;
	      box-shadow: 0 0 10px rgba(0,0,0,0.1);
	      margin: auto;
	    }

	    h1 {
	      text-align: center;
	      color: #34495e;
	      font-size: 26px;
	    }

	    .section {
	      margin-bottom: 25px;
	    }

	    .section-title {
	      font-size: 18px;
	      border-left: 4px solid #3498db;
	      padding-left: 10px;
	      margin-bottom: 15px;
	      color: #2c3e50;
	    }

	    table {
	      width: 100%;
	      border-collapse: collapse;
	      font-size: 14px;
	    }

	    th, td {
	      border: 1px solid #ddd;
	      padding: 10px;
	      vertical-align: top;
	    }

	    th {
	      background-color: #ecf0f1;
	      color: #2c3e50;
	      font-weight: 600;
	      text-align: center;
	    }

	    td {
	      background-color: #ffffff;
	      border: 1px solid #ddd !important;
	    }

	    .criteria-total {
	      font-weight: bold;
	      background-color: #f8f9fa;
	      text-align: right;
	    }

	    #dynamic_field thead th {
	      background-color: #ecf0f1 !important;
	      color: #2c3e50 !important;
	      font-weight: 600;
	      text-align: center;
	      border: 1px solid #ddd !important;
	    }
	  </style>
	</head>
	<body>
	  <div id="pdfContent">
	    <h1>Tender Details</h1>

	    <div class="section">
	      <div class="section-title">Tender Information</div>
	      <table>
	        <tr><th>Tender Name</th><td>${tenderName}</td></tr>
	        <tr><th>Template Effective Date</th><td>${effectiveDate}</td></tr>
	        <tr><th>Template Created Date</th><td>${createdDate}</td></tr>
	        <tr><th>Description</th><td>${tenderDescription}</td></tr>
	      </table>
	    </div>

	    <div class="section">
	      <div class="section-title">Tender Section Details</div>
	      ${sectionDetails}
	    </div>

	    <div class="section">
	      <div class="section-title">Criteria Details</div>
	      ${content.innerHTML}
	    </div>
	  </div>
	</body>
	</html>
	`;


	fetch(`tender-management-contentpdf?pathvalue=${pathValue}&tenderid=${tenderId}`, {
		method: "POST",
		headers: { "Content-Type": "text/plain" },
		body: htmlContent
	})
		.then(response => {
			$(".loader").hide();
			console.log("response-->", response);
			if (!response.ok) {
				toastr.error('Something went wrong.');
				return;
			}

			else if (response.ok) {
				fetchAndUpdateGrid();
				return response.blob();
			}

		})
		.then(blob => {
			$(".loader").hide();
			var blobUrl = URL.createObjectURL(blob);
			window.open(blobUrl, '_blank');  // Open in new tab
		})
		.catch(error => console.error("Error downloading PDF:", error));





}


function downloadPdf(pathValue) {
	var tenderId = $("#headertenderid").text();
	openReviewData();
	var content = document.querySelector("#reviewModal .modal-body");
	console.log("content-->", content.innerHTML);
	var firstTitle = $("h3.section-title1").first().text();
	console.log(firstTitle);

	if (!content) {
		console.error("Error: Content not found!");
		return;
	}

	
	let tenderName = $("#templateName").val();
		let effectiveDateRaw = $("#effectiveDate").val();
		let createdDateRaw = $("#date").val();

		let effectiveDate = formatDateTime(effectiveDateRaw);
		let createdDate = formatDateTime(createdDateRaw);

		let tenderDescription = CKEDITOR.instances.tenderEditor.getData();
		console.log()
		var sectionDetails = generateTenderSectionsHtml();

		var htmlContent = `
		<html lang="en">
		<head>
		  <meta charset="UTF-8">
		  <title>Tender Details PDF</title>
		  <style>
		    body {
		      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
		      padding: 20px;
		      color: #2c3e50;
		    }

		    #pdfContent {
		      background: #fff;
		      box-shadow: 0 0 10px rgba(0,0,0,0.1);
		      margin: auto;
		    }

		    h1 {
		      text-align: center;
		      color: #34495e;
		      font-size: 26px;
		    }

		    .section {
		      margin-bottom: 25px;
		    }

		    .section-title {
		      font-size: 18px;
		      border-left: 4px solid #3498db;
		      padding-left: 10px;
		      margin-bottom: 15px;
		      color: #2c3e50;
		    }

		    table {
		      width: 100%;
		      border-collapse: collapse;
		      font-size: 14px;
		    }

		    th, td {
		      border: 1px solid #ddd;
		      padding: 10px;
		      vertical-align: top;
		    }

		    th {
		      background-color: #ecf0f1;
		      color: #2c3e50;
		      font-weight: 600;
		      text-align: center;
		    }

		    td {
		      background-color: #ffffff;
		      border: 1px solid #ddd !important;
		    }

		    .criteria-total {
		      font-weight: bold;
		      background-color: #f8f9fa;
		      text-align: right;
		    }

		    #dynamic_field thead th {
		      background-color: #ecf0f1 !important;
		      color: #2c3e50 !important;
		      font-weight: 600;
		      text-align: center;
		      border: 1px solid #ddd !important;
		    }
		  </style>
		</head>
		<body>
		  <div id="pdfContent">
		    <h1>Tender Details</h1>

		    <div class="section">
		      <div class="section-title">Tender Information</div>
		      <table>
		        <tr><th>Tender Name</th><td>${tenderName}</td></tr>
		        <tr><th>Template Effective Date</th><td>${effectiveDate}</td></tr>
		        <tr><th>Template Created Date</th><td>${createdDate}</td></tr>
		        <tr><th>Description</th><td>${tenderDescription}</td></tr>
		      </table>
		    </div>

		    <div class="section">
		      <div class="section-title">Tender Section Details</div>
		      ${sectionDetails}
		    </div>

		    <div class="section">
		      <div class="section-title">Criteria Details</div>
		      ${content.innerHTML}
		    </div>
		  </div>
		</body>
		</html>
		`;
	
	fetch(`tender-management-contentpdf?pathvalue=${pathValue}&tenderid=${tenderId}`, {
		method: "POST",
		headers: { "Content-Type": "text/plain" },
		body: htmlContent
	})
		.then(response => response.blob())
		.then(blob => {
			var a = document.createElement("a");
			a.href = URL.createObjectURL(blob);
			a.download = `Tender_${firstTitle}.pdf`;
			document.body.appendChild(a);
			a.click();
			document.body.removeChild(a);
		})
		.catch(error => console.error("Error downloading PDF:", error));
}






function addTender() {

	clearValidationMessage('templateNameError');
	clearValidationMessage('effectiveDateError');
	templateSectionMainOptions.api.deselectAll();

	$("#templateName").val("");
	$("#effectiveDate").val("");
	$("#sectionContainer").empty();
	$("#tenderModal").modal("show");
	var now = new Date();
	var year = now.getFullYear();
	var month = String(now.getMonth() + 1).padStart(2, '0');
	var day = String(now.getDate()).padStart(2, '0');
	var hours = String(now.getHours()).padStart(2, '0');
	var minutes = String(now.getMinutes()).padStart(2, '0');
	var formattedDateTime = year + '-' + month + '-' + day + 'T' + hours + ':' + minutes;
	$('#date').val(formattedDateTime);
	$('#date').attr('readonly', true);
	$('#tenderModal').on('shown.bs.modal', function() {
		initializeCKEditorDesc();
	});
	$("#tenderSave-btn").show();
	$("#next-btn-1").hide();
	$("#publishTender").hide();
	$("#voucherclassTab").hide();
	CKEDITOR.instances['tenderEditor'].setData('');
	$("#sectionEditView").html('');

	$("#addMuliSection").show();
}

function initializeCKEditorDesc() {
	if (CKEDITOR.instances['tenderEditor']) {
		CKEDITOR.instances['tenderEditor'].destroy(true);
	}
	CKEDITOR.replace('tenderEditor');
}

$(document).ready(function() {
	$("#addMuliSection").click(function() {
		$("#sectionContainer").append(createSection());
	});

	$("body").on("click", ".delete-section", function() {
		$(this).closest(".section").remove();
		updateSectionNumbers(); // Update section numbers after removal
	});

	$("body").on("click", ".save-section", function() {
		const sectionElement = $(this).closest(".section");
		const sectionName = sectionElement.find(".section-name").val();
		const sectionDescription = sectionElement.find(".section-description").val();

		if (sectionName.trim() !== "" && sectionDescription.trim() !== "") {
			const sectionObject = {
				name: sectionName,
				description: sectionDescription,
			};

			savedSections.push(sectionObject);

			/*const savedSectionHtml = `<div class="card mb-3 saved-section">
								<div class="card-header d-flex justify-content-between align-items-center">
									<h4 class="section-name"><strong>${sectionName}</strong></h4>
									<div>
										<button class="btn btn-primary btn-sm edit-saved-section" type="button">Edit</button>
										<i class="bi bi-pencil-square edit-saved-section"></i>
										<button class="btn btn-danger btn-sm delete-saved-section" type="button">Delete</button>
										<i class="fa fa-trash delete-saved-section"></i>
									</div>
								</div>
								<div class="card-body">
									<span class="section-description">${sectionDescription}</span>
								</div>
							  </div>`;*/

			const savedSectionHtml = `<div class="card mb-3 saved-section">
							                                  <div class="card-header d-flex justify-content-between align-items-center">
							                                      <h4 class="section-name"><strong>${sectionName}</strong></h4>
							                                      <div>
							  										<i class="bi bi-pencil-square edit-saved-section"></i>
							  										<i class="fa fa-trash delete-saved-section"></i>
							                                      </div>
							                                  </div>
							                                  <div class="card-body">
							                                      <span class="section-description">${sectionDescription}</span>
							                                  </div>
							                                </div>`;
			$("#sectionContainer").append(savedSectionHtml);
			sectionElement.remove();
		} else {
			swal("Please enter both section name and description!", " ", "error");
		}
	});

	$("body").on("click", ".edit-saved-section", function() {
		const savedSection = $(this).closest(".saved-section");
		const sectionName = savedSection.find("strong").text();
		const sectionDescription = savedSection.find("p").text();

		savedSection.replaceWith(createSection(sectionName, sectionDescription));
		updateSectionNumbers();
	});

	$("body").on("click", ".delete-saved-section", function() {
		$(this).closest(".saved-section").remove();
		updateSectionNumbers(); // Update section numbers after deletion
	});


	// Criteria Add More Sections
	var rowId = 1;
	/*$(document).on('click', '#add', function() {
		rowId++;
		$('#dynamic_field tbody').append(`
			<tr id="row${rowId}">
			  <td><h4>${rowId}</h4></td>
			  <td><textarea name="criteria[]" class="ckeditorCriteria" id="ckeditorCriteria1_${rowId}""></textarea></td>
			  <td><textarea name="required_document[]" class="ckeditorCriteria" id="ckeditorCriteria2_${rowId}"></textarea></td>
			  <td class="dynamic_td">
				<button type="button" class="btn btn-success save-btn-criteria" data-id="${rowId}">Save</button>
				<button type="button" class="btn btn-warning edit-btn-criteria d-none" data-id="${rowId}">Edit</button>
				<button type="button" class="btn btn-danger delete-btn-criteria" data-id="${rowId}">Delete</button>
			  </td>
			</tr>
		  `);
		resetRowIds();
		//setTimeout(initializeCKEditor(), 5000);
		CKEDITOR.replace(`ckeditorCriteria1_${rowId}`);
		CKEDITOR.replace(`ckeditorCriteria2_${rowId}`);
		//initializeCKEditor();

	});*/


	$(document).on('click', '.add-more-btn', function() {
		const $table = $(this).closest('td').find('.dynamic-table tbody');
		const rowCount = $table.find('tr').length + 1;

		const rowHtml = `
	        <tr id="row${rowCount}">
	            <td><h4>${rowCount}</h4></td>
	            <td><textarea name="criteria[]" class="ckeditorCriteria" id="ckeditorCriteria1_${rowCount}"></textarea></td>
	            <td><textarea name="required_document[]" class="ckeditorCriteria" id="ckeditorCriteria2_${rowCount}"></textarea></td>
	            <td>
	                <button type="button" class="btn btn-success save-btn-criteria" data-id="${rowCount}">Save</button>
	                <button type="button" class="btn btn-warning edit-btn-criteria d-none" data-id="${rowCount}">Edit</button>
	                <button type="button" class="btn btn-danger delete-btn-criteria" data-id="${rowCount}">Delete</button>
	            </td>
	        </tr>`;

		$table.append(rowHtml);

		// Initialize CKEditor for new fields
		CKEDITOR.replace(`ckeditorCriteria1_${rowCount}`);
		CKEDITOR.replace(`ckeditorCriteria2_${rowCount}`);
	});


	// Save the row data
	$(document).on('click', '.save-btn-criteria', function() {
		var row = $(this).closest('tr');
		let count = $(this).data("id");
		var criteriaId = row.find('textarea[name="criteria[]"]').attr('id');
		var requiredDocumentId = row.find('textarea[name="required_document[]"]').attr('id');
		var criteriaEditor = CKEDITOR.instances['ckeditorCriteria1_' + count].getData();
		var requiredDocumentEditor = CKEDITOR.instances['ckeditorCriteria2_' + count].getData();

		if (criteriaEditor && requiredDocumentEditor) {
			var criteria = criteriaEditor;
			var requiredDocument = requiredDocumentEditor;

			// Replace text areas with plain text
			row.find('td:eq(1)').html('<span>' + criteria + '</span>');
			row.find('td:eq(2)').html('<span>' + requiredDocument + '</span>');

			// Show Edit button, hide Save button
			$(this).addClass('d-none');
			row.find('.edit-btn-criteria').removeClass('d-none');
		} else {
			console.error('CKEditor instance not found for:', criteriaId, requiredDocumentId);
		}
	});

	// Edit the row data
	$(document).on('click', '.edit-btn-criteria', function() {
		var row = $(this).closest('tr');
		var criteria = $(this).closest('tr').find('td:eq(1)').html();
		var requiredDocument = $(this).closest('tr').find('td:eq(2)').html();
		let count = $(this).data("id");


		row.find('td:eq(1)').html(`<textarea id="ckeditorCriteria1_${count}" rows="10" cols="80" class="ckeditorCriteria"></textarea>`);
		row.find('td:eq(2)').html(`<textarea id="ckeditorCriteria2_${count}" rows="10" cols="80" class="ckeditorCriteria"></textarea>`);

		$(`#ckeditorCriteria1_${count}`).val(criteria);
		$(`#ckeditorCriteria2_${count}`).val(requiredDocument);

		$(this).addClass('d-none');
		row.find('.save-btn-criteria').removeClass('d-none');
		setTimeout(initializeCKEditor(), 5000);
	});



	// Delete the row and reset row IDs
	$(document).on('click', '.delete-btn-criteria', function() {
		$(this).closest('tr').remove();
		resetRowIds();
	});

	// Reset row IDs after adding/deleting rows
	function resetRowIds() {
		$('#dynamic_field tbody tr').each(function(index) {
			$(this).find('td:first h4').text(index + 1);
		});
		rowId = $('#dynamic_field tbody tr').length;
	}

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});

	$(document).on("click", ".remove-btn", function() {

		let itemToRemove = $(this).data("item");
		selectedSteps = selectedSteps.filter(item => item !== itemToRemove);

		$(this).parent().remove();
		$("#timelineTable tbody").find(`tr[data-item='${itemToRemove}']`).remove();

		updateDropdown();
	});




	$(document).on("click", ".team_evalution_btn", function() {
		var selectedSteps = ["Technical Evaluation", "Criteria Evaluation"]; // Predefined evaluation types
		evalutionSelection('evalution_text'); // Update UI with preselected values
	});

});

let sectionCounter = 0;
let savedSections = [];

/*function createSection(name = "", description = "") {
	sectionCounter++;
	return `
<div class="card mb-3 section" style="height:205px">
	<div class="card-header d-flex justify-content-between align-items-center">
		<input type="text" class="form-control w-50 section-name" placeholder="Enter Section Name" value="${name}">
		<div class="operations-Buttons">
		<button class="btn btn-danger btn-sm delete-section operation-btn-active" type="button">Delete Section</button>
		<i class="fa fa-trash delete-section"></i>
		<i class="fa-solid fa-floppy-disk save-section "></i>
		<button class="btn btn-primary save-section operation-btn-active" type="button">Save Section</button>
		</div>
	</div>
	<div class="card-body">
		<textarea class="form-control section-description" placeholder="Enter Section Description">${description}</textarea>
	</div>
</div>`;
}*/


function createSection(name = "", description = "") {
	sectionCounter++;
	return `
<div class="card mb-3 section" style="height:205px">
    <div class="card-header d-flex justify-content-between align-items-center">
        <input type="text" class="form-control w-50 section-name" placeholder="Enter Section Name" value="${name}">
		<div class="operations-Buttons">
		<i class="fa fa-trash delete-section"></i>
		<i class="fa-solid fa-floppy-disk save-section "></i>
		</div>
    </div>
    <div class="card-body">
        <textarea class="form-control section-description" placeholder="Enter Section Description">${description}</textarea>
    </div>
</div>`;
}


function editSection(name = "", description = "") {
	sectionCounter++;
	return `
	<div class="card mb-3 section" style="height:205px">
	    <div class="card-header d-flex justify-content-between align-items-center">
	        <input type="text" class="form-control w-50 section-name" placeholder="Enter Section Name" value="${name}">
	    </div>
	    <div class="card-body">
	        <textarea class="form-control section-description" placeholder="Enter Section Description">${description}</textarea>
	    </div>
	</div>`;
}

function loadSections(data) {
	let container = document.getElementById("sectionEditView");
	container.innerHTML = "";
	$("#addMuliSection").hide();
	let heading = document.createElement("h2");
	heading.classList.add("tender-heading");
	heading.innerText = "Tender Section Details";
	container.appendChild(heading);

	data.forEach((section, index) => {
		if (index < data.length - 1) { // Exclude the last item
			let sectionElement = document.createElement("div");
			sectionElement.classList.add("section-card");

			sectionElement.innerHTML = `
                <div class="section-header">
                    <div class="section-number">${index + 1}</div>
                    <h3 class="section-title">${section.name}</h3>
                </div>
                <p class="section-description">${section.description}</p>
            `;

			container.appendChild(sectionElement);
		}
	});

}


function updateSectionNumbers() {
	$(".section-number").each(function(index) {
		$(this).text(index + 1);
	});
}

const prefix = "OMC/2024/TD/";

function generateDynamicId(callback) {
	/*$.ajax({
		url: 'http://localhost:3000/tenders',
		type: 'GET',
		success: function (response) {*/
	const existingIds = mainData.tenders.map(item => item.templateId);
	const highestNumber = existingIds
		.map(id => parseInt(id.replace(prefix, ''), 10))
		.reduce((max, num) => num > max ? num : max, 0);

	const newIdNumber = highestNumber + 1;
	const paddedNumber = String(newIdNumber).padStart(3, '0');
	const newId = prefix + paddedNumber;

	callback(newId);
	/*},
	error: function (error) {
		console.error("Error fetching existing IDs", error);
	}
});*/
}

function stripHtmlTags(html) {
	return html
		.replace(/&/g, "&amp;")
		.replace(/</g, "&lt;")
		.replace(/>/g, "&gt;")
		.replace(/"/g, "&quot;")
		.replace(/'/g, "&#039;")
	//.replace(/\n/g, "<br>");
}

function clearValidationMessage(errorElementId) {
	document.getElementById(errorElementId).textContent = "";
}

function saveTenderData() {
	const tenderDescription = CKEDITOR.instances.tenderEditor.getData();
	var content = tenderDescription.replace(/<(strong|em|ol|ul|li|p|br)[^>]*>(.*?)<\/\1>/g, "$2");
	content = content.replace(/<\/?(ul|ol|li|p|em|strong|br)[^>]*>/g, "");
	const tenderDescriptionPlain = content;
	const allSections = $(".saved-section").map(function() {
		return {
			name: $(this).find(".section-name strong").text(),
			description: $(this).find(".section-description").text()
		};
	}).get();

	const templateName = $("#templateName").val();
	const effectiveDate = $("#effectiveDate").val();
	const createdDate = $("#date").val();
	const status = $("#status").val();

	// Format the datetime-local value to remove 'T'
	const formattedEffectiveDate = effectiveDate.split("T").join(" ");
	const formattedCreatedDate = createdDate.split("T").join(" ");

	const criteriaData = [{
		name: "Criteria",
		description: "This criteria section is system-generated(Optional)"
	}];

	const data = {
		templateId: "",
		templateName: templateName,
		effectiveDate: formattedEffectiveDate,
		creationDate: formattedCreatedDate,
		status: status,
		templateDescription: tenderDescriptionPlain,
		sections: allSections,
		criteria: criteriaData
	};



	/* FORM VALIDATION STARTS*/
	var validation = true;


	if (data.templateName == null || data.templateName == "") {
		//validation = validationUpdated("Template Name Required", "templateName");
		toastr.error("Template Name Required")
		return;
	}

	if (data.effectiveDate == null || data.effectiveDate == "") {
		//validation = validationUpdated("Effective Date Required", "effectiveDate");
		toastr.error("Effective Date Required")
		return;
	}

	if (data.sections == null || data.sections == "") {
		$("body").removeClass("overlay");
		/*$("#messageParagraph").text("Atleast One Template Section Required");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');*/
		toastr.error("Atleast One Template Section Required");
		return;
	}


	function validationUpdated(message, fieldId) {
		document.getElementById(fieldId + "Error").textContent = message;

	}




	if (validation) {
		$(".loader").show();

		$.ajax({
			type: "POST",
			url: "save-tender-data",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response.code == "Success") {
					/*	$('.loader').hide();
						$("body").removeClass("overlay");
						$("#messageParagraph").text(response.message);
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						$('#tenderModal').modal("hide");*/

					//showSnackbar(response.message);
					toastr.success(response.message);
					$("#tenderSave-btn").show();
					$("#next-btn-1").show();
					$("#publishTender").show();
					$("#voucherclassTab").show();
					$(".loader").hide();

				} else {
					/*$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');*/

					//showSnackbar(response.message);
					toastr.success(response.message);
					$(".loader").hide();
				}
				fetchAndUpdateGrid();
			},
			error: function(datas) {
				console.log(datas);
			}
		});


	}
}


function showSnackbar(message) {
	const snackbar = document.getElementById("snackbar");
	snackbar.textContent = message;
	snackbar.className = "snackbar show";
	setTimeout(() => {
		snackbar.className = snackbar.className.replace("show", "");
	}, 3000);
}

function fetchAndUpdateGrid() {

	var fromDate = '';
	var toDate = '';
	agGrid.simpleHttpRequest({
		url: `tender-data?fromDate=${fromDate}&toDate=${toDate}`
	}).then(function(response) {
		if (response.code === "Success") {
			const parsedResponse = JSON.parse(response.body);
			//window.templateSectionMainApi.setRowData(parsedResponse);
			var rowData = [];
			window.templateSectionMainApi.setRowData(rowData);
			window.templateSectionMainApi.setRowData(parsedResponse);
			mainGridData = parsedResponse;

			if (parsedResponse && parsedResponse.length > 0) {
				window.templateSectionMainApi.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true); // Select the first row
					}
				});
			}
		}
	});

}

function saveStatus() {
	const allTemplateRows = window.templateDetailsApi.getModel().rowsToDisplay;

	if (!allTemplateRows || allTemplateRows.length === 0) {
		toastr.error('No rows found in the template details grid');
		return;
	}

	const selectedTemplate = allTemplateRows[0].data;
	const templateId = selectedTemplate.templateId;
	const allSections = window.templateSectionApi.getModel().rowsToDisplay;
	const updatedSections = allSections.map(node => node.data);

	//template.sections = updatedSections;
	const payload = { sections: updatedSections };
	$(".loader").show();

	$.ajax({
		type: "POST",
		url: "update-tender-data",
		contentType: "application/json",
		data: JSON.stringify(payload),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				/*$("body").removeClass("overlay");
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');*/
				//	showSnackbar(response.message);
				toastr.success(response.message);
			} else {
				$(".loader").hide();
				/*$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');*/
				//	showSnackbar(response.message);
				toastr.success(response.message);
			}
		},
		error: function(datas) {
			console.log(datas);
		}
	});

}



function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}


// Criteria Add More Sections
$(document).ready(function() {
	var rowId = 1;
	$(document).on('click', '#add', function() {
		rowId++;
		$('#dynamic_field tbody').append(`
	        <tr id="row${rowId}">
	          <td><h4>${rowId}</h4></td>
	          <td><textarea name="criteria[]" class="ckeditorCriteria" id="ckeditorCriteria1_${rowId}""></textarea></td>
	          <td><textarea name="required_document[]" class="ckeditorCriteria" id="ckeditorCriteria2_${rowId}"></textarea></td>
	          <td><input name="score[]" type="number" class="form-control score-input square-input" id="score_${rowId}" min="0" max="100" step="1" placeholder="Enter score"></td>

	          <td class="dynamic_td">
	            <button type="button" class="btn btn-success save-btn-criteria" data-id="${rowId}">Save</button>
	            <button type="button" class="btn btn-warning edit-btn-criteria d-none" data-id="${rowId}">Edit</button>
	            <button type="button" class="btn btn-danger delete-btn-criteria" data-id="${rowId}">Delete</button>
	          </td>
	        </tr>
	      `);
		resetRowIds();
		CKEDITOR.replace(`ckeditorCriteria1_${rowId}`);
		CKEDITOR.replace(`ckeditorCriteria2_${rowId}`);

	});

	// Save the row data
	$(document).on('click', '.save-btn-criteria', function() {
		var row = $(this).closest('tr');
		let count = $(this).data("id");
		var criteriaId = row.find('textarea[name="criteria[]"]').attr('id');
		var requiredDocumentId = row.find('textarea[name="required_document[]"]').attr('id');
		var criteriaEditor = CKEDITOR.instances['ckeditorCriteria1_' + count].getData();
		var requiredDocumentEditor = CKEDITOR.instances['ckeditorCriteria2_' + count].getData();
		var score = row.find(`#score_${count}`).val();

		if (criteriaEditor && requiredDocumentEditor) {
			var criteria = criteriaEditor;
			var requiredDocument = requiredDocumentEditor;

			// Replace text areas with plain text
			row.find('td:eq(1)').html(`<span id="criteria_${count}">${criteria}</span>`);
			row.find('td:eq(2)').html(`<span id="criteriaDescription_${count}">${requiredDocument}</span>`);
			row.find('td:eq(3)').html(`<span id="score_${count}">${score}</span>`);

			// Show Edit button, hide Save button
			$(this).addClass('d-none');
			row.find('.edit-btn-criteria').removeClass('d-none');
		} else {
			console.error('CKEditor instance not found for:', criteriaId, requiredDocumentId);
		}
	});

	// Edit the row data
	$(document).on('click', '.edit-btn-criteria', function() {
		var row = $(this).closest('tr');
		var criteria = $(this).closest('tr').find('td:eq(1)').html();
		var requiredDocument = $(this).closest('tr').find('td:eq(2)').html();
		var score = row.find('td:eq(3)').text().trim();
		let count = $(this).data("id");

		row.find('td:eq(1)').html(`<textarea id="ckeditorCriteria1_${count}" rows="10" cols="80" class="ckeditorCriteria"></textarea>`);
		row.find('td:eq(2)').html(`<textarea id="ckeditorCriteria2_${count}" rows="10" cols="80" class="ckeditorCriteria"></textarea>`);
		row.find('td:eq(3)').html(`<input type="number" class="form-control score-input square-input" id="score_${count}" min="0" max="100" step="1" value="${score}">`);

		$(`#ckeditorCriteria1_${count}`).val(criteria);
		$(`#ckeditorCriteria2_${count}`).val(requiredDocument);

		$(this).addClass('d-none');
		row.find('.save-btn-criteria').removeClass('d-none');
		setTimeout(initializeCKEditor(), 5000);
	});



	// Delete the row and reset row IDs
	$(document).on('click', '.delete-btn-criteria', function() {
		$(this).closest('tr').remove();
		resetRowIds();
	});

	// Reset row IDs after adding/deleting rows
	function resetRowIds() {
		$('#dynamic_field tbody tr').each(function(index) {
			$(this).find('td:first h4').text(index + 1);
		});
		rowId = $('#dynamic_field tbody tr').length;
	}

});

let isClicked = false;
function publishTender() {
	//$('#confirmationModal').modal('show');
	//$(".tender-content").hide();

	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you really want to publish the tender? This process cannot be undone.',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, Publish',
		cancelButtonText: 'Cancel',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			openReviewModal('publishPdf');
			//$(".tender-content").show();
		}
	});

}

function closePublishModel() {
	//$('#confirmationModal').modal('hide');
	$(".tender-content").show();
	$(".confirmPublish-dialog").hide();
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

function cancelTemplateSection() {
	$("#templateSectionGrid").show();
	$("#templateDetailsGrid").show();
	$(".templateSection-content").hide();
	$("#saveStatus").show();
}

function ApplyTenderData(row) {

	var templateName = row.templateName;
	var effectiveDate = row.effectiveDate;
	var desc = row.templateDescription;
	var creationDate = row.creationDate;

	$("#templateName").val(templateName);
	$("#effectiveDate").val(effectiveDate);
	$("#date").val(creationDate);
	CKEDITOR.instances['tenderEditor'].setData(desc);

}


function resetBtn() {
	$("#quickFilter").val('');

	templateSectionMainOptions.api.setQuickFilter('');

	templateSectionMainOptions.api.setFilterModel(null);

	templateSectionMainOptions.api.setRowData(mainGridData);

	templateSectionMainOptions.api.refreshCells({ force: true });

}


function onQuickFilterChanged() {
	templateSectionMainOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}

function cancelButton() {
	var firstRowNode = templateSectionMainOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}

var selectedSteps = [];
let evalCount = 0;
let firstCall = true; // Flag to track first call

function evalutionSelection(element) {
	let selectedValue = $(element).val();

	// If first call, add default evaluations first
	if (firstCall) {
		firstCall = false; // Set flag to false after first call
		["Technical Evaluation", "Financial Evaluation"].forEach(step => {
			if (!selectedSteps.includes(step)) {
				selectedSteps.push(step);
				addEvaluation(step);
			}
		});
	}

	// Add the selected value if it's new
	if (selectedValue && !selectedSteps.includes(selectedValue)) {
		selectedSteps.push(selectedValue);
		addEvaluation(selectedValue);
		$("#timelineContainer").show();
	}
}

function addEvaluation(evalType) {
	evalCount++;
	let tableBody = document.getElementById("evalTableBody");

	let row = document.createElement("tr");
	row.innerHTML = `
        <td>${evalType}</td>
        <td>
            <input type="date" class="deadline_date" id="assign_User_deadlineDate_${evalCount}">
        </td>
        <td>
            <div class="multi-select">
                <div class="selected-users" id="selected_assignUser_${evalCount}">
                    <i class="bi bi-person-plus-fill assign_user" onclick="assignUserBtn(${evalCount})"></i>
                </div>
                <div class="user-dropdown" id="user-dropdown_${evalCount}" style="display: none;">
                    <input type="text" id="debitAccountSubGroup_${evalCount}"
                        class="form-control debitAccountSubGroupCls"
                        onkeyup="debitSubGroup(this.id,${evalCount});"
                        autocomplete="off" placeholder="Search User...">
                </div>
                <div id="suggesstion-box1_${evalCount}" class="userSearchingBox"></div>
            </div>
        </td>
        <td>
            <select>
                <option value="Pending">Pending</option>
                <option value="Completed">Completed</option>
            </select>
        </td>
        <td>
            ${evalCount > 2 ? `
                <i class="bi bi-plus-circle-fill add_kra_row_btn" onclick="addKraRow(${evalCount})" data-bs-toggle="tooltip" title="Add KRA Row"></i>
                <i class="bi bi-view-list view_kra_table_btn" onclick="toggleKraTable(${evalCount})" data-bs-toggle="tooltip" title="View KRA Table"></i>
            ` : ''}
        </td>
    `;

	let kraRow = document.createElement("tr");
	kraRow.id = "kra-row-" + evalCount;
	kraRow.className = "kra-table";
	kraRow.innerHTML = `
        <td colspan="5">
            <table>
                <thead>
                    <tr>
                        <th>KRA Sl No</th>
                        <th>KRA Name</th>
                        <th>KRA Description</th>
                        <th>KRA Weightage</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="kra-body-${evalCount}"></tbody>
            </table>
        </td>
    `;

	tableBody.appendChild(row);
	tableBody.appendChild(kraRow);
}

function toggleKraTable(rowId) {
	let kraBody = document.getElementById("kra-body-" + rowId);
	let kraRow = document.getElementById("kra-row-" + rowId);
	if (kraBody.children.length === 0) {
		toastr.error('No KRA rows available. Please add at least one KRA row before viewing.');
		return; // Stop execution
	}

	kraRow.style.display = kraRow.style.display === "none" || kraRow.style.display === "" ? "table-row" : "none";
}


function addKraRow(rowId) {
	let kraBody = document.getElementById("kra-body-" + rowId);
	let kraRows = kraBody.getElementsByTagName("tr");

	// Check if there is at least one existing row
	if (kraRows.length > 0) {
		let lastKraRow = kraRows[kraRows.length - 1];
		let inputs = lastKraRow.getElementsByTagName("input");

		// Check if all inputs in the last row are filled
		for (let input of inputs) {
			if (input.value.trim() === "") {
				toastr.error("Please fill all fields in the previous KRA row before adding a new one.");
				return; // Stop function execution
			}
		}
	}

	// Proceed to add a new row if validation passes
	let kraCount = kraRows.length + 1;
	let kraRow = document.createElement("tr");
	kraRow.innerHTML = `
        <td>${kraCount}</td>
        <td><input type="text" class="form-control" placeholder="KRA Name"></td>
        <td><input type="text" class="form-control" placeholder="KRA Description"></td>
        <td><input type="text" class="form-control" placeholder="KRA Weightage"></td>
        <td><i class="fa-solid fa-trash kra_remove_btn" onclick="removeKraRow(this)"></i></td>
    `;
	kraBody.appendChild(kraRow);

	// Show the container only if it is hidden
	let krRowsId = document.getElementById("kra-row-" + rowId);
	if (krRowsId.style.display === "none" || krRowsId.style.display === "") {
		krRowsId.style.display = "table-row";
	}
}



function removeKraRow(button) {
	let kraBody = button.closest("tbody");
	let kraRow = button.closest("tr");

	kraRow.remove();

	if (kraBody.children.length === 0) {
		let kraContainer = kraBody.closest("tr.kra-table");
		if (kraContainer) {
			kraContainer.style.display = "none";
		}
	}
}


function assignUserBtn(index) {
	$(`#user-dropdown_${index}`).show();
}


function debitSubGroup(id, index) {
	var l = id.split("_");
	var counter = index;
	var search = $("#" + id).val();
	$.ajax({
		type: "POST",
		url: "tender-management-assignuserlist",
		dataType: "json",
		contentType: "application/json",
		data: $("#" + id).val(),
		success: function(response) {
			console.log(response);

			if (response.code === "Success") {
				let responseBody = JSON.parse(response.body);
				let content = '<ul id="autocomplete-list">';

				if (responseBody.length !== 0) {
					$("#debitAccountSubGroup_0").css("background", "#FFF");

					for (let i = 0; i < responseBody.length; i++) {
						content += `
	                        <li class="autocompletedata cp"
	                        onclick="autocompleteValue1('${responseBody[i].empId}', '${responseBody[i].employeeName}' , '${index}')">
	                        ${responseBody[i].employeeName}
	                        </li>`;
					}
				} else {
					content += `
	                    <li  class="autocompletedata cp">
	                    No Data Found
	                    </li>`;
				}

				content += "</ul>";
				$("#suggesstion-box1_" + counter).show().html(content);
			}
		},
		error: function(data) {
			console.error("Error:", data);
		}
	});


	if (search.length == 0) {
		$("#suggesstion-box1_" + counter).hide();
	}

}



function autocompleteValue1(empId, empName, index) {
	let itemsContainer = $(`#selected_assignUser_${index}`);
	itemsContainer.find(".assign_user").before(
		`<span class="selected-assign-item" id="${empId}">${empName} 
            <i class="fas fa-times remove-selecteduser-btn" data-index="${index}" onclick="removeAssignUser('${empId}', ${index})"></i>
        </span>`
	);
	$("#suggesstion-box1_" + index).hide();
	$(`#debitAccountSubGroup_${index}`).text('');
	$(`#user-dropdown_${index}`).hide();
}


function removeAssignUser(id, index) {
	let userElement = $(`#selected_assignUser_${index}`).find(`#${id}`);
	if (userElement.length) {
		userElement.remove();
	}
}


function extractTableData() {
	let evaluations = [];
	$("#evalTableBody tr").each(function() {
		let evalType = $(this).find("td:eq(0)").text().trim();
		let deadlineDate = $(this).find("td:eq(1) input").val();
		let userDropdown = $(this).find("td:eq(2) .selected-users").text().trim();
		let status = $(this).find("td:eq(3) select").val();
		let kraDetails = [];
		let UserIds = [];
		let userName = [];

		if (!evalType || !deadlineDate || !userDropdown) {
			return; // Skip empty rows
		}


		$(this).find(".selected-users .selected-assign-item").each(function() {
			let userId = $(this).attr("id") || "";
			let names = $(this).text().trim();

			if (userId) UserIds.push(userId);
			if (names) userName.push(names);
		});

		console.log("userss->", UserIds);

		console.log("names-->", userName);
		let userId = userDropdown.match(/\((.*?)\)/); // Extract user ID from text
		let users = userId ? userId[1] : "";

		let kraBodyId = $(this).next("tr.kra-table").find("tbody").attr("id");
		if (kraBodyId) {
			$(`#${kraBodyId} tr`).each(function() {
				let kraSlNo = $(this).find("td:eq(0)").text().trim();
				let kraName = $(this).find("td:eq(1) input").val();
				let kraDesc = $(this).find("td:eq(2) input").val();
				let kraWeightage = $(this).find("td:eq(3) input").val();

				if (kraSlNo && kraName && kraDesc && kraWeightage) {
					kraDetails.push({
						kraSlNo: kraSlNo,
						kraName: kraName,
						kraDesc: kraDesc,
						kraWeightage: kraWeightage,
						evaluationType: evalType
					});
				}
			});
		}

		evaluations.push({
			type: evalType,
			date: deadlineDate,
			users: UserIds.length > 0 ? UserIds.join(",") : "",
			userName: userName.length > 0 ? userName.join(",") : "",
			status: status || "Pending",
			KRADetails: kraDetails
		});
	});

	console.log(JSON.stringify(evaluations, null, 2));

	if (evaluations.length > 0) {
		saveEvaluationUser(evaluations);
	}
}







function saveEvaluationUser(tableData) {
	var allValid = true;
	let tenderId = $("#headertenderid").text();
	if (allValid) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "tender-management-evalutionuseradd?id=" + tenderId,
			async: true,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(tableData),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					$('.loader').hide();
				}
				else {
					toastr.success(response.message);
					$('.loader').hide();
				}
			},
			error: function(data) {
				$('.loader').hide();
			}
		})
	}
}



function evalutionEditDetails(evalutionData) {
	console.log("eval details-->", evalutionData);
	if (evalutionData) {
		document.querySelectorAll("[id^=kra-body-]").forEach(kraTable => kraTable.innerHTML = "");

		evalutionData.forEach((evalItem, index) => {
			let { type, date, users, userName, status, KRADetails } = evalItem;

			if (!selectedSteps.includes(type)) {
				selectedSteps.push(type);
				addEvaluation(type);
			}

			let rowId = index + 1;
			let deadlineInput = document.getElementById(`assign_User_deadlineDate_${rowId}`);
			if (deadlineInput) {
				deadlineInput.value = date;
			} else {
				console.error(`Element with ID assign_User_deadlineDate_${rowId} not found.`);
			}

			let userContainer = document.getElementById(`selected_assignUser_${rowId}`);
			if (userContainer) {
				let selectedUsersHTML = "";
				let userIds = users ? users.split(",") : [];
				let userNames = userName ? userName.split(",") : [];

				userIds.forEach((userId, i) => {
					selectedUsersHTML += `
						<span class="selected-assign-item" id="${userId.trim()}">
							${userNames[i].trim()} 
							<i class="fas fa-times remove-selecteduser-btn" data-index="${index}" 
							onclick="removeAssignUser('${userId.trim()}', ${index})"></i>
						</span>
					`;
				});
				userContainer.innerHTML = selectedUsersHTML;
			}

			let statusDropdown = document.querySelector(`#evalTableBody tr:nth-child(${rowId}) select`);
			if (statusDropdown) {
				statusDropdown.value = status;
			}

			let kraBody = document.getElementById(`kra-body-${rowId}`);
			if (!kraBody) {
				console.error(`Element with ID kra-body-${rowId} not found.`);
				return;
			}

			kraBody.innerHTML = "";

			KRADetails.forEach((kraItem, kraIndex) => {
				let kraCount = kraIndex + 1;
				let kraRow = document.createElement("tr");
				kraRow.innerHTML = `
					<td>${kraCount}</td>
					<td><input type="text" class="form-control" value="${kraItem.kraName}" placeholder="KRA Name"></td>
					<td><input type="text" class="form-control" value="${kraItem.kraDesc}" placeholder="KRA Description"></td>
					<td><input type="text" class="form-control" value="${kraItem.kraWeightage}" placeholder="KRA Weightage"></td>
					<td><i class="fa-solid fa-trash kra_remove_btn" onclick="removeKraRow(this)"></i></td>
				`;
				kraBody.appendChild(kraRow);
			});

			let kraRowContainer = document.getElementById(`kra-row-${rowId}`);
			if (kraRowContainer) {
				kraRowContainer.style.display = KRADetails.length > 0 ? "table-row" : "none";
			}
		});

		$("#timelineContainer").show();
		$("#save-evalution-team").hide();
		$(".add_kra_row_btn").hide();
		$("#evalution-dropdown").hide();
	} else {
		$("#selectedItems").hide();
		$("#save-evalution-team").show();
		$("#evalution-dropdown").show();
		$("#timelineContainer").hide();
	}
}



function handleEnter(event) {
	if (event.key === "Enter") {
		event.preventDefault();
		//evalutionSelection('evalution_text');
		$(".evalution_type_add_icon").click();
	}
}


