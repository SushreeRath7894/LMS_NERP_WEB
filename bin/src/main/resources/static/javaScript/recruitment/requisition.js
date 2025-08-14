/* Recruitment Requisition Js
*  @Author: Pankaj Kr.
*
*/

$(document).ready(function() {
	$("#jobTitle").prop("disabled", false).select2({
		placeholder: "Type Job Title",
		allowClear: true,
		tags: true,
		createTag: function(params) {
			return {
				id: params.term,
				text: params.term,
				newOption: true
			};
		},
		templateResult: function(data) {
			let $result = $("<span></span>").text(data.text);
			if (data.newOption) {
				$result.append(" <em>(new)</em>");
			}
			return $result;
		}
	}).on("select2:unselect", function(e) {
		// Call addNew() function when an option is removed
		newBtn();
	});

	$("#jobTitle").on("select2:select", function(e) {
		let selectedValue = e.params.data.id;
		let isNewOption = e.params.data.newOption;

		if (!isNewOption) {
			existingJobs(selectedValue);
		}
	});

	$("#designation").select2({
		placeholder: "Select Designation",
		allowClear: true
	});

	$("#jobType").select2({
		placeholder: "Select Job Type",
		allowClear: true
	});

	$("#location").select2({
		placeholder: "Select Location",
		allowClear: true
	});

	$("#educationReq").select2({
		placeholder: "Select Education",
		allowClear: true
	});

	$("#dept").select2({
		placeholder: "Select Department",
		allowClear: true
	});

	$("#hiringManager").select2({
		placeholder: "Select Manager",
		allowClear: true
	});

	$("#workHour").select2({
		placeholder: "Select Work Hour",
		allowClear: true
	});

	$("#band").select2({
		placeholder: "Select Band",
		allowClear: true
	});
	for (var n = 1; n <= 25; n++) {
		$('#box ul').append('<li><span>image ' + n + ' </span><img src="http://unsplash.it/280/180?image=' + n + '"></li>');
	}
	/*$('#box').mCustomScrollbar({
		theme: "inset",
		scrollButtons: { enable: true }
	});*/

	$("#closeKey").click(function() {
		var rowLength = gridOptions.api.getDisplayedRowCount();
		$("#totalReq").find('span').html(rowLength);
	})

	$("#maxExpError").hide();
	$("#minExpError").hide();
	getRequisitionList();



});

function existingJobs(value) {
	titlePage(value, "existing-job");
}

$(document).ready(function() {
	let flag = false;
	document.getElementById('goBtn').addEventListener('click', function() {
		flag = true;
		quickFilterGrid(gridOptions);
	});

	document.getElementById('resetBtn').addEventListener('click', function() {
		document.getElementById('quickFilter').value = '';
		gridOptions.api.setQuickFilter('');
		flag = false;
		selectFirstRow(gridOptions);
	});

	document.getElementById('quickFilter').addEventListener('keyup', function(event) {
		const inputValue = this.value.trim();

		if (event.key === 'Enter' && inputValue !== '') {
			flag = true;
			quickFilterGrid(gridOptions);
		} else if (event.key === 'Backspace' && flag) {
			quickFilterGrid(gridOptions);
		}
	});
});


function getRequisitionList() {
	agGrid.simpleHttpRequest({
		url: "view-new-requi-mstr-view-data",
	}).then(function(data) {
		var allData = data;

		if (allData && allData.length > 0) {
			$("#reqStatusTimeline").removeClass("d-none");
			gridOptions.api.setRowData(allData);
			const reqId = $("#reqId").val();
			if (reqId) {
				gridOptions.api.forEachNode(function(node) {
					if (node.data && node.data.requisitionId === reqId) {
						node.setSelected(true);
						return;
					}
				});
			} else {
				gridOptions.api.forEachNode(function(node) {
					if (allData && allData.length > 0) {
						gridOptions.api.forEachNode(function(node) {
							if (node.rowIndex === 0) {
								node.setSelected(true);
							}
						});
					}
				});

			}
		} else {
			$("#reqStatusTimeline").addClass("d-none");
			gridOptions.api.setRowData();
		}


	})
}

function clearForm() {

	var userid = $("#sessionId").val();
	$(".benefitChk").prop("checked", false);
	$(".about").attr("checked", false);
	$("#appliedId").text("0");
	$("#shortlistId").text("0");
	$("#interviewedId").text("0");
	$("#hiredId").text("0");
	$("#offeredId").text("0");
	$("#rejectedId").text("0");
	$("#offerAcceptedId").text("0");
	$("#offerDeclinedId").text("0");
	$("#interviewScheduledId").text("0");
	$("#holdId").text("0");
	$("#maxExp").val('');
	$("#minExp").val('');
	$('#createDate').html('');
	$(".btn-hs").hide();
	$("#hideTbl").hide();
	$("#btnReset").hide();
	enableField();

	$("#delete1").hide();
	$('#reqId').val("");
	$('#jobTitle').val("").trigger('change');
	$('#jobType').val("").trigger('change');
	$('#location').val("").trigger('change');
	$('#educationReq').val("").trigger('change');
	$('#dept').val("").trigger('change');
	$('#hiringManager').val("").trigger('change');
	$('#workHour').val("").trigger('change');
	$('#band').val("").trigger('change');
	$('#designation').val("").trigger('change');
	$('#minSalary').val("");
	$('#maxSalary').val("");
	$('#noPos').val("");
	$('#dateReq').val("");
	$('#completionDateReq').val("");
	$('#jobDescResp').val("");
	$('#applyStartDate').val("");
	$('#applyEndDate').val("");
	$('#txtAreaSumN').val("");
	$("#txtAreaResN").val("");
	$("#txtAreaSumN").val("");
	$("#saveData").show();
	$("#reqStatusTimeline").addClass("d-none");
	$("#requisitionRounds").addClass("d-none");
	$(".requisitionIdHead").html("");
	intialRowdata();

	$("#maxExpError").hide();
	$("#minExpError").hide();
	txtLen = $('#txtAreaSumN').val().length;
	var pId1 = $('#txtAreaSumN').next().attr("id");
	$('#' + pId1 + ' span').empty();
	$('#' + pId1 + ' span').append(txtLen);
	$('#txtAreaResN').val("");
	txtLen = $('#txtAreaResN').val().length;
	var pId2 = $('#txtAreaResN').next().attr("id");
	$('#' + pId2 + ' span').empty();
	$('#' + pId2 + ' span').append(txtLen);
	$('#txtAreaExpN').val("");
	txtLen = $('#txtAreaExpN').val().length;
	var pId3 = $('#txtAreaExpN').next().attr("id");
	$('#' + pId3 + ' span').empty();
	$('#' + pId3 + ' span').append(txtLen);

	if ($('#approvalCheck').is("checked")) {
		approval();
	}
	if ($('.about').is("checked")) {
		//about();
	}
	document.getElementById("approvalCheck").checked = false;
	$('#createdDiv').attr("class", "grey-box");
	$('#divCreate').attr("class", "");

	$('#activityId').hide();

}

function cnclBtn() {
	$("#add").removeClass('d-none');
	$("#cancelJob").addClass('d-none');
	$("#delete").removeClass('d-none');
	$("#edit-req").removeClass('d-none');
	$("#forward").removeClass('d-none');
	getRequisitionList();
}

function newBtn() {
	$('#edit-jd-btn').show();
	$('#generateJd').show();
	$("#add").addClass('d-none');
	$("#delete").addClass('d-none');
	$("#edit-req").addClass('d-none');
	$("#forward").addClass('d-none');
	$("#cancelJob").removeClass('d-none');
	gridOptions.api.deselectAll();
	var userid = $("#sessionId").val();
	$("#reqStatusTimeline").addClass('d-none');
	$(".benefitChk").prop("checked", false);
	$(".about").attr("checked", false);
	$("#appliedId").text("0");
	$("#shortlistId").text("0");
	$("#interviewedId").text("0");
	$("#hiredId").text("0");
	$("#offeredId").text("0");
	$("#rejectedId").text("0");
	$("#offerAcceptedId").text("0");
	$("#offerDeclinedId").text("0");
	$("#interviewScheduledId").text("0");
	$("#holdId").text("0");
	$("#maxExp").val('');
	$("#minExp").val('');
	$('#createDate').html('');
	$(".btn-hs").hide();
	$("#hideTbl").hide();
	$("#btnReset").hide();
	$("#edit-req").attr("disabled", true);
	$("#delete").attr("disabled", true);
	$("#approve").attr("disabled", true);
	$("#reject").attr("disabled", true);
	$('#saveData').show();
	$('#forward').show();
	$('#delete').show();
	$('#edit-req').show();
	enableField();

	$("#delete1").hide();
	$('#reqId').val("");
	$('#jobTitle').val("").trigger('change');
	$('#jobType').val("").trigger('change');
	$('#location').val("").trigger('change');
	$('#educationReq').val("").trigger('change');
	$('#dept').val("").trigger('change');
	$('#hiringManager').val("").trigger('change');
	$('#workHour').val("").trigger('change');
	$('#band').val("").trigger('change');
	$('#designation').val("").trigger('change');
	$('#minSalary').val("");
	$('#maxSalary').val("");
	$('#noPos').val("");
	$('#dateReq').val("");
	$('#completionDateReq').val("");
	$('#jobDescResp').val("");
	$('#applyStartDate').val("");
	$('#applyEndDate').val("");
	$('#txtAreaSumN').val("");
	$("#txtAreaResN").val("");
	$("#txtAreaSumN").val("");
	intialRowdata();

	$("#maxExpError").hide();
	$("#minExpError").hide();
	txtLen = $('#txtAreaSumN').val().length;
	var pId1 = $('#txtAreaSumN').next().attr("id");
	$('#' + pId1 + ' span').empty();
	$('#' + pId1 + ' span').append(txtLen);
	$('#txtAreaResN').val("");
	txtLen = $('#txtAreaResN').val().length;
	var pId2 = $('#txtAreaResN').next().attr("id");
	$('#' + pId2 + ' span').empty();
	$('#' + pId2 + ' span').append(txtLen);
	$('#txtAreaExpN').val("");
	txtLen = $('#txtAreaExpN').val().length;
	var pId3 = $('#txtAreaExpN').next().attr("id");
	$('#' + pId3 + ' span').empty();
	$('#' + pId3 + ' span').append(txtLen);

	if ($('#approvalCheck').is("checked")) {
		approval();
	}
	if ($('.about').is("checked")) {
		//about();
	}
	document.getElementById("approvalCheck").checked = false;
	$('#createdDiv').attr("class", "grey-box");
	$('#divCreate').attr("class", "");

	$('#activityId').hide();

}


$(document).ready(function() {
	/*$("#add").attr("disabled", false);*/
	$("#copy").attr("disabled", true);
	$('#delete').attr('disabled', true);
	$('#approve').attr('disabled', true);
	$('#post').attr('disabled', true);
	$('#reject').attr('disabled', true);
	$('#vendor').attr("disabled", true);

	/*agGrid.simpleHttpRequest({ url: 'view-new-requi-mstr-view-data' }).then(function(data) {
		var len = data.length;
		$('#totalReq').find('span').html(len);
		gridOptions.api.setRowData(data);
	});*/
	cancelBtn();

	$("#copycancelBtn").click(function() {
		$("#myGrid").show();
		$(".btn-hs").show();
		$("#hideTbl").show();
		$("#btnReset").show();
		/*$("#demo").hide();*/
	});

	$("#profilebtn").click(function() {
		$("#myTbl").hide();
		$(".btn-hs").hide();
		$("#profile").show();
	})
	$("#profilecancelbtn").click(function() {
		$("#myTbl").show();
		$(".btn-hs").show();
		$("#profile").hide();
	});

	$("#closeKey").click(function() {
		var rowLength = gridOptions.api.getDisplayedRowCount();
		$("#totalReq").find('span').html(rowLength);
	})


	$('.progLang1').chosen({
		width: '200px',
		placeholder_text_multiple: 'Select your programming languages'
	});

	$(".progLang2").chosen({
		width: '200px',
		placeholder_text_multiple: 'Select Experience'
	});


	getSkillList();

	$('table tr:first-child .myRemove-btn').hide();

});
function cancelBtn() {
	$("#myGrid").show();
	$(".btn-hs").show();
	$("#hideTbl").show();
	$(".benefitChk").prop("checked", false);
	$(".about").attr("checked", false);
	/*$("#add").attr("disabled", false);*/
	$("#copy").attr("disabled", true);
	$('#delete').attr('disabled', true);
	$('#approve').attr('disabled', true);
	$('#post').attr('disabled', true);
	$('#reject').attr('disabled', true);
	$('#vendor').attr("disabled", true);
	$("#btnReset").show();
	/*$("#demo").hide();*/
	$(".formValidation").remove();

	$('#myGrid1').empty();
	$('#activityId').hide();
	len = 0;
	$('#approvalName').hide();
	getRequisitionList();

	$('#skillTable tbody tr').remove();

	intialRowdata();
}

$(document).ready(function() {

	$('#approvalName').hide();

	/* $('#header').load('../header-ads.html');
	 $('#footer').load('../footer-ads.html');*/
	jQuery(document).delegate('a.add-record', 'click', function(e) {
		e.preventDefault();
		var content = jQuery('#append_table tr'),
			size = jQuery('#myTbl >tbody >tr').length + 1,
			element = null,
			element = content.clone();
		element.attr('id', 'rec-' + size);
		element.find('.delete-record').attr('data-id', size);
		element.appendTo('#myTbl_body');
		element.find('.sn').html(size);
	});
	jQuery(document).delegate('a.delete-record', 'click', function(e) {
		e.preventDefault();
		var didConfirm = confirm("Are you sure You want to delete");
		if (didConfirm == true) {
			var id = jQuery(this).attr('data-id');
			var targetDiv = jQuery(this).attr('targetDiv');
			jQuery('#rec-' + id).remove();

			//regnerate index number on table
			$('#myTbl_body tr').each(function(index) {
				$(this).find('span.sn').html(index + 1);
			});
			return true;
		} else {
			return false;
		}




	});


	$('input,textarea').focus(function() {
		$(this).data('placeholder', $(this).attr('placeholder'))
			.attr('placeholder', '');
	}).blur(function() {
		$(this).attr('placeholder', $(this).data('placeholder'));
	});




});

var count = 0;
function allCheck() {
	count++;

	if (count == 1) {
		$('.checkCls').prop("checked", true);
	} else {
		count = 0;
		$('.checkCls').prop("checked", false);
	}
}

function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}

var txtLen = 0;
function textCount(event) {

	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
}


