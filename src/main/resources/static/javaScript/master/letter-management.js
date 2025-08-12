function utf8ToBase64(str) {
    const utf8Bytes = new TextEncoder().encode(str);
    const base64String = btoa(String.fromCharCode(...utf8Bytes));
    return base64String;
}
function base64ToUtf8(base64Str) {
    const binaryString = atob(base64Str);
    const utf8Bytes = Uint8Array.from(binaryString, char => char.charCodeAt(0));
    const decodedString = new TextDecoder().decode(utf8Bytes);
    return decodedString;
}
function viewData(selectId=null) {
	   $(".loader").show();
	   agGrid.simpleHttpRequest({
	      url: "letter-management-view-employee?type="+type
	   }).then(function (data) {
		   $(".loader").hide();
		   if(data.body[0] != null){
	      	 var allData = JSON.parse(data.body);
	         var len = allData.length;
	         $('#totalReq').find('span').html(len);
	         gridOptions.api.setRowData(allData);
	         if(allData == null) {
	         newLetter();
	         }
		if (selectId !== null) {  
			    let selectedRow = null;
			    gridOptions.api.forEachNode((rowNode) => {  
			        if (rowNode.data.noticeId === selectId) {  
			            selectedRow = rowNode;  
			        }  
			    });  
			    if (selectedRow) {  
			        selectedRow.setSelected(true);  
			        gridOptions.api.ensureIndexVisible(selectedRow.rowIndex);  
			    }  
			}else{
				var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
					firstRowNode.setSelected(true);
				}	
			}
	      } else {
	    	  $(".loader").hide();
	         $('#totalReq').find('span').html("0");
	         gridOptions.api.setRowData([]);
	         //newLetter();
	      }
	      $(".loader").hide();
	   });
	   $('#editNotice').attr("disabled", true);
	   $('#sendMail').attr("disabled", true);
}

function onQuickFilterLetter() {
	gridOptions.api
		.setQuickFilter(document.getElementById('quickFilter').value);
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
		if (firstRowNode) {
			firstRowNode.setSelected(true);
		}
}
function resetLetter() {
	$("#quickFilter").val('');
	onQuickFilterLetter();
}

