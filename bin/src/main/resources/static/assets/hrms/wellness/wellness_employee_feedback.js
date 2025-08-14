function wellnessEmployeeFeedbackHighChat(){
	
      //hrmsfeedbacktype
        Highcharts.chart('hrmsfeedbacktype', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
            height:190
        },
        title: {
            text: '',
            align: 'left'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
        },
        accessibility: {
            point: {
                valueSuffix: '%'
            }
        },
        
            navigation: {
            buttonOptions: {
                enabled: false
            }
        },
        plotOptions: {
            pie: {
            colors: [
                    '#B422B6',
                    '#F79C92',
                    '#BF05FF'
                        
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<span>{point.name}</span><br>' +
                            '<span>{point.percentage:.0f} %</span>', 
                            
                style: {
                          fontWeight: 'bold',
                          color: 'black'
                        },		
                    connectorColor: 'rgba(128,128,128,1)',
                    distance: 10,
                },
                 center: ['50%', '50%'],
                size: '100%'
            }
        },
        series: [{
            name: 'Share',
            data: [
                { name: 'Negative', y: 27 },
                { name: 'Positive', y: 40 },
                { name: 'Neutral', y: 33 }
               
            ]
        }],
        
        credits: {
        enabled: false
        }
        
        
    });
    
    
    // Feedback Trend
            
    
    Highcharts.chart('hrmsfeedbacktrend', {
        chart: {
            type: 'spline',
            height:190
        },
        title: {
                   text: ''
                },
       
        xAxis: {
            categories: ['Jan 24', 'Feb 24', 'Mar 24','Apr 24','May 24','Jun 24'],
                
            accessibility: {
                description: 'Months of the year'
            }
        },
        yAxis: {
            gridLineWidth: 0,
            title: {
                text: ''
            },
            labels: {
                format: '{value}'
            }
        },
        tooltip: {
            crosshairs: true,
            shared: true
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
            showInLegend: false, 
            marker: {
                symbol: 'circle'
            },
            data: [28, 48, 30, 14, 31, 15],
            color: '#BF05FF'
    
        }]
    });
    
    
    
    }