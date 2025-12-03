function wellnessMentalHealthHighChat(){
	
      // Participation by Department
    
    Highcharts.chart('hrmsparticipationdept', {
        chart: {
            type: 'bar',
            height:290
        },
        title: {
            text: ''
        },
        xAxis: {
        categories: ['Jan', 'Feb', 'March','April','May']
            
        },
        yAxis: {
             gridLineColor: 'transparent',
            min: 0,
           title: {
                text: ''
            },
            labels: {
            enabled: false
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
        
        series: [{
            name: '',
            data: [60, 20, 30,50,10],
            color:'#F79C92',
            dataLabels: {
                        enabled: true,
                        style: {
                            textOutline: false 
                          }
                    }
        }, {
            name: '',
            data: [30, 40, 10,50,20],
            color:'#CA7CE5',
            dataLabels: {
                    enabled: true,
                    style: {
                        textOutline: false 
                      }
                    }
        },
        
        {
            name: '',
            data: [20, 50,40,10,30],
            color:'#bf05ff',
            dataLabels: {
                    enabled: true,
                    style: {
                        textOutline: false 
                      }
                    }
        }
        
        
        
        ]
    });
        
    
    // Challenge Type Breakdown
     
     
    var chartData4 = {
        categories: ['IT', 'Market', 'Sales'],
        seriesData: [
        {
            name: 'Other',
            data: [5],
            color: '#F79C92'
        }, {
            name: 'Physical',
            data: [6],
            color: '#DEAAF0'
        }, {
            name: 'Mental',
            data: [12],
            color: '#BF05FF '
            
        }
        
        ]
            
    };
    
    
    Highcharts.chart('challengetypebreakdown', {
        chart: {
            type: 'bar',
            height:80,
             spacingTop: 0,
            spacingBottom: 0,
            groupPadding: 0,
            pointPadding: 0,
            marginTop: 0,
        },
        title: {
            text: ''
        },
        xAxis: {
           
            visible: false,
             title: {
            text: ''
            },
            labels: {
        enabled: false
        }
            
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
                    textOutline: false 
                  }
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
    
        series: chartData4.seriesData
    });
    
    
    
    //Physical activity Goal
    
    Highcharts.chart('stressleveldistr', {
    
        chart: {
          type: 'column',
          height:153
        },
      
        title: {
          text: ''
        },
      
        xAxis: {
          categories: ['Sales', 'Finance', 'Design','IT','Qualify']
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
            stacking: 'normal'
          }
        },
      
        series: [{
          name: 'Stress',
          data: [70, 85, 70,85,75],
          color:'#CA7CE5'
          }, {
          name: 'Month',
          data: [75, 86, 95,85,95],
          color:'#F79C92'
        }	
        
        ]
      });
    
    
    
      // Substring template helper for the responsive labels
    Highcharts.Templating.helpers.substr = (s, from, length) =>
        s.substr(from, length);
    
    // Create the chart
    Highcharts.chart('hrmsfrequencypart', {
    
        chart: {
            type: 'heatmap',
            height:290,
            marginTop: 0,
            marginBottom: 0,
            plotBorderWidth: 1
        },
    
    
        title: {
            text: '',
            style: {
                fontSize: '1em'
            }
        },
        
    
        xAxis: {
            categories: ['Mon', 'Tues', 'Wed', 'Thurs', 'Fri' ]   
        },
    
        yAxis: {
            categories: ['10-11 am', '12-2 pm', '3-5 pm', '5-6 pm', '7-8 pm'],
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
            symbolHeight: 286
        },
    
        tooltip: {
            format: '<b>{series.xAxis.categories.(point.x)}</b> Frequency <br>' +
                '<b>{point.value}</b> of Participation <br>' +
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
            data: [[0, 0, 86], [0, 1, 46], [0, 2, 84], [0, 3, 24], [0, 4, 65],
                [1, 0, 56], [1, 1, 30], [1, 2, 87], [1, 3, 34], [1, 4, 69],
                [2, 0, 21], [2, 1, 77], [2, 2, 93], [2, 3, 67], [2, 4, 29],
                [3, 0, 18], [3, 1, 69], [3, 2, 56], [3, 3, 100], [3, 4, 96],
                [4, 0, 67], [4, 1, 20], [4, 2, 44], [4, 3, 15], [4, 4, 78]
               ],
            dataLabels: {
                enabled: true,
                color: '#000000',
                style: {
                    textOutline: false 
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
    
   
   
    
    }