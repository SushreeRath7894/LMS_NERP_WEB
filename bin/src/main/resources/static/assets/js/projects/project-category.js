 $(document).ready(function() {
 $("#categorySave").click(function(){
    	data = {};
    	
    	data.categoryId = $("#categoryId").val();
    	data.categoryName = $("#categoryName").val();
    	data.categoryDesc = $("#categoryDesc").val();
    	data.categoryStatus = $("input[name='isActive']:checked").val();
    	
    	$(".formValidation").remove();
		allValid = true;
		if( data.categoryName == null || data.categoryName == ""){
			allValid = false;
			validationModal("Category Name Required","categoryName");
		} 
		if( data.categoryDesc == null || data.categoryDesc == ""){
			allValid = false;
			validationModal("Category Description Required","categoryDesc");
		} 
    	
		if(allValid) {
			$('.loader').show();
	    	$("body").removeClass("overlay");
	    	submitCategory(data);
		}
    	
    	
    })
    
    $("#subCatSave").click(function(){
    	data = {};
    	
    	data.categoryId = $("#subCatId").val();
    	data.categoryName = $("#subCatName").val();
    	data.categoryDesc = $("#subCatDesc").val();
    	data.categoryStatus = $("input[name='isSubCatActive']:checked").val();
    	data.parentId = $("#parentCat").val();
    	
    	var slno = $("#slnoval").val();
    	
    	$(".formValidation").remove();
		allValid1 = true;
		if( data.categoryName == null || data.categoryName == ""){
			allValid1 = false;
			validationModal("Sub-Category Name Required","subCatName");
		} 
		if( data.categoryDesc == null || data.categoryDesc == ""){
			allValid1 = false;
			validationModal("Sub-Category Description Required","subCatDesc");
		} 
		
		if(allValid1) {
			$('.loader').show();
	    	$("body").removeClass("overlay");
	    	submitSubCategory(data,slno);
		}
		
    })
    
    $("#deleteModalBtn").click(function(){
    	var data = $("#deleteModalBtn").attr("value");
    	var slno = $("#deleteModalBtn").attr("slno");
    	$("#deleteModalBtn").attr("disabled","disabled");
    	if(data) {
    		if(slno == "1") {
    			$.ajax({
    				type : "POST",
    				url : "project-category-delete",
    				dataType : 'json',
    				contentType : 'application/json',
    				data : data,
    				success : function(response) {
    					if (response.message == "Success") {
    						$("#deleteModalBtn").removeAttr("disabled");
    						$("#"+data).remove();
    						cancelModalBtn();
    						closeCat();
    					}
    				}, error : function(data) {
    					console.log(data)
    				}
    			});
    		} else if(slno == "2") {
    			$.ajax({
    				type : "POST",
    				url : "project-category-delete",
    				dataType : 'json',
    				contentType : 'application/json',
    				data : data,
    				success : function(response) {
    					if (response.message == "Success") {
    						$("#deleteModalBtn").removeAttr("disabled");
    						$("#"+data).remove();
    						cancelModalBtn();
    						closeNav();
    					}
    				}, error : function(data) {
    					console.log(data)
    				}
    			});
    		}
    	}
    })
    
    getCategoryList();
});

function getCategoryList() {
	$("#costCeneterCBDiv").empty();
	$.ajax({
        type: "POST",
        url: "project-category-get-total-list",
        dataType: "json",
        contentType: "application/json",
        success: function(response) {
            if (response.message == "Success") {
            	console.log(response.body)
            	if(response.body.length > 0) {
            		for(var i = 0; i < response.body.length; i++) {
            			var row = "";
	            		if(response.body[i].categoryId == response.body[i].parentId) {
	            			row = '<tr data-node-id="'+response.body[i].categoryId+'" class="abc" id="'+response.body[i].categoryId+'">'+
	    					'<td class="firstnode tr-node-one" id=lbl_'+response.body[i].categoryId+'>'+response.body[i].categoryName+
	    					'<label for="check1">&nbsp;&nbsp;<a href="javascript:void(0)"><i title="Add Child" class="ti-plus cbicon" onclick=openNav("'+response.body[i].categoryId+'","1");></i></a>&nbsp;<a href="javascript:void(0)"><i title="Edit Category" class="ti-pencil" onclick=editCategory("'+response.body[i].categoryId+'")></i></a>&nbsp;<a href="javascript:void(0)"><i title="Delete Category" class="ti-close" onclick=deleteCategory("'+response.body[i].categoryId+'")></i></a></label>'+
	    					'</td></tr>';
	            		} else {
	            			row = '<tr data-node-id="'+response.body[i].categoryId+'" data-node-pid="'+response.body[i].parentId+'" class="abc" id="'+response.body[i].categoryId+'">'+
	    					'<td class="firstnode" id=lbl_'+response.body[i].categoryId+'>'+response.body[i].categoryName+
	    					'<label for="check1">&nbsp;&nbsp;<a href="javascript:void(0)"><i title="Add Child" class="ti-plus cbicon" onclick=openNav("'+response.body[i].categoryId+'","2");></i></a>&nbsp;<a href="javascript:void(0)"><i title="Edit Category" class="ti-pencil" onclick=editSubCategory("'+response.body[i].categoryId+'","'+response.body[i].parentId+'")></i></a>&nbsp;<a href="javascript:void(0)"><i title="Delete Category" class="ti-close" onclick=deleteCategory("'+response.body[i].categoryId+'")></i></a></label>'+
	    					'</td></tr>';
	            		}
						$("#costCeneterCBDiv").append(row);
            		}
            		
            		$('.loader').hide();
	            	$("body").removeClass("overlay");
	            	
	            	$('#basic').simpleTreeTable({
	            	    expander: $('#expander'),
	            	    collapser: $('#collapser'),
	            	    store: 'session',
	            		storeKey: 'simple-tree-table-basic'
	            	});
	            	
            	}
            } else {
            	$('.loader').hide();
            	$("body").removeClass("overlay");
            }
        },
        error: function(response) {
            console.log(response);
            $('.loader').hide();
        	$("body").removeClass("overlay");
        }
    })
}

