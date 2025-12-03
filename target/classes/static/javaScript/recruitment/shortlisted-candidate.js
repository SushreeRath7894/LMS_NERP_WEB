 $(document).ready(function() {
	var gridDivShortlisted = document.querySelector('#myGridShortlisted');
    new agGrid.Grid(gridDivShortlisted, gridOptionsShortlisted);
        
    gridOptionsShortlisted.api.setRowData([])
    getShortListedCandidate();
    
    $("#interviewerSelect").val('').trigger('chosen:updated');
	
	$(".chosen-select").chosen();
	 $('#interviewerSelect').chosen();
	 
	 var dateFormat = localStorage.getItem("dateFormat");
        $("#fromDateCalendar").datetimepicker({
            format: dateFormat,
            closeOnDateSelect: true,
            timepicker: false,
        }).on("change", function() {
            $('#fromDate').val($(this).val());
        })

        $('#fromDate').blur(function() {
            $("#fromDateCalendar").val($(this).val());
        })

        $("#toDateCalendar").datetimepicker({
            format: dateFormat,
            closeOnDateSelect: true,
            //maxDate : new Date(),
            timepicker: false,
        }).on("change", function() {
            $('#toDate').val($(this).val());
        })

        $('#toDate').blur(function() {
            $("#toDateCalendar").val($(this).val());
        })
        
 
		$("#toTimeCalander").datetimepicker({
		 format: 'h:i A',
		    closeOnDateSelect: false,
		    timepicker: true,
		    datepicker: false,
		    step: 1,
		    formatTime: 'h:i A'
	}).on("change", function() {
		$('#toTime').val($(this).val());
	})
        $("#fromTimeCalander").datetimepicker({
		 format: 'h:i A',
		    closeOnDateSelect: false,
		    timepicker: true,
		    datepicker: false,
		    step: 1,
		    formatTime: 'h:i A'
	}).on("change", function() {
		$('#fromTime').val($(this).val());
	})
	
         //joining date
     $("#joiningdateCalendar").datetimepicker({
        format: dateFormat,
        closeOnDateSelect: true,
        timepicker: false,
    }).on("change", function() {
        $('#joiningdate').val($(this).val());
    })

    $('#joiningdate').blur(function() {
        $("#joiningdateCalendar").val($(this).val());
    })
	
	$('.hr-nav-tabs li').click(function() {
	 $(this).prevAll().addClass("completed");
	  $(this).nextAll().removeClass("completed")
	});

 });
 
 $(document).ready(function () {
      $(".max-btn").on("click", function () {
        var parentCol = $(this).closest(".col-md-6, .col-md-12");

        if (parentCol.hasClass("col-md-6")) {
            parentCol.removeClass("col-md-6 pd-r pd-l").addClass("col-md-12");
            parentCol.siblings(".col-md-6").hide();
        } else {
            parentCol.removeClass("col-md-12").addClass("col-md-6 pd-r pd-l");
            parentCol.siblings(".col-md-6").show();
        }
    });
    
    
});

var columnDefsShortlisted = [{
	headerCheckboxSelection : false,
	headerCheckboxSelectionFilteredOnly : true,
	checkboxSelection : true,
	width : 10,
	sortable : false,
	filter : false,
	resizable : true,
	pinned : 'left',
}, {
	headerName : "Candidate Id",
	field : "candidateId",
	cellRenderer : function(params) {
		return '<a class="edit-css" >' + params.data.candidateId + '</a>';
	}
},{
	headerName : "Requisition ID",
	field : "requisitionId",
	pinned : 'left',
	cellRenderer : function(params) {
			return '<a class="edit-css" >' + params.data.requisitionId + '</a>';
		}
	/*cellRenderer : function(params) {
		  let encodedLogData = encodeURIComponent(JSON.stringify(params.data));
	        if (params.data.requisitionId) {
	        	return '<a class="edit-css" onclick=openRequisitionOverview(decodeURIComponent(\'' + encodedLogData + '\')) href="javascript:void(0)" title="Requisition Overview">'
	             + '<i class="fa fa-info-circle"> '+ params.data.requisitionId + ' </i>'
	             + '</a>';
	             
	        } else {
	            return params.data.requisitionId;
			}
	}*/
},{
	headerName : "Candidate Name",
	field : "candidateName"
}, {
	headerName : "Gender",
	field : "gender",
},{
	headerName : "Source",
	field : "source",
}, {
	headerName : "Sort listed By",
	field : "shortlistedBy"
},  {
	headerName : "Experience",
	field : "experience",
	width : 120,

},{
	headerName : "Email",
	field : "email",

}, {
	headerName : "Address",
	field : "address",
	
}, {
	headerName : "Status",
	field : "status"
} ];

