
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
			url : "building-management-upload-file",
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
		$("#deleteFile").modal('show');
	}
	function cancelModalBtn() {
		$("#deleteFile").modal('hide');
	}

function deleteLocFile() {
    $("#docUrlEdit").val("");
    $('#fileUpload').val("");
    $('#imgLoc').attr('src', '');
    $('#imgLoc').attr('src', '../assets/images/noimage.jpg');
    $("#deleteFile").modal('hide');

    var fileData = new FormData();
    fileData.append('file', 'none');
    fileData.append('path', 'none');


    $.ajax({
        type: "POST",
        url: "building-management-delete-file",
        enctype: "multipart/form-data",
        contentType: false,
        processData: false,
        cache: false,
        success: function(response) {},
        error: function(e) {

        }
    });
}

function editImages() {
    var i = 0;
    $("#saveAttachmentBtn").show();
    $("#editAttachmentBtn").hide();
    $("#assetimageview").hide();
    $("#viewAttachmentBtn").show();
    $("#docTbl").show();
    $('.docNoclss').each(function() {
        i = i + 1;
    });
    if (i == 0) {
        var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
            '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>' +
            '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>' +
            '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>' +
            '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
            '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>' +
            '</tr>';
        $("#doctbodyData").html(tbl);
    }
}
function ownershipChange() {
		$("#sdate").val("");
    	$("#edate").val("");
    	$("#locRent").val("");
    	$("#pdate").val("");

    if ($("#locOwnership").val() == "Own") {
        $('#leaseDiv').hide();
        $('#ownDiv').show();
    } else if ($("#locOwnership").val() == "Lease" || $("#locOwnership").val() == "Rent") {
        $('#leaseDiv').show();
        $('#ownDiv').hide();
    } else {
        $('#leaseDiv').hide();
        $('#ownDiv').hide();
    }
}

function changeOfProperty() {
    if ($("#locationType").val() == "LTYPE007") {
        $("#heightDivP").hide();
    } else {
        $("#heightDivP").show();
    }
}
function extractFilename(url) {
        // Split the URL by '/'
        var urlParts = url.split('/');
        // Get the last part of the URL, which is the filename
        var filename = urlParts[urlParts.length - 1];
        return filename;
    }

	function getFileExtension(filename) {
	    return filename.split('.').pop();
	}

	function viewDocuemntFile(iurl){
		window.open(window.atob(iurl), '_blank', 'width=800,height=600,top=100,left=100');
	}
	
