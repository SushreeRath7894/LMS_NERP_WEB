function getActivityTimeline(response) {
	console.log("RESPONSE--------------------->", response);
	$("#main-timeline-section").empty();
	if (!response || response.length === 0) {
		$("#main-timeline-section").append(
			'<div class="text-center text-muted">No Timeline History Found</div>'
		);
		return;  
	}
	let activityArrayObject = [];
	response.forEach((body) => {

		if (!activityArrayObject[body.createdOn]) {
			activityArrayObject[body.createdOn] = [];
		}
		activityArrayObject[body.createdOn].push(body);
	});

	const result = Object.keys(activityArrayObject).map((date) => ({
		date,
		activities: activityArrayObject[date],
	}));

	console.log("Activity Timeline ------------------->>>>>>>", result)

	result.forEach((data, count) => {
		let mailRow = "";
		let dateHeader = '<div class="timeLineHistDate pB20" id="todayDateUnderHistory_' + count + '">' + data.date + '</div>'

		data.activities.forEach((activity) => {

			if (activity.activityName == "Lead Created") {
				mailRow += '<li class="timeline_Sent Emails">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="green-bg" style="background-color: #a49f9f!important;"><img src="../css/ncrm/timeline-images/target.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<div><span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">'
					+ '' + activity.createdBy + '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + '</span>'
					+ '</div></span></li>';
			}
			if (activity.activityName == "Lead Convert To Contacted") {
				mailRow += '<li class="timeline_ConvertedToContacted">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="blue-bg" style="background-color: #4a90e2!important;"><img src="../css/ncrm/timeline-images/contact.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<div><span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">'
					+ '' + activity.createdBy + '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + '</span>'
					+ '</div></span></li>';
			}


			if (activity.activityName == "Note added") {
				mailRow += '<li class="timeline_added Notes">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><i class="fa fa-file-text-o timeline-icons" aria-hidden="true"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span><span class="pt10">'
					+ '<div><b>' + activity.activityDesc + '</b></div>'
					+ '</span><div><span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">'
					+ '' + activity.createdBy + '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + '</span>'
					+ '</div></span></li>';

			}

			if (activity.activityName == "Email Sent") {
				mailRow += '<li class="timeline_Sent Emails">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="green-bg"><i class="fa fa-envelope-o timeline-icons" aria-hidden="true"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" ><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '<div><span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">'
					+ '' + activity.createdBy + '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + '</span>'
					+ '</div></span></li>';
			}

			if (activity.activityName == "Draft Saved") {
				mailRow += '<li class="timeline_Sent Emails">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg" style="background-color: #656565!important;"><img src="../css/ncrm/timeline-images/mail-draft.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" ><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '<div><span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">'
					+ '' + activity.createdBy + '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + '</span>'
					+ '</div></span></li>';
			}

			if (activity.activityName == "Task Added") {
				mailRow += '<li class="timeline_added Tasks">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/task.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span><span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Task">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Task deleted") {
				mailRow += '<li class="timeline_added Tasks">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg" style="background-color: #ff00008a!important;"><i class="fa fa-trash-o timeline-icons" aria-hidden="true"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span><span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Task">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Task Closed") {
				mailRow += '<li class="timeline_added Tasks">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img class="timeline-closed-img" src="../css/ncrm/timeline-images/task-closed.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span><span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Task">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Task Updated") {
				mailRow += '<li class="timeline_added Tasks">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><i class="fa fa-pencil timeline-icons" aria-hidden="true" style="margin-top: 4px!important;font-size:14px!important;"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span><span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Task">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Product added") {
				mailRow += '<li class="timeline_added Tasks">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg" ><i class="fa fa-cart-plus timeline-icons" aria-hidden="true" style="margin-top:5px!important;font-size:14px!important;"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span><span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Task">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Product Deleted") {
				mailRow += '<li class="timeline_added Tasks">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg" style="background-color: #ff00008a!important;"><i class="fa fa-trash-o timeline-icons" aria-hidden="true"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span><span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Task">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank" >' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Deal Created") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img class="timeline-closed-img" src="../css/ncrm/timeline-images/deal.png"></div>'
					+ '</span> <span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '</div><div>'
					+ '</div></div><div>'
					+ '<span class="tl_by">by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Deal updated") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><i class="fa fa-pencil timeline-icons" aria-hidden="true" style="margin-top: 4px!important;font-size:14px!important;"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '<span>Stages from </span> <span>'
					+ '</span><span class="tl_from"><b>' + activity.previousStatus + '</b> to <b>' + activity.currentStatus + '</b>'
					+ '</span></div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Deal Closed") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img class="timeline-closed-img" src="../css/ncrm/timeline-images/deal-closed.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '<span>Stages from </span> <span>'
					+ '</span><span class="tl_from"><b>' + activity.previousStatus + '</b> to <b>' + activity.currentStatus + '</b>'
					+ '</span></div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			let formattedFrom = formatDateTime(activity.activityFromDate, activity.activityFromTime);
			let formattedTo = formatDateTime(activity.activityToDate, activity.activityToTime);

			if (activity.activityName == "Meeting added") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/meeting-schedule.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '<span>To from </span> <span>'
					+ '</span><span class="tl_from"><b>' + formattedFrom + '</b> to <b>' + formattedTo + '</b>'
					+ '</span></div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Meeting Updated") {
				let status = '';
				if (activity.previousStatus != activity.currentStatus) {
					status = '<span class="tl_from">Status from <b>' + activity.previousStatus + '</b> to <b>' + activity.currentStatus + '</b></span>';
				}
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><i class="fa fa-pencil timeline-icons" aria-hidden="true" style="margin-top: 4px!important;font-size:14px!important;"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '<span>To from </span> <span>'
					+ '</span><span class="tl_from"><b>' + formattedFrom + '</b> to <b>' + formattedTo + '</b>'
					+ '</span></div>'
					+ status
					+ '<div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Call added") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/call.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '<span>To from </span> <span>'
					+ '</span><span class="tl_from"><b>' + formattedFrom + '</b> to <b>' + formattedTo + '</b>'
					+ '</span></div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';

			}

			if (activity.activityName == "Call updated") {
				let status = '';
				if (activity.previousStatus != activity.currentStatus) {
					status = '<span class="tl_from">Status from <b>' + activity.previousStatus + '</b> to <b>' + activity.currentStatus + '</b></span>';
				}
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><i class="fa fa-pencil timeline-icons" aria-hidden="true" style="margin-top: 4px!important;font-size:14px!important;"></i></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.activityDesc + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '<span>To from </span> <span>'
					+ '</span><span class="tl_from"><b>' + formattedFrom + '</b> to <b>' + formattedTo + '</b>'
					+ '</span></div>'
					+ status
					+ '<div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}

			if (activity.activityName == "Quotation added") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/sales.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.salesTypes + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '</div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';

			}

			if (activity.activityName == "Sales order added") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/sales.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.salesTypes + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '</div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';

			}

			if (activity.activityName == "Purchase order") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/sales.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.salesTypes + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '</div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';

			}

			if (activity.activityName == "Invoice added") {
				mailRow += '<li class="timeline_updated Events">'
					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../css/ncrm/timeline-images/sales.png"></div></span>'
					+ '<span class="crm-font-regular f15 fL timeLineHistCont pR">'
					+ '<span>' + activity.activityName + '</span>'
					+ '<span class="tl_s" style="color: silver;"> - </span>'
					+ '<span class="pt10" title="Deal">'
					+ '<link-to lt-prop-route="crm.tab.module.entity.detail"><span style="font-weight: bold;" target="_blank">' + activity.salesTypes + '</span></link-to>'
					+ '</span>'
					+ '<div class="pulsefldupd"><div>'
					+ '</div><div></div></div>'
					+ '<div>'
					+ '<span class="tl_by"> by </span>'
					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'
					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';

			}

			if (activity.activityName == "Campaign Added") {
				mailRow += '<li class="timeline_RelListAssociation_added Campaigns">'

				mailRow += '<li class="timeline_updated Events">'

					+ '<span class="timeLineHistTime crm-font-regular f13 fL">' + activity.createdTime + ''
					+ '<div class="grey-bg"><img src="../assets/images/05.png"/></div>'
					+ '</span> <span class="crm-font-regular f15 fL timeLineHistCont pR">'

					+ '<span>' + activity.activityName + '</span>'

					+ '<span class="tl_s">- </span>'

					+ '<span class="pt10" title="Campaign">'

					+ '<link-to lt-prop-route="crm.tab.module.entity.detail" lt-prop-dp="[&quot;Events&quot;,&quot;5249707000000440289&quot;]"  data-params="{&quot;module&quot;:&quot;Events&quot;,&quot;id&quot;:&quot;5249707000000440289&quot;}" lt-prop-target="_blank" lyte-rendered=""><span style="font-weight: bold;" target="_blank">' + activity.mailSubject + '</span></link-to>'
					+ '</span>'

					+ '<div class="pulsefldupd"><div>'

					+ '<span>From </span> <span>'
					+ '</span><span class="tl_from"><b>' + activity.meetingFromDate + '</b>'
					+ '</span></div><div>'

					+ '<span>To </span> <span>'
					+ '</span><span class="tl_to"><b>' + activity.meetingToDate + '</b></span></div></div>'

					+ '<div>'

					+ '<span class="tl_by">By </span>'

					+ '<span class="f13" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">' + activity.createdBy + ''
					+ '</span>'

					+ '<span class="tl_dat" > At ' + activity.createdOn + ''
					+ '</span></div></span></li>';
			}


		});

		let activityUl = '<ul class="timeLineHistDataList" id="addActivityCls_' + count + '">' + mailRow + '</ul>'

		$("#main-timeline-section").append(dateHeader + activityUl);
	});
}

function formatDateTime(date, time) {
	// Split the date and time
	let [hours, minutes] = time.split(':');
	let ampm = 'AM';

	// Convert from 24-hour to 12-hour format
	hours = parseInt(hours);
	if (hours >= 12) {
		ampm = 'PM';
		if (hours > 12) hours -= 12;
	} else if (hours === 0) {
		hours = 12;
	}

	// Format the time with AM/PM
	let formattedTime = hours + ':' + minutes + ' ' + ampm;

	// Return the formatted date and time
	return date + ' ' + formattedTime;
}