var gridOptionsShortlisted = {
	columnDefs : columnDefsShortlisted,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 190,
		height : 10
	},
	rowSelection : 'single',
	pagination:true,
	paginationPageSize: 15,
	onSelectionChanged : rowSelectId
    //onSelectionChanged: changeShortListed

};

function rowSelectId() {
	 var selectedNodes = gridOptionsShortlisted.api.getSelectedNodes();
	 var selectedData = selectedNodes.map(node => node.data);
	 const reqId= selectedData.map(node => node.requisitionId);
	 const candidateId= selectedData.map(node => node.candidateId);
	 var selectedRows = gridOptionsShortlisted.api.getSelectedRows();
	 var rowCount = 0;
	 for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId+ '"';
	     rowCount = rowCount + 1;
	 }
	 if (rowCount > 0) {
		getRequisitionRounds(reqId,candidateId);
		viewJobDetails(reqId);
		$("#candidateId").val(candidateId);
	 } else {
		$("#candidateId").val("");
    }
};

function getShortListedCandidate(){
   agGrid.simpleHttpRequest({
		url : "review-hiring-candidate-shortlistd",
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		
		var allData = jsonData.candidateData;
		
			if (allData != null) {
				gridOptionsShortlisted.api.setRowData(allData);
				if (allData && allData.length > 0) {
					gridOptionsShortlisted.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
					
				}
			} else {
				gridOptionsShortlisted.api.setRowData();
			}
	})
 }
 
/* function openRequisitionOverview(data){
	let dataRetrived = JSON.parse(decodeURIComponent(data));
	
	let allData = dataRetrived.requisitionDetails[0];
  
  
    $("#requiIdModal").text(dataRetrived.requisitionId);
    $("#jobTitleModal").text(allData.title);
    $("#releaseDateModal").text(allData.jobType);
    $("#locationModal").text(allData.location);
    $("#eduModal").text(allData.educationRequired);
    $("#minSalModal").text(allData.minSalary);
    $("#maxSalModal").text(allData.maxSalary);
    $("#deptModal").text(allData.department);
    $("#noOfPositionsModal").text(allData.noOfPosition);
    $("#workHourModal").text(allData.workHour);
    $("#desgnModal").text(allData.designation);
    $("#joinDateModal").text(allData.joinDate);
    $("#summaryModal").text(allData.summary);
    $("#responsibilitesModal").text(allData.responsibilties);
    $("#skillModal").text(allData.skill);
}*/

 function checkValidation() {
    const fromTimeStr = $('#fromTime').val();
    const toTimeStr = $('#toTime').val();

    if (fromTimeStr && toTimeStr) {
        // Parse times
        const fromTime = new Date(`1970-01-01T${convertTo24Hour(fromTimeStr)}:00`);
        const toTime = new Date(`1970-01-01T${convertTo24Hour(toTimeStr)}:00`);

        if (fromTime >= toTime) {
        	$("#messageParagraph").text("From Time must be earlier than To Time.");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
            $('#fromTime').val(''); // Clear "From Time" if invalid
            $('#toTime').val('');   // Clear "To Time" if invalid
            $('#totalDuration').val('');
        } else {
            const durationInMinutes = (toTime - fromTime) / (1000 * 60);
            const hours = Math.floor(durationInMinutes / 60);
            const minutes = durationInMinutes % 60;
            $('#totalDuration').val(`${hours} hr ${minutes} min`);
        }
    }
}

function convertTo24Hour(timeStr) {
    const [time, modifier] = timeStr.split(' ');
    let [hours, minutes] = time.split(':');
    if (modifier === 'PM' && hours !== '12') {
        hours = parseInt(hours, 10) + 12;
    } else if (modifier === 'AM' && hours === '12') {
        hours = '00';
    }
    return `${hours}:${minutes}`;
}

