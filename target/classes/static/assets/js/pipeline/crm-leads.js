

$(document).ready(function() {
	

	 $("input[name=ReminderYesOrNo]:radio").click(function () {
	        if ($('input[name=ReminderYesOrNo]:checked').val() == "Yes") {
	            $('.reminderBtn').show();

	        } else if ($('input[name=ReminderYesOrNo]:checked').val() == "No") {
	            $('.reminderBtn').hide();
	            $('#reminderDateid').val("");
	            $('#reminderTime').val("");
	            $('#taskAlertBy').val("");

	        }
	    });
	
    $(".addRowMacro").click(function() {
        var fieldName = $("#fieldName").val();
        var fieldValue = $("#fieldValue").val();
        var ligne = "<tr id='addMacroRow'><td class='checkedCls'><input type='checkbox' name='select'></td><td class='fieldName'>" + fieldName + "</td><td class='fieldValue'>" + fieldValue + "</td></tr>";
        $("table.rowDataTable").append(ligne);
    });
    $(".deleteRowMacro").click(function() {
        $("table.rowDataTable").find('input[name="select"]').each(function() {
            if ($(this).is(":checked")) {
                $(this).parents("table.rowDataTable tr").remove();
            }
        });
    });
    
    CKEDITOR.replace('commentck', {
    	enterMode: CKEDITOR.ENTER_BR, 
     	height: 150,
     	removePlugins: 'wsc',
     	// config.enterMode = CKEDITOR.ENTER_BR,
     	scayt_autoStartup: true,
     	scayt_maxSuggestions: 3
     	});
    
    
});

function saveCampaignInfo(){
	var dataset = [];
	console.log(searchIDs);
	for (let i = 0; i < searchIDs.length; ++i) { 
	   item = {};
	   item['leadId']   =	searchIDs[i];
	   item['campaignType']   =	$("#campaignType").val();
	   item['campaignName']   =	$("#campaignName").val();
	   item['campaignStatus']   =	$("#campaignStatus").val();
	   item['startDate']   =	$("#startDate").val();
	   item['endDate']   =	$("#endDate").val();
	   item['campaignId']   =	$("#campaignId").val();
	   item['campaignOwner']   =	$("#campaignOwner").val();
	   item['budgetedCost']   =	$("#budgetedCost").val();
	   item['actualCost']   =	$("#actualCost").val();
	   item['expectedResponse']   =	$("#expectedResponse").val();
	   item['numberSent']   =	$("#numberSent").val();
	   item['campExpectRevenue']   =	$("#campExpectRevenue").val();
	   item['description']   =	$("#description").val();
	   dataset.push(item);
	   
	}
	
	console.log('dataset for campaign------'+JSON.stringify(dataset));
	saveCampaign(dataset);
}

function saveCampaign(dataset) {
 		$.ajax({
			type : "POST",
			url : "view-crm-leads-save-campaigns",
			contentType : "application/json",
			data : JSON.stringify(dataset),
			success : function(response) {
				if (response.message == "Success") {
					
					$("#messageParagraph").text(
					"Data Saved Successfully");
					
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#myGrid").show();
					$("#demo").hide();
					$("#new1").show();
					$("#demo1").hide();
					$("#delete1").show();
					$("#totalReq").show();
					$("#searchRowDiv").show();
					$('#myModalAddCampaign').modal('hide');
					
					
				}
			},
			error : function(datas) {
			}
		})

	}

function addMacroInfo(){
	var dataset = [];
	console.log(searchIDs);
	
	var emailType = $("input:radio[name=macroEmailType]:checked").val();
	  
		   $("#tbodyMacro > tr").each(function(){
			   for (let i = 0; i < searchIDs.length; ++i) {
			   	   item = {}; 
				   item['leadId']   =	searchIDs[i];
				   item['toMail']    =	emails[i];
				   item['macroName']    =	$("#macroName").val();
				   item['macroDescription']    =	$("#macroDescription").val();
				   item['emailType']    =	$("#emailType").val();
				   item['fieldName']      =		$(this).find(".fieldName").html(); 
				   item['fieldValue']      =	$(this).find(".fieldValue").html(); 
				   
				   dataset.push(item);
			   }
			
		   });
	
	
	console.log('data set for add row------------'+JSON.stringify(dataset))
	saveMacro(dataset);
}



function saveMacro(dataset) {
 		$.ajax({
			type : "POST",
			url : "view-crm-leads-save-macro",
			contentType : "application/json",
			data : JSON.stringify(dataset),
			success : function(response) {
				if (response.message == "Success") {
					
					$("#messageParagraph").text(
					"Data Saved Successfully");
					
					
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#myGrid").show();
					$("#demo").hide();
					$("#new1").show();
					$("#demo1").hide();
					$("#delete1").show();
					$("#totalReq").show();
					$("#searchRowDiv").show();
					
				}
			},
			error : function(datas) {
			}
		})

	}

function addTagsInfo(){
	var dataset = [];
	console.log(searchIDs);
	var isOverWrite = $("input:checkbox[name=IsOverwriteExistingTags]:checked").val();
 	for (let i = 0; i < searchIDs.length; ++i) {
	   item = {};
	   item['leadId']   =	searchIDs[i];
	   item['tagsName']   =	$("#tagsName").val();
	   item['isOverWrite']   =	isOverWrite;
	   dataset.push(item);
	}
	
	console.log('dataset for tags------'+JSON.stringify(dataset));
	//return false;
	saveTags(dataset);
}


function saveTags(dataset) {
 		$.ajax({
			type : "POST",
			url : "view-crm-leads-save-tags",
			contentType : "application/json",
			data : JSON.stringify(dataset),
			success : function(response) {
				if (response.message == "Success") {
					
					$("#messageParagraph").text(
					"Data Saved Successfully");
					
					
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$("#myGrid").show();
					$("#demo").hide();
					$("#new1").show();
					$("#demo1").hide();
					$("#delete1").show();
					$("#totalReq").show();
					$("#searchRowDiv").show();
					
					$('#myModalAddTags').modal('hide');
				/* 	agGrid.simpleHttpRequest({
						url : "manage-job-card"
					}).then(function(data) {
						var len = data.length;
						$('#totalReq').find('span').html(len);
						gridOptions.api.setRowData(data);
					})
					//location.reload();
					gridOptions1.api.setRowData([]); */
				}
			},
			error : function(datas) {
			}
		})

	} 


function saveMultiFileForLead(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;
	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");

	if (extension[1] == "jpg" || extension[1] == "png") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx"){
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	}else if (extension[1] == "doc" || extension[1] == "dox" || extension[1] == "docx"  ){
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	}else{
		var LightImg = "<div class='uploadicon position-l'> </div>";
	}
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
}

function addMailInfo(){
 	console.log('emails--------------',emails);
	var dataset = [];
	  item = {};
	console.log(searchIDs);
	for (let i = 0; i < searchIDs.length; ++i) {
		
		var imageValid = true;
		var uploadList = [];
		$("#doctbodyDataforcontact > tr").each(function() {
			var uFile = $(this).find(".document")[0].files[0];
			var fileName = $(this).find(".document").val();
			var data = [];
			var x = [];
			if (fileName != '' && fileName != 'undefined' && fileName != null) {
				var lastIndex = fileName.lastIndexOf("\\");
				if (lastIndex >= 0) {
					fileName = fileName.substring(lastIndex + 1);
				}
				var reader = new FileReader();
				reader.readAsDataURL(uFile);

				reader.onload = function() {
					data = reader.result.split(",");
					x.push(data[1]);
				};
			}else{
				if ($("#vendorRfqId").val()) {
					fileName = $(this).find(".uploadHidCls").val(); 					 
 				}else{
 					 x = []; 
 				}
					
			}
			uploadData = {};
			uploadData['documnentName'] = $("#documentName").val();
			uploadData['documentFile'] = x;
			uploadData['fileName'] = fileName;
			uploadList.push(uploadData);
			

		});
		
		setTimeout(function() {
	 
	   item['leadId']   =	searchIDs[i];
		item['fromEmail']    =	$("#fromEmail").val();
		item['toMail']    =	emails[i];
		item['mailSubject']    =	$("#mailSubject").val();
		item['commentck'] = CKEDITOR.instances.commentck.getData();
		item['docName']  =	 $("#docName").val();
		item['documentList']  =	 uploadList;
		//dataset.push(item);
		saveMail(item);
		
		}, 1000)
	}
	
	console.log('dataset for task------'+JSON.stringify(item));
	saveMail(dataset);
}


