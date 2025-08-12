$(document).ready(() => {


    // CATEGORY WISE BIDDING
    Highcharts.chart('categorywisebidding', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
            height: 250
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
                borderRadius: 5,
                dataLabels: {
                    enabled: true,
                    format: '<b>{point.name}</b><br>{point.percentage:.1f} %',
                    distance: -50,
                    filter: {
                        property: 'percentage',
                        operator: '>',
                        value: 4
                    }
                }
            }
        },
        series: [{
            name: 'Share',
            data: [{
                    name: 'Design',
                    y: 14,
                    'color': '#4f81bc'
                },
                {
                    name: 'Facrication',
                    y: 20,
                    'color': '#c1514f'
                },
                {
                    name: 'Eraction',
                    y: 7,
                    'color': '#9bbb58'
                },
                {
                    name: 'AMC',
                    y: 21,
                    'color': '#8064a1'
                },
                {
                    name: 'Civil Work',
                    y: 38,
                    'color': '#4aacc5'
                }
            ]
        }]

    });

    // STRIKE RATE
    Highcharts.chart('strike', {
        chart: {
            type: 'column',
            height: 250
        },
        xAxis: {
            categories: [
                'Design',
                'Facrication',
                'Eraction',
                'AMC',
                'Civil Work',
            ],
            crosshair: true
        },
        yAxis: {
            visible: false,
            min: 0,
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                '<td style="padding:0"><b>{point.y:1f}</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y}'
                }
            }
        },
        series: [{
            name: 'Bidding',
            color: '#4a8cd6',
            data: [22, 32, 21, 34, 62]

        }, {
            name: 'Final Stage',
            color: '#e45f32',
            data: [16, 24, 8, 28, 50]

        }, {
            name: 'L1',
            color: '#9bbb58',
            data: [3, 2, 1, 2, 6]

        }, {
            name: 'Contract',
            color: '#8064a1',
            data: [1, 1, 2, 4, 6]

        }]
    });


    // ROUTE CAUSE ANALYSIS
    Highcharts.chart('routecauseanalysis', {

        chart: {
            height: 250
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },
        xAxis: {
            categories: [
                'Design',
                'Facrication',
                'Eraction',
                'AMC',
                'Civil Work',
            ],
            crosshair: true
        },
        series: [{
            name: 'Price',
            color: '#4980b9',
            data: [16, 30, 10, 28, 50]
        }, {
            name: 'Experience',
            data: [7, 10, 5, 12, 34],
            color: '#be5154'
        }, {
            name: 'Documentation',
            data: [6, 10, 7, 12, 22],
            color: '#91c154'
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


    // GOVERNMENT VS CORPORATE
    Highcharts.chart('govtvscorp', {
        chart: {
            type: 'area',
            height: 250
        },
        title: {
            text: 'My Chart'
        },
        xAxis: {
            categories: [
                'Design',
                'Facrication',
                'Eraction',
                'AMC',
                'Civil Work',
            ],
        },
        tooltip: {
            enabled: true // Enable tooltips
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y}'
                }
            }
        },
        series: [{
            name: 'Government',
            color: '#4a8cd6',
            data: [
                18, 28, 8, 29, 50
            ]
        }, {
            name: 'Corporate',
            color: '#e45f32',
            data: [
                8, 12, 4, 14, 24
            ]
        }]
    });

    // PROPOSAL PROGRESS
    Highcharts.chart('proposalprogress', {
        chart: {
            type: 'bar',
            height: 578
        },
        xAxis: {
            categories: ['Contract', 'Negotiation', 'Presentation', 'Submission', 'Documentation',
                'Financial Proposal', 'Technical Proposal', 'Clarification', 'Review', 'Identify'
            ],
        },
        yAxis: {
            visible: false
        },
        tooltip: {
            pointFormat: '{series.name}: {point.y}'
        },
        plotOptions: {
            series: {
                borderWidth: 0,
                dataLabels: {
                    enabled: true,
                    format: '{point.y}'
                }
            }
        },
        series: [{
            name: 'Civil Work',
            color: '#4aacc5',
            data: [5, 7, 5, 7, 5, 7, 5, 1, 7, 5]
        }, {
            name: 'AMC',
            color: '#8064a1',
            data: [7, 3, 7, 3, 1, 3, 7, 2, 3, 7]
        }, {
            name: 'Eraction',
            color: '#9bbb58',
            data: [2, 4, 2, 4, 4, 4, 2, 8, 4, 4]
        }, {
            name: 'Facrication',
            color: '#c1514f',
            data: [4, 6, 4, 6, 3, 6, 4, 5, 6, 5]
        }, {
            name: 'Design',
            color: '#4f81bc',
            data: [3, 5, 3, 5, 2, 5, 3, 4, 5, 3]
        }]
    });


})