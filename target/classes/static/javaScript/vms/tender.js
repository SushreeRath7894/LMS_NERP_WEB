let headerTabs = "";
$(document).ready(function() {


	var gridDiv = document.querySelector('#contractNegotation');
	new agGrid.Grid(gridDiv, allTenderGridOptions);

	$("#applyBtn").attr("disabled", true)
	headerTabs = 'New Tenders Data';
	updateHeader();

	const status = '0';
	agGrid.simpleHttpRequest({
		url: `tender-all-data/${status}`
	}).then(function(response) {
		console.log("first function");
		if (response.code === "Success") {
			let parsedResponse = JSON.parse(response.body);
			console.log("response_body -->", parsedResponse);
			var rowData = [];
			allTenderGridOptions.api.setRowData(rowData);

			let currentDate = new Date();
			let year = currentDate.getFullYear();
			let month = String(currentDate.getMonth() + 1).padStart(2, "0");
			let day = String(currentDate.getDate()).padStart(2, "0");
			let hours = String(currentDate.getHours()).padStart(2, "0");
			let minutes = String(currentDate.getMinutes()).padStart(2, "0");

			let formattedCurrentDate = `${year}-${month}-${day} ${hours}:${minutes}`;
			console.log("date-->", formattedCurrentDate);

			var updatedResponse = parsedResponse.filter(element =>
				element.awardstatus == 0 && element.effectiveDate >= formattedCurrentDate
			);

			allTenderGridOptions.api.setRowData(updatedResponse)

			if (updatedResponse && updatedResponse.length > 0) {
				allTenderGridOptions.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true);
					}
				});
			}
			$(`.tender-not_found_${status}`).hide();
			$(".tender_submission-body").show();
			$(".tender_submission-title").text('');
			$("#tenderSave-btn").show();
		} else {
			allTenderGridOptions.api.setRowData([]);
			$(`.tender-not_found_0`).show();
			$(".tender_submission-body").hide();
			$(".tender_submission-title").text('');
			$("#tenderSave-btn").hide();
		}
	});

	// Add an event listener to handle row selection
	allTenderGridOptions.api.addEventListener(
		'selectionChanged', onSelectionChanged);
	//setInitialFilter();
});

var tenderGridDefs = [{
	headerName: + headerTabs,

	children: [{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 20
	},

	{
		headerName: "Template Id",
		field: "templateId",
		width: 200
	}, {
		headerName: "Name",
		field: "templateName",
		width: 330
	}, {
		headerName: "Description",
		field: "templateDescription",
		width: 500
	}, {
		headerName: "Created Date",
		field: "creationDate",
		width: 200
	}, {
		headerName: "Effective Date",
		field: "effectiveDate",
		width: 200
	}, {
		headerName: "Published Pdf",
		field: "tenderPdf",
		width: 180,
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
	}]
}];

var allTenderGridOptions = {
	columnDefs: tenderGridDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 10,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},
};

var selectedId = "";
function onSelectionChanged() {
	var selectedRows = allTenderGridOptions.api.getSelectedRows();
	console.log("Selected rows-->", selectedRows);

	if (selectedRows.length > 0) {
		selectedId = selectedRows[0].templateId;
		var tendorid = selectedRows[0].templateId;
		const criteriaData = selectedRows[0].criteria;
		console.log("Criteria Data: ", criteriaData);
		updateCriteriaTable(criteriaData);
		$("#applyBtn").attr("disabled", false);
		ApplyTenderData(selectedRows[0]);
		$("#headertendorid").text(tendorid);
	} else {
		$("#applyBtn").attr("disabled", true);
		$("#headertendorid").text('');
	}
}

