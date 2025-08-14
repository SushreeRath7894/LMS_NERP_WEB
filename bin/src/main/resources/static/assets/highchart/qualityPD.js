function quality(){
	
	   //return cost analysis
        Highcharts.chart('returnCostAnalysis', {
            chart: {
                zoomType: 'xy',
                animation: true,
                height: 250
            },
            title: {
                text: 'Return to Vendor Costs & Vendor Rejection Rate',
                align: 'left',
                style: { 'fontSize': '13px' }
            },
            subtitle: {
                text: 'by Supplier & Category',
                align: 'left',
                style: { 'fontSize': '10px' }
            },
            credits: { enabled: false },
            xAxis: [{
                categories: ['Globex Ship', 'McMillan Log', 'Plumbus Shop', 'Raw Suppl. Co.', 'Tech Consult.'],
                crosshair: true,
                lineColor: '#cccccc'
            }],
            yAxis: [{
                labels: {
                    format: '',
                    style: {
                        display: 'none',
                    }
                },
                title: {
                    text: 'Return Costs',
                    style: {
                        display: 'none',
                    }
                }
            },
            {
                title: {
                    text: 'Rejection Rates',
                    style: {
                        display: 'none',
                    }
                },
                labels: {
                    format: '{value} mm',
                    style: {
                        display: 'none',
                    }
                },
                opposite: false
            },
            ],
            tooltip: {
                shared: false
            },
            legend: {
                align: 'center',
                verticalAlign: 'bottom',
                floating: false,
                backgroundColor: 'transparent'
            },
            series: [{
                name: 'Return Costs',
                type: 'column',
                color: '#9fdca7',
                yAxis: 1,
                data: [27.6, 28.8, 21.7, 34.1, 23.6],
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#000000',
                    align: 'center',
                    verticalAlign: 'center',
                    format: '{point.y:.1f}',
                    style: {
                        fontSize: '10px',
                    }
                },

            }, {
                name: 'Rejection Rate',
                type: 'spline',
                data: [-13.6, -14.9, -5.8, -0.7, -13.14],
                color: '#ea8d64',
                dataLabels: {
                    enabled: true,
                    rotation: 0,
                    color: '#FFFFFF',
                    backgroundColor: '#ea8d64',
                    align: 'right',
                    format: '{point.y:.1f}',
                    style: {
                        fontSize: '10px',
                    }
                },
                marker: {
                    lineWidth: 2,
                    fillColor: '#ea8d64'
                }
            }]
        });
}