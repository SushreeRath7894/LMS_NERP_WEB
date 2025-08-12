function cost(){
	 //procurement roi
        Highcharts.chart('procurementROI', {
            chart: {
                animation: true,
                height: 235,
                marginLeft: 30
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
            credits: { enabled: false },
            xAxis: [{
                categories: ['Battery', 'Display', 'Others', 'Sensors', 'Switches', 'Transistors'],
                crosshair: true,
                lineColor: '#cccccc'
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color: '#136e6d'
                    }
                },
                title: {
                    text: 'Temperature',
                    style: {
                        display: 'none',
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: 'Precipitation',
                    style: {
                        display: 'none',
                    }
                },
                labels: {
                    format: '',
                    style: {
                        display: 'none',
                    }
                },
                opposite: false
            }],
            tooltip: {
                shared: false
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
                backgroundColor: 'transparent'
            },
            series: [{
                name: 'Precipitation',
                type: 'column',
                color: '#9fdca7',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7],

            }, {
                name: 'Benchmark',
                type: 'spline',
                data: [10, 10, 10, 10, 10, 10],
                color: '#b4617c',
                lineWidth: 3,
                dataLabels: {
                    enabled: false,
                },
                marker: {
                    enabled: false
                }
            }]
        });
        //spend under management
        Highcharts.chart('spendUnderManagement', {
            chart: {
                zoomType: 'xy',
                animation: true,
                height: 254
            },
            title: {
                text: 'Spend Under Management',
                align: 'left',
                style: { 'fontSize': '13px' }
            },
            subtitle: {
                text: 'by Project',
                align: 'left',
                style: { 'fontSize': '10px' }
            },
            credits: { enabled: false },

            yAxis: {
                title: {
                    text: ''
                },
            },

            xAxis: {
                categories: [],
                crosshair: true,
                lineColor: '#cccccc'
            },

            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
                backgroundColor: 'transparent'
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
                name: 'ER-289',
                data: [43934, 48656, 65165, 81827, 11243, 14283,
                    17533, 15174, 15517, 16154, 15610],
                color: '#d14f57'
            }, {
                name: 'ER-569',
                data: [24916, 37941, 29742, 29851, 32490, 30282,
                    38121, 36885, 33726, 34243, 31050],
                color: '#8bdeac'
            }, {
                name: 'LA-273',
                data: [11744, 30000, 16005, 19771, 20185, 24377,
                    32147, 30912, 29243, 29213, 25663],
                color: '#fae377'
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
        
        
          //cost 5 year trend
        Highcharts.chart('costFiveYearTrend', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 5
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
                color: '#77c6ff',
                showInLegend: false,
            },]
        });
        //cost 5 year trend 1
        Highcharts.chart('costFiveYearTrend1', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 5
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
                color: '#77c6ff',
                showInLegend: false,
            },]
        });
        //cost 5 year trend 2
        Highcharts.chart('costFiveYearTrend2', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 5
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
                color: '#77c6ff',
                showInLegend: false,
            },]
        });
        //cost 5 year trend 3
        Highcharts.chart('costFiveYearTrend3', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 5
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
                color: '#77c6ff',
                showInLegend: false,
            },]
        });
        //cost 5 year trend 4
        Highcharts.chart('costFiveYearTrend4', {
            chart: {
                type: 'line',
                animation: true,
                backgroundColor: 'transparent',
                height: 30,
                margin: 5
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
                color: '#77c6ff',
                showInLegend: false,
            },]
        });
        
        
          //cost reduction by supplier category
        Highcharts.chart('costReductionBySupplier', {
            chart: {
                type: 'pie',
                animation: true,
                height: 200
            },
            credits: { enabled: false },
            title: {
                text: ''
            },
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
                data: [["Transistor", 34], ["Switches", 19], ["Sensors", 16], ["Battery", 15], ["Display", 10], ["Other", 6]],
                size: '50%',
                innerSize: '45%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    format: '{point.percentage:.1f} %'
                }
            }]
        });
        //cost savings
        Highcharts.chart('costSavings', {
            chart: {
                type: 'bar',
                animation: true,
                height: 150
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
            xAxis: {
                categories: ['Switches', 'Display', 'Transistors', 'Others', 'Sensors', 'Battery'],
                title: {
                    text: 'SAVINGS',
                },
                gridLineColor: 'transparent',
                lineColor: '#cccccc'
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                labels: {
                    enabled: false,
                },
                gridLineWidth: 0
            },
            tooltip: {
                valueSuffix: '%'
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true,
                        format: '{y} %'
                    },
                    groupPadding: 0.1
                }
            },
            legend: {
                enabled: false,
            },
            credits: {
                enabled: false
            },
            series: [{
                name: '',
                data: [17, 16, 15, 15, 12, 9]
            }],

        });
        //cost avoidance
        Highcharts.chart('costAvoidance', {
            chart: {
                type: 'bar',
                animation: true,
                height: 150
            }, navigation: {
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
            xAxis: {
                categories: ['Switches', 'Display', 'Transistors', 'Others', 'Sensors', 'Battery'],
                title: {
                    text: 'AVOIDANCE',
                },
                gridLineColor: 'transparent',
                lineColor: '#cccccc'
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                },
                labels: {
                    enabled: false,
                },
                gridLineWidth: 0
            },
            tooltip: {
                valueSuffix: '%'
            },
            plotOptions: {
                bar: {
                    dataLabels: {
                        enabled: true,
                        format: '{y} %'
                    },
                    groupPadding: 0.1
                }
            },
            legend: {
                enabled: false,
            },
            credits: {
                enabled: false
            },
            series: [{
                name: '',
                data: [17, 16, 15, 15, 12, 9]
            }],

        });

   //kpiCostSaving1
        Highcharts.chart('kpiCostSaving1', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //kpiCostSaving2
        Highcharts.chart('kpiCostSaving2', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //kpiCostSaving3
        Highcharts.chart('kpiCostSaving3', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //kpiCostSaving4
        Highcharts.chart('kpiCostSaving4', {
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
                marker: {
                    enabled: false
                }
            },]
        });

//department KPIs1
Highcharts.chart('departmentKPIs1', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //department KPIs2
        Highcharts.chart('departmentKPIs2', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //department KPIs3
        Highcharts.chart('departmentKPIs3', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //department KPIs4
        Highcharts.chart('departmentKPIs4', {
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
                marker: {
                    enabled: false
                }
            },]
        });

        //supplier performance1
        Highcharts.chart('supplierPerformance1', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //supplier performance2
        Highcharts.chart('supplierPerformance2', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //supplier performance3
        Highcharts.chart('supplierPerformance3', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //supplier performance4
        Highcharts.chart('supplierPerformance4', {
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
                marker: {
                    enabled: false
                }
            },]
        });


        //operational KPIs1
        Highcharts.chart('operationalKPIs1', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //operational KPIs2
        Highcharts.chart('operationalKPIs2', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //operational KPIs3
        Highcharts.chart('operationalKPIs3', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //operational KPIs4
        Highcharts.chart('operationalKPIs4', {
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
                marker: {
                    enabled: false
                }
            },]
        });


        //Spend under management 1
        Highcharts.chart('spendUnderManagement1', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //Spend under management 2
        Highcharts.chart('spendUnderManagement2', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //Spend under management 3
        Highcharts.chart('spendUnderManagement3', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //Spend under management 4
        Highcharts.chart('spendUnderManagement4', {
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
                marker: {
                    enabled: false
                }
            },]
        });

////////////////////////////////////////////////////////////////////////////////////
        //Maverick 1
        Highcharts.chart('maverick1', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //Maverick 2
        Highcharts.chart('maverick2', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //Maverick 3
        Highcharts.chart('maverick3', {
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
                marker: {
                    enabled: false
                }
            },]
        });
        //Maverick 4
        Highcharts.chart('maverick4', {
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
                marker: {
                    enabled: false
                }
            },]
        });
}