function saveMail(item) {
	console.log("employee document11111111111---------"+JSON.stringify(item));
	console.log(item);
	$.ajax({
		type : "POST",
		url : "view-crm-contacts-add-emails-ajax",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(item),
		success : function(response) {
	
			if (response.message == "success") {
				$('#myModalAddEmail').modal('hide');
				closeModelEmail();
				location.reload();
			} else {

			
			}
		},
		error : function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	}) 
}
	
function saveMultiFile(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;
	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
 	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var extension = fileName.split(".");

	var iURL = URL.createObjectURL(uFile);
	$("#uploadedBillDiv_" + counter).html("");

	if (extension[1] == "jpg" || extension[1] == "png") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-picture-o'></i></a></div>";
	} else if (extension[1] == "pdf") {
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-pdf-o'></i> </a></div>";
	} else if (extension[1] == "xls" || extension[1] == "xlsx"){
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
	}else if (extension[1] == "doc" || extension[1] == "dox" || extension[1] == "docx"  ){
		var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-word-o'></i></a></div>";
	}else{
		var LightImg = "<div class='uploadicon position-l'> </div>";
	}
	$("#uploadedBillDiv_" + counter).html(LightImg);
	$("#imageName_" + counter).html(fileName);
	
	
 $.ajax({
		type : "POST",
		url : "view-crm-leads-upload-file",
		enctype : "multipart/form-data",
		//contentType : false,
		data : fileName,
		processData : false,
		cache : false,
		success : function(response) {

		},
		error : function(e) {

		}
	}); 
}


function openNavTask() { 
	document.getElementById("mySidenavTask").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:420px;";

	document.getElementById("mainTask").style.width = "75%";
}

$(document).ready(function () {
	$("#email").blur(function() 
			 {
			  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;  
			  var emailaddress = $("#email").val();
			  if(!emailReg.test(emailaddress)) {
				  alert("Not a valid email!");
			  }
			      
			  else{
				  
			  }  
			 });
	
	$("#secondaryEmail").blur(function() 
			 {
			  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;  
			  var emailaddress = $("#secondaryEmail").val();
			  if(!emailReg.test(emailaddress)) {
				  alert("Not a valid email!");
			  }
			      
			  else{
				  
			  }   
			 });
	
	
	
	
	
	 $("input[name=ReminderYesOrNo]:radio").click(function () {
	        if ($('input[name=ReminderYesOrNo]:checked').val() == "Yes") {
	            $('#isReminderOnDiv').show();

	        } else if ($('input[name=ReminderYesOrNo]:checked').val() == "No") {
	            $('#isReminderOnDiv').hide();
	            $('#reminderDateid').val("");
	            $('#reminderTime').val("");
	            $('#taskAlertBy').val("");


	        }
	    });
	
	
	var dateFormat = localStorage.getItem("dateFormat");
	//Personal Details DOB Date
	$("#dobCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#dueDateid').val($(this).val());
	})
	
	$('#dueDateid').blur(function(){
		$("#dobCalendar").val($(this).val());
	})
	
	
	$("#startDateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#startDate').val($(this).val());
	})
	
	$('#startDate').blur(function(){
		$("#startDateCalendar").val($(this).val());
	})
	
	
	$("#endDateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#endDate').val($(this).val());
	})
	
	$('#endDate').blur(function(){
		$("#endDateCalendar").val($(this).val());
	})
	
	
	
	 //Personal Details DOB Date
	$("#reminderCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#reminderDateid').val($(this).val());
	})
	
	$('#reminderDateid').blur(function(){
		$("#reminderCalendar").val($(this).val());
	}) 
	
	$("#dateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#dueDate').val($(this).val());
	})

	$('#dueDate').blur(function() {
		$("#dateCalendar").val($(this).val());
	})
	
	
	
	 $("#toDateCalendarTime").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step : 15
	}).on("change", function() {
		$('#reminderTime').val($(this).val());
	})

	$('#reminderTime').blur(function() {
		$("#toDateCalendarTime").val($(this).val());
	}) 
	
	
		
	
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

	$(addButton).click(function () {
		//Check maximum number of input fields
		if (x < maxField) {
			x++; //Increment field counter
			$(wrapper).append(fieldHTML);
		}
	});

	//Once remove button is clicked
	$(wrapper).on('click', '.remove_button', function (e) {
		e.preventDefault();
		$(this).parent().closest(".form-elements").remove();
		x--; //Decrement field counter
	});
});


$(document).ready(function(){
	
	
	var gridDiv = document.querySelector('#myGridUpdateField');
	new agGrid.Grid(gridDiv, gridOptions);
	
	
	var dateFormat = localStorage.getItem("dateFormat");
	$("#fromDateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#fromDate').val($(this).val());
	})
	$('#fromDate').blur(function(){
		$("#fromDateCalendar").val($(this).val());
	})

	$("#endDateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#toDate').val($(this).val());
	})
	$('#toDate').blur(function(){
		$("#endDateCalendar").val($(this).val());
	})
	
	
})
var columnDefs = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	},
	{
	headerName : "Serial No",
	field : "goalId",
	width :150,
	cellRenderer : function(params) {
		if (params.data.goalId) {

			return '<a onclick=editReimbruseimentTravel("'
					+ params.data.goalId
					+ '") href="javascript:void(0)">'
					+ params.data.goalId + '</a>';
		} else {
			return '<a onclick=editReimbruseimentTravel("'
					+ params.data.goalId
					+ '") href="javascript:void(0)">'
					+ params.data.goalId + '</a>';
		}
	}
	},{
		headerName : "Field Name",
		field : "goalName",
		cellStyle : {
			textAlign : 'left'
		},
		width :457,
	}, {
		headerName : "New Value",
		field : "goalDesc",
		cellStyle : {
			textAlign : 'left'
		},
			width :600,
	}];
	



$(function () {
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
	//$("#action").attr("disabled", true)
  /*   $("#myCheckbox").click(function () {
        if ($(this).is(":checked")) {
        	alert("warn")
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
    }); */
    
     if ($(".checkbox_check").is(":checked")) {
     } 
    
  /*   $('input[type="checkbox"]').click(function(){
        if($(this).prop("checked") == true){
            alert("Checkbox is checked.");
        }
        else if($(this).prop("checked") == false){
            alert("Checkbox is unchecked.");
        }
    });
        */ 
    
});

var searchIDs=[];
var emails=[];

function clickCheckBox(id,email){
	
		emails.push(email);
		     searchIDs = $("#allDetails input:checkbox:checked").map(function(){
		      return $(this).val();
		    }).get(); // <----
		    console.log(searchIDs.length);
		    var checkedLength=searchIDs.length;
		    if(checkedLength > 0){
		    	    $("#runMacro").attr("disabled", false);
		   			$("#sentMail").attr("disabled", false);
		   			$("#createTask").attr("disabled", false);
		   			$("#tags").attr("disabled", false);
		   			$("#action").attr("disabled", false);
		   			$(".actionCls").show();
		        } else {
		        	$("#runMacro").attr("disabled", true);
		     		$("#sentMail").attr("disabled", true);
		     		$("#createTask").attr("disabled", true);
		     		$("#tags").attr("disabled", true);
		     		$("#action").attr("disabled", true);
		     		$(".actionCls").hide();
		        }
		
}

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


