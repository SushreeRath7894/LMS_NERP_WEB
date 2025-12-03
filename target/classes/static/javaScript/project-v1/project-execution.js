$(()=>{
    $('#dprStatus').select2();  //#dprStatus
    $('#priorityMain').select2();  //#priorityMain
    $('#priorityC').select2();  //#priorityC
});

let type = '';
$(document).ready(function () {

    const urlParams = new URLSearchParams(window.location.search);
    type = urlParams.get('id');

    if (type == null || type == 'null') {
        type = '';
    }

    document.querySelector('.nav-item a[href="#raiseTicket"]').parentElement.style.display = 'none';
    $("#quickFilter").on("keydown", function (event) {
        if (event.key === "Enter" || event.which === 13) {
            event.preventDefault();
            onQuickFilterChanged();
        }
    });
    var gridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(gridDiv, gridOptions);
    gridOptions.api.setRowData([]);


    var gridDiv = document.querySelector('#myGridVersion');
    new agGrid.Grid(gridDiv, gridOptionsVersion);
    gridOptionsVersion.api.setRowData([]);


    var dprGridDiv = document.querySelector('#dprGrid');
    new agGrid.Grid(dprGridDiv, dprGridOptions);
    dprGridOptions.api.setRowData([]);

    $("#mySidenavMain").hide();
    $("#mySidenavChild").hide();
    $("#saveParent").hide();
    $("#saveChild").hide();
    $("#cancel").hide();
    $("#reqBtnbtn").hide();
    $("#openDailyReport").hide();


    var dateFormat = localStorage.getItem("dateFormat");

    $("#DateCalendarMain").datetimepicker({
        format: 'd-m-Y',
        closeOnDateSelect: true,
        timepicker: false,
        datepicker: true,
    }).on("change", function () {
        $('#startDateMain').val($(this).val());
        validateDates();
    });

    $('#startDateMain').blur(function () {
        $("#DateCalendarMain").val($(this).val());
    });

    $("#EndDateCalendarMain").datetimepicker({
        format: 'd-m-Y',
        closeOnDateSelect: true,
        timepicker: false,
        datepicker: true,
    }).on("change", function () {
        $('#endDateMain').val($(this).val());
        validateDates();
    });

    $('#endDateMain').blur(function () {
        $("#EndDateCalendarMain").val($(this).val());
    });
    /**/
    $("#ReceiveDateCalander").datetimepicker({
        format: 'd-m-Y',
        closeOnDateSelect: true,
        timepicker: false,
        datepicker: true,
    }).on("change", function () {
        $('#receiveDate').val($(this).val());
        validateDatesChild();

    });

    $('#receiveDate').blur(function () {
        $("#ReceiveDateCalander").val($(this).val());
    });

    getAllProject();


    $('.nav-item a').on('click', function () {
        const selectedId = $(this).attr('href').replace("#", "");
        if (selectedId === "raiseTicket") {
            return;
        } else {
            $("#reqTable").css('display', 'none');
            document.querySelector('.nav-item a[href="#raiseTicket"]').parentElement.style.display = 'none';
            $("#raiseTicket").css('display', 'none');
            $("#reqBtnbtn").hide();
        }
    });


    var gridDiv = document.querySelector('#product');
    new agGrid.Grid(gridDiv, productOptions);
    productOptions.api.setRowData([]);
    $("#reqTable").css('display', 'none');

    const toggleDescendantsAndIcons = (taskId, hide) => {
        const rows = document.querySelectorAll(`[data-stt-parent="${taskId}"]`);
        rows.forEach(row => {
            row.style.display = hide ? "none" : "";
            const childTaskId = row.getAttribute("data-task-id");
            const toggleIcon = row.querySelector(".toggle-icon");
            if (toggleIcon) {
                toggleIcon.classList.remove(hide ? "fa-chevron-down" : "fa-chevron-right");
                toggleIcon.classList.add(hide ? "fa-chevron-right" : "fa-chevron-down");
            }
            toggleDescendantsAndIcons(childTaskId, hide);
        });
    };
    document.addEventListener("click", (event) => {
        if (event.target.classList.contains("toggle-icon")) {
            const taskId = event.target.getAttribute("data-task-id");
            const isExpanded = event.target.classList.contains("fa-chevron-down");
            toggleDescendantsAndIcons(taskId, isExpanded);
            event.target.classList.toggle("fa-chevron-down");
            event.target.classList.toggle("fa-chevron-right");
        }
    });

});
var columnDefs = [
    {
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Project Id",
        field: "projectId",
        flex: 1

    },
    {
        headerName: "Project Name",
        field: "projectname",
        flex: 1
    },
    {
        headerName: "Project Incharge Name",
        field: "projectInc",
        flex: 1
    },

    {
        headerName: "Creation Date",
        field: "creationDate",
        flex: 1
    }];

// Define grid options
var gridOptions = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 200
    },
    pagination: true,
    paginationPageSize: 15,
    onSelectionChanged: rowSelect
};


let projectId = "";

function rowSelect() {
    var selectedRows = gridOptions.api.getSelectedRows();
    if (selectedRows.length > 0) {
        var datas = selectedRows[0];
        projectId = datas.projectId;
        var projectname = datas.projectname;
        $("#projectId").text(projectname);
        $("#projectIdExpenseLog").text(projectname);
        $("#projectIdChngOrd").text(projectname);
        $("#projectIdBgtVsAct").text(projectname);
        $("#projectIdPayment").text(projectname);
        $("#reqHeadId").text(projectname);

        getAllTaskDetails(projectId);
        $("#addBtn").prop("disabled", false);
        cancel();
    } else {
        getAllTaskDetails('');
        $("#projectId").text('');
        $("#addBtn").prop("disabled", true);
        cancel();
    }

}

function getAllProject() {

    agGrid.simpleHttpRequest({
        url: "view-all-task-project?type=" + type
    }).then(function (response) {
        if (response.code === "Success") {

            const responseBody = JSON.parse(response.body);
            const projectDetails = responseBody.projectDetails;
            var newRowData = projectDetails.reverse();
            gridOptions.api.setRowData(newRowData);
            if (newRowData && newRowData.length > 0) {
                gridOptions.api.forEachNode(function (node) {
                    if (node.rowIndex === 0) {
                        node.setSelected(true);
                    }
                });
            }

        } else {
            console.error("Failed to fetch data");
        }
    });

}

function addNew() {
    $(".formValidation").remove();
    $("#mySidenavMain").show();
    $("#saveParent").show();
    $("#addBtn").hide();
    $("#cancel").show();
    clearfieldsVal();
    $("#basicTbl").hide();
}

function saveParentData() {
    const PlanDataList = [];
    const planData = {};

    planData.parentId = $("#taskId").val();
    planData.projectId = projectId;
    planData.taskName = $("#taskNameMain").val();
    planData.priority = $("#priorityMain").val();
    planData.assignedTo = $("#assignToValM").val();
    planData.preceders = $("#precedersMain").val();
    planData.notes = $("#notesMain").val();
    planData.startDate = $("#startDateMain").val();
    planData.endDate = $("#endDateMain").val();
    PlanDataList.push(planData);


    var validation = true;

    if (planData.taskName == null || planData.taskName == "") {
        toastr.error("Task Name  Required");
        return;
    }

    if (planData.startDate == null || planData.startDate == "") {
        toastr.error("Start Date  Required");
        return;
    }
    if (planData.endDate == null || planData.endDate == "") {
        toastr.error("End Date  Required");
        return;
    }
    if (planData.assignedTo == null || planData.assignedTo == "") {
        toastr.error("Assign To  Required");
        return;
    }
    if (planData.preceders == null || planData.preceders == "") {
        toastr.error("Preceders  Required");
        return;
    }


    if (validation) {
        saveProjectTask(PlanDataList);
    }
}

