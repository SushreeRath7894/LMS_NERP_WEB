let type = '';
$(document).ready(function () {
	
	const urlParams = new URLSearchParams(window.location.search);
		type = urlParams.get('id');
		
		if(type == null || type == 'null') {
			type = '';
		}
	
   var gridDiv = document.querySelector('#myGrid');
   new agGrid.Grid(gridDiv, gridOptions);
   var data = []
   gridOptions.api.setRowData(data);
	viewData();
	 cancelNotice();
   CKEDITOR.replace('noticeContent', {
      height: 180,
      removePlugins: 'wsc',
      scayt_autoStartup: true,
      scayt_maxSuggestions: 3,
      autoParagraph: false,
   });
	$('#docTbl').on('click', '.rmv1', function() {
/*		openDeleteConfirm();
*/
		var value = $(this).parent("div").attr("id");
		$("#dltValue").val(value);
	});
   
	var dateFormat = localStorage.getItem("dateFormat");
	$("#dateCalendar").datetimepicker({
		format : dateFormat,
		closeOnDateSelect : true,
		timepicker : false,
	}).on("change", function() {
		$('#publishDate').val($(this).val());
	})
	$('#publishDate').blur(function() {
		$("#dateCalendar").val($(this).val());
	})
	
		$("#quickFilter").on("keydown", function(event) {
		if (event.key === "Enter" || event.which === 13) {
			event.preventDefault();
			quickFilterGrid(gridOptions);
		}
	});
});
	function onQuickFilterChanged() {
		if(document.getElementById('quickFilter').value) {
			gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
			gridOptions.api.getDisplayedRowAtIndex(0).setSelected(true);
		}
		

	}
function reset(){
	let data = $("#quickFilter").val('');
	if(data!=null && data!=""){
		$("#quickFilter").val('');
		gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
			gridOptions.api.getDisplayedRowAtIndex(0).setSelected(true);
	}
}


var columnDefs = [
	{
	   headerCheckboxSelection: false,
	   headerCheckboxSelectionFilteredOnly: true,
	   checkboxSelection: true,
	   width: 10,
	   sortable: false,
	   filter: false,
	   resizable: true,
	   pinned: 'left',
	},{
		headerName: 'Notice ID',
	   field: "noticeId",
	   width: 160,
	   pinned: 'left',
/*	   cellRenderer : function(params) {
			if(params.data.type==="SUBMIT"){
				return params.data.noticeId;
			}else{
				 return '<a id="noticeId" onclick=openDiv("' + params.data.noticeId +
		         '") href="javascript:void(0)"><i class="bi bi-pencil-square"></i>  ' +
		         params.data.noticeId + '</a>';
			}
		}*/
	},{
	   headerName: 'To',
	   field: "publishedTo",
	   width: 60,
	},/*{
		headerName : 'Notice Content',
		field : "noticeContent",
		width: 150,
		cellStyle : {
			textAlign : 'center'
		},cellRenderer : function(params) {
			let encodedLogData = encodeURIComponent(JSON.stringify(params.data));
			 return '<a id="id" onclick="openOverViewModal(decodeURIComponent(\'' + encodedLogData.replace(/'/g, "\\'") + '\'))" href="javascript:void(0)">'
		        + '<i class="bi bi-eye-fill" style="font-size: 10px;"> View </i>' + '</a>';
		}
	},*/{
	   headerName: 'Notice Subject',
	   field: "noticeSubject",
	    width: 220,
/*	},{
	   headerName: 'Preview',
	   field: "noticeAttachment",
	   width: 160,
	   cellStyle: {
	      textAlign: 'center'
	   },
	   cellRenderer: function (params) {
		   if(params.data.noticeId){
			    var s = "";
		        s = ' <a href="#" class="grn-btn" onclick="previewAsPDF(\'' + params.data.noticeId + '\')"><i class="fas fa-file-pdf"></i> Preview </a>';
		    	return s;
			}else{
				return '';
			}
	   }, */
	}, {
	    headerName: 'Published Date',
		field: "publishDate",
		 width: 100,
	},{
	   headerName: 'Published By',
	   field: "publishedBy",
	    width: 130,
	},{
        headerName: 'Status',
		field: "type",
		width: 100,
		
	},{
	    headerName: 'Created On',
		field: "publishedOn",
		width: 100,
	}];
const gridOptions = {
   columnDefs: columnDefs,
   defaultColDef: {
      sortable: true,
      filter: true,
      resizable: true,
      width: 190,
      height: 10
   },
   rowSelection: 'single',
   suppressRowClickSelection: true,
   pagination : true,
   paginationPageSize: 15,
   onSelectionChanged: onSelectionChanged,
};
function onSelectionChanged() {
	var selectedRows = gridOptions.api.getSelectedRows();
	var rowCount = selectedRows.length;
	var notId = selectedRows.map(node => node.noticeId);
   if (rowCount > 0) {
	editNoticeDetails(notId);
	cancelNotice();
      var status = selectedRows[0].type;
      if (status === "DRAFT") {
         $('#deleteNotice').show();
         $('#editNotice').show();
         $('#publishNotice').show();
         $('#downloadNotice').show();
      } else {
         $('#deleteNotice').hide();
         $('#editNotice').hide();
         $('#publishNotice').hide();
         $('#downloadNotice').show();
      }
   } else {
         $('#deleteNotice').hide();
         $('#editNotice').hide();
         $('#publishNotice').show();
         $('#downloadNotice').hide();
         
         $("#noticeId1").html('');
         $("#noticeSubject").val('');
        CKEDITOR.instances['noticeContent'].setData('');
        
   }
}