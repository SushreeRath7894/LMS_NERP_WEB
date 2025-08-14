/** Loader Js 
 * 
 * @author Pankaj
 */
/*
$(document).ready(function() {
	$('#customLoad').show();

	setTimeout(function() {
		$('#customLoad').hide();
		$('.loader-backdrop').hide();
	}, 3000);
});*/

/* 
* Decrypt function 
**/
function decrypt(ciphertext, key) {
	const bytes = CryptoJS.AES.decrypt(ciphertext, key);
	return bytes.toString(CryptoJS.enc.Utf8);
}

/**
 * Set Selected Task
 *  */
function getSelectedTaskId(taskid) {
	let id = decrypt(taskid, "notification")
	localStorage.setItem("taskId", id);
}

/**
 * Redirect to Activities 
 */

function getUrl(module, fun, activity) {

	$.ajax({
		type: "GET",
		url: "/index-get-breadcrumb-data?moduleId=" + module + "&fun="
			+ fun + "&activity=" + activity,
		async: false,
		success: function(response) {
			if (response.message == "Unsuccess") {
				console.log(JSON.stringify(response));

				modOnclick(fun);
				callActivity(activity, response.body.actURL);

			}
		},
		error: function(data) {
		}
	});
}

function doHideShow(element, option) {

	if (option) {
		element.forEach(function(id) {
			$("" + id).show();
		});
	} else {
		element.forEach(function(id) {
			$("" + id).hide();
		});
	}
}

/**
 * Author: Pankaj 
 * 
 * Get Next Action Data
 * 
 *  */


// JavaScript function to fetch and display upcoming deals
function getActionDeals(contact) {
	$.ajax({
		type: "GET",
		url: "view-crm-contact-upcoming-deals-actions?id=" + contact,
	}).then(function(data) {
		if (data.message === "Success") {
			$("#upcomingDealsList").empty(); // Clear previous content
			if (data.body.length === 0) {
				// Handle case when there are no upcoming deals
				$("#upcomingDealsList").append("<li>No upcoming deals found</li>");
			} else {
				// Iterate through each deal and append to the list
				data.body.forEach(function(deal) {
					var dealItem =
						'<li class="dealItem">' +
						'<div class="dealInfo">' +
						'<div class="dealName">' + deal.dealName + '</div>' +
						'<div class="dealAmount">$' + deal.dealAmount + '</div>' +
						'</div>' +
						'<div class="dealStatus">' +
						'<div class="stageName">' + deal.stageName + '</div>' +
						'<div class="dealClosingDate">' + deal.dealClosingDate + '</div>' +
						'</div>' +
						'</li>';
					$("#upcomingDealsList").append(dealItem);

				});
			}
		}
	});
}





function getAction(leadId) {
	var customColors = [
		'var(--redColor)',
		'var(--greenColor)',
		'var(--yellowColor)'
	];
	var colorIndex = 0;

	var loadMoreCount = 2; // Number of items to load initially
	var loadedItems = 0;

	$.ajax({
		type: "GET",
		url: "view-crm-lead-upcoming-task-actions?id=" + leadId,
		success: function(response) {
			var taskActivitys = $("#taskActivitys");

			// Clear the existing content
			taskActivitys.empty();

			if (response.code == "Success" && response.body.length > 0) {
				for (var i = 0; i < response.body.length; i++) {
					var taskStatus = response.body[i].taskStatus;
					taskId = response.body[i].taskId;

					if (taskStatus != "Completed") {
						var dateStr = response.body[i].dueDate;
						var dateComponents = dateStr.split('-');
						if (dateComponents.length === 3) {
							var day = dateComponents[0];
							var month = dateComponents[1];
							var year = dateComponents[2];

							var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
							var monthAbbreviation = months[parseInt(month) - 1];

							var formattedDate = monthAbbreviation + ' ' + day;

							var buttonColor = customColors[colorIndex];
							colorIndex = (colorIndex + 1) % customColors.length;

							var mailRow = '<li style="margin-bottom: 20px;">' +
								'<div class="row-container">' +
								'<button class="' + (buttonColor === 'var(--redColor)' ? 'red-button' : (buttonColor === 'var(--greenColor)' ? 'green-button' : 'yellow-button')) + '" style="background-color:' + buttonColor + '; font-weight: bold;">' + formattedDate + '</button>' +
								'<div class="toptxt1" style="margin-left: 48px; font-weight: 600">' + response.body[i].taskSubject + '</div>' +
								'</div>' +
								'</li>';

							if (loadedItems < loadMoreCount) {
								taskActivitys.append(mailRow);
								$('#seeMoreButton').hide();
							} else {
								// Add the item to the scrollable section
								if (loadedItems === loadMoreCount) {
									taskActivitys.append('<div id="scrollableSection" class="scrollable-section">');
								}
								$('#scrollableSection').append(mailRow);
								$('#seeMoreButton').show();
							}

							loadedItems++;
						}
					}
				}

				if (loadedItems > loadMoreCount) {
					$('#seeMoreButton').show();
				} else {
					// No need for "See More" button, hide it
					$('#seeMoreButton').hide();
				}
			} else {
				// No data, show a message
				taskActivitys.append('<p>No Record Found.</p>');
				$('#seeMoreButton').hide();
			}
		},
		error: function() {
			// Handle error, show a message
			var taskActivitys = $("#taskActivitys");
			taskActivitys.append('<p>Error loading data.</p>');
			$('#seeMoreButton').hide();
		}
	});
}

/*
 Mail Autosearch By pankaj kumar
*/

