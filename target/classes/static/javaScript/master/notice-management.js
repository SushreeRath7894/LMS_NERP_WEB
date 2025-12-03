function cancelNotice() {
	$(".formValidation").remove();
		if (window.location.href.indexOf("self-service") !== -1) {
					$('#newNotice').hide();
			    }else{
			    	$('#newNotice').show();
			    }
	$("#cancelNotice, #draftNotice, #dateCalendar").hide();
	$("#editNotice, #deleteNotice, #downloadNotice").show();
	$("#empNameAuto, #noticeType, #noticeSubject, #noticeReason,#noticeContent").prop('disabled', true);
	CKEDITOR.instances.noticeContent?.on('instanceReady', function() {
		this.setReadOnly(true);
	});
}
function editNotice() {
	$(".formValidation").remove();
	$("#cancelNotice, #draftNotice, #dateCalendar").show();
	$("#editNotice, #newNotice, #deleteNotice, #downloadNotice").hide();
	$("#empNameAuto, #noticeType, #noticeSubject, #noticeReason,#noticeContent").prop('disabled', false);
	CKEDITOR.instances.noticeContent?.setReadOnly(false);
}
function newNotice() {
	
	if (gridOptions.api) {
			gridOptions.api.deselectAll();
		}
	$('.loader-modal-autosearch').hide();
	$(".formValidation").remove();
	var currDate = getCurrentDate();
	$("#publishDate").val(currDate);
	$("#noticeSubject").val('');
	$("#noticeReason").val('');
	CKEDITOR.instances.noticeContent.setData("");
	$("#publishBy").val($("#sessionId").val());
	$("#publishByName").val($("#sessionName").val());
	$("#noticeId1").html("");
}
function getCurrentDate() {
	let today = new Date();
	let year = today.getFullYear();
	let month = String(today.getMonth() + 1).padStart(2, '0');
	let day = String(today.getDate()).padStart(2, '0');
	return day + '-' + month + '-' + year;
}
function viewData(selectId=null) {
	$(".loader").show();
	agGrid.simpleHttpRequest({
		url: "notice-management-view"
	}).then(function(data) {
		$(".loader").hide();
		if (data.body[0] != null) {
			var allData = JSON.parse(data.body);
			var len = allData.length;
			$('#totalReq').find('span').html(len);
			if(type == 'self-service') {
				allData = allData.filter(f => f.type == 'SUBMIT');
			}
			gridOptions.api.setRowData(allData);
			if (selectId !== null) {  
			    let selectedRow = null;  
			    gridOptions.api.forEachNode((rowNode) => {  
			        if (rowNode.data.noticeId === selectId) {  
			            selectedRow = rowNode;  
			        }  
			    });  
			    if (selectedRow) {  
			        selectedRow.setSelected(true);  
			        gridOptions.api.ensureIndexVisible(selectedRow.rowIndex);  
			    }  
			}else{
				var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}	
			}
		} else {
			$(".loader").hide();
			$('#totalReq').find('span').html("0");
			gridOptions.api.setRowData([]);
		}
		$(".loader").hide();
	});
	$('#editNotice').hide();
	$('#publishNotice').hide();
	$('#sendMail').attr("disabled", true);
}
function editNoticeDetails(noticeId) {
	$(".formValidation").remove();
	$(".loader").show();
	$.ajax({
		type: "GET",
		url: "notice-management-edit?id=" + noticeId,
		success: function(response) {
			if (response.code == "success") {
				$(".loader").hide();
				var allData = JSON.parse(response.body);
				selectedData = allData[0];
				CKEDITOR.instances['noticeContent'].setData(selectedData.noticeContent);
				$("#noticeSubject").val(selectedData.noticeSubject);
				$("#noticeReason").val(selectedData.reason);
				$("#noticeType").val(selectedData.noticeType);
				$("#publishDate").val(selectedData.publishDate);
				$("#publishBy").val(selectedData.publishedBy);
				$("#publishByName").val(selectedData.publishedByName);
				$("#noticeId1").html(selectedData.noticeId);
			}
			$(".loader").hide();
		}
	});
}
function saveNotice(type) {
	var obj = {};
	var allValid = true;
	var noticeContent = CKEDITOR.instances.noticeContent.getData();
	obj.noticeId = $("#noticeId1").html();
	obj.publishTo = $("#publishTo").html();
	obj.publishDate = $("#publishDate").val();
	obj.noticeSubject = $("#noticeSubject").val();
	obj.noticeDescription = noticeContent;
	obj.publishBy = $("#publishBy").val();
	obj.noticeType = type;
		
		if (obj.noticeSubject == "" || obj.noticeSubject == null) {
		toastr.error("Subject Required");
		return;
	}
	
		if (obj.noticeDescription == "" || obj.noticeDescription == null) {
		toastr.error("Content Required");
		return;
	}
	if (allValid) {
		saveData(obj, type,obj.noticeId);
	}
}
function saveData(data, type,nid) {
	$('.loader-modal').show();
	$.ajax({
		type: "POST",
		url: "notice-management-save",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				$('.loader-modal').hide();
				if (type == 'SUBMIT') {
					toastr.success('Notice published successfully');
				} else {	  
					toastr.success(response.message);
				}
				$("#noticeMaster").hide();
				$("#headerDiv").show();
				$('.loader-modal').hide();
				$('#addPageEmpName').hide();
				if(nid==""){
					viewData();
				}else{
					viewData(nid);
				}
			}
		},
		error: function(data) {
			console.log(data)
		}
	})
}
function downloadNotice() {
	var noticeId = $("#noticeId1").html();
	var organization = $("#sessionOrganization").val();
	var orgDivision = $("#sessionOrgDivision").val();
	var logo = $("#sessionLogo").val();
	var userId = $("#sessionId").val();
	window.open("/master/notice-management-pdf-download?noticeId=" + window.btoa(noticeId) + "&organization=" + window.btoa(organization) + "&orgDivision=" +
		window.btoa(orgDivision) + "&userId=" + window.btoa(userId) + "&logo=" + window.btoa(logo), '_blank');
}
function deleteNotice() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	var noticeId = selectedData[0].noticeId;
	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to delete this?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
			$.ajax({
				type: "GET",
				url: "notice-management-delete-notice?id=" + noticeId,
				success: function(response) {
					if (response.code == "success") {
						$(".loader").hide();
						viewData();
					} else {
						$(".loader").hide();
						viewData();
					}

				}
			});
		}
	});
}
function cancelBar() {
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}