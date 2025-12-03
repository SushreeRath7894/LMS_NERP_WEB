function cycleHighChart(){
	 //average sales cycle
        Highcharts.chart('averageSalesCycle', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 180
            },
            title: {
                text: '184 days',
                style: { "fontSize": "22px", "fontWeight": "600", 'color': "#2404a4" },
            },
            subtitle: {
                text: 'average sales cycle length',
                style: { "fontSize": "12px", "fontWeight": "500", 'color': "#2404a4", "textTransform": "uppercase" },
            },

            credits: {
                enabled: false
            },
            xAxis: {
                tickLength: 10,
                tickWidth: 1,
                labels: {
                    rotation: -45,
                    style: {
                        fontSize: '10px',
                        fontFamily: 'Verdana, sans-serif',
                        color: '#000000'
                    }
                },
                showInLegend: false,
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
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
            series: [{
                name: '',
                data: [16.0, 18.2, 23.1, 27.9, 32.2, 36.4, 39.8, 38.4, 35.5, 29.2,
                    22.0, 17.8],
                color: '#2404a4',
                showInLegend: false,
            },]
        });
  //lead conversion rate
        Highcharts.chart('leadConversionRate', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 25,
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
            credits: {
                enabled: false
            },

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#1da381',
            }],

        });
        //lead conversion rate 1
        Highcharts.chart('leadConversionRate1', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 25,
                margin: 0
            },
            title: {
                text: ''
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },

            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#1da381',
            }],

        });
        //lead conversion rate 2
        Highcharts.chart('leadConversionRate2', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 25,
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
            credits: {
                enabled: false
            },

            yAxis: {
                title: {
                    text: ''
                },
                showInLegend: false,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },

            xAxis: {
                // accessibility: {
                //     rangeDescription: 'Range: 2010 to 2020'
                // }, 
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                tickWidth: 0,
                tickLength: 0,
                showInLegend: false
            },


            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 0
                }
            },

            series: [{
                marker: {
                    enabled: false
                },
                name: '',
                data: [4, 5, 10, 2, 5, 6,
                    8, 1, 5, 3, 5],
                color: '#1da381',
            }],

        });

//sales cycle length
        Highcharts.chart('salesCycleLength', {
            chart: {
                type: 'bar',
                animation: true,
                backgroundColor: 'transparent',
                height: 41,
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
            credits: {
                enabled: false
            },
            xAxis: {
                categories: ['2016/17'],
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            legend: {
                reversed: true,
                enabled: false,
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true
                    },
                    pointWidth: 20
                }
            },
            series: [{
                name: '',
                data: [4], color: '#8bd3cc'
            }, {
                name: '',
                data: [4], color: '#d48c84'
            }, {
                name: '',
                data: [12], color: '#fad3b6'
            }, {
                name: '',
                data: [9], color: '#bdbdbd'
            }]
        });
        //sales cycle length 1
        Highcharts.chart('salesCycleLength1', {
            chart: {
                type: 'bar',
                animation: true,
                backgroundColor: 'transparent',
                height: 41,
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
            credits: {
                enabled: false
            },
            xAxis: {
                categories: ['2016/17'],
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            legend: {
                reversed: true,
                enabled: false,
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true
                    },
                    pointWidth: 20
                }
            },
            series: [{
                name: '',
                data: [4], color: '#8bd3cc'
            }, {
                name: '',
                data: [4], color: '#d48c84'
            }, {
                name: '',
                data: [12], color: '#fad3b6'
            }, {
                name: '',
                data: [9], color: '#bdbdbd'
            }]
        });
        //sales cycle length 2
        Highcharts.chart('salesCycleLength2', {
            chart: {
                type: 'bar',
                animation: true,
                backgroundColor: 'transparent',
                height: 41,
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
            credits: {
                enabled: false
            },
            xAxis: {
                categories: ['2016/17'],
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            legend: {
                reversed: true,
                enabled: false,
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true
                    },
                    pointWidth: 20
                }
            },
            series: [{
                name: '',
                data: [4], color: '#8bd3cc'
            }, {
                name: '',
                data: [4], color: '#d48c84'
            }, {
                name: '',
                data: [12], color: '#fad3b6'
            }, {
                name: '',
                data: [9], color: '#bdbdbd'
            }]
        });
}