$(document).ready(function() {

	var gridDiv = document.querySelector('#mySKUGridProduct');
	new agGrid.Grid(gridDiv, skuOptionsProduct);

	// Function to toggle the active-nav class
	function toggleSideNavbar() {
		$('.side-navbar').toggleClass('active-nav');
	}

	// Event listener for mobile menu icon click
	$('#mobile-menu-icon').click(function() {
		toggleSideNavbar();
	});

	// Event listener for window resize to hide the side navbar on mobile devices
	$(window).resize(function() {
		if ($(window).width() <= 768) {
			$('.side-navbar').removeClass('active-nav');
		}
	});

	let isValidEmail = false;
	$("#bccMail").on('keydown', function(event) {
		// Prevent default action for space and enter keys
		if (event.keyCode === 32 || event.keyCode === 13) {
			event.preventDefault();
		} else {
			const email = $(this).val().trim();
			isValidEmail = isValidEmailBcc(email);
		}
	});

	
	$("#bccMail").on('keyup', function(event) {
		const email = $(this).val().trim();
		if (event.keyCode === 32 || event.keyCode === 13) {
			if (isValidEmail) {
				addEmailToBccContainer(email);
				bccMailListData.add(email);
				$(this).val('');
				isValidEmail = false;
			}
		} else {
			isValidEmail = isValidEmailBcc(email);
		}
	});
	
	$("#ccMail").on('keydown', function(event) {
		if (event.keyCode === 32 || event.keyCode === 13) {
			event.preventDefault();
		} else {
			const email = $(this).val().trim();
			isValidEmail = isValidEmailCc(email);
		}
	});

	
	$("#ccMail").on('keyup', function(event) {
		const email = $(this).val().trim();
		if (event.keyCode === 32 || event.keyCode === 13) {
			if (isValidEmail) {
				addEmailToContainer(email);
				ccMailListData.add(email);
				$(this).val('');
				isValidEmail = false;
			}
		} else {
			isValidEmail = isValidEmailCc(email);
		}
	});

});



// Function to validate email address

function isValidEmailCc(email) {
	const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (email.trim() === '') {
		$("#errorMsg").html('<div class="sucessValidation"></div>').hide();
		return false;
	} else if (re.test(email.trim())) {
		if (isEmailComplete(email)) {
			$("#errorMsg").html('<div class="sucessValidation"></div>').show();
			return true;
		}
	} else {
		$("#errorMsg").html('<div class="formValidation"></div>').show();
		return false;
	}
}

function isValidEmailBcc(email) {
	const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	if (email.trim() === '') {
		$("#suggesstion-bccmailBox_").html('<div class="sucessValidation"></div>').hide();
		return false;
	} else if (re.test(email.trim())) {
		if (isEmailComplete(email)) {
			$("#suggesstion-bccmailBox_").html('<div class="sucessValidation"></div>').show();
			return true;
		}
	} else {
		$("#suggesstion-bccmailBox_").html('<div class="formValidation"></div>').show();
		return false;
	}
}

function removeErrorMsg() {
    var parentDivCc = document.getElementById("errorMsg");
    var parentDivBcc = document.getElementById("suggesstion-bccmailBox_");
    var childDivErrorCc = document.querySelector("#errorMsg .formValidation");
    var childDivSucessCc = document.querySelector("#errorMsg .sucessValidation");
    var childDivErrorBcc = document.querySelector("#suggesstion-bccmailBox_ .formValidation");
    var childDivSucessBcc = document.querySelector("#suggesstion-bccmailBox_ .sucessValidation");

    // Remove error message in CC
    if (childDivErrorCc) {
        parentDivCc.removeChild(childDivErrorCc);
    }

    // Remove success message in CC
    if (childDivSucessCc) {
        parentDivCc.removeChild(childDivSucessCc);
    }

    // Remove error message in BCC
    if (childDivErrorBcc) {
        parentDivBcc.removeChild(childDivErrorBcc);
    }

    // Remove success message in BCC
    if (childDivSucessBcc) {
        parentDivBcc.removeChild(childDivSucessBcc);
    }
    $("#suggesstion-mailBox_").html("");
}


function isEmailComplete(email) {
	return email.indexOf("@") !== -1 && email.indexOf("@") !== 0 && email.indexOf("@") !== email.length - 1;
}



let ccMailListData = new Set();
let bccMailListData = new Set();
function getMailsAutosearch(searchVal) {
	$.ajax({
		type: "POST",
		url: "view-crm-lead-mail-autosearch",
		dataType: 'json',
		contentType: 'application/json',
		data: searchVal,
		success: function(response) {
			if (response.code == "Success") {
				handleMailSearchResults(response.body);
			}
		},
		error: function(data) {
		}
	});
}

function handleMailSearchResults(results) {
	if (results.length != 0) {
		$("#suggesstion-mailBox_").show();
		$("#suggesstion-mailBox_").html(generateAutocompleteMailList(results, 'selectAutocompleteMailValue'));
	} else {
		$("#suggesstion-mailBox_").show();
	}
}


function generateAutocompleteMailList(items, clickFunction) {
	var content = '<ul id="autocomplete-list1" style="margin-left: -27px;font-size: 14px!important; font-weight: 500;">';
	for (var i = 0; i < items.length; i++) {
		content += '<div class="selected-item badge rounded-pill bg-info text-dark mail-search"><li class="autocompletedata cp" onClick="' + clickFunction + '(\'' +
			items[i].name + '\')">' +
			items[i].name + '</li><span class="remove-item badge rounded-pill bg-info text-dark"></span></div>';
	}
	content += '</ul>';
	return content;
}



function selectAutocompleteMailValue(name) {
	

	if ($("#selected-mail-container").find('.selected-item:contains("' + name + '")').length > 0) {
		// Item is already selected, show a message 
		$("#messageParagraph").text("Mail is already selected!!");
		$("#msgOkModal").removeClass("btn3");
		$("#msgOkModal").addClass("btn1");
		$("#msgModal").modal('show');
		return false;
	}

	//ccMailListData.add(name);
	
	 if (name && name.trim() !== "") {
        // Clear the Set if it's empty
        if (ccMailListData.size === 0) {
            ccMailListData.clear();
        }
        // Add the email to the Set
        ccMailListData.add(name.trim());
    }

	addEmailToContainer(name);
}

function selectAutocompleteMailValueForDraft(name) {

	//ccMailListData.add(name);
	 if (name && name.trim() !== "") {
        // Clear the Set if it's empty
        if (ccMailListData.size === 0) {
            ccMailListData.clear();
        }
        // Add the email to the Set
        ccMailListData.add(name.trim());
    }

	addEmailToContainer(name);
}

