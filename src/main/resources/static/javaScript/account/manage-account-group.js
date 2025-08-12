$(function() {
	$("#natureOfGroup").select2({
		placeholder: "Select",
		allowClear: true
	});

	$("#natureOfGroupParent").select2({
		placeholder: "Select",
		allowClear: true
	});
	//For tree structure of Module,Function and Activity Tree structure
	$.fn.extend({
		treed: function(o) {
			var openedClass = 'fa-minus-circle';
			var closedClass = 'fa-plus-circle';

			if (typeof o != 'undefined') {
				if (typeof o.openedClass != 'undefined') {
					openedClass = o.openedClass;
				}
				if (typeof o.closedClass != 'undefined') {
					closedClass = o.closedClass;
				}
			};

			//initialize each of the top levels
			var tree = $(this);
			tree.addClass("tree");
			tree.find('li').has("ul").each(function() {
				var branch = $(this); //li with children ul
				var titleExpand = "Expand";
				var titleCollapse = "Collapse";
				branch.prepend("<i class='indicator fa " + closedClass + "' title='" + titleExpand + "'></i>");
				branch.addClass('branch');

				// Only handle clicks on the indicator icon
				branch.find('.indicator').on('click', function(e) {
					var icon = $(this);
					icon.toggleClass(openedClass + " " + closedClass);
					icon.closest('li').children().children().toggle();

					var title = icon.hasClass(openedClass) ? titleCollapse : titleExpand;
					icon.attr('title', title);
					$('.listName').show();
					$('.addChild').show();
					$('.editChild').show();
					e.stopPropagation(); // Prevent the event from bubbling to the li
				});

				branch.children().children().toggle();
			});

			//fire event from the dynamically added icon
			tree.find('.indicator').each(function() {
				$(this).on('click', function() {
					$(this).closest('li').click();
				});
			});

			//fire event to open branch if the li contains an anchor instead of text
			tree.find('.branch>a').each(function() {
				$(this).on('click', function(e) {
					$(this).closest('li').find('.indicator').click();
					e.preventDefault();
				});
			});

			//fire event to open branch if the li contains a button instead of text
			tree.find('.branch>button').each(function() {
				$(this).on('click', function(e) {
					$(this).closest('li').find('.indicator').click();
					e.preventDefault();
				});
			});
		}
	});

	$('#tree1').treed();

	handelShowElement('rightSideFormParent');

	$("#parentName").attr("disabled", true);
	$("#natureOfGroup").attr("disabled", true);

})

/* $(function () {
	  if(!window.location.hash) {
			//setting window location
			window.location = window.location + '#valid';
			//using reload() method to reload web page
			window.location.reload();
		    
		}  
});  */

