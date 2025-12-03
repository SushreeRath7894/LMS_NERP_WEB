let headerTabs = "";
$(document).ready(function() {

	$("#addContract").attr("disabled", true);
	var gridDiv = document.querySelector('#tenderGrid');
	new agGrid.Grid(gridDiv, allTenderGridOptions);
	var gridVebdorDiv = document.querySelector('#vendorGrid');
	new agGrid.Grid(gridVebdorDiv, vendorGridOptions);
	vendorGridOptions.api.setRowData([]);
	allTenderGridOptions.api.setRowData([]);
	const status = 'vendor';
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
						node.setSelected(true);
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
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});

	$(document).on("click", ".toggle-desc-btn", function() {
		let parentCell = $(this).closest("td");

		parentCell.find(".short-desc, .full-desc").toggle();

		let isExpanded = parentCell.find(".full-desc").is(":visible");
		$(this).text(isExpanded ? "Show Less" : "Show More");
	});


	$(document).on("input", ".kra-assign-score", function() {
		let vendorIndex = $(this).data("vendor");
		let totalScore = 0;

		$(`.kra-assign-score[data-vendor="${vendorIndex}"]`).each(function() {
			let value = parseFloat($(this).val()) || 0;
			totalScore += value;
		});

		$(`#kra-total-${vendorIndex}`).text(totalScore);
	});


	$(document).on("input", ".kra-assign-score", function() {
		let vendorIndex = $(this).data("vendor");
		let assignScore = parseFloat($(this).val()) || 0; // Convert to number, default 0 if empty
		const maxScore = parseFloat($(this).attr("data-max-score")) || 0;

		if (assignScore > maxScore) {
			assignScore = 0;
			$(this).val('');
			toastr.error("Assign Score Can Not Be Greater Than KRA Score");
		}

		let totalScore = 0;
		$(`.kra-assign-score[data-vendor="${vendorIndex}"]`).each(function() {
			totalScore += parseFloat($(this).val()) || 0;
		});

		$(`#kra-total-${vendorIndex}`).text(totalScore);
	});


	document.querySelectorAll('.step').forEach(step => {
		step.addEventListener('click', function() {
			const stepNumber = parseInt(this.getAttribute('data-step'));
			filterVendorsByStep(stepNumber);
		});
	});

});

var tenderGridDefs = [{
	headerName: "Applied Tenders",

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
		headerName: "Tender Id",
		field: "templateId",
		width: 200,
		/*cellRenderer: function(params) {
			return `<a onclick="tenderDetails(${params.rowIndex})" href="javascript:void(0)">
				${params.data.templateId}
			</a>`;
		}*/
	}, {
		headerName: "Tender Name",
		field: "templateName",
		width: 330
	}, {
		headerName: "Description",
		field: "templateDescription",
		width: 300
	}, {
		headerName: "Awarded Status",
		field: "awardedStatus",
		width: 200,
		cellRenderer: function(params) {
			const status = params.value;
			if (status === "Awarded") {
				return `
		                <span title="Awarded" style="color: green; text-decoration: none;font-weight: 700;">
		                    <i class="fa fa-check-circle" aria-hidden="true" style="padding-right: 4px;"></i>Awarded
		                </span>
		                <a href="#" style="margin-left: 5px; color: blue; text-decoration: underline;" 
		                  onclick="viewVendors('${params.data.templateId}')">(vendors)</a>
		            `;
			} else {
				return `<a title="Not Awarded" style="color: #ff7c05; text-decoration: none;">
                    <i class="fa fa-times-circle" aria-hidden="true" style="padding-right: 4px;"></i>Not Awarded
                </a>`;
			}
		}
	}
		, {
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
	onGridReady: function(params) {
		window.gridApi = params.api;
	}
};

function tenderDetails(rowIndex) {
	gridApi.selectIndex(rowIndex, false, false);
}

var vendorGridDefs = [{
	hseaderCheckboxSelection: false,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	sortable: false,
	filter: false,
	resizable: true,
	width: 20
},

{
	headerName: "Tender Id",
	field: "tenderId",
	width: 230,
	cellStyle: { 'font-weight': 'bold' }
}, {
	headerName: "Vendor Id",
	field: "vendorId",
	width: 230,
	cellStyle: { 'font-weight': 'bold' }
}, {
	headerName: "Criteria Evaluation",
	field: "totalCriteriaEvaluation",
	width: 230,
	cellStyle: { 'font-weight': 'bold' }
}, {
	headerName: "Technical Evaluation",
	field: "technicalScore",
	width: 230,
	cellStyle: { 'font-weight': 'bold' }
}, {
	headerName: "Fechnical Evaluation",
	field: "financialScore",
	width: 230,
	cellStyle: { 'font-weight': 'bold' }
}, {
	headerName: "Total Evaluation",
	field: "totalEvaluation",
	width: 210,
	cellStyle: { 'font-weight': 'bold' }
}];

var vendorGridOptions = {
	columnDefs: vendorGridDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},
};

let selectedId = "";
function onSelectionChanged() {
	const selectedRows = allTenderGridOptions.api.getSelectedRows();

	if (selectedRows.length > 0) {
		const status = selectedRows[0].awardedStatus;
		var tenderid = selectedRows[0].templateId;
		$("#headertenderid").text(tenderid);
		$("#evalution_tenderId").text(selectedRows[0].templateName);
		$("#headertenderName").text(selectedRows[0].templateName);
		fetchVendorList(tenderid);
		selectedVendors = [];

		if (status !== "Awarded") {
			selectedId = selectedRows[0].templateId;
			$("#tenderId").html(selectedRows[0].templateId);
			$("#tenderName").html(selectedRows[0].templateName);
			$("#effectiveDate").html(selectedRows[0].effectiveDate);
			$("#todayDate").html(getCurrentDateTime());
			//$("#tenderGrid").addClass('hidden');
			//$("#searchRowDiv").addClass('hidden');
			//$("#totalReq1").addClass('hidden');
			$("#applyBtn").removeClass('hidden');
			$("#cancelBtn").removeClass('hidden');
			$("#vendorDetails").removeClass('hidden');
			$("#ttbtn").removeClass('hidden');
			//$("#addContract").addClass('hidden');
			$("#addContract").attr("disabled", true);
			$("#applyBtn").show();
			$("#evaluatedVendorsPage").addClass('hidden');
			$("#vendorDetails").show();
			$("#ttbtn").show();
			$("#cancelContract").hide();

			getVendorsDetails();
		} else {
			$("#addContract").attr("disabled", false);
			$("#addContract").removeClass('hidden');
			$("#applyBtn").hide();
			$("#addContract").click();
			$("#cancelContract").hide();
			/*$("#messageParagraph").html(`
					<div style="text-align: center;">
						<i class="fas fa-info-circle" style="font-size: 36px; color: #ff0000; margin-bottom: 10px;"></i>
						<h4 class="modal-title" style="font-weight: bold; margin-bottom: 10px;">Action Not Allowed</h4>
						<p style="font-size: 16px; color: #333;">The tender has already been awarded to the selected vendors.</p>
						<p style="font-size: 14px; color: #777;">Please check the vendor details to view the awarded profiles.</p>
					</div>
				`);
			$("#msgOkModal").removeClass("btn3").addClass("btn1");
			$("#msgModal").modal('show');*/
		}
	} else {
		$("#addContract").attr("disabled", true);
		$("#addContract").removeClass('hidden');
	}
}


function getCurrentDateTime() {
	var now = new Date();
	var day = String(now.getDate()).padStart(2, '0');
	var month = String(now.getMonth() + 1).padStart(2, '0');
	var year = now.getFullYear();
	var formattedDate = day + '-' + month + '-' + year;
	var hours = String(now.getHours()).padStart(2, '0');
	var minutes = String(now.getMinutes()).padStart(2, '0');
	var formattedTime = hours + ':' + minutes;
	var currentDateTime = formattedDate + ' ' + formattedTime;

	return currentDateTime;
}

function Cancel() {
	$("#tenderGrid").removeClass('hidden');
	$("#searchRowDiv").removeClass('hidden');
	$("#totalReq1").removeClass('hidden');
	$("#applyBtn").addClass('hidden');
	$("#cancelBtn").addClass('hidden');
	$("#vendorDetails").addClass('hidden');
	$("#ttbtn").addClass('hidden');
	$("#addContract").removeClass('hidden');

}

