$(document).ready(()=>{
// ----------------------------Sales Force--------------------------------------------
//leadsbySource
Highcharts.chart("leadsbySource", {
    chart: {
        type: "pie",
        animation: true,
        height: 270,
    },
    credits: { enabled: false },
    title: {
        text: "",
    },
    navigation: {
        buttonOptions: {
            enabled: false,
        },
    },
    colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#b422b6"],
    yAxis: {
        title: {
            text: "",
        },
    },
    plotOptions: {
        pie: {
            shadow: false,
        },
    },
    tooltip: {
        formatter: function () {
            return "<b>" + this.point.name + "</b>: " + this.y + " %";
        },
    },
    legend: {
        itemStyle: { fontSize: "11px" },

    },
    series: [
        {
            name: "",
            data: [
                ["Site Visitors", 18.52],
                ["Advertisement", 21.98],
                ["Word of Mouth", 19.51],
                ["Employee Referral", 20.25],
                ["Cold call/Prospecting", 16],
            ],
            size: "100%",
            innerSize: "65%",
            showInLegend: true,
            dataLabels: {
                enabled: true,
                format: "{point.percentage:.1f} %",
            },
        },
    ],
});
//Leads vs. Converted Leads by Source
Highcharts.chart('LeadsConvertedLeadsbySource', {
    chart: {
        type: 'bar',
         height: 270,
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Site Visitors', 'Advertisement', 'Word Of Mouth', 'Employee Referral', 'Cold call/Prospecting', 'Others', 'Custom referral']
    },
    yAxis: {
        min: 0,
        title: {
            text: ''
        }
    },
    legend: {
        reversed: true,
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
        name: 'Leads',
        data: [89, 82,80,79,75,71,39],
        color: '#F79C92'
    }, {
        name: 'Converted Leads',
        data: [59,51,52,49,53,51,29],
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




//LeadsConvertedLeadsbyMonth
Highcharts.chart('LeadsConvertedLeadsbyMonth', {
    chart: {
        type: 'area',
        animation: true,
        height: 280,
        legend: {
            enabled: true
        }
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
        categories: ['Oct 2023', 'Nov 2023', 'Dec 2023'],
        tickWidth: 0
    },
    yAxis: {
        title: '',
        labels: { enabled: true },
    },
    credits: false,
    series: [{
        name: 'Leads',
        data:
            [
                88,
                85,
                72,90,81,78
            ],
        color: '#CA7CE5 ',
        dataLabels: {
            enabled: true,
        },
    }, {
        name: 'Converted Leads',
        data:
            [
                57,
                57,
                50,52,56,55
            ],
        color: "#bf05ff",
        dataLabels: {
            enabled: true,
        },
    }]
});

//LeadsConversionRateTrend
Highcharts.chart("LeadsConversionRateTrend", {
    chart: {
        animation: true,
        height: 300,
    },
    navigation: {
        buttonOptions: {
            enabled: false,
        },
    },
    title: {
        text: "",
    },
    subtitle: {
        text: "",
    },
    yAxis: {
        title: {
            text: "",
        },
        labels: {
            format: '{value} %', // Display y-axis labels as percentages
        },
    },

    credits: { enabled: false },

    xAxis: {
        tickLength: 0,
        tickWidth: 0,
        // labels: {
        //     rotation: -45,
        //     style: {
        //         fontSize: '10px',
        //         color: '#000000'
        //     }
        // },
        showInLegend: false,
        categories: ["Oct 2023", "Nov 2023", "Dec 2023", "Jan 2024", "Feb 2024","Mar 2024"],
    },
    legend: {
        layout: "horizontal",
        align: "center",
        verticalAlign: "bottom",
    },

    plotOptions: {
        series: {
            label: {
                connectorAllowed: false,
            },
            // pointStart: 2010,
        },
    },

    series: [
        {
            type: "spline",
            name: "Conversion Rate",
            data: [
                70.77, 64.06, 60.72,55.78, 64.14, 80.51
            ],
            color: "#bf05ff",
            dataLabels: {
                enabled: true,
                format: '{y} %',
            },
        },
        {
            type: "spline",
            name: "Polynomial(Conversion Rate(CUSTOM)",
            data: [
                66.77, 62.06, 64.44, 68.78, 72.14, 74.51
            ],
            color: "#A594F9",
            marker: {
                enabled: false,
            },
            dashStyle: "line",
        },
    ],

    responsive: {
        rules: [
            {
                condition: {
                    maxWidth: 500,
                },
                chartOptions: {
                    legend: {
                        layout: "horizontal",
                        align: "center",
                        verticalAlign: "bottom",
                    },
                },
            },
        ],
    },
});

//leadsbyIndustry
Highcharts.chart("leadsbyIndustry", {
    chart: {
        type: "pie",
        animation: true,
        height: 270,
    },
    credits: { enabled: false },
    title: {
        text: "",
    },
    navigation: {
        buttonOptions: {
            enabled: false,
        },
    },
    colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#b422b6"],
    yAxis: {
        title: {
            text: "",
        },
    },
    plotOptions: {
        pie: {
            shadow: false,
        },
    },
    tooltip: {
        formatter: function () {
            return "<b>" + this.point.name + "</b>: " + this.y + " %";
        },
    },
    legend: {
        itemStyle: { fontSize: "11px" },

    },
    series: [
        {
            name: "",
            data: [
                ["Project Development", 18.52],
                ["Plastic Technology", 21.98],
                ["Consultant", 19.51],
                ["Leather Development", 20.25],
            ],
            size: "100%",
            innerSize: "65%",
            showInLegend: true,
            dataLabels: {
                enabled: true,
                format: "{point.percentage:.1f} %",
            },
        },
    ],
});
(async () => {

    const topology = await fetch(
        'https://code.highcharts.com/mapdata/custom/world.topo.json'
    ).then(response => response.json());

    Highcharts.mapChart('lcls', {
        chart: {
            map: topology,
            height:290,
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
            min: 10,
            max: 25,
            tickInterval: 5,
            stops: [[0, '#F1EEF6'], [0.05, '#f79c92'], [1, '#f8c0ba']],
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
        credits: {
            enabled: false
        },
        exporting: {
            enabled: false
        }
    });

})();
})