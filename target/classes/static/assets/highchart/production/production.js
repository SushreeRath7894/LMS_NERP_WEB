function productionHighChat(){
	
	var org = $("#productionOrganization").val();
	var orgDiv = $("#productionDivision").find('option:selected').text();
	var location = $("#productionLocation").find('option:selected').text();
	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();
	
	
		
	$.ajax({
		type: "GET",
		url: "dashboard-production-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,

		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				
				$("#productionVolumnInTonId").text(allData[0].productionVolumnInTon);
				$("#activeMachineTotalId").text(allData[0].activeMachineTotal);
				$("#totalOrderedQtyNosId").text(allData[0].totalOrderedQtyNos);
				$("#salesRavenueInMillionId").text(allData[0].salesRavenueInMillion);

			}
		}, error: function(data) {
			console.log(data);
		}
	})
	
	
	$.ajax({
		url: "dashboard-avg-monthly-sales-details?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-top-five-product-revenue?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData;
			$("#topFiveRevenueSalesId").empty();
			const colors = ['#9792e8', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5'];
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				var productName= item.productName;
				var totalQty= item.totalQty;
				var totalSalesValue= parseInt(item.totalSalesValue);
				var chartId = 	"manProdTopFive"+ (i+1);		
	   		//alert("chartId--------"+chartId);
	   		//// <td>${i+1}</td>	
		   		var top5Revenue = `<tr>
	                   
	                    <td align="left">${productName}</td>
	                    <td align="left">${totalSalesValue}</td>
	                    <td align="left">${totalQty}</td>
	                    <td align="left">
	                        <div id="${chartId}"></div>
	                    </td>
	                </tr>`;
	
				$("#topFiveRevenueSalesId").append(top5Revenue);
				
			Highcharts.chart(chartId, {
			chart: {
				type: 'column',
				height: 40,
				marginTop: 0,
				animation: true,
			},
			credits: {
				enabled: false
			},
			title: {
				text: '',
			},
			subtitle: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			xAxis: {
				type: 'category',
				tickLength: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false
				}
			},
			yAxis: {
				min: 0,
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				}
			},
			legend: {
				enabled: false
			},
			// tooltip: {
			//     pointFormat: 'Average weekly sales: <b>{point.y:.1f} thousand</b>'
			// },
			series: [{
				name: '',
				colors: [colors[i]],
				colorByPoint: true,
				groupPadding: 0,
				pointWidth: 5,
				data: [
					['1', 15],
					['2', 20],
					['3', 45],
					['4', 55],
					['5', 35],
					['6', 40],
					['7', 67],
					['8', 32],
					['9', 21],
					['10', 23],
					['11', 65],
					['12', 70],
					['13', 25],
					['14', 65],
					['15', 70],
					['16', 25],
					['17', 65],
					['18', 70],
					['19', 25],
					['20', 65],
					['21', 70],
					['22', 25],
					['23', 70],
					['24', 25]
				],
				dataLabels: {
					enabled: false,
				}
			}]
		});
		
		
								
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-top-five-product-by-production?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData;
			var totalLength = 0;
			totalLength = allData.length;
			
			var machineName1 = "";
			var machineName2 = "";
			var machineName3 = "";
			var machineName4 = "";
			var machineName5 = "";
			
			
			var machine1production = "";
			var machine2production = "";
			var machine3production = "";
			var machine4production = "";
			var machine5production = "";
			
			if(totalLength>0){
				machineName1 = allData[0].machineName;
				machine1production = allData[0].convertionInMtTotalProduction;
			}
			if(totalLength>1){
				machineName2 = allData[1].machineName;
				machine2production = allData[1].convertionInMtTotalProduction;
			}
			if(totalLength>2){
				machineName3 = allData[2].machineName;
				machine3production = allData[2].convertionInMtTotalProduction;
			}
			if(totalLength>3){
				machineName4 = allData[3].machineName;
				machine4production = allData[3].convertionInMtTotalProduction;
			}
			if(totalLength>4){
				machineName5 = allData[4].machineName;
				machine5production = allData[4].convertionInMtTotalProduction;
			}
				
		
			
			
			
			var totalProductionInTone = allData[0].totalProduction;
			if(machine1production == 0){
				machine1ProductionPercentage=0.00;
			}else{
				machine1ProductionPercentage = ((machine1production/totalProductionInTone)*100);
			}
			
			if(machine2production == 0){
				machine2ProductionPercentage=0.00;
			}else{
				machine2ProductionPercentage = ((machine2production/totalProductionInTone)*100);
			}
			
			if(machine3production == 0){
				machine3ProductionPercentage=0.00;
			}else{
				machine3ProductionPercentage = ((machine3production/totalProductionInTone)*100);
			}
			
			if(machine4production == 0){
				machine4ProductionPercentage=0.00;
			}else{
				machine4ProductionPercentage = ((machine4production/totalProductionInTone)*100);
			}
			
			if(machine5production == 0){
				machine5ProductionPercentage=0.00;
			}else{
				machine5ProductionPercentage = ((machine5production/totalProductionInTone)*100);
			}
			
			
			
			//alert("machine1ProductionPercentage--------------"+machine1ProductionPercentage);
			//man prod volume by top 5 machines 1
		Highcharts.chart('manProductionVolumeByMachineOne', {
			chart: {
				type: 'pie',
				height: 130,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#56156C', '#dfdfdf'],
			title: {
				text: parseFloat(machine1ProductionPercentage).toFixed(2) + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: machineName1,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: {"fontSize": '9', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [{
				name: '',
				data: [[machineName1, 65], ["", 35]],
				size: '120%',
				innerSize: '80%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
		//man prod volume by top 5 machines 2
		Highcharts.chart('manProductionVolumeByMachineTwo', {
			chart: {
				type: 'pie',
				height: 130,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#F79C92', '#dfdfdf'],
			title: {
				text: parseFloat(machine2ProductionPercentage).toFixed(2) + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: machineName2,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: {"fontSize": '9', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [{
				name: '',
				data: [[machineName2, 65], ["", 35]],
				size: '120%',
				innerSize: '80%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
		//man prod volume by top 5 machines 3
		Highcharts.chart('manProductionVolumeByMachineThree', {
			chart: {
				type: 'pie',
				height: 130,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#bf05ff', '#dfdfdf'],
			title: {
				text: parseFloat(machine3ProductionPercentage).toFixed(2) + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: machineName3,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: {"fontSize": '9', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [{
				name: '',
				data: [[machineName3, 65], ["", 35]],
				size: '120%',
				innerSize: '80%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
		//man prod volume by top 5 machines 4
		Highcharts.chart('manProductionVolumeByMachineFour', {
			chart: {
				type: 'pie',
				height: 130,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#B422B6', '#dfdfdf'],
			title: {
				text: parseFloat(machine4ProductionPercentage).toFixed(2) + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: machineName4,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: {"fontSize": '9', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [{
				name: '',
				data: [[machineName4, 65], ["", 35]],
				size: '120%',
				innerSize: '80%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
		//man prod volume by top 5 machines 5
		Highcharts.chart('manProductionVolumeByMachineFive', {
			chart: {
				type: 'pie',
				height: 130,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#CA7CE5', '#dfdfdf'],
			title: {
				text: parseFloat(machine5ProductionPercentage).toFixed(2) + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: machineName5,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: {"fontSize": '9', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [{
				name: '',
				data: [[machineName5, 65], ["", 35]],
				size: '120%',
				innerSize: '80%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
		
			
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
	$.ajax({
		url: "dashboard-machine-breakdown?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			
			var jsonData = JSON.parse(response.body);
			var allData = jsonData;
			
			var totalBreakdownTimeInHours = allData[0].totalBreakdownTimeInHours;
			var totalWorkingHours =  allData[0].totalWorkingHours;
			var breakDownMAchinePercentage = allData[0].breakDownMAchinePercentage;
			var machineBrokenPercentage = allData[0].machineBrokenPercentage; 
			var missingMachinePercentage = allData[0].missingMachinePercentage; 
			var machineServicePercentage = allData[0].machineServicePercentage; 
			
			$("#brokenMachineProductionId").text(machineBrokenPercentage + "%")
			$("#missingPartsProductionId").text(missingMachinePercentage + "%")
			$("#serviceProductionId").text(machineServicePercentage + "%")
			
			
			
			
			
			
			
			Highcharts.chart('manDowntimeCauses', {
			chart: {
				type: 'pie',
				height: 250,
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			colors: ['#56156C', '#F79C92', '#bf05ff'],
			title: {
				text: parseFloat(breakDownMAchinePercentage).toFixed(2) + "%",
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 110,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
			},
			subtitle: {
				text: 'Downtime',
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 130,
				margin: 0,
				style: {"fontSize": '12', "color": '#000000'}
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			plotOptions: {
				pie: {
					shadow: false
				}
			},
			series: [
					{
						name: "",
						data: [
							["Broken Macine", machineBrokenPercentage],
							["Missing Parts", missingMachinePercentage],
							["Service", machineServicePercentage],
						],
						size: "120%",
						innerSize: "80%",
						showInLegend: false,
						dataLabels: {
							enabled: false,
						},
					},
				]
		});
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-return-item-by-reason?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			//var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
					
	var jsonData = JSON.parse(response.body);
	var allData = jsonData.dashboardData;
	
	// Step 1: Create an object to store aggregated data by monthYear and reasonName
	var aggregatedData = {};
	
	// Loop through the dashboardData and accumulate totalCount for each monthYear and reasonName
	allData.forEach(item => {
	    var monthYear = item.monthYear;
	    var reasonName = item.reasonName;
	    var totalCount = item.totalCount;
	
	    // Initialize the monthYear object if it doesn't exist
	    if (!aggregatedData[monthYear]) {
	        aggregatedData[monthYear] = {};
	    }
	
	    // Initialize the reasonName array if it doesn't exist for the specific monthYear
	    if (!aggregatedData[monthYear][reasonName]) {
	        aggregatedData[monthYear][reasonName] = 0;
	    }
	
	    // Accumulate the totalCount
	    aggregatedData[monthYear][reasonName] += totalCount;
	});
	
	// Step 2: Prepare the series array
	var series = [
	    { name: 'Returned Due to Damage Product', data: [], color: '#B422B6' },
	    { name: 'Poor Quality', data: [], color: '#F79C92' },
	    { name: 'UnMatched Specimen', data: [], color: '#FPOK92' },
	    { name: 'Size Issue', data: [], color: '#bf05ff' }
	];
	
	// Step 3: Populate the data for each reasonName in the series array
	var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
	
	monthYeartList.forEach(monthYear => {
	    // Push totalCount for each reason into the series data
	    series[0].data.push(aggregatedData[monthYear]["Returned Due to Damage Product"] || 0);
	    series[1].data.push(aggregatedData[monthYear]["Poor Quality"] || 0);
	    series[2].data.push(aggregatedData[monthYear]["UnMatched Specimen"] || 0);
	    series[3].data.push(aggregatedData[monthYear]["Size Issue"] || 0);
	});
	
	console.log(series);
			
			
			
	Highcharts.chart('turnoverRateByAgeGroup', {
    chart: {
        type: 'column',
        animation: true,
        height: 250,
    },
    title: {
        text: ''
    },
    credits: {
        enabled: false
    },
    navigation: {
        buttonOptions: {
            enabled: false
        }
    },
    legend: {
        enabled: true,  // Enable the legend
        layout: 'horizontal',  // Arrange legend items horizontally
        align: 'center',  // Align it in the center
        verticalAlign: 'bottom',  // Place it at the bottom
        itemStyle: {
            fontWeight: 'normal',
            fontSize: '14px',
            color: '#333'  // Set the color of legend text
        }
    },
    xAxis: {
        categories: monthYeartList,
        rotation: '45deg',
    },
    yAxis: {
        title: {
            text: ''
        },
        labels: {enabled: false},
        stackLabels: {
            enabled: true
        }
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: false,
                color: '#ffffff'
            }
        }
    },
    series: series
});

			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
	
	
		//man Quick Stats One
		Highcharts.chart('manQuickStatsOne', {
			chart: {
				type: 'line',
				animation: true,
				backgroundColor: 'transparent',
				height: 40,
				margin: 10
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},

			credits: {
				enabled: false
			},
			xAxis: {
				tickLength: 0,
				tickWidth: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false,
				},
				categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
			},
			yAxis: {
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				},
				showInLegend: false
			},
			plotOptions: {
				line: {
					dataLabels: {
						enabled: false
					},
					showInLegend: false,
					enableMouseTracking: false
				}
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			series: [{
				name: '',
				data: [16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2],
				color: '#bf05ff',
				showInLegend: false,
				marker: {
					enabled: false
				}
			},]
		});
		//man Quick Stats Two
		Highcharts.chart('manQuickStatsTwo', {
			chart: {
				type: 'column',
				height: 50,
				animation: true,
			},
			credits: {
				enabled: false
			},
			title: {
				text: '',
			},
			subtitle: {
				text: ''
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			xAxis: {
				type: 'category',
				tickLength: 0,
				lineColor: 'transparent',
				labels: {
					enabled: false
				}
			},
			yAxis: {
				min: 0,
				gridLineColor: 'transparent',
				title: {
					text: ''
				},
				labels: {
					enabled: false
				}
			},
			legend: {
				enabled: false
			},
			// tooltip: {
			//     pointFormat: 'Average weekly sales: <b>{point.y:.1f} thousand</b>'
			// },
			series: [{
				name: 'Average monthly sales',
				colors: ['#F79C92'],
				colorByPoint: true,
				groupPadding: 0,
				pointWidth: 5,
				data: [
					['1', 15],
					['2', 20],
					['3', 45],
					['4', 55],
					['5', 35],
					['6', 40],
					['7', 67],
					['8', 32],
					['9', 21],
					['10', 23],
					['11', 65],
					['12', 70],
					['13', 25],
					['14', 65],
					['15', 70],
					['16', 25],
					['17', 65],
					['18', 70],
					['19', 25],
					['20', 65],
					['21', 70],
					['22', 25],
					['23', 70],
					['24', 25]
				],
				dataLabels: {
					enabled: false,
				}
			}]
		});
			
}

function returnByReason(id){
		var org = $("#productionOrganization").val();
		var orgDiv = $("#productionDivision").find('option:selected').text();
		var location = $("#productionLocation").find('option:selected').text();
		var fromDate = $("#fromDate2").val();
		var toDate = $("#toDate2").val();
	
		$.ajax({
		url: "dashboard-return-item-by-reason-by-type?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&type=" + id,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			//var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
					
	var jsonData = JSON.parse(response.body);
	var allData = jsonData.dashboardData;
	
	// Step 1: Create an object to store aggregated data by monthYear and reasonName
	var aggregatedData = {};
	
	// Loop through the dashboardData and accumulate totalCount for each monthYear and reasonName
	allData.forEach(item => {
	    var monthYear = item.monthYear;
	    var reasonName = item.reasonName;
	    var totalCount = item.totalCount;
	
	    // Initialize the monthYear object if it doesn't exist
	    if (!aggregatedData[monthYear]) {
	        aggregatedData[monthYear] = {};
	    }
	
	    // Initialize the reasonName array if it doesn't exist for the specific monthYear
	    if (!aggregatedData[monthYear][reasonName]) {
	        aggregatedData[monthYear][reasonName] = 0;
	    }
	
	    // Accumulate the totalCount
	    aggregatedData[monthYear][reasonName] += totalCount;
	});
	
	// Step 2: Prepare the series array
	var series = [
	    { name: 'Returned Due to Damage Product', data: [], color: '#B422B6' },
	    { name: 'Poor Quality', data: [], color: '#F79C92' },
	    { name: 'UnMatched Specimen', data: [], color: '#FPOK92' },
	    { name: 'Size Issue', data: [], color: '#bf05ff' }
	];
	
	// Step 3: Populate the data for each reasonName in the series array
	var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
	
	monthYeartList.forEach(monthYear => {
	    // Push totalCount for each reason into the series data
	    series[0].data.push(aggregatedData[monthYear]["Returned Due to Damage Product"] || 0);
	    series[1].data.push(aggregatedData[monthYear]["Poor Quality"] || 0);
	    series[2].data.push(aggregatedData[monthYear]["UnMatched Specimen"] || 0);
	    series[3].data.push(aggregatedData[monthYear]["Size Issue"] || 0);
	});
	
	console.log(series);
			
			
			
	Highcharts.chart('turnoverRateByAgeGroup', {
    chart: {
        type: 'column',
        animation: true,
        height: 250,
    },
    title: {
        text: ''
    },
    credits: {
        enabled: false
    },
    navigation: {
        buttonOptions: {
            enabled: false
        }
    },
    legend: {
        enabled: true,  // Enable the legend
        layout: 'horizontal',  // Arrange legend items horizontally
        align: 'center',  // Align it in the center
        verticalAlign: 'bottom',  // Place it at the bottom
        itemStyle: {
            fontWeight: 'normal',
            fontSize: '14px',
            color: '#333'  // Set the color of legend text
        }
    },
    xAxis: {
        categories: monthYeartList,
        rotation: '45deg',
    },
    yAxis: {
        title: {
            text: ''
        },
        labels: {enabled: false},
        stackLabels: {
            enabled: true
        }
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: false,
                color: '#ffffff'
            }
        }
    },
    series: series
});

			
		},
		error: function(error) {
			console.error(error);
		}
	});
	}