function saveProjectTask(PlanDataList) {
    $.ajax({
        type: "POST",
        url: "add-project-task-details",
        contentType: "application/json",
        data: JSON.stringify(PlanDataList),
        success: function (Response) {
            if (Response.code === "Success") {
                toastr.success(Response.message)
                $("#basicTbl").hide();
                $("#mySidenavMain").hide();
                cancel();
                getAllTaskDetails(projectId);

            } else {
                console.error("Error:", response.message);
            }
        },
        error: function (error) {
            console.error("AJAX Error:", error);
        }
    });

}


function getAssignToAutoSearchM() {
    var search = $("#assignToNameM").val();
    if (search == "") {
        $("#suggesstion-box_5").hide();
    }

    if (search) {
        $.ajax({
            type: "GET",
            url: "planning-new-autosearch-assignTo?searchValue="
                + search,
            success: function (response) {
                if (response.message == "success") {
                    if (response.body.length != 0) {
                        $("#search").css("background", "#6A2BBF");
                        var content = '<ul id="autocomplete-list" style="color:#6A2BBF;">';
                        for (var i = 0; i < response.body.length; i++) {

                            content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue5(\''
                                + response.body[i].key
                                + '\',\''
                                + response.body[i].name
                                + '\')">'
                                + response.body[i].name
                                + '</li>';
                        }
                        content += '</ul>';
                        $("#suggesstion-box_5").show();
                        $("#suggesstion-box_5").html(content);

                    } else {
                        $("#search").css("background", "#6A2BBF");
                        var content = '<div id="autocomplete-list">';
                        content += '<div onClick="selectAutocompleteValue5()">'
                            + "No Data Found" + '</div>';
                        content += '</div>';
                        $("#suggesstion-box_5").show();
                        $("#suggesstion-box_5").html(content);

                    }
                }
            },
            error: function (data) {
            }
        })
    }
}

function selectAutocompleteValue5(key, name) {
    if (name) {
        $("#assignToNameM").val(name);
        $("#assignToValM").val(key);
        $("#search").val(name);
        $("#search").attr('data-procat', name);
        $("#suggesstion-box_5").hide();

    } else {
        $("#assignToNameM").val("");
        $("#search").val("");
        $("#search").attr('data-procat', "");
        $("#suggesstion-box_5").hide();

    }
}

function getAssignToAutoSearchC() {
    var search = $("#assignToNameC").val();
    if (search == "") {
        $("#suggesstion-box_6").hide();
    }

    if (search) {
        $.ajax({
            type: "GET",
            url: "planning-new-autosearch-assignTo?searchValue="
                + search,
            success: function (response) {
                if (response.message == "success") {
                    if (response.body.length != 0) {
                        $("#search").css("background", "#6A2BBF");
                        var content = '<ul id="autocomplete-list" style="color:#6A2BBF;">';
                        for (var i = 0; i < response.body.length; i++) {

                            content += '<li class="autocompletedata cp" onclick="selectAutocompleteValue6(\''
                                + response.body[i].key
                                + '\',\''
                                + response.body[i].name
                                + '\')">'
                                + response.body[i].name
                                + '</li>';
                        }
                        content += '</ul>';
                        $("#suggesstion-box_6").show();
                        $("#suggesstion-box_6").html(content);

                    } else {
                        $("#search").css("background", "#6A2BBF");
                        var content = '<div id="autocomplete-list">';
                        content += '<div onClick="selectAutocompleteValue6()">'
                            + "No Data Found" + '</div>';
                        content += '</div>';
                        $("#suggesstion-box_6").show();
                        $("#suggesstion-box_6").html(content);

                    }
                }
            },
            error: function (data) {
            }
        })
    }
}

function selectAutocompleteValue6(key, name) {
    if (name) {
        $("#assignToNameC").val(name);
        $("#assignToValC").val(key);
        $("#search").val(name);
        $("#search").attr('data-procat', name);
        $("#suggesstion-box_6").hide();

    } else {
        $("#assignToNameC").val("");
        $("#search").val("");
        $("#search").attr('data-procat', "");
        $("#suggesstion-box_6").hide();

    }
}
 

function getAllTaskDetails(projectId) {

    agGrid.simpleHttpRequest({
        url: "view-all-task-details?id=" + projectId
    }).then(function (response) {
        if (response.message === "Success") {
            $("#basicTbl").show();
            const responseBody = JSON.parse(response.body[0]);
            const tableBody = $("#tbodyData12");
            $("#tbodyData12").empty();

            const taskMap = new Map();

            responseBody.forEach(task => {
                taskMap.set(task.taskId, task);
            });

            responseBody.forEach(task => {
                const parentTask = taskMap.get(task.taskParent);
                if (parentTask && task.taskId !== task.taskParent) {
                    parentTask.children = parentTask.children || [];
                    parentTask.children.push(task);
                }
            });

            const topLevelTasks = responseBody.filter(task => task.taskId === task.taskParent);

            const generateSlNo = (tasks, parentSlNo = "") => {
                let counter = 1;
                tasks.forEach(task => {
                    const currentSlNo = parentSlNo ? `${parentSlNo}.${counter}` : `${counter}`;
                    task.dynamicSlNo = currentSlNo;
                    if (task.children) {
                        generateSlNo(task.children, currentSlNo);
                    }
                    counter++;
                });
            };
            generateSlNo(topLevelTasks);
            const renderTasks = (tasks, level = 0, parentId = null) => {
                tasks.forEach(task => {
                    const taskId = task.taskId || "";
                    const taskName = task.taskName || "";
                    const taskPriority = task.taskPriority || "";
                    const projectName = task.projectName || "";
                    const preceders = task.preceder || "";
                    const notes = task.taskNote || "";
                    const assignedTo = task.assignedTo || "";
                    const dynamicSlNo = task.dynamicSlNo || "";
                    const requisitionId = task.requisitionId || "";

                    const slNo = task.slNo || "";

                    let parentTaskName = "";
                    let parentStartDate = "";
                    let parentEndDate = "";

                    // Find the parent task's start and end date if a parent exists
                    if (parentId && taskMap.has(parentId)) {
                        const parentTask = taskMap.get(parentId);
                        parentTaskName = parentTask.taskName || "N/A";
                        parentStartDate = parentTask.docDtls ? parentTask.docDtls[0]?.startDate || "" : "";
                        parentEndDate = parentTask.docDtls ? parentTask.docDtls[parentTask.docDtls.length - 1]?.endDate || "" : "";
                    }

                    let startDate = "";
                    let endDate = "";
                    let duration = 0;
                    let version = "";

                    if (task.docDtls && task.docDtls.length > 0) {
                        startDate = task.docDtls[0].startDate || "";
                        endDate = task.docDtls[task.docDtls.length - 1].endDate || "";
                        duration = task.docDtls.reduce((total, doc) => total + (doc.planDays || 0), 0);
                        version = task.docDtls[0].version || "";
                    }
                    const createdBy = task.docDtls[0]?.createdBy || "";
                    const isParent = taskId === task.taskParent;
                    const editFunction = isParent
                        ? `editParent('${taskId}', '${taskName}', '${taskPriority}', '${startDate}', '${endDate}', '${assignedTo}', '${notes}', '${slNo}')`
                        : `editChild('${taskId}', '${taskName}', '${taskPriority}', '${startDate}', '${endDate}', '${assignedTo}', '${notes}', '${slNo}', '${parentTaskName}', '${parentStartDate}', '${parentEndDate}')`; // Pass parent start and end date

                    const hasChildren = task.children && task.children.length > 0;
                    const treeIcon = hasChildren ? `<i class="fa fa-chevron-down toggle-icon" data-task-id="${taskId}" style="cursor: pointer;"></i>` : "";

                    const requisitionIcons = requisitionId
                        ? `
							<i class="fa fa-sign-in" style="cursor:pointer;" onclick="requisition('${taskId}')"></i>
							<i class="fa fa-eye" style="margin-left: 10px; cursor: pointer;" onclick="viewRequisition('${taskId}')"></i>
						  `
                        : `
							<i class="fa fa-sign-in" style="cursor:pointer;" onclick="requisition('${taskId}')"></i>
						  `;

                    const dprIcon = `
					    <i class="fa fa-pen-to-square" style="cursor: pointer; color: #007BFF;" 
					       onclick="openDPR('${taskId}','${taskName}','${startDate}', '${endDate}')"></i>
					`;


                    const row = `
					<tr data-task-id="${taskId}" data-stt-parent="${parentId}" class="task-row">
					    <td style="padding-left: ${level * 20}px;">
					        ${treeIcon}
					        ${dynamicSlNo} - ${taskName} 
					        <span class="mrg-lft">
					            <i class="fa fa-plus" style="height: 25px; width: 25px; font-size: 1.30em; color:#6A2BBF;" onclick="openAddModal('${taskId}', '${taskName}', '${taskPriority}', '${startDate}', '${endDate}', '${assignedTo}', '${notes}', '${slNo}')"></i>
					        </span>
					        <span class="mrg-lft">
					            <i class="fa fa-edit" style="height: 25px; width: 25px; font-size: 1.40em; color:#6A2BBF;" onclick="${editFunction}"></i>
					        </span>
					    </td>
					    <td>${taskPriority}</td>
					    <td>${projectName}</td>
					    <td>${startDate}</td>
					    <td>${endDate}</td>
					    <td>
					        <a href="javascript:void(0);" onclick="handleVersionClick('${taskId}', '${version}')">
					            ${version}
					        </a>
					    </td>
					    <td>${assignedTo}</td>
					    <td>${duration} days</td>
					    <td>${preceders}</td>
					    <td>${notes}</td>
					    <td>${createdBy}</td>
					    <td>${requisitionIcons}</td>
					    <td>${dprIcon}</td> <!-- New DPR Column -->
					</tr>`;


                    tableBody.append(row);

                    if (hasChildren) {
                        renderTasks(task.children, level + 1, taskId);
                    }
                });
            };


            renderTasks(topLevelTasks);
        } else {
            console.error("Failed to fetch data");
        }
    });
}

function openAddModal(taskId, taskName, taskPriority, startDate, endDate, assignedTo, notes, slNo) {
    childDateValidation(startDate, endDate)
    clearfieldsVal();
    $("#basicTbl").hide();
    $("#addBtn").hide();
    $("#cancel").show();
    $("#mySidenavMain").hide();
    $("#mySidenavChild").show();
    $("#taskName").text(`Parent: ${taskName}`);
    $("#taskParentIdC").val(taskId);
    $("#slNo").val(slNo);


    $("#saveParent").hide();
    $("#saveChild").show();

}

function editParent(taskId, taskName, taskPriority, startDate, endDate, assignedTo, notes) {
    $("#addBtn").hide();
    $("#cancel").show();
    $("#basicTbl").hide();
    clearfieldsVal();
    $("#mySidenavMain").show();
    $("#mySidenavChild").hide();
    $("#cancel").show();
    $("#taskIds").text(`Task Id: ${taskId}`);
    $("#mySidenav").show();

    $("#saveParent").show();
    $("#saveChild").hide();


    agGrid.simpleHttpRequest({
        url: "view-all-task-details-edit?id=" + taskId
    }).then(function (response) {
        if (response.message === "Success") {

            const responseBody = JSON.parse(response.body[0]);
            const taskDetails = responseBody.taskDetails[0];
            $("#taskId").val(taskDetails.taskId);
            $("#projectIdMain").val(taskDetails.projectId);
            $("#taskNameMain").val(taskDetails.taskName);
            $("#taskParent").val(taskDetails.taskParent);
            $("#taskSlNoMain").val(taskDetails.taskSlNo);
            $("#taskLevelMain").val(taskDetails.taskLevel);
            $("#precedersMain").val(taskDetails.preceder);
            $("#assignToValM").val(taskDetails.assignToVal);
            $("#assignToNameM").val(taskDetails.assignToName);
            $("#priorityMain").val(taskDetails.priority);
            $("#notesMain").val(taskDetails.note);
            $("#startDateMain").val(taskDetails.startDate);
            $("#endDateMain").val(taskDetails.endDate);
        }
    });

}

function editChild(taskId, taskName, taskPriority, startDate, endDate, assignedTo, notes, slNo, parentTaskName, parentStartDate, parentEndDate) {

    childDateValidation(parentStartDate, parentEndDate)

    clearfieldsVal();
    $("#mySidenavMain").hide();
    $("#mySidenavChild").show();
    $("#basicTbl").hide();
    $("#addBtn").hide();
    $("#cancel").show();
    $("#taskName").text(`Task Name: ${taskName}`);
    $("#mySidenav").show();
    $("#saveParent").hide();
    $("#saveChild").show();

    agGrid.simpleHttpRequest({
        url: "view-all-task-details-edit?id=" + taskId
    }).then(function (response) {
        if (response.message === "Success") {
            const responseBody = JSON.parse(response.body[0]);
            const taskDetails = responseBody.taskDetails[0];
            $("#planChildId").val(taskDetails.taskId);
            $("#projectIdC").val(taskDetails.projectId);
            $("#taskNameC").val(taskDetails.taskName);
            $("#taskParentIdC").val(taskDetails.taskParent);
            $("#taskSlNoChild").val(taskDetails.taskSlNo);
            $("#taskLevelC").val(taskDetails.taskLevel);
            $("#preceders").val(taskDetails.preceder);
            $("#assignToValC").val(taskDetails.assignToVal);
            $("#assignToNameC").val(taskDetails.assignToName);
            $("#priorityC").val(taskDetails.priority);
            $("#notesC").val(taskDetails.note);
            $("#startDateC").val(taskDetails.startDate);
            $("#endDateC").val(taskDetails.endDate);
        }
    })
}

function cancel() {
    clearfieldsVal();
    $("#cancel").hide();
    $("#addBtn").show();
    $("#cancel").hide();
    $("#saveParent").hide();
    $("#saveChild").hide();
    $("#mySidenavMain").hide();
    $("#mySidenavChild").hide();
    $("#basicTbl").show();
    $("#openDailyReport").hide();
    $("#mainTabRow").show();
    $("#notesDpr").val('');

}


function saveChildData() {
    var validationChild = true;
    var childPlanData = {};

    var childPlanDataList = [];
    childPlanData.parentId = $("#taskParentIdC").val();
    childPlanData.planChildId = $("#planChildId").val();
    childPlanData.projectId = projectId;
    childPlanData.taskName = $("#taskNameC").val();
    childPlanData.priority = $("#priorityC").val();
    childPlanData.assignedTo = $("#assignToValC").val();
    childPlanData.preceders = $("#preceders").val();
    childPlanData.notes = $("#notesC").val();
    childPlanData.startDate = $("#startDateC").val();
    childPlanData.endDate = $("#endDateC").val();

    childPlanDataList.push(childPlanData);


    if (childPlanData.taskName == null || childPlanData.taskName == "") {
        toastr.error("Task Name  Required");
        return;
    }

    if (childPlanData.startDate == null || childPlanData.startDate == "") {
        toastr.error("Start Date  Required");
        return;
    }
    if (childPlanData.endDate == null || childPlanData.endDate == "") {
        toastr.error("End Date  Required");
        return;
    }
    if (childPlanData.assignedTo == null || childPlanData.assignedTo == "") {
        toastr.error("Assign To  Required");
        return;
    }
    if (childPlanData.preceders == null || childPlanData.preceders == "") {
        toastr.error("Preceders  Required");
        return;
    }


    if (validationChild) {
        saveChildProjectTask(childPlanDataList);
    }

}

function saveChildProjectTask(childPlanDataList) {
    $.ajax({
        type: "POST",
        url: "add-project-task-details-child",
        contentType: "application/json",
        data: JSON.stringify(childPlanDataList),
        success: function (Response) {
            if (Response.code === "Success") {
                toastr.success(Response.message);
                $("#mySidenavMain").hide();
                $("#mySidenavChild").hide();
                cancel();
                getAllTaskDetails(projectId);
            } else {
                console.error("Error:", response.message);
            }
        },
        error: function (error) {
            console.error("AJAX Error:", error);
        }
    });
}

function clearfieldsVal() {
    $(".formValidation").remove();
    $("#taskId").val('');
    $("#taskNameMain").val('');
    $("#priorityMain").val('');
    $("#ATnameC").val('');
    $("#precedersMain").val('');
    $("#notesMain").val('');
    $("#startDateMain").val('');
    $("#endDateMain").val('');
    $("#slNo").val('');
    $("#taskIds1").val('');
    $("#taskNameC").val('');
    $("#priorityC").val('');
    $("#assignToNameM").val('');
    $("#assignToNameC").val('');
    $("#assignToValM").val('');
    $("#assignToValC").val('');
    $("#preceders").val('');
    $("#notesC").val('');
    $("#startDateC").val('');
    $("#endDateC").val('');
    $("#taskName").text('');
    $("#taskName").text('');
    $("#taskIds").text('');


    $("#dprStatus").val('');
    $("#startPlannedDate").val('');
    $("#endPlannedDate").val('');
    $("#plannedHours").val('');
    $("#startDateActPlanned").val('');
    $("#notesDpr").val('');
    $("#dprId").val('');
    $("#fileUpload").val('');
    //$("#wImgUrl").attr("src","");
    $("#wImgUrl").attr("src", "").hide();
    $("#remove-btn").hide();

}

var columnDefsVersion = [
    {
        headerCheckboxSelection: true,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 15,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Task Id",
        field: "taskId",

    },
    {
        headerName: "Version",
        field: "version"
    },
    {
        headerName: "Start Date",
        field: "startDate",

    },

    {
        headerName: "End Date",
        field: "endDate"
    }, {
        headerName: "Duration",
        field: "planDuration"
    }, {
        headerName: "Created By",
        field: "createdBy"
    }];

// Define grid options
var gridOptionsVersion = {
    columnDefs: columnDefsVersion,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 115
    },
    pagination: true,
    paginationPageSize: 15,

};


