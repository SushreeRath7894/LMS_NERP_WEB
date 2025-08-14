/*get current month and year*/
const d = new Date();
let month = d.getMonth() + 1;
let year = d.getFullYear();

let reviewAmount = [], reviewSalesAmount= [],reviewAmount3B = [], b2b, b2cs, b2cl, b2bpur, b2cspur, b2clpur, b2b3b, b2cs3b, b2cl3b;
 
 
/* Show Formatted Amount as per Currency */
function getFormattedAmount(amountVal){
	const formattedAmount = amountVal.toLocaleString('en-IN', {
	  style: 'currency',
	  currency: 'INR',
	  minimumFractionDigits: 2,
	  maximumFractionDigits: 2
	});
	
	return formattedAmount;
}

/*Customer and Business Type Filter*/
function checkB2C(data){
    return data.gstin === "NA";
}
function checkB2B(data){
    return data.gstin !== "NA";
}
function checkB2CL(data){
    return data.customerType === "B2CL"
}
function checkB2CS(data){
    return data.customerType === "B2CS"
}

/* Empty Aall Table Data */
function emptyTables(){
	gridOptions.api.setRowData();
    gridOptionsB2B.api.setRowData();
    gridOptionsB2CS.api.setRowData();
    gridOptionsB2CL.api.setRowData();
    gridOptionsHSNsales.api.setRowData();
    
    gridOptionsPurch.api.setRowData();
	gridOptionsPurchB2B.api.setRowData();
    gridOptionsPurchB2CS.api.setRowData();
    gridOptionsPurchB2CL.api.setRowData();
    gridOptionsHSNpurchase.api.setRowData();
    
    
    gridOptions3B.api.setRowData();
	gridOptions3BB2B.api.setRowData();
    gridOptions3BB2CS.api.setRowData();
    gridOptions3BB2CL.api.setRowData();
    gridOptions3BHSN.api.setRowData();
}
   
/*month onchange*/
function getMonthWiseData(selectedMonth){
	
	
	emptyTables()
    var selectedYear = $("#year").val();
    getGstrSalesData(selectedYear, selectedMonth);
	getHsnSalesData(selectedYear, selectedMonth);
	
	getGstrPurchaseData(selectedYear, selectedMonth);
	getHsnPurchaseData(selectedYear, selectedMonth);
	
	getGstrPurchase3BData(selectedYear, selectedMonth);
	getHsn3BData(selectedYear, selectedMonth);
}

/*Year onchange*/
function getYearWiseData(selectedYear){
	emptyTables()
    var selectedMonth = $("#month").val();
    getGstrSalesData(selectedYear, selectedMonth);
	getHsnSalesData(selectedYear, selectedMonth);
	
	getGstrPurchaseData(selectedYear, selectedMonth);
	getHsnPurchaseData(selectedYear, selectedMonth);
	
	getGstrPurchase3BData(selectedYear, selectedMonth);
	getHsn3BData(selectedYear, selectedMonth)
}



/*
* GSTR-1 Sales Section Strats
*/

/* Api for get GSTR Sales DATA*/
let allJsonArrayData = [];
let myGSTNo = '';
function getGstrSalesData(selectedYear, selectedMonth){
	
	$(".loader").show();
	agGrid.simpleHttpRequest({
    	url : "prepare-gst-list-sales?month=" + selectedMonth + "&year=" + selectedYear,
    	contentType : "application/json"

    }).then(function(data) {
        //var len = data.length;
        //$('#totalCandidate').find('span').html(len);
        let resp = data;
        
        
        resp = JSON.parse(resp.body[0]);
 
        let gstAll = JSON.parse(resp.GstReturn);
		myGSTNo = resp.gstNo;

        if(gstAll){

            let b2c = gstAll.filter(checkB2C);
             b2b = gstAll.filter(checkB2B);

            b2cl = b2c.filter(checkB2CL);
            b2cs = b2c.filter(checkB2CS);

            gridOptions.api.setRowData(gstAll);//Setting data on Sales invoice grid table
            gridOptionsB2B.api.setRowData(b2b);//Setting data on B2B grid table
            gridOptionsB2CS.api.setRowData(b2cs);//Setting data on B2CS grid table
            gridOptionsB2CL.api.setRowData(b2cl);//Setting data on B2CL grid table

            reviewSalesAmount = JSON. parse(resp.allAmountDtls);
            
            getReviewSales();
        } else{
            $(".reviewAllAmountData").html(0);
            gridOptions.api.setRowData();
            gridOptionsB2B.api.setRowData();
            gridOptionsB2CS.api.setRowData();
            gridOptionsB2CL.api.setRowData();
            reviewSalesAmount = null;
        }
        
        $(".loader").hide();
    });
}

