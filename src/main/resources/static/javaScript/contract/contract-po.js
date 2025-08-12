let headerTabs = "";
$(document).ready(function() {

	$("#vendorsResponseLogs").attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#changeReq").attr("disabled", true);

	notificationUpdate();

	var gridDiv = document.querySelector('#contractPoGrid');
	new agGrid.Grid(gridDiv, allTenderGridOptions);

	allTenderGridOptions.api.setRowData([]);

	$(".loader").show();

	agGrid.simpleHttpRequest({
		url: 'contract-po-view-contract'
	}).then(function(response) {
		if (response.code === "Success") {
			$(".loader").hide();
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
			$(".loader").hide();
			allTenderGridOptions.api.setRowData([]);
		}
	});

	// Add an event listener to handle row selection
	allTenderGridOptions.api.addEventListener(
		'selectionChanged', onSelectionChanged);
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});
});

var tenderGridDefs = [{
	headerName: "Contracts",

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
		headerName: "Contract Id",
		field: "templateId",
		width: 200,
		/*cellRenderer: function(params) {
			return `<a onclick="tenderDetails(${params.rowIndex})" href="javascript:void(0)">
					${params.data.templateId}
				</a>`;
		}*/
	}, {
		headerName: "Vendors",
		field: "vendorId",
		width: 200,
		/*cellRenderer: function(params) {
			return `<a href="javascript:void(0)">
					${params.data.vendorId}
				</a>`;
		}*/
	}, {
		headerName: ' Tender Id',
		field: "tenderId"
	}, {
		headerName: "Contract Name",
		field: "contractname",
		width: 330
	}, {
		headerName: "Description",
		field: "contractDescript",
		width: 300
	}, {
		headerName: "Vendor Status",
		field: "vendorContractStatus",
		width: 200,
		//hide: true,
		cellRenderer: function(params) {
			const status = params.value;

			if (status === "Approved") {
				return `
			                <span title="Awarded" style="color: green; text-decoration: none; font-weight: 700;">
			                    <i class="fa fa-check-circle" aria-hidden="true" style="padding-right: 4px;"></i>Approved
			                </span>
			            `;
			} else if (status === "Change Request") {
				return `<a title="Change Requested" style="color: #ff7c05; text-decoration: none;">
			                <i class="fa fa-exclamation-circle" aria-hidden="true" style="padding-right: 4px;"></i>Change Request
			            </a>`;
			} else {
				return `<a title="Not Responded" style="color: #808080; text-decoration: none;">
			                <i class="fa fa-ellipsis-h" aria-hidden="true" style="padding-right: 4px;"></i>Not Responded
			            </a>`;
			}
		}
	}, {
		headerName: "CONTRACT CreatedBy",
		field: "createdBy",
		width: 200
	}, {
		headerName: "CONTRACT CreatedOn",
		field: "createdOn",
		width: 200
	}, {
		headerName: "Published Pdf",
		field: "publishPdf",
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
	},
	onSelectionChanged: rowSelect
};



function tenderDetails(rowIndex) {
	gridApi.selectIndex(rowIndex, false, false);
}


function rowSelect() {
	var selectedRows = allTenderGridOptions.api.getSelectedRows();
	if (selectedRows.length > 0) {
		viewLogs();
	}
	console.log("Selected rows-->", selectedRows);

}

var selectedId = "";
function onSelectionChanged() {
	var selectedRows = allTenderGridOptions.api.getSelectedRows();
	console.log("rows-->", selectedRows);

	if (selectedRows.length > 0) {
		var status = selectedRows[0].vendorContractStatus;
		var contractid = selectedRows[0].templateId;
		$("#headercontractid").text(contractid);

		if (status === 'Approved') {
			$("#changeReq").attr("disabled", true);
			$("#approve").attr("disabled", true);
			$("#vendorsResponseLogs").attr("disabled", false);
		} else {
			$("#changeReq").attr("disabled", false);
			$("#approve").attr("disabled", false);
			$("#vendorsResponseLogs").attr("disabled", false);
		}
	} else {
		$("#headercontractid").text('');
		$("#changeReq").attr("disabled", true);
		$("#approve").attr("disabled", true);
		$("#vendorsResponseLogs").attr("disabled", true);
	}
}




/*All function for update notification */

