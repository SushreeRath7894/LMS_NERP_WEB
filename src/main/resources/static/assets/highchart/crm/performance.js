
function performanceHighChat(){
	
	
	
	 //CRM Perf Acquisitions By Campaign
        Highcharts.chart('cRMPerfAcquisitionsByCampaign', {
            chart: {
                type: 'pie',
                height: 140,
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
                text: '',
            },
            credits: {
                enabled: false,
            },
            colors: ['#2ca0e8', '#07507d', '#042438'],
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
                data: [["Campaign 1", 54], ["Campaign 2", 20], ["Campaign 3", 32]],
                size: '100%',
                innerSize: '0%',
                showInLegend: false,
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        //CRM Perf CTR By Campaign
        Highcharts.chart('cRMPerfCTRByCampaign', {
            chart: {
                type: 'column',
                height: 160,
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
                type: 'category',
            },
            yAxis: {
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
            series: [{
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 50,
                data: [
                    {
                        name: 'Campaign 1',
                        y: 15,
                        color: '#2ca0e8',
                    }, {
                        name: 'Campaign 2',
                        y: 35,
                        color: '#07507d',
                    }, {
                        name: 'Campaign 3',
                        y: 25,
                        color: '#042438',
                    },
                ],
                dataLabels: {
                    enabled: true,
                }
            }]
        });
        //CRM Perf CPC By Campaign
        Highcharts.chart('cRMPerfCPCByCampaign', {
            chart: {
                type: 'bar',
                height: 247,
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
            colors: ['#2ca0e8', '#07507d', '#042438'],
            xAxis: {
                categories: ['Campaign 1', 'Campaign 2', 'Campaign 3'],
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
                    overflow: 'justify'
                },
            },
            tooltip: {
                valueSuffix: ' millions'
            },
            plotOptions: {
                bar: {
                    colorByPoint: true,
                    dataLabels: {
                        enabled: true
                    },
                    pointWidth: 30,
                    groupPadding: 0
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                data: [631, 727, 721],
            }]
        });
        //CRM Perf CPA By Campaign
        Highcharts.chart('cRMPerfCPAByCampaign', {
            chart: {
                type: 'bar',
                height: 247,
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
            colors: ['#2ca0e8', '#07507d', '#042438'],
            xAxis: {
                categories: ['Campaign 1', 'Campaign 2', 'Campaign 3'],
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
                    overflow: 'justify'
                },
            },
            tooltip: {
                valueSuffix: ' millions'
            },
            plotOptions: {
                bar: {
                    colorByPoint: true,
                    dataLabels: {
                        enabled: true
                    },
                    pointWidth: 30,
                    groupPadding: 0
                }
            },
            legend: {
                enabled: false
            },
            series: [{
                data: [631, 727, 721],
            }]
        });
        //CRM Perf Total Spend
        Highcharts.chart('cRMPerfTotalSpend', {
            chart: {
                type: 'pie',
                height: 140,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#ff5d5d', '#dfdfdf'],
            title: {
                text: '₹67619',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 55,
                margin: 0,
                style: { "fontSize": '16', "color": '#000000' }
            },
            subtitle: {
                text: 'Total Spend',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '9', "color": '#000000' }
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
                data: [["Total Spent", 65], ["", 35]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
        //CRM Perf Total Spend Campaign 1
        Highcharts.chart('cRMPerfTotalSpendCampaignOne', {
            chart: {
                type: 'pie',
                height: 140,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#2ca0e8', '#dfdfdf'],
            title: {
                text: '₹67619',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 55,
                margin: 0,
                style: { "fontSize": '16', "color": '#000000' }
            },
            subtitle: {
                text: 'Campaign 1',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '9', "color": '#000000' }
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
                data: [["Total Spent", 65], ["", 35]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
        //CRM Perf Total Spend Campaign 2
        Highcharts.chart('cRMPerfTotalSpendCampaignTwo', {
            chart: {
                type: 'pie',
                height: 140,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#07507d', '#dfdfdf'],
            title: {
                text: '₹67619',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 55,
                margin: 0,
                style: { "fontSize": '16', "color": '#000000' }
            },
            subtitle: {
                text: 'Campaign 2',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '9', "color": '#000000' }
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
                data: [["Total Spent", 65], ["", 35]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
        });
        //CRM Perf Total Spend Campaign 3
        Highcharts.chart('cRMPerfTotalSpendCampaignThree', {
            chart: {
                type: 'pie',
                height: 140,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#042438', '#dfdfdf'],
            title: {
                text: '₹67619',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 55,
                margin: 0,
                style: { "fontSize": '16', "color": '#000000' }
            },
            subtitle: {
                text: 'Campaign 3',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '9', "color": '#000000' }
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
                data: [["Total Spent", 65], ["", 35]],
                size: '120%',
                innerSize: '80%',
                showInLegend: false,
                dataLabels: {
                    enabled: false
                }
            }]
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
}