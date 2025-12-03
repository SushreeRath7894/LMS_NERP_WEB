// Function to handle "See More" button click
let toMail= '';
$(document).ready(function () {
	
	watchLocationPermission();
	
    $('#seeMoreButton').on("click", function () {
        $('#scrollableSection').toggleClass('show-scrollable-section');
        $('#seeMoreButton').text($('#scrollableSection').hasClass('show-scrollable-section') ? 'See Less' : 'See More..');
    });
});
(function ($) {
  function initOwlCarousel() {
	$('.therapy-slider').owlCarousel(
	{
        loop: true,
        autoplay: false,
        nav: true,
        autoplayHoverPause: true,
        responsiveClass: true,
        dots: false,  
        margin: 30,
        nav: true,
        navText: ["<i class='bi bi-chevron-left'></i>", "<i class='bi bi-chevron-right'></i>"],         
        responsive:{
            0:{
                items: 1,
            },
            768:{
                items:2,
            },
            1200:{
                items: 4,
            }
        }
    }
	
	
	);
  }

  $(document).ready(function () {
	initOwlCarousel();
	
	CKEDITOR.replace('commentck', {
    	enterMode: CKEDITOR.ENTER_BR, 
     	height: 150,
     	removePlugins: 'wsc',
     	// config.enterMode = CKEDITOR.ENTER_BR,
     	scayt_autoStartup: true,
     	scayt_maxSuggestions: 3
     	});
  
  CKEDITOR.replace('commentck1', {
     	height: 150,
     	removePlugins: 'wsc',
     	scayt_autoStartup: true,
     	scayt_maxSuggestions: 3
     	});
	
    
  });
})(jQuery);



$(document).ready(function() {
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
});

$(document).ready(function () {
	var d = new Date();

	var month = d.getMonth()+1;
	var day = d.getDate();
	const monthNames = ["January", "February", "March", "April", "May", "June",
	  "July", "August", "September", "October", "November", "December"
	];
	var year =d.getFullYear();
	var monthName =monthNames[d.getMonth()];

	var output = day + ' ' + monthName + ', ' + year;
	$('#todayDateUnderHistory').text(output);

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
	    $('#No').prop('checked', true);
	
	
	var dateFormat = localStorage.getItem("dateFormat");
	
	$("#reminderCalendar11").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#callStartDate11').val($(this).val());
	})
	
	$('#callStartDate11').blur(function(){
		$("#reminderCalendar11").val($(this).val());
	}) 
	
	
	
	 $("#toDateCalendarTime11").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step : 15
	}).on("change", function() {
		$('#callStartTime11').val($(this).val());
	})

	$('#callStartTime11').blur(function() {
		$("#toDateCalendarTime11").val($(this).val());
	}) 
	
	
	$("#DateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#dueDate').val($(this).val());
	})

	$('#dueDate').blur(function() {
		$("#DateCalendar").val($(this).val());
	})
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
	
	
	//Meeting Calendar From Date
	$("#meetingCalendarFromDate").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#meetingFromDate').val($(this).val());
	})
	
	$('#meetingFromDate').blur(function(){
		$("#meetingCalendarFromDate").val($(this).val());
	}) 
	
	$("#meetingCalendarRepeatFromDate").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#meetingRepeatFromDate').val($(this).val());
	})
	
	$('#meetingRepeatFromDate').blur(function(){
		$("#meetingCalendarRepeatFromDate").val($(this).val());
	}) 
	
	
	$("#meetingCalendarToDate").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#meetingToDate').val($(this).val());
	})
	
	$('#meetingToDate').blur(function(){
		$("#meetingCalendarToDate").val($(this).val());
	}) 
	
	$("#meetingCalendarRepeatToDate").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#meetingRepeatToDate').val($(this).val());
	})
	
	$('#meetingRepeatToDate').blur(function(){
		$("#meetingCalendarRepeatToDate").val($(this).val());
	}) 
	
	
	
	$("#meetingCalendarFromTime").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step : 15
	}).on("change", function() {
		$('#meetingFromTime').val($(this).val());
	})

	$('#meetingFromTime').blur(function() {
		$("#meetingCalendarFromTime").val($(this).val());
	}) 
	
	
	$("#meetingCalendarRepeatFromTime").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step : 15
	}).on("change", function() {
		$('#meetingRepeatFromTime').val($(this).val());
	})

	$('#meetingRepeatFromTime').blur(function() {
		$("#meetingCalendarRepeatFromTime").val($(this).val());
	}) 
	
	
	$("#meetingCalendarRepeatToTime").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step : 15
	}).on("change", function() {
		$('#meetingRepeatToTime').val($(this).val());
	})

	$('#meetingRepeatToTime').blur(function() {
		$("#meetingCalendarRepeatToTime").val($(this).val());
	}) 
	
	
	$("#meetingCalendarToTime").datetimepicker({
		format : 'H:i',
		closeOnDateSelect : false,
		timepicker : true,
		datepicker : false,
		step : 15
	}).on("change", function() {
		$('#meetingToTime').val($(this).val());
	})

	$('#meetingToTime').blur(function() {
		$("#meetingCalendarToTime").val($(this).val());
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
	
	 $('.numberonly').keypress(function (e) {    

        var charCode = (e.which) ? e.which : event.keyCode    

        if (String.fromCharCode(charCode).match(/[^0-9]/g))    

            return false;                        

    });  
    
});


$(document).ready(function(){
	
	
	var gridDiv = document.querySelector('#myGridUpdateField');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var gridDiv = document.querySelector('#myGridLead');
	new agGrid.Grid(gridDiv, gridOptionsLead);
	
	var gridDiv = document.querySelector('#myGridCampaigns');
	new agGrid.Grid(gridDiv, gridOptionsCampaigns);
	
	
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
	
	
		//viewDetails();							
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
		
		
		$('select').on('change', function() {
			  var value=this.value;
			  if(value=="Task"){
				  var userId = $("#userId").text();
				  
				  var accountId=$("#accountId").text();
				  var accountName = $("#accountName").text();
				  
			   	   $('#taskOwner').val(userId);
				   $('#taskAccountName').val(accountName);
				   $('#accountId').val(accountId);
				  
				  
				  $('#myModalAddTask').modal('show');
			     }else if(value=="Meeting"){
				  showexecutive();
				  var userId = $("#userId").text();
				  
				  $('#meetingHost').val(userId);
				  $('#myModalAddMeeting').modal('show');
			  }else if(value=="Call"){
				  showexecutive();
				  var userId = $("#userId").text();
				  
				  $('#callOwner').val(userId);
				  var accountId=$("#accountId").text();
				  var accountName = $("#accountName").text();
				  
				  selectAutocompleteValue3(accountId, accountName)
				 $('#myModalAddCall').modal('show');
			  }
		});
	
	
})

function showexecutive(){
      var meet = "excutive";
	  $('.relatedMeetingTo1').val(meet);
	  $('.relatedMeetingTo1').prop('disabled', true);
	  $('.relatedMeetingTo1').val(meet).prop('disabled', true).css({'background-color': '#e9ecef'});
	  checkMeetingDetails1(meet);
}
$(function () {
 	var accountId=$("#accountId").text();
	//alert(accountId)
	$.ajax({
		type : "GET",
		url : "view-crm-accounts-ViewDetailPage?id=" + accountId,
	//	url : "view-crm-deals-ViewDetailPage?id=" + dealId,
		success : function(response) {

			if (response.message == "Success") {
				
				console.log('response for account dtls------'+JSON.stringify(response));
			 	$('#accountId').text(response.body[0].accountId);
			 	$('#accountOwner').text(response.body[0].accountOwner);
				$('#accountName').text(response.body[0].accountName);
				$('#accountName1').text(response.body[0].accountName);
				$('#phone').text(response.body[0].phone);
				$('#accountSite').text(response.body[0].accountSite);
				$('#parentAccount').text(response.body[0].parentAccount);
				$('#accountOwner1').text(response.body[0].accountOwner);
				$('#phone1').text(response.body[0].phone);
				
				$('#fax').text(response.body[0].fax);
				$('#website1').text(response.body[0].website);
				$('#website').text(response.body[0].website);
				$('#accountNo').text(response.body[0].accountNo);
				$('#accountType').text(response.body[0].accountType);
				$('#ticketSymbol').text(response.body[0].ticketSymbol);
				$('#ownership').text(response.body[0].ownership);
				$('#industry').text(response.body[0].industry);
				$('#industry1').text(response.body[0].industry);
				$('#employee').text(response.body[0].employee);
				$('#employee1').text(response.body[0].employee);
				$('#accountRevenue').text(response.body[0].accountRevenue);
				$('#accountRevenue1').text(response.body[0].accountRevenue);
				$('#sicCode').text(response.body[0].sicCode);
				$('#billingStreet').text(response.body[0].billingStreet);
				$('#shippingStreet').text(response.body[0].shippingStreet);
				$('#billingCity').text(response.body[0].billingCity);
				$('#shippingCity').text(response.body[0].shippingCity);
				$('#billingState').text(response.body[0].billingState);
				$('#shippingState').text(response.body[0].shippingState);
				$('#billingCode').text(response.body[0].billingCode);
				$('#shippingCode').text(response.body[0].shippingCode);
				$('#billingCountry').text(response.body[0].billingCountry);
		        $('#shippingCountry').text(response.body[0].shippingCountry);
				$('#description').text(response.body[0].description);
				$('#rating').text(response.body[0].rating);
				$('#createdBy1').text(response.body[0].createdBy);
				$('#createdBy2').text(response.body[0].createdBy);
				
				$('#referenceContact').text(response.body[0].referenceContact);
				
			
				getNote(accountId,"1", "","");
				getMail(accountId);
				getDraft(accountId);
				getProduct(accountId);
				getCampaign(accountId);
				getTask(accountId);
				getAction(accountId);
				getMeeting(accountId);
				getCall(accountId);
				getActivity(accountId);
				getTimeline(accountId);
				getDeals(accountId);
				getCount(accountId);
				getActionDeals(accountId)
			}
		}
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
	   item['campExpectRevenue']   =	$("#campExpectRevenue").val();
	   dataset.push(item);
	}
	
	console.log('dataset for campaign------'+JSON.stringify(dataset));
	saveCampaign(dataset);
}



function saveCampaign(dataset) {
	//alert('saveCampaign');return false;
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
					
				}
			},
			error : function(datas) {
			}
		})

	}

function saveMultiFileForAccount(event) {
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

function saveAccountEmails(){
	var item = {};
	$('.loader').show();
	var empId=$("#accountId").text();
	var fromEmail =$("#fromEmail").val();
	var toMail =$("#toMail").val();
	var mailSubject =$("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName =$("#docName").val();
	
	
	if(empId){
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
				if ($("#draftId").val()) {
					fileName = $(this).find(".uploadHidCls").val(); 					 
 				}else{
 					 x = []; 
 				}
					
			}
			uploadData = {};
			uploadData['documnentName'] = $("#documentName").val();
			uploadData['documentFile'] = x;
			uploadData['fileName'] = fileName;
			uploadData['imageNameEdit'] =  $(this).find(".uploadHidCls").val(); 
			uploadList.push(uploadData);
			

		});
		setTimeout(function() {
			
				item.emailAccountId = empId;
				item.employeeId =$('#accountOwner1').text();
				item.fromEmail = fromEmail;
				item.toMail = toMail;
				item.mailSubject = mailSubject;
				item.commentck = comment;
				item.docName = docName;
				item.documentList = uploadList;
				item.draftId = $('#draftId').val();
				//console.log("employee document---------"+JSON.stringify(item));
				saveAccountEmailDtls(item);
			
		}, 100)
		
	} else{
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}

