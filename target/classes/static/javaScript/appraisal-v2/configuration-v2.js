$(() => {
    const appraisalColumnDefs = [
        { headerCheckboxSelection: true, checkboxSelection: true, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },
        { headerName: "Employee ID", field: "employee_id" },
        { headerName: "Name", field: "employee_name" },
        { headerName: "Designation", field: "designation" },
        { headerName: "Band", field: "band" },
        { headerName: "Email ID", field: "email_id" }
    ];

    const appraisalGridOptions = {
        columnDefs: appraisalColumnDefs,
        defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
        rowSelection: 'single',
        pagination: true,
        paginationAutoPageSize: true,
        rowData: [],
        onSelectionChanged: autoAddGoalForSelectedEmployee // Automatically add a goal
    };

    const appraisalGridDiv = $("#appraisalGrid")[0];
    new agGrid.Grid(appraisalGridDiv, appraisalGridOptions);

    const fetchAppraisalData = () => {
        $.ajax({
            url: 'https://faux-api.com/api/v1/getallemployeeappraisals_7363648660627282',
            method: 'GET',
            dataType: 'json',
            success: (data) => {
                if (data.status === "success" && Array.isArray(data.result)) {
                    appraisalGridOptions.api.setRowData(data.result);
                    setTimeout(() => {
                        const firstNode = appraisalGridOptions.api.getDisplayedRowAtIndex(0);
                        if (firstNode) {
                            appraisalGridOptions.api.selectNode(firstNode, true);
                        }
                    }, 100);
                } else {
                    console.error("Unexpected response format:", data);
                }
            },
            error: (jqXHR, textStatus, errorThrown) =>
                console.error(`Error: ${textStatus}, ${errorThrown}`)
        });
    };

    fetchAppraisalData();

    const addGoalColumnDefs = [
        { headerCheckboxSelection: true, checkboxSelection: true, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },
        { headerName: "Goal ID", field: "goal_id", editable: false },
        { headerName: "Name", field: "employee_name", editable: false },
        { headerName: "KRA", field: "kra", editable: true },
        { headerName: "KPI", field: "kpi", editable: true },
        { headerName: "Weightage", field: "weightage", editable: true },
        { headerName: "From Date", field: "from_date", editable: true },
        { headerName: "To Date", field: "to_date", editable: true }
    ];

    const addGoalGridOptions = {
        columnDefs: addGoalColumnDefs,
        defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
        rowSelection: 'single',
        pagination: true,
        paginationAutoPageSize: true,
        rowData: []
    };

    const addGoalGridDiv = $("#addGoalsGrid")[0];
    new agGrid.Grid(addGoalGridDiv, addGoalGridOptions);

    function getTodayDate() {
        let today = new Date();
        return today.toISOString().split('T')[0];
    }

    function autoAddGoalForSelectedEmployee() {
        const selectedNode = appraisalGridOptions.api.getSelectedNodes()[0];

        if (!selectedNode) {
            console.warn("No employee selected.");
            return;
        }

        const selectedEmployee = selectedNode.data;

        // Clear previous goals before adding a new one
        addGoalGridOptions.api.setRowData([]);

        const newGoal = {
            goal_id: `G000002025_${addGoalGridOptions.api.getDisplayedRowCount() + 1}`,
            employee_name: selectedEmployee.employee_name,
            kra: "",
            kpi: "",
            weightage: "",
            from_date: getTodayDate(),
            to_date: getTodayDate()
        };

        addGoalGridOptions.api.applyTransaction({ add: [newGoal] });
    }

    $("#saveGoalsButton").click(() => {
        let goalData = [];
        addGoalGridOptions.api.forEachNode(node => goalData.push(node.data));

        if (goalData.length === 0) {
            alert("No goals to save.");
            return;
        }

        console.log("Sending Data:", JSON.stringify(goalData)); // ✅ Log payload

        $.ajax({
            url: 'https://faux-api.com/api/v1/getgoalsforemployee_7363648660627282',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(goalData),
            success: (response) => {
                console.log("API Response:", response); // ✅ Log API response

                if (response.status === "success") {
                    alert("Goals saved successfully!");
                } else {
                    alert("Failed to save goals. API responded with an error.");
                }
            },
            error: (jqXHR, textStatus, errorThrown) => {
                console.error("Save Error:", textStatus, errorThrown, jqXHR.responseText);
                alert(`An error occurred: ${jqXHR.responseText || textStatus}`);
            }
        });
    });


    /* Star rating */

    let selectedRatings = {}; // Store ratings for each row

    // Event delegation for dynamically added stars
    $("#goalContainer").on("mouseover", ".star", function () {
        let rating = $(this).data("value");
        let row = $(this).closest(".row").attr("id"); // Get the unique row ID
        highlightStars(row, rating);
    });

    $("#goalContainer").on("click", ".star", function () {
        let rating = $(this).data("value");
        let row = $(this).closest(".row").attr("id"); // Get the unique row ID
        selectedRatings[row] = rating; // Store selected rating for the row
        $(this).closest(".row").find(".rating-text").text(`You rated ${rating} stars`);
    });

    $("#goalContainer").on("mouseout", ".star", function () {
        let row = $(this).closest(".row").attr("id");
        highlightStars(row, selectedRatings[row] || 0); // Restore selected rating
    });

    function highlightStars(row, rating) {
        $(`#${row} .star`).each(function () {
            $(this).toggleClass("checked", $(this).data("value") <= rating);
        });
    }

    /* Star rating */


    /* Form layout */

    let goalCount = 1; // Unique ID counter

    function addGoalRow() {
        let uniqueId = `addGoal${goalCount}`;
        let rowHtml = `
            <div class="col">
                    <label class="form-label visually-hidden" for="${uniqueId}"></label>
                    <input type="text" class="form-control" id="${uniqueId}" placeholder="Add Goal...">
                </div>
                <div class="col align-content-center text-center">
                    <input class="form-check-input" type="checkbox" id="checkbox${goalCount}" value="" aria-label="...">
                </div>
                <div class="col text-center">
                    <span class="star" data-value="1">&#9733;</span>
                    <span class="star" data-value="2">&#9733;</span>
                    <span class="star" data-value="3">&#9733;</span>
                    <span class="star" data-value="4">&#9733;</span>
                    <span class="star" data-value="5">&#9733;</span>
                </div>
        `;

        $("#goalContainer").append(rowHtml);
        goalCount++;
    }

    // Add initial goal row on page load
    addGoalRow();

    // Event listener for "Add More" button
    $("#addMoreBtn").click(function () {
        addGoalRow();
    });

    /* Form layout */
});