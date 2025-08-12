$(document).ready(()=>{
// Data generated from http://www.bikeforums.net/professional-cycling-fans/1113087-2017-tour-de-france-gpx-tcx-files.html
Highcharts.chart('bottomChart', {
    chart: {
        type: 'areaspline',
        height:70,
    },
    title: {
        text: '',
        align: 'left'
    },
    legend: {
        enabled: false // Hide legend
    },
    xAxis: {
        labels: {
            enabled: false // Hide x-axis labels
        },
        visible: false // Hide x-axis line and ticks
    },
    yAxis: {
        title: {
            text: '' // Empty text to hide y-axis title
        },
        labels: {
            enabled: false // Hide y-axis labels
        },
        visible: false // Hide y-axis line and ticks
    },
    tooltip: {
        shared: true,
        headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        series: {
            pointStart: 20,
            marker: {
                enabled: false // Hide data points
            }
        },
        areaspline: {
            fillOpacity: 0.5
        }
    },
    series: [{
        name: '',
        data: [
            380, 373, 378, 385, 367, 360, 349, 356, 356, 359, 364, 314, 404, 349, 331, 311, 308, 316, 306, 303, 317
        ]
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});

// Data generated from http://www.bikeforums.net/professional-cycling-fans/1113087-2017-tour-de-france-gpx-tcx-files.html
Highcharts.chart('bounceRate', {
    chart: {
        type: 'areaspline',
        height:70,
    },
    title: {
        text: '',
        align: 'left'
    },
    legend: {
        enabled: false // Hide legend
    },
    xAxis: {
        labels: {
            enabled: false // Hide x-axis labels
        },
        visible: false // Hide x-axis line and ticks
    },
    yAxis: {
        title: {
            text: '' // Empty text to hide y-axis title
        },
        labels: {
            enabled: false // Hide y-axis labels
        },
        visible: false // Hide y-axis line and ticks
    },
    tooltip: {
        shared: true,
        headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        series: {
            pointStart: 20,
            marker: {
                enabled: false // Hide data points
            }
        },
        areaspline: {
            fillOpacity: 0.5,
            color:'#e3b1fc'
        }
    },
    series: [{
        name: '',
        data: [
            380, 373, 378, 385, 367, 360, 349, 356, 356, 359, 364, 314, 404, 349, 331, 311, 308, 316, 306, 303, 317
        ]
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
Highcharts.chart('asd', {
    chart: {
        type: 'areaspline',
        height:70,
    },
    title: {
        text: '',
        align: 'left'
    },
    legend: {
        enabled: false // Hide legend
    },
    xAxis: {
        labels: {
            enabled: false // Hide x-axis labels
        },
        visible: false // Hide x-axis line and ticks
    },
    yAxis: {
        title: {
            text: '' // Empty text to hide y-axis title
        },
        labels: {
            enabled: false // Hide y-axis labels
        },
        visible: false // Hide y-axis line and ticks
    },
    tooltip: {
        shared: true,
        headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        series: {
            pointStart: 2000,
            marker: {
                enabled: false // Hide data points
            }
        },
        areaspline: {
            fillOpacity: 0.5,
            color: '#F79C92' // Change the chart color to purple
        }
    },
    series: [{
        name: '',
        data: [
            380, 373, 378, 385, 367, 360, 349, 356, 356, 359, 364, 314, 404, 349, 331, 311, 308, 316, 306, 303, 317
        ]
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});

Highcharts.chart('ctr', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: 230,
    },
    credits: {
        enabled: false
    },
    title: {
        text: ''
    },
    pane: {
        startAngle: -90,
        endAngle: 89.9,
        background: null,
        center: ['50%', '75%'],
        size: '100%'
    },
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
        },

    }],
    exporting: {
        enabled: false
    },

});

Highcharts.chart('ccr', {
    chart: {
        zooming: {
            type: 'xy'
        },
        height: 230,
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
        },
        title: {
            text: 'Temperature',
        }
    }, { // Secondary yAxis
        title: {
            text: 'Precipitation',
        },
        labels: {
            format: '{value} mm',
        },
        opposite: true
    }],
    tooltip: {
        shared: true
    },
    legend: {
        align: 'center',
        // x: 80,
        verticalAlign: 'bottom',
        y: 20,
        floating: true,
        backgroundColor:
            Highcharts.defaultOptions.legend.backgroundColor || // theme
            'rgba(255,255,255,0.25)'
    },
    series: [{
        name: 'Clicks',
        type: 'column',
        yAxis: 1,
        data: [
            27.6, 28.8, 21.7, 34.1, 29.0, 28.4, 45.6, 51.7, 39.0,
            60.0, 28.6, 32.1
        ],
        color: '#B422B6',
        tooltip: {
            valueSuffix: ' mm'
        }

    },
    {
        name: 'Conversions',
        type: 'column',
        yAxis: 1,
        data: [
            7.6, 8.8, 7.7, 4.1, 9.0, 8.4, 5.6, 1.7, 9.0,
            6.0, 8.6, 3.1
        ],
        color: '#bf05ff',
        tooltip: {
            valueSuffix: ' mm'
        }

    },
     {
        name: 'Conversions Rate',
        type: 'spline',
        data: [
            -13.6, -14.9, -5.8, -0.7, 3.1, 13.0, 14.5, 10.8, 5.8,
            -0.7, -11.0, -16.4
        ],
        color: '#F79C92',
        tooltip: {
            valueSuffix: '°C'
        }
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled :false
    }
});
Highcharts.chart('wvtp', {
    chart: {
        type: 'pie',
        height: 230,
        options2d: {
            enabled: true,
            alpha: 45
        }
    },
    title: {
        text: ''
    },
    plotOptions: {
        pie: {
            innerSize: '60%',
            depth: 45,
            dataLabels: {
                enabled: true,
                markerHeight: 5,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
            }
        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: [{
            name: 'Chrome',
            y: 61.41,
            sliced: true,
            selected: true,
            color:'#b422b6'
        }, {
            name: 'Internet Explorer',
            y: 11.84,
            color: '#bb76de'
        }, {
            name: 'Firefox',
            y: 10.85,
            color: '#bf05ff'
        }, {
            name: 'Edge',
            y: 4.67,
            color: '#f79c92'
        }, {
            name: 'Safari',
            y: 4.18,
            color: '#b422b6'
        }, {
            name: 'Other',
            y: 7.05,
            color: '#72288b '
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
// Retrieved from https://www.ssb.no/jord-skog-jakt-og-fiskeri/jakt
Highcharts.chart('es', {
    chart: {
        type: 'areaspline',
        height:50,
    },
    title: {
        text: '',
        align: 'left'
    },
    legend: {
        enabled: false // Hide legend
    },
    xAxis: {
        labels: {
            enabled: false // Hide x-axis labels
        },
        visible: false // Hide x-axis line and ticks
    },
    yAxis: {
        title: {
            text: '' // Empty text to hide y-axis title
        },
        labels: {
            enabled: false // Hide y-axis labels
        },
        visible: false // Hide y-axis line and ticks
    },
    tooltip: {
        shared: true,
        headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        series: {
            pointStart: 20,
            marker: {
                enabled: false // Hide data points
            }
        },
        areaspline: {
            fillOpacity: 0.5,
            color: '#56156C'
        }
    },
    series: [{
        name: '',
        data: [
            380, 373, 378, 385, 367, 360, 349, 356, 356, 359, 364, 314, 404, 349, 331, 311, 308, 316, 306, 303, 317
        ]
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
// Retrieved from https://www.ssb.no/jord-skog-jakt-og-fiskeri/jakt
Highcharts.chart('eo', {
    chart: {
        type: 'areaspline',
        height:50,
    },
    title: {
        text: '',
        align: 'left'
    },
    legend: {
        enabled: false // Hide legend
    },
    xAxis: {
        labels: {
            enabled: false // Hide x-axis labels
        },
        visible: false // Hide x-axis line and ticks
    },
    yAxis: {
        title: {
            text: '' // Empty text to hide y-axis title
        },
        labels: {
            enabled: false // Hide y-axis labels
        },
        visible: false // Hide y-axis line and ticks
    },
    tooltip: {
        shared: true,
        headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        series: {
            pointStart: 2000,
            marker: {
                enabled: false // Hide data points
            }
        },
        areaspline: {
            fillOpacity: 0.5,
            color:'#B422B6'
        }
    },
    series: [{
        name: '',
        data: [
            380, 373, 378, 385, 367, 360, 349, 356, 356, 359, 364, 314, 404, 349, 331, 311, 308, 316, 306, 303, 317
        ]
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
// Retrieved from https://www.ssb.no/jord-skog-jakt-og-fiskeri/jakt
Highcharts.chart('ns', {
    chart: {
        type: 'areaspline',
        height:50,
    },
    title: {
        text: '',
        align: 'left'
    },
    legend: {
        enabled: false // Hide legend
    },
    xAxis: {
        labels: {
            enabled: false // Hide x-axis labels
        },
        visible: false // Hide x-axis line and ticks
    },
    yAxis: {
        title: {
            text: '' // Empty text to hide y-axis title
        },
        labels: {
            enabled: false // Hide y-axis labels
        },
        visible: false // Hide y-axis line and ticks
    },
    tooltip: {
        shared: true,
        headerFormat: '<b>Hunting season starting autumn {point.x}</b><br>'
    },
    credits: {
        enabled: false
    },
    plotOptions: {
        series: {
            pointStart: 2000,
            marker: {
                enabled: false // Hide data points
            }
        },
        areaspline: {
            fillOpacity: 0.5,
            color: '#F79C92' // Change the chart color to purple
        }
    },
    series: [{
        name: '',
        data: [
            380, 373, 378, 385, 367, 360, 349, 356, 356, 359, 364, 314, 404, 349, 331, 311, 308, 316, 306, 303, 317
        ]
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});

Highcharts.chart('eor', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: 230,
    },
    credits: {
        enabled: false
    },
    title: {
        text: ''
    },
    pane: {
        startAngle: -90,
        endAngle: 89.9,
        background: null,
        center: ['50%', '75%'],
        size: '100%'
    },
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
        },

    }],
    exporting: {
        enabled: false
    },

});

Highcharts.chart('ur', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: 230,
    },
    credits: {
        enabled: false
    },
    title: {
        text: ''
    },
    pane: {
        startAngle: -90,
        endAngle: 89.9,
        background: null,
        center: ['50%', '75%'],
        size: '100%'
    },
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
        },

    }],
    exporting: {
        enabled: false
    },
 
});
Highcharts.chart('pvps', {
    chart: {
        zoomType: 'xy',
        height:280,
    },
    title: {
        text: '',
        align: 'left'
    },
    xAxis: [{
        categories: [
            'Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024',
            'Apr 2024', 'May 2024', 'Jun 2024', 'Jul 2024', 'Aug 2024', 'Sep 2024'
        ],
        crosshair: true
    }],
    yAxis: [{ // Primary yAxis
        labels: {
            formatter: function() {
                return '$' + this.value + 'K'; // Display custom y-axis labels in $amount K format
            },
            // style: {
            //     color: Highcharts.getOptions().colors[1]
            // }
        },
        title: {
            text: 'Sales',
        }
    }, { // Secondary yAxis
        title: {
            text: 'Page Visits',
        },
        labels: {
            formatter: function() {
                return '$' + this.value + 'K'; // Display custom y-axis labels in $amount K format
            },
        },
        opposite: true
    }],
    tooltip: {
        shared: true
    },
    legend: {
        align: 'center',
        x: 0,
        verticalAlign: 'bottom',
        y: 20,
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
        color:'#f79c92',
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
Highcharts.chart('rgs', {
    chart: {
        type: 'bar',
        height:300,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Social Media', 'Referals', 'Email Marketing', 'Organic Search', 'Paid Search' ,'Direct Trafic']
    },
    yAxis: {
        min: 0,
        title: {
            text: 'Goals'
        }
    },
    legend: {
        reversed: true
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: true
            }
        }
    },
    series: [{
        name: 'Quater 1',
        data: [4, 4, 6, 15, 12, 14, 16],
        color:'#7d1a9e'
    }, {
        name: 'Quater 2',
        data: [5, 3, 12, 6, 11, 9, 15],
        color:'#bb76de'
    }, {
        name: 'Quater 3',
        data: [5, 15, 8, 5, 8, 18, 11],
        color:'#F79C92'
    },{
        name: 'Quater 4',
        data: [15, 10, 18, 7, 12],
        color: '#B422B6'
    }],
    legend: {
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom',
        borderWidth: 0
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
})
