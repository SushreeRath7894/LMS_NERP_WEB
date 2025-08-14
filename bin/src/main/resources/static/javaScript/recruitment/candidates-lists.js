// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);

	var myGrid12 = document.querySelector('#myGrid1');
	new agGrid.Grid(myGrid12, gridOptions1);

	var myGrid2 = document.querySelector('#myGrid2');
	new agGrid.Grid(myGrid2, gridOptions2);

	var myGrid3 = document.querySelector('#myGrid3');
	new agGrid.Grid(myGrid3, gridOptions3);

	var myGrid4 = document.querySelector('#myGrid4');
	new agGrid.Grid(myGrid4, gridOptions4);

	var myGrid5 = document.querySelector('#myGrid5');
	new agGrid.Grid(myGrid5, gridOptions5);

	var myGrid6 = document.querySelector('#myGrid6');
	new agGrid.Grid(myGrid6, gridOptions6);

	var myGridSrc = document.querySelector('#myGridSrc');
	new agGrid.Grid(myGridSrc, gridOptionsSrc);

	/*var myReq = document.querySelector('#myReq');
	new agGrid.Grid(myReq, gridOptionsReq);*/

	/*var myGridReq = document.querySelector('#myGridReq');
	new agGrid.Grid(myGridReq, gridOptionsApplyReq);*/

	var myGridBank = document.querySelector('#myGridBank');
	new agGrid.Grid(myGridBank, gridOptionsApplyBank);

	var myGridDoc = document.querySelector('#myGridDoc');
	new agGrid.Grid(myGridDoc, gridOptionsDoc);

	gridOptions1.api.setRowData();
	gridOptions2.api.setRowData();
	gridOptions3.api.setRowData();
	gridOptions4.api.setRowData();
	gridOptions5.api.setRowData();
	gridOptions6.api.setRowData();
	gridOptionsSrc.api.setRowData();
	gridOptionsDoc.api.setRowData();
	gridOptionsApplyBank.api.setRowData([]);
	getCandidateLists();
	getEducationList();

	var i, currentYear, startYear, endYear, newOption, dropdownYear;
	dropdownYear = document.getElementById("dropdownYear");
	currentYear = (new Date()).getFullYear();
	startYear = currentYear - 40;
	endYear = currentYear + 3;

	for (i = startYear; i <= endYear; i++) {
		newOption = document.createElement("option");
		newOption.value = i;
		newOption.label = i;
		if (i == currentYear) {
			newOption.selected = true;
		}
		dropdownYear.appendChild(newOption);
	}

	var dropdownAwardYear = document.getElementById("dropdownAwardYear");

	for (i = startYear; i <= endYear; i++) {
		newOption = document.createElement("option");
		newOption.value = i;
		newOption.label = i;
		if (i == currentYear) {
			newOption.selected = true;
		}
		dropdownAwardYear.appendChild(newOption);
	}

	$('#candPofileImageDiv').addClass('d-none');

});

$(document).ready(function() {
	// tabs li clcik event

	$('a[href="#candidateDetailsTab"]').on('click', function() {
		$(".jobs-item").removeClass("selected");
		closeJobDetails();
	});

	$('a[href="#candidateJobsTab"]').on('click', function() {
	});

	$('a[href="#candidateApplyJobsTab"]').on('click', function() {
		$(".jobs-item").removeClass("selected");
		closeJobDetails();
	});
});

$(document).ready(function() {
	document.getElementById('goBtn').addEventListener('click', function() {
		quickFilterGrid(gridOptions);
	});

	document.getElementById('resetBtn').addEventListener('click', function() {
		document.getElementById('quickFilter').value = '';
		gridOptions.api.setQuickFilter('');
		$(".loader").show();
		selectFirstRow(gridOptions);
	});

	document.getElementById('quickFilter').addEventListener('keyup', function(event) {
		if (event.key === 'Enter' || event.key === 'Backspace') {
			quickFilterGrid(gridOptions);
		}
	});
});

document.addEventListener('DOMContentLoaded', () => {
	const jobItems = document.querySelectorAll('.jobs-item');

	jobItems.forEach(item => {
		item.addEventListener('click', () => {
			// Remove the class from all items
			jobItems.forEach(job => job.classList.remove('active-job-box'));

			// Add the class to the clicked item
			item.classList.add('active-job-box');
		});
	});
});


var columnDefs = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Candidate ID",
		field: "candidateId",
	}, {
		headerName: "Name",
		field: "firstName"
	}, {
		headerName: "Location",
		field: "location"
	}, {
		headerName: "DOB",
		field: "dob",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Experience",
		field: "experience"
	}, {
		headerName: "Source",
		field: "source"
	}, {
		headerName: "No of Applications",
		field: "noApplications",
		width: 180
	}, {
		headerName: "Resume",
		field: "resume",
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			var div = "";
			if (params.data.resume) {
				var ext = params.data.resume.split(".");
				if (ext[1] == "pdf") {
					div = div
						+ " "
						+ '<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("' + params.data.resume + '")> </div>';
				} else {
					div = div
						+ " "
						+ '<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImage("' + params.data.resume + '")> </div>';
				}
			}
			return div;
		},
	}, {
		headerName: "Create Date",
		field: "createdOn",
		cellStyle: {
			textAlign: 'center'
		}
	}];

var gridOptions = {
	columnDefs: columnDefs,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 149,
		height: 10
	},
	pagination: true,
	paginationPageSize: 15,
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelectId

};

function getCandidateLists() {
	agGrid.simpleHttpRequest({ url: 'view-candidate-view-ajax' }).then(function(data) {
		var len = data.length;
		$('#totalCandidate').find('span').html(len);
		if (len > 0) {
			gridOptions.api.setRowData(data);
			gridOptions.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptions.api.setRowData();
		}
	});
}

function rowSelectId() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const candidateId = selectedData.map(node => node.candidateId);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].candidateId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		getAppliedJob(candidateId);
		editPage(candidateId);
		setFieldsDisabled(true);
		getOpensJobsList();
		$('#personal-tab').tab('show');
		clearAddress();
	} else {
		getAppliedJob();
		setFieldsDisabled(false);
		cancelBtn();

	}
};

var columnDefs1 = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Skills",
		field: "skillId",
		cellRenderer: function(params) {
			return '<a onclick=editSkillDetails("'
				+ params.data.skillId
				+ '") href="javascript:void(0)">'
				+ params.data.skills + '</a>';
		}
	}, {
		headerName: "Description",
		field: "skillDesc",
	}, {
		headerName: "Level",
		field: "skillLevel",
		type: "rightAligned"
	}, {
		headerName: "Experience",
		field: "experience",
		type: "rightAligned"
	}

];

var gridOptions1 = {
	columnDefs: columnDefs1,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 311,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelecSkills
};

function rowSelecSkills() {
	var selectedNodes = gridOptions1.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const skillId = selectedData.map(node => node.skillId);
	var selectedRows = gridOptions1.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].skillId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editSkillDetails(skillId);
	} else {
		enableSkill(false);
	}
};

function editSkillDetails(id1) {
	$.ajax({
		type: "GET",
		url: "view-candidate-edit-SkillDetails?skillId=" + id1,
		async: false,
		success: function(response) {
			console.log(response);
			if (response.message == "Success") {
				$('#skillId').val(response.body.skillId);
				$('#skills').val(response.body.skills);
				$('#skillsDesc').val(response.body.skillDesc);
				$('#skillLevel').val(response.body.skillLevel);
				$('#skillExp').val(response.body.experience);
				enableSkill(true);
			}
		}
	});
}