function addEmailToContainer(name) {
	removeErrorMsg();

	var manuallyTypedEmail = name;

	if (manuallyTypedEmail && manuallyTypedEmail.trim() !== '' && !emailExistsInContainer(manuallyTypedEmail)) {
		$("#selected-mail-container").append('<div class="selected-item badge rounded-pill bg-info text-dark">' + manuallyTypedEmail + '<span class="remove-item badge rounded-pill bg-info text-dark" onclick="removeSelectedCcMail(this)"><i class="fas fa-times fa-lg"></i></span></div>');
	}

	$("#ccMail").val("");
	$("#suggesstion-mailBox_").hide();
}

// Function to add email to BCC container
function addEmailToBccContainer(email) {
	var manuallyTypedEmail = email;
	if (manuallyTypedEmail && manuallyTypedEmail.trim() !== '' && !bccemailExistsInContainer(manuallyTypedEmail)) {
		$("#bcc-mail-container").append('<div class="selected-item badge rounded-pill bg-info text-dark">' + email + '<span class="remove-item badge rounded-pill bg-info text-dark" onclick="removeBccEmail(this)"><i class="fas fa-times fa-lg"></i></span></div>');
	}
}

function emailExistsInContainer(email) {
	return $("#selected-mail-container").find('.selected-item:contains("' + email + '")').length > 0;
}

function bccemailExistsInContainer(email) {
	return $("#bcc-mail-container").find('.selected-item:contains("' + email + '")').length > 0;
}

function removeSelectedCcMail(element) {
	var parentDiv = element.parentNode;
	var email = parentDiv.textContent.trim();
	ccMailListData.delete(email);
	parentDiv.parentNode.removeChild(parentDiv);
}

// Function to remove BCC email
function removeBccEmail(element) {
	var parentDiv = element.parentNode;
	var email = parentDiv.textContent.trim();
	bccMailListData.delete(email);
	parentDiv.parentNode.removeChild(parentDiv);
}

function verifyDuplicate(url, type, data, callback) {
	$.ajax({
		type: type,
		url: url,
		async: false,
		data: data,
		success: function(response) {
			console.log(response);
			callback(response);
		}
	});
}

function filterNote() {
	var id = $("#filterNote").val();
	if (id == "title") {
		$("#dateDiv").hide();
		$("#titleDiv").show();
		$("#titleDiv").css("visibility", "visible");
		$(".title-copy").show();
		$(".title-copy").css("visibility", "hidden");


	} else if (id == "date") {
		$("#dateDiv").show();
		$("#dateDiv").css("visibility", "visible");
		$("#titleDiv").css("visibility", "hidden");
		$(".title-copy").hide();
		$(".title-copy").css("visibility", "hidden");
	}

	else if (id == "both") {
		$("#dateDiv").show();
		$("#titleDiv").css("visibility", "visible");
		$("#dateDiv").css("visibility", "visible");
		$(".title-copy").hide();



	} else {
		$("#dateDiv").css("visibility", "hidden");
		$("#titleDiv").css("visibility", "hidden");

	}
}


/**
* Get Current Location
*/
let latitude = '';
let longitude = '';

function showPosition(position) {

	locationPermission = true;
	LocationMessage = "";
	$("#latitude").val("");
	$("#longitude").val("");
	latitude = position.coords.latitude;
	longitude = position.coords.longitude;
}

var statuss = true;
let idd = '';
let date = '';
let title = '';

