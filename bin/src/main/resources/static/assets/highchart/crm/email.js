$(document).ready(()=>{
//-----------------------------Email Tab Start----------------------
// Delivery Rates Starts Here
Highcharts.chart('dr', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: '66%'
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
        data: [79.01],
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
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            // Make the labels less space demanding on mobile
            chartOptions: {
                pane: {
                    startAngle: -90,
                    endAngle: 89.9,
                    background: null,
                    center: ['50%', '85%'],
                    size: '100%'
                },
                yAxis: {
                    plotBands: [{
                        from: 0,
                        to: 50,
                        color: '#B422B6',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 50,
                        to: 80,
                        color: '#bf05ff',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 80,
                        to: 100,
                        color: '#F79C92',
                        outerRadius: '135%',
                        thickness: 25
                    }]
                }
            }
        }]
    }
});

// Cliecked Open Rates Starts Here
Highcharts.chart('cor', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: '66%'
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
        data: [56.13],
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
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            // Make the labels less space demanding on mobile
            chartOptions: {
                pane: {
                    startAngle: -90,
                    endAngle: 89.9,
                    background: null,
                    center: ['50%', '85%'],
                    size: '100%'
                },
                yAxis: {
                    plotBands: [{
                        from: 0,
                        to: 50,
                        color: '#B422B6',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 50,
                        to: 80,
                        color: '#bf05ff',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 80,
                        to: 100,
                        color: '#F79C92',
                        outerRadius: '135%',
                        thickness: 25
                    }]
                }
            }
        }]
    }
});
// Click-Through Rate Starts Here
Highcharts.chart('cr', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: '66%'
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
        },
            {
                from: 50,
                to: 80,
                color: '#bf05ff',
                outerRadius: '125%',
                thickness: 25
            },
            {
                from: 80,
                to: 100,
                color: '#F79C92',
                outerRadius: '125%',
                thickness: 25
            }]
    },
    series: [{
        name: 'Fleet Utitlization Rate',
        data: [13.53],
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
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            // Make the labels less space demanding on mobile
            chartOptions: {
                pane: {
                    startAngle: -90,
                    endAngle: 89.9,
                    background: null,
                    center: ['50%', '85%'],
                    size: '100%'
                },
                yAxis: {
                    plotBands: [{
                        from: 0,
                        to: 50,
                        color: '#B422B6',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 50,
                        to: 80,
                        color: '#bf05ff',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 80,
                        to: 100,
                        color: '#F79C92',
                        outerRadius: '135%',
                        thickness: 25
                    }]
                }
            }
        }]
    }
});
// Click-Through Rate Starts Here
Highcharts.chart('sri', {
    chart: {
        type: 'gauge',
        plotBackgroundColor: null,
        plotBackgroundImage: null,
        plotBorderWidth: 0,
        plotShadow: false,
        height: '66%'
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
        },
            {
                from: 50,
                to: 80,
                color: '#bf05ff',
                outerRadius: '125%',
                thickness: 25
            },
            {
                from: 80,
                to: 100,
                color: '#F79C92',
                outerRadius: '125%',
                thickness: 25
            }]
    },
    series: [{
        name: 'Fleet Utitlization Rate',
        data: [1.44],
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
    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            // Make the labels less space demanding on mobile
            chartOptions: {
                pane: {
                    startAngle: -90,
                    endAngle: 89.9,
                    background: null,
                    center: ['50%', '85%'],
                    size: '100%'
                },
                yAxis: {
                    plotBands: [{
                        from: 0,
                        to: 50,
                        color: '#B422B6',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 50,
                        to: 80,
                        color: '#bf05ff',
                        outerRadius: '135%',
                        thickness: 25
                    }, {
                        from: 80,
                        to: 100,
                        color: '#F79C92',
                        outerRadius: '135%',
                        thickness: 25
                    }]
                }
            }
        }]
    }
});


