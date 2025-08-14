$(document).ready(function() {
	
		var gridDiv = document.querySelector('#myGrid2');
				new agGrid.Grid(gridDiv, gridOptionschaildview);
				agGrid.simpleHttpRequest({
					url : "canteen-combo-throughAjax"
				}).then(function(data) {
					var len = data.length;
					$('#totalReq').find('span').html(len);
		
					gridOptionschaildview.api.setRowData(data);
					
				});
	
	
				var gridDiv = document.querySelector('#myGrid1');
					new agGrid.Grid(gridDiv, gridOptionschaildassign);
					gridOptionschaildassign.api.setRowData([]);
					agGrid.simpleHttpRequest({
						url : "canteen-combo-menu-list"
					}).then(function(data) {
						var len = data.length;
						$('#totalReq').find('span').html(len);
						if (arraysEqual(selectedRows, dataArray)) {
						    const newRowsToAdd = [...selectedRows];
						    const combinedDataArray = dataArray.concat(newRowsToAdd);
						    gridOptionschaildassign.api.setRowData(combinedDataArray);
						}
						function arraysEqual(arr1, arr2) {
						    // ...
						}		
				});
				
		
	});

//******variable dec */
let totalPrice = 0;
var deleteId = "";


//*************************Function Decl **********************/

	    function onRowClickedForAssignItem(itemId){
		   var gridData = gridOptionschaildassign.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
			  const filteredgridData = gridData.filter(obj => obj.itemId != itemId);
			    gridOptionschaildassign.api.setRowData(filteredgridData);
			    var totalPrice = 0.0;
			    filteredgridData.forEach(item => {
		         if (item.hasOwnProperty('price')) {
		      	totalPrice += parseFloat(item.price)
		       }
		    });
		  document.getElementById("allPrice").value = totalPrice;  
		 }


     
     
  //****************onRowClickedAkm*********** ******/   
     function onRowClickedAkm(param) {	
		   totalPrice = 0;
		  	if(itemDataOnSearch.length > 0){		  		
		  		console.log("itemDataOnSearch", itemDataOnSearch);
		     	}else {
		  		if(gridOptionschaildassign.api.getModel().rowsToDisplay.length == 0){
					gridOptionschaildassign.api.setRowData([]);
				}
		    }
		    	
		  	//console.log("Grid Data on row select",param.api.getSelectedRows());
		     var selectedRows = param.api.getSelectedRows();
		     console.log("selectedRows"+selectedRows);
		     const assignItemTablelist = gridOptionschaildassign.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
		     console.log("selectedRows", selectedRows);
		     console.log("assignItemTablelist", assignItemTablelist);    
		     if(assignItemTablelist.length > 0){
		    	let filteredArrayForAssignItem = [];
		    		filteredArrayForAssignItem = selectedRows.filter(item => assignItemTablelist.some(searchItem => {
        	    			return JSON.stringify(item.itemId) === JSON.stringify(searchItem.itemId);
        	    		})
    				);
	    	if(filteredArrayForAssignItem.length > 0){
		    	   swal("Already Selected!");	
	            var totalPrice = 0.0;
	            filteredArrayForAssignItem.forEach(item => {
               if (item.hasOwnProperty('price')) {
           	totalPrice += parseFloat(item.price)
       }
     });
     
	 document.getElementById("allPrice").value = totalPrice;  
	    }else{
	        let gridListData = [...selectedRows, ...assignItemTablelist];
	        	gridOptionschaildassign.api.setRowData(gridListData);
	        	console.log("gridListData", gridListData)
		   		    gridListData.forEach(item => {
		   		    if (item.hasOwnProperty('price')) {
		   		    var priceValue = parseFloat(item.price);
		   		    if (!isNaN(priceValue)) {
		   		        totalPrice += priceValue;
		   		        }
		   		       }
		   		     });    
	        	}
		    }
		    else{
		    	gridOptionschaildassign.api.setRowData(selectedRows);
		    	selectedRows.forEach(item => {
			       if (item.hasOwnProperty('price')) {
			         const priceValue = parseFloat(item.price); 
			         if (!isNaN(priceValue)) { 
			           totalPrice += priceValue;
			         }
			       }
			     });
		    }
		   //  console.log("Total price:", totalPrice);
		   document.getElementById("allPrice").value = totalPrice.toFixed(2); 
		}
		
		