$(function() {
	$('.listName').show();
	$('.addChild').show();
	$('.editChild').show();

	$("#sbmtdata").click(function() {
		const ipAPI = 'https://api.ipify.org?format=json';
		if (!blankValidation("groupName", "TextField", "Child Name can not be left blank"))
			return false;
		var groupName = $('#groupName').val();
		var parentId = $("#parentId").val();
		var levelName = $("#levelName").val();

		//submitAndNext(data);
		swal.fire({
			title: "Are you sure to Submit?",
			text: "Once Submited,Can't revert back !",
			type: "warning",

			showCancelButton: true,
			confirmButtonColor: "#DD6BB5",
			confirmButtonText: "Submit",
			showLoaderOnConfirm: true,
			reverseButtons: true,
			confirmButtonAriaLabel: 'Thumbs up, great!',
			cancelButtonText: 'Cancel',
			cancelButtonAriaLabel: 'Thumbs down',

			preConfirm: () => {
				return fetch(ipAPI)
					.then(response => response.json())
					.then(data => Swal.insertQueueStep(data.ip))
					.catch(() => {
						Swal.insertQueueStep({
							type: 'error',
							title: 'Unable to get your public IP'
							,
						})
					})
			}
		}).then((result) => {
			if (result.value) {
				$.ajax({
					type: "POST",
					url: "add-child",
					data: { 'groupName': groupName, 'parentId': parentId, 'levelName': levelName },
					success: function(response) {
						console.log(response);
						if (response.message) {
							swal({
								title: "Data saved successfully.",
								type: "success",
							}).then(function() {
								window.location.href = "view-account-group";
							})
						} else {
							swal("Data saved successfully.");
							window.location.href = "view-account-group";
							//window.location.href="view-account-group";
						}
					}, error: function(response) {
						swal(response.code);
						window.location.href = "view-account-group";
					}
				})
			}
		})

	});


	$("#sbmtdataParent").click(function() {
		const ipAPI = 'https://api.ipify.org?format=json';
		if (!blankValidation("parentName", "TextField", "Parent Name can not be left blank"))
			return false;
		var parentName = $('#parentName').val();

		//submitAndNext(data);
		swal.fire({
			title: "Are you sure to Submit?",
			text: "Once Submited,Can't revert back !",
			type: "warning",

			showCancelButton: true,
			confirmButtonColor: "#DD6BB5",
			confirmButtonText: "Submit",
			showLoaderOnConfirm: true,
			reverseButtons: true,
			confirmButtonAriaLabel: 'Thumbs up, great!',
			cancelButtonText: 'Cancel',
			cancelButtonAriaLabel: 'Thumbs down',

			preConfirm: () => {
				return fetch(ipAPI)
					.then(response => response.json())
					.then(data => Swal.insertQueueStep(data.ip))
					.catch(() => {
						Swal.insertQueueStep({
							type: 'error',
							title: 'Unable to get your public IP'
							,
						})
					})
			}
		}).then((result) => {
			if (result.value) {
				$.ajax({
					type: "POST",
					url: "add-parent",
					data: { 'parentName': parentName },
					success: function(response) {
						console.log(response);
						if (response.message) {
							swal({
								title: "Data saved successfully.",
								type: "success",
							}).then(function() {
								window.location.href = "view-account-group";
							})
						} else {
							swal("Data saved successfully");
							window.location.href = "view-account-group";
						}
					}, error: function(response) {
						swal(response.code);
						window.location.href = "view-account-group";
					}
				})
			}
		})

	});

});


function addChildInfo() {
	const ipAPI = 'https://api.ipify.org?format=json';
	/*	if (!blankValidation("groupName", "TextField", "Child Name can not be left blank"))
			return false;*/
	var groupName = $('#groupName').val();
	var parentId = $("#parentId").val();
	var levelName = $("#levelName").val();


	if (groupName == "") {
		toastr.error("Child Name can not be left blank");
		return false;
	}

	console.log("groupName-->", groupName);
	console.log("parentId-->", parentId);
	console.log("levelName-->", levelName);

	//submitAndNext(data);
	swal.fire({
		title: "Are you sure to Submit?",
		text: "Once Submited,Can't revert back !",
		type: "warning",

		showCancelButton: true,
		confirmButtonColor: "#DD6BB5",
		confirmButtonText: "Submit",
		showLoaderOnConfirm: true,
		reverseButtons: true,
		confirmButtonAriaLabel: 'Thumbs up, great!',
		cancelButtonText: 'Cancel',
		cancelButtonAriaLabel: 'Thumbs down',

		preConfirm: () => {
			return fetch(ipAPI)
				.then(response => response.json())
				.then(data => Swal.insertQueueStep(data.ip))
				.catch(() => {
					Swal.insertQueueStep({
						type: 'error',
						title: 'Unable to get your public IP'
						,
					})
				})
		}
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "POST",
				url: "view-account-group-add-child",
				data: { 'groupName': groupName, 'parentId': parentId, 'levelName': levelName },
				success: function(response) {
					console.log(response);
					if (response.message) {
						/*swal({
							title: "Data saved successfully.",
							type: "success",
						}).then(function() {
							window.location.href = "manage-account-group";
						})*/

						toastr.success("Data saved successfully.");
						setTimeout(() => {
							window.location.href = "manage-account-group";
						}, 1000);
					} else {
						toastr.success("Data saved successfully.");
						setTimeout(() => {
							window.location.href = "manage-account-group";
						}, 1000);
						/* swal({
							title:response.code,
							text: response.message,
							type:"warning"
						}) */
					}
				}, error: function(response) {
					toastr.error(response.code);
					window.location.href = "manage-account-group";
				}
			})
		}
	})
}