function getReviewSales(){
	var totalAmountDtls = reviewSalesAmount;
	
	if(totalAmountDtls != null && totalAmountDtls != ""){
		$("#taxAmountAll").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalTaxableAmt)));
	    $("#igstAll").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalIGST)));
	    $("#cgstAll").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalCGST)));
	    $("#sgstAll").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalSGST)));
	} else{
		$("#taxAmountAll").html(0);
	    $("#igstAll").html(0);
	    $("#cgstAll").html(0);
	    $("#sgstAll").html(0);
	}
    

    var taxAmount = 0, igst = 0, cgst = 0, sgst = 0;

    if(b2b){
        taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
        b2b.forEach(function(b2b){
            taxAmount = taxAmount + b2b.total;
            igst = igst + b2b.igst;
            cgst = cgst + b2b.cgst;
            sgst = sgst + b2b.sgst;
        });
        $("#taxAmountb2b").html(getFormattedAmount(taxAmount));
        $("#igstb2b").html(getFormattedAmount(igst));
        $("#cgstb2b").html(getFormattedAmount(cgst));
        $("#sgstb2b").html(getFormattedAmount(sgst));
    } else{
        $("#taxAmountb2b").html(0);
        $("#igstb2b").html(0);
        $("#cgstb2b").html(0);
        $("#sgstb2b").html(0);
    }

    if(b2cs){
        taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
        b2cs.forEach(function(b2cs){
            taxAmount = taxAmount + b2cs.total;
            igst = igst + b2cs.igst;
            cgst = cgst + b2cs.cgst;
            sgst = sgst + b2cs.sgst;
        });
        $("#taxAmountb2cs").html(getFormattedAmount(taxAmount));
        $("#igstb2cs").html(getFormattedAmount(igst));
        $("#cgstb2cs").html(getFormattedAmount(cgst));
        $("#sgstb2cs").html(getFormattedAmount(sgst));
    } else{
       $("#taxAmountb2cs").html(0);
        $("#igstb2cs").html(0);
        $("#cgstb2cs").html(0);
        $("#sgstb2cs").html(0);
    }

    if(b2cl){
        taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
        b2cl.forEach(function(b2cl){
            taxAmount = taxAmount + b2cl.total;
            igst = igst + b2cl.igst;
            cgst = cgst + b2cl.cgst;
            sgst = sgst + b2cl.sgst;
        });
        $("#taxAmountb2cl").html(getFormattedAmount(taxAmount));
        $("#igstb2cl").html(getFormattedAmount(igst));
        $("#cgstb2cl").html(getFormattedAmount(cgst));
        $("#sgstb2cl").html(getFormattedAmount(sgst));
    } else{
        $("#taxAmountb2cl").html(0);
        $("#igstb2cl").html(0);
        $("#cgstb2cl").html(0);
        $("#sgstb2cl").html(0);
    }
}

/*Sales Invoice HSN Api*/
function getHsnSalesData(selectedYear, selectedMonth){
	$(".loader").show();
	agGrid.simpleHttpRequest({
    	url : "prepare-gst-list-sales-hsn?month=" + selectedMonth + "&year=" + selectedYear,
    	contentType : "application/json"
    }).then(function(data) {
    	
        if(data.code == "Success"){
			console.log("INSIDE getHsnSales FUNCTION: ", JSON. parse(data.body));
	        let resp = JSON. parse(JSON. parse(data.body[0]).salesItem);
	        gridOptionsHSNsales.api.setRowData(resp);
		}
        $(".loader").hide();
    });
}









/*Item wise invoice data table column definition*/
var columnDefs = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	}, {
		headerName : "GSTIN",
		field : "gstin",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Name",
		field : "customerName",
		width : 250,
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Invoice No",
		field : "invoiceNo",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Date",
		field : "date",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Place of Supply",
		field : "placeOfSupply",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	},  /*{
		headerName : "HSN Code",
		field : "hsnNo"
	}, {
		headerName : "Inter/Intra State",
		field : "IntState"
	},*/ {
		headerName : "Taxable Amount",
		field : "taxableAmt",
		width:180,
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.taxableAmt).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "Rate",
		field : "rate",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.rate).toFixed(2);
  			return '<div>' +
  			       formattedValue +
  			       '</div>';
  		},
	}, {
		headerName : "CGST",
		field : "cgst",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
		cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.cgst).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "SGST",
		field : "sgst",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.sgst).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "IGST",
		field : "igst",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.igst).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
        headerName : "CESS",
        field : "cess",
        cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
    }, {
         headerName : "Total",
         field : "total",
         cellStyle : {
 			textAlign : 'right',
 			fontFamily: 'Montserrat, sans-serif',
             fontSize: 'smaller'
 		},
        cellRenderer: function(params) {
        	const formattedValue = parseFloat(params.data.total).toFixed(2);
    		return '<div>' +
    			amountFormatter(formattedValue) +
    		'</div>';
    	},
     }/*, {
         headerName : "Action",
         field : "action",
         cellStyle : {
                 textAlign : 'center'
         },
         cellRenderer : function(params) {
            return '<a id="deleteInvoice" onclick=deleteInvoice("'
                    + params.data.invoiceNo
                    + '","' + params.data.itemId + '") href="javascript:void(0)"><i class="fa fa-trash" aria-hidden="true"></i></a>';
        }
     }*/
];

