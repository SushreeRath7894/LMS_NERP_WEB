function oppourtunityHighChart(){
	  //Churn reasons
        Highcharts.chart('churnReason', {
            chart: {
                animated: true,
                height: 250
            },
            title: {
                text: 'Churn Reasons',
                align: 'left'
            },
            credits: { enabled: false, },
            xAxis: {
                categories: ['Competitor Chosen', 'Functionality', 'Price', 'Needs Changed', 'Other']
            },
            yAxis: {
                title: {
                    text: ''
                }
            },

            series: [{
                type: 'bar',
                name: 'Number of lost opportunities',
                data: [59, 83, 65, 228, 184],
                color: '#00cddb',
            }, {
                name: 'lost purchase value',
                color: 'transparent',
                lineColor: 'transparent',
                data: [47, 83.33, 70.66, 239.33, 175.66],
                type: 'spline',
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    backgroundColor: '#005c9f',
                    align: 'right',
                    format: '{point.y:.1f}',
                    style: {
                        fontSize: '10px',
                    }
                },
                marker: {
                    lineWidth: 2,
                    fillColor: '#005c9f'
                }
            },]
        });
        //Sales Opportunity and Purchase Value
        Highcharts.chart('salesOppAndPurchase', {
            chart: {
                animation: true,
                height: 225
            },
            title: {
                text: 'New Sales Opportunities & Purchase Value',
                align: 'left'
            },

            subtitle: {
                text: ''
            },

            yAxis: {
                title: {
                    text: 'Number of Employees',
                    style: { 'display': 'none' }
                }
            },
            credits: { enabled: false },

            xAxis: {
                tickLength: 0,
                tickWidth: 0,
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
            legend: {
                layout: 'horizontal',
                align: 'center',
                verticalAlign: 'bottom'
            },

            plotOptions: {
                series: {
                    label: {
                        connectorAllowed: false
                    },
                    pointStart: 2010
                }
            },

            series: [{
                name: 'Number of opportunities',
                data: [43934, 48656, 65165, 81827, 112143, 142383,
                    171533, 165174, 155157, 161454, 154610],
                color: '#78daa9'
            }, {
                name: 'Purchase Value',
                data: [24916, 37941, 29742, 29851, 32490, 30282,
                    38121, 36885, 33726, 34243, 31050],
                dashStyle: 'ShortDash',
                color: '#107f8c',
            },],

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
        //Opportunities & Average Purchase
        Highcharts.chart('oppAndAvgPurchase', {
            chart: {
                type: 'column',
                animation: true,
                height: 248
            },
            title: {
                text: 'Number of Opportunities & Average Purchase Value By Package',
                align: 'left'
            },
            xAxis: {
                categories: ['Basic', 'Premium', 'Professional'],
                lineColor: 'transparent',
            },
            credits: { enabled: false },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                stackLabels: {
                    enabled: true
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
                name: 'Number of Opportunities',
                data: [3, 5, 1],
                color: '#005c9f'
            }, {
                name: 'Average Purchase Valuep',
                data: [14, 8, 8],
                color: '#00cddb'
            }]
        });

}