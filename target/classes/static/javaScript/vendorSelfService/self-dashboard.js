let tabView = (type) => {
    if (type == "summary") {

    } else if (type == "analytics") {
        analyticsChart();
    } else (type == "reports")

}

let analyticsChart = () => {
    renderColumnChart('tender', 'Total', 'Applied', 'Received');
    renderColumnChart('purchase-order', 'Open', 'Closed', 'Cancelled');
    renderColumnChart('grpo', 'Open', 'Closed', 'Cancelled');
    renderColumnChart('invoices', 'Open', 'Closed', 'Cancelled');
    renderColumnChart('greviance', 'Open', 'Closed', 'Cancelled');
    renderColumnChart('tds', 'Invoice Amount', 'Received Amount', 'TDS');
}

let renderColumnChart = (v0, v1, v2, v3) => {
    Highcharts.chart(v0, {
        chart: {
            type: 'column',
            height: '300'
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            categories: [
                'Jan',
                'Feb',
                'Mar',
                'Apr',
                'May',
                'June',
                'July',
                'Aug',
                'Sept'
            ],
            crosshair: true
        },
        exporting: {enabled: false},
        credits: {enabled: false},
        yAxis: {
            min: 0,
            title: {
                text: ''
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                pointWidth: 9,
                borderWidth: 1
            }
        },
        series: [{
            name: v1,
            data: [2, 3, 6, 1, 0, 4, 5, 2, 3],
            color: '#bf05ff'
        }, {
            name: v2,
            data: [1, 2, 3, 0, 3, 2, 1, 0, 3],
            color: '#D991F2'
        }, {
            name: v3,
            data: [0, 0, 1, 0, 3, 1, 1, 0, 1],
            color: '#F79C92'
        }]
    });
}

$(() =>{
    renderGridOne('gridDN', rowDataDN);
    renderGridTwo('gridTDS', rowDataTDS);
    renderGridThree('gridGST', rowDataGST);
    renderGridFour('gridAS', rowDataAS);
});
/*function () {
    /!*function adjustGridColumns() {
        const width = $(window).width();
        if (width < 768) {
            gridOptions.columnApi.getAllColumns().forEach(col => {
                if (col.getColId() !== '') {
                    gridOptions.columnApi.setColumnWidth(col, 120);
                }
            });
        } else {
            gridOptions.api.sizeColumnsToFit();
        }
    }

    adjustGridColumns();
    $(window).on('resize', adjustGridColumns);*!/
});*/

let rowDataDN = [
    { debitNoteNo: "DN/2024/001", date: "2024-09-02", po: "PO/2024/001", amount: 12650.00 },
    { debitNoteNo: "DN/2024/002", date: "2024-09-03", po: "PO/2024/002", amount: 12650.00 },
    { debitNoteNo: "DN/2024/003", date: "2024-09-04", po: "PO/2024/003", amount: 12650.00 },
    { debitNoteNo: "DN/2024/004", date: "2024-09-05", po: "PO/2024/004", amount: 12650.00 },
];

let rowDataTDS = [
    { invoiceNo: "IV/2024/001", date: "2024-09-02", po: "PO/2024/001", invoiceAmount: 20000.00, tdsAmount: 2000 },
    { invoiceNo: "IV/2024/002", date: "2024-09-03", po: "PO/2024/002", invoiceAmount: 20000.00, tdsAmount: 2000 },
    { invoiceNo: "IV/2024/003", date: "2024-09-04", po: "PO/2024/003", invoiceAmount: 20000.00, tdsAmount: 2000 },
    { invoiceNo: "IV/2024/004", date: "2024-09-05", po: "PO/2024/004", invoiceAmount: 20000.00, tdsAmount: 2000 },
];

let rowDataGST = [
    { invoiceNo: "IV/2024/001", date: "2024-09-02", po: "PO/2024/001", invoiceAmount: 20000.00, cGstAmount: 1800, sGstAmount: 1800, iGstAmount: 0, tcsAmount: 0 },
    { invoiceNo: "IV/2024/002", date: "2024-09-03", po: "PO/2024/002", invoiceAmount: 20000.00, cGstAmount: 1800, sGstAmount: 1800, iGstAmount: 0, tcsAmount: 0 },
    { invoiceNo: "IV/2024/003", date: "2024-09-04", po: "PO/2024/003", invoiceAmount: 20000.00, cGstAmount: 1800, sGstAmount: 1800, iGstAmount: 0, tcsAmount: 0 },
    { invoiceNo: "IV/2024/004", date: "2024-09-05", po: "PO/2024/004", invoiceAmount: 20000.00, cGstAmount: 1800, sGstAmount: 1800, iGstAmount: 0, tcsAmount: 0 },
];