function showCheckboxesSecond(){
	 var checkboxes = document.getElementById("checkboxes2");
	  if (!expanded) {
	    checkboxes.style.display = "block";
	    expanded = true;
	  } else {
	    checkboxes.style.display = "none";
	    expanded = false;
	  }
}

	//Starts Side Nav
	/* function openNav() {

		$('#lostReason').val("");

		document.getElementById("mySidenav").style.cssText = "width: 250px; position: absolute; right:-10px; overflow: hidden; height:auto; top:0px;";

		document.getElementById("main").style.width = "100%";
	} */
	
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
	

	const gridOptions = {
		columnDefs : columnDefs,
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height :10
		},
		rowSelection : 'multiple',
		suppressRowClickSelection : true,
		getRowNodeId : function(data) {
			return data.customerId;
		}
	};
	
	
	$(document).ready(function () {    
	    
        $('.numberonly').keypress(function (e) {    

            var charCode = (e.which) ? e.which : event.keyCode    

            if (String.fromCharCode(charCode).match(/[^0-9]/g))    

                return false;                        

        });    

    }); 
	
	function runMacro(){
 		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").show();
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
	}
	
	function sendEmail(){
	
$('#myModalAddEmail').modal('show');
		var emailString = emails.join(",");
		$("#toMail").val(emailString);
		$("#toMail").attr('disabled','disabled');
		
		
 		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").show();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").show();
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
	}
	
	
	
	
	function createTask(){
		
		$('#myModalAddTask').modal('show');
 		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		$("#myGrid").hide();
		$(".container").show();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").show();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
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
	}
	
	function closeModelTags(){ 
	
		$('#myModalAddTags').modal('hide');	
		$(".container").show();
	}
	function closeModelEmail(){ 
		
		$('#myModalAddEmail').modal('hide');	
		$(".container").show();
	}
function closeModelTask(){ 
		
		$('#myModalAddTask').modal('hide');	
		$(".container").show();
	}
	
