$(document).ready(()=>{
//Bounce Rate
 Highcharts.chart('GooglebounceRate', {
        chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
        height:270,
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
        data: [2.25],
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

//pvupv
Highcharts.chart('pvupv', {
    chart: {
        type: 'bar',
        height:270,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Testsite Home', 'Testsite', 'Logout', 'Mainpage Home', 'Home', 'Login', 'Users', 'Newuser', 'Newaccount']
    },
    yAxis: {
        min: 0,
        title: {
            text: ''
        }
    },
    legend: {
        reversed: true
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: false
            }
        }
    },
    series: [{
        name: 'Page View',
        data: [4, 15, 12, 14, 16, 18, 12],
        color: '#F79C92'
    }, {
        name: 'Unique Page Views',
        data: [5, 3, 12, 6, 11, 14, 8],
        color: '#B422B6'
    }],
    legend: {
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'top',
        borderWidth: 0
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
//avgpltv

Highcharts.chart('avgpltv', {
    chart: {
        type: 'spline',
        height:250,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['4/16/2024', '4/17/2024', '4/18/2024', '4/19/2024', '4/20/2024', '4/21/2024']
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        line: {
            dataLabels: {
                enabled: false
            },
            enableMouseTracking: false
        }
    },
    series: [{
        name: 'Avg. Page Load Time',
        data: [34.38, 32.99, 35.91, 33.86, 37.94],
        color: '#BF05FF',
        dataLabels: {
            enabled: true
        },
    }, {
        name: 'Avg. Time On Page',
        data: [11.68, 10.91, 11.36, 12.74, 11.88],
        color: '#F79C92',
        dataLabels: {
            enabled: true
        },
    },
    ],
    credits: { enabled: false },
    exporting: { enabled: false },
    legend: {
        align: 'left',
        verticalAlign: 'top',
        x: 10,
        useHTML: true,
        // labelFormatter: function() {
        //     return '<span style="color: #000000; font-size: 9px;">' + this.name + '</span>';
        // }
    },
});
//avgppersbydate
Highcharts.chart('avgppersbydate', {
    chart: {
        type: 'spline',
        height:270,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['4/16/2024', '4/17/2024', '4/18/2024', '4/19/2024', '4/20/2024', '4/21/2024']
    },
    yAxis: {
        title: {
            text: ''
        }
    },
    plotOptions: {
        line: {
            dataLabels: {
                enabled: true
            },
            enableMouseTracking: false
        }
    },
    series: [{
        name: '',
        data: [2.50, 2.50, 2.25,2.75,1],
        color: '#BF05FF',
        dataLabels: {
            enabled: true
        }
    }],
    credits: { enabled: false },
    exporting: { enabled: false },
    legend: {
        enabled: false
    },
});
//rtvsdltbyp
Highcharts.chart('rtvsdltbyp', {
    chart: {
        type: 'bar',
        height:250,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Testsite', 'Home', 'Newuser','Testsite Home', 'Users', 'Mainpage Home', 'Newaccount', 'Login', 'Logout']
    },
    yAxis: {
        min: 0,
        title: {
            text: ''
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
        name: 'Page Redirection Time',
        data: [4, 15, 12, 14, 16, 18, 12],
        color: '#F79C92'
    }, {
        name: 'Domain Lookup Time',
        data: [5, 3, 12, 6, 11, 14, 8],
        color: '#B422B6'
    }],
    legend: {
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'top',
        borderWidth: 0
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
});
//Clicks VS. Click-Through Rate(CTR)
Highcharts.chart('cctr', {
    chart: {
        type: 'spline',
        height:250,
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
})

//Conversions VS. Conversion Rate
Highcharts.chart('cvcr', {
    chart: {
        type: 'spline',
         height:270,
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
})

//Cost VS Avg. Cost Per Click(CPC)
Highcharts.chart('cacc', {
    chart: {
        type: 'spline',
        height:250,
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
})

