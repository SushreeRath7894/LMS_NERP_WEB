function inventoryAging(){
	
	        //Inventory Aging Pie
        Highcharts.chart('inventoryAgingPie', {
            chart: {
                type: 'pie',
                animation: true,
                height: 100,
                backgroundColor: 'transparent'
            },
            credits: { enabled: false },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            colors: ['#2091c8', '#96c3e1', '#d23148'],
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
                    shadow: false
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
                }
            },
            series: [{
                name: '',
                data: [["Completed", 40], ["In-Progress", 20], ["Pending", 30]],
                size: '100%',
                innerSize: '50%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '30%'
                }
            }]
        });
        //Inventory Aging Bar
        Highcharts.chart('inventoryAgingBar', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 110,
                animation: true,
            },
            colors: [
                '#45a96c',
                '#5c8cac'
            ],

            plotOptions: {
                column: {
                    colorByPoint: true
                }
            },
            credits: false,
            title: {
                text: ''
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
                categories: ['2009', '2008'],
                title: {
                    text: ""
                },
                // labels: {
                //     enabled: true
                // },
                lineColor: 'transparent'
            },
            yAxis: {

                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            legend: {
                enabled: false
            },
            tooltip: {
                enabled: false
            },
            series: [{
                name: 'IPS',
                data: [
                    ['1', 21],
                    ['2', 109]
                ],
                pointWidth: 70,
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#000000',
                    align: 'center',
                    style: {
                        fontSize: '13px'

                    }
                }
            }]
        });
        //Aging Stock Value
        Highcharts.chart('agingStockValue', {
            chart: {
                type: 'area',
                height: 250,
                animation: true,
            },
            credits: false,
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
                allowDecimals: true,
                accessibility: {
                    rangeDescription: 'Range: 1940 to 2017.'
                },
                labels: { enabled: false },
                tickLength: 0,
                tickWidth: 0,
            },
            yAxis: {
                allowDecimals: true,
                title: {
                    text: ''
                }
            },
            tooltip: {
                pointFormat: '{series.name} had stockpiled <b>{point.y:,.0f}</b><br/>warheads in {point.x}'
            },
            plotOptions: {
                area: {
                    pointStart: 1940,
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            },
            series: [{
                name: 'USA',
                data: [
                    5000000, 4900000, 4800000, 4700000, 4600000, 4500000, 4400000, 4300000, 4200000, 4400000
                ], showInLegend: false
            }]
        });
        //Aging Stock Value By Aging Group
        Highcharts.chart('agingStockValueByAgingGroup', {
            chart: {
                type: 'area',
                height: 250,
                animation: true,
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            }, xAxis: {
                labels: { enabled: false },
                tickLength: 0,
                tickWidth: 0,
            },
            yAxis: {
                title: {
                    text: ''
                },
                min: 0
            },
            credits: false,
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            plotOptions: {
                series: {
                    pointStart: 2012
                },
                area: {
                    stacking: 'normal',
                    lineColor: 'transparent',
                    marker: {
                        enabled: false,
                    }
                }
            },
            series: [{
                name: '< 100 days',
                data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214]
            }, {
                name: '100 - 200 days',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039]

            }, {
                name: '> 200 days',
                data: [4752, 4820, 4877, 4925, 5006, 4976, 4946, 4911, 4913]
            }]
        });
        //Stock Turnover Days
        Highcharts.chart('stockTurnoverDays', {
            chart: {
                type: 'line',
                height: 250,
                animation: true,
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: false,
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            yAxis: {
                title: {
                    text: ''
                },
                min: 0, tickInterval: 50,
            },

            xAxis: {
                accessibility: {
                    rangeDescription: 'Range: 2010 to 2020'
                },
                labels: { enabled: false },
                tickWidth: 0,
                tickLength: 0
            },

            legend: {
                enabled: false
            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 2010,
                    lineWidth: 3,
                    marker: false
                }
            },

            series: [{
                name: '',
                data: [450, 467, 432, 478, 550, 530, 420, 400, 390, 410]
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
        //Aging Stock Value By Year, Quarter, Month & Aging Group
        Highcharts.chart('agingStockValueByYear', {
            chart: {
                type: 'line',
                height: 250,
                animation: true,
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: false,
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

            xAxis: {
                labels: { enabled: false },
                tickWidth: 0,

            },

            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle'
            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    marker: false,
                    lineWidth: 2,
                    pointStart: 2010
                }
            },

            series: [{
                name: '< 100 days',
                data: [24916, 37941, 29742, 29851, 32490, 30282,
                    38121, 36885, 33726, 34243, 31050]
            }, {
                name: '100 days - 1 year',
                data: [11744, 30000, 16005, 19771, 20185, 24377,
                    32147, 30912, 29243, 29213, 25663]
            }, {
                name: '> 1 year',
                data: [21908, 5548, 8105, 11248, 8989, 11816, 18274,
                    17300, 13053, 11906, 10073]
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
  
}