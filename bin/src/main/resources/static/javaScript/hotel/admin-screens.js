//Document Ready Start Here >>>>
$(document).ready(function() {
	new agGrid.Grid(document.querySelector('#studentGrid'), gridOptions);
	gridOptions.api.setRowData(data);

	document.querySelectorAll(".nav-link").forEach(button => {
		button.addEventListener("click", function() {
			const target = this.getAttribute("data-bs-target");
			handleTabClick(target);
		});
	});
});


// Document Ready End

function prevBtn() {
	// Get the currently active tab
	const currentTab = document.querySelector('.nav-pills .nav-link.active');

	// Find the previous tab
	const prevTab = currentTab.parentElement.previousElementSibling?.querySelector('.nav-link');

	if (prevTab) {
		// If a previous tab exists, activate it and show its content
		const prevTabId = prevTab.getAttribute('href').substring(1); // Get the tab's href (remove the #)
		activateTab(prevTab, prevTabId);
	}
}

function activateTab(tabLink, tabId) {
	// Remove 'active' class from the current tab
	const currentTab = document.querySelector('.nav-pills .nav-link.active');
	const currentTabContent = document.querySelector('.tab-content .tab-pane.show');

	if (currentTab) {
		currentTab.classList.remove('active');
		currentTabContent.classList.remove('show', 'active');
	}

	// Add 'active' class to the previous tab and show its content
	tabLink.classList.add('active');
	document.getElementById(tabId).classList.add('show', 'active');
}


function nextBtnFunction() {
	// Simulate clicking the "Packages" tab
	document.querySelector('a[href="#packagesDetails"]').click();
}



const mainColumnDefs = [
	{
		headerName: "",
		checkboxSelection: true,
		headerCheckboxSelection: false,
		width: 50,
		pinned: 'left'
	},
	{ headerName: "Organization Id", field: "organization_Id", width: 100, pinned: 'left' },
	{ headerName: "Super Admin Name", field: "super_Admin_Name", width: 100, pinned: 'left' },
	{ headerName: "Organization Name", field: "name", width: 100, pinned: 'left' },
	{ headerName: "TAX Code", field: "tAX_Code", width: 100, pinned: 'left' },
	{ headerName: "Postal Code", field: "postal_Code", width: 100, pinned: 'left' },
	{ headerName: "Country", field: "country", width: 100, pinned: 'left' },
	{ headerName: "Street", field: "street", width: 100, pinned: 'left' },
	{ headerName: "City", field: "city", width: 100, pinned: 'left' },
	{ headerName: "State", field: "state", width: 100, pinned: 'left' },
	{ headerName: "Email Address", field: "email_Address", width: 100, pinned: 'left' },
	{ headerName: "Mobile No", field: "mobile_No", width: 100, pinned: 'left' }

];
let data = [
	{
		"organization_Id": "f005",
		"super_Admin_Name": "Emily Carter",
		"name": "Lakeside Villas",
		"tAX_Code": "GST5678912",
		"postal_Code": "60611",
		"country": "USA",
		"street": "78 Lake Shore Dr",
		"city": "Chicago",
		"state": "Illinois",
		"email_Address": "emily@lakesidevillas.com",
		"mobile No": "+1-555-567-8912"
	},
	{
		"organization_Id": "f006",
		"super_Admin_Name": "Michael Chen",
		"name": "Skyline Resorts",
		"tAX_Code": "GST6789123",
		"postal_Code": "98101",
		"country": "USA",
		"street": "500 Pine St",
		"city": "Seattle",
		"state": "Washington",
		"email_Address": "michael@skylineresorts.com",
		"mobile_No": "+1-555-678-9123"
	},
	{
		"organization_Id": "f007",
		"super_Admin_Name": "Sarah Thompson",
		"name": "Golden Sands Hotel",
		"tAX_Code": "GST7891234",
		"postal_Code": "33101",
		"country": "USA",
		"street": "850 Ocean Dr",
		"city": "Miami",
		"state": "Florida",
		"email_Address": "sarah@goldensands.com",
		"mobile_No": "+1-555-789-1234"
	},
	{
		"organization_Id": "f008",
		"super_Admin_Name": "David Singh",
		"name": "Maple Leaf Lodges",
		"tAX_Code": "GST8912345",
		"postal_Code": "80202",
		"country": "USA",
		"street": "321 Mountain Rd",
		"city": "Denver",
		"state": "Colorado",
		"email_Address": "david@mapleleaflodges.com",
		"mobile_No": "+1-555-891-2345"
	},
	{
		"organization_Id": "f009",
		"super_Admin_Name": "Natalie Brooks",
		"name": "Seaside Escapes",
		"tAX_Code": "GST9123456",
		"postal_Code": "92037",
		"country": "USA",
		"street": "950 Coast Blvd",
		"city": "San Diego",
		"state": "California",
		"email_Address": "natalie@seasideescapes.com",
		"mobile_No": "+1-555-912-3456"
	}




]