function getRequisitionRounds(requisitionId,candidateId) {
    agGrid.simpleHttpRequest({
        url: "review-hiring-get-rounds-list?requisitionId=" + requisitionId + "&candId="+candidateId,
    }).then(function (response) {
        if (response.code === "Success") {
            const roundList = JSON.parse(response.body[0]);
            const wizardContainer = document.querySelector(".wizard");

            wizardContainer.innerHTML = "";

            roundList.forEach((round, index) => {
                const li = document.createElement("li");

                // Add the active class only to the first round
                li.className = index === 0 ? "active" : "disabled";
                if (index === roundList.length - 1) {
                    li.classList.add("special-last-round");
                }

                const a = document.createElement("a");
                a.href = `#${round.roundId}`;
                a.setAttribute("data-toggle", "tab");
                a.setAttribute("aria-expanded", index === 0 ? "true" : "false");

                if (index === 0) {
                    // Add click listener only for active round
                    a.addEventListener("click", (event) => {
                        event.preventDefault(); // Prevent default navigation
                        handleRoundClick(round, requisitionId);
                    });
                } else {
                    // Disable click functionality for other rounds
                    a.classList.add("disabled-link");
                    a.style.pointerEvents = "none";
                    a.style.cursor = "not-allowed";
                }

                // Add the title to the anchor element
                a.textContent = round.title;

                li.appendChild(a);
                wizardContainer.appendChild(li);

                // Trigger the click handler for the first round
                if (index === 0) {
                    handleRoundClick(round, requisitionId);
                }
            });
        } else {
            console.log("Failed to fetch data");
        }
    });
}


function handleRoundClick(round, requisitionId) {
    var selectedRows = gridOptionsShortlisted.api.getSelectedRows();
	var candidateEmail = selectedRows[0].email;

    $("#title").val(round.title).attr("disabled", true);
    $("#description").val(round.description).attr("disabled", true);
    $("#candidateEmail").val(candidateEmail).attr("disabled", true);
    $("#roundId").val(round.roundId);
    getInterviewRoundsDetails(round.roundId,requisitionId);
   
    const $interviewerSelect = $("#interviewerSelect");
    $interviewerSelect.empty();
    let interviewers = [];
    try {
        interviewers = JSON.parse(round.interviewers);
    } catch (error) {
        console.error("Failed to parse interviewers data:", error);
    }
    interviewers.forEach(interviewer => {
        const option = `<option value="${interviewer.id}" id="${interviewer.id}" 
            ${interviewer.email ? `email="${interviewer.email}"` : ''}>${interviewer.name}</option>`;
        $interviewerSelect.append(option);
    });
    $interviewerSelect.trigger("chosen:updated");
    $interviewerSelect.on('change', function () {
        let selectedIds = [];
        let selectedEmails = [];

        // Get all selected options
        $(this).find('option:selected').each(function () {
            const selectedId = $(this).val();
            const selectedEmail = $(this).attr('email'); 
            
            selectedIds.push(selectedId);
            selectedEmails.push(selectedEmail);
        });
        $("#interviewerHiddenId").val(selectedIds.join(","));
        $("#interviewerEmail").val(selectedEmails.join(",")).attr("disabled", true);;
    });
}



function interviewSchedule() {
	var obj={};
	var allValid=true;
	/*var validationMailCandidate=true;
	var validationMailInterviewer=true;*/
    
	var selectedRows = gridOptionsShortlisted.api.getSelectedRows();
	var candId = selectedRows[0].candidateId;
	var candidateName = selectedRows[0].candidateName;
	var requisitionId = selectedRows[0].requisitionId;
    
    obj.fromDate = $('#fromDate').val();
    obj.toDate = $('#toDate').val();
    obj.fromTime = $('#fromTime').val();
    obj.toTime = $('#toTime').val();
    obj.location = $('#location').val();
    obj.summary = $('#summary').val();
    obj.totalDuration = $('#totalDuration').val();
    obj.description = $('#description').val();
    obj.title = $("#title").val();
    obj.interviewer = $("#interviewerHiddenId").val();
    obj.candidateId = candId;
    obj.requisitionId = requisitionId;
    obj.email = $("#candidateEmail").val();
    obj.interviewerEmail = $("#interviewerEmail").val();
    obj.meetingURL = $("#interviewURL").val();
    obj.candidateName = candidateName;
    obj.locationName = $('#location').find(":selected").text();
    obj.roundId = $("#roundId").val();
	obj.modeOfInt = "Online";

    if(obj.fromDate == null || obj.fromDate == ""){
		allValid = validationUpdated("Interview Date Required","fromDate");
	}
	if(obj.fromTime == null || obj.fromTime == ""){
		allValid = validationUpdated("From Time Required","fromTime");
	}
	if(obj.toTime == null || obj.toTime == ""){
		allValid = validationUpdated("To Time Required","toTime");
	}
	if($('#location').val() == null || $('#location').val() == ""){
		allValid = validationUpdated("Location Required","location");
	}
	if(obj.summary == null || obj.summary == ""){
		allValid = validationUpdated("Summary Required","summary");
	}
	if(obj.description == null || obj.description == ""){
		allValid = validationUpdated("Description Required","description");
	}
	if(obj.title == null || obj.title == ""){
		allValid = validationUpdated("Title Required","title");
	}
	if(obj.interviewer == null || obj.interviewer == ""){
		allValid = validationUpdated("Please Select At Least One Interviewer","interviewerHiddenId");
	}

	/*if (!validationUpdated("Candidate Email Can't Be Blank", 'candidateEmail'))
		allValid = false;
	
	if (!validationUpdated("Interviewer Email Can't Be Blank", 'interviewerEmail'))
		allValid = false;

	  
	 if(obj.email != null || obj.email != ""){ 
		   validationMailCandidate = validateEmailsCandidate(obj.email);
	       console.log('validationMailCandidate>',validationMailCandidate)
	   }
	   
	  
	 if(obj.interviewerEmail != null || obj.interviewerEmail != ""){ 
		   validationMailInterviewer = validateEmailsInterviewer(obj.interviewerEmail);
	       console.log('validationMailInterviewer>',validationMailInterviewer)
	   }*/
	   
if(allValid){
	$(".loader").show();
    $.ajax({
        type: "POST",
        url: "review-hiring-schedule-interview",
        contentType: "application/json",
        data: JSON.stringify(obj),
        success: function(response) {
            if (response.code == "success") {
				const roundId = $("#roundId").val();
				
				getInterviewRoundsDetails(roundId,requisitionId);
            	$(".loader").hide();
            	$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');

            } else{
            	$(".loader").hide();
            	$("#messageParagraph").text(response.message);
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$('#scheduleBttn').attr("disabled", true);
            }
        },
        error: function(data) {
        }
    }) 
}
}

