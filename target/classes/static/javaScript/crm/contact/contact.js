/* These all ready will be execute when the time of page is loading */
let userId = '';
 $(document).ready(function() {
        $('#customLoad').show();
        
        setTimeout(function() {
            $('#customLoad').hide();
            $('.loader-backdrop').hide();
        }, 4000); 
});
$(document).ready(function() {

	userId = $("#userId").val();
	$("#mySidenavTask").hide();

	var maxField = 5; // Total 5 product fields we add

	var addButton = $('.add_button'); // Add more button selector

	var wrapper = $('.field_wrapper'); // Input fields wrapper



	var fieldHTML = `<div class="form-elements">
		<div class="row">
		<div class="col-md-4">
			<div class="form-group">
				<label>Field:</label> <input type="text" class="form-control"
					id="field" placeholder="field" name="field[]">
			</div>
		</div>
			<div class="col-md-4">
			<div class="form-group">
				<label>Value:</label> <input type="text" class="form-control"
					id="value" placeholder="Value" name="value[]">
			</div>
		</div>
		<div class="col-md-4">
			<div class="form-group">
			<a href="javascript:void(0);" class="remove_button" title="Add field">Remove</a>
			</div>
			</div>
			</div>
		</div>`; //New input field html 

	var x = 1; //Initial field counter is 1

	$(addButton).click(function() {
		//Check maximum number of input fields
		if (x < maxField) {
			x++; //Increment field counter
			$(wrapper).append(fieldHTML);
		}
	});

	//Once remove button is clicked
	$(wrapper).on('click', '.remove_button', function(e) {
		e.preventDefault();
		$(this).parent().closest(".form-elements").remove();
		x--; //Decrement field counter
	});

	$("#date").datetimepicker({
		format: "d-m-Y",
		closeOnDateSelect: true,
		minDate: new Date(),
		timepicker: false,
	});
	$("#accountId").val("");

	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})

	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#toDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})

	$('#toDate').blur(function() {
		$("#toDateCalendar").val($(this).val());
	})

	$("#fromTime").datetimepicker({
		format: 'H:i',
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

	$("#toTime").datetimepicker({
		format: "H:i",
		closeOnDateSelect: false,
		timepicker: true,
		datepicker: false,
		step: 15
	})

	$('.numberonly').keypress(function(e) {

		var charCode = (e.which) ? e.which : event.keyCode

		if (String.fromCharCode(charCode).match(/[^0-9]/g))

			return false;

	});


	$("#email").blur(function() {
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var emailaddress = $("#email").val();
		if (!emailReg.test(emailaddress)) {
			alert("Not a valid email!");
		}

		else {

		}
	});

	$("#secondaryEmail").blur(function() {
		var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
		var emailaddress = $("#secondaryEmail").val();
		if (!emailReg.test(emailaddress)) {
			alert("Not a valid email!");
		}

		else {

		}
	});

	/*------------------Ag-Grid Loaded---------------------*/


	var gridDiv = document.querySelector('#myGridActivity');
	new agGrid.Grid(gridDiv, activityOptions);

	var gridDiv1 = document.querySelector('#activity');
	new agGrid.Grid(gridDiv1, activityOptions);

	var leadgridDiv = document.querySelector('#myGridLead');
	new agGrid.Grid(leadgridDiv, leadgridOptions);

	var pages;
	var pageno = 1;
	var rowData = [];
	leadgridOptions.api.setRowData(rowData);
	agGrid.simpleHttpRequest({
		url: "view-crm-contacts-view-Data?pageno=" + pageno + "&userId=" + userId,
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);

		var len = resp.length;
		$('#totalReq').find('span').html(len);
		leadgridOptions.api.setRowData(resp);
		if (resp.length > 0) {

			$('#totalPageno').val(resp[0].totalPageno);
			pages = resp[0].totalPageno;

		}
		createPagination(pages, pageno);

		$('.loader').hide();
	});

	$('#deleteLead').attr('disabled', true);


	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})
	$('#fromDate').blur(function() {
		$("#fromDateCalendar").val($(this).val());
	})

	$("#endDateCalendar").datetimepicker({
		format: dateFormat,
		closeOnDateSelect: true,
		timepicker: false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})
	$('#toDate').blur(function() {
		$("#endDateCalendar").val($(this).val());
	})

	/*var respDeal = (localStorage.getItem('conDeal')); 
	alert(respDeal)
	console.log("===dataaaaaaaaaaaaa====>"+respDeal);
	if(respDeal)
		viewContactDetails(respDeal);
	
	
	localStorage.setItem('conDeal', "");*/

});