var timeout;
function getEmployeeList() {		
	$("#employeeName").val('');
    $("#reportingManager").val('');
    $("#empMobile").val('');
    $("#empDept").val('');
    $("#empDesgn").val('');
    $("#employeeId").val('');
    $('.loader-modal-autosearch').show();
	        
    clearTimeout(timeout); 
	timeout = setTimeout(function() {
		var search = $("#empNameAuto").val();
		if (search) {
			$.ajax({
				type: "POST",
				url: "letter-management-autoserach-employee",
			    dataType: 'json',
				contentType: 'application/json',
				data: search,
				success: function(response) {
					if (response.message == "success") {
						
                $("#suggesstion-box_").empty();  
		   		   		$('.loader-modal-autosearch').hide();
		            	var jsonData = JSON.parse(response.body);
		    			var allData = jsonData;
		    			if (allData != null && allData.length > 0) {
			    			var len = allData.length;
		                    $("#empNameAuto").css("background", "#FFF");
		                   // var content = '<ul id="autocomplete-list1" class="sugg-cls-ul autocompletedata">';
		                     var content = '<ul id="autocomplete-list1" style="margin-left:-40px; font-weight:100; font-size:14px; color:#ccc;">';
		                    for (var i = 0; i < len; i++) {
		                        content += '<li onClick="selectAutocompleteValueItem1(\'' + window.btoa(allData[i].empId) + '\',\'' +
		                            window.btoa(allData[i].name) + '\',\'' + window.btoa(allData[i].empContact) + '\',\'' +
		                            window.btoa(allData[i].designation) + '\',\'' + window.btoa(allData[i].department) + '\',\'' +
		                            window.btoa(allData[i].manager) + '\',\'' + window.btoa(allData[i].email) + '\')">' +
		                            allData[i].empId + " - " + allData[i].name + '</li>';
		                    }
		                    content += '</ul>';
		                    $("#suggesstion-box_").show();
		                    $("#suggesstion-box_").html(content);
		                } else {
		                	 $('.loader-modal-autosearch').hide();
		                    $("#empNameAuto").css("background", "#FFF");
		                    var content = '<ul id="autocomplete-list1">';
		                    content += '<li style="font-weight:100; font-size:14px; color:#808080; background-color: #0909e4;" onClick="selectAutocompleteValueItem()">' +
		                        "No Data Found" + '</li>';
		                    content += '</ul>';
		                    $("#suggesstion-box_").show();
		                    $("#suggesstion-box_").html(content);
		                	$("#employeeName").val('');
		    		        $("#reportingManager").val('');
		    		        $("#empMobile").val('');
		    		        $("#empDept").val('');
		    		        $("#empDesgn").val('');
		                }
		            }else{
		            	 $('.loader-modal-autosearch').hide();
		            	 $("#suggesstion-box_").hide();
	            		$("#employeeName").val('');
	    		        $("#reportingManager").val('');
	    		        $("#empMobile").val('');
	    		        $("#empDept").val('');
	    		        $("#empDesgn").val('');
	    		        $("#employeeId").val('');
		            }
		        },
		        error: function(data) {
		            console.log(data);
		            $('.loader-modal-autosearch').hide();
		        }
		    });
		}else{
			$('.loader-modal-autosearch').hide();
			
        $("#suggesstion-box_").hide();
		}
	}, 200);
}
function selectAutocompleteValueItem1(empId, empName, contact, designation, department,manager, email) {
	if (empName) {
		$("#empNameAuto").val(window.atob(empName));
        $("#employeeId").val(window.atob(empId));
        $("#employeeName").val(window.atob(empName));
        if (contact !== "null") {
            $("#empMobile").val(window.atob(contact));
        } else {
            $("#empMobile").val();
        }
        if (manager !== "null") {
            $("#reportingManager").val(window.atob(manager) || '');
        } else {
            $("#reportingManager").val(); 
        } 
        if (department !== "null") {	        
            $("#empDept").val(window.atob(department) || '');
        } else {
        	 $("#empDept").val();
        }
        if (designation !== "null") {
            $("#empDesgn").val(window.atob(designation));
        } else {
            $("#empDesgn").val(''); 
        }
        $("#suggesstion-box_").hide();
    } else {
    	$("#employeeName").val('');
        $("#reportingManager").val('');
        $("#empMobile").val('');
        $("#empDept").val('');
        $("#empDesgn").val('');
       // $("#empDesgn").val('');
        $("#suggesstion-box_").hide();
    }
}
function getNoticeContent() {
   var id = $("#noticeType").val();
   if (id) {
      $.ajax({
         type: "GET",
         url: "letter-management-get-content?id=" + id,
         success: function (response) {
            if (response.message == "success") {
               var data = JSON.parse(response.body);
               var content = data.content;
               CKEDITOR.instances['noticeDescription'].setData(content.cont);
               $("#noticeSubject").val(content.typeName);
               $("#noticeReason").val(content.typeName);
            }
         }
      });
   }
}
function cancelLetter(){
	$(".formValidation").remove();
	$("#cancelLetter, #saveLetter").hide();
	 $("#downloadLetter,#next1,#newLetter").show();
	 if(type!='self-service') {
	 	$("#editLetter, #newLetter,#sendBtn, #deleteLetter").show();
	 }
	
	$("#empNameAuto, #noticeType, #noticeSubject, #noticeReason").prop('disabled', true);
	CKEDITOR.instances.noticeDescription?.on('instanceReady', function () {
	    this.setReadOnly(true); 
	});
}
function editLetter(){
	$(".formValidation").remove();
	$("#cancelLetter, #saveLetter").show();
	$("#editLetter, #newLetter, #deleteLetter, #downloadLetter").hide();
	$("#noticeType, #noticeSubject, #noticeReason").prop('disabled', false);
	$("#empNameAuto").prop('disabled', true);
	CKEDITOR.instances.noticeDescription?.setReadOnly(false); // Disable the editor once ready
}
function newLetter(){
	$("#newLetter").hide();
	$("#cancelLetter, #saveLetter").show();
	$("#empNameAuto,#noticeType").prop('disabled', false);
	$(".formValidation").remove();
	$('.loader-modal-autosearch').hide();
	$("#employeeName").val('');
    $("#reportingManager").val('');
	$("#letterId1").val('');
    $("#empMobile").val('');
    $("#empDept").val('');
    $("#empDesgn").val('');
    $("#noticeSubject").val('');
    $("#noticeReason").val('');
    $("#empNameAuto").val('');
    $("#employeeId").val('');
    $("#noticeType").val('');
    CKEDITOR.instances.noticeDescription.setData("");
    $("#empAutoSearch").show();
	$("#noticeMaster").show();
	$("#headerDiv").hide();
	$("#noticeID").val('');
	$("#noticeId").val('');
	var currDate = getCurrentDate();
    $("#currDate").html(currDate);
	$("#sendBtn, #next1").hide();
	if (gridOptions.api) {
			gridOptions.api.deselectAll();
		}
}
function getCurrentDate() {
   let today = new Date();
   let year = today.getFullYear();
   let month = String(today.getMonth() + 1).padStart(2, '0'); 
   let day = String(today.getDate()).padStart(2, '0');
   return day + '-' + month + '-' + year;
}
var documentListNotice;
function editLetterData(noticeId) {
	cancelLetter();
   $(".formValidation").remove();
   $("#noticeMaster").show();
   $("#headerDiv").hide();
   $('#empSection').show();
   $("#empAutoSearch").hide();
   $("#noticeID").html(noticeId);
   $("#noticeId").val(noticeId);
   $(".loader").show();
   $("#letterId1").val(noticeId);
   $("#letterId2").html(noticeId);
   $.ajax({
      type: "GET",
      url: "letter-management-edit?id=" + noticeId,
      success: function (response) {
         if (response.code == "success") {
            var allData = JSON.parse(response.body)
            selectedData = allData.editData;
            
            $("#employeeId").val(selectedData.empId);
            $("#noticeID").val(selectedData.noticeId);
            $("#employeeName").val(selectedData.empName);
            $("#empNameAuto").val(selectedData.empName);
            $("#addPageEmpName").val(selectedData.empName);
            $("#empDept").val(selectedData.department);
            $("#empDesgn").val(selectedData.designation);
            $("#empMobile").val(selectedData.mobile);
            $("#reportingManager").val(selectedData.manager);
            CKEDITOR.instances['noticeDescription'].setData(selectedData.noticeDesc);
            $("#noticeSubject").val(selectedData.noticeSubject);
            $("#noticeReason").val(selectedData.reason);
            $("#noticeType").val(selectedData.noticeType);
            $("#absentFrom").val(selectedData.absentFrom);
            var currDate = getCurrentDate();
            $("#currDate").html(currDate);


	   		$("#empNames").val(selectedData.empName);
			$("#empMails").val(selectedData.mail_to);
	   		$("#empCc").val(selectedData.mail_Cc);
			$("#emailBody").val(selectedData.mailBody || 'Please Find The Attachment.');
         }
         $(".loader").hide();
      }
   });
}