var userLogInId = sessionStorage.getItem("userId");
//AG GRID 
var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "Requisition ID",
		pinned: 'left',
		hide: true,
		cellRenderer: function(params) {
			return '<a class="edit-css" >' + params.data.requisitionId + '</a>';

		},
	},
	{
	    headerName: "Job Title",
	    pinned: 'left',
	    field: "jobTitle",
	    width: 200,
	    cellStyle: { textAlign: "leftAligned" },
	    cellRenderer: function(params) {
	        const jobTitle = params.data.jobTitle;
	        const activityStatus = params.data.activityStatus;
	        const approveStatus = params.data.approveStatus;
	        
	        const name = params.value || '';
	        const createdOn = params.data.createdOn || ''; 

	        let createdDate;
	        if (createdOn) {
	            const [day, month, year] = createdOn.split('-').map(Number);
	            createdDate = new Date(year, month - 1, day); 
	        }

	        const today = new Date();
	        const timeDiff = createdDate ? today - createdDate : 0;
	        const daysDiff = Math.floor(timeDiff / (1000 * 60 * 60 * 24)); 

	        let badgeText = '';
	        let badgeStyle = '';
	        let openBadge = '';

	        // Check for active and approved jobs to add "Open" badge
	        if (activityStatus === "Active" && approveStatus === "3") {
	            openBadge = `
	                <span class="candidateName-badge top-candidate" >
	                    Open
	                </span>
	            `;
	        }

	        // Determine days-based badge
	        if (!createdOn || isNaN(daysDiff)) {
	            // Handle invalid or missing date, show only job title and open badge (if applicable)
	            return `
	                <div style="display: flex; align-items: center; height: 100%; gap: 5px;">
	                    <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
	                    ${openBadge}
	                </div>
	            `;
	        }

	        if (daysDiff === 0) {
	            badgeText = 'New';
	            badgeStyle = `
	                background: rgba(52, 168, 83, 0.2);
	                color: #34a853;
	                font-size: 10px;
	                font-weight: 600;
	                padding: 2px 6px 0px 7px;
	                border-radius: 10px;
	                display: inline-flex;
	                align-items: center;
	                backdrop-filter: blur(2px);
	                white-space: nowrap;
	                line-height: 17px;
	            `;
	        } else if (daysDiff >= 1 && daysDiff <= 6) {
	            // 1 to 6 days ago: Orange badge
	            badgeText = daysDiff === 1 ? '1 day ago' : `${daysDiff} days ago`;
	            badgeStyle = `
	                background: rgba(255, 165, 0, 0.2);
	                color: #ff8c00;
	                font-size: 10px;
	                font-weight: 600;
	                padding: 2px 6px 0px 7px;
	                border-radius: 10px;
	                display: inline-flex;
	                align-items: center;
	                backdrop-filter: blur(2px);
	                white-space: nowrap;
	                line-height: 17px;
	            `;
	        } else if (daysDiff >= 7 && daysDiff <= 20) {
	            // 7 to 20 days ago: Orange badge, show weeks for 7 or 14 days, otherwise days
	            if (daysDiff === 7) {
	                badgeText = '1 week ago';
	            } else if (daysDiff === 14) {
	                badgeText = '2 weeks ago';
	            } else {
	                badgeText = `${daysDiff} days ago`;
	            }
	            badgeStyle = `
	                background: rgba(255, 165, 0, 0.2);
	                color: #ff8c00;
	                font-size: 10px;
	                font-weight: 600;
	                padding: 2px 6px 0px 7px;
	                border-radius: 10px;
	                display: inline-flex;
	                align-items: center;
	                backdrop-filter: blur(2px);
	                white-space: nowrap;
	                line-height: 17px;
	            `;
	        } else if (daysDiff >= 30) {
	            badgeText = `${daysDiff} days Old`;
	            badgeStyle = `
	                background: rgba(128, 128, 128, 0.2);
	                color: #666;
	                font-size: 10px;
	                font-weight: 600;
	                padding: 2px 6px 0px 7px;
	                border-radius: 10px;
	                display: inline-flex;
	                align-items: center;
	                backdrop-filter: blur(2px);
	                white-space: nowrap;
	                line-height: 17px;
	            `;
	        } else {
	            badgeText = `${daysDiff} days ago`;
	            badgeStyle = `
	                background: rgba(255, 165, 0, 0.2);
	                color: #ff8c00;
	                font-size: 10px;
	                font-weight: 600;
	                padding: 2px 6px 0px 7px;
	                border-radius: 10px;
	                display: inline-flex;
	                align-items: center;
	                backdrop-filter: blur(2px);
	                white-space: nowrap;
	                line-height: 17px;
	            `;
	        }

	        // Combine job title, days badge, and open badge (if applicable)
	        return `
	            <div style="display: flex; align-items: center; height: 100%; gap: 5px;">
	                <span style="flex: 1; overflow: hidden; text-overflow: ellipsis;">${name}</span>
	                ${badgeText ? `<span style="${badgeStyle}">${badgeText}</span>` : ''}
	                ${openBadge}
	            </div>
	        `;
	    }
	},
	{
		headerName: "Job Status",
		field: "activityStatus",
		width: 100,
		cellStyle: { textAlign: "center" },
		cellRenderer: function(params) {
			const status = params.data.activityStatus;
			const statusMap = {
				Active: { class: "bg-active-badge", label: "Active" },
				Inactive: { class: "bg-warning", label: "Inactive" },
				Closed: { class: "bg-close", label: "Closed" },
				Created: { class: "bg-created", label: "Created" },
			};

			const { class: badgeClass, label } = statusMap[status] || statusMap["Created"];

			return `<span class="badge ${badgeClass}" style="width: 57px; display: inline-block; text-align: center;">${label}</span>`;
		},
	},

	{
		headerName: "Approval Status",
		field: "approveStatus",
		width: 100,
		cellStyle: { textAlign: "center" },
		cellRenderer: function(params) {
			const status = params.data.approveStatus;
			const statusMap = {
				"0": { class: "bg-pending", label: "Pending" },
				"3": { class: "bg-primary-badge", label: "Approved" },
				"2": { class: "bg-close", label: "Rejected" },
				"1": { class: "bg-info-for", label: "Forwarded" },
			};

			const { class: badgeClass, label } = statusMap[status] || { class: "bg-secondary", label: "Unknown" };

			return `<span class="badge ${badgeClass}" style="display: inline-block; text-align: center;">${label}</span>`;
		},
	},

	{
		headerName: "Job Post",
		field: "jobPost",
		width: 120,
		cellRenderer: function(params) {
			// Static configuration with optimized logo URLs
			const platformConfig = {
				"LinkedIn": {
					displayName: "LinkedIn",
					logo: "https://cdn-icons-png.flaticon.com/512/174/174857.png", // PNG version
					bgColor: "#0077B5"
				},
				"Naukri": {
					displayName: "Naukri",
					logo: "",
					bgColor: "#0A5F59"
				},
				"Indeed": {
					displayName: "Indeed",
					logo: "",
					bgColor: "#2164F3"
				},
				"Monster": {
					displayName: "Monster",
					logo: "",
					bgColor: "#6D1F57"
				},
				"Glassdoor": {
					displayName: "Glassdoor",
					logo: "",
					bgColor: "#0CAA41"
				},
				"Shine": {
					displayName: "Shine",
					logo: "https://www.shine.com/next/static/images/shine-logo.png",
					bgColor: "#00A9E0"
				}
			};

			// For demo - replace with params.data.jobPost when dynamic
			const demoPlatforms = ["LinkedIn", "Naukri", "Indeed", "Monster", "Glassdoor"];
			const platforms = demoPlatforms; // Replace with params.data.jobPost when dynamic

			// Process platforms
			const platformList = Array.isArray(platforms) ?
				platforms :
				(platforms || "").split(",").map(p => p.trim());

			// Take first 3 platforms to display
			const visiblePlatforms = platformList.slice(0, 3);
			const extraCount = platformList.length - 3;

			// Generate overlapping logos
			const logosHtml = visiblePlatforms.map((platform, index) => {
				const config = platformConfig[platform] || {
					displayName: platform,
					logo: "https://cdn-icons-png.flaticon.com/512/157/157933.png",
					bgColor: "#666666"
				};

				return `
	                <div class="platform-logo-overlap" 
	                     title="${config.displayName}"
	                     style="z-index: ${visiblePlatforms.length - index}; 
	                            background-color: ${config.bgColor};
	                            margin-left: ${index > 0 ? '-8px' : '0'};">
	                    <img src="${config.logo}" 
	                         alt="${config.displayName}"
	                         onerror="this.src='https://cdn-icons-png.flaticon.com/512/157/157933.png'"/>
	                </div>
	            `;
			}).join("");

			// Add +X indicator if there are more platforms
			const extraHtml = extraCount > 0 ? `
	            <div class="extra-platforms-badge" 
	                 title="${platformList.slice(3).map(p => platformConfig[p]?.displayName || p).join(", ")}">
	                +${extraCount}
	            </div>
	        ` : "";

			return `
	            <div class="platform-logos-container">
	                ${logosHtml}
	                ${extraHtml}
	            </div>
	        `;
		}
	},
	{
		headerName: "Job Description",
		field: "jdPdf",
		width: 100,
		hide: true,
		cellStyle: { textAlign: "center" },
		cellRenderer: function(params) {
			// For static demo - treat all records as having PDFs
			const hasPdf = true;

			return `
	            <div style="display: flex; justify-content: center;">
	                <a href="javascript:void(0)" class="pdf-icon-link" 
	                   onclick="handlePdfClick('${params.data.requisitionId}')">
	                    <img src="../assets/images/pdf_demo.png" 
	                         alt="PDF Icon"
	                         style="height: 20px; width: auto;"
	                         onerror="this.src='https://cdn-icons-png.flaticon.com/512/157/157933.png'"/>
	                </a>
	            </div>
	        `;
		}
	},
	{
		headerName: "Job Url",
		field: "url",
		width: 80,
		cellStyle: { textAlign: "center" },
		cellRenderer: function(params) {
			const approveStatus = params.data.approveStatus;
			const requisitionId = params.data.requisitionId;

			if (approveStatus == "3") {
				return `
		            <a href="javascript:void(0);" 
		               onclick="fetchJobUrl('${requisitionId}')"
		               title="View job application page"
		               style="display: inline-block; padding: 5px;">
		                <i class="fa-solid fa-share-from-square"></i>
		            </a>
		        `;

			} else {
				return '<span style="color: #999;">-</span>';
			}


		}
	},
	{ headerName: "Open Positions", field: "noPosition", width: 100 },
	{ headerName: "Band Level", field: "band", width: 100, cellStyle: { textAlign: "leftAligned" } },
	{ headerName: "Department", field: "department", cellStyle: { textAlign: "leftAligned" } },
	{ headerName: "Hiring Manager", field: "hiringManager", cellStyle: { textAlign: "leftAligned" } },
	{ headerName: "Expected Join Date", field: "joinDate" },

	{ headerName: "Application Start", field: "applyStartDate", width: 180 },
	{ headerName: "Application End", field: "applyEndDate" },

	{
		headerName: "Salary Cap (CTC)",
		field: "minSalary",
		valueGetter: (params) => `₹ ${params.data.minSalary} - ₹ ${params.data.maxSalary}`,
		hide: false
	},
	{ headerName: "Total Applicants", field: "applicants", hide: true },
	{ headerName: "Shortlisted Candidates", field: "shortlisted", hide: true },
	{ headerName: "Interviewed Candidates", field: "interviewed", hide: true },
	{ headerName: "Experience", field: "minExp" },
	{ headerName: "Approved By", field: "approvedBy", cellStyle: { textAlign: "leftAligned" }, hide: true },
	{ headerName: "Rejected By", field: "rejeactedBy", cellStyle: { textAlign: "leftAligned" }, hide: true },
	{ headerName: "Remarks/Comments", field: "comments", hide: true },
	{ headerName: "Created On", field: "createdOn", cellStyle: { textAlign: "leftAligned" } },
];