/*Item wise invoice data table grid option definition*/
//Grid Options for All Sales Invoice table
var gridOptions = {
    columnDefs : columnDefs,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};
//Grid Options for B2B Sales Invoice table
var gridOptionsB2B = {
    columnDefs : columnDefs,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};
//Grid Options for B2CS Sales Invoice table
var gridOptionsB2CS = {
    columnDefs : columnDefs,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};
//Grid Options for B2CL Sales Invoice table
var gridOptionsB2CL = {
    columnDefs : columnDefs,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};



/* HSN Data table column defination (Sales & Purchase) */
var columnDefsHSNsales = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	},
	{
		headerName : "Invoice No",
		field : "invoieId",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "HSN Code",
		field : "hsnCode",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "SKU",
		field : "sku",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Item Name",
		field : "itemName",
		width : 300,
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Quantity",
		field : "quantity",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.quantity).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "Taxable Amount",
		field : "taxableValue",
		width:180,
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.taxableValue).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "Rate",
		field : "gstRate",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.gstRate).toFixed(2);
  			return '<div>' +
  			       formattedValue +
  			       '</div>';
  		},
	}, {
		headerName : "CGST",
		field : "cgst",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
		cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.cgst).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "SGST",
		field : "sgst",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.cgst).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "IGST",
		field : "igst",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.igst).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
         headerName : "Total",
         field : "totalValue",
         cellStyle : {
 			textAlign : 'right',
 			fontFamily: 'Montserrat, sans-serif',
             fontSize: 'smaller'
 		},
        cellRenderer: function(params) {
    			const formattedValue = parseFloat(params.data.totalValue).toFixed(2);
    			return '<div>' +
    			       amountFormatter(formattedValue) +
    			       '</div>';
        },
     }, {
        headerName : "CESS",
        field : "cess",
        cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
    }
];

/* HSN Sales Grid Options*/

var gridOptionsHSNsales = {
    columnDefs : columnDefsHSNsales,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};


/*setup the grid after the page has finished loading*/
//For All Sales Invoice Table
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv = document.querySelector('#gridAllInvoice');
	new agGrid.Grid(gridDiv, gridOptions);
});
//For B2B Sales Invoice Table
document.addEventListener('DOMContentLoaded', function() {
	var gridDivB2B = document.querySelector('#gridB2BInvoice');
	new agGrid.Grid(gridDivB2B, gridOptionsB2B);
});
//For B2CS Sales Invoice Table
document.addEventListener('DOMContentLoaded', function() {
	var gridDivB2CS = document.querySelector('#gridB2CSInvoice');
	new agGrid.Grid(gridDivB2CS, gridOptionsB2CS);
});
//For B2CL Sales Invoice Table
document.addEventListener('DOMContentLoaded', function() {
	var gridDivB2CL = document.querySelector('#gridB2CLInvoice');
	new agGrid.Grid(gridDivB2CL, gridOptionsB2CL);
});
//For HSN Sales Item table
document.addEventListener('DOMContentLoaded', function() {
	var gridDivHSNsales = document.querySelector('#gridHSNInvoiceSales');
	new agGrid.Grid(gridDivHSNsales, gridOptionsHSNsales);
});


/**
 * GSTR-1 CSV Export
 */