function handleVersionClick(taskId) {
    agGrid.simpleHttpRequest({
        url: "view-all-task-version-details?id=" + taskId
    }).then(function (response) {
        if (response.code === "Success") {
            $("#versionDetailsModal").show();

            const responseBody = JSON.parse(response.body[0]);
            const versionDetails = responseBody.versionDetails;

            gridOptionsVersion.api.setRowData(versionDetails);
        }
    });

}

$(window).on("click", function (event) {
    if ($(event.target).is("#versionDetailsModal")) {
        $("#versionDetailsModal").fadeOut();
    }
});


let taskIdReq = "";

function requisition(taskId) {
    $(".formValidation").remove();
    $("#deleteProdBtn").prop("disabled", true);
    $("#raiseTicket").show();
    taskIdReq = taskId;
    const requisitionTab = document.querySelector('.nav-item a[href="#raiseTicket"]');
    requisitionTab.parentElement.style.display = 'block';
    const activeTabs = document.querySelectorAll('.nav-item .active, .tab-pane.active');
    activeTabs.forEach(tab => tab.classList.remove('active', 'show'));
    requisitionTab.classList.add('active');
    const requisitionContent = document.querySelector('#raiseTicket');
    requisitionContent.classList.add('active', 'show');
    $("#reqTable").css('display', 'none');
    clearReqForm();
}