function titlePage(id, status) {
	$('.loader').show();
	$(".btn-hs").hide();
	$("#hideTbl").hide();
	$("#btnReset").hide();
	$('#activityId').show();
	$("#delete1").hide();
	$('#searchRowDiv').hide();
	$(".main-div").show();
	$(".hr2").show();
	$(".newgrid").show();
	$("#minExpError").hide();
	$("#maxExpError").hide();
	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.message == "Success") {
				$('.loader').hide();
				var s1 = 0;
				var s2 = 0;

				for (i = 0; i < response.body.length; i++) {

					$("#appliedId").text(response.body[i].applicants);
					$("#shortlistId").text(response.body[i].shortlisted);
					$("#interviewedId").text(response.body[i].interviewed);
					$("#hiredId").text(response.body[i].hired);
					$("#offeredId").text(response.body[i].offered);
					$("#rejectedId").text(response.body[i].rejected);
					$("#offerAcceptedId").text(response.body[i].offerAccepted);
					$("#offerDeclinedId").text(response.body[i].offerDeclined);
					$("#interviewScheduledId").text(response.body[i].interviewScheduled);
					$("#holdId").text(response.body[i].hold);
					$("#finalizedId").text(response.body[i].offerAccepted);
					$('#jobTitle').val(response.body[i].requisitionId).trigger('change');
					$('.requisitionIdHead').text(response.body[i].jobTitle);
					$('#jobType').val(response.body[i].jobType).trigger('change');
					$('#location').val(response.body[i].jobLocation).trigger('change');
					$('#educationReq').val(response.body[i].minEducation).trigger('change');
					$('#dept').val(response.body[i].department).trigger('change');
					$('#hiringManager').val(response.body[i].hiringManager).trigger('change');
					$('#workHour').val(response.body[i].workHour).trigger('change');
					$('#band').val(response.body[i].band).trigger('change');
					$('#designation').val(response.body[i].designation).trigger('change');
					$("#reqStatusTimeline").removeClass('d-none');

					$('#minExp').val(response.body[i].minExp);
					$('#maxExp').val(response.body[i].maxExp);

					var amount = amountFormatter((response.body[i].minSalary)).toLocaleString();
					var amount1 = amountFormatter((response.body[i].maxSalary)).toLocaleString();

					$('#minSalary').val(amount);
					$('#maxSalary').val(amount1);
					$('#noPos').val(response.body[i].noPosition);
					$('#jobDescResp').val(response.body[i].aiJd);
					//$('#dateReq').val(new Date(response.body[i].joinDate));

					if (status == "existing-job") {
						$('#applyStartDate').val("");
						$('#applyEndDate').val("");
						$('#dateReqCalendar').val("");
						$('#completionDateReq').val("");
						$('#dateReq').val("");
						$('#reqId').val("");
					} else {
						$('#applyStartDate').val(response.body[i].applyStartDate);
						$('#applyEndDate').val(response.body[i].applyEndDate);
						//$('#dateReqCalendar').val(response.body[i].joinDate);
						$('#dateReq').val(response.body[i].joinDate);
						$('#completionDateReq').val(response.body[i].completionDateReq);
						$('#reqId').val(response.body[i].requisitionId);
					}

					/*$('#applyStartCalendar').val(response.body[i].applyStartDate);
					$('#applyEndCalendar').val(response.body[i].applyEndDate);*/
					$('#txtAreaSumN').val(response.body[i].positionSummary);
					txtLen = $('#txtAreaSumN').val().length;
					var pId1 = $('#txtAreaSumN').next().attr("id");
					$('#' + pId1 + ' span').empty();
					$('#' + pId1 + ' span').append(txtLen);
					$('#txtAreaResN').val(response.body[i].positionResponsibility);
					txtLen = $('#txtAreaResN').val().length;
					var pId2 = $('#txtAreaResN').next().attr("id");
					$('#' + pId2 + ' span').empty();
					$('#' + pId2 + ' span').append(txtLen);
					$('#txtAreaExpN').val(response.body[i].requiredSkillExperience);
					txtLen = $('#txtAreaExpN').val().length;
					var pId3 = $('#txtAreaExpN').next().attr("id");
					$('#' + pId3 + ' span').empty();
					$('#' + pId3 + ' span').append(txtLen);
					// First, uncheck all checkboxes
					$(".benefitChk").prop("checked", false);


					// Then, loop through the response and check only the required checkboxes
					response.body.forEach(item => {
						$("#" + item.reqBenefits).prop("checked", true);
					});

					if (response.body[i].approver != null && response.body[i].approver != "") {
						$("#approvalCheck").attr("checked", "checked");
						s1 = 1;
					}

					const todayDate = getFormattedTodayDate();
					if (response.body[i].about != null && response.body[i].about != "") {
						$(".about").attr("checked", "checked");
						s2 = 1;
					}
					if (response.body[i].approveStatus == "0") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");
						$('#forwarded-Div').removeClass("d-none");

					} else if (response.body[i].approveStatus == "1") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));
						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#forwarded-Div').removeClass("d-none");

					} else if (response.body[i].approveStatus == "2" &&
						response.body[i].forwardedBy !== null && response.body[i].forwardedBy !== "") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));
						$('#rejeactedDate').html(response.body[i].rejeactedDate);
						$('#rejeactedBy').html("By: " + capitalizeName(response.body[i].rejeactedBy));
						$('#closedStatus').html("Closed on");
						$('#closedOn').html(response.body[i].rejeactedDate);

						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#rejeactedDiv').attr("class", "green-box");
						$('#rejeactedStatusBox').attr("class", "green-box-heading");
						$('#closedDiv').attr("class", "green-box");
						$('#closedStatusBox').attr("class", "green-box-heading");

						$('#inactive-Div').addClass("d-none");
						$('#active-Div').addClass("d-none");
						$('#rejeacted-Div').removeClass("d-none");
						$('#closed-Div').removeClass("d-none");
						$('#approved-Div').addClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");
						$('#forwarded-Div').removeClass("d-none");


					} else if (response.body[i].approveStatus == "2" &&
						response.body[i].forwardedBy === null || response.body[i].forwardedBy === "") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						/*$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));*/
						$('#rejeactedDate').html(response.body[i].rejeactedDate);
						$('#rejeactedBy').html("By: " + capitalizeName(response.body[i].rejeactedBy));
						$('#closedStatus').html("Closed on");
						$('#closedOn').html(response.body[i].rejeactedDate);

						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#rejeactedDiv').attr("class", "green-box");
						$('#rejeactedStatusBox').attr("class", "green-box-heading");
						$('#closedDiv').attr("class", "green-box");
						$('#closedStatusBox').attr("class", "green-box-heading");

						$('#inactive-Div').addClass("d-none");
						$('#active-Div').addClass("d-none");
						$('#rejeacted-Div').removeClass("d-none");
						$('#closed-Div').removeClass("d-none");
						$('#approved-Div').addClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");
						$('#forwarded-Div').addClass("d-none");


					} else if (parseDate(response.body[i].joinDate) < parseDate(todayDate) && response.body[i].approveStatus == "3") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));
						$('#approvedDate').html(response.body[i].approvedDate);
						$('#approvedBy').html("By: " + capitalizeName(response.body[i].approvedBy));
						$('#activeStatus').html("Active since ");
						$('#activeOn').html(response.body[i].approvedDate);
						$('#inActiveStatus').html("Inactive since ");
						$('#inActiveOn').html(response.body[i].applyEndDate);
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#forwarded-Div').removeClass("d-none");

						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#approvedDiv').attr("class", "green-box");
						$('#divApproved').attr("class", "green-box-heading");
						$('#activeDiv').attr("class", "green-box");
						$('#divActive').attr("class", "green-box-heading");
						$('#inactiveDiv').attr("class", "green-box");
						$('#divInActive').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");

					} else if (parseDate(response.body[i].joinDate) < parseDate(todayDate) && response.body[i].approveStatus == "1") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));
						$('#approvedDate').html(response.body[i].approvedDate);
						$('#approvedBy').html("By: " + capitalizeName(response.body[i].approvedBy));
						$('#activeStatus').html("Active since ");
						$('#activeOn').html(response.body[i].approvedDate);
						$('#inActiveStatus').html("Inactive since ");
						$('#inActiveOn').html(response.body[i].applyEndDate);
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#forwarded-Div').removeClass("d-none");

						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#approvedDiv').attr("class", "green-box");
						$('#divApproved').attr("class", "green-box-heading");
						$('#activeDiv').attr("class", "green-box");
						$('#divActive').attr("class", "green-box-heading");
						$('#inactiveDiv').attr("class", "green-box");
						$('#divInActive').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");

					} else if (parseDate(response.body[i].applyEndDate) < parseDate(todayDate) && response.body[i].approveStatus == "3") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));
						$('#approvedDate').html(response.body[i].approvedDate);
						$('#approvedBy').html("By: " + capitalizeName(response.body[i].approvedBy));
						$('#activeStatus').html("Active since ");
						$('#activeOn').html(response.body[i].approvedDate);
						$('#inActiveStatus').html("Inactive since ");
						$('#inActiveOn').html(response.body[i].applyEndDate);
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#forwarded-Div').removeClass("d-none");

						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#approvedDiv').attr("class", "green-box");
						$('#divApproved').attr("class", "green-box-heading");
						$('#activeDiv').attr("class", "green-box");
						$('#divActive').attr("class", "green-box-heading");
						$('#inactiveDiv').attr("class", "green-box");
						$('#divInActive').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#requisitionRounds').addClass("d-none");
						$('#skills-nxt-btn').addClass("d-none");

					} else if (response.body[i].approveStatus === "3" &&
						response.body[i].forwardedBy !== null && response.body[i].forwardedBy !== "" &&
						response.body[i].approvedBy !== null && response.body[i].approvedBy !== "") {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));
						$('#approvedDate').html(response.body[i].approvedDate);
						$('#approvedBy').html("By: " + capitalizeName(response.body[i].approvedBy));
						$('#activeStatus').html("Active since ");
						$('#activeOn').html(response.body[i].approvedDate);
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#forwarded-Div').removeClass("d-none");

						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");
						$('#approvedDiv').attr("class", "green-box");
						$('#divApproved').attr("class", "green-box-heading");
						$('#activeDiv').attr("class", "green-box");
						$('#divActive').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#requisitionRounds').removeClass("d-none");
						$('#skills-nxt-btn').removeClass("d-none");


					} else if (response.body[i].approveStatus === "3" &&
						(response.body[i].forwardedBy === null || response.body[i].forwardedBy === "") &&
						response.body[i].approvedBy !== null && response.body[i].approvedBy !== ""
					) {
						resetTimeline();
						$('#createDate').html(response.body[i].createdOn);
						$('#createBy').html("By: " + capitalizeName(response.body[i].createdBy));
						/*$('#forwardDate').html(response.body[i].forwardedDate);
						$('#forwardBy').html("By: " + capitalizeName(response.body[i].forwardedBy));*/
						$('#approvedDate').html(response.body[i].approvedDate);
						$('#approvedBy').html("By: " + capitalizeName(response.body[i].approvedBy));
						$('#activeStatus').html("Active since ");
						$('#activeOn').html(response.body[i].approvedDate);
						$('#active-Div').removeClass("d-none");
						$('#rejeacted-Div').addClass("d-none");
						$('#closed-Div').addClass("d-none");
						$('#approved-Div').removeClass("d-none");
						$('#forwarded-Div').addClass("d-none");


						$('#createdDiv').attr("class", "green-box");
						$('#divCreate').attr("class", "green-box-heading");
						/*$('#forwardedDiv').attr("class", "green-box");
						$('#divForward').attr("class", "green-box-heading");*/
						$('#approvedDiv').attr("class", "green-box");
						$('#divApproved').attr("class", "green-box-heading");
						$('#activeDiv').attr("class", "green-box");
						$('#divActive').attr("class", "green-box-heading");
						$('#inactive-Div').removeClass("d-none");
						$('#requisitionRounds').removeClass("d-none");
						$('#skills-nxt-btn').removeClass("d-none");

					} else {
						// Clear the content of the divs
						resetTimeline();

					}

					$('.loader').hide();

				}
				if (s1 == 1) {
					approval();
				}
				if (s2 == 1) {
					//about();
				}

				/*$("#demo").show();*/
			}
		},
		error: function(data) {

		}
	});


	if (status === "Active") {
		$("#activeStatusBox").css("color", "#3467af");
	}

	else if (status === "Inactive") {
		$("#activeStatusBox").html("INACTIVE");
		$("#activeStatusBox").css("color", "#3467af");
	}

	else if (status === "Closed") {
		$("#activeStatusBox").html("INACTIVE");
		$("#activeStatusBox").css("color", "#3467af");
		$("#closedStatusBox").css("color", "#3467af");
	}

	editSkillData(id);


}

function getFormattedTodayDate() {
	const today = new Date();
	const day = String(today.getDate()).padStart(2, '0');
	const month = String(today.getMonth() + 1).padStart(2, '0'); // Months are zero-based
	const year = today.getFullYear();
	return `${day}-${month}-${year}`;
}

// Function to convert dd-MM-yyyy to a JavaScript Date object
function parseDate(dateStr) {
	const [day, month, year] = dateStr.split('-').map(Number);
	return new Date(year, month - 1, day); // Month is zero-based
}

function resetTimeline() {
	// Clear the content of the divs
	$('#createDate').html('');
	$('#createBy').html('');
	$('#forwardDate').html('');
	$('#forwardBy').html('');
	$('#approvedDate').html('');
	$('#approvedBy').html('');
	$('#activeStatus').html('');
	$('#activeOn').html('');
	$('#inActiveStatus').html('');
	$('#inActiveOn').html('');

	// Remove the classes from the divs
	$('#createdDiv').attr("class", "grey-box created-box");
	$('#divCreate').attr("class", "status");
	$('#forwardedDiv').attr("class", "grey-box forwarded-box");
	$('#divForward').attr("class", "status");
	$('#approvedDiv').attr("class", "grey-box approved-box");
	$('#divApproved').attr("class", "status");
	$('#activeDiv').attr("class", "grey-box approved-box");
	$('#divActive').attr("class", "status");
	$('#inactiveDiv').attr("class", "grey-box approved-box");
	$('#divInActive').attr("class", "status");
}

function capitalizeName(name) {
	return name
		.toLowerCase() // Convert the entire name to lowercase
		.replace(/\b\w/g, function(char) {
			return char.toUpperCase(); // Capitalize the first letter of each word
		});
}

function disabledFields() {
	$('#appliedId, #shortlistId, #interviewedId, #reqId, #jobTitle, #jobType, #location, #educationReq, #dept, #hiringManager, #workHour, #band, #designation, #minExp, #maxExp, #minSalary, #maxSalary, #noPos, #dateReq, #applyStartDate, #applyEndDate, #dateReqCalendar,#completionDateReqCalendar, #applyStartCalendar, #applyEndCalendar, #txtAreaSumN, #txtAreaResN, #txtAreaExpN,#jobDescResp').prop("disabled", true);
	$('#applyStartCalendar, #applyEndCalendar, #dateReqCalendar, #completionDateReqCalendar').addClass('disabled-icon');
	$('.myRemove-btn').attr("disabled", true);

}

function enableField() {
	$('#appliedId, #shortlistId, #interviewedId, #reqId, #jobTitle, #jobType, #location, #educationReq, #dept, #hiringManager, #workHour, #band, #designation, #minExp, #maxExp, #minSalary, #maxSalary, #noPos, #dateReq, #applyStartDate, #applyEndDate, #dateReqCalendar, #applyStartCalendar,#completionDateReqCalendar, #applyEndCalendar, #txtAreaSumN, #txtAreaResN, #txtAreaExpN, #jobDescResp').prop("disabled", false);
	$('.chosen-select').prop("disabled", false);
	$('#addNewSkills').attr("disabled", false);
	$('#saveData').attr("disabled", false);
	$('.myRemove-btn').attr("disabled", false);
	$('#applyStartCalendar, #applyEndCalendar,#completionDateReqCalendar, #dateReqCalendar').removeClass('disabled-icon');
}

function editJd() {
	$("#jobDescResp").attr("disabled", false);
	$("#txtAreaSumN").attr("disabled", false);
	$("#txtAreaResN").attr("disabled", false);
	$('#saveData').show();
	$('#generateJd').show();
	$('#saveData').attr("disabled", false);
}


// GET The Edit Skill Values As JSON Data

function editSkillData(id) {

	//$('table tr:first-child .myRemove-btn').hide();


	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-edit-skill?reqId=" + id,
		async: false,
		success: function(response) {
			var DropDownData = response.body;

			var responseData = JSON.parse(response.body);

			console.log("responsedata-->", responseData);

			if (responseData == null || responseData == "") {

				intialRowdata();
			}
			else {
				const skillTable = document.getElementById('skillTable');
				const tbody = skillTable.querySelector('tbody');

				tbody.innerHTML = '';

				responseData.forEach((item, index) => {
					const newRow = document.createElement('tr');
					newRow.innerHTML = `
			            <td>
			                <div class="col-md-12">
			                    <select class="progLang1 chosen-select" id="language_${index + 1}">
			                        <option value="">Select Skill</option>
			                        <option value="${item.skillName}" selected>${item.skillName}</option>
			                    </select>
			                </div>
			            </td>
			            <td>
			                <div class="col-md-12">
			                    <select class="progLang2 chosen-select" id="experience_${index + 1}">
			                        <option value="">Select Experience</option>
			                        <option value="0-1" ${item.skillValue === '0-1' ? 'selected' : ''}>0-1 years</option>
			                        <option value="1-2" ${item.skillValue === '1-2' ? 'selected' : ''}>1-2 years</option>
			                        <option value="2-3" ${item.skillValue === '2-3' ? 'selected' : ''}>2-3 years</option>
			                        <option value="3-4" ${item.skillValue === '3-4' ? 'selected' : ''}>3-4 years</option>
			                        <option value="4-5" ${item.skillValue === '4-5' ? 'selected' : ''}>5-5 years</option>
			                        <option value="5+" ${item.skillValue === '5+' ? 'selected' : ''}>5+ years</option>
			                        <option value="10+" ${item.skillValue === '10+' ? 'selected' : ''}>10+ years</option>
			                    </select>
			                </div>
			            </td>
			             <td>
			                <div class="col-md-12">
			                   <select class="progLang3 chosen-select"
									id="ratings_${index + 1}">
									<option value="">Rate skill (1 to 10)</option>
									<option value="1" ${item.skillRating === '1' ? 'selected' : ''}>1 - Beginner</option>
									<option value="2" ${item.skillRating === '2' ? 'selected' : ''}>2</option>
									<option value="3" ${item.skillRating === '3' ? 'selected' : ''}>3</option>
									<option value="4" ${item.skillRating === '4' ? 'selected' : ''}>4</option>
									<option value="5" ${item.skillRating === '5' ? 'selected' : ''}>5 - Intermediate</option>
									<option value="6" ${item.skillRating === '6' ? 'selected' : ''}>6</option>
									<option value="7" ${item.skillRating === '7' ? 'selected' : ''}>7</option>
									<option value="8" ${item.skillRating === '8' ? 'selected' : ''}>8</option>
									<option value="9" ${item.skillRating === '9' ? 'selected' : ''}>9</option>
									<option value="10" ${item.skillRating === '10' ? 'selected' : ''}>10 - Expert</option>
								</select>
			                </div>
			            </td>
						<td>
				            <div class="col-md-12 skills-kras">
				                <i class="fa-solid fa-square-pen" onclick="addSkillsKras(${index + 1});"></i>
								<i class="fa-solid fa-eye" onclick="viewSkillsKras(${index + 1});"></i>
								<input type="hidden" id="skills-competency_${index + 1}" value="No Cmpetency Required">
				            </div>
				        </td>
			            ${index > 0
							? `<td>
			                          <div class="col-md-4">
			                              <button onClick="removeSkill(this)" class="reject-btn myRemove-btn" style="float: left" disabled>Remove</button>
			                          </div>
			                       </td>`
							: ''
						}
			        `;

					tbody.appendChild(newRow);

					$.ajax({
						type: "GET",
						url: "view-new-requi-mstr-skills",
						async: false,
						success: function(response) {
							var DropDownData = response.body;
							var inputTag = newRow.querySelector(`#language_${index + 1}`);

							var filteredDropDownData = DropDownData.filter(function(dropdownItem) {
								return dropdownItem.name !== item.skillName;
							});

							filteredDropDownData.forEach(function(dropdownItem) {
								var option = document.createElement('option');
								option.value = dropdownItem.name;
								option.text = dropdownItem.name;
								inputTag.append(option);
							});

							$(inputTag).trigger("chosen:updated");
						}
					});
				});

				$('.chosen-select').chosen('destroy').chosen({ width: '100%' });


			}
		}

	});

};


//Function TO Get The Intial Row Data

function intialRowdata() {
	console.log("event fire")

	const skillTable = document.getElementById('skillTable');
	const tbody = skillTable.querySelector('tbody');

	tbody.innerHTML = '';


	const newRow = document.createElement('tr');
	newRow.innerHTML = `
	            <td>
	                <div class="col-md-12">
	                    <select class="progLang1 chosen-select" id="language_0">
	                        <option value="">Select Skill</option>
	                       
	                    </select>
	                </div>
	            </td>
	            <td>
	                <div class="col-md-12">
	                    <select class="progLang2 chosen-select" id="experience_0">
	                        <option value="">Select Experience</option>
	                        <option value="0-1">0-1 years</option>
							<option value="1-2">1-2 years</option>
							<option value="2-3">2-3 years</option>
							<option value="3-4">3-4 years</option>
							<option value="3-5">3-5 years</option>
							<option value="5+">5+ years</option>
							<option value="10+">10+ years</option>
	                    </select>
	                </div>
	            </td>
	            <td>
	              <div class="col-md-12">
					<select class="progLang3 chosen-select" id="ratings_0">
						<option value="">Rate skill (1 to 10)</option>
						<option value="1">1 - Beginner</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5 - Intermediate</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10 - Expert</option>
					</select>
				</div>
	            </td>
				
				<td>
		            <div class="col-md-12 skills-kras">
		                <i class="fa-solid fa-square-pen" onclick="addSkillsKras('0');"></i>
						<i class="fa-solid fa-eye" onclick="viewSkillsKras('0');"></i>
						<input type="hidden" id="skills-competency_0"  value="No Cmpetency Required">
		            </div>
		        </td>
	           
	        `;

	tbody.appendChild(newRow);

	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-skills",
		async: false,
		success: function(response) {
			var DropDownData = response.body;
			var inputTag = newRow.querySelector("#language_0");



			DropDownData.forEach(function(dropdownItem) {
				var option = document.createElement('option');
				option.value = dropdownItem.name;
				option.text = dropdownItem.name;
				inputTag.append(option);
			});

			$(inputTag).trigger("chosen:updated");
		}
	});


	$('.chosen-select').chosen('destroy').chosen({ width: '100%' });

}

