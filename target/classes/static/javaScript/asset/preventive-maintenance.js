$(()=>{
	$('#catid').select2();  //#catid
	$('#assetsubcat').select2();  //#assetsubcat
	$('#frequency').select2();  //#frequency
	$('#occRate').select2();  //#occRate
	$('#priority').select2();  //#priority
	$('#taskType').select2();  //#taskType
	$('#taskUOM').select2();  //#taskUOM
});

(function($) {
	$.fn.visible = function() {
		return this.css('visibility', 'visible');
	};

	$.fn.invisible = function() {
		return this.css('visibility', 'hidden');
	};
})(jQuery);


//		ready Fuctions
$(document).ready(function() {
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	var gridDiv = document.querySelector('#activity');
	new agGrid.Grid(gridDiv, activityOptions);
	getDates();
	cancelPolicy();
	cancelCheckList();

});
// grid details startttsss
// main grid
var columnDefs = [
		{
			headerCheckboxSelection : false,
			headerCheckboxSelectionFilteredOnly : false,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true,
			pinned : 'left',
		},
		{
			headerName : 'Policy ID',
			field : "policyId",
			pinned : 'left',
		}, {
			headerName : 'Policy Name',
			field : "policyName",
		}, {
			headerName : 'Category',
			field : "category",
		}, {
			headerName : 'Sub Category',
			field : "subcategory",
		}, {
			headerName : 'Frequency',
			field : "frequency",
		}, {
			headerName : 'Occurrence Rate',
			field : "occRate",
			
		}, {
			headerName : 'Occurrence Starts From',
			field : "occSdate",
		},{
			headerName : 'Occurrence Ends On',
			field : "occEdate",
		},{
			headerName : 'Approve Status',
			field : "approvests",
			cellStyle : {
				textAlign : 'center'
			},cellRenderer : function(params) {
				if(params.data.approvests=="Approved"){
					return '<div style="color:#0642f5">Approved</div>';
				}else{
					return '<div style="color:#a9a9a9">Pending</div>';
				}	
			}
		}];
var gridOptions = {
	columnDefs : columnDefs,
	rowSelection : 'single',
	groupSelectsChildren : true,
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 170
	},
	onSelectionChanged : onSelectionChanged,
//	paginationAutoPageSize: true,
	paginationPageSize: 15,
	pagination: true,
};
var id = "";
var apstatus = "";
function onSelectionChanged() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	 var selectedData = selectedNodes.map(node => node.data);	 
	 id= selectedData.map(node => node.policyId);
	 apstatus=selectedData.map(node => node.approvests);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
		$(".br-dis").attr("disabled", true);
		$('#editCheckList').attr('disabled', true);
		$('#deleteCheckList').attr('disabled', true);
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	//		$("#catid, #frequency, #policyName, #occRate, #occSdate, #occEdate, #assetsubcat")
	if (rowCount > 0) {
		editAssetPolicy(id);
		nextTab('policyTab');
		onSelectionChangeChild();
		var editId= $("#hiddenId").val();
		if(editId != id){		
			$(".formValidation").remove();
			$("#catid, #frequency, #policyName, #occRate, #occSdate, #occEdate, #assetsubcat").prop('disabled', true);
			$("#cancelPolicy, #savePolicy").hide();
			$("#editPolicy, #addPolicy, #deletePolicy, #approvePolicy, #next1 ,#checkListTab").show();
		}
			if(apstatus=="Approved"){
			$('#deletePolicy').hide();
			$('#editPolicy').hide();
			$('#approvePolicy').hide();
		}else{
			$('#deletePolicy').show();
			$('#editPolicy').show();
			$('#approvePolicy').show();
		}
	}else {
			$(".formValidation").remove();
			editPolicy();	
			$("#policyId").html("");
			$("#hiddenId").empty();
			$("#hiddenId").val("");
			$("#catid").val("").trigger('change');
			$("#frequency").val("").trigger('change');
			$("#policyName").val("");
			$("#policybox").html("");
			$("#assetsubcat").val("").trigger('change');
			$("#occRate").val("").trigger('change');
			$("#occSdate").val("");
			$("#occEdate").val("");
			$("#checkListTab").hide();
	}
}
// checklist grid
var activityDefs = [
		{
			checkboxSelection : true,
			sortable : false,
			filter : false,
			resizable : true,
			width : 30,
			pinned : 'left'
		}, {
			headerName : "slNo",
			field : "slNo",
			hide : true,
		}, {
			headerName : "Policy Id",
			field : "policyId",
			hide : true,
			pinned : 'left',
		}, {
			headerName : "Sl No",
			field : "policyName",
			cellStyle : {
				textAlign : 'center'
			},
			/*cellRenderer : function(params) {
				return '<a onclick=editRow("' + params.data.slNo+'") href="javascript:void(0)">'
						+ params.data.policyName + '</a>';
			}*/
		},{
			headerName : "Task Description",
			field : "description",
			width : 240,
			cellStyle : {
				textAlign : 'center'
			},
		}, {
			headerName : "Task Priority",
			field : "priority",
			cellStyle : {
				textAlign : 'center'
			},
		},{
			headerName : "Task Type",
			field : "taskType",
			cellStyle : {
				textAlign : 'center'
			},
		},{
			headerName : "Unit Of Measurement",
			field : "taskUOM",
			cellStyle : {
				textAlign : 'center'
			},
		},{
			headerName : "Minimum Range",
			field : "minRange",
			width : 170,
			cellStyle : {
				textAlign : 'center'
			},
		},{
			headerName : "Maximum Range",
			field : "maxRange",
			width : 170,
			cellStyle : {
				textAlign : 'center'
			},
		}];