$(function() {
	$("#demo").hide();
	$("#demoSendEmail").hide();
	$("#macroContainer").hide();
	$("#demoCreateTask").hide();
	$("#demoCreateTags").hide();

	$("#demoSetReminder").hide();
	$("#demoMassUpdate").hide();
	$("#demoAddCampaign").hide();
	$("#demoUpdateResponse").hide();
	$("#demoPrintMailingLabels").hide();
	$("#demoMailMerge").hide();
	$("#demoMassConvert").hide();
	$("#demoDeleteAction").hide();
	$("#myCheckbox").click(function() {
		if ($(this).is(":checked")) {
			$("#runMacro").attr("disabled", false);
			$("#sentMail").attr("disabled", false);
			$("#createTask").attr("disabled", false);
			$("#tags").attr("disabled", false)
			$("#action").attr("disabled", false)
		} else {
			$("#runMacro").attr("disabled", true);
			$("#sentMail").attr("disabled", true);
			$("#createTask").attr("disabled", true);
			$("#tags").attr("disabled", true)
			$("#action").attr("disabled", true)
		}
	});



	var id = (localStorage.getItem('contactId'));
	console.log("===dataaaaaaaaaaaaa====>" + id);
	if (id)
		//editLeadInfo(leadId);
		editContactInfo(id)

	localStorage.setItem('contactId', "");
});


function openNavTask() {
	//alert('hello');
	document.getElementById("mySidenavTask").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:420px;";

	document.getElementById("mainTask").style.width = "75%";
}

//Pagination
var pages;
function createPagination(pages, page) {
	if ($("#totalPageno").val() == '') {
		var pages = 10;
	} else {
		var pages = $("#totalPageno").val();
	}

	var str = '<ul>';
	var active;
	var pageCutLow = page - 1;
	var pageCutHigh = page + 1;
	// Show the Previous button only if you are on a page other than the first
	if (page > 1) {
		str += '<li class="page-item previous no"><a onclick="createPagination(pages, ' + (page - 1) + ')">Previous</a></li>';
	}
	// Show all the pagination elements if there are less than 6 pages total
	if (pages < 6) {
		for (let p = 1; p <= pages; p++) {
			active = page == p ? "active" : "no";
			str += '<li class="' + active + '"><a onclick="createPagination(pages, ' + p + ')">' + p + '</a></li>';
		}
	}
	// Use "..." to collapse pages outside of a certain range
	else {
		// Show the very first page followed by a "..." at the beginning of the
		// pagination section (after the Previous button)
		if (page > 2) {
			str += '<li class="no page-item"><a onclick="createPagination(pages, 1)">1</a></li>';
			if (page > 3) {
				str += '<li class="out-of-range"><a onclick="createPagination(pages,' + (page - 2) + ')">...</a></li>';
			}
		}
		// Determine how many pages to show after the current page index
		if (page === 1) {
			pageCutHigh += 2;
		} else if (page === 2) {
			pageCutHigh += 1;
		}
		// Determine how many pages to show before the current page index
		if (page === pages) {
			pageCutLow -= 2;
		} else if (page === pages - 1) {
			pageCutLow -= 1;
		}
		// Output the indexes for pages that fall inside the range of pageCutLow
		// and pageCutHigh
		for (let p = pageCutLow; p <= pageCutHigh; p++) {
			if (p === 0) {
				p += 1;
			}
			if (p > pages) {
				continue
			}

			active = page == p ? "active" : "no";
			str += '<li class="page-item ' + active + '"><a onclick="createPagination(pages, ' + p + ')">' + p + '</a></li>';

		}
		// Show the very last page preceded by a "..." at the end of the pagination
		// section (before the Next button)
		if (page < pages - 1) {
			if (page < pages - 2) {
				str += '<li class="out-of-range"><a onclick="createPagination(pages,' + (page + 2) + ')">...</a></li>';
			}
			str += '<li class="page-item no"><a onclick="createPagination(pages, pages)">' + pages + '</a></li>';
		}
	}
	// Show the Next button only if you are on a page other than the last
	if (page < pages) {
		str += '<li class="page-item next no"><a onclick="createPagination(pages, ' + (page + 1) + ')">Next</a></li>';
		changePagination(page);
	} else if (page <= pages) {
		//str += '<li class="page-item next no"><a onclick="createPagination(pages, '+(page+1)+')">Next</a></li>';
		changePagination(page);
	}
	str += '</ul>';
	// Return the pagination string to be outputted in the pug templates
	document.getElementById('pagination').innerHTML = str;
	return str;

}

function changePagination(page) {
	$('.loader').show();
	var pageno = page;
	agGrid.simpleHttpRequest({
		url: "view-crm-contacts-view-Data?pageno=" + pageno + "&userId=" + userId,
	}).then(function(data) {
		var resp = JSON.parse(data.body[0]);

		var len = resp.length;
		$('#totalReq').find('span').html(len);

		leadgridOptions.api.setRowData(resp);

		if (resp.length > 0) {

			$('#totalPageno').val(resp[0].totalPageno);
			pages = resp[0].totalPageno;

		}
		$('.loader').hide();
	});
}