const gridOptions = {
	columnDefs: mainColumnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		width: 100,
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 15,
	onRowSelected: onCheckboxClick,
};
//edit function call and row select 

function onCheckboxClick() {

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const organization_Id = selectedData.map(node => node.organization_Id);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].organization_Id + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editCandidate()
	} else {
	}
}

function onGridRowClick(event) {
	const data = event.data;


	console.log(data)
	console.log(document.getElementById("location"));

	document.getElementById("organization_Id").value = data.organization_Id || '';
	document.getElementById("super_Admin_Name").value = data.super_Admin_Name || '';
	document.getElementById("name").value = data.name || '';
	document.getElementById("tAX_Code").value = data.tAX_Code || '';
	document.getElementById("postal_Code").value = data.postal_Code || '';
	document.getElementById("country").value = data.country || '';
	document.getElementById("street").value = data.street || '';
	document.getElementById("city").value = data.city || '';
	document.getElementById("state").value = data.state || '';
	document.getElementById("email_Address").value = data.email_Address || '';
	document.getElementById("mobile_No").value = data.mobile_No || '';


}



//edit


function editCandidate() {

	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const organization_Id = selectedData.map(node => node.organization_Id);
	const super_Admin_Name = selectedData.map(node => node.super_Admin_Name);
	$("#super_Admin_Name").val(super_Admin_Name);
	const name = selectedData.map(node => node.name);
	$("#name").val(name);
	const tAX_Code = selectedData.map(node => node.tAX_Code);
	$("#tAX_Code").val(tAX_Code);
	const postal_Code = selectedData.map(node => node.postal_Code);
	$("#postal_Code").val(postal_Code);
	const country = selectedData.map(node => node.country);
	$("#country").val(country);
	const street = selectedData.map(node => node.street);
	$("#street").val(street);
	const city = selectedData.map(node => node.city);
	$("#city").val(city);
	const state = selectedData.map(node => node.state);
	$("#state").val(state);
	const email_Address = selectedData.map(node => node.email_Address);
	$("#email_Address").val(email_Address);
	const mobile_No = selectedData.map(node => node.mobile_No);
	$("#mobile_No").val(mobile_No);



}

document.addEventListener("DOMContentLoaded", function() {
	document.querySelectorAll(".nav-link").forEach(button => {
		button.addEventListener("click", function() {
			const target = this.getAttribute("data-bs-target");
			handleTabClick(target);
		});
	});

	document.querySelectorAll(".custom-tab-group .nav-link").forEach(button => {
		button.addEventListener("click", function() {
			const target = this.getAttribute("data-bs-target");
			handleFilterClick(target); // Call your custom logic
		});
	});

});


