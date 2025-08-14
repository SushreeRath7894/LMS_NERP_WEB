$(document).ready(function() {
		var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	gridOptions.api.setRowData([]);

	getAllPatientsDetails();

	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			onQuickFilterChanged();
		}
	});
   
    $(".br-s-btn-tx").hide();
     $("#demo").hide();
     $("#cancel").hide();
     $("#save").hide();
});


var columnDefs = [
	{
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: false,
		checkboxSelection: true,
		width: 8,
		sortable: false,
		filter: false,
		resizable: true,
		pinned: 'left',
	},
	{
		headerName: "OPD ID",
		field: "opdId",
	},
	{
		headerName: "Patient ID",
		field: "patientId",
	},
	{
		headerName: "Patient Name",
		field: "patientName"
	},
	{
		headerName: "Age",
		field: "age"
	},
	{
		headerName: "Mobile",
		field: "mobNo"
	}, {
        headerName: "Payment Status",
        field: "pay_status",
        flex: 1,
        cellRenderer: function(params) {
            if (params.data.pay_status == "Fully Paid") {
                return '<a style="color:blue;font-weight: bold;">Fully Paid</a>';
            } else if (params.data.pay_status == "Not Paid") {
                return '<a  style="color:red;font-weight: bold;">Not Paid</a>';
            } else if (params.data.pay_status == "Partial Paid") {
                return '<a style="color:black;font-weight: bold;">Partial Paid</a>';
            }
        }
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
		flex: 1
	},
	pagination: true,
	paginationPageSize: 15,
	onGridReady: function(params) {
		params.api.sizeColumnsToFit();
		window.gridApi = params.api;
		window.gridOptions = params.api;
		getAllPatientsDetails();
	},
	onSelectionChanged: rowSelect
};

