function wellnessOverviewHighChat(){
	//alert("welness js")
    // Participation Trend
    
    Highcharts.chart('participationtrend', {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
        chart: {
            type: 'bar',
            backgroundColor: '#ffffff',
            color: 'black',
             height: 150
        },
        title: {
            text: ''
        },
        exporting: { enabled: false },
     credits: {
        enabled: false
        },
        xAxis: {
            categories: ['Jan 22', 'Feb 22','Mar 22','Apr 22','May 22'],
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
            showInLegend: false,   
            name: 'Normal Hours',
            data: [50,60,80,90,110],
            color:'#bf05ff'
        }
    
      ]
    });	
    
    
    //Wellness Trend 
            
    
        Highcharts.chart('hrmswellnesstrend', {
            chart: {
                type: 'spline',
                height: 150
            },
            title: {
                text: ''
            },
            xAxis: {
                categories: ['Jan 24', 'Feb 24', 'Mar 24', 'Apr 24', 'May 24', 'Jun 24'],
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
    
    //Salary Distribution by Experience
    
   
    
    
    // Participation Trend
    
    Highcharts.chart('employeewellness', {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
        chart: {
            type: 'bar',
            backgroundColor: '#ffffff',
            color: 'black',
            height: 150
        },
        title: {
            text: ''
        },
        exporting: { enabled: false },
     credits: {
        enabled: false
        },
        xAxis: {
            categories: ['Physical Activity', 'Mental Wellness','Stress Level'],
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
            showInLegend: false,   
            data: [80,90,110],
            color:'#F79C92'
        }
    
      ]
    });	
    
    // Participation Summary
    
    Highcharts.chart('participationsummary', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: 250
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
            size: '100%'
          }
        },
        series: [{
          type: 'pie',
          name: '',
          innerSize: '50%',
          data: [
           
            ['Activities',27],
            ['Wellness',40],
            ['Challenging',33]
            
          ]
        }]
      });	
      
 
      
        // Substring template helper for the responsive labels
    Highcharts.Templating.helpers.substr = (s, from, length) =>
        s.substr(from, length);
    
    // Create the chart
    Highcharts.chart('employeeengagement', {
    
        chart: {
            type: 'heatmap',
            marginTop: 20,
            marginBottom: 40,
            plotBorderWidth: 1,
            height:250,
            
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
            symbolHeight: 280
        },
    
        tooltip: {
            format: '<b>{series.xAxis.categories.(point.x)}</b> Employee <br>' +
                '<b>{point.value}</b> engagement <br>' +
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
          
      //////////////////////////////////end 1///////////////////////////////////////////    
        
   /*       
       //Physical activity Goal
    
    Highcharts.chart('physicalactgoal', {
    
        chart: {
          type: 'column',
          height:200
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
            stacking: 'normal',
            dataLabels: {
                enabled: true,
                color: '#000000',
                style: {
                 textOutline: false 
               }
            }
          }
        },
      
        series: [{
          name: 'Completed',
          data: [70, 85, 70,85,75],
          color:'#CA7CE5'
          }, {
          name: 'Goal',
          data: [75, 86, 95,85,95],
          color:'#F79C92'
        }	
        
        ]
      });
    
    
      Highcharts.chart('challengebreakdown', {
        chart: {
            type: 'bar',
            height:200,
            spacingTop: 0,
            spacingBottom: 0,
            groupPadding: 0,
            pointPadding: 0
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
                    color: 'black', 
                    textOutline: 'none' 
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
    
        series: chartData3.seriesData
    });
    
        
       //Avg. Step Count Trend
            
    
    Highcharts.chart('stepcounttrend', {
        chart: {
            type: 'spline',
            height:200
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
    
    
    
      
    
    // Participation by Department
    
    Highcharts.chart('participationdepartment', {
        chart: {
            type: 'bar',
            height:250
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
     
     
    var chartData3 = {
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
    
    
  
    
   
    //Frequency of Participation
    
    
    //BMI Summary
    
    
    Highcharts.chart('hrmsbmisummary', {
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false,
            type: 'pie',
            height:250
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
                    '#BF05FF',
                    '#CA7CE5'
                        
                ],
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    format: '<span>{point.name}</span><br>' +
                            '<span>{point.percentage:.0f} %</span>', 
                    style: {
                      fontWeight: 'bold',
                      color: 'white'
                    },		
                    connectorColor: 'rgba(128,128,128,1)',
                    distance: -40,
                },
                 center: ['50%', '50%'],
                size: '110%'
            }
        },
        series: [{
            name: 'Share',
            data: [
                { name: 'Highly', y: 21 },
                { name: 'Overweight', y: 32 },
                { name: 'Inappropriate', y: 26 },
                { name: 'Poor Physical', y: 21 }
               
            ]
        }],
        
        credits: {
        enabled: false
        }
        
        
    });
    
    
    
      // Substring template helper for the responsive labels
    Highcharts.Templating.helpers.substr = (s, from, length) =>
        s.substr(from, length);
    
    // Create the chart
    Highcharts.chart('frequencyParticipation', {
    
        chart: {
            type: 'heatmap',
            height:250,
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
            symbolHeight: 280
        },
    
        tooltip: {
            format: '<b>{series.xAxis.categories.(point.x)}</b> Frequency<br>' +
                '<b>{point.value}</b> participation <br>' +
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
                    color: 'black', 
                    textOutline: 'none' 
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
    
    //////////////////////////////////end 2///////////////////////////////////////////
   
      
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
    
    /////////////////////////////////end 3/////////////////////////////////////////
    
    
      //Frequency of Participation
    
   
    
    
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
    
    
    
         
    
  ////////////////////////////////end 4/////////////////////////////////////////////
                
    */
   
    
    }