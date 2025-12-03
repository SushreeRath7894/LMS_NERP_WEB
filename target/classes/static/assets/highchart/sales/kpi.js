/*=====================================OnClickOnKPI Start===========================================*/
function kpiHighChart(){		
	var financialYear=$("#orderStatusFilter").val();
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
    var toYear = dateRange[1]	
    
    var fromDate =fromYear+"-04-01";
	var toDate =toYear+"-03-31";				
	
	var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    var day = currentDate.getDate().toString().padStart(2, '0');
    currentDate = year + '-' + month + '-' + day;
    
    var aprStart=fromYear+"-04-01";  var aprEnd=fromYear+"-04-30";
    var mayStart=fromYear+"-05-01";  var mayEnd=fromYear+"-05-31";
    var junStart=fromYear+"-06-01";  var junEnd=fromYear+"-06-30";
    var julStart=fromYear+"-07-01";  var julEnd=fromYear+"-07-31";
    var augStart=fromYear+"-08-01";  var augEnd=fromYear+"-08-31";
    var sepStart=fromYear+"-09-01";  var sepEnd=fromYear+"-09-30";
    var octStart=fromYear+"-10-01";  var octEnd=fromYear+"-10-31";
    var novStart=fromYear+"-11-01";  var novEnd=fromYear+"-11-30";
    var decStart=fromYear+"-12-01";  var decEnd=fromYear+"-12-31";
    var janStart=toYear+"-01-01";  var janEnd=toYear+"-01-31";
    var febStart=toYear+"-02-01";  var marchStart=toYear+"-03-01";
    var marchStart=toYear+"-03-01";  var marchEnd=toYear+"-03-31";	
	
	$.ajax({
    type: "GET",
    url : "customer-dashboard-salesRevenue?fromYear=" + fromYear + "&toYear=" + toYear,
    async: true,
    success: function (response) {
        if (response.code === "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.AllData;  
            // Create an array to store data for Highcharts
            	var allData = jsonData.revenueVsCrossSale;
									
				var monthDatas=[];
				monthDatas.push(allData[0].first)
				monthDatas.push(allData[0].second)
				monthDatas.push(allData[0].third)
				monthDatas.push(allData[0].forth)
				monthDatas.push(allData[0].fifth)
				monthDatas.push(allData[0].sixth)
				monthDatas.push(allData[0].seventh)
				monthDatas.push(allData[0].eighth)
				monthDatas.push(allData[0].ninth)
				monthDatas.push(allData[0].tenth)
				monthDatas.push(allData[0].eleventh)
				monthDatas.push(allData[0].twelveth)
				
			    var revenueDatas=[];
			    var crossDatas =[];
			    if (new Date(currentDate) >= new Date(fromDate)) {
				

					if (new Date(currentDate) >= new Date(aprStart) && new Date(currentDate) <= new Date(aprEnd)) {
						revenueDatas.push(allData[0].Apr01Revenue)
						crossDatas.push(allData[0].Apr01CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(mayStart) && new Date(currentDate) <= new Date(mayEnd)) {
					    revenueDatas.push(allData[0].May02Revenue)
					    crossDatas.push(allData[0].May02CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(junStart) && new Date(currentDate) <= new Date(junEnd)) {
					   revenueDatas.push(allData[0].Jun03Revenue)
					   crossDatas.push(allData[0].Jun03CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(julStart) && new Date(currentDate) <= new Date(julEnd)) {
					   revenueDatas.push(allData[0].Jul04Revenue)
					   crossDatas.push(allData[0].Jul04CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(augStart) && new Date(currentDate) <= new Date(augEnd)) {
					    revenueDatas.push(allData[0].Aug05Revenue)
					    crossDatas.push(allData[0].Aug05CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(sepStart) && new Date(currentDate) <= new Date(sepEnd)) {
					    revenueDatas.push(allData[0].Sep06Revenue)
					    crossDatas.push(allData[0].Sep06CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(octStart) && new Date(currentDate) <= new Date(octEnd)) {
					    revenueDatas.push(allData[0].Oct07Revenue)
					    crossDatas.push(allData[0].Oct07CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(novStart) && new Date(currentDate) <= new Date(novEnd)) {
					    revenueDatas.push(allData[0].Nov08Revenue)
					    crossDatas.push(allData[0].Nov08CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(decStart) && new Date(currentDate) <= new Date(decEnd)) {
					    revenueDatas.push(allData[0].Dec09Revenue)
					    crossDatas.push(allData[0].Dec09CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}					
					
					if (new Date(currentDate) >= new Date(janStart) && new Date(currentDate) <= new Date(janEnd)) {
					    revenueDatas.push(allData[0].Jan10Revenue)
					    crossDatas.push(allData[0].Jan10CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					if (new Date(currentDate) >= new Date(febStart) && new Date(currentDate) < new Date(marchStart)) {
					    revenueDatas.push(allData[0].feb11Revenue)
					    crossDatas.push(allData[0].feb11CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(marchStart) && new Date(currentDate) <= new Date(marchEnd)) {
					    revenueDatas.push(allData[0].Mar12Revenue)
					    crossDatas.push(allData[0].Mar12CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					 
					
				   }else{
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
				}
				
				if (new Date(toDate) <= new Date(currentDate)) {
					revenueDatas = [];
					revenueDatas.push(allData[0].Apr01Revenue)
					revenueDatas.push(allData[0].May02Revenue)
					revenueDatas.push(allData[0].Jun03Revenue)
					revenueDatas.push(allData[0].Jul04Revenue)
					revenueDatas.push(allData[0].Aug05Revenue)
					revenueDatas.push(allData[0].Sep06Revenue)
					revenueDatas.push(allData[0].Oct07Revenue)
					revenueDatas.push(allData[0].Nov08Revenue)
					revenueDatas.push(allData[0].Dec09Revenue)
					revenueDatas.push(allData[0].Jan10Revenue)
					revenueDatas.push(allData[0].feb11Revenue)
					revenueDatas.push(allData[0].Mar12Revenue)
					
					
					crossDatas = [];					
					crossDatas.push(allData[0].Apr01CrossSaleRevenue)
					crossDatas.push(allData[0].May02CrossSaleRevenue)
					crossDatas.push(allData[0].May03CrossSaleRevenue)
					crossDatas.push(allData[0].May04CrossSaleRevenue)
					crossDatas.push(allData[0].May05CrossSaleRevenue)
					crossDatas.push(allData[0].May06CrossSaleRevenue)
					crossDatas.push(allData[0].May07CrossSaleRevenue)
					crossDatas.push(allData[0].May08CrossSaleRevenue)
					crossDatas.push(allData[0].May09CrossSaleRevenue)
					crossDatas.push(allData[0].May10CrossSaleRevenue)
					crossDatas.push(allData[0].May11CrossSaleRevenue)
					crossDatas.push(allData[0].May12CrossSaleRevenue)
				}
				
				
				console.log("revenueDatas----------------"+revenueDatas);
				console.log("crossDatas------------------"+crossDatas);

     Highcharts.chart('salesRevenue', {
                chart: {
                    type: 'area',
                    backgroundColor: 'transparent',
                    height: 200
                },
                credits: false,
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
               // categories: ['Apr 2024','May 2024','Jun 2024','Jul 2024','Aug 2024','Sep 2024','Oct 2024','Nov 2024','Dec 2024','Jan 2025','Feb 2025','Mar 2025'],
               	categories:monthDatas,
                labels: {
                    style: {
                        fontSize: '10px',
                    }
                }
            },
                yAxis: {
                    title: {
                        useHTML: true,
                        text: 'INR'
                    }
                },
                tooltip: {
                    shared: true,
                    headerFormat: '<span style="font-size:12px"><b>{point.key}</b></span><br>'
                },
                plotOptions: {
                    area: {
                        stacking: 'normal',
                        lineColor: 'transparent',
                        lineWidth: 0,
                        marker: {
                            lineWidth: 1,
                            lineColor: 'transparent'
                        }
                    },
                    series: {
				    marker: {
				      enabled: false
				    }
				  }
                },
                navigation: {
			        buttonOptions: {
			            enabled: false
			        }
			    },
                series: [
					 {
                    name: 'Sales Revenue',                  
                    data: revenueDatas,
                    //data: [80,90,101,102,120,130,106,100,160,100,105,190],
                    color: '#15807E'
                },
					{
                    name: 'Up/Cross Sales',
                    data : crossDatas,
                    //data: [120,140,155,165,185,195,235,255,265,240,271,299],
                    color: '#00FF00'
                }
               
                       
                ]
            });
            

        }
    },
    error: function (data) {
        console.log(data);
    }
});

			       /* }	}
						},error	: function(data){
							console.log(data);	
						}
					})
					*/
					


			   $.ajax({
					type	: "GET",
					url : "customer-dashboard-KpiHeadData?fromDate=" + fromDate + "&toDate=" + toDate,
					async:true,
					success	: function(response){
						
						if(response.code=="success"){
							//alert(response.code=="success")
							var jsonData = JSON.parse(response.body);
							var allData=jsonData.AllData;
							
							
							
							var profitTotal = allData[0].profit;//profit
				            
				            var purchaseAmount = allData[0].purchaseAmount;
				            var noOfPurchase = allData[0].noOfPurchase;
				            
				            var revenueAmount = allData[0].revenueAmount;////revenueAmount
				            var noOfSales = allData[0].noOfSales;/////number_of_sales
				            
				            var cost = allData[0].costAmount;//cost
				            var marketingCostBreakup = allData[0].marketingCostBreakup;///Product
				            var marketingSalesBreakup = allData[0].marketingSalesBreakup;//Service
				            
				            var upCrossRevenueAmount = allData[0].upCrossRevenueAmount;//revenuesales
				            var upCrossPercentageRevenue = allData[0].upCrossPercentageRevenue;//percentageofrevenue
				            
				            
				            var churnTotalCount = allData[0].churnTotalCount;//total
				            var churnRate = allData[0].churnRate;//totalrate
				            var churnTotalRevenueAmount = allData[0].churnTotalRevenueAmount;///totalrev
				           
							
							$("#number_of_sales").text(noOfSales);
							$("#revenueAmount").text(parseFloat(revenueAmount).toFixed(2));				
							$("#profit").text(parseFloat(profitTotal).toFixed(2));
							$("#cost").text(parseFloat(cost).toFixed(2));
							
							$("#marketingCost").text(parseFloat(marketingCostBreakup).toFixed(2));
							$("#marketingSales").text(parseFloat(marketingSalesBreakup).toFixed(2));
							
							$("#upCrossPercentageRevenue").text(upCrossPercentageRevenue);
							$("#upCrossRevenueAmount").text(parseFloat(upCrossRevenueAmount).toFixed(2));
							
							
							$("#churnTotalCount").text(churnTotalCount);						
							$("#churnRate").text(churnRate);
							$("#churnTotalRevenueAmount").text(parseFloat(churnTotalRevenueAmount).toFixed(2));
							
							 Highcharts.chart('costBreakdown', {
						            chart: {
						                type: 'pie',
						                animation: true,
						                backgroundColor: 'transparent',
						                height: 130,
						                margin: 0
						            },
						            title: {
						                text: '',
						            },
						            subtitle: {
						                text: ''
						            },
						            credits: {
						                enabled: false
						            },
						
						            plotOptions: {
						                pie: {
						                    dataLabels: {
						                        distance: '-30%'
						                    },
						                    colors: [
						                        '#005594',
						                        '#a6c4fc',
						                    ],
						                    innerSize: '30%'
						                }
						            },
						
						            series: [{
						                data: [
						                    ['Sales', 33],
						                    ['Marketing', 67],
						                ]
						            }]
						        });
									                  
						}
				},error	: function(data){
					console.log(data);	
				}
			});		
       
 //incremental sales
 $.ajax({
    type: "GET",
    url: "customer-dashboard-incrementalSales",
    async: true,
    success: function (response) {
        if (response.code == "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.AllData;

            var categories = [];
            var seriesData = [];

            for (var i = 0; i < allData.length; i++) {
                categories.push(allData[i].saleInv_creation);
                seriesData.push(allData[i].total_saleInv);
            }

            
		     Highcharts.chart('incrementalSales', {
		     chart: {
		        type: 'bar',
		        animation: true,
		        backgroundColor: 'transparent',
		        height: 280,
		    },
		    title: {
		        text: '',
		    },
		    subtitle: {
		        text: ''
		    },
		    credits: {
		        enabled: false
		    },
		    xAxis: {
		        categories: ['Email','Facebook','GDN','Google Ads Search','Instagram','Twitter'],
		        crosshair: true,
		        lineColor: 'transparent',
		        title: {
		            text: null
		        },
		        labels: {
		                    enabled: false,
		                }
		        
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: 'INR'
		        }
		    },
		    legend: {
		        verticalAlign: 'top',
		    },
		    plotOptions: {
		        column: {
		            pointPadding: 0.2,
		            borderWidth: 0
		        }
		    },
		    navigation: {
		        buttonOptions: {
		            enabled: false
		        }
		    },
		    series: [
		        {
		            name: 'Email',
		            data: [10],
		            color: '#5c8ad4'
		        },
		        {
		            name: 'Facebook',
		            data: [20],
		            color: '#15807E'
		        },
		        {
		            name: 'GDN',
		            data: [9],
		            color: '#0099ff'
		        },
		        {
		            name: 'Google Ads Search',
		            data: [29],
		            color: '#e6e6e6'
		        },
		        {
		            name: 'Instagram',
		            data: [4],
		            color: '#00ff00'
		        },
		        {
		            name: 'Twitter',
		            data: [14],
		            color: '#993300'
		        }      
		    ]
		});
        }
    },
    error: function (data) {
        console.log(data);
    }
});


var thisFromYear = parseInt(fromYear);    
var thisToYear = parseInt(toYear); 

var lastFromYear = (thisFromYear - 1);
var lastToYear = (thisToYear - 1);
	    
			   
$.ajax({
    type: "GET",
    url : "customer-dashboard-accumulated-revenue?currentFromYear=" + thisFromYear + "&currentToYear=" + thisToYear+
     "&lastFromYear=" +lastFromYear+ "&lastToYear=" + lastToYear,
    async: true,
    success: function (response) {
        if (response.code == "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.accumulatedRevenue;

          
          	var previousRevenue=allData[0].previousRevenue;
          	var currentRevenue=allData[0].currentRevenue;
          	var newRevenue=allData[0].newRevenue;
          	var crossSalesRevenue=allData[0].upsaleRevenue;
          	var lostRevenue=allData[0].lostRevenue;


			Highcharts.chart('accumulatedRevenueKPI', {
	            chart: {
	                type: 'waterfall',
	                backgroundColor: 'transparent',
	                height: 210
	            },			
	            title: {
	                text: '',
	            },
	            subtitle: {
	                text: ''
	            },
	            credits: {
	                enabled: false
	            },
	            xAxis: {
	                type: 'category'
	            },	
	            yAxis: {
	                title: {
	                    text: 'INR'
	                }
	            },
	
	            legend: {
	                enabled: false
	            },
	
	            tooltip: {
	                pointFormat: '<b>${point.y:,.2f}</b> USD'
	            },
	
	            series: [{
	                upColor: '#1e81b0',
	                color: '#1e81b0',
	                groupPadding: 0,
	                pointWidth: 13,
	                data: [{
	                    name: 'Previous Revenue',
	                    y: previousRevenue
	                }, {
	                    name: 'New Revenue',
	                    y: newRevenue
	                }, {
	                    name: 'Upsell Revenue',
	                    y: crossSalesRevenue
	                },{
	                    name: 'Loss Revenue',
	                    y: lostRevenue
	                },  {
	                    name: 'Current Revenue',
	                    y: currentRevenue
	                }],
	                dataLabels: {
	                    enabled: true,
	                    format: '{divide y 1000}k'
	                },
	                pointPadding: 0
	            }]
	        });

		    
        }
    },
    error: function (data) {
        console.log(data);
    }
});
			   
			   
			   
}
   /*=====================================OnClickOnKPI End===========================================*/   
   
   /*=============================OnClickOnKPI Datewise Search Start==================================*/    
          
    function kpiHighChartOnSearchBtn(){
	
	var financialYear=$("#orderStatusFilter").val();
	var dateRange = financialYear.split('-');
	var fromYear = dateRange[0];
    var toYear = dateRange[1]	
    
    var fromDate =fromYear+"-04-01";
	var toDate =toYear+"-03-31";				
	
	var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = (currentDate.getMonth() + 1).toString().padStart(2, '0');
    var day = currentDate.getDate().toString().padStart(2, '0');
    currentDate = year + '-' + month + '-' + day;
    
    var aprStart=fromYear+"-04-01";  var aprEnd=fromYear+"-04-30";
    var mayStart=fromYear+"-05-01";  var mayEnd=fromYear+"-05-31";
    var junStart=fromYear+"-06-01";  var junEnd=fromYear+"-06-30";
    var julStart=fromYear+"-07-01";  var julEnd=fromYear+"-07-31";
    var augStart=fromYear+"-08-01";  var augEnd=fromYear+"-08-31";
    var sepStart=fromYear+"-09-01";  var sepEnd=fromYear+"-09-30";
    var octStart=fromYear+"-10-01";  var octEnd=fromYear+"-10-31";
    var novStart=fromYear+"-11-01";  var novEnd=fromYear+"-11-30";
    var decStart=fromYear+"-12-01";  var decEnd=fromYear+"-12-31";
    var janStart=toYear+"-01-01";  var janEnd=toYear+"-01-31";
    var febStart=toYear+"-02-01";  var marchStart=toYear+"-03-01";
    var marchStart=toYear+"-03-01";  var marchEnd=toYear+"-03-31";	
	
	$.ajax({
    type: "GET",
    url : "customer-dashboard-salesRevenue?fromYear=" + fromYear + "&toYear=" + toYear,
    async: true,
    success: function (response) {
        if (response.code === "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.AllData;  
            // Create an array to store data for Highcharts
            	var allData = jsonData.revenueVsCrossSale;
									
				var monthDatas=[];
				monthDatas.push(allData[0].first)
				monthDatas.push(allData[0].second)
				monthDatas.push(allData[0].third)
				monthDatas.push(allData[0].forth)
				monthDatas.push(allData[0].fifth)
				monthDatas.push(allData[0].sixth)
				monthDatas.push(allData[0].seventh)
				monthDatas.push(allData[0].eighth)
				monthDatas.push(allData[0].ninth)
				monthDatas.push(allData[0].tenth)
				monthDatas.push(allData[0].eleventh)
				monthDatas.push(allData[0].twelveth)
				
			    var revenueDatas=[];
			    var crossDatas =[];
			    if (new Date(currentDate) >= new Date(fromDate)) {
				

					if (new Date(currentDate) >= new Date(aprStart) && new Date(currentDate) <= new Date(aprEnd)) {
						revenueDatas.push(allData[0].Apr01Revenue)
						crossDatas.push(allData[0].Apr01CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(mayStart) && new Date(currentDate) <= new Date(mayEnd)) {
					    revenueDatas.push(allData[0].May02Revenue)
					    crossDatas.push(allData[0].May02CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(junStart) && new Date(currentDate) <= new Date(junEnd)) {
					   revenueDatas.push(allData[0].Jun03Revenue)
					   crossDatas.push(allData[0].Jun03CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(julStart) && new Date(currentDate) <= new Date(julEnd)) {
					   revenueDatas.push(allData[0].Jul04Revenue)
					   crossDatas.push(allData[0].Jul04CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(augStart) && new Date(currentDate) <= new Date(augEnd)) {
					    revenueDatas.push(allData[0].Aug05Revenue)
					    crossDatas.push(allData[0].Aug05CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(sepStart) && new Date(currentDate) <= new Date(sepEnd)) {
					    revenueDatas.push(allData[0].Sep06Revenue)
					    crossDatas.push(allData[0].Sep06CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(octStart) && new Date(currentDate) <= new Date(octEnd)) {
					    revenueDatas.push(allData[0].Oct07Revenue)
					    crossDatas.push(allData[0].Oct07CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(novStart) && new Date(currentDate) <= new Date(novEnd)) {
					    revenueDatas.push(allData[0].Nov08Revenue)
					    crossDatas.push(allData[0].Nov08CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(decStart) && new Date(currentDate) <= new Date(decEnd)) {
					    revenueDatas.push(allData[0].Dec09Revenue)
					    crossDatas.push(allData[0].Dec09CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}					
					
					if (new Date(currentDate) >= new Date(janStart) && new Date(currentDate) <= new Date(janEnd)) {
					    revenueDatas.push(allData[0].Jan10Revenue)
					    crossDatas.push(allData[0].Jan10CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					if (new Date(currentDate) >= new Date(febStart) && new Date(currentDate) < new Date(marchStart)) {
					    revenueDatas.push(allData[0].feb11Revenue)
					    crossDatas.push(allData[0].feb11CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					
					if (new Date(currentDate) >= new Date(marchStart) && new Date(currentDate) <= new Date(marchEnd)) {
					    revenueDatas.push(allData[0].Mar12Revenue)
					    crossDatas.push(allData[0].Mar12CrossSaleRevenue)
					}else{
						revenueDatas.push(0)
						crossDatas.push(0)
					}
					 
					
				   }else{
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					revenueDatas.push(0)
					
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
					crossDatas.push(0)
				}
				
				if (new Date(toDate) <= new Date(currentDate)) {
					revenueDatas = [];
					revenueDatas.push(allData[0].Apr01Revenue)
					revenueDatas.push(allData[0].May02Revenue)
					revenueDatas.push(allData[0].Jun03Revenue)
					revenueDatas.push(allData[0].Jul04Revenue)
					revenueDatas.push(allData[0].Aug05Revenue)
					revenueDatas.push(allData[0].Sep06Revenue)
					revenueDatas.push(allData[0].Oct07Revenue)
					revenueDatas.push(allData[0].Nov08Revenue)
					revenueDatas.push(allData[0].Dec09Revenue)
					revenueDatas.push(allData[0].Jan10Revenue)
					revenueDatas.push(allData[0].feb11Revenue)
					revenueDatas.push(allData[0].Mar12Revenue)
					
					
					crossDatas = [];					
					crossDatas.push(allData[0].Apr01CrossSaleRevenue)
					crossDatas.push(allData[0].May02CrossSaleRevenue)
					crossDatas.push(allData[0].May03CrossSaleRevenue)
					crossDatas.push(allData[0].May04CrossSaleRevenue)
					crossDatas.push(allData[0].May05CrossSaleRevenue)
					crossDatas.push(allData[0].May06CrossSaleRevenue)
					crossDatas.push(allData[0].May07CrossSaleRevenue)
					crossDatas.push(allData[0].May08CrossSaleRevenue)
					crossDatas.push(allData[0].May09CrossSaleRevenue)
					crossDatas.push(allData[0].May10CrossSaleRevenue)
					crossDatas.push(allData[0].May11CrossSaleRevenue)
					crossDatas.push(allData[0].May12CrossSaleRevenue)
				}
				
				
				console.log("revenueDatas----------------"+revenueDatas);
				console.log("crossDatas------------------"+crossDatas);

     Highcharts.chart('salesRevenue', {
                chart: {
                    type: 'area',
                    backgroundColor: 'transparent',
                    height: 200
                },
                credits: false,
                title: {
                    text: ''
                },
                subtitle: {
                    text: ''
                },
                xAxis: {
               // categories: ['Apr 2024','May 2024','Jun 2024','Jul 2024','Aug 2024','Sep 2024','Oct 2024','Nov 2024','Dec 2024','Jan 2025','Feb 2025','Mar 2025'],
               	categories:monthDatas,
                labels: {
                    style: {
                        fontSize: '10px',
                    }
                }
            },
                yAxis: {
                    title: {
                        useHTML: true,
                        text: 'INR'
                    }
                },
                tooltip: {
                    shared: true,
                    headerFormat: '<span style="font-size:12px"><b>{point.key}</b></span><br>'
                },
                plotOptions: {
                    area: {
                        stacking: 'normal',
                        lineColor: 'transparent',
                        lineWidth: 0,
                        marker: {
                            lineWidth: 1,
                            lineColor: 'transparent'
                        }
                    },
                    series: {
				    marker: {
				      enabled: false
				    }
				  }
                },
                navigation: {
			        buttonOptions: {
			            enabled: false
			        }
			    },
                series: [
					 {
                    name: 'Sales Revenue',                  
                    data: revenueDatas,
                    //data: [80,90,101,102,120,130,106,100,160,100,105,190],
                    color: '#15807E'
                },
					{
                    name: 'Up/Cross Sales',
                    data : crossDatas,
                    //data: [120,140,155,165,185,195,235,255,265,240,271,299],
                    color: '#00FF00'
                }
               
                       
                ]
            });
            

        }
    },
    error: function (data) {
        console.log(data);
    }
});

			       /* }	}
						},error	: function(data){
							console.log(data);	
						}
					})
					*/
					


			   $.ajax({
					type	: "GET",
					url : "customer-dashboard-KpiHeadData?fromDate=" + fromDate + "&toDate=" + toDate,
					async:true,
					success	: function(response){
						
						if(response.code=="success"){
							//alert(response.code=="success")
							var jsonData = JSON.parse(response.body);
							var allData=jsonData.AllData;
							
							
							
							var profitTotal = allData[0].profit;//profit
				            
				            var purchaseAmount = allData[0].purchaseAmount;
				            var noOfPurchase = allData[0].noOfPurchase;
				            
				            var revenueAmount = allData[0].revenueAmount;////revenueAmount
				            var noOfSales = allData[0].noOfSales;/////number_of_sales
				            
				            var cost = allData[0].costAmount;//cost
				            var marketingCostBreakup = allData[0].marketingCostBreakup;///Product
				            var marketingSalesBreakup = allData[0].marketingSalesBreakup;//Service
				            
				            var upCrossRevenueAmount = allData[0].upCrossRevenueAmount;//revenuesales
				            var upCrossPercentageRevenue = allData[0].upCrossPercentageRevenue;//percentageofrevenue
				            
				            
				            var churnTotalCount = allData[0].churnTotalCount;//total
				            var churnRate = allData[0].churnRate;//totalrate
				            var churnTotalRevenueAmount = allData[0].churnTotalRevenueAmount;///totalrev
				           
							
							$("#number_of_sales").text(noOfSales);
							$("#revenueAmount").text("₹"+parseFloat(revenueAmount).toFixed(2));				
							$("#profit").text("₹"+parseFloat(profitTotal).toFixed(2));
							$("#cost").text("₹"+parseFloat(cost).toFixed(2));
							
							$("#marketingCost").text("₹"+parseFloat(marketingCostBreakup).toFixed(2));
							$("#marketingSales").text("₹"+parseFloat(marketingSalesBreakup).toFixed(2));
							
							$("#upCrossPercentageRevenue").text(upCrossPercentageRevenue);
							$("#upCrossRevenueAmount").text("₹"+parseFloat(upCrossRevenueAmount).toFixed(2));
							
							
							$("#churnTotalCount").text(churnTotalCount);						
							$("#churnRate").text(churnRate);
							$("#churnTotalRevenueAmount").text("₹"+parseFloat(churnTotalRevenueAmount).toFixed(2));
							
							 Highcharts.chart('costBreakdown', {
						            chart: {
						                type: 'pie',
						                animation: true,
						                backgroundColor: 'transparent',
						                height: 130,
						                margin: 0
						            },
						            title: {
						                text: '',
						            },
						            subtitle: {
						                text: ''
						            },
						            credits: {
						                enabled: false
						            },
						
						            plotOptions: {
						                pie: {
						                    dataLabels: {
						                        distance: '-30%'
						                    },
						                    colors: [
						                        '#005594',
						                        '#a6c4fc',
						                    ],
						                    innerSize: '30%'
						                }
						            },
						
						            series: [{
						                data: [
						                    ['Sales', 33],
						                    ['Marketing', 67],
						                ]
						            }]
						        });
									                  
						}
				},error	: function(data){
					console.log(data);	
				}
			});

					
					
					
					
       
 //incremental sales
 $.ajax({
    type: "GET",
    url: "customer-dashboard-incrementalSales",
    async: true,
    success: function (response) {
        if (response.code == "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.AllData;

            var categories = [];
            var seriesData = [];

            for (var i = 0; i < allData.length; i++) {
                categories.push(allData[i].saleInv_creation);
                seriesData.push(allData[i].total_saleInv);
            }

            
		     Highcharts.chart('incrementalSales', {
		     chart: {
		        type: 'bar',
		        animation: true,
		        backgroundColor: 'transparent',
		        height: 280,
		    },
		    title: {
		        text: '',
		    },
		    subtitle: {
		        text: ''
		    },
		    credits: {
		        enabled: false
		    },
		    xAxis: {
		        categories: ['Email','Facebook','GDN','Google Ads Search','Instagram','Twitter'],
		        crosshair: true,
		        lineColor: 'transparent',
		        title: {
		            text: null
		        },
		        labels: {
		                    enabled: false,
		                }
		        
		    },
		    yAxis: {
		        min: 0,
		        title: {
		            text: 'INR'
		        }
		    },
		    legend: {
		        verticalAlign: 'top',
		    },
		    plotOptions: {
		        column: {
		            pointPadding: 0.2,
		            borderWidth: 0
		        }
		    },
		    navigation: {
		        buttonOptions: {
		            enabled: false
		        }
		    },
		    series: [
		        {
		            name: 'Email',
		            data: [10],
		            color: '#5c8ad4'
		        },
		        {
		            name: 'Facebook',
		            data: [20],
		            color: '#15807E'
		        },
		        {
		            name: 'GDN',
		            data: [9],
		            color: '#0099ff'
		        },
		        {
		            name: 'Google Ads Search',
		            data: [29],
		            color: '#e6e6e6'
		        },
		        {
		            name: 'Instagram',
		            data: [4],
		            color: '#00ff00'
		        },
		        {
		            name: 'Twitter',
		            data: [14],
		            color: '#993300'
		        }      
		    ]
		});
        }
    },
    error: function (data) {
        console.log(data);
    }
});
			   
			   
			   
var thisFromYear = parseInt(fromYear);    
var thisToYear = parseInt(toYear); 

var lastFromYear = (thisFromYear - 1);
var lastToYear = (thisToYear - 1);
	    
			   
$.ajax({
    type: "GET",
    url : "customer-dashboard-accumulated-revenue?currentFromYear=" + thisFromYear + "&currentToYear=" + thisToYear+
     "&lastFromYear=" +lastFromYear+ "&lastToYear=" + lastToYear,
    async: true,
    success: function (response) {
        if (response.code == "success") {
            var jsonData = JSON.parse(response.body);
            var allData = jsonData.accumulatedRevenue;

          
          	var previousRevenue=allData[0].previousRevenue;
          	var currentRevenue=allData[0].currentRevenue;
          	var newRevenue=allData[0].newRevenue;
          	var crossSalesRevenue=allData[0].upsaleRevenue;
          	var lostRevenue=allData[0].lostRevenue;
          	
          	


			Highcharts.chart('accumulatedRevenueKPI', {
	            chart: {
	                type: 'waterfall',
	                backgroundColor: 'transparent',
	                height: 210
	            },			
	            title: {
	                text: '',
	            },
	            subtitle: {
	                text: ''
	            },
	            credits: {
	                enabled: false
	            },
	            xAxis: {
	                type: 'category'
	            },	
	            yAxis: {
	                title: {
	                    text: 'INR'
	                }
	            },
	
	            legend: {
	                enabled: false
	            },
	
	            tooltip: {
	                pointFormat: '<b>${point.y:,.2f}</b> USD'
	            },
	
	            series: [{
	                upColor: '#1e81b0',
	                color: '#1e81b0',
	                groupPadding: 0,
	                pointWidth: 13,
	                data: [{
	                    name: 'Previous Revenue',
	                    y: previousRevenue
	                }, {
	                    name: 'New Revenue',
	                    y: newRevenue
	                }, {
	                    name: 'Upsell Revenue',
	                    y: crossSalesRevenue
	                },{
	                    name: 'Loss Revenue',
	                    y: lostRevenue
	                },  {
	                    name: 'Current Revenue',
	                    y: currentRevenue
	                }],
	                dataLabels: {
	                    enabled: true,
	                    format: '{divide y 1000}k'
	                },
	                pointPadding: 0
	            }]
	        });

		    
        }
    },
    error: function (data) {
        console.log(data);
    }
});
          }
   /*=============================OnClickOnKPI Datewise Search End==================================*/           