// let the grid know which columns and what data to use
var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onSelectionChanged: rowSelectId,
	onFirstDataRendered: function(params) {
		const firstRowNode = params.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
	},
};


var dataVendor = {};
function onSelectionChangedVendor() {

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	gridOptionsChild.api.setRowData(selectedData);
}

function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);

	var rowlength2 = gridOptions.api.getDisplayedRowCount();
	$("#totalReq").find('span').html(rowlength2);
}

//OTHER THAN AG-GRID
var len = 0;
var approverName = '';
function approval() {
	len++;

	if (len == 1) {
		$('#approvalName').show();
		approverName = $('#approver').val();
	} else {
		$('#approvalName').hide();
		len = 0;

	}

}
var len1 = 0;
var aboutComp = '';
/* function about(){
	len1 ++;
	var id =  document.getElementById("aboutCompany");
	if(len1 == 1){
		id.style.display="block";
		aboutComp = $('#aboutComp').attr('value');
	}else{
		id.style.display="none";
		
		len1=0;
	}
	
} */

// hide show

var reqId = "";
id = "";
function rowSelectId() {
	/*  var status=$("#approveStatus").val();
	 alert(status) */
	var userLoginId = sessionStorage.getItem('userId');
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	reqId = selectedData.map(node => node.requisitionId);
	var reqTitle = selectedData.map(node => node.jobTitle);
	var status = selectedData.map(node => node.activityStatus);

	var empID = selectedData.map(node => node.empID);
	var selectedRows = gridOptions.api.getSelectedRows();
	id = "";
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	var uid = $("#sessionId").val();
	if (rowCount > 0) {
		/*$('#add').attr('disabled', true);*/
		/*$("#requisitionIdHead").text(reqTitle);*/
		titlePage(reqId, status);
		getRequisitionRounds(reqId);
		nextBtnFunction('requisitionStatusTab');
		disabledFields();
		if (reqId != uid) {
			if (selectedData.map(node => node.approveStatus) == 3) {
				/*$("#add").attr("disabled", true);*/
				$("#copy").attr("disabled", false);
				$('#delete').attr('disabled', true);
				$('#approve').attr('disabled', true);
				$('#post').attr('disabled', false);
				$('#reject').attr('disabled', true);
				$('#vendor').attr("disabled", false);
				$('#edit-req').attr("disabled", true);
				$('#addNewSkills').attr("disabled", true);
				$('#saveData').attr("disabled", true);
				$('#forward').attr("disabled", true);
				$('#saveData').hide();
				$('#forward').hide();
				$('#delete').hide();
				$('#edit-req').hide();
				$('#generateJd').hide();
				$('#edit-jd-btn').hide();
				$('#nextPositionBtn').removeClass("d-none");
			} else if (selectedData.map(node => node.approveStatus) == 2) {
				/*	$("#add").attr("disabled", true);*/
				$("#copy").attr("disabled", true);
				$('#delete').attr('disabled', true);
				$('#approve').attr('disabled', true);
				$('#post').attr('disabled', true);
				$('#reject').attr('disabled', true);
				$('#vendor').attr("disabled", true);
				$('#edit-req').attr("disabled", true);
				$('#addNewSkills').attr("disabled", true);
				$('#saveData').attr("disabled", true);
				$('#forward').attr("disabled", true);
				$('#saveData').hide();
				$('#forward').hide();
				$('#delete').hide();
				$('#edit-req').hide();
				$('#generateJd').hide();
				$('#edit-jd-btn').hide();
				$('#nextPositionBtn').addClass("d-none");
			}
			else if ((selectedData.map(node => node.approveStatus) == 1)) {
				/*$("#add").attr("disabled", true);*/
				$("#copy").attr("disabled", true);
				$('#delete').attr('disabled', false);
				$('#approve').attr('disabled', false);
				$('#post').attr('disabled', true);
				$('#reject').attr('disabled', false);
				$('#edit-req').attr("disabled", false);
				$('#vendor').attr("disabled", true);
				$('#addNewSkills').attr("disabled", true);
				$('#saveData').attr("disabled", true);
				$('#forward').attr("disabled", true);
				$('#saveData').hide();
				$('#forward').hide();
				$('#delete').hide();
				$('#edit-req').hide();
				$('#generateJd').hide();
				$('#edit-jd-btn').hide();
				$('#nextPositionBtn').addClass("d-none");
			}
			else if (selectedData.map(node => node.approveStatus) == 0) {
				/*$("#add").attr("disabled", true);*/
				$("#copy").attr("disabled", true);
				$('#delete').attr('disabled', false);
				$('#approve').attr('disabled', false);
				$('#post').attr('disabled', true);
				$('#reject').attr('disabled', false);
				$('#vendor').attr("disabled", true);
				$('#edit-req').attr("disabled", false);
				$('#addNewSkills').attr("disabled", true);
				$('#saveData').attr("disabled", true);
				$('#forward').attr("disabled", false);
				$('#saveData').show();
				$('#forward').show();
				$('#delete').show();
				$('#edit-req').show();
				$('#generateJd').show();
				$('#edit-jd-btn').show();
				$('#nextPositionBtn').addClass("d-none");
			}
			else {
				/*$("#add").attr("disabled", true);*/
				$("#copy").attr("disabled", true);
				$('#delete').attr('disabled', false);
				$('#approve').attr('disabled', false);
				$('#post').attr('disabled', true);
				$('#reject').attr('disabled', false);
				$('#vendor').attr("disabled", true);
				$('#edit-req').attr("disabled", false);
				$('#addNewSkills').attr("disabled", true);
				$('#saveData').attr("disabled", true);
				$('#saveData').show();
				$('#forward').show();
				$('#delete').show();
				$('#edit-req').show();
				$('#generateJd').show();
				$('#edit-jd-btn').show();
			}
		} else {
			/*$("#add").attr("disabled", true);*/
			$("#copy").attr("disabled", true);
			$('#delete').attr('disabled', false);
			$('#approve').attr('disabled', false);
			$('#post').attr('disabled', true);
			$('#reject').attr('disabled', false);
			$('#vendor').attr("disabled", true);
			$('#edit-req').attr("disabled", false);
			$('#addNewSkills').attr("disabled", true);
			$('#saveData').attr("disabled", true);
			$('#nextPositionBtn').addClass("d-none");
		}

	} else {
		enableField();
		clearForm();

		$("#add").attr("disabled", false);
		$("#copy").attr("disabled", true);
		$('#delete').attr('disabled', true);
		$('#approve').attr('disabled', true);
		$('#post').attr('disabled', true);
		$('#reject').attr('disabled', true);
		$('#vendor').attr("disabled", true);
		$('#edit-req').attr("disabled", false);
		$('#nextPositionBtn').addClass("d-none");

	}
	if (rowCount > 1) {
		$('#delete').attr('disabled', true);
		$('#approve').attr('disabled', true);
		$('#reject').attr('disabled', true);
	}
}


function sanitizeInput(input) {

	return input.replace(/['"]/g, '');
}

$('#txtAreaSumN, #txtAreaResN').on('input', function() {
	var sanitizedValue = sanitizeInput($(this).val());
	$(this).val(sanitizedValue);
});

var userRole = [];
function addNewReq() {
	var userid = $("#sessionId").val();
	$("input:checkbox[name=benefits]:checked").each(function() {
		var value = $(this).val();
		if (userRole.indexOf(value) === -1) {
			userRole.push(value);
		}
	})

	var minSalary = parseFloat($('#minSalary').val().replace(/\D/g, ''));
	var maxSalary = parseFloat($('#maxSalary').val().replace(/\D/g, ''));
	var jobTitle = $("#jobTitle option:selected").text();
	var trimmedJobTitle = jobTitle.replace(/\s*\([^)]*\)/g, '').trim();
	var obj = {};
	obj.requisitionId = $('#reqId').val();
	obj.benefits = userRole;
	obj.jobTitle = trimmedJobTitle;
	obj.jobType = $('#jobType').find(":selected").val();
	obj.jobLocation = $('#location').find(":selected").val();
	obj.minEducation = $('#educationReq').find(":selected").val();
	obj.minSalary = minSalary;
	obj.maxSalary = maxSalary;
	obj.department = $('#dept').find(":selected").val();
	obj.hiringManager = $('#hiringManager').find(":selected").val();
	obj.noPosition = $('#noPos').val();
	obj.workHour = $('#workHour').find(":selected").val();
	obj.band = $('#band').find(":selected").val();
	obj.designation = $('#designation').val();
	obj.joinDate = $('#dateReq').val();
	obj.applyStartDate = $('#applyStartDate').val();
	obj.applyEndDate = $('#applyEndDate').val();

	obj.positionSummary = sanitizeInput($('#txtAreaSumN').val());
	obj.positionResponsibility = sanitizeInput($('#txtAreaResN').val());
	obj.requiredSkillExperience = $('#txtAreaExpN').val();
	obj.approver = approverName;
	obj.about = aboutComp;
	obj.minExp = $("#minExp").val();
	obj.maxExp = $("#maxExp").val();
	obj.completionDateReq = $("#completionDateReq").val();
	obj.aiJd = $("#jobDescResp").val();


	/* FORM VALIDATION STARTS*/
	//$(".formValidation").remove();

	allValid = true;

	if (obj.jobTitle == null || obj.jobTitle == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Job Title Required");
		return;
	}

	if (obj.designation == null || obj.designation == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Designation Required");
		return;
	}

	if (obj.jobType == null || obj.jobType == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Job Type Required");
		return;
	}
	if (obj.jobLocation == null || obj.jobLocation == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Location Required");
		return;
	}
	if (obj.minEducation == null || obj.minEducation == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Minimum Education Required");
		return;
	}
	if ($('#minSalary').val() == null || $('#minSalary').val() == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Minimum Salary Required");
		return;
	}

	if (minSalary >= maxSalary) {
		toastr.error("Maximum Salary should be greater than Minimum Salary.");
		nextBtnFunction('requisitionDetailsTab');
		return;
	}

	if ($('#maxSalary').val() == null || $('#maxSalary').val() == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Maximum Salary Required");
		return;
	}

	if (obj.department == null || obj.department == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Department Name Required");
		return;
	}
	if (obj.hiringManager == null || obj.hiringManager == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Hiring Manager Name Required");
		return;
	}

	if (obj.noPosition == null || obj.noPosition == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Position Required");
		return;
	}
	if (obj.workHour == null || obj.workHour == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Working Hour Required");
		return;
	}

	if (obj.band == null || obj.band == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Band Name Required");
		return;
	}

	if (obj.minExp == null || obj.minExp == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Min Experience Required");
		return;
	}

	if (obj.maxExp == null || obj.maxExp == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Max Experience Required");
		return;
	}
	if (obj.applyStartDate == null || obj.applyStartDate == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Apply Start Date Required");
		return;
	}
	if (obj.applyEndDate == null || obj.applyEndDate == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Apply End Date Required");
		return;
	}
	if (obj.joinDate == null || obj.joinDate == "") {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Joining Date Required");
		return;
	}


	if (parseInt(obj.minExp) > parseInt(obj.maxExp)) {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Maximum Experience Should Be Greater");
		return;

		/*$("#main").show();*/

	}

	if ((parseInt(obj.minExp) < 0) || (parseInt(obj.maxExp)) < 0) {
		nextBtnFunction('requisitionDetailsTab');
		toastr.error("Experience Can Not Be Negative");
		return;
	}

	var positionChar = $("#charNumSN").find('span').html();
	var responsibilityChar = $("#charNumRN").find('span').html();

	if (positionChar > 2000) {
		nextBtnFunction('positionTab');
		toastr.error("Position Summary in 2000 characters or less.");
		return;
	}

	if (responsibilityChar > 2000) {
		nextBtnFunction('positionTab');
		toastr.error("Position Responsibility in up to 2000 Characters.");
		return;
	}

	if (allValid) {
		// Get The Skills Object
		var skillTable = document.querySelectorAll("#skillTable tbody tr");
		var skillsData = [];
		var isValid = true;

		skillTable.forEach(function(row) {
			var skill = row.querySelector("select.progLang1").value;
			var experience = row.querySelector("select.progLang2").value;
			var ratings = row.querySelector("select.progLang3").value;

			if (!skill || !experience || !ratings) {
				isValid = false;
			} else {
				var skillObject = {};
				skillObject[skill] = experience,
					skillObject["ratings"] = ratings;
				skillsData.push(skillObject);

			}
		});

		if (isValid) {
			obj.skillsData = skillsData;
		} else {
			/*nextBtnFunction('requisitionDetailsTab');*/
			toastr.error("Skills Ratings Required");
			return;
		}
	}


	console.log("obj-->", obj);

	if (allValid) {
		$('.loader').show();
		/*$("body").addClass("overlay");*/

		$.ajax({
			type: "POST",
			url: "view-new-requi-mstr-ajax",
			contentType: "application/json",
			data: JSON.stringify(obj),

			success: function(response) {
				if (response.message == "Success") {
					$('.loader').hide();
					/*$("body").removeClass("overlay");*/
					if (response.code == "201") {
						nextBtnFunction('requisitionStatusTab');
						toastr.success('Requisition Created Successfully');
						getRequisitionList();
						disabledFields();
						cnclBtn();
						$("#reqStatusTimeline").removeClass('d-none');
					}
					else if (response.code == "200") {
						nextBtnFunction('requisitionStatusTab');
						toastr.success('Requisition Modify Successfully');
						disabledFields();
						getRequisitionList();
						$("#reqStatusTimeline").removeClass('d-none');

					}
				}
			}, error: function(data) {

			}
		}) //ajax ends
	}

}



function deleteReq() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].requisitionId;
	$.ajax({
		type: "POST",
		url: "view-new-requi-mstr-delete?id=" + id,
		success: function(response) {
			if (response.message == "success") {
				getRequisitionList();
				$("#reqId").val("");
				$("#vendor").attr("disabled", true);
				$("#add").attr("disabled", false);
				$("#delete").attr("disabled", true);
				$("#approve").attr("disabled", true);
				$("#reject").attr("disabled", true);

				toastr.success("Requisition Delete Successfully");

			}
		},
		error: function(data) {
		}
	})
}
function copyBtn() {
	var status = 1;
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';
	selectedRows.forEach(function(selectedRow, index) {
		if (index == 0) {
			selectedRowsString += selectedRow.requisitionId;
			status = 1;
		} else {
			$('#modalMsg').text("");
			$('#reqValid').modal('toggle');
			$('#modalMsg').text("Select only one Requisition.");
			selectedRowsString = "";
			status = 0;
		}

	});
	if (status) {
		copyData(selectedRowsString);
	}

}
function copyData(selectedRowsString) {
	if (selectedRowsString) {
		$("#myGrid").hide();
		$(".btn-hs").hide();
		$("#hideTbl").hide();
		$('#reqId').val("");
		$('#createDate').html('');

		$.ajax({
			type: "GET",
			url: "view-new-requi-mstr-edit?id=" + selectedRowsString,
			success: function(response) {
				if (response.message == "Success") {
					var s1 = 0;
					var s2 = 0;
					for (i = 0; i < response.body.length; i++) {
						$('#jobTitle').val(response.body[i].jobTitle);
						$('#jobType').val(response.body[i].jobType);
						$('#location').val(response.body[i].jobLocation);
						$('#educationReq').val(response.body[i].minEducation);
						$('#dept').val(response.body[i].department);
						$('#hiringManager').val(response.body[i].hiringManager);
						$('#workHour').val(response.body[i].workHour);
						$('#band').val(response.body[i].band);
						$('#minSalary').val(response.body[i].minSalary);
						$('#maxSalary').val(response.body[i].maxSalary);
						$('#noPos').val(response.body[i].noPosition);
						$('#designation').val(response.body[i].designation);

						var date = dateFormat(response.body[i].joinDate);
						document.getElementById("dateReq").value = date;

						var dateCompletion = dateFormat(response.body[i].completionDateReq);
						document.getElementById("completionDateReq").value = dateCompletion;

						var date1 = dateFormat(response.body[i].applyStartDate);
						document.getElementById("applyStartDate").value = date1;

						var date2 = dateFormat(response.body[i].applyEndDate);
						document.getElementById("applyEndDate").value = date2;

						$('#txtAreaSumN').val(response.body[i].positionSummary);
						$('#jobDescResp').val(response.body[i].aiJd);
						txtLen = $('#txtAreaSumN').val().length;
						var pId1 = $('#txtAreaSumN').next().attr("id");
						$('#' + pId1 + ' span').empty();
						$('#' + pId1 + ' span').append(txtLen);
						$('#txtAreaResN').val(response.body[i].positionResponsibility);
						txtLen = $('#txtAreaResN').val().length;
						var pId2 = $('#txtAreaResN').next().attr("id");
						$('#' + pId2 + ' span').empty();
						$('#' + pId2 + ' span').append(txtLen);
						$('#txtAreaExpN').val(response.body[i].requiredSkillExperience);
						txtLen = $('#txtAreaExpN').val().length;
						var pId3 = $('#txtAreaExpN').next().attr("id");
						$('#' + pId3 + ' span').empty();
						$('#' + pId3 + ' span').append(txtLen);
						$("#" + response.body[i].reqBenefits).attr("checked", "checked");
						if (response.body[i].approver != null && response.body[i].approver != "") {
							$("#approvalCheck").attr("checked", "checked");
							s1 = 1;
						}
						if (response.body[i].about != null && response.body[i].about != "") {
							$(".about").attr("checked", "checked");
							s2 = 1;
						}
					}
					if (s1 == 1) {
						approval();
					}
					if (s2 == 1) {
						//about();
					}

					/*$("#demo").show()*/
				}
			},
			error: function(data) {

			}
		})
	} else {
		$('#reqValid').modal('toggle');
		$('#modalMsg').text("");
		$('#modalMsg').text("Select an Requisition.");
	}
}