let rowDataAS = [
    { invoiceNo: "IV/2024/001", paymentAdviceNo: "PMC/PA/2024/001", adviceDate: "2024-08-01", paymentTowards: "Advance Payment", adviceAmount: 20000 },
    { invoiceNo: "IV/2024/002", paymentAdviceNo: "PMC/PA/2024/002", adviceDate: "2024-08-16", paymentTowards: "Mobilization Cost", adviceAmount: 50000 },
    { invoiceNo: "IV/2024/003", paymentAdviceNo: "PMC/PA/2024/003", adviceDate: "2024-09-02", paymentTowards: "Payment Against Invoice", adviceAmount: 35000 },
    { invoiceNo: "IV/2024/004", paymentAdviceNo: "PMC/PA/2024/004", adviceDate: "2024-09-04", paymentTowards: "Transportation Cost", adviceAmount: 12000 },
];

/*const totalAmount = rowDataDN.reduce((sum, row) => sum + row.amount, 0);
rowDataDN.push({ debitNoteNo: 'Total', date: '', po: '', amount: totalAmount });*/

/*const checkboxSelector = function(params) {
    return params.node ? params.node.rowIndex !== rowDataDN.length - 1 : true;
};*/

/*const columnDefsDN = [
    {
        headerName: "",
        checkboxSelection: checkboxSelector,
        maxWidth: 30,
        suppressSizeToFit: true,
        pinned: 'left'
    },
    {
        headerName: "Debit Note No",
        field: "debitNoteNo",
        colId: "debitNoteNo",
        flex: 1,
        cellRenderer: function(params) {
            if (params.value === 'Total') {
                return '<strong class="fw-600 text-end">Total</strong>';
            }
            return params.value;
        },
        cellStyle: function(params) {
            if (params.value === 'Total') {
                return { fontWeight: 'bold', textAlign: 'right' };
            }
            return {};
        }
    },
    { headerName: "Date", field: "date", colId: "date", flex: 1 },
    { headerName: "PO", field: "po", colId: "po", flex: 1 },
    {
        headerName: "Amount",
        field: "amount",
        colId: "amount",
        flex: 1,
        type: 'numericColumn',
        cellStyle: { textAlign: 'right' },
        valueFormatter: function (params) {
            if (params.value == null || params.value === '') return '';
            return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
        }
    }
];*/

/*const gridOptionsDN = {
    columnDefs: columnDefsDN,
    rowData: rowDataDN,
    defaultColDef: {
        resizable: true,
    },
    suppressRowClickSelection: true,
    rowSelection: 'multiple',
    paginationAutoPageSize: true,
    pagination: true,
    getRowStyle: function (params) {
        if (params.node.rowIndex === rowDataDN.length - 1) {
            return {
                fontWeight: 'bold',
                background: '#e8e8e8'
            };
        }
    }
};*/

let renderGridOne = (gridX, rowDataX) => {

    const totalAmount = rowDataX.reduce((sum, row) => sum + row.amount, 0);
    rowDataX.push({ debitNoteNo: 'Total', date: '', po: '', amount: totalAmount });

    const checkboxSelector = (params) => params.node ? params.node.rowIndex !== rowDataX.length - 1 : true;

    const columnDefsX = [
        {
            headerName: "",
            checkboxSelection: checkboxSelector,
            maxWidth: 30,
            suppressSizeToFit: true,
            pinned: 'left'
        },
        {
            headerName: "Debit Note No",
            field: "debitNoteNo",
            colId: "debitNoteNo",
            flex: 1,
            colSpan: function(params) {
                if (params.data.debitNoteNo === 'Total') {
                    return 3;
                }
                else {
                    return 1;
                }
            },
            cellStyle: function(params) {
                if (params.value === 'Total') {
                    return { textAlign: 'right' };
                }
                return {};
            },
            cellRenderer: function(params) {
                if (params.value === 'Total') {
                    return '<strong class="fw-600">Total</strong>';
                }
                return params.value;
            },
        },
        { headerName: "Date", field: "date", colId: "date", flex: 1 },
        { headerName: "PO",  field: "po",  colId: "po",  flex: 1, },
        {
            headerName: "Amount",
            field: "amount",
            colId: "amount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        }
    ];

    const gridOptionsX = {
        columnDefs: columnDefsX,
        rowData: rowDataX,
        defaultColDef: {
            resizable: true,
        },
        suppressRowClickSelection: true,
        rowSelection: 'multiple',
        paginationAutoPageSize: true,
        pagination: true,
        getRowStyle: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return {
                    fontWeight: 'bold',
                    background: '#e8e8e8'
                };
            }
        },
        getRowClass: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return "total-row";
            }
        }
    };

    new agGrid.Grid(document.getElementById(gridX), gridOptionsX);
};