var pipelineid = "";
function onSelectionChanged() {
	var selectedNodes = leadgridOptions.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	pipelineid = selectedData.map(node => node.pipelineId);
	var selectedRows = leadgridOptions.api.getSelectedRows();
	id = "";
	for (var i = 0; i < selectedRows.length; i++) {
		id = id + '"' + selectedRows[i].pipelineId + '",';
	}
	id = id.substring(0, id.length - 1);
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#add').attr('disabled', true);
		$('#deleteLead').attr('disabled', false);

		$(".actionCls").show();

	} else {
		$('#deleteLead').attr('disabled', true);
		$('#add').attr('disabled', false);

		$(".actionCls").hide();
	}
}
var columnLeadDefs = [{
	headerCheckboxSelection: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true,
	pinned: 'left'

	/*},{
		headerName : "Contact Id",
		field : "contactId",
		pinned : 'left',
		cellRenderer : function(params) {
			console.log(params,'draftttt')
			return '<a onclick=editContactInfo("'
			+ params.data.contactId
			+ '") href="javascript:void(0)">'
			+ params.data.contactId+'</a>';
		},
		width: 200, */

}, {
	headerName: "Contact Name",
	field: "contactname",
	width: 200,
	cellRenderer: function(params) {
		console.log(params, 'draftttt')
		return '<a onclick=viewContactDetails("'
			+ params.data.contactId
			+ '") href="javascript:void(0)">'
			+ params.data.contactname + '</a>';
	},
}, {
	headerName: "Phone",
	field: "contactPhone",
	width: 200,
}, {
	headerName: "Email",
	field: "contactEmail",
	width: 200,
	cellStyle: {
		textAlign: 'left'
	},
}, {
	headerName: "Company",
	field: "accountName",
	width: 200,
}, {
	headerName: "Contact Owner",
	field: "contactOwner",
	width: 200,
}, {
	headerName: "Created Date",
	field: "createdDate",
	width: 200,
},
];

	var leadgridOptions = {
		columnDefs : columnLeadDefs,
		suppressHorizontalScroll: false,
		rowSelection : 'multiple',
		defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 250,
		height : 10
		},
		suppressRowClickSelection : true,
		onSelectionChanged : rowSelect,
	};


var expanded = false;
function showCheckboxes() {
	var checkboxes = document.getElementById("checkboxes");
	if (!expanded) {
		checkboxes.style.display = "block";
		expanded = true;
	} else {
		checkboxes.style.display = "none";
		expanded = false;
	}
}


function showCheckboxesSecond() {
	var checkboxes = document.getElementById("checkboxes2");
	if (!expanded) {
		checkboxes.style.display = "block";
		expanded = true;
	} else {
		checkboxes.style.display = "none";
		expanded = false;
	}
}

function openNav() {

	$('#goalName').val("");
	$('#expectedResult').val("");
	$('#weightage').val("");

	document.getElementById("mySidenav").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:100px;";

	document.getElementById("main_content").style.width = "75%";
}

function closeNav() {
	document.getElementById("mySidenav").style.width = "0";
	document.getElementById("main").style.width = "100%";
}

function openNav1() {

	$('#fromtime').val("");
	$('#totime').val("");

	document.getElementById("mySidenav1").style.cssText = "width: 250px; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px;";

	document.getElementById("main").style.width = "100%";
}

function closeNav1() {
	document.getElementById("mySidenav1").style.width = "0";
	document.getElementById("main").style.width = "100%";
}

//side Nav Ends
var count = 0;
function allCheck() {
	count++;

	if (count == 1) {
		$('.checkCls').prop("checked", true);
	} else {
		count = 0;
		$('.checkCls').prop("checked", false);
	}
}

var txtLen = 0;
function textCount(event) {

	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	$('#' + pId + ' span').empty();
	txtLen = $('#' + id).val().length;
	$('#' + pId + ' span').append(txtLen);
}