//************************Delete Function**********************************************		
	  function deleteIncentive() {		
			$.ajax({
				type : "GET",
				url : "canteen-combo-delete-id?id=" + deleteId,
				success : function(response) {
					if (response.message == "Success") {
						swal("Menu delete successfully!", " ", "success");
						cancelBtn();
						agGrid.simpleHttpRequest({
							url : "canteen-combo-all-throughAjax"
						}).then(function(data) {
							gridOptions.api.setRowData(data);
						});
					}
				}
	
			});
	
		$('#delete').attr("disabled", true);
		}
				
		//**********************Add function ***********************
		   function saveTableData() {
		         var selectedRows = gridOptionschaildassign.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
		          var requestData = {
		    	    comboId:  $("#comboId").text(),
		    	    comboName:  $("#comboName").val(),
		    	    itemList: selectedRows,
		    	    allPrice: $("#allPrice").val()
		       };
		       
		if (requestData.comboName == null || requestData.comboName == "") {

			validation = false;
			$("#errmsg_name").css('color', 'red');
			$("#errmsg_name").css('font-size', '10px');
			$("#errmsg_name").html("Combo Name Required").show().fadeOut(
					6000);
			return requestData;
		}
		if (requestData.allPrice == null || requestData.allPrice == "") {

			validation = false;
			$("#errmsg_price").css('color', 'red');
			$("#errmsg_price").css('font-size', '10px');
			$("#errmsg_price").html("All Price Required").show().fadeOut(
					6000);
			return requestData;
		}
		      $.ajax({
		          type: "POST",
		           url: "canteen-combo-add-dtls",
		           contentType: "application/json",
		           data: JSON.stringify(requestData), 
		            success: function (response) {
		            if (response.message == "Success") {
		            	 console.log(response.message == "Success");
		                  swal("Menu added successfully!", " ", "success");
		                  cancelBtn();
		                  agGrid.simpleHttpRequest({
		                  url: "canteen-combo-all-throughAjax"
		                }).then(function (data) {
		                gridOptions.api.setRowData(data);
		               });
		            }
		        },
		        error: function (data) {
		            // Handle error
		        }
		    });
		  }
			

	//***********************Subcategry function**********************/
	    function clubMemberGetDetail(){
		    var clubMemberId = $('#clubmember').val();
			   $.ajax({
				 type : "GET",
				 url : "canteen-combo-getMemberDetails?id=" + subcategry,
				 async : false,
				 success : function(response) {
					//console.log("response------" + JSON.stringify(response));
					  if (response.message == "Success") {
						$("#rangefrom").val(response.body[0].memberRangeFrom);
						$("#rangeto").val(response.body[0].memberRangeTo);
					}
				  }
			   })
		    }
		
	
	//************************categry****************************//
		function getIncentiveStatus(){
				
			 var categry = $('#categry').val();
			 $.ajax({
				type : "GET",
				url : "canteen-combo-getIncentiveDetails?id=" + categry,
				async : false,
				success : function(response) {
					if (response.message == "Success") {
						$("#incentivecode").val(response.body[0].itemId);
					}
				}
			})
		}
		
		

	//*******************************Category call**********************
		function getIncentiveStatus() {
			var catId = $("#categry").val();
			var subCatId = $("#subcategry").val();
			var variant = $("#variant").val();
			  $.ajax({
				 type : "GET",
				 url : "canteen-combo/canteen-item-list?catId=" + catId + "&subCatId="
						+ subCatId + "&variant=" + variant,
				 async : false,
				 success : function(response) {
					console.log("response------" + JSON.stringify(response));
					if (response.body != "") {
						gridOptionschaildview.api.setRowData();
						gridOptionschaildview.api.setRowData(response);	
					}
				}
			})
		   }
	
	
	//******************************view function***********************	
	    function getIncentiveStatusall() {
			$.ajax({
				type : "GET",
				url : "canteen-combo-all-throughAjax",
				async : false,
				success : function(response) {
					console.log("response------" + JSON.stringify(response));
					if (response.body != "") {
						
						gridOptions.api.setRowData();
						gridOptions.api.setRowData(response);
					}
	
				}
			})
		} 
		

 //*******************************Auto Search *************************/            
	    let itemDataOnSearch = []; 
	  //Searching
	      function getProductList() {
			 $("#itemId").val("");
			  var search = $("#itemName").val();
			  if (search) {
				$.ajax({
						type : "POST",
							url : "canteen-combo-menu-list",
							dataType : 'json',
							contentType : 'application/json',
							data : search,
							success : function(response) {
								if (response.code == "success") {
									if (response.body.length != 0) {
										itemDataOnSearch = [];
										itemDataOnSearch = response.body;
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1" >';
										for (var i = 0; i < response.body.length; i++) {
											content += '<li style="margin-left:0px; font-weight:400; font-size:14px; color:#343a40;     background-color: #dbdbdb;"  class="autocompletedata cp" 										onClick="selectAutocompleteValue1(\''
													+ response.body[i].itemId + '\',\''+response.body[i].itemName+'\')">'
													+ response.body[i].itemName
													+ '</li>';}
										content += '<li  >'
												+ '</li>';
										content += '</ul>';
										////console.log("content " + content)
										$("#suggesstion-box11_").show();
										$("#suggesstion-box11_").html(content);
	
									} else {
										$("#search").css("background", "#FFF");
										var content = '<ul id="autocomplete-list1">';
										content += '<li style="margin-left:0px; font-weight:100; font-size:14px; color:#ccc;     background-color: #dbdbdb;">'
												+ "No Data Found" + '</li>';
										content += '<li style="margin-left:-30px;" '
												+ '</li>';
										content += '</ul>';
										$("#suggesstion-box11_").show();
										$("#suggesstion-box11_").html(content);
										
									}
								}
							
							},
							error : function(data) {
								//console.log(data);
							}
						})
			        }
	       	}

	    function selectAutocompleteValue1(itemid,itemname) {
		//alert(itemname)
		   $("#itemName").val(itemname);
		     totalPrice = 0;
	         //console.log("Serahc", itemDataOnSearch);
		     itemDataOnSearch = itemDataOnSearch.filter(itm => itm.itemId == itemid);	
	        //	console.log("Serahc DATA Autocolplete", itemDataOnSearch);
		          const prices = [];
		          var allprice = 0 ;
		       itemDataOnSearch.forEach(item => {
	             if (item.hasOwnProperty('price')) {
	              prices.push(item.price);
	              var intValue = parseInt(prices);
	              allprice = allprice+intValue;
	           }
	          });   
		 document.getElementById("allPrice").value = allprice;  
		 	
	       if (itemDataOnSearch.length > 0) {	
	      	   const assignItemTableData = gridOptionschaildassign.api.getModel().rowsToDisplay.map(rowNode => rowNode.data);
	          if(assignItemTableData.length > 0){
	        	console.log(assignItemTableData)
	        	
	        	const filteredArray = assignItemTableData.filter(item => itemDataOnSearch.some(searchItem => {
	        	    	return JSON.stringify(item.itemId) === JSON.stringify(searchItem.itemId);
	        	  	})
	        	);
	        	console.log(filteredArray, "  Duplicate object");
	        	if(filteredArray.length > 0){
	        		
							swal("Already Selected!");
							
	          	var totalPrice = 0.0;
		          filteredArray.forEach(item => {
	              if (item.hasOwnProperty('price')) {
	            	totalPrice += parseFloat(item.price)
	            }
	          });
	     document.getElementById("allPrice").value = totalPrice;  				
	        	} else{
	        		let gridList = [...assignItemTableData, ...itemDataOnSearch];
	        		gridOptionschaildassign.api.setRowData(gridList);
	        		//gridOptionschaildassign.api.setRowData(jsondata);
	     
		   		     let totalPrice = 0;
		   		     gridList.forEach(item => {
		   		       if (item.hasOwnProperty('price')) {
		   		         const priceValue = parseFloat(item.price); 
		   		         if (!isNaN(priceValue)) { 
		   		           totalPrice += priceValue; 
		   		         }
		   		       }
		   		     });
		   		     document.getElementById("allPrice").value = totalPrice.toFixed(2); 
			        	}
			        }else{
			        	gridOptionschaildassign.api.setRowData(itemDataOnSearch);
			        }
		        let gridList = [...assignItemTableData, ...itemDataOnSearch];
		        $("#suggesstion-box11_").hide(); 
		    } else {
		        // Handle the case where itemId is not available
		        $("#itemId").val("");
		        $("#itemName").val("");
		        $("#price").val("");
		        $("#search").val("");
		        $("#search").attr('data-procat', "");
		        $("#suggesstion-box11_").hide();
		        gridOptionschaildassign.api.setRowData(itemDataOnSearch);
		    }
		   
		}
		    
		    
		    	