function getNoteAll(id,cuPages,filterDate,filterTitle){
	

	idd = id ;
	date = filterDate;
	title = filterTitle;
	
	var pages;
	var pageno = cuPages;
	

	$("#noteListWithDoc").empty();
	$("#countNote").empty().append(0);
	$.ajax({
		type : "GET",
		url : "view-crm-leads-view-detail-view-note?id=" + id + "&pageno=" + pageno +
		"&filterDate="+filterDate + "&filterTitle="+filterTitle,
		success : function(response) {

			if (response.code == "Success") {
				
				 
				var resp=JSON.parse(response.body[0]);
			     var len = resp.noteList.length;
			     
				$("#noteListWithDoc").empty();
				 $("#countNote").empty().append(resp.noteList[0].totalOrderById);
				console.log(resp.noteList,'noteeeeeejsonnnnndata')
				var xyz = '';
				
				 
 				 for (var i = 0; i < resp.noteList.length; i++) {
					  
					let reviewData = '';
					if ((resp.noteList[i].reveiwCreatedBy != null && resp.noteList[i].reveiwCreatedBy != "") && (resp.noteList[i].adminReview != null && resp.noteList[i].adminReview != "")) {
					    reviewData = '<div class="review-box " style="margin-top: -48px;">'
								    + '<span class="admin-review greytext "><p style="color: #1d7fed;font-weight: 700;">' + resp.noteList[i].reveiwCreatedBy + '(Admin) Review</p>- &nbsp;<p>' + resp.noteList[i].adminReview + '</p></span>'
								    +'</div>';
									    
					}
					
					var userRole = $("#userRole").val();
					let reviewBtn= '';
					if(userRole.includes('rol001')) {
						reviewBtn = '<a class="actionicon" id="adminReviewBtn" onclick=adminReveiw("'+resp.noteList[i].noteId+'")><i class="bx bxs-pencil" ></i></a>'
  
					}
					
					 let res = [];
					 /*var ownerImage = "https://images.pexels.com/photos/1633578/pexels-photo-1633578.jpeg?cs=srgb&dl=beef-bread-buns-1633578.jpg&fm=jpg";*/
					res = resp.noteList[i].documentList;
					
					let locationLink = "<a target='_blank' href='https://www.google.com/maps/search/?api=1&query="+ resp.noteList[i].latitude + "," + resp.noteList[i].longitude +"'>";
					
					
					xyz += '<div class="col-md-10"><table class="table table-borderless"> <tbody><tr>'
						
						+'<td width="90%">'
						/*+'<img  src="'+ownerImage+'" class="rounded-circle"/>'*/
						+'<div class="d-flex align-items-center justify-content-between">'
						+'<div class="fL">'
						+'<p class="titletxt">'+resp.noteList[i].noteTitle+'</p>'
						+'<p class="desc">'+resp.noteList[i].noteDesc+'</p>'
						+'</div>'
						+'<div class="fR" >'
						+ reviewBtn
						/*+'<a class="actionicon" onclick=editNote("'+resp.noteList[i].noteId+'" )><i class="bx bxs-pencil" ></i></a>'
                        +'<a class="actionicon" onclick=deleteNote("'+resp.noteList[i].noteId+'")><i class="bx bx-trash"></i></a>'*/						+'</div></div>'
						+'<div class=""><table class="table table-borderless"><tbody><tr><td><div class="d-flex">';
						for(var j=0;j<res.length;j++){
							xyz += '<div class="bdr"><a href="'+res[j].fileUrl+'" target="_blank">'
/*								+'<img title="'+res[j].fileName+'" src="'+res[j].fileUrl+'" class="img-fluid"/>
*/								+`<figure>
								  <img class="img-fluid" src="${res[j].fileUrl}" alt="Image" />
								  <figcaption class="note-filepath">${res[j].fileName}</figcaption>
								</figure></a></div>'`
						}
						xyz += '</div><div class="d-flex align-items-center mt-3">'
						+'<span class="greytext">'
						+ (resp.noteList[i].noteLead !== null ? "Lead" : resp.noteList[i].noteContact !== null ? "Contact" : "Account")
						+'</span>'
						+'<span class="greytext prd-5">-</span>'
						+'<span class="bluetext"><a href="#">'
						+ (resp.noteList[i].noteLead !== null ? resp.noteList[i].noteLead : resp.noteList[i].noteContact !== null ? resp.noteList[i].noteContact : resp.noteList[i].noteAccount)
						+'</a></span>'
						+'<span class="dot"></span>'
						+'<span class="greytext">Add Note</span>'
						+'<span class="dot"></span>'
						+'<span class="greytext">'
						+ resp.noteList[i].createdDate 
						+ ' '
						+'by' 
						+ ' '
						+ resp.noteList[i].createdBy
						+'</span>'
						+'</div>'
						+'<div class="d-flex align-items-center">'
						+'<span class="dot"></span>'
						+'<span class="greytext">See Location -</span>'
						+'<span class="greytext">'
						+ locationLink
                        +'<i class="bi bi-geo-alt-fill" style="font-size: 13px;"></i>'// Replace with your preferred location icon
						+'</span>'
						+'</div>'
						+'</td></tr></table></div></td></tr>'
						+'</table></div><div class="col-md-2"></div>';	
						
					xyz += reviewData
						
				} 
				$("#noteListWithDoc").append(xyz);
		        
		        
		       
		        if (len > 0) {

					$('#totalNotePageno').val(resp.noteList[0].totalPageno); 
					pages = resp.noteList[0].totalPageno;
					
		
				}
			
				if(statuss)
				createPaginationNote(pages, pageno);
				
				//console.log('response for leadId------'+JSON.stringify(response));
			} else {
				
				$("#noteListWithDoc").append(response.message);
				$("#noteListWithDoc").css({"margin-left": "initial", "color": "#807f7f","font-size": "14px"});
				$("#notePagination").hide();
			}			
		}
		
	});
	
}

function closePopup() {
	document.getElementById("popupBar").style.display = "none";
	document.body.style.overflow = "auto";
}

let noteId = "";
function adminReveiw(id) {
	noteId = id;
	document.getElementById("popupBar").style.display = "block";
	document.body.style.overflow = "hidden";


}


function submitAdminReview() {
	var adminReview = document.getElementById("adminReviewText").value;

	$.ajax({
		type: "GET",
		url: "view-crm-leads-view-detail-admin-reviews-note?id=" + noteId + "&desc=" + adminReview,
		success: function(response) {

			if (response.code == "Success") {
				console.log(response);
				closePopup();

				location.reload();
			}
		}
	});
}

