function kpiHighChat(){
	
	  //CRM KPI Revenue per Aquisition
        Highcharts.chart('cRMKpiRevenuePerAquisition', {
            chart: {
                type: 'area',
                zoomType: 'xy',
                animation: true,
                height: 179
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
                categories: ['W1', 'W2', 'W3', 'W4', 'W5', 'W6', 'W7', 'W8', 'W9', 'W10', 'W11'],
                allowDecimals: false,
                title: {
                    text: '',
                }
            },
            yAxis: {
                title: {
                    text: ''
                },
                labels: { format: '₹{value}', },
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
                data: [9, 10, 8, 5, 4, 3, 2, 5, 10]
            }]
        });
        
        
        
         //CRM KPI Marketing Spend
        Highcharts.chart('cRMKpiMarketingSpend', {
            chart: {
                type: 'column',
                height: 125,
                animation: true,
            },
            navigation: {
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
                categories: ['W1', 'W2', 'W3', 'W4', 'W5', 'W6', 'W7', 'W8', 'W9', 'W10', 'W11', 'W12'],
                labels: {
                    style: {
                        fontSize: '10px',
                    }
                }
            },
            yAxis: {
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    style: {
                        fontSize: '10px',
                    }
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                groupPadding: 0,
                data: [123, 543, 242, 132, 444, 654, 134, 754.234, 543, 234, 345],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        
        
        
        
        //CRM KPI CPA 1
        Highcharts.chart('cRMKpiCpaOne', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 35,
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
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
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
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#0162a9',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        
        
        
         //CRM KPI CPA 2
        Highcharts.chart('cRMKpiCpaTwo', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 35,

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
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
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
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#0162a9',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        //CRM KPI CPA 3
        Highcharts.chart('cRMKpiCpaThree', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 35,

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
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
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
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#0162a9',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        //CRM KPI CPA 4
        Highcharts.chart('cRMKpiCpaFour', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 35,

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
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
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
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#0162a9',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        //CRM KPI CPA 5
        Highcharts.chart('cRMKpiCpaFive', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 35,

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
                tickLength: 0,
                tickWidth: 0,
                lineColor: 'transparent',
                labels: {
                    enabled: false,
                },
                categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10']
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
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            series: [{
                name: '',
                data: [16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#0162a9',
                showInLegend: false,
                marker: {
                    enabled: false
                }
            },]
        });
        
        
        
        //CRM KPI Profit Per Aquisition
        Highcharts.chart('cRMKpiProfitPerAquisition', {
            chart: {
                type: 'bar',
                height: 259,
                animation: true,
            },
            navigation: {
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
                categories: ['Display', 'Fairs', 'Search', 'Social', 'TV'],
                title: {
                    text: null
                },
                gridLineColor: 'transparent',
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                labels: {
                    enabled: false,
                },
                gridLineColor: 'transparent',
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true,
                        format: '₹{y}'
                    },
                    pointWidth: 20,
                    groupPadding: 0
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                data: [631, 527, 221, 137, 675],
            }]
        });
        
        	
        
}