function getAllSalesInvoice(){
	/*let allColumns = gridOptions.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptions.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		    });*/
	getB2BSalesInvoice();
	getB2CSSalesInvoice();
	getB2CLSalesInvoice();
	getHSNSalesInvoice();
}
function getB2BSalesInvoice(){
	let allColumns = gridOptionsB2B.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsB2B.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-sals-B2B-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getB2CSSalesInvoice(){
	let allColumns = gridOptionsB2CS.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsB2CS.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-sals-B2CS-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getB2CLSalesInvoice(){
	let allColumns = gridOptionsB2CL.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsB2CL.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-sals-B2CL-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getHSNSalesInvoice(){
	let allColumns = gridOptionsHSNsales.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsHSNsales.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-sals-HSN-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}

/*
* GSTR-1 Sales Section Ends
*/



/*
* GSTR-2 Purchase Section Strats
*/


/*GSTR Purchase API*/

function getGstrPurchaseData(selectedYear, selectedMonth){
	$(".loader").show();
	b2b = [], b2cs = [], b2cl = [];
    agGrid.simpleHttpRequest({
    	url : "prepare-gst-list-purchase?month=" + selectedMonth + "&year=" + selectedYear,
    	contentType : "application/json"
    }).then(function(data) {
		
		if(data.code == "Success"){
			console.log("INSIDEMONTH ONCHANGE FUNCTION: ", JSON. parse(JSON. parse(data.body[0]).GstPurchesreturn));
			let gstPurData = JSON. parse(data.body[0]).GstPurchesreturn;
	        let resp = JSON. parse(gstPurData);
	        if(resp != null && resp != ""){
				gridOptionsPurch.api.setRowData(resp);   
		        let b2c = resp.filter(checkB2C);
		        b2bpur = resp.filter(checkB2B);
		
		        b2clpur = b2c.filter(checkB2CL);
		        b2cspur = b2c.filter(checkB2CS);
		        
		        gridOptionsPurchB2B.api.setRowData(b2bpur);
		        gridOptionsPurchB2CS.api.setRowData(b2cspur);
		        gridOptionsPurchB2CL.api.setRowData(b2clpur);
		        
		       /* console.log("Purchase........",b2b)
		        console.log(b2c)
		        console.log(b2cl)
		        console.log(b2cs)*/
		        
		        reviewAmount = JSON. parse(JSON. parse(data.body[0]).allAmountDtls);
		        reviewPurchase()
			}else{
				gridOptionsPurch.api.setRowData();
				gridOptionsPurchB2B.api.setRowData();
		        gridOptionsPurchB2CS.api.setRowData();
		        gridOptionsPurchB2CL.api.setRowData();
		        gridOptionsHSNpurchase.api.setRowData();
		        reviewAmount = null
			}
	        
		}
	 	$(".loader").hide();
    });
}

function reviewPurchase(){
	//alert("HHH")
	var totalAmountDtls = reviewAmount;
	
			if(totalAmountDtls != null && totalAmountDtls != ""){
				$("#taxAmountAllPur").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalTaxableAmt)));
	            $("#igstAllPur").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalIGST)));
	            $("#cgstAllPur").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalCGST)));
	            $("#sgstAllPur").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalSGST)));
			} else{``
				$("#taxAmountAllPur").html(0);
	            $("#igstAllPur").html(0);
	            $("#cgstAllPur").html(0);
	            $("#sgstAllPur").html(0);
			}
            

            var taxAmount = 0, igst = 0, cgst = 0, sgst = 0;

            if(b2bpur){
                taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
                b2bpur.forEach(function(b2b){
                    taxAmount = taxAmount + b2b.total;
                    igst = igst + b2b.igst;
                    cgst = cgst + b2b.cgst;
                    sgst = sgst + b2b.sgst;
                });
                $("#taxAmountb2bPur").html(getFormattedAmount(taxAmount));
                $("#igstb2bPur").html(getFormattedAmount(igst));
                $("#cgstb2bPur").html(getFormattedAmount(cgst));
                $("#sgstb2bPur").html(getFormattedAmount(sgst));
            } else{
                $("#taxAmountb2bPur").html(0);
                $("#igstb2bPur").html(0);
                $("#cgstb2bPur").html(0);
                $("#sgstb2bPur").html(0);
            }

            if(b2cspur){
                taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
                b2cspur.forEach(function(b2cs){
                    taxAmount = taxAmount + b2cs.total;
                    igst = igst + b2cs.igst;
                    cgst = cgst + b2cs.cgst;
                    sgst = sgst + b2cs.sgst;
                });
                $("#taxAmountb2csPur").html(getFormattedAmount(taxAmount));
                $("#igstb2csPur").html(getFormattedAmount(igst));
                $("#cgstb2csPur").html(getFormattedAmount(cgst));
                $("#sgstb2csPur").html(getFormattedAmount(sgst));
            } else{
                $("#taxAmountb2csPur").html(0);
                $("#igstb2csPur").html(0);
                $("#cgstb2csPur").html(0);
                $("#sgstb2csPur").html(0);
            }

            if(b2clpur){
                taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
                b2clpur.forEach(function(b2cl){
                    taxAmount = taxAmount + b2cl.total;
                    igst = igst + b2cl.igst;
                    cgst = cgst + b2cl.cgst;
                    sgst = sgst + b2cl.sgst;
                });
                $("#taxAmountb2clPur").html(getFormattedAmount(taxAmount));
                $("#igstb2clPur").html(getFormattedAmount(igst));
                $("#cgstb2clPur").html(getFormattedAmount(cgst));
                $("#sgstb2clPur").html(getFormattedAmount(sgst));
            } else{
                $("#taxAmountb2clPur").html(0);
                $("#igstb2clPur").html(0);
                $("#cgstb2clPur").html(0);
                $("#sgstb2clPur").html(0);
            }
}


/*Purchase Invoice HSN Api*/
function getHsnPurchaseData(selectedYear, selectedMonth){
	$(".loader").show();
	agGrid.simpleHttpRequest({
    	url : "prepare-gst-list-purchase-hsn?month=" + selectedMonth + "&year=" + selectedYear,
    	contentType : "application/json"
    }).then(function(data) {
		if(data.code == "Success"){
			
			let code = JSON.parse(data.body[0]);
	        let resp = JSON.parse(code.purchaseItem);
	        console.log("AKMMMM",resp)
	        gridOptionsHSNpurchase.api.setRowData(resp);
		}
		$(".loader").hide();
        
    });
}