var activityOptions = {
	columnDefs : activityDefs,
	rowSelection : 'single', // ensures only one row can be selected
	groupSelectsChildren : false, // prevent group select all
	suppressRowClickSelection : true,
	suppressAggFuncInHeader : true,
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 150
	},
	onSelectionChanged : onSelectionChangeChild,

};
var childid = "";
function onSelectionChangeChild() {
	 var selectedNodes = activityOptions.api.getSelectedNodes();
	 var selectedData = selectedNodes.map(node => node.data);
	 childid= selectedData.map(node => node.slnoId);
	 var childid1= selectedData.map(node => node.slNo);
	var selectedRows = activityOptions.api.getSelectedRows();
	var rowCount = 0;
	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
		$(".br-dis").attr("disabled", false);
		$('#addCheckList').attr('disabled', true);
		$('#editCheckList').attr('disabled', false);
		$('#deleteCheckList').attr('disabled', false);
		var data = policyCheckList.filter(f => String(f.slNo) === String(childid1));
		if(data){
			$("#checkslNo").val(data[0].slNo);
			$("#taskName").val(data[0].policyName);
	    	$("#priority").val(data[0].priority).trigger('change');
	    	$("#description").val(data[0].description);
	    	$("#taskType").val(data[0].taskType).trigger('change');
			$('#taskUOM').val(data[0].taskUOM).trigger('change');
			$('#minRange').val(data[0].minRange);
			$('#maxRange').val(data[0].maxRange);
		}		
	}else{
		$('#addCheckList').attr('disabled', false);
		$(".br-dis").attr("disabled", true);
		$('#editCheckList').attr('disabled', true);
		$('#deleteCheckList').attr('disabled', true);
	//	$('#save').attr('disabled', false);
	}
}
function getDates() {
	var dateFormat = localStorage.getItem("dateFormat");
	$("#DateCalendarAssign").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#asdate').val($(this).val());
	})
	$('#asdate').blur(function() {
		$("#DateCalendarAssign").val($(this).val());
	})
	$("#DateCalendar1").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#sdate').val($(this).val());
	})
	$('#sdate').blur(function() {
		$("#DateCalendar1").val($(this).val());
	})
		$("#DateCalendar2").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#edate').val($(this).val());
		})

		$('#edate').blur(function() {
			$("#DateCalendar2").val($(this).val());
		})
		/*$("#DateCalendar3").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#occSdate').val($(this).val());
		})
		$('#occSdate').blur(function() {
			$("#DateCalendar3").val($(this).val());
		})
		$("#DateCalendar4").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : t,
		}).on("change", function() {
			$('#occEdate').val($(this).val());
		})
		$('#occEdate').blur(function() {
			$("#DateCalendar4").val($(this).val());
		})*/
	    $("#occSdate").datetimepicker({
        format: 'H:i',
        closeOnDateSelect: false,
        timepicker: true,
        datepicker: false,
        step: 1,
    	})
	    const now = new Date();
		now.setMinutes(now.getMinutes() + 1); 
	/*	$("#occEdate").datetimepicker({
		    format: "H:i",
		    closeOnDateSelect: false,
		    timepicker: true,
		    datepicker: false,
		    step: 1,
		});*/
		  $('#occEdate').datetimepicker({
            datepicker: false,
            format: 'H:i',     // 24-hour format (e.g., 14:30)
            step: 15,          // 15-minute steps
            scrollInput: false
        });

        // Show picker when calendar icon is clicked
        $('#DateCalendar4').on('click', function () {
            $('#occEdate').datetimepicker('show');
        });
  		
		$('#occSdate').datetimepicker({
            datepicker: false,
            format: 'H:i',     // 24-hour format (e.g., 14:30)
            step: 15,          // 15-minute steps
            scrollInput: false
        });

        // Show picker when calendar icon is clicked
        $('#DateCalendar3').on('click', function () {
            $('#occSdate').datetimepicker('show');
        });
}
// view functions
function viewPolicy(id=null){
	agGrid.simpleHttpRequest({
		url : 'preventive-maintenance-view'
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData=jsonData.Asset;
		var len = allData.length;
		$('#totalAsset').find('span').html(len);
		gridOptions.api.setRowData(allData);
		if (id != null || id != '') {
			var count = 0;
		    gridOptions.api.forEachNode(function(node) {
			 if (String(node.data.policyId) === String(id)) {
					count ++;					
		           node.setSelected(true);
		        }
		    });
		if(count == 0){		
			var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		}
		} else {
			var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
			if (firstRowNode) {
				firstRowNode.setSelected(true);
			}
		}
	});
}
// search page starts
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
}
function reset() {
	$('#quickFilter').val("");
	onQuickFilterChanged();
}
function cancelBar() {
	var id = document.getElementById("closeKey");
	id.style.display = "block";
	if ($('#quickFilter').val() == null || $('#quickFilter').val() == "") {
		id.style.display = "none";
	}
}
function closeKey(){
	gridOptions.api.setQuickFilter(null);$('#quickFilter').val('');
	document.getElementById('closeKey').style.display='none';
	$('#totalAsset').find('span').html(gridOptions.api.getModel().getRowCount());
}
// search page ends
// edit functions
var assetGSubCat='';
var policyCheckList = "";
function editAssetPolicy(policyId) {
	$('.loader').show();
	var options='<option value="">Select</option>';
	agGrid.simpleHttpRequest({
		url : 'asset-policy-edit?id='+ policyId
	}).then(function(data) {
		var jsonData = JSON.parse(data.body);
		var allData=jsonData.Asset;
		var allData1=allData[0].PolicyList;

	//	console.clear();
		console.log("allData[0] ==>", allData[0]);

		$("#policyId").html(allData[0].policyName);
		$("#policyId1").html(allData[0].policyName);
		$("#hiddenId").val(allData[0].policyid);
		$("#catid").val(allData[0].catid).trigger('change');
		assetGSubCat = allData[0].assetsubcat;
		$("#assetsubcat").val(assetGSubCat).trigger('change');
		$("#frequency").val(allData[0].frequency).trigger('change');
		$("#policyName").val(allData[0].policyName);
		$("#policybox").html(allData[0].policyid);			
		$("#occRate").val(allData[0].occRate).trigger('change');
		$("#occSdate").val(allData[0].occSdate);
		$("#occEdate").val(allData[0].occEdate);
		if(allData[0].frequency=="Daily"){
			$("#occDiv").show();
		}else{
			$("#occDiv").hide();
		}
		var x=allData[0].assetsubcat;
		getsubcategory(allData[0].assetsubcat);
		if(allData1 != null){
			activityOptions.api.setRowData(allData1);
			policyCheckList = allData1 ;
		}else{
			activityOptions.api.setRowData('');
			policyCheckList = ""; 
		}
		console.log("RESPONSE::",allData);
		
		/*if(allData[0].approvests==1){
		 	$("#catid").prop('disabled', true);
			$("#assetsubcat").prop('disabled', true);
			$("#frequency").prop('disabled', true);
			$("#save").hide();
		}else{
			$("#catid").prop('disabled', false);
			$("#assetsubcat").prop('disabled', false);
			$("#frequency").prop('disabled', false);
			$("#save").show();
		}*/
		$("#tdiv").hide();
		$("#searchRowDiv").hide();
		$("#totalAsset").hide();
		$("#ttbtn").hide(); 
	});
	$('.loader').hide();
}
function cancelPolicy(){
	viewPolicy($("#hiddenId").val());	
	$(".formValidation").remove();
	$("#catid, #frequency, #policyName, #occRate, #occSdate, #occEdate, #assetsubcat").prop('disabled', true);
	$("#cancelPolicy, #savePolicy, #DateCalendar3, #DateCalendar4 ").hide();
	$("#editPolicy, #addPolicy, #deletePolicy, #approvePolicy, #next1 ,#checkListTab").show();
}
function cancelCheckList(){
	activityOptions.api.deselectAll();	
	$(".formValidation").remove();
	$("#cancelCheckList, #saveCheckList, #checkListForm").hide();
	$("#editCheckList, #approveCheckList, #activity, #prev1, #checkBtnList").show();
}
function getDets(){
	$(".formValidation").remove();
	$("#catid, #frequency, #policyName, #occRate, #occSdate, #occEdate, #assetsubcat").prop('disabled', true);
	$("#cancelPolicy, #savePolicy").hide();
	$("#editPolicy, #addPolicy, #deletePolicy, #approvePolicy, #next1 ,#checkListTab").show();
	$(".formValidation").remove();
	$("#cancelCheckList, #saveCheckList, #checkListForm").hide();
	$("#editCheckList, #approveCheckList, #activity, #prev1, #checkBtnList").show();
	
	if(apstatus=="Approved"){
			$('#deletePolicy').hide();
			$('#editPolicy').hide();
			$('#approvePolicy').hide();
		}else{
			$('#deletePolicy').show();
			$('#editPolicy').show();
			$('#approvePolicy').show();
		}
//	editAssetPolicy(editId);
}
function editPolicy(){
	$("#catid, #frequency, #policyName, #occRate, #occSdate, #occEdate, #assetsubcat").prop('disabled', false);
	$("#cancelPolicy, #savePolicy, #DateCalendar3, #DateCalendar4").show();
	$("#editPolicy, #addPolicy, #deletePolicy, #approvePolicy, #next1").hide();
}
function editCheckList(){	
	$("#cancelCheckList, #saveCheckList, #checkListForm").show();
//	$("#addCheckList, #deleteCheckList").attr('disabled', true);
	$("#approveCheckList,#activity, #prev1, #checkBtnList").hide();
}
function addPolicy(){
	gridOptions.api.deselectAll();	
}
function addCheckList(){
	activityOptions.api.deselectAll();	
	$(".formValidation").remove();
	editCheckList();
	$('#uomDiv').hide();
	$('#minRangeDiv').hide();
	$('#maxRangeDiv').hide();
	$("#checkListId").html("");
	$("checkslNo").val('');
	$("#taskType").val('').trigger('change');
	$("#taskName").val('');
	$("checkslNo").val('');
	$("#priority").val('').trigger('change');
	$("#description").val('');
	$("#taskType").val('').trigger('change');
	$('#taskUOM').val('').trigger('change');
	$('#minRange').val('');
	$('#maxRange').val('');
	$("#rowEdit").val(null);
}
function approvePolicy(){
	$('#approveModal').modal('show');
}
function deletePolicy(){
	$('#deletePolicymodal').modal('show');
}
function deleteCheckList(){
	$('#deleteCheckListModal').modal('show');
}
function cancelModalBtn() {
	$('.modal').modal('hide');
}
function freqChange(){
	if($("#frequency").val()=="Daily"){
		$('#occDiv').show();
	}else{
		$('#occDiv').hide();
	}
}
function taskTypeChange(){
	if($("#taskType").val()=="Quantitative"){
		$('#uomDiv').show();
		$('#minRangeDiv').show();
		$('#maxRangeDiv').show();
	}else{
		$('#uomDiv').hide();
		$('#minRangeDiv').hide();
		$('#maxRangeDiv').hide();
	}
}
function getsubcategory(id=null){
		var catid=$("#catid").val();
		var options='<option value="">Select</option>';
		console.log("CAT ID:::",catid);
		if(catid!="" && catid!=null){
		agGrid.simpleHttpRequest(
				{
					url : 'asset-policy-subcategory?id='+ catid
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData=jsonData.Asset;
					 allData.forEach(function(rowNode){

						options += '<option value="'+rowNode.subcatid+'">'+rowNode.subcatname+'</option>';
						  });
					 $("#assetsubcat").html(options);
				if(id){
					$("#assetsubcat").val(id);
				}
				});
		}else{
			$("#assetsubcat").html(options);
		}
}		
function savePolicy(){
	$(".formValidation").remove();
	var valid= true;
	var item = {};
//	item.policyid = $("#policyId").html();
	item.policyid = $("#hiddenId").val();
	item.catid = $("#catid").val();
	item.assetsubcat = $("#assetsubcat").val();
	item.frequency = $("#frequency").val();
	item.policyName = $("#policyName").val();
	item.occRate = $("#occRate").val();
	item.occSdate = $("#occSdate").val();
	item.occEdate = $("#occEdate").val();
	item.type = "Asset";
	
	if (item.catid == null || item.catid == "") {
		toastr.error("Category is Required");
		return;
	 }
	if (item.assetsubcat == null || item.assetsubcat == "") {
		toastr.error("Sub-Category is Required");
		return;
	 }
	if (item.frequency == null || item.frequency == "") {
		toastr.error("Frequency is Required");
		return;
	 }
	if (item.policyName == null || item.policyName == "") {
		toastr.error("Name is Required");
		return;
	 }
	if(item.frequency == "Daily"){
		if (item.occRate == null || item.occRate == "") {
			toastr.error("Occurrence Rate is Required");
		return;
		 }
	}
	if (item.occSdate == null || item.occSdate == "") {
		toastr.error("Occurrence Starts From is Required");
		return;
	 }
	
	if (item.occEdate == null || item.occEdate == "") {
		toastr.error("Occurrence Ends On is Required");
		return;
	 }
	console.log("item===",item);
	if (valid) {
		savePolicyData(item);
	}else{		
	}
}
function savePolicyData(datas) {
	console.log("datas===",JSON.stringify(datas))
	$('.loader').show();
	$.ajax({
		type : "POST",
		url : "preventive-maintenance-addPolicy",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(datas),
		success : function(response) {
			$('.loader').hide();
			if (response.code == "success") {
				toastr.success(response.message)
				cancelPolicy();
				gridOptions.api.paginationGoToFirstPage();
			} else {
				toastr.error("Something Went Wrong")
			}
		},
		error : function(data) {
			$('.loader').hide();
		}
	})
}
function saveCheckList(){
	$(".formValidation").remove();
	var valid= true;
	var item = {};
	var id = $("#hiddenId").val();
	item.policyid = id;
	item.taskId = $("#checkslNo").val();
	item.policyName = $("#taskName").val();
    item.priority = $("#priority").val();
    item.description = $("#description").val();
    item.taskType = $("#taskType").val();
    item.childType = "asset";

    if (item.policyName == null || item.policyName == "") {
			toastr.error("Sl No is Required");
			return;
	 }
	if (item.priority == null || item.priority == "") {
		toastr.error("Priority is Required");
			return;
	 }
	if (item.description == null || item.description == "") {
		toastr.error("Description is Required");
			return;
	 }
	if (item.taskType == null || item.taskType == "") {
		toastr.error("Type is Required");
			return;
	 }
	if(item.taskType=="Quantitative"){
		item.taskUOM =$('#taskUOM').val();
		item.minRange =$('#minRange').val();
		item.maxRange =$('#maxRange').val();
		if (item.taskUOM == null || item.taskUOM == "") {
			toastr.error("UOM No is Required");
			return;
		 }
		if (item.minRange == null || item.minRange == "") {
			toastr.error("Min Range is Required");
			return;
		 }
		if (item.maxRange == null || item.maxRange == "") {
			toastr.error("Max Range is Required");
			return;
		 }
	}else if(item.taskType=="Non Quantitative"){
		item.taskUOM ="NA"
		item.minRange ="NA"
		item.maxRange ="NA"
	}
	if (valid) {
		console.log("item child======",item)
		saveCheckListData(item,id);
	}else{		
	}
}
function saveCheckListData(datas,id) {
	console.log("datas===",JSON.stringify(datas))
	$('.loader').show();
	$.ajax({
		type : "POST",
		url : "preventive-maintenance-addCheckList",
		dataType : "json",
		contentType : "application/json",
		data : JSON.stringify(datas),
		success : function(response) {
			$('.loader').hide();
			if (response.code == "success") {
				toastr.success(response.message)
				editAssetPolicy(id);
				cancelCheckList();
			} else {
				toastr.error("Something Went Wrong")
			}
		},
		error : function(data) {
			$('.loader').hide();
		}
	})
}
function deletePolicyOnClick(){
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].policyId;
		$.ajax({
	 		type : "POST",
	 		url : "preventive-maintenance-delete?id=" + id,
	 		success : function(response) {
				if (response.code == "success") {
					toastr.success(response.message)
					viewPolicy($("#hiddenId").val());
					cancelModalBtn();
				} else {
					toastr.error("Something Went Wrong")
				}
	 		},
	 		error : function(data) {
	 			console.log(data)
	 		}
	 	})
}
function deleteCheckListOnClick(){
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].policyId;
	var selectedRows1 = activityOptions.api.getSelectedRows();
	var checkId = selectedRows1[0].slNo;
		$.ajax({
	 		type : "POST",
	 		url : "preventive-maintenance-delete-checkList?id=" + checkId,
	 		success : function(response) {
				if (response.code == "success") {
					toastr.success(response.message)
					editAssetPolicy(id);
					cancelModalBtn();
					$('#editCheckList').attr('disabled', true);
					$('#deleteCheckList').attr('disabled', true);
				$("#addCheckList").prop('disabled', false);
				} else {
					toastr.error("Something Went Wrong")
				}
	 		},
	 		error : function(data) {
	 			console.log(data)
	 		}
	 	})
}
function approveOnclick() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var id = selectedRows[0].policyId;
		if (id) {
			$('.loader').show();
				$.ajax({
		type : "POST",
		url : "preventive-maintenance-approve?id="+ id,
		success : function(response) {
				if (response.code == "success") {
					toastr.success(response.message)
					viewPolicy($("#hiddenId").val());
					cancelModalBtn();
					$('#editCheckList').attr('disabled', true);
					$('#deleteCheckList').attr('disabled', true);
					$("#addCheckList").prop('disabled', false);
				} else {
					toastr.error("Something Went Wrong")
				}
		},
		error : function(data) {
		}
	})
		} else {
			$('.loader').hide();
			$("#alert").modal('show');
			document.getElementById("textId").innerHTML = "Please Select Atleast one Record !";
		}
}
function nextTab(id) {
	console.log(id);
	const tabElement = document.querySelector('#' + id + ' a');
	const tab = new bootstrap.Tab(tabElement);
	tab.show();
}