// Click Rates Starts Here
Highcharts.chart('wvtp11', {
    chart: {
        type: 'pie',
        height:250,
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
            innerSize: '50%',
            depth: 45,
            showInLegend: true,
            dataLabels: {
                enabled: true,
                format: '<b>{point.name}</b>: {point.percentage:.1f} %'
            }

        }
    },
    series: [{
        name: 'Brands',
        colorByPoint: true,
        data: [{
            name: 'Chrome',
            y: 17.54,
            color: '#72288b'
        }, {
            name: 'iPhone',
            y: 17,
            color: '#664092'
        }, {
            name: 'Other',
            y: 15.3,
            color: '#f79c92'
        }, {
            name: 'Firefox',
            y: 12.3,
            color: '#aa89b5'
        }, {
            name: 'IE',
            y: 12.48,
            color: '#bb76de'
        }, {
            name: 'Nokia',
            y: 12.48,
            color: '#bf05ff'
        }, {
            name: 'iPad',
            y: 12.02,
            color: '#b422b6'
        }]
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

//Bounce & Unsubscribe Rates Starts Here
Highcharts.chart('bur', {
    chart: {
        type: 'spline',
        height:230,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Oct2023', 'Nov2023', 'Dec2023', 'Jan2024', 'Feb2024']
    },
    yAxis: {
        tooltip: {
            valueSuffix: '%'
        },
    },
    plotOptions: {
        series: {
            dataLabels: {
                enabled: true,
                format: '<b>{point.y:.1f}%</b>'
            },
            cursor: 'pointer',
            enableMouseTracking: true
        }
    },
    series: [{
        name: 'Bounce Rate',
        data: [0.82, 0.64, 0.66, 0.76, 0.67],
        color:'#72288b'
    },
        {
            name: 'Unsubscribe Rate',
            data: [0.60, 0.44, 0.44, 0.59, 0.56],
            color:'#f79c92'
        }],
    credits: { enabled: false },
    exporting: { enabled: false },
});
//Email Engagement By Device Starts Here
Highcharts.chart('eed', {
    chart: {
        type: 'column',
        height:340,
    },
    title: {
        text: '',
    },
    xAxis: {
        categories: ['Blackberry', 'Deskto', 'Other', 'Phone', 'Tablet', 'Webmail'],
        crosshair: true,
        accessibility: {
            description: 'Devices'
        }
    },
    yAxis: {
        min: 0,
        title: {
            text: ''
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
            name: 'Opens',
            data: [6200.0, 4400.0, 8000.0, 7700.0, 6500.0, 5800.0],
            color: '#F79C92'
        },
        {
            name: 'Unique Opens',
            data: [3500.0, 2000.0, 4000.0, 4200.0, 3500.0, 3500.0],
            color: '#bf05ff'
        }],
    credits: { enabled: false },
    exporting: { enabled: false },
});
//Opens By Country Starts Here
(async () => {

    const topology = await fetch(
        'https://code.highcharts.com/mapdata/custom/world.topo.json'
    ).then(response => response.json());

    Highcharts.mapChart('obc', {
        chart: {
            map: topology,
            height:340,
        },
        title: {
            text: '',
            align: 'left'
        },
        credits: {
            href: 'https://data.worldbank.org',
            mapText: ' Data source: The World Bank'
        },
        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },
        /*colorAxis: {
            min: 10
        }*/
        colorAxis: {
            min: 10,
            max: 25,
            tickInterval: 5,
            stops: [[0, '#F1EEF6'], [0.65, '#f79c92'], [1, '#f8c0ba']],
            labels: {
                format: '{value}%'
            }
        },
        data: {
            csv: document.getElementById('csv').innerText,
            seriesMapping: [{
                code: 1,
                value: 2
            }],
            color: '#FF0000' // Set a specific color for all data points on the map
        },
        tooltip: {
            valueDecimals: 1,
            valueSuffix: ' years'
        },
        series: [{
            name: 'Life expectancy',
            joinBy: ['iso-a3', 'code'],
            dataLabels: {
                enabled: true,
                format: '{point.value:.0f}',
                filter: {
                    operator: '>',
                    property: 'labelrank',
                    value: 250
                },
                style: {
                    fontWeight: 'normal'
                }
            }
        }],
        exporting: {
            enabled: false
        }
    });

})();

Highcharts.chart('oor', {
    chart: {
        zoomType: 'xy',
        height:270,
    },
    title: {
        text: '',
        align: 'left'
    },
    xAxis: [{
        categories: ['Oct2023', 'Nov2023', 'Dec2023', 'Jan2024', 'Feb2024'],
        crosshair: true
    }],
    yAxis: [{ // Primary yAxis
        labels: {
            format: '{value}k',
        },
        title: {
            text: 'Opens',
        }
    }, { // Secondary yAxis
        title: {
            text: 'Open Rate',
        },
        labels: {
            format: '{value}%',
        },
        opposite: true
    }],
    tooltip: {
        shared: true
    },
    plotOptions: {
        series: {

        }
    },
    legend: {
        align: 'center',
        x: 0,
        verticalAlign: 'bottom',
        y: 20,
        floating: true,
        backgroundColor: Highcharts.defaultOptions.legend.backgroundColor || 'rgba(255,255,255,0.25)'
    },
    series: [{
        name: 'Opens',
        type: 'column',
        yAxis: 1,
        data: [2090, 2063, 2097, 1097, 400],
        color: '#B422B6',
        tooltip: {
            valueSuffix: '{value}K'
        },
        dataLabels: {
            enabled: true,
            formatter: function() {
                if (this.y > 1000) {
                    return Highcharts.numberFormat(this.y / 1000, 3) + 'K';
                }
                return Highcharts.numberFormat(this.y, 0);
            }
        }
    },
        {
            name: 'Open Rate',
            type: 'spline',
            data: [58.37, 53.35, 60.10, 47.46, 45.61],
            color: '#F79C92',
            tooltip: {
                valueSuffix: '{value}%'
            },
            dataLabels: {
                enabled: true,
                format: '{point.y:.1f}%'
            },
        }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    }
});

//Clicks By Month Starts Here
Highcharts.chart('cbm', {
    chart: {
        type: 'spline',
        height:270,
    },
    title: {
        text: 'Monthly Data'
    },
    xAxis: {
        categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024']
    },
    yAxis: {
        title: {
            text: 'Data Value'
        }
    },
    tooltip: {
        formatter: function() {
            if (this.y > 1000) {
                return Highcharts.numberFormat(this.y / 1000, 2) + 'K';
            }
            return Highcharts.numberFormat(this.y, 0);
        }
    },
    plotOptions: {
        series: {
            dataLabels: {
                enabled: true,
                formatter: function() {
                    if (this.y > 1000) {
                        return Highcharts.numberFormat(this.y / 1000, 2) + 'K';
                    }
                    return Highcharts.numberFormat(this.y, 0);
                }
            }
        }
    },
    series: [{
        name: 'Series Name',
        data: [843, 1140, 1690, 1060, 261],
        color: '#F79C92',
    }],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    }
});
//Investment And Revenue By Campaign Type
Highcharts.chart('irct', {
    chart: {
        type: 'column',
        height:270,
    },
    title: {
        text: '',
    },
    xAxis: {
        categories: ['Transactional', 'Automation', 'Drip Campaign', 'Advertisement'],
        crosshair: true,
        accessibility: {
            description: 'Devices'
        }
    },
    yAxis: {
        labels: {
            formatter: function () {
                return '$' + this.value + 'K';
            },
        },
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + '</b>: $' + Highcharts.numberFormat(this.y, 2, '.', ',') + 'K';
        }
    },
    plotOptions: {
        column: {
            pointPadding: 0.2,
            borderWidth: 0,
            dataLabels: {
                enabled: true,
                format: '${point.y}K'
            }
        }
    },
    series: [
        {
            name: 'Investment',
            data: [4.05, 3.98, 2.91, 2.77],
            color: '#664092'
        },
        {
            name: 'Revenue',
            data: [9.38, 6.61, 4.61, 5.98],
            color: '#f79c92'
        }
    ],
    credits: { enabled: false },
    exporting: { enabled: false },
});