function handleTabClick(target) {
	if (target === "#dashboard") {
		document.querySelector('[data-bs-target="#org-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#org-package"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-nformation"]').style.display = "none";
		document.querySelector('[data-bs-target="#properties-contract"]').style.display = "none";
		document.querySelector('[data-bs-target="#subscription-upgrade"]').style.display = "none";
		document.querySelector('[data-bs-target="#subscription-downgrade"]').style.display = "none";
		document.querySelector('[data-bs-target="#subscription-changeplan"]').style.display = "none";
		document.querySelector('[data-bs-target="#subscription-transaction"]').style.display = "none";

	} else if (target === "#organisation") {
		document.querySelector('[data-bs-target="#org-information"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#org-package"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#properties-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-contract"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#user-info"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#user-role"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-upgrade"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-downgrade"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-changeplan"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-transaction"]').closest("li").style.display = "none";

	} else if (target === "#properties") {
		document.querySelector('[data-bs-target="#org-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#org-package"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-information"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#properties-contract"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#user-info"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#user-role"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-upgrade"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-downgrade"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-changeplan"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-transaction"]').closest("li").style.display = "none";
	}  else if (target === "#users") {
		document.querySelector('[data-bs-target="#org-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#org-package"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-contract"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#user-info"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#user-role"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#subscription-upgrade"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-downgrade"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-changeplan"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-transaction"]').closest("li").style.display = "none";
	} else if (target === "#subscription") {
		document.querySelector('[data-bs-target="#org-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#org-package"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-information"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#properties-contract"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#user-info"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#user-role"]').closest("li").style.display = "none";
		document.querySelector('[data-bs-target="#subscription-upgrade"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#subscription-downgrade"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#subscription-changeplan"]').closest("li").style.display = "block";
		document.querySelector('[data-bs-target="#subscription-transaction"]').closest("li").style.display = "block";
    }
}

/*Property Tabs Js*/

$(document).ready(function() {
	var gridDiv = document.querySelector('#propertyGrid');
	new agGrid.Grid(gridDiv, gridOptionsProperty);
	gridOptionsProperty.api.setRowData(propertyData);


});



function prevBtn() {
	const currentTab = document.querySelector('.nav-pills .nav-link.active');
	const prevTab = currentTab.parentElement.previousElementSibling?.querySelector('.nav-link');

	if (prevTab) {
		const prevTabId = prevTab.getAttribute('href').substring(1); 
		activateTab(prevTab, prevTabId);
	}
}

function activateTab(tabLink, tabId) {
	// Remove 'active' class from the current tab
	const currentTab = document.querySelector('.nav-pills .nav-link.active');
	const currentTabContent = document.querySelector('.tab-content .tab-pane.show');

	if (currentTab) {
		currentTab.classList.remove('active');
		currentTabContent.classList.remove('show', 'active');
	}

	// Add 'active' class to the previous tab and show its content
	tabLink.classList.add('active');
	document.getElementById(tabId).classList.add('show', 'active');
}


function nextBtnFunction() {
	// Simulate clicking the "contract" tab
	document.querySelector('a[href="#contractDetails"]').click();
}

const columnDefsProperty = [
	{ checkboxSelection: true, width: 40, pinned: 'left' },
	{ headerName: "Property Id", field: "Property_Id" },
	{ headerName: "Property Name", field: "Property_Name" },
	{ headerName: "Location", field: "Location" },
	{ headerName: "Address Line1", field: "Address_Line1" },
	{ headerName: "Address Line2", field: "Address_Line2" },
	{ headerName: "City", field: "City" },
	{ headerName: "Pin", field: "Pin" },
	{ headerName: "Check In", field: "Check_In" },
	{ headerName: "Check Out", field: "Check_Out" },
	{ headerName: "Status", field: "Status" }
];




