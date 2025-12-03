let templateSectionOptions;
let templateSectionMainOptions;
document.addEventListener('DOMContentLoaded', function() {
	$("#publishContract").attr("disabled", true);
	CKEDITOR.replace('landingPageEditor');
	templateSectionMainOptions = {
		columnDefs: [

			{
				headerName: 'Contract Templates',

				children: [
					{
						headerCheckboxSelection: false,
						headerCheckboxSelectionFilteredOnly: true,
						checkboxSelection: true,
						sortable: false,
						filter: false,
						resizable: true,
						width: 20
					},

					{
						headerName: "Contract Id",
						field: "contractId",
						width: 200
					},
					{
						headerName: "Contract Name",
						field: "contractName",
						width: 350
					}, {
						headerName: "Tender Name",
						field: "tenderName",
						width: 350
					}, {
						headerName: "Vendor Id",
						field: "vendorId",
						width: 200
					},
					{
						headerName: "Description",
						field: "templateDescription",
						width: 430
					},
					{
						headerName: "Created Date",
						field: "creationDate",
						width: 200
					}, {
						headerName: "Effective Date",
						field: "effectiveDate",
						width: 250
					}, {
						headerName: "status",
						field: "status",
						width: 230,
						cellRenderer: function(params) {
							return params.value == "1" ? 'Active' : 'Inactive';
						}
					}, {
						headerName: "Published Pdf",
						field: "tenderPdf",
						width: 230,
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
					},
					{
						headerName: "Published Status",
						field: "publishStatus",
						width: 230,
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
					}
				]
			}
		],
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true
		},
		pagination: true,
		paginationPageSize: 15,
		rowData: null,
		onSelectionChanged: function() {
			const selectedRows = this.api.getSelectedRows();
			console.log("selected rows-->", selectedRows);


			var rowCount = 0;

			selectedRows.forEach(function(selectedRow, index) {
				rowCount = rowCount + 1;
			});

			if (rowCount > 0) {
				$("#tenderSave-btn").hide();
				var contractid = selectedRows[0].contractId;
				$("#headercontractid").text(contractid);
			} else {
				$("#tenderSave-btn").show();
				$("#headercontractid").text("");
			}

			if (selectedRows.length > 0) {
				selectedRows.forEach(function(row) {
					console.log("row-->", row);
					ApplyTenderData(row);
					const rowIndex = row.rowIndex;
					console.log('Selected row index:', rowIndex);
				});
				
				loadSections(selectedRows[0].sections);
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
				url: `view-contract-data?fromDate=${fromDate}&toDate=${toDate}`
			}).then(function(response) {
				if (response.code === "Success") {
					const parsedResponse = JSON.parse(response.body);
					//params.api.setRowData(parsedResponse);
					var rowData = [];
					params.api.setRowData(rowData);
					params.api.setRowData(parsedResponse);
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
				const selectedTemplate = selectedRows[0];

				var publishStatus = (selectedRows[0]?.publishStatus); // Use optional chaining to avoid errors if selectedRows is empty
				if (selectedRows.length > 0) {
					$("#contractId").val(selectedRows[0].contractId);
					if (selectedRows.length > 0 && publishStatus == '1') {
						$("#publishContract").attr("disabled", false);
						$("#btnContent").text("Renew Contract");
					} else {
						$("#btnContent").text("Publish Contract");

						$("#publishContract").attr("disabled", false);
					}

					// Populate templateDetailsOptions grid
					const templateDetailsData = [{
						contractId: selectedTemplate.contractId,

						contractName: selectedTemplate.contractName,
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
					//const addCntract = document.getElementById('addCntract');
					const saveStatus = document.getElementById('saveStatus');
					//const searchRowDiv = document.getElementById('searchRowDiv');

					//templateDetailsMainGrid.classList.add('hidden-height2');
					templateDetailsGrid.classList.remove('hidden');
					templateSectionGrid.classList.remove('hidden');
					addSection.classList.remove('hidden');
					saveStatus.classList.remove('hidden');
					//	addCntract.classList.add('hidden');
					//searchRowDiv.classList.add('hidden');
					previewSection.classList.remove('hidden');
					downloadPdf.classList.remove('hidden');
					templateDetailsGrid.classList.add('hidden-height');
					templateSectionGrid.classList.add('hidden-height1');
				} else {
					// Hide details and sections grids
					const templateDetailsGrid = document.getElementById('templateDetailsGrid');
					const templateSectionGrid = document.getElementById('templateSectionGrid');
					const templateDetailsMainGrid = document.getElementById('templateDetailsMainGrid');
					//const addSection = document.getElementById('addSection');
					const previewSection = document.getElementById('previewSection');
					const downloadPdf = document.getElementById('downloadPdf');
					const addCntract = document.getElementById('addCntract');
					const saveStatus = document.getElementById('saveStatus');
					//const searchRowDiv = document.getElementById('searchRowDiv');
					$("#addCntract").attr("disabled", false);
					$("#publishContract").attr("disabled", true);
					$("#renewContract").attr("disabled", true);

					//templateDetailsMainGrid.classList.remove('hidden-height2');
					templateDetailsGrid.classList.add('hidden');
					templateSectionGrid.classList.add('hidden');
					//addSection.classList.add('hidden');
					//addCntract.classList.remove('hidden');
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
						field: "contractId",
						width: 280
					},
					{
						headerName: "Template Name",
						field: "contractName",
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
	/* document.querySelectorAll('.ckeditorCriteria').forEach((element) => {
		CKEDITOR.replace(element.id);
	}); */
}

function addSection() {
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
			            /* <button type="button" class="btn btn-success save-btn-criteria d-none" data-id="${index}">Save</button>
			            <button type="button" class="btn btn-warning edit-btn-criteria" data-id="${index}">Edit</button>
			            <button type="button" class="btn btn-danger delete-btn-criteria" data-id="${index}">Delete</button> */
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
						<button type="button" id="add" class="btn btn-primary">Add
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
					<button type="button" id="add" class="btn btn-primary">Add
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
				const contract = $("#contractId").val();
				const rowData = {
					contractId: contract,
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

function openReviewData() {
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

		/*<h3 class="section-title1">${row.sectionNo}. ${row.name}</h3>*/
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

}
/*function openReviewModal(pathValue) {
	const contractIds = $("#contractId").val();
	const selectedRows = templateSectionMainOptions.api.getSelectedRows();
	const selectedTemplate = selectedRows[0];
	const vendor = (selectedTemplate.vendorId);

	openReviewData();
	$(".loader").show();
	const content = document.querySelector('#reviewModal .modal-body').innerHTML;
	fetch(`get-contract-data/${pathValue}?contractIds=` + contractIds + "&vendor=" + vendor, {
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
				$("#messageParagraph").html(`Contract published successfully!`);
				$("#msgOkModal").removeClass("btn3").addClass("btn1");
				$("#msgModal").modal('show');
				isClicked = false;
				$(".loader").hide();
				fetchAndUpdateGrid();

				openReviewModal('previewPdf');
				cancelContract();

				const encryptedVendor = getQueryParam('vendor');
				const encryptedContract = getQueryParam('contract');

				if (encryptedVendor && encryptedContract) {
					const vendorId = decryptBase64(encryptedVendor);
					const contractId = decryptBase64(encryptedContract);

					if (vendorId && contractId) {
						window.location.href = "/purchase/vendor-evaluation";
					}

				}
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
}*/


function openReviewModal(pathValue) {

	var contractId = $("#headercontractid").text();
	const selectedRows = templateSectionMainOptions.api.getSelectedRows();
	const selectedTemplate = selectedRows[0];
	const vendor = (selectedTemplate.vendorId);
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

	fetch(`contract-creation-contentpdf?pathvalue=${pathValue}&tenderid=${contractId}&vendor=${vendor}`, {
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




/*function downloadPdf(pathValue) {

	const contractIds = $("#contractId").val();
	openReviewData();
	$(".loader").show();
	const content = document.querySelector('#reviewModal .modal-body').innerHTML;

	fetch(`get-contract-data/${pathValue}?contractIds=` + contractIds, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({
			pdfContent: content
		})
	}).then(response => {
		if (response.ok) {
			return response.blob(); // Convert response to blob
		} else {
			throw new Error('Error generating PDF');
		}
	})
		.then(blob => {
			const url = window.URL.createObjectURL(blob);
			const a = document.createElement('a');
			a.href = url;
			a.download = 'sample-pdf.pdf';
			document.body.appendChild(a);
			a.click();
			window.URL.revokeObjectURL(url);
			document.body.removeChild(a);
			$(".loader").hide();
		});
}*/


function downloadPdf(pathValue) {
	var tenderId = $("#headercontractid").text();
	const selectedRows = templateSectionMainOptions.api.getSelectedRows();
	const selectedTemplate = selectedRows[0];
	const vendor = (selectedTemplate.vendorId);
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

	fetch(`contract-creation-contentpdf?pathvalue=${pathValue}&tenderid=${tenderId}&vendor=${vendor}`, {
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

function addCntract() {
	templateSectionMainOptions.api.deselectAll();
	clearValidationMessage('templateNameError');
	clearValidationMessage('effectiveDateError');
	clearValidationMessage("tenderEditorError");
	clearValidationMessage("tenderIdError");
	clearValidationMessage("vendorIdError");

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
	$("#next-btn-1").hide();
	$("#publishTender").hide();

	$("#cancel-contract-btn").show();
	$("#voucherclassTab").hide();
	$("#sectionEditView").html('');
	$("#sectionEditView").hide();
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
								<button class="btn btn-danger btn-sm delete-saved-section" type="button">Delete</button>
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
	$(document).on('click', '#add', function() {
		rowId++;
		$('#dynamic_field tbody').append(`
        <tr id="row${rowId}">
          <td><h4>${rowId}</h4></td>
          <td><textarea name="criteria[]" class="ckeditorCriteria" id="ckeditorCriteria1_${rowId}""></textarea></td>
          <td><textarea name="required_document[]" class="ckeditorCriteria" id="ckeditorCriteria2_${rowId}"></textarea></td>
          <td>
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

	});

	// Save the row data
	$(document).on('click', '.save-btn-criteria', function() {
		var row = $(this).closest('tr');
		let count = $(this).data("id");
		var criteriaId = row.find('textarea[name="criteria[]"]').attr('id');
		var requiredDocumentId = row.find('textarea[name="required_document[]"]').attr('id');
		var criteriaEditor = CKEDITOR.instances['ckeditorCriteria1_' + count].getData();
		console.log("HAPPY GURU PURNIMA", criteriaEditor)
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
		console.log(row);
		var criteria = $(this).closest('tr').find('td:eq(1)').html();
		var requiredDocument = $(this).closest('tr').find('td:eq(2)').html();
		let count = $(this).data("id");

		console.log(criteria)

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
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});

});

let sectionCounter = 0;
let savedSections = [];

function createSection(name = "", description = "") {
	/*sectionCounter++;
	return `
	<div class="card mb-3 section">
		<div class="card-header d-flex justify-content-between align-items-center">
			<input type="text" class="form-control w-50 section-name" placeholder="Enter Section Name" value="${name}">
			<button class="btn btn-danger btn-sm delete-section" type="button">Delete Section</button>
		</div>
		<div class="card-body">
			<textarea class="form-control section-description" placeholder="Enter Section Description">${description}</textarea>
		</div>
		<div class="card-footer text-end">
			<button class="btn btn-primary save-section" type="button">Save Section</button>
		</div>
	</div>`;*/

	sectionCounter++;
	return `
				<div class="card mb-3 section">
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

function updateSectionNumbers() {
	$(".section-number").each(function(index) {
		$(this).text(index + 1);
	});
}

const prefix = "OMC/2024/TD/";

function generateDynamicId(callback) {

	const existingIds = mainData.tenders.map(item => item.templateId);
	const highestNumber = existingIds
		.map(id => parseInt(id.replace(prefix, ''), 10))
		.reduce((max, num) => num > max ? num : max, 0);

	const newIdNumber = highestNumber + 1;
	const paddedNumber = String(newIdNumber).padStart(3, '0');
	const newId = prefix + paddedNumber;

	callback(newId);

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



CKEDITOR.on('instanceReady', function(event) {
	var editor = event.editor;
	editor.on('change', function() {
		clearValidationMessage("tenderEditorError");
	});
});

//Function to clear the validation message
function clearValidationMessage(errorElementId) {
	document.getElementById(errorElementId).textContent = "";
}


// Example validation function for form submission
function validateCKEditor() {
	var ckEditorContent = CKEDITOR.instances.tenderEditor.getData();
	if (ckEditorContent.trim() === "") {
		validationUpdated("Description is required", "tenderEditor");
		return false;
	}
	return true;
}

function validationUpdated(message, fieldId) {

	document.getElementById(fieldId + "Error").textContent = message;
}

function validateForm() {
	var validation = true;

	// Validate CKEditor
	if (!validateCKEditor()) {
		validation = false;
	}

	return validation;
}

function saveContractData() {
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
	const tenderId = $("#tenderId").val();
	const vendorId = $("#vendorId").val();

	// Format the datetime-local value to remove 'T'
	const formattedEffectiveDate = effectiveDate.split("T").join(" ");
	const formattedCreatedDate = createdDate.split("T").join(" ");

	const data = {
		templateId: "",
		templateName: templateName,
		effectiveDate: formattedEffectiveDate,
		creationDate: formattedCreatedDate,
		status: status,
		templateDescription: tenderDescriptionPlain,
		sections: allSections,
		tenderId: tenderId,
		vendorId: vendorId
	};

	/* FORM VALIDATION STARTS*/

	var validation = true;


	if (data.templateName == null || data.templateName == "") {
		//validation = validationUpdated("Template Name Required", "templateName");
		toastr.error('Template Name Required');
		return;
	}
	if (data.effectiveDate == null || data.effectiveDate == "") {

		//validation = validationUpdated("Effective Date Required", "effectiveDate");
		toastr.error(`Effective Date Required`);
		return;
	}
	if (data.tenderId == null || data.tenderId == "") {
		//validation = validationUpdated("Tender Required", "tenderId");
		toastr.error(`Tender Required`);
		return;
	}
	if (data.vendorId == null || data.vendorId == "") {
		//validation = validationUpdated("Vendor Required", "vendorId");
		toastr.error(`Vendor Required`);
		return;
	}


	if (data.templateDescription == null || data.templateDescription == "") {

		validateCKEditor();
		return;
	}


	if (data.sections == null || data.sections == "") {
		$("body").removeClass("overlay");
		/*$("#messageParagraph").text("Atleast One Template Section Required");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');*/
		var text = `Atleast One Template Section Required`
		//showSnackbar(text);
		toastr.error(`Atleast One Template Section Required`);
		return;
	}



	function validationUpdated(message, fieldId) {
		document.getElementById(fieldId + "Error").textContent = message;

	}

	if (validation) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "save-contract-data",
			contentType: "application/json",
			data: JSON.stringify(data),
			success: function(response) {
				if (response.code == "Success") {
					$('.loader').hide();
					$("body").removeClass("overlay");
					/*$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('#tenderModal').modal("hide");*/
					//showSnackbar(response.message);
					toastr.success(response.message);
					$("#next-btn-1").show();
					$("#publishTender").show();
					cancelContractBtn();

				} else {
					$('.loader').hide();
					/*$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');*/
					//showSnackbar(response.message);
					toastr.success(response.message);
				}
				fetchAndUpdateGrid();
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
}


function fetchAndUpdateGrid() {
	var fromDate = '';
	var toDate = '';
	agGrid.simpleHttpRequest({
		url: `view-contract-data?fromDate=${fromDate}&toDate=${toDate}`
	}).then(function(response) {
		if (response.code === "Success") {
			const parsedResponse = JSON.parse(response.body);
			//window.templateSectionMainApi.setRowData(parsedResponse);
			var rowData = [];
			window.templateSectionMainApi.setRowData(rowData);
			window.templateSectionMainApi.setRowData(parsedResponse);

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
		console.error('No rows found in the template details grid');
		return;
	}

	const selectedTemplate = allTemplateRows[0].data;
	const templateId = selectedTemplate.templateId;
	const allSections = window.templateSectionApi.getModel().rowsToDisplay;
	const updatedSections = allSections.map(node => node.data);

	//template.sections = updatedSections;
	const payload = { sections: updatedSections };

	$.ajax({
		type: "POST",
		url: "update-contract-data",
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
				//showSnackbar(response.message)
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

          <td>
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


});

let isClicked = false;
function publishContract() {
	if (!isClicked) {
		isClicked = true;
		openReviewModal('publishPdf');
	}
}

function cancelContract() {
	gridApi.deselectAll();
}


/*function for vendor list on change of tender  */

/* function getVendorList(){
   alert("List is coming")
} */

function getVendorList(id) {


	var tender = $('#tenderId').val();
	if (tender) {
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$.ajax({
			type: "GET",
			url: "get-omc-vendorList?id=" + tender,
			success: function(response) {
				if (response.code == "Success") {
					$("#vendorId").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#vendorId").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#vendorId").append(option);
					}

					if (id) {
						$("#vendorId").val(id).attr("disabled", true).css("background-color", "#e9ecef");
					}

				}
			},
			error: function(e) {
			}
		});
	} else {
		$("#vendorId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#vendorId").append(option);
		$("#vendorId").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
	}

}


function getQueryParam(param) {
	const urlParams = new URLSearchParams(window.location.search);
	return urlParams.get(param);
}

function decryptBase64(encodedString) {
	try {
		return atob(encodedString);
	} catch (error) {
		console.error("Error decoding Base64 string:", error);
		return null;
	}
}

window.onload = function() {
	const encryptedVendor = getQueryParam('vendor');
	const encryptedContract = getQueryParam('contract');
	const encryptedTender = getQueryParam('tenderId');

	if (encryptedVendor && encryptedContract) {
		const vendorId = decryptBase64(encryptedVendor);
		const contractId = decryptBase64(encryptedContract);

		if (vendorId && contractId) {
			if (window.templateSectionMainApi) {
				selectContractRow(contractId, vendorId);
			} else {
				document.addEventListener('gridReady', function() {
					selectContractRow(contractId, vendorId);
				});
			}
		} else {
			console.error("Failed to decrypt the vendor or contract ID.");
		}
	} else if (encryptedVendor && encryptedTender) {
		const vendorId = decryptBase64(encryptedVendor);
		const tenderId = decryptBase64(encryptedTender);
		if (vendorId && tenderId) {
			$("#tenderId").val(tenderId).attr("disabled", true).css("background-color", "#e9ecef");
			getVendorList(vendorId);
			addCntract();

		} else {
			console.error("Failed to decrypt the vendor or Tender ID.");
		}

	} else {
		console.warn("No vendor or contract information found in the URL.");
	}
};

function selectContractRow(contractId, vendorId) {
	const allRows = [];
	window.templateSectionMainApi.forEachNode(function(node) {
		allRows.push(node.data);
	});

	const targetRow = allRows.find(row => row.contractId == contractId);
	if (targetRow) {
		window.templateSectionMainApi.forEachNode(function(node) {
			if (node.data.contractId == contractId) {
				node.setSelected(true);
			}
		});
	} else {
		console.warn("No matching contract found for Contract ID:", contractId, "and Vendor ID:", vendorId);
	}
}


function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	console.log("Element-->", tabElement);
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		console.log("tabTrigger-->", tabTrigger);
		tabTrigger.show();
	}
}

function ApplyTenderData(row) {

	var templateName = row.contractName;
	var effectiveDate = row.effectiveDate;
	var desc = row.templateDescription;
	var creationDate = row.creationDate;
	var tendername = row.tenderName;
	var vendorId = row.vendorId;

	$("#templateName").val(templateName);
	$("#effectiveDate").val(effectiveDate);
	$("#date").val(creationDate);
	CKEDITOR.instances['tenderEditor'].setData(desc);
	$("#tenderId").val(tendername);
	$("#vendorId").val(vendorId);

}

function cancelTemplateSection() {
	$("#templateSectionGrid").show();
	$("#templateDetailsGrid").show();
	$(".templateSection-content").hide();
	$("#saveStatus").show();
}

function showSnackbar(message) {
	const snackbar = document.getElementById("snackbar");
	snackbar.textContent = message;
	snackbar.className = "snackbar show";
	setTimeout(() => {
		snackbar.className = snackbar.className.replace("show", "");
	}, 3000);
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


function cancelContractBtn() {
	$("#cancel-contract-btn").hide();
	$("#next-btn-1").show();
	$("#publishTender").show();
	$("#voucherclassTab").show();
	var firstRowNode = templateSectionMainOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
		firstRowNode.setSelected(true);
	}
}


function loadSections(data) {
	console.log("data-->" , data);
	let container = document.getElementById("sectionEditView");
	container.innerHTML = "";
	$("#addMuliSection").hide();
	let heading = document.createElement("h2");
	heading.classList.add("tender-heading");
	heading.innerText = "Contract Section Details";
	container.appendChild(heading);
	
	console.log("before foreach loop");

	data.forEach((section, index) => {
		console.log("section element-->",section);
		if (index < data.length) { // Exclude the last item
			console.log("in if clause");
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
			console.log("section element is-->" , sectionElement);
		}
	});

}