// for product table
var productDefs = [
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
        headerName: "SlNo",
        field: "slNo",
        width: 70,
        cellRenderer: function (params) {
            const slNo = params.node.rowIndex + 1;

            return '<a onclick="editProduct(' + slNo + ')" href="javascript:void(0)">' + slNo + '</a>';
        }
    },
    {
        headerName: "MATERIAL CODE",
        field: "sku",
        cellStyle: {
            textAlign: 'center'
        }
    }, {
        headerName: "HSN CODE",
        field: "hsnCode",
        cellStyle: {
            textAlign: 'center'
        }
    },
    {
        headerName: "MATERIAL NAME",
        field: "itemName",

    }, {
        headerName: "itemId",
        field: "itemId",
        hide: "true"
    }, {
        headerName: "Model/Size",
        field: "model",
        cellStyle: {
            textAlign: 'center'
        }

    }, {
        headerName: "Quantity",
        field: "quantity",
        cellStyle: {
            textAlign: 'center'
        }

    }, {

        headerName: 'Unit',
        field: "unitName",
        width: 175,
        cellStyle: {
            textAlign: 'center'
        }
    }, {
        headerName: 'Unit',
        field: "unit",
        hide: true,

    },];


// let the grid know which columns and what data to use activity table
var productOptions = {
    columnDefs: productDefs,
    rowSelection: 'multiple',
    groupSelectsChildren: true,
    suppressRowClickSelection: true,
    suppressAggFuncInHeader: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 153
    },
    onSelectionChanged: onSelectionChangedItem,
    getRowNodeId: function (data) {
        return data.slNo;
    }

};


function onSelectionChangedItem() {
    var selectedRows = productOptions.api.getSelectedRows();
    if (selectedRows.length > 0) {
        $("#deleteProdBtn").prop("disabled", false);
    } else {
        $("#deleteProdBtn").prop("disabled", true);
    }
}