function cancelModalBtn() {
	$("#delete").modal('hide');
	$("#deleteModalBtn").attr("value","");
	$("#deleteModalBtn").attr("slno","");
	$("#deleteModalBtn").removeAttr("disabled");
}

function deleteCategory(id) {
	if(id) {
		$("#delete").modal('show');
		$("#deleteModalBtn").attr("value",id);
		$("#deleteModalBtn").attr("slno","1");
	} else {
		$("#delete").modal('hide');
		$("#deleteModalBtn").attr("value","");
		$("#deleteModalBtn").removeAttr("disabled");
		$("#deleteModalBtn").attr("slno","");
	}
}

function deleteSubCategory(id) {
	if(id) {
		$("#delete").modal('show');
		$("#deleteModalBtn").attr("value",id);
		$("#deleteModalBtn").attr("slno","2");
	} else {
		$("#delete").modal('hide');
		$("#deleteModalBtn").attr("value","");
		$("#deleteModalBtn").removeAttr("disabled");
		$("#deleteModalBtn").attr("slno","");
	}
}

function editSubCategory(id,pId) {
	openNav(pId,"2");
	$("#subCatId").val(id);
	if(id) {
		$.ajax({
	        type: "POST",
	        url: "project-category-get-category-dtls-by-id",
	        dataType: "json",
	        contentType: "application/json",
	        data: id,
	        success: function(response) {
	            if (response.message == "Success") {
	            	if(response.body != null && response.body != "") {
	            		$("#subCatName").val(response.body.categoryName);
			        	$("#subCatDesc").val(response.body.categoryDesc);
			        	if(response.body.categoryStatus == "1") {
			        		$('#subCatStatus').prop('checked', true);
			        	} else {
			        		$('#subCatStatus').prop('checked', false);
			        	}
			        	var onAttr = "deleteSubCategory('"+response.body.categoryId+"')";
			        	$("#dltSlideSubCat").attr("onclick",onAttr);
	            	}
	            }
	        }, error : function(data) {
	        	console.log(data)
	    		$("#subCatId").val("");
	    		$("#subCatName").val("");
	    		$("#subCatDesc").val("");
	    		$('#subCatStatus').prop('checked', true);
	    		$("#dltSlideSubCat").attr("onclick","");
	        }
		});
	} else {
		$("#subCatId").val("");
		$("#subCatName").val("");
		$("#subCatDesc").val("");
		$('#subCatStatus').prop('checked', true);
		$("#dltSlideSubCat").attr("onclick","");
	}
}

function editCategory(id) {
	openCat();
	$("#categoryId").val(id);
	if(id) {
		$.ajax({
	        type: "POST",
	        url: "project-category-get-category-dtls-by-id",
	        dataType: "json",
	        contentType: "application/json",
	        data: id,
	        success: function(response) {
	            if (response.message == "Success") {
	            	if(response.body != null && response.body != "") {
	            		$("#categoryName").val(response.body.categoryName);
			        	$("#categoryDesc").val(response.body.categoryDesc);
			        	if(response.body.categoryStatus == "1") {
			        		$('#catStatus').prop('checked', true);
			        	} else {
			        		$('#catStatus').prop('checked', false);
			        	}
			        	var onAttr = "deleteCategory('"+response.body.categoryId+"')";
			        	$("#deleteSlideCat").attr("onclick",onAttr);
	            	}
	            }
	        }, error : function(data) {
	        	console.log(data)
	        	$("#categoryId").val("");
	        	$("#categoryName").val("");
	        	$("#categoryDesc").val("");
	        	$('#catStatus').prop('checked', true);
	        	$("#deleteSlideCat").attr("onclick","");
	        }
		});
	} else {
		$("#categoryId").val("");
		$("#categoryName").val("");
		$("#categoryDesc").val("");
		$('#catStatus').prop('checked', true);
		$("#deleteSlideCat").attr("onclick","");
	}
}

