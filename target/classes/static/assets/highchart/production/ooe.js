function ooeHighChat() {
	var org = $("#OOEOrganization").val();
	var orgDiv = $("#OOEDivision").find('option:selected').text();
	var location = $("#OOELocation").find('option:selected').text();
	var fromDate = $("#fromDate1").val();
	var toDate = $("#toDate1").val();
	
	
	$.ajax({
		type: "GET",

		url: "dashboard-ooe-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,

		success: function(response) {
			console.log(response);
			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				  		
				$("#totalUnitOOE").text(allData[0].totalNoOfUnitsCount);
				$("#actualUnitOOE").text(allData[0].actualUnitsCount);
				$("#oeeCount").text(allData[0].totalOEECount);
				$("#capacityUtilizationOEE").text(allData[0].capicityUtilizationCount + '%');
				$("#firstPassYieldOEE").text(allData[0].firstPassYeildCount  + '%');
				$("#scrapeRateOEE").text(allData[0].scrapRateCount  + '%');

			}
		}, error: function(data) {
			console.log(data);
		}
	})

				
	//Ajax dashboard for machine utilization
	$.ajax({
		url: "dashboard-machine-specialization-details?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
var allData = jsonData.dashboardData;

$("#machineProductionDetails").empty();
var machineWorking = '';

for (var i = 0; i < allData.length; i++) {
    var item = allData[i];
    var machineName = item.machineName;
    var machineWorkingStatus = item.machineWorkingStatus;
    var throughPut = item.throughPut;
    var machineOEE = item.machineOEE;
    var machineCapacityUtilization = item.machineCapacityUtilization;
    var machineFirstPassYeild = item.machineFirstPassYeild;
    var machineScrapeRate = item.machineScrapeRate;

    var chartId = "ooe" + machineName.replace(/\s/g, '') + '_' + i;

    if (machineWorkingStatus === "Running") {
        machineWorking = `
            <div class="col-md-3">
                <div class="kpi-card-container">
                    <div class="machine-name">${machineName}</div>
                    <div class="machine-running-status running">${machineWorkingStatus}</div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">Throughput</div>
                        <div class="w-100 machine-results">${throughPut}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">OEE</div>
                        <div class="w-100 machine-results">${machineOEE}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">Capacity Utilization</div>
                        <div class="w-100 machine-results">${machineCapacityUtilization}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">First Pass Yield</div>
                        <div class="w-100 machine-results">${machineFirstPassYeild}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">Scrap Rate</div>
                        <div class="w-100 machine-results">${machineScrapeRate}</div>
                    </div>
                    <div id="${chartId}" style="width:100%;"></div>
                </div>
            </div>`;
    } else if (machineWorkingStatus === "Not Running") {
        machineWorking = `
            <div class="col-md-3">
                <div class="kpi-card-container">
                    <div class="machine-name">${machineName}</div>
                    <div class="machine-running-status not-running">${machineWorkingStatus}</div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">Throughput</div>
                        <div class="w-100 machine-results">${throughPut}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">OEE</div>
                        <div class="w-100 machine-results">${machineOEE}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">Capacity Utilization</div>
                        <div class="w-100 machine-results">${machineCapacityUtilization}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">First Pass Yield</div>
                        <div class="w-100 machine-results">${machineFirstPassYeild}</div>
                    </div>
                    <div class="d-flex w-100 bb-machine">
                        <div class="w-100 machine-td">Scrap Rate</div>
                        <div class="w-100 machine-results">${machineScrapeRate}</div>
                    </div>
                    <div id="${chartId}" style="width:100%;"></div>
                </div>
            </div>`;
    }

    // Append the generated HTML
    $("#machineProductionDetails").append(machineWorking);
    
    


			
				//ooe machine A
			Highcharts.chart(chartId, {
				chart: {
					animation: true,
					height: 100,
					margin: 0
				},
				navigation: {
					buttonOptions: {
						enabled: false
					}
				},
				title: {
					text: ''
				},
				subtitle: {
					text: ''
				},
				credits: { enabled: false },
				xAxis: [{
					/*categories: ['1', '2', '3', '4', '5', '6'],*/
					crosshair: true,
					lineColor: '#cccccc',
					labels: { enabled: false }
				}],
				yAxis: [{ // Primary yAxis
					labels: {
						enabled: false,
					},
					title: {
						text: '',
					},
					gridLineColor: 'transparent'
				}, { // Secondary yAxis
					title: {
						text: 'Precipitation',
						style: {
							display: 'none',
						}
					},
					labels: {
						enabled: false,
					},
					gridLineColor: 'transparent'
				}],
				tooltip: {
					shared: false
				},
				legend: {
					enabled: false,
				}, plotOptions: {
					plotWidth: 10,
					column: {
						zones: [{
							value: 28,
							color: '#56156C'
						}, {
							color: '#B422B6'
						}]
					}
				},
				series: [{
					name: 'Precipitation',
					type: 'column',
					yAxis: 1,
					data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7,
						27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7],
		
				}, {
					name: 'Benchmark',
					type: 'spline',
					data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
						30, 30, 30, 30, 30, 30],
					color: '#F79C92',
					lineWidth: 3,
					dataLabels: {
						enabled: false,
					},
					marker: {
						enabled: false
					}
				}]
			});
			
			}	
			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
}