function getItemQutotationList() {
    var search = $("#itemName").val();
    if (search) {
        $.ajax({
            type: "POST",
            url: "rmpm-requisition-item-get-list",
            dataType: 'json',
            contentType: 'application/json',
            data: search,
            success: function (response) {
                if (response.message == "success") {
                    if (response.body.length != 0) {
                        $("#itemId").val("");
                        $("#gstRate").val("");
                        $("#sku").val("");
                        $("#brandName").val("");
                        $("#brandId").val("");
                        $("#hsnCode").val("");
                        $("#unit").val("");
                        $("#model").val("");
                        $("#itemName").css("background", "#FFF");
                        var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
                        for (var i = 0; i < response.body.length; i++) {
                            content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueItem1(\''
                                + response.body[i].sku
                                + '\',\''
                                + response.body[i].productId
                                + '\',\''
                                + window.btoa(response.body[i].productName)
                                + '\',\''
                                + response.body[i].brandId
                                + '\',\''
                                + window.btoa(response.body[i].brandName)
                                + '\',\''
                                + window.btoa(response.body[i].hsnCode)
                                + '\',\''
                                + window.btoa(response.body[i].unit)
                                + '\',\''
                                + window.btoa(response.body[i].gstRate)
                                + '\',\''
                                + window.btoa(response.body[i].model)
                                + '\')">'
                                + response.body[i].sku
                                + " - "
                                + response.body[i].productName
                                + '</li>';
                        }
                        content += '</ul>';
                        $("#suggesstion-box_").show();
                        $("#suggesstion-box_").html(content);

                    } else {
                        $("#itemName").css("background", "#FFF");
                        var content = '<ul id="autocomplete-list1">';
                        content += '<font-weight:100; font-size:14px; color:#ccc;     background-color: #0909e4;"li onClick="selectAutocompleteValueItem()">'
                            + "No Data Found" + '</li>';
                        content += '</ul>';
                        $("#suggesstion-box_").show();
                        $("#suggesstion-box_").html(content);
                    }
                }
            },
            error: function (data) {
            }
        })
    } else {
        $("#itemId").val("");
        $("#itemName").val("");
        $("#gstRate").val("");
        $("#sku").val("");
        $("#brandName").val("");
        $("#brandId").val("");
        $("#hsnCode").val("");
        $("#unit").val("");
        $("#model").val("");
        $("#suggesstion-box_").hide();
    }
}

function selectAutocompleteValueItem1(sku, productId, productName, brandId, brandName,
                                      hsnCode, unit, gstRate, model) {
    if (sku) {
        //$("#sku").val(sku);
        $("#itemName").val(window.atob(productName));
        $("#itemIdTemp").val(productId);
        $("#brandName").val(window.atob(brandName));
        $("#brandId").val(brandId);
        $("#skuTemp").val(sku);
        $("#sku").val(sku);
        $("#hsnCode").val(window.atob(hsnCode));
        $("#unit").val(window.atob(unit));
        $("#gstRate").val(window.atob(gstRate));
        $("#model").val(window.atob(model));
        $("#itemName").attr('data-procat', itemId);
        $("#suggesstion-box_").hide();
        //hideShowS();
    } else {
        $("#sku").val("");
        $("#itemId").val("");
        $("#itemName").val("");
        $("#gstRate").val("");
        $("#brandName").val("");
        $("#brandId").val("");
        $("#hsnCode").val("");
        $("#unit").val("");
        $("#model").val("");
        $("#itemName").attr('data-procat', "");
        $("#suggesstion-box_").hide();

    }
}

function saveTableData(event) {
    event.preventDefault();
    var item = {};
    var data = 1;
    var validation = true;
    var editProduct = $("#editProduct").val();
    if (item.itemName == null || item.itemName == "") {
        validation = validationUpdated("Item Name Required", 'itemName');
    }
    if (item.quantity == null || item.quantity == "") {
        validation = validationUpdated("Quantity Required", 'quantity');
    }
    if (validation) {
        item.slNo = data;
        productOptions.api.forEachNode(function (rowNode, index) {
            if (!editProduct) {
                data = data + 1;
                item.slNo = data;
            } else {
                item.slNo = editProduct;
            }

        });
        item.itemId = $('#itemIdTemp').val();
        item.sku = $('#skuTemp').val();
        item.itemName = $('#itemName').val();
        item.hsnCode = $('#hsnCode').val();
        item.model = $("#model").val();
        item.quantity = $("#quantity").val();
        item.unit = $('#unit').val();
        item.unitName = $("#unit option:selected").text();
        var datas = [];

        if (editProduct) {
            productOptions.api.forEachNode((node) => {
                var currentSlNo = node.rowIndex + 1;
                if (currentSlNo == editProduct) {
                    node.setData(item);
                }
            });
        } else {
            productOptions.api.forEachNode(function (rowNode, index) {
                datas.push(rowNode.data);
            });
            datas.push(item)


            productOptions.api.setRowData(datas);
        }
        $("#sku").val('');
        $("#skuEditId").html('');
        $("#itemId").val('');
        $("#itemName").val('');
        $("#model").val('');
        $("#quantity").val('');
        $("#unit").val('');
        $("#search").val('');
        $("#hsnCode").val('');
        $("#editProduct").val(null);
    }

}

function getRowDataBySlNo(targetSlNo) {
    let targetRowData = null;

    productOptions.api.forEachNode((node) => {
        const currentSlNo = node.rowIndex + 1;

        if (currentSlNo === targetSlNo) {
            targetRowData = node.data;
        }
    });

    if (targetRowData) {
        return targetRowData;
    } else {
        return null;
    }
}

function editProduct(slNo) {

    var reqId = $("#reqHeadId").html();
    var data = getRowDataBySlNo(slNo);
    $("#editProduct").val(slNo);
    $("#sku").val(data.sku);
    $("#itemId").val(data.itemId);
    $("#hsnCode").val(data.hsnCode);
    $("#itemName").val(data.itemName);
    $("#model").val(data.model);
    $("#editProduct").val(slNo);
    $("#quantity").val(data.quantity);
    $("#unit").val(data.unit);
    $("#unitPrice").val(data.unitPrice);
    $("#skuTemp").val(data.sku);
    $("#lineTotal").val(data.lineTotal);
    $("#itemIdTemp").val(data.itemId);


}

function deleteProduct(event) {
    event.preventDefault();
    var selectedRows = productOptions.api.getSelectedRows();
    productOptions.api.applyTransaction({
        remove: selectedRows
    });

}


function saveReqData() {
    var validation = true;
    var item = {};
    item.receiveDate = $("#receiveDate").val();
    if (!item.receiveDate) {
        toastr.error("Date Required")
        return;
    }

    var totalRowCount1 = productOptions.api.getModel().getRowCount();

    if (totalRowCount1 > 0) {
        item.planId = taskIdReq;
        item.projectId = projectId;
        item.desc = $("#desc").val();
        item.reqId = $("#reqHeadId").html();

        var datas = [];
        productOptions.api.forEachNode(function (rowNode, index) {
            var item1 = rowNode.data;
            datas.push(item1);
        });
        item.details = datas;

        if (validation) {
            saveAllRequisition(item);
        }
    } else {
        toastr.error("Please add at least one item before proceeding.");
    }
}


function saveAllRequisition(datas) {
    $('.loader').show();
    $("body").addClass("overlay");
    $.ajax({
        type: "POST",
        url: "rmpm-requisition-add-task",
        contentType: "application/json",
        data: JSON.stringify(datas),
        success: function (response) {
            if (response.code == "success") {
                $(".formValidation").remove();
                $('.loader').hide();
                $("body").removeClass("overlay");
                const requisitionTab = document.querySelector('.nav-item a[href="#vital"]');
                requisitionTab.parentElement.style.display = 'block';
                const activeTabs = document.querySelectorAll('.nav-item .active, .tab-pane.active');
                activeTabs.forEach(tab => tab.classList.remove('active', 'show'));
                requisitionTab.classList.add('active');
                document.querySelector('#vital').classList.add('active', 'show');
                $("#reqTable").css('display', 'none');
                document.querySelector('.nav-item a[href="#raiseTicket"]').parentElement.style.display = 'none';
                $("#raiseTicket").css('display', 'none');
                getAllTaskDetails(projectId);
                clearReqForm();
            }
        },
        error: function (datas) {
        }
    })

}

function clearReqForm() {
    productOptions.api.setRowData([]);
    $("#itemName").val('');
    $("#sku").val('');
    $("#hsnCode").val('');
    $("#unit").val('');
    $("#model").val('');
    $("#quantity").val('');
    $("#desc").val('');
    $("#receiveDate").val('');
}

function viewRequisition(reqId) {
    $("#reqBtnbtn").show();
    const requisitionTab = document.querySelector('.nav-item a[href="#raiseTicket"]');
    requisitionTab.parentElement.style.display = 'block';
    const activeTabs = document.querySelectorAll('.nav-item .active, .tab-pane.active');
    activeTabs.forEach(tab => tab.classList.remove('active', 'show'));
    requisitionTab.classList.add('active');
    const requisitionContent = document.querySelector('#raiseTicket');
    requisitionContent.classList.add('active', 'show');

    $("#raiseTicket").hide();
    $("#reqTable").css('display', 'block');
    agGrid.simpleHttpRequest({
        url: "view-all-task-requisition-details?id=" + reqId
    }).then(function (data) {
        const jsonData = JSON.parse(data.body[0]);
        const allData = jsonData.requisitionDetails;

        const tbody = document.getElementById("tbodyReqData");

        tbody.innerHTML = "";

        let previousTaskId = null;
        let previousReqId = null;
        let previousReceiveDate = null;

        allData.forEach((item) => {
            const row = document.createElement("tr");

            const taskIdCell = (item.taskId && item.taskId !== previousTaskId) ? item.taskId : '';
            const reqIdCell = (item.reqId && item.reqId !== previousReqId) ? item.reqId : '';
            const receiveDateCell = (item.receiveDate && item.receiveDate !== previousReceiveDate) ? item.receiveDate : '';

            previousTaskId = item.taskId;
            previousReqId = item.reqId;
            previousReceiveDate = item.receiveDate;

            row.innerHTML = `
				                <td>${taskIdCell}</td>
				                <td>${reqIdCell}</td>
				                <td>${receiveDateCell}</td>
				                <td>${item.desc || ''}</td>
				                <td>${item.sku || ''}</td>
				                <td>${item.hsnCode || ''}</td>
				                <td>${item.itemName || ''}</td>
				                <td>${item.model || ''}</td>
				                <td>${item.quantity || ''}</td>
				                <td>${item.unitName || ''}</td>
									            `;
            tbody.appendChild(row);
        });
        const tableDiv = document.getElementById("reqTable");
        tableDiv.style.display = "block";
    });
}


function validateDates() {
    const startDateValue = $("#startDateMain").val();
    const endDateValue = $("#endDateMain").val();


    if (startDateValue && endDateValue) {
        try {

            const parseDate = (dateString) => {
                const [day, month, year] = dateString.split("-").map(Number);
                return new Date(year, month - 1, day);
            };

            const startDate = parseDate(startDateValue);
            const endDate = parseDate(endDateValue);

            if (endDate < startDate) {
                $("#error10")
                    .text("End Date cannot be earlier than Start Date. Clearing End Date.")
                    .css("color", "red");
                $("#endDateMain").val("");
            } else {
                $("#error10").text("");
            }
        } catch (error) {
            console.error("Error parsing dates:", error);
            $("#error10").text("Invalid date format. Please use DD-MM-YYYY").css("color", "red");
        }
    } else {
        $("#error10").text("Both dates are required").css("color", "red");
    }
}

function validateDatesChild() {
    var startDateValue1 = $("#startDateC").val();
    var endDateValue1 = $("#endDateC").val();
    if (startDateValue1 && endDateValue1) {
        const parseDate1 = (dateString) => {
            var [day, month, year] = dateString.split("-").map(Number);
            return new Date(year, month - 1, day);
        };

        var startDate1 = parseDate1(startDateValue1);
        var endDate1 = parseDate1(endDateValue1);

        if (endDate1 < startDate1) {
            $("#error10")
                .text("End Date cannot be earlier than Start Date. Clearing End Date.")
                .css("color", "red");
            $("#endDateC").val("");
        } else {
            $("#error10").text("");
        }
    } else {
        $("#error10").text("Both dates are required").css("color", "red");
    }
}

function onQuickFilterChanged() {
    gridOptions.api
        .setQuickFilter(document.getElementById('quickFilter').value);
    var displayedRowCount = gridOptions.api.getDisplayedRowCount();

    var len = displayedRowCount;
    $('#totalReq').find('span').html(len);
}

function cancalReq() {
    const requisitionTab = document.querySelector('.nav-item a[href="#vital"]');
    requisitionTab.parentElement.style.display = 'block';
    const activeTabs = document.querySelectorAll('.nav-item .active, .tab-pane.active');
    activeTabs.forEach(tab => tab.classList.remove('active', 'show'));
    requisitionTab.classList.add('active');
    document.querySelector('#vital').classList.add('active', 'show');
    $("#reqTable").css('display', 'none');
    document.querySelector('.nav-item a[href="#raiseTicket"]').parentElement.style.display = 'none';
    $("#raiseTicket").css('display', 'none');
    $("#reqBtnbtn").hide();
}

function childDateValidation(startDate, endDate) {

    var dateFormat = "DD-MM-YYYY";

    var formattedStartDate = moment(startDate, "DD-MM-YYYY").toDate();
    var formattedEndDate = moment(endDate, "DD-MM-YYYY").toDate();

    $('#startDateC').datetimepicker({
        format: "d-m-Y",
        closeOnDateSelect: true,
        timepicker: false,
        minDate: formattedStartDate,
        maxDate: formattedEndDate,
        onShow: function () {
            this.setOptions({
                minDate: formattedStartDate,
                maxDate: formattedEndDate
            });
        },
        onSelectDate: function (ct) {
            var selectedDate = moment(ct).format(dateFormat);
            $('#startDateC').val(selectedDate);
            validateDatesChild();
        }
    });

    $('#endDateC').datetimepicker({
        format: "d-m-Y",
        closeOnDateSelect: true,
        timepicker: false,
        minDate: formattedStartDate,
        maxDate: formattedEndDate,
        onShow: function () {
            this.setOptions({
                minDate: formattedStartDate,
                maxDate: formattedEndDate
            });
        },
        onSelectDate: function (ct) {
            var selectedDate = moment(ct).format(dateFormat);
            $('#endDateC').val(selectedDate);
            validateDatesChild();
        }
    });

    $('#startDateC, #endDateC').attr("readonly", true);

    $('#DateCalendarC').on("click", function () {
        $('#startDateC').datetimepicker("show");
    });

    $('#EndDateCalendarC').on("click", function () {
        $('#endDateC').datetimepicker("show");
    });

    $('#startDateC, #endDateC').on("focus", function (event) {
        $(this).blur();
    });
}

function resetBtn() {
    $("#quickFilter").val('');
    gridOptions.api.setQuickFilter('');
    gridOptions.api.refreshCells({force: true});
}

function openDPR(taskId, taskName, startDate, endDate) {


    $("#saveDpr").hide();
    $("#taskIdDpr").val(taskId);
    dateCalanderInitalization(startDate, endDate);
    $("#taskNameDpr").text(taskName);
    $(".formValidation").remove();
    $("#saveParent").hide();
    $("#addBtn").hide();
    $("#cancel").show();
    $("#DateCalendarPlanned").hide();
    $("#DateCalendarPlannedEnd").hide();
    $("#DateCalendarActPlanned").hide();
    $("#DateCalendarActPlannedEnd").hide();
    $("#basicTbl").hide();
    $("#mainTabRow").hide();
    $("#openDailyReport").show();
    $("#openDprForm").hide();
    $("#addDpr").show();
    $("#editDpr").hide();
    $("#deleteDpr").hide();
    $("#cancelDpr").hide();
    clearfieldsVal();
    getDprData();

    // Image Src check
    var imageElement = $("#wImgUrl");
    if (imageElement.src == undefined) {
        $("#wImgUrl").hide();
        $("#remove-btn").hide();
    } else {
        $("#wImgUrl").show();
        $("#remove-btn").show();
    }
}
 
function dateCalanderInitalization(startDate, endDate) {

    var dateFormat = "DD-MM-YYYY";
    var formattedStartDate = moment(startDate, dateFormat).toDate();
    var formattedEndDate = moment(endDate, dateFormat).toDate();

    function initializeDatePicker(selector, targetInput) {
        $(selector).datetimepicker({
            format: "d-m-Y",
            closeOnDateSelect: true,
            timepicker: false,
            minDate: formattedStartDate,
            maxDate: formattedEndDate,
            onShow: function () {
                this.setOptions({
                    minDate: formattedStartDate,
                    maxDate: formattedEndDate
                });
            },
            onSelectDate: function (ct) {
                var selectedDate = moment(ct).format(dateFormat);
                $(targetInput).val(selectedDate);
                validateDates();
            }
        });

        $(targetInput).blur(function () {
            $(selector).val($(this).val());
        });
    }

    initializeDatePicker("#DateCalendarPlanned", "#startPlannedDate");
    initializeDatePicker("#DateCalendarPlannedEnd", "#endPlannedDate");
    initializeDatePicker("#DateCalendarActPlanned", "#startDateActPlanned");
    initializeDatePicker("#DateCalendarActPlannedEnd", "#endDateActPlanned");

    $("#startPlannedDate, #endPlannedDate, #startDateActPlanned, #endDateActPlanned").attr("readonly", true);
}


function getSetCurrentDate() {
    const actStartDate = $("#startDateActPlanned").val();
    const actEndDate = $("#endDateActPlanned").val();
    const status = $("#dprStatus").val();

    function getFormattedDate() {
        const today = new Date();
        const day = String(today.getDate()).padStart(2, '0');
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const year = String(today.getFullYear());
        return `${day}-${month}-${year}`;
    }

    if (!actStartDate && status == 1) {
        $("#startDateActPlanned").val(getFormattedDate());
        getActualDuration();
    }

    if (!actEndDate && status == 2) {
        $("#endDateActPlanned").val(getFormattedDate());
        getActualDuration();
    }
}

function getActualDuration() {
    const startDate = $('#startDateActPlanned').val();
    const endDateActPlanned = $('#endDateActPlanned').val();

    if (!startDate || !endDateActPlanned) {
        return;
    }

    const startParts = startDate.split('-').map(Number);
    const endParts = endDateActPlanned.split('-').map(Number);

    if (startParts.length !== 3 || endParts.length !== 3 || startParts.some(isNaN) || endParts.some(isNaN)) {
        return;
    }

    const [startDay, startMonth, startYear] = startParts;
    const [endDay, endMonth, endYear] = endParts;

    const start = new Date(startYear, startMonth - 1, startDay);
    const end = new Date(endYear, endMonth - 1, endDay);

    if (isNaN(start) || isNaN(end)) {
        return;
    }

    const diffTime = end - start;

    if (diffTime < 0) {
        return;
    }

    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1;

    const totalActDuration = diffDays * 9;

    $("#actualPlannedHours").val(totalActDuration).prop("disabled", true);
}

let base64StringGlobal = '';
let originlFileName = "";

function uploadImage(event) {
    const file = event.target.files[0];
    if (file) {
        $("#wImgUrl").show();
        $("#remove-btn").show();
        $("#viewIngSectionId").show();
        $("#imageViewParent").hide();
        originlFileName = file.name;
        const reader = new FileReader();

        reader.onload = function (e) {
            const base64String = e.target.result;
            base64StringGlobal = base64String;

            const img = document.getElementById("wImgUrl");
            img.src = base64String;
            let u = "openInNextTab('" + base64String + "')";
            $("#wImgUrl").attr('onclick', u);
            $("#viewIngSectionId").show();
        };

        reader.readAsDataURL(file);
    }
}

function openInNextTab(url) {
    if (isBase64Image(url)) {
        if (url) {
            const newTab = window.open();
            newTab.document.write(`<img src="${url}">`);
        }
    } else {
        window.open(url);
    }
}

function isBase64Image(str) {
    const base64Regex = /^data:image\/(png|jpeg|jpg|gif|webp|bmp);base64,[A-Za-z0-9+/=]+$/;
    return base64Regex.test(str);
}

function removeUrl() {

    Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want to remove this?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Yes, remove it!',
        cancelButtonText: 'No, keep it',
        confirmButtonColor: 'var(--mainColor)',
    }).then((result) => {
        if (result?.value) {
            let imgUrl = $("#wImg").val();
            if (imgUrl) {
                var id = $('#wId').val();

                $.ajax({
                    type: "GET",
                    url: "/configuration/delete-master-data?id=" + id + "&type=WHIMG&imgurl=" + imgUrl,
                    dataType: "json",
                    contentType: "application/json",
                    success: function (response) {
                        if (response.code == "success") {
                            toastr.success('Image removed successfully')
                            $("#wImgUrl").attr('src', '');
                            $("#wImgUrl").removeAttr('onclick');
                            $('#fileUpload').val('');
                            $('#wImg').val('');
                            $("#viewIngSectionId").hide();
                            base64StringGlobal = '';
                            /*$("#viewIngSectionId").hide();
                            $("#imageViewParent").show();*/
                        } else {
                            toastr.error('Something went wrong')
                            /*$("#viewIngSectionId").show();
                            $("#imageViewParent").hide();*/
                        }
                    },
                    error: function (response) {
                        toastr.error('Something went wrong')
                    }
                })
            } else {
                $("#wImgUrl").attr('src', '');
                $("#wImgUrl").removeAttr('onclick');
                $('#fileUpload').val('');
                $('#wImg').val('');
                $("#viewIngSectionId").hide();
                base64StringGlobal = '';
            }
        }
    })
}


