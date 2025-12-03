function compliance(){
	//suppliers
        Highcharts.chart('suppliers', {
            chart: {
                type: 'pie',
                height: 110,
                margin: 0,
                width: 150
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#5d89a8'],
            title: {
                text: '804',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 35,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'SUPPLIERS',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 50,
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
                data: [["Suppliers", 804]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
        //contracted
        Highcharts.chart('contracted', {
            chart: {
                type: 'pie',
                height: 110,
                margin: 0,
                width: 150
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#4cb581', '#dfdfdf'],
            title: {
                text: '26%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 35,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'CONTRACTED',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 50,
                margin: 0,
                style: { "fontSize": '10', "color": '#000000' }
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
                data: [["Contracted", 65], ["", 35]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
        //unlisted
        Highcharts.chart('unlisted', {
            chart: {
                type: 'pie',
                height: 110,
                margin: 0,
                width: 150
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#d14f57', '#dfdfdf'],
            title: {
                text: '31%',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 35,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'UNLISTED',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 50,
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
                data: [["Unlisted", 35], ["", 65]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
        //fiveYearTrend
        Highcharts.chart('fiveYearTrend', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 10
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
                dashStyle: 'ShortDashDot',
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
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
                data: [16.0, 18.2, 23.1, 27.9, 32.2],
                color: '#04a4a4',
                showInLegend: false,
                dashStyle: 'ShortDashDot',
            },]
        });
        
        
        
        
        //fiveYearTrend1
        Highcharts.chart('fiveYearTrend1', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 10
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
                dashStyle: 'ShortDashDot',
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
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
                data: [16.0, 28.2, 23.1, 17.9, 32.2],
                color: '#00536e',
                showInLegend: false,
                dashStyle: 'ShortDashDot',
            },]
        });
        //fiveYearTrend2
        Highcharts.chart('fiveYearTrend2', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 10
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
                dashStyle: 'ShortDashDot',
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May']
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
                data: [16.0, 18.2, 23.1, 17.9, 12.2],
                color: '#c3264f',
                showInLegend: false,
                dashStyle: 'ShortDashDot',
            },]
        });
   //customer acquisition cost
        Highcharts.chart('nrOfSuppliers', {
            chart: {
                type: 'column',
                backgroundColor: 'transparent',
                height: 100,
                margin: 0
            },
            colors: [
                '#45a96c',
                '#5c8cac',
                '#d44d55'
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
                    ['3', 67]
                ],
                pointWidth: 100,
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
}