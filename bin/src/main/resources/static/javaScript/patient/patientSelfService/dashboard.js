$(function () {
    function createChart(id, unit, data, colorFrom, colorTo, yMin, yMax) {
        Highcharts.chart(id, {
            chart: {
                type: 'area',
                height: '75%'
            },
            title: { text: null },
            legend: { enabled: false },
            xAxis: {
                categories: ['0', '1', '2', '3', '4', '5', '6'],
                tickLength: 0
            },
            yAxis: {
                min: yMin,
                max: yMax,
                title: { text: null }
            },
            tooltip: { valueSuffix: ' ' + unit },
            credits: { enabled: false },
            exporting: { enabled: false },
            series: [{
                name: '',
                data: data,
                dashStyle: 'ShortDash',
                color: colorTo,
                fillOpacity: 0.5,
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
                    stops: [
                        [0, colorTo],
                        [1, colorFrom]
                    ]
                },
                marker: {
                    enabled: true,
                    radius: 3
                }
            }]
        });
    }

    createChart('bp-chart', 'mm/Hg', [120, 122, 119, 121, 123, 120, 122], 'rgba(255,77,77,0)', 'rgba(255,77,77,0.6)', 50, 150);
    createChart('hr-chart', 'beats/min', [90, 92, 94, 96, 95, 93, 94], 'rgba(255,170,0,0)', 'rgba(255,170,0,0.6)', 50, 130);
    createChart('glucose-chart', 'mg/dL', [130, 128, 132, 134, 133, 131, 130], 'rgba(255,102,102,0)', 'rgba(255,102,102,0.6)', 50, 150);
    createChart('oxy-chart', 'SpO2', [95, 96, 94, 97, 96, 95, 95], 'rgba(51,153,255,0)', 'rgba(51,153,255,0.6)', 75, 125);
    createChart('sleep-chart', 'hrs', [6, 6.5, 7, 7.5, 7, 6.5, 6.25], 'rgba(255,102,179,0)', 'rgba(255,102,179,0.6)', 0, 10);
    createChart('cholesterol-chart', 'mg/dL', [80, 82, 85, 88, 87, 85, 86], 'rgba(51,204,51,0)', 'rgba(51,204,51,0.6)', 50, 150);
    createChart('temp-chart', '°F', [97.6, 97.8, 98, 97.5, 97.9, 97.6, 97.7], 'rgba(255,153,102,0)', 'rgba(255,153,102,0.6)', 90, 110);
    createChart('weight-chart', 'bmi', [180, 182, 185, 188, 187, 185, 185.2], 'rgba(179,102,255,0)', 'rgba(179,102,255,0.6)', 50, 250);
});