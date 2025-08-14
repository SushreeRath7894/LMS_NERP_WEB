$(() => {
    new agGrid.Grid(document.getElementById('gridEHR1'), gridOptionsEHR1);
    new agGrid.Grid(document.getElementById('gridEHR2'), gridOptionsEHR2);
    new agGrid.Grid(document.getElementById('gridVitals'), gridOptionsVitals);
    new agGrid.Grid(document.getElementById('gridAssessment'), gridOptionsVitals);

    let fromDateVitals = $('#vitals--fromDate');
    let toDateVitals = $('#vitals--toDate');
    let fromDateAssessment = $('#assessment--fromDate');
    let toDateAssessment = $('#assessment--toDate');

    fromDateVitals.datetimepicker({
        timepicker: false,
        format: 'd-M-Y', // Example: 01-Mar-2025
        scrollMonth: false,
        scrollInput: false
    });
    $('#vitals--fromDate-picker').on('click', () => fromDateVitals.datetimepicker('toggle'));

    toDateVitals.datetimepicker({
        timepicker: false,
        format: 'd-M-Y', // Example: 01-Mar-2025
        scrollMonth: false,
        scrollInput: false
    });
    $('#vitals--toDate-picker').on('click', () => toDateVitals.datetimepicker('toggle'));

    fromDateAssessment.datetimepicker({
        timepicker: false,
        format: 'd-M-Y', // Example: 01-Mar-2025
        scrollMonth: false,
        scrollInput: false
    });
    $('#assessment--fromDate-picker').on('click', () => fromDateAssessment.datetimepicker('toggle'));

    toDateAssessment.datetimepicker({
        timepicker: false,
        format: 'd-M-Y', // Example: 01-Mar-2025
        scrollMonth: false,
        scrollInput: false
    });
    $('#assessment--toDate-picker').on('click', () => toDateAssessment.datetimepicker('toggle'));

    new agGrid.Grid(document.querySelector('#gridPhysicalActivity'), gridOptionsPhysicalActivity);
    new agGrid.Grid(document.querySelector('#gridActivities1'), gridOptionsActivities1);
    new agGrid.Grid(document.querySelector('#gridActivities2'), gridOptionsActivities2);

});

let toNext = () => {
    // Find the currently active tab
    const $current = $('.myCareTabs .nav-link.active');

    // Get the parent <li> of the current tab
    const $currentLi = $current.closest('li');

    // Find the next <li> (next tab item)
    const $nextLi = $currentLi.next();

    // If next tab exists, trigger Bootstrap tab activation
    if ($nextLi.length) {
        const $nextLink = $nextLi.find('a[data-bs-toggle="pill"]');

        // Trigger Bootstrap's tab show programmatically
        const tab = new bootstrap.Tab($nextLink[0]);
        tab.show();

        // Optional: manually trigger your activityTabs function
        // const href = $nextLink.attr('href'); // e.g., "#tabSchedule"
        // activityTabs(href.substring(1)); // removes "#" before passing
    }
};

let toPrev = () => {
    const $current = $('.myCareTabs .nav-link.active');
    const $currentLi = $current.closest('li');
    const $prevLi = $currentLi.prev();

    if ($prevLi.length) {
        const $prevLink = $prevLi.find('a[data-bs-toggle="pill"]');

        const tab = new bootstrap.Tab($prevLink[0]);
        tab.show();

        // const href = $prevLink.attr('href'); // e.g., "#tabSchedule"
        // activityTabs(href.substring(1));
    }
}

let activityTabs = (x) => {
};

const columnDefsEHR1 = [
    {
        headerName: "",
        field: "checkbox",
        checkboxSelection: true,
        suppressHeaderCheckboxSelection: true,
        minWidth: 35,
        width: 35,
        pinned: "left",
        suppressSizeToFit: true,
        suppressMenu: true,
        sortable: false,
        filter: false,
        resizable: false
    },
    {headerName: "Package ID", field: "packageId", sortable: true, filter: true},
    {headerName: "Package Name", field: "packageName", sortable: true, filter: true},
    {headerName: "Start Date", field: "startDate", sortable: true, filter: true},
    {headerName: "End Date", field: "endDate", sortable: true, filter: true},
    {headerName: "Renewal Date", field: "renewalDate", sortable: true, filter: true},
    {headerName: "Status", field: "status", sortable: true, filter: true}
];

const rowDataEHR1 = [
    {packageId: "CTR0002", packageName: "RPM", startDate: "01-Mar-25", endDate: "31-Mar-25", renewalDate: "01-Apr-25", status: "Renew"},
    {packageId: "CTR0001", packageName: "Health Check-up", startDate: "01-Mar-25", endDate: "28-Feb-26", renewalDate: "01-Mar-26", status: "Active"}
];

let selectedAssetIndexEHR1 = null;