function getVendorsDetails() {
	$(".loader").show();
	const vendorStatus = "applied-vendors";
	agGrid.simpleHttpRequest({
		url: "tender-applied-vendor-data?tenderId=" + selectedId + "&status=" + vendorStatus,
	}).then(function(response) {
		if (response.code === "Success") {
			const parsedResponse = JSON.parse(response.body);
			let vendorTableBody = '';
			let technicalTableBody = '';
			let financialTableBody = '';
			let evalutionTableBody = '';

			let hideTechnicalTable = true;
			let hideFinancialTable = true;

			parsedResponse.forEach((vendor, vendorIndex) => {
				let rowSpanValue = vendor.criteriaName ? vendor.criteriaName.length : "";
				let vendorTotalScore = 0;


				// Vendor Details
				var assignScore = 0;
				var vendorScore = 0;
				if (vendor.criteriaData) {
					$(".criteria-details-table").show();
					let vendorNameCell = ''; // To store the vendor name cell with rowspan
					let criteriaRows = ''; // To store all criteria rows for this vendor

					if (vendor.criteriaData.length > 0) {
						vendorNameCell = `<td class="category criteria-details-category" rowspan="${vendor.criteriaData.length}" value="${vendor.vendorId}">${vendor.vendorName}</td>`;
					}

					vendor.criteriaData.forEach((criteria, index) => {
						let status = criteria.criteriaAcceptanceStatus === '1' ? 'YES' : 'NO';
						let documentLink = criteria.criteriaDocument;
						let documentName = documentLink.split('/').pop();
						let criteriaId = criteria.criteriaId;

						let predefinedScore = parseInt(criteria.criteriaScore) || 0;
						let assignScore = 0; // Default assigned score
						let isCriteriaDisabled = false;

						let showMoreRequired = criteria.criteriaDesc.length > 150;

						let criteriaHtml = `
                            <div class="criteria-content">
                                <div class="criteria-short">${criteria ? criteria.criteriaDesc.slice(0, 150) : ''}${showMoreRequired ? '...' : ''}</div>
                                <div class="criteria-full" style="display: none;">${criteria.criteriaDesc}</div>
                                ${showMoreRequired ? '<button class="toggle-content-btn">Show More</button>' : ''}
                            </div>`;

						// For the first row, include the vendor name cell
						if (index === 0) {
							criteriaRows += `
                                <tr>
                                    ${vendorNameCell}
                                    <td>${criteriaHtml}</td>
                                    <td>${status}</td>
                                    <td style="display:none">${criteriaId}</td>
                                    <td><a href="${documentLink}" target="_blank">${documentName}</a></td>
                                    <td id="predefinedScore_${index}">${predefinedScore}</td>
                                    <td>
                                        <input type="text" class="vendor-score" data-vendor="${index}" data-max-score="${predefinedScore}" value="${assignScore}" id="assignScore_${index}" ${isCriteriaDisabled ? 'disabled' : ''} oninput="updateTotalScore(${index},${vendorIndex})">
                                    </td>
                                </tr>`;
						} else {
							// For subsequent rows, don't include the vendor name cell
							criteriaRows += `
                                <tr>
                                    <td>${criteriaHtml}</td>
                                    <td>${status}</td>
                                    <td style="display:none">${criteriaId}</td>
                                    <td><a href="${documentLink}" target="_blank">${documentName}</a></td>
                                    <td id="predefinedScore_${index}">${predefinedScore}</td>
                                    <td>
                                        <input type="text" class="vendor-score" data-vendor="${index}" data-max-score="${predefinedScore}" value="${assignScore}" id="assignScore_${index}" ${isCriteriaDisabled ? 'disabled' : ''} oninput="updateTotalScore(${index},${vendorIndex})">
                                    </td>
                                </tr>`;
						}
					});

					vendorTableBody += criteriaRows;
					vendorTableBody += `
                        <tr class="total-score">
                            <td colspan="4">CRITERIA TOTAL SCORE</td>
                            <td class="total-cal">TOTAL</td>
                            <td class="total-cal" id="vendor-total-${vendorIndex}">${vendorScore}</td>
                        </tr>`;
				} else {
					$(".criteria-details-table").hide();
				}

				let technicalScore = 0;
				let financialScore = 0;
				let krascore = 0;

				let isTechnicalDisabled = false;
				let isFinancialDisabled = false;

				if (vendor.evaluationScores && vendor.evaluationScores.criteria && vendor.evaluationScores.criteria.length > 0) {
					let criteriaData = JSON.parse(vendor.evaluationScores.criteria[0]);
					technicalScore = criteriaData[0]?.assignScore || 0;
				}

				if (vendor.evaluationScores && vendor.evaluationScores.technical && vendor.evaluationScores.technical.length > 0) {
					let technicalData = JSON.parse(vendor.evaluationScores.technical[0]);
					technicalScore = technicalData.techAsignScore || technicalScore;
				}

				if (vendor.evaluationScores && vendor.evaluationScores.financial) {
					let financialData = JSON.parse(vendor.evaluationScores.financial[0]);
					financialScore = financialData.finAsignScore || financialScore;
				}

				if (technicalScore > 0) isTechnicalDisabled = true;
				if (financialScore > 0) isFinancialDisabled = true;

				// Technical Details
				if (vendor.technicalData && vendor.technicalData.length > 0) {
					hideTechnicalTable = false;

					let techVendorNameCell = '';
					let techRows = '';

					if (vendor.technicalData.length > 0) {
						techVendorNameCell = `<td class="category technical-details-category" rowspan="${vendor.technicalData.length}" value="${vendor.vendorId}">${vendor.vendorName}</td>`;
					}

					vendor.technicalData.forEach((techinical, index) => {
						if (index === 0) {
							techRows += `
                                <tr>
                                    ${techVendorNameCell}
                                    <td class="technical-desc">${techinical.Technical_Desc || 'N/A'}</td>
                                    <td><a href="${techinical.Technical_Doc}" target="_blank">${techinical.Technical_Doc ? techinical.Technical_Doc.split('/').pop() : 'N/A'}</a></td>
                                    <td><input type="text" class="technical-score techAsignScr" data-vendor="${index}" value="${technicalScore}" id="technicalScore_${index}" ${isTechnicalDisabled ? 'disabled' : ''} oninput="updateTotalScoreForTech(${index})"></td>
                                </tr>`;
						} else {
							techRows += `
                                <tr>
                                    <td class="technical-desc">${techinical.Technical_Desc || 'N/A'}</td>
                                    <td><a href="${techinical.Technical_Doc}" target="_blank">${techinical.Technical_Doc ? techinical.Technical_Doc.split('/').pop() : 'N/A'}</a></td>
                                    <td><input type="text" class="technical-score techAsignScr" data-vendor="${index}" value="${technicalScore}" id="technicalScore_${index}" ${isTechnicalDisabled ? 'disabled' : ''} oninput="updateTotalScoreForTech(${index})"></td>
                                </tr>`;
						}
					});

					technicalTableBody += techRows;
					technicalTableBody += `
                        <tr class="total-score">
                            <td colspan="3">TECHNICAL TOTAL SCORE</td>
                            <td class="total-cal" id="technical-total-${vendorIndex}">${technicalScore}</td>
                        </tr>`;
				}

				// Financial Details
				if (vendor.financialData && vendor.financialData.length > 0) {
					hideFinancialTable = false;

					let finVendorNameCell = '';
					let finRows = '';

					if (vendor.financialData.length > 0) {
						finVendorNameCell = `<td class="category financial-details-category" rowspan="${vendor.financialData.length}" value="${vendor.vendorId}">${vendor.vendorName}</td>`;
					}

					vendor.financialData.forEach((financial, index) => {
						if (index === 0) {
							finRows += `
                                <tr>
                                    ${finVendorNameCell}
                                    <td class="financial-desc">${financial.Financial_Desc || 'N/A'}</td>
                                    <td><a href="${financial.Financial_Doc}" target="_blank">${financial.Financial_Doc ? financial.Financial_Doc.split('/').pop() : 'N/A'}</a></td>
                                    <td><input type="text" class="financial-score finAsignScr" data-vendor="${index}" value="${financialScore}" id="financialScore_${index}" ${isFinancialDisabled ? 'disabled' : ''} oninput="updateTotalScoreForFinance(${index})"></td>
                                </tr>`;
						} else {
							finRows += `
                                <tr>
                                    <td class="financial-desc">${financial.Financial_Desc || 'N/A'}</td>
                                    <td><a href="${financial.Financial_Doc}" target="_blank">${financial.Financial_Doc ? financial.Financial_Doc.split('/').pop() : 'N/A'}</a></td>
                                    <td><input type="text" class="financial-score finAsignScr" data-vendor="${index}" value="${financialScore}" id="financialScore_${index}" ${isFinancialDisabled ? 'disabled' : ''} oninput="updateTotalScoreForFinance(${index})"></td>
                                </tr>`;
						}
					});

					financialTableBody += finRows;
					financialTableBody += `
                        <tr class="total-score">
                            <td colspan="3">FINANCIAL TOTAL SCORE</td>
                            <td class="total-cal" id="financial-total-${vendorIndex}">${financialScore}</td>
                        </tr>`;
				}

				// KRA Details
				if (vendor.evaluationKras) {
					let kraDetailsList = JSON.parse(vendor.evaluationKras);

					let kraVendorNameCell = '';
					let kraRows = '';

					if (kraDetailsList.length > 0) {
						kraVendorNameCell = `<td class="kra-vendorname" rowspan="${kraDetailsList.length}" value="${vendor.vendorId}">${vendor.vendorName || 'N/A'}</td>`;
					}

					kraDetailsList.forEach((kraDetails, index) => {
						let kraDesc = kraDetails.kraDesc || 'N/A';
						let shortDesc = kraDesc.length > 50 ? kraDesc.substring(0, 50) + "..." : kraDesc;
						let toggleBtn = kraDesc.length > 50 ? `<button class="toggle-desc-btn evolution-btn">Show More</button>` : "";

						if (index === 0) {
							kraRows += `
                                <tr>
                                    ${kraVendorNameCell}
                                    <td class="evalution-type d-none" value="${kraDetails.evaluationType}">${kraDetails.evaluationType || 'N/A'}</td>
                                    <td class="kra-sl-no d-none" value="${kraDetails.kraSlNo}">${kraDetails.kraSlNo || 'N/A'}</td>
                                    <td class="kra-name" value="${kraDetails.kraName}">${kraDetails.kraName}</td>
                                    <td class="kra-desc">
                                        <span class="short-desc">${shortDesc}</span>
                                        <span class="kra-full-desc full-desc" style="display: none;">${kraDesc}</span>
                                        ${toggleBtn}
                                    </td>
                                    <td class="category kra-weightage-score" id="kra_predefind_score_${vendorIndex}" value="${kraDetails.kraWeightage}">${kraDetails.kraWeightage}</td>
                                    <td><input type="text" class="kra-assign-score" data-max-score="${kraDetails.kraWeightage}" id="kra_assign_score_${vendorIndex}" data-vendor="${vendorIndex}" value="${krascore}"></td>
                                </tr>`;
						} else {
							kraRows += `
                                <tr>
                                    <td class="evalution-type d-none" value="${kraDetails.evaluationType}">${kraDetails.evaluationType || 'N/A'}</td>
                                    <td class="kra-sl-no d-none" value="${kraDetails.kraSlNo}">${kraDetails.kraSlNo || 'N/A'}</td>
                                    <td class="kra-name" value="${kraDetails.kraName}">${kraDetails.kraName}</td>
                                    <td class="kra-desc">
                                        <span class="short-desc">${shortDesc}</span>
                                        <span class="kra-full-desc full-desc" style="display: none;">${kraDesc}</span>
                                        ${toggleBtn}
                                    </td>
                                    <td class="category kra-weightage-score" id="kra_predefind_score_${vendorIndex}" value="${kraDetails.kraWeightage}">${kraDetails.kraWeightage}</td>
                                    <td><input type="text" class="kra-assign-score" data-max-score="${kraDetails.kraWeightage}" id="kra_assign_score_${vendorIndex}" data-vendor="${vendorIndex}" value="${krascore}"></td>
                                </tr>`;
						}
					});

					evalutionTableBody += kraRows;
					evalutionTableBody += `
                        <tr class="total-score">
                            <td colspan="4"> TOTAL KRA SCORE</td>
                            <td class="total-cal" id="kra-total-${vendorIndex}">${krascore}</td>
                        </tr>`;
				} else {
					$(".table-kradetails").hide();
				}
			});

			$(".loader").hide();

			document.querySelector('.criteria-details-table tbody').innerHTML = vendorTableBody;
			document.querySelector('.technical-details tbody').innerHTML = technicalTableBody;
			document.querySelector('.financial-details tbody').innerHTML = financialTableBody;
			document.querySelector('.table-kradetails tbody').innerHTML = evalutionTableBody;

			// Hide tables if no data
			if (hideTechnicalTable) {
				document.querySelector('.technical-details').style.display = 'none';
			} else {
				document.querySelector('.technical-details').style.display = 'table';
			}

			if (hideFinancialTable) {
				document.querySelector('.financial-details').style.display = 'none';
			} else {
				document.querySelector('.financial-details').style.display = 'table';
			}

			// Toggle content buttons for "Show More"
			document.querySelectorAll('.toggle-content-btn').forEach(button => {
				button.addEventListener('click', function() {
					const content = this.previousElementSibling;
					const shortContent = content.previousElementSibling;

					if (content.style.display === 'none') {
						content.style.display = 'block';
						shortContent.style.display = 'none';
						this.textContent = 'Show Less';
					} else {
						content.style.display = 'none';
						shortContent.style.display = 'block';
						this.textContent = 'Show More';
					}
				});
			});
		} else {
			console.log('Error fetching vendor data');
		}
	});
}


function updateTotalScore(cindex, vendorIndex) {
	$(`#vendor-total-${vendorIndex}`).html("");
	const vendorScoreInput = $(`#assignScore_${cindex}`);
	const vendorTotalCell = $(`#vendor-total-${vendorIndex}`);

	let assignScore = parseFloat(vendorScoreInput.val()) || 0;
	const maxScore = parseFloat(vendorScoreInput.attr('data-max-score')) || 0;

	if (assignScore > maxScore) {
		vendorScoreInput.val('');
		toastr.error("Assign Score Cannot Be Greater Than Predefined Score");
		assignScore = 0;
	}

	let totalCriteriaScore = 0;

	$(`.vendor-score[data-vendor="${vendorIndex}"]`).each(function() {
		let value = parseFloat($(this).val()) || 0;
		totalCriteriaScore += value;
	});

	let overallTotal = calculateOverallTotalScore();

	vendorTotalCell.text(overallTotal);
}

function calculateOverallTotalScore() {
	let overallTotal = 0;

	$(".vendor-score").each(function() {
		let value = parseFloat($(this).val()) || 0;
		overallTotal += value;
	});

	return overallTotal;
}



function updateTotalScoreForTech(vendorIndex) {
	const technicalScore = $(`#technicalScore_${vendorIndex}`).val();
	const techTotalScore = $(`#technical-total-${vendorIndex}`);
	techTotalScore.text(technicalScore != "" ? technicalScore : 0);
}



function updateTotalScoreForFinance(vendorIndex) {
	const financialScore = $(`#financialScore_${vendorIndex}`).val();
	const financeTotalScore = $(`#financial-total-${vendorIndex}`);
	financeTotalScore.text(financialScore != "" ? financialScore : 0);
}


/*function updateKraScore(vendorIndex) {
	const vendorScoreInput = $(`#kra_assign_score_${vendorIndex}`);
	const vendorTotalCell = $(`#kra-total-${vendorIndex}`);
	let assignScore = parseFloat(vendorScoreInput.val()) || 0; // Ensure it's a number
	const maxScore = parseFloat(vendorScoreInput.attr('data-max-score')) || 0;
	
	if (assignScore > maxScore) {
		assignScore = 0;
		$(`#kra_assign_score_${vendorIndex}`).val('');
		toastr.error("Assign Score Can Not Be Greater Then KRA Score");
	}
	vendorTotalCell.text(assignScore);
}*/



