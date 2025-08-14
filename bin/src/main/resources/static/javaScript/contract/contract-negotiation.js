
var mainData = {
	"contracts": [
		{
			"id": "6b52",
			"templateId": "OMC/2024/CM/001",
			"templateName": "Template A",
			"effectiveDate": "2024-09-07T22:13",
			"creationDate": "2024-08-29T17:08",
			"status": "1",
			"templateDescription": "Description A\n"

		},
		{
			"id": "7c83",
			"templateId": "OMC/2024/CM/002",
			"templateName": "Template B",
			"effectiveDate": "2024-09-10T10:00",
			"creationDate": "2024-08-30T09:15",
			"status": "2",
			"templateDescription": "Description B\n"
		},
		{
			"id": "8d94",
			"templateId": "OMC/2024/CM/003",
			"templateName": "Template C",
			"effectiveDate": "2024-09-15T15:45",
			"creationDate": "2024-08-31T14:20",
			"status": "3",
			"templateDescription": "Description C\n"

		},
		{
			"id": "9e05",
			"templateId": "OMC/2024/CM/004",
			"templateName": "Template D",
			"effectiveDate": "2024-09-20T12:00",
			"creationDate": "2024-09-01T11:05",
			"status": "1",
			"templateDescription": "Description D\n"
		},
		{
			"id": "af16",
			"templateId": "OMC/2024/CM/005",
			"templateName": "Template E",
			"effectiveDate": "2024-09-25T14:30",
			"creationDate": "2024-09-02T12:10",
			"status": "2",
			"templateDescription": "Description E\n"
		},
		{
			"id": "b027",
			"templateId": "OMC/2024/CM/006",
			"templateName": "Template F",
			"effectiveDate": "2024-09-30T09:00",
			"creationDate": "2024-09-03T08:45",
			"status": "3",
			"templateDescription": "Description F\n"
		},
		{
			"id": "c138",
			"templateId": "OMC/2024/CM/007",
			"templateName": "Template G",
			"effectiveDate": "2024-10-05T13:30",
			"creationDate": "2024-09-04T12:20",
			"status": "1",
			"templateDescription": "Description G\n"
		},
		{
			"id": "d249",
			"templateId": "OMC/2024/CM/008",
			"templateName": "Template H",
			"effectiveDate": "2024-10-10T15:00",
			"creationDate": "2024-09-05T13:00",
			"status": "2",
			"templateDescription": "Description H\n"
		},
		{
			"id": "e350",
			"templateId": "OMC/2024/CM/009",
			"templateName": "Template I",
			"effectiveDate": "2024-10-15T10:00",
			"creationDate": "2024-09-06T09:00",
			"status": "3",
			"templateDescription": "Description I\n"
		},
		{
			"id": "f461",
			"templateId": "OMC/2024/CM/010",
			"templateName": "Template J",
			"effectiveDate": "2024-10-20T12:45",
			"creationDate": "2024-09-07T11:30",
			"status": "1",
			"templateDescription": "Description J\n"
		}
	]
}

$(document).ready(function() {
	var gridDiv = document.querySelector('#contractNegotation');
	new agGrid.Grid(gridDiv, contractNegotationGridOptions);

	//contractNegotationGridOptions.api.setRowData(mainData.contracts);

	var rowData = [];
	contractNegotationGridOptions.api.setRowData(rowData);
	contractNegotationGridOptions.api.setRowData(mainData.contracts);
	if (mainData.contracts && mainData.contracts.length > 0) {
		contractNegotationGridOptions.api.forEachNode(function(node) {
			if (node.rowIndex === 0) {
				node.setSelected(true);
				onSelectionChanged();// Select the first row
			}
		});
	}

	// Add an event listener to handle row selection
	contractNegotationGridOptions.api.addEventListener('selectionChanged', onSelectionChanged);
	
	$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			console.log("Enter key pressed, calling filter function...");
			onQuickFilterChanged();
		}
	});
});