let renderGridTwo = (gridX, rowDataX) => {
    const totalInvoiceAmount = rowDataX.reduce((sum, row) => sum + row.invoiceAmount, 0);
    const totalTdsAmount = rowDataX.reduce((sum, row) => sum + row.tdsAmount, 0);

    rowDataX.push({ invoiceNo: 'Total', date: '', po: '', invoiceAmount: totalInvoiceAmount, tdsAmount: totalTdsAmount });

    const checkboxSelector = (params) => params.node ? params.node.rowIndex !== rowDataX.length - 1 : true;

    const columnDefsX = [
        {
            headerName: "",
            checkboxSelection: checkboxSelector,
            maxWidth: 30,
            suppressSizeToFit: true,
            pinned: 'left'
        },
        {
            headerName: "Invoice No",
            field: "invoiceNo",
            colId: "invoiceNo",
            flex: 1,
            colSpan: function(params) {
                if (params.data.invoiceNo === 'Total') {
                    return 3;
                }
                else {
                    return 1;
                }
            },
            cellStyle: function(params) {
                if (params.value === 'Total') {
                    return { textAlign: 'right' };
                }
                return {};
            },
            cellRenderer: function(params) {
                if (params.value === 'Total') {
                    return '<strong class="fw-600">Total</strong>';
                }
                return params.value;
            },
        },
        { headerName: "Date", field: "date", colId: "date", flex: 1 },
        { headerName: "PO",  field: "po",  colId: "po",  flex: 1, },
        {
            headerName: "Invoice Amount",
            field: "invoiceAmount",
            colId: "invoiceAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        },
        {
            headerName: "TDS Amount",
            field: "tdsAmount",
            colId: "tdsAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        }
    ];

    const gridOptionsX = {
        columnDefs: columnDefsX,
        rowData: rowDataX,
        defaultColDef: {
            resizable: true,
        },
        suppressRowClickSelection: true,
        rowSelection: 'multiple',
        paginationAutoPageSize: true,
        pagination: true,
        getRowStyle: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return {
                    fontWeight: 'bold',
                    background: '#e8e8e8'
                };
            }
        },
        getRowClass: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return "total-row";
            }
        }
    };

    new agGrid.Grid(document.getElementById(gridX), gridOptionsX);
}