let stateId = '';
let cityId = '';
function BuildingEdit(id=null) {
    $('#locDataId').val(id);
    $('#assetAgDiv').hide();
    $('#ownerAgDiv').hide();
    $('#rentAgDiv').hide();
    $('#leaseDiv').hide();
    $('#ownDiv').hide();
    $("#myGrid").hide();
    $("#searchRowDiv").hide();

    $('.formValidation').remove();
    $("#floorSection").val("");
    $("#sectionRoomId").val("");
    //$("#searchRowDiv").hide();
    changeOfProperty();

    $('.loader').show();
    $("body").addClass("overlay");

    $("#liFloor").empty();
    $("#check1").prop("checked", true);

    $(".cbicon").addClass("green-cls");
	var imagesdiv="";
    if (id) {

        $("#saveAttachmentBtn").hide();
        $("#editAttachmentBtn").show();
        $("#assetimageview").show();
        $("#docTbl").hide();

        $("#accordionForFloorRoom").show();
        $("#viewLocDivId").empty();
        $("#viewLocDivId").text("Property ID: " + id);
       

        agGrid.simpleHttpRequest({
            url: 'building-management-details?id=' + id
        }).then(function(response) {
            console.log("RESPONSE::", response)
            if (response.code == "success") {
                var jsonData = JSON.parse(response.body);
                var allData1 = jsonData.Property;
                var allData = allData1[0];
                var allData2 = allData.AssetImages;
                console.log("RESPONSE PARSED::", allData);
				stateId=allData.locState;
				cityId=allData.locCity;
			//	$("#propertyId").html(allData.locationName);
				$("#propertyId1").html(allData.locationName);
				$("#propertyId2").html(allData.locationName);
				$("#propertyId3").html(allData.locationName);
				$("#propertyId4").html(allData.locationName);
				$("#propertyId5").html(allData.locationName);
				$("#propId1").html(allData.locationId);
				$("#propId2").html(allData.locationId);
				$("#propId3").html(allData.locationId);
				$("#propId4").html(allData.locationId);
				$("#propId5").html(allData.locationId);
                $("#locationName").val(allData.locationName);
                $("#locationCode").val(allData.locationCode);
                $("#locationType").val(allData.locationType).trigger('change');
                $("#locCountry").val(allData.locCountry).trigger('change');
               /* $("#locState").val(allData.locState).trigger('change');
                $("#locCity").val(allData.locCity).trigger('change');*/
                $("#locPincode").val(allData.locPincode);
                $("#locDescription").val(allData.locDescription);

                $("#locOwnership").val(allData.locOwnership).trigger('change');
				ownershipChange();
                $("#locStreet").val(allData.locStreet);
                $("#locHeight").val(allData.locHeight);
                $("#locWidth").val(allData.locWidth);
                $("#locLength").val(allData.locLength);
                $("#locArea").val(allData.locArea);
                $("#locationId").val(allData.locationId);

                $("#locDescription").val(allData.locDescription);
                $("#locPincode").val(allData.locPincode);
                $("#sdate").val(allData.locSdate);
                $("#edate").val(allData.locEdate);
                $("#locRent").val(allData.locRent);
                $("#pdate").val(allData.locPdate);
                $("#docUrlEdit").val(allData.fileLocation);

                changeOfProperty();

                var locVirtual = allData.locVirtual;

                if (locVirtual == '1') {
                    $('#locVirtual').prop('checked', true);
                } else {
                    $('#locVirtual').prop('checked', false);
                }

                var locStatus = allData.locStatus;

                if (locStatus == '1') {
                    $('#locStatus').prop('checked', true);
                } else {
                    $('#locStatus').prop('checked', false);
                }



                $('#imgLoc').attr('src', '');
                if (allData.fileLocation != null &&
                    allData.fileLocation != "") {
                    $('#imgLoc').attr('src', '/document/document/' + allData.fileLocation);
                } else {
                    $('#imgLoc').attr('src', '../assets/images/noimage.jpg');
                }

                $("#myUL").show();

                $("#locchkboxlabel").text("");
                var labeltxt = allData.locationCode +
                    " - " + allData.locationName;
                $("#locchkboxlabel").text(labeltxt);
                $("#locCount").text(
                    ' (' + allData.locCount + ')');

                $('.loader').hide();
                $("body").removeClass("overlay");


                if (allData2 != null && allData2.length != 0) {
              //      $("#doctbodyData").empty();
                    allData2.forEach(function(rowNode, index) {
                        var filename = extractFilename(rowNode.docurl);
                        var iurl = "/document/assetDocUrl/" + filename;

                        if (getFileExtension(filename) == "jpg" || getFileExtension(filename) == "png" || getFileExtension(filename) == "jpeg") {
                            imagesdiv += `<div class="col-lg-2 col-md-8 mb-4 mb-lg-0">
										  <div class="bg-image hover-overlay ripple shadow-1-strong rounded" data-ripple-color="light">
										  <img src=${iurl} class="w-100"  /> </div></div>`;
                        }

                        tbl = '<tr>' +
                            '<td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
                            '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option>  </select> </div></td>' +
                            '<td><div class="form-group"> <input type="text" value="' + rowNode.docname + '" class="form-control docNoclss" id="docnoid_' + index + '"> </div></td>' +
                            '<td><div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_' + index + '" id="uploadFor_' + index + '"> <i class="ti-pencil" id="clickImg_' + index + '"></i> </label>' +
                            '<div class="controls"> <input type="file" class="document" id="uploadDoc_' +
                            index +
                            '"name="userImage" onchange="saveMultiFile(event)" value="' + rowNode.filename + '" /> </div>' +
                            '</div> <input type="hidden" id="uploadHidden_' + index + '" value="' + rowNode.docurl + '" class="uploadHidCls">' +
                            '<div id="uploadedBillDiv_' + index + '" align="center" class="uploadedBillCls"><div class="uploadicon position-l"><i class="bi bi-file-earmark" onclick="viewDocuemntFile(&quot;' + window.btoa(rowNode.docurl) + '&quot;);"></i></div></div>' +
                            '<div id="imageName_' + index + '" class="imageName">' +
                            rowNode.filename +
                            '</div>' +
                            '<input type="hidden" id="editId_' + index + '" value="' + rowNode.assetid + '">' +
                            '<div id="dltImage_' + index + '" class="custom-file-delete"><i class="ti-close position-l rmv1" onclick="openDeleteConfirm()"></i></div> </td>' +
                            '</tr>';
             //           $("#doctbodyData").append(tbl);

                    });

                } else {
                    var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>' +
                        '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>' +
                        '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>' +
                        '<td> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>' +
                        '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">' +
                        '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>' +
                        '</tr>';
          //          $("#doctbodyData").html(tbl);
                    $("#viewAttachmentBtn").hide();
                }
                $("#assetimageenter").html(imagesdiv);
                editImages();
            }
        });

        //showRoomDetails(id);

    } else {
        $("#accordionForFloorRoom").hide();
        $("#viewLocDivId").empty();
        $("#viewLocDivId").text("Property ID: ");
        $("#locationName").val("");
        $("#locationCode").val("");
        $("#locStreet").val("");
        $("#locWidth").val("");
        $("#locLength").val("");
        $("#locArea").val("");
        $("#locHeight").val("");
        $("#locationId").val("");
        var typeVal = $("#locationType option:first").val();
        $("#locationType").val(typeVal).trigger('change');
        $("#locCountry").val("").trigger('change');
        $("#locCountry").val("").trigger('change');
        $("#locCountry").val("").trigger('change');
        $("#locPincode").val("");
        $("#locDescription").val("");
        $("#locOwnership").val("").trigger('change');
        $('#locVirtual').prop('checked', false).trigger('change');
        $('#locStatus').prop('checked', false).trigger('change');

        $("#locState").val("").trigger('change');
//        $("#locState").append("<option value=''>Select</option>");
        $("#locCity").val("").trigger('change');
    //    $("#locCity").append("<option value=''>Select</option>");

		$("#sdate").val("");
    	$("#edate").val("");
    	$("#locRent").val("");
    	$("#pdate").val("");

        $('#imgLoc').attr('src', '');
        $('#imgLoc').attr('src', '../assets/images/noimage.jpg');

        $("#myUL").hide();

        $("#liFloor").empty();

        $(".flowsection").css("border-right", "0px solid #ccc")
        $(".slidearrow").hide();

        $(".panel-heading").removeClass("active");
        $(".panel-collapse").removeClass("show");

        /* $("#layoutId").addClass("collapsed");
        $("#layoutId").attr("aria-expanded","false"); */
        $('.loader').hide();
        $("body").removeClass("overlay");
        deleteLocFile();
    }
}