var negotiationGridDefs = [
	{
		headerName: 'Contract negotiation and Collaboration',

		children: [
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
				headerName: "Template Id",
				field: "templateId",
				width: 200
			},
			{
				headerName: "Name",
				field: "templateName",
				width: 450
			},
			{
				headerName: "Description",
				field: "templateDescription",
				width: 430
			},
			{
				headerName: "Created Date",
				field: "creationDate",
				width: 200
			}, {
				headerName: "Effective Date",
				field: "effectiveDate",
				width: 350
			}, {
				headerName: "status",
				field: "status",
				width: 330,
				cellRenderer: function(params) {
					return params.value == "1" ? 'Active' : 'Inactive';
				}
			}
		]
	}];

var contractNegotationGridOptions = {
	columnDefs: negotiationGridDefs,
	rowSelection: 'single',
	groupSelectsChildren: true,
	suppressRowClickSelection: true,
	suppressAggFuncInHeader: true,
	pagination: true,
	paginationPageSize: 10,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 153
	},

};

var selectedId = "";
var selectedTemplate = "";

function onSelectionChanged() {
	selectedId = "";
	selectedTemplate = "";
	var selectedRows = contractNegotationGridOptions.api.getSelectedRows();

	console.log("selected rows-->", selectedRows);

	if (selectedRows != "") {
		selectedId = selectedRows[0].templateId;
		$("#headercontractid").text(selectedId);
		$("#headercontractid2").text(selectedId);
		selectedTemplate = selectedRows[0].templateName;
	}



	// Show the infoDiv if at least one row is selected, hide it otherwise
	if (selectedRows.length > 0) {
		//document.getElementById('infoDiv').style.display = 'block';
	} else {
		//document.getElementById('infoDiv').style.display = 'none';
		$("#headercontractid").text('');
		$("#headercontractid2").text('');
	}
}

var selectedValues = [];
var selectedNames = [];
function vendorList() {
	$('#vendor option:selected').each(function() {
		selectedValues.push($(this).val());
		selectedNames.push($(this).text());
	});
	console.log(selectedValues)
	console.log(selectedNames)
	$('#selectedVendors').val(selectedNames.join(', '));
}



