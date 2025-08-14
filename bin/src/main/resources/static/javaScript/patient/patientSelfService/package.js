$(() => {
    new agGrid.Grid(document.getElementById('gridPackage'), gridOptionsPackage);

    let packageLocation = $('#package--location')[0];
    let packageState = $('#package--state')[0];
    let packageCountry = $('#package--country')[0];
    let packageActivities = $('#package--activities')[0];

    new SlimSelect({select: packageLocation});
    new SlimSelect({select: packageState});
    new SlimSelect({select: packageCountry});
    new SlimSelect({select: packageActivities});

    new agGrid.Grid(document.getElementById('gridSchedule'), gridOptionsSchedule);
    new agGrid.Grid(document.getElementById('gridAssets'), gridOptionsAssets);
    new agGrid.Grid(document.getElementById('gridRenewal1'), gridOptionsRenewal1);
    new agGrid.Grid(document.getElementById('gridRenewal2'), gridOptionsRenewal2);
});

let toNext = () => {
    // Find the currently active tab
    const $current = $('.packageTabs .nav-link.active');

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
    const $current = $('.packageTabs .nav-link.active');
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

const columnDefsPackage = [
    {
        headerName: "",
        field: "checkbox",
        checkboxSelection: true,
        suppressHeaderCheckboxSelection: true, // ❗ disables "select all"
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
    {headerName: "Valid Till", field: "validTill", sortable: true, filter: true},
    {headerName: "Price", field: "price", sortable: true, filter: true},
    {headerName: "Status", field: "status", sortable: true, filter: true}
];

const rowDataPackage = [];
for (let i = 0; i < 25; i++) {
    const paddedId = String(i + 1).padStart(4, '0');
    rowDataPackage.push({
        packageId: `CTR${paddedId}`,
        packageName: "Health Check up",
        startDate: "01-Mar-25",
        validTill: "28-Feb-26",
        price: "1000.00",
        status: "Active"
    });
}

let selectedRowIndexPackage = null;

const gridOptionsPackage = {
    columnDefs: columnDefsPackage,
    rowData: rowDataPackage,
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
        gridOptionsPackage.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedRowIndexPackage = event.rowIndex;
    },
    onGridReady: function (params) {
        // ✅ Select first row on load
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedRowIndexPackage = 0;
            }
        });
    }
};


let rowDataSchedule = [
    {activity: 'Pulse', frequency: 'Daily', parameter: '1', uom: 'No', fromTime: '8:00 AM', toTime: '9:00 AM', remarks: 'Collect data as per specified time'},
    {activity: 'Pulse', frequency: 'Daily', parameter: '1', uom: 'No', fromTime: '1:00 PM', toTime: '2:00 PM', remarks: 'Collect data as per specified time'},
    {activity: 'Pulse', frequency: 'Daily', parameter: '1', uom: 'No', fromTime: '1:00 PM', toTime: '2:00 PM', remarks: 'Collect data as per specified time'},
    {activity: 'Walk', frequency: 'Daily', parameter: '30', uom: 'Min', fromTime: '6:00 AM', toTime: '5:00 PM', remarks: 'Walk for 30 minutes'},
    {activity: 'Assessments', frequency: 'Daily', parameter: 'NA', uom: 'NA', fromTime: '12:01 AM', toTime: '12:00 PM', remarks: 'Medical Adherence'}
];

let columnDefsSchedule = [
    {
        headerName: '',
        field: 'checkboxStart',
        minWidth: 35,
        width: 35,
        maxWidth: 35,
        checkboxSelection: true,
        suppressHeaderCheckboxSelection: true,
        suppressSizeToFit: true
    },
    {headerName: 'Activity', field: 'activity'},
    {headerName: 'Frequency', field: 'frequency'},
    {headerName: 'Parameter', field: 'parameter'},
    {headerName: 'UoM', field: 'uom'},
    {headerName: 'From Time', field: 'fromTime'},
    {headerName: 'To Time', field: 'toTime'},
    {headerName: 'Remarks', field: 'remarks'},
];

let selectedRowIndexSchedule = null;

