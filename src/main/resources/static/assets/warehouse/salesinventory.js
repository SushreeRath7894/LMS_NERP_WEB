function saleInventory(){
	
	 //net Sales Inventory
        Highcharts.chart('netSalesInventory', {
            chart: {
                type: 'line',
                height: 50,
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
                labels: {enabled:false},
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
                    lineColor: '#be2525'
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
        //net Profit Inventory
        Highcharts.chart('netProfitInventory', {
            chart: {
                type: 'line',
                height: 50,
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
                labels: {enabled:false},
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
                    lineColor: '#25be7e'
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