//EXPERIENCE
var columnDefs2 = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Designation",
		field: "workExperineceId",
		/* cellRenderer : function(params) {
			return '<div>' + params.data.designation + '</div>';
		} */
		cellRenderer: function(params) {
			return '<a onclick=editWorkDetails("'
				+ params.data.workExperineceId
				+ '") href="javascript:void(0)">'
				+ params.data.designation + '</a>';
		}
	}, {
		headerName: "Organization",
		field: "organization"
	}, {
		headerName: "Worked From",
		field: "workFrom",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Worked Till",
		field: "workTill",
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Description",
		field: "description"
	},
	{
		headerName: "Work Experience",
		field: "workExp"
	}


];

var gridOptions2 = {
	columnDefs: columnDefs2,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 250,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelecExperience
};

function rowSelecExperience() {
	var selectedNodes = gridOptions2.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const workExperineceId = selectedData.map(node => node.workExperineceId);
	var selectedRows = gridOptions2.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].workExperineceId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editWorkDetails(workExperineceId);
	} else {
		$('#desgId').val('').attr("disabled", false);
		$('#orgId').val('').attr("disabled", false);
		$('#jobDescId').val('').attr("disabled", false);
		$('#workFromExp1').val('').attr("disabled", false);
		$('#workTillExp1').val('').attr("disabled", false);
		$('#workExp').val('').attr("disabled", false);
	}
};

function editWorkDetails(id1) {
	$.ajax({
		type: "GET",
		url: "view-candidate-edit-WorkDetails?workId=" + id1,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('#workExperineceId').val(response.body.workExperineceId);
				$('#desgId').val(response.body.designation).attr("disabled", true);
				$('#orgId').val(response.body.organization).attr("disabled", true);
				$('#jobDescId').val(response.body.description).attr("disabled", true);
				$('#workFromExp1').val(response.body.workFrom).attr("disabled", true);
				$('#workTillExp1').val(response.body.workTill).attr("disabled", true);
				$('#workExp').val(response.body.workExp).attr("disabled", true);
			}
		}
	});
}

var columnDefs3 = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	},
	{
		headerName: "Type Id",
		field: "typeId",
		hide: true
	}, {
		headerName: "Type",
		field: "type"
	}, {
		headerName: "Address",
		field: "addressId",
		width: 200,
		cellRenderer: function(params) {
			return '<div>' + params.data.address + '</div>';
		},
	}, {
		headerName: "Country",
		field: "countryId",
		cellRenderer: function(params) {
			return '<div>' + params.data.country + '</div>';
		},
	}, {
		headerName: "State",
		field: "stateId",
		cellRenderer: function(params) {
			return '<div>' + params.data.state + '</div>';
		},
	}, {
		headerName: "City",
		field: "cityId",
		cellRenderer: function(params) {
			return '<div>' + params.data.city + '</div>';
		},
	}, {
		headerName: "Pin Code",
		field: "pinCode",
	}

];

var gridOptions3 = {
	columnDefs: columnDefs3,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 180,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelectAddress
};

function rowSelectAddress() {
	var selectedNodes = gridOptions3.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const addressId = selectedData.map(node => node.addressId);
	const typeId = selectedData.map(node => node.typeId);
	var selectedRows = gridOptions3.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].requisitionId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editAddress(addressId, typeId);
	} else {
		enableAddress(false);
		clearAddress();

	}
};

var columnDefs4 = [
	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Qualification Id",
		field: "educationId",
		width: 50,
		hide: true,
	}, {
		headerName: "Qualification",
		field: "qualification",
		width: 200,

	}, {
		headerName: "Institution",
		field: "institution",
		width: 200
	}, {
		headerName: "Passing Year",
		field: "passingYear",
		cellStyle: {
			textAlign: 'center'
		},
		width: 200
	}];

var gridOptions4 = {
	columnDefs: columnDefs4,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 300,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelectEducation

};


function rowSelectEducation() {
	var selectedNodes = gridOptions4.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const educationId = selectedData.map(node => node.educationId);
	var selectedRows = gridOptions4.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(selectedRow, index) {
		rowCount = rowCount + 1;
	});

	if (rowCount > 0) {
		editEducation(educationId);

	} else {
      clearEducation();
	}
}

function editEducation(id1) {
	$.ajax({
		type: "GET",
		url: "view-candidate-edit-education?eduid=" + id1,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('#educationId').val(response.body.educationId);
				$('#qualification').val(response.body.qualification);
				$('#institution').val(response.body.institution);
				$('#dropdownYear').val(response.body.passingYear);
				enableEducation(true);
			} else {
				addEducation();
			}
		}
	});
}

function clearEducation(){
	$('#educationId').val("");
	$('#qualification').val("").attr('disabled', false);
	$('#institution').val("").attr('disabled', false);
	$('#dropdownYear').val("").attr('disabled', false);
}

//AWARDS
var columnDefs5 = [

	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Award Name",
		field: "awardId",
		hide: true,
		width: 100,
	}, {
		headerName: "Award Name",
		field: "awardName",
		width: 300,
	}, {
		headerName: "Year",
		field: "awardYear",
		width: 100,
		cellStyle: {
			textAlign: 'center'
		}
	}, {
		headerName: "Description",
		field: "awardDescription",
		width: 300,
	}];

var gridOptions5 = {
	columnDefs: columnDefs5,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 414,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelecAwards
};



function rowSelecAwards() {
	var selectedNodes = gridOptions5.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const awardId = selectedData.map(node => node.awardId);
	var selectedRows = gridOptions5.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].awardId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editAwardDetails(awardId);
	} else {
		$('#awardName').val('').attr("disabled", false);
		$('#dropdownAwardYear').val('').attr("disabled", false);
		$('#awardDescId').val('').attr("disabled", false);
	}
};

function editAwardDetails(id1) {
	$.ajax({
		type: "GET",
		url: "view-candidate-editAwardDetails?awardId=" + id1,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('#awardId').val(response.body.awardId);
				$('#awardName').val(response.body.awardName).attr("disabled", true);
				$('#dropdownAwardYear').val(response.body.awardYear).attr("disabled", true);
				$('#awardDescId').val(response.body.awardDescription).attr("disabled", true);
			}
		}
	});
}

//Reference
var columnDefs6 = [

	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 10,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Reference Name",
		field: "referenceId",
		/* cellRenderer : function(params) {
			return '<div>' + params.data.name + '</div>';
		} */
		cellRenderer: function(params) {
			return '<a onclick=editReferenceDetails("'
				+ params.data.referenceId
				+ '") href="javascript:void(0)">'
				+ params.data.name + '</a>';
		}
	}, {
		headerName: "Mobile No",
		field: "mobileNo",
		type: "rightAligned"
	}, {
		headerName: "Email Id",
		field: "emailId"
	}, {
		headerName: "Description",
		field: "description"
	}];

var gridOptions6 = {
	columnDefs: columnDefs6,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 300,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelecReference
};

function rowSelecReference() {
	var selectedNodes = gridOptions6.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const referenceId = selectedData.map(node => node.referenceId);
	var selectedRows = gridOptions6.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].referenceId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editReferenceDetails(referenceId);
	} else {
		$('#referenceName').val('').attr("disabled", false);
		$('#referenceMobileNo').val('').attr("disabled", false);
		$('#referenceEmail').val('').attr("disabled", false);
		$('#referenceDescId').val('').attr("disabled", false);
	}
};

function editReferenceDetails(id1) {
	$.ajax({
		type: "GET",
		url: "view-candidate-editReferenceDetails?refId=" + id1,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('#referenceId').val(response.body.referenceId).attr("disabled", true);
				$('#referenceName').val(response.body.name).attr("disabled", true);
				$('#referenceMobileNo').val(response.body.mobileNo).attr("disabled", true);
				$('#referenceEmail').val(response.body.emailId).attr("disabled", true);
				$('#referenceDescId').val(response.body.description).attr("disabled", true);

			}
		}
	});
}