function details() {
	//$("#chatBoxModal").modal('show');

	// Retrieve data from localStorage
	var storedData = localStorage.getItem('contractData');
	var contractData = storedData ? JSON.parse(storedData) : [];

	// Filter the data to find the row with the selected ID
	var selectedContract = contractData.find(function(item) {
		return item[0] === selectedId; // Assuming item[0] is the contractId
	});

	if (selectedContract) {
		var storedContractId = selectedContract[0];
		var storedContractName = selectedContract[1];
		var storedVendors = selectedContract[2];
		var storedTenderDescription = selectedContract[3];

		console.log("Stored Contract ID:", storedContractId);
		console.log("Stored Contract Name:", storedContractName);
		console.log("Stored Vendors:", storedVendors);
		console.log("Stored Tender Description:", storedTenderDescription);

		// Update the contract name on the UI
		$("#contractName").text(storedContractName);

		// Initialize vendors and chats
		var vendors = storedVendors;
		var chats = {};

		vendors.forEach(function(vendor) {
			chats[vendor] = []; // Initialize empty chat history for each vendor
			// Add storedTenderDescription as a default message from our side
			var defaultMessage = {
				sender: "You",
				text: storedTenderDescription,
				time: new Date().toLocaleTimeString()
			};
			chats[vendor].push(defaultMessage);

			// Add some past conversation messages
			var pastMessages = [
				{ sender: "Vendor", text: "Thank you for your message. We will get back to you soon.", time: new Date().toLocaleTimeString() },
				{ sender: "You", text: "You're welcome! Let me know if you need any further information.", time: new Date().toLocaleTimeString() }
			];
			chats[vendor].push(...pastMessages); // Add past messages to chat history
		});

		// Display chat for the first vendor (optional)
		if (vendors.length > 0) {
			displayChat(vendors[0]);
		}

		// Dynamically create vendor tabs
		function createVendorTabs() {
			$('#vendorTabs').empty(); // Clear existing tabs
			vendors.forEach(function(vendor, index) {
				var tabClass = index === 0 ? 'active' : '';
				$('#vendorTabs').append(`
	                    <li class="nav-item">
	                        <a class="nav-link vendorTab ${tabClass}" id="${vendor}-tab" data-toggle="tab" href="#${vendor}-content" role="tab" aria-controls="${vendor}-content" aria-selected="${index === 0}">${vendor}</a>
	                    </li>
	                `);
			});
		}

		// Function to display chat for the selected vendor
		function displayChat(vendor) {
			$('#chatBox').empty(); // Clear the chatbox
			if (chats[vendor].length === 0) {
				$('#chatBox').append('<p>No messages yet. Start the conversation!</p>');
			} else {
				chats[vendor].forEach(function(message) {
					var messageClass = message.sender === "You" ? "text-right usertext" : "text-left vendorText";
					$('#chatBox').append(`
	                        <div class="chat-message  ${messageClass}">
	                            <strong>${message.sender}:</strong><br>
	                            <div>${message.text}</div>
	                            <span class="chat-timestamp">${message.time}</span>
	                        </div>
	                    `);
				});
			}
		}

		// Event listener for tab clicks
		$('#vendorTabs').on('click', 'a', function() {
			var selectedVendor = $(this).text();
			displayChat(selectedVendor);
		});

		// Function to add a message to the chat
		function addMessage(vendor, sender, text) {
			var message = {
				sender: sender,
				text: text,
				time: new Date().toLocaleTimeString()
			};
			chats[vendor].push(message);
			displayChat(vendor); // Refresh the chatbox
		}

		// Send message button click
		$('#sendMessageButton').click(function() {
			var selectedVendor = $('#vendorTabs .nav-link.active').text();
			var chatInput = $('#chatInput').val().trim();
			if (chatInput !== '') {
				addMessage(selectedVendor, "You", chatInput); // Your message
				$('#chatInput').val(''); // Clear the input field

				// Simulate a vendor's reply after a short delay
				/* setTimeout(function() {
					addMessage(selectedVendor, selectedVendor, "This is a reply from " + selectedVendor);
				}, 1000); // 1-second delay for vendor reply */
			}
		});

		// Initialize vendor tabs and chat
		createVendorTabs();
	} else {
		console.log("No contract data found for the selected ID.");
	}
}









function saveContract() {
	// Get the values
	var contractId = selectedId;
	var contractName = selectedTemplate;
	var vendors = selectedNames;
	var tenderDescription = extractTextFromHtml(CKEDITOR.instances.tenderEditor.getData());

	// Retrieve existing data from local storage
	var storedData = localStorage.getItem('contractData');
	var contractData = storedData ? JSON.parse(storedData) : [];

	// Check if the contractId already exists
	var index = contractData.findIndex(function(item) {
		return item[0] === contractId;
	});

	if (index !== -1) {
		// Replace the existing entry with the new one
		contractData[index] = [contractId, contractName, vendors, tenderDescription];
	} else {
		// Add new entry if it doesn't exist
		contractData.push([contractId, contractName, vendors, tenderDescription]);
	}

	// Store the updated array in local storage as a JSON string
	localStorage.setItem('contractData', JSON.stringify(contractData));

	// Hide the infoDiv
	toastr.success('Contract Details Saved Successfully');
}


function extractTextFromHtml(html) {
	var tempDiv = document.createElement('div');
	tempDiv.innerHTML = html;
	var textContent = tempDiv.textContent || tempDiv.innerText || "";
	return textContent.trim();
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	console.log("Element-->", tabElement);
	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		console.log("tabTrigger-->", tabTrigger);
		tabTrigger.show();
	}
}

function resetBtn() {
	$("#quickFilter").val('');
	contractNegotationGridOptions.api.setQuickFilter('');
	contractNegotationGridOptions.api.refreshCells({ force: true });
}
function onQuickFilterChanged() {
	contractNegotationGridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
}