const gridOptionsEHR1 = {
    columnDefs: columnDefsEHR1,
    rowData: rowDataEHR1,
    rowSelection: "single",
    suppressRowClickSelection: false,
    pagination: true,
    paginationAutoPageSize: true,
    defaultColDef: {
        flex: 1,
        minWidth: 100,
        resizable: true
    },
    onRowClicked: function (event) {
        gridOptionsEHR1.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexEHR1 = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexEHR1 = 0;
            }
        });
    }
};

const columnDefsEHR2 = [
    {headerName: "Package Name", field: "packageName", minWidth: 200},
    {headerName: "Scheme", field: "scheme", width: 150},
    {headerName: "Effective Date", field: "effectiveDate", width: 150},
    {
        headerName: "Price",
        field: "price",
        width: 100,
        type: 'numericColumn',
        valueFormatter: currencyFormatterEHR2,
        cellClass: 'ag-right-aligned-cell',
        headerClass: 'custom-class-1'
    },
    {
        headerName: "Tax",
        field: "tax",
        width: 100,
        type: 'numericColumn',
        valueFormatter: currencyFormatterEHR2,
        cellClass: 'ag-right-aligned-cell',
        headerClass: 'custom-class-1'
    },
    {
        headerName: "Total",
        field: "total",
        width: 100,
        type: 'numericColumn',
        valueFormatter: currencyFormatterEHR2,
        cellClass: 'ag-right-aligned-cell',
        headerClass: 'custom-class-1'
    }
];

const rowDataEHR2 = [
    {packageName: "RPM", scheme: "Six Months", effectiveDate: "01-Mar-25", price: 6000.00, tax: 360.00, total: 6360.00}
];

const pinnedBottomRowDataEHR2 = [
    {tax: 'Discount', total: 60.00},
    {tax: 'Net Payable', total: 6300.00},
    {packageName: 'Six Thousand Three Hundred Only.'}
];

const gridOptionsEHR2 = {
    columnDefs: columnDefsEHR2,
    rowData: rowDataEHR2,
    pinnedBottomRowData: pinnedBottomRowDataEHR2,
    suppressHorizontalScroll: true,
    defaultColDef: {
        flex: 1,
        resizable: true
    },
    getRowStyle: function (params) {
        if (params.node.rowPinned) {
            return {
                fontWeight: params.data.tax === 'Net Payable' ? 'bold' : 'normal'
            };
        }
        return null;
    }
};

function currencyFormatterEHR2(params) {
    if (typeof params.value === 'number') {
        return '₹' + params.value.toLocaleString('en-IN', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
    }
    return params.value || '';
}


const columnDefsVitals = [
    {headerName: '', checkboxSelection: true, headerCheckboxSelection: false, maxWidth: 35, width: 35, suppressSizeToFit: true, pinned: 'left'},
    {headerName: "Date", field: "date", width: 120},
    {headerName: "Time", field: "time", width: 100},
    {headerName: "Activity", field: "activity", width: 120},
    {headerName: "Value", field: "value", width: 100, type: 'numericColumn', headerClass: 'custom-class-1'},
    {headerName: "", field: "unit", width: 100},
    {headerName: "Assessment", field: "assessment", cellRenderer: assessmentBadgeRendererVitals, width: 150},
    {headerName: "Suggestion", field: "suggestion", width: 150}
];

const rowDataVitals = [
    {date: "01-Mar-2025", time: "8:00 AM", activity: "Pulse", value: 60, unit: "beats/min", assessment: "Critical (Low)", suggestion: ""},
    {date: "01-Mar-2025", time: "1:00 PM", activity: "Pulse", value: 76, unit: "beats/min", assessment: "Abnormal (Low)", suggestion: ""},
    {date: "01-Mar-2025", time: "8:00 PM", activity: "Pulse", value: 97, unit: "beats/min", assessment: "Normal", suggestion: ""}
];

let selectedAssetIndexVitals = null;

const gridOptionsVitals = {
    columnDefs: columnDefsVitals,
    rowData: rowDataVitals,
    suppressHorizontalScroll: true,
    rowSelection: 'single',
    suppressRowClickSelection: false,
    defaultColDef: {
        flex: 1,
        resizable: true
    },
    pagination: true,
    paginationAutoPageSize: true,
    onRowClicked: function (event) {
        gridOptionsVitals.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexVitals = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexVitals = 0;
            }
        });
    }
};

function assessmentBadgeRendererVitals(params) {
    const value = params.value || '';
    let color = '';

    if (value.includes('Critical')) {
        color = 'bg-danger'; // red
    } else if (value.includes('Abnormal')) {
        color = 'bg-warning text-dark'; // yellow
    } else if (value.includes('Normal')) {
        color = 'bg-success'; // green
    }

    return `<span class="badge rounded-pill ${color}">${value}</span>`;
}


