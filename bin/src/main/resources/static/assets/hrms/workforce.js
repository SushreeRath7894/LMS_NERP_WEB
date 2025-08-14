function workforceHighChat(){
	//alert("workforce js")
	
	
	Highcharts.chart('partTimeVsFullTime', {
	    chart: {
	        type: 'line',
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
	    credits: false,
	    xAxis: [{
	        categories: ['2020', '2021', '2022', '2023', '2024', '2025'],  // Static year data
	        crosshair: true,
	        tickWidth: 1
	    }],
	    yAxis: [{ // Primary yAxis
	        labels: {
	            format: '{value} %',
	        },
	        min: 0,
	        title: {
	            text: 'Full Time',
	        }
	    }, { // Secondary yAxis
	        title: {
	            text: 'Part Time',
	        },
	        labels: {
	            format: '{value} %',
	        }, opposite: true,
	    }],
	    tooltip: {
	        shared: true
	    },
	    plotOptions: {
	        dataLabels: {
	            enabled: true
	        }
	    },
	    series: [{
	        name: 'Full Time',
	        type: 'line',
	        dashStyle: 'ShortDashDot',
	        data: [70, 72, 74, 75, 76, 78],  // Static data for Full Time
	        color: '#BF05FF'
	    }, {
	        name: 'Part Time',
	        type: 'line',
	        yAxis: 1,
	        dashStyle: 'ShortDash',
	        data: [30, 28, 26, 25, 24, 22],  // Static data for Part Time
	        color: '#F79C92'
	    }]
	});

	
	
	   
         Highcharts.chart('timeToQuitJob', {
            chart: {
                type: 'area',
                zoomType: 'xy',
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
            credits: {
                enabled: false
            },

            xAxis: {
                categories: ['0', '1', '2', '3', '4', '5', '6', '7', '8'],
                //categories: yearList,
                allowDecimals: false,
                title: {
                    text: 'Years at Company',
                }
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: { format: '{value} %', },
                gridLineColor: 'transparent'
            },
            legend: { enabled: false, },
            plotOptions: {
                area: {

                    dashStyle: 'ShortDash',
                    marker: {
                        symbol: 'circle',
                        radius: 4,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            series: [{
                name: '',
                data: [0, 10, 8, 5, 4, 3, 2, 1, 0],
               //data:empList,
               color:'#BF05FF'
            }]
        });

	
       
        Highcharts.chart('femaleMaleRatio', {
		    chart: {
		        type: 'pie',
		        animation: true,
		        height: 250
		    },
		    credits: { enabled: false },
		    title: {
		        text: ''
		    },
		    navigation: {
		        buttonOptions: {
		            enabled: false
		        }
		    },
		    yAxis: {
		        title: {
		            text: ''
		        }
		    },
		    plotOptions: {
		        pie: {
		            shadow: false,
		            colors: [
		                '#BF05FF',
		                '#F79C92'
		            ],
		        }
		    },
		    tooltip: {
		        formatter: function () {
		            return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
		        }
		    },
		    series: [{
		        name: '',
		        data: [
		            { name: 'Male', y: 60 },  // Static data for male
		            { name: 'Female', y: 40 }  // Static data for female
		        ],
		        size: '50%',
		        innerSize: '45%',
		        showInLegend: false,
		        dataLabels: {
		            enabled: true,
		            format: '{y} ({point.percentage:.1f} %)'
		        }
		    }]
		});

			
	
       
	
        
        Highcharts.chart('noticesDuringFirstYear', {
            chart: {
                type: 'column',
                animation: true,
                height: 236,
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
                categories: ['< 25', '26-35', '36-45', '46-54', '> 55'],

            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: { enabled: false },
                gridLineColor: 'transparent'
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
            legend: { enabled: false, },

            series: [{
                name: '',
                data: [74, 73, 47, 53, 67],
                color: '#F79C92'
            }]
        });
	
}