function closeModelCampaign(){ 
	
	$('#myModalAddCampaign').modal('hide');	
	$(".container").show();
}	
	
	function createTags(){ 
		
		$('#myModalAddTags').modal('show');
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").show();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").show();
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
	}
	
	
	function setReminder(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		$("#demoSetReminder").show();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
		
	}
	
	
	function massUpdate(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").show();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
	}
	
	
	function addToCampaign(){ 
		$('#myModalAddCampaign').modal('show');
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").show();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").show();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
	}
	
	function updateResponse(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").show();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
	}
	
	function printMailingLabes(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").show();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
	}
	
	function mailMerge(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").show();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").hide();
	}
	
	function massConvert(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").show();
		$("#demoDeleteAction").hide();
	}
	
	
	
	function deleteAction(){ 
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").hide();
		$("#macroContainer").hide();
		$("#demoSendEmail").hide();
		$("#demoCreateTask").hide();
		$("#demoCreateTags").hide();
		
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMailMerge").hide();
		$("#demoMassConvert").hide();
		$("#demoDeleteAction").show();
	}
	
	// for new button
	function newBtn() {  
		$("#add").hide();
		$("#copy").hide();
		$("#delete").hide();
		//$("#myGrid").hide();
		$(".container").hide();
		$("#searchRowDiv").hide();
		$("#totalReq").hide();
		$("#statusDiv").hide();
		$("#idDiv").hide();
		$("#collapseFour").hide();
		$("#headingFour").hide();
		$("#myGridActivity").hide();

		$("#demo").show();
		//$("#runMacro").attr("disabled", true);
 		//$("#sentMail").attr("disabled", true);
 		//$("#createTask").attr("disabled", true);
 		//$("#tags").attr("disabled", true)

	}
	// for cancel button
	function cancelBtn() {
		$("#add").show();
		$("#copy").show();
		$("#delete").show();
		$("#totalReq").show();
		$(".container").show();
		$("#searchRowDiv").show();
		$("#demo").hide();
		$('#myModalAddTags').modal('hide');
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMassConvert").hide();
		

		$('#leadId').val("");
		$('#leadOwner').val("");
		$('#company').val("");
		$('#firstName').val("");
		$('#lastName').val("");
		$('#title').val("");
		$('#email').val("");
		$('#phone').val("");
		$('#fax').val("");
		$('#mobile').val("");
		$('#website').val("");
		$('#referenceContact').val("");
		$('#leadSource').val("");
		$('#leadStatus').val("");
		$('#industry').val("");
		$('#noOfEmp').val("");
		$('#annualRevenue').val("");
		$('#ratings').val("");
		$('#emailOpt').val("");
		$('#skypeId').val("");
		$('#secondaryEmail').val("");
		$('#twitter').val("");
		$('#country').val("");
		$('#states').val("");
		$('#city').val("");
		$('#addressStreet').val("");
		$('#zip').val("");
		$('#description').val("");
		$('#createdBy').val("");
		$('#imgLoc').attr('src', '');
		$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

		var fileData = new FormData();

		fileData.append('file', 'none');
		fileData.append('path', 'none');
		
		$('#emailOpt').prop('checked', false);
		

	/* 	agGrid.simpleHttpRequest({
			url : "view-crm-leads-getDetails"
		}).then(function(data) {
			gridOptions.api.setRowData(data);
		}); */
	}

	$(document).ready(function() {
		 
viewDetails();
			 
		$("#date").datetimepicker({
			format : "d-m-Y",
			closeOnDateSelect : true,
			minDate : new Date(),
			timepicker : false,
		});
		$("#pipelineId").val("");

		var dateFormat = localStorage.getItem("dateFormat");
		$("#fromDateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#fromDate').val($(this).val());
		})

		$('#fromDate').blur(function() {
			$("#fromDateCalendar").val($(this).val());
		})

		$("#toDateCalendar").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#toDate').val($(this).val());
		})

		$('#toDate').blur(function() {
			$("#toDateCalendar").val($(this).val());
		})

		$("#fromTime").datetimepicker({
			format : 'H:i',
			closeOnDateSelect : false,
			timepicker : true,
			datepicker : false,
			step : 15
		})

		$("#toTime").datetimepicker({
			format : "H:i",
			closeOnDateSelect : false,
			timepicker : true,
			datepicker : false,
			step : 15
		})
		
	

	});
	
	var id=[];									
	function viewDetails(){
		//alert('hello');
	 	$.ajax({
			type : "GET",
			url : "view-crm-leads-view-Data",
			async : false,
			success : function(response) {

				if (response.message == "View successfully") {
						//console.log("in success"+response.body[0])
					div = ''; 
					for (var i = 0; i < response.body.length; i++) {
						var im=response.body[i].imageName; 
						var img = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="new_img"> <img src="'+im+'" width="70"  height= "70" alt="User" class="mr-3 img-thumbnail" /></a>';
						
						var oim=response.body[i].ownerImage; 
						var oimg = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="'+oim+'" width="40" alt="User" class="mr-3 img-thumbnail" /></a>';
						
						           
						id=response.body[i].leadId;
						var fullName = response.body[i].firstName;
						var companyName = response.body[i].company;
						var xyx = '<div class="card mb-2"><div class="card-body p-2 p-sm-3 d-flex"><div class="col-md-8">'
							    + '<div class="media forum-item d-flex">'
							    + '<div class="d-flex flex-column chk">'
								+ '<input type="checkbox" id="myCheckbox" name="myCheckbox" class="checkbox_check mr-5" value="'+id
								//+ '" onclick=clickCheckBox("'+id
								+ '" onclick=clickCheckBox("'+id+'","'+response.body[i].email		
								+ '")>'
								+ '<a href="#" data-toggle="collapse" onclick=editLeadInfo("'+id
								+ '")><i class="fa fa-edit fa-icon img-hover" aria-hidden="true" '
								+ '></i></a><div>'
								+ '<a href="#" data-toggle="collapse" onclick=deleteLeadInfo("'+id
								+ '")><i class="fa fa-trash fa-icon img-hover" aria-hidden="true" '
								+ '></i></a></div></div>'
								+ img
								+ '<div>'
								+ '<div class="u_name">'
								+ '<a href="#" onclick=viewLeadDetails("'+id+'")>'
								+ fullName
								+ '</a>'
								+ '</div>'
								+ '<div class="d-flex1">'
								+ '<span>Phone : '
								+ '<u>'
								+ response.body[i].phone
								+ '</u></span><span>|</span><span>Email : '
								+ '<u>'
								+ response.body[i].email
								+ '</u></span> <span>|</span><span>Mobile : <u>'
								+ response.body[i].mobile
								+ '</u> </span> </div> <div class="d-flex1"> <span>Company : '
								+ '<u>'
								+ companyName
								+ '</u></span> <span>|</span><span>Title : '
								+ '<u>'
								+ response.body[i].title
								+ '</u></span><span>|</span><span>Lead Source : '
								+ '<u>'
								+ response.body[i].leadSource
								+ '</u></span> </div> </div> </div></div>'
								+ '<div class="col-md-4"><div class="media forum-item d-flex">'
								//+ '<a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="40" alt="User" class="mr-3 img-thumbnail" />'
								+ oimg
								+ '</a> <div> <div>'
								+ response.body[i].ownerName
								+'</div><div>'
								+ response.body[i].createdDate
								+ ' '
								+ response.body[i].createdTime
								+'</div></div></div></div>'
								+ '</div></div>';
								
						div = xyx;
						$("#allDetails").append(div);

					} 
					
					$("#runMacro").attr("disabled", true);
					$("#sentMail").attr("disabled", true);
					$("#createTask").attr("disabled", true);
					$("#tags").attr("disabled", true);
					$("#action").attr("disabled", true);
					

			 	}
				
			},
			error : function(data) {
			} 
		});
	 	
		}
	
	
	
	function deleteLeadInfo(id){ 
		 $.ajax({
			type : "GET",
			url : "view-crm-leads-deleteDetails?id=" + id,
			success : function(response) {

				if (response.message == "Success") {
					console.log(response);
					location.reload();
					/* 
					viewDetails();
					$("#add").show();
					$("#copy").show();
					$("#delete").show();
					$("#totalReq").show();
					$(".container").show();
					$("#searchRowDiv").show();
					$("#demo").hide();
					$("#demoSetReminder").hide();
					$("#demoMassUpdate").hide();
					$("#demoAddCampaign").hide();
					$("#demoUpdateResponse").hide();
					$("#demoPrintMailingLabels").hide();
					$("#demoMassConvert").hide(); */
				}
			}
		}); 
	    }
	
	
	function editLeadInfo(id){ 
		//alert('hello');
		$.ajax({
			type : "GET",
			url : "view-crm-leads-editDetails?id=" + id,
			success : function(response) {

				if (response.code == "Success") {
					newBtn();
					console.log('response------'+JSON.stringify(response));
					$('#leadId').val(response.body[0].leadId);
				 	$('#leadOwner').val(response.body[0].leadOwner);
					$('#company').val(response.body[0].company);
					$('#firstName').val(response.body[0].firstName);
					$('#lastName').val(response.body[0].lastName);
					$('#title').val(response.body[0].title);
					$('#email').val(response.body[0].email);
					$('#phone').val(response.body[0].phone);
					$('#fax').val(response.body[0].fax);
					$('#mobile').val(response.body[0].mobile);
					$('#website').val(response.body[0].website);
					$('#referenceContact').val(response.body[0].referenceContact);
					$('#leadSource').val(response.body[0].leadSource);
					$('#leadStatus').val(response.body[0].leadStatus);
					$('#industry').val(response.body[0].industry);
					$('#noOfEmp').val(response.body[0].noOfEmp);
					$('#annualRevenue').val(response.body[0].annualRevenue);
					$('#ratings').val(response.body[0].ratings);
					$('#tags').val(response.body[0].tags);
					$('#skypeId').val(response.body[0].skypeId);
					$('#secondaryEmail').val(response.body[0].secondaryEmail);
					
					var emailValue=response.body[0].emailOpt;	
					if(emailValue =='on'){
						console.log('emailOpt-----------'+response.body[0].emailOpt);
						$('#emailOpt').prop('checked', true);
					}else{
						$('#emailOpt').prop('checked', false);
					}
					
					
					$('#twitter').val(response.body[0].twitter);
					$('#country').val(response.body[0].country);
					getStateDataOnEdit(response.body[0].states);
					//$('#states').val(response.body[0].states);
					$('#city').val(response.body[0].city);
					$('#addressStreet').val(response.body[0].addressStreet);
					$('#zip').val(response.body[0].zip);
					$('#description').val(response.body[0].description);
					
					$('#imgLoc').attr('src', '');
					if(response.body[0].imageName!=null && response.body[0].imageName!="") { 
						$('#imgLoc').attr('src', response.body[0].imageName);
					} else {
						$('#imgLoc').attr('src', '../assets/images/noimage.jpg');
					}
				}
			}
		});
	    }
	
	
//Profile Image Upload & Delete Strats

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();

	var lastIndex = fileName.lastIndexOf("\\");
	if (lastIndex >= 0) {
		fileName = fileName.substring(lastIndex + 1);
	}
	var iURL = URL.createObjectURL(uFile);

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', iURL);

	var fileData = new FormData();
	fileData.append('file', uFile);
	fileData.append('path', 'none');

	$.ajax({
		type : "POST",
		url : "view-crm-leads-upload-file",
		enctype : "multipart/form-data",
		contentType : false,
		data : fileData,
		processData : false,
		cache : false,
		success : function(response) {

		},
		error : function(e) {

		}
	});
}

function deleteFile() {

	$('#imgLoc').attr('src', '');
	$('#imgLoc').attr('src', '../assets/images/noimage.jpg');

	var fileData = new FormData();

	fileData.append('file', 'none');
	fileData.append('path', 'none');

	$.ajax({
		type : "POST",
		url : "view-crm-leads-delete-file",
		enctype : "multipart/form-data",
		contentType : false,
		/* data        : fileData, */
		processData : false,
		cache : false,
		success : function(response) {
		},
		error : function(e) {

		}
	});
}

//Profile Image Upload & Delete Ends