/*function recalculateVendorTotal(vendorIndex) {
	let totalScore = 0;
	document.querySelectorAll(`.vendor-score[data-vendor="${vendorIndex}"]`).forEach(input => {
		const score = parseInt(input.value, 10) || 0;
		totalScore += score;
	});

	document.getElementById(`vendor-total-${vendorIndex}`).textContent = totalScore;
}

function recalculateTechnicalTotal(vendorIndex) {
	let totalScore = 0;
	document.querySelectorAll(`.technical-score[data-vendor="${vendorIndex}"]`).forEach(input => {
		const score = parseInt(input.value, 10) || 0;
		totalScore += score;
	});

	document.getElementById(`technical-total-${vendorIndex}`).textContent = totalScore;
}

function recalculateFinancialTotal(vendorIndex) {
	let totalScore = 0;
	document.querySelectorAll(`.financial-score[data-vendor="${vendorIndex}"]`).forEach(input => {
		const score = parseInt(input.value, 10) || 0;
		totalScore += score;
	});

	document.getElementById(`financial-total-${vendorIndex}`).textContent = totalScore;
}*/

function getVendorData() {
	let vendors = [];
	const rows = document.querySelectorAll('.criteria-details-table tbody tr');
	let currentVendor = null;

	rows.forEach((row) => {
		const vendorNameCell = row.querySelector('.criteria-details-category');
		const criteriaShort = row.querySelector('.criteria-full')?.innerText.trim() || '';
		const criteriaIdElement = row.querySelector('td:nth-child(4)');
		const predefinedScoreElement = row.querySelector('td[id^="predefinedScore"]');
		const assignScoreElement = row.querySelector('.vendor-score');

		if (vendorNameCell) {
			if (currentVendor) {
				vendors.push(currentVendor);
			}

			currentVendor = {
				vendorId: vendorNameCell.getAttribute('value'),
				vendorName: vendorNameCell.innerText.trim(),
				totalCriteriaEvaluation: 0,
				criteriaList: []
			};
		}

		// If we have a criteria, push it to the current vendor's criteria list
		if (criteriaShort && currentVendor) {
			let criteriaObj = {
				criteriaId: criteriaIdElement ? criteriaIdElement.innerText.trim() : '',
				criteria: '',
				predefinedScore: predefinedScoreElement ? Number(predefinedScoreElement.innerText.trim()) : 0,
				assignScore: assignScoreElement ? Number(assignScoreElement.value.trim()) : 0
			};

			currentVendor.criteriaList.push(criteriaObj);
		}
	});

	// Push the last vendor if it exists
	if (currentVendor) {
		vendors.push(currentVendor);
	}

	// Calculate total criteria evaluation for each vendor
	vendors.forEach(vendor => {
		vendor.totalCriteriaEvaluation = vendor.criteriaList.reduce((total, criteria) => {
			return total + (criteria.assignScore || 0);
		}, 0);
	});

	return vendors;
}

function getTechnicalData() {
	let technicalVendors = [];
	const rows = document.querySelectorAll('.technical-details tbody tr');
	let currentVendor = null;
	rows.forEach(row => {
		const vendorNameCell = row.querySelector('.technical-details-category');
		if (vendorNameCell) {
			currentVendor = {
				vendorId: vendorNameCell.getAttribute('value'),
				vendorName: vendorNameCell.innerText.trim(),
				technicalScore: '',
				//technicalDescription: '',
				techAsignScore: row.querySelector('.techAsignScr').value.trim()
			};

			/*const technicalDescCell = row.querySelector('.technical-desc');
			if (technicalDescCell) {
				currentVendor.technicalDescription = technicalDescCell.innerText.trim();
			}*/
			technicalVendors.push(currentVendor);
		}

		if (currentVendor) {
			const totalScoreRow = row.nextElementSibling;
			if (totalScoreRow && totalScoreRow.classList.contains('total-score')) {
				const totalScoreCell = totalScoreRow.querySelector('.total-cal');
				if (totalScoreCell) {
					currentVendor.technicalScore = totalScoreCell.innerText.trim();
				}
			}
		}
	});
	return technicalVendors;
}


function getFinancialData() {
	let financialVendors = [];
	const rows = document.querySelectorAll('.financial-details tbody tr');
	let currentVendor = null;

	rows.forEach(row => {
		const vendorNameCell = row.querySelector('.financial-details-category');
		if (vendorNameCell) {
			currentVendor = {
				vendorId: vendorNameCell.getAttribute('value'),
				vendorName: vendorNameCell.innerText.trim(),
				financialScore: '',
				//financialDescription: '',
				finAsignScore: row.querySelector('.finAsignScr').value.trim()
			};
			const financialDescCell = row.querySelector('.financial-desc');
			/*if (financialDescCell) {
				currentVendor.financialDescription = financialDescCell.innerText.trim();
			}*/
			financialVendors.push(currentVendor);
		}

		if (currentVendor) {
			const totalScoreRow = row.nextElementSibling;
			if (totalScoreRow && totalScoreRow.classList.contains('total-score')) {
				const totalScoreCell = totalScoreRow.querySelector('.total-cal');
				if (totalScoreCell) {
					currentVendor.financialScore = totalScoreCell.innerText.trim();
				}
			}
		}
	});
	return financialVendors;
}

function getKraDetails() {
	let financialVendors = [];
	const rows = document.querySelectorAll('.table-kradetails tbody tr');

	let vendorMap = new Map(); // To store vendor data and avoid duplicates
	let lastVendorId = null; // Store last seen vendor ID

	rows.forEach(row => {
		const vendorNameCell = row.querySelector('.kra-vendorname');

		if (vendorNameCell) {
			lastVendorId = vendorNameCell.getAttribute('value') || `vendor_${vendorMap.size}`;
			let vendorName = vendorNameCell.innerText.trim();

			if (!vendorMap.has(lastVendorId)) {
				vendorMap.set(lastVendorId, {
					vendorId: lastVendorId,
					vendorName,
					financialScore: '',
					kraDetails: []
				});
			}
		}

		if (!lastVendorId) return; // Skip row if no vendor ID found

		let kraName = row.querySelector('.kra-name')?.innerText.trim() || 'N/A';
		let kraDesc = row.querySelector('.kra-desc .kra-full-desc')?.innerText.trim() || 'N/A';
		let kraWeightage = row.querySelector('.kra-weightage-score')?.innerText.trim() || '0';
		let kraAssignScore = row.querySelector('.kra-assign-score')?.value.trim() || '0';

		// Fetching hidden column values from `value` attribute
		let evallutionType = row.querySelector('.evalution-type')?.getAttribute('value') || 'N/A';
		let kraSlNo = row.querySelector('.kra-sl-no')?.getAttribute('value') || 'N/A';

		if (kraName !== 'N/A' && kraDesc !== 'N/A' && kraWeightage !== '0' && kraAssignScore !== '0') {
			vendorMap.get(lastVendorId).kraDetails.push({
				kraName,
				kraDesc,
				kraWeightage,
				kraAssignScore,
				evallutionType,
				kraSlNo
			});
		}

		// Handle total score row
		const totalScoreRow = row.nextElementSibling;
		if (totalScoreRow && totalScoreRow.classList.contains('total-score')) {
			const totalScoreCell = totalScoreRow.querySelector('.total-cal');

			if (totalScoreCell && lastVendorId && vendorMap.has(lastVendorId)) {
				vendorMap.get(lastVendorId).financialScore = totalScoreCell.innerText.trim();
			}
		}
	});

	return Array.from(vendorMap.values());
}




/*function getKraDetails() {
	let financialVendors = [];
	const rows = document.querySelectorAll('.table-kradetails tbody tr');

	let currentVendor = null;
	let vendorIndex = -1;

	rows.forEach(row => {
		const vendorNameCell = row.querySelector('.kra-vendorname');

		if (vendorNameCell) {
			vendorIndex++;
			currentVendor = {
				vendorId: vendorNameCell.getAttribute('value') || `vendor_${vendorIndex}`,
				vendorName: vendorNameCell.innerText.trim(),
				financialScore: '',
				kraDetails: []
			};
			financialVendors.push(currentVendor);
		}

		if (currentVendor) {
			let kraName = row.querySelector('.kra-name')?.innerText.trim() || 'N/A';
			let kraDesc = row.querySelector('.kra-desc .kra-full-desc')?.innerText.trim() || 'N/A';
			let kraWeightage = row.querySelector('.kra-weightage-score')?.innerText.trim() || '0';
			let kraAssignScore = row.querySelector('.kra-assign-score')?.value.trim() || '0';

			if (kraName !== 'N/A' && kraDesc !== 'N/A' && kraWeightage !== '0' && kraAssignScore !== '0') {
				currentVendor.kraDetails.push({
					kraName,
					kraDesc,
					kraWeightage,
					kraAssignScore
				});
			}

			const totalScoreRow = row.nextElementSibling;
			if (totalScoreRow && totalScoreRow.classList.contains('total-score')) {
				const totalScoreCell = totalScoreRow.querySelector('.total-cal');
				if (totalScoreCell) {
					currentVendor.financialScore = totalScoreCell.innerText.trim();
				}
			}
		}
	});

	return financialVendors;
}*/



function cancelBtn() {
	$("#vendorEvaluationModal").modal("hide");
}

function viewVendors(tenderId) {
	$(".loader").show();
	$("#evaluatedVendorsPage").hide();
	const vendorStatus = "awarded-vendors";

	agGrid.simpleHttpRequest({
		url: "tender-applied-vendor-data?tenderId=" + tenderId + "&status=" + vendorStatus,
	}).then(function(response) {
		if (response.code === "Success") {
			//$("#vendorProfileModal").modal("show");
			$(".awardedvendor-content").show();
			$("#ttbtn").hide();
			$("#vendorDetails").hide();
			$(".loader").hide();
			$("#applyBtn").hide();
			$("#awardVendorClose").show();
			$(".adminRemark-content").hide();

			const vendorsData = JSON.parse(response.body);
			const vendors = vendorsData[0].vendors;

			$(".awardedvendor-body-profile").empty();

			vendors.forEach(function(vendor, index) {
				const vendorCardHtml = `
	                    <div class="col-md-4 mb-4 mt-4">
	                        <div class="card vendor-card text-center " style="height:200px">
	                            <div class="card-body position-relative">
	                                <!-- Profile Image -->
	                                <img src="https://static.vecteezy.com/system/resources/previews/019/900/306/non_2x/happy-young-cute-illustration-face-profile-png.png"
	                                    alt="Vendor ${index + 1}" class="rounded-circle vendor-img">
	                                <!-- Verified Icon -->
	                                <img src="https://cdn-icons-png.flaticon.com/512/7595/7595571.png"
	                                    alt="Verified" class="position-absolute"
	                                    style="top: 10px; right: 10px; width: 40px; height: 40px;"
	                                    title="Verified">
	                                <!-- Vendor Details -->
	                                <h5 class="card-title mb-1 mt-3 font-weight-bold" title="${vendor.vendorName}">${vendor.vendorName}</h5>
	                                <p class="text-muted mb-1">${vendor.vendorPhone}</p>
	                                <p class="text-muted mb-1">${vendor.vendorEmail}</p>
	                                <p class="text-success font-weight-bold mb-1">Awarded</p>
	                            </div>
	                        </div>
	                    </div>`;

				// Append each vendor card to the row
				$(".awardedvendor-body-profile").append(vendorCardHtml);
			});
		}
	});
}


function closeProfileModal() {
	//$("#vendorProfileModal").modal("hide");
	$(".awardedvendor-content").hide();
	$("#ttbtn").hide();
	$("#vendorDetails").hide();
	$("#applyBtn").hide();
	$("#awardVendorClose").hide();
	$("#evaluatedVendorsPage").show();
}

