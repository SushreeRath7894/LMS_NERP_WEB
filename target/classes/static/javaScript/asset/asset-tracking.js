$(document).ready(function() {
	
	var gridDivFloor = document.querySelector('#myGrid');
	new agGrid.Grid(gridDivFloor, gridOptions);
/*	cancelRoom();
	cancelFloorDets();*/
	viewAsset();
});
	var columnDefs = [
		{
			headerCheckboxSelection : false,
			headerCheckboxSelectionFilteredOnly : false,
			checkboxSelection : true,
			width : 10,
			sortable : false,
			filter : false,
			pinned : 'left',
			resizable : true
		},
		{
			headerName : 'Asset ID',
			field : "assetId",
			pinned : 'left',
	//		width: 200,
		}, {
			headerName : 'Asset Name',
			field : "assetname",
	//		width: 200,
		},{
			headerName : 'Asset Type',
			field : "assettype",
	//		width: 200,
		},{
			headerName : 'Asset Category',
			field : "assetcat",
	//		width: 200,

		},{
			headerName : 'Asset Subcategory',
			field : "assetsubcat",
		//	width: 200,

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
		width : 150
	},
	onSelectionChanged : onSelectionChanged,
	paginationAutoPageSize: true,
	pagination: true,
};

var id = "";
var apstatus = "";
var assetname = "";
var purchaseno = "";
var pdate = "";
var assettype = "";
var assignname = "";
var wstatus = "";
function onSelectionChanged() {
	var selectedNodes = gridOptions.api.getSelectedNodes();
	 var selectedData = selectedNodes.map(node => node.data);
	 id= selectedData.map(node => node.assetId);
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = 0;
	
	showInnerContent(id);

	selectedRows.forEach(function(i) {
		rowCount = rowCount + 1;
	});
	if (rowCount > 0) {
	}else {}
}
function onQuickFilterChanged() {
	gridOptions.api.setQuickFilter(document.getElementById('quickFilter').value);
	$('#totalReq').find('span').html(gridOptions.api.getModel().getRowCount());
	gridOptions.api.deselectAll();
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
	    firstRowNode.setSelected(true);
	}
}
function reset() {
	$('#quickFilter').val("");
	onQuickFilterChanged();
	gridOptions.api.deselectAll();
	var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
	if (firstRowNode) {
	    firstRowNode.setSelected(true);
    }
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
// view all asset
function viewAsset(){
	agGrid.simpleHttpRequest({
			url : 'asset-tracking-view'
		}).then(function(data) {
			var jsonData = JSON.parse(data.body);
			var allData=jsonData.Asset;
			var len = allData.length;
			$('#totalAsset').find('span').html(len);
			gridOptions.api.setRowData(allData);
			var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
				if (firstRowNode) {
				    firstRowNode.setSelected(true);
		        }
			
		});
}
function showInnerContent(id){
		agGrid.simpleHttpRequest(
				{
					url : "asset-tracking-asset-details?id="+ id
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData=jsonData.Asset;
					console.log("DATA:::",allData);
					var logbook="";
					var preventive="";
					var corrective="";
					$("#assetIdT").html(allData[0].assetId);
					$("#assetName").html(allData[0].assetname);
					$("#assetType").html(allData[0].assettype);
					$("#assetCategory").html(allData[0].assetcatname);
					$("#assetSubcategory").html(allData[0].assetsubcatname);
					$("#assetPrice").html(allData[0].assetPrice);
					$("#assetIdBox").html(allData[0].assetname);
					$("#assetQr").attr("src", allData[0].qr_codeurl);
					
					var statusAsset=`<span class='bg-green' id="workingStatus">${allData[0].workingstatus}</span> 
									 <span class='bg-yellow' id="approvedStatus">${allData[0].approvests}</span> 
									 <span class='bg-blue' id="assignStatus">${allData[0].assignname}</span>`;
					if(allData[0].logbook!=null){
					
					allData[0].logbook.forEach(function(rowNode){
						if(rowNode.status=='Active'){
							var classColor='bg-success';
						}else if(rowNode.status=='Dissociated'){
							var classColor='bg-danger';
						}else{
							var classColor='bg-warning';
						}
						logbook += `<li class="list-inline-item items-list">
							<div class="px-4">
							<div class="event-date badge ${classColor}" style="display:inline-block; width:175px">${rowNode.assigneddate} : ${rowNode.dissociateddate}</div>
							<h5 class="pt-2">${rowNode.assignedto}</h5>
							<p class="text-muted">(${rowNode.status})</p>
							</div>
						</li>`;
						  });
					}else{
						logbook = `<p class="text-muted">No Activity Found As Of Now</p>`;
					}
					if(allData[0].preventive!=null){
					allData[0].preventive.forEach(function(rowNode){
						if(rowNode.status=='Expired'){
							var classColor='bg-danger';
						}else{
							var classColor='bg-success';
						}
						preventive += `<tr>
							<th style="text-align: center;">${rowNode.allocid}</th>
							<td style="text-align: center;">${rowNode.policyName}</td>
							<td style="text-align: center;">${rowNode.assetname}</td>
							<td style="text-align: center;">${rowNode.frequency}</td>
							<td style="text-align: center;"><div class="event-date badge ${classColor}">${rowNode.status}</div></td>
						</tr>`;
						  });
					}else{
						preventive = `<tr>
											<th colspan="5">No Allocated Policy Records</th>
									   </tr>`;
					}
					if(allData[0].corrective!=null){
					allData[0].corrective.forEach(function(rowNode){
						if(rowNode.status=='RAISED'){
							var classColor='bg-blue';
						}else if(rowNode.status=='INSPECTION'){
							var classColor='bg-yellow';
						}else if(rowNode.status=='ACTION'){
							var classColor='bg-purple';
						}else if(rowNode.status=='RESOLVED'){
							var classColor='bg-green';
						}else{
							var classColor='bg-red';
						}
						corrective += `<div class="col-md-3">
							<div class="card p-3 mb-2">
							<div class="d-flex justify-content-between">
								<div class="d-flex flex-row align-items-center">
									<div class="icon">
										<i class="bi bi-ticket-detailed"></i>
									</div>
									<div class="ms-2 c-details">
										<h6 class="mb-0">${rowNode.ticketid}</h6>
										<span>${rowNode.createdOn}</span>
									</div>
								</div>
								<div class="badge">
									<span class=${classColor}>${rowNode.status}</span>
								</div>
								</div>
							</div>
						</div>`;
						  });
					
					}else{
						corrective = `<div class="col-md-3">
							<div class="card p-3 mb-2">
							<div class="d-flex justify-content-between">
								<div class="d-flex flex-row align-items-center">
									<div class="ms-2 c-details">
										<h6 class="mb-0">No Tickets Raised</h6>
									</div>
								</div>
								</div>
							</div>
						</div>`;
					}			
					
					$("#logbookDiv").html(logbook);
					$("#preventiveDiv").html(preventive);
					$("#correctiveDiv").html(corrective);
					$("#statusDiv").html(statusAsset);
				});
	}