//*********************Edit function ****************//
			function editPage(id) {
			
				$.ajax({
					type : "GET",
					url : "canteen-combo-edit?id=" + comboId,
					async : false,
					success : function(response) {
						console.log("response------" + JSON.stringify(response.body));
						if (response.message == "Success") {
							
							const stringifyData = JSON.stringify(response.body);
							const jsondata = JSON.parse(stringifyData);
							console.log("Edit data"+jsondata)
							
							gridOptionschaildassign.api.setRowData(jsondata);
							
			     			      
							$("#comboId").val(response.body[0].comboId);
							$("#comboName").val(response.body[0].comboName);
							$("#allPrice").val(response.body[0].allPrice);
							categry = response.body[0].categry;
							$("#categry").val(response.body[0].categry);
							getIncentiveStatus(response.body[0].categry);
							$("#subcategry").val(response.body[0].subcategry);	
							$("#variant").val(response.body[0].variant);	
						}
			
					}
				})
			  }
					                    
		        function newBtn() {	
				if (gridOptions.api) {
        gridOptions.api.deselectAll();
    }
					gridOptionschaildassign.api.setRowData();
					$("#comboId").val('');
					$("#comboName").val('');
					$("#allPrice").val('');
					$("#categry").val('');
					$("#subcategry").val('');
					$("#variant").val('');
					gridOptionschaildview.api.setRowData();
					
			}
			// for cancel button
			function cancelBtn() {
				$("#add").show();
				$("#copy").show();
				$("#delete").show();
				$("#totalReq").show();
				$("#myGrid").show();
				$("#searchRowDiv").show();
				$("#demo").hide();
		
				$('#itemId').val("");
				$('#itemName').val("");
				$('#price').val("");
				$('#categry').val("");
				$('#subcategry').val("");
				$('#variant').val("");
				$('#status').val("");
		
				agGrid.simpleHttpRequest({
					url : "canteen-combo-all-throughAjax"
				}).then(function(data) {
					gridOptions.api.setRowData(data);
				});
				
			}
		


