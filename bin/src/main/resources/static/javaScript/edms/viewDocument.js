$(document).ready(function() {
	
	var gridDiv = document.querySelector('#myGrid');
	new agGrid.Grid(gridDiv, gridOptions);
	
	var rowData = [];
	gridOptions.api.setRowData(rowData);
	
	agGrid.simpleHttpRequest({ 
		url : 'view-document-user-listing',
	}).then(function(data) {
		console.log('data',data)
		var resp=JSON.parse(data.body[0]);
		var len = resp.UserDocumentList.length;
		console.log(len,'resppppp')
		$('#document').find('span').html(len);
		gridOptions.api.setRowData(resp.UserDocumentList);
	});
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
	/*{headerName : "Name",field : "documentName",width:250,},*/
	{headerName : "Document Id",field : "documentName",width:250,
	cellRenderer : function(params) {
			console.log(params)
			return '<a id="document"  href="javascript:void(0)">'
			+ params.data.documentName + '</a>';

		 }},
	{headerName : "Type",field : "accessType",width:250,},
	{headerName : "Read Access",field : "readStatus",width:250,},
	{headerName : "Write Access",field : "writeStatus",width:250,},
	{headerName : "Delete Access",field : "deleteStatus",width:250,},
	{headerName : "Remarks",field : "remarks",width:250,},
	{headerName : "Created Date",field : "createdDate",width:250,},
	,];
var gridOptions = {
columnDefs : columnDefs,
rowSelection : 'multiple',
defaultColDef : {
	sortable : true,
	filter : true,
	resizable : true,
	width : 120
},
suppressRowClickSelection: true,
};