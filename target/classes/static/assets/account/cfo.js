function cfo(){

    //cfo Revenue
          Highcharts.chart('cfoRevenue', {
  
              chart: {
                  type: 'gauge',
                  plotBackgroundColor: null,
                  plotBackgroundImage: null,
                  plotBorderWidth: 0,
                  plotShadow: false,
                  height: '95px'
  
              },
  
              title: {
                  text: ''
              },
              navigation: {
                  buttonOptions: {
                      enabled: false
                  }
              },
              credits: { enabled: false, },
  
              pane: {
                  startAngle: -98,
                  endAngle: 97.9,
                  background: null,
                  center: ['50%', '75%'],
                  size: '100%'
              },
  
              // the value axis
              yAxis: {
                  min: 0,
                  max: 100,
                  tickPixelInterval: 0,
                  tickPosition: 'inside',
                  tickColor: 'transparent',
                  tickLength: 0,
                  tickWidth: 0,
                  minorTickInterval: null,
                  labels: {
                      enabled: false
                  },
                  lineWidth: 0,
                  plotBands: [{
                      from: 0,
                      to: 70,
                      color: '#BF05FF', // green
                      thickness: 7
                  }, {
                      from: 70,
                      to: 100,
                      color: '#cccccc', // gray
                      thickness: 7
                  }]
              },
  
              series: [{
                  name: '',
                  data: [100],
                  tooltip: {
                      valueSuffix: ' km/h'
                  },
                  dataLabels: {
                      enabled: false,
                  },
                  dial: {
                      radius: '0',
                      backgroundColor: 'gray',
                      baseWidth: 12,
                      baseLength: '0%',
                      rearLength: '0%'
                  },
                  pivot: {
                      backgroundColor: 'gray',
                      radius: 0
                  }
  
              }]
  
          });
  
     //cfo EBIT
     Highcharts.chart('cfoEbit', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '95px',
      },
  
      title: {
          text: ''
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: 0,
          max: 100,
          tickPixelInterval: 0,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              enabled: false
          },
          lineWidth: 0,
          plotBands: [{
              from: 0,
              to: 70,
              color: '#F79C92', // green
              thickness: 7
          }, {
              from: 70,
              to: 100,
              color: '#cccccc', // gray
              thickness: 7
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '0',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 0
          }
  
      }]
  
  });
  
  
  
  //cfo Operating Expenses
  Highcharts.chart('cfoOperatingExpenses', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '95px',
  
  
      },
  
      title: {
          text: ''
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: 0,
          max: 100,
          tickPixelInterval: 0,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              enabled: false
          },
          lineWidth: 0,
          plotBands: [{
              from: 0,
              to: 70,
              color: '#CA7CE5', // green
              thickness: 7
          }, {
              from: 70,
              to: 100,
              color: '#cccccc', // gray
              thickness: 7
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '0',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 0
          }
  
      }]
  
  });
  
  
  //cfo Gross Profit
  Highcharts.chart('cfoGrossProfit', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '95px',
          backgroundColor: 'transparent'
      },
  
      title: {
          text: ''
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: 0,
          max: 100,
          tickPixelInterval: 0,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              enabled: false
          },
          lineWidth: 0,
          plotBands: [{
              from: 0,
              to: 70,
              color: '#56156C', // green
              thickness: 7
          }, {
              from: 70,
              to: 100,
              color: '#cccccc', // gray
              thickness: 7
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '0',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 0
          }
  
      }]
  
  });
  
  
   //cfo Ebit Perc
   Highcharts.chart('cfoEbitPerc', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '95px',
          backgroundColor: 'transparent'
      },
  
      title: {
          text: ''
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: 0,
          max: 100,
          tickPixelInterval: 0,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              enabled: false
          },
          lineWidth: 0,
          plotBands: [{
              from: 0,
              to: 70,
              color: '#B422B6', // green
              thickness: 7
          }, {
              from: 70,
              to: 100,
              color: '#cccccc', // gray
              thickness: 7
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '0',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 0
          }
  
      }]
  
  });
  
  //cfo Net Income
  Highcharts.chart('cfoNetIncome', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '95px',
          backgroundColor: 'transparent'
      },
  
      title: {
          text: ''
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: 0,
          max: 100,
          tickPixelInterval: 0,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              enabled: false,
          },
          lineWidth: 0,
          plotBands: [{
              from: 0,
              to: 70,
              color: '#FFC1BA', // green
              thickness: 7
          }, {
              from: 70,
              to: 100,
              color: '#cccccc', // gray
              thickness: 7
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '0',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 0
          }
  
      }]
  
  });
  
  
  
   //cfo costs
   Highcharts.chart('cfoCosts', {
      chart: {
          type: 'pie',
          height: 114,
          margin: 0
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      title: {
          text: '',
      },
      subtitle: {
          text: '',
      },
      credits: {
          enabled: false,
      },
      yAxis: {
          title: {
              text: ''
          }
      },
      plotOptions: {
          pie: {
              shadow: false,
              colors: [
                  '#B422B6',
                  '#F79C92',
                  '#BF05FF'
              ]
          }
      },
      series: [{
          name: '',
          data: [["Product Sales", 28], ["Service & Maintainance", 6], ["Licensing", 62]],
          size: '100%',
          innerSize: '50%',
          showInLegend: false,
          dataLabels: {
              enabled: true,
              formatter: function () {
                  return Math.round(this.percentage * 100) / 100 + ' %';
              },
              distance: 5
          }
      }]
  });
  //cfo revenue
  Highcharts.chart('cfoRevenuePie', {
      chart: {
          type: 'pie',
          height: 114,
          margin: 0
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      title: {
          text: '',
      },
      subtitle: {
          text: '',
      },
      credits: {
          enabled: false,
      },
      yAxis: {
          title: {
              text: ''
          }
      },
      plotOptions: {
          pie: {
              shadow: false,
              colors: [
                  '#B422B6',
                  '#F79C92',
                  '#BF05FF'
              ]
          }
      },
      series: [{
          name: '',
          data: [["Product Sales", 28], ["Service & Maintainance", 6], ["Licensing", 62]],
          size: '100%',
          innerSize: '50%',
          showInLegend: false,
          dataLabels: {
              enabled: true,
              formatter: function () {
                  return Math.round(this.percentage * 100) / 100 + ' %';
              },
              distance: 5
          }
      }]
  });
  
  // cfo Employee Satisfaction
  Highcharts.chart('cfoEmployeeSatisfaction', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '100%'
      },
  
      title: {
          text: '71',
          align: 'center',
          verticalAlign: 'center',
          floating: true,
          y: 150,
          margin: 0,
          style: { "fontSize": '14', "color": '#000000' }
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: -100,
          max: 100,
          // tickPixelInterval: 72,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              distance: 20,
              style: {
                  fontSize: '11px'
              }
          },
          lineWidth: 0,
          plotBands: [{
              from: -100,
              to: 100,
              color: '#BF05FF',
              thickness: 20
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '70%',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 6
          }
  
      }]
  
  });
  
  
   //cfo Employee Satisfaction Line
   Highcharts.chart('cfoEmployeeSatisfactionLine', {
      chart: {
          type: 'line',
          animation: true,
          backgroundColor: 'transparent',
          height: 110,
          
          // margin: 10
      },
      title: {
          text: ''
      },
      subtitle: {
          text: ''
      },
  
      credits: {
          enabled: false
      },
      xAxis: {
          tickLength: 0,
          tickWidth: 0,
          lineColor: 'transparent',
          labels: {
              enabled: false,
          },
          dashStyle: 'ShortDashDot',
          categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul']
      },
      yAxis: {
          gridLineColor: 'transparent',
          title: {
              text: ''
          },
          labels: {
              enabled: false
          },
          showInLegend: false
      },
      plotOptions: {
          line: {
              dataLabels: {
                  enabled: false
              },
              showInLegend: false,
              enableMouseTracking: false
          }
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      series: [{
          name: '',
          data: [16.0, 18.2, 13.1, 27.9, 32.2, 24.5, 15.4],
          color: '#F79C92',
          showInLegend: false,
          // dashStyle: 'ShortDashDot',
      },]
  });
  //cfo Customer Satisfaction Line
  Highcharts.chart('cfoCustomerSatisfactionLine', {
      chart: {
          type: 'line',
          animation: true,
          backgroundColor: 'transparent',
          height: 110,
          // margin: 10
      },
      title: {
          text: ''
      },
      subtitle: {
          text: ''
      },
  
      credits: {
          enabled: false
      },
      xAxis: {
          tickLength: 0,
          tickWidth: 0,
          lineColor: 'transparent',
          labels: {
              enabled: false,
          },
          dashStyle: 'ShortDashDot',
          categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul']
      },
      yAxis: {
          gridLineColor: 'transparent',
          title: {
              text: ''
          },
          labels: {
              enabled: false
          },
          showInLegend: false
      },
      plotOptions: {
          line: {
              dataLabels: {
                  enabled: false
              },
              showInLegend: false,
              enableMouseTracking: false
          }
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      series: [{
          name: '',
          data: [16.0, 18.2, 13.1, 27.9, 32.2, 24.5, 15.4],
          color: '#BF05FF',
          showInLegend: false,
          // dashStyle: 'ShortDashDot',
      },]
  });
  
  
  // cfo Customer Satisfaction
  Highcharts.chart('cfoCustomerSatisfaction', {
  
      chart: {
          type: 'gauge',
          plotBackgroundColor: null,
          plotBackgroundImage: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: '100%'
      },
  
      title: {
          text: '71',
          align: 'center',
          verticalAlign: 'center',
          floating: true,
          y: 150,
          margin: 0,
          style: { "fontSize": '14', "color": '#000000' }
      },
      navigation: {
          buttonOptions: {
              enabled: false
          }
      },
      credits: { enabled: false, },
  
      pane: {
          startAngle: -98,
          endAngle: 97.9,
          background: null,
          center: ['50%', '75%'],
          size: '100%'
      },
  
      // the value axis
      yAxis: {
          min: -100,
          max: 100,
          // tickPixelInterval: 72,
          tickPosition: 'inside',
          tickColor: 'transparent',
          tickLength: 0,
          tickWidth: 0,
          minorTickInterval: null,
          labels: {
              distance: 20,
              style: {
                  fontSize: '11px'
              }
          },
          lineWidth: 0,
          plotBands: [{
              from: -100,
              to: 100,
              color: '#F79C92',
              thickness: 20
          }]
      },
  
      series: [{
          name: '',
          data: [100],
          tooltip: {
              valueSuffix: ' km/h'
          },
          dataLabels: {
              enabled: false,
          },
          dial: {
              radius: '70%',
              backgroundColor: 'gray',
              baseWidth: 12,
              baseLength: '0%',
              rearLength: '0%'
          },
          pivot: {
              backgroundColor: 'gray',
              radius: 6
          }
  
      }]
  
  });
  
  
   
  
  
  
  }