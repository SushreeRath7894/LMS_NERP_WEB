function delivery(){
	
	 //supplier defect rate
        Highcharts.chart('supplierDefectRate', {
            chart: {
                zoomType: 'xy',
                animation: true,
                height: 180
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: 'Supplier Defect Rate',
                align: 'Center',
                style: { 'fontSize': '11px' }
            },
            subtitle: {
                text: ''
            },
            credits: { enabled: false },

            yAxis: {
                title: {
                    text: ''
                },
                gridLineColor: 'transparent',
                labels: {
                    style: { fontSize: '10px' }
                }
            },

            xAxis: {
                categories: [],
                crosshair: true,
                lineColor: '#cccccc',
                labels: {
                    style: { fontSize: '10px' }
                }
            },

            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'center',
                itemStyle: {
                    fontSize: '9px'
                },

            },
            tooltip: { enabled: false },

            plotOptions: {
                series: {
                    states: {
                        hover: {
                            enabled: false
                        }
                    },
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 2010
                }
            },

            series: [{
                name: 'Installation & Developers',
                data: [43934, null, null, null, null, 142383,
                    null, null, null, 161454, null],
                lineColor: 'transparent',
                marker: {
                    fillColor: 'red'
                }
            }, {
                name: 'Manufacturing',
                data: [43934, 48656, 65165, 81827, 112143, 142383,
                    171533, 165174, 155157, 161454, 154610],
                marker: {
                    enabled: false,
                    fillColor: '#91ceb6',
                    border: '#91ceb6'
                },
                lineColor: '#91ceb6',

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
        //supplier availability
        Highcharts.chart('supplierAvailability', {
            chart: {
                zoomType: 'xy',
                animation: true,
                height: 180
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: 'Supplier Defect Rate',
                align: 'Center',
                style: { 'fontSize': '11px' }
            },
            subtitle: {
                text: ''
            },
            credits: { enabled: false },

            yAxis: {
                title: {
                    text: ''
                },
                gridLineColor: 'transparent',
                labels: {
                    style: { fontSize: '10px' }
                }
            },

            xAxis: {
                categories: [],
                crosshair: true,
                lineColor: '#cccccc',
                labels: {
                    style: { fontSize: '10px' }
                }
            },

            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'center',
                itemStyle: {
                    fontSize: '9px'
                },
            },
            tooltip: { enabled: false },

            plotOptions: {
                series: {
                    states: {
                        hover: {
                            enabled: false
                        }
                    },
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 2010
                }
            },

            series: [{
                name: 'Installation & Developers',
                data: [43934, null, null, null, null, 142383,
                    null, null, null, 161454, null],
                lineColor: 'transparent',
                marker: {
                    fillColor: 'red'
                }
            }, {
                name: 'Manufacturing',
                data: [43934, 48656, 65165, 81827, 112143, 142383,
                    171533, 165174, 155157, 161454, 154610],
                marker: {
                    enabled: false,
                    fillColor: '#91ceb6',
                    border: '#91ceb6'
                },
                lineColor: '#91ceb6',

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
        //lead time(in days)
        Highcharts.chart('leadTime', {
            chart: {
                zoomType: 'xy',
                animation: true,
                height: 180
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: 'Supplier Defect Rate',
                align: 'Center',
                style: { 'fontSize': '11px' }
            },
            subtitle: {
                text: ''
            },
            credits: { enabled: false },

            yAxis: {
                title: {
                    text: ''
                },
                gridLineColor: 'transparent',
                labels: {
                    style: { fontSize: '10px' }
                }
            },

            xAxis: {
                categories: [],
                crosshair: true,
                lineColor: '#cccccc',
                labels: {
                    style: { fontSize: '10px' }
                }
            },

            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'center',
                itemStyle: {
                    fontSize: '9px'
                },
            },
            tooltip: { enabled: false },

            plotOptions: {
                series: {
                    states: {
                        hover: {
                            enabled: false
                        }
                    },
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 2010
                }
            },

            series: [{
                name: 'Installation & Developers',
                data: [43934, null, null, null, null, 142383,
                    null, null, null, 161454, null],
                lineColor: 'transparent',
                marker: {
                    fillColor: 'red'
                }
            }, {
                name: 'Manufacturing',
                data: [43934, 48656, 65165, 81827, 112143, 142383,
                    171533, 165174, 155157, 161454, 154610],
                marker: {
                    enabled: false,
                    fillColor: '#91ceb6',
                    border: '#91ceb6'
                },
                lineColor: '#91ceb6',

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
   
        //supplier defect type
        Highcharts.chart('supplierDefectType', {
            chart: {
                type: 'column',
                animation: true,
                height: 217
            },
            title: {
                text: '',
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                categories: ['Supplier1', 'Supplier2', 'Supplier3', 'Supplier4', 'Supplier5', 'Supplier6'],
                lineColor: '#e8e8e8',
            },
            credits: { enabled: false },
            yAxis: {
                min: 0,
                title: {
                    text: 'Defect Type'
                },
                stackLabels: {
                    enabled: false
                },
                labels: {
                    enabled: false,
                },
                lineColor: 'transparent',
                gridLineColor: 'transparent'
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
            },
            /* tooltip: {
                headerFormat: '<b>{point.x}</b><br/>',
                pointFormat: '{series.name}: {point.y}<br/>Total: {point.stackTotal}'
            }, */
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true
                    }
                }
            },
            series: [{
                name: 'Rejected',
                data: [7, 7, 7, 7, 7, 7],
                color: '#d05555'
            }, {
                name: 'Impact',
                data: [7, 7, 7, 7, 7, 7],
                color: '#2596be'
            }, {
                name: 'No Impact',
                data: [7, 7, 7, 7, 7, 7],
                color: '#7d7d7d'
            }]
        });
        //Delivery Time
        Highcharts.chart('deliveryTime', {
            chart: {
                type: 'bar',
                animation: true,
                height: 263,
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
                categories: ['Supplier 1', 'Supplier 2', 'Supplier 3', 'Supplier 4', 'Supplier 5', 'Supplier 6'],

            },
            yAxis: {
                min: 0,
                max: 100,
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },

                gridLineColor: 'transparent',
                lineColor: '#cccccc'
            },
            legend: {
                reversed: true
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        format: '{y} %',
                        color: '#ffffff'
                    }

                }
            },

            series: [{
                name: 'Early',
                data: [74, 73, 47, 53, 67, 62],
                color: '#2596be'
            }, {
                name: 'Late',
                data: [18, 14, 35, null, 21, 10],
                color: '#d05555'
            }, {
                name: 'On-Time',
                data: [8, 13, 18, 47, 12, 28],
                color: '#ffb52e'
            }]
        });


        //availability
        Highcharts.chart('availability', {
            chart: {
                type: 'pie',
                height: 110,
                margin: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#5d89a8'],
            title: {
                text: '94%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 40,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'Availability',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 60,
                margin: 0,
                style: { "fontSize": '11', "color": '#000000' }
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
                data: [["Availability", 94]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
}