//Source
var columnDefs7 = [

	{
		headerCheckboxSelection: true,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		width: 30,
		sortable: false,
		filter: false,
		resizable: true
	}, {
		headerName: "Source",
		field: "sourceId",
		/* cellRenderer : function(params) {
			return '<div>' + params.data.name + '</div>';
		} */
		cellRenderer: function(params) {
			return '<a onclick=editSourceDetails("'
				+ params.data.sourceId
				+ '") href="javascript:void(0)">'
				+ params.data.name + '</a>';
		}
	}, {
		headerName: "Description",
		field: "description"
	}];

var gridOptionsSrc = {
	columnDefs: columnDefs7,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 619,
		height: 10
	},
	rowSelection: 'single',
	suppressRowClickSelection: true,
	onSelectionChanged: rowSelecSource
};

function rowSelecSource() {
	var selectedNodes = gridOptionsSrc.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const sourceId = selectedData.map(node => node.sourceId);
	var selectedRows = gridOptionsSrc.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].sourceId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editSourceDetails(sourceId);
	} else {
		$('#sourceName').val('').attr("disabled", false);
		$('#sourceDescId').val('').attr("disabled", false);
	}
};

function editSourceDetails(id1) {
	$.ajax({
		type: "GET",
		url: "view-candidate-editSourceDetails?sourceId=" + id1,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$('#sourceId').val(response.body.sourceId).attr("disabled", true);
				$('#sourceName').val(response.body.name).attr("disabled", true);
				$('#sourceDescId').val(response.body.description).attr("disabled", true);

			}
		}
	});
}

//EMPLOYEE Document DETAILS STARTS	
var columnDefsDoc = [
	{
		headerName: "",
		field: "checkDoc",
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30
	},
	{
		headerName: "Document Type",
		field: "documentType",
		cellRenderer: function(params) {
			return '<a onclick=editDoc("'
				+ params.data.documentType
				+ '") href="javascript:void(0)">'
				+ params.data.documentTypeName + '</a>';
		}
	}, {
		headerName: "Name",
		field: "employeeName",
	}, {
		headerName: "Document",
		field: "documentName",
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			var div = "";
			if (params.data.documentName) {
				var ext = params.data.documentName.split(".");
				if (ext[1] == "pdf") {
					div = div
						+ " "
						+ '<div class ="fa fa-file-pdf-o" style="cursor: pointer; color: red;" onclick=viewImage("' + params.data.documentName + '")> </div>';
				} else {
					div = div
						+ " "
						+ '<div class ="fa fa-picture-o" style="cursor: pointer; color: blue;" onclick=viewImage("' + params.data.documentName + '")> </div>';
				}
			}
			return div;
		},
	}, {
		headerName: "Status",
		field: "status",
		hide: true,
		cellStyle: {
			textAlign: 'center'
		},
		cellRenderer: function(params) {
			if (params.data.status == "1") {
				return '<div style="color:green">' + "Active" + '</div>';
			} else {
				return '<div>' + "Inactive" + '</div>';
			}
		}
	}];

var gridOptionsDoc = {
	columnDefs: columnDefsDoc,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 414,
		height: 20
	},
	rowSelection: 'single',
	onSelectionChanged: rowSelecDocuments,
	suppressRowClickSelection: true,
	getRowNodeId: function(data) {
		return data.documentType;
	}
};

function rowSelecDocuments() {
	var selectedNodes = gridOptionsDoc.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const documentType = selectedData.map(node => node.documentType);
	var selectedRows = gridOptionsDoc.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].documentType + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editDoc(documentType);
	} else {
		$('#documentType').val('').attr("disabled", false);
	}
};

function editDoc(id) {
	var empid = $('#candidateId').val();
	$.ajax({
		type: "GET",
		url: "view-candidate-document-edit?docType=" + id + "&empid=" + empid,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#documentType").attr('disabled', true);
				$("#ddtype").removeClass("select");
				$("#mySidenavDoc").show();
				var fileName = response.body.documentName;
				if (fileName != null) {
					var ext = response.body.documentName.split(".");
					$("#imageName_0").html(fileName);

					if (ext[1] == "jpg" || ext[1] == "png") {
						var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
							+ fileName + '")></i></a> </div>';
					} else if (ext[1] == "pdf") {
						var LightImg = '<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
							+ fileName + '")></i></a> </div>';
					} else {
						var LightImg = "<div class='uploadicon position-l'> </div>";
					}

					$("#uploadedBillDiv_0").html(LightImg);
				}
				var doctype = response.body.documentType;

				let allRows = [];
				gridOptionsDoc.api.forEachNode((node) => {
					allRows.push(node.data.documentTypeName.toLowerCase());
				});

				$.ajax({
					type: "GET",
					url: "view-candidate-documenttype-list?candidate=" + "edit",
					contentType: false,
					success: function(response) {
						if (response.message == "success") {
							var responseData = response.body;

							console.log("response Data-->", responseData);
							var filterdata = responseData.filter(function(item) {
								return !allRows.includes(item.name.toLowerCase());
							});



							$("#documentType").empty();
							$("#documentType")
								.append("<option value=''>Select</option>");
							for (var i = 0; i < response.body.length; i++) {
								var option = $("<option></option>");
								$(option).val(filterdata[i].key);
								$(option).html(filterdata[i].name);
								$("#documentType").append(option);
							}
							$("#documentType").val(doctype);
							alert(doctype)
						}
					},
					error: function(data) {
						console.log(data);
						$("#documentType").empty();
						$("#documentType").append("<option value=''>Select</option>");

					}
				})
			}
		}
	});
}

var columnDefsApplyBank = [
	{
		headerName: "",
		field: "checkDoc",
		headerCheckboxSelection: false,
		headerCheckboxSelectionFilteredOnly: true,
		checkboxSelection: true,
		sortable: false,
		filter: false,
		resizable: true,
		width: 30
	},
	{
		headerName: "Account No",
		field: "accountNo",
		cellStyle: {
			textAlign: 'left'
		},
		cellRenderer: function(params) {
			return '<a onclick="editCandidateBank(\''
				+ params.data.bankId + '\')" href="javascript:void(0)">'
				+ params.data.accountNo + ' <i class="fa fa-edit"></i></a>';
		}
	}, {
		headerName: "Bank Name",
		field: "bankName",
		cellStyle: {
			textAlign: "left"

		}
	}, {
		headerName: "IFSC",
		field: "ifscCode",
		cellStyle: {
			textAlign: 'left'
		}
	}, {
		headerName: "Address",
		field: "bankAddress",
		cellStyle: {
			textAlign: "left"
		}
	}];

var gridOptionsApplyBank = {
	columnDefs: columnDefsApplyBank,
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 309,
		height: 10
	},
	rowSelection: 'single',
	onSelectionChanged: rowSelecAccounts,
};

function rowSelecAccounts() {
	var selectedNodes = gridOptionsApplyBank.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	const bankId = selectedData.map(node => node.bankId);
	var selectedRows = gridOptionsApplyBank.api.getSelectedRows();
	var rowCount = 0;
	for (var i = 0; i < selectedRows.length; i++) {
		var id = id + '"' + selectedRows[i].bankId + '"';
		rowCount = rowCount + 1;
	}
	if (rowCount > 0) {
		editCandidateBank(bankId);
	} else {
		$('#accountNo').val('').attr("disabled", false);
		$('#bankName').val('').attr("disabled", false);
		$('#ifscCode').val('').attr("disabled", false);
		$('#bankAddress').val('').attr("disabled", false);
		$('#bankId').val('').attr("disabled", false);
	}
};