let propertyData = [


	{
		"Property_Id": "P001",
		"Property_Name": "Sunset Villa",
		"Location": "Goa",
		"Address_Line1": "Beach Road",
		"Address_Line2": "Candolim",
		"City": "Panaji",
		"Pin": "403001",
		"Check_In": "14:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P002",
		"Property_Name": "Mountain View Lodge",
		"Location": "Manali",
		"Address_Line1": "Hadimba Temple Rd",
		"Address_Line2": "Old Manali",
		"City": "Manali",
		"Pin": "175131",
		"Check_In": "13:00",
		"Check_Out": "10:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P003",
		"Property_Name": "Palm Breeze Resort",
		"Location": "Kerala",
		"Address_Line1": "Alleppey Beach",
		"Address_Line2": "Near Lighthouse",
		"City": "Alleppey",
		"Pin": "688007",
		"Check_In": "15:00",
		"Check_Out": "12:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P004",
		"Property_Name": "Oceanic Stay",
		"Location": "Mumbai",
		"Address_Line1": "Juhu Tara Rd",
		"Address_Line2": "Santacruz West",
		"City": "Mumbai",
		"Pin": "400049",
		"Check_In": "14:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P005",
		"Property_Name": "Royal Heritage",
		"Location": "Jaipur",
		"Address_Line1": "Amer Road",
		"Address_Line2": "Near City Palace",
		"City": "Jaipur",
		"Pin": "302002",
		"Check_In": "13:00",
		"Check_Out": "10:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P006",
		"Property_Name": "Hilltop Inn",
		"Location": "Ooty",
		"Address_Line1": "Fern Hill",
		"Address_Line2": "Near Lake",
		"City": "Ooty",
		"Pin": "643001",
		"Check_In": "12:00",
		"Check_Out": "10:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P007",
		"Property_Name": "Desert Mirage",
		"Location": "Jaisalmer",
		"Address_Line1": "Sam Sand Dunes",
		"Address_Line2": "Desert Camp",
		"City": "Jaisalmer",
		"Pin": "345001",
		"Check_In": "15:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P008",
		"Property_Name": "Lakeview Resort",
		"Location": "Udaipur",
		"Address_Line1": "Pichola Lake",
		"Address_Line2": "Haridas Ji Ki Magri",
		"City": "Udaipur",
		"Pin": "313001",
		"Check_In": "13:00",
		"Check_Out": "10:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P009",
		"Property_Name": "Snow Valley Stay",
		"Location": "Shimla",
		"Address_Line1": "The Mall",
		"Address_Line2": "Near Church",
		"City": "Shimla",
		"Pin": "171001",
		"Check_In": "14:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P010",
		"Property_Name": "Green Meadows",
		"Location": "Coonoor",
		"Address_Line1": "Upper Coonoor",
		"Address_Line2": "Lamb’s Rock Rd",
		"City": "Coonoor",
		"Pin": "643102",
		"Check_In": "12:00",
		"Check_Out": "10:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P011",
		"Property_Name": "Backwater Haven",
		"Location": "Kumarakom",
		"Address_Line1": "Lake Side",
		"Address_Line2": "Near Bird Sanctuary",
		"City": "Kottayam",
		"Pin": "686563",
		"Check_In": "14:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P012",
		"Property_Name": "Coral Reef Hotel",
		"Location": "Andaman",
		"Address_Line1": "Havelock Island",
		"Address_Line2": "Govind Nagar",
		"City": "Port Blair",
		"Pin": "744101",
		"Check_In": "13:00",
		"Check_Out": "10:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P013",
		"Property_Name": "Riverside Retreat",
		"Location": "Rishikesh",
		"Address_Line1": "Laxman Jhula",
		"Address_Line2": "Tapovan",
		"City": "Rishikesh",
		"Pin": "249192",
		"Check_In": "12:00",
		"Check_Out": "10:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P014",
		"Property_Name": "Cloud Nine Hotel",
		"Location": "Shillong",
		"Address_Line1": "Police Bazar",
		"Address_Line2": "Main Road",
		"City": "Shillong",
		"Pin": "793001",
		"Check_In": "14:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P015",
		"Property_Name": "Tea Garden Estate",
		"Location": "Darjeeling",
		"Address_Line1": "Happy Valley",
		"Address_Line2": "Near Zoo",
		"City": "Darjeeling",
		"Pin": "734101",
		"Check_In": "13:00",
		"Check_Out": "10:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P016",
		"Property_Name": "Sapphire Inn",
		"Location": "Chennai",
		"Address_Line1": "T. Nagar",
		"Address_Line2": "North Usman Rd",
		"City": "Chennai",
		"Pin": "600017",
		"Check_In": "14:00",
		"Check_Out": "12:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P017",
		"Property_Name": "Bayfront Residency",
		"Location": "Pondicherry",
		"Address_Line1": "Beach Road",
		"Address_Line2": "White Town",
		"City": "Puducherry",
		"Pin": "605001",
		"Check_In": "13:00",
		"Check_Out": "11:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P018",
		"Property_Name": "Valley Breeze",
		"Location": "Lonavala",
		"Address_Line1": "Tiger Point Road",
		"Address_Line2": "Khandala",
		"City": "Lonavala",
		"Pin": "410401",
		"Check_In": "14:00",
		"Check_Out": "11:00",
		"Status": "Active"
	},
	{
		"Property_Id": "P019",
		"Property_Name": "Sunshine Meadows",
		"Location": "Panchgani",
		"Address_Line1": "Table Land",
		"Address_Line2": "Near Main Market",
		"City": "Panchgani",
		"Pin": "412805",
		"Check_In": "13:00",
		"Check_Out": "10:00",
		"Status": "Inactive"
	},
	{
		"Property_Id": "P020",
		"Property_Name": "Golden Sands Resort",
		"Location": "Goa",
		"Address_Line1": "Colva Beach",
		"Address_Line2": "Margao",
		"City": "South Goa",
		"Pin": "403708",
		"Check_In": "15:00",
		"Check_Out": "12:00",
		"Status": "Active"
	}
];