function cancelMail(){
	$(".formValidation").remove();
	$("#cancelMail, #addMail").hide();
	$("#sendMail").show();
	$("#empMails, #empCc, #emailBody").prop('disabled', true);
}
function sendMail(){
	$(".formValidation").remove();
	if(type!='self-service') {
		$("#cancelMail, #addMail").show();
	}
	
	$("#sendMail").hide();
	$("#empMails, #empCc, #emailBody").prop('disabled', false);
}
function descriptionPreview() {

   var organization = $("#sessionOrganization").val();
   var orgDivision = $("#sessionOrgDivision").val();
   var userId = $("#sessionId").val();
   var logo = $("#sessionLogo").val();
  
   window.open("/master/letter-management-pdf-download?noticeId=" +
   window.btoa(globId) + "&organization=" + window.btoa(organization) + "&orgDivision=" +
	      window.btoa(orgDivision) + "&userId=" +  window.btoa(userId) + "&logo=" + window.btoa(logo), '_blank');
}
function saveLetter() {
   var obj = {};
   var allValid = true;
   var noticeDescription = CKEDITOR.instances.noticeDescription.getData();
   obj.noticeId = $("#noticeID").val();
   obj.currDate = $("#currDate").html();
   obj.employeeId = $("#employeeId").val();
   obj.employeeName = $("#employeeName").val();
   obj.empMobile = $("#empMobile").val();
   obj.noticeType = $("#noticeType").val();
   obj.noticeReason = $('#noticeReason').val();
   obj.noticeSubject = $("#noticeSubject").val();
   obj.noticeDescription = noticeDescription;
   obj.absentFromDate = $("#absentFrom").val();	   
   
   	if (obj.employeeName == "" || obj.employeeName == null) {
		toastr.error("Employee name required");
		return;
	}
	
	if (obj.noticeType == "" || obj.noticeType == null) {
		toastr.error("Letter type required");
		return;
	}
	if (obj.noticeSubject == "" || obj.noticeSubject == null) {
		toastr.error("Subject required");
		return;
	}
	 console.log('obj>>>>>>>>>>',obj)
   
   if (allValid) {
	  saveLetterData(obj,obj.noticeId);
   }
}
function saveLetterData(data,nid) {
$('.loader-modal').show();
   $.ajax({
      type: "POST",
      url: "letter-management-master-save",
      dataType: "json",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: function (response) {
         if (response.code == "success") {
        	 console.log('response>>',response)
			 toastr.success(response.message);
	        $("#noticeMaster").hide();
            $("#headerDiv").show();
            $('.loader-modal').hide();
            $('#addPageEmpName').hide();
            	if(nid==""){
					viewData();
				}else{
					viewData(nid);
				}
			cancelLetter();
         } 
   
      },
      error: function (data) {
         console.log(data)
      }
   })
}
function addMail() {
	   var selectedNodes = gridOptions.api.getSelectedNodes();
	   var selectedData = selectedNodes.map(node => node.data);
	   var obj = {};
	   var validation = true;
	   var validationMailTo = true;
	   var validationMailCc = true;
	   var emailTo = $("#empMails").val();
	   var emailCc = $("#empCc").val();
	   var emailBody = $("#emailBody").val();
	   var organization = $("#sessionOrganization").val();
	   var orgDivision = $("#sessionOrgDivision").val();
	   var logo = $("#sessionLogo").val();
	   var userId = $("#sessionId").val();
	   var baseUrl =$('#baseURL').val();
	   var url = baseUrl+"master/letter-management-pdf-download?noticeId=" +  window.btoa(globId) + "&organization=" +
			      window.btoa(organization) + "&orgDivision=" + window.btoa(orgDivision) + "&userId=" +  window.btoa(userId) + "&logo=" + window.btoa(logo);
	   
	   console.log("url",url);
	      
	      if (emailTo == "" || emailTo == null) {
		toastr.error("Employee email required");
		return;
	}
 	      
	  if (emailCc == "" || emailCc == null) {
		toastr.error("Email CC required");
		return;
	}
	   
	   if(emailTo){		   
		   var validationMailTo = validateEmailsTO(emailTo);
	   }	
	   
	      
	   if(emailCc){
		   var validationMailCc = validateEmailsCC(emailCc);
	   }
	   if (validation && validationMailTo && validationMailCc) {
	      $.ajax({
	         type: "GET",
	         url: "letter-management-send-mail?noticeId=" + globId + "&emailTo=" + emailTo + "&emailCc=" + emailCc + "&emailBody=" + emailBody + "&url=" + window.btoa(url),
	         async: true,
	         success: function (response) {
			 console.log("response===",response)
	            if (response.code == "success") {
	                $('#emailModal').modal('hide');
					toastr.success("Email send successfully");
                    $('#editLetter, #sendMail, #deleteLetter').attr("disabled", true);
                    $('.loader-modal').hide();
                    $("modal-body").removeClass("overlay");
					cancelMail();
                    viewData();
	            }else{
					toastr.error('Something went wrong..Try again after sometime.');
                     $('.loader-modal').show();
                     $("modal-body").removeClass("overlay");
                     $('#editLetter, #sendMail, #deleteLetter').attr("disabled", true);
                     $('.loader-modal').hide();
                     $("modal-body").removeClass("overlay");
                     viewData();
					cancelMail();
	            }

	         },
	         error: function (data) {}
	      });
	   }    }
 function validateEmailsCC(data) {
	    var emails = data.split(',');
	    var validEmails = [];
	    var invalidEmails = [];
	    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	    for (var i = 0; i < emails.length; i++) {
	        var email = emails[i].trim();
	        if (emailPattern.test(email)) {
	            validEmails.push(email);
	        } else if (email !== "") {
	            invalidEmails.push(email);
	        }
	    }

	    if (invalidEmails.length > 0) {
	       $("#empCc").val('');
		   toastr.error("Invalid email(s): " + invalidEmails.join(", "));
	       /*$("#messageParagraph").text("Invalid email(s): " + invalidEmails.join(", "));
			$("#msgOkModal").removeClass("btn btn-primary edit-btn");
			$("#msgOkModal").addClass("btn btn-primary edit-btn");*/
            $('.loader-modal').show();
           return false;
	    }else{
	    	return true;
	    }
	}
 
 function validateEmailsTO(data) {
	    var emails = data.split(',');
	    var validEmails = [];
	    var invalidEmails = [];
	    var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	    for (var i = 0; i < emails.length; i++) {
	        var email = emails[i].trim();
	        if (emailPattern.test(email)) {
	            validEmails.push(email);
	        } else if (email !== "") {
	            invalidEmails.push(email);
	        }
	    }

	    if (invalidEmails.length > 0) {
			toastr.error("Invalid email(s): " + invalidEmails.join(", "));
	       /*$("#messageParagraph").text("Invalid email(s): " + invalidEmails.join(", "));
	       $("#empMails").val('');
			$("#msgOkModal").removeClass("btn btn-primary edit-btn");
			$("#msgOkModal").addClass("btn btn-primary edit-btn");*/
            $('.loader-modal').show();
           return false;
	    }else{
	    	return true;
	    }
	}
	
	function validateAndProcessEmails(event) {
	    var input = event.target;
	    var value = input.value;
	    
	    var sanitizedValue = value.replace(/\s/g, '');
	    
    input.value = sanitizedValue;
}
function travelPrev(tab){
	$("#infoBtn").addClass('active');
	$("#sendBtn").removeClass('active');
		
	$("#letterinfoTab").addClass('active');
	$("#sendTab").removeClass('active');
}
function travelNext(tab){
	$("#sendBtn").addClass('active');
	$("#infoBtn").removeClass('active');
		
	$("#sendTab").addClass('active');
	$("#letterinfoTab").removeClass('active');
}

