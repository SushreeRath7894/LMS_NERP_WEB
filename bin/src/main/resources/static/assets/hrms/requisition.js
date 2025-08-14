function requisitionHighchart() {
	
	 //Total department Name
            Highcharts.chart('TotaldepartmentName', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Sales & Marketing', 'Product Management', 'Software Development', 'Project Management', 'QA & Testing', 'Research & Development', 'System Administration', 'App Development', 'Finance', 'Human Resource'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [10, 8, 7, 6, 6, 6, 6, 5, 5, 5],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });

            
            
              //Monthly Request Mom
            Highcharts.chart('MonthlyRequestMom', {
    
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
    
                plotOptions: {
                    column: {
                        groupPadding: 0
                    }
                },
    
                xAxis: [{
                    categories: ['September', 'October', 'November', 'December', 'January', 'February'],
                    min: 0
                }],
    
                yAxis: {
    
                    title: {
                        text: ''
                    },
                    labels: {
                        format: '{value}'
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
                    enabled: true
                },
    
                series: [{
                    name: 'Total Hirings',
                    data: [6, 16, 13, 12, 10, 7],
                    color: '#bf05ff'
                },
    
                {
                    name: 'WoW %',
                    type: 'line',
                    data: [0, 18, 4, 5, 4, 2],
                    color: '#F79C92'
                }]
    
            });
            
            
             //Approval Rate Name
    Highcharts.chart('ApprovalRateName', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Virtulaize Software', 'Version Control System', 'Testing Tools', 'Storage Devices', 'Software Development Kits', 'Servers', 'Remote Access Tools', 'Recruitment & Applicant Tracking System', 'Performance Management System', 'Mouse', 'Monitoring & Management Tools', 'Marketing Automatic Tools', 'Laptops', 'HR Management Software', 'Employee Engagement & Communicative Tools', 'Email Marketing Software', 'Desktop Computer', 'CRM', 'Collaboration Tools', 'Cable Management Tools', 'Analytics & Reporting Tools'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [91.1, 85, 93.6, 78.2, 91, 70.5, 91.7, 89, 87.5, 96.8, 95.2, 82.7, 89.7, 85.6, 83.1, 78.3, 82.6, 75.6, 91.7, 92.4, 78.6],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
            
            
	  //Monthly Request Mom
    Highcharts.chart('MonthlyMom', {
    
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
    
                plotOptions: {
                    column: {
                        groupPadding: 0
                    }
                },
    
                xAxis: [{
                    categories: ['December 2023', 'January 2024', 'November', 'October 2023', 'September', 'February 2024'],
                    min: 0
                }],
    
                yAxis: {
    
                    title: {
                        text: ''
                    },
                    labels: {
                        format: '{value}'
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
                    enabled: true
                },
    
                series: [{
                    name: 'Total Requests',
                    data: [167, 166, 150, 125, 121, 94],
                    color: '#F79C92',
                    dataLabels: {
                        enabled: true,
                    }
                },
    
                {
                    name: 'MoM %',
                    type: 'line',
                    data: [166, 165, 149, 124, 121, 93],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
    
            });
            
            
            // Tier Analysis
    Highcharts.chart('DepartmentWiseTotalRequest', {
                chart: {
                    type: 'column',
                    height: 300
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: [
                        'App Development',
                        'Finance',
                        'Human Resource',
                        'Product Management',
                        'Project Management',
                        'QA & Testing',
                        'Research & Development',
                        'Sales & Marketing',
                        'Software Development',
                        'System Administration'
                    ],
                    crosshair: true
                },
                exporting: { enabled: false },
                credits: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        pointWidth: 16,
                        borderWidth: 1
                    }
                },
    
                legend: {
                    enabled: true
                },
                series: [{
                    name: 'Total Requests',
                    data: [5, 5, 5, 8, 6, 6, 6, 10, 7, 6],
                    color: '#bf05ff'
                }, {
                    name: 'Requests Fulfiled',
                    data: [2, 4, 1, 5, 6, 3, 4, 8, 3, 6],
                    color: '#F79C92'
                }
                ]
            });
    //Requests Purpose	
    Highcharts.chart('RequestsPurpose', {
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: 0,
                    plotShadow: false,
                    height: 300
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
                        size: '70%'
                    }
                },
                series: [{
                    type: 'pie',
                    name: '',
                    innerSize: '50%',
                    data: [
                        ['Workload Management', 28.13],
                        ['Skill Oriented', 17.19],
                        ['Replacement', 54.69]
                    ]
                }]
            });
   
    //Supplier Wise Request
    Highcharts.chart('SupplierWiseRequest', {
    
                chart: {
                    type: 'column',
                    height: 300
                },
    
                title: {
                    text: ''
                },
    
                xAxis: {
                    categories: ['Cyber Tech Supplies', 'Networks Hardware', 'DataTech Equipment', 'ITPro Solutions', 'Digital Dynamics', 'TechSolution Inc', 'SysAdmin Supplies', 'Innovate IT Solutions', 'TechGeni Enterprises', 'CloudTech Technology']
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
                    },
                    series: {
                        stacking: 'normal',
                        dataLabels: {
                            enabled: true
                        }
                    }
                },
    
                series: [{
                    name: 'Normal',
                    data: [36, 36, 31, 21, 15, 21, 8, 11, 7],
                    color: '#CA7CE5'
                }, {
                    name: 'Urgent',
                    data: [74, 62, 54, 40, 35, 26, 34, 26, 23],
                    color: '#F79C92'
                }
    
                ]
            });
            
            
            
            //Supplier Success Rate
    Highcharts.chart('SupplierSuccessRate', {
                chart: {
                    type: 'bar',
                    height: 300,
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
                    text: ''
                },
                xAxis: {
                    categories: ['Dember 2023', 'January 2024', 'November 2023', 'October 2023', 'September 2023', 'February 2023'],
                    title: {
                        text: null
                    },
                    gridLineColor: 'transparent',
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    },
                    labels: {
                        enabled: false,
                    },
                    gridLineColor: 'transparent',
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: true,
                            format: '{y}%'
                        },
                        pointWidth: 20,
                        groupPadding: 0
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    data: [60, 70, 72, 80, 91, 100],
                    color: '#bf05ff'
                }]
            });
            
             
    // Request Rejection Reasons
    Highcharts.chart('RequestRejectionReasons', {
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
                    categories: [
                        'December',
                        'January',
                        'October',
                        'November',
                        'September',
                        'February'
                    ],
                    crosshair: true
                },
                exporting: { enabled: false },
                credits: {
                    enabled: false
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: ''
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        pointWidth: 14,
                        borderWidth: 1
                    }
                },
                series: [{
                    name: 'Alternative Solutions',
                    data: [16, 8, 9, 4, 7, 4],
                    color: '#bf05ff',
                    dataLabels: { enabled: true, }
                }, {
                    name: 'Budhet Constraints',
                    data: [14, 13, 14, 20, 11, 5],
                    color: '#CA7CE5',
                    dataLabels: { enabled: true, }
                },
                {
                    name: 'Vendor Issues',
                    data: [15, 12, 7, 5, 4, 8],
                    color: '#F79C92',
                    dataLabels: { enabled: true, }
                }
    
                ]
            });
   
   
   	 //Monthly Target Achievement
    Highcharts.chart('MonthlyTargetAchievement', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 250
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
                    text: ''
                },
                xAxis: {
                    categories: ['December 2023', 'February 2024', 'January 2024', 'November 2023', 'October 2023', 'September 2023'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [4.5, 1.5, 2.5, 1.8, 3.5, 4.9],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
            
            
            //=================================================end 1
   /*
	
	//alert("requisition js")
            // Compensation chart Start
            var chartData = {
                categories: ['IT', 'Marketing', 'Sales', 'Administration', 'Engineering'],
                seriesData: [{
                    name: 'IT',
                    data: [2],
                    color: '#51A4D6'
                }, {
                    name: 'Marketing',
                    data: [3],
                    color: '#DEAAF0'
                }, {
                    name: 'Sales',
                    data: [4],
                    color: '#9792E8'
                },
                {
                    name: 'Administration',
                    data: [6],
                    color: '#F58D68'
                },
                {
                    name: 'Engineering',
                    data: [10],
                    color: '#BF05FF'
                }
    
                ]
            };
            // Revenue by Channel
    
            var chartData1 = {
                categories: ['Flipkart', 'Amazon', 'Ebay', 'Nykaa', 'Own Website'],
                seriesData: [
                    {
                        name: 'Flipkart',
                        data: [12],
                        color: '#BF05FF'
                    }, {
                        name: 'Amazon',
                        data: [3],
                        color: '#DEAAF0'
                    }, {
                        name: 'Ebay',
                        data: [4],
                        color: '#9792E8'
    
                    },
                    {
                        name: 'Nykaa',
                        data: [6],
                        color: '#F58D68'
    
                    },
                    {
                        name: 'Own Website',
                        data: [10],
                        color: '#51A4D6'
    
                    }
    
                ]
    
            };
           
            //Request Approval Rate
            Highcharts.chart('RequestApprovalRate', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Project Management', 'Systems Administration', 'Finance', 'Sales & Marketing', 'Research & Development', 'Product Management', 'QA & Testing', 'Software Development', 'App Development', 'Human Resource'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [100, 100, 80, 80, 67, 63, 50, 43, 40, 20],
    
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
            //Urgent Requests Name
            Highcharts.chart('UrgentRequestsName', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Product Management', 'Research & Development', 'Project Management', 'QA & Testing', 'Human Resource', 'Sales & Marketing', 'Software Development', 'Finance', 'App Development', 'Systems Administration'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [5, 5, 4, 4, 3, 3, 3, 2, 1, 1],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
            //Avg Resolution Time
            Highcharts.chart('AvgResolutionTime', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Sales & Marketing', 'QA & Testing', 'Finance', 'System Administration', 'App Development', 'Research & Development', 'Human Resource ', 'Project Management', 'Product Management', 'Software Development'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [75, 69, 56, 53, 52, 50, 49, 46, 45, 41],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
          
            //Weekly Request Wow
    Highcharts.chart('WeeklyRequestWow', {
    
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
    
                plotOptions: {
                    column: {
                        groupPadding: 0
                    }
                },
    
                xAxis: [{
                    categories: ['September', 'October', 'November', 'December', 'January', 'February'],
                    min: 0
                }],
    
                yAxis: {
    
                    title: {
                        text: ''
                    },
                    labels: {
                        format: '{value}'
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
                    enabled: true
                },
    
                series: [{
                    name: 'Total Hirings',
                    data: [3, 11, 11, 10, 6, 3],
                    color: '#bf05ff'
                },
    
                {
                    name: 'WoW %',
                    type: 'line',
                    data: [3, 1, 2, 9, 2, 1],
                    color: '#F79C92'
                }]
    
            });
    //Daily Request Dod
    Highcharts.chart('DailyRequestDoD', {
    
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
    
                plotOptions: {
                    column: {
                        groupPadding: 0
                    }
                },
    
                xAxis: [{
                    categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17'],
                    min: 0
                }],
    
                yAxis: {
    
                    title: {
                        text: ''
                    },
                    labels: {
                        format: '{value}'
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
                    enabled: true
                },
    
                series: [{
                    name: 'Total Hirings',
                    data: [1, 1, 0, 3, 3, 0, 1, 1, 1, 0, 3, 2, 5, 2, 2, 2, 1],
                    color: '#bf05ff'
                },
    
                {
                    name: 'WoW %',
                    type: 'line',
                    data: [4, 2.8, 2.7, 0, 1, 1, 0, 3.5, 3.3, 3, 0, 0.5, 2, 0.5, 2, 1.8, 2.5],
                    color: '#F79C92'
                }]
    
            });
    
    //Request Resolution Rate
            Highcharts.chart('RequestResolutionRate', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Virtulaize Software', 'Version Control System', 'Testing Tools', 'Storage Devices', 'Software Development Kits', 'Servers', 'Remote Access Tools', 'Recruitment & Applicant Tracking System', 'Performance Management System', 'Mouse', 'Monitoring & Management Tools', 'Marketing Automatic Tools', 'Laptops', 'HR Management Software', 'Employee Engagement & Communicative Tools', 'Email Marketing Software', 'Desktop Computer', 'CRM', 'Collaboration Tools', 'Cable Management Tools', 'Analytics & Reporting Tools'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [91.1, 85, 93.6, 78.2, 91, 70.5, 91.7, 89, 87.5, 96.8, 95.2, 82.7, 89.7, 85.6, 83.1, 78.3, 82.6, 75.6, 91.7, 92.4, 78.6],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
    //Total Request Equipment
            Highcharts.chart('TotalRequestEquipment', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Virtulaize Software', 'Version Control System', 'Testing Tools', 'Storage Devices', 'Software Development Kits', 'Servers', 'Remote Access Tools', 'Recruitment & Applicant Tracking System', 'Performance Management System', 'Mouse', 'Monitoring & Management Tools', 'Marketing Automatic Tools', 'Laptops', 'HR Management Software', 'Employee Engagement & Communicative Tools', 'Email Marketing Software', 'Desktop Computer', 'CRM', 'Collaboration Tools', 'Cable Management Tools', 'Analytics & Reporting Tools'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [57, 24, 26, 28, 32, 29, 28, 51, 35, 39, 42, 48, 41, 40, 42, 57, 58, 43, 22, 28, 53],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
    //Urgent Request by Equipment
            Highcharts.chart('UrgentRequestbyEquipment', {
                chart: {
                    type: 'column',
                    animation: true,
                    height: 260
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
                    text: ''
                },
                xAxis: {
                    categories: ['Virtulaize Software', 'Version Control System', 'Testing Tools', 'Storage Devices', 'Software Development Kits', 'Servers', 'Remote Access Tools', 'Recruitment & Applicant Tracking System', 'Performance Management System', 'Mouse', 'Monitoring & Management Tools', 'Marketing Automatic Tools', 'Laptops', 'HR Management Software', 'Employee Engagement & Communicative Tools', 'Email Marketing Software', 'Desktop Computer', 'CRM', 'Collaboration Tools', 'Cable Management Tools', 'Analytics & Reporting Tools'],
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                yAxis: {
                    gridLineColor: 'transparent',
                    title: {
                        text: ''
                    },
                    labels: {
                        style: {
                            fontSize: '10px',
                        }
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
                    groupPadding: 0,
                    data: [8, 5, 5, 7, 5, 5, 6, 8, 7, 6, 6, 6, 7, 9, 6, 8, 9, 5, 5, 7, 8],
                    color: '#bf05ff',
                    dataLabels: {
                        enabled: true,
                    }
                }]
            });
  
    //Weekly Request Wow
    Highcharts.chart('WeeklyWow', {
    
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
    
                plotOptions: {
                    column: {
                        groupPadding: 0
                    }
                },
    
                xAxis: [{
                    categories: ['37', '38', '39', '40', '41', '42', '43', '44', '45', '46', '47', '48', '49', '50', '51', '52', '53', '1', '2', '3', '4', '5', '6', '7'],
                    min: 0
                }],
    
                yAxis: {
    
                    title: {
                        text: ''
                    },
                    labels: {
                        format: '{value}'
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
                    enabled: true
                },
    
                series: [{
                    name: 'Total Hirings',
                    data: [29, 47, 45, 28, 27, 25, 34, 35, 35, 34, 34, 37, 41, 30, 34, 45, 3, 33, 33, 41, 39, 32, 40, 42],
                    color: '#bf05ff'
                },
    
                {
                    name: 'WoW %',
                    type: 'line',
                    data: [0, 15, 12, 10, 12, 12, 13, 11, 12, 12, 12, 12, 10, 11, 13, 11, 35, 10, 11, 12, 12, 13, 9],
                    color: '#F79C92'
                }]
    
            });
    //Daily Request Dod
     Highcharts.chart('DailyDoD', {
    
                chart: {
                    type: 'column',
                    height: 250
                },
                title: {
                    text: ''
                },
    
                plotOptions: {
                    column: {
                        groupPadding: 0
                    }
                },
    
                xAxis: [{
                    categories: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17'],
                    min: 0
                }],
    
                yAxis: {
    
                    title: {
                        text: ''
                    },
                    labels: {
                        format: '{value}'
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
                    enabled: true
                },
                series: [{
                    name: 'Total Hirings',
                    data: [4, 5, 3, 5, 3, 5, 4, 5, 5, 10, 7, 5, 10, 7, 5, 9, 6],
                    color: '#B422B6'
                },
    
                {
                    name: 'WoW %',
                    type: 'line',
                    data: [2, 3, 2, 3.5, 2, 4, 2, 2.5, 2, 3, 2.5, 2, 1.8, 2, 2.5, 2, 1.5],
                    color: '#F79C92'
                }]
    
            });
   
   
   */
    
}