function showField(){
		$('#searchAnnualRevenue').toggle();
	}
 	function showField1(){
		$('#searchCity').toggle();
	}
 	function showField2(){
		$('#searchCompany').toggle();
	}
 	function showField3(){
		$('#searchConvertedAccount').toggle();
	}
 	function showField4(){
		$('#searchConvertedContact').toggle();
	}
 	function showField5(){
		$('#searchConvertedDeal').toggle();
	}
 	function showField6(){
		$('#searchCountry').toggle();
	}
 	function showField7(){
		$('#searchCreatedBy').toggle();
	}
 	function showField8(){
		$('#searchCreatedTime').toggle();
	}
 	function showField9(){
		$('#searchEmail').toggle();
	}
 	function showField10(){
		$('#searchEmailOptOut').toggle();
	}
 	function showField11(){
		$('#searchFax').toggle();
	}
 	function showField12(){
		$('#searchFirstName').toggle();
	}
 	function showField13(){
		$('#searchIndustry').toggle();
	}
 	function showField14(){
		$('#searchLastActivityTime').toggle();
	}
 	function showField15(){
		$('#searchLastName').toggle();
	}
 	function showField16(){
		$('#searchLeadConversionTime').toggle();
	}
 	function showField17(){
		$('#searchLeadName').toggle();
	}
 	function showField18(){
		$('#searchLeadOwner').toggle();
	}
 	function showField19(){
		$('#searchLeadSource').toggle();
	}
 	function showField20(){
		$('#searchLeadStatus').toggle();
	}
 	function showField21(){
		$('#searchMobile').toggle();
	}
 	function showField22(){
		$('#searchModifiedBy').toggle();
	}
 	function showField23(){
		$('#searchModifiedTime').toggle();
	}
 	function showField24(){
		$('#searchNoOfEmployees').toggle();
	}
 	function showField25(){
		$('#searchPhone').toggle();
	}
 	function showField26(){
		$('#searchRating').toggle();
	}
 	function showField27(){
		$('#searchSalutation').toggle();
	}
 	function showField28(){
		$('#searchSecondaryEmail').toggle();
	}
 	function showField29(){
		$('#searchSkypeID').toggle();
	}
 	function showField30(){
		$('#searchState').toggle();
	}
 	function showField31(){
		$('#searchStreet').toggle();
	}
 	function showField32(){
		$('#searchModifiedByTag').toggle();
	}
 	function showField33(){
		$('#searchTitle').toggle();
	}
 	function showField34(){
		$('#searchTwitter').toggle();
	}
 	function showField35(){
		$('#searchUnsubscribedMode').toggle();
	}
 	function showField36(){
		$('#searchUnsubscribedTime').toggle();
	}
 	function showField37(){
		$('#searchWebsite').toggle();
	}
 	function showField38(){
		$('#searchZipCode').toggle();
	}
 	
 	
 	function filter(){
 		obj={};
 		
	//	obj.leadId = $('#leadId').text();
		obj.leadOwner = $('#searchLeadOwner').val();
		obj.company = $('#searchCompany').val();
		obj.firstName = $('#searchFirstName').val();
		obj.lastName = $('#searchLastName').val();
		obj.title = $('#searchTitle').val();
		obj.email = $('#searchEmail').val();
		obj.phone = $('#searchPhone').val();
		obj.fax = $('#searchFax').val();
		obj.mobile = $('#searchMobile').val();
		obj.website = $('#searchWebsite').val();
		obj.leadSource = $('#searchLeadSource').val();
		obj.leadStatus = $('#searchLeadStatus').val();
		obj.industry = $('#searchIndustry').val();
		obj.noOfEmp = $('#searchNoOfEmployees').val();
		obj.annualRevenue = $('#searchAnnualRevenue').val();
		obj.ratings = $('#searchRating').val();
		obj.emailOpt = $('#searchEmailOptOut').val();
		obj.skypeId = $('#searchSkypeID').val();
		obj.secondaryEmail = $('#searchSecondaryEmail').val();
		obj.twitter = $('#searchTwitter').val();
		obj.country = $('#searchCountry').val();
		obj.states = $('#searchState').val();
		obj.city = $('#searchCity').val();
		obj.addressStreet = $('#searchStreet').val();
		obj.zip = $('#searchZipCode').val();
		//obj.description = $('#description').val();
		obj.createdBy = $('#searchCreatedBy').val();
		
		obj.searchConvertedAccount = $('#searchConvertedAccount').val();
		obj.searchConvertedContact = $('#searchConvertedContact').val();
		obj.searchConvertedDeal = $('#searchConvertedDeal').val();
		obj.searchCreatedTime = $('#searchCreatedTime').val();
		obj.searchLastActivityTime = $('#searchLastActivityTime').val();
		obj.searchLeadConversionTime = $('#searchLeadConversionTime').val();
		obj.searchLeadName = $('#searchLeadName').val();
		obj.searchModifiedBy = $('#searchModifiedBy').val();
		obj.searchModifiedTime = $('#searchModifiedTime').val();
		obj.searchSalutation = $('#searchSalutation').val();
		obj.searchModifiedByTag = $('#searchModifiedByTag').val();
		obj.searchUnsubscribedMode = $('#searchUnsubscribedMode').val();
		obj.searchUnsubscribedTime = $('#searchUnsubscribedTime').val();
		
		console.log("response of obj----"+JSON.stringify(obj));
		
		$.ajax({
			type : "POST",
			url : "view-crm-leads-view-Data-search",
			contentType : "application/json",
			async : false,
			data : JSON.stringify(obj),
			success : function(response) {

				if (response.message == "Success") {
					
					console.log(response);	
						
					div = ''; 
					$("#allDetails").empty();
					
					
					for (var i = 0; i < response.body.length; i++) {
						
						var im=response.body[i].imageName; 
						var img = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="new_img"> <img src="'+im+'" width="70"  height= "70" alt="User" class="mr-3 img-thumbnail" /></a>';
						
						var oim=response.body[i].ownerImage; 
						var oimg = ' <a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="'+oim+'" width="40" alt="User" class="mr-3 img-thumbnail" /></a>';
						
						
						
						id=response.body[i].leadId;
						//$(".mainDivRecord").remove(div);
						var fullName = response.body[i].firstName + '&nbsp' +response.body[i].lastName;
						var companyName = response.body[i].company;
						div  = div + '<div class="card mb-2 mainDivRecord"><div class="card-body p-2 p-sm-3 d-flex"><div class="col-md-8">'
							    + '<div class="media forum-item d-flex">'
							    + '<div class="d-flex flex-column chk">'
								+ '<input type="checkbox" id="myCheckbox" name="myCheckbox" class="checkbox_check mr-5" value="'+id
								//+ '" onclick=clickCheckBox("'+id
								+ '" onclick=clickCheckBox("'+id+'","'+response.body[i].email		
								+ '")>'
								+ '<a href="#" data-toggle="collapse" onclick=editLeadInfo("'+id
								+ '")><i class="fa fa-edit fa-icon img-hover" aria-hidden="true" '
								+ '></i></a><div>'
								+ '<a href="#" data-toggle="collapse" onclick=deleteLeadInfo("'+id
								+ '")><i class="fa fa-trash fa-icon img-hover" aria-hidden="true" '
								+ '></i></a></div></div>'
								+ img
								+ '<div>'
								+ '<div class="u_name">'
								+ '<a href="#" onclick=viewLeadDetails("'+id+'")>'
								+ fullName
								+ '</a>'
								+ '</div>'
								+ '<div class="d-flex1">'
								+ '<span>Phone : '
								+ '<u>'
								+ response.body[i].phone
								+ '</u></span><span>|</span><span>Email : '
								+ '<u>'
								+ response.body[i].email
								+ '</u></span> <span>|</span><span>Mobile : <u>'
								+ response.body[i].mobile
								+ '</u> </span> </div> <div class="d-flex1"> <span>Company : '
								+ '<u>'
								+ companyName
								+ '</u></span> <span>|</span><span>Title : '
								+ '<u>'
								+ response.body[i].title
								+ '</u></span><span>|</span><span>Lead Source : '
								+ '<u>'
								+ response.body[i].leadSource
								+ '</u></span> </div> </div> </div></div>'
								+ '<div class="col-md-4"><div class="media forum-item d-flex">'
								//+ '<a href="#" data-toggle="collapse" data-target=".forum-content" class="p_img"> <img src="https://bootdey.com/img/Content/avatar/avatar1.png" width="40" alt="User" class="mr-3 img-thumbnail" />'
								+ oimg
								+ '</a> <div> <div>'
								+ response.body[i].leadOwner
								+'</div><div>'
								+ response.body[i].createdDate
								+ ' '
								+ response.body[i].createdTime
								+ '</div></div></div></div>'
								+ '</div></div>';
								
					//	div = xyx;
						
						

					} 
					
					$("#allDetails").append(div);
					
					$("#runMacro").attr("disabled", true);
					$("#sentMail").attr("disabled", true);
					$("#createTask").attr("disabled", true);
					$("#tags").attr("disabled", true);
					$("#action").attr("disabled", true);
					

			 	}
				
			},
			error : function(data) {
			} 
		});
		
	}