let gridOptionsSchedule = {
    columnDefs: columnDefsSchedule,
    rowData: rowDataSchedule,
    rowSelection: 'single',
    suppressRowClickSelection: false,
    pagination: true,
    paginationAutoPageSize: true,
    defaultColDef: {
        flex: 1,
        minWidth: 100,
        resizable: true
    },
    onRowClicked: function (event) {
        gridOptionsSchedule.api.forEachNode(function (node) {
            node.setSelected(false);
        });
        event.node.setSelected(true);
        selectedRowIndexSchedule = event.rowIndex;
    },
    onGridReady: function (params) {
        // ✅ Select first row when grid is ready
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedRowIndexSchedule = 0;
            }
        });
    }
};

const columnDefsAssets = [
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
    {headerName: "Asset No", field: "assetNo", sortable: true, filter: true},
    {headerName: "Asset Name", field: "assetName", sortable: true, filter: true},
    {headerName: "Make/Brand", field: "brand", sortable: true, filter: true},
    {headerName: "Issue Date", field: "issueDate", sortable: true, filter: true},
    {headerName: "Status", field: "status", sortable: true, filter: true}
];

const rowDataAssets = [
    {
        assetNo: "AM001",
        assetName: "Cardio Ring",
        brand: "CareTechR",
        issueDate: "01-Mar-25",
        status: "Active"
    },
    {
        assetNo: "AM002",
        assetName: "RHM",
        brand: "Remote Health Monitor",
        issueDate: "01-Mar-25",
        status: "Active"
    }
];

let selectedAssetIndexAssets = null;

const gridOptionsAssets = {
    columnDefs: columnDefsAssets,
    rowData: rowDataAssets,
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
        gridOptionsAssets.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexAssets = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexAssets = 0;
            }
        });
    }
};

const columnDefsRenewal1 = [
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

const rowDataRenewal1 = [
    {packageId: "CTR0002", packageName: "RPM", startDate: "01-Mar-25", endDate: "31-Mar-25", renewalDate: "01-Apr-25", status: "Renew"},
    {packageId: "CTR0001", packageName: "Health Check-up", startDate: "01-Mar-25", endDate: "28-Feb-26", renewalDate: "01-Mar-26", status: "Active"}
];

let selectedAssetIndexRenewal1 = null;

const gridOptionsRenewal1 = {
    columnDefs: columnDefsRenewal1,
    rowData: rowDataRenewal1,
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
        gridOptionsRenewal1.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexRenewal1 = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexRenewal1 = 0;
            }
        });
    }
};

const columnDefsRenewal2 = [
    { headerName: "Package Name", field: "packageName", minWidth: 200 },
    { headerName: "Scheme", field: "scheme", width: 150 },
    { headerName: "Effective Date", field: "effectiveDate", width: 150 },
    { headerName: "Price",  field: "price",  width: 100,  type: 'numericColumn',  valueFormatter: currencyFormatterRenewal2,  cellClass: 'ag-right-aligned-cell',  headerClass: 'custom-class' },
    { headerName: "Tax",  field: "tax",  width: 100,  type: 'numericColumn',  valueFormatter: currencyFormatterRenewal2,  cellClass: 'ag-right-aligned-cell',  headerClass: 'custom-class' },
    { headerName: "Total",  field: "total",  width: 100,  type: 'numericColumn',  valueFormatter: currencyFormatterRenewal2,  cellClass: 'ag-right-aligned-cell',  headerClass: 'custom-class' }
];

const rowDataRenewal2 = [
    { packageName: "RPM",  scheme: "Six Months",  effectiveDate: "01-Mar-25",  price: 6000.00,  tax: 360.00,  total: 6360.00 }
];

const pinnedBottomRowDataRenewal2 = [
    { tax: 'Discount', total: 60.00 },
    { tax: 'Net Payable', total: 6300.00 },
    { packageName: 'Six Thousand Three Hundred Only.' }
];

const gridOptionsRenewal2 = {
    columnDefs: columnDefsRenewal2,
    rowData: rowDataRenewal2,
    pinnedBottomRowData: pinnedBottomRowDataRenewal2,
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

function currencyFormatterRenewal2(params) {
    if (typeof params.value === 'number') {
        return '₹' + params.value.toLocaleString('en-IN', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
    }
    return params.value || '';
}