function saveAccountEmailDtls(item){
	console.log("employee document11111111111---------"+JSON.stringify(item));
	console.log(item);//return false;
	$.ajax({
		type : "POST",
		url : "view-crm-contacts-add-emails-ajax",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(item),
		success : function(response) {
			$('.loader').hide();
			closeModelEmail();
			$("#messageParagraph").text("Mail Saved Successfully");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			location.reload(); 
		},
		error : function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	}) //ajax ends 
}

function saveAccountDrafts(){
	$('.loader').show();
	var item = {};
	var empId=$("#accountId").text();
	var fromEmail =$("#fromEmail").val();
	var toMail =$("#toMail").val();
	var mailSubject =$("#mailSubject").val();
	var comment = CKEDITOR.instances.commentck.getData();
	var docName =$("#docName").val();
	console.log(item,'draftdata');
	
	if(empId){
		
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
				item.emailAccountId = empId;
				item.employeeId = $('#accountOwner1').text();
				item.fromEmail = fromEmail;
				item.toMail = toMail;
				item.mailSubject = mailSubject;
				item.commentck = comment;
				item.docName = docName;
				item.documentList = uploadList;
				console.log("employee draft document---------"+JSON.stringify(item));
				saveAccountDraftDtls(item);
			
		}, 100)
		
	} else{
		$("#candMsg").text("");
		$("#candMsg").text("Save the personal details first!");
		$("#candValid").modal('show');
	}
}
function saveAccountDraftDtls(item){
	$.ajax({
		type : "POST",
		url : "view-crm-contacts-add-drafts",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(item),
		success : function(response) {
			if (response.message == "success") {
				$('.loader').hide();
				closeModelEmail();
				$("#messageParagraph").text("Draft Saved Successfully");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				location.reload();
			} else {

				//$('.loader').hide();
				//$("body").removeClass("overlay");
			}
		},
		error : function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})
}
function addMacroInfo(){
	var dataset = [];
	console.log(searchIDs);
	
	var emailType = $("input:radio[name=macroEmailType]:checked").val();
	//alert(emailType);
	
		   $("#tbodyMacro > tr").each(function(){
			   for (let i = 0; i < searchIDs.length; ++i) {
			   	   item = {};
				//   alert('hello------'+searchIDs[i]);
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
	//alert('saveMacro');return false;
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
	//alert(isOverWrite);return false;
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
	//alert('saveTask');return false;
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
				
				}
			},
			error : function(datas) {
			}
		})

	} 



function addMailInfo(){
	//alert('hi');return false;
	console.log('emails--------------',emails);
	var dataset = [];
	console.log(searchIDs);
	for (let i = 0; i < searchIDs.length; ++i) {
	   item = {};
	   item['leadId']   =	searchIDs[i];
		item['fromEmail']    =	$("#fromEmail").val();
		item['toMail']    =	emails[i];
		item['mailSubject']    =	$("#mailSubject").val();
		item['docnoid']  =	 $("#docnoid_").val();
		item['attachment']   =	$("#attachment").val();
		item['mailDescription']   =	$("#description").val();
		
		dataset.push(item);
	}
	
	console.log('dataset for task------'+JSON.stringify(dataset));
	//return false;
	saveMail(dataset);
}