function getInterviewRoundsDetails(roundId,requisitionId){
	const candidateId = $("#candidateId").val();
	 agGrid.simpleHttpRequest({
        url: "review-hiring-get-rounds-deails?roundId=" + roundId + "&requisitionId="+requisitionId + "&candidateId="+candidateId,
    }).then(function (response) {
        if (response.code === "Success") {
            const roundDetailsArray = JSON.parse(response.body[0]); 
           if (Array.isArray(roundDetailsArray) && roundDetailsArray.length > 0 && roundDetailsArray[0] !== null) {
			const roundDetails = roundDetailsArray[0];
            if(roundDetails.interview_status == "scheduled"){
				$("#scheduledMsg").css("display","block").addClass('alert-warning').removeClass('alert-success');
	            $("#interview-date").text(roundDetails.formatted_date);
	            $("#interview-time").text(roundDetails.fromTime);
	            $("#interview-time").text(roundDetails.fromTime);
	            $("#interviewer-name").text(roundDetails.createdBy);
				$(".interview-status").text(roundDetails.interview_status);
				$("#interviewSchedule").attr('disabled',true);
	            $("#interview-status").addClass('bg-warning').removeClass('bg-green');
			} else if(roundDetails.interview_status == "completed"){
				$("#scheduledMsg").css("display","block").removeClass('alert-warning').addClass('alert-success');
	            $("#interview-date").text(roundDetails.formatted_date);
	            $("#interview-time").text(roundDetails.fromTime);
	            $("#interview-time").text(roundDetails.fromTime);
	            $("#interviewer-name").text(roundDetails.createdBy);
				$("#interviewSchedule").attr('disabled',true);
				$(".interview-status").text(roundDetails.interview_status);
	            $("#interview-status").removeClass('bg-warning').addClass('bg-green');
			}else if(roundDetails.interview_status == "postpone"){
				$("#interviewSchedule").attr('disabled',true);
				$("#scheduledMsg").css("display","block").addClass('alert-warning').removeClass('alert-success');
	            $("#interview-date").text(roundDetails.formatted_date);
	            $("#interview-time").text(roundDetails.fromTime);
	            $("#interview-time").text(roundDetails.fromTime);
	            $("#interviewer-name").text(roundDetails.createdBy);
				$(".interview-status").text(roundDetails.interview_status);
	            $("#interview-status").addClass('bg-warning').removeClass('bg-green');
			} else{
				$("#scheduledMsg").css("display","none");
			}
            
            $("#location").val(roundDetails.location).attr("disabled",true);
            $("#fromDate").val(roundDetails.fromDate).attr("disabled",true);
            $("#fromTime").val(roundDetails.fromTime).attr("disabled",true);
            $("#toTime").val(roundDetails.toTime).attr("disabled",true);
            $("#totalDuration").val(roundDetails.duration).attr("disabled",true);
            $("#summary").val(roundDetails.summary).attr("disabled",true);
             $("#interviewerEmail").val(roundDetails.interviewers_email).attr("disabled",true);
            const selectedInterviewerIds = roundDetails.interviewer_Id.split(","); 

            $("#interviewerSelect")
                .val(selectedInterviewerIds)
                .trigger("chosen:updated"); 
            $("#interviewerSelect").prop("disabled", true);
            $("#interviewerSelect").trigger("chosen:updated");
            
			} else{
				
			$("#scheduledMsg").css("display","none");
            $("#interview-date").text("");
            $("#interview-time").text("");
            $("#interview-time").text("");
            $("#interviewer-name").text("");
            $(".interview-status").text("");
				
			$("#location").val("").attr("disabled",false);
            $("#fromDate").val("").attr("disabled",false);
            $("#fromTime").val("").attr("disabled",false);
            $("#toTime").val("").attr("disabled",false);
            $("#totalDuration").val("").attr("disabled",false);
            $("#summary").val("").attr("disabled",false);
             $("#interviewerEmail").val("").attr("disabled",false);

            $("#interviewerSelect")
                .val("")
                .trigger("chosen:updated"); 
            $("#interviewerSelect").prop("disabled", false);
            $("#interviewerSelect").trigger("chosen:updated");
			}
            
            
        }
    });
}