function evaluateVendor() {
	$("#evluatedVendorDetails").removeClass('hidden');
	$("#vendorDetails").addClass('hidden');
	$("#cancelBtn").addClass('hidden');
	$("#applyBtn").addClass('hidden');
	$("#allocateBtn").removeClass('hidden');
	$("#allocatCancelBtn").removeClass('hidden');
	$("#ttbtn").addClass('hidden');
	$("#ttbtn1").removeClass('hidden');
	$(".adminRemark-content").hide();
	$("#evluatedVendorDetails").show();
	$("#ttbtn1").show();
	$("#allocateBtn").show();
	$("#adminRemarkSaveBtn").hide();
	evaluateVendorDemo();
}

function allocatCancel() {
	$("#evluatedVendorDetails").addClass('hidden');
	$("#vendorDetails").removeClass('hidden');
	$("#cancelBtn").removeClass('hidden');
	$("#applyBtn").removeClass('hidden');
	$("#allocateBtn").addClass('hidden');
	$("#allocatCancelBtn").addClass('hidden');
	$("#ttbtn").removeClass('hidden');
	$("#ttbtn1").addClass('hidden');
}

function evaluateVendorDemo() {
	let obj = {};

	var tenderId = $("#tenderId").text();
	obj.tenderId = tenderId;
	var vendorData = getVendorData();
	const technicalData = getTechnicalData();
	const financialData = getFinancialData();

	// Map over vendorData to create mergedData
	let criteriaSet = new Set();
	let mergedData = vendorData.map(vendor => {
		let technical = technicalData.find(tech => tech.vendorId === vendor.vendorId) || {};
		let financial = financialData.find(fin => fin.vendorId === vendor.vendorId) || {};

		vendor.criteriaList.forEach(criteria => {
			criteriaSet.add(criteria.criteria);
		});

		// Prepare criteria scores for this vendor
		let criteriaScores = vendor.criteriaList.map(criteria => {
			return {
				criteria: criteria.criteria,
				vendorWeightageScore: criteria.predefinedScore,
				vendorScore: criteria.assignScore
			};
		});


		return {
			tenderId: tenderId,
			vendorId: vendor.vendorId,
			vendorName: vendor.vendorName,
			totalCriteriaEvaluation: vendor.totalCriteriaEvaluation || 0,
			technicalTotalScore: technical.technicalScore || 0,
			technicalScore: technical.techAsignScore,
			financialTotalScore: financial.financialScore || 0,
			financialScore: financial.finAsignScore,
			totalEvaluation: (
				parseFloat(vendor.totalCriteriaEvaluation || 0) +
				parseFloat(technical.technicalScore || 0) +
				parseFloat(financial.financialScore || 0)
			),
			criteriaScores: criteriaScores
		};
	});

	// Create a structured object that holds the criteria separately
	let finalEvaluationData = {
		tenderId: tenderId,
		criteria: Array.from(criteriaSet),
		vendors: mergedData
	};

	dynamicEvaluationTable(finalEvaluationData);
}