function editCandidateBank(canbid) {
	$.ajax({
		type: "GET",
		url: "view-candidate-edit-bankDetails?id=" + canbid,
		async: false,
		success: function(response) {
			console.log(response)
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var ebData = jsonData.editBank[0];
				$("#accountNo").val(ebData.accountNo).attr("disabled", true);
				$("#bankName").val(ebData.bankName).attr("disabled", true);
				$("#ifscCode").val(ebData.iffcCode).attr("disabled", true);
				$("#bankAddress").val(ebData.address).attr("disabled", true);
				$('#bankId').val(ebData.bankdid).attr("disabled", true);
			}
		}
	});
}

function editPage(id) {
	$('#searchRowDiv').hide();
	$('#candidateId').val(id);
	gridOptions1.api.setRowData([]);
	gridOptions2.api.setRowData([]);
	gridOptions3.api.setRowData([]);
	gridOptions4.api.setRowData([]);
	gridOptions5.api.setRowData([]);
	gridOptions6.api.setRowData([]);
	gridOptionsSrc.api.setRowData([]);
	gridOptionsDoc.api.setRowData([]);
	gridOptionsApplyBank.api.setRowData([]);

	$.ajax({
		type: "GET",
		url: "view-candidate-edit-candidate?id=" + id,
		contentType: "application/json",
		success: function(response) {
			console.log(response)
			if (response.message == "Success") {
				$(".loader").show();
				$('#firstnameid').val(response.body[0].firstName);
				$('#lastnameid').val(response.body[0].lastName);
				$('#genderid').val(response.body[0].gender);
				$('#dobid').val(response.body[0].dob);
				$('#dobCalendar').val(response.body[0].dob);

				$('#bloodgroupid').val(response.body[0].bloodGroup);
				$('#maritalstatusid').val(response.body[0].maritalStatus);
				$('#nationalityid').val(response.body[0].nationality);
				$('#fatherid').val(response.body[0].fatherName);
				$('#motherid').val(response.body[0].motherName);
				$('#mobilenoid').val(response.body[0].mobileNo);
				$('#personalmailid').val(response.body[0].personalEmail);
				$('#workmailid').val(response.body[0].workEmail);
				$('#aadharNo').val(response.body[0].aadharNo);
				$('#panNo').val(response.body[0].panNo);
				$('#emergencyMob').val(response.body[0].emergencyMob);

				$('#nameDtls').text(
					response.body[0].firstName + " "
					+ response.body[0].lastName);
				$('#locationDtls').text(response.body[0].location);
				$('#mobileNoDtls').text(response.body[0].mobileNo);
				$('#emailDtls').text(response.body[0].personalEmail);
				$('#canimghid').val(response.body[0].fileUpload);


				//document.getElementById('mainSecond').style.display = "block";
				$(".mainSecond").show();

				$('#imgemp').attr('src', '');
				if (response.body[0].fileUpload == null || response.body[0].fileUpload == "") {
					/*$('#imgemp').attr('src', '../assets/images/noimage.jpg');*/
					$("#imgemp").attr("src", "../assets/images/noimage.jpg");
				} else {
					$('#imgemp').attr('src', response.body[0].fileUploadFile);
				}

				getAddressDetails(id);
				getEducationDetails(id);
				getSkillsDetails(id);
				getWorkExperienceDetails(id);
				getAwardDetails(id);
				getRefrenceDetails(id);
				getSourcesDetails(id);
				getDocumentsDetails(id);
				getBankDetails(id);

			}
			$(".loader").hide();
		},
		error: function(data) {

			console.log(data);
		}
	}) //ajax ends
}

function getBankDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-bankDetails?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			gridOptionsApplyBank.api.setRowData(data);
			gridOptionsApplyBank.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptionsApplyBank.api.setRowData([]);
		}
	});
}

function getDocumentsDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-document-ajax?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			gridOptionsDoc.api.setRowData(data);
			gridOptionsDoc.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptionsDoc.api.setRowData([]);
		}
	});
}

function getSourcesDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-source?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			gridOptionsSrc.api.setRowData(data);
			gridOptionsSrc.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptionsSrc.api.setRowData([]);
		}
	});
}

function getAddressDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-address?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			$("#saveAddress").attr("disabled", true);
			gridOptions3.api.setRowData(data);
			/*gridOptions3.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});*/
		} else {
			gridOptions3.api.setRowData([]);
		}
	});
}

function getEducationDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-education?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			$("#saveEducationBtn").attr("disabled", true);
			gridOptions4.api.setRowData(data);
			/*gridOptions4.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});*/
		} else {
			gridOptions4.api.setRowData([]);
			addEducation();
		}
	});
}

function getSkillsDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-skills?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			$("#saveSkillBtn").attr("disabled", true);
			gridOptions1.api.setRowData(data);
			gridOptions1.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptions1.api.setRowData([]);
			addSkill();
		}
	});
}

function getWorkExperienceDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-workExperience?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			gridOptions2.api.setRowData(data);
			gridOptions2.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptions2.api.setRowData([]);
		}
	});
}

function getAwardDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-award?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			gridOptions5.api.setRowData(data);
			gridOptions5.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptions5.api.setRowData([]);
		}
	});
}

function getRefrenceDetails(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-view-reference?id=' + id
	}).then(function(data) {
		if (data.length > 0) {
			gridOptions6.api.setRowData(data);
			gridOptions6.api.forEachNode(function(node) {
				if (node.rowIndex === 0) {
					node.setSelected(true);
				}
			});
		} else {
			gridOptions6.api.setRowData([]);
		}
	});
}

function cancelBtn() {
	$('#firstnameid').val('');
	$('#lastnameid').val('');
	$('#genderid').val('');
	$('#dobid').val('');
	$('#dobCalendar').val('');
	$('#bloodgroupid').val('');
	$('#maritalstatusid').val('');
	$('#nationalityid').val('');
	$('#fatherid').val('');
	$('#motherid').val('');
	$('#mobilenoid').val('');
	$('#personalmailid').val('');
	$('#workmailid').val('');
	$('#aadharNo').val('');
	$('#panNo').val('');
	$('#emergencyMob').val('');
	$('#nameDtls').text('');
	$('#locationDtls').text('');
	$('#mobileNoDtls').text('');
	$('#emailDtls').text('');
	$('#canimghid').val('');
	$('#imgemp').attr('src', '../assets/images/noimage.jpg');

	gridOptions1.api.setRowData([]);
	gridOptions2.api.setRowData([]);
	gridOptions3.api.setRowData([]);
	gridOptions4.api.setRowData([]);
	gridOptions5.api.setRowData([]);
	gridOptions6.api.setRowData([]);
	gridOptionsSrc.api.setRowData([]);
	gridOptionsDoc.api.setRowData([]);
	gridOptionsApplyBank.api.setRowData([]);
	gridOptions1.api.deselectAll();
	gridOptions2.api.deselectAll();
	gridOptions3.api.deselectAll();
	gridOptions4.api.deselectAll();
	gridOptions5.api.deselectAll();
	gridOptions6.api.deselectAll();
	gridOptionsSrc.api.deselectAll();
	gridOptionsDoc.api.deselectAll();
	gridOptionsApplyBank.api.deselectAll();
	clearAddress();
	clearEducation();
}

function statelist(st, ct) {
	var country = $("#countryid").val();
	if (country) {
		$.ajax({
			type: "POST",
			url: "view-candidate-state-list",
			dataType: 'json',
			contentType: 'application/json',
			data: country,
			success: function(
				response) {
				if (response.message == "success") {
					console
						.log(response);
					$(
						"#stateid")
						.empty();
					$(
						"#stateid")
						.append(
							"<option value=''>Select</option>");
					$(
						"#cityid")
						.empty();
					$(
						"#cityid")
						.append(
							"<option value=''>Select</option>");

					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(
							option)
							.val(
								response.body[i].key);
						$(
							option)
							.html(
								response.body[i].name);
						$(
							"#stateid")
							.append(
								option);
					}
					if (st != '') {
						$('#stateid').val(st);
						citylist(ct);
					}
				}
			},
			error: function(
				data) {
				console
					.log(data);
				$("#stateid")
					.empty();
				$("#stateid")
					.append(
						"<option value=''>Select</option>");
				$("#cityid")
					.empty();
				$("#cityid")
					.append(
						"<option value=''>Select</option>");
			}
		})
	} else {
		$("#stateid").empty();
		$("#stateid").append("<option value=''>Select</option>");
		$("#cityid").empty();
		$("#cityid").append("<option value=''>Select</option>");
	}
}