function updateCriteriaTable(criteriaData) {

	console.log("in the criteriatable function");
	var tableBody = $("#criteriaTable tbody");

	tableBody.empty();

	criteriaData.forEach(function(item, index) {
		var row = `<tr>
					    <td style="text-align: center;">${index + 1}</td>
					    <input type="hidden" id="criteriaId_${index}" value="${item.criteriaId}">
                        <td id="criteriaName_${index}">${item.criteriaName}</td>
                        <td id="criteriaData_${index}">${item.criteriaData}</td>
                        <td style="text-align: center;"> 
                            <label class="switch">
                                <input type="checkbox" class="approveToggle" data-index="${index}">
                                <span class="slider round"></span>
                            </label>
                        </td>
                       <td>
                            <input type="file" class="form-control documentUpload" data-index="${index}">
                            <a href="#" class="selectedDocumentLink" id="selectedDoc_${index}" style="display:none;" target="_blank">No document selected</a>
                        </td>
                    </tr>`;

		tableBody.append(row);
	});

	$(".documentUpload").on("change", function() {
		var index = $(this).data("index");
		var file = this.files[0];

		if (file) {
			var fileURL = URL.createObjectURL(file);
			var fileName = file.name;

			var documentLink = $(`#selectedDoc_${index}`);
			documentLink.attr("href", fileURL);
			documentLink.text(fileName);
			documentLink.show();
		} else {
			$(`#selectedDoc_${index}`).text("No document selected").hide();
		}
	});
}

function apply() {
	if (selectedId) {
		$("#chatBoxModal").modal('show');
	} else {
		$("#chatBoxModal").modal('hide');
	}
}

function closeBtn() {
	$("#chatBoxModal").modal('hide');
}

/*function setInitialFilter() {
	var gridApi = allTenderGridOptions.api;

	gridApi.setFilterModel({
		status: {
			type: 'equals',
			filter: '0'
		}
	});
	gridApi.onFilterChanged();
}*/


function filter(id) {

	var buttons = document.querySelectorAll('.btn-group .child');
	buttons.forEach(function(button) {
		button.classList.remove('headerBtnActive');

	});

	// Set headerTabs based on the filter ID
	switch (id) {
		case "0":
			headerTabs = 'New Tenders Data';
			$(".tender_submission-dialog").show();
			$(".tender-content").hide();
			$("#tenderSave-btn").attr("disabled", false);
			break;
		case "1":
			headerTabs = 'Applied Tenders Data';
			$(".tender_submission-dialog").hide();
			$(".tender-content").show();
			$("#tenderSave-btn").attr("disabled", true);
			break;
		case "2":
			headerTabs = 'Awarded Tenders Data';
			$(".tender_submission-dialog").hide();
			$(".tender-content").show();
			$("#tenderSave-btn").attr("disabled", true);
			break;
		case "3":
			headerTabs = 'Cancelled Tenders Data';
			$(".tender_submission-dialog").hide();
			$(".tender-content").show();
			$("#tenderSave-btn").attr("disabled", true);
			break;
		default:
			headerTabs = '';
	}

	// Update header and refresh grid
	updateHeader();

	// Add 'active' class to the clicked button
	var clickedButton = document.getElementById(event.target.id);
	clickedButton.classList.add('headerBtnActive');

	if (id == '0') {
		$("#applyBtn").show();
	} else {
		$("#applyBtn").hide();
	}

	const status = id;
	agGrid.simpleHttpRequest({
		url: `tender-all-data/${status}`
	}).then(function(response) {
		console.log("in the ajax call function " + status, response);
		if (response.code === "Success") {
			let parsedResponse = JSON.parse(response.body);
			//allTenderGridOptions.api.setRowData(parsedResponse);

			var rowData = [];
			allTenderGridOptions.api.setRowData(rowData);

			let currentDate = new Date();
			let year = currentDate.getFullYear();
			let month = String(currentDate.getMonth() + 1).padStart(2, "0");
			let day = String(currentDate.getDate()).padStart(2, "0");
			let hours = String(currentDate.getHours()).padStart(2, "0");
			let minutes = String(currentDate.getMinutes()).padStart(2, "0");

			let formattedCurrentDate = `${year}-${month}-${day} ${hours}:${minutes}`;
			console.log("date-->", formattedCurrentDate);



			if (status == 0) {
				var updatedResponse = parsedResponse.filter(element =>
					element.awardstatus == 0 && element.effectiveDate >= formattedCurrentDate
				);
				allTenderGridOptions.api.setRowData(updatedResponse);
				if (updatedResponse && updatedResponse.length > 0) {
					allTenderGridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}
			}
			else {
				allTenderGridOptions.api.setRowData(parsedResponse);
				if (parsedResponse && parsedResponse.length > 0) {
					allTenderGridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true); // Select the first row
						}
					});
				}
			}

			switch (status) {
				case "0":
					$(`.tender-not_found_0`).hide();
					$(".tender_submission-body").show();
					$(".tender_submission-title").text('');
					$("#tenderSave-btn").show();
					break;
				case "1":
					$(`.tender-not_found_1`).hide();
					$(".applied-tender-body").show();
					$(".tender_submission-title").text('');
					$("#tenderSave-btn").show();
					break;
				case "2":
					$(".tender_submission-dialog").hide();
					$(".tender-content").show();
					$(`.tender-not_found_1`).hide();
					$("#tenderSave-btn").attr("disabled", true);
					$(".applied-tender-body").show();

					break;
				case "3":

					$(".tender_submission-dialog").hide();
					$(".tender-content").show();
					$(`.tender-not_found_1`).hide();
					$("#tenderSave-btn").attr("disabled", true);
					$(".applied-tender-body").show();
					break;
				default:
					headerTabs = '';
			}
		} else {
			allTenderGridOptions.api.setRowData([]);
			switch (id) {
				case "0":
					$(`.tender-not_found_0`).show();
					$(".tender_submission-body").hide();
					$(".tender_submission-title").text('');
					$("#tenderSave-btn").hide();
					break;
				case "1":
					$(`.tender-not_found_1`).show();
					$(".applied-tender-body").hide();
					$(".tender_submission-title").text('');
					$("#tenderSave-btn").hide();
					break;
				case "2":
					$(".tender_submission-dialog").hide();
					$(`.tender-not_found_1`).show();
					$("#tenderSave-btn").attr("disabled", true);
					$(".applied-tender-body").hide();

					break;
				case "3":

					$(".tender_submission-dialog").hide();
					$(`.tender-not_found_1`).show();
					$("#tenderSave-btn").attr("disabled", true);
					$(".applied-tender-body").hide();
					break;
				default:
					headerTabs = '';
			}
		}
	});
}