function calculateArea(element){
		element.value = element.value.replace(/[^0-9]/g, '');
		var width=$("#locWidth").val()
		var length=$("#locLength").val()
		var area=0;
		if((width!=null || width!='') && (length!=null || length!='')){
			area=parseFloat(width)*parseFloat(length);
            var acres = area / 43560;
			$("#locArea").val(acres.toFixed(2));
		}
	}

function saveProp() {

    var locCode = "Location";
    data = {};
    var uploadList = [];
    valid = true;
    data.locationId = $("#locationId").val();
    data.locationName = $("#locationName").val();
    data.locationCode = locCode;
    data.locationType = $("#locationType").val();
    data.locCountry = $("#locCountry").val();
    data.locState = $("#locState").val();
    data.locCity = $("#locCity").val();
    data.locOwnership = $("#locOwnership").val();
    data.locStreet = $("#locStreet").val();
    data.locHeight = $("#locHeight").val();
    data.locWidth = $("#locWidth").val();
    data.locLength = $("#locLength").val();
    data.locArea = $("#locArea").val();
    data.fileLocation = $("#docUrlEdit").val();
    data.locVirtual = "1";
    data.locStatus = $("input[name='isActive']:checked").val();

    data.locDescription = $("#locDescription").val();
    data.locPincode = $("#locPincode").val();
    data.locSdate =  $("#sdate").val();
    data.locEdate =  $("#edate").val();
    data.locRent =  $("#locRent").val();
    data.locPdate =  $("#pdate").val();

    if (data.locationName == null || data.locationName == "") {
		toastr.error("Property Name is Required");
		return;
    }
    if (data.locationType == null || data.locationType == "") {
		toastr.error("Property Type is Required");
		return;
    }
    if (data.locCountry == null || data.locCountry == "") {
		toastr.error("Country is Required");
		return;
    }
    if (data.locState == null || data.locState == "") {
		toastr.error("State is Required");
		return;
    }
    if (data.locCity == null || data.locCity == "") {
		toastr.error("City is Required");
		return;
    }
    if (data.locStreet == null || data.locStreet == "") {
		toastr.error("Street is Required");
		return;
    }
    if (data.locPincode == null || data.locPincode == "") {
		toastr.error("Pincode is Required");
		return;
    }
    if (data.locDescription == null || data.locDescription == "") {
		toastr.error("Description is Required");
		return;
    }
    if (data.locOwnership == null || data.locOwnership == "") {
		toastr.error("Ownership is Required");
		return;
    }
    if (valid) {
        data.documentList = uploadList;
    }
    if (valid) {
        $('.formValidation').remove();
        submitLocation(data,$("#locationId").val());
        console.log("data:::::", data);
    }
}
function submitLocation(dataset,id) {
		console.log("SAVE DATA:::",dataset)
		$(".loader").show();
		$("body").addClass('overlay');
		setTimeout(function() {
		$.ajax({
			type : "POST",
			url : "building-management-save",
			async: false,
			dataType : "json",
			contentType : "application/json",
			data : JSON.stringify(dataset),
			success : function(response) {
				console.log("Success Data",response.body);
				if (response.code == "success") {
	/*				var jsonData = JSON.parse(response.body);
					var allData=jsonData.Property;*/
					toastr.success(response.message)
					viewBuilding(id);
					cancelProp();

					$(".loader").hide();
					$("body").removeClass('overlay');

				} else {
					$(".loader").hide();
					$("body").removeClass('overlay');
				}
			},
			error : function(response) {
				$(".loader").hide();
				$("body").removeClass('overlay');
			}
		})
		}, 1000)
	}
	// save rent owner details dataaaa
	function saveOwner(){
		var valid= true;
		var datas = [];
		var uploadList = [];
		
		var selectedRows = gridOptionsBuild.api.getSelectedRows();
		
		var item = {};
		item.type = "Owner";
		item.ownerId = $("#ownerId").val();
		item.ownerName = $("#ownerName").val();
		item.ownerCountry = $("#ownerCountry").val();
		item.ownerState = $("#ownerState").val();
		item.ownerCity = $("#ownerCity").val();
		item.ownerStreet = $("#ownerStreet").val();
		item.ownerPincode = $("#ownerPincode").val();
		item.ownerContact = $("#ownerContact").val();
		item.ownerEmail = $("#ownerEmail").val();
		item.ownerStatus = $("#ownerStatus").val();
		item.locationId = selectedRows[0].locationId


		if (item.ownerName == null || item.ownerName == "") {
			toastr.error("Name is Required");
			return;
		 }
		if (item.ownerCountry == null || item.ownerCountry == "") {
			toastr.error("Country is Required");
			return;
		 }
		 if (item.ownerState == null || item.ownerState == "") {
			toastr.error("State is Required");
			return;
		 }
		if (item.ownerCity == null || item.ownerCity == "") {
			toastr.error("City is Required");
			return;
		 } 
		if (item.ownerStreet == null || item.ownerStreet == "") {
			toastr.error("Street is Required");
			return;
		 }
		if (item.ownerPincode == null || item.ownerPincode == "") {
			toastr.error("Pincode is Required");
			return;
		 }
		if (item.ownerContact == null || item.ownerContact == "") {
			toastr.error("Contact is Required");
			return;
		 }
		if (item.ownerEmail == null || item.ownerEmail == "") {
			toastr.error("Email is Required");
			return;
		 }
		item.documentList = uploadList;
		console.log("Item For Building Owner--------->",item);
		if (valid) {
			datas.push(item);
			saveRentOwnerData(datas);
		}
	}
	
	function saveRent(){
		$(".formValidation").remove();
		var valid= true;
		var datas = [];
		
		var uploadList = [];
		var selectedRows = gridOptionsBuild.api.getSelectedRows();
		
		var item = {};
		item.type = "Rent";
		item.ownerName = $("#ownerName1").val();
		item.ownerId = $("#ownerId").val();
		item.rentSDate = $("#rentSDate").val();
		item.rentEDate = $("#rentEDate").val();
		item.rentSecDeposit = $("#rentSecDeposit").val();
		item.rentRentPMonth = $("#rentRentPMonth").val();
		item.rentBankName = $("#rentBankName").val();
		item.rentIFSC = $("#rentIFSC").val();
		item.ownerStatus = $("#rentStatus").val();
		item.rentAcNo = $("#rentAcNo").val();
		item.locationId = selectedRows[0].locationId


		if (item.ownerName == null || item.ownerName == "") {
			toastr.error("Owner Name is Required");
			return;
		 }
		if (item.rentSDate == null || item.rentSDate == "") {
			toastr.error("Start Date is Required");
			return;
		 }
		if (item.rentEDate == null || item.rentEDate == "") {
			toastr.error("End Date is Required");
			return;
		 }
		if (item.rentSecDeposit == null || item.rentSecDeposit == "") {
			toastr.error("Security Deposit is Required");
			return;
		 }
		if (item.rentRentPMonth == null || item.rentRentPMonth == "") {
			toastr.error("Rent per Month is Required");
			return;
		 }
		if (item.rentBankName == null || item.rentBankName == "") {
			toastr.error("Bank Name is Required");
			return;
		 }
		if (item.rentIFSC == null || item.rentIFSC == "") {
			toastr.error("IFSC is Required");
			return;
		 }
		if (item.ownerStatus == null || item.ownerStatus == "") {
			toastr.error("Status is Required");
			return;
		 }
		item.documentList = [];
		 if(valid){
			 datas.push(item);
			 saveRentOwnerData(datas);
		 }
	}
	
	function saveRentOwnerData(datas){
		console.log("DATA::::TO::::SAVE:::",datas);
		setTimeout(function() {
			$.ajax({
				type : "POST",
				url : "building-management-add-owner",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(datas),
				success : function(response) {
					if (response.code == "success") {
						//cancelModalOwn();
						toastr.success(response.message)
						var selectedRows = gridOptionsBuild.api.getSelectedRows();
						viewRentOwnerDets(datas[0].type,selectedRows[0].locationId);
						cancelOwner();
						cancelRent();
					} else {
						$('.loader').hide();
					toastr.success("Something Went Wrong")
					}
				},
				error : function(data) {
					$('.loader').hide();
				}
			
			})
			}, 1000)
	}
	
	function savePropertyDocumentation(){
		
		$(".formValidation").remove();
		var valid= true;
		var datas = [];
		
		var uploadList = [];
		var selectedRows = gridOptionsBuild.api.getSelectedRows();
		
		var item = {};
		item.ownerId = selectedRows[0].locationId;
		$('.docNoclss').each(function() {
			if ($(this).val() == null || $(this).val() == "") {
				infofileName = false;
				toastr.error("Document Name is Required");
				return;
		//		validationModal("Document Name Required", $(this).attr('id'));
			}
		});
		$("#doctbodyData > tr").each(
						function(i) {
							var uFile = $(this).find(".document")[0].files[0];
							var fileName = $(this).find(".document").val();
							var fileNametxt = $(this).find('.imageName').text();
							var data = [];
							var x = [];
							
							if (fileNametxt != '' && fileNametxt != 'undefined' && fileNametxt != null ) {
								if (fileName != '' && fileName != 'undefined' && fileName != null ) {
								
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
								}
								uploadData = {};
								uploadData['documnentName'] = $("#docnoid_" + i).val();
								uploadData['documentFile'] = x;
								uploadData['fileName'] = $(this).find('.imageName').text();
								uploadData['documentURL'] = $(this).find(".uploadHidCls").val();
								uploadData['imageNameEdit'] = $(this).find(".uploadHidCls").val();
								uploadList.push(uploadData);
							}
						});
				item.documentList = uploadList;
				 datas.push(item);
		console.log("DATA::::TO::::SAVE:::",datas);
		setTimeout(function() {
			$.ajax({
				type : "POST",
				url : "building-management-add-documents",
				dataType : "json",
				contentType : "application/json",
				data : JSON.stringify(datas),
				success : function(response) {
					if (response.code == "success") {
					toastr.success(response.message)
					//	openDeleteConfirm();
						$('#docnoid_0').val("");
						$('#imageName_0').empty();
					    var selectedRows = gridOptionsBuild.api.getSelectedRows();
						cancelDocument();
						viewPropertyDocs(selectedRows[0].locationId);
						
						
					} else {
						$('.loader').hide();
						toastr.success("Something Went Wrong")
					}
				},
				error : function(data) {
					$('.loader').hide();
				}
			
			})
			}, 1000)
	}
	
