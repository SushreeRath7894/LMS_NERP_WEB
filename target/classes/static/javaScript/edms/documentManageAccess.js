var id = '';
var type = '';
var users = '';
$(document).ready(function() {
	
	/*var gridDiv5 = document.querySelector('#myGrid5');
	new agGrid.Grid(gridDiv5, gridOptions5);
	
	var rowData = [];
	gridOptions5.api.setRowData(rowData);
	
	var gridDiv6 = document.querySelector('#myGrid6');
	new agGrid.Grid(gridDiv6, gridOptions6); 
	
	var rowData = [];
	gridOptions6.api.setRowData(rowData);*/
	$("#usersInGroup").hide();		
	//time date
		$("#fromDateCalenderTime").datetimepicker({
			format : 'd-m-Y',
			closeOnDateSelect : true,
			timepicker : false,
			/*minDate: 0,*/
		}).on("change", function() {
			$('#fromDateTime').val($(this).val());
		})

		$('#fromDateTime').blur(function() {
			$("#fromDateCalenderTime").val($(this).val());
		})
		
 let urlParams = (new URL(document.location)).searchParams;
 id = window.atob(urlParams.get('uid'));
 type = window.atob(urlParams.get('type'));
 
/*agGrid.simpleHttpRequest({
		url : 'manage-access-document-listing?id='+id,
	}).then(function(data) {
		console.log('data',data)
		const jsonString = data.body.map(item => item.replace(/'/g, '"'));
		// Convert to JSON array
		const jsonArray = jsonString.map(item => JSON.parse(item));
		console.log('jsonArray',jsonArray)
		var len = jsonArray.length;
		$('#document').find('span').html(len);
		gridOptions5.api.setRowData(jsonArray);
	});
	*/
	
	/*let accessid = '';
  agGrid.simpleHttpRequest({
		url : 'manage-access-document-accessed-list?id='+id+'&accessid='+accessid,
	}).then(function(data) {
		console.log('data',data)
		console.log(data,'resppppp')
		var resp=JSON.parse(data.body[0]);
		var len = resp.AccessList.length;
		console.log(resp,'resppppp')
		$('#accDoc').find('span').html(len);
		gridOptions6.api.setRowData(resp.AccessList);
	});
	
	if(users != '' || users != null){
	 users = JSON.parse(window.atob(urlParams.get('users')));
	}
console.log(users,'userssssssssssss')
	if(type === 'usergroup'){
		$("#usersInGroup").show();	
	}
	var gridDiv7 = document.querySelector('#myGrid7');
	new agGrid.Grid(gridDiv7, gridOptions7);
	
	var rowData = [];
	gridOptions7.api.setRowData(rowData);
		
	gridOptions7.api.setRowData(users);
});*/


var columnDefs5 = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true

	},
	/*{headerName : "Name",field : "documentName",width:250,},*/
	{headerName : "Document Id",field : "docid",width:250,
	cellRenderer : function(params) {
			console.log(params)
			return '<a id="docid"  href="javascript:void(0)">'
			+ params.data.docid + '</a>';

		 }},
	{headerName : "Type",field : "docControl",width:250,},
	{headerName : "Folder Name",field : "folderName",width:250,},
	{headerName : "File",field : "fileName",width:250,},
	{headerName : "Name",field : "documentName",width:250,},
	{headerName : "Date",field : "date",width:250,},
	{headerName : "Access",field : "access",width:250,
	},];
