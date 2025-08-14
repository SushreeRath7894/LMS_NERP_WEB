function costManagementHighChat(){
	
	Highcharts.chart('manAssetTurnover', {
			chart: {
				type: 'column',
				animation: true,
				height: 200,
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
			xAxis: {
				categories: ['2018', '2019', '2020', '2021', '2022'],

			},
			yAxis: {
				title: {
					text: ''
				},
				labels: {format: '{value} %', },
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						format: '{y} %',
						color: '#000000'
					}

				}
			},
			legend: {enabled: false, },

			series: [{
				name: '',
				data: [74, 73, 47, 53, 67],
				color: '#2caffe'
			}]
		});
		//maintenance cost with target
		Highcharts.chart('manMaintenanceCostWithTarget', {
			chart: {
				animation: true,
				height: 200
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
			credits: {enabled: false},
			xAxis: [{
				categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Maintenance Cost',
				type: 'line',
				data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 28.8, 21.7, 34.1, 23.6, 34.7, 43.4],

			}, {
				name: 'Target',
				type: 'line',
				data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30],
				color: '#b4617c',
				lineWidth: 1,
				dashStyle: 'ShortDash',
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});

		//unit cost with target
		Highcharts.chart('manUnitCostWithTarget', {
			chart: {
				animation: true,
				height: 200
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
			credits: {enabled: false},
			xAxis: [{
				categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Unit Cost',
				type: 'column',
				data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 28.8, 21.7, 34.1, 23.6, 34.7, 43.4],

			}, {
				name: 'Target',
				type: 'line',
				data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30],
				color: '#b4617c',
				lineWidth: 1,
				dashStyle: 'ShortDash',
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
		//man Right First Time(Last 12 Months)
		
		//man rate of return
		
		//man Avg Right First Time
		
		//man Most Common Defects
		
		//man quality defect density
	/*	Highcharts.chart('manQualityDefectDensity', {
			chart: {
				animation: true,
				height: 250
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
			credits: {enabled: false},
			xAxis: [{
				categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
				labels: {format: '{value} %', },
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Product 1',
				type: 'line',
				color: '#0162a9',
				data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 28.8, 21.7, 34.1, 23.6, 34.7, 43.4],
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}

			}, {
				name: 'Product 2',
				type: 'line',
				data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30],
				color: '#b4617c',
				lineWidth: 1,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
		//man Downtime Causes
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
			colors: ['#1fca9e', '#00bbf3', '#0070c0'],
			title: {
				text: '4.4%',
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 100,
				margin: 0,
				style: {"fontSize": '25', "color": '#000000'}
			},
			subtitle: {
				text: 'Downtime',
				align: 'center',
				verticalAlign: 'center',
				floating: true,
				y: 130,
				margin: 0,
				style: {"fontSize": '14', "color": '#000000'}
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
				data: [["Broken Machine", 35], ["Missing Parts", 35], ["Service", 30]],
				size: '100%',
				innerSize: '70%',
				showInLegend: false,
				dataLabels: {
					enabled: false
				}
			}]
		});
		//
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
			legend: {enabled: false},
			xAxis: {
				categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
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

			series: [{
				name: '',
				data: [74, 73, 47, 53, 67, 74, 73, 47, 53, 67, 43, 21],
				color: '#1fca9e'
			}, {
				name: '',
				data: [18, 14, 35, 10, 21, 18, 14, 35, 10, 21, 43, 23],
				color: '#00bbf3'
			}, {
				name: '',
				data: [18, 14, 35, 10, 21, 18, 14, 35, 10, 21, 43, 23],
				color: '#0070c0'
			}]
		});*/
		
		Highcharts.chart('manReturnOnAssets', {
			chart: {
				animation: true,
				height: 200
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
			credits: {enabled: false},
			xAxis: [{
				categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				labels: {format: '{value} %', },
				title: {
					text: '',
				},
			},
			tooltip: {
				shared: false
			},
			legend: {
				enabled: false,
			},
			plotOptions: {
				plotWidth: 10,
			},
			series: [{
				name: '',
				type: 'column',
				data: [27.6, 28.8, -21.7, 34.1, 23.6, -34.7, -28.8, 21.7, 34.1, 23.6, -34.7, 43.4],

			}, {
				name: 'Benchmark',
				type: 'line',
				data: [10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10],
				color: '#b4617c',
				lineWidth: 1,
				dashStyle: 'LongDash',
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
	
}