function notificationUpdate() {

	var vendorIdd = localStorage.getItem("vendorId");
	var notificationId = localStorage.getItem("notificationId");
	if (vendorIdd != '') {
		$.ajax({
			type: "GET",
			url: "contract-po-view-update-notification?id=" + notificationId,
			success: function(response) {
				if (response.message == "Success") {
					localStorage.removeItem("vendorIdd");
					//localStorage.removeItem("createdBy");
					localStorage.removeItem("notificationId");
					updateNotificationCount();

				} else {
					("error" + console.message)
				}

			}
		});
	}
}

function updateNotificationCount() {
	let userId = sessionStorage.getItem("userId")
	let notify = "notification"


	$.ajax({
		type: "GET",
		url: "/purchase/manage-vendor-workflow-notification?userId=" + userId + "&type=" + notify,
		success: function(response) {
			if (response.code == "Success") {

				let data = JSON.parse(response.body);

				console.log("PKJ -----$$$$$$>>>>.", data);
				$("#mySidenav-crm").find('div').remove();

				let div = "";

				data.forEach((task) => {
					let priorityDescription = '';
					switch (task.type) {
						case 'Raise Ticket':
							priorityDescription = sessionStorage.getItem("internalReview");
							break;

						case 'Approval':
							priorityDescription = sessionStorage.getItem("greivanceApprove");
							break;

						case 'contract':
							priorityDescription = sessionStorage.getItem("contractPo");
							break;

					}


					let notificationCard = `
					    <div class="position-box">
					        <h6 onclick="getSelectedDoc('${task.vendorId}','${task.createdBy}','${task.notificationId}'); 
					            getPurchaseUrl('MOD001', 
					            '${sessionStorage.getItem("vmsFun")}', 
					          '${priorityDescription}'
					        )">
					            ${task.type}
					        </h6>
					        <ul>
					            <li class="head-li"><i class="far fa-user icon-colr"></i>&nbsp;&nbsp;${task.createdByEmpName}</li>
					            <li class="head-li"><i class="far fa-calendar icon-colr"></i>&nbsp;&nbsp;${task.createdOn}</li>
					            <li class="head-li"><i class="far fa-calendar icon-colr"></i>&nbsp;&nbsp;${task.type}</li>
					        </ul>
					    </div>`;

					$("#mySidenav-crm").append(notificationCard);
				});

				var divCount = document.getElementById("mySidenav-crm").getElementsByTagName("div").length;

				if (divCount > 0) {
					$("#badge-notification-count").css("display", "block");
					$("#badge-notification-count").text(divCount)
					$("#badge-icount").css("display", "block");
				} else {
					$("#mySidenav-crm").append("<div class='no-notify-msg-div'><i class='ti-comment'></i><p class='no-notify-msg'>You Have No New Notifications</p></div>");
					$("#mySidenav-crm").addClass("no-notification")
				}
			} else {
				$("#mySidenav-crm").find("div").remove()
				$("#mySidenav-crm").append("<div class='no-notify-msg-div'><i class='ti-comment'></i><p class='no-notify-msg'>You Have No New Notifications</p></div>");
				$("#mySidenav-crm").addClass("no-notification")
				$("#badge-notification-count").css("display", "none");
				$("#badge-notification-count").text("")
				$("#badge-icount").css("display", "none");
				$("#mySidenav-crm").removeClass("no-notification");
			}
			$('.loader').hide();
		}
	});
}

var statusId = "";
function approve() {
	$(".review-content").show();
	$(".logs-content").hide();
	$("#remarkModalLabel").html("");
	$("#remarkModalLabel").html("Vendor Approval");
	$("#remarksModal").modal("show");
	$("#vendorResponse-btn").show();
	statusId = "Approved";
}

function changeReq() {
	$(".review-content").show();
	$(".logs-content").hide();
	$("#remarkModalLabel").html("");
	$("#remarkModalLabel").html("Vendor Change Request");
	$("#remarksModal").modal("show");
	$("#vendorResponse-btn").show();
	statusId = "Change Request";
}

function saveVendorResponse() {

	var selectedRows = allTenderGridOptions.api.getSelectedRows();
	const contract_id = selectedRows[0].templateId;
	const tender_id = selectedRows[0].tenderId;
	const remark = $("#vendorRemarks").val();

	var datas = [{
		contractId: contract_id,
		vendorStatus: statusId,
		remark: remark,
		tenderId: tender_id
	}];

	console.log("data is coming like this=====>", datas);

	saveResponseData(datas);
}

