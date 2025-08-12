$(document).ready(()=>{
//gvslsub
Highcharts.chart('gvslsub', {
    chart: {
        type: 'line',
        height: 280,
    },
    title: {
        text: ''
    },

    xAxis: {
        categories: ["Week 10 2024", "Week 11 2024", "Week 12 2024", "Week 13 2024", "Week 14 2024","Week 15 2024","Week 16 2024"],

        accessibility: {
            description: 'Months of the year'
        }
    },
    yAxis: {
        title: {
            text: ''
        },
        labels: {
            format: '{value}'
        }
    },
    tooltip: {
        crosshairs: true,
        shared: true,
    },
    plotOptions: {
        spline: {

            marker: {
                radius: 4,
                lineColor: '#666666',
                lineWidth: 1
            }
        }
    },

    navigation: {
        buttonOptions: {
            enabled: false
        }
    },

    credits: {
        enabled: false
    },

    series: [{
        name: 'Gained',
        marker: {
            symbol: 'circle'
        },
        data: [6, 8, 4, 9,5,3,9],
        color: '#BF05FF',
        dataLabels: {
            enabled: true,
            // formatter: function () {
            //     return this.y / 1000 + 'k';
            // }
        },

    }, {
        name: 'Lost',
        marker: {
            symbol: 'circle'
        },
        data: [46, 41, 36,59,49,41,4],
        color: '#F79C92',
        dataLabels: {
            enabled: true,
            // formatter: function () {
            //     return this.y / 1000 + 'k';
            // }
        },
    }]
});
Highcharts.chart('likes', {
    chart: {
        type: 'areaspline',
        height:80,
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
            color: '#B422B6'
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
//
Highcharts.chart('Shares', {
    chart: {
        type: 'areaspline',
        height:80,
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
//
Highcharts.chart('Dislikes', {
    chart: {
        type: 'areaspline',
        height:80,
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
//
Highcharts.chart('comment', {
    chart: {
        type: 'areaspline',
        height:80,
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
//avgviewduration
Highcharts.chart('avgviewduration', {
    chart: {
        animation: true,
        height: 206
    },
    title: {
        text: '',
    },
    colorAxis: {
        stops: [
            [0, '#bf05ff'],
            [0.5, '#b422b6'],
            [1, '#72288b']
        ]
    },
    navigation: {
        buttonOptions: {
            enabled: false
        }
    },

    xAxis: {
        categories: ['3/24/2024', '3/25/2024', '3/26/2024','3/27/2024','3/28/2024','3/29/2024','3/30/2024','3/31/2024','4/1/2024','4/2/2024','4/3/2024','4/4/2024','4/5/2024','4/6/2024','4/7/2024','4/8/2024'],
        tickWidth: 0
    },

    yAxis: {
        title: '',
        labels: { enabled: true },
    },
    legend: true,
    credits: false,
    series: [{
        type: 'area',
        name: '',
        data: [4.75,3.74,3.68,3.70,3.81,3.75,3.65,3.68,3.67,3.68,3.67,3.75],
        // dashStyle: 'dash',
        dataLabels:{
            enabled:true
        },
        color: '#b422b6',
        marker: {
            fillColor: '#ca7ce5',
            lineWidth: 2,
            lineColor: '#ca7ce5'
        }
    }]
});
//watchTime
Highcharts.chart('watchtime', {
    chart: {
        animation: true,
        height: 215,
    },
    title: {
        text: '',
    },

    navigation: {
        buttonOptions: {
            enabled: false
        }
    },

    xAxis: {
        categories: ['3/24/2024', '3/25/2024', '3/26/2024','3/27/2024','3/28/2024','3/29/2024','3/30/2024','3/31/2024','4/1/2024','4/2/2024','4/3/2024','4/4/2024'],
        tickWidth: 0
    },
    yAxis: {
        title: '',
        labels: { enabled: true },
    },
    legend: true,
    credits: false,
    series: [{
        type: 'area',
        name: '',
        data: [57,70,81,50,86,67,78,68,67,38,45,75],
        // dashStyle: 'dash',
        dataLabels:{
            enabled:true
        },
        color: '#b422b6',
        marker: {
            fillColor: '#ca7ce5',
            lineWidth: 2,
            lineColor: '#ca7ce5'
        }
    }]
});

//viewDistributionTrafficSources

Highcharts.chart('viewDistributionTrafficSources', {
    chart: {
        type: 'pie',
        options2d: {
            enabled: true,
            alpha: 45
        },
        height:'45%',
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
                format: '{point.percentage:.1f} %'
            }
        }
    },
    series: [{
        name: '',
        colorByPoint: true,
        data: [{
            name: '',
            y: 25.52,
            // sliced: true,
            selected: true,
            color: '#bf05ff'
        }, {
            name: '',
            y: 23.15,
            color: '#F79C92'
        }, {
            name: '',
            y: 18.83,
            color: '#56156C'
        }, {
            name: '',
            y: 17.45,
            color:'#B422B6'
        }, {
            name: '',
            y: 15.05,
            color: '#CA7CE5'
        },]
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


(async () => {

    const topology = await fetch(
        'https://code.highcharts.com/mapdata/custom/world.topo.json'
    ).then(response => response.json());

    Highcharts.mapChart('vdc', {
        chart: {
            map: topology,
            height:'45%',
        },
        title: {
            text: '',
            align: 'left'
        },
        mapNavigation: {
            enabled: true,
            buttonOptions: {
                verticalAlign: 'bottom'
            }
        },
         colorAxis: {
            min: 0,
            max: 1.75,
            tickInterval: null,
            stops: [[0, '#F1EEF6'], [0.05, '#f79c92'], [1, '#f8c0ba']],
            labels: {
                format: '{value}K'
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

Highcharts.Templating.helpers.substr = (s, from, length) =>
    s.substr(from, length);

// Create the chart
Highcharts.chart('AvgViewbyDayHour', {

    chart: {
        type: 'heatmap',
        marginTop: 40,
        marginBottom: 80,
        plotBorderWidth: 1,
        height:320,
    },


    title: {
        text: '',
        style: {
            fontSize: '1em'
        }
    },

    xAxis: {
        categories: [
            '1', '2', '3', '4', '5', '6', '7', '8', '9', '10','11', '12', '13', '14', '15', '16', '17', '18', '19', '20','21','22'
        ]
    },

    yAxis: {
        categories: ['Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sunday',],
        title: null,
        reversed: false
    },

    accessibility: {
        point: {
            descriptionFormat: '{(add index 1)}. ' +
                '{series.xAxis.categories.(x)} sales ' +
                '{series.yAxis.categories.(y)}, {value}.'
        }
    },

    colorAxis: {
        min: 0,
        minColor: '#FFFFFF',
        maxColor: Highcharts.getOptions().colors[0]
    },

    legend: {
        align: 'right',
        layout: 'vertical',
        margin: 0,
        verticalAlign: 'top',
        y: 25,
        symbolHeight: 280
    },
    credits: {
        enabled: false
    },
    exporting: {
        enabled: false
    },
    tooltip: {
        format: '<b>{series.xAxis.categories.(point.x)}</b> sold<br>' +
            '<b>{point.value}</b> items on <br>' +
            '<b>{series.yAxis.categories.(point.y)}</b>'
    },
    colorAxis: {
        min: 0,
        max: 25,
        tickInterval: 5,
        stops: [[0, '#f4d7fe'], [5, '#ebb5fe'], [10, '#e18ffd'], [15, '#b040d7'], [20, '#bf05ff'],[25, '#72288b']],
        labels: {
            format: '{value}%'
        }
    },
    series: [{
        name: 'Sales per employee',
        borderWidth: 1,
        data: [
                    [0, 0, 10], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67], [0, 5, 24], [0, 6, 77],
                    [1, 0, 92], [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48], [1, 5, 84], [1, 6, 179],
                    [2, 0, 35], [2, 1, 15], [2, 2, 123], [2, 3, 64], [2, 4, 52], [2, 5, 160], [2, 6, 258],
                    [3, 0, 72], [3, 1, 132], [3, 2, 114], [3, 3, 19], [3, 4, 16],  [3, 5, 229], [3, 6, 126],
                    [4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], [4, 4, 115], [4, 5, 77], [4, 6, 119],
                    [5, 0, 88], [5, 1, 32], [5, 2, 12], [5, 3, 6], [5, 4, 120], [5, 5, 86], [5, 6, 220],
                    [6, 0, 13], [6, 1, 44], [6, 2, 88], [6, 3, 98], [6, 4, 96], [6, 5, 198], [6, 6, 196],
                    [7, 0, 31], [7, 1, 1], [7, 2, 82], [7, 3, 32], [7, 4, 30], [7, 5, 320], [7, 6, 130],
                    [8, 0, 85], [8, 1, 97], [8, 2, 123], [8, 3, 64], [8, 4, 84], [8, 5, 164], [8, 6, 84],
                    [9, 0, 47], [9, 1, 114], [9, 2, 31], [9, 3, 48], [9, 4, 91], [9, 5, 8], [9, 6, 99],
                    [10, 0, 47], [10, 1, 114], [10, 2, 31], [10, 3, 48], [10, 4, 91], [10, 5, 128], [10, 6, 91],
                    [11, 0, 47], [11, 1, 114], [11, 2, 31], [11, 3, 48], [11, 4, 91], [11, 5, 128], [11, 6, 55],
                    [12, 0, 47], [12, 1, 114], [12, 2, 31], [12, 3, 48], [12, 4, 91], [12, 5, 178], [12, 6, 141],
                    [13, 0, 47], [13, 1, 114], [13, 2, 31], [13, 3, 48], [13, 4, 91], [13, 5, 62], [13, 6, 91],
                    [14, 0, 47], [14, 1, 114], [14, 2, 31], [14, 3, 48], [14, 4, 91], [14, 5, 11], [14, 6, 111],
                    [15, 0, 47], [15, 1, 114], [15, 2, 31], [15, 3, 48], [15, 4, 91], [15, 5, 68], [15, 6, 222],
                    [16, 0, 47], [16, 1, 114], [16, 2, 31], [16, 3, 48], [16, 4, 91], [16, 5, 43], [16, 6, 210],
                    [17, 0, 47], [17, 1, 114], [17, 2, 31], [17, 3, 48], [17, 4, 91], [17, 5, 231], [17, 6, 191],
                    [18, 0, 47], [18, 1, 114], [18, 2, 31], [18, 3, 48], [18, 4, 91], [18, 5, 48], [18, 6, 91],
                    [19, 0, 47], [19, 1, 114], [19, 2, 31], [19, 3, 48], [19, 4, 91], [19, 5, 48], [19, 6, 91],
                    [20, 0, 47], [20, 1, 114], [20, 2, 31], [20, 3, 48], [20, 4, 91], [20, 5, 48], [20, 6, 91],
                    [21, 0, 47], [21, 1, 114], [21, 2, 31], [21, 3, 48], [21 ,4, 91],[21, 5, 31], [21, 6, 48],
],
        dataLabels: {
            enabled: false,
            color: '#000000'
        }

    }],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                yAxis: {
                    labels: {
                        format: '{substr value 0 1}'
                    }
                }
            }
        }]

    }

});


Highcharts.chart('ViewbyDeviceandOs', {
    chart: {
        type: 'bar',
        height:340,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Unknown Platform', 'Tv', 'Tablet', 'Mobile', 'Game Console', 'Desktop']
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
        name: 'Android',
        data: [4, 4, 6, 15, 12, 14, 16],
        color: '#7d1a9e'
    }, {
        name: 'IOS',
        data: [5, 3, 12, 6, 11, 9, 15],
        color: '#bb76de'
    }, {
        name: 'Linux',
        data: [5, 15, 8, 5, 8, 18, 11],
        color: '#F79C92'
    }, {
        name: 'Other',
        data: [15, 10, 18, 7, 12],
        color: '#B422B6'
    }
        , {
            name: 'Windows',
            data: [15, 10, 18, 7, 12],
            color: '#56156C'
        }
        , {
            name: 'Kai OS',
            data: [15, 10, 18, 7, 12],
            color: '#eb95e2'
        }
        , {
            name: 'PlayStation',
            data: [15, 10, 18, 7, 12],
            color: '#cf44ff'
        }
        , {
            name: 'Wli',
            data: [15, 10, 18, 7, 12],
            color: '#f79b07'
        }, {
            name: 'Xbox',
            data: [15, 10, 18, 7, 12],
            color: '#e37ce7'
        }
        , {
            name: 'Web Os',
            data: [15, 10, 18, 7, 12],
            color: '#6b8abc'
        } ,{
            name: 'Smart Tv',
            data: [15, 10, 18, 7, 12],
            color: '#db62ed'
        },
        {
            name: 'Tizen(Views)',
            data: [15, 10, 18, 7, 12],
            color: '#89366a'
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