function viewJobDetails(reqid) {
    $.ajax({
        type: "GET",
        url: "view-new-requi-mstr-edit?id=" + reqid,
        async: false,
        success: function (response) {
            if (response.message === "Success" && response.body.length > 0) {
                const job = response.body[0]; 
                
                // Update job details
                $("#jobId").val(reqid);
                $("#job-title").text(job.jobTitle || "N/A");
                $("#jd-positionSummary").text(job.positionSummary || "N/A");
                $("#jd-positionResponsibility").text(job.positionResponsibility || "N/A");
                $("#jd-designation").text(job.designation || "N/A");
                $("#jd-band").text(job.band || "N/A");
                $("#jd-hiring-manager").text(job.hiringManager || "N/A");
                $("#jd-join-date").text(job.joinDate || "N/A");
                $("#jd-apply-date").text(job.applyStartDate || "N/A");
                $("#jd-end-date").text(job.applyEndDate || "N/A");
                $("#jd-positions").text(job.noPosition || "N/A");
                $("#jd-experience").text(`${job.minExp || "N/A"} - ${job.maxExp || "N/A"} years`);
                $("#jd-salary").text(`₹${job.minSalary || "N/A"} - ₹${job.maxSalary || "N/A"}`);
                $("#jd-status").text(job.activityStatus === "1" ? "Active" : "Inactive");
                $("#jd-apprv-status").text(job.approveStatus || "Pending");
                // Add skills dynamically
				$.ajax({
				    type: "GET",
				    url: "view-new-requi-mstr-edit-skill?reqId=" + reqid,
				    async: false,
				    success: function (skillResponse) {
				        if (skillResponse.code === "success") {
				            const skillsData = JSON.parse(skillResponse.body);
				            const skillsContainer = $("#jobDetailsSec .jd-skills");
				            skillsContainer.empty(); 
				
				            if (skillsData.length > 0) {
								 /*<i class="fas fa-check-circle text-success me-3 bg-web-clr-txt"></i>*/
				                skillsData.forEach(skill => {
				                    skillsContainer.append(`
				                        <div class="col-md-4 mb-3">
				                            <div class="skill-card border rounded p-2 d-flex align-items-center">
				                               
				                                <div>
				                                    <span class="fw-bold">${skill.skillName}</span>
				                                    <small class="d-block text-muted">Experience: ${skill.skillValue} years</small>
				                                </div>
				                            </div>
				                        </div>
				                    `);
				                });
				            } else {
				                skillsContainer.append(`
				                    <div class="col-12">
				                        <p class="text-muted">No skills specified</p>
				                    </div>
				                `);
				            }
				        } else {
				            console.error("Failed to fetch skills data.");
				        }
				    },
				    error: function (error) {
				        console.error("Error fetching skills data:", error);
				    }
				});


                // Footer details
                $("#created-on").text(job.createdOn || "N/A");
                $("#approved-by").text(job.approvedBy || "N/A");
            } else {
                console.error("Failed to fetch job details or no data found.");
            }
        },
        error: function (error) {
            console.error("Error fetching job details:", error);
        }
    });
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

