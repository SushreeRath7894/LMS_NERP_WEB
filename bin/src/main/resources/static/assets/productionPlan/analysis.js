function analysisHighChat(){
	//alert("hello")
	
	
	
	//production Analysis availability
Highcharts.chart('productionAnalysisAvailability', {

chart: {
    type: 'gauge',
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: '71.2%'
},

title: {
    text: '100%',
    align: 'center',
    verticalAlign: 'center',
    floating: true,
    y: 170,
    margin: 0,
    style: { "fontSize": '22', "color": '#005c9f' }
},
navigation: {
    buttonOptions: {
        enabled: false
    }
},
credits: { enabled: false, },

pane: {
    startAngle: -98,
    endAngle: 97.9,
    background: null,
    center: ['50%', '75%'],
    size: '100%'
},

// the value axis
yAxis: {
    min: 0,
    max: 100,
    tickPixelInterval: 40,
    tickPosition: 'inside',
    tickColor: 'transparent',
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
        distance: 20,
        style: {
            fontSize: '11px'
        }
    },
    lineWidth: 0,
    plotBands: [{
        from: 0,
        to: 100,
        color: '#55BF3B', // green
        thickness: 40
    }]
},

series: [{
    name: '',
    data: [100],
    tooltip: {
        valueSuffix: ' km/h'
    },
    dataLabels: {
        enabled: false,
    },
    dial: {
        radius: '0',
        backgroundColor: 'gray',
        baseWidth: 12,
        baseLength: '0%',
        rearLength: '0%'
    },
    pivot: {
        backgroundColor: 'gray',
        radius: 0
    }

}]

});
//production Analysis performance
Highcharts.chart('productionAnalysisPerformance', {

chart: {
    type: 'gauge',
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: '71.2%'
},

title: {
    text: '100%',
    align: 'center',
    verticalAlign: 'center',
    floating: true,
    y: 170,
    margin: 0,
    style: { "fontSize": '22', "color": '#005c9f' }
},
navigation: {
    buttonOptions: {
        enabled: false
    }
},
credits: { enabled: false, },

pane: {
    startAngle: -98,
    endAngle: 97.9,
    background: null,
    center: ['50%', '75%'],
    size: '100%'
},

// the value axis
yAxis: {
    min: 0,
    max: 100,
    tickPixelInterval: 40,
    tickPosition: 'inside',
    tickColor: 'transparent',
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
        distance: 20,
        style: {
            fontSize: '11px'
        }
    },
    lineWidth: 0,
    plotBands: [{
        from: 0,
        to: 100,
        color: '#d70010', // red
        thickness: 40
    }]
},

series: [{
    name: '',
    data: [100],
    tooltip: {
        valueSuffix: ' km/h'
    },
    dataLabels: {
        enabled: false,
    },
    dial: {
        radius: '0',
        backgroundColor: 'gray',
        baseWidth: 12,
        baseLength: '0%',
        rearLength: '0%'
    },
    pivot: {
        backgroundColor: 'gray',
        radius: 0
    }

}]

});
//production Analysis quality
Highcharts.chart('productionAnalysisQuality', {

chart: {
    type: 'gauge',
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: '71.2%'
},

title: {
    text: '100%',
    align: 'center',
    verticalAlign: 'center',
    floating: true,
    y: 170,
    margin: 0,
    style: { "fontSize": '22', "color": '#005c9f' }
},
navigation: {
    buttonOptions: {
        enabled: false
    }
},
credits: { enabled: false, },

pane: {
    startAngle: -98,
    endAngle: 97.9,
    background: null,
    center: ['50%', '75%'],
    size: '100%'
},

// the value axis
yAxis: {
    min: 0,
    max: 100,
    tickPixelInterval: 40,
    tickPosition: 'inside',
    tickColor: 'transparent',
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
        distance: 20,
        style: {
            fontSize: '11px'
        }
    },
    lineWidth: 0,
    plotBands: [{
        from: 0,
        to: 100,
        color: '#b800ff', // green
        thickness: 40
    }]
},

series: [{
    name: '',
    data: [100],
    tooltip: {
        valueSuffix: ' km/h'
    },
    dataLabels: {
        enabled: false,
    },
    dial: {
        radius: '0',
        backgroundColor: 'gray',
        baseWidth: 12,
        baseLength: '0%',
        rearLength: '0%'
    },
    pivot: {
        backgroundColor: 'gray',
        radius: 0
    }

}]

});
//production Analysis oee
Highcharts.chart('productionAnalysisOEE', {

chart: {
    type: 'gauge',
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: '71.2%'
},

title: {
    text: '100%',
    align: 'center',
    verticalAlign: 'center',
    floating: true,
    y: 170,
    margin: 0,
    style: { "fontSize": '22', "color": '#005c9f' }
},
navigation: {
    buttonOptions: {
        enabled: false
    }
},
credits: { enabled: false, },

pane: {
    startAngle: -98,
    endAngle: 97.9,
    background: null,
    center: ['50%', '75%'],
    size: '100%'
},

// the value axis
yAxis: {
    min: 0,
    max: 100,
    tickPixelInterval: 40,
    tickPosition: 'inside',
    tickColor: 'transparent',
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
        distance: 20,
        style: {
            fontSize: '11px'
        }
    },
    lineWidth: 0,
    plotBands: [{
        from: 0,
        to: 100,
        color: '#063970', // blue
        thickness: 40
    }]
},

series: [{
    name: '',
    data: [100],
    tooltip: {
        valueSuffix: ' km/h'
    },
    dataLabels: {
        enabled: false,
    },
    dial: {
        radius: '0',
        backgroundColor: 'gray',
        baseWidth: 12,
        baseLength: '0%',
        rearLength: '0%'
    },
    pivot: {
        backgroundColor: 'gray',
        radius: 0
    }

}]

});
//Run Time vs Downtime
Highcharts.chart('runTimeVsDowntime', {
            chart: {
                type: 'column',
                animation: true,
                height: 250,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                categories: ['Project1', 'Project2', 'Project3', 'Project4', 'Project5'],

            },
            yAxis: {
                title: {
                    text: ''
                },
                min: 0,
                max: 500
            },
            plotOptions: {
                column: {
                pointPadding: 0,
                borderWidth:0
                },
                series: {
                    dataLabels: {
                        enabled: true,
                    }

                }
            },
            legend: { enabled: true, },

            series: [{
                name: 'Run Time',
                data: [300, 300,300,300,300],
                color: '#2caffe'
            }, {
                name: 'Downtime',
                data: [40,40,40,40,40],
                color: '#d568fb'
            }]
        });
 //prod Cost Last 12 Months
 const ranges = [
        [null],[null],[null],[null],[null],[null],
        [50, 50],
        [37, 47],
        [25, 35],
        [30,45],
        [32,48],

    ],
    averages = [
        [null],
        [null],
        [null],
        [null],
        [null],
        [null],
        [50],
        [43],
        [30],
        [35],
        [37],
    ];
 Highcharts.chart('prodCostLastTwelveMonths', {
            chart: {
                // type: 'area',
                // zoomType: 'xy',
                animation: true,
                height: 250
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
        type: 'datetime',
        accessibility: {
            rangeDescription: 'Range: Jul 1st 2022 to Jul 31st 2022.'
        }
    },

    yAxis: {
        title: {
            text: 'Production Cost'
        }
    },

    tooltip: {
        crosshairs: true,
        shared: true,
        valueSuffix: '°C'
    },

    plotOptions: {
        series: {
            pointStart: Date.UTC(2022, 6, 1),
            pointIntervalUnit: 'day',
            dataLabels: {
                enabled: true
            }
        }
    },

    series: [{
        name: 'Production Cost',
        data: [10, 8, 20, 22, 30, 35,
            50,null,null, null, null,null],
        color: '#2596be',
    },{
        name: 'Forecast',
        data: averages,
        zIndex: 1,
        color: '#2596be',
        marker: {
            fillColor: 'white',
            lineWidth: 2,
            lineColor: '#2587be',
            symbol: 'circle'
        }
    }, {
        name: 'Upper and Lower bound',
        data: ranges,
        type: 'arearange',
        lineWidth: 0,
        linkedTo: ':previous',
        // color: Highcharts.getOptions().colors[0],
        fillOpacity: 0.3,
        zIndex: 0,
        marker: {
            enabled: false
        }
    }]
});
	
}