// for new button
function newBtn() {
	//	alert('hello');
	$("#add").hide();
	$("#copy").hide();
	$("#deleteLead").hide();
	$("#myGridLead").hide();
	$("#pagination").hide();
	$("#searchRowDiv").hide();
	$("#totalReq").hide();
	$("#statusDiv").hide();
	$("#idDiv").hide();
	$("#collapseFour").hide();
	$("#headingFour").hide();
	$("#contactOwner").val(userId);
	$(".maincontentsec").hide();

	$("#demo").show();

}
// for cancel button
function cancelBtn() {
		$("#add").show();
		$("#copy").show();
		$("#deleteLead").show();
		$("#totalReq").show();
		$("#searchRowDiv").show();
		$("#demo").hide();
        $("#myGridLead").show();
		$("#pagination").show();
		$(".maincontentsec").show();
		$('#pipelineId').val("");
		$('#contactOwner').val("");
		$('#leadSource').val("");
		$('#firstName').val("");
		$('#lastName').val("");
		$('#dealAccountName').val("");
		$('#accountId').val("");
		$('#title').val("");
		$('#referenceContact').val("");
		$('#email').val("");
		$('#department').val("");
		$('#phone').val("");
		$('#homePhone').val("");
		$('#otherPhone').val("");
		$('#fax').val("");
		$('#mobile').val("");
		$('#dateBirth').val("");
		$('#assistant').val("");
		$('#assistPhone').val("");
		$('#emailOpt').val("");
		$('#skypeId').val("");
		$('#secondaryEmail').val("");
		$('#twitter').val("");
		$('#reportingTo').val("");
		
		$('#mailingStreet').val("");
		$('#otherMailing').val("");
		$('#mailingCity').val("");
		$('#otherCity').val("");
		
		$('#otherCity').val("");
		$('#mailingState').val("");
		$('#otherState').val("");
		$('#mailingZip').val("");
		$('#description').val("");
		$('#otherZip').val("");
		$('#mailingCountry').val("");
		$('#otherCountry').val("");
		$('#emailOpt').prop('checked', false);

}




////ends
function addContact() {
	var ratingId = $("input:radio[name=rating]:checked").val();
	var obj = {};
	obj.pipelineId = $('#pipelineId').val();
	obj.contactOwner = $('#contactOwner').val();
	obj.leadSource = $('#leadSource').val();
	obj.firstName = $('#firstName').val();
	obj.lastName = $('#lastName').val();
	obj.accountName = $('#dealAccountName').val();
	obj.accountId = $('#accountId').val();
	obj.title = $('#title').val();
	obj.referenceContact = $('#referenceContact').val();
	obj.email = $('#email').val();
	obj.department = $('#department').val();
	obj.phone = $('#phone').val();
	obj.homePhone = $('#homePhone').val();
	obj.otherPhone = $('#otherPhone').val();
	obj.fax = $('#fax').val();
	obj.mobile = $('#mobile').val();
	obj.dateBirth = $('#dateBirth').val();
	obj.assistant = $('#assistant').val();
	obj.assistPhone = $('#assistPhone').val();
	obj.emailOpt = $('#emailOpt').val();
	obj.skypeId = $('#skypeId').val();
	obj.secondaryEmail = $('#secondaryEmail').val();
	obj.twitter = $('#twitter').val();
	obj.reportingTo = $('#reportingTo').val();

	obj.mailingStreet = $('#mailingStreet').val();
	obj.otherMailing = $('#otherMailing').val();
	obj.mailingCity = $('#mailingCity').val();
	obj.otherCity = $('#otherCity').val();

	obj.otherCity = $('#otherCity').val();
	obj.mailingState = $('#mailingState').val();
	obj.otherState = $('#otherState').val();
	obj.mailingZip = $('#mailingZip').val();
	obj.description = $('#description').val();
	obj.otherZip = $('#otherZip').val();
	obj.mailingCountry = $('#mailingCountry').val();
	obj.otherCountry = $('#otherCountry').val();
	console.log("Contact Data------->>>>>"+JSON.stringify(obj));

	var validation = true;

	if (obj.firstName == null || obj.firstName == "") {
		validation = validationUpdated("First Name Required", "firstName");
	}
	if (obj.lastName == null || obj.lastName == "") {
		validation = validationUpdated("Last Name Required", "lastName");
	}
	if (obj.dealAccountName == null || obj.dealAccountName == "") {
		validation = validationUpdated("Account Name Required", "dealAccountName");
	}
	if (obj.email == null || obj.email == "") {
		validation = validationUpdated("Email Required", "email");
	}
	if (obj.title == null || obj.title == "") {
		validation = validationUpdated("Title Required", "title");
	}
	if (obj.mobile == null || obj.mobile == "") {
		validation = validationUpdated("Mobile No. Required", "mobile");
	}

	if (validation) {
		$.ajax({
			type: "POST",
			url: "view-crm-contacts-add",
			contentType: "application/json",
			data: JSON.stringify(obj),
			success: function(response) {
				console.log(response);
				if (response.message == "Success") {

					cancelBtn()

					$("#messageParagraph").text("Data Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');


					var pages;
					var pageno = 1;
					var rowData = [];
					leadgridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "view-crm-contacts-view-Data?pageno=" + pageno + "&userId=" + userId,
					}).then(function(data) {
						var resp = JSON.parse(data.body[0]);

						var len = resp.length;
						$('#totalReq').find('span').html(len);
						leadgridOptions.api.setRowData(resp);
						if (resp.length > 0) {

							$('#totalPageno').val(resp[0].totalPageno);
							pages = resp[0].totalPageno;

						}
						createPagination(pages, pageno);

						$('.loader').hide();
					});
				}
			},
			error: function(data) {

				console.log(data);
			}
		})
	}

}