function addParentInfo() {
	const ipAPI = 'https://api.ipify.org?format=json';

	/*  if ($("#parentName").val() == null) {
				  validation = validationUpdated("Parent Name can not be left blank",
						  "parentName");
			  }  */

	/*if (!blankValidation("parentName", "TextField", "Parent Name can not be left blank"))
		return false;*/
	/* if (!blankValidation("natureOfGroup","TextField", "Select Nature Of Group"))
	return false;  */
	var parentName = $('#parentName').val();
	var natureOfGroup = $('#natureOfGroup').val();
	console.log("parentName==" + parentName + "===natureOfGroup====" + natureOfGroup);


	if (parentName == "") {
		toastr.error("Please Enter Parent Name");
		return false;
	}


	if (natureOfGroup == "") {
		toastr.error("Please select nature of group");
		return false;
	}




	//return false;
	//submitAndNext(data);
	swal.fire({
		title: "Are you sure to Submit?",
		text: "Once Submited,Can't revert back !",
		type: "warning",

		showCancelButton: true,
		confirmButtonColor: "#DD6BB5",
		confirmButtonText: "Submit",
		showLoaderOnConfirm: true,
		reverseButtons: true,
		confirmButtonAriaLabel: 'Thumbs up, great!',
		cancelButtonText: 'Cancel',
		cancelButtonAriaLabel: 'Thumbs down',

		preConfirm: () => {
			return fetch(ipAPI)
				.then(response => response.json())
				.then(data => Swal.insertQueueStep(data.ip))
				.catch(() => {
					Swal.insertQueueStep({
						type: 'error',
						title: 'Unable to get your public IP'
						,
					})
				})
		}
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "POST",
				url: "view-account-group-add-parent",
				data: { 'parentName': parentName, 'natureOfGroup': natureOfGroup },
				success: function(response) {
					console.log(response);
					if (response.message == "Success") {
						toastr.success("Data saved successfully.");
						setTimeout(() => {
							window.location.href = "manage-account-group";
						}, 1000);
					} else {
						toastr.error("Group name already exists!");
						setTimeout(() => {
							//window.location.href = "manage-account-group";
						}, 1000);
					}
				}, error: function(response) {
					toastr.error(response.code);
					window.location.href = "manage-account-group";
				}
			})
		}
	})

}

function OpenModel(index, levelName) {
	$('#levelName').val(levelName);
	$('#parentId').val(index);
	//$('#myModal').modal('show');
	handelDivElement = "addChildData";

	handelShowElement('formForChild');
	$("#parent_save_btn_for_grup").removeClass("d-none");
	$("#cancelform-btn").removeClass("d-none");
	$("#add-btn").hide();

	/*var clickedElement = window.event ? event.srcElement : event.target;
	   var groupName = $(clickedElement).closest('span').find('.listName').text().trim();
	   console.log("clickedElement", clickedElement);
*/	   var clickedElement = window.event ? window.event.srcElement : event.target;

	// This gets the direct parent (usually the <a> wrapping the <i> icon)
	var parentElement = clickedElement.parentNode;

	// Now get the grandparent (e.g., the <span> tag)
	var grandParent = parentElement.parentNode;

	// Get the first child of the grandparent
	var firstChild = grandParent.firstElementChild;

	console.log("First child:", firstChild);
	console.log("First child text content:", firstChild.textContent.trim());
	$("#parentGroupName").text(firstChild.textContent.trim());

	if (firstChild.textContent.trim()) {
		$(".parent_acc_grp_name_text").removeClass("d-none");
	}


}


function OpenModelParent() {
	$("#parentName").val("");
	$("#natureOfGroup").val("");
	$('#myModalParent').modal('show');

}



function closeModelAddParent(index) {
	$("#parentName").val("");
	$("#natureOfGroup").val("");
	$('#myModalParent').modal('hide');
}

function closeModelAddChild(index) {
	$('#myModal').modal('hide');
}

function editModel(index, levelName) {
	//alert(index)
	$('#editId').val(index);
	$('#editName').val(levelName);
	handelDivElement = "childModify";
	//$('#myModalEdit').modal('show');
	handelShowElement('childModifySection');
	$("#parent_save_btn_for_grup").removeClass("d-none");
	$("#cancelform-btn").removeClass("d-none");
	$("#add-btn").hide();

}

function closeModelEdit() {
	$('#myModalEdit').modal('hide');
}

function modifyNameCategory() {
	var selectedId = $("#editId").val();
	var changedName = $("#editName").val();
	console.log("changedName==" + changedName)

	$.ajax({
		type: "GET",
		url: "view-account-group-modifyName?id=" + selectedId + "&nameGroup=" + changedName,
		success: function(response) {
			if (response.code == "Success") {
				/*	swal({
						title: "Modified Successfully.",
						type: "success",
					}).then(function() {
						window.location.href = "manage-account-group";
					})*/

				toastr.success("Modified Successfully.");
				setTimeout(() => {
					window.location.href = "manage-account-group";
				}, 1000);

			}
		}

	});
}