let patientId="";
function rowSelect(event) {
	var selectedRows = event.api.getSelectedRows(); // safer access
	console.log(selectedRows);
	var selectedData = selectedRows.map(node => node.data);
	var rowCount = selectedData.length;

	if (selectedRows.length > 0) {
		patientId = selectedRows[0].patientId;
		editOpdDetails(patientId);
	} else {
		$("#patientName").val('');
		$("#age").val('');
		$("#gender").val('');
		$("#mobNo").val('');
		$("#email").val('');
		$("#address").val('');
		$("#wordNo").val('');
		$("#bedNo").val('');
		$("#roomNo").val('');
		$("#consuDoctor").val('');
		$("#department").val('');
		$("#admitDate").val('');
	}
}
function getAllPatientsDetails() {
	var fromdate = 1234;
	var todate = 1234;

	agGrid.simpleHttpRequest({
		url: "opd-billing-manage-view?fromdate=" + fromdate + "&todate=" + todate
	}).then(function(response) {
		if (response.message === "Success") {

			const responseBody = JSON.parse(response.body);
			const patientData = responseBody.OPDRecords;

			window.gridApi.setRowData(patientData);

			if (patientData && patientData.length > 0) {
				window.gridApi.forEachNode(function(node) {
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
function onQuickFilterChanged() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	setTimeout(() => {
		if (gridOptions.api) {
			gridOptions.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

/*function resetBtn() {
	$("#quickFilter").val('');
	dischargeGridOption.api.setQuickFilter('');
	dischargeGridOption.api.refreshCells({
		force: true
	});
	setTimeout(() => {
		if (dischargeGridOption.api) {
			dischargeGridOption.api.getDisplayedRowAtIndex(0)?.setSelected(true);
		}
	}, 300);
}

function nextTab(id) {
    const tabElement = document.querySelector('#' + id + ' a');
    const tab = new bootstrap.Tab(tabElement);
    tab.show();
}*/

function editOpdDetails(patientId) {
	$.ajax({
		type: "GET",
		url: "opd-billing-details-edit?Id=" + patientId,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				const opdDetails = JSON.parse(response.body).opdDetails;

				console.log(opdDetails, 'opd');

				if (opdDetails == null) {
					$("#patientName").val('');
					$("#age").val('');
					$("#gender").val('');
					$("#mobNo").val('');
					$("#email").val('');
					$("#address").val('');
					/*$("#wordNo").val('');
					$("#bedNo").val('');
					$("#roomNo").val('');*/
					$("#consuDoctor").val('');
					$("#department").val('');
					$("#admitDate").val('');
					
				} else {
					$("#patientName").val(opdDetails[0].patientName);
					$("#age").val(opdDetails[0].age);
					$("#gender").val(opdDetails[0].gender);
					$("#mobNo").val(opdDetails[0].mobNo);
					$("#email").val(opdDetails[0].email);
					$("#address").val(opdDetails[0].address);
					/*$("#wordNo").val(opdDetails[0].wordNo);
					$("#bedNo").val(opdDetails[0].bedNo);
					$("#roomNo").val(opdDetails[0].roomNo);*/
					$("#consuDoctor").val(opdDetails[0].consuDoctor);
					$("#department").val(opdDetails[0].department);
					$("#admitDate").val(opdDetails[0].admitDate);

				}


			}

		},
		error: function(error) {
			console.error("Error fetching patient details:", error);
		}
	});
}

function nextTab(id) {
    const tabElement = document.querySelector('#' + id + ' a');
    const tab = new bootstrap.Tab(tabElement);
    tab.show();
}


 document.addEventListener('DOMContentLoaded', function() {
            document.querySelectorAll('.category-header').forEach(header => {
                header.addEventListener('click', function() {
                    const btn = this.querySelector('.expand-btn');
                    this.classList.toggle('expanded');
                });
            });
        });
        
        
        document.addEventListener('DOMContentLoaded', function() {
        document.querySelectorAll('.category-header').forEach(header => {
            header.addEventListener('click', function() {
                const btn = this.querySelector('.expand-btn');
                this.classList.toggle('expanded');
            });
        });

        document.querySelectorAll('.add-entry-btn').forEach(button => {
            button.addEventListener('click', function () {
                const tbodySelector = this.getAttribute('data-target');
                const tbody = document.querySelector(tbodySelector);

                const inputRow = document.createElement('tr');
                inputRow.innerHTML = 
                    `<td><input type="date" class="form-control form-control-sm new-date" required></td>
                    <td><input type="text" class="form-control form-control-sm new-desc" placeholder="Description" required></td>
                    <td><input type="number" step="0.01" class="form-control form-control-sm new-amount" placeholder="Amount" required></td>
                    <td><button class="btn btn-sm btn-success save-entry-btn">Save</button></td>`
                ;
                tbody.appendChild(inputRow);

                inputRow.querySelector('.save-entry-btn').addEventListener('click', function () {
                    const date = inputRow.querySelector('.new-date').value;
                    const desc = inputRow.querySelector('.new-desc').value;
                    const amount = inputRow.querySelector('.new-amount').value;

                    if (date && desc && amount) {
                        inputRow.innerHTML = 
                           ` <td>${date}</td>
                            <td>${desc}</td>
                            <td>$${parseFloat(amount).toFixed(2)}</td>
                            <td></td>`
                        ;
                    } else {
                        alert('Please fill all fields before saving.');
                    }
                });
            });
        });
    });
    
    
    
function saveBill() {
    var billData = {};

    var categoryId = $("#category").val();
    if (!categoryId) {
        toastr.error("Please select a category.");
        return;
    }

    var description = $("#desc").val().trim();
    if (!description) {
        toastr.error("Please enter a description.");
        return;
    }

    billData['billId'] = $("#billId").val();
    billData['categoryId'] = categoryId;
    billData['category'] = $("#category option:selected").text();
    billData['desc'] = description;
    billData['items'] = [];

    console.log("Collected Bill Data:", billData);

    var categoryExists = $(".billing-table tbody tr.category-header").filter(function () {
        return $(this).attr("value") === billData.categoryId;
    }).length > 0;
    if (categoryExists) {
        toastr.error("This category is already added.");
        return;
    }

    const tblData = document.querySelector(".billing-table tbody");

    const oldTotalRow = tblData.querySelector(".grand-total-row");
    if (oldTotalRow) oldTotalRow.remove();

    // Initialize new parent row with $0.00
    tblData.innerHTML += 
        `<tr id="parentRow_${billData.categoryId}" class="category-header"
            data-bs-toggle="collapse"
            data-bs-target="#childDetails_${billData.categoryId}"
            aria-expanded="false" value="${billData.categoryId}">
            <td>${billData.category}</td>
            <td>${billData.desc}</td>
            <td class="category-amount text-end">0.00</td>
            <td class="text-end"><button class="btn go-btn fs-5 lh-1"><i class="fa-solid fa-caret-down"></i></button></td>
        </tr>
        <tr class="collapse child-row" id="childDetails_${billData.categoryId}">
            <td colspan="4" style="padding: 0 !important;">
                <table class="table table-borderless mb-0 child-table_${billData.categoryId}">
                    <tbody class="child-body_${billData.categoryId}"></tbody>
                </table>
                <div class="col text-end p-2">
                    <button class="btn go-btn fs-5 add-entry-btn lh-1"
                        data-target=".child-body_${billData.categoryId}"
                        data-categoryid="${billData.categoryId}">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                </div>
            </td>
        </tr>`;

    tblData.innerHTML += 
        `<tr class="grand-total-row total-row">
            <td colspan="2" class="fw-bold">Total</td>
            <td class="grand-total-amount text-end"><strong>$0.00</strong></td>
            <td></td>
        </tr>`;

    // Event listener for adding entries to child rows
    document.querySelectorAll('.add-entry-btn').forEach(button => {
        button.addEventListener('click', function () {
            const tbodySelector = this.getAttribute('data-target');
            const categoryId = this.getAttribute('data-categoryid');
            const tbody = document.querySelector(tbodySelector);

            const inputRow = document.createElement('tr');
            inputRow.innerHTML = 
                `<td width="32%"><input type="date" class="form-control form-control-sm new-date" required></td>
                <td><input type="text" class="form-control form-control-sm new-desc" placeholder="Description" required></td>
                <td><input type="number" step="0.01" min="0" class="form-control form-control-sm new-amount" placeholder="Amount" required></td>
                <td width="5%"><button class="btn go-btn save-entry-btn">Save</button></td>`;
            tbody.appendChild(inputRow);

            inputRow.querySelector('.save-entry-btn').addEventListener('click', function () {
                const date = inputRow.querySelector('.new-date').value;
                const desc = inputRow.querySelector('.new-desc').value.trim();
                const amountValue = inputRow.querySelector('.new-amount').value.trim();
                const amount = parseFloat(amountValue);

                if (date && desc && !isNaN(amount)) {
                    inputRow.innerHTML = 
                        `<td width="32%">${date}</td>
                        <td width="40%">${desc}</td>
                        <td class="text-end" width="15%">${amount.toFixed(2)}</td>
                        <td></td>`;

                    let total = 0;
                    tbody.querySelectorAll('tr').forEach(row => {
                        const amountCell = row.querySelector('td:nth-child(3)');
                        if (amountCell) {
                            const text = amountCell.textContent.trim();
                            const val = parseFloat(text);
                            if (!isNaN(val)) {
                                total += val;
                            }
                        }
                    });

                    const parentRow = document.getElementById(`parentRow_${categoryId}`);
                    if (parentRow) {
                        parentRow.querySelector('.category-amount').textContent = total.toFixed(2);
                    }

                    recalculateGrandTotal();
                } else {
                    toastr.error("Please fill all fields before saving.");
                }
            });
        });
    });

    // Reset form fields
    $("#billId").val('');
    $("#category").val('');
    $("#desc").val('');
    $("#add").show();
    $("#cancel").hide();
    $("#save").hide();
    $("#demo").hide();
    $("#billing-table").removeClass('d-none');
    $("#saveBill").show();

    recalculateGrandTotal();
}
    function recalculateGrandTotal() {
        let grandTotal = 0;
        document.querySelectorAll('.category-amount').forEach(cell => {
            const val = parseFloat(cell.textContent.trim());
            if (!isNaN(val)) grandTotal += val;
        });

        const totalCell = document.querySelector('.grand-total-amount');
        if (totalCell) {
            totalCell.innerHTML = `<strong>${grandTotal.toFixed(2)}</strong>`;
        }
    }

function addBill(){
	  $("#demo").show();
	   $("#cancel").show();
     $("#save").show();
     $("#add").hide();
     $("#saveBill").hide();
     $("#no-billing-info-0").addClass('d-none');
     $('#billing-table').addClass('d-none');
}


function cancelBill(){
	 $("#demo").hide();
     $("#cancel").hide();
     $("#save").hide();
     $("#billing-table").removeClass('d-none');
     $("#add").show();
     $("#saveBill").show();
}