function saveDpr() {
    var DprModel = {};
    DprModel.projectId = projectId;
    DprModel.drpId = $("#dprId").val();
    DprModel.taskId = $("#taskIdDpr").val();
    DprModel.drpStatus = $("#dprStatus").val();
    DprModel.startPlannedDate = $("#startPlannedDate").val();
    DprModel.endPlannedDate = $("#endPlannedDate").val();
    DprModel.plannedHours = $("#plannedHours").val();
    DprModel.startDateActPlanned = $("#startDateActPlanned").val();
    DprModel.endDateActPlanned = $("#endDateActPlanned").val();
    DprModel.actualPlannedHours = $("#actualPlannedHours").val();
    DprModel.notesMain = $("#notesDpr").val();
    DprModel.wImg = $("#wImg").val();
    DprModel.imgName = originlFileName;
    DprModel.fileupload = base64StringGlobal;

    if ($("#dprStatus").val() == 0 || $("#dprStatus").val() == null) {
        toastr.error("Please Change Project  Status");
        return;
    }


    $.ajax({
        type: "POST",
        url: "save-dpr",
        dataType: "Json",
        contentType: "application/json",
        data: JSON.stringify(DprModel),
        success: function (response) {
            if (response.code == "success") {
                toastr.success(response.message)
                getDprData();
                $("#editDpr").prop("disabled", true);
                $("#deleteDpr").prop("disabled", true);
                $("#addDpr").prop("disabled", false);
            } else {
                toastr.error(response.message)
            }
        },
        error: function (response) {
            toastr.error("Something went wrong")
        }
    })
}


