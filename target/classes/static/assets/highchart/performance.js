function performanceHighChat(){
	
	  Highcharts.chart('performanceDashboard', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 250,
                marginTop: 30,
                animation: true,
            },
            credits: {
                enabled: false
            },navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: '12,924 <span>&#8364;</span>',
                style: {
                    color: '#04427d',
                    fontSize: '30px'
                }
            },
            subtitle: {
                text: 'AVERAGE WEEKLY SALES REVIEW',
                style: {
                    color: '#04427d',
                    fontSize: '16px'
                }
            },
            xAxis: {
                type: 'category',
                min: 0,
                max: 30,
                tickLength: 0,
                lineColor: '#04427d',
                labels: {
                    rotation: -90,
                    style: {
                        fontSize: '10px',
                        fontFamily: 'Verdana, sans-serif',
                        color: '#04427d'
                    }
                }
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: 'Average weekly sales: <b>{point.y:.1f} thousand</b>'
            },
            series: [{
                name: 'Average weekly sales',
                colors: ['#04427d'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 7,
                data: [
                    ['Week 1 2016', 37.33],
                    ['Week 2 2016', 31.18],
                    ['Week 3 2016', 27.79],
                    ['Week 4 2016', 22.23],
                    ['Week 5 2016', 21.91],
                    ['Week 6 2016', 21.74],
                    ['Week 7 2016', 21.32],
                    ['Week 8 2016', 20.89],
                    ['Week 9 2016', 20.67],
                    ['Week 10 2016', 19.11],
                    ['Week 11 2016', 16.45],
                    ['Week 12 2016', 16.38],
                    ['Week 13 2016', 15.41],
                    ['Week 14 2016', 15.25],
                    ['Week 15 2016', 14.974],
                    ['Week 16 2016', 14.970],
                    ['Week 17 2016', 14.86],
                    ['Week 18 2016', 14.16],
                    ['Week 19 2016', 13.79],
                    ['Week 20 2016', 13.64],
                    ['Week 21 2016', 19.11],
                    ['Week 22 2016', 16.45],
                    ['Week 23 2016', 16.38],
                    ['Week 24 2016', 15.41],
                    ['Week 25 2016', 15.25],
                    ['Week 26 2016', 14.974],
                    ['Week 27 2016', 14.970],
                    ['Week 28 2016', 14.86],
                    ['Week 29 2016', 14.16],
                    ['Week 30 2016', 13.79],
                    ['Week 31 2016', 43.79]
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
  
        //above sales target
        Highcharts.chart('aboveSalesTarget', {
            chart: {
                type: 'pie',
                animation: true,
                backgroundColor: 'transparent',
                height: 250,
            },navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false,
            },
            plotOptions: {
                pie: {
                    innerSize: '60%',
                    dataLabels: {
                        distance: '-30%',
                        enabled: false
                    },
                    showInLegend: true,
                    colors: [
                        '#87a7c6',
                        '#04427d',
                    ],
                }

            },
            title: {
                text: '124,924 <span>&#8364;</span>',
                style: {
                    color: '#04427d',
                    fontSize: '20px'
                }
            },
            subtitle: {
                text: 'ABOVE SALES TARGET | YTD',
                style: {
                    color: '#04427d',
                    fontSize: '14px'
                }
            },
            series: [{
                showInLegend: false,
                data: [
                    ['x', 80],
                    ['y', 20],
                ],
            }]
        })
        //acumulated revenue
        Highcharts.chart('accumulatedRevenue', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 150,
                marginTop: 30,
                animation: true,
            },navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category',
                tickLength: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false
                }
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                }
            },
            legend: {
                enabled: false
            },
            // tooltip: {
            //     pointFormat: 'Average weekly sales: <b>{point.y:.1f} thousand</b>'
            // },
            series: [{
                name: 'Average monthly sales',
                colors: ['#2404a4'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 10,
                data: [
                    ['Jan', 15],
                    ['Feb', 20],
                    ['Mar', 25],
                    ['Apr', 30],
                    ['May', 35],
                    ['Jun', 40],
                    ['Jul', 45],
                    ['Aug', 50],
                    ['Sep', 55],
                    ['Oct', 60],
                    ['Nov', 65],
                    ['Dec', 70]
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        //sales Country Performance
        Highcharts.chart('salesCountryPerformance', {
            chart: {
                type: 'pie',
                animation: true,
                backgroundColor: 'transparent',
                height: 280,
            },navigation: {
                buttonOptions: {
                    enabled: false
                }
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
                        enabled: false
                    },
                    colors: [
                        '#0070c0',
                        '#97ccf2',
                        '#0a58ca',
                    ],
                    showInLegend: true
                }
            },
            legend: {
                enabled: true,
                floating: false,
                borderWidth: 0,
                align: 'center',
                layout: 'vertical',
                verticalAlign: 'bottom',
                margin: 0,
                padding: 0,
                labelFormatter: function () {
                    return '<span style="color:' + this.color + '">' + this.name + ': </span>  <b>' + this.y + '&#8364;<br/>';
                }
            },

            series: [{
                data: [
                    ['Austria', 70],
                    ['Germany', 25],
                    ['Switzerland', 5],
                ]
            }]
        });
        //average Revenue Per Unit
        Highcharts.chart('averageRevenuePerUnit', {
            chart: {
                type: 'areaspline',
                backgroundColor: 'transparent',
                height: 100,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            title: {
                text: '',
            },
            subtitle: {
                text: ''
            },
            legend: {
                enabled: false
            },
            xAxis: {
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                visible: false,
            },
            yAxis: {
                title: {
                    text: ''
                },
                lineColor: 'transparent',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
            },
            tooltip: {
                enabled: false,
                shared: true,
                // headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                series: {
                    pointStart: 2000
                },
                areaspline: {
                    fillOpacity: 0.5,
                    color: '#2404a4'
                },
            },
            series: [{
                name: '',
                data:
                    [
                        38000,
                        37300,
                        37892,
                        38564,
                        36770,
                        36026,
                        34978,
                        35657,
                        35620,
                        35971,
                        36409,
                        36435,
                        34643,
                        34956,
                        33199,
                        31136,
                        30835,
                        31611,
                        30666,
                        30319,
                        31766
                    ],
                dataLabels: {
                    enabled: false,
                }
            },]
        });
        //customer lifetime value
        Highcharts.chart('customerLifetimeValue', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 100
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
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
                lineColor: 'transparent',
                labels: {
                    enabled: false
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
                        enabled: true
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
        //customer acquisition cost
        Highcharts.chart('customerAcquisitionCost', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 100,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#2404a4'],
            credits: false,
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category',
                title: {
                    text: ""
                },
                labels: {
                    enabled: false
                },
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
                    ['2', 109],
                    ['3', 112],
                    ['4', 125],
                    ['5', 106],
                    ['6', 112],
                    ['7', 143],
                    ['8', 207],
                    ['9', 386],
                    ['10', 408]

                ],
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    align: 'right',
                    y: 0, // 10 pixels down from the top
                    style: {
                        fontSize: '10px'

                    }
                }
            }]
        });
}