function getStateDetails(){
	
	var cname = $('#country').val();
	if (cname) { 
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
		$.ajax({type : "GET",
			url : "view-crm-leads-stateList?id="+ cname,
					success : function(response) {
						if (response.message == "success") {
							$("#states").empty();
							var option = $("<option></option>");
							$(option).val(null);
							$(option).html("Select");
							$("#states").append(option);
							for (var i = 0; i < response.body.length; i++) {
								var option = $("<option></option>");
								$(option).val(response.body[i].key);
								$(option).html(response.body[i].name);
								$("#states").append(option);
							}
						}
					},
					error : function(e) { 
					}
				});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		//$("#dname").append(option);
	}
}

function getStateDataOnEdit(stateId) {
	var country = $("#country").val();
	if (country) { 
		$.ajax({
			type : "GET",
			url : "view-crm-leads-stateList?id=" + country,
			success : function(response) {
				if (response.message == "success") {
					$("#states").empty();
					var option = $("<option></option>");
					$(option).val(null);
					$(option).html("Select");
					$("#states").append(option);
					for (var i = 0; i < response.body.length; i++) {
						var option = $("<option></option>");
						$(option).val(response.body[i].key);
						$(option).html(response.body[i].name);
						$("#states").append(option);
					}
					$("#states").val(stateId);
				}
			},
			error : function(e) { 
			}
		});
	} else {
		$("#states").empty();
		var option = $("<option></option>");
		$(option).val(null);
		$(option).html("Select");
		$("#states").append(option);
	}
}
	
	function addTaskInfo(){
		var dataset = [];
		console.log(searchIDs);
		for (let i = 0; i < searchIDs.length; ++i) {
			//alert(searchIDs[i])
		   console.log(searchIDs[i]);
		   item = {};
		   item['leadId']   =	searchIDs[i];
		   //item['taskOwner']    =	$("#taskOwner").val();
		   //item['taskLead']    =	$("#taskLead").val();
		   item['taskContactName']    =	$("#taskContactName").val();
		   item['contactId']    =	$("#contactId").val();
		   item['taskAccountName']    =	$("#taskAccountName").val();
		   item['accountId']    =	$("#accountId").val();
		   item['taskStatus']    =	$("#taskStatus").val();
			item['taskSubject']    =	$("#taskSubject").val();
			item['taskDueDate']    =	$("#dueDateid").val();
			item['taskPriority']    =	$("#taskPriority").val();
			item['leadOwner']  =	 $("#taskOwner").val();
			
			var ReminderYesOrNo = $("input[name='ReminderYesOrNo']:checked").val();
			item['reminderYesOrNo']   = ReminderYesOrNo;
			item['reminderDate']   =	$("#reminderDateid").val();
			item['reminderTime']   = $("#reminderTime").val();
			item['taskAlertBy']   = $("#taskAlertBy").val();	
			
			var RepeateYesOrNo = $("input[name='RepeateYesOrNo']:checked").val();
			item['repeateYesOrNo']   = RepeateYesOrNo;
			item['description'] = $('#description').val(); 
			dataset.push(item);
		}
		
		console.log('dataset for task------'+JSON.stringify(dataset));
		saveTask(dataset);
		
	}
	
	function saveTask(dataset) { 
			$.ajax({
				type : "POST",
				url : "view-crm-leads-save-task",
				contentType : "application/json",
				data : JSON.stringify(dataset),
				success : function(response) {
					if (response.message == "Success") {
						
						$("#messageParagraph").text("Data Saved Successfully");
						console.log(response);
						location.reload();
						//return false;
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
						$("#myGrid").show();
						$("#demo").hide();
						$("#new1").show();
						$("#demo1").hide();
						$("#delete1").show();
						$("#totalReq").show();
						$("#searchRowDiv").show();
						
						agGrid.simpleHttpRequest({
							url : "manage-job-card"
						}).then(function(data) {
							var len = data.length;
							$('#totalReq').find('span').html(len);
							gridOptions.api.setRowData(data);
						})
						location.reload();
						gridOptions1.api.setRowData([]);
					}
				},
				error : function(datas) {
				}
			})

		}
	
	
	function saveLeadInfo() { 
		var obj = {};
		
		obj.leadId = $('#leadId').val();
		obj.leadOwner = $('#leadOwner').val();
		obj.company = $('#company').val();
		obj.firstName = $('#firstName').val();
		obj.lastName = $('#lastName').val();
		obj.title = $('#title').val();
		obj.email = $('#email').val();
		obj.phone = $('#phone').val();
		obj.fax = $('#fax').val();
		obj.mobile = $('#mobile').val();
		obj.website = $('#website').val();
		obj.referenceContact = $('#referenceContact').val();
		obj.leadSource = $('#leadSource').val();
		obj.leadStatus = $('#leadStatus').val();
		obj.industry = $('#industry').val();
		obj.noOfEmp = $('#noOfEmp').val();
		obj.annualRevenue = $('#annualRevenue').val();
		obj.ratings = $('#ratings').val();
		obj.emailOpt = $('#emailOpt').val();
		obj.skypeId = $('#skypeId').val();
		obj.secondaryEmail = $('#secondaryEmail').val();
		obj.twitter = $('#twitter').val();
		obj.country = $('#country').val();
		obj.states = $('#states').val();
		obj.city = $('#city').val();
		obj.addressStreet = $('#addressStreet').val();
		obj.zip = $('#zip').val();
		obj.description = $('#description').val();
		obj.createdBy = $('#createdBy').val();
		
		console.log(obj);

		/* FORM VALIDATION STARTS*/

		var validation = true;

	    if (obj.leadOwner == null || obj.leadOwner == "") {
			validation = validationUpdated("Executive Required",
					"leadOwner");
		}
 	
	    if (obj.company == null || obj.company == "") {
			validation = validationUpdated("Lead company Required",
					"company");
		}
	    
	    
	    if (obj.firstName == null || obj.firstName == "") {
			validation = validationUpdated("First Name Required",
					"firstName");
		}
	    
	    
	    if (obj.lastName == null || obj.lastName == "") {
			validation = validationUpdated("Last Name Required",
					"lastName");
		}
	    
	    if (obj.title == null || obj.title == "") {
			validation = validationUpdated("Title Required",
					"title");
		}
	    
	    if (obj.phone == null || obj.phone == "") {
			validation = validationUpdated("Phone Required",
					"phone");
		}
	    
	    if (obj.email == null || obj.email == "") {
			validation = validationUpdated("Email Required",
					"email");
		}
	    
	    if (obj.mobile == null || obj.mobile == "") {
			validation = validationUpdated("Mobile Required",
					"mobile");
		}
	    
	    if (obj.leadSource == null || obj.leadSource == "") {
			validation = validationUpdated("Lead Source Required",
					"leadSource");
		}
	    
	    
	    if (obj.leadStatus == null || obj.leadStatus == "") {
			validation = validationUpdated("Lead Status Required",
					"leadStatus");
		}
	    
	    if (obj.industry == null || obj.industry == "") {
			validation = validationUpdated("Lead Industry Required",
					"industry");
		}
	    
	    
	    if (obj.noOfEmp == null || obj.noOfEmp == "") {
			validation = validationUpdated("Number Of Emp Required",
					"noOfEmp");
		}
	    
	    if (obj.country == null || obj.country == "") {
			validation = validationUpdated("Country Required",
					"country");
		}
	    
	    if (obj.states == null || obj.states == "") {
			validation = validationUpdated("State Required",
					"states");
		}
	    
	    
	    if (obj.city == null || obj.city == "") {
			validation = validationUpdated("City Required",
					"city");
		}
	    
	    if (obj.addressStreet == null || obj.addressStreet == "") {
			validation = validationUpdated("Address Street Required",
					"addressStreet");
		}
	    
	    if (obj.zip == null || obj.zip == "") {
			validation = validationUpdated("Zip Street Required",
					"zip");
		}
	    
 
	    
		/* FORM VALIDATION ENDS*/

		if (validation) {
			
			//alert("save----saveLeadInfo");
			$.ajax({
				type : "POST",
				url : "view-crm-leads-add-lead-details",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					if (response.message == "Success") {
						console.log(response);
						/* viewDetails();
						$("#add").show();
						$("#copy").show();
						$("#delete").show();
						$("#totalReq").show();
						$(".container").show();
						$("#searchRowDiv").show();
						$("#demo").hide();
						$("#demoSetReminder").hide();
						$("#demoMassUpdate").hide();
						$("#demoAddCampaign").hide();
						$("#demoUpdateResponse").hide();
						$("#demoPrintMailingLabels").hide();
						$("#demoMassConvert").hide(); */
						location.reload();

						
					}
				},
				error : function(data) {

					//console.log(data);
				}
			})
		}

	}
	
	function getAccountList() {
		//alert("JJJ");
		var search = $("#taskAccountName").val();

		if (search) {

			$.ajax({
						type : "POST",
						url : "view-crm-tasks-get-account-list",
						dataType : 'json',
						contentType : 'application/json',
						data : search,
						success : function(response) {
							if (response.message == "success") {
								if (response.body.length != 0) {
									$("#search").css("background", "#FFF");
									var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
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
									content += '<li onClick="selectAutocompleteValue()">'
											+ "No Data Found" + '</li>';
									content += '</ul>';
									$("#suggesstion-box1_").show();
									$("#suggesstion-box1_").html(content);
								}
							}
						},
						error : function(data) {
							console.log(data);
						}
					})
		}

	}

	function selectAutocompleteValue1(accountId, accountName, custGSTNo, taxType) {

		if (accountId) {
			

			$("#accountId").val(accountId);

			$("#taskAccountName").val(accountName);
			
			$("#search").val(accountName);
			$("#search").attr('data-procat', accountId);
			$("#suggesstion-box1_").hide();
		//	hideShowS();
			//checkForDuplicate(key,counter);

		} else {

			$("#accountId").val("");

			$("#taskAccountName").val("");
			
			$("#search").val("");
			$("#search").attr('data-procat', "");
			$("#suggesstion-box1_").hide();

		}
	}
	
	function getNameListContact() {
		var searchVal = $("#taskContactName").val();
		if(searchVal == ""){
		$("#suggesstion-boxcontact_").hide();
		}
		if (searchVal) {
					$.ajax({
						type : "POST",
						url : "view-crm-tasks-autosearchDetailsContact",
						dataType : 'json',
						contentType : 'application/json',
						data : searchVal,
						success : function(response) {
							if (response.message == "success") {
								
								if (response.body.length != 0) {
									$("#search").css("background", "#FFF");
									var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
									for (var i = 0; i < response.body.length; i++) {
										content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueContact(\''
												+ response.body[i].name
												+ '\',\''
												+ response.body[i].key
												+ '\')">'
												+ response.body[i].name
												+ '</li>';
									}
									content += '</ul>';
									$("#suggesstion-boxcontact_").show();
									$("#suggesstion-boxcontact_").html(content);

								}
								else {
									$("#search").css("background", "#FFF");
									var content = '<div id="autocomplete-list1">';
									content += '<div onClick="selectAutocompleteContactValue()">'
											+ "No Data Found" + '</div>';
									content += '</div>';
									$("#suggesstion-boxcontact_").show();
									$("#suggesstion-boxcontact_").html(content);
								}
							}
						},
						error : function(data) {
							console.log(data);
						}
					})
		} 
	}

			function selectAutocompleteValueContact(name,ContactId) {
				if (name) {
					//$("#personName").val("");
					$("#contactId").val(ContactId);
					
					$("#taskContactName").val(name);
					$("#search").val(ContactId);
					$("#search").attr('data-procat', name);
					$("#suggesstion-boxcontact_").hide();
		
					} else {
						$("#contactId").val("");
			
						$("#taskContactName").val("");
			
						$("#search").val("");
						$("#search").attr('data-procat', "");
						$("#suggesstion-boxcontact_").hide();
		
					}
				}
				function selectAutocompleteContactValue() {

				$("#contactId").val("");

				$("#taskContactName").val("");

				$("#search").val("");
				$("#search").attr('data-procat', "");
				$("#suggesstion-boxcontact_").hide();

				}

		
	
	// Edit & stage change 
	function editPage(id) {

		var editId = id.split(",");

		var pipelineId = editId[0];
		var modal = editId[1]; 
		$("#demo").show();

		$.ajax({
			type : "GET",
			url : "view-crm-pipeline-edit?id=" + pipelineId,
			async : false,
			success : function(response) {
				console.log(response);
				if (response.message == "Success") {
					agGridActivity(pipelineId);
					$("#pipelineId").val(response.body.pipelineId);
					$("#oppertunity").val(response.body.oppertunity);
					$("#expectedRevenue").val(response.body.expectedRevenue);
					$("#probability").val(response.body.probability);
					$('#customer').val(response.body.customer);
					$('#email').val(response.body.email);
					$('#phone').val(response.body.phone);
					$('#saleperson').val(response.body.saleperson);
					$('#salesTeam').val(response.body.salesTeam);
					$('#date').val(response.body.expectedClosing);
					let index = response.body.priority;
					$('#star' + index).prop('checked', true);
					$('#tags').val(response.body.tags);
					$('#company').val(response.body.company);
					$('#description').val(response.body.description);
					$('#campanyName').val(response.body.campanyName);
					$('#addressStreet').val(response.body.addressStreet);
					$('#addressStreet2').val(response.body.addressStreet2);
					$('#city').val(response.body.city);
					$('#states').val(response.body.states);
					$('#zip').val(response.body.zip);
					$('#country').val(response.body.country);
					$('#website').val(response.body.website);
					$('#language').val(response.body.language);
					$('#contactName').val(response.body.contactName);
					$('#tittle').val(response.body.tittle);
					$('#jobPosition').val(response.body.jobPosition);
					$('#mobile').val(response.body.mobile);
					$('#referdBy').val(response.body.referdBy);
					$('#campaign').val(response.body.campaign);
					$('#medium').val(response.body.medium);
					$('#source').val(response.body.source);
					$('#stageStatus').val(response.body.pipelineStatus);
					//for stage active status
					if (response.body.pipelineStatus == 1) {
						$("#stages1").attr("class", "btn btn-primary");
						$("#stages2").attr("class", "btn btn-outline-primary");
						$("#stages3").attr("class", "btn btn-outline-primary");
						$("#stages4").attr("class", "btn btn-outline-primary");
					}
					if (response.body.pipelineStatus == 2) {
						$("#stages2").attr("class", "btn btn-primary");
						$("#stages1").attr("class", "btn btn-outline-primary");
						$("#stages3").attr("class", "btn btn-outline-primary");
						$("#stages4").attr("class", "btn btn-outline-primary");
					}
					if (response.body.pipelineStatus == 3) {
						$("#stages3").attr("class", "btn btn-primary");
						$("#stages1").attr("class", "btn btn-outline-primary");
						$("#stages2").attr("class", "btn btn-outline-primary");
						$("#stages4").attr("class", "btn btn-outline-primary");
					}
					if (response.body.pipelineStatus == 4) {
						$("#stages4").attr("class", "btn btn-primary");
						$("#stages2").attr("class", "btn btn-outline-primary");
						$("#stages3").attr("class", "btn btn-outline-primary");
						$("#stages1").attr("class", "btn btn-outline-primary");
					}
					if (modal == '1') {
						$("#add").hide();
						$("#copy").hide();
						$("#delete").hide();
						$("#totalReq").hide();
						$("#myGrid").hide();
						$("#searchRowDiv").hide();
						$("#save").hide();
						$("#cancel").show();
						$("#statusDiv").show();
						$("#idDiv").show();
						$("#collapseFour").show();
						$("#headingFour").show();
						$("#myGridActivity").show();

						$("#pipelineHeadId").html(response.body.pipelineId);
						$("#oppertunity").attr('disabled', true);
						$("#expectedRevenue").attr('disabled', true);
						$("#probability").attr('disabled', true);
						$("#customer").attr('disabled', true);
						$("#email").attr('disabled', true);
						$("#phone").attr('disabled', true);
						$("#saleperson").attr('disabled', true);
						$("#salesTeam").attr('disabled', true);
						$("#date").attr('disabled', true);
						$(".rating").attr('disabled', true);
						$("#tags").attr('disabled', true);
						$("#company").attr('disabled', true);
						$("#description").attr('disabled', true);
						$("#campanyName").attr('disabled', true);
						$("#addressStreet").attr('disabled', true);
						$("#addressStreet2").attr('disabled', true);
						$("#city").attr('disabled', true);
						$("#states").attr('disabled', true);
						$("#zip").attr('disabled', true);
						$("#country").attr('disabled', true);
						$("#website").attr('disabled', true);
						$("#language").attr('disabled', true);
						$("#contactName").attr('disabled', true);
						$("#tittle").attr('disabled', true);
						$("#jobPosition").attr('disabled', true);
						$("#mobile").attr('disabled', true);
						$("#referdBy").attr('disabled', true);
						$("#campaign").attr('disabled', true);
						$("#medium").attr('disabled', true);
						$("#source").attr('disabled', true);
						/* 	$("#stages1").attr('disabled', false);
							$("#stages2").attr('disabled', false);
							$("#stages3").attr('disabled', false);
							$("#stages4").attr('disabled', false); */

					} else {

						$("#add").hide();
						$("#copy").hide();
						$("#delete").hide();
						$("#totalReq").hide();
						$("#myGrid").hide();
						$("#searchRowDiv").hide();
						$("#statusDiv").hide();
						$("#idDiv").hide();
						$("#collapseFour").hide();
						$("#headingFour").hide();
						$("#myGridActivity").hide();
						$("#mySidenav").hide();

						$("#oppertunity").attr('disabled', false);
						$("#expectedRevenue").attr('disabled', false);
						$("#probability").attr('disabled', false);
						$("#customer").attr('disabled', false);
						$("#email").attr('disabled', false);
						$("#phone").attr('disabled', false);
						$("#saleperson").attr('disabled', false);
						$("#salesTeam").attr('disabled', false);
						$("#date").attr('disabled', false);
						$(".rating").attr('disabled', false);
						$("#tags").attr('disabled', false);
						$("#company").attr('disabled', false);
						$("#description").attr('disabled', false);
						$("#campanyName").attr('disabled', false);
						$("#addressStreet").attr('disabled', false);
						$("#addressStreet2").attr('disabled', false);
						$("#city").attr('disabled', false);
						$("#states").attr('disabled', false);
						$("#zip").attr('disabled', false);
						$("#country").attr('disabled', false);
						$("#website").attr('disabled', false);
						$("#language").attr('disabled', false);
						$("#contactName").attr('disabled', false);
						$("#tittle").attr('disabled', false);
						$("#jobPosition").attr('disabled', false);
						$("#mobile").attr('disabled', false);
						$("#referdBy").attr('disabled', false);
						$("#campaign").attr('disabled', false);
						$("#medium").attr('disabled', false);
						$("#source").attr('disabled', false);

					}
				}
			}
		})
	}

	/***************************        stage change         ****************************************/

	function addStages(event) {

		var pipelineId = $("#pipelineId").val();
		var stages = event.currentTarget.value;
		var previousStage = $("#stageStatus").val(); 
		if (stages != previousStage) {
			$
					.ajax({
						type : "GET",
						url : "view-crm-pipeline-addStages?id=" + pipelineId
								+ "&stage=" + stages + "&previousStage="
								+ previousStage,
						success : function(response) {

							if (response.message == "success") {

								if (stages == 1) {
									$("#stages1").attr("class",
											"btn btn-primary");
									$("#stages2").attr("class",
											"btn btn-outline-primary");
									$("#stages3").attr("class",
											"btn btn-outline-primary");
									$("#stages4").attr("class",
											"btn btn-outline-primary");
								}
								if (stages == 2) {
									$("#stages2").attr("class",
											"btn btn-primary");
									$("#stages1").attr("class",
											"btn btn-outline-primary");
									$("#stages3").attr("class",
											"btn btn-outline-primary");
									$("#stages4").attr("class",
											"btn btn-outline-primary");
								}
								if (stages == 3) {
									$("#stages3").attr("class",
											"btn btn-primary");
									$("#stages1").attr("class",
											"btn btn-outline-primary");
									$("#stages2").attr("class",
											"btn btn-outline-primary");
									$("#stages4").attr("class",
											"btn btn-outline-primary");
								}
								if (stages == 4) {
									$("#stages4").attr("class",
											"btn btn-primary");
									$("#stages2").attr("class",
											"btn btn-outline-primary");
									$("#stages3").attr("class",
											"btn btn-outline-primary");
									$("#stages1").attr("class",
											"btn btn-outline-primary");
								} 
								agGrid
										.simpleHttpRequest(
												{
													url : 'view-crm-pipeline-activity-through-ajax?id='
															+ pipelineId
												}).then(
												function(data) {
													activityOptions.api
															.setRowData(data);
												});

							} else {
								swal({
									title : response.code,
									text : response.message,
									type : "warning"
								});
							}
						},
						error : function(data) {
							console.log(data);
						}
					});
		}

	}

/*********************************** Activity Log           **********************     ************************************** */
var activityDefs = [ {
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		sortable : false,
		filter : false,
		resizable : true,
		width : 20
	}, {
		headerName : "Activity Name",
		field : "operationName"
	}, {
		headerName : "User Name",
		field : "operationBy"
	}, {
		headerName : "Created Date",
		field : "operationOn",
		cellStyle : {
			textAlign : 'center'
		}

	} ];

	var activityOptions = {
		columnDefs : activityDefs,

		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 420
		}

	};

	document.addEventListener('DOMContentLoaded', function() {

		var gridDiv = document.querySelector('#myGridActivity');
		new agGrid.Grid(gridDiv, activityOptions);
		
		var gridDiv1 = document.querySelector('#activity');
		new agGrid.Grid(gridDiv1, activityOptions);

	});
	function agGridActivity(pipelineId) {
		agGrid.simpleHttpRequest({
			url : 'view-crm-pipeline-activity-through-ajax?id=' + pipelineId
		}).then(function(data) {
			activityOptions.api.setRowData(data);
		});
	}

	//ag grid for activity log end

	 function viewLeadDetails(id){
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest+"view-crm-leads-detail?id="+id;
		//window.location.href = rest+"view-crm-leads-detail/"+id;	
	 }
	