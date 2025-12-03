function getSubtitle() {
    return `
    <span style="font-size: 22px; font-weight: bold;">
      120 PROJECTS
    </span>`;
}

function getTaskSubTitle() {
    return `
    <div style="font-size: 22px; font-weight: bold;">
      <img src="../assets/images/planning.png" height="50" width="50">
    </div>`;
}

function getSpentOnTimeSubTitle() {
    return `
    <div style="font-size: 22px; font-weight: bold;">
      <img src="../assets/images/date.png" height="50" width="50">
    </div>`;
}


function operationalHighChat(){
	 // PROJECTS
    Highcharts.chart('projects', {

        chart: {
            height: 250,
backgroundColor: '#fff',
        },

        subtitle: {
            useHTML: true,
            text: getSubtitle(),
            floating: true,
            verticalAlign: 'middle',
            y: 0
        },

        legend: {
            enabled: false
        },

        plotOptions: {
            series: {
                borderWidth: 0,
                colorByPoint: true,
                type: 'pie',
                size: '100%',
                innerSize: '80%',
                dataLabels: {
                    enabled: true,
                    crop: false,
                    distance: '-10%',
                    style: {
                        fontWeight: 'bold',
                        fontSize: '16px'
                    },
                    connectorWidth: 0
                }
            }
        },
        colors: ['#fdb60c', '#1b2c71'],
        series: [{
            type: 'pie',
            name: 'Value',
            data: [

                [
                    "Uncompleted",
                    35
                ],
                [
                    "Completed",
                    65
                ],
                [
                    "Onhold",
                    0
                ]
            ]
        }]
    });


    // TASKS
    Highcharts.chart('tasks', {
        chart: {
            type: 'pie',
            backgroundColor: '#fff',
            height: 250
        },
        title: {
            text: 'Air composition'
        },
        subtitle: {
            useHTML: true,
            text: getTaskSubTitle(),
            floating: true,
            verticalAlign: 'middle',
            y: -35,
            x: 3
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '{point.name}: {y}'
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'Value',
            colorByPoint: true,
            size: '80%',
            innerSize: '60%',
            data: [{
                name: 'Completed',
                y: 29,
                color: '#1a4474'
            }, {
                name: 'Not Started',
                y: 16,
                color: '#4a8cd6'
            }, {
                name: 'Inprogress',
                y: 55,
                color: '#e45f32'
            }]
        }]
    });

    // TASK CREATED VS COMPLETED
    Highcharts.chart('taskcreated', {

        chart: {
            backgroundColor: '#fff',
            height: 250
        },

        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        xAxis: {
            categories: [
                '12-01-2022',
                '12-02-2022',
                '12-03-2022',
                '12-04-2022',
                '12-05-2022',
                '12-06-2022',
                '12-07-2022',
                '12-08-2022'
            ],
            crosshair: true
        },
        series: [{
            name: 'Created',
            color: '#1a4474',
            data: [0, 10, 22, 46, 60, 80,
                96, 120
            ]
        }, {
            name: 'Completed',
            data: [0, 6, 18, 44, 66, 82,
                94, 116
            ],
            color: '#e45f32'
        }],

        responsive: {
            rules: [{
                condition: {
                    maxWidth: 500
                },
                chartOptions: {
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom'
                    }
                }
            }]
        }

    });


    // SPEND ON TIME
    Highcharts.chart('spentontime', {
        chart: {
            type: 'pie',
            backgroundColor: '#fff',
            height: 250
        },
        title: {
            text: 'Air composition'
        },
        tooltip: {
            valueSuffix: '%'
        },
        subtitle: {
            useHTML: true,
            text: getSpentOnTimeSubTitle(),
            floating: true,
            verticalAlign: 'middle',
            y: -55,
            x: 3
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '{point.name}: {y} %'
                },
                showInLegend: true
            }
        },
        series: [{
            name: 'Value',
            colorByPoint: true,
            size: '80%',
            innerSize: '60%',
            data: [{
                    name: 'Project Name 1',
                    y: 28,
                    color: '#1a4474'
                }, {
                    name: 'Project Name 2',
                    y: 23,
                    color: '#e45f32'
                }, {
                    name: 'Project Name 3',
                    y: 20,
                    color: '#4a8cd6'
                },
                {
                    name: 'Project Name 4',
                    y: 20,
                    color: '#87b3e4'
                },
                {
                    name: 'Project Name 5',
                    y: 5,
                    color: '#f5bda4'
                },
                {
                    name: 'All Other Projects',
                    y: 4,
                    color: '#f19c7f'
                }
            ]
        }]
    });




    Highcharts.chart('outstandtask', {
        chart: {
            type: 'column',
            height: 350,
            backgroundColor: '#fff',
        },
        xAxis: {
            categories: ['Paul Finley', 'Raghav Kumar', 'Sanjay Singh', 'Priya Ranjan', 'Debashis Kumar']
        },
        legend: {
            enabled: false
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y:1f}'
                }
            }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },
        colors: ['#182b77'],
        series: [{
            name: 'Browsers',
            colorByPoint: true,
            data: [{
                    name: 'Paul Finley',
                    y: 4
                },
                {
                    name: 'Raghav Kumar',
                    y: 5
                },
                {
                    name: 'Sanjay Singh',
                    y: 0
                },
                {
                    name: 'Priya Ranjan',
                    y: 6
                },
                {
                    name: 'Debashis Kumar',
                    y: 0
                }
            ]
        }],
    });


    Highcharts.chart('runningproject', {
        chart: {
            type: 'bar',
            height: 250,
           backgroundColor: '#fff',
        },
        xAxis: {
            categories: ['Airport Rail', 'Roy Hill', 'Moolarben', 'Gree-Orallair']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Goals'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true
                }
            }
        },
        colors: ['#fcba0c', '#182b77', '#04c8b4'],
        series: [{
            name: 'Start Date',
            data: [4, 4, 6, 15]
        }, {
            name: 'Days Completed',
            data: [5, 3, 12, 6]
        }, {
            name: 'Days Remaining',
            data: [5, 15, 8, 5]
        }]
    });

    Highcharts.chart('workload', {
        chart: {
            type: 'bar',
            height: 350,
            backgroundColor: '#fff',
        },
        xAxis: {
            categories: ['Project 8', 'Project 7', 'Project 6', 'Project 5', 'Project 4', 'Project 3', 'Project 2', 'Project 1']
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Goals'
            }
        },
        legend: {
            reversed: true
        },
        plotOptions: {
            series: {
                stacking: 'normal',
                dataLabels: {
                    enabled: true
                }
            }
        },
        colors: ['#4a8cd6', '#e45f32', '#182b77'],
        series: [{
            name: 'Completed',
            data: [4, 4, 6, 15, 4, 4, 6, 15]
        }, {
            name: 'Remaining',
            data: [5, 3, 12, 6, 5, 3, 12, 6]
        }, {
            name: 'Overdue',
            data: [5, 15, 8, 5, 5, 15, 8, 5]
        }]
    });


    


    Highcharts.chart('resourceallocation', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
            height: 250,
            backgroundColor: '#fff',
        },
        title: {
            text: 'Browser market shares in February, 2022',
            align: 'left'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                borderRadius: 5,
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b><br>{point.percentage:.1f} %',
                    distance: -50,
                    filter: {
                        property: 'percentage',
                        operator: '>',
                        value: 4
                    }
                }
            }
        },
        series: [{
            name: 'Share',
            data: [{
                    name: 'Alpha_4',
                    y: 15,
                    'color': '#5a9bd5'
                },
                {
                    name: 'Bams_5',
                    y: 25,
                    'color': '#ed7d31'
                },
                {
                    name: 'Gamma_12',
                    y: 40,
                    'color': '#a5a5a5'
                },
                {
                    name: 'Actives_4',
                    y: 15,
                    'color': '#ffc000'
                },
                {
                    name: 'Spak_2',
                    y: 5,
                    'color': '#4473c5'
                }
            ]
        }]
    });



    Highcharts.chart('time', {
        chart: {
            type: 'bar',
            height: 350,
            backgroundColor: '#fff',
        },
        accessibility: {
            point: {
                valueDescriptionFormat: '{index}. Age {xDescription}, {value}.'
            }
        },
        xAxis: [{
            categories: ['Planned Completion', 'Actual Completion', 'Slippage'],
            reversed: true,
            labels: {
                step: 1
            },
        }],
        yAxis: {
            title: {
                text: null
            },
            labels: {
                formatter: function() {
                    return Math.abs(this.value) + '';
                }
            },
        },

        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    formatter: function() {
                        return Math.abs(this.y); // Use Math.abs() to get the absolute value
                    }
                }
            }
        },

        series: [{
            name: 'Behind',
            color: '#e45f32',
            data: [
                null, null, -21
            ]
        }, {
            name: 'Ahead',
            color: '#1b2c71',
            data: [
                73, 52, null
            ]
        }]
    });


	Highcharts.chart('totalDelivery', {
		chart : {
			type : 'pie',
			backgroundColor: '#fff',
			options3d : {
				enabled : true,
				alpha : 45
			}
		},
		title : {
			text : ''
		},
		subtitle : {
			text : ''
		},
		plotOptions : {
			pie : {
				colors: ['#fb7a7d','fb7af3','#d57afb','#7a83fb','#7ae7fb','#2c6361','#eaf493','#ffac36','#ffee36','#fa1d1d'],
				innerSize : 100,
				showInLegend : true,
				depth : 45,
				dataLabels : {
					enabled : true,
					distance : -50,
					
					color : '#FFF',

					style : {
						textShadow : false,
						textOutline : false,
					}
				}
			},  series: {						    		 
	    	    dataLabels: {
		    	      enabled: true,
		    	    },cursor: 'pointer',
		    	    
	            	point: {
	                	events: {
	                    	click: function(oEvent) {
	                    		totaldeliverygrid(this)
	                    	}
	                	}
	            	}
		    	  }
		},

		credits : {
			enabled : false
		},

		exporting : {
			enabled : false
		},

		series : [ {
			name : 'Leave',
			data : [ [ 'High', 6 ],
					[ 'Medium', 2 ],
					[ 'Low', 4 ]

			]
		} ]
	});

}