function updateHeader() {
	// Update the grid header dynamically
	allTenderGridOptions.columnDefs[0].headerName = headerTabs;
	allTenderGridOptions.api.setColumnDefs(allTenderGridOptions.columnDefs);
}



function sendMessageButton() {

	const technicalProposalDesc = CKEDITOR.instances.technicalProposalDesc.getData();
	var technicalContent = technicalProposalDesc.replace(/<(strong|em|ol|ul|li|p|br)[^>]*>(.*?)<\/\1>/g, "$2");
	technicalContent = technicalContent.replace(/<\/?(ul|ol|li|p|em|strong|br)[^>]*>/g, "");

	const financialProposalDesc = CKEDITOR.instances.financialProposalDesc.getData();
	var financialContent = financialProposalDesc.replace(/<(strong|em|ol|ul|li|p|br)[^>]*>(.*?)<\/\1>/g, "$2");
	financialContent = financialContent.replace(/<\/?(ul|ol|li|p|em|strong|br)[^>]*>/g, "");

	var formData = new FormData();

	// Handle technical proposal file upload
	var technicalElement = document.getElementById('technicalProposalDoc');
	var technicalFileName = '';
	if (technicalElement.files.length > 0) {
		var selectedTechnicalFile = technicalElement.files[0];
		technicalFileName = selectedTechnicalFile.name;
		formData.append('technicalProposalFile', selectedTechnicalFile);
	} else {
		console.log("No technical proposal file selected");
	}

	// Handle financial proposal file upload
	var financialFileName = '';
	var financialElement = document.getElementById('financialProposalDoc');
	if (financialElement.files.length > 0) {
		var selectedFinancialFile = financialElement.files[0];
		financialFileName = selectedFinancialFile.name;
		formData.append('financialProposalFile', selectedFinancialFile);
	} else {
		console.log("No financial proposal file selected");
	}

	const technicalProposalData = {
		technicalFile: technicalFileName,
		description: technicalContent
	};

	const financialProposalData = {
		financialFile: financialFileName,
		description: financialContent
	};

	let rows = document.querySelectorAll("tbody tr");
	console.log("rows--->", rows);
	let dataList = [];

	rows.forEach((row, index) => {
		let dataIndex = row.querySelector('.approveToggle').getAttribute('data-index');

		let name = row.querySelector(`#criteriaName_${dataIndex}`).innerText.trim();
		let description = row.querySelector(`#criteriaData_${dataIndex}`).innerText.trim();
		let criteriaId = row.querySelector(`#criteriaId_${dataIndex}`).value;
		let toggle = row.querySelector(`.approveToggle[data-index="${dataIndex}"]`).checked;
		let fileInput = row.querySelector(`.documentUpload[data-index="${dataIndex}"]`);
		let selectedFile = fileInput.files.length ? fileInput.files[0] : null;
		let docName = row.querySelector(`#selectedDoc_${dataIndex}`).innerText || "No document selected";

		let rowData = {
			criteriaId: criteriaId,
			toggle: toggle,
			documentName: docName
		};

		dataList.push(rowData);

		if (selectedFile) {
			formData.append(`criteriaFiles_${index}`, selectedFile);
		}
	});
	const tenderApplicationFee = $("#tenderApplicationFee").val();
	let mainDataList = {
		technicalProposalData: technicalProposalData,
		financialProposalData: financialProposalData,
		criteriaData: dataList,
		applicationFee: tenderApplicationFee,
		applicationFeeType: "₹",
		tenderId: selectedId
	};

	console.log("mainDataList :", mainDataList);

	uploadFiles(formData);
	saveData(mainDataList);
}