// auto complete
function contactNameAutoComplete() {

	var x = $("#contactName").val();
	$
		.ajax({

			type: "GET",
			url: "/pipeline/view-crm-pipeline-contactAutoComplete?id="
				+ x,
			dataType: 'json',
			contentType: 'application/json',
			success: function(response) {
				console.log(response);
				if (response.message == "success") {

					if (response.body.length != 0) {
						console.log("if: " + response);
						$("#contactName").css("background", "#FFF");
						var content = '<ul id="autocomplete-list">';
						for (var i = 0; i < response.body.length; i++) {

							console.log(response)
							content += '<li onClick="selectAutocompleteValue1(\''
								+ response.body[i].contactName
								+ '\',\''
								+ response.body[i].oppertunity
								+ '\',\''
								+ response.body[i].customer
								+ '\',\''
								+ response.body[i].email
								+ '\',\''
								+ response.body[i].phone
								+ '\',\''
								+ response.body[i].city
								+ '\',\''
								+ response.body[i].addressStreet
								+ '\',\''
								+ response.body[i].campanyName
								+ '\',\''
								+ response.body[i].states
								+ '\',\''
								+ response.body[i].country
								+ '\',\''
								+ response.body[i].zip
								+ '\')">'
								+ response.body[i].contactName
								+ '</li>';
						}
						content += '</ul>';
						$("#suggesstion-box1").show();
						$("#suggesstion-box1").html(content);

	}
	}
	}
	});
	}

	function selectAutocompleteValue1(accountId, accountName, custGSTNo, taxType) {

		if (accountId) {
			

			$("#accountId").val(accountId);

			$("#dealAccountName").val(accountName);
			
			$("#search").val(accountName);
			$("#search").attr('data-procat', accountId);
			$("#suggesstion-box1_").hide();
		//	hideShowS();
			//checkForDuplicate(key,counter);

		} else {

			$("#accountId").val("");

			$("#dealAccountName").val("");
			
			$("#search").val("");
			$("#search").attr('data-procat', "");
			$("#suggesstion-box1_").hide();

		}
	}
	
	function viewContactDetails(id){
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest+"view-crm-contacts-detail?id="+id;
		
	}
	
	function cancelDeleteModalBtn(){
		$("#deleteModal").hide();
	}
	
    var deleteId = "";
	function rowSelect() {
		var selectedRows = leadgridOptions.api.getSelectedRows();
		deleteId = "";

		for (var i = 0; i < selectedRows.length; i++) {
			deleteId = deleteId + '"' + selectedRows[i].contactId + '",';
			// deleteId = deleteId  + selectedRows[i].vendorId + ',';
		}
		deleteId = deleteId.substring(0, deleteId.length - 1);
		console.log(deleteId)
		var rowCount = 0;

		selectedRows.forEach(function(selectedRow, index) {
			rowCount = rowCount + 1;
		});

		if (rowCount > 0) {
			$('#deleteLead').attr("disabled", false);

		} else {
			$('#deleteLead').attr("disabled", true);
		}
	}
	
    
  function deleteLeadInfo() {
	$("#deleteModal").show();
 }
 
  function deleteLeadOnclick() {
        $('.loader').show();
		$.ajax({
			type : "GET",
			url : "view-crm-contacts-delete-id?id=" + deleteId,
			success : function(response) {
				if (response.code == "Success") {
				 	var pages;
					var pageno = 1;
					var rowData = [];
					leadgridOptions.api.setRowData(rowData);
					agGrid.simpleHttpRequest({
						url: "view-crm-contacts-view-Data?pageno=" + pageno+"&userId="+userId,
					}).then(function (data) {
						var resp = JSON.parse(data.body[0]);
				
						var len = resp.length;
						$('#totalReq').find('span').html(len);
						leadgridOptions.api.setRowData(resp);
						if (resp.length > 0) {
				
							$('#totalPageno').val(resp[0].totalPageno); 
							pages = resp[0].totalPageno;
				
						}
						createPagination(pages, pageno);
				
						$('.loader').hide();
				     });
				     $("#deleteModal").hide();
				}
			},
			error: function(data) {
				console.log(data);
			}

		});

		$('#deleteLead').attr("disabled", true);
	}
	
function onQuickFilterChanged() {
		leadgridOptions.api
				.setQuickFilter(document.getElementById('quickFilter').value);
	}