let renderGridThree = (gridX, rowDataX) => {
    const totalInvoiceAmount = rowDataX.reduce((sum, row) => sum + row.invoiceAmount, 0);
    const totalCGSTAmount = rowDataX.reduce((sum, row) => sum + row.cGstAmount, 0);
    const totalSGSTAmount = rowDataX.reduce((sum, row) => sum + row.sGstAmount, 0);
    const totalIGSTAmount = rowDataX.reduce((sum, row) => sum + row.iGstAmount, 0);
    const totalTCSAmount = rowDataX.reduce((sum, row) => sum + row.tcsAmount, 0);

    rowDataX.push({
        invoiceNo: 'Total',
        date: '',
        po: '',
        invoiceAmount: totalInvoiceAmount,
        cGstAmount: totalCGSTAmount,
        sGstAmount: totalSGSTAmount,
        iGstAmount: totalIGSTAmount,
        tcsAmount: totalTCSAmount
    });

    const checkboxSelector = (params) => params.node ? params.node.rowIndex !== rowDataX.length - 1 : true;

    const columnDefsX = [
        {
            headerName: "",
            checkboxSelection: checkboxSelector,
            maxWidth: 30,
            suppressSizeToFit: true,
            pinned: 'left'
        },
        {
            headerName: "Invoice No",
            field: "invoiceNo",
            colId: "invoiceNo",
            flex: 1,
            colSpan: function(params) {
                if (params.data.invoiceNo === 'Total') {
                    return 3;
                }
                else {
                    return 1;
                }
            },
            cellStyle: function(params) {
                if (params.value === 'Total') {
                    return { textAlign: 'right' };
                }
                return {};
            },
            cellRenderer: function(params) {
                if (params.value === 'Total') {
                    return '<strong class="fw-600">Total</strong>';
                }
                return params.value;
            },
        },
        { headerName: "Date", field: "date", colId: "date", flex: 1 },
        { headerName: "PO",  field: "po",  colId: "po",  flex: 1, },
        {
            headerName: "Invoice Amount",
            field: "invoiceAmount",
            colId: "invoiceAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        },
        {
            headerName: "CGST",
            field: "cGstAmount",
            colId: "cGstAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        },
        {
            headerName: "SGST",
            field: "sGstAmount",
            colId: "sGstAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        },
        {
            headerName: "IGST",
            field: "iGstAmount",
            colId: "iGstAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        },
        {
            headerName: "TCS",
            field: "tcsAmount",
            colId: "tcsAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        }
    ];

    const gridOptionsX = {
        columnDefs: columnDefsX,
        rowData: rowDataX,
        defaultColDef: {
            resizable: true,
        },
        suppressRowClickSelection: true,
        rowSelection: 'multiple',
        paginationAutoPageSize: true,
        pagination: true,
        getRowStyle: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return {
                    fontWeight: 'bold',
                    background: '#e8e8e8'
                };
            }
        },
        getRowClass: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return "total-row";
            }
        }
    };

    new agGrid.Grid(document.getElementById(gridX), gridOptionsX);
}

let renderGridFour = (gridX, rowDataX) => {
    const totalAdviceAmount = rowDataX.reduce((sum, row) => sum + row.adviceAmount, 0);

    rowDataX.push({
        invoiceNo: 'Total',
        paymentAdviceNo: '',
        adviceDate: '',
        paymentTowards: '',
        adviceAmount: totalAdviceAmount,
    });

    const checkboxSelector = (params) => params.node ? params.node.rowIndex !== rowDataX.length - 1 : true;

    const columnDefsX = [
        {
            headerName: "",
            checkboxSelection: checkboxSelector,
            maxWidth: 30,
            suppressSizeToFit: true,
            pinned: 'left'
        },
        {
            headerName: "Invoice No",
            field: "invoiceNo",
            colId: "invoiceNo",
            flex: 1,
            colSpan: function(params) {
                if (params.data.invoiceNo === 'Total') {
                    return 4;
                }
                else {
                    return 1;
                }
            },
            cellStyle: function(params) {
                if (params.value === 'Total') {
                    return { textAlign: 'right' };
                }
                return {};
            },
            cellRenderer: function(params) {
                if (params.value === 'Total') {
                    return '<strong class="fw-600">Total</strong>';
                }
                return params.value;
            },
        },
        { headerName: "Payment Advice No", field: "paymentAdviceNo", colId: "paymentAdviceNo", flex: 1 },
        { headerName: "Advice Date", field: "adviceDate", colId: "adviceDate", flex: 1 },
        { headerName: "Payment Towards",  field: "paymentTowards",  colId: "paymentTowards",  flex: 1, },
        {
            headerName: "Advice Amount",
            field: "adviceAmount",
            colId: "adviceAmount",
            flex: 1,
            headerClass: "align-to-right",
            cellStyle: { textAlign: 'right' },
            valueFormatter: function (params) {
                if (params.value == null || params.value === '') return '';
                return '\u20B9' + Number(params.value).toLocaleString('en-IN', { minimumFractionDigits: 2 });
            }
        }
    ];

    const gridOptionsX = {
        columnDefs: columnDefsX,
        rowData: rowDataX,
        defaultColDef: {
            resizable: true,
        },
        suppressRowClickSelection: true,
        rowSelection: 'multiple',
        paginationAutoPageSize: true,
        pagination: true,
        getRowStyle: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return {
                    fontWeight: 'bold',
                    background: '#e8e8e8'
                };
            }
        },
        getRowClass: function (params) {
            if (params.node.rowIndex === rowDataX.length - 1) {
                return "total-row";
            }
        }
    };

    new agGrid.Grid(document.getElementById(gridX), gridOptionsX);
}