//****************gridDiv  call********************/      
       $(function() {
				var gridDiv = document.querySelector('#myGrid');
				new agGrid.Grid(gridDiv, gridOptions);
				agGrid.simpleHttpRequest({
					url : "canteen-combo-all-throughAjax"
				}).then(function(data) {
					var len = data.length;
					$('#totalReq').find('span').html(len);
					gridOptions.api.setRowData(data);
					
					var firstRowNode = gridOptions.api.getDisplayedRowAtIndex(0);
					if (firstRowNode) {
						firstRowNode.setSelected(true);
		}
				});
				
			});
		
				
					

//************************************columnDefs*************************
				 var  columnDefs= [ {
					headerCheckboxSelection : true,
					headerCheckboxSelectionFilteredOnly : true,
					checkboxSelection : true,
					width : 10,
					sortable : false,
					filter : false,
					resizable : true
				},
				{
					headerName : 'Combo Id',
					field : "comboId",
					width : 350,
					
				},
			
				{
					headerName : "Combo Name",
					field : "comboName",
					width : 350,
					cellStyle : {
						textAlign : 'center'
					},
					  cellRenderer : function(params) {
							return '<div style="color:black;font-weight: bold;">'
							+ params.data.comboName 
							+ '</div>';
			      	}
				}, {
					headerName : "Total Price",
					field : "allPrice",
					cellStyle : {
						textAlign : 'center'
					},
					  cellRenderer : function(params) {
							return '<div style="color:black;font-weight: bold;">'
							+ params.data.allPrice 
							+ '</div>';
			      	}
				}];
			
				 const gridOptions = {
							columnDefs : columnDefs,
							defaultColDef : {
								sortable : true,
								filter : true,
								resizable : true,
								width : 220,
								height : 10
							},
							//rowSelection : 'multiple',
							suppressRowClickSelection : true,
							onSelectionChanged : rowSelect,
							/*getRowNodeId : function(data) {
								return data.comboId;
							}*/
						};
				
	var comboId = '';							 	
					function rowSelect() {
						var selectedRows = gridOptions.api.getSelectedRows();
					    var selectedData = selectedRows.map(node => node.data);
						var rowCount = 0;
				
						selectedData.forEach(function(selectedRow, index) {
							rowCount = rowCount + 1;
						});
						for (var i = 0; i < selectedRows.length; i++) {
							comboId = comboId + selectedRows[i].comboId;
							editPage(comboId)					
						}
									
		comboId = '';
		
		if (rowCount > 0) {
		
	}else {
gridOptionschaildassign.api.setRowData();
					$("#comboId").val('');
					$("#comboName").val('');
					$("#allPrice").val('');
					$("#categry").val('');
					$("#subcategry").val('');
					$("#variant").val('');
					gridOptionschaildview.api.setRowData();
	}
				
			}
			
				
				
	//********************************chaildview****************************//
	
				const chaildview = [
						{
							headerCheckboxSelection : true,
							checkboxSelection : true,
							width : 8,
							sortable : false,
							filter : false,
							resizable : true
			
						},
						{
							headerName : 'Item Id',
							field : "itemId",
							width : 300
						},
			
						{
							headerName : "Item Name",
							field : "itemName",
							width : 300,
							cellStyle : {
								textAlign : 'center'
							}
						}, {
							headerName : "Price",
							field : "price",
							width : 300,
							cellStyle : {
								textAlign : 'right'
							}
						}
			
				];
			
				 var gridOptionschaildview = {
							columnDefs : chaildview,
							defaultColDef : {
								sortable : true,
								filter : true,
								resizable : true,
								width : 200,
								height : 20
							},
							rowSelection : 'single',
							onSelectionChanged : onRowClickedAkm
							//onRowClicked : onRowClickedAkm
						};
						
									  
				
    //************************ItemchildViewassign******************************** */
				 var ItemchildViewassign = [{
					headerCheckboxSelection : true,
					headerCheckboxSelectionFilteredOnly : true,
					checkboxSelection : true,
					width : 10,
					sortable : false,
					filter : false,
					resizable : true
				}, {
					headerName : "Item Id",
					field : "itemId",
					width : 175
			
				}, {
					headerName : "Item Name",
					field : "itemName",
					width : 175
				}, {
					headerName : "Price",
					field : "price",
					width : 175
				}, {
					headerName : "Action",
					field : "param",
					width : 95,
					cellStyle : {
						textAlign : 'center'
						
					},
					  cellRenderer : function(param) {
						  return `
						  <div >
					        <div style="color: black; font-weight: bold;"></div>
					        <button onclick=onRowClickedForAssignItem('${param.data.itemId}')>Delete</button>
					    </div>
					    `;
			      	}
				}];
				var gridOptionschaildassign = {
					columnDefs : ItemchildViewassign,
					defaultColDef : {
						sortable : true,
						filter : true,
						resizable : true,
						width : 200,
						height : 20
					},
					rowSelection : 'multiple',
					//onSelectionChanged : onRowClicked
					//onRowClicked : onRowClicked
				};