const gridOptionsProperty = {
	columnDefs: columnDefsProperty,
	rowData: propertyData,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 180,
	},
	rowSelection: 'single',
	pagination: true,
	paginationPageSize: 10
};

/*User Js*/


// Static Data - 4 Records
var rowData = [
  {
    name: "Alice Johnson",
    role: "Admin",
    propertyName: "Sattkara Heights",
    emailAddress: "alice.johnson@example.com",
    mobileNo: "9876543210",
    status: "Active"
  },
  {
    name: "Raj Mehta",
    role: "Manager",
    propertyName: "Sunrise Villa",
    emailAddress: "raj.mehta@example.com",
    mobileNo: "9123456789",
    status: "Inactive"
  },
  {
    name: "Priya Sharma",
    role: "Staff",
    propertyName: "Palm Residency",
    emailAddress: "priya.sharma@example.com",
    mobileNo: "9012345678",
    status: "Active"
  },
  {
    name: "John Dsouza",
    role: "Supervisor",
    propertyName: "Ocean View",
    emailAddress: "john.dsouza@example.com",
    mobileNo: "9988776655",
    status: "Suspended"
  },
  {
    name: "Neha Verma",
    role: "Admin",
    propertyName: "Green Meadows",
    emailAddress: "neha.verma@example.com",
    mobileNo: "9955667788",
    status: "Active"
  },
  {
    name: "Amit Khanna",
    role: "Staff",
    propertyName: "Lakeview Apartments",
    emailAddress: "amit.khanna@example.com",
    mobileNo: "8844553322",
    status: "Inactive"
  },
  {
    name: "Sara Thomas",
    role: "Manager",
    propertyName: "Skyline Towers",
    emailAddress: "sara.thomas@example.com",
    mobileNo: "7777888899",
    status: "Active"
  },
  {
    name: "Vikram Reddy",
    role: "Supervisor",
    propertyName: "Hilltop Estate",
    emailAddress: "vikram.reddy@example.com",
    mobileNo: "7665544332",
    status: "Suspended"
  },
  {
    name: "Divya Kapoor",
    role: "Staff",
    propertyName: "Heritage Homes",
    emailAddress: "divya.kapoor@example.com",
    mobileNo: "7554433221",
    status: "Active"
  },
  {
    name: "Rohit Sen",
    role: "Manager",
    propertyName: "Elite Residency",
    emailAddress: "rohit.sen@example.com",
    mobileNo: "9443322110",
    status: "Inactive"
  }
];
// Column Definitions
 var columnDefsUsers = [
	{ 
	      headerCheckboxSelection: true,
	      checkboxSelection: true,
	      width: 50,
	      suppressSizeToFit: true,
	},
	{
		headerName: "Name",
		field: "name",
		width: "150"
	},
	{
		headerName: "Role",
		field: "role",
		width: "150"
	},
	{
		headerName: "Property Name",
		field: "propertyName",
		width: "150"
	},
	{
		headerName: "Email Address",
		field: "emailAddress",
		width: "150"

	},
	{
		headerName: "Mobile No",
		field: "mobileNo",
		width: "150"

	},
	{
		headerName: "Status",
		field: "status",
		width: "150"

	},	
];
// Grid Options
var gridOptionsUsers = {
  columnDefs: columnDefsUsers,
	rowData: rowData,
  rowSelection: 'single',
  suppressRowClickSelection: true,
  defaultColDef: {
      sortable: true,
      filter: true,
      resizable: true,
  },
  pagination: true,
  paginationPageSize: 15
};
$(document).ready(function() {

	var gridDiv = document.querySelector('#usersGrid');
	new agGrid.Grid(gridDiv, gridOptionsUsers);
	gridOptionsUsers.api.setRowData(rowData);
	
	//When The User Click On The Enter Button also The Search Features Work .
	document.getElementById("quickFilter").addEventListener("keydown", function (event) {
	    if (event.key === "Enter") { 
	        onQuickFilterChanged();
	    }
	});

});