/*Item wise purchase data table column definition For GSTR-2A(Purchase)*/
var columnDefsPurchase = [
	{
		headerCheckboxSelection : true,
		headerCheckboxSelectionFilteredOnly : true,
		checkboxSelection : true,
		width : 10,
		sortable : false,
		filter : false,
		resizable : true
	},
	{
		headerName : "Vendor Gstin",
		field : "gstin",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Vendor Name",
		field : "name",
		width : 250 ,
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Invoice No",
		field : "invoiceNo",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Date",
		field : "date",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Place of Supply",
		field : "placeOfSupply",
		cellStyle : {
			textAlign : 'left',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		}
	}, {
		headerName : "Taxable Amount",
		field : "taxableamount",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.taxableamount).toFixed(2);
  			return '<div>' +
  			       amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "Rate",
		field : "rate",
		cellStyle : {
			textAlign : 'right',
			fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
		},
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.rate).toFixed(2);
  			return '<div>' +
  			amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "CGST",
		field : "cgst",
		cellStyle : {
            textAlign : 'right',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
        },
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.cgst).toFixed(2);
  			return '<div>' +
  			amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "SGST",
		field : "sgst",
		cellStyle : {
            textAlign : 'right',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
        },
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.sgst).toFixed(2);
  			return '<div>' +
  			amountFormatter(formattedValue) +
  			       '</div>';
  		},
	}, {
		headerName : "IGST",
		field : "igst",
		cellStyle : {
            textAlign : 'right',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
        },
        cellRenderer: function(params) {
  			const formattedValue = parseFloat(params.data.igst).toFixed(2);
  			return '<div>' +
  			amountFormatter(formattedValue) +
  			       '</div>';
  		},
	},  {
        headerName : "CESS",
        field : "cess",
        cellStyle : {
            textAlign : 'right',
            fontFamily: 'Montserrat, sans-serif',
            fontSize: 'smaller'
        },
    }, {
         headerName : "Total",
         field : "total",
         cellStyle : {
             textAlign : 'right',
             fontFamily: 'Montserrat, sans-serif',
             fontSize: 'smaller'
         },
          cellRenderer: function(params) {
    			const formattedValue = parseFloat(params.data.total).toFixed(2);
    			return '<div>' +
    			       amountFormatter(formattedValue) +
    			       '</div>';
          },
     } /*{
         headerName : "Action",
         field : "action",
         cellStyle : {
                 textAlign : 'center'
         },
         cellRenderer : function(params) {
            return '<a id="deleteInvoice" onclick=deleteInvoice("'
                    + params.data.invoiceNo
                    + '","' + params.data.itemId + '") href="javascript:void(0)"><i class="fa fa-trash" aria-hidden="true"></i></a>';
        }
     }*/
];


//Grid Options for Purchase Invoice table

//For All Invoice
var gridOptionsPurch = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
};
   
//For B2B invoice
var gridOptionsPurchB2B = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};

//For B2CS invoice
var gridOptionsPurchB2CS = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};

//For B2CL invoice
var gridOptionsPurchB2CL = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};

/* HSN Purchase Grid Options*/

var gridOptionsHSNpurchase = {
    columnDefs : columnDefsHSNsales,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};


//For Purchase Invoice Table
document.addEventListener('DOMContentLoaded', function() {
	var gridDivPurch = document.querySelector('#gridPurchInvoice');
	new agGrid.Grid(gridDivPurch, gridOptionsPurch);
});

//For purchase B2B
document.addEventListener('DOMContentLoaded', function() {
	var gridDivPurchB2B = document.querySelector('#gridB2BInvoicePur');
	new agGrid.Grid(gridDivPurchB2B, gridOptionsPurchB2B);
});

//For purchase B2CS
document.addEventListener('DOMContentLoaded', function() {
	var gridDivPurchB2CS = document.querySelector('#gridB2CSInvoicePur');
	new agGrid.Grid(gridDivPurchB2CS, gridOptionsPurchB2CS);
});

//For purchase B2CL
document.addEventListener('DOMContentLoaded', function() {
	var gridDivPurchB2CL = document.querySelector('#gridB2CLInvoicePur');
	new agGrid.Grid(gridDivPurchB2CL, gridOptionsPurchB2CL);
});

//For HSN Purchase Item table
document.addEventListener('DOMContentLoaded', function() {
	var gridDivHSNpurchase = document.querySelector('#gridHSNpurchase');
	new agGrid.Grid(gridDivHSNpurchase, gridOptionsHSNpurchase);
});


function getPurchaseInvoice(){
	/*let allColumns = gridOptionsPurch.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsPurch.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		    });*/
	getHsnPurchase();
	getB2BSalesInvoicePur();
	getB2CSSalesInvoicePur();
	getB2CLSalesInvoicePur();
}

