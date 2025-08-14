/**
 * 
 */
 $(document).ready(function() {
		$("#divrow").hide();
		
		var date = (new Date()).toISOString().split('T')[0];
		var newToDate = changeDateFormat(date);
		var newFromDate = changeFromDateFormat(date);
		$("#fromDateBonus").val(newFromDate);
		$("#toDateBonus").val(newToDate);
		//$('.loader').show();
		
		var dateFormat = localStorage.getItem("dateFormat");
		$("#fromDateCalenderBonus").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#fromDateBonus').val($(this).val());
			viewBonusDetails();
		})

		$('#fromDateBonus').blur(function() {
			$("#fromDateCalenderBonus").val($(this).val());
		})

		$("#toDateCalendarBonus").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#toDateBonus').val($(this).val());
			viewBonusDetails();
		})

		$('#toDateBonus').blur(function() {
			$("#toDateCalendarBonus").val($(this).val());
		})

		$("#bonusDateCalender").datetimepicker({
			format : dateFormat,
			closeOnDateSelect : true,
			timepicker : false,
		}).on("change", function() {
			$('#bonusDate').val($(this).val());
		//	viewBonusDetails();
		})

		$('#bonusDate').blur(function() {
			$("#bonusDateCalender").val($(this).val());
		})
		
	})
	
	
	function viewBonusDetails() {
		var fdate = $("#fromDateBonus").val();
		var tdate = $("#toDateBonus").val();
		
		var selectedNodes = gridOptionsEmployee?.api?.getSelectedNodes();
			var selectedData = selectedNodes?.map(node => node.data);
			var id = selectedData?.map(node => node.employeeId);
		agGrid.simpleHttpRequest(
				{
					url : "payroll-report-employee-bonus-exgratia-view?fromDate=" + fdate
							+ "&toDate=" + tdate + "&id=" + id
				}).then(function(data) {
					var jsonData = JSON.parse(data.body);
					var allData=jsonData.viewData;
					if(allData){
						gridOptionsbonus.api.setRowData(allData);
						var totalRowCount = gridOptionsbonus.api.getModel().getRowCount();
						$('#bonuscnt').find('span').html(totalRowCount);
					}else{
						gridOptionsbonus.api.setRowData();
						//$('#totalReq').find('span').html('0');
						$('#bonuscnt').find('span').html('0');
						
					}

		});
	}

	function changeDateFormat(inputDate) { // expects Y-m-d
		var splitDate = inputDate.split('-');
		if (splitDate.count == 0) {
			return null;
		}

		var year = splitDate[0];
		var month = splitDate[1];
		var day = splitDate[2];

		return day + '-' + month + '-' + year;
	}
	function changeFromDateFormat(inputDate) { // expects Y-m-d
		var splitDate = inputDate.split('-');
		if (splitDate.count == 0) {
			return null;
		}

		var year = splitDate[0];
		var month = splitDate[1];
		var day = splitDate[2];

		return day + '-' + month + '-' + year;
	}
	/* -------------------search bar for mygrid------------------------ */

	function onQuickFilterChangedbonus() {
		gridOptionsbonus.api
				.setQuickFilter(document.getElementById('quickFilterbonus').value);
	    var totalRowCount = gridOptionsbonus.api.getModel().getRowCount();
	    $('#bonuscnt').html(totalRowCount);
	}

	function cancelBarbonus() {
		var id = document.getElementById("closeKeybonus");
		id.style.display = "block";

		if ($('#quickFilterbonus').val() == null || $('#quickFilterbonus').val() == "") {
			id.style.display = "none";
		}
	}
	//show position
	function showPosition(position) {
		$("#latitude").val("");
		$("#longitude").val("");
		var latitude = position.coords.latitude;
		var longitude = position.coords.longitude;

		$("#latitude").val(latitude);
		$("#longitude").val(longitude);

		var locAPI = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
				+ latitude
				+ ","
				+ longitude
				+ "&key=AIzaSyD-o-8txzrqCvKZaf35i-zILm2ooG851uE";

		$("#latitude").val(latitude);
		var lat1 = $("#latitude").val();
		$("#longitude").val(longitude);
		var lon1 = $("#longitude").val();
		var lat2 = $("#latitude2").val();
		var lon2 = $("#longitude2").val();

		if (lon2 == "" || lat2 == "") {
			$.get({
				url : locAPI,
				success : function(data) {
					console.log("data===1==" + data);
					console.log(data);
					var loc = $("#location1").val();
					var city = loc.split(',');
					var plc = data.results[0].formatted_address;
					$("#punchInLocation").val(plc);
					$("#punchOutLocation").val(
							data.results[0].formatted_address);
				}

			});
		} else {
			$.get({
				url : locAPI,
				success : function(data) {
					console.log(data);
					var place = data.results[0].formatted_address;
					$("#punchInLocation").val(place);
					$("#punchOutLocation").val(place);
				}

			});
		}
	}
	
	 $(document).ready(function() {
		var gridDiv = document.querySelector('#myGridbonus');
		new agGrid.Grid(gridDiv, gridOptionsbonus);

		//var dateFormat = localStorage.getItem("dateFormat");

		$("#fromDateCalendar").datetimepicker({
			format : 'H:i',
			closeOnDateSelect : false,
			timepicker : true,
			datepicker : false,
			step : 15
		}).on("change", function() {
			$('#punchinTime').val($(this).val());
		})

		

		//punch out time
		$("#fromDateCalendar1").datetimepicker({
			format : 'H:i',
			closeOnDateSelect : false,
			timepicker : true,
			datepicker : false,
			step : 15
		}).on("change", function() {
			$('#punchoutTime').val($(this).val());
		})

		$('#punchoutTime').blur(function() {
			$("#fromDateCalendar1").val($(this).val());
		})

		$("#cancel").hide();
		$("#add").hide();
		$('#delete').attr("disabled", true);
		$("#add1").hide();
		$('#uploadModal').hide();

		viewBonusDetails();
	});
	 
	 const columnDefsbonus = [

			{
				headerName : "Employee Id",
				field : "employeeId",
				width : 130,
			}, {
				headerName : "Employee Name",
				field : "employeeName",
				width : 280,
			}, {
				headerName : " Attendance",
				field : "attendance",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				}
			}, {
				headerName : "Basic Salary",
				field : "basicSal",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				}
			}, {
				headerName : "Bonus",
				field : "bonus",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				}

			}, {
				headerName : "Exgratia",
				field : "exgratia",
				width : 150,
				cellStyle : {
					textAlign : 'center'
				}

			},  {
				headerName : "Total Bonus & Exgratia",
				field : "total",
				width: 230,
				cellStyle : {
					textAlign : 'center'
				}

			}, {
				headerName : "Bank Account Details",
				field : "details",
				width : 280,
				cellStyle : {
					textAlign : 'center'
				}

			},  ];
			const gridOptionsbonus = {
				columnDefs : columnDefsbonus,
				defaultColDef : {
					sortable : true,
					filter : true,
					resizable : true,
					width : 266,
					height : 10
				}
			};
			/* Function for attendanceModal show */
			function addBonus() {
				$("#empid").val("");
				$("#empName").val("");
				$('#attendanceModal').modal('toggle');
			}
			function closeModalbonus() {
				$('#attendanceModal').modal('hide');
			}
			function checkBonus() {
				var validation = true;
				var empids = $("#empid").val();
				if (empids == null || empids == "") {
					$("#error1").show();
					$("#error1").html("Employee Name Required");
					validation = false;
				}
				if (validation) {
					$("#bonuscnt").hide();
					$("#searchRowDiv").hide();
					$("#downloadbonus").hide();
					$("#upload").hide();
					$("#fdate").hide();
					$("#tdate").hide();
					$("#fdateDiv").hide();
					$("#tdateDiv").hide();
					$.ajax({
						type : 'GET',
						url : '/master/employee-bonus-exgratia-getPunch?empids=' + empids,
						contentType : false,
						success : function(response) {
							if (response.message == "success") {
								$("#employeeId").val(empids);
								$("#employeeName").val($("#empName").val());
								if (response.body.code == 0) {
									$("#isOut").val(response.body.name);
									addchkbonus();
								} else {
									$("#isOut").val(2);
									addchkbonus();
								}
							}
						},
						error : function(datas) {
						}
					})
				}
			}
			
			function addchkbonus() {
				closeModalbonus();
				var puchVal = $("#isOut").val();
				if (puchVal == 0) {
					$("#demo").show();
					$("#pit").show();
					$("#pin").show();
					$("#pot").hide();
					$("#pon").hide();

					$("#addattendance").hide();
					$("#add1").hide();
					$("#add").show();
					$("#myGridbonus").hide();
					$("#cancel").show();
					$("#delete").hide();

					$("#isOut").val("");
					$("#punchinTime").val("");
					$("#punchinNote").val("");

					var currentdate = new Date();
					var datetime = currentdate.getDate() + "-"
							+ (currentdate.getMonth() + 1) + "-"
							+ currentdate.getFullYear() + " " + currentdate.getHours()
							+ ":" + currentdate.getMinutes() + ":"
							+ currentdate.getSeconds();

					$("#punchinTime").val(datetime);

				} else if (puchVal == 1) {
					$("#demo").show();
					$("#pit").hide();
					$("#pin").hide();
					$("#pot").show();
					$("#pon").show();
					$("#addattendance").hide();
					$("#add").hide();
					$("#add1").show();
					$("#myGridbonus").hide();
					$("#cancel").show();
					$("#delete").hide();

					$("#isOut").val("");
					$("#punchoutTime").val("");
					$("#punchoutNote").val("");

					var currentdate1 = new Date();
					var datetime1 = currentdate1.getDate() + "-"
							+ (currentdate1.getMonth() + 1) + "-"
							+ currentdate1.getFullYear() + " "
							+ currentdate1.getHours() + ":" + currentdate1.getMinutes()
							+ ":" + currentdate1.getSeconds();
					$("#punchoutTime").val(datetime1);
				} else {
					$("#bonuscnt").show();
					$("#searchRowDiv").show();
					$("#downloadbonus").show();
					$("#upload").show();
					$("#fdate").show();
					$("#tdate").show();
					$("#fdateDiv").show();
					$("#tdateDiv").show();
					var ename = $("#empName").val();
					toastr.success(ename.concat(" attendance up-to-date"))
					
					/* $("#messageParagraph").text(ename.concat(" attendance up-to-date"));
					$("#msgOkModal").removeClass("btn3");
					$("#msgOkModal").addClass("btn1");
					$("#msgModal").modal('show'); */
				}

			}
			function cancelbonus() {
				location.reload();
				$("#demo").hide();
				$("#demo1").hide();
				$("#add1").hide();
				$("#add").hide();
				$("#myGridbonus").show();
				$("#cancel").hide();
				$('#addattendance').attr("disabled", true);
				$('#delete').attr("disabled", true);
				$("#bonuscnt").hide();
				$("#searchRowDiv").hide();
				$("#downloadbonus").hide();
		        onFilterDropdownChange();
			}
			
			function downloadDetailsbonus() {
				var dataset = [];
				gridOptionsbonus.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
					dataset.push(rowNode.data);
				});
				gridOptionsbonus.api.exportDataAsCsv(dataset);
			}

			function checkAlphabet(fieldId) {
				var tempVal = $("#" + fieldId).val().replace(/[^a-zA-Z., ]/g, '');
				$("#" + fieldId).val(tempVal);
			}
			//Function for upload 	 
			function uploadDetails() {

				$(".formValidation").remove();
				//$('#uploadModal').modal('toggle');
				$('#uploadModal').show();
				$('#bonusdi').hide();
				$("#uploadedBillDiv_0").html('');
				$("#imageName_0").html('');
				$("#uploadDoc_0").val('');
				$("#bonusDate").val('');
			}
			
			function closeUploadModal() {
				//$('#uploadModal').modal('hide');
				//location.reload();
				$('#uploadModal').hide();
				$('#bonusdi').show();
				$("#imageName_0").empty();
				$("#uploadedBillDiv_0").empty();
			}


			/* save document file */
			function saveMultiFile(event) {
				$(".formValidation").remove();
				var uFile = $(uploadDoc_0)[0].files[0];
				var fileName = event.currentTarget.value;
				var lastIndex = fileName.lastIndexOf("\\");
				if (lastIndex >= 0) {
					fileName = fileName.substring(lastIndex + 1);
				}
				var extension = fileName.split(".");
				var iURL = URL.createObjectURL(uFile);
				$("#uploadedBillDiv_0").html("");

				if (extension[1] == "xls" || extension[1] == "xlsx") {
					var LightImg = "<div class='uploadicon position-l'><a class='example-image-link' href='" + iURL + "' title='" + fileName + "' target='_balnk'><i class='fa fa-file-excel-o'></i></a></div>";
				} else {
					var LightImg = "<div class='uploadicon position-l'> </div>";
				}
				$("#uploadedBillDiv_0").html(LightImg);
				$("#imageName_0").html(fileName);
				var fileData = new FormData();
				fileData.append('file', uFile);
				fileData.append('path', 'none');
				$.ajax({
					type : "POST",
					url : "payroll-report-employee-bonus-exgratia-upload-file",
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
			
			//Upload  Bonus
			var dataset = [];
			function saveUploadData() {
				var data = {};
				var validation = true;
				data.date = $("#bonusDate").val();
				dataset.push(data);
				var udata = $("#imageName_0").html();
				if (udata == null || udata == "") {
					validation = validationUpdated("Please Upload Excel",
							"validationDiv");
				}if (data.date == null || data.date == "") {
					validation = validationUpdated("Bonus Date Required",
					"bonusDate");
		}
				console.log("JSON.stringify(dataset)---",JSON.stringify(dataset))
				if (validation) {
					$.ajax({
						type : "POST",
						url : "payroll-report-employee-bonus-exgratia-save-excelData",
						dataType : "json",
						contentType : "application/json",
						data : JSON.stringify(dataset),
						success : function(response) {
							console.log("response=",response)
							if (response.message == "Success") {
								$("#uploadedBillDiv_0").html("");
								$("#imageName_0").html("");
								toastr.success("Bonus Uploaded Successfully")
								
								//$('#uploadModal').modal('hide');
								$('#uploadModal').hide();
								$('#bonusdi').show();
								viewBonusDetails();
							} else {
								toastr.success("Duplicate Bonus Entry")
								
								//$('#uploadModal').modal('hide');
								$('#uploadModal').hide();
								$('#bonusdi').show();
								$("#uploadedBillDiv_0").html("");
								$("#imageName_0").html("");
							}
						},
						error : function(response) {
							console.log(response);
						}
					})
				}

			}
			function setFromToDate() {
				toastr.success("Please choose to date greater than or equal to from date")
				
				$("#fromDateBonus").val("");
				$("#toDateBonus").val("");
				var date = (new Date()).toISOString().split('T')[0];
				var newToDate = changeDateFormat(date);
				var newFromDate = changeFromDateFormat(date);
				$("#fromDateBonus").val(newFromDate);
				$("#toDateBonus").val(newToDate);
			}
			function dateChange() {
				var fromdate = $('#fromDateBonus').val();
				var todate = $('#toDateBonus').val();
				var fd = fromdate.split("-");
				var td = todate.split("-");
				if (fromdate != '' && todate != '') {
					if (fd[2] == td[2]) {
						if (fd[1] == td[1]) {
							if (fd[0] <= td[0]) {
								viewBonusDetails();
							} else {
								setFromToDate();
							}
						} else if (fd[1] < td[1]) {
							viewBonusDetails();
						} else {
							setFromToDate();
						}

					} else if (fd[2] < td[2]) {
						viewBonusDetails();
					} else {

						setFromToDate();
					}
				} else {

				}
			}
			
			
			function onFilterDropdownChange() {
			    var selectedShift = document.getElementById('shiftFilter').value;

			    // Set filter model to filter by 'employeeBy' column
			    gridOptionsbonus.api.setFilterModel({
			    	shift: {
			            type: 'equals',
			            filter: selectedShift
			        }
			    });
			    // Apply the filter
			    gridOptionsbonus.api.onFilterChanged();
			    var totalRowCount = gridOptionsbonus.api.getModel().getRowCount();
			    $('#bonuscnt').html(totalRowCount);
			}
	function downloadDetails() {
		var dataset = [];
		gridOptionsbonus.api.forEachNodeAfterFilterAndSort(function(rowNode, index) {
			dataset.push(rowNode.data);
		});
		gridOptionsbonus.api.exportDataAsCsv(dataset);
	}		