// Define column headers (1–5 with labels)
const columnDefsPhysicalActivity = [
    {
        headerName: "Physical Activity",
        field: "question",
        cellStyle: {textAlign: 'left', whiteSpace: 'normal'}
    },
    ...[1, 2, 3, 4, 5].map(function (val) {
        const labels = ["Never", "Almost never", "Occasionally", "Almost always", "Always"];
        return {
            headerName: '',
            field: "col" + val,
            headerClass: 'custom-class-2',
            headerComponentParams: {
                template:
                    `<span style="white-space: normal; font-weight: 600; color: #000000; font-size: 12px;">${val}<br>${labels[val - 1]}</span>`
            },
            cellRenderer: function (params) {
                const rowIndex = params.node.rowIndex;
                const groupName = "q" + rowIndex;
                const isChecked = params.value === true ? "checked" : "";
                return `<input type="radio" class="form-check-input" name="${groupName}" data-row="${rowIndex}" data-col="col${val}" ${isChecked}>`;
            }
        };
    })
];

// Define row data (the questions)
const rowDataPhysicalActivity = [
    {
        question: "I engage in moderate physical activity outside of work for at least 20 to 30 minutes five days a week.",
        col1: true // default selection
    },
    {
        question: "My physical activity includes stretching, aerobic activity, and strength conditioning.",
        col1: true // default selection
    }
];

// Grid options
const gridOptionsPhysicalActivity = {
    columnDefs: columnDefsPhysicalActivity,
    rowData: rowDataPhysicalActivity,
    suppressHorizontalScroll: true,
    rowHeight: 90,
    defaultColDef: {
        flex: 1,
        minWidth: 50,
        resizable: true,
        cellStyle: {textAlign: 'center', display: 'inline-flex', alignItems: 'center', justifyContent: 'center'}
    },
    onGridReady: function () {
        // Attach event listener to radio buttons once grid is rendered
        $('#gridPhysicalActivity').on('change', 'input[type="radio"]', function () {
            const rowIndex = $(this).data('row');
            const colField = $(this).data('col');

            // Reset all options in the row
            for (let i = 1; i <= 5; i++) {
                rowDataPhysicalActivity[rowIndex]['col' + i] = false;
            }

            // Set selected option
            rowDataPhysicalActivity[rowIndex][colField] = true;

            // Refresh cells for this row
            gridOptionsPhysicalActivity.api.refreshCells({
                rowNodes: [gridOptionsPhysicalActivity.api.getDisplayedRowAtIndex(rowIndex)],
                force: true
            });
        });
    }
};

const columnDefsActivities1 = [
    {headerName: '', checkboxSelection: true, headerCheckboxSelection: false, maxWidth: 35, width: 35, suppressSizeToFit: true, pinned: 'left'},
    {headerName: "Date", field: "date"},
    {headerName: "Step Counts", field: "stepCounts"},
    {headerName: "Prescribed", field: "prescribed"},
    {headerName: "Remarks", field: "remarks"},
    {headerName: "Action", field: "action"}
];

const rowDataActivities1 = [
    {date: "12-Mar", stepCounts: "100", prescribed: "200", remarks: "Not meeting the requirements", action: ""},
    {date: "13-Mar", stepCounts: "100", prescribed: "", remarks: "", action: ""},
    {date: "14-Mar", stepCounts: "100", prescribed: "", remarks: "", action: ""},
    {date: "15-Mar", stepCounts: "100", prescribed: "", remarks: "", action: ""},
    {date: "16-Mar", stepCounts: "100", prescribed: "", remarks: "", action: ""},
];

let selectedAssetIndexActivities1 = null;

const gridOptionsActivities1 = {
    columnDefs: columnDefsActivities1,
    rowData: rowDataActivities1,
    suppressHorizontalScroll: true,
    rowSelection: 'single',
    suppressRowClickSelection: false,
    defaultColDef: {
        flex: 1,
        minWidth: 150,
        resizable: true
    },
    pagination: true,
    paginationAutoPageSize: true,
    onRowClicked: function (event) {
        gridOptionsActivities1.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexActivities1 = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexActivities1 = 0;
            }
        });
    }
};

const columnDefsActivities2 = [
    {headerName: '', checkboxSelection: true, headerCheckboxSelection: false, maxWidth: 35, width: 35, suppressSizeToFit: true, pinned: 'left'},
    {headerName: "Alert ID", field: "alertId"},
    {headerName: "Purpose", field: "purpose"},
    {headerName: "Status", field: "status"},
    {headerName: "Since Time", field: "sinceTime"},
    {headerName: "Remarks", field: "remarks"},
    {headerName: "Action", field: "action"}
];

const rowDataActivities2 = [
    {alertId: "A0001", purpose: "High BP", status: "Inactive", sinceTime: "11-Mar-2025", remarks: "", action: ""},
];

let selectedAssetIndexActivities2 = null;

const gridOptionsActivities2 = {
    columnDefs: columnDefsActivities2,
    rowData: rowDataActivities2,
    suppressHorizontalScroll: true,
    rowSelection: 'single',
    suppressRowClickSelection: false,
    defaultColDef: {
        flex: 1,
        minWidth: 150,
        resizable: true
    },
    pagination: true,
    paginationAutoPageSize: true,
    onRowClicked: function (event) {
        gridOptionsActivities2.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexActivities2 = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexActivities2 = 0;
            }
        });
    }
};