var gridOptions5 = {
columnDefs : columnDefs5,
rowSelection : 'multiple',
defaultColDef : {
	sortable : true,
	filter : true,
	resizable : true,
	width : 120
},
suppressRowClickSelection: true,
onSelectionChanged : onSelectionChangedUser,
};
var listItem = [];
function onSelectionChangedUser(){
	var item = {};
	var selectedRows = gridOptions5.api.getSelectedRows();
	//var selectedData = selectedNodes.map(node => node.data);
	//docid = selectedData[0].docid;
	//console.log(selectedRows)
	if (selectedRows.length > 0) {
		for(var i=0;i< selectedRows.length;i++){	
			item.docid = selectedRows[i].docid;	
		}
		listItem.push(item);
	}
	console.log(listItem)
	//$("#accessModal").show();
}
function openDocumentTypeModal(){
	let doctype = $("#docType").val();
	let date = $("#fromDateTime").val();
	
	console.log(doctype,'@@@@@@@@@@',date);
	agGrid.simpleHttpRequest({
		url : 'manage-access-document-list-by-type?doctype='+doctype+'&date='+date,
	}).then(function(data) {
		console.log('data',data)
		const jsonString = data.body.map(item => item.replace(/'/g, '"'));
		// Convert to JSON array
		const jsonArray = jsonString.map(item => JSON.parse(item));
		console.log('jsonArray',jsonArray)
		var len = jsonArray.length;
		$('#document').find('span').html(len);
		gridOptions5.api.setRowData(jsonArray);
	});
}
function giveAccess(){
	$("#accessModal").show();
}
function SaveAccess(){
if(type == 'user'){
	let obj = {};
	listItem.forEach(function(obj) {
		
		obj.employeeId = id;
		obj.accessType = type;
		obj.document =obj.docid;
		obj.readStatus = $("#read").is(":checked");
		obj.writeStatus = $("#write").is(":checked");
		obj.deleteStatus = $("#delete").is(":checked");
		obj.remarks = $("#remarks").val();
	});
	console.log('listItem accesssave',listItem)
	$.ajax({
			type : "POST",
			url : "manage-access-save-user-access",
			contentType : "application/json",
			data : JSON.stringify(listItem),
			success : function(response) {
		   if (response.message == "Success") {
			$("#accessModal").hide();
			agGrid.simpleHttpRequest({
				url : 'manage-access-document-listing?id='+id,
			}).then(function(data) {
				console.log('data',data)
				const jsonString = data.body.map(item => item.replace(/'/g, '"'));
				// Convert to JSON array
				const jsonArray = jsonString.map(item => JSON.parse(item));
				console.log('jsonArray',jsonArray)
				var len = jsonArray.length;
				$('#document').find('span').html(len);
				gridOptions5.api.setRowData(jsonArray);
			});
			let accessid = '';
			 agGrid.simpleHttpRequest({
				url : 'manage-access-document-accessed-list?id='+id+'&accessid='+accessid,
			}).then(function(data) {
				console.log('data',data)
				console.log(data,'resppppp')
				var resp=JSON.parse(data.body[0]);
				var len = resp.AccessList.length;
				console.log(resp,'resppppp')
				$('#accDoc').find('span').html(len);
				gridOptions6.api.setRowData(resp.AccessList);
			});
			}
			
			}
		});	
	}else if(type == 'usergroup'){
		const result = [];

		for (const user of users) {
		    for (const document of listItem) {
		        result.push({
		            "employeeId": user.employeeId,
		            "empName": user.empName,
		            "document": document.docid,
		            "accessType":type,
		            "groupId": id,
		            "readStatus":$("#read").is(":checked"),
					"writeStatus":$("#write").is(":checked"),
					"deleteStatus":$("#delete").is(":checked"),
					"remarks":$("#remarks").val()
		        });
		    }
		}
		//return false;
	$.ajax({
			type : "POST",
			url : "manage-access-save-user-access",
			contentType : "application/json",
			data : JSON.stringify(result),
			success : function(response) {
		   if (response.message == "Success") {
				$("#accessModal").hide();
				agGrid.simpleHttpRequest({
					url : 'manage-access-document-listing?id='+id,
				}).then(function(data) {
					console.log('data',data)
					const jsonString = data.body.map(item => item.replace(/'/g, '"'));
					// Convert to JSON array
					const jsonArray = jsonString.map(item => JSON.parse(item));
					console.log('jsonArray',jsonArray)
					var len = jsonArray.length;
					$('#document').find('span').html(len);
					gridOptions5.api.setRowData(jsonArray);
				});
				let accessid = '';
				 agGrid.simpleHttpRequest({
					url : 'manage-access-document-accessed-list?id='+id+'&accessid='+accessid,
				}).then(function(data) {
					console.log('data',data)
					console.log(data,'resppppp')
					var resp=JSON.parse(data.body[0]);
					var len = resp.AccessList.length;
					console.log(resp,'resppppp')
					$('#accDoc').find('span').html(len);
					gridOptions6.api.setRowData(resp.AccessList);
				});
			}
			}
		})
console.log(result);
	
	}
}
function CancelAccess(){
	$("#accessModal").hide();
}
/*function OpenAccessDocument(){
	$("#userAccessControl").hide();
	$("#accessedDoc").show();
}*/
	var columnDefs6 = [
		{
			headerCheckboxSelection : true,
			headerCheckboxSelectionFilteredOnly : true,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			resizable : true

		},
		{headerName : "Access ID",field : "accessId",width:200,
		cellRenderer : function(params) {
			console.log(params)
			return '<a id="accessId" onclick=editDocument("'
			+params.data.accessId+'") href="javascript:void(0)">'
			+ params.data.accessId + '</a>';}},
		{headerName : "Employee Id",field : "employeeId",width:200,}, 
		{headerName : "Access Type",field : "accessType",width:200,},
		{headerName : "Document",field : "documentName",width:200,}, 
		{headerName : "Read",field : "readStatus",width:200,},
		{headerName : "Write",field : "writeStatus",width:200,},
		{headerName : "Delete",field : "deleteStatus",width:200,},
		{headerName : 'Created Date',field : "createdDate",width:200,},];
var gridOptions6 = {
	columnDefs : columnDefs6,
	rowSelection : 'single',
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	suppressRowClickSelection: true,
//	onSelectionChanged : onSelectionChanged,
};
//Open Nav for Exit Management
function openNav() {

	document.getElementById("mySidenav").style.cssText = "width: 25%; position: absolute; right:-10px; overflow: hidden; height:auto;";
	document.getElementById("accessedDoc").style.width = "75%";
	$("#cancel").show();
	$("#save").show();
	$('#delete').hide();
}
function closeNav() {
		document.getElementById("mySidenav").style.width = "0";
		document.getElementById("accessedDoc").style.width = "100%";
	}
function editDocument(accessid){
	openNav();
	$.ajax({
		type: "GET",
		url: 'manage-access-document-accessed-list?id='+id+'&accessid='+accessid,
		success: function(response) {
			if (response.message == "Success") {
			 var resp=JSON.parse(response.body[0]).AccessList;
			 
			 console.log(resp,'@@@@@@@@@@@@@RESP')
			    $("#accessId").val(resp[0].accessId);
				$("#remarksDet").val(resp[0].remarks);
				if(resp[0].readStatus === 'true'){
					$('#readStatus').prop('checked', true);
				}else{
					$('#readStatus').prop('checked', false);
				}
				if(resp[0].writeStatus === 'true'){
					$('#writeStatus').prop('checked', true);
				}else{
					$('#writeStatus').prop('checked', false);
				}
				if(resp[0].deleteStatus === 'true'){
					$('#deleteStatus').prop('checked', true);
				}else{
					$('#deleteStatus').prop('checked', false);
				}
			}
			
		}
	})
}
function saveAccessedDocument(){
	let obj = {};
	obj.accessType = type;
	obj.accessId= $("#accessId").val();
	obj.readStatus = $("#readStatus").is(":checked");
	obj.writeStatus = $("#writeStatus").is(":checked");
	obj.deleteStatus = $("#deleteStatus").is(":checked");
	obj.remarks = $("#remarksDet").val();
	let data = [];
	data.push(obj);
	$.ajax({
			type : "POST",
			url : "manage-access-save-user-access",
			contentType : "application/json",
			data : JSON.stringify(data),
			success : function(response) {
				closeNav();
				let accessid = '';
				agGrid.simpleHttpRequest({
						url : 'manage-access-document-accessed-list?id='+id+'&accessid='+accessid,
					}).then(function(data) {
						console.log('data',data)
						console.log(data,'resppppp')
						var resp=JSON.parse(data.body[0]);
						var len = resp.AccessList.length;
						console.log(resp,'resppppp')
						$('#userSec').find('span').html(len);
						gridOptions6.api.setRowData(resp.AccessList);
					});	
			}
		});	
}
var columnDefs7= [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true

	},
	/*{headerName : "Name",field : "documentName",width:250,},*/
	{headerName : "User Id",field : "employeeId",width:600,
	cellRenderer : function(params) {
			console.log(params)
			return '<a id="employeeId"  href="javascript:void(0)">'
			+ params.data.employeeId + '</a>';
	}},
	{headerName : "Name",field : "empName",width:600,},
	];
	var gridOptions7 = {
	columnDefs : columnDefs7,
	rowSelection : 'single',
	defaultColDef : {
		sortable : true,
		filter : true,
		resizable : true,
		width : 120
	},
	suppressRowClickSelection: true,
	//onSelectionChanged : onSelectionChangedUser,
	};