var rowData = [];

// VENDOR ALLOCATION START

var rowDataVendor = [];
var rowDataReq = [];
function vendorAllocate() {
	$('#allocateVendor').attr('disabled', true);

	var status = 0;
	rowDataReq = [];

	var rowCount = 0;
	gridOptions.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
		if (rowNode.data.requisitionId) {
			rowCount++;
		}
	});

	console.log('rowCount>>', rowCount)
	var selectedRows = gridOptions.api.getSelectedRows();
	var selectedRowsString = '';

	selectedRows.forEach(function(selectedRow, index) {
		if (index > 0) {
			selectedRowsString += ',';
		}
		rowDataReq.push(selectedRow.requisitionId);
		status = 1;
	});

	getRequisitionList();

	if (status == 0) {
		$('#modalMsg').text("");
		$('#reqValid').modal('toggle');
		$('#modalMsg').text("Select an Requisition.");
		selectedRowsString = "";
	} else {
		agGrid.simpleHttpRequest({
			url: "/recruitment/view-new-requi-mstr-view-data-ajax"
		}).then(function(data) {

			var len = data.length;
			$('#recuitId').find('span').html(len);
			gridOptionsVendor.api.setRowData(data);
			gridOptionsVendor.api.forEachNode(function(node) {
				/*  if(node.data.vendorStatus == "1"){
					
					console.log("true",node.data);
					node.setSelected(true);
				} */

				//console.log("node-->" , node)
			})


		});
		$('#myModal10').modal('show');
	}
}

function allocateVendor() {
	rowDataVendor = [];
	var listData = {};

	var selectedRows = gridOptionsVendor.api.getSelectedRows();

	selectedRows.forEach(function(selectedRow, index) {

		rowDataVendor.push(selectedRow.vendorId);
	});

	listData.requisitionId = rowDataReq;
	listData.vendorId = rowDataVendor;

	$.ajax({
		type: "POST",
		url: "view-new-requi-mstr-add-vendor-ajax",
		contentType: "application/json",
		data: JSON.stringify(listData),
		/* async 		: false, */
		success: function(response) {

			if (response.message == "Success") {

				$('#modalMsg').text("");
				$('#reqValid').modal('toggle');
				$('#modalMsg').text("Vendor allocated successfully.");

				vendorAllocate()
			}
		}, error: function(data) {


		}
	})
}


var columnDefsChild = [

	{ headerName: "Req ID", field: "requisitionId" },
	{ headerName: "Title", field: "jobTitle" },
	{ headerName: "# of positions", field: "noPosition" },
	{ headerName: "Band", field: "band" },
	{ headerName: "Department", field: "department" },
	{ headerName: "Hiring Manager", field: "hiringManager" },
	{ headerName: "Join Date", field: "joinDate" },
	{ headerName: "CTC Cap", field: "minSalary" },
	{ headerName: "Status", field: "activityStatus" }
];

var gridOptionsChild = {
	columnDefs: columnDefsChild,
	rowData: rowData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	}

};


var columnDefsVendor = [
	{
		//headerCheckboxSelection : true,
		//headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection: function(params) {
			return params.data.vendorStatus !== '1';
		},
		width: 10,
		sortable: false,
		filter: false,
		resizable: true,

	},
	{ headerName: "Vendor Id", field: "vendorId" },
	{
		headerName: "Vendor Name", field: "vendorName",
		width: 200,
		cellClass: function(params) {
			if (params.data.vendorStatus === '1') {
				return (params.data.vendorStatus === '1' ? 'blueClsWT' : 'tptCls');
			}

		}
	},
	{ headerName: "Vendor Location", field: "vendorLocation" },
	{ headerName: "Expertize", field: "" },
	{ headerName: "Reqs Sent (Last 12 Months)", field: "" },
	{ headerName: "Candidates (Last 12 Months)", field: "" },
	{ headerName: "Closed (Last 12 Months)", field: "" }
];

var gridOptionsVendor = {
	columnDefs: columnDefsVendor,
	rowData: rowData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	},
	rowSelection: 'single',
	onSelectionChanged: onSelectionChangedCandidateVendor,
	onGridReady: function(params) {
		// Set rowData after grid is ready
		params.api.setRowData(rowData);  // Make sure rowData is set
		// Select rows based on vendorStatus
		params.api.forEachNode(function(node) {
			if (node.data.vendorStatus == '1') {
				node.setSelected(true); // Select the row if vendorStatus is 1
			}
		});
	}
};

function onSelectionChangedCandidateVendor() {
	var selectedNodes = gridOptionsVendor.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);

	var selectedRows = gridOptionsVendor.api.getSelectedRows();


	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		if (selectedRow.vendorStatus != 1) {
			rowCount = rowCount + 1;
		}
	});


	if (rowCount > 0) {
		$('#allocateVendor').attr('disabled', false);
	} else {
		$('#allocateVendor').attr('disabled', true);
	}
}

/* -------------------function for approve button----------------- */
var empid = "";
var empname = "";
function approveReq(status) {
	var rqid = "";
	//var id="";

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	rqid = selectedData.map(node => node.requisitionId);


	var userLogInId = sessionStorage.getItem('userId');
	var userrole = $("#sessionRole").val();


	var roleid = "";
	for (var i = 6; i <= userrole.length; i = i + 6) {
		roleid = roleid + '"' + userrole.slice(i - 6, i) + '",';
	}
	roleid = roleid.substring(0, roleid.length - 1);


	empid = $("#sessionId").val();
	empname = $("#sessionName").val();
	/*comment = $("#comment").val();*/
	comment = "Demo Comment";

	console.log("comment::::" + comment);
	console.log("empid::::" + empid);
	console.log("empname::::" + empname);


	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-approve?approveId=" + rqid + "&name=" + empid + "&comment=" + comment + "&roleid=" + roleid + "&status=" + status,
		async: false,
		success: function(response) {
			if (response.code == "success") {
				toastr.success(response.message);
				cancelBtn();
				$('#commentModal').modal('hide');
				$('#approve').attr('disabled', true);
				$('#delete').attr('disabled', true);
				$('#reject').attr('disabled', true);
			} else {
				toastr.success(response.message);
				cancelBtn();
				$('#commentModal').modal('hide');
				$('#approve').attr('disabled', true);
				$('#delete').attr('disabled', true);
				$('#reject').attr('disabled', true);
			}

		},
		error: function(data) {
		}
	});

	$("#comment").val("");
}

/*-----------------------function for reject button------------------- */
var rejempid = "";
var rejempname = "";
function rejectReq() {
	var rqid = "";
	//var id="";

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	rqid = selectedData.map(node => node.requisitionId);

	rejempid = $("#sessionId").val();
	rejempname = $("#sessionName").val();
	comment = $("#comment").val();
	//alert(rejempname)
	//alert(comment)

	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-reject?rejectId=" + rqid + "&name=" + rejempid + "&comment=" + comment,
		async: false,
		success: function(response) {

			if (response.message == "Success") {
				$("#messageParagraph").text("Requisition Rejected Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				cancelBtn();
				$('#commentModal').modal('hide');
				$('#approve').attr('disabled', true);
				$('#delete').attr('disabled', true);
				$('#reject').attr('disabled', true);
			} else {
				$("#messageParagraph").text("YOU’RE ALREADY REJECTED!");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				cancelBtn();
				$('#commentModal').modal('hide');
				$('#approve').attr('disabled', true);
				$('#delete').attr('disabled', true);
				$('#reject').attr('disabled', true);
			}

		},
		error: function(data) {
		}
	});

	$("#comment").val("");
}

function reject() {
	approveReq('2');
}
/* Function for commentModal show */
/*function reject() {


	$('#commentModal').modal('toggle');
	$("#approveLeaveSubmitBtn").hide();
	$("#rejectLeaveSubmitBtn").show();
}
function approve() {
	$('#commentModal').modal('toggle');
	$("#approveLeaveSubmitBtn").show();
	$("#rejectLeaveSubmitBtn").hide();

}*/

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var myVendorReq = document.querySelector('#myVendorReq');
	new agGrid.Grid(myVendorReq, gridOptionsChild);

	var addVendor = document.querySelector('#addVendor');
	new agGrid.Grid(addVendor, gridOptionsVendor);

	var dateFormat = localStorage.getItem("dateFormat");
	$("#dateReqCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#dateReq').val($(this).val());
		handelDates(document.getElementById("dateReq"));
	})

	$('#dateReq').blur(function() {
		$("#dateReqCalendar").val($(this).val());
		handelDates(document.getElementById("dateReq"));
	});

	$("#completionDateReqCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#completionDateReq').val($(this).val());
		handelDates(document.getElementById("completionDateReq"));
	})

	$('#completionDateReq').blur(function() {
		$("#completionDateReqCalendar").val($(this).val());
		handelDates(document.getElementById("completionDateReq"));
	})

	// Set minDate to today for both calendars
	var today = new Date();
	today.setHours(0, 0, 0, 0); // Set time to midnight to ensure whole day is available

	$("#applyStartCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: today  // Prevent selecting past dates
	}).on("change", function() {
		$('#applyStartDate').val($(this).val());
		handelDates(document.getElementById("applyStartDate"));
	});

	$('#applyStartDate').blur(function() {
		$("#applyStartCalendar").val($(this).val());
		handelDates(document.getElementById("applyStartDate"));
	});

	$("#applyEndCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
		minDate: today  // Prevent selecting past dates
	}).on("change", function() {
		$('#applyEndDate').val($(this).val());
		handelDates(document.getElementById("applyEndDate"));
	});

	$('#applyEndDate').blur(function() {
		$("#applyEndCalendar").val($(this).val());
		handelDates(document.getElementById("applyEndDate"));
	});

});


function formatAmount(element) {
	let value = element.value;
	if (value) {
		let formattedValue = indianCurreny(value);
		element.value = formattedValue;
		$(element.id).val(value);

	}

}

function indianCurreny(value) {
	if (value !== null && value !== undefined) {
		value = value.replace(/[^\d.,]/g, '');

		var parts = value.toString().split('.');
		var integerPart = parts[0].replace(/,/g, '');
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';

		if (decimalPart.length > 3) {
			decimalPart = decimalPart.substring(0, 3);
		}

		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}


function amountFormatter(value) {
	if (value !== null && value !== undefined) {
		var parts = value.toString().split('.');
		var integerPart = parts[0];
		var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
		var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
		return formattedIntegerPart + decimalPart;
	} else {
		return '';
	}
}


function getSkillList() {
	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-skills",
		async: false,
		success: function(response) {
			var DropDownData = response.body;

			// Initialize the first dropdown
			var inputTag = $(".progLang1");
			inputTag.empty();

			var defaultOption = document.createElement('option');
			defaultOption.value = "";
			defaultOption.text = "Select Skill";
			inputTag.append(defaultOption);

			for (var i = 0; i < DropDownData.length; i++) {
				var option = document.createElement('option');
				option.value = DropDownData[i].name;
				option.text = DropDownData[i].name;
				inputTag.append(option);
			}

			inputTag.trigger("chosen:updated");
		}
	});
}



function addNewSkillBtn() {
	const skillTable = document.getElementById('skillTable');
	const tbody = skillTable.querySelector('tbody');

	const lastRow = tbody.querySelector('tr:last-child');

	const languageSelect = lastRow.querySelector('.progLang1');
	var selectOneId = languageSelect.id.split("_");


	const newRowCount = selectOneId[1];

	var currentSkillSelect = $("#language_" + newRowCount).val();
	var currentExp = $("#experience_" + newRowCount).val();

	var newUpdatedIndex;

	if (!currentSkillSelect || !currentExp) {
		toastr.error("Select Both  Skill And Experience Before Adding A new Row");
		return;
	}
	else {

		newUpdatedIndex = parseInt(newRowCount) + 1;
	}


	var allSelectSkillBox = document.querySelectorAll(".progLang1");

	let skillsArray = [];


	allSelectSkillBox.forEach(function(element) {

		var selectId = element.id;

		var selectValues = $(`#${selectId}`).val();

		skillsArray.push(selectValues);
	});


	const newRow = document.createElement('tr');


	newRow.innerHTML = `
        <td>
            <div class="col-md-12">
                <select class="progLang1 chosen-select" id="language_${newUpdatedIndex}">
                    <option value="">Select Skill</option>
                </select>
            </div>
        </td>
        <td>
            <div class="col-md-12">
                <select class="progLang2 chosen-select" id="experience_${newUpdatedIndex}">
                <option value="">Select Experience</option>
                <option value="0-1">0-1 years</option>
				<option value="1-2">1-2 years</option>
				<option value="2-3">2-3 years</option>
				<option value="3-4">3-4 years</option>
				<option value="3-5">3-5 years</option>
				<option value="5+">5+ years</option>
				<option value="10+">10+ years</option>
                </select>
            </div>
        </td>
        <td>
        <div class="col-md-12">
				<select class="progLang3 chosen-select" id="ratings_${newUpdatedIndex}">
					<option value="">Rate skill (1 to 10)</option>
					<option value="1">1 - Beginner</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5 - Intermediate</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10 - Expert</option>
				</select>
			</div>
        </td>
		<td>
            <div class="col-md-12 skills-kras">
                <i class="fa-solid fa-square-pen" onclick="addSkillsKras(${newUpdatedIndex});"></i>
				<i class="fa-solid fa-eye" onclick="viewSkillsKras(${newUpdatedIndex});"></i>
				<input type="hidden" id="skills-competency_${newUpdatedIndex}"  value="No Cmpetency Required">
            </div>
        </td>
        <td>
            <div class="col-md-4">
                <button onClick="removeSkill(this)" class="reject-btn myRemove-btn" style="float: left">Remove</button>
            </div>
        </td>
    `;

	tbody.appendChild(newRow);
	$('.chosen-select').chosen('destroy').chosen({ width: '100%' });

	$.ajax({
		type: "GET",
		url: "view-new-requi-mstr-skills",
		async: false,
		success: function(response) {
			var DropDownData = response.body;
			var inputTag = newRow.querySelector(`#language_${newUpdatedIndex}`);

			var filteredDropDownData = DropDownData.filter(function(item) {

				return !skillsArray.includes(item.name);
			});


			for (var i = 0; i < filteredDropDownData.length; i++) {
				var option = document.createElement('option');
				option.value = filteredDropDownData[i].name;
				option.text = filteredDropDownData[i].name;
				inputTag.append(option);
			}

			$(inputTag).trigger("chosen:updated");
		}
	});
}

