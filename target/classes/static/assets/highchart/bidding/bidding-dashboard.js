function operationalHighChat(){
    
    	 $("#operationalImg").hide();
     	$("#kpiImg").show();
     	
    	 //cost breakdown
        Highcharts.chart('costBreakdown', {
            chart: {
                type: 'pie',
                animation: true,
                backgroundColor: 'transparent',
                height: 130,
                margin: 0
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },

            plotOptions: {
                pie: {
                    dataLabels: {
                        distance: '-30%'
                    },
                    colors: [
                        '#15807e',
                        '#7dcbab',
                    ],
                    innerSize: '30%'
                }
            }, 

             series: [{
                data: [
                    ['Sales', 33],
                    ['Marketing', 67],
                ]
            }] 
        });
   	 
    	
        //sales revenue
        Highcharts.chart('salesRevenue', {
            chart: {
                type: 'area',
                backgroundColor: 'transparent',
                height: 200
            },
            credits: false,
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            yAxis: {
                title: {
                    useHTML: true,
                    text: ''
                }
            },
            tooltip: {
                shared: true,
                headerFormat: '<span style="font-size:12px"><b>{point.key}</b></span><br>'
            },
            plotOptions: {
                area: {
                    stacking: 'normal',
                    lineColor: 'transparent',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: 'transparent'
                    }
                }
            },
            series: [{
                name: 'New Customers',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039],
                color: '#15807e'
            }, {
                name: 'Up Cross Selling',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039],
                color: '#7ccbaa'
            }]
        });
       
        //incremental sales
        Highcharts.chart('incrementalSales', {
            chart: {
                type: 'bar',
                animation: true,
                backgroundColor: 'transparent',
                height: 240,
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            xAxis: {
                categories: [
                    'Jan'
                ],
                crosshair: true,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            legend: {
                verticalAlign: 'top',
            },

            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            },
            series: [{
                name: 'A',
                data: [49.9],
                color: '#a7e16b'

            }, {
                name: 'B',
                data: [83.6],
                color: '#15807e'

            }, {
                name: 'C',
                data: [48.9],
                color: '#51a2b9'

            }, {
                name: 'D',
                data: [42.4],
                color: '#98a842'

            }, {
                name: 'E',
                data: [22.4],
                color: '#98a842'

            }, {
                name: 'F',
                data: [32.4],
                color: '#98a842'

            },]
            
            
        });
        
      
      //Accumulated revenue KPI
        Highcharts.chart('accumulatedRevenueKPI', {
            chart: {
                type: 'waterfall',
                backgroundColor: 'transparent',
                height: 170,
                width: 420
            },

            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },

            credits: {
                enabled: false
            },

            xAxis: {
                type: 'category'
            },

            yAxis: {
                title: {
                    text: 'USD'
                }
            },

            legend: {
                enabled: false
            },

            tooltip: {
                pointFormat: '<b>${point.y:,.2f}</b> USD'
            },

            series: [{
                upColor: '#15807e',
                color: '#15807e',
                groupPadding: 0,
                pointWidth: 13,
                data: [{
                    name: 'Start',
                    y: 120000
                }, {
                    name: 'Product Revenue',
                    y: 569000
                }, {
                    name: 'Service Revenue',
                    y: 231000
                }, {
                    name: 'Positive Balance',
                    isIntermediateSum: true,
                    color: '#15807e'
                }, {
                    name: 'Fixed Costs',
                    y: -342000
                }, {
                    name: 'Variable Costs',
                    y: -233000
                }, {
                    name: 'Balance',
                    isSum: true,
                    color: '#15807e'
                }],
                dataLabels: {
                    enabled: true,
                    format: '{divide y 1000}k'
                },
                pointPadding: 0
            }]
        }); 
        
    }
    