//Pagination
var pages;
function createPaginationNote(pages, page) {
	statuss = false;
	if ($("#totalNotePageno").val() == '') {
		var pages = 10;
	} else {
		var pages = $("#totalNotePageno").val();
	}

	var str = '<ul>';
	var active;
	var pageCutLow = page - 1;
	var pageCutHigh = page + 1;
	// Show the Previous button only if you are on a page other than the first
	if (page > 1) {
		//str += '<li class="page-item previous no"><a onclick="createPagination(pages, ' + (page - 1) + ')">Previous</a></li>';
		str += '<li class="page-item previous no"><a onclick="createPaginationNote(pages, ' + (page - 1) + ')">&laquo;</a></li>';

	}
	// Show all the pagination elements if there are less than 6 pages total
	if (pages < 6) {
		for (let p = 1; p <= pages; p++) {
			active = page == p ? "active" : "no";
			str += '<li class="' + active + '"><a onclick="createPaginationNote(pages, ' + p + ')">' + p + '</a></li>';
		}
	}
	// Use "..." to collapse pages outside of a certain range
	else {
		// Show the very first page followed by a "..." at the beginning of the
		// pagination section (after the Previous button)
		if (page > 2) {
			str += '<li class="no page-item"><a onclick="createPaginationNote(pages, 1)">1</a></li>';
			if (page > 3) {
				str += '<li class="out-of-range"><a onclick="createPaginationNote(pages,' + (page - 2) + ')">...</a></li>';
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
			str += '<li class="page-item ' + active + '"><a onclick="createPaginationNote(pages, ' + p + ')">' + p + '</a></li>';

		}
		// Show the very last page preceded by a "..." at the end of the pagination
		// section (before the Next button)
		if (page < pages - 1) {
			if (page < pages - 2) {
				str += '<li class="out-of-range"><a onclick="createPaginationNote(pages,' + (page + 2) + ')">...</a></li>';
			}
			str += '<li class="page-item no"><a onclick="createPaginationNote(pages, pages)">' + pages + '</a></li>';
		}
	}
	// Show the Next button only if you are on a page other than the last
	if (page < pages) {
		str += '<li class="page-item next no"><a onclick="createPaginationNote(pages, ' + (page + 1) + ')">&raquo;</a></li>';
		changePaginationNote(page);
	} else if (page <= pages) {
		//str += '<li class="page-item next no"><a onclick="createPagination(pages, '+(page+1)+')">Next</a></li>';
		changePaginationNote(page);
	}
	str += '</ul>';
	// Return the pagination string to be outputted in the pug templates
	document.getElementById('notePagination').innerHTML = str;
	return str;

}


function changePaginationNote(page) {
	var id = idd;
	var filterDate = date
	var filterTitle = title
	var pageno = page;
	getNoteAll(id, pageno, filterDate, filterTitle);

}

var locationPermission = true;
var LocationMessage = "";
function watchLocationPermission() {
	if (navigator.geolocation) {
		navigator.geolocation.watchPosition(showPosition,

			function(error) {
				if (error.code === error.PERMISSION_DENIED) {
					// alert(locationPermission)
					locationPermission = false;
					LocationMessage = "Permission denied! Please provide location access."
				} else {
					console.error("Error getting location:", error.message);
					LocationMessage = error.message;
					locationPermission = false;
				}
			}
		);//here "showPosition" is a function which is declared on the common.js file
	} else {
		console.log("browser not supported");
		locationPermission = false;
		LocationMessage = "browser not supported";
	}
}

//Get Contact With Account Autosearch 

function getContactList() {
	var searchVal = $("#contactName").val();
	if (searchVal.length === 0) {
		$("#suggesstion-boxcontact_").hide();
	}

	if (searchVal) {
		$.ajax({
			type: "POST",
			url: "view-crm-meetings-autosearchContact",
			dataType: 'json',
			contentType: 'application/json',
			data: searchVal,
			success: function(response) {
				if (response.code == "Success") {

					if (response.body.length != 0) {
						$("#search").css("background", "#FFF");
						var content = '<ul id="autocomplete-list1" class="searhlist-dsg">';
						for (var i = 0; i < response.body.length; i++) {
							content += '<li class="autocompletedata cp" style="margin-left: -21px; font-weight: 600" onClick="selectAutocompleteContact(\''
								+ response.body[i].name
								+ '\',\''
								+ response.body[i].key
								+ '\',\''
								+ response.body[i].code
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
			error: function(data) {
				console.log(data);
			}
		})
	}

}
function selectAutocompleteContact(name, ContactId, code) {

	$.ajax({
		type: "GET",
		url: "view-crm-meetings-get-deal-account-data?id=" + ContactId,
		success: function(response) {
			if (response.code == "Success" && response.body.length > 0) {
				var accountId = response.body[0].key;
				$("#accountId").val(accountId);

				var account = response.body[0].name;
				$("#dealAccountName").val(account);


			}
		}
	});

	if (name) {
		$("#contactId").val(ContactId);

		$("#contactName").val(name);
		$("#contactMail").val(code);
		$("#search").val(ContactId);
		$("#search").attr('data-procat', name);
		$("#suggesstion-boxcontact_").hide();

	} else {
		$("#contactId").val("");

		$("#contactName").val("");

		$("#search").val("");
		$("#search").attr('data-procat', "");
		$("#suggesstion-boxcontact_").hide();

	}
}
function selectAutocompleteContactValue() {

	$("#contactId").val("");

	$("#contactName").val("");

	$("#search").val("");
	$("#search").attr('data-procat', "");
	$("#suggesstion-boxcontact_").hide();
}

// Function for convert lead by Pankaj

function convertLeadToNextStep(data) {
	var obj = {};
	var dealcheck = $("input:checkbox[name=dealcheck]:checked").val();
	obj.leadOwnerId = data.leadOwner
	obj.leadId = data.leadId
	obj.firstName = data.firstName
	obj.lastName = data.lastName
	obj.accountName = data.company
	obj.email = data.email
	obj.phone = data.phone
	obj.fax = data.projectId
	obj.website = data.website
	obj.title = data.title
	obj.mobile = data.mobile
	obj.skypeId = data.skypeId
	obj.secondaryEmail = data.secondaryEmail
	obj.twitter = data.twitter
	obj.description = data.description
	obj.referenceContact = data.referenceContact
	obj.dealcheck = dealcheck;
	obj.dealAmount = $('#dealAmountCheck').val();
	obj.dealName = $('#dealNameCheck').val();
	obj.dealClosingDate = $('#dealClosingDateCheck').val();
	obj.dealStage = $('#dealStageCheck').val();
	obj.dealCampaignSource = $('#dealCampaignSource').val();
	obj.dealContactRole = $('#dealContactRole').val();
	obj.probability = $('#probability').val();

	console.log('converted data response--------------' + JSON.stringify(obj));

	var validation = true;

	$.ajax({
		type: "POST",
		url: "view-crm-leads-detail-converted",
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(response) {
			if (response.message == "Success") {

				sessionStorage.setItem("contact_Id", response.body[0].contactId)
				sessionStorage.setItem("account_Id", response.body[0].contactId)

			}
		},
		error: function(data) {

			console.log(data);
		}
	})

}

/*Add New Product Features*/


function profileSelect() {
	$('#addItemModel').modal('show');
	$('#addProductModal').modal('hide');
	$(".formValidation").remove();
	$("#mainItem").hide();
	$("#profile").show();
	$("#prCategoryId").val("");
	$("#tempCategoryId").val("");
	$("#prLevelId").val("");
	$("#productId").val("");
	$("#catDesc").text("");
	$("#vertical").empty();
	$("#imgemp").val("");
	$('#imgemp').attr('src', '../assets/images/noimage.jpg');
	//$("#imgemp").attr("src", "");
	var img = '<li data-thumb="../assets/css/extend/sld-noimg.jpg"><img src="../assets/css/extend/sld-noimg.jpg" id="imgLoc" alt="Mountains" class="hvrbox-layer_bottom img-fluid"></li>';
	$("#vertical").append(img);
	$("#productName").val("");
	$("#brand").val("");
	$("#mode").val("");
	$("#hsnCodeProduct").val("");
	$("#sicCode").val("");
	$("#fileUpload").val("");
	$("#productheadId").html("");
	$("#prodStatus").prop("checked", false);
	closeNavSku();

	//skuOptions.api.setRowData();
	//purchaseOptions.api.setRowData();
	$('#productSave').hide();
	//getBrandList();

	$("#reqTable").hide();
}

var columnSKUDefsProduct = [{
	headerCheckboxSelection: true,
	headerCheckboxSelectionFilteredOnly: true,
	checkboxSelection: true,
	width: 10,
	sortable: false,
	filter: false,
	resizable: true
},
{
	headerName: "SKU",
	field: "sku",
	cellRenderer: function(params) {
		return '<a onclick=openSKUDetails("' + params.data.sku + '","' + params.data.productId + '") href="javascript:void(0)">' + params.data.sku + '</a>';
	}
},
{
	headerName: "Manufacture Item",
	field: "manufacture",
	width: 200,
},
{
	headerName: "Model",
	field: "model"
},
{
	headerName: "Variation Type",
	field: "variationType"
},
{
	headerName: "Variation Value",
	field: "variationValue"
},
{
	headerName: "UOM",
	field: "unit"
},
{
	headerName: "Unit Sale Price",
	field: "sPrice",
	cellStyle: { textAlign: 'right' }
},
{
	headerName: "Create Date",
	field: "createdDate",
	cellStyle: { textAlign: 'center' }
}
];



var skuOptionsProduct = {
	columnDefs: columnSKUDefsProduct,
	rowSelection: 'multiple',
	defaultColDef: {
		sortable: true,
		filter: true,
		resizable: true,
		width: 150,
		height: 10
	},
	rowSelection: 'multiple',
	suppressRowClickSelection: true,
	onSelectionChanged: onSelectionChangedsku
};

function onSelectionChangedsku() {
	var selectedRows = skuOptionsProduct.api.getSelectedRows();

	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$('#deletess').attr("disabled", false);
		$('#newBtn2').attr("disabled", true);

	} else {
		$('#deletess').attr("disabled", true);
		$('#newBtn2').attr("disabled", false);

	}
}

function deleteskuDtls() {
	$("#deleteSKU").modal('show');
	$("#addItemModel").modal('hide');
}

function cancelModalSKUDtls() {
	$("#deleteSKU").modal('hide');
	$("#addItemModel").modal('show');
}

function productSave() {



	var data = [];
	$(".hvrbox-layer_bottom").each(function(i) {
		var xyz = "";
		toDataUrl($(this).attr("src"), function(myBase64) {

			xyz = myBase64;
			data.push(myBase64);
			console.log(xyz)
		});


	})



	obj = {};


	obj['productId'] = $("#productId").val();
	obj['productName'] = $("#productName").val();
	obj['brand'] = $("#brand").val();
	obj['mode'] = $("#mode").val();
	obj['hsnCode'] = $("#hsnCode").val();
	obj['sicCode'] = $("#sicCode").val();
	obj['productStatus'] = $("input[name='isActive']:checked").val();
	obj['productCategory'] = $("#prCategoryId").val();
	obj['productCategoryText'] = $("#catDesc").text();
	obj['imgList'] = data;
	$(".formValidation").remove();
	allPValid = true;
	if ($("#productName").val() == null || $("#productName").val() == "") {
		allPValid = false;
		validationModal("Product Name Required", "productName");
	}
	/* if( $("#brand").val() == null || $("#brand").val() == ""){
		allPValid = false;
		validationModal("Brand Required","brand");
	} 
	if( $("#mode").val() == null || $("#mode").val() == ""){
		allPValid = false;
		validationModal("Mode Required","mode");
	}  */
	/* if( $("#hsnCode").val() == null || $("#hsnCode").val() == ""){
		allPValid = false;
		validationModal("HSN Code Required","hsnCode");
	} 
	 */


	var productId = $('#productheadId').html();


	if (allPValid) {
		if (productId != "") {
			var rowCount = skuOptionsProduct.api.getDisplayedRowCount();
			if (rowCount > 0) {
				submitProduct(obj);
			} else {
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Please Select Atleast one Item");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
			}

		} else {
			submitProduct(obj);
		}


	}

}

function deleteskud() {

	var selectedNodes = skuOptionsProduct.api.getSelectedNodes();
	var selectedData = selectedNodes.map(node => node.data);
	sku = selectedData.map(node => node.sku);
	var id = $('#productId').val();
	$('.loader').show();
	$.ajax({
		type: "GET",
		url: "view-product-deleteSku?id=" + sku,
		success: function(response) {
			if (response.message == "Success") {
				$('.loader').hide();
				$("#addItemModel").modal('show');
				$('#newBtn2').attr("disabled", false);
				$('#deletess').attr("disabled", true);

				agGrid.simpleHttpRequest({
					url: 'view-product-get-sku-by-product?id=' + id
				}).then(function(data) {
					console.log(data)
					skuOptionsProduct.api.setRowData(data);

					$("#skuPurchase").empty();
					$("#skuPurchase").append("<option value>Select</option>");
					for (var i = 0; i < data.length; i++) {

						$("#skuPurchase").append("<option value='" + data[i].sku + "'>" + data[i].sku + "</option>");
					}
				});
				closeNav();
				cancelModalSKUDtls();
			}
		},
		error: function(data) {
			console.log(data);
			$('.loader').hide();
		}
	})

}

function profilecancelbtn() {
	$('#addProductModal').modal('show');
	$('#addItemModel').modal('hide');
}

function toDataUrl(url, callback) {
	var xhr = new XMLHttpRequest();
	xhr.onload = function() {
		var reader = new FileReader();
		reader.onloadend = function() {
			callback(reader.result);
		}
		reader.readAsDataURL(xhr.response);
	};
	xhr.open('GET', url);
	xhr.responseType = 'blob';
	xhr.send();
}

function saveFile() {

	var uFile = $('#fileUpload')[0].files[0];
	var fileName = $('#fileUpload').val();
	console.log(uFile);
	console.log(fileName);

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
		url: "view-product-upload-file",
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


function next() {
	$("#next").show();
	$("#main").show();
	$("#profile").show();
	/* $("#productSave").hide();	 */
	$("#accordionExample").hide();
	var data = [];
	$(".hvrbox-layer_bottom").each(function(i) {
		var xyz = "";
		toDataUrl($(this).attr("src"), function(myBase64) {

			xyz = myBase64;
			data.push(myBase64);
			console.log(xyz)
		});
	})

	obj = {};

	obj['productId'] = $("#productId").val();
	obj['productName'] = $("#productName").val();
	obj['brand'] = $("#brand").val();
	obj['mode'] = $("#mode").val();
	obj['hsnCode'] = $("#hsnCodeProduct").val();
	obj['sicCode'] = $("#sicCode").val();
	obj['productStatus'] = $("input[name='isActive']:checked").val();
	obj['productCategory'] = $("#prCategoryId").val();
	obj['productCategoryText'] = $("#catDesc").text();
	obj['imgList'] = data;
	obj['addedFrom'] = "crm";
	obj['contactId'] = $("#contactId").text();
	$(".formValidation").remove();
	allPValid = true;
	if ($("#productName").val() == null || $("#productName").val() == "") {
		allPValid = false;
		validationModal("Product Name Required", "productName");
	}
	/* if( $("#brand").val() == null || $("#brand").val() == ""){
		allPValid = false;
		validationModal("Brand Required","brand");
	}  */
	/* if( $("#mode").val() == null || $("#mode").val() == ""){
		allPValid = false;
		validationModal("Mode Required","mode");
	}  */
	if ($("#hsnCodeProduct").val() == null || $("#hsnCodeProduct").val() == "") {
		allPValid = false;
		validationModal("HSN/SAC Code Required", "hsnCodeProduct");
	}
	var productId = $('#productheadId').html();
	if (allPValid) {
		if (productId != "") {
			var rowCount = skuOptionsProduct.api.getDisplayedRowCount();
			if (rowCount > 0) {
				submitProduct(obj);
			} else {
				$("body").removeClass("overlay");
				$("#messageParagraph").text("Please Select Atleast one Item");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#mySKUGridProduct").show();
				$("#mainItem").show();
			}

		} else {
			submitProduct(obj);
		}


	}
}

function categoryBtn() {
	//closeModal();
	$("#myModalCat").modal('show');
	$('#addItemModel').modal('hide');
	getCategoryList();

}
function getCategoryList() {
	$('.loader').show();
	$("body").addClass("overlay");
	$("#productDiv").modal("show");
	$.ajax({
		type: "POST",
		url: "view-product-get-total-list",
		dataType: "json",
		contentType: "application/json",
		success: function(response) {
			if (response.message == "Success") {
				$("#costCeneterCBDiv").empty();
				for (var i = 0; i < response.body.length; i++) {
					var row = "";
					if (response.body[i].categoryId == response.body[i].parentId) {
						if (response.body[i].nodeCount > 0) {
							row = '<tr data-node-id="' + response.body[i].categoryId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '>' + response.body[i].categoryName + '</td></tr>';
						} else {
							row = '<tr data-node-id="' + response.body[i].categoryId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '><input class="benefitChk" type="checkbox" id="ccCheck_' + response.body[i].categoryId + '" value="' + response.body[i].categoryId +
								'" name="' + response.body[i].categoryName + '" onchange=selectCheckBox("' + response.body[i].categoryId + '","' + response.body[i].catLevel + '")>' + response.body[i].categoryName + '</td></tr>';
						}

					} else {
						if (response.body[i].nodeCount > 0) {
							row = '<tr data-node-id="' + response.body[i].categoryId + '" data-node-pid="' + response.body[i].parentId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '>' + response.body[i].categoryName + '</td></tr>';
						} else {

							row = '<tr data-node-id="' + response.body[i].categoryId + '" data-node-pid="' + response.body[i].parentId + '" class="abc" id="' + response.body[i].categoryId + '">' +
								'<td class="firstnode" id=lbl_' + response.body[i].categoryId + '><input class="benefitChk" type="checkbox" id="ccCheck_' + response.body[i].categoryId + '" value="' + response.body[i].categoryId + '" name="' +
								response.body[i].categoryName + '" onchange=selectCheckBox("' + response.body[i].categoryId + '","' + response.body[i].catLevel + '")>' + response.body[i].categoryName + '</td></tr>';
						}
					}
					$("#costCeneterCBDiv").append(row);

				}

				var pcat = $("#prCategoryId").val();
				$(".benefitChk").prop("checked", false);
				$("#ccCheck_" + pcat).prop("checked", true);

				$('.loader').hide();
				$("body").removeClass("overlay");

				$('#basic').simpleTreeTable({
					expander: $('#expander'),
					collapser: $('#collapser'),
					store: 'session',
					storeKey: 'simple-tree-table-basic'
				});
			}
		}, error: function(data) {
			console.log(data)
			$('.loader').hide();
			$("body").removeClass("overlay");
			$("#productDiv").modal("show");
		}
	});
}


function selectCategory() {

	var id = $("#tempCategoryId").val();
	var lvl = $("#prLevelId").val();

	if (id && lvl) {
		$("#prCategoryId").val(id);
		if (lvl == 'L1') {
			var textDesc = $("#lbl_" + id).text();
			$("#catDesc").text(textDesc);
		} else {
			var res = lvl.substring(1, 1000000);

			var lastText = $("#lbl_" + id).text();
			var textDesc = "";
			for (i = res; i > 1; i--) {
				var pUL = $("#" + id).attr("data-node-pid");
				//pUL = pUL.substring(3, 10000000000000);
				var newText = $("#lbl_" + pUL).text();
				console.log(newText + " " + i)
				textDesc = newText + " > " + textDesc;
				id = pUL;
			}
			textDesc = textDesc + lastText;
			$("#catDesc").html(textDesc);
		}
	}
	$("#myModalCat").modal("hide");
	$('#addItemModel').modal('show');
	closeModal();
}

function closeModal() {
	$("#myModalCat").modal("hide");
	//$("#myModal").modal("empty");
}

function selectCheckBox(id, lvl) {
	$(".benefitChk").prop("checked", false);
	$("#ccCheck_" + id).prop("checked", true);
	$("#tempCategoryId").val(id);
	$("#prLevelId").val(lvl);

}
function openNavItem() {
	$(".formValidation").remove();
	var productId = $("#productId").val();
	var productName = $("#productName").val();

	//$("#skudiv").hide();
	$("#skuPrdId").text(productId);
	$("#isEdit").val("");
	$("#skuId").val("");
	$("#model").val("");
	$("#manufacture").val(productName);
	$("#variationType").val("");
	$("#variationValue").val("");
	$("#itemUnit").val("");
	$("#salePrice").val("0");
	$("#saleTax").val("");
	$("#saleCess").val("");
	document.getElementById("mySidenavItem").style.cssText = "width: 30%; position: absolute; right:-10px; overflow: hidden; height:auto;bottom:4px;";

	document.getElementById("mainItem").style.width = "70%";
}
function closeNavSku() {
	$("#isEdit").val("");
	document.getElementById('skuId').readOnly = false;
	$("#skuId").val("");
	$("#model").val("");
	$("#manufacture").val("");
	$("#variationType").val("");
	$("#variationValue").val("");
	$("#itemUnit").val("");
	$("#salePrice").val("");
	$("#saleTax").val("");
	$("#saleCess").val("");
	document.getElementById("mySidenavItem").style.width = "0";
	document.getElementById("mainItem").style.width = "100%";
}

function submitProduct(dataset) {
	console.log(dataset)
	$.ajax({
		type: "POST",
		url: "/pipeline/view-product-save",
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {
                getProductActivity($("#contactId").text(),"productTimeline");
				//returnPage();
				agGrid.simpleHttpRequest({
					url: 'view-product-get-sku-listing?type=' + "both"
				}).then(function(data) {
					console.log(data)
					gridOptionsProduct.api.setRowData(data);
				});
				var productIdd = $('#productId').val();
				if (productIdd != "") {
					profilecancelbtn();
				} else {

					$('.loader').hide();
					$("body").removeClass("overlay");

					$("#productId").val(response.body.productId);
					$("#productheadId").html(response.body.productId);

					$("#mySKUGridProduct").show();
					$("#mainItem").show();
					$('#deletess').attr("disabled", true);
				}
			} else {
				$('.loader').hide();
				$("body").removeClass("overlay");
				swal({
					title: response.code,
					text: response.message,
					type: "warning"
				})
			}
		},
		error: function(response) {
			$('.loader').hide();
			$("body").removeClass("overlay");
			console.log(response);
		}
	})
}
function check1(fieldId) {
	var tempVal = $("#" + fieldId).val().replace(/[^0-9 ]/g, '');
	$("#" + fieldId).val(tempVal);
}


function saveSKUData() {
	obj = {};

	obj.isEdit = $("#isEdit").val();
	obj.productId = $("#productId").val();
	obj.sku = $("#skuId").val();
	obj.model = $("#model").val();
	obj.manufacture = $("#manufacture").val();
	obj.variationType = $("#variationType").val();
	obj.variationValue = $("#variationValue").val();
	obj.unit = $("#itemUnit").val();
	var price = $("#salePrice").val();
	price = price.replaceAll(",", "");
	obj.salePrice = price;
	obj.saleTax = $("#saleTax").val();
	obj.saleCess = $("#saleCess").val();

	$(".formValidation").remove();
	allValid = true;

	if (obj.manufacture == null || obj.manufacture == "") {
		allValid = false;
		validationModal("Item Name Required", "manufacture");
	}


	if (obj.unit == null || obj.unit == "") {
		var mode = $("#mode").val();
		if (mode == "PMODE00004") {
			allValid = true;
		} else {
			allValid = false;
			validationModal("UOM Required", "itemUnit");
		}

	}
	if (obj.salePrice == null || obj.salePrice == "") {
		allValid = false;
		validationModal("Sale Price Required", "salePrice");
	}


	if (allValid) {
		submitProductDetails(obj);

	}

}
function submitProductDetails(dataset) {
	console.log(dataset)
	$('.loader').show();
	$.ajax({
		type: "POST",
		url: "/purchase/view-product-save-sku-dtls",
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(dataset),
		success: function(response) {
			if (response.message == "Success") {
				$("#productSave").show();

				$('.loader').hide();
				closeNavSku();
				var productId = $("#productId").val();
				agGrid.simpleHttpRequest({
					url: 'view-product-get-sku-by-product?id=' + productId
				}).then(function(data) {
					console.log(data)
					skuOptionsProduct.api.setRowData(data);

					$("#skuPurchase").empty();
					$("#skuPurchase").append("<option value>Select</option>");
					for (var i = 0; i < data.length; i++) {
						$("#skuPurchase").append("<option value='" + data[i].sku + "'>" + data[i].sku + "</option>");
					}
				});

				agGrid.simpleHttpRequest({
					url: 'view-product-get-purchase-by-product?id=' + productId
				}).then(function(data) {
					console.log(data)
					purchaseOptions.api.setRowData(data);
				});
			} else {
				$('.loader').hide();
				$("#messageParagraph").text("This SKU value is already exists.");
				$("#msgOkModal").removeClass("btn3");
				$("#msgOkModal").addClass("btn1");
				$("#msgModal").modal('show');
				$("#productSave").show();
				/* swal({
					title: response.code,
					text: response.message,
					type: "warning"
				}) */
			}
		},
		error: function(response) {
			console.log(response);
		}
	})
}

