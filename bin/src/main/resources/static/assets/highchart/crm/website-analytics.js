$(document).ready(()=>{
Highcharts.chart('rrm', {
    chart: {
        type: 'column',
        height:230,
    },
    title: {
        text: '',
        align: 'left'
    },

    xAxis: {
        categories: ['USA', 'China', 'Brazil', 'EU', 'India', 'Russia'],
        crosshair: true,
        accessibility: {
            description: 'Countries'
        }
    },
    yAxis: {
        min: 0,
        title: {
            text: '1000 metric tons (MT)'
        }
    },
    tooltip: {
        valueSuffix: ' (1000 MT)'
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0
        }
    },
    series: [
        {
            name: '',
            data: [406292, 260000, 107000, 68300, 27500, 14500],
            color: '#F79C92',
        }
    ],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
  Highcharts.chart('br', {
        chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height:230,
        },
        title: {
          text: '',
          align: "center",
          verticalAlign: "bottom",
          floating: true,
          y: 30,
          margin: 0,
          style: {
            fontSize: "16",
            color: "#51a4d6"
          },
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
    pane: {
        startAngle: -90,
        endAngle: 89.9,
        background: null,
        center: ['50%', '75%'],
        size: '100%'
    },
        // the value axis
    yAxis: {
        min: 0,
        max: 100,
        tickPixelInterval: 50,
        tickPosition: 'inside',
        tickColor: 'grey',
        tickLength: 10,
        tickWidth: 2,
        minorTickInterval: null,
        labels: {
            distance: -20,
            style: {
                fontSize: '10px'
            }
        },
          lineWidth: 0,
          plotBands: [{
            from: 0,
            to: 50,
            color: '#B422B6',
            outerRadius: '125%',
            thickness: 25
        }, {
            from: 50,
            to: 80,
            color: '#bf05ff',
            outerRadius: '125%',
            thickness: 25
        }, {
            from: 80,
            to: 100,
            color: '#F79C92',
            outerRadius: '125%',
            thickness: 25
        }]
        },
    series: [{
        name: 'Fleet Utitlization Rate',
        data: [72.73],
        tooltip: {
            valueSuffix: ' %'
        },
        dataLabels: {
            format: '{y} %',
            borderWidth: 0,
            color: '#333333',
            style: {
                fontSize: '16px'
            }
        },
          dial: {
            radius: '80%',
            backgroundColor: 'gray',
            baseWidth: 12,
            baseLength: '0%',
            rearLength: '0%'
          },
          pivot: {
            backgroundColor: 'gray',
            radius: 6
          }
        }]
      });
 Highcharts.chart('ccrchart', {
        chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
        height:230,
        },
        title: {
          text: '',
          align: "center",
          verticalAlign: "bottom",
          floating: true,
          y: 30,
          margin: 0,
          style: {
            fontSize: "16",
            color: "#51a4d6"
          },
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
    pane: {
        startAngle: -90,
        endAngle: 89.9,
        background: null,
        center: ['50%', '75%'],
        size: '100%'
    },
        // the value axis
    yAxis: {
        min: 0,
        max: 100,
        tickPixelInterval: 50,
        tickPosition: 'inside',
        tickColor: 'grey',
        tickLength: 10,
        tickWidth: 2,
        minorTickInterval: null,
        labels: {
            distance: -20,
            style: {
                fontSize: '10px'
            }
        },
          lineWidth: 0,
          plotBands: [{
            from: 0,
            to: 50,
            color: '#B422B6',
            outerRadius: '125%',
            thickness: 25
        }, {
            from: 50,
            to: 80,
            color: '#bf05ff',
            outerRadius: '125%',
            thickness: 25
        }, {
            from: 80,
            to: 100,
            color: '#F79C92',
            outerRadius: '125%',
            thickness: 25
        }]
        },
    series: [{
        name: 'Fleet Utitlization Rate',
        data: [72.73],
        tooltip: {
            valueSuffix: ' %'
        },
        dataLabels: {
            format: '{y} %',
            borderWidth: 0,
            color: '#333333',
            style: {
                fontSize: '16px'
            }
        },
          dial: {
            radius: '80%',
            backgroundColor: 'gray',
            baseWidth: 12,
            baseLength: '0%',
            rearLength: '0%'
          },
          pivot: {
            backgroundColor: 'gray',
            radius: 6
          }
        }]
      });


Highcharts.chart('revenuets', {
    chart: {
        type: 'pie',
        options2d: {
            enabled: true,
            alpha: 45
        },
        height: 270
    },
    title: {
        text: ''
    },
    plotOptions: {
        pie: {
            innerSize: '50%',
            depth: 45,
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
            }
        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: [ {
            name: 'Social',
            y: 11.84,
            color: '#B422B6',
        }, {
            name: 'Organic Search',
            y: 10.85,
            color: '#f79c92',
        }, {
            name: 'Referal',
            y: 4.67,
            color: '#BF05FF',
        }, {
            name: 'Direct',
            y: 4.18,
            color: '#72288b',
        }, {
            name: 'Other',
            y: 7.05,
            color: '#bb76de',
        }]
    }],
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'middle',
        borderWidth: 0
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});

Highcharts.chart('arpu', {
    chart: {
        type: 'spline',
        inverted: true,
        height: 270
    },
    title: {
        text: '',
        align: 'left'
    },
    xAxis: {
        reversed: false,
        title: {
            enabled: true,
            text: 'Amount ($K)' // Update the x-axis title to indicate amounts in thousands
        },
        labels: {
            format: '${value}K' // Add the dollar sign and 'K' to indicate thousands
        },
        accessibility: {
            rangeDescription: 'Range: $0K to $80K.' // Update the accessibility range description
        },
        maxPadding: 0.05,
        showLastLabel: true
    },
    yAxis: {
        title: {
            text: 'Temperature'
        },
        labels: {
            format: '{value}°'
        },
        accessibility: {
            rangeDescription: 'Range: -90°C to 20°C.'
        },
        lineWidth: 2
    },
    legend: {
        enabled: false
    },
    tooltip: {
        headerFormat: '<b>{series.name}</b><br/>',
        pointFormat: '{point.x}K: {point.y}°C' // Update the tooltip to show amounts in thousands
    },
    plotOptions: {
        spline: {
            marker: {
                enabled: false // It should be 'enabled' instead of 'enable'
            },
            color: '#b422b6' // Set the color of the spline to red
        }
    },
    series: [{
        name: 'Temperature',
        data: [
            [300, 5], [280, 10], [250,15], [310, 25], [270, 30],
            [250, 35], [320,38], [400,43], [380, 52]
        ]
    }],
    exporting: {
        enabled: false
    },
    credits: {
        enabled: false
    }
});
Highcharts.chart('tlpr', {
    chart: {
        type: 'bar',
        height: 270,
    },
    title: {
        text: '',
        align: 'left'
    },
    xAxis: {
        categories: ['/', '/blog', '/bi/register', '/blog/10-great-bu.', '/blog/creat-us'],
        title: {
            text: null
        },
        gridLineWidth: 1,
        lineWidth: 0
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Population (millions)',
            align: 'high'
        },
        labels: {
            overflow: 'justify'
        },
        gridLineWidth: 0
    },
    tooltip: {
        valueSuffix: ' millions'
    },
    plotOptions: {
        bar: {
            dataLabels: {
                enabled: true
            },
            pointWidth: 15, // Adjust the width of the bars here
            groupPadding: 0.1
        }
    },
    legend: {
        layout: 'vertical',
        align: 'right',
        verticalAlign: 'top',
        x: -40,
        y: 80,
        floating: true,
        borderWidth: 1,
        backgroundColor: Highcharts.defaultOptions.legend.backgroundColor || '#FFFFFF',
        shadow: true
    },
    credits: {
        enabled: false
    },
    series: [{
        name: 'Year',
        data: [
            { y: 250300, color: '#b422b6' },
            { y: 66000, color: '#bf05ff' },
            { y: 58640, color: '#bb76de' },
            { y: 36110, color: '#f79c92' },
            { y: 29710, color: '#b422b6' }
        ]
    }],
    exporting: {
        enabled: false
    },
    credits: {
        enabled: false
    }
});

Highcharts.chart('traov', {
    chart: {
        zooming: {
            type: 'xy'
        },
        height: 270,
    },
    title: {
        text: '',
        align: 'left'
    },

    xAxis: [{
        categories: [
            'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
            'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
        ],
        crosshair: true
    }],
    yAxis: [{ // Primary yAxis
        labels: {
            format: '{value}°C',
            // style: {
            //     color: Highcharts.getOptions().colors[1]
            // }
        },
        title: {
            text: 'Temperature',
            // style: {
            //     color: Highcharts.getOptions().colors[1]
            // }
        }
    }, { // Secondary yAxis
        title: {
            text: 'Precipitation',
            // style: {
            //     color: Highcharts.getOptions().colors[0]
            // }
        },
        labels: {
            format: '{value} mm',
            // style: {
            //     color: Highcharts.getOptions().colors[0]
            // }
        },
        opposite: true
    }],
    tooltip: {
        shared: true
    },
    legend: {
        align: 'left',
        x: 80,
        verticalAlign: 'top',
        y: 60,
        floating: true,
        backgroundColor:
            Highcharts.defaultOptions.legend.backgroundColor || // theme
            'rgba(255,255,255,0.25)'
    },
    series: [{
        name: 'Precipitation',
        type: 'column',
        yAxis: 1,
        data: [
            27.6, 28.8, 21.7, 34.1, 29.0, 28.4, 45.6, 51.7, 39.0,
            60.0, 28.6, 32.1
        ],
        color:'#b422b6',
        tooltip: {
            valueSuffix: ' mm'
        }

    }, {
        name: 'Temperature',
        type: 'spline',
        data: [
            -13.6, -14.9, -5.8, -0.7, 3.1, 13.0, 14.5, 10.8, 5.8,
            -0.7, -11.0, -16.4
        ],
        color:'#72288b',
        tooltip: {
            valueSuffix: '°C'
        }
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
})