function submitCategory(dataset) {
	$.ajax({
        type: "POST",
        url: "project-category-save",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(dataset),
        success: function(response) {
            if (response.message == "Success") {
            	console.log(response.body)
				closeCat();
            	getCategoryList();
            	$('.loader').hide();
            	$("body").removeClass("overlay");
            	$("#messageParagraph").text("Data Saved Successfully");
	        	$("#msgOkModal").removeClass("btn3");
	        	$("#msgOkModal").addClass("btn1");
	        	$("#msgModal").modal('show');
            } else {
            	getCategoryList();
            	$('.loader').hide();
            	$("body").removeClass("overlay");
            	$("#messageParagraph").text("Something Went Wrong");
	        	$("#msgOkModal").removeClass("btn1");
	        	$("#msgOkModal").addClass("btn3");
	        	$("#msgModal").modal('show');
            }
        },
        error: function(response) {
            console.log(response);
            getCategoryList();
            $('.loader').hide();
        	$("body").removeClass("overlay");
        	$("#messageParagraph").text("Something Went Wrong");
        	$("#msgOkModal").removeClass("btn1");
        	$("#msgOkModal").addClass("btn3");
        	$("#msgModal").modal('show');
        }
    })
}

function submitSubCategory(dataset,slno) {
	
	$.ajax({
        type: "POST",
        url: "project-category-save-subcat",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(dataset),
        success: function(response) {
            if (response.message == "Success") {
            	console.log(response.body)
				closeNav();
            	getCategoryList();
            	$('.loader').hide();
            	$("body").removeClass("overlay");
            	$("#messageParagraph").text("Data Saved Successfully");
	        	$("#msgOkModal").removeClass("btn3");
	        	$("#msgOkModal").addClass("btn1");
	        	$("#msgModal").modal('show');
            } else {
            	getCategoryList();
            	$('.loader').hide();
            	$("body").removeClass("overlay");
            	$("#messageParagraph").text("Something Went Wrong");
	        	$("#msgOkModal").removeClass("btn1");
	        	$("#msgOkModal").addClass("btn3");
	        	$("#msgModal").modal('show');
            }
        },
        error: function(response) {
            console.log(response);
            getCategoryList();
        	$('.loader').hide();
        	$("body").removeClass("overlay");
        	$("#messageParagraph").text("Something Went Wrong");
        	$("#msgOkModal").removeClass("btn1");
        	$("#msgOkModal").addClass("btn3");
        	$("#msgModal").modal('show');
        }
    })
}

function openNav(categoryId,sl) {
	closeCat();
	var pName = $("#lbl_"+categoryId).text();
	$("#subCatParentSpan").text(" "+pName);
	$("#parentCat").val(categoryId);
	
	$("#subCatId").val("");
	$("#subCatName").val("");
	$("#subCatDesc").val("");
	$('#subCatStatus').prop('checked', true);
	
	$("#slnoval").val(sl);
	
	$("#dltSlideSubCat").attr("onclick","");
	
	document.getElementById("mySidenavSubCat").style.cssText = "width: 270px; position: absolute; right:-10px; overflow: hidden;";
	document.getElementById("main").style.width = "420px";
}

function closeNav() {
	
	$("#subCatParentSpan").text(" ");
	$("#parentCat").val("");
	
	$("#subCatId").val("");
	$("#subCatName").val("");
	$("#subCatDesc").val("");
	$('#subCatStatus').prop('checked', true);
	
	$("#slnoval").val("");
	
	$("#dltSlideSubCat").attr("onclick","");
	
	document.getElementById("mySidenavSubCat").style.width = "0";
	document.getElementById("main").style.width = "480px";
}

function openCat() {
	closeNav();
	$("#categoryId").val("");
	$("#categoryName").val("");
	$("#categoryDesc").val("");
	$('#catStatus').prop('checked', true);
	
	$("#deleteSlideCat").attr("onclick","");
	
	document.getElementById("mySidenavCat").style.cssText = "width: 270px; position: absolute; right:-10px; overflow: hidden;";
	document.getElementById("main").style.width = "420px";
}

function closeCat() {
	
	$("#categoryId").val("");
	$("#categoryName").val("");
	$("#categoryDesc").val("");
	$('#catStatus').prop('checked', true);
	
	$("#deleteSlideCat").attr("onclick","");
	
	document.getElementById("mySidenavCat").style.width = "0";
	document.getElementById("main").style.width = "480px";
}