function selectAutocompleteValue1(contactName, oppertunity, customer,
	email, phone, city, addressStreet, campanyName, states, country,
	zip) {
	if (contactName) {
		$("#contactName").val(contactName);
		$("#oppertunity").val(campanyName);
		$("#customer").val(customer);
		$("#email").val(email);
		$("#phone").val(phone);
		$("#city").val(city);
		$("#addressStreet").val(addressStreet);
		$("#campanyName").val(campanyName);
		$("#states").val(states);
		$("#country").val(country);
		$("#zip").val(zip);
		$("#contactName").attr('data-procat', contactName);
		$("#suggesstion-box1").hide();
	} else {
		$("#contactName").val("");
		$("#oppertunity").val("");
		$("#customer").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#city").val("");
		$("#addressStreet").val("");
		$("#campanyName").val("");
		$("#states").val("");
		$("#country").val("");
		$("#zip").val("");
		$("#contactName").attr('data-procat', "");
		$("#suggesstion-box1").hide();
	}
}






function showField() {
	$('#searchAccountName').toggle();
}
function showField1() {
	$('#searchAssistant').toggle();
}
function showField2() {
	$('#searchAsstPhone').toggle();
}
function showField3() {
	$('#searchContactName').toggle();
}
function showField4() {
	$('#searchContactOwner').toggle();
}
function showField5() {
	$('#searchCreatedBy').toggle();
}
function showField6() {
	$('#searchdateBirth').toggle();
}
function showField7() {
	$('#searchdepartment').toggle();
}
function showField8() {
	$('#searchEmail').toggle();
}
function showField10() {
	$('#searchEmailOptOut').toggle();
}
function showField11() {
	$('#searchFax').toggle();
}
function showField12() {
	$('#searchFirstName').toggle();
}
function showField13() {
	$('#searchHomePhone').toggle();
}
function showField15() {
	$('#searchLastName').toggle();
}
function showField16() {
	$('#searchMailingState').toggle();
}
function showField17() {
	$('#searchMailingCity').toggle();
}
function showField18() {
	$('#searchMailingCountry').toggle();
}
function showField19() {
	$('#searchLeadSource').toggle();
}
function showField20() {
	$('#searchMailingStreet').toggle();
}
function showField21() {
	$('#searchMobile').toggle();
}
function showField22() {
	$('#searchMailingZip').toggle();
}
function showField23() {
	$('#searchOtherCity').toggle();
}
function showField24() {
	$('#searchOtherCountry').toggle();
}
function showField25() {
	$('#searchOtherPhone').toggle();
}
function showField26() {
	$('#searchOtherState').toggle();
}
function showField27() {
	$('#searchOtherStreet').toggle();
}
function showField28() {
	$('#searchSecondaryEmail').toggle();
}
function showField29() {
	$('#searchSkypeID').toggle();
}
function showField32() {
	$('#searchOtherZip').toggle();
}
function showField33() {
	$('#searchTitle').toggle();
}
function showField34() {
	$('#searchTwitter').toggle();
}
function showField35() {
	$('#searchPhone').toggle();
}
function showField36() {
	$('#searchReportingTo').toggle();
}
function filter() {

	var obj = {};

	//obj.pipelineId = $('#pipelineId').val();
	obj.contactOwner = $('#searchContactOwner').val();
	obj.leadSource = $('#searchLeadSource').val();
	obj.firstName = $('#searchFirstName').val();
	obj.lastName = $('#searchLastName').val();
	obj.accountName = $('#searchAccountName').val();
	obj.title = $('#searchTitle').val();
	obj.email = $('#searchEmail').val();
	obj.department = $('#searchdepartment').val();
	obj.phone = $('#searchPhone').val();
	obj.homePhone = $('#searchHomePhone').val();
	obj.otherPhone = $('#searchOtherPhone').val();
	obj.fax = $('#searchFax').val();
	obj.mobile = $('#searchMobile').val();
	obj.dateBirth = $('#searchdateBirth').val();
	obj.assistant = $('#searchAssistant').val();
	obj.assistPhone = $('#searchAsstPhone').val();
	obj.emailOpt = $('#searchEmailOptOut').val();
	obj.skypeId = $('#searchSkypeID').val();
	obj.secondaryEmail = $('#searchSecondaryEmail').val();
	obj.twitter = $('#searchTwitter').val();
	obj.reportingTo = $('#searchReportingTo').val();

	obj.mailingStreet = $('#searchMailingStreet').val();
	obj.otherMailing = $('#searchOtherStreet').val();
	obj.mailingCity = $('#searchMailingCity').val();
	obj.otherCity = $('#searchOtherCity').val();

	obj.otherCity = $('#searchOtherCity').val();
	obj.mailingState = $('#searchMailingState').val();
	obj.otherState = $('#searchOtherState').val();
	obj.mailingZip = $('#searchMailingZip').val();
	obj.description = $('#description').val();
	obj.otherZip = $('#searchOtherZip').val();
	obj.mailingCountry = $('#searchMailingCountry').val();
	obj.otherCountry = $('#searchOtherCountry').val();
	obj.createdBy = $('#searchCreatedBy').val();


	console.log(obj);
	console.log("response of obj----" + JSON.stringify(obj));

	$.ajax({
		type: "POST",
		url: "view-crm-contacts-view-Data-filter",
		contentType: "application/json",
		async: false,
		data: JSON.stringify(obj),
		success: function(response) {

			if (response.message == "Success") {
				console.log(response);

				div = '';
				$("#allDetails").empty();
				for (var i = 0; i < response.body.length; i++) {
					//	var im=response.body[i].imageName;
					//alert(im)<img src="'+im+'" width="80" alt="User" class="mr-3 img-thumbnail" />
					var img = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> </a>';
					var fullName = response.body[i].firstName + '&nbsp' + response.body[i].lastName;

					var oim = response.body[i].ownerImage;
					var oimg = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="' + oim + '" width="40" alt="User" class="mr-3 img-thumbnail" /></a>';

					id = response.body[i].pipelineId;
					div = div + '<div class="card mb-2"><div class="card-body p-2 p-sm-3 d-flex"><div class="col-md-8">'
						+ '<div class="media forum-item d-flex">'
						+ '<div class="d-flex flex-column chk">'
						+ '<input type="checkbox" id="myCheckbox" name="myCheckbox" class="checkbox_check mr-5" value="' + id
						//+ '" onclick=clickCheckBox("'+id
						+ '" onclick=clickCheckBox("' + id + '","' + response.body[i].phone
						+ '")>'
						+ '<a href="#" data-toggle="collapse" onclick=editContactInfo("' + id
						+ '")><i class="fa fa-edit fa-icon img-hover" aria-hidden="true" '
						+ '></i></a><div>'
						+ '<a href="#" data-toggle="collapse" onclick=deleteContact("' + id
						+ '")><i class="fa fa-trash fa-icon img-hover" aria-hidden="true" '
						+ '></i></a></div></div>'
						//+ img
						+ '<div>'
						+ '<div class="u_name">'
						+ '<a href="#" onclick=viewContactDetails("' + id + '")>'
						+ fullName
						+ '</div>'
						+ '<div class="d-flex1">'
						+ '<span>Account Name : '
						+ '<u>'
						+ response.body[i].accountName
						+ '</u></span><span> | </span><span>Email : '
						+ '<u>'
						+ response.body[i].email
						+ '</u></span> <span> | </span><span>Phone : <u>'
						+ response.body[i].phone
						+ '</u></span> <span> | </span><span>Executive : '
						+ '<u>'
						+ response.body[i].contactOwner
						+ '</u></div> </div> </div></div>'
						+ '<div class="col-md-4"><div class="media forum-item d-flex">'
						//+ '<a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="40" alt="User" class="mr-3 img-thumbnail" />'
						+ oimg
						+ '</a> <div> <div>'
						+ response.body[i].contactOwner
						+ '</div><div>'
						+ response.body[i].createdDate
						+ ' '
						+ response.body[i].createdTime
						+ '</div></div></div></div>'
						+ '</div></div>';

					//div = xyx;
					//$("#allDetails").append(div);

				}
				$("#allDetails").append(div);


				$("#runMacro").attr("disabled", true);
				$("#sentMail").attr("disabled", true);
				$("#createTask").attr("disabled", true);
				$("#tags").attr("disabled", true);

			}

		},
		error: function(data) {
		}
	});

}