function saveResponseData(datas) {
	$.ajax({
		type: "POST",
		url: "contract-po-add-response",
		contentType: "application/json",
		data: JSON.stringify({ datas: datas }),
		success: function(response) {
			if (response.code == "Success") {
				$("body").removeClass("overlay");
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#remarksModal").modal("hide");
				agGrid.simpleHttpRequest({
					url: 'contract-po-view-contract'
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
						$("#vendorsResponseLogs").attr("disabled", true);
						$("#approve").attr("disabled", true);
						$("#changeReq").attr("disabled", true);
					} else {
						allTenderGridOptions.api.setRowData([]);
					}
				});
			} else {
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}
			//viewResponseData();
		},
		error: function(error) {
			console.error(error);
		}
	});
} function viewLogs() {
	var selectedRows = allTenderGridOptions.api.getSelectedRows();
	const contract_id = selectedRows[0].templateId;
	const tender_id = selectedRows[0].tenderId;

	$.ajax({
		url: "contract-po-view-response?contractId=" + contract_id + "&tenderId=" + tender_id,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			if (response.code === "Success") {
				const logs = JSON.parse(response.body);
				const allLogs = [...(logs.contract_data || []), ...(logs.pdf_data || [])];


				allLogs.sort((a, b) => {
					const dateA = parseDate(a.createdOn);
					const dateB = parseDate(b.createdOn);
					return dateA - dateB;
				});


				let timelineHtml = '';
				const adminRoleId = $("#adminRoleId").val();
				const vendorRoleId = $("#vendorRoleId").val();

				allLogs.forEach((log, index) => {
					let iconClass = "fas fa-info-circle";
					let textClass = "template-title";

					const isVendorLog = log.logStatus === "Vendor Log";
					const isPdfLog = log.logStatus === "Pdf Log";

					if (isVendorLog) {
						// Handling Vendor logs
						if (log.status === "Approved") {
							iconClass = "fas fa-check-circle text-success";
							textClass = "template-title-success";
							bodyClass = "body-success";
							headerClass = "header-success";
						} else if (log.status === "Change Request") {
							iconClass = "fas fa-exclamation-circle text-warning";
							textClass = "template-title-warning";
							bodyClass = "body-warning";
							headerClass = "header-warning";
						}

						const showAdminComment = adminRoleId === 'rol001' && log.adminRemark && log.adminRemark !== "null";
						const showVendorComment = vendorRoleId === 'rol030' && log.adminRemark && log.adminRemark !== "null";
						const showInputField = adminRoleId === 'rol001' && (!log.adminRemark || log.adminRemark === "null");

						timelineHtml += `
                            <li>
                                <input type="hidden" id="logId_${index}" value="${log.logId}">
                                <div class="timeline-time">
                                    <span class="date align-text-rg" id="dateTime_${index}">${log.createdDay}</span>
                                    <span class="date align-text-rg" id="dateTime_${index}">${log.createdOn}</span>
                                </div>
                                <div class="timeline-icon">
                                    <a href="javascript:;">&nbsp;</a>
                                </div>
                                <div class="timeline-body ${bodyClass}">
                                    <div class="timeline-header vendor-info ${headerClass}">
                                        <div>
                                            <span class="userimage"><img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt=""></span>
                                            <span id="username_${index}" class="vendor-name">${log.vendorName} (Vendor)</span>
                                        </div>
                                        <span class="username">
                                            <a id="userStatus_${index}" class="vendor-status" data-toggle="collapse" href="#collapseExample_${index}"
                                               style="color: ${log.vendorResponseStatus === 'Current' ? 'green' : log.vendorResponseStatus === 'Previous' ? '#bebebe' : 'black'};">
                                                ${log.vendorResponseStatus}
                                            </a>
                                            <small></small>
                                        </span>
                                    </div>
                                    <div class="timeline-content">
                                        <h4 class="${textClass}" id="status_${index}">
                                            <i class="${iconClass} fa-fw"></i> ${log.status}
                                            <a href="javascript:void(0);" class="toggle-collapse" data-target="#collapseExample_${index}">
                                                <i class="fas fa-chevron-down" id="toggleIcon_${index}" style="font-size: 16px;"></i>
                                            </a>
                                        </h4>
                                        <p class="collapse vendor-cmt" id="collapseExample_${index}">${log.remark}</p>
                                    </div>
                                    <div class="timeline-comment-box" style="display: ${showAdminComment || showVendorComment || showInputField ? 'block' : 'none'};">
                                        <div class="user">
                                            <img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="User Avatar">
                                        </div>
                                        <div class="input">
                                            ${showAdminComment ? `
                                            <p class="admin-comment" style="color: #007BFF;">
                                                <strong>${log.adminRemarkBy}</strong> - ${log.adminRemark}
                                                <span class="admin-comment-date" style="color: #6c757d; font-size: 0.85em; margin-left: 10px;">
                                                    (${log.adminRemarkDate})
                                                </span>
                                            </p>` : ''}
            
                                            ${showVendorComment ? `
                                            <p class="admin-comment" style="color: #007BFF;">
                                                <strong>${log.adminRemarkBy}</strong> - ${log.adminRemark}
                                                <span class="admin-comment-date" style="color: #6c757d; font-size: 0.85em; margin-left: 10px;">
                                                    (${log.adminRemarkDate})
                                                </span>
                                            </p>` : ''}
            
                                            ${showInputField ? `
                                            <div class="row">
                                                <div class="col-lg-9">
                                                    <div class="form-group">
                                                        <input type="text" class="form-control" 
                                                            placeholder="Write a comment..." 
                                                            id="adminComment_${index}" 
                                                            value="">
                                                    </div>
                                                </div> 
                                                <div class="col-lg-3">
                                                    <div class="form-group">
                                                        <span class="input-group-btn">
                                                            <button class="btn btn-primary f-s-12 rounded-corner" type="button" onclick="submitComment(${index});">
                                                                <i class="fas fa-paper-plane"></i> Comment
                                                            </button>
                                                        </span>
                                                    </div>
                                                </div> 
                                            </div>` : ''}
                                        </div>
                                    </div>
                                </div>
                            </li>
                        `;
					} else if (isPdfLog) {
						timelineHtml += `
                            <li>
                                <input type="hidden" id="logId_${index}" value="${log.logId}">
                                <div class="timeline-time">
                                    <span class="date align-text-rg" id="dateTime_${index}">${log.createdDay}</span>
                                    <span class="date align-text-rg" id="dateTime_${index}">${log.createdOn}</span>
                                </div>
                                <div class="timeline-icon">
                                    <a href="javascript:;">&nbsp;</a>
                                </div>
                                <div class=" timeline-body-pdf">
                                    <div class="timeline-header vendor-info" style="border-bottom: 1px solid #b90000!important;">
                                        <div>
                                            <span class="userimage"><img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt=""></span>
                                            <span id="username_${index}" class="vendor-name">${log.createdBy} (Admin)</span>
                                        </div>
                                        <span class="username" style="cursor: pointer;" data-toggle="collapse" href="#lessPdf_${index}">
                                            <a id="userStatus_${index}" class="vendor-status">
                                                ${log.status}  <span style="font-weight: 700;font-size: 15px;">(${log.contractVersion})</span>
                                            </a>
                                        </span>
                                    </div>
                                    <div class="timeline-content">
                                        <div class="collapse" id="lessPdf_${index}">
                                            <h4 id="status_${index}">
                                                ${log.remark}
                                            </h4>
                                            <p class="vendor-cmt">An updated version of the contract is now available. Please take a moment to review the changes and ensure you're up to date with the latest terms and conditions.</p>
                                        </div>
                                        <div class="released-by-section">
                                            <strong>Released By:</strong> <span id="releasedBy_${index}" class="released-by">${log.createdBy}</span>
                                        </div>
                                        <div class="contract-section">
                                            <a href="${log.contractDocument}" target="_blank" title="View or Download Contract">
                                                <i class="fas fa-file-pdf" style="font-size: 20Px;"></i> View Contract
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        `;
					}
				});

				$("#vendorResponse .timeline").html(timelineHtml);
				$("#vendorTimeline").modal("show");

				// Add click event for toggle collapse icons
				$('.toggle-collapse').on('click', function() {
					const target = $(this).data('target');
					$(target).collapse('toggle');

					// Change icon based on the collapse state
					const icon = $(this).find('i');
					if (icon.hasClass('fa-chevron-down')) {
						icon.removeClass('fa-chevron-down').addClass('fa-chevron-up');
					} else {
						icon.removeClass('fa-chevron-up').addClass('fa-chevron-down');
					}
				});
			}
		}
	});
}

