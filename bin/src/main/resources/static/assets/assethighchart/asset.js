
function assests() {

	var fromDate = $("#fromDate2").val();
	var toDate = $("#toDate2").val();
	var org = $("#assetOrg").find('option:selected').text();
	var orgDiv = $("#assetOrgDiv").find('option:selected').text();

	//eam Asset Fulfillment Time added by Ganesh
	$.ajax({
		type: "GET",
		url: "dashboard-asset-asset-fulfillment-time?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var categoryData = {};
				var colors = ['#deaaf0', '#bf05ff', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92', '#F79C92'];

				// Initialize the categoryData object
				allData.forEach(item => {
					if (!categoryData[item.categoryName]) {
						categoryData[item.categoryName] = {
							oneToFive: 0,
							sixToTen: 0,
							elevenToTwenty: 0,
							twentyToThirty: 0,
							greaterToThirty: 0
						};
					}
				});

				// Process each item and update the counts in categoryData
				allData.forEach(item => {
					var Date_Difference = parseInt(item.Date_Difference);
					var category = categoryData[item.categoryName];

					if (Date_Difference >= 1 && Date_Difference <= 5) {
						category.oneToFive++;
					}

					if (Date_Difference >= 6 && Date_Difference <= 10) {
						category.sixToTen++;
					}

					if (Date_Difference >= 11 && Date_Difference <= 20) {
						category.elevenToTwenty++;
					}

					if (Date_Difference >= 21 && Date_Difference <= 30) {
						category.twentyToThirty++;
					}

					if (Date_Difference > 30) {
						category.greaterToThirty++;
					}
				});

				// Create the series array for charting
				var series = [];
				var colorIndex = 0;

				for (var categoryName in categoryData) {
					var counts = categoryData[categoryName];
					series.push({
						name: categoryName,
						data: [
							counts.oneToFive,
							counts.sixToTen,
							counts.elevenToTwenty,
							counts.twentyToThirty,
							counts.greaterToThirty
						],
						// Assign color from the colors array
						color: colors[colorIndex % colors.length]
					});
					colorIndex++;
				}

				// Example to display the series array (you can use console.log or other methods)
				console.log('series for ',JSON.stringify(series));
				
				// Output the series array
				// This is where you would use the series array with your charting library




				Highcharts.chart('assetAssetFulfillmentTime', {
					chart: {
						type: 'column',
						animation: true,
						height: 260
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: ['0-5', '6-10', '11-20', '21-30', '31+'],
						title: {
							text: '# of days'
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: '# of requests'
						},
						stackLabels: {
							enabled: false
						}
					},

					plotOptions: {
						column: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: false
							}
						}
					},
					series: series,
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	//Verification Status
	//eam Verification Status added by Ganesh
	$.ajax({
		type: "GET",
		url: "dashboard-asset-verification-status?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;
				var verifyCount = allData[0].verifyCount;
				var verifiedCount = allData[0].verifiedCount;
				var mismatchCount = allData[0].mismatchCount;
				Highcharts.chart('assetVerificationStatus', {
					chart: {
						type: 'column',
						animation: true,
						height: 260
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: ['Verify', 'Verified', 'Mismatched'],
						title: {
							text: null
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
						stackLabels: {
							enabled: false
						}
					},

					plotOptions: {
						column: {
							dataLabels: {
								enabled: false
							}
						}
					},
					legend: { enabled: false },
					series: [{
						name: '',
						data: [verifyCount, verifiedCount, mismatchCount],
						color: '#bf05ff'
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	//************************dashboard-assetEndOfLife**************Start By Bulet************* 	
	//eam End Of Life added by Ganesh
	$.ajax({
		type: "GET",
		url: "dashboard-asset-end-life-next-days?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code === "success") {
				var jsonData = JSON.parse(response.body); // Assuming response.body is already in JSON format as described

				// Extract categories and corresponding counts
				var categories = jsonData.body.map(function(item) {
					return item.category;
				});

				// Extract counts for 'End of Report', 'End of Life', and 'End of Extended Support'
				var endofreport_counts = jsonData.body.map(function(item) {
					return parseInt(item.endofreport); // Parse as integer assuming it's a number
				});

				var endoflifecount_counts = jsonData.body.map(function(item) {
					return parseInt(item.endoflifecount); // Parse as integer assuming it's a number
				});

				var endofextendedSupport_counts = jsonData.body.map(function(item) {
					return parseInt(item.endofextendedSupport); // Parse as integer assuming it's a number
				});

				// Render Highcharts chart with extracted data
				Highcharts.chart('assetEndOfLife', {
					chart: {
						type: 'bar',
						animation: true,
						height: 268
					},
					title: {
						text: ''
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categories, // Use categories extracted from jsonData
					},
					yAxis: {
						min: 0,
						title: {
							text: '# of models'
						}
					},
					legend: {
						reversed: true
					},
					plotOptions: {
						series: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: true
							}
						}
					},
					series: [{
						name: 'End of Report',
						data: endofreport_counts,
						color: "#bf05ff"
					}, {
						name: 'End of Life',
						data: endoflifecount_counts,
						color: "#deaaf0"
					}, {
						name: 'End of Extended Support',
						data: endofextendedSupport_counts,
						color: "#9792e8"
					}]
				});
			}
		},
		error: function(data) {
			console.log(data);
		}
	});
	//**************************************dashboard-assetEndOfLife ***********************************End */				


	//******************************************asset count by category**********************start By Bulet****************
	$.ajax({
		type: "GET",
		url: "dashboard-asset-asset-count-by-category?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {
			if (response.code == "success") {
				// Parse the response body which is a stringified JSON
				var jsonData = JSON.parse(response.body);

				var countData = [];
				var CategoryData = [];
				var totalAssets = jsonData.total_assets;



				// Extract and prepare data for Highcharts
				jsonData.body.forEach(function(item) {
					CategoryData.push(item[0]); // Category names
					countData.push(item[1]); // Category counts
				});

				// Prepare data in the format required by Highcharts
				var chartData = [];
				for (var i = 0; i < CategoryData.length; i++) {
					chartData.push({ name: CategoryData[i], y: countData[i] });
				}

				// Create the Highcharts pie chart
				Highcharts.chart('assetAssetCountByCategory', {
					chart: {
						type: 'pie',
						height: 130,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'],
					title: {
						text: totalAssets,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15px', "color": '#000000' }
					},
					subtitle: {
						text: 'Count',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 65,
						margin: 0,
						style: { "fontSize": '9px', "color": '#000000' }
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
							shadow: false,
							center: ['50%', '50%']
						}
					},
					series: [{
						name: 'Assets',
						data: chartData,
						size: '120%',
						innerSize: '70%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});

				// Generate the legends dynamically
				var legendContainer = $('#legendContainer');
				legendContainer.empty(); // Clear any existing legends

				var colors = ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'];

				for (var i = 0; i < CategoryData.length; i++) {
					var legendItem = `
                        <div class="d-flex align-items-center">
                            <div class="man-legend" style="background: ${colors[i % colors.length]};"></div>
                            <div class="man-legend-name">${CategoryData[i]} </div>
                        </div>
                    `;
					legendContainer.append(legendItem);
				}
			}
		}, error: function(data) {
			console.log(data);
		}
	});


	//******************************************asset count by category*****************End By Bulet****************
	//asset Asset Count By Life State
	$.ajax({
		type: "GET",
		url: "dashboard-asset-asset-count-by-Life-state?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var build = allData.build;
				var inUse = allData.inUse;
				var inStock = allData.inStock;
				var retired = allData.retired;
				var consumed = allData.consumed;
				var inTransit = allData.inTransit;
				var inMaintenance = allData.inMaintenance;

				var allCount = build + inUse + inStock + retired + consumed + inTransit + inMaintenance;
				//alert("allCount---------"+allCount);   
				//eam Asset Count By Life State


				Highcharts.chart('assetAssetCountByLifeState', {
					chart: {
						type: 'pie',
						height: 130,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8', '#9792e8'],
					title: {
						text: allCount,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15', "color": '#000000' }
					},
					subtitle: {
						text: 'Count',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 65,
						margin: 0,
						style: { "fontSize": '9', "color": '#000000' }
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
						data: [["In stock", inStock], ["In use", inUse]
							, ["Consumed", consumed], ["In maintenance", inMaintenance]
							, ["Retired", retired], ["Build", build], ["In transit", inTransit]],
						size: '120%',
						innerSize: '70%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});




	//asset Asset Value By Category

	$.ajax({
		type: "GET",
		url: "dashboard-asset-asset-value-by-category?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {
				var jsonData = JSON.parse(response.body);

				var countData = [];
				var CategoryData = [];
				var price_sum = jsonData.total_price_sum;

				jsonData.body.forEach(function(item) {
					CategoryData.push(item[0]); 
					countData.push(item[1]); 
				});

				var chartData = [];
				for (var i = 0; i < CategoryData.length; i++) {
					chartData.push({ name: CategoryData[i], y: countData[i] });
				}

				Highcharts.chart('assetAssetValueByCategory', {
					chart: {
						type: 'pie',
						height: 130,
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'],
					title: {
						text: price_sum,
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 20,
						margin: 0,
						style: { "fontSize": '15', "color": '#000000' }
					},
					subtitle: {
						text: 'Cost',
						align: 'center',
						verticalAlign: 'center',
						floating: true,
						y: 65,
						margin: 0,
						style: { "fontSize": '9', "color": '#000000' }
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
						data: chartData,
						size: '120%',
						innerSize: '70%',
						showInLegend: false,
						dataLabels: {
							enabled: false
						}
					}]
				});
			}


			var legendContainer1 = $('#legendContainer1');
			legendContainer1.empty(); 

			var colors = ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'];

			for (var i = 0; i < CategoryData.length; i++) {
				var legendItem = `
                        <div class="d-flex align-items-center">
                            <div class="man-legend" style="background: ${colors[i % colors.length]};"></div>
                            <div class="man-legend-name">${CategoryData[i]} </div>
                        </div>
                    `;
				legendContainer1.append(legendItem);
			}


		}, error: function(data) {
			console.log(data);
		}
	});

	//asset Asset Count By Location
	$.ajax({
    type: "GET",
    url: "dashboard-asset-asset-count-by-location?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
    async: true,
    success: function(response) {

        if (response.code == "success") {

            var jsonData = JSON.parse(response.body);
            var allData = jsonData.body;

            // Initialize an empty array to store the results
            var result = [];

            // Initialize a variable to store the total count
            var totalCount = 0;
            var html = ''; // Initialize html outside the loop

            // Clear the existing legends before appending
            $('#locationByCatCount').empty();

            // Iterate over each item in the parsed data
            var i = 0; // Initialize i outside the loop
            allData.forEach(function(item) {
                result.push([item.locationName, item.totalCount]);
                totalCount += item.totalCount; // Sum the totalCount values

                var colors = ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'];
                html += `
                    <div class="d-flex align-items-center">
                      <div class="man-legend" style="background: ${colors[i % colors.length]};"></div>
                      <div class="man-legend-name">${item.locationName}</div>
                    </div>
                `;

                i++; // Increment i
            });

            // Append the accumulated HTML to the element
            $('#locationByCatCount').append(html);

            //eam Asset Count By Location added by Ganesh
            Highcharts.chart('assetAssetCountByLocation', {
                chart: {
                    type: 'pie',
                    height: 130,
                },
                navigation: {
                    buttonOptions: {
                        enabled: false
                    }
                },
                colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'],
                title: {
                    text: totalCount,
                    align: 'center',
                    verticalAlign: 'center',
                    floating: true,
                    y: 20,
                    margin: 0,
                    style: { "fontSize": '15', "color": '#000000' }
                },
                subtitle: {
                    text: 'Count',
                    align: 'center',
                    verticalAlign: 'center',
                    floating: true,
                    y: 65,
                    margin: 0,
                    style: { "fontSize": '9', "color": '#000000' }
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
                    data: result,
                    size: '120%',
                    innerSize: '70%',
                    showInLegend: false,
                    dataLabels: {
                        enabled: false
                    }
                }]
            });
        }
    },
    error: function(data) {
        console.log(data);
    }
});



	function createPieChart(containerId, allData, count) {
		console.log(containerId, allData)
		var datalist = [];
		var total = 0;
		for (var i = 0; i < allData.length; i++) {
			var data = [];
			total += allData[i].value;
			data.push(allData[i].key);
			data.push(allData[i].value);
			datalist.push(data);
		}
		Highcharts.chart(containerId, {
			chart: {
				type: 'pie',
				height: 200
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: total,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: { "fontSize": '20', "color": '#000000' }
			},
			subtitle: {
				text: count,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: { "fontSize": '9', "color": '#000000' }
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			legend: {
				labelFormatter: function() {
					return '<span style="color:' + this.color + '">' + this.name + ': </span>  <b>' + this.y + '<br/>';
				}
			},
			series: [{
				name: '',
				data: datalist,
				size: '120%',
				innerSize: '70%',
				showInLegend: true,
				dataLabels: {
					enabled: false
				}
			}]
		});
	}
	function createPieChart1(containerId, allData, count) {
		console.log(containerId, allData)
		var datalist = [];
		var total = 0;
		for (var i = 0; i < allData.length; i++) {
			var data = [];
			total += allData[i].value;
			data.push(allData[i].key);
			data.push(allData[i].value);
			datalist.push(data);
		}
		Highcharts.chart(containerId, {
			chart: {
				type: 'pie',
				height: 240
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: total,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 50,
				margin: 0,
				style: { "fontSize": '20', "color": '#000000' }
			},
			subtitle: {
				text: count,
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 65,
				margin: 0,
				style: { "fontSize": '9', "color": '#000000' }
			},
			credits: {
				enabled: false,
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			legend: {
				labelFormatter: function() {
					return '<span style="color:' + this.color + '">' + this.name + ': </span>  <b>' + this.y + '<br/>';
				}
			},
			series: [{
				name: '',
				data: datalist,
				size: '120%',
				innerSize: '70%',
				showInLegend: true,
				dataLabels: {
					enabled: false
				}
			}]
		});
	}

	//************************dashboard-assetBreakupHardwareAssets******************Start By Bulet */	  

	//eam Breakup Of Hardware Assets Based On Hardware Asset Status by Ganesh

	$.ajax({
		type: "GET",
		url: "dashboard-asset-asset-status?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var categoryLIst = [];
				var allocateList = [];
				var scrapList = [];
				var inStockList = [];
				var underMaintenanceList = [];
				var inTransitList = [];
				var withVendorList = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryLIst.push(item.categoryName);
					allocateList.push(item.assignedAssetCount);
					scrapList.push(item.scrapedAssetCount);
					inStockList.push(item.stockCount);
					underMaintenanceList.push(item.maintenanceCount);
					inTransitList.push(item.inTransitCount);
					withVendorList.push(item.withVendorCount);

				}




				Highcharts.chart('assetBreakupHardwareAssets', {
					chart: {
						type: 'column',
						animation: true,
						height: 268
					},
					title: {
						text: ''
					},
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					credits: false,
					xAxis: {
						categories: categoryLIst,// ['Sales', 'Procurement', 'Production', 'Administration', 'Inventory', 'QA', 'Finance', 'Others']
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
						stackLabels: {
							enabled: false
						}
					},
					plotOptions: {
						column: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: false
							}
						}
					},
					series: [{
						name: 'Allocate',
						data: allocateList,//[3, 5, 1, 6, 5, 7, 8, 4],
						color: "#d4c9d8"
					}, {
						name: 'Scrap',
						data: scrapList,//[8, 2, 6, 3, 6, 7, 5, 2],
						color: "#56156c"
					}, {
						name: 'In Stock',
						data: inStockList,//[8, 2, 6, 3, 6, 7, 5, 2],
						color: "#f79c92"
					}, {
						name: 'Under Maintenance',
						data: underMaintenanceList,//[3, 5, 1, 6, 5, 7, 8, 4],
						color: "#ca7ce5"
					}, {
						name: 'In Transit',
						data: inTransitList,//[8, 2, 6, 3, 6, 7, 5, 2],
						color: "#bf05ff"
					}, {
						name: 'With Vendor',
						data: withVendorList,//[3, 5, 1, 6, 5, 7, 8, 4],
						color: "#b422b6"
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});




	//eam assets Pulled From Pool added by Ganesh
	$.ajax({
		type: "GET",
		url: "dashboard-asset-assets-pulled-from-pool-net-new-purchase?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var jsonData = JSON.parse(response.body);
				var allData = jsonData.dashboardData;

				var categoryLIst = [];
				var pooledList = [];
				var newPurchaseList = [];

				for (var i = 0; i < allData.length; i++) {
					var item = allData[i];
					categoryLIst.push(item.categoryName);
					pooledList.push(item.pooledCount);
					newPurchaseList.push(item.newPurchaseCount);

				}
				Highcharts.chart('assetAssetsPulledFromPool', {
					chart: {
						type: 'bar',
						animation: true,
						height: 260
					},
					title: {
						text: ''
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: categoryLIst,//['Software', 'Cloud', 'Hardware'],
					},
					yAxis: {
						min: 0,
						title: {
							text: '# of assets'
						}
					},
					legend: {
						reversed: true
					},
					plotOptions: {
						series: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: true
							}
						}
					},
					series: [{
						name: 'Net New Purchase',
						data: newPurchaseList,//[4, 4, 3],
						color: "#deaaf0"
					}, {
						name: 'Pulled from the Pool',
						data: pooledList,//[5, 3, 2],
						color: "#bf05ff"
					}]
				});
			}
		}, error: function(data) {
			console.log(data);
		}
	});



	//************************dashboard-assetTotalSpend******************Start By Bulet */	  


	//eam Total Spend
	$.ajax({
		type: "GET",
		url: "dashboard-asset-total-spend?fromDate=" + fromDate + "&toDate=" + toDate + "&org=" + org + "&orgDiv=" + orgDiv,
		async: true,
		success: function(response) {

			if (response.code == "success") {

				var data = JSON.parse(response.body);
				const categoryCounts = {};
				const monthNames = new Set();  // Initialize an empty set for MonthNames

				// Define an array of color codes
				const colors = ['#B422B6', '#22B4B6', '#B6B422', '#22B642', '#B62222', '#4222B6', '#22B66A'];

				$.each(data, function(index, entry) {
					const monthSequence = entry.MonthSequence;
					const category = entry.categoryName;
					const count = entry.catAmountTotal;

					// Add the MonthName to the set
					monthNames.add(entry.MonthName);

					if (!categoryCounts[category]) {
						categoryCounts[category] = [];
					}

					categoryCounts[category][monthSequence - 1] = count;

					for (let i = 0; i < monthSequence; i++) {
						if (!categoryCounts[category][i]) {
							categoryCounts[category][i] = 0;
						}
					}
				});

				const seriesDataArray = [];
				Object.keys(categoryCounts).forEach((category, index) => {
					const counts = categoryCounts[category];

					// Get color for the category
					const color = colors[index % colors.length];

					const seriesData = {
						name: category,
						data: counts,
						color: color
					};

					seriesDataArray.push(seriesData);
				});

				const seriesDataList = seriesDataArray;
				const monthNamesArray = Array.from(monthNames);
				console.log("seriesDataList-------------", seriesDataList);

				Highcharts.chart('assetTotalSpend', {
					chart: {
						type: 'column',
						animation: true,
						height: 260
					},
					title: {
						text: '',
					},
					credits: false,
					navigation: {
						buttonOptions: {
							enabled: false
						}
					},
					xAxis: {
						categories: monthNamesArray,//['April', 'May', 'June', 'July'],
						title: {
							text: null
						},
					},
					yAxis: {
						min: 0,
						title: {
							text: null
						},
					},

					plotOptions: {
						column: {
							stacking: 'normal',
							reversedStacks: false,
							dataLabels: {
								enabled: true,
								style: { 'color': '#000000' }
							}
						}
					},
					series: seriesDataList
				});

			}
		}, error: function(data) {
			console.log(data);
		}
	});
}

function getAssetsFilterWithDate() {
	assests();
}
function resetAssetsTab() {
	var today = new Date();

	if (today.getMonth() < 2
		|| (today.getMonth() === 2 && today.getDate() < 31)) {
		var fromYear = today.getFullYear() - 1;
	} else {
		var fromYear = today.getFullYear();
	}

	var fromDate = ('0' + 1).slice(-2) + '-' + ('0' + 4).slice(-2) + '-'
		+ fromYear;
	var toDateFinancialYear = ('0' + 31).slice(-2) + '-'
		+ ('0' + 3).slice(-2) + '-' + (fromYear + 1);
	var toDate = ('0' + today.getDate()).slice(-2) + '-'
		+ ('0' + (today.getMonth() + 1)).slice(-2) + '-'
		+ today.getFullYear();

	$("#toDate2").val(toDate);
	$("#fromDate2").val(fromDate);

	assests();
}

function getAssetOrgData() {
	assests();
}
function getAssetOrgDivData() {
	assests();
}