/* function removeSkill(button) {
	const rowToRemove = button.closest('tr');
	rowToRemove.remove();

	$('.chosen-select').chosen('destroy').chosen({ width: '100%' });
}
 */


function removeSkill(button) {
	const rowToRemove = button.closest('tr');
	const tbody = rowToRemove.parentNode;


	const skillTable = document.getElementById('skillTable');
	const tableBody = skillTable.querySelector('tbody');

	const totalRows = tbody.querySelectorAll('tr');

	console.log("totalRows-->", totalRows.length);

	var lengthOfRow = totalRows.length;


	// return false;



	if (tbody.firstElementChild === rowToRemove && lengthOfRow == 1) {
		Swal.fire({
			icon: "warning",
			title: "Error",
			text: "Atleast One Skill Is Needed",
		});
		return;
	}

	rowToRemove.remove();

	$('.chosen-select').chosen('destroy').chosen({ width: '100%' });
}


function validateExperience() {
	let minExp = document.getElementById("minExp");
	let maxExp = document.getElementById("maxExp");

	minExp.value = minExp.value.replace(/[^0-9]/g, '');
	maxExp.value = maxExp.value.replace(/[^0-9]/g, '');

	let minValue = parseInt(minExp.value) || 0;
	let maxValue = parseInt(maxExp.value) || 0;

	if (minValue > maxValue && maxExp.value !== "") {
		toastr.error("Max Experience should be greater than Min Experience");
		maxExp.value = "";
	} else {
	}
}



function handelDropDown(element) {

	var elementValue = $(element).val();

	if (elementValue != "") {
		$(element).siblings(".formValidation").hide();

	}
	else {
		$(element).siblings(".formValidation").show();

	}
}

function handelDates(event) {
	var elementValue = $(event).val();
	if (elementValue != "") {
		$(event).siblings(".formValidation").hide();
	}
	else {
		$(event).siblings(".formValidation").show();
	}
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

function saveRoundsData() {
	const timelineItems = [];
	const requisitionId = $("#reqId").val();
	const roundTitle = $("#roundTitle").val();
	const roundDescription = $("#roundDescription").val();
	const roundId = $("#roundId").val();

	// Get selected options from the multi-select dropdown
	const selectedOptions = $("#interviewerSelect option:selected");

	// Extract IDs, names, and emails
	const interviewers = selectedOptions.map(function() {
		return {
			id: $(this).val(),
			name: $(this).text().trim(),
			email: $(this).data('email') || ''
		};
	}).get();

	// Validate roundTitle
	if (!roundTitle) {
		toastr.error('Title is required');
		return;
	}

	// Validate roundDescription
	if (!roundDescription) {
		toastr.error('Description is required');
		return;
	}

	// Validate interviewers
	if (interviewers.length === 0) {
		toastr.error('At least one interviewer is required');
		return;
	}

	timelineItems.push({
		roundNumber: "0",
		requisitionId: requisitionId,
		roundTitle: roundTitle,
		roundDescription: roundDescription,
		interviewers: interviewers,
		roundId: roundId === 'null' ? null : roundId
	});

	if (timelineItems.length === 0) {
		toastr.error("No rounds to save.");
		return;
	}

	let roundDataObj = {
		rounds: timelineItems
	};

	console.log(roundDataObj);

	$.ajax({
		url: 'review-hiring-save-interview-rounds',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(roundDataObj),
		success: function(response) {
			if (response.code === "Success") {
				getRequisitionRounds(requisitionId);
				$('#saveRoundsData').attr('disabled', true);
				toastr.success(response.message);
				$('.timeline-item').removeClass('d-none');
				$('#closeInterviewRound').addClass('d-none');
				$('#saveRoundBtn').addClass('d-none');
				$('#addInterviewRound').removeClass('d-none');
				$('.inline-form').remove();
			} else {
				toastr.error("Error");
			}
		},
		error: function(xhr, status, error) {
			toastr.error('Failed to save round: ' + error);
		}
	});
}


function getRequisitionRounds(requisitionId) {
	const candId = "";
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-rounds-list?requisitionId=" + requisitionId + "&candId=" + candId,
	}).then(function(response) {
		if (response.code === "Success") {
			let roundList = JSON.parse(response.body[0]);
			roundList = roundList?.sort((a, b) => a.roundOrder - b.roundOrder);
			$('#timelineContainer').html("").attr('data-requisition-id', requisitionId);

			if (!roundList || roundList.length === 0 || roundList[0] === null) {
				const messageDiv = $('<div class="container" id="error-msg">' +
					'<div class="d-flex justify-content-center align-items-center" style="height: 341px;">' +
					'<h5 class="text-muted not-found">No rounds found for this requisition</h5>' +
					'</div>' +
					'</div>');

				$('#saveRoundsData').attr('disabled', true);
				$('#timelineContainer').append(messageDiv);
			} else {
				$('#saveRoundsData').attr('disabled', false);

				// Create and append timeline items
				roundList.forEach((round) => {
					const interviewers = JSON.parse(round.interviewers);
					const interviewerIds = interviewers.map(interviewer => interviewer.id).join(',');

					const $timelineItem = createTimelineItem(
						round.roundOrder,
						round.title,
						round.description,
						interviewers,
						interviewerIds,
						round.roundId
					);

					$('#timelineContainer').append($timelineItem);
				});

				// Initialize sortable functionality
				initSortableTimeline();
			}
		} else {
			console.log("Failed to fetch data");
		}
	});
}

function initSortableTimeline() {
	console.log("Initializing sortable");
	$("#timelineContainer").sortable({
		handle: ".round-order-handle",
		ghostClass: "sortable-ghost",
		animation: 150,
		update: function(event, ui) {
			console.log("Sorting completed");
			updateRoundOrderNumbers();
			saveNewRoundOrder();
		},
		start: function(event, ui) {
			console.log("Sorting started");
		}
	});
}

function updateRoundOrderNumbers() {
	$('.timeline-item').each(function(index) {
		const newOrder = index + 1;
		$(this).attr('data-round-number', newOrder);
		$(this).find('.order-number').text(newOrder);
	});
}

function saveNewRoundOrder() {
	const requisitionId = $('#timelineContainer').data('requisition-id');
	const rounds = [];

	$('.timeline-item').each(function(index) {
		const roundId = $(this).attr('value');
		const newPosition = index + 1;

		rounds.push({
			roundId: roundId,
			newOrder: newPosition
		});
	});

	const roundDataObj = {
		requisitionId: requisitionId,
		rounds: rounds
	};


	$.ajax({
		url: 'review-hiring-update-round-order',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(roundDataObj),
		success: function(response) {
			if (response.code === "Success") {
				toastr.success('Round order updated successfully');
				// Update the displayed numbers
				updateRoundOrderNumbers();
				getRequisitionRounds(requisitionId);
			} else {
				toastr.error("Failed to update round order");
			}
		},
		error: function(xhr, status, error) {
			toastr.error('Error updating round order: ' + error);
		}
	});
}

let $timelineContainer;
let highestRoundNumber = 0;

function createTimelineItem(roundNumber, title, description, interviewers, selectedInterviewersIds, roundId) {
	const interviewerNames = interviewers.map(interviewer => {
		return `<span class="interviewer-box" value="${interviewer.id}" data-email="${interviewer.email}">
            ${interviewer.name}
        </span>`;
	}).join(' ');

	const $timelineItem = $(`
        <div class="timeline-item" data-round-number="${roundNumber}" value="${roundId === null ? 'null' : roundId}">
            <div class="timeline-content">
                <div class="round-order-handle" title="Drag to reorder">
                    <i class="bi bi-grip-vertical"></i>
                    <span class="order-number">${roundNumber}</span>
                </div>
                <input type="hidden" class="round-id" value="${roundId}"/>
                <h2><span class="round-title">${title}</span></h2>
                <p class="round-description">${description || 'No description provided.'}</p>
                <input type="hidden" class="interviewers-ids" value="${selectedInterviewersIds}">
                <p><strong>Interviewers:</strong>
                    <div class="interviewer-names-container">
                        ${interviewerNames}
                    </div>
                </p>
				<div class="action-icons">
	                <i class="bi bi-pencil-square edit-round" title="Edit Round" data-round-id="${roundId}" data-round-number="${roundNumber}"></i>
	                <i class="bi bi-trash delete-round" title="Delete Round" data-round-id="${roundId}" data-round-number="${roundNumber}" onclick="deleteRounds('${roundId}');"></i>
	            </div>
            </div>
        </div>
    `);

	return $timelineItem;
}
function fetchEmployeeList() {
	return agGrid.simpleHttpRequest({
		url: "review-hiring-get-employee-list"
	}).then(function(response) {
		if (response.code === "Success") {
			const employeeList = JSON.parse(response.body[0]);
			return employeeList;
		} else {
			console.log("Failed to fetch data");
			return [];
		}
	});
}

function editRound(roundId, roundNumber) {
	$('.timeline-item').addClass('d-none');
	$('#closeInterviewRound').removeClass('d-none');
	$('#saveRoundBtn').removeClass('d-none');
	$('#addInterviewRound').addClass('d-none');
	$('#error-msg').empty();
	$('#saveRoundsData').attr('disabled', false);

	// Remove any existing form first
	$('.inline-form').remove();

	// Get the timeline item being edited
	const $timelineItem = $(`.timeline-item[data-round-number="${roundNumber}"]`);

	// Get the existing data
	const title = $timelineItem.find('.round-title').text();
	const description = $timelineItem.find('.round-description').text();
	const selectedInterviewersIds = $timelineItem.find('.interviewers-ids').val().split(',');


	fetchEmployeeList().then(function(employeeList) {
		// Create the edit form
		const $inlineForm = $('<div>', { class: 'inline-form' }).html(
			`<input type="text" id="roundTitle" placeholder="Enter Round Title" required class="form-control mb-2" value="${title}">
             <input type="hidden" id="roundId" value="${roundId}"/>
             <label><strong>Interviewer(s)</strong></label>
             <select id="interviewerSelect" multiple class="form-control mb-2 chosen-select" required>
                 <option value="" disabled>Select Interviewers</option>
                 ${employeeList.map(interviewer =>
				`<option value="${interviewer.empId}" data-email="${interviewer.empMail || ''}" 
                     ${selectedInterviewersIds.includes(interviewer.empId.toString()) ? 'selected' : ''}>
                     ${interviewer.empName}
                     </option>`
			).join('')}
             </select>
             <textarea id="roundDescription" rows="2" placeholder="Enter Round Description" class="form-control mb-2">${description}</textarea>
             <input type="hidden" id="selectedInterviewersIds" name="selectedInterviewersIds">`
		);

		$timelineContainer.append($inlineForm);
		$inlineForm.show();

		// Apply Chosen to the select element
		$inlineForm.find('#interviewerSelect').chosen();

		// Close button handler
		const closeInterviewRound = document.getElementById('closeInterviewRound');
		closeInterviewRound.onclick = function() {
			$('.inline-form').remove();
			$('.timeline-item').removeClass('d-none');
			$('#closeInterviewRound').addClass('d-none');
			$('#saveRoundBtn').addClass('d-none');
			$('#addInterviewRound').removeClass('d-none');
		};
	});
}

function deleteRounds(roundId) {
	agGrid.simpleHttpRequest({
		url: "review-hiring-delete-rounds?roundId=" + roundId,
	}).then(function(response) {
		if (response.code === "Success") {
			toastr.success(response.message);
			const requisitionId = $('#timelineContainer').data('requisition-id');
			getRequisitionRounds(requisitionId);
		}
	});
}

$(document).ready(function() {
	// Initialize variables
	$timelineContainer = $('#timelineContainer');
	const $addInterviewRoundBtn = $('#addInterviewRound');
	let roundCounter = $timelineContainer.children('.timeline-item').length;
	highestRoundNumber = roundCounter;

	// Event delegation for edit buttons
	$(document).on('click', '.edit-round', function() {
		const roundId = $(this).data('round-id');
		const roundNumber = $(this).data('round-number');
		editRound(roundId, roundNumber);
	});

	// Update the "Add Interview Round" button handler
	$addInterviewRoundBtn.on('click', function() {
		// Hide all existing timeline items
		$('.timeline-item').addClass('d-none');
		$('#closeInterviewRound').removeClass('d-none');
		$('#saveRoundBtn').removeClass('d-none');
		$('#error-msg').empty();
		$('#addInterviewRound').addClass('d-none');
		$('#saveRoundsData').attr('disabled', false);

		// Remove any existing form first
		$('.inline-form').remove();

		fetchEmployeeList().then(function(employeeList) {
			// Create a new form dynamically
			const $inlineForm = $('<div>', { class: 'inline-form' }).html(
				`<input type="text" id="roundTitle" placeholder="Enter Round Title" required class="form-control mb-2">
                <input type="hidden" id="roundId"/>
                <label><strong>Interviewer(s)</strong></label>
                <select id="interviewerSelect" multiple class="form-control mb-2 chosen-select" required>
                    <option value="" disabled>Select Interviewers</option>
                    ${employeeList.map(interviewer =>
					`<option value="${interviewer.empId}" data-email="${interviewer.empMail || ''}">${interviewer.empName}</option>`
				).join('')}
                </select>
                <textarea id="roundDescription" rows="2" placeholder="Enter Round Description" class="form-control mb-2"></textarea>
                <input type="hidden" id="selectedInterviewersIds" name="selectedInterviewersIds">`
			);

			$timelineContainer.append($inlineForm);
			$inlineForm.show();

			// Apply Chosen to the select element
			$inlineForm.find('#interviewerSelect').chosen();

			const closeInterviewRound = document.getElementById('closeInterviewRound');
			closeInterviewRound.addEventListener('click', function() {
				$('.inline-form').remove();
				// Show all timeline items again when closing
				$('#closeInterviewRound').addClass('d-none');
				$('#saveRoundBtn').addClass('d-none');
				$('.timeline-item').removeClass('d-none');
				$('#addInterviewRound').removeClass('d-none');
			});

			/*const saveRoundBtn = document.getElementById('saveRoundBtn');
			saveRoundBtn.addEventListener('click', function() {
				const title = $('#roundTitle').val().trim();
				const description = $('#roundDescription').val().trim();

				const selectedInterviewers = $('#interviewerSelect').find('option:selected').map(function() {
					return {
						name: $(this).text(),
						email: $(this).data('email'),
						id: $(this).val()
					};
				}).get();

				if (!title) {
					toastr.error('Title required');
					return;
				}
				
				if (!description) {
					toastr.error('Description required');
					return;
				}

				const selectedInterviewersIds = selectedInterviewers.map(interviewer => interviewer.id).join(',');
				$('#selectedInterviewersIds').val(selectedInterviewersIds);

				// Ensure highestRoundNumber is an integer before incrementing
				highestRoundNumber = parseInt(highestRoundNumber) || 0;
				const newRoundNumber = highestRoundNumber + 1;
				highestRoundNumber = newRoundNumber; // Update highest round number

				const $timelineItem = createTimelineItem(
					newRoundNumber,
					title,
					description,
					selectedInterviewers,
					selectedInterviewersIds,
					null
				);

				$timelineContainer.append($timelineItem);
				$('.timeline-item').removeClass('d-none');
				$('#closeInterviewRound').addClass('d-none');
				$('#addInterviewRound').removeClass('d-none');
				$('#saveRoundBtn').addClass('d-none');
				$('.inline-form').remove();

				// Clear form
				$('#roundTitle').val('');
				$('#roundDescription').val('');
				$('#interviewerSelect').val('').trigger('chosen:updated');
			});*/
		});
	});
});