function editModelParent(index, levelName, natureGrp) {
	//alert(index)
	console.log(natureGrp)
	$('#editIdParent').val(index);
	$('#editNameParent').val(levelName);
	if (natureGrp === null || natureGrp === "null") {
		$('#natureOfGroupParent').val("").trigger('change');
	} else {
		$('#natureOfGroupParent').val(natureGrp).trigger('change');
	}

	//$('#myModalEditParent').modal('show');
	handelDivElement = "parentModification";
	handelShowElement('parentModification');
	$("#parent_save_btn_for_grup").removeClass("d-none");
	$("#cancelform-btn").removeClass("d-none");
	$("#add-btn").hide();


}

function closeModelEditParent() {
	$('#myModalEditParent').modal('hide');
}

function modifyNameCategoryParent() {
	var selectedId = $("#editIdParent").val();
	var changedName = $("#editNameParent").val();
	var changedNatureGrp = $("#natureOfGroupParent").val();
	$.ajax({
		type: "GET",
		url: "view-account-group-modifyNameParent?id=" + selectedId + "&nameGroup=" + changedName + "&natureGrp=" + changedNatureGrp,
		success: function(response) {
			if (response.code == "Success") {
				/*swal({
					title: "Modified Successfully.",
					type: "success",
				}).then(function() {
					window.location.href = "manage-account-group";
				})*/

				toastr.success("Modified Successfully.");
				setTimeout(() => {
					window.location.href = "manage-account-group";
				}, 1000);

			}
		}

	});
}


function handelShowElement(tabname) {
	$(".formSection").hide();
	$("." + `${tabname}`).show();
	$("." + `${tabname}`).addClass("activeDiv")
}

// Global Variable 
var handelDivElement = "";
function handelSaveBtn() {
	var divElement = $(".formSection.activeDiv");
	console.log("divElements-->", divElement);
	divElement.each(function(index, element) {
		console.log("innerelements-->", element);
	})
	if (divElement) {
		//const divId = divElement.attr("id");
		const divId = handelDivElement;

		console.log("Div-->", divId);

		if (divId == "parentAddForm") {
			addParentInfo();
		}
		else if (divId == "addChildData") {
			addChildInfo();
		}
		else if (divId == "childModify") {
			modifyNameCategory();
		}
		else if (divId == "parentModification") {
			modifyNameCategoryParent();
		}
	}


}


function addParentGroup() {
	handelShowElement('rightSideFormParent');
	$("#parentName").val('');
	$("#natureOfGroup").val('').trigger('change');
	handelDivElement = "parentAddForm";
	$("#cancelform-btn").removeClass("d-none");
	$("#add-btn").hide();
	$("#parentName").attr("disabled", false);
	$("#natureOfGroup").attr("disabled", false);
	$("#parent_save_btn_for_grup").removeClass("d-none");
}

function SearchUserInput(event) {
	if (event.key === "Enter" || event.keyCode === 13) {
		event.preventDefault();
		onQuickFilterChanged();
	}
}

function cancelForm() {
	$("#cancelform-btn").addClass("d-none");
	$("#add-btn").show();
	$("#parentName").attr("disabled", true);
	$("#natureOfGroup").attr("disabled", true);
	$("#parent_save_btn_for_grup").addClass("d-none");

	$("#parentAddForm").show();
	$("#addChildData").hide();
	$("#childModify").hide();
	$("#parentModification").hide();
	$(".parent_acc_grp_name_text").addClass("d-none");
	$("#parentGroupName").text('');
}




function resetBtn() {
	$("#quickFilter").val('');
	onQuickFilterChanged();
}

function onQuickFilterChanged() {
	var filterValue = $("#quickFilter").val().trim().toLowerCase();
	var accountGroupRow = $(".branch");
	accountGroupRow.each(function(index, element) {
		var elementInnerHtml = element.innerText.trim().toLowerCase();
		if (elementInnerHtml.includes(filterValue)) {
			console.log("the element is -->", element);
			$(element).show();
		}
		else {
			$(element).hide();
		}
	})
}
function removeSpecialChars(input) {
	input.value = input.value.replace(/[^a-zA-Z0-9 ,./()\-&%]/g, '');
}