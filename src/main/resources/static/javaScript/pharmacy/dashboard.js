$(document).ready(function() {
	getAllMedicienDetails();
	getAllPaymentDetails();
	});

$(() => {
    // createBarChart('barChartToday');
    activateTab('tabToday');
    new agGrid.Grid($('#gridPDC')[0], gridOptionsPDC);
});

let createBarChart = (chartID) => {
    Highcharts.chart(chartID, {
        chart: {type: 'column', height: (9 / 16 * 100) + '%'},
        title: null,
        xAxis: {
            categories: ['15 Apr'],
            crosshair: true
        },
        credits: { enabled: false },
        exporting: { enabled: false },
        yAxis: {
            min: 0,
            title: {text: null}
        },
        legend: {align: 'center', verticalAlign: 'top', layout: 'horizontal'},
        tooltip: {shared: true},
        plotOptions: {
            column: {pointPadding: 0.2, borderWidth: 0}
        },
        series: [
            {name: 'Sales', data: [80], color: '#bf05ff'},
            {name: 'Purchase', data: [0], color: '#7a00b3'}
        ]
    });
}

let activateTab = (tabID) => {
    if (tabID === 'tab7Days') {
        createBarChart('barChart7days');
    } else if(tabID === 'tab30Days') {
        createBarChart('barChart30days');
    } else if(tabID === 'tab60Days') {
        createBarChart('barChart60days');
    } else
        createBarChart('barChartToday');
}

const columnDefsPDC = [
    {headerName: '', checkboxSelection: true, headerCheckboxSelection: false, maxWidth: 35, width: 35, suppressSizeToFit: true, pinned: 'left'},
    {headerName: "Party Name", field: "partyName"},
    {headerName: "Amount", field: "amount", type: 'numericColumn', headerClass: 'numeric-content'},
];

const rowDataPDC = [];

let selectedAssetIndexPDC = null;

const gridOptionsPDC = {
    columnDefs: columnDefsPDC,
    rowData: rowDataPDC,
    suppressHorizontalScroll: true,
    rowSelection: 'single',
    suppressRowClickSelection: false,
    defaultColDef: {
        flex: 1,
        minWidth: 100,
        resizable: true
    },
    pagination: true,
    paginationAutoPageSize: true,
    onRowClicked: function (event) {
        gridOptionsPDC.api.forEachNode((node) => node.setSelected(false));
        event.node.setSelected(true);
        selectedAssetIndexPDC = event.rowIndex;
    },
    onGridReady: function (params) {
        params.api.forEachNode((node, index) => {
            if (index === 0) {
                node.setSelected(true);
                selectedAssetIndexPDC = 0;
            }
        });
        // Show overlay if no data
        if (rowDataPDC.length === 0) {
            params.api.showNoRowsOverlay();
        }
    },
    overlayNoRowsTemplate: `
        <div class="custom-no-rows-overlay">
          <div class="vstack gap-2 opacity-50">
            <i class="fa-solid fa-circle-exclamation text-primary-color-500 fs-5"></i>
            <h5>No PDC Issued</h5>
          </div>
        </div>
    `
};

function currencyFormatterPDC(params) {
    if (typeof params.value === 'number') {
        return '₹' + params.value.toLocaleString('en-IN', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
    }
    return params.value || '';
}

function getAllMedicienDetails() {
    $("#expiredDatList").empty();
    $("#showMoreBtn").remove();
    $("#expiredDatContainer").removeClass("scrollable-container"); // Remove scroll before loading

    agGrid.simpleHttpRequest({
        url: "his-billing-pharmacy-dashboard-expiried-view"
    }).then(function(response) {
        console.log('Full response:', response);

        if (response.code === "success") {
            const responseBody = typeof response.body === 'string' ? JSON.parse(response.body) : response.body;
            const view = responseBody.view;

            console.log('View:', view);

            if (view && view.length > 0) {
                const initialData = view.slice(0, 6);
                initialData.forEach(a => {
                    const d = `<tr>
                        <td><div class="vstack gap-1">
                        <p class="text-primary-color-500">${a?.manufactureitem ?? ''}</p>
                        <span class="text-muted opacity-50">${a?.batch ?? ''}</span></div></td>
                        <td class="text-end">${a?.qty ?? ''}</td>
                        <td class="text-end text-danger">${a?.expDate ?? ''}</td>
                    </tr>`;
                    $("#expiredDatList").append(d);
                });

                if (view.length > 6) {
                    const showMoreBtn = `
                        <tr id="showMoreBtn">
                            <td colspan="3" class="text-center">
                                <button class="btn btn-sm btn-outline-primary" onclick="showMoreExpiredItems()">View All</button>
                            </td>
                        </tr>`;
                    $("#expiredDatList").append(showMoreBtn);

                    // Store remaining items
                    window.remainingExpiredItems = view.slice(6);
                }
            }
        } else {
            console.error("Failed to fetch data");
        }
    });
}

function showMoreExpiredItems() {
    if (window.remainingExpiredItems && window.remainingExpiredItems.length > 0) {
        window.remainingExpiredItems.forEach(a => {
            const d = `<tr>
                <td><div class="vstack gap-1">
                <p class="text-primary-color-500">${a?.manufactureitem ?? ''}</p>
                <span class="text-muted opacity-50">${a?.batch ?? ''}</span></div></td>
                <td class="text-end">${a?.qty ?? ''}</td>
                <td class="text-end text-danger">${a?.expDate ?? ''}</td>
            </tr>`;
            $("#expiredDatList").append(d);
        });

        $("#showMoreBtn").remove();
        window.remainingExpiredItems = [];

        // NOW activate scroll
        $("#expiredDatContainer").addClass("scrollable-container");
    }
}

function getAllPaymentDetails() {
    agGrid.simpleHttpRequest({
        url: "his-billing-pharmacy-dashboard-payment-view"
    }).then(function(response) {
        console.log('Full response:', response);

        if (response.code === "success") {
            const responseBody = typeof response.body === 'string' ? JSON.parse(response.body) : response.body;
            const viewPayment = responseBody;

            console.log('View:', viewPayment);

            if (viewPayment && viewPayment.length > 0) {
                const initialData = viewPayment.slice(0, 6);
                let totalPaidAmount = 0;

                initialData.forEach(a => {
                    const paidAmount = a?.paid_amount ?? 0;
                    totalPaidAmount += paidAmount;

                    const d = `<tr>
                        <td>
                            <div class="vstack gap-1">
                                <p>${a?.cust_name ?? ''}</p>
                                <span class="text-muted">${a?.pharma_id ?? ''}</span>
                            </div>
                        </td>
                        <td class="text-end text-danger align-content-center">${paidAmount.toFixed(2)}</td>
                    </tr>`;
                    $("#paymentDatList").append(d);
                });

                // Format number with commas (Indian format)
                const formattedAmount = totalPaidAmount.toLocaleString('en-IN', {minimumFractionDigits: 2});

                // Update the total in the <h4> dynamically
                const totalHtml = `
                    <i class="fa-solid fa-arrow-up fs-6"></i>
                    <span>-<i class="fa-solid fa-indian-rupee-sign fs-5 me-1"></i>${formattedAmount}</span>
                `;
                $(".col h4").html(totalHtml);
            }
        } else {
            console.error("Failed to fetch data");
        }
    });
}