function cancelBar() {
/*	var id = document.getElementById("closeKey");
	id.style.display = "block";
*/
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
function deleteLetter(){
		 var selectedNodes = gridOptions.api.getSelectedNodes();
		 var selectedData = selectedNodes.map(node => node.data);
		 var noticeId = selectedData[0].noticeId;
		 	Swal.fire({
		title: 'Are you sure?',
		text: 'Do you want to delete this?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it',
		confirmButtonColor: 'var(--mainColor)',
	}).then((result) => {
		if (result?.value) {
		 $.ajax({
		      type: "GET",
		      url: "letter-management-delete-letter?id=" + noticeId,
		      success: function (response) {
		         if (response.code == "success") {
		        	 $(".loader").hide();
					
		        	 viewData();
		         }else{
		        	 $(".loader").hide();
				
		        	 viewData();
		         }
		        
		      }
		   });
		   	}
	});
	}
function downloadLetter(){
		var noticeId=$("#letterId1").val();
	   var organization = $("#sessionOrganization").val();
	   var orgDivision = $("#sessionOrgDivision").val();
	   var logo = $("#sessionLogo").val();
	   var userId = $("#sessionId").val();
	   
	   window.open("/master/manage-letter-pdf-download?noticeId=" +
			      window.btoa(noticeId) + "&organization=" +
			      window.btoa(organization) + "&orgDivision=" +
			      window.btoa(orgDivision) + "&userId=" +  window.btoa(userId) + "&logo=" + window.btoa(logo), '_blank');
	}
	
function descriptionDownload(noticeId) {
	   var organization = $("#sessionOrganization").val();
	   var orgDivision = $("#sessionOrgDivision").val();
	   var logo = $("#sessionLogo").val();
	   var userId = $("#sessionId").val();
	   
	   window.open("/master/manage-letter-pdf-download?noticeId=" +
			      window.btoa(noticeId) + "&organization=" +
			      window.btoa(organization) + "&orgDivision=" +
			      window.btoa(orgDivision) + "&userId=" +  window.btoa(userId) + "&logo=" + window.btoa(logo), '_blank');
	}
	
		function viewEmployeeAttachment(id){
		$('.loader').show();
		$("#noticeNoAttachment").html(id);
		
		agGrid.simpleHttpRequest(
					{ 
						url : 'manage-letter-attachment-view?id='+ id
					}).then(function(data) {
						var imagesdiv="";
						var jsonData = JSON.parse(data.body[0]);
						var allData=jsonData.attachment;
						console.log('allData>>',allData)
						$('.loader').hide();
						if(allData!= null && allData !=''){
							allData.forEach(function(rowNode){
							if (rowNode.fileName !== '' && rowNode.fileName !== null) {
								
								var iurl=rowNode.docurl;
						
								imagesdiv += `<div class="col-lg-2 col-md-6 mb-4 mb-lg-0">${rowNode.docName}</div><div class="col-lg-2 col-md-6 mb-4 mb-lg-0">
					                <a href="${iurl}" ${iurl.endsWith('.pdf') ? 'target="_blank"' : 'data-lightbox="images"'}>
					                    <div class="bg-image hover-overlay ripple shadow-1-strong rounded" data-ripple-color="light">
					                        ${iurl.endsWith('.pdf') ? '<i class="far fa-file-pdf fa-5x" style="font-size: 30px"></i>' : `<img src="${iurl}" class="w-100"/>`}
					                    </div>
					                </a>
					            </div>`;
							}
						
							  });
						}else{
							imagesdiv =imagesdiv+ `<div class="col-md-12 mt-4">No documents found</div>`;
						}
						$("#replyDocument").html(imagesdiv);
						
	});
			$('#employeeAttachment').modal('show');
	}