document.addEventListener("DOMContentLoaded", function() {
	document.querySelectorAll(".nav-link").forEach(button => {
		button.addEventListener("click", function() {
			const target = this.getAttribute("data-bs-target");
			handleTabClick(target);
		});
	});
	
	document.querySelectorAll(".custom-tab-group .nav-link").forEach(button => {
	    button.addEventListener("click", function () {
	      const target = this.getAttribute("data-bs-target");
	      handleFilterClick(target); // Call your custom logic
	    });
	  });
	  
	  
});
//AG GridSearch Features And Functionality --->>>>
function SearchUserInput(event) {
    const value = event.target.value;
    gridOptionsUsers.api.setQuickFilter(value);
}



function onQuickFilterChanged() {
    const value = document.getElementById("quickFilter").value;
    gridOptionsUsers.api.setQuickFilter(value);
}
function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(a.nav-link[href="#${tablink}"]);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}
 


//For Saving Corporate Details -->>>>
function saveCorporate(){
	
	
	if($("#firstName").val() == "" ||  $("#firstName").val() == null){
		toastr.error("first name is Required");
		return;
	}
	else if($("#lastName").val() == "" ||  $("#lastName").val() == null){
		toastr.error("Last name is Required");
		return;
	}
	else if($("#mobileNo").val() == "" ||  $("#mobileNo").val() == null){
		toastr.error("Mobile no is Required");
		return;
	}
	else if($("#emailId").val() == "" ||  $("#emailId").val() == null){
		toastr.error("Email is Required");
		return;
	}
	
	else{

	let corporateDetails = {}
	corporateDetails['firstName'] = $("#firstName").val();
	corporateDetails['lastName'] = $("#lastName").val();
	corporateDetails['mobileNo'] = $("#mobileNo").val();
	corporateDetails['emailId'] = $("#emailId").val();

	clearFields();
	console.log("Data For corporateDetails ->>",corporateDetails);
	toastr.success("Data Saved SuccessFully");
	}
	
}
function addUser()
{
	clearFields();	
}

//For Clear All Fields -->>>
function clearFields(){
	
	$("#firstName").val("")
	$("#lastName").val("")
	$("#mobileNo").val("")
	$("#emailId").val("")
	
}

/*Subscription Js start*/