function citylist(ct) {
	var state = $("#stateid").val();
	if (state) {
		$.ajax({
			type: "POST",
			url: "view-candidate-city-list",
			dataType: 'json',
			contentType: 'application/json',
			data: state,
			success: function(
				response) {
				if (response.message == "success") {
					console
						.log(response);
					$(
						"#cityid")
						.empty();
					$(
						"#cityid")
						.append(
							"<option value=''>Select</option>");

					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(
							option)
							.val(
								response.body[i].key);
						$(
							option)
							.html(
								response.body[i].name);
						$(
							"#cityid")
							.append(
								option);
					}
					if (ct != '') {
						$('#cityid').val(ct);
					}
				}
			},
			error: function(
				data) {
				console
					.log(data);
				$("#cityid")
					.empty();
				$("#cityid")
					.append(
						"<option value=''>Select</option>");
			}
		})
	} else {
		$("#cityid").empty();
		$("#cityid")
			.append(
				"<option value=''>Select</option>");
	}
}

function setFieldsDisabled(isDisabled) {
	$('#firstnameid').prop('disabled', isDisabled);
	$('#lastnameid').prop('disabled', isDisabled);
	$('#genderid').prop('disabled', isDisabled);
	$('#dobid').prop('disabled', isDisabled);
	$('#dobCalendar').prop('disabled', isDisabled);
	$('#bloodgroupid').prop('disabled', isDisabled);
	$('#maritalstatusid').prop('disabled', isDisabled);
	$('#nationalityid').prop('disabled', isDisabled);
	$('#fatherid').prop('disabled', isDisabled);
	$('#motherid').prop('disabled', isDisabled);
	$('#mobilenoid').prop('disabled', isDisabled);
	$('#personalmailid').prop('disabled', isDisabled);
	$('#workmailid').prop('disabled', isDisabled);
	$('#aadharNo').prop('disabled', isDisabled);
	$('#panNo').prop('disabled', isDisabled);
	$('#emergencyMob').prop('disabled', isDisabled);
	$('#imgemp').prop('disabled', isDisabled);

}

function newCandidate() {
	gridOptions.api.deselectAll();
	setFieldsDisabled(false);
	$('#newRegistration').removeClass('hidden');
	$('#myTab .nav-link').each(function() {
		if (!$(this).is('#personal-tab')) {
			$(this).addClass('disabled');
			$(this).removeAttr('data-toggle');
		}
	});
	$('#personal-tab').tab('show');
	$('#saveCandDetails').removeClass('d-none');
	$('#cancelCandDetails').removeClass('d-none');
	$('#newCandDetails').addClass('d-none');
	$('#editCandDetails').addClass('d-none');
	addCandidate();
}

function addCandidate() {

	$('#candidateId').val("");
	$('#can_Id').html("");
	$("#aadharNo").val('');
	$("#firstnameid").val("");
	$("#lastnameid").val("");
	$("#genderid").val("");
	$("#dobid").val("");
	$("#bloodgroupid").val("");
	$("#maritalstatusid").val("");
	$("#fatherid").val("");
	$("#motherid").val("");
	$("#mobilenoid").val("");
	$("#personalmailid").val("");
	$("#workmailid").val("");
	$("#panNo").val("");
	$("#emergencyMob").val("");

	$('#nationalityid').val('dp087');
	$('#candPofileImageDiv').removeClass('d-none');

}

function editCandidate() {
	setFieldsDisabled(false);
	$('#candPofileImageDiv').removeClass('d-none');
	$('#personal-tab').tab('show');
	$('#saveCandDetails').removeClass('d-none');
	$('#cancelCandDetails').removeClass('d-none');
	$('#newCandDetails').addClass('d-none');
	$('#editCandDetails').addClass('d-none');
}

function cancelCandDetails() {
	getCandidateLists();
	$('#myTab .nav-link').each(function() {
		$(this).removeClass('disabled');
		$(this).attr('data-toggle', 'tab');
	});

	$('#newRegistration').addClass('hidden');
	$('#saveCandDetails').addClass('d-none');
	$('#cancelCandDetails').addClass('d-none');
	$('#newCandDetails').removeClass('d-none');
	$('#editCandDetails').removeClass('d-none');
	$('#candPofileImageDiv').addClass('d-none');
	setFieldsDisabled(true);
}

function editAddress(id1, id2) {
	$.ajax({
		type: "GET",
		url: "view-candidate-edit-address?addressId=" + id1 + "&addressType=" + id2,
		async: false,
		success: function(response) {
			if (response.message == "Success") {
				$("#Type").attr('disabled', true);
				$('#addressId').val(response.body.addressId);
				$('#Type').val(response.body.type);
				$('#address').val(response.body.address);
				$('#countryid').val(response.body.country);
				$('#pinCode').val(response.body.pinCode);
				$("#stateid").attr('disabled', true);
				$("#cityid").attr('disabled', true);
				var stsId1 = response.body.state;
				var ctId1 = response.body.city;
				statelist(stsId1, ctId1);
				enableAddress(true);
			}
		}
	});

}

function clearAddress(){
	$('#addressId').val("").attr('disabled', false);
	$("#Type").val("").attr('disabled', false);
	$('#address').val("").attr('disabled', false);
	$('#countryid').val("").attr('disabled', false);
	$('#pinCode').val("").attr('disabled', false);
	$("#stateid").val("").attr('disabled', false);
	$("#cityid").val("").attr('disabled', false);
}

function getEducationList() {

	$.ajax({
		type: "GET",
		url: "view-candidate-get-educationDetails",
		async: false,
		success: function(response) {
			var DropDownData = response.body;
			var inputTag = $("#qualification");
			inputTag.empty();
			var defaultOption = document.createElement('option');
			defaultOption.value = "";
			defaultOption.text = "Select";
			inputTag.append(defaultOption);

			for (var i = 0; i < DropDownData.length; i++) {
				var option = document.createElement('option');
				option.value = DropDownData[i].key;
				option.text = DropDownData[i].name;
				inputTag.append(option);
			}


		}

	})

}

function getOpensJobsList() {
	agGrid.simpleHttpRequest({
		url: "review-hiring-requisition-list",
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var requisitionList = jsonData.requisitionData;

		var jobListContainer = $(".jobs-box");

		// Clear the existing content
		jobListContainer.empty();

		// Loop through each job requisition
		requisitionList.forEach(function(requisition) {
			var jobItem = `
        <div class="jobs-item">
          <div class="jobs-item-link">
            <div class="jobs-item-bg"></div>

            <div class="jobs-item-title">${requisition.jobTitle}</div>

            <div class="jobs-item-date-box">
              <span class="jobs-item-date">
                <i class="fas fa-map-marker-alt"></i> Bhubaneswar
              </span>
            </div>
            <div class="jobs-item-date-box">
              <span class="jobs-item-date">
                <i class="fas fa-briefcase"></i> ${requisition.minExp} - ${requisition.maxExp} years
              </span>
            </div>
            <div class="jobs-item-date-box">
              <span class="jobs-item-date">
                <i class="fas fa-calendar-alt"></i> Last Date: ${requisition.applyEndDate}
              </span>
            </div>

            <button class="apply-now-btn" onclick="selectJobItem(this); applyJobs('${requisition.requisitionId}');">Apply</button>
            <button class="apply-now-btn" onclick="selectJobItem(this); viewJobDetails('${requisition.requisitionId}');">Job Details</button>
			<!-- <button class="apply-now-btn" onclick="applyJobUrl('${requisition.requisitionId}');">Job Url</button>-->
          </div>
        </div>
      `;

			// Append the dynamically created job item to the container
			jobListContainer.append(jobItem);
		});
	});
}