//editAccountInfo
// Edit & stage change 
function editContactInfo(id) {

	$.ajax({
		type: "GET",
		url: "view-crm-contacts-edit?id=" + id,
		async: false,
		success: function(response) {
			console.log('response on edit----------' + JSON.stringify(response.body.pipelineId));
			if (response.message == "Success") {

				newBtn();
				var birthDate = "";
				var birthday = "";
				if (response.body.dateBirth) {
					birthDate = response.body.dateBirth;
					birthday = birthDate.split(' ')[0];
				}


				$("#pipelineHeadId").html(response.body.pipelineId);
				$("#pipelineId").val(response.body.pipelineId);
				$("#contactOwner").val(response.body.contactOwner);
				$("#leadSource").val(response.body.leadSource);
				$("#firstName").val(response.body.firstName);
				$('#lastName').val(response.body.lastName);
				$('#accountId').val(response.body.accountId);
				$('#dealAccountName').val(response.body.accountName);
				$('#title').val(response.body.title);

				$('#referenceContact').val(response.body.referenceContact);

				$('#department').val(response.body.department);
				$('#email').val(response.body.email);
				$('#phone').val(response.body.phone);
				$('#homePhone').val(response.body.homePhone);
				$('#otherPhone').val(response.body.otherPhone);
				$('#fax').val(response.body.fax);
				$('#mobile').val(response.body.mobile);
				$('#dateBirth').val(birthday);
				$('#assistant').val(response.body.assistant);
				$('#assistPhone').val(response.body.assistPhone);


				var emailValue = response.body.emailOpt;
				if (emailValue == 'on') {
					console.log('emailOpt-----------' + response.body.emailOpt);
					$('#emailOpt').prop('checked', true);
				} else {
					$('#emailOpt').prop('checked', false);
				}
				//let index = response.body.emailOpt;
				//$('#emailOpt' + index).prop('checked', true);
				$('#skypeId').val(response.body.skypeId);
				$('#secondaryEmail').val(response.body.secondaryEmail);
				$('#twitter').val(response.body.twitter);
				$('#reportingTo').val(response.body.reportingTo);
				$('#mailingStreet').val(response.body.mailingStreet);
				$('#otherMailing').val(response.body.otherMailing);
				$('#mailingCity').val(response.body.mailingCity);
				$('#otherCity').val(response.body.otherCity);
				$('#mailingState').val(response.body.mailingState);
				$('#otherState').val(response.body.otherState);
				$('#mailingZip').val(response.body.mailingZip);
				$('#otherZip').val(response.body.otherZip);
				$('#mailingCountry').val(response.body.mailingCountry);
				$('#otherCountry').val(response.body.otherCountry);
				$('#description').val(response.body.description);
				$('#createdDate').val(response.body.createdDate);

				//for stage active status

			}
		}
	})
}

