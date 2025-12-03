function inventoryOverviewHighChat(){
	   //Inventory Overview Pie
        Highcharts.chart('inventoryOverviewPie', {
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
        //Inventory Overview Line 1
        Highcharts.chart('inventoryOverviewLine1', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 2
        Highcharts.chart('inventoryOverviewLine2', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
                }
            },

            series: [{
                name: '',
                data: [45, 67, 132, 278, 150, 530, 420, 500, 490, 410]
            }, {
        name: '',
        data: [480, 480,480,480,480,480,480,480,480,480],
        dashStyle: 'ShortDash',
        color: '#be2565'
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
        //Inventory Overview Line 3
        Highcharts.chart('inventoryOverviewLine3', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 4
        Highcharts.chart('inventoryOverviewLine4', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 5
        Highcharts.chart('inventoryOverviewLine5', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
                }
            },

            series: [{
        name: '',
        data: [249, 379, 297, 296, 324, 302,
            321, 365, 337, 342, 310]
    }, {
        name: '',
        data: [null, null, null, null, null, 302,
            327, 312, 243, 213, 363]
    }, {
        name: '',
        data: [null, null, null, null, null, 302,
        107, 164, 118, 107]
    }, {
        name: '',
        data: [null, null, null, null, null, 302,
            170, 130, 116, 103]
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
        //Inventory Overview Line 6
        Highcharts.chart('inventoryOverviewLine6', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 7
        Highcharts.chart('inventoryOverviewLine7', {
            chart: {
                type: 'area',
                height: 150,
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
            legend: {enabled:false},
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
        //Inventory Overview Line 8
        Highcharts.chart('inventoryOverviewLine8', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 9
        Highcharts.chart('inventoryOverviewLine9', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 10
        Highcharts.chart('inventoryOverviewLine10', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 11
        Highcharts.chart('inventoryOverviewLine11', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
        //Inventory Overview Line 12
        Highcharts.chart('inventoryOverviewLine12', {
            chart: {
                type: 'line',
                height: 150,
                animation: true,
            },
            title: {
                text: 'Balance',
                style: { fontSize: '12px' }
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
                tickLength: 0,
                lineColor: 'transparent'
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
                    lineWidth: 2,
                    marker: false,
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
}