function selectJobItem(buttonElement) {
	$(".jobs-item").removeClass("selected");
	$(buttonElement).closest(".jobs-item").addClass("selected");
}

function viewJobDetails(reqid) {
	$(".jobs-box").addClass("d-none");
	$("#closeJd").removeClass("d-none");
	$("#jobDetailsSec").removeClass("d-none");
	$("#applyJd").removeClass("d-none");

	$.ajax({
		type: "GET",
		url: "review-hiring-job-details?id=" + reqid,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				const data = JSON.parse(response.body);
				if (data.requisitionData.length > 0) {
					const job = data.requisitionData[0];
					let candidateId = $("#candidateId").val();
					getAppliedJob(candidateId);
					// Update job details
					$("#jobId").val(job.requisitionId);
					$("#job-title").text(job.jobTitle || "N/A");
					$("#jd-positionSummary").text(job.summary || "N/A");
					$("#jd-positionResponsibility").text(job.responsibility || "N/A");
					$("#jd-designation").text(job.designation || "N/A");
					$("#jd-band").text(job.jobBand || "N/A");
					$("#jd-hiring-manager").text(job.hiringManager || "N/A");
					$("#jd-join-date").text(job.joinDate || "N/A");
					$("#jd-apply-date").text(job.applyStartDate || "N/A");
					$("#jd-end-date").text(job.applyEndDate || "N/A");
					$("#jd-positions").text(job.noOfPositions || "N/A");
					$("#jd-experience").text(`${job.minExp || "N/A"} - ${job.maxExp || "N/A"} years`);
					$("#jd-salary").text(`₹${job.minSalary || "N/A"}`);
					$("#jd-status").text(job.activityStatus === "Active" ? "Active" : "Inactive");
					$("#jd-apprv-status").text(job.approvalStatus || "Pending");
					$("#jobsPagePrev").addClass("d-none");
					$("#jobsPageNext").addClass("d-none");

					// Add skills dynamically
					const skillsContainer = $("#jobDetailsSec .jd-skills");
					skillsContainer.empty();

					if (job.skillsReq && job.skillsReq.length > 0) {
						job.skillsReq.forEach(skill => {
							skillsContainer.append(`
                                <div class="col-md-4 mb-3">
                                    <div class="skill-card border rounded p-2 d-flex align-items-center">
                                        <div>
                                            <span class="fw-bold">${skill.skillName}</span>
                                            <small class="d-block text-muted">Experience: ${skill.skillExp} years</small>
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

					// Footer details
					$("#created-on").text(job.createdOn || "N/A");
					$("#approved-by").text(job.approvedBy || "N/A");
				} else {
					console.error("No requisition data found.");
				}
			} else {
				console.error("Failed to fetch job details.");
			}
		},
		error: function(error) {
			console.error("Error fetching job details:", error);
		}
	});
}


function applyJobDetails() {
	const reqId = $("#jobId").val();
	applyForRequisition(reqId);
}

function closeJobDetails() {
	$("#closeJd").addClass("d-none");
	$(".jobs-box").removeClass("d-none");
	$("#jobDetailsSec").addClass("d-none");
	$("#applyJd").addClass("d-none");
	$("#jobId").val("");

	const fieldsToClear = [
		"#job-title",
		"#jd-positionSummary",
		"#jd-positionResponsibility",
		"#jd-designation",
		"#jd-band",
		"#jd-hiring-manager",
		"#jd-join-date",
		"#jd-apply-date",
		"#jd-end-date",
		"#jd-positions",
		"#jd-experience",
		"#jd-salary",
		"#jd-status",
		"#jd-apprv-status"
	];

	$("#jobsPagePrev").removeClass("d-none");
	$("#jobsPageNext").removeClass("d-none");

	// Clear the text content of all specified fields
	fieldsToClear.forEach(selector => {
		$(selector).text("");
	});

	$("#jd-skills").html("");
}

function applyJobs(reqid) {
	viewJobDetails(reqid);
}

function getAppliedJob(id) {
	agGrid.simpleHttpRequest({
		url: 'view-candidate-requisition?id=' + id
	}).then(function(data1) {
		if (data1.body[0] === null) {
			let jobListContainer = document.getElementById('applied-job-list');
			jobListContainer.innerHTML = '';
			jobListContainer.innerHTML = `
                <div class="d-flex justify-content-center align-items-center" style="height: 250px;">
                    <p class="text-muted">No Applied Job Found</p>
                </div>
            `;
			return;
		} else {

			let jobData = JSON.parse(data1.body[0]);
			let jobIdOnPage = $("#jobId").val();

			let appliedJob = jobData.find(job => job.requisitionId === jobIdOnPage);
			if (appliedJob) {
				$("#appliedRole").text(appliedJob.jobTitle); 
				$("#appliedDateTime").text(appliedJob.applied_Date);
				$("#jobAppliedAlert").removeClass("d-none");
				$("#applyJd").addClass("d-none");
			} else {
				$("#jobAppliedAlert").addClass("d-none");
				$("#applyJd").removeClass("d-none");
			}
			let jobListContainer = document.getElementById('applied-job-list');
			jobListContainer.innerHTML = '';

			jobData.forEach(function(job) {
				let initials = job.jobTitle
					.split(' ')
					.map(word => word.charAt(0))
					.join('');

				let jobCard = `
                <div class="card mb-2">
                    <div class="card-body jd-card-bdy">
                        <div class="d-flex flex-column flex-lg-row">
                            <span class="avatar avatar-text rounded-3">${initials}</span>
                            <div class="row flex-fill mb-0">
                                <div class="col-sm-8">
                                    <h6>${job.jobTitle}</h6>
                                    <div class="d-flex align-items-center">
                                        <p class="text-muted mb-0 me-3 d-flex align-items-center">
                                            <i class="bi bi-geo-alt-fill me-1"></i> <!-- Location Icon -->
                                            <strong></strong> ${job.location}
                                        </p>
                                        <p class="text-muted mb-0 me-3">
                                            <strong>Applied On:</strong> ${job.applied_Date}
                                        </p>
                                        <span class="badge bg-success jd-badge">Applied</span>
                                    </div>
                                </div>
                                <div class="col-sm-4 text-lg-end">
                                    <button class="apply-now-btn jd-btn" onclick="viewJobDetailsPage('${job.requisitionId}')">Job Details</button>
									<button class="apply-now-btn jd-btn" onclick="viewJobLogs('${job.requisitionId}')" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">Track</button>
                                </div>

								<div class="collapse mt-2" id="collapseExample">
								  <div class="card card-body">
								    Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident.
								  </div>
								</div>
                            </div>
							
                        </div>
                    </div>
                </div>
            `;

				jobListContainer.innerHTML += jobCard;
			});
		}
	});
}

function viewJobDetailsPage(requisitionId) {
	let opensJobsTab = document.querySelector('a[href="#candidateJobsTab"]');
	let candidateJobsTabContent = document.getElementById('candidateJobsTab');
	document.querySelectorAll('.nav-link').forEach(tab => tab.classList.remove('active'));
	document.querySelectorAll('.tab-pane').forEach(tabContent => tabContent.classList.remove('active', 'show'));
	opensJobsTab.classList.add('active');
	candidateJobsTabContent.classList.add('active', 'show');
	viewJobDetails(requisitionId);
}

function applyOtherJobs() {
	let opensJobsTab = document.querySelector('a[href="#candidateJobsTab"]');
	let candidateJobsTabContent = document.getElementById('candidateJobsTab');
	document.querySelectorAll('.nav-link').forEach(tab => tab.classList.remove('active'));
	document.querySelectorAll('.tab-pane').forEach(tabContent => tabContent.classList.remove('active', 'show'));
	opensJobsTab.classList.add('active');
	candidateJobsTabContent.classList.add('active', 'show');
}


//Mobile Number validation
var mobValid1;
function mobVal1() {

	var mob = $('#mobilenoid').val();
	var phoneno = /^\d{10}$/;
	if (mob != '') {
		if (phoneno.test(mob)) {

			$("#error9").hide();

			mobValid1 = true;
			return true;
		} else {

			$("#error9").html(
				"please enter  10 digit mobile number  ex:7089451278");
			$("#error9").show();
			mobValid1 = false;
			return false;
		}
	} else {
		$("#error9").html("Mobile No Required");

		mobValid1 = false;
		return false;
	}

}

//Email validation
var wmailValid;

function wmailVal() {
	var mail2 = $('#workmailid').val();
	var mid2 = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;

	if (mail2 != "") {
		if (mid2.test(mail2)) {
			$("#error5").hide();
			wmailValid = true;
			return true;
		} else {
			$("#error5").show();
			$("#error5").html("Please enter a valid email id.");
			wmailValid = false;
			return false;
		}

	} else {
		$("#error5").hide();
		wmailValid = true;
		return true;
	}

}

//Personal Email validation
var pmailValid;

function pmailVal() {
	var mail = $('#personalmailid').val();
	var mid = /^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,4})$/;

	if (mail != "") {
		if (mid.test(mail)) {
			$("#error3").hide();
			pmailValid = true;
			return true;
		} else {
			$("#error3").show();
			$("#error3").html("Please enter a valid email id.");
			pmailValid = false;
			return false;
		}

	} else {
		$("#error3").hide();
		pmailValid = true;
		return true;
	}

}

function candidateSave() {
	var obj = {};
	mobVal1();
	wmailVal();
	pmailVal();

	var validation = true;
	obj.candidateId = $('#candidateId').val();
	obj.firstName = $('#firstnameid').val();
	obj.lastName = $('#lastnameid').val();
	obj.gender = $('#genderid').val();
	obj.dob = $('#dobid').val();
	obj.bloodGroup = $('#bloodgroupid').val();
	obj.maritalStatus = $('#maritalstatusid').val();
	obj.nationality = $('#nationalityid').val();
	obj.fatherName = $('#fatherid').val();
	obj.motherName = $('#motherid').val();
	obj.mobileNo = $('#mobilenoid').val();
	obj.personalEmail = $('#personalmailid').val();
	obj.workEmail = $('#workmailid').val();
	obj.aadharNo = $('#aadharNo').val();
	obj.panNo = $('#panNo').val();
	obj.emergencyMob = $('#emergencyMob').val();
	obj.fileUpload = $('#canimghid').val();
	obj.password = "User@123";

	$('#nameDtls').text(obj.firstName + " " + obj.lastName);
	$('#mobileNoDtls').text(obj.mobileNo);
	$('#emailDtls').text(obj.personalEmail);


	if (obj.firstName == null || obj.firstName == "") {
		toastr.error("First Name Required");
		return;
	}
	if (obj.lastName == null || obj.lastName == "") {
		toastr.error("Last Name Required");
		return;
	}
	if (obj.gender == null || obj.gender == "") {
		toastr.error("Gender Required");
		return;
	}
	if (obj.dob == null || obj.dob == "") {
		toastr.error("Date Of Birth Required");
		return;
	}

	if (validation && mobValid1 && pmailValid && wmailValid) {

		$('.loader').show();
		$("body").addClass("overlay");

		$.ajax({
			type: "POST",
			url: "view-candidate-add-ajax",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.code == "success") {
					toastr.success(response.message);
					getCandidateLists();
					$('#candidateId').val(response.body.candidateId);
					$('#can_Id').html(response.body.candidateId);
					closeJobDetails();
					cancelCandDetails();
					$("body").removeClass("overlay");

				} else {
					toastr.error('Something went wrong');
					$("body").removeClass("overlay");
					/*$('#candMsg').text(response.message);*/
				}
			},
			error: function(data) {
				$('.loader').hide();
				$("body").removeClass("overlay");
				console.log(data);
			}
		})
	}
}

function saveAddress() {
	var valid = true;


	var obj = {};
	var id = $('#candidateId').val();

	obj.candidateId = $('#candidateId').val();
	obj.addressId = $('#addressId').val();
	obj.type = $('#Type').val();
	obj.address = $('#address').val().replace(/["']/g, '');
	obj.country = $('#countryid').val();
	obj.state = $('#stateid').val();
	obj.city = $('#cityid').val();
	obj.pinCode = $('#pinCode').val();

	if (obj.type == null || obj.type == "") {
		toastr.error("Address Type Required");
		return;

	}
	if (obj.address == null || obj.address == "") {
		toastr.error("Address Required");
		return;

	}
	if (obj.country == null || obj.country == "") {
		toastr.error("Country Required");
		return;

	}
	if (obj.state == null || obj.state == "") {
		toastr.error("State Required");
		return;

	}
	if (obj.city == null || obj.city == "") {
		toastr.error("City Required");
		return;

	}
	if (obj.pinCode == null || obj.pinCode == "") {
		toastr.error("Pin Code Required");
		return;

	}


	if (valid) {

		$.ajax({
			type: "POST",
			url: "view-candidate-add-address",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {

				if (response.message == "Success") {
					$("#saveAddress").attr("disabled", true);
					getAddressDetails(id);
					if (response.code == "201") {
						toastr.success('Address Saved Successfully');
					}
					else if (response.code == "200") {
						toastr.success('Address Modified Successfully');
					}
				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}
}

function saveEducation() {
	var valid = true;

	var obj = {};
	var id = $('#candidateId').val();

	obj.candidaateId = $('#candidateId').val();
	obj.educationId = $('#educationId').val();
	obj.qualification = $('#qualification').val();
	obj.institution = $('#institution').val();
	obj.passingYear = $('#dropdownYear').val();
	if (obj.qualification == null || obj.qualification == "") {
		toastr.error("Qualification Required");
		return;
	}
	if (obj.passingYear == null || obj.passingYear == "") {
		toastr.error("Passing Year Required");
		return;

	}
	if (valid) {

		$.ajax({
			type: "POST",
			url: "view-candidate-add-education",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {

				if (response.message == "Success") {
					$("#saveEducationBtn").attr("disabled", true);
					$("#editEducationBtn").attr("disabled", false);
					getEducationDetails(id);
					if (response.code == "201") {
						toastr.success('Education Saved Successfully');
					}
					else if (response.code == "200") {
						toastr.success('Education Modified Successfully');
					}
				}
			},
			error: function(data) {

				console.log(data);
			}
		}) //ajax ends
	}
}

function saveSkill() {
	var valid = true;
	var obj = {};
	var id = $('#candidateId').val();

	obj.candidateId = $('#candidateId').val();
	obj.skillId = $('#skillId').val();
	obj.skills = $('#skills').val();
	obj.skillDesc = $('#skillsDesc').val();
	obj.skillLevel = $('#skillLevel').val();
	obj.experience = $('#skillExp').val();


	if (obj.skills == null || obj.skills == "") {
		toastr.error("Skill Required");
		return;
	}

	if (valid) {
		$.ajax({
			type: "POST",
			url: "view-candidate-add-skills",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				if (response.message == "Success") {
					$("#saveSkillBtn").attr("disabled", true);
					$("#editSkillBtn").attr("disabled", false);
					getSkillsDetails(id);
					if (response.code == "201") {
						toastr.success("Skill Details Saved Successfully");
					}
					else if (response.code == "200") {
						toastr.success("Skill Details Modified Successfully");
					}

					$('#skillId').val("");
				}
			},
			error: function(data) {
				console.log(data);
			}
		});
	}
}

function saveExperiene() {
	var obj = {};
	var id = $('#candidateId').val();

	obj.candidateId = $('#candidateId').val();
	obj.workExperineceId = $('#workExperineceId').val();
	obj.designation = $('#desgId').val();
	obj.organization = $('#orgId').val();
	obj.description = $('#jobDescId').val();
	obj.workFrom = $('#workFromExp1').val();
	obj.workTill = $('#workTillExp1').val();
	obj.workExp = $('#workExp').val();

	if (obj.designation == null || obj.designation == "") {
		toastr.error("Designation is Required");
		return;
	} if (obj.organization == null || obj.organization == "") {
		toastr.error("Organization is Required");
		return;
	}
	if (obj.workExp == null || obj.workExp == "") {
		toastr.error("Work Experience Required");
		return;
	}

	if (obj.workFrom == null || obj.workFrom == "") {
		toastr.error("From Date Required");
		return;
	}
	if (obj.workTill == null || obj.workTill == "") {
		toastr.error("Till Date Required");
		return;
	}

	$.ajax({
		type: "POST",
		url: "view-candidate-add-workExperience",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {

			if (response.message == "Success") {
				getWorkExperienceDetails(id);

				if (response.code == "201") {
					toastr.success("Experience Saved Successfully");
				}
				else if (response.code == "200") {
					toastr.success("Experience Modified Successfully");
				}
			}
		},
		error: function(data) {

			console.log(data);
		}
	});
}

function applyForRequisition(reqId) {
	var candId = "";
	var selectedRows = gridOptions.api.getSelectedRows();

	selectedRows.forEach(function(selectedRow, index) {
		candId = selectedRow.candidateId;
		//selectedRowsString += selectedRow.itemId;
	});
	var obj = {};
	obj.requisitionId = reqId;
	obj.candidateId = candId;

	console.log(obj);

	$('.loader').show();
	$("body").addClass("overlay");

	$.ajax({
		type: "POST",
		url: "view-candidate-apply-requisition",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.code == "Success") {
				$('.loader').hide();
				$("body").removeClass("overlay");
				nextBtnFunction('candidateApplyJobsTab');
				toastr.success(response.message);
				closeJobDetails();
				getAppliedJob(candId);
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				toastr.success(response.message);
			}
		},
		error: function(data) {
			console.log(data);
		}
	})
}

function nextBtnFunction(tablink) {
	const tabElement = document.querySelector(`a.nav-link[href="#${tablink}"]`);

	if (tabElement) {
		const tabTrigger = new bootstrap.Tab(tabElement);
		tabTrigger.show();
	}
}

function enableAddress(status) {
	$("#address").attr('disabled', status);
	$("#countryid").attr('disabled', status);
	$("#stateid").attr('disabled', status);
	$("#cityid").attr('disabled', status);
	$("#pinCode").attr('disabled', status);
	$("#saveAddress").attr("disabled", status);
}
function addEducation() {
	$('#educationId').val('').attr('disabled', false);
	$('#qualification').val('').attr('disabled', false);
	$('#institution').val('').attr('disabled', false);
	$('#dropdownYear').val('').attr('disabled', false);
	$("#saveEducationBtn").attr("disabled", false);
	$("#editEducationBtn").attr("disabled", true);
	gridOptions4.api.deselectAll();
}

function enableEducation(status) {
	$('#educationId').attr('disabled', status);
	$('#qualification').attr('disabled', status);
	$('#institution').attr('disabled', status);
	$('#dropdownYear').attr('disabled', status);
	$("#saveEducationBtn").attr("disabled", status);
	$("#addEducationBtn").attr("disabled", false);
	$("#editEducationBtn").attr("disabled", false);
}

function enableSkill(status) {
	$('#skills').attr("disabled", status);
	$('#skillsDesc').attr("disabled", status);
	$('#skillLevel').attr("disabled", status);
	$('#skillExp').val('').attr("disabled", status);
	$("#addSkillBtn").attr("disabled", false);
	$("#editSkillBtn").attr("disabled", false);
}

function addSkill() {
	$('#skillId').val('');
	$('#skills').val('').attr("disabled", false);
	$('#skillsDesc').val('').attr("disabled", false);
	$('#skillLevel').val('').attr("disabled", false);
	$('#skillExp').val('').attr("disabled", false);
	$("#saveSkillBtn").attr("disabled", false);
	$("#editSkillBtn").attr("disabled", true);
	gridOptions1.api.deselectAll();
}

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgemp').attr('src', '');
	$('#imgemp').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-candidate-upload-file",
		enctype: "multipart/form-data",
		contentType: false,
		data: fileData,
		processData: false,
		cache: false,
		success: function(response) {

		},
		error: function(e) {

		}
	});
}

function deleteFile() {
	$('#fileUpload').val("");
	$('#imgemp').attr('src', '');
	$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	let imageId = $('#canimghid').val();
	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$.ajax({
		type: "POST",
		url: "view-candidate-delete-file?imageName=" + imageId,
		enctype: "multipart/form-data",
		contentType: false,
		/* data        : fileData, */
		processData: false,
		cache: false,
		success: function(response) {
			$('#canimghid').val("");
		},
		error: function(e) {

		}
	});
}

function applyJobUrl(id) {
	//let encodedId = btoa(id);

	let url = `http://localhost:1013/candidates-job-apply/${id}`;

	window.open(url, "_blank");
}


function saveAchivement() {


	var valid = true;
	var obj = {};
	var id = $('#candidateId').val();

	obj.candidateId = $('#candidateId').val();
	obj.awardId = $('#awardId').val();
	obj.awardName = $('#awardName').val();
	obj.awardYear = $('#dropdownAwardYear').val();
	obj.awardDescription = $('#awardDescId').val();

	if (obj.awardName == null || obj.awardName == "") {
		toastr.error("Award Name Required");
		return;
	}

	if ($('#awardDescId').val().length > 1000) {

		toastr.error('Description should be 1000 Characters.');
		valid = false;
		return;
	}


	console.log("obj-->", obj);
	if (valid) {

		$.ajax({
			type: "POST",
			url: "view-candidate-add-award",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {

				if (response.message == "Success") {
					getAwardDetails(id);

					if (response.code == "201") {
						toastr.success('Award & Achievements Saved Successfully');
						$("#saveAchivementBtn").attr("disabled", true);
						$("#editAchivementBtn").attr("disabled", false);
					}
					else if (response.code == "200") {
						toastr.success('Award & Achievements Modified Successfully');
						$("#saveAchivementBtn").attr("disabled", true);
						$("#editAchivementBtn").attr("disabled", false);
					}
				}
			},
			error: function(data) {

				console.log(data);
			}
		})  //ajax ends
	}

}

function enableAchivement(id) {
	$("#saveAchivementBtn").attr("disabled", false);
	$("#awardName").attr("disabled", false);
	$("#dropdownAwardYear").attr("disabled", false);
	$("#awardDescId").attr("disabled", false);
}

function addAchivement() {
	$("#saveAchivementBtn").attr("disabled", false);
	$("#editAchivementBtn").attr("disabled", true);
	$("#awardName").attr("disabled", false).val("");
	$("#dropdownAwardYear").attr("disabled", false).val("");
	$("#awardDescId").attr("disabled", false).val("");
	$("#awardId").val("");
	gridOptions5.api.deselectAll();
}