// Function to parse the date in 'dd-MM-yyyy hh:mm:ss a' format
function parseDate(dateString) {
	const [datePart, timePart, period] = dateString.split(' ');
	const [day, month, year] = datePart.split('-').map(Number);
	let [hours, minutes, seconds] = timePart.split(':').map(Number);

	// Adjust hours for AM/PM
	if (period === 'PM' && hours < 12) {
		hours += 12; // Convert to 24-hour format
	} else if (period === 'AM' && hours === 12) {
		hours = 0; // Midnight case
	}

	// Return a new Date object
	return new Date(year, month - 1, day, hours, minutes, seconds);
}

/*function viewLogsBase() {
	var selectedRows = allTenderGridOptions.api.getSelectedRows();
	const contract_id = selectedRows[0].templateId;
	const tender_id = selectedRows[0].tenderId;

	$.ajax({
		url: "contract-po-view-response?contractId=" + contract_id + "&tenderId=" + tender_id,
		type: 'GET',
		dataType: 'json',
		success: function (response) {
			if (response.code == "Success") {
				//const logs = JSON.parse(response.body);
				const contrctLogs = JSON.parse(response.body).contract_data;
				const pdfLogs = JSON.parse(response.body).pdf_data;
				let timelineHtml = '';
				const adminRoleId = $("#adminRoleId").val();
				const vendorRoleId = $("#vendorRoleId").val();

				if (contrctLogs != "null" && contrctLogs != null) {
					contrctLogs.forEach((log, index) => {
						let iconClass = "fas fa-info-circle";
						let textClass = "template-title";
						if (log.status === "Approved") {
							iconClass = "fas fa-check-circle text-success";
							textClass = "template-title-sucess";
						} else if (log.status === "Change Request") {
							iconClass = "fas fa-exclamation-circle text-warning";
							textClass = "template-title-warning";
						}

						// Determine if admin comments and input should be shown
						const showAdminComment = adminRoleId === 'rol001' && log.adminRemark && log.adminRemark !== "null";
						const showVendorComment = vendorRoleId === 'rol030' && log.adminRemark && log.adminRemark !== "null";
						const showInputField = adminRoleId === 'rol001' && (!log.adminRemark || log.adminRemark === "null");

						timelineHtml += `
					<li>
						<input type="hidden" id="logId_${index}" value="${log.logId}">
						<div class="timeline-time">
							<span class="date align-text-rg" id="dateTime_${index}">${log.createdDay}</span>
							<span class="date align-text-rg" id="dateTime_${index}">${log.createdOn}</span>
						</div>
						<div class="timeline-icon">
							<a href="javascript:;">&nbsp;</a>
						</div>
						<div class="timeline-body">
							<div class="timeline-header vendor-info">
								<div>
									<span class="userimage"><img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt=""></span>
									<span id="username_${index}" class="vendor-name">${log.vendorName}</span>
								</div>
								<span class="username">
									<a id="userStatus_${index}" class="vendor-status" data-toggle="collapse" href="#collapseExample_${index}"
									   style="color: ${log.vendorResponseStatus === 'Current' ? 'green' : log.vendorResponseStatus === 'Previous' ? '#bebebe' : 'black'};">
										${log.vendorResponseStatus}
									</a>
									<small></small>
								</span>
							</div>
							<div class="timeline-content">
								<h4 class="${textClass}" id="status_${index}">
									<i class="${iconClass} fa-fw"></i> ${log.status}
									<a href="javascript:void(0);" class="toggle-collapse" data-target="#collapseExample_${index}">
										<i class="fas fa-chevron-down" id="toggleIcon_${index}" style="font-size: 16px;"></i>
									</a>
								</h4>
								<p class="collapse vendor-cmt" id="collapseExample_${index}">${log.remark}</p>
							</div>
							<div class="timeline-comment-box" style="display: ${showAdminComment || showVendorComment || showInputField ? 'block' : 'none'};">
								<div class="user">
									<img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt="User Avatar">
								</div>
								<div class="input">
									${showAdminComment ? `
									<p class="admin-comment" style="color: #007BFF;">
										<strong>${log.adminRemarkBy}</strong> - ${log.adminRemark}
										<span class="admin-comment-date" style="color: #6c757d; font-size: 0.85em; margin-left: 10px;">
											(${log.adminRemarkDate})
										</span>
									</p>` : ''}
	
									 ${showVendorComment ? `
									 <p class="admin-comment" style="color: #007BFF;">
										<strong>${log.adminRemarkBy}</strong> - ${log.adminRemark}
										<span class="admin-comment-date" style="color: #6c757d; font-size: 0.85em; margin-left: 10px;">
											(${log.adminRemarkDate})
										</span>
									</p>` : ''}
	
									${showInputField ? `
									<div class="row">
	
									<div class="col-lg-9">
									<div class="form-group">
										<input type="text" class="form-control" 
											placeholder="Write a comment..." 
											id="adminComment_${index}" 
											value="">
									</div> </div> 
									<div class="col-lg-3">
										 <div class="form-group">
										<span class="input-group-btn">
											<button class="btn btn-primary f-s-12 rounded-corner" type="button" onclick="submitComment(${index});">
												<i class="fas fa-paper-plane"></i> Comment
											</button>
										</span>
									</div> </div> 
									</div>` : ''}
								</div>
							</div>
						</div>
					</li>
					`;
					});

				}

				if (pdfLogs != "null" && pdfLogs != null) {

					pdfLogs.forEach((log2, index) => {
						timelineHtml += `<li>
						<input type="hidden" id="logId_${index}" value="${log2.logId}">
						<div class="timeline-time">
							<span class="date align-text-rg" id="dateTime_${index}">${log2.createdDay}</span>
							<span class="date align-text-rg" id="dateTime_${index}">${log2.createdOn}</span>
						</div>
						 <div class="timeline-icon">
							<a href="javascript:;">&nbsp;</a>
						 </div>
						 <div class="alert-danger timeline-body-pdf">
							<div class="timeline-header vendor-info" style="border-bottom: 1px solid #b90000!important;">
								<div>
									<span class="userimage"><img src="https://bootdey.com/img/Content/avatar/avatar3.png" alt=""></span>
									<span id="username_${index}" class="vendor-name">${log2.createdBy}</span>
								</div>
								<span class="username" style="cursor: pointer;" data-toggle="collapse" href="#lessPdf_${index}" >
									<a id="userStatus_${index}" class="vendor-status">
										${log2.status}  <span style="font-weight: 700;font-size: 15px;">(${log2.contractVersion})</span>
									</a>
								</span>
							</div>
							<div class="timeline-content">
								<div class="collapse" id="lessPdf_${index}">
								<h4 id="status_${index}">
									 ${log2.remark}
								</h4>
								<p class="vendor-cmt">An updated version of the contract is now available. Please take a moment to review the changes and ensure you're up to date with the latest terms and conditions.</p>
								</div>
								<div class="released-by-section">
									<strong>Released By:</strong> <span id="releasedBy_${index}" class="released-by">${log2.createdBy}</span>
								</div>
							    
								<div class="contract-section">
									<a href="${log2.contractDocument}" target="_blank" title="View or Download Contract">
										<i class="fas fa-file-pdf" style="font-size: 20Px;"></i> View Contract
									</a>
								</div>
							</div>
						 </div>
					  </li>`
					});

				}

				$("#vendorResponse .timeline").html(timelineHtml);
				$("#vendorTimeline").modal("show");

				// Add click event for toggle collapse icons
				$('.toggle-collapse').on('click', function () {
					const target = $(this).data('target');
					$(target).collapse('toggle');

					// Change icon based on the collapse state
					const icon = $(this).find('i');
					if (icon.hasClass('fa-chevron-down')) {
						icon.removeClass('fa-chevron-down').addClass('fa-chevron-up');
					} else {
						icon.removeClass('fa-chevron-up').addClass('fa-chevron-down');
					}
				});
			} else {
				$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3").addClass("btn1");
				$("#msgModal").modal('show');
			}
		}
	});
}*/


function submitComment(index) {
	const logId = document.getElementById(`logId_${index}`).value;
	const adminComment = document.getElementById(`adminComment_${index}`).value;



	var datas = [{
		logId: logId,
		adminRemark: adminComment
	}];

	var validation = true;
	if (datas.adminComment == null || datas.adminComment == "") {
		validation = validationUpdated("Comments Required", "adminComment_" + index);
	}

	if (validation) {

		$.ajax({
			type: "POST",
			url: "contract-po-add-admin-response",
			contentType: "application/json",
			data: JSON.stringify({ datas: datas }),
			success: function(response) {
				if (response.code == "Success") {
					viewLogs();
				} else {
					$("#messageParagraph").text(response.message);
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
				}
			},
			error: function(error) {
				console.error(error);
			}
		});
	}
}

function closeremarkModal() {
	$("#remarksModal").modal('hide');
}

function resetBtn() {
	$("#quickFilter").val('');

	allTenderGridOptions.api.setQuickFilter('');

	allTenderGridOptions.api.refreshCells({ force: true });
}


function onQuickFilterChanged() {
	allTenderGridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}