const checkedVendors = [];
function dynamicEvaluationTable(mergedData) {
	const tbody = $('.vendor-table tbody');
	tbody.empty();

	const criteriaRows = [];

	if (mergedData.vendors.length === 0) {
		tbody.append('<tr><td colspan="100%">No data available</td></tr>');
		return;
	}

	let vendorCount = mergedData.vendors.length;
	let topScoredVendorCount = Math.min(3, vendorCount);
	const totalColumns = 2 + vendorCount * 2;
	let emptySectionColspan = totalColumns - (2 + topScoredVendorCount * 2);

	criteriaRows.push(`
    <tr>
        <th colspan="2"></th>
        <th colspan="${topScoredVendorCount * 2}">
            <div class="strp">Top Scored Vendors</div>
        </th>
        ${emptySectionColspan > 0 ? `
            <th colspan="${emptySectionColspan}">
                <div class="strp"></div>
            </th>
        ` : ''}
    </tr>
`);

	criteriaRows.push(`
    <tr class="vendors-columns">
        <th rowspan="2" class="criteria-column" style="width: 20%!important;">Criteria</th>
        <th rowspan="2" class="weightage-column" style="width: 10%!important;">Weightage</th>
        ${mergedData.vendors.map((vendor, vendorIndex) => `
            <th colspan="2" class="vendor-header header-style criteria-column" id="${vendor.vendorId}">
                <input type="checkbox" class="vendor-checkbox form-check-input" data-column-index="${vendorIndex}"/> ${vendor.vendorName}
            </th>
        `).join('')}
    </tr>
    <tr>
        ${mergedData.vendors.map(() => `
            <th colspan="2" class="header-cell-selection">Score</th>
        `).join('')}
    </tr>
`);

	const uniqueCriteria = mergedData.criteria;

	uniqueCriteria.forEach((criterion, index) => {
		var cleanedContent = criterion.replace(/['"]/g, '');

		criteriaRows.push(`
        <tr>
            <td class="criteria-column" title="${cleanedContent}">${cleanedContent.length > 50 ? cleanedContent.substring(0, 50) + '...' : cleanedContent}</td>
            <td class="weightage-column">${mergedData.vendors[0].criteriaScores[index].vendorWeightageScore}</td>
            ${mergedData.vendors.map(vendor => {
			const score = vendor.criteriaScores.find(cs => cs.criteria === criterion)?.vendorScore || 0; // Updated to match new structure
			return `<td colspan="2" class="total-column" id="vendorScore_${vendor.vendorId}">${score}</td>`;
		}).join('')}
        </tr>
    `);
	});

	criteriaRows.push(`
    <tr class="technical-row">
        <th colspan="2" class="criteria-column bg-white text-dark">Technical Proposal</th>
        <td colspan="${2 * mergedData.vendors.length}" class="header-cell-selection" style="background-color: #8698aa !important;">Technical Score</td>
    </tr>
`);

	criteriaRows.push(`
    <tr>
        <td colspan="2" class="criteria-column">Technical Analysis Score</td>
        ${mergedData.vendors.map(vendor => `
            <td colspan="2" class="total-column" id="vendorTechScore_${vendor.vendorId}">${vendor.technicalScore}</td>
        `).join('')}
    </tr>
`);

	criteriaRows.push(`
    <tr class="financial-row">
        <th colspan="2" class="criteria-column bg-light text-dark">Financial Proposal</th>
        <td colspan="${2 * mergedData.vendors.length}" class="header-cell-selection" style="background-color: #8698aa !important;">Financial Score</td>
    </tr>
`);

	criteriaRows.push(`
    <tr>
        <td colspan="2" class="criteria-column">Financial Analysis Score</td>
        ${mergedData.vendors.map(vendor => `
            <td colspan="2" class="total-column" id="vendorFinScore_${vendor.vendorId}">${vendor.financialScore}</td>
        `).join('')}
    </tr>
`);

	// Calculate total scores for each vendor
	const totalScores = mergedData.vendors.map(vendor => {
		const vendorScore = vendor.totalCriteriaEvaluation || 0; // Use totalCriteriaEvaluation
		const technicalScore = Number(vendor.technicalScore) || 0;
		const financialScore = Number(vendor.financialScore) || 0;

		return vendorScore + technicalScore + financialScore;
	});

	criteriaRows.push(`
    <tr class="total-row">
        <th colspan="2" class="criteria-column text-dark" >Total</th>
        ${mergedData.vendors.map((vendor, index) => `
            <td colspan="2" id="vendorTotalScore_${vendor.vendorId}">${totalScores[index]}</td>
        `).join('')}
    </tr>
`);

	tbody.append(criteriaRows.join(''));

	$('.vendor-checkbox').on('click', function() {
		const columnIndex = $(this).data('column-index');
		const isChecked = $(this).is(':checked');
		const bgClass = 'bg-changed';
		const vendorId = $(this).closest('th').attr('id');
		const vendorName = $(this).closest('th').text().trim().replace($(this).closest('th').find('input').next().text().trim(), '').trim();
		const headerCell = $(this).closest('th');
		const totalCriteriaEvaluation = $("#vendorScore_" + vendorId).text().trim();
		const technicalScore = $("#vendorTechScore_" + vendorId).text().trim();
		const financialScore = $("#vendorFinScore_" + vendorId).text().trim();
		const totalEvaluation = $("#vendorTotalScore_" + vendorId).text().trim();
		const tenderId = $("#tenderId").text().trim();

		// If the checkbox is checked, add the vendor to the array
		if (isChecked) {
			checkedVendors.push({ vendorId, vendorName, totalCriteriaEvaluation, technicalScore, financialScore, totalEvaluation, tenderId });
			headerCell.addClass(bgClass);
		} else {
			const index = checkedVendors.findIndex(vendor => vendor.vendorId === vendorId);
			if (index !== -1) {
				checkedVendors.splice(index, 1);
			}
			headerCell.removeClass(bgClass);
		}

		updateSelectedVendors();
	});
}



function updateSelectedVendors() {
	const container = $('#selectedVendorsContainer');
	container.empty();

	checkedVendors.forEach(vendor => {
		container.append(`
        <div class="selected-vendor-item">
            ${vendor.vendorName} (ID: ${vendor.vendorId})
            <span class="remove-vendor" data-vendor-id="${vendor.vendorId}" onclick="removeVendor(this);">&times;</span>
        </div>
    `);
	});
}

function removeVendor(element) {
	const vendorId = $(element).data('vendor-id');
	const index = checkedVendors.findIndex(vendor => vendor.vendorId === vendorId);
	if (index !== -1) {
		checkedVendors.splice(index, 1);
		updateSelectedVendors();
		$(`th#${vendorId} input.vendor-checkbox`).prop('checked', false).trigger('click');
		$(`th#${vendorId}`).removeClass('bg-changed');
	}
}




/*popup for remark to allocate vendor*/

function assignVendor() {

	if (checkedVendors.length === 0) {
		/*$("#messageParagraph").text(`Please select at least one row to assign.`);
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');*/
		var errortext = `Please select at least one row to assign.`;
		toastr.error(errortext);
		//showSnackbar(errortext);

	} else {
		//$("#remarksModal").modal("show");
		$(".adminRemark-content").show();
		$("#evluatedVendorDetails").hide();
		$("#adminRemarkSaveBtn").show();
		$("#allocateBtn").hide();
		$("#ttbtn1").hide();
		$("#adminRemarkSaveBtn").show();
	}
}
/*function saveAdminResponse(tenderid,) {
	console.log("asve admin click")
	$("#remarksModal").modal("hide");
	const vendorRemarks = $("#vendorRemarks").val();
	if (vendorRemarks == '' || vendorRemarks == null) {
		var errorText = `Please enter remarks.`;
		toastr.error(errorText);
		return;
	}
	var selectedData = allTenderGridOptions.api.getSelectedRows();

	var checkedVendor = selectedData[0].templateId;


	const payload = {
		selectedData: checkedVendor,
		vendorRemarks: vendorRemarks
	};
	
	
	console.log("payload for allocation is -->" , payload);

	$.ajax({
		url: 'assign-vendor?id='+checkedVendor,
		type: 'POST',
		contentType: 'application/json',
		dataType: "json",
		success: function(response) {
			cancelBtn();
			Cancel();
			const status = 'vendor';
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
					allocatCancel();
					Cancel();
					$("#adminRemarkSaveBtn").hide();
				} else {
					allTenderGridOptions.api.setRowData([]);
				}
			});
			$(".loader").hide();
			$("#messageParagraph").text(`Vendors assigned successfully.`);
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			var successtext = `Vendors assigned successfully.`;
			//showSnackbar(successtext);
			toastr.success(successtext);

		},
		error: function(error) {
			console.error('Error:', error);

		}
	});
}*/

function closeremarkModal() {
	$("#remarksModal").modal("hide");
}

function cancelContract() {
	$("#evaluatedVendorsPage").addClass('hidden');
	$("#tenderGrid").removeClass('hidden');
	$("#searchRowDiv").removeClass('hidden');
	$("#totalReq1").removeClass('hidden');
	$("#cancelContract").addClass('hidden');
	$("#addContract").removeClass('hidden');
}

function addContract() {
	$("#evaluatedVendorsPage").removeClass('hidden');
	$("#vendorDetails").hide();
	$("#ttbtn").hide();
	//$("#tenderGrid").addClass('hidden');
	//$("#searchRowDiv").addClass('hidden');
	$("#totalReq1").addClass('hidden');
	$("#cancelContract").removeClass('hidden');
	$("#addContract").addClass('hidden');
	const selectedRows = allTenderGridOptions.api.getSelectedRows();
	const status = selectedRows[0].awardedStatus;
	const tenderId = selectedRows[0].templateId;
	$("#awardTenderId").html(selectedRows[0].templateId);
	$("#awardTenderName").html(selectedRows[0].templateName);
	$("#awardEffectiveDate").html(selectedRows[0].effectiveDate);
	$("#awardTodayDate").html(getCurrentDateTime());

	const tenderPdfUrl = selectedRows[0].tenderPdf;
	if (tenderPdfUrl) {
		$("#awardPublishTenderDoc").html(`<a href="${tenderPdfUrl}" target="_blank">View Document</a>`);
	} else {
		$("#awardPublishTenderDoc").html("No Document Available");
	}
	$("#awardPublishDate").html("");

	const vendorStatus = "awarded-vendors";
	var vendorContainer = document.querySelector('.awarded-vendors-data');

	agGrid.simpleHttpRequest({
		url: "tender-applied-vendor-data?tenderId=" + tenderId + "&status=" + vendorStatus,
	}).then(function(response) {
		if (response.code === "Success" && response.body) {
			let parsedBody = JSON.parse(response.body);

			if (parsedBody && parsedBody.length > 0 && parsedBody[0].vendors) {
				vendorContainer.innerHTML = '';

				parsedBody[0].vendors.forEach(function(vendor) {
					let vendorCard = `
							<div class="col-md-2 mb-4 mt-4 parentVendorCard">
								<div class="card vendor-card-ev text-center innerVendorElement" onclick="selectCard(this, '${vendor.vendorName}')">
									<input type="hidden" id="vendorId" value="${vendor.vendorId}">
									<input type="hidden" id="contractId" value="${vendor.contractId}">
									<div class="card-body position-relative">
										<img src="https://static.vecteezy.com/system/resources/previews/019/900/306/non_2x/happy-young-cute-illustration-face-profile-png.png"
											alt="Vendor Image" class="rounded-circle vendor-img">
										<!-- Verified Icon -->
										<img src="https://cdn-icons-png.flaticon.com/512/7595/7595571.png"
											alt="Verified" class="position-absolute"
											style="top: 10px; right: 10px; width: 40px; height: 40px;" title="Verified">
										<h5 class="card-title mb-1 mt-3 font-weight-bold">${vendor.vendorName}</h5>
										<p class="text-muted mb-1">+91 ${vendor.vendorPhone}</p>
										<p class="text-muted mb-1">${vendor.vendorEmail}</p>
										<p class="text-success font-weight-bold mb-1 selected-awarded-vendors">
											Awarded</p>
									</div>
								</div>
							</div>`;

					vendorContainer.innerHTML += vendorCard;
				});
			} else {
				console.error("No vendors found in the parsed body.");
			}
		} else {
			console.error("Failed to fetch vendor data or response body is empty.");
		}
	});


}

function selectCard(cardElement, vendorName) {
	const allCards = document.querySelectorAll('.vendor-card-ev');
	allCards.forEach(card => {
		card.classList.remove('selected');
	});

	cardElement.classList.add('selected');

	document.getElementById('vendorDetailsContainer').style.display = 'block';
	document.getElementById('selectedVendorName').innerText = vendorName + " Details";
	document.getElementById('vendorInfo').innerText = "Details of " + vendorName + " will be shown here.";

	const vendor = getSelectedVendorId();
	const contractIds = getSelectedContractId();

	$.ajax({
		type: "GET",
		url: "get-omc-user-details",
		data: {
			vendorId: vendor,
			contractId: contractIds
		},
		success: function(response) {
			if (response.code === "Success") {
				const contractVersions = JSON.parse(response.body);

				// If there are contract versions, disable New Contract tab and enable Renewal Contract tab
				if (contractVersions.length > 0) {
					showTabContent('renewalContract');
					document.getElementById('tabNew').classList.add('disabled');
					document.getElementById('tabRenewal').classList.remove('disabled');

					// Prevent clicking on the New tab
					document.getElementById('tabNew').addEventListener('click', function(e) {
						e.preventDefault();
					});
				}
				// If there are no contract versions, enable New Contract tab and disable Renewal Contract tab
				else {
					showTabContent('newContract');
					document.getElementById('tabRenewal').classList.add('disabled');
					document.getElementById('tabNew').classList.remove('disabled');

					document.getElementById('tabRenewal').addEventListener('click', function(e) {
						e.preventDefault();
					});
				}
			} else if (response.code === "Failed") {
				showTabContent('newContract');
				document.getElementById('tabRenewal').classList.add('disabled');
				document.getElementById('tabNew').classList.remove('disabled');

				document.getElementById('tabRenewal').addEventListener('click', function(e) {
					e.preventDefault();
				});
			}
		}
	});
}


function showTabContent(tabId) {
	document.querySelectorAll('.tab-item').forEach(tab => {
		tab.classList.remove('active');
	});

	document.querySelector(`[data-tab="${tabId}"]`).classList.add('active');

	document.querySelectorAll('.contract-tab').forEach(content => {
		content.classList.remove('active');
		content.style.display = 'none';
	});

	document.getElementById(tabId).classList.add('active');
	document.getElementById(tabId).style.display = 'block';

	getContractVersionsDetail();
}

function toggleMenu(index) {
	const menu = document.getElementById(`menuOptions-${index}`);
	const isMenuOpen = menu.style.display === "block";

	const allDropdowns = document.querySelectorAll(".menu-options");
	allDropdowns.forEach(dropdown => {
		dropdown.style.display = "none";
	});

	menu.style.display = isMenuOpen ? "none" : "block";
}

window.onclick = function(event) {
	if (!event.target.matches('.menu-btn')) {
		var dropdowns = document.getElementsByClassName("menu-options");
		for (var i = 0; i < dropdowns.length; i++) {
			var openDropdown = dropdowns[i];
			if (openDropdown.style.display === "block") {
				openDropdown.style.display = "none";
			}
		}
	}
}

function getSelectedVendorId() {
	const selectedCard = $('.card.vendor-card-ev.selected');

	if (selectedCard.length > 0) {
		const vendorId = selectedCard.find('#vendorId').val();
		return vendorId;
	}

	return null; // If no selected card
}

function getSelectedContractId() {
	const selectedCard = $('.card.vendor-card-ev.selected');

	if (selectedCard.length > 0) {
		const contractId = selectedCard.find('#contractId').val();
		return contractId;
	}

	return null; // If no selected card
}

function getContractVersionsDetail() {
	const vendor = getSelectedVendorId();
	const contractIds = getSelectedContractId();

	$.ajax({
		type: "GET",
		url: "get-omc-user-details",
		data: {
			vendorId: vendor,
			contractId: contractIds
		},
		success: function(response) {
			if (response.code === "Success") {
				const contractVersions = JSON.parse(response.body);
				const pdfContainer = document.querySelector('.venored-contract.versions');
				pdfContainer.innerHTML = '';

				contractVersions.forEach((versionData, index) => {
					const pdfUrl = versionData.pdfVersionWise;
					const version = versionData.version;
					const versionDiv = document.createElement('div');
					versionDiv.classList.add('col');

					const pdfViewer = `
		                        <h5>${version}</h5>
		                        <div class="position-relative">
		                            <div class="pdf-menu position-absolute top-0 end-0 mx-4 my-1">
		                                <button class="menu-btn" onclick="toggleMenu(${index})">☰</button>
		                                <ul class="menu-options" id="menuOptions-${index}" style="display: none;">
		                                   <li><a href="#" onclick="downloadPDF('${pdfUrl}')">Download Contract</a></li>
                              			   <li><a href="#" onclick="showPDF('${pdfUrl}')">Show Contract</a></li>
		                                </ul>
		                            </div>
		                            <!-- PDF Viewer -->
		                            <div id="pdfViewer-${index}" style="margin-top: 10px;">
		                                 <!--<object type="application/pdf" data="${pdfUrl}" width="250" height="200"></object>-->
				                      <img src="../assets/images/pdf_demo.png" alt="PDF Icon" width="200" height="200" />
		                            </div>
		                        </div>
		                    `;

					versionDiv.innerHTML = pdfViewer;
					pdfContainer.appendChild(versionDiv);
				});
			} else if (response.code === "Failed") {
				const pdfContainer = document.querySelector('.venored-contract.versions');
				pdfContainer.innerHTML = '<p>No contract versions available.</p>';
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching contract details:", error);
		}
	});
}

function showPDF(pdfUrl) {
	// Open the PDF URL in a new tab
	window.open(pdfUrl, '_blank');
}

function downloadPDF(pdfUrl) {
	window.open(pdfUrl, '_blank');
}


function createNewContract() {
	const vendor = getSelectedVendorId();
	const tenderId = $("#awardTenderId").text();

	const encryptedVendor = btoa(vendor);
	const encryptedTender = btoa(tenderId);
	window.location.href = `/purchase/contract-creation?vendor=${encryptedVendor}&tenderId=${encryptedTender}`;

}

function renewContract() {
	const vendor = getSelectedVendorId();
	const contractId = getSelectedContractId();

	const encryptedVendor = btoa(vendor);
	const encryptedContract = btoa(contractId);

	window.location.href = `/purchase/contract-creation?vendor=${encryptedVendor}&contract=${encryptedContract}`;
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

	allTenderGridOptions.api.setQuickFilter('');

	allTenderGridOptions.api.refreshCells({ force: true });
}


function onQuickFilterChanged() {
	allTenderGridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}

function saveTeamEvalution() {
	let vendorCriteriaDetails = getVendorData();
	let technicalDetails = getTechnicalData();
	let financialDetails = getFinancialData();
	let kraDetails = getKraDetails();


	let finalJson = [];

	let mergedTechnicalDetails = technicalDetails.map(techItem => {
		let criteriaItem = vendorCriteriaDetails.find(c => c.vendorId === techItem.vendorId);
		return {
			vendorId: techItem.vendorId,
			vendorName: techItem.vendorName,
			criteria: criteriaItem ? criteriaItem : {},
			technical: techItem
		};
	});

	if (mergedTechnicalDetails.length > 0) {
		finalJson.push({
			"evaluationType": "Technical Evaluation",
			"evaluationDetails": mergedTechnicalDetails
		});
	}

	if (financialDetails.length > 0) {
		finalJson.push({
			"evaluationType": "Financial Evaluation",
			"evaluationDetails": financialDetails
		});
	}

	if (kraDetails.length > 0) {
		let kraEvaluationType = kraDetails[0]?.kraDetails[0]?.evallutionType || 'N/A';
		finalJson.push({
			"evaluationType": kraEvaluationType,
			"evaluationDetails": kraDetails
		});
	}

	saveEvaluationDetails(finalJson);
}
/*
function saveTeamEvalution() {
	let vendorCriteriaDetails = getVendorData();
	let technicalDetails = getTechnicalData();
	let financialDetails = getFinancialData();
	let kraDetails = getKraDetails();

	console.log("kra details-->", kraDetails);

	// Create an array with only non-empty evaluation data
	let finalJson = [];

	if (vendorCriteriaDetails.length > 0) {
		finalJson.push({
			"evaluationType": "technical",
			"evaluationDetails": vendorCriteriaDetails
		});
	}
	if (technicalDetails.length > 0) {
		finalJson.push({
			"evaluationType": "technical",
			"evaluationDetails": technicalDetails
		});
	}
	if (financialDetails.length > 0) {
		finalJson.push({
			"evaluationType": "financial",
			"evaluationDetails": financialDetails
		});
	}
	if (kraDetails.length > 0) {
		let kraEvaluationType = kraDetails[0]?.kraDetails[0]?.evallutionType || 'N/A';
		finalJson.push({
			"evaluationType": kraEvaluationType,
			"evaluationDetails": kraDetails
		});
	}

	console.log("Final Merged JSON -->", finalJson);
	saveEvaluationDetails(finalJson);
}
*/


function saveEvaluationDetails(finalJson) {
	var allValid = true;
	let tenderId = $("#headertenderid").text();
	if (allValid) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "vendor-evaluation-details-add?id=" + tenderId,
			async: true,
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(finalJson),
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


function fetchVendorList(id) {
	let tenderId = $("#evalution_tenderId").text();
	let cardContainerBox = $("#cardsParentContainer");

	$.ajax({
		type: "GET",
		url: "vendor-evaluation-fetchvendorlist?tenderId=" + id,
		dataType: "json",
		success: function(response) {

			let jsonResponse = JSON.parse(response.body);

			tenderTrackerWizard(jsonResponse);
			cardContainerBox.empty();

			jsonResponse.forEach(vendorObj => {
				let vendor = vendorObj.VendorDetails;
				let cardHtml = `
				    <div class="card vendor-card text-center p-2 m-2" style="min-height: 200px; max-height: 220px;">
				        <div class="card-body p-2 vendor-card-body">
				            <!-- Profile Image -->
				            <img src="https://static.vecteezy.com/system/resources/previews/019/900/306/non_2x/happy-young-cute-illustration-face-profile-png.png" 
				                alt="Vendor" class="vendor-img" style="width: 60px; height: 60px;">
				            <!-- Vendor Details -->
				            <h6 class="card-title mt-1 mb-1 vendor_name" title="${vendor.VendorName}" style="font-size: 14px;">
				                ${vendor.VendorName}
				            </h6>
				            <p class="contact-info mb-1" style="font-size: 12px;">
				                <i class="bi bi-telephone-fill"></i> ${vendor.VendorMobile}
				            </p>
				            <p class="contact-info mb-1" 
				                style="font-size: 12px;
				                      max-width: 140px; /* Ensure a fixed width */
				                      white-space: nowrap; 
				                      overflow: hidden; 
				                      text-overflow: ellipsis; 
				                      display: inline-block; 
				                      vertical-align: middle;">
				                <i class="bi bi-envelope-fill"></i> ${vendor.VendorEmail}
				            </p>
				            <span class="status-badge" style="font-size: 10px; padding: 2px 6px;">Vendor ID: ${vendor.VendorId}</span>
				            <div class="button-group mt-2">
				                <button class="btn btn-sm award_btn" style="border-radius: 10px; padding: 5px 12px; font-size: 12px;">
				                    Award
				                </button>
				                <button class="btn btn-sm details_btn" 
				                    onclick="fetchVendorEvalutionDetail('${vendor.VendorId}', '${vendor.TenderId}')" 
				                    style="border-radius: 10px; padding: 5px 12px; font-size: 12px;">
				                    Details
				                </button>
				            </div>
				        </div>
				    </div>
				`;



				cardContainerBox.append(cardHtml);
			});
		},
		error: function(xhr, status, error) {
			console.error("Error fetching vendor list:", error);
		}
	});
}


var evaluationAssignTotalScore = [];

function fetchVendorEvalutionDetail(vendorId, tenderid) {
	return new Promise((resolve, reject) => {
		$.ajax({
			type: "GET",
			url: "vendor-evaluation-vendorevalutiondetails?tenderId=" + tenderid + "&vendorId=" + vendorId,
			dataType: "json",
			success: function(response) {
				if (response.code == "Success" && response.body[0] != null) {
					let jsonData = JSON.parse(response.body);
					console.log("JSON data is -->", jsonData);
					$(".ven-ev-container").removeClass('d-none');
					$("#cardsParentContainer").addClass('d-none');
					$(".ven-cls").removeClass('d-none');
					$(".ven-ev-container").show();

					document.getElementById("vendorHeader").innerText = `Vendor: ${jsonData[0].VendorName} (ID: ${jsonData[0].VendorId})`;

					let evalBody = "";
					let kraBody = "";
					let scores = {
						"Registration": 100,
						"criteria": 0,
						"Technical": 0,
						"Financial": 0
					};

					jsonData.forEach(item => {
						let evalJson = JSON.parse(item.EvaluationJson);


						if (evalJson.criteria) {
							evalJson.criteria.criteriaList.forEach((c, index) => {
								scores.Technical += parseInt(c.assignScore);
								evalBody += `<tr>
                                    ${index === 0 ? `<td rowspan="${evalJson.criteria.criteriaList.length}">Criteria</td>` : ''}
                                    <td>${item.EvaluationUserName}</td>
                                    <td>${c.predefinedScore}</td>
                                    <td>${c.assignScore}</td>
                                </tr>`;
							});
						}

						if (evalJson.technical) {
							scores.Technical += parseInt(evalJson.technical.techAsignScore);
							evalBody += `<tr>
                                <td>Technical</td>
                                <td>${item.EvaluationUserName}</td>
                                <td>No Predefined Score</td>
                                <td>${evalJson.technical.techAsignScore}</td>
                            </tr>`;
						}

						if (evalJson.finAsignScore) {
							scores.Financial += parseInt(evalJson.finAsignScore);
							evalBody += `<tr>
                                <td>Financial</td>
                                <td>${item.EvaluationUserName}</td>
                                <td>No Predefined Score</td>
                                <td>${evalJson.finAsignScore}</td>
                            </tr>`;
						}

						if (evalJson.kraDetails) {
							evalJson.kraDetails.forEach((kra, index) => {
								kraBody += `<tr>
                                    ${index === 0 ? `<td rowspan="${evalJson.kraDetails.length}">${item.EvaluationType}</td>` : ''}
                                    <td>${kra.kraName}</td>
                                    <td>${kra.kraDesc}</td>
                                    <td>${kra.kraWeightage}</td>
                                    <td>${kra.kraAssignScore}</td>
                                    <td>${item.EvaluationUserName}</td>
                                </tr>`;

								if (!scores[item.EvaluationType]) {
									scores[item.EvaluationType] = 0;
								}
								scores[item.EvaluationType] += parseInt(kra.kraAssignScore);
							});

							$("#kraTable").show();
						}
						else {

							$("#kraTable").hide();
						}
					});

					document.getElementById("evaluationBody").innerHTML = evalBody;
					document.getElementById("kraBody").innerHTML = kraBody;

					evaluationAssignTotalScore = Object.entries(scores).map(([key, value]) => ({ type: key, totalScore: value }));

					// Wizard Section

					let TenderEvTypes = jsonData[0].TenderAssignEvType;
					let vendorTrackingDtls = JSON.parse(jsonData[0].VendorTrackingStatus);
					TenderEvTypes.unshift("Registration");
					TenderEvTypes.push("Onboarding");

					console.log("---------------");
					console.log("TenderEvTypes-->", TenderEvTypes);
					console.log("vendorTrackingDtls--->", vendorTrackingDtls);
					console.log("---------------");

					let wizardContainer = $("#evalutionTypesWizrd");
					let wizardHtml = TenderEvTypes.map((element, index) => {
						let trimmedElement = element.trim();
						let isActive = vendorTrackingDtls.hasOwnProperty(trimmedElement) && vendorTrackingDtls[trimmedElement] == 1;

						return `<div class="step evStep_${index} ${isActive ? `active` : ``}" data-step="${trimmedElement}">
					                <div class="step-number">${index + 1}</div>
					                <div class="step-label">${trimmedElement}</div>
					            </div>`;
					}).join("");

					wizardContainer.html(wizardHtml);

					let totalSteps = TenderEvTypes.length; // Total evaluation steps
					let completedSteps = TenderEvTypes.filter(step => vendorTrackingDtls[step] == 1).length; // Count completed evaluations
					let progressPercentage = Math.round((completedSteps / totalSteps) * 100); // Calculate progress
					console.log("completedSteps:", completedSteps); // Debugging
					$(".vendor-progress-bar-container")
						.css("width", `${progressPercentage}%`)
						.attr("aria-valuenow", progressPercentage);


					//	animateProgressAndSteps(progressBar, $(".vendor-progress-bar-container"), $(".step"), progressPercentage, completedEvaluations);


					resolve();
				} else {
					// toastr.error("No Record Found");
					//reject("No Record Found");
					let scores = {
						"Registration": 100,
						"criteria": 0,
						"Technical": 0,
						"Financial": 0
					};
					evaluationAssignTotalScore = Object.entries(scores).map(([key, value]) => ({ type: key, totalScore: value }));
					document.getElementById("evaluationBody").innerHTML = '';
					document.getElementById("kraBody").innerHTML = '';
					$(".ven-ev-container").hide();

					resolve();
				}
			},
			error: function(xhr, status, error) {
				console.error("Error fetching vendor list:", error);
				reject(error);
			}
		});
	});
}


/*function fetchVendorEvalutionDetail(vendorId, tenderid) {
	let tenderId = $("#evalution_tenderId").text();
	let cardContainerBox = $("#cardsParentContainer");

	$.ajax({
		type: "GET",
		url: "vendor-evaluation-vendorevalutiondetails?tenderId=" + tenderid + "&vendorId=" + vendorId,
		dataType: "json",
		success: function(response) {

			if (response.code == "Success" && response.body[0] != null) {
				let jsonData = JSON.parse(response.body);
				
				console.log("JSON data is -->" , jsonData);
				$(".ven-ev-container").removeClass('d-none');
				$("#cardsParentContainer").addClass('d-none');
				$(".ven-cls").removeClass('d-none');


				document.getElementById("vendorHeader").innerText = `Vendor: ${jsonData[0].VendorName} (ID: ${jsonData[0].VendorId})`;
				let evalBody = "";
				let kraBody = "";
				let criteriaScore = 0;
				let technicalScore = 0;
				let financialScore = 0;
				
				jsonData.forEach(item => {
					let evalJson = JSON.parse(item.EvaluationJson);
					if (evalJson.criteria) {
						evalJson.criteria.criteriaList.forEach((c, index) => {
							criteriaScore +=parseInt(c.assignScore);
							evalBody += `<tr>
									   ${index === 0 ? `<td rowspan="${evalJson.criteria.criteriaList.length}">Criteria</td>` : ''}
									   <td>${item.EvaluationUserName}</td>
									   <td>${c.predefinedScore}</td>
									   <td>${c.assignScore}</td>
								   </tr>`;
						});
					}
					if (evalJson.technical) {
						technicalScore += parseInt(evalJson.technical.techAsignScore);
						evalBody += `<tr>
								   <td>Technical</td>
								   <td>${item.EvaluationUserName}</td>
								   <td>No Predefined Score</td>
								   <td>${evalJson.technical.techAsignScore}</td>
							   </tr>`;
					}
					if (evalJson.finAsignScore) {
						financialScore += parseInt(evalJson.finAsignScore);
						evalBody += `<tr>
								   <td>Financial</td>
								   <td>${item.EvaluationUserName}</td>
								   <td>No Predefined Score</td>
								   <td>${evalJson.finAsignScore}</td>
							   </tr>`;
					}

					if (evalJson.kraDetails) {
						evalJson.kraDetails.forEach((kra, index) => {
							kraBody += `<tr>
									   ${index === 0 ? `<td rowspan="${evalJson.kraDetails.length}">${item.EvaluationType}</td>` : ''}
									   <td>${kra.kraName}</td>
									   <td>${kra.kraDesc}</td>
									   <td>${kra.kraWeightage}</td>
									   <td>${kra.kraAssignScore}</td>
									   <td>${item.EvaluationUserName}</td>
								   </tr>`;
						});
					}

				});
				document.getElementById("evaluationBody").innerHTML = evalBody;
				document.getElementById("kraBody").innerHTML = kraBody;
				
				
				console.log("criteria score-->",criteriaScore);
				console.log("technical score-->",technicalScore);
				console.log("financial score -->", financialScore);
				
			}
			else {
				toastr.error("No Record Found");
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching vendor list:", error);
		}
	});
}*/


/*function fetchVendorEvalutionDetail(vendorId, tenderid) {
	let tenderId = $("#evalution_tenderId").text();
	let cardContainerBox = $("#cardsParentContainer");

	$.ajax({
		type: "GET",
		url: "vendor-evaluation-vendorevalutiondetails?tenderId=" + tenderid + "&vendorId=" + vendorId,
		dataType: "json",
		success: function(response) {
			if (response.code == "Success" && response.body[0] != null) {

				let jsonResponse = JSON.parse(response.body);

				console.log("RESPONSE IN JSON -->", jsonResponse);
				$(".ven-ev-container").removeClass('d-none');
				$("#cardsParentContainer").addClass('d-none');
				$(".ven-cls").removeClass('d-none');

				// Organize data by Vendor ID and Evaluation Type
				var groupedData = {};

				jsonResponse.forEach(item => {
					var vendorId = item.VendorId;
					var vendorName = item.VendorName;  // Store Vendor Name
					var evalType = item.EvaluationType;
					var userId = item.EvaluationUserName;
					var evalJson = evalType === "technical" || evalType === "financial"
						? JSON.parse(item.EvaluationJson)
						: JSON.parse(item.EvaluationJson).kraDetails;

					console.log("eval jsn-->", evalJson);

					var assignedScore = 0;
					var predefinedScore = 0;

					// Extract Assigned & Predefined Score based on evaluation type
					if (Array.isArray(evalJson)) {
						assignedScore = evalJson[0]?.assignScore || 0;
						predefinedScore = evalJson[0]?.predefinedScore || 0;
					} else {
						if (evalType === "technical") {
							assignedScore = parseInt(evalJson.techAsignScore) || 0;
							predefinedScore = parseInt(evalJson.technicalScore) || 0;
						} else if (evalType === "financial") {
							assignedScore = parseInt(evalJson.finAsignScore) || 0;
							predefinedScore = parseInt(evalJson.financialScore) || 0;
						}
					}

					// Store grouped data
					if (!groupedData[vendorId]) {
						groupedData[vendorId] = {
							"vendorName": vendorName, // Store vendor name
							"criteria": [],
							"technical": [],
							"financial": []
						};
					}

					groupedData[vendorId][evalType].push({
						eval_type: evalType,
						user: userId,
						score: assignedScore,
						predefinedScore: predefinedScore
					});
				});

				// Populate the table
				var tableHead = $("#evaluationTable thead").empty();
				var tableBody = $("#evaluationTable tbody").empty();

				$.each(groupedData, function(vendorId, evaluations) {
					var vendorName = evaluations.vendorName; // Get vendor name

					// Append Vendor Name & ID Row before column headers
					tableHead.append(`<tr class='table-dark text-white'>
					<th colspan='4'>Vendor: ${vendorName} (ID: ${vendorId})</th>
				</tr>`);
					tableHead.append(`<tr>
					<th>Evaluation Type</th>
					<th>Evaluation User</th>
					<th>Assigned Score</th>
					<th>Predefined Score</th>
				</tr>`);

					// Append Evaluation Sections
					appendSection("Criteria", evaluations.criteria);
					appendSection("Technical", evaluations.technical);
					appendSection("Financial", evaluations.financial);
				});

			}
			else {
				toastr.error("No records found");
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching vendor list:", error);
		}
	});
}*/


function appendSection(title, data) {
	// Populate the table
	var tableHead = $("#evaluationTable thead");
	var tableBody = $("#evaluationTable tbody");
	if (data.length > 0) {
		//tableBody.append(`<tr class='section-header'><td colspan='4'>${title} Evaluation</td></tr>`);

		let totalScore = 0;
		let totalPredefined = 0;

		data.forEach(item => {
			tableBody.append(`<tr>
                    <td>${item.eval_type.toUpperCase()}</td>
                    <td>${item.user}</td>
                    <td>${item.score}</td>
                    <td>${item.predefinedScore}</td>
                </tr>`);
			totalScore += item.score;
			totalPredefined += item.predefinedScore;
		});

		// Append Total Row
		tableBody.append(`<tr class='table-secondary'>
                <td colspan="2"><strong>Total ${title} Score</strong></td>
                <td><strong>${totalScore}</strong></td>
                <td><strong>${totalPredefined}</strong></td>
            </tr>`);
	}
}

function closeVendorDtls() {
	$(".ven-ev-container").addClass('d-none');
	$("#cardsParentContainer").removeClass('d-none');
	$(".ven-cls").addClass('d-none');
}


function filterVendorsByStep(stepNumber) {
	// Update wizard steps UI
	document.querySelectorAll('.step').forEach(step => {
		step.classList.remove('active', 'completed');
		const stepNum = parseInt(step.getAttribute('data-step'));

		if (stepNum === stepNumber) {
			step.classList.add('active');
		} else if (stepNum < stepNumber) {
			step.classList.add('completed');
		}
	});

	// Filter vendors
	const filteredVendors = vendors.filter(v => v.currentStep === stepNumber);
	renderVendorCards(filteredVendors);
}



function tenderTrackerWizard(jsonData) {
	console.log("preview section json -->", jsonData);
	if (jsonData) {
		let evalutionList = jsonData[0].EvaluationTypes;
		evalutionList.unshift("Registration");
		evalutionList.push("Onboarding");

		let wizardContainer = $("#evalutionTypesWizrd");
		let cardContainerBox = $("#appliedVendorContiner");

		let cardHtml = jsonData.map(vendorObj => {
			let vendor = vendorObj.VendorDetails;

			let evaluationStatus = vendor.EvaluationStatus ? JSON.stringify(vendor.EvaluationStatus) : '[]'; // Default to empty array

			let parsedStatus = [];
			let progressPercentage = 0;
			let evaluationResult = "";

			try {
				parsedStatus = JSON.parse(evaluationStatus);
				if (!Array.isArray(parsedStatus)) {
					parsedStatus = [];
				}
				evaluationResult = parsedStatus.join(", ");

				let completedSteps = 1; // Start with Registration completed
				let totalSteps = evalutionList.length;

				evalutionList.forEach(step => {
					if (parsedStatus.includes(step.trim())) {
						completedSteps++;
					}
				});

				progressPercentage = Math.round((completedSteps / totalSteps) * 100);
			} catch (error) {
				console.error("Error parsing evaluationStatus:", error);
			}

			return `
                <div class="card applied-vendor-card vendor-card text-center p-2 m-2" 
                     style="min-height: 200px; max-height: 237px;" 
                     data-evaluation='${JSON.stringify(parsedStatus)}' onclick="selectVendorCard(this)">
					 <div class="tick-icon">✔️</div>
                    <div class="card-body p-2 vendor-card-body">
                        <img src="https://static.vecteezy.com/system/resources/previews/019/900/306/non_2x/happy-young-cute-illustration-face-profile-png.png" 
                             alt="Vendor" class="vendor-img" style="width: 60px; height: 60px;">
                        <h6 class="card-title mt-1 mb-1 vendor_name" title="${vendor.VendorName}" style="font-size: 14px;">
                            ${vendor.VendorName}
                        </h6>
                        <p class="contact-info mb-1" style="font-size: 12px;">
                            <i class="bi bi-telephone-fill"></i> ${vendor.VendorMobile}
                        </p>
                        <p class="contact-info mb-1" 
                           style="font-size: 12px; max-width: 140px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
                            <i class="bi bi-envelope-fill"></i> ${vendor.VendorEmail}
                        </p>
                        <span class="status-badge" style="font-size: 10px; padding: 2px 6px;margin-bottom: 4px;">Vendor ID: ${vendor.VendorId}</span>
                        <input type="hidden" value="0">
                        <div class="progress">
                            <div class="progress-bar" id="approvalProgress-vendor-${vendor.VendorId}"
                                 role="progressbar" style="width: ${progressPercentage}%;">
                                ${progressPercentage}%
                            </div>
                        </div>
                        <div class="button-group mt-2">
                            <button class="btn btn-sm details_btn" 
                                    onclick="fetchVendorTrackerData('${vendor.VendorId}', '${vendor.TenderId}','${evalutionList}','${evaluationResult}',this)" 
                                    style="border-radius: 10px; padding: 5px 12px; font-size: 12px;">
                                Details
                            </button>
							
							${vendor.EvalutionAssignScoreTotal != null
					? `<button class="btn btn-sm details_btn score_btn" 
							                style="border-radius: 10px; padding: 5px 12px; font-size: 12px;">
							            ${vendor.EvalutionAssignScoreTotal}
							       </button>`
					: ""}

						
                        </div>
                    </div>
                </div>
            `;
		}).join("");

		cardContainerBox.html(cardHtml);
	}
}

/*function tenderTrackerWizard(jsonData) {

	console.log("preview section json -->", jsonData);
	if (jsonData) {
		let evalutionList = jsonData[0].EvaluationTypes;
		evalutionList.unshift("Registration");
		evalutionList.push("Onboarding");

		let wizardContainer = $("#evalutionTypesWizrd");
		let cardContainerBox = $("#appliedVendorContiner");



		// Generate Wizard Steps
		let wizardHtml = evalutionList.map((element, index) => `
			<div class="step ${index === 0 ? "active" : ""}" data-step="${element}">
				<div class="step-number">${index + 1}</div>
				<div class="step-label">${element}</div>
			</div>
		`).join("");

		wizardContainer.html(wizardHtml);

		// Generate Vendor Cards
		let cardHtml = jsonData.map(vendorObj => {
			let vendor = vendorObj.VendorDetails;
			console.log("vendor-->", vendor);
			let evaluationStatus = JSON.stringify(vendor.EvaluationStatus); // Store evaluation status
			console.log("evaluationStatus-->" , evaluationStatus)
			let parsedStatus = JSON.parse(evaluationStatus);
			let evaluationResult = parsedStatus.join(", ");
			let completedSteps = 1;
			let totalSteps = evalutionList.length;

			let completedEvaluations = evaluationStatus.split(',').map(item => item.trim());
			evalutionList.forEach((step, index) => {
				if (parsedStatus.includes(step.trim())) {
					completedSteps++;
				}
			});

			let progressPercentage = Math.round((completedSteps / totalSteps) * 100);

			return `
				<div class="card applied-vendor-card vendor-card text-center p-2 m-2" 
					 style="min-height: 200px; max-height: 237px;" 
					 data-evaluation='${evaluationStatus !== null ? JSON.stringify(evaluationStatus) : "[]"}'>
					<div class="card-body p-2 vendor-card-body">
						<img src="https://static.vecteezy.com/system/resources/previews/019/900/306/non_2x/happy-young-cute-illustration-face-profile-png.png" 
							 alt="Vendor" class="vendor-img" style="width: 60px; height: 60px;">
						<h6 class="card-title mt-1 mb-1 vendor_name" title="${vendor.VendorName}" style="font-size: 14px;">
							${vendor.VendorName}
						</h6>
						<p class="contact-info mb-1" style="font-size: 12px;">
							<i class="bi bi-telephone-fill"></i> ${vendor.VendorMobile}
						</p>
						<p class="contact-info mb-1" 
						   style="font-size: 12px; max-width: 140px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
							<i class="bi bi-envelope-fill"></i> ${vendor.VendorEmail}
						</p>
						<span class="status-badge" style="font-size: 10px; padding: 2px 6px;margin-bottom: 4px;">Vendor ID: ${vendor.VendorId}</span>
						<input type="hidden" value="0">
						<div class="progress">
										   <div class="progress-bar" id="approvalProgress-vendor-${vendor.VendorId}"
												role="progressbar" style="width: ${progressPercentage}%;">
											   ${progressPercentage}%
										   </div>
									   </div>
						<div class="button-group mt-2">
							<button class="btn btn-sm details_btn" 
									onclick="fetchVendorTrackerData('${vendor.VendorId}', '${vendor.TenderId}','${evalutionList}','${evaluationResult}')" 
									style="border-radius: 10px; padding: 5px 12px; font-size: 12px;">
								Details
							</button>
						</div>
					</div>
				</div>
			`;
		}).join("");

		cardContainerBox.html(cardHtml);

		// Add Click Event to Filter Cards
		$(".step").click(function() {
			let selectedStep = $(this).attr("data-step");

			$(".step").removeClass("active");
			$(this).addClass("active");

			$(".applied-vendor-card").each(function() {
				let evaluationList = JSON.parse($(this).attr("data-evaluation"));

				if (selectedStep === "Registration" || evaluationList.includes(selectedStep)) {
					$(this).show();
				} else {
					$(this).hide();
				}
			});
		});
	}
}*/


function fetchVendorTrackerData(vendorId, tenderId, evaluationList, evaluationStatus, buttonElement) {
	let vendorCard = $(buttonElement).closest(".vendor-card-body");

	let vendorName = vendorCard.find(".vendor_name").text().trim();
	let vendorPhone = vendorCard.find(".contact-info i.bi-telephone-fill").parent().text().trim();
	let vendorEmail = vendorCard.find(".contact-info i.bi-envelope-fill").parent().text().trim();
	let vendorImage = vendorCard.find(".vendor-img").attr("src");

	$("#vendor_tracker_close_btn").removeClass("d-none");


	$("#vendorName").text(vendorName);
	$("#vendorEmail").text(vendorEmail)
		.attr("title", vendorEmail)
		.tooltip("dispose")
		.tooltip();
	$("#vendorPhone").text(vendorPhone);
	$("#vendorLogo").attr("src", vendorImage);
	$("#vendor_id").text(vendorId);

	fetchVendorEvalutionDetail(vendorId, tenderId)
		.then(() => {

			$("#applied-vendor-details").removeClass("d-none");
			$("#appliedVendorContiner").addClass("d-none");

			let evaluationArray = evaluationList.split(',');

			let evaluationListContainer = $("#evaluationList");
			let progressBar = $("#approvalProgress");

			evaluationListContainer.empty();
			let totalSteps = evaluationArray.length;

			let completedEvaluations = evaluationStatus
				? evaluationStatus.split(',').map(item => item.trim())
				: [];

			if (!completedEvaluations.includes("Registration")) {
				completedEvaluations.unshift("Registration");
			}
			let completedSteps = completedEvaluations.length;

			if (!completedEvaluations.length) {
				completedEvaluations.push(evaluationArray[0]);
				completedSteps = 1;
			}

			evaluationArray.forEach((step, index) => {
				let stepId = `step${index + 1}Status`;
				let status = "Pending";
				let badgeClass = "bg-secondary";

				if (completedEvaluations.includes(step.trim())) {
					status = "Completed";
					badgeClass = "bg-success";
				}

				let evaluationData = evaluationAssignTotalScore.find(item => {
					let typeLower = item.type.toLowerCase();
					let stepLower = step.toLowerCase();
					return stepLower.split(" ").some(word => typeLower.includes(word));
				});
				let totalScore = evaluationData ? evaluationData.totalScore : 0;


				let listItem = `
				    <li class="list-group-item d-flex justify-content-between align-items-center">
				        ${step} 
				        <div>
				            <span class="badge ${badgeClass}" id="${stepId}">${status}</span>
				            ${!(step === "Registration" || step === "Onboarding") ?
						`<span class="badge ${badgeClass}" id="step2Score">${status == "Pending" ? 0 : `${totalScore}`}</span>` : ""}
				        </div>
				    </li>
				`;


				evaluationListContainer.append(listItem);
			});

			let progressPercentage = Math.round((completedSteps / totalSteps) * 100);
			if (!evaluationStatus) {
				progressPercentage = Math.round((1 / totalSteps) * 100);
			}

			/*let wizardContainer = $("#evalutionTypesWizrd");
			let wizardHtml = evaluationArray.map((element, index) => {
				let isActive = completedEvaluations.includes(element) ? "active" : "";
				return `<div class="step ${isActive}" data-step="${element}">
							<div class="step-number">${index + 1}</div>
							<div class="step-label">${element}</div>
						</div>`;
			}).join("");

			wizardContainer.html(wizardHtml);*/
			animateProgressAndSteps(progressBar, $(".vendor-progress-bar-container"), $(".step"), progressPercentage, completedEvaluations);
		})
		.catch(error => console.error("Error in fetchVendorTrackerData:", error));
}


function animateProgressAndSteps(progressElement, barElement, steps, targetPercentage, completedEvaluations) {
	let currentWidth = 0; // Start from 0%
	let stepCount = completedEvaluations.length;
	let totalSteps = steps.length;
	let stepInterval = targetPercentage / totalSteps;
	let index = 0;

	let interval = setInterval(() => {
		currentWidth += stepInterval;
		progressElement.css("width", currentWidth + "%").text(Math.round(currentWidth) + "%");
		//barElement.css("width", currentWidth + "%");

		if (index < stepCount) {
			$(steps[index]).addClass("active");
			index++;
		}

		if (currentWidth >= targetPercentage) {
			clearInterval(interval);
		}
	}, 100);
}




function cancelVendorContainer() {
	$("#applied-vendor-details").addClass("d-none");
	$("#appliedVendorContiner").removeClass("d-none");
	$("#vendor_tracker_close_btn").addClass("d-none");
}


function updateStepStatus() {
	let status = { "Registration": 1 };  // Ensure "Registration" is always included
	let totalSteps = $(".wizard-steps .step").length;
	let completedSteps = 0;

	$(".wizard-steps .step").each(function() {
		let stepName = $(this).data("step");
		let isActive = $(this).hasClass("active");

		status[stepName] = isActive ? 1 : 0;
		if (isActive) completedSteps++;
	});

	let progressPercentage = Math.round((completedSteps / totalSteps) * 100);
	$(".vendor-progress-bar-container")
		.css("width", `${progressPercentage}%`)
		.attr("aria-valuenow", progressPercentage);


	let tenderId = $("#headertenderid").text().trim();
	let vendorId = $("#vendor_id").text().trim();

	if (!tenderId || !vendorId) {
		return;
	}

	let payload = {
		"EVJSON": status,
		"Tender_id": tenderId,
		"vendor_id": vendorId,
		"Vendor_Reject_status": 0
	};


	$.ajax({
		url: 'vendor-evaluation-vendortrackingstatus',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(response) {
			console.log("Success:", response);
		},
		error: function(error) {
			console.error('Error:', error);
		}
	});

}

function nextStepFunc() {
	let currentActiveSteps = $(".wizard-steps .step.active");
	let nextStep = currentActiveSteps.last().next(".step");
	let vendorId = $("#vendor_id").text();
	let tenderid = $("#headertenderid").text();

	if (nextStep.length > 0) {
		nextStep.addClass("active");

		if (nextStep.data("step") === "Onboarding") {
			//vendorAllocation(vendorId, tenderid);
		}
	}

	updateStepStatus();
}

/*function vendorAllocation(vendorId, tenderid) {
	let scores = {}; // Store scores dynamically
	let activeType = null;
	let rowspanCount = 0;

	// Process Evaluation Table
	$("#evaluationBody tr").each(function () {
		let firstCell = $(this).find("td:first");
		let firstCellText = firstCell.text().trim();
		let lastCell = $(this).find("td:last").text().trim();

		if (firstCell.attr("rowspan")) {
			activeType = firstCellText; 
			rowspanCount = parseInt(firstCell.attr("rowspan"), 10); 
			scores[activeType] = 0; 
		}

		if (activeType && rowspanCount > 0) {
			let value = parseInt(lastCell, 10);
			if (!isNaN(value)) {
				scores[activeType] += value; 
			}
			rowspanCount--; 
			if (rowspanCount === 0) {
				activeType = null; 
			}
		} else if (!firstCell.attr("rowspan")) {
			let value = parseInt(lastCell, 10);
			if (!isNaN(value)) {
				scores[firstCellText] = (scores[firstCellText] || 0) + value; // Store direct values
			}
		}
	});

	activeType = null;
	rowspanCount = 0;

	$("#kraBody tr").each(function () {
		let firstCell = $(this).find("td:first");
		let firstCellText = firstCell.text().trim();
		let assignedScoreCell = $(this).find("td:nth-child(5)"); 

		if (firstCell.attr("rowspan")) {
			activeType = firstCellText; // Set active type (e.g., "RSA")
			rowspanCount = parseInt(firstCell.attr("rowspan"), 10); // Get dynamic rowspan count
			scores[activeType] = scores[activeType] || 0; // Ensure key exists
		}

		if (activeType && rowspanCount > 0) {
			let value = parseInt(assignedScoreCell.text().trim(), 10);
			if (!isNaN(value)) {
				scores[activeType] += value;
			}
			rowspanCount--; // Decrease the rowspan counter
			if (rowspanCount === 0) {
				activeType = null; // Reset when rowspan ends
			}
		} else if (!firstCell.attr("rowspan")) {
			let rowType = firstCellText;
			scores[rowType] = scores[rowType] || 0; // Ensure key exists
			let value = parseInt(assignedScoreCell.text().trim(), 10);
			if (!isNaN(value)) {
				scores[rowType] += value;
			}
		}
	});

	// Calculate total score dynamically
	let totalScore = Object.values(scores).reduce((sum, val) => sum + val, 0);

	let result = {
		"EvaluationScore": scores,
		"totalScore": totalScore
	};

	console.log(result);
}*/


/*
function vendorAllocation(vendorId, tenderid) {
	let scores = {}; // Store scores dynamically
	let activeType = null;
	let rowspanCount = 0;

	// Process Evaluation Table
	$("#evaluationBody tr").each(function () {
		let firstCell = $(this).find("td:first");
		let firstCellText = firstCell.text().trim();
		let lastCell = $(this).find("td:last").text().trim();

		if (firstCell.attr("rowspan")) {
			activeType = firstCellText;
			rowspanCount = parseInt(firstCell.attr("rowspan"), 10);
			scores[activeType] = 0;
		}

		if (activeType) {
			let value = parseInt(lastCell, 10);
			if (!isNaN(value)) {
				scores[activeType] += value;
			}
			rowspanCount--;
			if (rowspanCount === 0) activeType = null;
		} else {
			let value = parseInt(lastCell, 10);
			if (!isNaN(value)) {
				scores[firstCellText] = (scores[firstCellText] || 0) + value;
			}
		}
	});

	activeType = null;
	rowspanCount = 0;

	$("#kraBody tr").each(function () {
		let firstCell = $(this).find("td:first");
		let firstCellText = firstCell.text().trim();
		let assignedScoreCell = $(this).find("td:nth-child(5)").text().trim();

		if (firstCell.attr("rowspan")) {
			activeType = firstCellText;
			rowspanCount = parseInt(firstCell.attr("rowspan"), 10);
			scores[activeType] = 0;
		}

		if (activeType) {
			let value = parseInt(assignedScoreCell, 10);
			if (!isNaN(value)) {
				scores[activeType] += value;
			}
			rowspanCount--;
			if (rowspanCount === 0) activeType = null;
		} else {
			let value = parseInt(assignedScoreCell, 10);
			if (!isNaN(value)) {
				scores[firstCellText] = (scores[firstCellText] || 0) + value;
			}
		}
	});

	let totalScore = Object.values(scores).reduce((sum, val) => sum + val, 0);

	let result = {
		"EvaluationScore": scores,
		"totalScore": totalScore
	};

	console.log("----*",result);
}

*/


let selectedVendors = [];


function selectVendorCard(element) {
	$("#vendor_allocate_user_btn").removeClass('d-none');

	let vendorId = $(element).find(".status-badge").text().replace("Vendor ID: ", "").trim();
	let vendorName = $(element).find(".vendor_name").text().trim();
	let vendorScore = $(element).find(".score_btn").text().trim();
	let tickIcon = $(element).find(".tick-icon");

	if ($(element).hasClass("selected_vendors")) {
		// Remove selection
		$(element).removeClass("selected_vendors");
		selectedVendors = selectedVendors.filter(vendor => vendor.vendorId !== vendorId);
		tickIcon.fadeOut(200);
	} else {
		// Add selection
		$(element).addClass("selected_vendors");
		selectedVendors.push({ vendorId, vendorName, totalScore: vendorScore });
		tickIcon.fadeIn(200);
	}

	// Generate the final JSON structure

}




function allocateVendor() {
	let finalJson = {
		tender: $("#headertenderid").text(),
		allocateDetails: selectedVendors
	};
	console.log("Final JSON:", JSON.stringify(finalJson, null, 2));
	$.ajax({
		type: "POST",
		url: "vendor-evaluation-vendorAllocationDetails",
		async: true,
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(finalJson),
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