function getHsnPurchase(){
	let allColumns = gridOptionsHSNpurchase.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsHSNpurchase.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-purchase-HSN-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}




function getB2BSalesInvoicePur(){
	let allColumns = gridOptionsPurchB2B.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsPurchB2B.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-purchase-B2B-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getB2CSSalesInvoicePur(){
	let allColumns = gridOptionsPurchB2CS.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsPurchB2CS.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-purchase-B2CS-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getB2CLSalesInvoicePur(){
	let allColumns = gridOptionsPurchB2CL.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsPurchB2CL.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-purchase-B2SL-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}

/*
* GSTR-2 Purchase Section Ends
*/



/*
* GSTR-3B Section Stsrts
*/




//Grid Options for 3B Invoice table

//For All Invoice
var gridOptions3B = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};

var gridOptions3BB2B = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};


var gridOptions3BB2CS = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};


var gridOptions3BB2CL = {
    columnDefs : columnDefsPurchase,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};


var gridOptions3BHSN = {
    columnDefs : columnDefsHSNsales,
    defaultColDef : {
        sortable : true,
        filter : true,
        resizable : true,
        width : 149,
        height : 10
    },
    //rowSelection : 'multiple',
    suppressRowClickSelection : true,
    //onSelectionChanged : onSelectionChangedState
};

//For 3B Table
document.addEventListener('DOMContentLoaded', function() {
	var gridDiv3B = document.querySelector('#grid3BInvoice');
	new agGrid.Grid(gridDiv3B, gridOptions3B);
});

document.addEventListener('DOMContentLoaded', function() {
	var gridDiv3BB2B = document.querySelector('#gridB2BInvoice3B');
	new agGrid.Grid(gridDiv3BB2B, gridOptions3BB2B);
});

document.addEventListener('DOMContentLoaded', function() {
	var gridDiv3BB2CS = document.querySelector('#gridB2CSInvoice3B');
	new agGrid.Grid(gridDiv3BB2CS, gridOptions3BB2CS);
});

document.addEventListener('DOMContentLoaded', function() {
	var gridDiv3BB2CL = document.querySelector('#gridB2CLInvoice3B');
	new agGrid.Grid(gridDiv3BB2CL, gridOptions3BB2CL);
});

document.addEventListener('DOMContentLoaded', function() {
	var gridDiv3BHSN = document.querySelector('#gridHSN3B');
	new agGrid.Grid(gridDiv3BHSN, gridOptions3BHSN);
});




function getGstrPurchase3BData(selectedYear, selectedMonth){
	$(".loader").show();
	b2b3b = [], b2cs3b = [], b2cl3b = [];
    agGrid.simpleHttpRequest({
    	url : "prepare-gst-list-3B?month=" + selectedMonth + "&year=" + selectedYear,
    	contentType : "application/json"
    }).then(function(data) {
		if(data.code == "Success"){
			console.log("INSIDEMONTH ONCHANGE FUNCTION 3B: ", JSON.parse(JSON.parse(data.body[0]).Gst3Breturn));
			let gst3BData = JSON.parse(data.body[0]).Gst3Breturn;
	        let resp = JSON.parse(gst3BData);
	        
	      
	        if(resp != null && resp != ""){
				console.log("GSTR-3B -------->>> ", resp)
				gridOptions3B.api.setRowData(resp);   
		        let b2c3b = resp.filter(checkB2C);
		        b2b3b = resp.filter(checkB2B);
		
		        b2cl3b = b2c3b.filter(checkB2CL);
		        b2cs3b = b2c3b.filter(checkB2CS);
		        
		        gridOptions3BB2B.api.setRowData(b2b3b);
		        gridOptions3BB2CS.api.setRowData(b2cs3b);
		        gridOptions3BB2CL.api.setRowData(b2cl3b);
		        
		    	let purchasedata = (JSON.parse(JSON.parse(data.body[0]).Gst3Breturn))[0].allAmountDtlsPur;
		    	let salesdata = (JSON.parse(JSON.parse(data.body[0]).Gst3Breturn))[0].allAmountDtlsSales;
		    	
		    	reviewAmount3B = [{
					"totalTaxableAmt": (purchasedata.totalTaxableAmt + salesdata.totalTaxableAmt),
		            "totalIGST" : (purchasedata.totalIGST + salesdata.totalIGST),
		            "totalCGST": (purchasedata.totalCGST + salesdata.totalCGST),
		            "totalSGST" : (purchasedata.totalSGST + salesdata.totalSGST)
				}]
		    	
		        reviewGSTR3B()
			}else{
				gridOptions3B.api.setRowData();
				gridOptions3BB2B.api.setRowData();
		        gridOptions3BB2CS.api.setRowData();
		        gridOptions3BB2CL.api.setRowData();
		        //gridOptionsHSNpurchase.api.setRowData();
		        reviewAmount3B = null
			}
	        
		}
		$(".loader").hide();
    });
}

