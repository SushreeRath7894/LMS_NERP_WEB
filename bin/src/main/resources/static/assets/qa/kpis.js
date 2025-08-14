function kpiHighchat(){
	
	    //Raw Material Testing
        Highcharts.chart('rawMaterialTesting', {
            chart: {
                type: 'pie',
                animation: true,
                height: 200
            },
            credits: { enabled: false },
            title: {
                text: '15',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 85,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'Total',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 105,
                margin: 0,
                style: { "fontSize": '11', "color": '#000000' }
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
                innerSize: '60%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '1%'
                }
            }]
        });
        //CRQS Testing
        Highcharts.chart('CRQSTesting', {
            chart: {
                type: 'pie',
                animation: true,
                height: 200
            },
            credits: { enabled: false },
            title: {
                text: '15',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 85,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'Total',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 105,
                margin: 0,
                style: { "fontSize": '11', "color": '#000000' }
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
                innerSize: '60%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '1%'
                }
            }]
        });
        //PCRO Testing
        Highcharts.chart('PCROTesting', {
            chart: {
                type: 'pie',
                animation: true,
                height: 200
            },
            credits: { enabled: false },
            title: {
                text: '15',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 85,
                margin: 0,
                style: { "fontSize": '20', "color": '#000000' }
            },
            subtitle: {
                text: 'Total',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 105,
                margin: 0,
                style: { "fontSize": '11', "color": '#000000' }
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
                innerSize: '60%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '1%'
                }
            }]
        });
        //Raw Material Testing Timeline
        Highcharts.chart('rawMaterialTestingTimeline', {
            chart: {
                type: 'line',
                animation: true,
                height: 328
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: false,

            yAxis: {
                title: {
                    text: 'Days'
                }
            },

            xAxis: {
                categories: ['Material Received', 'Test Requested', 'Sample Collected', 'Sample Tested', 'Test Approved'],
                labels: {
                    autoRotation: 0,
                    width: 20,
                    wordBreak: 'break-all',
                    step: 1,
                    formatter: function () {
                        return this.value.replace(/ /g, '<br />');
                    }
                }
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
                }
            },

            series: [{
                name: 'M1',
                data: [43934, 48656, 65165, 81827, 112143]
            }, {
                name: 'M2',
                data: [24916, 37941, 29742, 29851, 32490]
            }, {
                name: 'M3',
                data: [11744, 30000, 16005, 19771, 20185]
            }, {
                name: 'M4',
                data: [null, null, 12543, 24563, null]
            }, {
                name: 'M5',
                data: [21908, 5548, 8105, 11248, 8989]
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
        //PCRO Testing Sequence
        Highcharts.chart('PCROTestingSequence', {
            chart: {
                type: 'line',
                animation: true,
                height: 327
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: false,

            yAxis: {
                title: {
                    text: 'Days'
                }
            },

            xAxis: {
                categories: ['Material Received', 'Test Requested', 'Sample Collected', 'Sample Tested', 'Test Approved'],
                labels: {
                    autoRotation: 0,
                    width: 20,
                    wordBreak: 'break-all',
                    step: 1,
                    formatter: function () {
                        return this.value.replace(/ /g, '<br />');
                    }
                }
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
                }
            },

            series: [{
                name: 'M1',
                data: [43934, 48656, 65165, 81827, 112143]
            }, {
                name: 'M2',
                data: [24916, 37941, 29742, 29851, 32490]
            }, {
                name: 'M3',
                data: [11744, 30000, 16005, 19771, 20185]
            }, {
                name: 'M4',
                data: [null, null, 12543, 24563, null]
            }, {
                name: 'M5',
                data: [21908, 5548, 8105, 11248, 8989]
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
        //CRQS Testing Sequence
        Highcharts.chart('CRQSTestingSequence', {
            chart: {
                type: 'line',
                animation: true,
                height: 328
            },
            title: {
                text: ''
            },

            subtitle: {
                text: ''
            },
            credits: false,

            yAxis: {
                title: {
                    text: 'Days'
                }
            },

            xAxis: {
                categories: ['Material Received', 'Test Requested', 'Sample Collected', 'Sample Tested', 'Test Approved'],
                labels: {
                    autoRotation: 0,
                    width: 20,
                    wordBreak: 'break-all',
                    step: 1,
                    formatter: function () {
                        return this.value.replace(/ /g, '<br />');
                    }
                }
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
                }
            },

            series: [{
                name: 'M1',
                data: [43934, 48656, 65165, 81827, 112143]
            }, {
                name: 'M2',
                data: [24916, 37941, 29742, 29851, 32490]
            }, {
                name: 'M3',
                data: [11744, 30000, 16005, 19771, 20185]
            }, {
                name: 'M4',
                data: [null, null, 12543, 24563, null]
            }, {
                name: 'M5',
                data: [21908, 5548, 8105, 11248, 8989]
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
        //Sample Alarm Threshold
        Highcharts.chart('alarmThreshold', {
            chart: {
                animation: true,
                height: 327
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
                categories: ['Low1', 'Low2', 'High1', 'High2', 'Reject Low', 'Reject High'],
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
                    text: '',
                    style: {
                        display: 'none',
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: '',
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
                name: '',
                showInLegend: false,
                type: 'column',
                color: '#9fdca7',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7],

            }, {
                name: 'Threshold',
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
}