function saveFloor(){
	$('.formValidation').remove();
	data = {};
	var valid=true;					
	data.locationId = $("#locationId").val();
	data.locationName = $("#floorName").val();
	data.locationCode = $("#floorId").val();
	data.floorId = $("#floorDBVal").val();
	data.floorHeight = $("#height").val();
	data.floorWidth = $("#width").val();
	data.floorLength = $("#length").val();
	data.documentList = [];

	if (data.locationCode == null || data.locationCode == "") {
			toastr.error("Floor Code is Required");
			return;
	}
	if (data.locationName == null || data.locationName == "") {
			toastr.error("Floor Name is Required");
			return;
	}
	if(valid){
		$('.loader').show();
		$("body").addClass("overlay");
		submitFloor(data,data.locationId);
	}
}
function submitFloor(dataset,id) {
  $.ajax({
        type : "POST",
        url : "building-management-save-floor",
        dataType : "json",
        contentType : "application/json",
        data : JSON.stringify(dataset),
        success : function(response) {
          console.log(response);
          if (response.code == "success") {
					toastr.success(response.message)
			showFloorDetss(id);
			
            $('.loader').hide();
            $("body").removeClass("overlay");

          } else {
					toastr.success("Something Went Wrong")
            $('.loader').hide();
            $("body").removeClass("overlay");
          }
        },
        error : function(data) {
          console.log(data);
          $('.loader').hide();
          $("body").removeClass("overlay");
        }
      })
}