function reviewGSTR3B(){
	var totalAmountDtls = reviewAmount3B;
	
	if(totalAmountDtls != null && totalAmountDtls != ""){
		$("#taxAmountAll3B").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalTaxableAmt)));
	    $("#igstAll3B").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalIGST)));
	    $("#cgstAll3B").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalCGST)));
	    $("#sgstAll3B").html(getFormattedAmount(JSON. parse(totalAmountDtls[0].totalSGST)));
	} else{
		$("#taxAmountAll3B").html(0);
	    $("#igstAll3B").html(0);
	    $("#cgstAll3B").html(0);
	    $("#sgstAll3B").html(0);
	}
    

    var taxAmount = 0, igst = 0, cgst = 0, sgst = 0;

    if(b2b3b){
        taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
        b2b3b.forEach(function(b2b){
            taxAmount = taxAmount + b2b.total;
            igst = igst + b2b.igst;
            cgst = cgst + b2b.cgst;
            sgst = sgst + b2b.sgst;
        });
        $("#taxAmountb2b3B").html(getFormattedAmount(taxAmount));
        $("#igstb2b3B").html(getFormattedAmount(igst));
        $("#cgstb2b3B").html(getFormattedAmount(cgst));
        $("#sgstb2b3B").html(getFormattedAmount(sgst));
    } else{
        $("#taxAmountb2b3B").html(0);
        $("#igstb2b3B").html(0);
        $("#cgstb2b3B").html(0);
        $("#sgstb2b3B").html(0);
    }

    if(b2cs3b){
        taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
        b2cs3b.forEach(function(b2cs){
            taxAmount = taxAmount + b2cs.total;
            igst = igst + b2cs.igst;
            cgst = cgst + b2cs.cgst;
            sgst = sgst + b2cs.sgst;
        });
        $("#taxAmountb2cs3B").html(getFormattedAmount(taxAmount));
        $("#igstb2cs3B").html(getFormattedAmount(igst));
        $("#cgstb2cs3B").html(getFormattedAmount(cgst));
        $("#sgstb2cs3B").html(getFormattedAmount(sgst));
    } else{
       $("#taxAmountb2cs3B").html(0);
        $("#igstb2cs3B").html(0);
        $("#cgstb2cs3B").html(0);
        $("#sgstb2cs3B").html(0);
    }

    if(b2cl3b){
        taxAmount = 0, igst = 0, cgst = 0, sgst = 0;
        b2cl3b.forEach(function(b2cl){
            taxAmount = taxAmount + b2cl.total;
            igst = igst + b2cl.igst;
            cgst = cgst + b2cl.cgst;
            sgst = sgst + b2cl.sgst;
        });
        $("#taxAmountb2cl3B").html(getFormattedAmount(taxAmount));
        $("#igstb2cl3B").html(getFormattedAmount(igst));
        $("#cgstb2cl3B").html(getFormattedAmount(cgst));
        $("#sgstb2cl3B").html(getFormattedAmount(sgst));
    } else{
        $("#taxAmountb2cl3B").html(0);
        $("#igstb2cl3B").html(0);
        $("#cgstb2cl3B").html(0);
        $("#sgstb2cl3B").html(0);
    }
}

function get3BInvoice(){
	/*let allColumns = gridOptionsPurch.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptionsPurch.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		    });*/
	getHsn3B();
	getB2BInvoice3B();
	getB2CSInvoice3B();
	getB2CLInvoice3B();
}

function getHsn3B(){
	let allColumns = gridOptions3BHSN.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptions3BHSN.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-3B-HSN-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}




