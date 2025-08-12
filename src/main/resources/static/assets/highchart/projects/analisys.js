function analysisHighChat(){
         //sales volume by country
        Highcharts.chart('salesVolByCountry', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 100,
                marginTop: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#003961'],
            title: {
                text: '86',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '13', "color": '#000000' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
                // labels: {
                //     distance: 0,
                //     y: 16,
                //     style: {
                //         fontSize: '12px'
                //     }
                // },
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#ec0e90',
                    thickness: 20
                }, {
                    from: 22,
                    to: 42,
                    color: '#f7f79f',
                    thickness: 20
                }, {
                    from: 42,
                    to: 70,
                    color: '#52a6a0',
                    thickness: 20
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#333333',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '50%',
                    backgroundColor: '#730101',
                    topWidth: 1,
                    baseWidth: 5,
                    baseLength: '10%',
                    rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });
        //sales volume by country 1
        Highcharts.chart('salesVolByCountry1', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 100,
                marginTop: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#003961'],
            title: {
                text: '86',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '13', "color": '#000000' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
                // labels: {
                //     distance: 0,
                //     y: 16,
                //     style: {
                //         fontSize: '12px'
                //     }
                // },
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#ec0e90',
                    thickness: 20
                }, {
                    from: 22,
                    to: 42,
                    color: '#f7f79f',
                    thickness: 20
                }, {
                    from: 42,
                    to: 70,
                    color: '#52a6a0',
                    thickness: 20
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#333333',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '50%',
                    backgroundColor: '#730101',
                    topWidth: 1,
                    baseWidth: 5,
                    baseLength: '10%',
                    rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });
        //sales volume by country 2
        Highcharts.chart('salesVolByCountry2', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 100,
                marginTop: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#003961'],
            title: {
                text: '86',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '13', "color": '#000000' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
                // labels: {
                //     distance: 0,
                //     y: 16,
                //     style: {
                //         fontSize: '12px'
                //     }
                // },
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#ec0e90',
                    thickness: 20
                }, {
                    from: 22,
                    to: 42,
                    color: '#f7f79f',
                    thickness: 20
                }, {
                    from: 42,
                    to: 70,
                    color: '#52a6a0',
                    thickness: 20
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#333333',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '50%',
                    backgroundColor: '#730101',
                    topWidth: 1,
                    baseWidth: 5,
                    baseLength: '10%',
                    rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });
        //sales volume by country 3
        Highcharts.chart('salesVolByCountry3', {

            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false,
                backgroundColor: 'transparent',
                height: 100,
                marginTop: 0
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            colors: ['#003961'],
            title: {
                text: '86',
                align: 'center',
                verticalAlign: 'center',
                floating: true,
                y: 70,
                margin: 0,
                style: { "fontSize": '13', "color": '#000000' }

            },
            subtitle: {
                text: ''
            },
            credits: {
                enabled: false
            },
            pane: {
                startAngle: -98,
                endAngle: 99.9,
                background: null,
                center: ['50%', '75%'],
                size: '110%'
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 50, lineWidth: 0,
                tickPixelInterval: 0,
                tickPosition: 'inside',
                tickColor: '#FFFFFF',
                tickLength: 0,
                minorTickInterval: null,
                // labels: {
                //     distance: 0,
                //     y: 16,
                //     style: {
                //         fontSize: '12px'
                //     }
                // },
                labels: {
                    enabled: false
                },
                plotBands: [{
                    from: 0,
                    to: 22,
                    color: '#ec0e90',
                    thickness: 20
                }, {
                    from: 22,
                    to: 42,
                    color: '#f7f79f',
                    thickness: 20
                }, {
                    from: 42,
                    to: 70,
                    color: '#52a6a0',
                    thickness: 20
                }]
            },

            series: [{
                name: '',
                data: [22],

                dataLabels: {
                    borderWidth: 0,
                    color: '#333333',
                    style: {
                        fontSize: '16px'
                    },
                    enabled: false,
                },
                dial: {
                    radius: '50%',
                    backgroundColor: '#730101',
                    topWidth: 1,
                    baseWidth: 5,
                    baseLength: '10%',
                    rearLength: '0%'
                },
                pivot: {
                    radius: 0
                },

            }]

        });
        //revenue and profit
        Highcharts.chart('revenueAndProfit', {
            chart: {
                zoomType: 'xy',
                animation: true,
                backgroundColor: 'transparent',
                height: 250
            },
            title: {
                text: 'Revenue and Profit Trend',
                align: 'center'
            },
            subtitle: {
                text: ''
            },
            credits: { enabled: false },
            xAxis: [{
                categories: ['Q12021', 'Q22021', 'Q32021', 'Q12022', 'Q12022'],
                crosshair: true,
                labels: {
                    rotation: -45,
                    style: {
                        fontSize: '12px',
                    }
                }
            }],
            yAxis: [{
                labels: {
                    format: '{value}',
                    style: {
                        color: '#005c9f'
                    }
                },
                title: {
                    text: 'Revenue/Profit',
                    style: {
                        color: '#005c9f'
                    }
                }
            }, {
                title: {
                    text: 'Profit Margin',
                    style: {
                        color: '#68bfff'
                    }
                },
                labels: {
                    format: '{value}',
                    style: {
                        color: '#68bfff'
                    }
                },
                opposite: true
            }],
            tooltip: {
                shared: true
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
                backgroundColor: 'transparent'
            },
            series: [{
                name: 'Revenue',
                type: 'column',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 43.2], color: '#005c9f',
                tooltip: {
                    valueSuffix: ' mm'
                }

            }, {
                name: 'Profit',
                type: 'column',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 22.1],
                color: '#68bfff',
                tooltip: {
                    valueSuffix: ' mm'
                }

            },
            {
                name: 'Profit Margin',
                type: 'spline',
                data: [13.6, 14.9, 15.8, 15.7, 15.1], color: '#ea34a1',
                tooltip: {
                    valueSuffix: '°C'
                }
            }]
        });
        //map


        //productByRevenue
        Highcharts.chart('productByRevenue', {
            chart: {
                zoomType: 'xy',
                animation: true,
                backgroundColor: 'transparent',
                height: 324
            },
            title: {
                text: 'Top 10 Products By Revenue',
                align: 'center'
            },
            subtitle: {
                text: ''
            },
            credits: { enabled: false },
            xAxis: [{
                categories: ['Q12021', 'Q22021', 'Q32021', 'Q12022', 'Q12022'],
                crosshair: true,
                labels: {
                    rotation: -45,
                    style: {
                        fontSize: '12px',
                    }
                }
            }],
            yAxis: [{
                labels: {
                    enabled: false,
                    format: '{value}',
                    style: {
                        color: '#005c9f'
                    }
                },
                title: {
                    text: '',
                    style: {
                        color: '#005c9f'
                    }
                }
            }, {
                title: {
                    text: '',
                    style: {
                        color: '#68bfff'
                    }
                },
                labels: {
                    enabled: false,
                    format: '{value}',
                    style: {
                        color: '#68bfff'
                    }
                },
                opposite: true
            }],
            tooltip: {
                shared: true
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
                backgroundColor: 'transparent'
            },
            series: [{
                name: 'Revenue',
                type: 'column',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 21.7], color: '#005c9f',
                tooltip: {
                    valueSuffix: ' mm'
                },
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    align: 'right',
                    format: '{point.y:.1f}',
                    style: {
                        fontSize: '10px',
                    }
                }

            }, {
                name: 'Profit',
                type: 'column',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 21.7],
                color: '#68bfff',
                tooltip: {
                    valueSuffix: ' mm'
                },
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    align: 'right',
                    format: '{point.y:.1f}',
                    style: {
                        fontSize: '10px',
                    }
                }

            },
            {
                name: 'Profit Margin',
                type: 'spline',
                data: [0.3, 0.5, 0.3, 0.5, 0.8], color: '#ea34a1',
                marker: { fillColor: 'transparent' },
                tooltip: {
                    valueSuffix: ''
                }, lineColor: 'transparent',
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    backgroundColor: '#ea34a1',
                    align: 'right',
                    format: '{point.y:.1f}',
                    style: {
                        fontSize: '10px',
                    }
                }
            }]
        });
       
}