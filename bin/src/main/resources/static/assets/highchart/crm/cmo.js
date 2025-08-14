
function comHighChat(){
	
 //CRM CMO Cost Per Lead
        Highcharts.chart('cRMcMOCostPerLead', {
            chart: {
                type: 'area',
                animation: true,
                height: 200,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                categories: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08', '12-09', '12-10', '12-11', '12-12'],
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            plotOptions: {

                area: {
                    stacking: 'normal',
                    lineColor: '#666666',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: '#666666'
                    }
                }
            },
            series: [{
                type: 'area',
                name: 'Cost Per Lead',
                data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214, 14325, 18953, 14325],
                marker: { enabled: false },
                color: '#a7d2ff',
            }, {
                type: 'line',
                name: 'Net Income Per Lead',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039, 6754, 6234, 5045],
                marker: { enabled: false },
                color: '#020e73',
            }]
        });
	
	
	
	
	
	 //CRM CMO Cost Per MQL
        Highcharts.chart('cRMcMOCostPerMQL', {
            chart: {
                type: 'area',
                animation: true,
                height: 200,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                categories: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08', '12-09', '12-10', '12-11', '12-12'],
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            plotOptions: {

                area: {
                    stacking: 'normal',
                    lineColor: '#666666',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: '#666666'
                    }
                }
            },
            series: [{
                type: 'area',
                name: 'Cost Per MQL',
                data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214, 14325, 18953, 14325],
                marker: { enabled: false },
                color: '#a7d2ff',
            }, {
                type: 'line',
                name: 'Net Income Per MQL',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039, 6754, 6234, 5045],
                marker: { enabled: false },
                color: '#020e73',
            }]
        });
        //CRM CMO Cost Per SQL
        Highcharts.chart('cRMcMOCostPerSQL', {
            chart: {
                type: 'area',
                animation: true,
                height: 200,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                categories: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08', '12-09', '12-10', '12-11', '12-12'],
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            plotOptions: {

                area: {
                    stacking: 'normal',
                    lineColor: '#666666',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: '#666666'
                    }
                }
            },
            series: [{
                type: 'area',
                name: 'Cost Per SQL',
                data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214, 14325, 18953, 14325],
                marker: { enabled: false },
                color: '#a7d2ff',
            }, {
                type: 'line',
                name: 'Net Income Per SQL',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039, 6754, 6234, 5045],
                marker: { enabled: false },
                color: '#020e73',
            }]
        });
        //CRM CMO Cost Per Customer
        Highcharts.chart('cRMcMOCostPerCustomer', {
            chart: {
                type: 'area',
                animation: true,
                height: 200,
            },
            title: {
                text: ''
            },
            credits: {
                enabled: false
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            xAxis: {
                tickLength: 0,
                tickWidth: 0,
                categories: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08', '12-09', '12-10', '12-11', '12-12'],
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            plotOptions: {

                area: {
                    stacking: 'normal',
                    lineColor: '#666666',
                    lineWidth: 1,
                    marker: {
                        lineWidth: 1,
                        lineColor: '#666666'
                    }
                }
            },
            series: [{
                type: 'area',
                name: 'Cost Per Customer',
                data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214, 14325, 18953, 14325],
                marker: { enabled: false },
                color: '#a7d2ff',
            }, {
                type: 'line',
                name: 'Net Income Per Customer',
                data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039, 6754, 6234, 5045],
                marker: { enabled: false },
                color: '#020e73',
            }]
        });
        //CRM CMO Users Column
        Highcharts.chart('cRMcMOCostUsersColumn', {
            chart: {
                type: 'column',
                height: 50,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: null,
            },
            xAxis: {
                type: 'category',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
                lineColor: 'transparent'
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
            },
            legend: {
                enabled: false
            },
            series: [{
                name: '',
                colors: ['#ef6909'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 10,
                data: [
                    ['Jan 2016', 15],
                    ['Feb 2016', 20],
                    ['Mar 2016', 25],
                    ['Apr 2016', 30],
                    ['May 2016', 35],
                    ['Jun 2016', 15],
                    ['Jul 2016', 20],
                    ['Aug 2016', 25],
                    ['Sep 2016', 26],
                    ['Oct 2016', 32],
                    ['Nov 2016', 18],
                    ['Dec 2016', 35],
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        
        
        
          //CRM CMO Leads Column
        Highcharts.chart('cRMcMOCostLeadsColumn', {
            chart: {
                type: 'column',
                height: 50,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: null,
            },
            xAxis: {
                type: 'category',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
                lineColor: 'transparent'
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
            },
            legend: {
                enabled: false
            },
            series: [{
                name: '',
                colors: ['#ef6909'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 10,
                data: [
                    ['Jan 2016', 15],
                    ['Feb 2016', 20],
                    ['Mar 2016', 25],
                    ['Apr 2016', 30],
                    ['May 2016', 35],
                    ['Jun 2016', 15],
                    ['Jul 2016', 20],
                    ['Aug 2016', 25],
                    ['Sep 2016', 26],
                    ['Oct 2016', 32],
                    ['Nov 2016', 18],
                    ['Dec 2016', 35],
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        //CRM CMO MQL Column
        Highcharts.chart('cRMcMOCostMQLColumn', {
            chart: {
                type: 'column',
                height: 50,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: null,
            },
            xAxis: {
                type: 'category',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
                lineColor: 'transparent'
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
            },
            legend: {
                enabled: false
            },
            series: [{
                name: '',
                colors: ['#ef6909'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 10,
                data: [
                    ['Jan 2016', 15],
                    ['Feb 2016', 20],
                    ['Mar 2016', 25],
                    ['Apr 2016', 30],
                    ['May 2016', 35],
                    ['Jun 2016', 15],
                    ['Jul 2016', 20],
                    ['Aug 2016', 25],
                    ['Sep 2016', 26],
                    ['Oct 2016', 32],
                    ['Nov 2016', 18],
                    ['Dec 2016', 35],
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        //CRM CMO SQL Column
        Highcharts.chart('cRMcMOCostSQLColumn', {
            chart: {
                type: 'column',
                height: 50,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: null,
            },
            xAxis: {
                type: 'category',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
                lineColor: 'transparent'
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
            },
            legend: {
                enabled: false
            },
            series: [{
                name: '',
                colors: ['#ef6909'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 10,
                data: [
                    ['Jan 2016', 15],
                    ['Feb 2016', 20],
                    ['Mar 2016', 25],
                    ['Apr 2016', 30],
                    ['May 2016', 35],
                    ['Jun 2016', 15],
                    ['Jul 2016', 20],
                    ['Aug 2016', 25],
                    ['Sep 2016', 26],
                    ['Oct 2016', 32],
                    ['Nov 2016', 18],
                    ['Dec 2016', 35],
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
        //CRM CMO Customer Column
        Highcharts.chart('cRMcMOCostCustomerColumn', {
            chart: {
                type: 'column',
                height: 49,
                animation: true,
            },
            navigation: {
                buttonOptions: {
                    enabled: false
                }
            },
            credits: {
                enabled: false
            },
            title: {
                text: '',
            },
            subtitle: {
                text: null,
            },
            xAxis: {
                type: 'category',
                labels: {
                    enabled: false
                },
                gridLineColor: 'transparent',
                lineColor: 'transparent'
            },
            yAxis: {
                min: 0,
                gridLineColor: 'transparent',
                title: {
                    text: ''
                },
                labels: {
                    enabled: false
                },
            },
            legend: {
                enabled: false
            },
            series: [{
                name: '',
                colors: ['#ef6909'],
                colorByPoint: true,
                groupPadding: 0,
                pointWidth: 10,
                data: [
                    ['Jan 2016', 15],
                    ['Feb 2016', 20],
                    ['Mar 2016', 25],
                    ['Apr 2016', 30],
                    ['May 2016', 35],
                    ['Jun 2016', 15],
                    ['Jul 2016', 20],
                    ['Aug 2016', 25],
                    ['Sep 2016', 26],
                    ['Oct 2016', 32],
                    ['Nov 2016', 18],
                    ['Dec 2016', 35],
                ],
                dataLabels: {
                    enabled: false,
                }
            }]
        });
}