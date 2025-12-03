function compensationHighChat(){
	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();
	var org = $("#compensationHrmsOrganization").val();
	var orgDiv = $("#compensationHrmsDivision").find('option:selected').text();
	var location = $("#compensationHrmsLocation").find('option:selected').text();

	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-compensation-head-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
	
				$("#totalNoEmployee").text(allData[0].totalNoEmployee);
				$("#grossSalaryPaidAmount").text(allData[0].grossSalaryPaidAmount/1000000 + "M");
				$("#netSalaryPaidAmount").text(allData[0].netSalaryPaidAmount/1000000 + "M");
				$("#totalDeductionAmount").text(allData[0].totalDeductionAmount/1000 + "K");
				$("#failedNoOfPayment").text(allData[0].failedNoOfPayment);
				$("#avgAppraisalPercentage").text(allData[0].avgAppraisalPercentage+ "%");
				$("#avgTenureOfAnEmployee").text(allData[0].avgTenureOfAnEmployee);
				
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
   
   $.ajax({
		type: "GET",	
		url: "hrms-dashboard-compensation-salary-distribution-by-dept?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);  // Parse the JSON response
				var allData = jsonData.dashboardData; // Extract the data array
				
				var deptNameList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					deptNameList.push(item.deptName);
				}
				
				var colors = ["#BF05FF","#DB51C9","#DB5199","#E976AD","#F79C92"];
				
				var series = allData.map(function(item, index) {
			    return {
			        name: item.deptName,
			        data: [item.departmentWiseSalaryPercentage],
			        color: colors[index % colors.length] // Cycle through colors if there are more departments than colors
			    };
			});
				
		Highcharts.chart('chartContainer', {
		    chart: {
		        type: 'bar',
		        height: 200,
		        spacingTop: 0,
		        spacingBottom: 0,
		        spacingLeft: 0,
		        spacingRight: 0,
		        groupPadding: 0,  // Remove the padding between groups
		        pointPadding: 0   // Remove the padding between individual bars
		    },
		    title: {
		        text: ''
		    },
		    xAxis: {
		        categories: deptNameList,//['IT','Marketing','Sales','Administration','Engineering'],  // Set the categories directly
		        visible: false,
		        title: {
		            text: ''
		        },
		        labels: {
		            enabled: false
		        },
		        tickmarkPlacement: 'on',
		        gridLineWidth: 0, // Remove the grid lines for a cleaner look
		    },
		    yAxis: {
		        gridLineColor: 'transparent',
		        title: {
		            text: false,
		            visible: false,
		            lineWidth: 0,
		            minorGridLineWidth: 0,
		            lineColor: 'transparent'
		        },
		        labels: {
		            enabled: false
		        }
		    },
		    plotOptions: {
		        series: {
		            stacking: 'normal',
		            dataLabels: {
		                enabled: true,
		                style: {
		                    color: 'black',
		                    textOutline: 'none',
		                    fontWeight: 'bold'
		                },
		                formatter: function() {
		                    return this.series.name + ': ' + this.y; // Display name and value
		                },
		                align: 'center' // Align the labels to the left side
		            }
		        }
		    },
		    navigation: {
		        buttonOptions: {
		            enabled: false
		        }
		    },
		    credits: {
		        enabled: false
		    },
		    legend: {
		        enabled: false
		    },
		    series: series,
		});
						
						

	
				
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	
	 $.ajax({
		type: "GET",	
		url: "hrms-dashboard-compensation-employee-count-by-salary-range?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
						var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var totalSalarySpend = allData[0].totalSalarySpend;
				
				var totalSpendCTCBelow20k = 0;
				var totalSpendCTCBet20kto35k = 0;
				var totalSpendCTCBet35kto50k = 0;
				var totalSpendCTCBet50kto100k = 0;
				var totalSpendCTCAbove100k = 0;
				
				var totalPercntTotalSpendCTCBelow20k = 0;
				var totalPercntTotalSpendCTCBet20kto35k = 0;
				var totalPercntTotalSpendCTCBet35kto50k = 0;
				var totalPercntTotalSpendCTCBet50kto100k = 0;
				var totalPercntTotalSpendCTCAbove100k = 0;
				
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					if(item.ctc < 20000){
						totalSpendCTCBelow20k = totalSpendCTCBelow20k + item.ctc;
					}
					
					if (item.ctc > 20000 && item.ctc <= 35000) {
					    totalSpendCTCBet20kto35k = totalSpendCTCBet20kto35k + item.ctc;
					}
					
					if (item.ctc > 35000 && item.ctc <= 50000) {
					    totalSpendCTCBet35kto50k = totalSpendCTCBet35kto50k + item.ctc;
					}
					
					if (item.ctc > 50000 && item.ctc <= 100000) {
					    totalSpendCTCBet50kto100k = totalSpendCTCBet50kto100k + item.ctc;
					}
					
					if (item.ctc > 100000) {
					    totalSpendCTCAbove100k = totalSpendCTCAbove100k + item.ctc;
					}
				}
				
				// Calculate percentages for each spend category, checking for zero values
				totalPercntTotalSpendCTCBelow20k = (totalSpendCTCBelow20k === 0) ? 0 : (totalSpendCTCBelow20k / totalSalarySpend) * 100;
				totalPercntTotalSpendCTCBet20kto35k = (totalSpendCTCBet20kto35k === 0) ? 0 : (totalSpendCTCBet20kto35k / totalSalarySpend) * 100;
				totalPercntTotalSpendCTCBet35kto50k = (totalSpendCTCBet35kto50k === 0) ? 0 : (totalSpendCTCBet35kto50k / totalSalarySpend) * 100;
				totalPercntTotalSpendCTCBet50kto100k = (totalSpendCTCBet50kto100k === 0) ? 0 : (totalSpendCTCBet50kto100k / totalSalarySpend) * 100;
				totalPercntTotalSpendCTCAbove100k = (totalSpendCTCAbove100k === 0) ? 0 : (totalSpendCTCAbove100k / totalSalarySpend) * 100;
				
				var totalPercntTotalSpendCTCBelow20kInt = parseInt(totalPercntTotalSpendCTCBelow20k);
				var totalPercntTotalSpendCTCBet20kto35kInt = parseInt(totalPercntTotalSpendCTCBet20kto35k);
				var totalPercntTotalSpendCTCBet35kto50kInt = parseInt(totalPercntTotalSpendCTCBet35kto50k);
				var totalPercntTotalSpendCTCBet50kto100kInt = parseInt(totalPercntTotalSpendCTCBet50kto100k);
				var totalPercntTotalSpendCTCAbove100kInt = parseInt(totalPercntTotalSpendCTCAbove100k);
				
				//alert("totalPercntTotalSpendCTCBelow20kInt--------"+totalPercntTotalSpendCTCBelow20kInt);
				//alert("totalPercntTotalSpendCTCBet20kto35kInt--------"+totalPercntTotalSpendCTCBet20kto35kInt);
				//alert("totalPercntTotalSpendCTCBet35kto50kInt--------"+totalPercntTotalSpendCTCBet35kto50kInt);
				//alert("totalPercntTotalSpendCTCBet50kto100kInt--------"+totalPercntTotalSpendCTCBet50kto100kInt);
				//alert("totalPercntTotalSpendCTCAbove100kInt--------"+totalPercntTotalSpendCTCAbove100kInt);
				
			/*	alert(
				    'Total Spend Below 20k: ' + totalSpendCTCBelow20k + '\n' +
				    'Total Spend Between 20k and 35k: ' + totalSpendCTCBet20kto35k + '\n' +
				    'Total Spend Between 35k and 50k: ' + totalSpendCTCBet35kto50k + '\n' +
				    'Total Spend Between 50k and 100k: ' + totalSpendCTCBet50kto100k + '\n' +
				    'Total Spend Above 100k: ' + totalSpendCTCAbove100k
				);*/
				
				// Optional: You can alert or log these values to check
				/*alert(
				    'Percentage Below 20k: ' + totalPercntTotalSpendCTCBelow20k.toFixed(2) + '%\n' +
				    'Percentage Between 20k and 35k: ' + totalPercntTotalSpendCTCBet20kto35k.toFixed(2) + '%\n' +
				    'Percentage Between 35k and 50k: ' + totalPercntTotalSpendCTCBet35kto50k.toFixed(2) + '%\n' +
				    'Percentage Between 50k and 100k: ' + totalPercntTotalSpendCTCBet50kto100k.toFixed(2) + '%\n' +
				    'Percentage Above 100k: ' + totalPercntTotalSpendCTCAbove100k.toFixed(2) + '%'
				);
				*/
				
				
	
				//Emplyoee Count		
   
			   Highcharts.chart('empcount', {
			       chart: {
			         plotBackgroundColor: null,
			         plotBorderWidth: 0,
			         plotShadow: false,
			         height:250
			       },
			       title: {
			         text: '',
			         align: 'center',
			         verticalAlign: 'middle',
			         y: 60
			       },
			       tooltip: {
			         pointFormat: '{series.name}: <b>{point.percentage:1f}%</b>'
			       },
			       accessibility: {
			         point: {
			           valueSuffix: '%'
			         }
			       },
			         credits: {
			         enabled: false
			       },
			       
			       exporting: { enabled: false },
			       plotOptions: {
			         pie: {
			         colors: [
			                     '#CA7CE5',
			                     '#bf05ff',
			                     '#B422B6',
			                     '#56156C',	
			                     '#F79C92'	
			                 ],
			           dataLabels: {
			             enabled: true,
			              format: '<span style="font-size: 12px"><b>{point.name}</b></span><br>' +
			                         '<span style="opacity: 1">{point.percentage:1f} %</span>',
			             distance: 10,
			             style: {
			               fontWeight: 'normal',
			               color: 'black'
			             }
			           },
			           startAngle: -180,
			           endAngle: 180,
			           center: ['50%', '50%'],
			           size: '70%'
			         }
			       },
			       series: [{
			         type: 'pie',
			         name: '',
			         innerSize: '50%',
			         data: [
			           ['>20K',totalPercntTotalSpendCTCBelow20kInt],
			           ['20-35K',totalPercntTotalSpendCTCBet20kto35kInt],
			           ['35-50K',totalPercntTotalSpendCTCBet35kto50kInt],
			           ['50-100K',totalPercntTotalSpendCTCBet50kto100kInt],
			           ['<100K',totalPercntTotalSpendCTCAbove100kInt]
			           
			   
			           
			         ]
			       }]
			     });	
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-compensation-joining-leaving-trend?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var monthYearList = [];
				var employeeJoiningCountList = [];
				var employeeLeavingCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					monthYearList.push(item.monthYear);
					employeeJoiningCountList.push(item.employeeJoiningCount);
					employeeLeavingCountList.push(item.employeeLeavingCount);
				}
				
				   Highcharts.chart('trendline', {	
				       chart: {
				           type: 'spline',
				           height:220
				       },
				       title: {
				                  text: ''
				               },
				      
				       xAxis: {
				           categories: monthYearList,//['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
				               
				           accessibility: {
				               description: 'Months of the year'
				           }
				       },
				       yAxis: {
				           title: {
				               text: ''
				           },
				           labels: {
				               format: '{value}'
				           }
				       },
				       tooltip: {
				           crosshairs: true,
				           shared: true
				       },
				       plotOptions: {
				           spline: {
				           
				               marker: {
				                   radius: 4,
				                   lineColor: '#666666',
				                   lineWidth: 1
				               }
				           }
				       },
				       
				       navigation: {
				           buttonOptions: {
				               enabled: false
				           }
				       },
				       
				       credits: {
				       enabled: false
				       },
				       
				       series: [{
				           name: 'Emplyoees Joining',
				           marker: {
				               symbol: 'circle'
				           },
				           data: employeeJoiningCountList,//[11, 8, 10, 14, 9, 15],
				           color: '#BF05FF'
				   
				       }, {
				           name: 'Emplyoees Leaving',
				           marker: {
				               symbol: 'circle'
				           },
				           data: employeeLeavingCountList,//[10, 12, 8, 15, 8, 14],
				           color: '#F79C92'
				       }]
				   });	
								
			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	
	$.ajax({
		type: "GET",	
		url: "hrms-dashboard-compensation-salary-by-performance?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
			/*	var jobRoleList = [];
				var maleCountList = [];
				var femaleCountList = [];
				var othersCountList = [];
					
				for (var i = 0; i < allData.length; i++) {
					
					var item = allData[i];
					jobRoleList.push(item.jobRoleName);
					maleCountList.push(item.maleCount);
					femaleCountList.push(item.femaleCount);
					othersCountList.push(item.othersCount);
				}
				*/
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
   
   
   
   $.ajax({
		type: "GET",	
		url: "hrms-dashboard-compensation-avg-salary-year-range-by?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
	
		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				var lessThan1YearMaleSalarySpend = 0; 
				var lessThan1YearFemaleSalarySpend = 0; 
				var lessThan1YearSalarySpend = 0; 
				var lessThan1YearMalePercentage = 0;
				var lessThan1YearFemalePercentage = 0;
				
				var between1YearTo2YearMaleSalarySpend = 0; 
				var between1YearTo2YearFemaleSalarySpend = 0; 
				var between1YearTo2YearSalarySpend = 0; 
				var between1YearTo2YearMalePercentage = 0; 
				var between1YearTo2YearFemalePercentage = 0; 
				
				var between2YearTo3YearMaleSalarySpend = 0; 
				var between2YearTo3YearFemaleSalarySpend = 0; 
				var between2YearTo3YearSalarySpend = 0; 
				var between2YearTo3YearMalePercentage = 0; 
				var between2YearTo3YearFemalePercentage = 0; 
				
				var between3YearTo5YearMaleSalarySpend = 0; 
				var between3YearTo5YearFemaleSalarySpend = 0; 
				var between3YearTo5YearSalarySpend = 0; 
				var between3YearTo5YearMalePercentage = 0; 
				var between3YearTo5YearFemalePercentage = 0; 
				
				var greaterThan5YearMaleSalarySpend = 0; 
				var greaterThan5YearFemaleSalarySpend = 0; 
				var greaterThan5YearSalarySpend = 0; 
				var greaterThan5YearMalePercentage = 0; 
				var greaterThan5YearFemalePercentage = 0;
				
				var malePercentageList = [];
				var femalePercentageList = [];
				for (var i = 0; i < allData.length; i++) {					
					var item = allData[i];
					if(item.joiningYear <1){
						if(item.gender == 'Male'){
							lessThan1YearMaleSalarySpend = lessThan1YearMaleSalarySpend + item.ctc;
						}else{
							lessThan1YearFemaleSalarySpend = lessThan1YearFemaleSalarySpend + item.ctc;
						}
						
						lessThan1YearSalarySpend = lessThan1YearSalarySpend + item.ctc;
					}
					
					
					if(item.joiningYear >=1 && item.joiningYear <=2){
						if(item.gender == 'Male'){
							between1YearTo2YearMaleSalarySpend = between1YearTo2YearMaleSalarySpend + item.ctc;
						}else{
							between1YearTo2YearFemaleSalarySpend = between1YearTo2YearFemaleSalarySpend + item.ctc;
						}
						
						between1YearTo2YearSalarySpend = between1YearTo2YearSalarySpend + item.ctc;
					}
					
					if(item.joiningYear >=2 && item.joiningYear <=3){
						if(item.gender == 'Male'){
							between2YearTo3YearMaleSalarySpend = between2YearTo3YearMaleSalarySpend + item.ctc;
						}else{
							between2YearTo3YearFemaleSalarySpend = between2YearTo3YearFemaleSalarySpend + item.ctc;
						}
						
						between2YearTo3YearSalarySpend = between2YearTo3YearSalarySpend + item.ctc;
					}
					
					if(item.joiningYear >=3 && item.joiningYear <=5){
						if(item.gender == 'Male'){
							between3YearTo5YearMaleSalarySpend = between3YearTo5YearMaleSalarySpend + item.ctc;
						}else{
							between3YearTo5YearFemaleSalarySpend = between3YearTo5YearFemaleSalarySpend + item.ctc;
						}
						
						between3YearTo5YearSalarySpend = between3YearTo5YearSalarySpend + item.ctc;
					}
					
					if(item.joiningYear >5 ){
						if(item.gender == 'Male'){
							greaterThan5YearMaleSalarySpend = greaterThan5YearMaleSalarySpend + item.ctc;
						}else{
							greaterThan5YearFemaleSalarySpend = greaterThan5YearFemaleSalarySpend + item.ctc;
						}
						
						greaterThan5YearSalarySpend = greaterThan5YearSalarySpend + item.ctc;
					}
					
				}
				
			
				
				
				if(lessThan1YearMaleSalarySpend >0){
				 	lessThan1YearMalePercentage = (lessThan1YearMaleSalarySpend/lessThan1YearSalarySpend)*100;
				 	lessThan1YearFemalePercentage = 100-lessThan1YearMalePercentage;
				}
				
				 if(between1YearTo2YearMaleSalarySpend >0){
				 	between1YearTo2YearMalePercentage = (between1YearTo2YearMaleSalarySpend/between1YearTo2YearSalarySpend)*100;
				 	between1YearTo2YearFemalePercentage = 100-between1YearTo2YearMalePercentage;
				 }
				 if(between2YearTo3YearMaleSalarySpend >0){
					between2YearTo3YearMalePercentage = (between2YearTo3YearMaleSalarySpend/between2YearTo3YearSalarySpend)*100;
				 	between2YearTo3YearFemalePercentage = 100-between2YearTo3YearMalePercentage;
				 
				}
				 
				 if(between3YearTo5YearMaleSalarySpend > 0){
					 between3YearTo5YearMalePercentage = (between3YearTo5YearMaleSalarySpend/between3YearTo5YearSalarySpend)*100;
				 	between3YearTo5YearFemalePercentage = 100-between3YearTo5YearMalePercentage;
				 
				}
				
				
				 if(greaterThan5YearMaleSalarySpend > 0){
					greaterThan5YearMalePercentage = (greaterThan5YearMaleSalarySpend/greaterThan5YearSalarySpend)*100;
				 	greaterThan5YearFemalePercentage = 100-greaterThan5YearMalePercentage;
				 
				}
				
				
				// Push the values into the malePercentageList
				malePercentageList.push(lessThan1YearMalePercentage);
				malePercentageList.push(between1YearTo2YearMalePercentage);
				malePercentageList.push(between2YearTo3YearMalePercentage);
				malePercentageList.push(between3YearTo5YearMalePercentage);
				malePercentageList.push(greaterThan5YearMalePercentage);
				
				// Push the values into the femalePercentageList
				femalePercentageList.push(lessThan1YearFemalePercentage);
				femalePercentageList.push(between1YearTo2YearFemalePercentage);
				femalePercentageList.push(between2YearTo3YearFemalePercentage);
				femalePercentageList.push(between3YearTo5YearFemalePercentage);
				femalePercentageList.push(greaterThan5YearFemalePercentage);
				
				// Alert the updated lists
			//	alert("Male Percentage List: " + malePercentageList.join(', '));
			//	alert("Female Percentage List: " + femalePercentageList.join(', '));

				
			  //Average Salary
   
			   Highcharts.chart('avgsalary', {
			     chart: {
			       type: 'column',
			       height:250
			     },
			     title: {
			       text: ''
			     },
			     subtitle: {
			       text: ''
			     },
			     xAxis: {
			       categories: [
			         '>1Y',
			         '1-2Y',
			         '2-3Y',
			         '3-5Y',
			         '5Y+'
			       ],
			       crosshair: true
			     },
			      exporting: { enabled: false },
			    credits: {
			       enabled: false
			       },
			     yAxis: {
			       min: 0,
			       title: {
			         text: ''
			       }
			     },
			     tooltip: {
			       headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
			       pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
			         '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
			       footerFormat: '</table>',
			       shared: true,
			       useHTML: true
			     },
			     plotOptions: {
			       column: {
			         pointPadding: 0.2,
			         pointWidth: 16,
			         borderWidth: 1
			       }
			     },
			     series: [{
			       name: 'Male',
			       data: malePercentageList,
			       color:'#bf05ff'
			     }, {
			       name: 'Female',
			       data: femalePercentageList,
			       color:'#CA7CE5'
			     }
			      
			     ]
			   });	
												
			}
		}, error: function(data) {
			console.log(data);
		}
	})
   
      
       
 
       
       
   //Average Salary
   
   Highcharts.chart('jobrolechart', {
     chart: {
       type: 'column',
       height:250
     },
     title: {
       text: ''
     },
     subtitle: {
       text: ''
     },
     xAxis: {
       categories: [
         '0-3Y',
         '3-5Y',
         '5-10Y',
         '10-15Y',
         '15-20Y',
         '20+Y'
       ],
       crosshair: true
     },
      exporting: { enabled: false },
    credits: {
       enabled: false
       },
     yAxis: {
       min: 0,
       title: {
         text: ''
       }
     },
     tooltip: {
       headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
       pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
         '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
       footerFormat: '</table>',
       shared: true,
       useHTML: true
     },
     plotOptions: {
       column: {
         pointPadding: 0.2,
         pointWidth: 16,
         borderWidth: 1
       }
     },
     series: [{
       name: 'Male',
       data: [49.9, 71.5, 23.5,40,35,65],
       color:'#bf05ff'
     }, {
       name: 'Female',
       data: [83.6, 78.8, 45.5,25,20,35],
       color:'#CA7CE5'
     }
    
      
     ]
   });	
       
       
  /*           
 Highcharts.chart('chartContainer', {
    chart: {
        type: 'bar',
        height: 200,
        spacingTop: 0,
        spacingBottom: 0,
        spacingLeft: 0,
        spacingRight: 0,
        groupPadding: 0,  // Remove the padding between groups
        pointPadding: 0   // Remove the padding between individual bars
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['IT','Marketing','Sales','Administration','Engineering'],  // Set the categories directly
        visible: false,
        title: {
            text: ''
        },
        labels: {
            enabled: true
        },
        tickmarkPlacement: 'on',
        gridLineWidth: 0, // Remove the grid lines for a cleaner look
    },
    yAxis: {
        gridLineColor: 'transparent',
        title: {
            text: false,
            visible: false,
            lineWidth: 0,
            minorGridLineWidth: 0,
            lineColor: 'transparent'
        },
        labels: {
            enabled: false
        }
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                style: {
                    color: 'black',
                    textOutline: 'none',
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.series.name + ': ' + this.y; // Display name and value
                },
                align: 'center' // Align the labels to the left side
            }
        }
    },
    navigation: {
        buttonOptions: {
            enabled: false
        }
    },
    credits: {
        enabled: false
    },
    legend: {
        enabled: false
    },
    series: [
        {
            name: 'IT',
            data: [5],
            color: '#F79C92'
        }, {
            name: 'Sales',
            data: [2],
            color: '#E976AD'
        },
        {
            name: 'Marketing',
            data: [12],
            color: '#DB5199'
        }, 
        {
            name: 'Administration',
            data: [3],
            color: '#DB51C9'
        },{
            name: 'Engineering',
            data: [8],
            color: '#BF05FF'
        },
    ]
});

*/
/*
Highcharts.chart('salaryperformance', {
    chart: {
        type: 'column',
        marginTop: 0,
        marginBottom: 110,
        plotBorderWidth: 1,
        height: 250
    },

    title: {
        text: '',
        style: {
            fontSize: '8px'
        }
    },

    xAxis: {
        categories: ['0-3 Y', '3-5 Y', '5-10 Y', '15-20 Y', '20+ Y'],
        title: {
            text: '',
            style: {
                fontSize: '8px'
            }
        }
    },

    yAxis: {
        min: 0,
        title: {
            text: ''
        },
        stackLabels: {
            enabled: true,
            style: {
                fontWeight: 'bold',
                color: '#000000'
            }
        }
    },

    tooltip: {
        pointFormat: '<b>{series.name}</b><br>' +
            'Numbers: <b>{point.y}</b><br>' +
            'For: <b>{point.category}</b>'
    },

    plotOptions: {
        column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                color: '#000000',
                style: {
                    textOutline: false
                }
            }
        }
    },
    legend: {
        labelStyle: {
            fontSize: '8px' // Adjust the font size here for series names
        }
    },

    navigation: {
        buttonOptions: {
            enabled: false
        }
    },

    series: [{
        name: '<50K',
        data: [4, 6, 7, 8, 7],
        color: '#CA7CE5',
        fontSize: '8px'
    }, {
        name: '51K-100K',
        data: [11, 12, 23, 43, 21],
        color: '#BF05FF',
        fontSize: '8px'
    }, {
        name: '101K-150K',
        data: [14, 12, 32, 34, 29],
        color: '#F4DEFC',
        fontSize: '8px'
    }, {
        name: '151K-300K',
        data: [30, 20, 21, 19, 17],
        color: '#9C27B0',
        fontSize: '8px',
    }, {
        name: '301K+',
        data: [23, 20, 44, 15, 8],
        color: '#673AB7',
        fontSize: '8px'
    }],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                yAxis: {
                    labels: {
                        format: '{value}'
                    }
                }
            }
        }]
    }
});

*/
   
   //Salary Performance
   
   
   
   // Substring template helper for the responsive labels
   Highcharts.Templating.helpers.substr = (s, from, length) =>
       s.substr(from, length);
   
   // Create the chart
   Highcharts.chart('salaryperformance', {
   
       chart: {
           type: 'heatmap',
           marginTop: 10,
           marginBottom: 40,
           plotBorderWidth: 1,
           height:240
       },
   
   
       title: {
           text: '',
           style: {
               fontSize: '1em'
           }
       },
       
   
       xAxis: {
           categories: ['95+', '90+', '80+', '70+', 'Below 70' ]
              
       },
   
       yAxis: {
           categories: ['0-3 Y', '3-5 Y', '5-10 Y', '15-20 Y', '20+ Y'],
           title: null,
           reversed: true
       },
   
       accessibility: {
           point: {
               descriptionFormat: '{(add index 1)}. ' +
                   '{series.xAxis.categories.(x)} sales ' +
                   '{series.yAxis.categories.(y)}, {value}.'
           }
       },
       
       
   
    colorAxis: {
       reversed: false,
       min: 0,
       stops: [
           [0, '#FFD9D5'],
           [0.5, '#BF05FF'],
           [1, '#F4DEFC']
       ]
   },
   
       legend: {
           align: 'right',
           layout: 'vertical',
           margin: 0,
           verticalAlign: 'top',
           y: 25,
           symbolHeight: 280
       },
   
       tooltip: {
           format: '<b>{series.xAxis.categories.(point.x)}</b> sold<br>' +
               '<b>{point.value}</b> items on <br>' +
               '<b>{series.yAxis.categories.(point.y)}</b>'
       },
       
       navigation: {
           buttonOptions: {
               enabled: false
           }
       },
       
       credits: {
       enabled: false
       },
   
       series: [{
           name: 'Sales per employee',
           borderWidth: 1,
           borderColor: '#F2CEFF',
           data: [[0, 0, 4], [0, 1, 6], [0, 2, 7], [0, 3, 8], [0, 4, 7],
               [1, 0, 11], [1, 1, 12], [1, 2, 23], [1, 3, 43], [1, 4, 21],
               [2, 0, 14], [2, 1, 12], [2, 2, 32], [2, 3, 34], [2, 4, 29],
               [3, 0, 30], [3, 1, 20], [3, 2, 21], [3, 3, 19], [3, 4, 17],
               [4, 0, 23], [4, 1, 20], [4, 2, 44], [4, 3, 15], [4, 4, 8]
              ],
           dataLabels: {
               enabled: true,
               color: '#000000',
               style: {
                textOutline: false 
              }
           }
       }],
   
       responsive: {
           rules: [{
               condition: {
                   maxWidth: 500
               },
               chartOptions: {
                   yAxis: {
                       labels: {
                           format: '{substr value 0 1}'
                       }
                   }
               }
           }]
       }
   
   });
   
   
   
   }