function saveMail(dataset) {
	//alert('saveTask');return false;
		$.ajax({
			type : "POST",
			url : "view-crm-leads-save-mail",
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
	
	
function saveMultiFile(event) {
	var AssignItemQty = event.currentTarget.value;
	var currentFldId = event.currentTarget.getAttribute('id');
	var l = currentFldId.split("_");
	var counter = l[1];
	var currentFldId = "#" + currentFldId;
	var uFile = $(currentFldId)[0].files[0];
	var fileName = event.currentTarget.value;
//	alert(fileName);
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


function openNavTask() {
	//alert('hello');
	document.getElementById("mySidenavTask").style.cssText = "width: 290px; position: absolute; right:-10px; overflow: hidden; height:auto; top:420px;";

	document.getElementById("mainTask").style.width = "75%";
}

/* -----------------Campaigns Grid Start------------------ */
var campaignDefs = [{
		    headerCheckboxSelection: false,
		    headerCheckboxSelectionFilteredOnly: false,
		    checkboxSelection: false,
		    width: 10,
		    sortable: false,
		    filter: false,
		    resizable: true,
		    pinned: 'left',
		},
		{
		    headerName: "Campaign Name",
		    field: "campaignName",
		    pinned: 'left',
		    cellRenderer: function(params) {
		        return '<a onclick="openCampaignDetails(\''+params.data.campaignId+'\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("campaignFun") + '\', \'' + sessionStorage.getItem("campaignAct") + '\')" href="javascript:void(0)">' + params.data.campaignName + '</a>';
		    }
		},
		{
			 headerName: "Status",
			    field: "campaignStatus",
			    width: 200,
			    pinned: 'left',
		},
		{
			 headerName: "Type",
			    field: "campaignType",
			    cellStyle: {textAlign: 'center'}
		   
		},
		{
		   
		    headerName: "Start Date",
		    field: "startDate",
		    width: 250,
		},
		{
		    headerName: "End Date",
		    field: "endDate",
		    cellStyle: {textAlign: 'center'}
		},
		{
			headerName: "Expected Revenue",
		    field: "expectedRevenue",
		    cellStyle: {textAlign: 'center'}
		},
		{
		  
		    headerName: "Budgeted Cost",
		    field: "budgetedCost",
		    cellStyle: {textAlign: 'center'}

		}]
		var gridOptionsCampaigns = {
				columnDefs : campaignDefs,
				rowSelection : 'multiple',
				groupSelectsChildren : true,
				suppressRowClickSelection : true,
				suppressAggFuncInHeader : true,
				defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 251,
				height : 10
				}
			};

var leadDefs = [{
    headerCheckboxSelection: true,
    headerCheckboxSelectionFilteredOnly: true,
    checkboxSelection: true,
    width: 10,
    sortable: false,
    filter: false,
    resizable: true,
    pinned: 'left',
},
{
    headerName: "Product Id",
    field: "productId",
    pinned: 'left',
    cellRenderer: function(params) {
        return '<a onclick=openDetails("' + params.data.productId + '") href="javascript:void(0)">' + params.data.productId + '</a>';
    }
},
{
  headerName: "Product Name",
	field: "productName",
	width: 250,
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Brand",
	field: "productBrand",
	width: 250,
	cellStyle: { textAlign: 'center' }
},
{
	headerName: "Category",
	field: "productCategories",
	width: 250,
	cellStyle: { textAlign: 'center' }
},
{
    headerName: "Created By",
    field: "createdBy",
    cellStyle: {textAlign: 'center'}
},
{
	
	    headerName: "Create Date",
	    field: "createdDate",
	    cellStyle: { textAlign: 'center' },
	    valueFormatter: params => {
	        if (params.value) {
	            const formattedDate = new Date(params.value);
	            return formattedDate.toLocaleDateString('en-US');
	        }
	        return '';
	    },
	

  
}]
var gridOptionsLead = {
	columnDefs : leadDefs,
	rowSelection : 'multiple',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
	sortable : true,
	filter : true,
	resizable : true,
	width : 251,
	height : 10
	}
};
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
	

function openCampaignDetails(campaignId){
	localStorage.setItem('campaignId', campaignId);
}

function filterNoteByDate(){
	var filterDate = $("#dateFilter").val();
	var filterTitle = $("#noteTitleSearch").val();
	var accountId = $("#accountId").text();
	
	statuss = true;
	getNote(accountId,"1",filterDate,filterTitle)
}

var statuss = true;
function getNote(accountId,cuPages,filterDate,filterTitle){
	getNoteAll(accountId,cuPages,filterDate,filterTitle)
}


let doclen = '';
function editNote(id){
		//alert('hello');
		$.ajax({
			type : "GET",
			url : "view-crm-leads-view-detail-edit-note?id=" + id,
			success : function(response) {
				var resp=JSON.parse(response.body[0]);
				console.log(resp.NoteDetails);
				$('#leadNoteId').val(resp.NoteDetails[0].noteId);
				$('#titleId').val(resp.NoteDetails[0].noteTitle);
				$('#noteId').val(resp.NoteDetails[0].noteDesc);
				$('#documentName').val(resp.NoteDetails[0].noteDocName);
				
				var doclist = JSON.parse(resp.NoteDetails[0].documentList);
				docListlen = doclist.length;
				$('#docListlen').val(docListlen);
				console.log(doclist,'doclistdoclist');
				if (doclist.length > 0) {
					var LightImg = '';
					 for(let i = 0;i< doclist.length;i++){
					 	var ext = doclist[i].fileName.split(".");
						$("#divFiles").html(doclist[i].fileName);
			           if (ext[1] == "jpg" || ext[1] == "png") {
			        	   LightImg	+= '<input type="hidden" id="uploadHidden_'+i+'" value="'+doclist[i].fileName+'" class="uploadHidCls">'
			        		   +'<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-picture-o" style="color: blue" onclick=viewImage("'
								+ doclist[i].fileName + '")></i></a>'+ doclist[i].fileName +'</div>';
						}else if (ext[1] == "pdf") {
							 LightImg += '<input type="hidden" id="uploadHidden_'+i+'" value="'+doclist[i].fileName+'" class="uploadHidCls">'
								+'<div class ="uploadicon position-l"><a class="example-image-link" target="_balnk"><i class="fa fa-file-pdf-o" style="color: red" onclick=viewImage("'
								+ doclist[i].fileName + '")></i></a> '+ doclist[i].fileName +'</div>';
						} else {
							 LightImg += "<div class='uploadicon position-l'> </div>";
						}  
						$("#divFiles").html(LightImg); 				
					} 
					
				}else{
					LightImg	+= '<tr><td>'
				 		+'<div class="control-group position-r">'
						+'<label class="custom-file-upload" for="fileUploader"'
						+'id="uploadFor_0"> <i class="ti-plus"></i>'
						+'</label>'
						+'<div class="controls">'
						+'<input type="file" class="document" id="fileUploader"'
						+'name="userImage"  multiple onchange="updateList()"/>'
						+'</div></div>' 
						+'<div id="divFiles">'
						+'<input type="hidden" id="uploadHidden_" class="uploadHidCls"/>'
					    +'</div></td>'
						+'<td><a class="primarybtns" onclick="saveNoteWithDoc()">Save</a></td>'
						+'</tr>'
						$("#doctbodyData").html(LightImg); 	
				}
				
			}
		})
}
var noteid='';
function deleteNote(id){
	$("#deleteModal").show();
	noteid = id;
}

function deleteNoteOnclick(){
		$.ajax({
			type : "GET",
			url : "view-crm-leads-view-detail-delete-note?id=" + noteid,
			success : function(response) {

				if (response.message == "Success") {
					console.log(response);
					location.reload();
				}
			}
		}); 
	
}
function viewImage(id){
	window.open("/document/crm/"+id,'_blank');
}
function cancelDeleteModalBtn(){
	$("#deleteModal").hide();
}
function getMail(accountId){
	$("#countEmail").empty().append(0);
	agGrid.simpleHttpRequest({
		url : "view-crm-leads-view-mail?id=" + accountId,
	}).then(function(data) {
		var resp=JSON.parse(data.body[0]);
		var len = resp.mailList.length;
		$('#totalEmp').find('span').html(len);
		$("#countEmail").empty().append(len);
		console.log(resp.mailList,'notice')
		mailgridOptions.api.setRowData(resp.mailList);
	});
	
}

function getDraft(accountId){
	agGrid.simpleHttpRequest({
		url : "view-crm-leads-view-draft?id=" + accountId,
	}).then(function(data) {
		var resp=JSON.parse(data.body[0]);
		var len = resp.draftList.length;
		$('#totalEmp').find('span').html(len);
		console.log(resp.draftList,'notice')
		draftgridOptions.api.setRowData(resp.draftList);
	});
}

function getProduct(accountId) {
	$("#countProduct").empty();
    agGrid.simpleHttpRequest({
        url: "view-crm-leads-view-detail-product?id=" + accountId,
    }).then(function (data) {
        console.log('---data----------------------', data.body);
        var prdLength = data.body.length;
         $("#countProduct").append(prdLength);
        gridOptionsLead.api.setRowData(data.body);
    });
}
function viewEmailDetails(id,tomail)
{
$('#myModalViewEmailContact').modal('show');

var empId =$("#accountId").text();

	$.ajax({
		type : "GET",
		url : "view-crm-leads-details-email-view?id1=" + empId+"&id2="+id+"&id3="+tomail,
		async : false,
		success : function(response) {
			
			if (response.message == "success") {
				console.log("edit", response.body)
				//$("#id").val(response.body.id);
				//$("#leadId").val(response.body.leadId);
				$("#fromEmail1").val(response.body.fromEmail);
				$("#toMail1").val(response.body.toMail);
				$("#mailSubject1").val(response.body.mailSubject);
				$("#docName1").val(response.body.docnoid);
				
				

				$("#emailAttachmentView").text(response.body.attachment);
				$("#emailAttachmentView").attr("href",response.body.ownerImageLink);

				
				CKEDITOR.instances['commentck1'].setData(response.body.commentck);
				$('#myModalViewEmailContact').modal('show');
				
				
				
			}
		},
		error : function(data) {
			console.log(data)
			$("#reqId").val("");
			$("#date").val("");
			$("#eligibility").val("");
			$("#loanamt").val("");
			$("#ternure").val("");
			$("#intrestRate").val("");
			$("#status").val("");
		}
	});

}

function getCampaign(accountId){
	$("#countCampaign").empty(); 
	agGrid.simpleHttpRequest({
		url : "view-crm-leads-view-detail-campaign?id=" + accountId,
		
	}).then(function(data) {
		console.log('---daata----------------------',data.body)
       var len = data.body.length
		$("#countCampaign").append(len);
		
		gridOptionsCampaigns.api.setRowData(data.body);
		
	});
}

function editTask(taskId) {
	localStorage.setItem('taskId', taskId);
}

function getTask(accountId){
	$.ajax({
		type : "GET",
		url : "view-crm-leads-view-detail-task?id=" + accountId,
		success : function(response) {

			if (response.message == "Success") {
				$("#taskActivity").empty(); // Clear the existing content
				trCount = '';
				var countItm=response.body.length;
			//	console.log("response for task-------"+JSON.stringify(response));
				//return false;
				var countOpen=0,countClose=0;
				for (var i = 0; i < response.body.length; i++) {
					
					var taskStatus = response.body[i].taskStatus;
					taskId =  response.body[i].taskId;
					if(taskStatus !="Completed"){
						var mailRow='<li>'
							+ '<a href="javascript:void(0)" class="toptxt" onclick="editTask(\''+taskId+'\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("taskFun") + '\', \'' + sessionStorage.getItem("taskAct") + '\')">' + response.body[i].taskSubject + '</a>'
						   //+'<div class="toptxt">'+response.body[i].taskSubject+'</div>'
						    +'<div class="smalltxt">'+response.body[i].dueDate+'</div>'
						    +'<div class="smalltxt"><i class="bi bi-person-fill"></i> '+response.body[i].taskOwner+'</div>'
						    +'<p>Status : '+response.body[i].taskStatus+'</p>'
						    +'<p>Priority : '+response.body[i].taskPriority+'</p>'
																
						    +'</li>';
							trCount = mailRow;
							$("#taskActivity").append(trCount);	
							countOpen=countOpen+1;
							
					}else{
						var mailRow='<li>'
						    +'<div class="toptxt">'+response.body[i].taskSubject+'</div>'
						    +'<div class="smalltxt">'+response.body[i].dueDate+'</div>'
						    +'<div class="smalltxt"><i class="bi bi-person-fill"></i> '+response.body[i].taskOwner+'</div>'
						    +'<p>Status : '+response.body[i].taskStatus+'</p>'
						    +'<p>Priority : '+response.body[i].taskPriority+'</p>'
																
						    +'</li>';
							trCount = mailRow;
							$("#closedTaskActivity").append(trCount);
							countClose=countClose+1;
							
						
					}
					

					} 
					
					$("#openTasks").text(countOpen);
					localStorage.setItem("countOpen", countOpen);
					
					$("#closeTasks").text(countClose);
					localStorage.setItem("countClose", countClose);
					getCount(accountId);
					
				if(countItm<1){
				var mailRow='<li>'
				    +'<div >No Record Found</div>'
														
				    +'</li>'
						trCount = mailRow;
						$("#taskActivity").append(trCount);
				}
				//console.log('response for leadId------'+JSON.stringify(response));
								
			}
		}
	});
}

function editMeetingTitle(meetingId){
	localStorage.setItem('meetingId', meetingId);

}
/*function getUrl(module, fun, activity) {
	$.ajax({
		type : "GET",
		url : "/index-get-breadcrumb-data?moduleId=" + module + "&fun="
				+ fun + "&activity=" + activity,
		async : false,
		success : function(response) {
			if (response.message == "Unsuccess") {
				console.log(JSON.stringify(response));
				
				modOnclick(fun);
				callActivity(activity, response.body.actURL);

			}
		},
		error : function(data) {
		}
	});
}  */
//getMeeting


function getMeeting(accountId){
	$.ajax({
		type : "GET",
		url : "view-crm-leads-view-detail-meeting?id=" + accountId,
		success : function(response) {

			if (response.message == "Success") {
				$("#meetingActivity").empty();
				trCount = '';
				var countItm=response.body.length;
			//	console.log("response for meeting-------"+JSON.stringify(response));
				//return false;
				var countOpenM=0,countCloseM=0;
				for (var i = 0; i < response.body.length; i++) {
					var meetingStatus=response.body[i].meetingStatus;
					meetingId =  response.body[i].meetingId;
					//console.log("meetingStatus---------"+meetingStatus);
					if(meetingStatus !="Completed"){
						var mailRow='<li>'
						    + '<a href="javascript:void(0)" class="toptxt" onclick="editMeetingTitle(\''+meetingId+'\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("meetingFun") + '\',\'' + sessionStorage.getItem("meetingAct") + '\')">' + response.body[i].meetingTitle + '</a>'

						 //   +'<div class="toptxt">'+response.body[i].meetingTitle+'</div>'
						    +'<div class="smalltxt">Meeting Status : '+response.body[i].meetingStatus+'</div>'
						    +'<p>Meeting From : '+response.body[i].meetingFromDate+' - <span> '+response.body[i].meetingFromTime+'</span></p>'
						    +'<p>Meeting To : '+response.body[i].meetingToDate+' - <span> '+response.body[i].meetingToTime+'</span></p>'
						    +'<div class="smalltxt"><i class="bi bi-person-fill"></i> '+response.body[i].meetingHost+'</div>'
							   
						    +'</li>';
						    
							trCount = mailRow;
							$("#meetingActivity").append(trCount);
							countOpenM=countOpenM+1;
							
					}else{
						var mailRow='<li>'
						    +'<div class="toptxt">'+response.body[i].meetingTitle+'</div>'
						    +'<div class="smalltxt">Meeting Status : '+response.body[i].meetingStatus+'</div>'
						    +'<p>Meeting From : '+response.body[i].meetingFromDate+' - <span> '+response.body[i].meetingFromTime+'</span></p>'
						    +'<p>Meeting To : '+response.body[i].meetingToDate+' - <span> '+response.body[i].meetingToTime+'</span></p>'
						    +'<div class="smalltxt"><i class="bi bi-person-fill"></i> '+response.body[i].meetingHost+'</div>'
							   
						    +'</li>';
						    
							trCount = mailRow;
							$("#closedMeetingActivity").append(trCount);
							countCloseM=countCloseM+1;
							
					}
				} 
				
			    $("#openMeetings").text(countOpenM);
				localStorage.setItem("countOpenM", countOpenM);
				$("#closeMeetings").text(countCloseM);
				localStorage.setItem("countCloseM", countCloseM);
				getCount(accountId);
				
				if(countItm<1){
				var mailRow='<li>'
				    +'<div >No Record Found</div>'
														
				    +'</li>'
						trCount = mailRow;
						$("#meetingActivity").append(trCount);
				}
				//console.log('response for leadId------'+JSON.stringify(response));
								
			}
		}
	});
}


function editCalls(callId){
	localStorage.setItem('callId', callId);
}

//getCall


function getCall(accountId){
	$.ajax({
		type : "GET",
		url : "view-crm-leads-view-detail-call?id=" + accountId,
		success : function(response) {

			if (response.message == "Success") {
				trCount = '';
				var countItm=response.body.length;
				console.log("response for call-------"+JSON.stringify(response));
				//return false;
				var countOpenC=0,countCloseC=0;
				for (var i = 0; i < response.body.length; i++) {
					var callStatus=response.body[i].callStatus;
					console.log("callStatus---------"+callStatus);
					callId =  response.body[i].callId;
					if(callStatus !="Completed"){
						var mailRow='<li>'
						    //+'<div class="toptxt">'+response.body[i].callSubject+'</div>'
						    + '<a href="javascript:void(0)" class="toptxt" onclick="editCalls(\''+callId+'\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("callFun") + '\',\'' + sessionStorage.getItem("callAct") + '\')">' + response.body[i].callSubject + '</a>'
						    +'<p>Call At : '+response.body[i].callStartDate+' - <span> '+response.body[i].callStartTime+'</span></p>'
						    +'<div class="smalltxt"><i class="bi bi-person-fill"></i> '+response.body[i].callOwner+'</div>'
						    /*+'<div class="smalltxt">Call Related To : '+response.body[i].callToWhom+'</div>'*/
						    +'<p>Call Status : '+response.body[i].callStatus+'</p>'
							    
						    +'</li>';
						    
						    
							trCount = mailRow;
							$("#callActivity").append(trCount);
							countOpenC=countOpenC+1;
							
					}
					
					else{
						var mailRow='<li>'
						    +'<div class="toptxt">'+response.body[i].callSubject+'</div>'
						    +'<p>Call At : '+response.body[i].callStartDate+' - <span> '+response.body[i].callStartTime+'</span></p>'
						    +'<div class="smalltxt"><i class="bi bi-person-fill"></i> '+response.body[i].callOwner+'</div>'
						   /* +'<div class="smalltxt">Call Related To : '+response.body[i].callToWhom+'</div>'*/
						    +'<p>Call Status : '+response.body[i].callStatus+'</p>'
							    
						    +'</li>';
						    
						    
							trCount = mailRow;
							$("#closedCallActivity").append(trCount);
							
							countCloseC=countCloseC+1;
							
					} 
					

					} 
					$("#openCalls").text(countOpenC);
					localStorage.setItem("countOpenC", countOpenC);
					$("#closeCalls").text(countCloseC);
					localStorage.setItem("countCloseC", countCloseC);
					getCount(accountId);
				if(countItm<1){
				var mailRow='<li>'
				    +'<div >No Record Found</div>'
														
				    +'</li>'
						trCount = mailRow;
						$("#callActivity").append(trCount);
				}
				//console.log('response for leadId------'+JSON.stringify(response));
								
			}
		}
	});
}


function getTimeline(accountId){
  var promise3 = new Promise(function (resolve, reject) {

	        $.ajax({
	            type: "GET",
	            url : "view-crm-leads-view-detail-activity?id=" + accountId,
	            async: true,
	            success: resolve,
	            error: reject,
	        });
	    });
  promise3.then(function (response) {
    if (response.message === "Success") {	
		$("#updatedDates").empty();
		var activityName=response.body[0].activityName;
		var createdTime=response.body[0].createdTime;
		var createdOn=response.body[0].createdOn;
		 $("#updatedDates").append(createdTime);
		
  
      
    } else {
        console.log("Promise was rejected or the response was not successful.");
    }
});	 
}

//getActivity

function getActivity(accountId){
	//alert('hello activity');return false;
	$.ajax({
		type : "GET",
		url : "view-crm-leads-view-detail-activity?id=" + accountId,
		success : function(response) {
			if (response.message == "Success") {
				getActivityTimeline(response.body);				
			}
		}
	});
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
	
	
	
	// for new button
function newBtn() { 
	//	alert('hello');
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
		$("#demoSetReminder").hide();
		$("#demoMassUpdate").hide();
		$("#demoAddCampaign").hide();
		$("#demoUpdateResponse").hide();
		$("#demoPrintMailingLabels").hide();
		$("#demoMassConvert").hide();
		

		$('#pipelineId').val("");
		$('#oppertunity').val("");
		$('#expectedRevenue').val("");
		$('#probability').val("");
		$('#customer').val("");
		$('#email').val("");
		$('#phone').val("");
		$('#saleperson').val("");
		$('#salesTeam').val("");
		$('#date').val("");
		$("#star1").attr("checked", false);
		$("#star2").prop("checked", false);
		$("#star3").prop("checked", false);
		//$('#tags').val("");
		$('#campanyName').val("");
		$('#description').val("");
		$('#campanyName').val("");
		$('#addressStreet').val("");
		$('#addressStreet2').val("");
		$('#city').val("");
		$('#states').val("");
		$('#zip').val("");
		$('#country').val("");
		$('#website').val("");
		$('#language').val("");
		$('#contactName').val("");
		$('#tittle').val("");
		$('#jobPosition').val("");
		$('#mobile').val("");
		$('#referdBy').val("");
		$('#campaign').val("");
		$('#medium').val("");
		$('#source').val("");

		agGrid.simpleHttpRequest({
			url : "view-crm-leads-getDetails"
		}).then(function(data) {
			gridOptions.api.setRowData(data);
		});
	}


	function editDraft(id){
		//alert(id);
		var accountId=$("#accountId").text();
		$.ajax({
			type : "GET",
			url : "view-crm-leads-details-edit-draft?id1=" + accountId+"&id2="+id,
			async : false,
			success : function(response) {
				
				if (response.message == "success") {
					console.log("edit", response.body)
					//$("#id").val(response.body.id);
					//$("#leadId").val(response.body.leadId);
					$('#myModalAddEmail').modal('show');
					$("#closeMail").hide();
					$("#draftId").val(response.body.id);
					$("#fromEmail").val(response.body.fromEmail);
					$("#toMail").val(response.body.toMail);
					$("#mailSubject").val(response.body.mailSubject);
					$("#docName").val(response.body.docnoid);				
					CKEDITOR.instances['commentck'].setData(response.body.commentck);
					
					var fileName=response.body.attachment;
						var tbl = '';
					if (fileName != null) {
					tbl  += '<tr><td>'
							+'<div class="control-group position-r">'
							+'<label class="custom-file-upload" for="uploadDoc_1" id="uploadFor_1"> <i class="ti-plus"></i>'
							+'</label><div class="controls">'
							+'<input type="file" class="document" id="uploadDoc_1" name="userImage" accept="image/*" onchange="saveMultiFileForLead(event)" />'
							+'</div></div>'
							+'<input type="hidden" id="uploadHidden_1"  value="'+fileName+'" class="uploadHidCls">'
							+'<div id="uploadedBillDiv_1" align="center" class="uploadedBillCls"></div>'
							+'<div id="imageName_1" class="imageName">'+fileName+'</div>'
							+'</td></tr>'
							$("#doctbodyDataforcontact").html(tbl);
					}else{
						tbl += '<tr><td>'
							+'<div class="control-group position-r">'
							+'<label class="custom-file-upload" for="uploadDoc_1" id="uploadFor_1"> <i class="ti-plus"></i>'
							+'</label><div class="controls">'
							+'<input type="file" class="document" id="uploadDoc_1" name="userImage" accept="image/*" onchange="saveMultiFileForLead(event)" />'
							+'</div></div>'
							+'<input type="hidden" id="uploadHidden_1" class="uploadHidCls">'
							+'<div id="uploadedBillDiv_1" align="center" class="uploadedBillCls"></div>'
							+'<div id="imageName_1" class="imageName"></div>'
							+'</td></tr>'
							$("#doctbodyDataforcontact").html(tbl);
					}
				}
			},
			error : function(data) {
				console.log(data)
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

function getNameList1() {
	
	var relatedType=$("#relatedType").val();
	var searchVal = $("#relatedName").val();
	if(searchVal == ""){
	$("#suggesstion-box2_").hide();
	}
	if (relatedType && searchVal) {
				$.ajax({
				type : "GET",
				url : "view-crm-calls-autosearchDetailsRelated?id1=" + relatedType+"&id2="+searchVal,
				dataType : 'json',
				contentType : 'application/json',
				success : function(response) {
					if (response.message == "success") {
						
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue11(\''
										+ response.body[i].name
										+ '\',\''
										+ response.body[i].key
										+ '\')">'
										+ response.body[i].name
										+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-box2_").show();
							$("#suggesstion-box2_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list1">';
							content += '<div onClick="selectAutocompleteValue0()">'
									+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-box2_").show();
							$("#suggesstion-box2_").html(content);
						}
					}
				},
				error : function(data) {
					console.log(data);
				}
			})


	 }
	

	}

function selectAutocompleteValue11(name, relatedId) {

	if (name) {
	$("#relatedId").val(relatedId);
	$("#relatedName").val(name);
	$("#search").val(relatedId);
	$("#search").attr('data-procat', name);
	$("#suggesstion-box2_").hide();

	} else {
	$("#relatedId").val("");
	$("#relatedName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box2_").hide();

	}
	}
function selectAutocompleteValue0() {

	$("#relatedId").val("");

	$("#personName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-box2_").hide();

	}

function getNameList() {

	var callToWhom=$("#callToWhom").val();
	var lName = $("#lName").val();
	var cName = $("#cName").val();
	 if(lName == ""){
		$("#suggesstion-boxLead_").hide();
	}
	if(cName == ""){
		$("#suggesstion-boxContactCall_").hide();
	} 
	
	if(callToWhom=='Lead'){
		
	var searchVal = lName;
	
	$.ajax({
				type : "POST",
				url : "view-crm-calls-autosearchDetailsLead",
				dataType : 'json',
				contentType : 'application/json',
				data : searchVal,
				success : function(response) {
					if (response.code == "Success") {
						
						if (response.body.length != 0) {
							$("#search").css("background", "#FFF");
							var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
							for (var i = 0; i < response.body.length; i++) {
								content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueLead(\''
										+ response.body[i].name
										+ '\',\''
										+ response.body[i].key
										+ '\')">'
										+ response.body[i].name
										+ '</li>';
							}
							content += '</ul>';
							$("#suggesstion-boxLead_").show();
							$("#suggesstion-boxLead_").html(content);

						} else {
							$("#search").css("background", "#FFF");
							var content = '<div id="autocomplete-list1">';
							content += '<div onClick="selectAutocompleteContactValueLead()">'
									+ "No Data Found" + '</div>';
							content += '</div>';
							$("#suggesstion-boxLead_").show();
							$("#suggesstion-boxLead_").html(content);
						}
					}
				},
				error : function(data) {
					console.log(data);
				}
			})
	 }
	else{
		var searchVal = cName;
		$.ajax({
					type : "POST",
					url : "view-crm-calls-autosearchDetailsContact",
					dataType : 'json',
					contentType : 'application/json',
					data : searchVal,
					success : function(response) {
						if (response.code == "Success") {
							
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
								for (var i = 0; i < response.body.length; i++) {
									content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueContactCall(\''
											+ response.body[i].name
											+ '\',\''
											+ response.body[i].key
											+ '\')">'
											+ response.body[i].name
											+ '</li>';
								}
								content += '</ul>';
								$("#suggesstion-boxContactCall_").show();
								$("#suggesstion-boxContactCall_").html(content);

							}
							else {
								$("#search").css("background", "#FFF");
								var content = '<div id="autocomplete-list1">';
								content += '<div onClick="selectcontactcall()">'
										+ "No Data Found" + '</div>';
								content += '</div>';
								$("#suggesstion-boxContactCall_").show();
								$("#suggesstion-boxContactCall_").html(content);
							}
						}
					},
					error : function(data) {
						console.log(data);
					}
				})
	
		
		
	} 
	
}

	function selectAutocompleteValueLead(name,LeadId) {

	if (name) {
		
	//$("#personName").val("");
	$("#leadId").val(LeadId);
	
	$("#lName").val(name);
	$("#search").val(LeadId);
	$("#search").attr('data-procat', name);
	$("#suggesstion-boxLead_").hide();

	} else {
	$("#leadId").val("");

	$("#lName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxLead_").hide();

	}
	}
	function selectAutocompleteContactValueLead() {

	$("#leadId").val("");

	$("#lName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxLead_").hide();

	}
	
	
	function selectAutocompleteValueContactCall(name,ContactId) {

		if (name) {
			
		//$("#personName").val("");
		$("#contactId").val(ContactId);
		
		$("#cName").val(name);
		$("#search").val(ContactId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxContactCall_").hide();

		} else {
		$("#contactId").val("");

		$("#cName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxContactCall_").hide();

		}
		}
		function selectcontactcall() {

		$("#contactId").val("");

		$("#cName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxContactCall_").hide();

		}
	

	
		function checkCallDetails() {
			var id = $("#callToWhom").val();
			if (id == "Lead") {
				
				$("#leadId").val("");
				$(".leadNameCls").show();
				$(".contactNameCls").hide();
				$("#contactName").val("");
				$("#relatedType").prop('disabled', true);
				$("#relatedName").prop('disabled', true);
				}
			else {
				$("#contactId").val("");
				$(".contactNameCls").show();
				$(".leadNameCls").hide();
				$("#lName").val("");
				$("#relatedType").prop('disabled', false);
				$("#relatedName").prop('disabled', false);
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
						if (response.code == "Success") {
							
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
	
	function getNameListParticipants() {
		var search = $("#meetingParticipants").val();
		if(search == ""){
		$("#suggesstion-boxpart_").hide();
		}
			if (search) {
			$.ajax({
						type : "POST",
						url : "view-crm-meetings-autosearchDetailsContact",
						dataType : 'json',
						contentType : 'application/json',
						data : search,
						success : function(response) {
							if (response.code == "Success") {
								
								if (response.body.length != 0) {
									$("#search").css("background", "#FFF");
									var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
									for (var i = 0; i < response.body.length; i++) {
										content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueParticipants(\''
												+ response.body[i].name
												+ '\',\''
												+ response.body[i].key
												+ '\')">'
												+ response.body[i].name
												+ '</li>';
									}
									content += '</ul>';
									$("#suggesstion-boxpart_").show();
									$("#suggesstion-boxpart_").html(content);

								}
								else {
									$("#search").css("background", "#FFF");
									var content = '<div id="autocomplete-list1">';
									content += '<div onClick="selectAutocompleteParticipantsValue()">'
											+ "No Data Found" + '</div>';
									content += '</div>';
									$("#suggesstion-boxpart_").show();
									$("#suggesstion-boxpart_").html(content);
								}
							}
						},
						error : function(data) {
							console.log(data);
						}
					})
		
			}
		
			
			
		} 
	

		function selectAutocompleteValueParticipants(name, participantId) {

		if (name) {
		//$("#personName").val("");
		$("#participantId").val(participantId);
		$("#meetingParticipants").val(name);
		$("#search").val(participantId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxpart_").hide();

		} else {
		$("#participantId").val("");

		$("#meetingParticipants").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxpart_").hide();

		}
		}
		function selectAutocompleteParticipantsValue() {

		$("#participantId").val("");

		$("#meetingParticipants").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxpart_").hide();

		}
		
		function checkMeetingDetails() {
			var id = $("#relatedMeetingTo").val();
			if (id == "Lead") {
				$(".leadNameCls").show();
				$(".contactNameCls").hide();
				$("#contactName").val("");
				$("#relatedType").prop('disabled', true);
				$("#relatedName").prop('disabled', true);
				}
			else {
				$(".contactNameCls").show();
				$(".leadNameCls").hide();
				$("#leadName").val("");
				$("#relatedType").prop('disabled', false);
				$("#relatedName").prop('disabled', false);
			}
		}
		
		function getNameLeadContactList() {
			var relatedMeetingTo=$("#relatedMeetingTo").val();
			var leadName = $("#meetingleadName").val();
			var contactName = $("#meetingcontactName").val();
			 if(leadName == ""){
				$("#suggesstion-boxmeetingLead_").hide();
			}
			if(contactName == ""){
				$("#suggesstion-boxmeetingContact_").hide();
			} 
			
			if(relatedMeetingTo=='Lead'){
			var searchVal = leadName;
			$.ajax({
						type : "POST",
						url : "view-crm-meetings-autosearchLead",
						dataType : 'json',
						contentType : 'application/json',
						data : searchVal,
						success : function(response) {
							if (response.code == "Success") {
								
								if (response.body.length != 0) {
									$("#search").css("background", "#FFF");
									var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
									for (var i = 0; i < response.body.length; i++) {
										content += '<li class="autocompletedata cp" onClick="selectAutocompleteMeetingLeadValue(\''
												+ response.body[i].name
												+ '\',\''
												+ response.body[i].key
												+ '\')">'
												+ response.body[i].name
												+ '</li>';
									}
									content += '</ul>';
									$("#suggesstion-boxmeetingLead_").show();
									$("#suggesstion-boxmeetingLead_").html(content);

								} else {
									$("#search").css("background", "#FFF");
									var content = '<div id="autocomplete-list1">';
									content += '<div onClick="selectAutocompleteMeetingLead()">'
											+ "No Data Found" + '</div>';
									content += '</div>';
									$("#suggesstion-boxmeetingLead_").show();
									$("#suggesstion-boxmeetingLead_").html(content);
								}
							}
						},
						error : function(data) {
							console.log(data);
						}
					})
			 }
			else{
				var searchVal = contactName;
				$.ajax({
							type : "POST",
							url : "view-crm-meetings-autosearchContact",
							dataType : 'json',
							contentType : 'application/json',
							data : searchVal,
							success : function(response) {
								if (response.code == "Success") {
									
									if (response.body.length != 0) {
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
										for (var i = 0; i < response.body.length; i++) {
											content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueMeetingContact(\''
													+ response.body[i].name
													+ '\',\''
													+ response.body[i].key
													+ '\')">'
													+ response.body[i].name
													+ '</li>';
										}
										content += '</ul>';
										$("#suggesstion-boxmeetingContact_").show();
										$("#suggesstion-boxmeetingContact_").html(content);

									}
									else {
										$("#search").css("background", "#FFF");
										var content = '<div id="autocomplete-list1">';
										content += '<div onClick="selectAutocompleteContactMeetingValue()">'
												+ "No Data Found" + '</div>';
										content += '</div>';
										$("#suggesstion-boxmeetingContact_").show();
										$("#suggesstion-boxmeetingContact_").html(content);
									}
								}
							},
							error : function(data) {
								console.log(data);
							}
						})
			
				
				
			} 
			

			}

			function selectAutocompleteMeetingLeadValue(name,LeadId) {

			if (name) {
				
			//$("#personName").val("");
			$("#leadId").val(LeadId);
			
			$("#meetingleadName").val(name);
			$("#search").val(LeadId);
			$("#search").attr('data-procat', name);
			$("#suggesstion-boxmeetingLead_").hide();

			} else {
			$("#leadId").val("");

			$("#meetingleadName").val("");

			$("#search").val("");
			$("#search").attr('data-procat', "");
			$("#suggesstion-boxmeetingLead_").hide();

			}
			}
			function selectAutocompleteMeetingLead() {

			$("#leadId").val("");

			$("#meetingleadName").val("");

			$("#search").val("");
			$("#search").attr('data-procat', "");
			$("#suggesstion-boxmeetingLead_").hide();

			}
			
			
			function selectAutocompleteValueMeetingContact(name,ContactId) {
				if (name) {
				$("#contactId").val(ContactId);
				
				$("#meetingcontactName").val(name);
				$("#search").val(ContactId);
				$("#search").attr('data-procat', name);
				$("#suggesstion-boxmeetingContact_").hide();

				} else {
				$("#contactId").val("");

				$("#meetingcontactName").val("");

				$("#search").val("");
				$("#search").attr('data-procat', "");
				$("#suggesstion-boxmeetingContact_").hide();

				}
				}
				function selectAutocompleteContactMeetingValue() {

				$("#contactId").val("");

				$("#meetingcontactName").val("");

				$("#search").val("");
				$("#search").attr('data-procat', "");
				$("#suggesstion-boxmeetingContact_").hide();

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
						if (response.code == "Success") {
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



function getStateDetails(){
	
	var cname = $('#country').val();
	if (cname) {
		//alert(cname);
		//$("#dname").empty();
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
						//alert("error");
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
		//alert(stateId);return false;
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
				//alert("error");
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
		//alert('task add');
		var obj = {};
		obj.taskAccountId=$("#accountId").text();
		obj.taskId = $('#taskId').text();
		obj.taskOwner = $('#taskOwner').val();
		obj.ownerName =$('#accountOwner1').text();
		obj.pageType = "Account";
		obj.taskSubject = $('#taskSubject').val();
		obj.dueDate = $('#dueDate').val();
		obj.taskContactName = $('#taskContactName').val();
		obj.contactId = $('#contactId').val();
		obj.taskAccountName = $('#taskAccountName').val();
		obj.accountId = $('#accountId').val();
		obj.taskStatus = $('#taskStatus').val();
		obj.taskPriority = $('#taskPriority').val();
		
	    var ReminderYesOrNo = $("input[name='ReminderYesOrNo']:checked").val();
		obj.reminderYesOrNo = ReminderYesOrNo;	
		
		var RepeateYesOrNo = $("input[name='RepeateYesOrNo']:checked").val();
		obj.repeateYesOrNo =   RepeateYesOrNo;
		
		if(ReminderYesOrNo == 'Yes'){
			obj.reminderDateid = $('#reminderDateid').val();
			obj.reminderTime = $('#reminderTime').val();
			obj.taskAlertBy = $('#taskAlertBy').val();
		}else{
			obj.reminderDateid = "";
			obj.reminderTime = "";
			obj.taskAlertBy = "";
		}
		
		obj.description = $('#description').val(); 
		
		console.log(obj);
		
		var validation = true;
	
		if (obj.taskSubject == null || obj.taskSubject == "") {
			validation = validationUpdated("Task Subject Required",
				"taskSubject");
		}
		if (obj.dueDate == null || obj.dueDate == "") {
			validation = validationUpdated("Due Date Required",
				"dueDate");
		}
		if (obj.taskStatus == null || obj.taskStatus == "") {
			validation = validationUpdated("Task status Required",
				"taskStatus");
		}
		if (obj.taskPriority == null || obj.taskPriority == "") {
			validation = validationUpdated("Task priority Required",
				"taskPriority");
		}

		/* FORM VALIDATION ENDS*/
        var accountId=$("#accountId").text();
		if (validation) {
			$('.loader').show();
			//alert('save');
			$.ajax({
				type : "POST",
				url : "view-crm-leads-detail-add-task-dtls",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					console.log(response);
					if (response.message == "Success") {
					getTask(accountId);
					getAction(accountId);
					getTimeline(accountId);
					closeModelTask();
					$("#messageParagraph").text("Task Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					$('.loader').hide();
					}
				},
				error : function(data) {

					console.log(data);
				}
			})
		}
		
	}
	
	function addMeetingInfo() {
		
		var executiveSelect = $("#meetingHost");
		var selectedOption = executiveSelect.find(":selected");
		var executiveMail = selectedOption.data("code") || '';
		
		if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
	     ccMeetingMail = ccMeetingMail.slice(0, -1);
	     }
		var type=$('#relatedMeetingTo').val();
		var obj = {};
		obj.meetingAccountId=$("#accountId").text();
		obj.ownerName =$('#accountOwner1').text();
		obj.meetingType = "Account";
		obj.meetingId = $('#meetingId').text();
		obj.meetingTitle = $('#meetingTitle').val();
		obj.meetingLocation = $('#meetingLocation').val();
		obj.isThisOnlineMeeting = $('#isThisOnlineMeeting').val();
		obj.isAllDay = $('#isAllDay').val();
		obj.meetingFromDate = $('#meetingFromDate').val();
		obj.meetingFromTime = $('#meetingFromTime').val();
		obj.meetingToDate = $('#meetingToDate').val();
		obj.meetingToTime = $('#meetingToTime').val();
		obj.meetingHost = $('#meetingHost').val();
		obj.leadName = $('#meetingleadName').val();
		obj.leadId = $('#leadId').val();
		//obj.meetingleadId = $('#leadId').val();
		obj.contactName = $('#meetingcontactName').val();
		obj.contactId = $('#contactId').val();
		//obj.meetingParticipants = $('#meetingParticipants').val();
		//obj.participantId = $('#participantId').val();
		obj.relatedMeetingTo = $('#relatedMeetingTo').val();
		obj.meetingStatus = $('#meetingStatus').val();
		obj.isRepeat = $('#isRepeat').val();
		obj.isAllDayRepeat = $('#isAllDayRepeat').val();
		obj.meetingRepeatFromDate = $('#meetingRepeatFromDate').val();
		obj.meetingRepeatFromTime = $('#meetingRepeatFromTime').val();
		obj.meetingRepeatToDate = $('#meetingRepeatToDate').val();
		obj.meetingCalendarRepeatToTime = $('#meetingCalendarRepeatToTime').val();
		obj.repeatType = $('#repeatType').val();
		obj.description = $('#description').val();
		obj.participantId = JSON.stringify(participantData); 
	    obj.ccMail = ccMeetingMail; 
	    obj.excutiveMail = executiveMail; 
		console.log(obj);//return false;
			
		
		var validation = true;
		var validation = true;

		if (obj.meetingTitle == null || obj.meetingTitle == "") {
			validation = validationUpdated("Meeting title Required", "meetingTitle");
		}
		if (obj.meetingLocation == null || obj.meetingLocation == "") {
			validation = validationUpdated("Meeting Location Required", "meetingLocation");
		}
		if (obj.meetingFromDate == null || obj.meetingFromDate == "") {
			validation = validationUpdated("Meeting From Date Required", "meetingFromDate");
		}
		if (obj.meetingFromTime == null || obj.meetingFromTime == "") {
			validation = validationUpdated("Meeting From Time Required", "meetingFromTime");
		}
		if (obj.meetingToDate == null || obj.meetingToDate == "") {
			validation = validationUpdated("Meeting To Date Required", "meetingToDate");
		}
		if (obj.meetingToTime == null || obj.meetingToTime == "") {
			validation = validationUpdated("Meeting To Time Required", "meetingToTime");
		}
		if (obj.meetingStatus == null || obj.meetingStatus == "") {
			validation = validationUpdated("Meeting Status Required", "meetingStatus");
		}
		/* FORM VALIDATION ENDS*/
         var accountId=$("#accountId").text();
		if (validation) {
			closeModelMeeting();
		    $('.loader').show();
			$.ajax({
				type : "POST",
				url : "view-crm-leads-detail-add-meeting-dtls",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					console.log(response);
					if (response.message == "Success") {
					getAction(accountId);
					getTimeline(accountId);
					
					getMeeting(accountId);
					
					
					$('.loader').hide();
					$("#messageParagraph").text("Meeting Saved Successfully");
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show');
					}
				},
				error : function(data) {

					console.log(data);
				}
			})
		}

	}
	
	function addCallInfo() {
	  
		var executiveSelect = $("#callOwner");
		var selectedOption = executiveSelect.find(":selected");
		var executiveMail = selectedOption.data("code") || '';
		
		if (ccMeetingMail.length > 0 && ccMeetingMail.endsWith(',')) {
	     ccMeetingMail = ccMeetingMail.slice(0, -1);
	     }
	   
		
		var id = $("#callToWhom").val();
		var obj = {};
		obj.callAccountId=$("#accountId").text();
		obj.callId = $('#callId').text();
		obj.ownerName =$('#accountOwner1').text();
		obj.pageType = "Account";
		obj.callToWhom = $('#callToWhom').val();
		obj.leadName = $('#lName').val();
		obj.leadId = $('#leadId').val();
		//obj.callleadId = $('#leadId').val();
		obj.contactName = $('#cName').val();
		obj.contactId = $('#contactId').val();
		obj.relatedType = $('#relatedType').val();
		obj.relatedName = $('#relatedName').val();
		obj.relatedId = $('#relatedId').val();
		obj.callType = $('#callType').val();
		obj.callStatus = $('#callStatus').val();
		obj.callStartDate = $('#callStartDate11').val();
		obj.callStartTime = $('#callStartTime11').val();
		
		obj.callOwner = $('#callOwner').val();
		obj.callSubject = $('#callSubject').val();
		obj.callReminder = $('#callReminder').val();
		obj.callPurpose = $('#callPurpose').val();
		obj.callAgenda = $('#callAgenda').val();
		obj.participantId = JSON.stringify(participantData); 
	    obj.ccMail = ccMeetingMail; 
	    obj.excutiveMail = executiveMail; 
		console.log(obj);

		/* FORM VALIDATION STARTS*/
		var validation = true;
	    if (obj.callStatus == null || obj.callStatus == "") {
		validation = validationUpdated("Call Status Required",
			"callStatus");
		}
	
		if (obj.callSubject == null || obj.callSubject == "") {
			validation = validationUpdated("Subject Required",
				"callSubject");
		}
		if (obj.callPurpose == null || obj.callPurpose == "") {
			validation = validationUpdated("Call purpose Required",
				"callPurpose");
		}
		if (obj.callStartDate11 == null || obj.callStartDate11 == "") {
			validation = validationUpdated("Call Date Required",
				"callStartDate11");
		}
		if (obj.callStartTime11 == null || obj.callStartTime11 == "") {
			validation = validationUpdated("Call Time Required",
				"callStartTime11");
		}
        var accountId=$("#accountId").text();
		if (validation) {
			closeModelCall();
	        $('.loader').show();
			$.ajax({
				type : "POST",
				url : "view-crm-leads-detail-add-call-dtls",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					console.log(response);
					if (response.message == "Success") {
						getActivity(accountId);
				        getAction(accountId);
					     getTimeline(accountId);
						 getCall(accountId);
					    $('.loader').hide();
						$("#messageParagraph").text("Call Saved Successfully");
						$("#msgOkModal").removeClass("btn3");
						$("#msgOkModal").addClass("btn1");
						$("#msgModal").modal('show');
					}
				},
				error : function(data) {

					console.log(data);
				}
			})
		}

	}
	
	
	
	function addCampaignInfo() {
		var obj = {};
		obj.campaignId = $('#campaignId').text();
		obj.campaignAccountId=$("#accountId").text();
		obj.ownerName =$('#accountOwner1').text();
		obj.pageType = "Account";
		obj.campaignOwner = $('#campaignOwner').val();
		obj.campaignType = $('#campaignType').val();
		obj.campaignName = $('#campaignName').val();
		obj.campaignStatus = $('#campaignStatus').val();
		obj.startDate = $('#startDate').val();
		obj.endDate = $('#endDate').val();
		obj.expectedRevenue = $('#expectedRevenue').val();
		obj.budgetedCost = $('#budgetedCost').val();
		obj.actualCost = $('#actualCost').val();
		obj.expectedResponse = $('#expectedResponse').val();
		obj.numberSent = $('#numberSent').val();
		obj.description = $('#description').val(); 
		
		console.log("Object mapping for Campaign--------"+JSON.stringify(obj));

		var validation = true;

  		if (obj.campaignType == null || obj.campaignType == "") {
		validation = validationUpdated("Campaign Type Required",
			"campaignType");
		}
	
		if (obj.campaignName == null || obj.campaignName == "") {
			validation = validationUpdated("Campaign Name Required",
				"campaignName");
		}
		
		if (obj.startDate == null || obj.startDate == "") {
			validation = validationUpdated("Start Date Required",
				"startDate");
		}
		
		if (obj.endDate == null || obj.endDate == "") {
			validation = validationUpdated("End Date Required",
				"endDate");
		}
		
		if (obj.campaignStatus == null || obj.campaignStatus == "") {
			validation = validationUpdated("Campaign Status Required",
				"campaignStatus");
		}

		/* FORM VALIDATION ENDS*/
        var accountId=$("#accountId").text();
		if (validation) {
			$('.loader').show();
			$.ajax({
				type : "POST",
				url : "view-crm-accounts-detail-add-campaign-dtls",
				contentType : "application/json",
				data : JSON.stringify(obj),
				success : function(response) {
					console.log(response);
					if (response.message == "Success") {
						
				    getAction(accountId);
					getTimeline(accountId);
					getCampaign(accountId);
					getActivity(accountId);
					closeModelCampaign();
					
					  $('.loader').hide();
					  $("#messageParagraph").text("Campaign Saved Successfully");
					  $("#msgOkModal").removeClass("btn3");
					  $("#msgOkModal").addClass("btn1");
					  $("#msgModal").modal('show');
					}
				},
				error : function(data) {

					console.log(data);
				}
			})
		}

	}


function saveNoteWithDoc(){
	
	watchLocationPermission();
	
	if(locationPermission){
		$('.loader').show();
		$("body").removeClass("overlay");
		var item = {};
		var empId =$("#accountId").text();
		var titleId =$("#titleId").val();
		var noteId =$("#noteId").val();
		let folder = [];
		if(empId && titleId){
			
			var imageValid = true;
			var uploadList = [];
			let x = [];
			let fileName = '';
			//alert(document.getElementById("fileUploader").files.length)
			if(document.getElementById("fileUploader").files.length > 0){
		    for (let i = 0; i < document.getElementById("fileUploader").files.length; i++) 
		        {    
				let uFile = document.getElementById("fileUploader").files[i];
				fileName = uFile.name;
				let data = [];
				let x = [];
				if (fileName != '' && fileName != 'undefined' && fileName != null) {
					let reader = new FileReader();
					reader.readAsDataURL(uFile);
					let obj1 = {};
					reader.onload = function() {
						data = reader.result.split(",");
						x.push(data[1]);
						
						obj1.fileName = uFile.name;
						obj1.documentFile = x;
						folder.push(obj1);
						console.log(folder,'@@@@@@@@')
					};
					console.log(folder,'@@@@@@@@')
				}
	
			};
		   }
	
			if ($("#docListlen").val() > 0) {
			var div = document.getElementById('divFiles');
			let maxLen = div.querySelectorAll('input[type="hidden"]').length;
			if(maxLen > 0){
			for(let i =0;i<maxLen ;i++){
				fileName =document.getElementById('uploadHidden_' + i).value;
			 	x = []; 
			 	let obj = {};
				obj.fileName = fileName;
				obj.imageNameEdit = fileName;
				folder.push(obj);
			}
			}}
			setTimeout(function() {
				
					item.accountId = empId;
					item.employeeId =$('#accountOwner1').text();
					item.leadNoteId =$('#leadNoteId').val();
					item.titleId = titleId;
					item.noteId = noteId;
					item.documentList = folder; 
					item.latitude = latitude; 
					item.longitude = longitude; 
					console.log("employee document---------"+JSON.stringify(item));
				    saveAccountNoteDoc(JSON.stringify(item));
				
			}, 2000)
			
		} else{
			$("#messageParagraph").text("Note Title Required!");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$('.loader').hide();
		}
	} else{
		$("#messageParagraph").text(LocationMessage);
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
	}
}

function saveAccountNoteDoc(item){
	var accountId=$("#accountId").text();
	console.log(item); //return false;
 	$.ajax({
		type : "POST",
		url : "view-crm-leads-add-notes-ajax",
		dataType : "json",
		contentType : "application/json",
		data : item,
		success : function(response) {
			getNote(accountId,"1", "","");
			$('.loader').hide();
			$("#messageParagraph").text("Note Saved Successfully");
			$("#msgOkModal").removeClass("btn3");
			$("#msgOkModal").addClass("btn1");
			$("#msgModal").modal('show');
			$("#titleId").val("");
		    $("#noteId").val("");
		    $("#divFiles").html("");
			
			
		},
		error : function(data) {
			console.log(data);
			$('.loader').hide();
			$("body").removeClass("overlay");
		}
	})  //ajax ends
} 

////////////////////////////////////////////////////////////////////

function AddCampaignModal(index){
	$('#myModalAddCampaign').modal('show');
}

function addEmailModal(index){
	$('#myModalAddEmail').modal('show');
	 $('#toMail').val(toMail);
   
	$("#mailSubject").val("");
	$("#comment").val("");
	$("#docName").val("");
	$("#uploadList").val("");
	$("#bccMail").val("");
	$("#selected-mail-container").html("");
	CKEDITOR.instances['commentck'].setData("");
	//$("#selected-mail-container").html("");
}

function toggleCC() {
        document.getElementById("ccField").style.display = "block";
        document.getElementById("bccField").style.display = "block";
    }

function closeModelEmail(index) {
	$('#myModalAddEmail').modal('hide');
}

function closeViewModelEmail(index){
	$('#myModalViewEmailContact').modal('hide');
}



function closeModelCampaign(index) {
	$('#myModalAddCampaign').modal('hide');
}

function closeModelTask(index) {
	$('#myModalAddTask').modal('hide');
}

function closeModelMeeting(index) {
	$('#myModalAddMeeting').modal('hide');
}

function closeModelCall(index) {
	$('#myModalAddCall').modal('hide');
}



function getProductSearch() {
	
	var searchVal = $("#searchProduct").val();
	
	var rowCount = $('#countAssignProduct').val();
	var accountId =$("#accountId").text();
	var assigRow ="";
	if(rowCount>0){
		var assigRow ="Yes";
	}else{
		var assigRow ="No";
	}
	
	//alert('searchVal---------'+searchVal);
	if(searchVal == ""){
	$("#suggesstion-boxproduct_").hide();
	}
	var pageType="Account";
	if (searchVal) {

		$.ajax({
					type : "GET",
					url : "view-crm-leads-autosearchProduct?searchVal=" + searchVal +"&id="+accountId+"&assigRow="+assigRow+"&pageType="+pageType,
					
					success : function(response) {
					if (response.message == "Success") {
						console.log("product list for searchProductSearch----------"+JSON.stringify(response));
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
								for (var i = 0; i < response.body.length; i++) {
									content += '<li class="autocompletedata cp" onClick="selectAutocompleteValueProduct(\''
											+ response.body[i].name
											+ '\',\''
											+ response.body[i].key
											+ '\')">'
											+ response.body[i].name
											+ '</li>';
								}
								content += '</ul>';
								$("#suggesstion-boxproduct_").show();
								$("#suggesstion-boxproduct_").html(content);

							}
							else {
								$("#search").css("background", "#FFF");
								var content = '<div id="autocomplete-list1">';
								content += '<div onClick="selectAutocompleteValueProduct()">'
										+ "No Data Found" + '</div>';
								content += '</div>';
								$("#suggesstion-boxproduct_").show();
								$("#suggesstion-boxproduct_").html(content);
							}
						
						
						///////////////////////////////////
						
					}
					
					
				},
				error : function(data) {
					console.log(data);
				}
			});

	} 
}


function selectAutocompleteValueProduct(productName, productCode){
	$("#suggesstion-boxproduct_").hide();
	var rowCount = $('#countAssignProduct').val();
	var accountId =$("#accountId").text();
	var leadName =$("#leadName").text();
	var assigRow ="";
	if(rowCount>0){
		var assigRow ="Yes";
	}else{
		var assigRow ="No";
	}
	var pageType="Account";
	$.ajax({
			type : "GET",
			url : "view-crm-leads-details-product-view?id=" + accountId +"&id2="+assigRow+"&pageType="+pageType+"&productCode="+productCode,
			success : function(response) {
			if (response.message == "Success") {
				console.log("console response for product---------------------------------", response.body.length)
				//return false;
				
				$('#leadNameProduct').html(leadName);				
				
				var countItm=response.body.length;
				//alert('hello');
				$("#productTblBody").empty();
				for (var i = 0; i < countItm; i++) {
					
					//trCount = '';
					var taxable =response.body[i].taxable;
					if(taxable=='on'){
						taxable='True';
					}else{
						taxable='False';
					}
					var mailRow2='<tr><td>'						
						+ '<input type="checkbox" id="productCheckbox" name="productCheckbox" class="checkbox_check mr-5" onclick=clickProductCheck("'+response.body[i].productId		
						+ '")>'
						
						+'</td><td><a href="#" class="trlink">'+response.body[i].productName+'</a></td>'
						+'<td>'+response.body[i].productCode+'</td>'
						+'<td><a href="#" class="trlink">'+response.body[i].productVendor+'</a></td>'
						+'<td>$'+response.body[i].unitPrice+'</td>'
						+'<td>'+response.body[i].tax+'</td>'
						+'<td>'+taxable+'</td>'
						+'</tr>';
						trCount = mailRow2;
						$("#productTblBody").append(mailRow2);

					} 
				
				if(countItm<1){
					var mailRow='<tr>'
						+'<td colspan="6" align="center">No Record Found.</td></tr>';
						trCount = mailRow2;
						$("#productTblBody").append(mailRow2);
				}
				
			}
			
			
		},
		error : function(data) {
			console.log(data);
		}
	});
}

var checkedIDs=[];
var checkedVal=[];
function clickProductCheck(id){
	checkedVal.push(id);
	checkedIDs = $("#productTblBody input:checkbox:checked").map(function(){
		      return $(this).val();
		    }).get(); 
		    var checkedLength=checkedIDs.length;

		    console.log("checkedVal---------"+checkedVal);
		
}

function addProductToAccount(){
    var dataset = [];
    
    productCheckedId = "";
	for (var i = 0; i < checkedIDs.length; ++i) {
		productCheckedId = productCheckedId + '"' + checkedVal[i] + '",';
	}
	productCheckedId = productCheckedId.substring(0, productCheckedId.length - 1);
	console.log("productCheckedId--------------"+productCheckedId);
	
    for (let i = 0; i < checkedIDs.length; ++i) {
 	   item = {};
 	   item['productId']   =	checkedVal[i];
 	   item['accountId']   =	$("#accountId").text();
 	   item['pageType']   =	"Account";
 	   dataset.push(item);
 	}
 	
 	console.log('dataset for product add------'+JSON.stringify(dataset));
 	//return false;
 	productAssign(dataset);
}


function productAssign(dataset) {
		$.ajax({
		type : "POST",
		url : "view-crm-leads-add-product?id=" + productCheckedId,
		contentType : "application/json",
		data : JSON.stringify(dataset),
		success : function(response) {
			if (response.message == "Success") {
				
				$("#messageParagraph").text(
				"Data Saved Successfully");
				closeViewModelAddProduct();
				location.reload();
				
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
			}
		},
		error : function(datas) {
		}
	})

} 


function closeViewModelAddProduct(index){
	$('#addProductModal').modal('hide');
}


function scrollProduct(){
		  $('html, body').animate({
		    scrollTop: $("#products").offset().top - 250
		 }, 500); 	
}



function scrollNotes(){
	  $('html, body').animate({
	    scrollTop: $("#notes").offset().top - 250
	 }, 500); 	
}

function scrollDeals(){
	  $('html, body').animate({
	    scrollTop: $("#deals").offset().top - 250
	 }, 500); 	
}


function scrollContacts(){
	  $('html, body').animate({
	    scrollTop: $("#contacts").offset().top - 250
	 }, 500); 	
}

function scrollOpenActivities(){
	  $('html, body').animate({
	    scrollTop: $("#open").offset().top - 250
	 }, 500); 	
}


function scrollClosedActivities(){
	  $('html, body').animate({
	    scrollTop: $("#closed").offset().top - 250
	 }, 500); 	
}


function scrollEmails(){
	  $('html, body').animate({
	    scrollTop: $("#emails").offset().top - 250
	 }, 500); 	
}

function scrollCampaigns(){
	  $('html, body').animate({
	    scrollTop: $("#campaigns").offset().top - 250
	 }, 500); 	
}


function scrollQuotes(){
	  $('html, body').animate({
	    scrollTop: $("#quotes").offset().top - 250
	 }, 500); 	
}

function scrollSalesOrder(){
	  $('html, body').animate({
	    scrollTop: $("#salesorder").offset().top - 250
	 }, 500); 	
}


function scrollInvoices(){
	  $('html, body').animate({
	    scrollTop: $("#invoices").offset().top - 250
	 }, 500); 	
}


function scrollMemberAccount(){
	  $('html, body').animate({
	    scrollTop: $("#memberaccount").offset().top - 250
	 }, 500); 	
}


function scrollCases(){
	  $('html, body').animate({
	    scrollTop: $("#cases").offset().top - 250
	 }, 500); 	
}


$(document).ready(function () {
        $(document).on('click', "button", function (e) {
            $(this).closest('tr').remove();
        });
        var mailgridDiv = document.querySelector('#myGridMail');
		new agGrid.Grid(mailgridDiv, mailgridOptions);
		
		var draftgridDiv = document.querySelector('#myGridDraft');
	    new agGrid.Grid(draftgridDiv, draftgridOptions);
	    
	    var gridDiv = document.querySelector('#myGridDeals');
		new agGrid.Grid(gridDiv, gridOptionsDeals);

		var rowData = [];
		mailgridOptions.api.setRowData(rowData);
		var rowData = [];
		draftgridOptions.api.setRowData(rowData);
 });
    
    
	var filelist = new Array();
	function updateList() {
	    var input = document.getElementById('fileUploader');
	    var HTML = "<table>";
	    console.log(input.files.item, "......... Input file data.")
	    for (var i = 0; i < input.files.length; ++i) {
	        //filelist[i] = input.files.item(i).name;
	        filelist.push(input.files.item(i).name);
	        HTML += "<tr><td>" 
	              + filelist[i] 
	              + "</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button></button></td></tr>";
	    }
	    HTML += "</table>";
	    $("#divFiles").append(HTML);
	    /* output.innerHTML = HTML; */
	    console.log(filelist);
	}
	var columnMailDefs = [ {
		headerCheckboxSelection : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true,
		pinned : 'left'

	}, {
		headerName : "Subject",
		field : "mailSubject",
		pinned : 'left'

	}, {
		headerName : "Sent To",
		field : "toMail",
		pinned : 'left'

	}, {
		headerName : "Sent Date",
		field : "createdDate",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Sent By",
		field : "createdBy",
		cellStyle : {
			textAlign : 'center'
		}
	} 
	];

	var mailgridOptions = {
		columnDefs : columnMailDefs,
		/* rowSelection : 'multiple',
 */
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 250,
			height : 10
		},
	};
	var columnDraftDefs = [ {
		headerCheckboxSelection : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true,
		pinned : 'left'

	},
	{
		headerName : "Draft Id",
		field : "draftId",
		pinned : 'left',
		cellRenderer : function(params) {
			console.log(params,'draftttt')
			return '<a onclick=editDraft("'
			+ params.data.draftId
			+ '") href="javascript:void(0)">'
			+ params.data.draftId+'</a>';
		},

	},{
		headerName : "Subject",
		field : "mailSubject",
		pinned : 'left'

	}, {
		headerName : "Sent To",
		field : "toMail",
		pinned : 'left'

	}, {
		headerName : "Sent Date",
		field : "createdDate",
		cellStyle : {
			textAlign : 'center'
		},
	}, {
		headerName : "Sent By",
		field : "createdBy",
		cellStyle : {
			textAlign : 'center'
		},
	} ,
	{
		headerName : "Action",
		field : "draftId",
		cellStyle : {
			textAlign : 'center'
		},
		cellRenderer : function(params) {
			console.log(params,'draftttt')
			return '<a onclick=deleteDraft("'
			+ params.data.draftId
			+ '") href="javascript:void(0)">'
			+'<i class="fa fa-trash fa-icon img-hover" aria-hidden="true"></i> </a>';
		},
	} 
	];

	var draftgridOptions = {
		columnDefs : columnDraftDefs,
		/* rowSelection : 'multiple',
 */
		defaultColDef : {
			sortable : true,
			filter : true,
			resizable : true,
			width : 200,
			height : 10
		},
	};
	
    /* -----------------Deals Grid Start------------------ */
var dealsDefs = [{
		    headerCheckboxSelection: false,
		    headerCheckboxSelectionFilteredOnly: false,
		    checkboxSelection: false,
		    width: 10,
		    sortable: false,
		    filter: false,
		    resizable: true,
		    pinned: 'left',
		},
		{
		    headerName: "Deal Name",
		    field: "dealName",
		    pinned: 'left',
		    cellRenderer: function(params) {
		        return '<a onclick="openCAccountDeals(\''+params.data.dealId+'\');getUrl(\'MOD031\',\'' + sessionStorage.getItem("dealFun") + '\',\'' + sessionStorage.getItem("dealAct") + '\')" href="javascript:void(0)">' + params.data.dealName + '</a>';
		    }
		},
		{
			 headerName: "Amount",
			    field: "dealAmount",
			    width: 200,
			    cellStyle: {textAlign: 'right'},
			    pinned: 'left',
			    cellRenderer: function(params) {
		        const value = parseFloat(params.value);
		        if (!isNaN(value)) {
		            return value.toFixed(2);
		        } else {
		            return params.value;
		        }
		    }
		},
		{
			 headerName: "Stage",
			    field: "dealStage",
			     width: 200,
			    cellStyle: {textAlign: 'right'}
		   
		},
		{
		   
		    headerName: "Probability(%)",
		    field: "probability",
		    width: 200,
		     cellStyle: {textAlign: 'center'}
		},
		{
		    headerName: "Closing Date",
		    field: "dealClosingDate",
		     width: 200,
		    cellStyle: {textAlign: 'right'}
		},
		{
			headerName: "Type",
		    field: "dealType",
		    cellStyle: {textAlign: 'right'}
	

		}]
		var gridOptionsDeals = {
				columnDefs : dealsDefs,
				rowSelection : 'multiple',
				groupSelectsChildren : true,
				suppressRowClickSelection : true,
				suppressAggFuncInHeader : true,
				defaultColDef : {
				sortable : true,
				filter : true,
				resizable : true,
				width : 251,
				height : 10
				}
			};	
	
	var draftid='';
	function deleteDraft(id){
		$("#deleteDraftModal").show();
		draftid = id;
	}

	function deleteDraftOnclick(){
			$.ajax({
				type : "GET",
				url : "view-crm-leads-delete-draft?id=" + draftid,
				success : function(response) {

					if (response.message == "Success") {
						console.log(response);
						location.reload();
					}
				}
			}); 
		
	}
	function cancelDeleteDraftModalBtn(){
		$("#deleteDraftModal").hide();
	}
	
	function backToPage(event){
		 event.preventDefault();
		$('.loader').show();
		var baseUrl = (window.location).href;
		var rest = baseUrl.substring(0, baseUrl.lastIndexOf("/") + 1);
		window.location.href = rest+"view-crm-accounts"
		
		$('.loader').hide();
	}
	
	function editAccountInfo(){
		var accountId=$("#accountId").text();
		
		localStorage.setItem('accountId', accountId);
	}
	
function getTimeline(accountId) {
    var promise3 = new Promise(function (resolve, reject) {
        $.ajax({
            type: "GET",
            url: "view-crm-leads-view-detail-activity?id=" + accountId,
            async: true,
            success: resolve,
            error: reject,
        });
    });

    promise3.then(function (response) {
        if (response.message === "Success") {
            $("#updatedTimes").empty();
            $("#updatedDates").empty();

            var createdTime = response.body[0].createdTime;
            var createdOn = response.body[0].createdOn;

            $("#updatedDates").append(createdTime);
        } else {
            console.log("Promise was rejected or the response was not successful.");
        }
    });
}


 /*
*
**** This Function used for get Invite the Participants in Call & Meetings 
*  /* For Contact Parcitipants
*/
function getParticipantLead(searchVal, dropdown){
	
	 var modal = dropdown.closest('.modal');
	 
	 let val = $('#'+ modal.id + ' .inviteMeeting').val()
	 
	
	if (searchVal == "") {
	    $(".suggesstion-boxmeetingLead1_").hide();
	}
	let searchId = '';
	getNameListParticipants1(searchId, val, searchVal,"lead");
}

function getParticipantContact(searchVal, dropdown){
	
	var modal = dropdown.closest('.modal');
	 
	let val = $('#'+ modal.id + ' .inviteMeeting').val();
	
	if (searchVal == "") {
	    $(".suggesstion-boxmeetingContact1_").hide();
	}
	let searchId = '';
	getNameListParticipants1(searchId, val, searchVal,"contact");
}

function getParticipantExcecutive(searchVal, dropdown){
	var modal = dropdown.closest('.modal');
	 
	let val = $('#'+ modal.id + ' .inviteMeeting').val()

	if (searchVal == "") {
	    $(".suggesstion-boxmeetingExcutives_").hide();
	}
	let searchId = $("#userId").text();
	getNameListParticipants1(searchId, val, searchVal,"excutive");
}

 /*$(document).ready(function() {
        // Event listener for the select element
        $("#relatedMeetingTo1").on("change", function() {
            checkMeetingDetails1();
        });
        
        document.getElementById("meetingcontactName1").addEventListener("keyup", function(){
		
		let type = $("#relatedMeetingTo1").val();
		let searchVal = "";
		
		searchVal = $("#meetingcontactName1").val();
		if (searchVal == "") {
		    $("#suggesstion-boxmeetingContact1_").hide();
		}
		
		
		let searchId = '';
		
		getNameListParticipants1(searchId, type, searchVal,"contact");
	});
	
	document.getElementById("meetingleadName1").addEventListener("keyup", function(){
		
		let type = $("#relatedMeetingTo1").val();
		let searchVal = "";
		
		searchVal = $("#meetingleadName1").val();
		if (searchVal == "") {
		    $("#suggesstion-boxmeetingLead1_").hide();
		}
		
		let searchId = '';
		
		getNameListParticipants1(searchId, type, searchVal,"lead");
	});
        
    });*/

    function checkMeetingDetails1() {
        var id = $("#relatedMeetingTo1").val();
        if (id == "Lead1") {
            $(".leadNameCls1").show();
            $(".contactNameCls1").hide();
            $("#meetingcontactName1").val(""); // Clear contact name input
            $("#contactId").val(""); // Clear contact ID input
            $("#relatedType").prop('disabled', true);
            $("#relatedName").prop('disabled', true);
        } else if (id == "Contact1") {
            $(".contactNameCls1").show();
            $(".leadNameCls1").hide();
            $("#meetingleadName1").val(""); // Clear lead name input
            $("#leadId").val(""); // Clear lead ID input
            $("#relatedType").prop('disabled', false);
            $("#relatedName").prop('disabled', false);
        } else {
            // Handle other cases if needed
            $(".leadNameCls1").hide();
            $(".contactNameCls1").hide();
            $("#meetingleadName1").val("");
            $("#leadId").val("");
            $("#meetingcontactName1").val("");
            $("#contactId").val("");
            $("#relatedType").prop('disabled', false);
            $("#relatedName").prop('disabled', false);
        }
    }
	
	function getAccountList2() {
	
 	var search = $("#taskAccount1").val();

	if (search) {

		$.ajax({
					type : "POST",
					url : "view-crm-tasks-get-account-list",
					dataType : 'json',
					contentType : 'application/json',
					data : search,
					success : function(response) {
						if (response.code == "Success") {
							if (response.body.length != 0) {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list3" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
								for (var i = 0; i < response.body.length; i++) {
									content += '<li class="autocompletedata cp" onClick="selectAutocompleteValue3(\''
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
								$("#suggesstion-box3_").show();
								$("#suggesstion-box3_").html(content);

							} else {
								$("#search").css("background", "#FFF");
								var content = '<ul id="autocomplete-list3">';
								content += '<li onClick="selectAutocompleteValue2()">'
										+ "No Data Found" + '</li>';
								content += '</ul>';
								$("#suggesstion-box3_").show();
								$("#suggesstion-box3_").html(content);
							}
						}
					},
					error : function(data) {
						console.log(data);
					}
				})
	}

}

function selectAutocompleteValue3(accountId, accountName) {

	if (accountId) {
		

		$("#accountId").val(accountId);

		$("#taskAccount1").val(accountName);
		
		$("#search").val(accountName);
		$("#search").attr('data-procat', accountId);
		$("#suggesstion-box3_").hide();
	//	hideShowS();
		//checkForDuplicate(key,counter);

	} else {

		$("#accountId").val("");

		$("#taskAccount1").val("");
		
		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-box3_").hide();

	}
}

function getDeals(accountId) {
	
	$("#countDeals").empty(); 
        agGrid.simpleHttpRequest({
            url: 'view-crm-contact-deals-through-ajax?id=' + accountId
        }).then(function (data) {
            console.log('---data for Deals----------------------', data.body);
            var len = data.body.length;
            $("#countDeals").append(len);
            gridOptionsDeals.api.setRowData(data.body);
            var noRecordsMessage = document.getElementById('noRecordsMessage');
            var myGridDeals = document.getElementById('myGridDeals');

            if (data.body.length > 0) {
                // Data exists, hide the "No records found" message and show the Ag-Grid
                noRecordsMessage.style.display = 'none';
                myGridDeals.style.display = 'block';
                $("#addDeals").show();
            } else {
                // No data, show the "No records found" message and hide the Ag-Grid
                noRecordsMessage.style.display = 'block';
                myGridDeals.style.display = 'none';
                $("#addDeals").hide();
            }
        });
    }
    
  function assignDeal() {
    var accId = $("#accountId").text();
  
    var accName = $("#accountName").text();
   
    localStorage.setItem('accId', accId);
    localStorage.setItem('accName', accName);
   
}

function openCAccountDeals(dealId){
	
	localStorage.setItem('dealId', dealId);
}

	function getCount(accountId){
	
		
       
			var countOpen = parseInt(localStorage.getItem("countOpen")) || 0;
			var countOpenM = parseInt(localStorage.getItem("countOpenM")) || 0;
			var countOpenC = parseInt(localStorage.getItem("countOpenC")) || 0;
			
			var countClose = parseInt(localStorage.getItem("countClose")) || 0;
			var countCloseM = parseInt(localStorage.getItem("countCloseM")) || 0;
			var countCloseC = parseInt(localStorage.getItem("countCloseC")) || 0;
		
		
			var total = countOpen + countOpenM + countOpenC;
			var total1 = countClose + countCloseM + countCloseC;
			
			$("#countActivity").text(total);
			$("#countCloseActivity").text(total1);
		
		
	}

	
	
	