var columnDefsDpr = [
    {
        headerCheckboxSelection: false,
        headerCheckboxSelectionFilteredOnly: true,
        checkboxSelection: true,
        width: 10,
        sortable: false,
        filter: false,
        resizable: true
    },
    {
        headerName: "Drp Id",
        field: "reportId",
        hide: "true"

    },
    {
        headerName: "Task Id",
        field: "taskId",

    },
    {
        headerName: "Start Date",
        field: "startDate"
    },
    {
        headerName: "End Date",
        field: "endDate",
    },
    {
        headerName: "Planned Hours",
        field: "palnnedHours",
    },
    {
        headerName: "Actual Start Date",
        field: "actStartDate"
    }, {
        headerName: "Actual End Date",
        field: "actEndDate"
    }, {
        headerName: "Actual Hours",
        field: "actHours"
    }, {
        headerName: "Notes",
        field: "notes"
    }, {
        headerName: "Status",
        field: "status",
        cellRenderer: (params) => {
            switch (params.value) {
                case '0':
                    return "To Do";
                case '1':
                    return "In Progress";
                case '2':
                    return "Completed";
                default:
                    return "Unknown";
            }
        }
    }, {
        headerName: "Created On",
        field: "createdOn",
    }, {
        headerName: "Document",
        field: "docDtls",
        cellRenderer: (params) => {
            if (!params.value) return "";

            return `
      <a href="${params.value}" target="_blank" style="text-decoration: none;">
        <img src="https://img.icons8.com/ios-filled/50/BF05FF/picture.png" 
             alt="Image" 
             style="width: 24px; height: 24px; cursor: pointer;" />
      </a>`;
        }
    },
    {
        headerName: "Documnet Name",
        field: "imgName",
        hide: "true"
    }];