function uploadFiles(formData) {
	$.ajax({
		type: "POST",
		url: "tender-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: formData,
		processData: false,
		cache: false,
		success: function(response) {
			console.log(response.message);
		},
		error: function(e) {
			console.error('Error:', e);
		}
	});
}

function saveData(tenderData) {
	$(".loader").show();

	$.ajax({
		type: "POST",
		url: "tender-activity-save-vendors",
		contentType: "application/json",
		data: JSON.stringify(tenderData),
		success: function(response) {
			if (response.code == "Success") {
				$(".loader").hide();
				$("#chatBoxModal").modal('hide');
				$("body").removeClass("overlay");
				/*$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');*/

				showSnackbar(response.message);
				$("#Medium").trigger("click");
				setTimeout(function() {
					$("#Medium").addClass("headerBtnActive");
				}, 500); y
				filter('1');

			} else {
				$(".loader").hide();
				/*$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');*/
				showSnackbar(response.message);
			}
			upDateAgridOnApply();
		},
		error: function(datas) {
			console.log(datas);
		}
	});

}


function upDateAgridOnApply() {
	const status = '0';
	agGrid.simpleHttpRequest({
		url: `tender-all-data/${status}`
	}).then(function(response) {
		if (response.code === "Success") {
			const parsedResponse = JSON.parse(response.body);
			//allTenderGridOptions.api.setRowData(parsedResponse);
			var rowData = [];
			allTenderGridOptions.api.setRowData(rowData);

			allTenderGridOptions.api.setRowData(parsedResponse);

			if (parsedResponse && parsedResponse.length > 0) {
				allTenderGridOptions.api.forEachNode(function(node) {
					if (node.rowIndex === 0) {
						node.setSelected(true); // Select the first row
					}
				});
			}
		} else {
			allTenderGridOptions.api.setRowData([]);
		}
	});

	// Add an event listener to handle row selection
	allTenderGridOptions.api.addEventListener(
		'selectionChanged', onSelectionChanged);
	//setInitialFilter();
}

function ApplyTenderData(row) {

	console.log("Apply Roww-->", row);
	var templateName = row.templateName;
	var effectiveDate = row.effectiveDate;
	var desc = row.templateDescription;
	var creationDate = row.creationDate;

	$("#templateName").val(templateName);
	$("#effectiveDate").val(effectiveDate);
	$("#date").val(creationDate);
	CKEDITOR.instances['tenderEditor'].setData(desc);

}

function showSnackbar(message) {
	const snackbar = document.getElementById("snackbar");
	snackbar.textContent = message;
	snackbar.className = "snackbar show";
	setTimeout(() => {
		snackbar.className = snackbar.className.replace("show", "");
	}, 3000);
}