Highcharts.chart('orcr', {
    chart: {
        type: 'spline',
        height:260,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['31/3/2024', '1/4/2024', '2/4/2024', '3/4/2024', '4/4/2024', '5/4/2024',
            '6/4/2024', '7/4/2024', '10/4/2024', '11/4/2024', '12/4/2024', '13/4/2024', '14/4/2024',
            '15/4/2024', '16/4/2024', '17/4/2024', '18/4/2024', '19/4/2024', '20/4/2024']
    },
    yAxis: {
        tooltip: {
            valueSuffix: '%'
        },
    },
    plotOptions: {
        series: {
            dataLabels: {
                enabled: true,
                format: '<b>{point.y:.1f}%</b>'
            },
            cursor: 'pointer',
            enableMouseTracking: true
        }
    },
    series: [{
        name: 'Open Rate',
        data: [50.84, 61.44, 51.07, 55.20, 44.33, 56.05, 45.17, 63.52, 36.73, 36.73,
            53.00, 51.07, 31.75, 32.75, 32.75, 57.17, 98.02, 58.72, 48.35],
        color:'#72288b'
    },
        {
            name: 'Click Rate',
            data: [60.84, 40.44, 65.35, 50.84, 44.33, 65.05, 45.17, 63.52, 53.71, 46.45,
                34.50, 53.00, 57.59, 50.00, 37.81, 57.17, 85.03, 58.72, 32.10],
            color:'#f79c92'
        }],
    credits: { enabled: false },
    exporting: { enabled: false },
});
//Campaign Conversion Funnel
Highcharts.chart('ccf', {
    chart: {
        type: 'funnel',
        height:270,
    },
    title: {
        text: null
    },
    accessibility: {
        screenReaderSection: {
            beforeChartFormat: '<{headingTagName}>' +
                '{chartTitle}</{headingTagName}><div>{typeDescription}</div>' +
                '<div>{chartSubtitle}</div><div>{chartLongdesc}</div>'
        }
    },
    plotOptions: {
        series: {
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.percentage:.1f}%</b>',
                allowOverlap: true,
                y: 10
            },
            width: '80%',
            height: '80%',
            showInLegend: true,
            colors: ['#7d1a9e', '#b422b6', '#f79c92', '#bf05ff']
        }
    },
    series: [{
        name: '',  //TODO: Need a proper name
        data: [
            ['Total Email Sent', 65.04],
            ['Total Unique Opens', 16.94],
            ['Total Unique Clicks', 18.02]
        ]
    }],
    credits: { enabled: false },
    exporting: { enabled: false },
});


})