// Define grid options
var dprGridOptions = {
    columnDefs: columnDefsDpr,
    rowSelection: 'single',
    suppressRowClickSelection: true,
    defaultColDef: {
        sortable: true,
        filter: true,
        resizable: true,
        width: 100
    },
    pagination: true,
    paginationPageSize: 15,

    onSelectionChanged: rowSelectDpr
};

function addDpr() {

    const taskId = $("#taskIdDpr").val();
    $("#dprGrid").hide();
    $("#openDprForm").show();
    $("#addDpr").hide();
    $("#editDpr").hide();
    $("#deleteDpr").hide();
    $("#cancelDpr").show();
    $("#saveDpr").show();
    $("#cancel1").hide();
    $("#imageViewParent").hide();
    $("#saveDpr").prop("disabled", false);
    clearfieldsVal();

    agGrid.simpleHttpRequest({
        url: "view-all-task-details-edit?id=" + taskId
    }).then(function (response) {
        if (response.message === "Success") {

            const responseBody = JSON.parse(response.body[0]);
            const taskDetails = responseBody.taskDetails[0];
            $("#plannedHours").val(taskDetails.plannedHours).prop("disabled", true);
            $("#startPlannedDate").val(taskDetails.startDate);
            $("#endPlannedDate").val(taskDetails.endDate);
            $("#startDateActPlanned").val(taskDetails.actualStartDate);
            console.log("drp status==========>", taskDetails.status);
            if (taskDetails.status == 2) {
                $("#saveDpr").attr("disabled", true);
            } else {
                $("#saveDpr").attr("disabled", false)
            }
            let statusDropdown = $("#dprStatus");
            let currentStatus = parseInt(taskDetails.status);
            statusDropdown.val(currentStatus);
            statusDropdown.find("option").prop("disabled", true);
            statusDropdown.find(`option[value='${currentStatus}']`).prop("disabled", false);
            let nextStatus = currentStatus + 1;
            if (statusDropdown.find(`option[value='${nextStatus}']`).length > 0) {
                statusDropdown.find(`option[value='${nextStatus}']`).prop("disabled", false);
            }
            statusDropdown.trigger("change");

        }


        getSetCurrentDate();
    });


}


function cancelDpr() {
    $("#openDprForm").hide();
    $("#dprGrid").show();

    $("#addDpr").show();
    $("#editDpr").show();
    $("#deleteDpr").show();
    $("#cancelDpr").hide();
    $("#saveDpr").hide();
    $("#cancel1").show();


}

function getDprData() {
    const taskId = $("#taskIdDpr").val();
    agGrid.simpleHttpRequest({
        url: "view-all-task-dpr?id=" + taskId
    }).then(function (response) {
        if (response.message === "Success") {
            cancelDpr();
            const responseBody = JSON.parse(response.body);
            const dprDetails = responseBody.dprDetails;
            dprGridOptions.api.setRowData(dprDetails);

            $("#editDpr").prop("disabled", true);
            $("#deleteDpr").prop("disabled", true);
            $("#addDpr").prop("disabled", false);

        }
    })
}

function rowSelectDpr() {

    var selectedRows = dprGridOptions.api.getSelectedRows();
    if (selectedRows.length > 0) {
        $("#editDpr").prop("disabled", false);
        $("#deleteDpr").prop("disabled", false);
        $("#addDpr").prop("disabled", true);
    } else {
        $("#editDpr").prop("disabled", true);
        $("#deleteDpr").prop("disabled", true);
        $("#addDpr").prop("disabled", false);
    }
}


function editDprDetails() {
    $("#dprGrid").hide();
    $("#openDprForm").show();
    $("#addDpr").hide();
    $("#editDpr").hide();
    $("#deleteDpr").hide();
    $("#cancelDpr").show();
    $("#saveDpr").show();
    $("#dprGrid").hide();
    $("#openDprForm").show();
    $("#addDpr").hide();
    $("#editDpr").hide();
    $("#deleteDpr").hide();
    $("#cancelDpr").show();
    $("#saveDpr").show();
    $("#cancel1").hide();
    clearfieldsVal();

    var selectedRows = dprGridOptions.api.getSelectedRows();


    if (selectedRows.length > 0) {
        const datas = selectedRows[0];
        $("#dprId").val(datas.reportId);
        $("#taskIdDpr").val(datas.taskId);
        $("#startPlannedDate").val(datas.startDate);
        $("#endPlannedDate").val(datas.endDate);
        $("#plannedHours").val(datas.palnnedHours).prop("disabeld", true);
        ;
        $("#startDateActPlanned").val(datas.actStartDate);
        $("#endDateActPlanned").val(datas.actEndDate);
        $("#actualPlannedHours").val(datas.actHours).prop("disabeld", true);
        console.log("-->", datas.imgName)
        $("#notesDpr").val(datas.notes);
        if (datas.docDtls != "") {
            $("#showImageLink").attr("href", datas.docDtls);
            $("#viewIngSectionId").hide();
            $("#imageViewParent").show();
            $("#remove-btn").show();

        } else {
            $("#showImageLink").attr("href", "");
            $("#viewIngSectionId").show();
            $("#imageViewParent").hide();
            $("#remove-btn").show();
        }

        let statusDropdown = $("#dprStatus");
        let currentStatus = parseInt(datas.status);
        statusDropdown.val(currentStatus);
        statusDropdown.find("option").prop("disabled", true);
        statusDropdown.find(`option[value='${currentStatus}']`).prop("disabled", false);
        let nextStatus = currentStatus + 1;
        if (statusDropdown.find(`option[value='${nextStatus}']`).length > 0) {
            statusDropdown.find(`option[value='${nextStatus}']`).prop("disabled", false);
        }
        statusDropdown.trigger("change");
    } else {
        $("#editDpr").prop("disabled", true);
        $("#deleteDpr").prop("disabled", true);
        $("#addDpr").prop("disabled", false);

    }

    const selectedRowData = selectedRows[0];
    const startDate = selectedRowData.actStartDate;
    const date = selectedRowData.actEndDate;
    const status = selectedRowData.status;
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const year = today.getFullYear();
    const currentDate = `${day}-${month}-${year}`;

    if (date != currentDate && status == 2) {
        $("#saveDpr").prop("disabled", true);
    } else if (startDate != currentDate && status == 1) {
        $("#saveDpr").prop("disabled", true);
    } else {
        $("#saveDpr").prop("disabled", false);
    }


}