var activityDefs = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	sortable: false,
	filter: false,
	resizable: true,
	width: 20
}, {
	headerName: "Activity Name",
	field: "operationName"
}, {
	headerName: "User Name",
	field: "operationBy"
}, {
	headerName: "Created Date",
	field: "operationOn",
	cellStyle: {
		textAlign: 'center'
	}

}];

var activityOptions = {
	columnDefs: activityDefs,

	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 420
	}

};

function agGridActivity(accountId) {
	agGrid.simpleHttpRequest({
		url: 'view-crm-pipeline-activity-through-ajax?id=' + accountId
	}).then(function(data) {
		activityOptions.api.setRowData(data);
	});
}

function getAccountList() {
	var search = $("#dealAccountName").val();
	if (search) {

		$.ajax({
			type: "POST",
			url: "view-crm-tasks-get-account-list",
			dataType: 'json',
			contentType: 'application/json',
			data: search,
			success: function(response) {
				if (response.code == "Success") {
					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" style=" font-weight:100px; font-size:14px; color:#ccc; color: black; background:#f0f3f1">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue1(\''
								+ response.body[i].custId
								+ '\',\''
								+ response.body[i].custName
								+ '\',\''
								+ response.body[i].custGSTNo
								+ '\',\''
								+ response.body[i].taxType
								+ '\')">'
								+ response.body[i].custName
								+ '</li>';
						}
						content += '</ul>';
						console.log("content " + content)
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);

					} else {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1">';
						content += '<li>'
							+ "" + '</li>';
						content += '</ul>';
						$("#suggesstion-box1_").show();
						$("#suggesstion-box1_").html(content);
					}
				}
			},
			error: function(data) {
				console.log(data);
			}
		})
	}

}

function selectAutocompleteValue1(accountId, accountName, custGSTNo, taxType) {

	if (accountId) {


		$("#accountId").val(accountId);

		$("#dealAccountName").val(accountName);
		$("#search").val(accountName);
		$("#search").attr('data-procat', accountId);
		$("#suggesstion-box1_").hide();

	}else {

		$("#accountId").val(accountId);

		$("#dealAccountName").val(accountName);

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box1_").hide();

	}
	
}

function viewContactDetails(id) {

	var baseUrl = (window.location).href;
	var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);

	window.location.href = rest + "view-crm-contacts-detail?id=" + id;
}


function deleteLeadInfo() {
	$("#deleteModal").show();

}
function cancelDeleteModalBtn() {
	$("#deleteModal").hide();

}

function deleteLeadOnclick() {
	$.ajax({
		type: "GET",
		url: "view-crm-contacts-delete-id?id=" + pipelineid,
		success: function(response) {

			if (response.message == "Success") {
				console.log(response);
				location.reload();

			}
		}
	});
}

function onQuickFilterChanged() {
	var quickFilterValue = $('#quickFilter').val();
	leadgridOptions.api.setQuickFilter(quickFilterValue);
	updateTotalTaskCount();
}

function cancelBar() {
	var closeKey = $('#closeKey');
	closeKey.css('display', $('#quickFilter').val() ? 'block' : 'none');
}

function resetQuickFilter() {
	leadgridOptions.api.setQuickFilter(null);
	$('#quickFilter').val('');
	$('#closeKey').css('display', 'none');
	updateTotalTaskCount();
}

function updateTotalTaskCount() {
	var displayedRowCount = leadgridOptions.api.getDisplayedRowCount();
	$('#totalReq span').html(displayedRowCount);
}

