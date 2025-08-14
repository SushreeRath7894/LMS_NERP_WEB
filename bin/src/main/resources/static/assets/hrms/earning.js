function earningHighChat(){
//alert("earning js")

     
 Highcharts.chart('hrmslargeproject', {
    chart: {
        type: 'bar',
        height: 200,
        spacingTop: 0,
        spacingBottom: 0,
        spacingLeft: 0,
        spacingRight: 0,
        groupPadding: 0,  // Remove the padding between groups
        pointPadding: 0   // Remove the padding between individual bars
    },
    title: {
        text: ''
    },
    xAxis: {
        categories: ['Micro Projects','Mini Projects','Small Projects','Large Projects','Mid Sized Projects'],  // Set the categories directly
        visible: false,
        title: {
            text: ''
        },
        labels: {
            enabled: true
        },
        tickmarkPlacement: 'on',
        gridLineWidth: 0, // Remove the grid lines for a cleaner look
    },
    yAxis: {
        gridLineColor: 'transparent',
        title: {
            text: false,
            visible: false,
            lineWidth: 0,
            minorGridLineWidth: 0,
            lineColor: 'transparent'
        },
        labels: {
            enabled: false
        }
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                style: {
                    color: 'black',
                    textOutline: 'none',
                    fontWeight: 'bold'
                },
                formatter: function() {
                    return this.series.name + ': ' + this.y; // Display name and value
                },
                align: 'center' // Align the labels to the left side
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
    legend: {
        enabled: false
    },
    series: [
        {
            name: 'Micro Projects',
            data: [2],
            color: '#F79C92'
        }, {
            name: 'Mini Projects',
            data: [2],
            color: '#E976AD'
        },
        {
            name: 'Small Projects',
            data: [2],
            color: '#DB5199'
        }, 
        {
            name: 'Large Projects',
            data: [3],
            color: '#DB51C9'
        },{
            name: 'Mid Sized Projects',
            data: [3],
            color: '#BF05FF'
        },
    ]
});

     
    
  
    
    
    
    Highcharts.chart('hrmsbillable', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height:250
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
          credits: {
          enabled: false
        },
        
        exporting: { enabled: false },
        plotOptions: {
          pie: {
          colors: [
                      '#CA7CE5',
                      '#bf05ff',
                      '#B422B6',
                      '#56156C',	
                      '#F79C92'	
                  ],
            dataLabels: {
              enabled: true,
              distance: 10,
              format: '<span>{point.name}</span><br>' +
              '<span>{point.percentage:.0f} %</span>', 		
              style: {
                fontWeight: 'bold',
                color: 'black'
              }
            },
            startAngle: -180,
            endAngle: 180,
            center: ['50%', '50%'],
            size: '70%'
          }
        },
        series: [{
          type: 'pie',
          name: '',
          innerSize: '50%',
          data: [
            ['20-30 Hours',5],
            ['0 Hours',30],
            ['<5 Hours',25],
            ['11-20 Hours',20],
            ['6-10 Hours',20]
            
          ]
        }]
      });	
      
    
    
      // Large Projects Chart
     
     
           
    
    
      //Trend Dollar per Hour
    
    Highcharts.chart('hrmstrenddollar', {
        chart: {
            type: 'spline',
            height:234
        },
        title: {
                   text: ''
                },
       
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
                
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
            name: 'Engineers',
            marker: {
                symbol: 'circle'
            },
            data: [11, 8, 10, 14, 9, 15],
            color: '#51A4D6'
    
        }, {
            name: 'HR',
            marker: {
                symbol: 'circle'
            },
            data: [10, 12, 8, 15, 8, 14],
            color: '#BF05FF'
        },
        
        {
            name: 'Data Science',
            marker: {
                symbol: 'circle'
            },
            data: [15, 28, 25, 40, 20, 16],
            color: '#F79C92'
        },
        
        {
            name: 'Managers',
            marker: {
                symbol: 'circle'
            },
            data: [40, 30, 50, 45, 35, 28],
            color: '#CA7CE5'
        }
        
        
        ]
    });	
    
    
             // Substring template helper for the responsive labels
    Highcharts.Templating.helpers.substr = (s, from, length) =>
        s.substr(from, length);
    
    // Create the chart
    Highcharts.chart('hrmsprojectsdist', {
    
        chart: {
            type: 'heatmap',
            marginTop: 0,
            marginBottom: 50,
            plotBorderWidth: 1,
            height:250
        },
    
    
        title: {
            text: '',
            style: {
                fontSize: '1em'
            }
        },
        
    
        xAxis: {
            categories: ['20-30', '31-35', '36-40', '41-45', '46+' ]   
        },
    
        yAxis: {
            categories: ['< Month', '1-2 Months', '2-6 Months', '6-12 Months', '1+ Years'],
            title: null,
            reversed: true
        },
    
        accessibility: {
            point: {
                descriptionFormat: '{(add index 1)}. ' +
                    '{series.xAxis.categories.(x)} sales ' +
                    '{series.yAxis.categories.(y)}, {value}.'
            }
        },
        
        
    
     colorAxis: {
        reversed: false,
        min: 0,
        stops: [
            [0, '#FFD9D5'],
            [0.5, '#BF05FF'],
            [1, '#F4DEFC']
        ]
    },
    
        legend: {
            align: 'right',
            layout: 'vertical',
            margin: 0,
            verticalAlign: 'top',
            y: 25,
            symbolHeight: 280
        },
    
        tooltip: {
            format: '<b>{series.xAxis.categories.(point.x)}</b> Projects<br>' +
                '<b>{point.value}</b> distribution <br>' +
                '<b>{series.yAxis.categories.(point.y)}</b>'
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
            name: 'Sales per employee',
            borderWidth: 1,
            borderColor: '#F2CEFF',
            data: [[0, 0, 4], [0, 1, 6], [0, 2, 7], [0, 3, 8], [0, 4, 7],
                [1, 0, 11], [1, 1, 12], [1, 2, 23], [1, 3, 43], [1, 4, 21],
                [2, 0, 14], [2, 1, 12], [2, 2, 32], [2, 3, 34], [2, 4, 29],
                [3, 0, 30], [3, 1, 20], [3, 2, 21], [3, 3, 19], [3, 4, 17],
                [4, 0, 23], [4, 1, 20], [4, 2, 44], [4, 3, 15], [4, 4, 8]
               ],
            dataLabels: {
                enabled: true,
                color: '#000000',
                style: {
                    textOutline: false,
                    fontSize:'11px' 
                  }
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
    
    
    
    

    
    
     Highcharts.chart('hrmsconcluded', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height:260
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}: <b>{point.percentage:.0f}%</b>'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
          credits: {
          enabled: false
        },
        
        exporting: { enabled: false },
        plotOptions: {
          pie: {
          colors: [
                      '#CA7CE5',
                      '#bf05ff',
                      '#B422B6',	
                      '#F79C92'	
                  ],
            dataLabels: {
              enabled: true,
              distance: 10,
              format: '<span>{point.name}</span><br>' +
              '<span>{point.percentage:.0f} %</span>', 
              style: {
                fontWeight: 'bold',
                color: 'black'
              }
            },
            startAngle: -180,
            endAngle: 180,
            center: ['50%', '50%'],
            size: '70%'
          }
        },
        series: [{
          type: 'pie',
          name: '',
          innerSize: '50%',
          data: [
            ['Negative',10],
            ['Extreme',36],
            ['Satisfactory',30],
            ['Neutral',24]
          
            
          ]
        }]
      });	
              
              
              
        Highcharts.chart('hrmstotalworkhrs', {
        chart: {
            type: 'bar',
            backgroundColor: '#ffffff',
            color: 'black',
            height:240
        },
        title: {
            text: ''
        },
        exporting: { enabled: false },
     credits: {
        enabled: false
        },
        xAxis: {
            categories: ['Project 1', 'Project 2','Project 3','Project 4','Project 5','Project 6'],
            title: {
                text: null
            }
             
        },
        yAxis: {
            min: 0,
            color: 'black',
            gridLineWidth: 0,
            title: {
                text: '',
                align: 'high'
            },
           labels: {
            format: '{value}'
           }
        },
        tooltip: {
            valueSuffix: ' '
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        
        credits: {
            enabled: false
        },
        series: [{
            name: 'Normal Hours',
            data: [50, 60,70,50,80,90],
            color:'#bf05ff'
        },
        {
            name: 'Over Time',
            data: [50, 15,35,55, 70,80],
            color:'#F79C92'
        }
        
      
      ]
    });
    
    
    
    
      Highcharts.chart('hrmsdollarhour', {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
        chart: {
	        type: 'bar',
            backgroundColor: '#ffffff',
            color: 'black',
            height:240
        },
        title: {
            text: ''
        },
        exporting: { enabled: false },
     credits: {
        enabled: false
        },
        xAxis: {
            categories: ['Project 1', 'Project 2','Project 3','Project 4','Project 5','Project 6','Project 7'],
            title: {
                text: null
            }
             
        },
        yAxis: {
            min: 0,
            color: 'black',
            gridLineWidth: 0,
            title: {
                text: '',
                align: 'high'
            },
           labels: {
            format: '{value}'
           }
        },
        tooltip: {
            valueSuffix: ' '
        },
        plotOptions: {
            bar: {
                dataLabels: {
                    enabled: true
                }
            }
        },
        
        credits: {
            enabled: false
        },
        series: [{
            name: 'Normal Hours',
            data: [30, 40,50,60,80,90,110],
            color:'#bf05ff'
        }
    
      ]
    });	
            
                
   
   
    
    }