/*function fetchEmployeeList() {
	let type = "Recruitment";
	agGrid.simpleHttpRequest({
		url: "review-hiring-get-application-log?type=" + type,
	}).then(function(response) {
		if (response.code === "Success") {
			const employeeList = JSON.parse(response.body[0]);

		} else {
			console.log("Failed to fetch data");
		}
	});
}*/
function generateJdWithAI() {
	$(".loader").show();
	var benefitNames = [];

	const aboutCompany = `Nirmalya is a team of experienced technical and business professionals that help our customers to achieve
         their 'Operations and Maintenance Performance Management' goals. Our experts minimize inefficiencies 360 degrees 
         focusing Assets, Processes, Technology, Materials, People, Infrastructure, and Energy. We have worked hand-in-hand 
         with our customers, creating industry-specific software solutions and services that enable a world of better business.
        Nirmalya stands at the forefront as a prominent supplier of advanced cloud-based systems such as next-generation 
        Enterprise Resource Planning, Supply Chain Management, Smart Warehouse Management, Human Capital Management, 
        Enterprise Asset Management, and Electronic Document Management System. In the face of a volatile environment filled with 
        supply disruptions and demand fluctuations, manufacturers and supply chains are compelled to swiftly adapt to these changes
         and optimize their agility, efficiency, and resilience in order to provide impeccable customer service. Nirmalya 
         caters customized solutions that facilitate digital transformation and empower enterprises to thrive in this era 
         of technological advancement`;

	$("input:checkbox[name=benefits]:checked").each(function() {
		var benefitName = $(this).next('span').text();
		benefitNames.push(benefitName);
	});

	var minSalary = parseFloat($('#minSalary').val().replace(/\D/g, ''));
	var maxSalary = parseFloat($('#maxSalary').val().replace(/\D/g, ''));
	var jobTitle = $("#jobTitle option:selected").text();
	var obj = {};
	obj.benefits = benefitNames;
	obj.jobTitle = jobTitle;
	obj.jobType = $('#jobType').find(":selected").text();
	obj.jobLocation = $('#location').find(":selected").text();
	obj.minEducation = $('#educationReq').find(":selected").text();
	obj.minSalary = minSalary;
	obj.maxSalary = maxSalary;
	obj.department = $('#dept').find(":selected").text();
	obj.noPosition = $('#noPos').val();
	obj.workHour = $('#workHour').find(":selected").text();
	obj.band = $('#band').find(":selected").text();
	obj.designation = $('#designation').find(":selected").text();
	obj.joinDate = $('#dateReq').val();
	obj.applyStartDate = $('#applyStartDate').val();
	obj.applyEndDate = $('#applyEndDate').val();
	obj.about = aboutCompany;
	obj.minExp = $("#minExp").val();
	obj.maxExp = $("#maxExp").val();

	var skillTable = document.querySelectorAll("#skillTable tbody tr");
	var skillsData = [];
	var isValid = true;

	skillTable.forEach(function(row, index) {
		var skill = row.querySelector("select.progLang1").value;
		var experience = row.querySelector("select.progLang2").value;
		var ratings = row.querySelector("select.progLang3").value;
		var skillsCompetency = $(`#skills-competency_${index + 1}`).val();

		if (!skill || !experience || !ratings) {
			isValid = false;
		} else {
			var skillObject = {};
			skillObject[skill] = experience;
			skillObject["ratings"] = ratings;
			skillObject["skillsCompetency"] = skillsCompetency;
			skillsData.push(skillObject);
		}
	});
	obj.skillsData = skillsData;

	console.log(obj);

	// Send AJAX request to backend
	$.ajax({
		url: "generate-jd",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			console.log("Full response:", response);

			if (response && response.body) {
				const textarea = $('#jobDescResp');
				const summaryTextarea = $('#txtAreaSumN');
				const responsibilitiesTextarea = $('#txtAreaResN');

				// Clear all textareas
				textarea.val('').show();
				summaryTextarea.val('').show();
				responsibilitiesTextarea.val('').show();

				// Get the content
				const content = response.body.raw || response.body.markdown || '';
				const formattedContent = formatJobDescription(content);

				// Extract sections with improved logic
				const jobOverview = extractSection(content, "Job Overview:", ["Key Responsibilities:", "Required Qualifications"]);
				const keyResponsibilities = extractSection(content, "Key Responsibilities:", ["Required Qualifications", "Preferred Attributes"]);

				// Display in textareas with typing effect
				typeWriter(formattedContent, textarea, 10);

				// Type the extracted sections immediately (without animation)
				summaryTextarea.val(jobOverview);
				responsibilitiesTextarea.val(keyResponsibilities);

				// Update character counters
				textCount({ target: summaryTextarea[0] });
				textCount({ target: responsibilitiesTextarea[0] });

				// Also update HTML view if needed
				if (response.body.html) {
					$('#jobDescriptionContainer').html(response.body.html);
				}
			} else {
				console.error('Invalid response format');
				$('#jobDescriptionContainer').html('<p class="error">Error generating job description</p>');
				$('#jobDescResp').val('Error: Invalid response from server').show();
			}

			$(".loader").hide();
		},
		error: function(xhr, status, error) {
			console.error("Error generating JD:", error);
			$('#jobDescriptionContainer').html('<p class="error">Error: ' + error + '</p>');
			$('#jobDescResp').val('Error: ' + error);
			$(".loader").hide();
		}
	});

	// Improved section extraction function
	function extractSection(content, sectionTitle, nextSectionMarkers = []) {
		const startIndex = content.indexOf(sectionTitle);
		if (startIndex === -1) return "";

		// Find the end of the section by looking for the next section marker
		let endIndex = content.length;
		for (const marker of nextSectionMarkers) {
			const markerIndex = content.indexOf(marker, startIndex);
			if (markerIndex !== -1 && markerIndex < endIndex) {
				endIndex = markerIndex;
			}
		}

		// If no markers found, look for double newlines
		if (endIndex === content.length) {
			endIndex = content.indexOf("\n\n", startIndex);
			if (endIndex === -1) endIndex = content.length;
		}

		let sectionContent = content.substring(startIndex, endIndex).trim();

		// Clean up the content
		sectionContent = sectionContent
			.replace(/\*\*/g, '')      // Remove markdown bold
			.replace(/^#+\s*/gm, '')   // Remove markdown headers
			.replace(/^[=-]+$/gm, '')  // Remove underline bars
			.replace(/^- /gm, '• ');   // Standardize bullet points

		return sectionContent;
	}

	// Format job description with bold headers
	function formatJobDescription(text) {
		// Process each line
		return text.split('\n').map(line => {
			// Identify key-value headers (Job Title:, Location:, etc.)
			if (line.match(/^[A-Z][a-zA-Z\s]+:\s*$/) ||
				line.match(/^[A-Z][a-zA-Z\s]+:$/) ||
				line.match(/^[A-Z\s]+:$/)) {
				return `\n${line.toUpperCase()}\n`;
			}
			// Identify section headers (About Us, Job Overview, etc.)
			else if (line.match(/^[A-Z][a-zA-Z\s]+:$/) ||
				line.match(/^[A-Z\s]+:$/) ||
				line.match(/^[A-Z][a-zA-Z\s]+:$/)) {
				return `\n${line.toUpperCase()}\n`;
			}
			return line;
		}).join('\n')
			.replace(/\*\*/g, '')      // Remove markdown bold
			.replace(/^#+\s*/gm, '')   // Remove markdown headers
			.replace(/^[=-]+$/gm, '')  // Remove underline bars
			.replace(/^- /gm, '• ');   // Standardize bullet points
	}
}

// Typing animation function (unchanged)
function typeWriter(text, element, speed) {
	let i = 0;
	element.val('');

	function typing() {
		if (i < text.length) {
			const currentChar = text.charAt(i);
			const nextChar = text.charAt(i + 1);

			element.val(element.val() + currentChar);
			element.scrollTop(element[0].scrollHeight);

			if (currentChar === '\n') {
				i++;
				setTimeout(typing, speed * 2);
			}
			else if (currentChar === '•' && text.charAt(i - 1) === '\n') {
				i++;
				setTimeout(typing, speed * 2);
			}
			else {
				i++;
				setTimeout(typing, speed);
			}
		}
	}

	typing();
}
// Auto-resize textarea function
function autoResizeTextarea() {
	const textarea = $('#jobDescResp');
	textarea.css('height', 'auto').css('height', textarea[0].scrollHeight + 'px');
}

// Initialize on document ready
$(document).ready(function() {
	$(document).on('input', '#jobDescResp', autoResizeTextarea);
});

function addSkillsKras(rowIndexId) {
	$('#skillsModal').modal('show');
	$('#rowIndex').val(rowIndexId);
	let skills = $(`#language_${rowIndexId}`).find(":selected").text();
	$('#skillsModalLabel').html(skills);
}

document.addEventListener('DOMContentLoaded', function() {
	const skillsContainer = document.getElementById('skillsContainer');
	const addSkillBtn = document.getElementById('addSkillBtn');
	const emptyFieldAlert = document.getElementById('emptyFieldAlert');
	const saveSkillsBtn = document.getElementById('saveSkillsBtn');
	let skillCount = 1;

	const skillExamples = [
		"Example: '5+ years Python with Django framework experience'",
		"Example: 'Azure cloud architecture certification required'",
		"Example: 'Team leadership in agile environments (Scrum certified)'",
		"Example: 'Fluency in Spanish for customer support role'",
		"Example: 'Data analysis with Power BI and advanced Excel skills'",
		"Example: 'UI/UX design with Figma and Adobe XD proficiency'"
	];

	// Function to validate a specific skill input by ID
	function validateSkillInput(inputId) {
		const input = document.getElementById(inputId);
		if (!input) return false;

		if (input.value.trim() === '') {
			input.classList.add('empty-field');
			input.focus();
			setTimeout(() => {
				input.classList.remove('empty-field');
			}, 500);
			return false;
		}
		return true;
	}

	// Add new skill
	addSkillBtn.addEventListener('click', function() {
		// Validate all existing inputs before adding a new one
		const allInputs = document.querySelectorAll('.competency-input');
		let allValid = true;

		allInputs.forEach(input => {
			if (!validateSkillInput(input.id)) {
				allValid = false;
			}
		});

		if (!allValid) {
			emptyFieldAlert.classList.remove('d-none');
			return;
		}

		// Hide error message if all fields are valid
		emptyFieldAlert.classList.add('d-none');

		// Proceed with adding new skill
		skillCount++;
		const randomExample = skillExamples[Math.floor(Math.random() * skillExamples.length)];
		const newSkillId = `competency-input_${skillCount}`;

		const newSkill = document.createElement('div');
		newSkill.className = 'skill-item mb-2 p-3 rounded-3 bg-light position-relative';
		newSkill.innerHTML = `
      <div class="d-flex align-items-center">
        <div class="skill-number d-flex align-items-center justify-content-center rounded-circle bg-main text-white me-3" style="width: 32px; height: 32px; flex-shrink: 0;">
          ${skillCount}
        </div>
        <input type="text" class="form-control flex-grow-1 competency-input" placeholder="${randomExample}" id="${newSkillId}">
        <button type="button" class="btn-close ms-2 remove-skill" aria-label="Remove skill"></button>
      </div>
      <div class="mt-2 ps-5">
        <small class="text-muted">Specify minimum years, certifications, or proficiency level needed</small>
      </div>
    `;
		skillsContainer.appendChild(newSkill);

		// Scroll to and focus the new skill
		newSkill.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
		document.getElementById(newSkillId).focus();
	});

	// Save skills data
	saveSkillsBtn.addEventListener('click', function() {
		const competencyInputs = document.querySelectorAll('.competency-input');
		const skillsData = [];
		let hasEmptyFields = false;

		competencyInputs.forEach((input, index) => {
			const skillName = input.value.trim();

			if (!skillName) {
				hasEmptyFields = true;
				input.classList.add('empty-field');
				setTimeout(() => input.classList.remove('empty-field'), 500);
			}

			skillsData.push({
				id: (index + 1).toString(),
				name: skillName || `Skill ${index + 1}` // Fallback if empty
			});
		});

		if (hasEmptyFields) {
			toastr.error('Please fill all competency fields before saving.');
			return;
		}
		let rowIndex = $("#rowIndex").val();
		// Here's your data in the requested format
		console.log('Skills Competency Data:', skillsData);
		$(`#skills-competency_${rowIndex}`).val(JSON.stringify(skillsData));
		closeCompetency();
	});

	// Remove skill
	skillsContainer.addEventListener('click', function(e) {
		if (e.target.classList.contains('remove-skill')) {
			const skillItem = e.target.closest('.skill-item');
			if (skillItem && skillCount > 1) {
				skillItem.remove();
				skillCount--;

				// Update numbers on remaining skills
				const skillNumbers = document.querySelectorAll('.skill-number');
				skillNumbers.forEach((num, index) => {
					num.textContent = index + 1;
				});

				// Hide error message if it was shown
				emptyFieldAlert.classList.add('d-none');
			}
		}
	});

	// Hide error when user starts typing in empty field
	document.addEventListener('input', function(e) {
		if (e.target.classList.contains('competency-input')) {
			const inputId = e.target.id;
			if (validateSkillInput(inputId)) {
				emptyFieldAlert.classList.add('d-none');
			}
		}
	});
});

function closeCompetency() {
	$('#skillsModal').modal('hide');
	$('#rowIndex').val("");
	const skillsContainer = document.getElementById('skillsContainer');
	skillsContainer.innerHTML = `
        <div class="skill-item mb-2 p-3 rounded-3 bg-light position-relative">
            <div class="d-flex align-items-center">
                <!-- Circular Number Badge -->
                <div class="skill-number d-flex align-items-center justify-content-center rounded-circle bg-main text-white me-3" 
                     style="width: 32px; height: 32px; flex-shrink: 0;">
                    1
                </div>
                <!-- Skill Input -->
                <input type="text" class="form-control flex-grow-1 competency-input" id="competency-input_1"
                     placeholder="Example: 'Advanced React.js with Redux experience'">
                <!-- Remove button (hidden for first item) -->
                <button type="button" class="btn-close ms-2 remove-skill d-none"
                        aria-label="Remove skill"></button>
            </div>
            <div class="mt-2 ps-5">
                <small class="text-muted">Be specific about required expertise level and technologies</small>
            </div>
        </div>
    `;

	// Reset the skill counter
	skillCount = 1;
}

function viewSkillsKras(rowIndexId) {
	let data = $(`#skills-competency_${rowIndexId}`).val();

	// If no data exists, call addSkillsKras
	if (!data || data.trim() === '') {
		addSkillsKras(rowIndexId);
		return;
	}

	// Clear the existing skills container
	const skillsContainer = document.getElementById('skillsContainer');
	skillsContainer.innerHTML = '';

	try {
		const skillsData = JSON.parse(data);
		skillCount = skillsData.length; // Update the global skill counter

		// Only proceed if we actually have skills data
		if (skillsData.length > 0) {
			// Add each skill to the modal
			skillsData.forEach((skill, index) => {
				const skillId = `competency-input_${index + 1}`;
				const skillItem = document.createElement('div');
				skillItem.className = 'skill-item mb-3 p-3 rounded-3 bg-light position-relative';
				skillItem.innerHTML = `
                    <div class="d-flex align-items-center">
                        <div class="skill-number d-flex align-items-center justify-content-center rounded-circle bg-main text-white me-3" style="width: 32px; height: 32px; flex-shrink: 0;">
                            ${index + 1}
                        </div>
                        <input type="text" class="form-control flex-grow-1 competency-input" value="${skill.name.replace(/"/g, '&quot;')}" id="${skillId}">
                        <button type="button" class="btn-close ms-2 remove-skill ${index === 0 ? 'd-none' : ''}" aria-label="Remove skill"></button>
                    </div>
                    <div class="mt-2 ps-5">
                        <small class="text-muted">Specify minimum years, certifications, or proficiency level needed</small>
                    </div>
                `;
				skillsContainer.appendChild(skillItem);
			});

			// Show the modal with populated data
			$('#rowIndex').val(rowIndexId);
			$('#skillsModal').modal('show');
		} else {
			// If array is empty, show default modal
			addSkillsKras(rowIndexId);
		}
	} catch (e) {
		console.error('Error parsing skills data:', e);
		// If data is invalid, show default modal
		addSkillsKras(rowIndexId);
	}
}

function handlePdfClickd(requisitionId) {
	alert("Clicked PDF for Requisition ID: " + requisitionId);

	let pdfData = $('#jobDescResp').val();
	console.log("Pdf Data::::", pdfData);

	let htmlContent = generateHtmlFromData(pdfData);

	$.ajax({
		url: '/recruitment/generate-pdf',
		type: 'POST',
		contentType: 'application/json',
		data: htmlContent,
		success: function(response) {
			// Handle the response as a Blob directly
			var blob = new Blob([response], { type: 'application/pdf' });
			var blobUrl = URL.createObjectURL(blob);
			window.open(blobUrl, '_blank');
		},
		error: function(xhr, status, error) {
			console.error("Error generating PDF:", error);
			alert("Error generating PDF. Please try again.");
		}
	});
}

function generateHtmlFromData(pdfData) {
	// Start with a very simple template to test
	return `
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <style>
            body { font-family: Arial; margin: 20px; }
            h1 { color: #333; }
            p { margin-bottom: 10px; }
        </style>
    </head>
    <body>
        <h1>Test PDF Generation</h1>
        <p>If you can see this text, PDF generation is working.</p>
        <p>Job Title: ${pdfData.jobTitle || 'Test Title'}</p>
    </body>
    </html>
    `;
}

function handlePdfClick(requisitionId) {
	let pdfData = `
	    <!DOCTYPE html>
	    <html>
	    <head>
	        <meta charset="UTF-8">
	        <style>
	            body { font-family: Arial; margin: 20px; }
	            h1 { color: #333; }
	            p { margin-bottom: 10px; }
	        </style>
	    </head>
	    <body>
	        <h1>Test PDF Generation</h1>
	        <p>If you can see this text, PDF generation is working.</p>
	        <p>Job Title: ${pdfData.jobTitle || 'Test Title'}</p>
	    </body>
	    </html>
	    `; // Assume this contains valid HTML
	console.log("Pdf Data:", pdfData);

	$.ajax({
		url: '/recruitment/generate-pdf',
		type: 'POST',
		contentType: 'application/json', // Send as JSON
		data: JSON.stringify({ html: pdfData }), // Send HTML inside JSON
		xhrFields: {
			responseType: 'blob' // Important to receive binary data
		},
		success: function(response) {
			const blob = new Blob([response], { type: 'application/pdf' });
			const blobUrl = URL.createObjectURL(blob);

			const newWindow = window.open(blobUrl, '_blank');
			if (!newWindow) {
				alert('Popup blocked. Please allow popups for this site.');
			}
		},
		error: function(xhr, status, error) {
			console.error("Error generating PDF:", error);
			alert("Error generating PDF. Please check console for details.");
		}
	});
}


function generateHtmlFromData22(pdfData) {
	// Parse the text data into structured format
	const jobData = parseJobData(pdfData);

	// Generate the HTML using the template
	return `
	<!DOCTYPE html>
	<html lang="en">
	<head>
	    <meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <title>${jobData.jobTitle} - Nirmalya Labs</title>
	    <style>
	        body {
	            font-family: 'Inter', -apple-system, BlinkMacSystemFont, sans-serif;
	            line-height: 1.6;
	            color: #2d3748;
	            max-width: 800px;
	            margin: 0 auto;
	            padding: 0;
	            background-color: white;
	        }
	        
	        .container {
	            padding: 0;
	        }
	        
	        .header {
	            background: linear-gradient(135deg, rgb(191, 5, 255), rgb(150, 0, 210));
	            color: white;
	            padding: 40px 40px 30px;
	            text-align: center;
	            position: relative;
	            overflow: hidden;
	        }
	        
	        .logo-container {
	            margin-bottom: 20px;
	            background-color: white;
	            padding: 15px;
	            border-radius: 8px;
	            display: inline-block;
	            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
	        }
	        
	        .logo {
	            height: 40px;
	            width: auto;
	        }
	        
	        .job-title {
	            font-size: 2.2rem;
	            font-weight: 800;
	            margin-bottom: 8px;
	            letter-spacing: -0.5px;
	            color: white;
	        }
	        
	        .company-name {
	            font-size: 1.2rem;
	            font-weight: 500;
	            opacity: 0.9;
	            margin-bottom: 20px;
	            color: white;
	        }
	        
	        .meta-grid {
	            display: table;
	            width: 100%;
	            margin: 25px 0;
	            table-layout: fixed;
	            border-spacing: 12px;
	        }
	        
	        .meta-row {
	            display: table-row;
	        }
	        
	        .meta-card {
	            display: table-cell;
	            background-color: rgba(255,255,255,0.15);
	            padding: 12px;
	            border-radius: 8px;
	            text-align: center;
	            backdrop-filter: blur(5px);
	            border: 1px solid rgba(255,255,255,0.2);
	            vertical-align: top;
	        }
	        
	        .meta-label {
	            font-size: 0.75rem;
	            text-transform: uppercase;
	            letter-spacing: 0.5px;
	            margin-bottom: 4px;
	            opacity: 0.8;
	            color: white;
	        }
	        
	        .meta-value {
	            font-weight: 600;
	            font-size: 1rem;
	            color: white;
	        }
	        
	        .btn-apply {
	            display: inline-block;
	            background-color: white;
	            color: rgb(191, 5, 255);
	            padding: 12px 28px;
	            text-decoration: none;
	            border-radius: 50px;
	            font-weight: 600;
	            font-size: 1rem;
	            margin: 10px 0;
	            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
	        }
	        
	        .content-area {
	            padding: 40px;
	        }
	        
	        .section {
	            margin-bottom: 40px;
	        }
	        
	        .section-title {
	            font-size: 1.5rem;
	            font-weight: 700;
	            color: #1a1a2e;
	            margin-bottom: 20px;
	            position: relative;
	            padding-bottom: 10px;
	        }
	        
	        .section-title:after {
	            content: '';
	            position: absolute;
	            left: 0;
	            bottom: 0;
	            width: 50px;
	            height: 3px;
	            background: rgb(191, 5, 255);
	            border-radius: 3px;
	        }
	        
	        .tag-container {
	            margin: 15px 0;
	            font-size: 0;
	        }
	        
	        .tag {
	            background-color: rgba(191, 5, 255, 0.1);
	            color: rgb(191, 5, 255);
	            padding: 6px 12px;
	            border-radius: 50px;
	            font-size: 0.85rem;
	            font-weight: 500;
	            display: inline-block;
	            margin: 0 8px 8px 0;
	        }
	        
	        ul {
	            padding-left: 20px;
	        }
	        
	        li {
	            margin-bottom: 12px;
	            position: relative;
	        }
	        
	        li:before {
	            content: '•';
	            color: rgb(191, 5, 255);
	            font-weight: bold;
	            display: inline-block;
	            width: 1em;
	            margin-left: -1em;
	        }
	        
	        .highlight-card {
	            background-color: rgba(191, 5, 255, 0.1);
	            border-radius: 12px;
	            padding: 25px;
	            margin: 30px 0;
	            border: 1px solid rgba(191, 5, 255, 0.2);
	        }
	        
	        .card-title {
	            font-weight: 700;
	            color: #1a1a2e;
	            margin-bottom: 15px;
	            font-size: 1.1rem;
	        }
	        
	        .two-column {
	            display: table;
	            width: 100%;
	            margin-bottom: 30px;
	        }
	        
	        .column {
	            display: table-cell;
	            width: 50%;
	            padding-right: 30px;
	            vertical-align: top;
	        }
	        
	        .column:last-child {
	            padding-right: 0;
	        }
	        
	        .deadline-notice {
	            background-color: #fff8e6;
	            border-left: 4px solid rgb(191, 5, 255);
	            padding: 15px;
	            border-radius: 0 8px 8px 0;
	            margin: 25px 0;
	            font-size: 0.95rem;
	        }
	        
	        .deadline-icon {
	            color: rgb(191, 5, 255);
	            font-weight: bold;
	            font-size: 1.2rem;
	            margin-right: 10px;
	        }
	        
	        .deadline-text {
	            font-weight: 600;
	            color: #1a1a2e;
	        }
	        
	        @media (max-width: 768px) {
	            .two-column, .column {
	                display: block;
	                width: 100%;
	                padding-right: 0;
	            }
	            
	            .meta-grid, .meta-card {
	                display: block;
	                width: 100%;
	                margin-bottom: 12px;
	            }
	            
	            .content-area {
	                padding: 30px 20px;
	            }
	            
	            .header {
	                padding: 30px 20px;
	            }
	            
	            .job-title {
	                font-size: 1.8rem;
	            }
	        }
	        
	        .footer {
	            text-align: center;
	            padding: 30px;
	            color: #718096;
	            font-size: 0.9rem;
	            border-top: 1px solid #eee;
	        }
	        
	        .footer-links {
	            margin-top: 15px;
	        }
	        
	        .footer-link {
	            color: rgb(191, 5, 255);
	            text-decoration: none;
	            margin: 0 10px;
	            font-weight: 500;
	        }
	    </style>
	</head>
	<body>
	    <div class="header">
	        <div class="logo-container">
	            <img src="https://www.nerp.in/assets/images/logo1.png" alt="Nirmalya Labs Logo" class="logo" onerror="this.src='https://via.placeholder.com/150x40?text=Nirmalya+Labs';this.style.border='1px solid #eee'">
	        </div>
	        
	        <div class="job-title">${jobData.jobTitle}</div>
	        <div class="company-name">${jobData.company}</div>
	        
	        <div class="meta-grid">
	            <div class="meta-row">
	                <div class="meta-card">
	                    <div class="meta-label">Location</div>
	                    <div class="meta-value">${jobData.location}</div>
	                </div>
	                <div class="meta-card">
	                    <div class="meta-label">Job Type</div>
	                    <div class="meta-value">${jobData.jobType}</div>
	                </div>
	                <div class="meta-card">
	                    <div class="meta-label">Department</div>
	                    <div class="meta-value">${jobData.department}</div>
	                </div>
	                <div class="meta-card">
	                    <div class="meta-label">Positions</div>
	                    <div class="meta-value">${jobData.numberOfPositions}</div>
	                </div>
	            </div>
	        </div>
	        
	        <a href="#" class="btn-apply">Apply Now</a>
	    </div>
	    
	    <div class="content-area">
	        <div class="deadline-notice">
	            <span class="deadline-icon">⏰</span>
	            <span class="deadline-text">Application Period: ${jobData.applicationPeriod}</span><br>
	            Expected Joining: ${jobData.expectedJoiningDate}
	        </div>
	        
	        <div class="section">
	            <div class="section-title">About the Role</div>
	            <p>${jobData.jobOverview || 'Join our team in this exciting role.'}</p>
	            
	            <div class="tag-container">
	                ${jobData.tags ? jobData.tags.map(tag => `<span class="tag">${tag}</span>`).join('') : ''}
	            </div>
	        </div>
	        
	        <div class="section">
	            <div class="section-title">About Nirmalya</div>
	            <p>${jobData.aboutUs || 'Nirmalya is a team of experienced professionals helping clients achieve Operations and Maintenance Performance Management goals.'}</p>
	        </div>
	        
	        ${jobData.keyResponsibilities ? `
	        <div class="section">
	            <div class="section-title">Key Responsibilities</div>
	            <ul>
	                ${jobData.keyResponsibilities.map(resp => `<li>${resp}</li>`).join('')}
	            </ul>
	        </div>
	        ` : ''}
	        
	        <div class="two-column">
	            <div class="column">
	                <div class="section">
	                    <div class="section-title">Requirements</div>
	                    <div class="card-title">Must Have</div>
	                    <ul>
	                        ${jobData.requiredQualifications ? jobData.requiredQualifications.map(qual => `<li>${qual}</li>`).join('') : ''}
	                    </ul>
	                    
	                    ${jobData.preferredAttributes ? `
	                    <div class="card-title">Preferred Attributes</div>
	                    <ul>
	                        ${jobData.preferredAttributes.map(attr => `<li>${attr}</li>`).join('')}
	                    </ul>
	                    ` : ''}
	                    
	                    <div class="card-title">Work Schedule</div>
	                    <p>${jobData.workHours || 'Standard business hours'}</p>
	                </div>
	            </div>
	            
	            <div class="column">
	                <div class="highlight-card">
	                    <div class="card-title">Compensation & Benefits</div>
	                    <ul>
	                        ${jobData.compensationBenefits ? jobData.compensationBenefits.map(benefit => `<li>${benefit}</li>`).join('') : ''}
	                    </ul>
	                </div>
	                
	                ${jobData.interviewProcess ? `
	                <div class="section">
	                    <div class="card-title">Interview Process</div>
	                    <ul>
	                        ${jobData.interviewProcess.map(step => `<li>${step}</li>`).join('')}
	                    </ul>
	                </div>
	                ` : ''}
	            </div>
	        </div>
	        
	        <div class="highlight-card">
	            <div class="section-title">How to Apply</div>
	            <p>${jobData.howToApply || 'Please submit your application through our website.'}</p>
	            
	            ${jobData.additionalInstructions ? `
	            <div class="card-title">Application Instructions</div>
	            <ul>
	                ${jobData.additionalInstructions.map(instruction => `<li>${instruction}</li>`).join('')}
	            </ul>
	            ` : ''}
	            
	            <div style="text-align: center; margin-top: 25px;">
	                <a href="#" class="btn-apply" style="background-color: rgb(191, 5, 255); color: white;">Submit Application</a>
	            </div>
	            
	            <p style="font-size: 0.9rem; margin-top: 20px; color: #718096; text-align: center;">
	                ${jobData.applicationNote || 'Late applications will not be considered. Please ensure all materials are complete.'}
	            </p>
	        </div>
	    </div>
	    
	    <div class="footer">
	        <div class="footer-links">
	            <a href="https://www.nerp.in" class="footer-link">Our Website</a>
	            <a href="#" class="footer-link">Careers</a>
	            <a href="#" class="footer-link">Privacy Policy</a>
	            <a href="#" class="footer-link">Contact Us</a>
	        </div>
	        <p>© ${new Date().getFullYear()} Nirmalya Labs Private Limited. All rights reserved.</p>
	    </div>
	</body>
	</html>
    `;
}

function parseJobData(textData) {
	// This function parses the text data into a structured object
	const data = {
		jobTitle: extractValue(textData, 'Job Title:'),
		company: extractValue(textData, 'Company:'),
		location: extractValue(textData, 'Location:'),
		jobType: extractValue(textData, 'Job Type:'),
		department: extractValue(textData, 'Department:'),
		designation: extractValue(textData, 'Designation:'),
		band: extractValue(textData, 'Band:'),
		numberOfPositions: extractValue(textData, 'Number of Positions:'),
		expectedJoiningDate: extractValue(textData, 'Expected Joining Date:'),
		applicationPeriod: extractValue(textData, 'Application Period:'),
		aboutUs: extractSection(textData, 'About Us:', 'Job Overview:'),
		jobOverview: extractSection(textData, 'Job Overview:', 'Key Responsibilities:'),
		keyResponsibilities: extractList(textData, 'Key Responsibilities:', 'Required Qualifications & Skills:'),
		requiredQualifications: extractList(textData, 'Required Qualifications & Skills:', 'Preferred Attributes:'),
		preferredAttributes: extractList(textData, 'Preferred Attributes:', 'Work Hours:'),
		workHours: extractValue(textData, 'Work Hours:'),
		compensationBenefits: extractList(textData, 'Compensation & Benefits:', 'Interview Process:'),
		interviewProcess: extractList(textData, 'Interview Process:', 'How to Apply:'),
		howToApply: extractSection(textData, 'How to Apply:', 'Additional Instructions:'),
		additionalInstructions: extractList(textData, 'Additional Instructions:'),
		tags: ['Frontend', 'HTML/CSS', 'Bootstrap'] // You can generate these dynamically
	};

	return data;
}

function extractValue(text, label) {
	const regex = new RegExp(`${label}\\s*(.*?)(?:\\n|$)`);
	const match = text.match(regex);
	return match ? match[1].trim() : '';
}

function extractSection(text, startLabel, endLabel) {
	const startIndex = text.indexOf(startLabel);
	if (startIndex === -1) return '';

	let endIndex = endLabel ? text.indexOf(endLabel) : text.length;
	if (endIndex === -1) endIndex = text.length;

	return text.substring(startIndex + startLabel.length, endIndex).trim();
}

function extractList(text, startLabel, endLabel) {
	const section = extractSection(text, startLabel, endLabel);
	if (!section) return [];

	// Split by bullet points or new lines
	return section.split('\n')
		.map(line => line.replace(/^[•\-\*]\s*/, '').trim())
		.filter(line => line.length > 0);
}
function base64UrlEncode(str) {
	return btoa(str)
		.replace(/\+/g, '-')
		.replace(/\//g, '_')
		.replace(/=+$/, '');
}
function fetchJobUrl(requisitionId) {
	const encodedId = base64UrlEncode(JSON.stringify({ requisitionId }));

	window.open(`/candidates-job-apply/${encodedId}`, '_blank');
}

