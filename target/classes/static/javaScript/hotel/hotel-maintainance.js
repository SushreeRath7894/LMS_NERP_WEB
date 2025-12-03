$(document).ready(function(){
	
	$("#resultStatus").select2({
				placeholder: "Select ",
				allowClear: true
			});
	
	var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
				+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0" onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
				+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0"  onblur="checkForDuplicateEntry(event)"></div></td>'
				+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
				+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
				+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"></div><input type="hidden" id="editId_0></td>'
				+ '</tr>';
			$("#doctbodyData").html(tbl);
});


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
		var extension = fileName.split(".").pop();
		var iURL = URL.createObjectURL(uFile);
		$("#uploadedBillDiv_" + counter).html("");
	
	
		console.log("extension is-->", extension);
		if (extension == 'pdf' || extension == 'jpg' || extension == 'png' || extension == 'xls' || extension == 'doc' || extension == 'webp' || extension == 'xlsx' || extension == 'docx' || extension == 'txt') {
			window.st = 1;
		} else {
			toastr.error("Invalid file type! Please upload a valid document file. Supported formats: PDF, JPG, PNG, XLS, DOC, WEBP, XLSX, DOCX, TXT.");
			window.st = 0;
	
			var lengthOfTableRow1 = 0;
			$("#docTbl > #doctbodyData > tr").each(function() {
				lengthOfTableRow1 = lengthOfTableRow1 + 1;
			})
			var id = $("#dltValue").val();
			$("#" + id).closest('tr').remove();
			closeDeleteConfirm();
			if (lengthOfTableRow1 == 1) {
				var tbl = '<tr> <td style="display:none" align="center" class="pdb-24"><input class="checkCls" type="checkbox" id="check2"><label for="check2"></label></td>'
					+ '<td style="display:none"><div class="form-group"> <select class="form-control documentclss" id="docid_0"  onblur="removeValid(event);"> <option value="">Select</option> </select> </div></td>'
					+ '<td><div class="form-group"> <input type="text" value="" class="form-control docNoclss" id="docnoid_0" onblur="checkForDuplicateEntry(event)"></div></td>'
					+ '<td class="d-flex align-items-center"> <div class="control-group position-r"> <label class="custom-file-upload" for="uploadDoc_0" id="uploadFor_0"> <i class="ti-plus" id="clickImg_0"></i> </label>'
					+ '<div class="controls"> <input type="file" class="document" id="uploadDoc_0" name="userImage" onchange="saveMultiFile(event)" /></div> </div> <input type="hidden" id="uploadHidden_0" class="uploadHidCls">'
					+ '<div id="uploadedBillDiv_0" align="center" class="uploadedBillCls"></div> <div id="imageName_0" class="imageName"></div><div id="dltImage_0" class="dltImage"  ></div> </td>'
					+ '</tr>';
				$("#doctbodyData").append(tbl);
			}
		}
		if (extension != null && extension != "") {
			$("#uploadHidden_" + counter).val('');
		}
		var LightImg = "";
	
		if (["jpg", "png", "jpeg", "webp"].includes(extension.toLowerCase())) {
			LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-image img-color'></i></a></span>";
		} else if (extension.toLowerCase() === "pdf") {
			LightImg = "<span class='uploadicon position-l bdr-n'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-pdf img-color'></i></a></span>";
		} else if (["xls", "xlsx"].includes(extension.toLowerCase())) {
			LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-excel img-color'></i></a></span>";
		} else if (["doc", "docx"].includes(extension.toLowerCase())) {
			LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-word img-color'></i></a></span>";
		} else if (["mp4", "mov"].includes(extension.toLowerCase())) {
			LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-video img-color'></i></a></span>";
		} else if (["mp3", "wav", "aac"].includes(extension.toLowerCase())) {
			LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file-audio img-color'></i></a></span>";
		} else {
			LightImg = "<span class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_blank'><i class='fa fa-file img-color'></i></a></span>";
		}
	
		var dltImg = "<i class='ti-close position-l rmv1' onclick='openDeleteConfirm(0)'></i>";
		$("#uploadedBillDiv_" + counter).html(LightImg);
		$("#imageName_" + counter).html(fileName);
		$("#dltImage_" + counter).html(dltImg);
		$("#dltImage_" + counter).addClass("custom-file-delete");
		$("#clickImg_" + counter).removeClass("ti-plus");
		$("#clickImg_" + counter).addClass("ti-pencil");
		$("#dltImage_0").show();
	}
	
	function openDeleteConfirm(index) {
		$("#uploadedBillDiv_" + index).html("");
		$("#uploadHidden_" + index).val("");
		$("#uploadDoc_" + index).val("");
		$("#imageName_" + index).text("");
	
		$("#dltImage_" + index).hide();
		$("#uploadFor_" + index).show();
		$("#clickImg_" + index).addClass("ti-plus").removeClass("ti-pencil");
	
	}