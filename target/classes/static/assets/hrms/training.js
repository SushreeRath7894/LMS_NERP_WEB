function trainingHighChat(){
//alert("training js")
    //Newly Joined Emplyoee 	
    
    Highcharts.chart('hrmsnewlyemp', {
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
            ['5+ Year',10],
            ['Less Than',30],
            ['1-2 Year',25],
            ['2-3 Year',20],
            ['3-5 Year',20]
            
          ]
        }]
      });	
      
    
    
      //Training Cost and Cost per Employee
            
    
    Highcharts.chart('hrmsemptrend', {
        chart: {
            type: 'spline',
            height:250
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
            name: 'Emplyoees Joining',
            marker: {
                symbol: 'circle'
            },
            data: [11, 8, 10, 14, 9, 15],
            color: '#BF05FF'
    
        }, {
            name: 'Emplyoees Leaving',
            marker: {
                symbol: 'circle'
            },
            data: [10, 12, 8, 15, 8, 14],
            color: '#F89D93'
        }]
    });
    
    
    
    //Status By Employee Tier
    
    Highcharts.chart('hrmsemplyoeetier', {
    
        chart: {
          type: 'column',
          height:250
        },
      
        title: {
          text: ''
        },
      
        xAxis: {
          categories: ['High Level', 'Mid Level', 'Low Level']
        },
      
        yAxis: {
          allowDecimals: false,
          min: 0,
          title: {
            text: ''
          }
        },
        exporting: { enabled: false },
           credits: {
           enabled: false
          },
        tooltip: {
          formatter: function () {
            return '<b>' + this.x + '</b><br/>' +
              this.series.name + ': ' + this.y + '<br/>' +
              'Total: ' + this.point.stackTotal;
          }
        },
      
        plotOptions: {
          column: {
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                style: {
                    textOutline: false,
                    color:'#000000' 
                  }
            }
          }
        },
        
        legend: {
          enabled: false
       },
      
        series: [{
        
          name: 'Onboarding',
          data: [5, 3, 4],
          stack: 'male',
          color:'#CA7CE5'
          }, {
          name: 'Training',
          data: [3, 4, 4],
          stack: 'male',
          color:'#F79C92'
        },{
          name: 'Completed',
          data: [5, 3, 4],
          stack: 'male',
          color:'#BF05FF'
          },
          {
          name: 'No Projects',
          data: [3, 4, 4],
          stack: 'male',
          color:'#B422B6'
        }
        
        ]
      });
      
    
    
    //Training Cost and Cost per Employee
            
    
    Highcharts.chart('hrmstrainingtrend', {
        chart: {
            type: 'spline',
            height:250
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
        
    
    // Employees By Department
    
    Highcharts.chart('hrmsemployeedept', {
        chart: {
            type: 'bar',
            backgroundColor: '#ffffff',
            color: 'black',
            height:250
        },
        title: {
            text: ''
        },
        exporting: { enabled: false },
     credits: {
        enabled: false
        },
        xAxis: {
            categories: ['IT', 'Enginnering','Operations','Sales','Marketing','Data Science'],
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
            name: 'Onboarding',
            data: [50, 60,70,50,70,50],
            color:'#bf05ff'
        },
        {
            name: 'Training',
            data: [50, 15,35,65,35,65],
            color:'#CA7CE5'
        },
        {
            name: 'Completed',
            data: [20, 25,46,55,46,55],
            color:'#F58D68'
        },
        {
            name: 'No Project',
            data: [20, 25,46,66,46,66],
            color:'#E36D61'
        }
        
      
      ]
    });	
        
    
    
    
    }