function getB2BInvoice3B(){
	let allColumns = gridOptions3BB2B.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptions3BB2B.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-3B-B2B-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getB2CSInvoice3B(){
	let allColumns = gridOptions3BB2CS.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptions3BB2CS.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-3B-B2CS-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}
function getB2CLInvoice3B(){
	let allColumns = gridOptions3BB2CL.columnApi.getAllColumns();
		    let exportColumns = allColumns .filter(col => !col.userProvidedColDef.suppressExcelExport);
		    gridOptions3BB2CL.api.exportDataAsCsv({
		        columnKeys: exportColumns,
		        fileName: 'gstr-3B-B2SL-report' + '-' + $("#month").val() + '-' + $("#year").val()
		    });
}


/*GST 3B Invoice HSN Api*/
function getHsn3BData(selectedYear, selectedMonth){
	$(".loader").show();
	agGrid.simpleHttpRequest({
    	url : "prepare-gst-list-3B-hsn?month=" + selectedMonth + "&year=" + selectedYear,
    	contentType : "application/json"
    }).then(function(data) {
    	
		if(data.code == "Success"){
			let code = JSON.parse(data.body[0]);
	        let resp = JSON.parse(code.purchaseItem);
	        console.log("AKMMMM",resp)
	        gridOptions3BHSN.api.setRowData(resp);
		}
		$(".loader").hide();
    });
}

/*
* GSTR-3B PDF Report
*/

//for GST 3B Pdf
    
 function get3BInvoicePdf(){
     $('#get3BInvoicePdfStmt').modal('show');
  }
 
  function cancelBtn() {
		$('#get3BInvoicePdfStmt').modal('hide');
  } 
  
  
  function getStatement(){
		
		let status = true;
	   
		var yearsData = $("#year-id").val();
		var monthsData = $("#month-id").val();
		
	
		if(yearsData == "" || yearsData == null){
			swal("Please Enter Year");
			status = false;
		}
		if(monthsData == "" || monthsData == null){
			swal("Please Enter Months");
			status = false;
		}
		if(status){
			$("#companyName").val("");
		    $("#year-id").val("");
			$("#month-id").val("");
			
			window.open("/gstreturn/gst-3B-view-pdf-report?years=" + yearsData + "&months=" + monthsData, '_blank');
		}
		
}


/*
* GSTR-3B  Section Ends
*/


$(document).ready(function(){
	
    /*Month and Year dropdown*/
    let months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
    for(var i = 0; i<12; i++){
        $("#month").append('<option value="' + (i+1) + '" text="' + months[i]  + '">' + months[i] + '</option>');
    }

    /*Select Current Month and Year*/
    const d = new Date();
    let month = d.getMonth() + 1;
    let year = d.getFullYear();
    
    $("#month").val(month);
    $("#year").val(year);
    
    getGstrSalesData(year, month);
	getHsnSalesData(year, month);

	getGstrPurchaseData(year, month);
	getHsnPurchaseData(year, month);
	
	getGstrPurchase3BData(year, month);
	getHsn3BData(year, month)
});

// Amount Formatter - Comma Separation INR Standard
function amountFormatter(value) {
    if (value !== null && value !== undefined) {
        var parts = value.toString().split('.');
        var integerPart = parts[0];
        var decimalPart = parts.length > 1 ? '.' + parts[1] : '';
        var formattedIntegerPart = new Intl.NumberFormat('en-IN').format(integerPart);
        return formattedIntegerPart + decimalPart;
    } else {
        return '';
    }
}
function downloadJson() {

    $(".loader").show();

    let createdJSON = {};

    let month = $("#month").val();
    let year = $("#year").val();

    const today = new Date();

    const day = String(today.getDate()).padStart(2, '0'); // Add leading zero if needed
    const month1 = String(today.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const year1 = today.getFullYear();

    const formattedDate = `${day}-${month1}-${year1}`;
    const formattedDate1 = `${day}${month1}${year1}`;

    createdJSON.gstin = myGSTNo;
    createdJSON.fp = month + '' + year;
    createdJSON.gt = 0.0;
    createdJSON.cur_gt = 0.0;
    createdJSON.fil_dt = formattedDate;

    if (allJsonArrayData && allJsonArrayData.length > 0) {
        let b2b = allJsonArrayData.filter(checkB2B);
        let b2cl = allJsonArrayData.filter(checkB2CL); // checkB2CL
        let b2cs = allJsonArrayData.filter(checkB2CS); // checkB2CS
        if (b2b && b2b.length > 0) {
            let b2bCreatedArr = convertToB2BFormat(b2b);
            createdJSON.b2b = b2bCreatedArr;
        } else {
            createdJSON.b2b = [];
        }
        if (b2cl && b2cl.length > 0) {
            let b2clCreatedArr = convertToB2CLFormat(b2cl);
            createdJSON.b2cl = b2clCreatedArr;
        } else {
            createdJSON.b2cl = [];
        }
        if (b2cs && b2cs.length > 0) {
            let b2csCreatedArr = convertToB2CSFormat(b2cs);
            createdJSON.b2cs = b2csCreatedArr;
        } else {
            createdJSON.b2cs = [];
        }
    } else {
        createdJSON.b2b = [];
        createdJSON.b2cs = [];
        createdJSON.b2cl = [];
    }

    agGrid.simpleHttpRequest({
        url: "prepare-gst-list-sales-hsn?month=" + month + "&year=" + year,
        contentType: "application/json"
    }).then(function(data) {
        if (data.code == "Success") {
            let resp = JSON.parse(JSON.parse(data.body[0]).salesItem);

            if (resp && resp.length > 0) {
                createdJSON.hsn = transformAndGroupByHSNCode(resp);
            } else {
                createdJSON.hsn = {};
            }
        }
    });

    setTimeout(() => {
        if (createdJSON) {
            const jsonStr = JSON.stringify(createdJSON, null, 2);
            const blob = new Blob([jsonStr], {
                type: "application/json"
            });
            const url = URL.createObjectURL(blob);

            const a = document.createElement("a");
            a.href = url;
            a.download = "returns_" + formattedDate1 + "_R1_" + myGSTNo;
            a.click();

            URL.revokeObjectURL(url);
        }
        $(".loader").hide();
    }, 3000);


}