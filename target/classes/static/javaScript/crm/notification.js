function getCrmNotifications() {

	let userId = sessionStorage.getItem("userId")
	let notify = "notification"


	$.ajax({
		type: "GET",
		url: "/pipeline/admin-task-assign-user-tasks?userId=" + userId + "&type=" + notify,
		success: function(response) {
			console.log("ASHIHS AKM -----$$$$$$>>>>.", response);
			if (response.code == "Success") {
				let data = JSON.parse(response.body);
				console.log("PKJ -----$$$$$$>>>>.", data);
				$("#mySidenav-crm").find('div').remove();

				let div = "";

				data.forEach((task) => {
					let priorityIcon = '';
					let priorityDescription = '';
					switch (task.priority) {
						case 'High':
							priorityIcon = '<i class="fas fa-exclamation-circle icon-colr"></i>';
							priorityDescription = `This task, assigned by <span style="font-size: 13px;color: #6a2bbf;">${task.executiveName}</span>, requires immediate attention.`;
							break;
						case 'Medium':
							priorityIcon = '<i class="fas fa-info-circle icon-colr"></i>';
							priorityDescription = `This task, assigned by ${task.executiveName}, is of medium priority.`;
							break;
						case 'Low':
							priorityIcon = '<i class="fas fa-check-circle icon-colr"></i>';
							priorityDescription = `This task, assigned by ${task.executiveName}, is of low priority.`;
							break;
						default:
							priorityIcon = '<i class="fas fa-question-circle icon-colr"></i>';
							priorityDescription = 'Priority level not specified.';
					}
					
					let notificationCard =
						`<div class="position-box">
				            <h6 onclick="getSelectedTaskId('${task.taskId}'); getUrl('MOD029', '${sessionStorage.getItem("assignedTaskFun")}', '${sessionStorage.getItem("assignedTaskAct")}')">${task.title}</h6>
				            <ul>
				                <li class="head-li"><i class="far fa-user icon-colr"></i>&nbsp;&nbsp;${task.executiveName}</li>
				                <li class="head-li">${priorityIcon}&nbsp;&nbsp;${task.priority}</li>
				                <li class="head-li"><i class="far fa-calendar icon-colr"></i>&nbsp;&nbsp;${task.createdDate}</li>
				                <li><i class="notification-type"</i> ${priorityDescription}</li>
				            </ul>
				        </div>`;
					$("#mySidenav-crm").append(notificationCard);
				});



				//$("#mySidenav-crm").append(div)


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


/**
 * Admin Notification
 */
function getAdminNotification() {
	$.ajax({
		type: "GET",
		url: "/pipeline/admin-task-assign-notificaton",
		success: function(response) {
			console.log("Response:", response);
			if (response.code === "Success") {
				let data = JSON.parse(response.body);
				$("#mySidenav-crm").find('div').remove();

				if (data != null && data.length > 0) {
					data.forEach((task) => {
						let notificationCard =
							`<div class="position-box">
                                    ${task.header}
                                    <ul>
                                        <li class="head-li"><i class="far fa-paper-plane icon-colr"></i>&nbsp;&nbsp;${task.executiveName}</li>
                                        <li class="head-li"><i class="far fa-calendar icon-colr"></i>&nbsp;&nbsp;${task.createdOn} &nbsp;&nbsp;<i class="far fa-clock icon-colr"></i> ${task.createdTime}</li>
                                        <li><i class="notification-type"</i> ${task.notificationType}</li>
                                    </ul>
                          </div>`;
						$("#mySidenav-crm").append(notificationCard);
					});

					$("#badge-notification-count").text(data.length).show();
					$("#badge-icount").show();
				} else {
					$("#mySidenav-crm").append(`<div class="no-notify-msg-div">
                        <i class="ti-comment"></i>
                        <p class="no-notify-msg">You Have No New Notifications</p>
                    </div>`);
				}
			} else {
				$("#mySidenav-crm").empty().append(`<div class="no-notify-msg-div">
                    <i class="ti-comment"></i>
                    <p class="no-notify-msg">You Have No New Notifications</p>
                </div>`);
			}

			$('.loader').hide();
		}
	});
}


$(document).ready(function() {
	if (sessionStorage.getItem("module") == "MOD029" || sessionStorage.getItem("module") == "MOD008"){  
		$(".notify-bell").removeClass("notify-bell-hide");
		$(".notify-bell").addClass("notify-bell-show");
		getCrmNotifications();
		if ($("#userRole").val()) {
			getAdminNotification();
		}
	}

	let mySidenavCrm = document.getElementById("mySidenav-crm");

	$("#notification").on('click', function() {
		mySidenavCrm.style.width = "300px";
	});

	$("#close-notification").on('click', function() {
		mySidenavCrm.style.width = "0";
	});

});

/**
 * redirect to Contact and Lead details view
 */
function viewLeadDetailsNoti(id, notificatioId) {
	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-leads-detail?id=" + id;
}


function viewContactDetailsNotification(id, dealId, notificatioId, type) {
	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
	window.location.href = rest + "view-crm-contacts-detail?id=" + id;
	localStorage.setItem('noitificationDeal', dealId);
	localStorage.setItem('redirectDeal', type);
}

function viewSalesDetailsNotification(salesId,notificationId,type) {
  if (type == 'Quotation') {
		sessionStorage.setItem("quotationId", salesId);
		
		sessionStorage.setItem('module', sessionStorage.getItem("salesModule"));
		sessionStorage.setItem('function',sessionStorage.getItem("salesOrderFun"));
		sessionStorage.setItem('activity', sessionStorage.getItem("quoteAct"));
		window.location.href = "/sales/view-quotation";

	} else if (type == 'Sales Order') {

		sessionStorage.setItem("salesOrderId", salesId);
		
		sessionStorage.setItem('module', sessionStorage.getItem("salesModule"));
		sessionStorage.setItem('function',sessionStorage.getItem("salesOrderFun"));
		sessionStorage.setItem('activity', sessionStorage.getItem("salesAct"));
		window.location.href = "/sales/view-saleorder";

	} else if (type == 'Purchase Order') {

		sessionStorage.setItem("purchaseOrderId", salesId);
		
		sessionStorage.setItem('module', sessionStorage.getItem("salesModule"));
		sessionStorage.setItem('function',sessionStorage.getItem("salesOrderFun"));
		sessionStorage.setItem('activity', sessionStorage.getItem("purchaseAct"));
		window.location.href = "/sales/view-po-or-wo";

	} else if (type == 'Sales Invoice') {

		sessionStorage.setItem("salesInvoiceId", salesId);
		
		sessionStorage.setItem('module', sessionStorage.getItem("salesModule"));
		sessionStorage.setItem('function',sessionStorage.getItem("salesOrderFun"));
		sessionStorage.setItem('activity', sessionStorage.getItem("invoiceAct"));
		window.location.href = "/sales/view-saleInvoice";
	} else {

		$("#messageParagraph").text("Something Went Wrong!");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');

	}
   
}