var rowDataSubs = [
  {
    item: "Toilet Roll",
    noOfUnits: 100,
    frequency: "Daily",
    totalAmount: "₹2,000"
  },
  {
    item: "Shampoo Bottles",
    noOfUnits: 50,
    frequency: "Weekly",
    totalAmount: "₹1,500"
  },
  {
    item: "Hand Towels",
    noOfUnits: 80,
    frequency: "Weekly",
    totalAmount: "₹2,400"
  },
  {
    item: "Room Freshener",
    noOfUnits: 30,
    frequency: "Monthly",
    totalAmount: "₹900"
  },
  {
    item: "Pillow Covers",
    noOfUnits: 60,
    frequency: "Monthly",
    totalAmount: "₹1,800"
  },
  {
    item: "Bed Sheets",
    noOfUnits: 45,
    frequency: "Monthly",
    totalAmount: "₹4,500"
  },
  {
    item: "Water Bottles",
    noOfUnits: 200,
    frequency: "Daily",
    totalAmount: "₹3,000"
  },
  {
    item: "Bath Soap",
    noOfUnits: 150,
    frequency: "Weekly",
    totalAmount: "₹2,250"
  },
  {
    item: "Laundry Bags",
    noOfUnits: 25,
    frequency: "Monthly",
    totalAmount: "₹750"
  },
  {
    item: "Slippers",
    noOfUnits: 40,
    frequency: "Weekly",
    totalAmount: "₹1,200"
  }
];
var columnDefsSubscription = [
	{ 
	      headerCheckboxSelection: true,
	      checkboxSelection: true,
	      width: 50,
	      suppressSizeToFit: true,
	},
	{
		headerName: "Item",
		field: "item",
		width: "150"
	},
	{
		headerName: "No of Units",
		field: "noOfUnits",
		width: "150"
	},
	{
		headerName: "Frequency",
		field: "frequency",
		width: "150"
	},
	{
		headerName: "Total",
		field: "totalAmount",
		width: "150"

	},
];
// Define grid options
var gridOptionsSubscription = {
  columnDefs: columnDefsSubscription,
	rowData: rowDataSubs,
  rowSelection: 'single',
  suppressRowClickSelection: true,
  defaultColDef: {
      sortable: true,
      filter: true,
      resizable: true,
  },
  pagination: true,
  paginationPageSize: 15
};

$(document).ready(function() {



	
	var gridDiv = document.querySelector('#myGridSubscription');
	new agGrid.Grid(gridDiv, gridOptionsSubscription);
	gridOptionsSubscription.api.setRowData(rowDataSubs);
	
		
	//When The User Click On The Enter Button also The Search Features Work .
	document.getElementById("quickFilter").addEventListener("keydown", function (event) {
	    if (event.key === "Enter") { 
	        onQuickFilterChanged();
	    }
	});
	
	document.getElementById('operation-reservationTab').style.display = 'block';
	 document.getElementById('downgradeTab').style.display = 'none';
	document.getElementById('changePlantab').style.display = 'none';

});
function goToConfirmOrder() {
    // Change step bar
    document.getElementById('step2').classList.add('active');

    // Hide current section and show next
    document.getElementById('upgradeAddOnsPage').style.display = 'none';
    document.getElementById('confirmOrderPage').style.display = 'block';
  }
function goBackToUpgradePage() {
  const stepItems = document.querySelectorAll(".stepper .step");
  stepItems[1].classList.remove("active");

  document.getElementById("confirmOrderPage").style.display = "none";
  document.getElementById("upgradeAddOnsPage").style.display = "block";
document.getElementById('changePlantab').style.display = 'none';
}
function goToDowngradePage()
{
	document.getElementById('operation-reservationTab').style.display = 'none';
    document.getElementById('downgradeTab').style.display = 'block';
	document.getElementById('changePlantab').style.display = 'none';
}
function goToUpgradePage()
{
	document.getElementById('operation-reservationTab').style.display = 'block';
    document.getElementById('downgradeTab').style.display = 'none';
	document.getElementById('changePlantab').style.display = 'none';
}
function changePlanPage()
{
	document.getElementById('operation-reservationTab').style.display = 'none';
    document.getElementById('downgradeTab').style.display = 'none';	
	document.getElementById('changePlantab').style.display = 'block';	
}