function validateForm() {
	let isValid = true;

	// Loop through each mandatory field and check if it is filled
	$('span.mandatory').each(function() {
		// Get the corresponding input/select field next to the mandatory asterisk
		let input = $(this).closest('.form-group').find('input, select, textarea');

		// Check if the field is empty or invalid
		if (input.val().trim() === '' || input.val() === 'Select') {
			isValid = false;
		}
	});

	// Enable or disable the Save button based on validation
	$('#next').prop('disabled', !isValid);
}


function next() {
	$("#previewTab").removeClass('hidden');
	$("#canteenTab").addClass('hidden');
	

	$(".nav-link").removeClass("active");
	$(".nav-link[href='#previewTab']").addClass("active");
			
			$("#tab-previewTab").addClass("active").attr("aria-selected", "true");
			$("#tab-canteenTab").removeClass("active").attr("aria-selected", "false");

			// Show the "Doctor Details" tab content
			$("#previewTab").addClass("show active");
			$("#canteenTab").removeClass("show active");
		}
		
function prev() {
	$("#canteenTab").removeClass('hidden');
	$("#previewTab").addClass('hidden');
	

	$(".nav-link").removeClass("active");
	$(".nav-link[href='#canteenTab']").addClass("active");
		
			$("#tab-canteenTab").addClass("active").attr("aria-selected", "true");
			$("#tab-previewTab").removeClass("active").attr("aria-selected", "false");
			
			
			$("#canteenTab").addClass("show active");
			$("#previewTab").removeClass("show active");
		}
				    