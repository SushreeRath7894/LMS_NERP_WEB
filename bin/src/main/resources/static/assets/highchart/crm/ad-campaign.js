$(document).ready(()=>{
//<--Oppertunities By Source Starts Here-->
 Highcharts.chart('obs', {
    chart: {
        plotBackgroundColor: null,
        plotBorderWidth: null,
        plotShadow: false,
        type: 'pie',
        height:270,
    },
    title: {
        text: ''
    },
    tooltip: {
        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
    },
    accessibility: {
        point: {
            valueSuffix: '%'
        }
    },
    plotOptions: {
        pie: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.percentage:.1f}%</b>',
                distance: 20
            },
            showInLegend: true
        }
    },
    series: [{
        name: 'Social Media Platform',
        colorByPoint: true,
        data: [{
            name: 'Facebook/Socmedpost',
            y: 31.25,
            color:'#bf05ff'

        }, {
            name: 'Google/Referral',
            y: 40.15,
            color:'#b422b6'
        }, {
            name: 'Youtube/Video',
            y: 25,
            color:'#72288b'
        }, {
            name: 'Twitter/Socmedpost',
            y: 12.5,
            color:'#f79c92'
        }]
    }],
    credits: { enabled: false },
    exporting: { enabled: false },
});

//<--Oppertunities By Landing Starts Here-->
Highcharts.chart('obl', {
    chart: {
        type: 'bar',
        height:270,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['/summer offer', '/home/category/food', '/home/sample food', '/home/free-delivery', '/home/customer offer/food'],
        title: {
            text: null
        },
        gridLineWidth: 0,
        lineWidth: 1
    },
    yAxis: {
        min: 0,
        title: {
            text: '',
            align: 'high'
        },
        gridLineWidth: 2
    },
    tooltip: {
        valueSuffix: ''
    },
    plotOptions: {
        bar: {
            cursor: 'pointer',
            dataLabels: {
                enabled: false
            },
            groupPadding: 0.1
        }
    },
    legend: {
        enabled: false
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    series: [{
        name: '',
        data: [5.0, 5.0, 2.0, 2.0, 2.0],
        color: '#F79C92'
    }]
});

//<--Conversion Funnel-->
Highcharts.chart('cf', {
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
        name: 'Won Chart',  //TODO: Need a proper name
        data: [
            ['User', 72.26],
            ['Leads', 11.68],
            ['Oppertunities', 11.68],
            ['Won Oppertunities', 4.38]
        ]
    }],
    credits: { enabled: false },
    exporting: { enabled: false },
});

//Top 5 Campaigns By Conversion Rate
Highcharts.chart('tcr', {
    chart: {
        type: 'bar',
        height:270,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Campaign_5', 'Campaign_4', 'Campaign_3', 'Campaign_6', 'Campaign_9'],
        title: {
            text: null
        },
        gridLineWidth: 0,
        lineWidth: 1
    },
    yAxis: {
        min: 0,
        title: {
            text: '',
            align: 'high'
        },
        gridLineWidth: 2
    },
    tooltip: {
        valueSuffix: '%'
    },
    plotOptions: {
        bar: {
            cursor: 'pointer',
            dataLabels: {
                enabled: true,
                format: '<b>{point.percentage:.1f}%</b>'
            },
            groupPadding: 0.1,
            color: '#72288b'  // Set a single color for all bars
        }
    },
    legend: {
        enabled: false
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    series: [{
        name: '',
        data: [55.0, 44.0, 30.32, 10.55, 10.10]
    }]
});
Highcharts.chart('oablp', {
    chart: {
        type: 'pie',
        options2d: {
            enabled: true,
            alpha: 45
        },
        height:270,
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
            color:'#72288b'
        }, {
            name: 'Organic Search',
            y: 10.85,
            color:'#b422b6'
        }, {
            name: 'Referal',
            y: 4.67,
            color:'#f79c92'
        }, {
            name: 'Direct',
            y: 4.18,
            color:'#bb76de'
        }, {
            name: 'Other',
            y: 7.05,
            color:'#bf05ff'
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

Highcharts.chart('oabs', {
    chart: {
        type: 'pie',
        height:270,
    },
    title: {
        text: ''
    },
    tooltip: {
        valueSuffix: '%'
    },
   
    plotOptions: {
        series: {
            allowPointSelect: true,
            cursor: 'pointer',
            dataLabels: [{
                enabled: true,
                distance: 20
            }, {
                enabled: true,
                distance: -40,
                format: '{point.percentage:.1f}%',
                style: {
                    fontSize: '12px',
                    textOutline: 'none',
                    opacity: 0.7
                },
                filter: {
                    operator: '>',
                    property: 'percentage',
                    value: 10
                }
            }]
        }
    },
    series: [
        {
            name: 'Percentage',
            colorByPoint: true,
            data: [
                {
                    name: 'Water',
                    y: 55.02,
                    color:'#72288b'
                },
                {
                    name: 'Fat',
                    sliced: false,
                    selected: false,
                    y: 26.71,
                    color: '#b422b6'
                },
                {
                    name: 'Carbohydrates',
                    y: 1.09,
                    color: '#f79c92'
                },
                {
                    name: 'Protein',
                    y: 15.5,
                    color: '#bf05ff'
                },
                {
                    name: 'Ash',
                    y: 1.68,
                    color: '#bb76de'
                }
            ]
        }
    ],
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});

Highcharts.chart('topcoa', {
    chart: {
        type: 'bar',
        height:260,
    },
    title: {
        text: '',
        align: 'left'
    },
    xAxis: {
        categories: ['Campaign', 'Campaign_2', 'Campaign_3', 'Campaign_4', 'Campaign_5'],
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
            groupPadding: 0.1,
            color: '#bf05ff' // Custom colors for each bar
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
        data: [250300, 66000, 58640, 36110, 29710]
    }],
    exporting: {
        enabled: false
    },
    credits: {
        enabled: false
    }
});
})