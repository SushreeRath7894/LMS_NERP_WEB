function controlHighChat(){
var org = $("#controlOrganization").val();
var orgDiv = $("#controlDivision").find('option:selected').text();
var location = $("#controlLocation").find('option:selected').text();
var fromDate = $("#fromDate7").val();
var toDate = $("#toDate7").val();
//dashboard-control-count1
$.ajax({
		url: "dashboard-control-cycle-yield-throughput-workforce-lead-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var yield = 0;
			var cycleTime = 0;
			var throughput = 0;
			var leadTime = 0;
			var workforceProductivity = 0;
			
			
			yield = allData[0].yield;
			cycleTime = allData[0].cycleTime;
			throughput = allData[0].throughput;
			leadTime = allData[0].leadTime;	
			workforceProductivity = allData[0].workforceProductivity;	
			
			$("#total_yield").text(yield + ' mins');
			$("#total_cycle_time").text(cycleTime + ' %');
			$("#total_throughput").text(throughput);
			$("#lead_time").text(leadTime + ' Units per labour hours');
			$("#workforce_productivity").text(workforceProductivity + ' Day(s)');
			
		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	 const currentDate = new Date();

    // Current month and year
    const currentMonth = currentDate.getMonth() + 1; // Months are zero-indexed
    const currentYear = currentDate.getFullYear();
   // alert("Current Month: " + currentMonth + ", Current Year: " + currentYear);

    // Last month and year
    let lastMonth = currentMonth - 1;
    let lastYear = currentYear;

    if (lastMonth === 0) {
        lastMonth = 12; // December of the previous year
        lastYear -= 1;
    }

   // currentMonth  currentYear    lastMonth    lastYear
	
	$.ajax({
		url: "dashboard-qty-rework-qty-production-order-qty-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv + "&currentMonth=" + currentMonth
		+ "&currentYear=" + currentYear + "&lastMonth=" + lastMonth+ "&lastYear=" + lastYear,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
		
			var qtyProduced = 0;
			var qtyProducedThisYear = 0;
			var reworkQtyProducedLastYear = 0;
			var reworkQtyProduced = 0;
			var reworkQtyProducedThisYear = 0;
			var reworkQtyProducedLastYear = 0;			
			var productionVolumn = 0;
			var orderedQty = 0;
			
			
			qtyProduced = allData[0].qtyProduced;
			qtyProducedThisYear = allData[0].qtyProducedThisYear;
			qtyProducedLastYear = allData[0].qtyProducedLastYear;
			reworkQtyProduced = allData[0].reworkQtyProduced;	
			reworkQtyProducedThisYear = allData[0].reworkQtyProducedThisYear;	
			reworkQtyProducedLastYear = allData[0].reworkQtyProducedLastYear;	
			productionVolumn = allData[0].productionVolumn;	
			orderedQty = allData[0].orderedQty;	
						
			
			$("#quantityProduced").text(qtyProduced );
			$("#quantityProducedThisMonth").text(qtyProducedThisYear);
			$("#quantityProducedLastMonth").text(qtyProducedLastYear);
			$("#reworkQuantity").text(reworkQtyProduced);
			$("#reworkQuantityThisMonth").text(reworkQtyProducedThisYear);	
			$("#reworkQuantityLastMonth").text(reworkQtyProducedLastYear);	
			$("#productionVolumn").text(productionVolumn);	
			$("#quantityOrder").text(orderedQty);	
			
		},
		error: function(error) {
			console.error(error);
		}
	});


$.ajax({
		url: "dashboard-control-ooe-capacity-firstPassYeild-scrape-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var oee = 0;
			var scrapRate = 0;
			var firstPassYield = 0;
			var capacityUtilization = 0;
			
			var oeeList = [];
			var scrapRateList = [];
			var firstPassYieldList = [];
			var capacityUtilizationList = []; 
			
			
			oee = allData[0].oee;
			scrapRate = allData[0].scrapRate;
			firstPassYield = allData[0].firstPassYield;
			capacityUtilization = allData[0].capacityUtilization;
			
			oeeList.push(oee);
			scrapRateList.push(scrapRate);
			firstPassYieldList.push(firstPassYield);
			capacityUtilizationList.push(capacityUtilization);
			
			//OOE
	     Highcharts.chart('ooe_chart', {
	       chart: {
	         type: 'gauge',
	         plotBackgroundColor: null,
	         plotBackgroundImage: null,
	         plotBorderWidth: 0,
	         plotShadow: false,
	         height: '52%'
	       },
	       title: {
	         text: oee + "%",
	         align: "center",
	         verticalAlign: "bottom",
	         floating: true,
	         y: 30,
	         margin: 0,
	         style: {
	           fontSize: "14",
	           color: "#000000"
	         },
	       },
	       navigation: {
	         buttonOptions: {
	           enabled: false
	         }
	       },
	       credits: {
	         enabled: false,
	       },
	       pane: {
	         startAngle: -120,
	         endAngle: 119.9,
	         background: null,
	         center: ['50%', '75%'],
	         size: '100%'
	       },
	       // the value axis
	       yAxis: {
	         min: 0,
	         max: 100,
	         tickPixelInterval: 72,
	         tickPosition: 'inside',
	         tickColor: 'transparent',
	         tickLength: 20,
	         tickWidth: 2,
	         minorTickInterval: null,
	         labels: {
	           distance: 20,
	           style: {
	             fontSize: '11px'
	           }
	         },
	         lineWidth: 0,
	         plotBands: [{
	           from: 0,
	           to: 30,
	           color: '#bf05ff',
	           thickness: 15
	         }, {
	           from: 30,
	           to: 70,
	           color: '#CA7CE5',
	           thickness: 15
	         }, {
	           from: 70,
	           to: 100,
	           color: '#F79C92',
	           thickness: 15
	         }]
	       },
	       series: [{
	         name: '',
	         data: oeeList,
	         tooltip: {
	           valueSuffix: ' %'
	         },
	         dataLabels: {
	           enabled: false,
	         },
	         dial: {
	           radius: '80%',
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
	     //Capacity Utilization
	     Highcharts.chart('utilization', {
	       chart: {
	         type: 'gauge',
	         plotBackgroundColor: null,
	         plotBackgroundImage: null,
	         plotBorderWidth: 0,
	         plotShadow: false,
	         height: '52%'
	       },
	       title: {
	         text: capacityUtilization + "%",
	         align: "center",
	         verticalAlign: "bottom",
	         floating: true,
	         y: 30,
	         margin: 0,
	         style: {
	           fontSize: "14",
	           color: "#000000"
	         },
	       },
	       navigation: {
	         buttonOptions: {
	           enabled: false
	         }
	       },
	       credits: {
	         enabled: false,
	       },
	       pane: {
	         startAngle: -120,
	         endAngle: 119.9,
	         background: null,
	         center: ['50%', '75%'],
	         size: '100%'
	       },
	       // the value axis
	       yAxis: {
	         min: 0,
	         max: 100,
	         tickPixelInterval: 72,
	         tickPosition: 'inside',
	         tickColor: 'transparent',
	         tickLength: 20,
	         tickWidth: 2,
	         minorTickInterval: null,
	         labels: {
	           distance: 20,
	           style: {
	             fontSize: '11px'
	           }
	         },
	         lineWidth: 0,
	         plotBands: [{
	           from: 0,
	           to: 30,
	           color: '#bf05ff',
	           thickness: 15
	         }, {
	           from: 30,
	           to: 70,
	           color: '#CA7CE5',
	           thickness: 15
	         }, {
	           from: 70,
	           to: 100,
	           color: '#F79C92',
	           thickness: 15
	         }]
	       },
	       series: [{
	         name: '',
	         data: capacityUtilizationList,
	         tooltip: {
	           valueSuffix: ' %'
	         },
	         dataLabels: {
	           enabled: false,
	         },
	         dial: {
	           radius: '80%',
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
	     //First Pass Yield
	     Highcharts.chart('firstpass', {
	       chart: {
	         type: 'gauge',
	         plotBackgroundColor: null,
	         plotBackgroundImage: null,
	         plotBorderWidth: 0,
	         plotShadow: false,
	         height: '52%'
	       },
	       title: {
	         text: firstPassYield + "%",
	         align: "center",
	         verticalAlign: "bottom",
	         floating: true,
	         y: 30,
	         margin: 0,
	         style: {
	           fontSize: "14",
	           color: "#000000"
	         },
	       },
	       navigation: {
	         buttonOptions: {
	           enabled: false
	         }
	       },
	       credits: {
	         enabled: false,
	       },
	       pane: {
	         startAngle: -120,
	         endAngle: 119.9,
	         background: null,
	         center: ['50%', '75%'],
	         size: '100%'
	       },
	       // the value axis
	       yAxis: {
	         min: 0,
	         max: 100,
	         tickPixelInterval: 72,
	         tickPosition: 'inside',
	         tickColor: 'transparent',
	         tickLength: 20,
	         tickWidth: 2,
	         minorTickInterval: null,
	         labels: {
	           distance: 20,
	           style: {
	             fontSize: '11px'
	           }
	         },
	         lineWidth: 0,
	         plotBands: [{
	           from: 0,
	           to: 30,
	           color: '#bf05ff',
	           thickness: 15
	         }, {
	           from: 30,
	           to: 70,
	           color: '#CA7CE5',
	           thickness: 15
	         }, {
	           from: 70,
	           to: 100,
	           color: '#F79C92',
	           thickness: 15
	         }]
	       },
	       series: [{
	         name: '',
	         data: firstPassYieldList,
	         tooltip: {
	           valueSuffix: ' %'
	         },
	         dataLabels: {
	           enabled: false,
	         },
	         dial: {
	           radius: '80%',
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
	     
	    
	     //Scrap Rate
	     Highcharts.chart('scrap-rate', {
	       chart: {
	         type: 'gauge',
	         plotBackgroundColor: null,
	         plotBackgroundImage: null,
	         plotBorderWidth: 0,
	         plotShadow: false,
	         height: '52%'
	       },
	       title: {
	         text: scrapRate + "%",
	         align: "center",
	         verticalAlign: "bottom",
	         floating: true,
	         y: 30,
	         margin: 0,
	         style: {
	           fontSize: "14",
	           color: "#000000"
	         },
	       },
	       navigation: {
	         buttonOptions: {
	           enabled: false
	         }
	       },
	       credits: {
	         enabled: false,
	       },
	       pane: {
	         startAngle: -120,
	         endAngle: 119.9,
	         background: null,
	         center: ['50%', '75%'],
	         size: '100%'
	       },
	       // the value axis
	       yAxis: {
	         min: 0,
	         max: 100,
	         tickPixelInterval: 72,
	         tickPosition: 'inside',
	         tickColor: 'transparent',
	         tickLength: 20,
	         tickWidth: 2,
	         minorTickInterval: null,
	         labels: {
	           distance: 20,
	           style: {
	             fontSize: '11px'
	           }
	         },
	         lineWidth: 0,
	         plotBands: [{
	           from: 0,
	           to: 30,
	           color: '#bf05ff',
	           thickness: 15
	         }, {
	           from: 30,
	           to: 70,
	           color: '#CA7CE5',
	           thickness: 15
	         }, {
	           from: 70,
	           to: 100,
	           color: '#F79C92',
	           thickness: 15
	         }]
	       },
	       series: [{
	         name: '',
	         data: scrapRateList,
	         tooltip: {
	           valueSuffix: ' %'
	         },
	         dataLabels: {
	           enabled: false,
	         },
	         dial: {
	           radius: '80%',
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
			
			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-control-availablity-performance-quality-effectiveness-count?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var performanceValue = 0;
			var availabilityValue = 0;
			var qualityValue = 0;
			var oeeValue = 0;
			
			
			performanceValue = allData[0].performance;
			availabilityValue = allData[0].availablity;
			qualityValue = allData[0].quality;
			oeeValue = allData[0].ooeCount;
			
			
			  //avilability
		      Highcharts.chart('avilability', {
		        chart: {
		          type: 'pie',
		          height: 190,
		        },
		        navigation: {
		          buttonOptions: {
		            enabled: false
		          }
		        },
		        colors: ['#CA7CE5', '#dfdfdf'],
		        title: {
		          text: availabilityValue + "%",
		          align: 'center',
		          verticalAlign: 'middle',
		          floating: true,
		          y: 15,
		          margin: 0,
		          style: {
		            "fontSize": '14',
		            "color": '#000000'
		          }
		        },
		        subtitle: {
		          text: '',
		          align: 'center',
		          verticalAlign: 'center',
		          floating: true,
		          y: 65,
		          margin: 0,
		          style: {
		            "fontSize": '9',
		            "color": '#000000'
		          }
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
		            shadow: false
		          }
		        },
		        series: [{
		          name: '',
		          data: [
		            ["Contracted", 96],
		            ["", 4]
		          ],
		          size: '100%',
		          innerSize: '70%',
		          showInLegend: false,
		          dataLabels: {
		            enabled: false
		          }
		        }]
		      });
		      
		      
		      //perfomance
		      Highcharts.chart('perfomance', {
		        chart: {
		          type: 'pie',
		          height: 190,
		        },
		        navigation: {
		          buttonOptions: {
		            enabled: false
		          }
		        },
		        colors: ['#F79C92', '#dfdfdf'],
		        title: {
		          text: performanceValue + "%",
		          align: 'center',
		          verticalAlign: 'middle',
		          floating: true,
		          y: 15,
		          margin: 0,
		          style: {
		            "fontSize": '14',
		            "color": '#000000'
		          }
		        },
		        subtitle: {
		          text: '',
		          align: 'center',
		          verticalAlign: 'center',
		          floating: true,
		          y: 65,
		          margin: 0,
		          style: {
		            "fontSize": '9',
		            "color": '#000000'
		          }
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
		            shadow: false
		          }
		        },
		        series: [{
		          name: '',
		          data: [
		            ["Contracted", 93],
		            ["", 7]
		          ],
		          size: '100%',
		          innerSize: '70%',
		          showInLegend: false,
		          dataLabels: {
		            enabled: false
		          }
		        }]
		      });
		      
		      
		      //quality
		      Highcharts.chart('quality_chart', {
		        chart: {
		          type: 'pie',
		          height: 190,
		        },
		        navigation: {
		          buttonOptions: {
		            enabled: false
		          }
		        },
		        colors: ['#bf05ff', '#dfdfdf'],
		        title: {
		          text: qualityValue + "%",
		          align: 'center',
		          verticalAlign: 'middle',
		          floating: true,
		          y: 15,
		          margin: 0,
		          style: {
		            "fontSize": '14',
		            "color": '#000000'
		          }
		        },
		        subtitle: {
		          text: '',
		          align: 'center',
		          verticalAlign: 'center',
		          floating: true,
		          y: 65,
		          margin: 0,
		          style: {
		            "fontSize": '9',
		            "color": '#000000'
		          }
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
		            shadow: false
		          }
		        },
		        series: [{
		          name: '',
		          data: [
		            ["Contracted", 97],
		            ["", 3]
		          ],
		          size: '100%',
		          innerSize: '70%',
		          showInLegend: false,
		          dataLabels: {
		            enabled: false
		          }
		        }]
		      });
		      
		      
		      //effectiveness
		      Highcharts.chart('effectiveness', {
		        chart: {
		          type: 'pie',
		          height: 190,
		        },
		        navigation: {
		          buttonOptions: {
		            enabled: false
		          }
		        },
		        colors: ['#B422B6', '#dfdfdf'],
		        title: {
		          text: oeeValue + "%",
		          align: 'center',
		          verticalAlign: 'middle',
		          floating: true,
		          y: 15,
		          margin: 0,
		          style: {
		            "fontSize": '14',
		            "color": '#000000'
		          }
		        },
		        subtitle: {
		          text: '',
		          align: 'center',
		          verticalAlign: 'center',
		          floating: true,
		          y: 65,
		          margin: 0,
		          style: {
		            "fontSize": '9',
		            "color": '#000000'
		          }
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
		            shadow: false
		          }
		        },
		        series: [{
		          name: '',
		          data: [
		            ["Contracted", 87],
		            ["", 13]
		          ],
		          size: '100%',
		          innerSize: '70%',
		          showInLegend: false,
		          dataLabels: {
		            enabled: false
		          }
		        }]
		      });
	  		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-control-right-first-time?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var rightFirstTime = 0;			
			var rightFirstTimeList = [];
			
			rightFirstTime = allData[0].rightFirstTime;
			rightFirstTimeList.push(rightFirstTime);
			
			 //Right First Time
	       Highcharts.chart('rightfirsttime', {
	         chart: {
	           type: 'gauge',
	           plotBackgroundColor: null,
	           plotBackgroundImage: null,
	           plotBorderWidth: 0,
	           plotShadow: false,
	           height: '128'
	         },
	         title: {
	           text: rightFirstTime + " %",
	           align: "center",
	           verticalAlign: "bottom",
	           floating: true,
	           y: 18,
	           margin: 0,
	           style: {
	             fontSize: "14",
	             color: "#61426c"
	           },
	         },
	         navigation: {
	           buttonOptions: {
	             enabled: false
	           }
	         },
	         credits: {
	           enabled: false,
	         },
	         pane: {
	           startAngle: -88,
	           endAngle: 88,
	           background: null,
	           center: ['50%', '75%'],
	           size: '100%'
	         },
	         // the value axis
	         yAxis: {
	           min: 0,
	           max: 100,
	           // tickPixelInterval: 72,
	           tickPosition: 'inside',
	           tickColor: 'transparent',
	           tickLength: 0,
	           tickWidth: 0,
	           minorTickInterval: null,
	           labels: {
	             enabled: false,
	             distance: -35,
	             style: {
	               fontSize: '11px'
	             }
	           },
	           lineWidth: 0,
	           plotBands: [{
	             from: 0,
	             to: 90,
	             color: '#bf05ff',
	             thickness: 25
	           }, {
	             from: 90,
	             to: 100,
	             color: '#56156C',
	             thickness: 25
	           }]
	         },
	         series: [{
	           name: '',
	           data: rightFirstTimeList,
	           tooltip: {
	             valueSuffix: ' %'
	           },
	           dataLabels: {
	             enabled: false,
	           },
	           dial: {
	             radius: '60%',
	             backgroundColor: 'gray',
	             baseWidth: 6,
	             baseLength: '0%',
	             rearLength: '0%'
	           },
	           pivot: {
	             backgroundColor: 'gray',
	             radius: 3
	           }
	         }]
	       });
	    
			
			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-control-throughput?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
		   var jsonData = JSON.parse(response.body);
		   var allData = jsonData.dashboardData;
			
		   var throughput = 0;			
		   var throughputList = [];
			
			throughput = allData[0].throughput;
			throughputList.push(throughput);
			
			   //Throughput
	       Highcharts.chart('Throughput', {
	         chart: {
	           type: 'gauge',
	           plotBackgroundColor: null,
	           plotBackgroundImage: null,
	           plotBorderWidth: 0,
	           plotShadow: false,
	           height: '130'
	         },
	         title: {
	           text: throughput + "Minutes",
	           align: "center",
	           verticalAlign: "bottom",
	           floating: true,
	           y: 30,
	           margin: 0,
	           style: {
	             fontSize: "14",
	             color: "#000000"
	           },
	         },
	         navigation: {
	           buttonOptions: {
	             enabled: false
	           }
	         },
	         credits: {
	           enabled: false,
	         },
	         pane: {
	           startAngle: -120,
	           endAngle: 119.9,
	           background: null,
	           center: ['50%', '75%'],
	           size: '100%'
	         },
	         // the value axis
	         yAxis: {
	           min: 0,
	           max: 12,
	           tickPixelInterval: 72,
	           tickPosition: 'inside',
	           tickColor: 'transparent',
	           tickLength: 20,
	           tickWidth: 2,
	           minorTickInterval: null,
	           labels: {
	             distance: 20,
	             style: {
	               fontSize: '11px'
	             }
	           },
	           lineWidth: 0,
	           plotBands: [{
	             from: 0,
	             to: 4,
	             color: '#bf05ff',
	             thickness: 15
	           }, {
	             from: 4,
	             to: 8,
	             color: '#CA7CE5',
	             thickness: 15
	           }, {
	             from: 8,
	             to: 12,
	             color: '#F79C92',
	             thickness: 15
	           }]
	         },
	         series: [{
	           name: '',
	           data: throughputList,
	           tooltip: {
	             valueSuffix: ' minutes'
	           },
	           dataLabels: {
	             enabled: false,
	           },
	           dial: {
	             radius: '80%',
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
			
		},
		error: function(error) {
			console.error(error);
		}
	});


$.ajax({
		url: "dashboard-control-cost-analysis-over-time?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var productionCostList = [];
			var manufacturingCostList = [];
			var labourCostList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				monthYearList.push(item.monthYear);
				productionCostList.push(item.productionCost);
				manufacturingCostList.push(item.manufacturingCost);
				labourCostList.push(item.LabourCost);
			}		
			
			
			      // Cost Analysis Over Time
      Highcharts.chart('costanalysis', {
        chart: {
          type: 'area',
          height: 282,
        },
        title: {
          text: '',
          align: 'left'
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        subtitle: {
          text: '',
          align: 'left'
        },
        xAxis: {
          gridLineWidth: 1,
          categories: monthYearList,
        },
        yAxis: {
          title: {
            text: '',
          },
          labels: {
            x: -10,
            y: 0,
            //format: '${value:.,0f}M'
            format: '{value:.,0f}'
          },
        },
        legend: {
          layout: 'horizontal',
          align: 'left',
          x: 500,
          verticalAlign: 'top',
          y: 0,
          floating: false,
        },
        credits: {
          enabled: false
        },
        series: [{
          name: 'Production Cost',
          data: productionCostList,
          marker: {
            fillColor: '#CA7CE5',
            symbol: 'circle',
            radius: 4
          },
          color: '#CA7CE5',
          dataLabels: {
            enabled: true,
          }
        }, {
          name: 'Manufacturing Cost',
          data: manufacturingCostList,
          marker: {
            fillColor: '#B422B6',
            symbol: 'circle',
            radius: 4
          },
          color: '#B422B6',
          dataLabels: {
            enabled: true,
          }
        }, {
          name: 'Labor Cost',
          data: labourCostList,
          marker: {
            fillColor: '#56156C',
            symbol: 'circle',
            radius: 4
          },
          color: '#56156C',
          dataLabels: {
            enabled: true,
          }
        }, ]
      });
				
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
	
	$.ajax({
		url: "dashboard-control-production-variance?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthList = [];
			var productionCostList = [];
			var allCostForTwelveMonth = 0;
			
					
			for (var i = 1; i < allData.length; i++) {				
				var item = allData[i];				
				monthList.push(item.monthYear);
				var productionExpenseValue = item.productionExpense/1000000;
				productionCostList.push(parseInt(productionExpenseValue));
				allCostForTwelveMonth = allCostForTwelveMonth + productionExpenseValue;
			}	
			
			var averageCostForTwelveMonth = (parseInt(allCostForTwelveMonth/12));
			var averageCostForTwelveMonthList = [];
			
			for (var i = 0; i < 12; i++) {			
				averageCostForTwelveMonthList.push(averageCostForTwelveMonth);
			}
			
			
      //Production Variance
      Highcharts.chart('production_variance', {
        chart: {
          zoomType: "xy",
          animation: true,
          height: 300
        },
        navigation: {
          buttonOptions: {
            enabled: false,
          },
        },
        title: {
          text: "",
        },
        subtitle: {
          text: "",
        },
        credits: {
          enabled: false
        },
        xAxis: [{
          categories: monthList,
          labels: {
            style: {
              fontSize: "10px"
            }
          },
        }, ],
        yAxis: [{ // left y axis
          title: {
            text: 'Actual Production',
            x: -15,
          },
          labels: {
            align: 'left',
            x: -20,
            y: 0,
           // format: '{value:.,0f}K'
            format: '{value:.,0f}'
          },
          showFirstLabel: true
        }, { // right y axis
          linkedTo: 0,
          gridLineWidth: 0,
          opposite: true,
          title: {
            text: 'Target Production',
            x: 15,
          },
          labels: {
            align: 'right',
            x: 20,
            y: 0,
           // format: '{value:.,0f}K'
            format: '{value:.,0f}'
          },
          showFirstLabel: true,
        }],
        tooltip: {
          shared: true,
        },
        legend: {
          enabled: false,
          align: 'center',
          itemStyle: {
            fontSize: "10px"
          },
          layout: 'horizontal',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
        },
        series: [{
          name: "Target",
          type: "column",
          data: averageCostForTwelveMonthList,
          colors: ['#CA7CE5', '#CA7CE5', '#CA7CE5', '#F79C92', '#CA7CE5', '#CA7CE5', '#CA7CE5', '#CA7CE5', '#F79C92', '#CA7CE5', '#F79C92', '#F79C92', '#CA7CE5', '#CA7CE5'], // '#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', 
          colorByPoint: true,
        }, {
          name: "Actual",
          data: productionCostList,
          color: "#F79C92",
          // yAxis: 1,
        }, ],
      });	
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-control-top-five-machine-production?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData;
			var totalLength = 0;
			totalLength = allData.length;
			
			var machineName1 = "";
			var machineName2 = "";
			var machineName3 = "";
			var machineName4 = "";
			var machineName5 = "";
			
			
			var machine1production = "";
			var machine2production = "";
			var machine3production = "";
			var machine4production = "";
			var machine5production = "";
			
			if(totalLength>0){
				machineName1 = allData[0].machineName;
				machine1production = allData[0].convertionInMtTotalProduction;
			}
			if(totalLength>1){
				machineName2 = allData[1].machineName;
				machine2production = allData[1].convertionInMtTotalProduction;
			}
			if(totalLength>2){
				machineName3 = allData[2].machineName;
				machine3production = allData[2].convertionInMtTotalProduction;
			}
			if(totalLength>3){
				machineName4 = allData[3].machineName;
				machine4production = allData[3].convertionInMtTotalProduction;
			}
			if(totalLength>4){
				machineName5 = allData[4].machineName;
				machine5production = allData[4].convertionInMtTotalProduction;
			}
				
		
			
			
			
			var totalProductionInTone = allData[0].totalProduction;
			if(machine1production == 0){
				machine1ProductionPercentage=0.00;
			}else{
				machine1ProductionPercentage = ((machine1production/totalProductionInTone)*100);
			}
			
			if(machine2production == 0){
				machine2ProductionPercentage=0.00;
			}else{
				machine2ProductionPercentage = ((machine2production/totalProductionInTone)*100);
			}
			
			if(machine3production == 0){
				machine3ProductionPercentage=0.00;
			}else{
				machine3ProductionPercentage = ((machine3production/totalProductionInTone)*100);
			}
			
			if(machine4production == 0){
				machine4ProductionPercentage=0.00;
			}else{
				machine4ProductionPercentage = ((machine4production/totalProductionInTone)*100);
			}
			
			if(machine5production == 0){
				machine5ProductionPercentage=0.00;
			}else{
				machine5ProductionPercentage = ((machine5production/totalProductionInTone)*100);
			}
			
			
				 

      // Top 5 Machines by Production Volume     
      Highcharts.chart('productionvolume', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: 320
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}:   {point.percentage: .1 f} %'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
        credits: {
          enabled: false
        },
        exporting: {
          enabled: false
        },
        plotOptions: {
          pie: {
            colors: ['#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', ],
            dataLabels: {
              enabled: true,
              distance: 10,
              style: {
                fontWeight: 'bold',
				color: '#000000',
				textOutline: false
              }
            },
            showInLegend: true,
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
        data: [{
            name: machineName1,
            y: machine1ProductionPercentage
            
        },  {
            name: machineName2,
            y: machine2ProductionPercentage
        },  {
            name: machineName3,
            y: machine3ProductionPercentage
        }, {
            name: machineName4,
            y: machine4ProductionPercentage
        }, {
            name: machineName5,
            y: machine5ProductionPercentage
        }]
        }]
      });
				
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-control-production-efficiency-Runtime-downtime?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var machineNameList = [];
			var totalRunTimeInHoursList = [];
			var totalBreakDownInHoursList = [];
			
			for (var i = 0; i < allData.length; i++) {
				var item = allData[i];
				machineNameList.push(item.machineName);
				totalRunTimeInHoursList.push(item.totalRunTimeInHours);
				totalBreakDownInHoursList.push(item.totalBreakDownInHours);				
			}	
			     //Production Efficiency:Runtime vs. Downtime
      Highcharts.chart('productionefficiency', {
        chart: {
          type: 'bar',
          height: 300
        },
        title: {
          text: ''
        },
        xAxis: {
          categories: machineNameList,
          },
        yAxis: {
          min: 0,
          title: {
            text: ''
          }
        },
        legend: {
          reversed: true,
          verticalAlign: 'top',
        },
        plotOptions: {
          series: {
            stacking: 'normal',
            dataLabels: {
              enabled: true,
			  style: {color: '#ffffff',textOutline: false }
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
          name: 'Downtime hours',
          data: totalBreakDownInHoursList,
          dataLabels: {
            enabled: false,
          },
          color: '#CA7CE5'
        }, {
          name: 'Runtime hours',
          data: totalRunTimeInHoursList,
          color: '#bf05ff'
        }]
      });	
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	


	      
	  
	  $.ajax({
		url: "dashboard-machine-breakdown?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData;
			
			var totalBreakdownTimeInHours = allData[0].totalBreakdownTimeInHours;
			var totalWorkingHours =  allData[0].totalWorkingHours;
			var breakDownMAchinePercentage = allData[0].breakDownMAchinePercentage;
			
			var machineBrokenPercentage = allData[0].machineBrokenPercentage; 
			var missingMachinePercentage = allData[0].missingMachinePercentage; 
			var machineServicePercentage = allData[0].machineServicePercentage; 
			
		//	alert("machineBrokenPercentage-----------"+machineBrokenPercentage);
		//	alert("missingMachinePercentage-----------"+missingMachinePercentage);
		//	alert("machineServicePercentage-----------"+machineServicePercentage);
			
 
      //Downtime Causes       
      Highcharts.chart('downtime_cause', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false,
          height: 320
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}:  < b > {point.percentage: .1 f} % < /b>'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
        credits: {
          enabled: false
        },
        exporting: {
          enabled: false
        },
        plotOptions: {
          pie: {
            colors: ['#CA7CE5', '#bf05ff', '#B422B6'],
            dataLabels: {
              enabled: true,
              distance: 10,
              style: {
                fontWeight: 'bold',
                color: '#000000',
				textOutline: false
              }
            },
            showInLegend: true,
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
        data: [{
            name: 'Broken Macine',
            y: machineBrokenPercentage
            
        },  {
            name: 'Missing Parts',
            y: missingMachinePercentage
        }, {
            name: 'Service',
            y: machineServicePercentage
        }]
        }]
      });
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-maintenance-cost-with-target?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var avgMaintenanceCostThisMonthList = [];
			var avgMaintenanceCostList = [];
			var avgMaintenanceCost = allData[0].avgMaintenanceCost;
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				monthYearList.push(item.monthYear);
				avgMaintenanceCostThisMonthList.push(item.avgMaintenanceCostThisMonth);
				avgMaintenanceCostList.push(avgMaintenanceCost);
			}	
			
			
				  //maintenance costs
      Highcharts.chart('maintenance', {
        chart: {
          animation: true,
          height: 280
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: monthYearList,
          crosshair: true,
          lineColor: '#cccccc',
          rotation: '45deg'
        }],
        yAxis: {
          labels: {
            format: '${value}',
          },
          title: {
            text: '',
          },
        },
        tooltip: {
          shared: false
        },
        plotOptions: {
          series: {
            dataLabels: {
              enabled: true,
              color: '#000000'
            }
          }
        },
        series: [{
          name: 'Maintenance Cost',
          type: 'line',
          data: avgMaintenanceCostThisMonthList,
          color: '#B422B6',
          dataLabels: {
            enabled: false,
          },
        }, {
          name: 'Target Maintenance Cost',
          type: 'line',
          data: avgMaintenanceCostList,
          color: '#F79C92',
          lineWidth: 2,
          dashStyle: 'ShortDash',
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });		
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-cost-management-unit-cost-target?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var unitCostThisMonthList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				monthYearList.push(item.monthYear);
				unitCostThisMonthList.push(item.unitCostThisMonth);
			}	
			
			
			 // Calculate the average
            var sum = $.map(unitCostThisMonthList, function(value) {
                return value;
            }).reduce(function(a, b) {
                return a + b;
            }, 0);

            var target = sum / unitCostThisMonthList.length;
            var targetList = [];
            for (var i = 0; i < allData.length; i++) {
				targetList.push(target);
			}
			
			
		
      //unit cost with target
      Highcharts.chart('unitcost', {
        chart: {
          animation: true,
          height: 280
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: monthYearList,
          crosshair: true,
          lineColor: '#cccccc',
          rotation: '45deg'
        }],
        yAxis: {
          labels: {
            format: '${value}',
          },
          title: {
            text: '',
          },
        },
        tooltip: {
          shared: false
        },
        plotOptions: {
          series: {
            dataLabels: {
              enabled: true,
              color: '#000000'
            }
          }
        },
        series: [{
          name: 'Unit Cost',
          type: 'column',
          data: unitCostThisMonthList,
          color: '#bf05ff',
        }, {
          name: 'Unit Cost Target',
          type: 'line',
          data: targetList,
          color: '#b4617c',
          lineWidth: 2,
          dashStyle: 'ShortDash',
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });	
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	//Ajax dashboard for machine utilization
	$.ajax({
		url: "dashboard-machine-specialization-details?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;

			$("#machineProductionDetails1").empty();
			var machineWorking = '';

			for (var i = 0; i < allData.length; i++) {
			    var item = allData[i];
			    var machineName = item.machineName;
			    var machineWorkingStatus = item.machineWorkingStatus;
			    var throughPut = item.throughPut;
			    var machineOEE = item.machineOEE;
			    var machineCapacityUtilization = item.machineCapacityUtilization;
			    var machineFirstPassYeild = item.machineFirstPassYeild;
			    var machineScrapeRate = item.machineScrapeRate;
			
			    var chartId = "ooe1" + machineName.replace(/\s/g, '') + '_' + i;
			
			    if (machineWorkingStatus === "Running") {
			        machineWorking = `
			            <div class="col-md-3">
			                <div class="kpi-card-container">
			                    <div class="machine-name">${machineName}</div>
			                    <div class="machine-running-status running">${machineWorkingStatus}</div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">Throughput</div>
			                        <div class="w-100 machine-results">${throughPut}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">OEE</div>
			                        <div class="w-100 machine-results">${machineOEE}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">Capacity Utilization</div>
			                        <div class="w-100 machine-results">${machineCapacityUtilization}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">First Pass Yield</div>
			                        <div class="w-100 machine-results">${machineFirstPassYeild}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">Scrap Rate</div>
			                        <div class="w-100 machine-results">${machineScrapeRate}</div>
			                    </div>
			                    <div id="${chartId}" style="width:100%;"></div>
			                </div>
			            </div>`;
			    } else if (machineWorkingStatus === "Not Running") {
			        machineWorking = `
			            <div class="col-md-3">
			                <div class="kpi-card-container">
			                    <div class="machine-name">${machineName}</div>
			                    <div class="machine-running-status not-running">${machineWorkingStatus}</div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">Throughput</div>
			                        <div class="w-100 machine-results">${throughPut}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">OEE</div>
			                        <div class="w-100 machine-results">${machineOEE}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">Capacity Utilization</div>
			                        <div class="w-100 machine-results">${machineCapacityUtilization}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">First Pass Yield</div>
			                        <div class="w-100 machine-results">${machineFirstPassYeild}</div>
			                    </div>
			                    <div class="d-flex w-100 bb-machine">
			                        <div class="w-100 machine-td">Scrap Rate</div>
			                        <div class="w-100 machine-results">${machineScrapeRate}</div>
			                    </div>
			                    <div id="${chartId}" style="width:100%;"></div>
			                </div>
			            </div>`;
			    }
			    
			    //alert("machineWorking--------"+machineWorking);
			
			    // Append the generated HTML
			    $("#machineProductionDetails1").append(machineWorking);
    
    
				//ooe machine A
				Highcharts.chart(chartId, {
				chart: {
					animation: true,
					height: 100,
					margin: 0
				},
				navigation: {
					buttonOptions: {
						enabled: false
					}
				},
				title: {
					text: ''
				},
				subtitle: {
					text: ''
				},
				credits: { enabled: false },
				xAxis: [{
					/*categories: ['1', '2', '3', '4', '5', '6'],*/
					crosshair: true,
					lineColor: '#cccccc',
					labels: { enabled: false }
				}],
				yAxis: [{ // Primary yAxis
					labels: {
						enabled: false,
					},
					title: {
						text: '',
					},
					gridLineColor: 'transparent'
				}, { // Secondary yAxis
					title: {
						text: 'Precipitation',
						style: {
							display: 'none',
						}
					},
					labels: {
						enabled: false,
					},
					gridLineColor: 'transparent'
				}],
				tooltip: {
					shared: false
				},
				legend: {
					enabled: false,
				}, plotOptions: {
					plotWidth: 10,
					column: {
						zones: [{
							value: 28,
							color: '#56156C'
						}, {
							color: '#B422B6'
						}]
					}
				},
				series: [{
					name: 'Precipitation',
					type: 'column',
					yAxis: 1,
					data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7,
						27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7],
		
				}, {
					name: 'Benchmark',
					type: 'spline',
					data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
						30, 30, 30, 30, 30, 30],
					color: '#F79C92',
					lineWidth: 3,
					dataLabels: {
						enabled: false,
					},
					marker: {
						enabled: false
					}
				}]
			});
			
			}	
			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	 // Avg. Hours to Product Completion by Product and Operator
	      Highcharts.chart('productandoperator', {
	        chart: {
	          type: 'column',
	          height: 320,
	        },
	        title: {
	          text: '',
	          align: 'left'
	        },
	        navigation: {
	          buttonOptions: {
	            enabled: false
	          }
	        },
	        legend: {
	          reversed: true,
	          verticalAlign: 'top',
	        },
	        credits: {
	          enabled: false,
	        },
	        subtitle: {
	          text: '',
	          align: 'left'
	        },
	        xAxis: {
	          categories: ['Operator_1', 'Operator_2', 'Operator_3', 'Operator_4', 'Operator_5', 'Operator_6', 'Operator_7']
	        },
	        yAxis: {
	          min: 0,
	          title: {
	            text: ''
	          }
	        },
	        tooltip: {
	          pointFormat: ' < span style = "color:{series.color}" > {series.name} < /span>:  < b > {point.y} < /b> ({point.number:.0f}) < br / > ',
	          shared: true
	        },
	        plotOptions: {
	          column: {
	            stacking: 'number',
	            dataLabels: {
	              enabled: true,
				  style: {color: '#ffffff',textOutline: false }
	              // format: '{point.percentage:.0f}'
	            }
	          }
	        },
	        series: [{
	          name: 'Steel',
	          data: [5.18, 5.15, 5.13, 5.18, 5.20, 5.16, 5.14],
	          color: '#F79C92',
	        }, {
	          name: 'Cement',
	          data: [5.10, 5.15, 5.13, 5.12, 5.20, 5.14, 5.16],
	          color: '#B422B6',
	        }, {
	          name: 'Cotton Clothe',
	          data: [5.17, 5.12, 5.17, 5.09, 5.24, 5.07, 5.19],
	          color: '#CA7CE5',
	        }]
	      });
	      
	      
	      $.ajax({
		url: "dashboard-rate-of-return?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			    monthYearList.push(item.monthYear);
			    purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			    salesRateOfReturnList.push(item.salesRateOfReturn);
			}
			
			
			
		Highcharts.chart('rateof-return', {
			chart: {
				type: 'column',
				zoomType: 'xy',
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
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
			xAxis: [{
				categories: monthYearList,
				crosshair: true
			}],
			yAxis: [{
				labels: {format: '{value} %', },
				title: {
					text: '',
				},
			}
			],
			plotOptions: {
				column: {
					pointPadding: 0,
					borderWidth: 0
				}
			},
			legend: {
				enabled: true
			},
			series: [{
				name: 'Purchase',
				data: purchaseRateOfReturnList,
				tooltip: {
					valueSuffix: ' mm'
				},
				color: '#bf05ff',

			}, {
				name: 'Sales',
				data: salesRateOfReturnList,
				color: '#F79C92',
				margin: '0',

			}]
		});	
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-defect-density?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			         monthYearList.push(item.monthYear);
			   		 purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			   		 salesRateOfReturnList.push(item.salesRateOfReturn);
			   
			}
			
			
			
		Highcharts.chart('defect-density', {
			chart: {
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},
			credits: {enabled: false},
			xAxis: [{
				categories: monthYearList,
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
				labels: {format: '{value} %', },
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Purchase',
				type: 'line',
				color: '#F79C92',
				data: purchaseRateOfReturnList,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}

			}, {
				name: 'Sales',
				type: 'line',
				data: salesRateOfReturnList,
				color: '#B422B6',
				lineWidth: 1,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	/////##############################################################################
		/*
		$.ajax({
		url: "dashboard-control-hours-to-product-completion-by-product?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}	
				
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
		
	     */
	/////##############################################################################
	

/*		

	$.ajax({
		url: "dashboard-control-defect-analysis-type-rate?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	//=================================Below Mapped with database================================

	
	
	
	
	$.ajax({
		url: "dashboard-return-item-by-reason?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	

	
	
	
	
	$.ajax({
		url: "dashboard-cost-management-return-on-assets?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	$.ajax({
		url: "dashboard-cost-management-asset-turnover?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	
	
	$.ajax({
		url: "dashboard-kpi-effectiveness?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-kpi-quality-performance?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-kpi-production?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-kpi-cost-revenue?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	
	$.ajax({
		url: "dashboard-machine-specialization-details?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.topMeanTimeToRepair;
			
			var assetList = [];
			var repairTimeList = [];
					
			for (var i = 0; i < allData.length; i++) {
				
				var item = allData[i];
				assetList.push(item.assetName);
				repairTimeList.push(item.OpenCloseDiff);
				
			}			
			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	*/
	

      //Defect Analysis by Type and Rate
      Highcharts.chart('defectanalysis', {
        chart: {
          animation: true,
          height: 320
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        title: {
          text: ''
        },
        subtitle: {
          text: ''
        },
        legend: {
          align: 'center',
          layout: 'horizontal',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
        },
        credits: {
          enabled: false
        },
        xAxis: [{
          categories: ['Assebly Errors', 'Dimensional Errors', 'Labeling Errors', 'Material Defect', 'Packaging Error', 'Surface defects'],
          crosshair: true,
          lineColor: '#cccccc',
          rotation: '45deg'
        }],
        yAxis: [{ // left y axis
          title: {
            text: 'Tickets',
            x: -10,
          },
          labels: {
            align: 'left',
            x: -10,
            y: 0,
            format: '{value:.,0f}'
          },
          showFirstLabel: true
        }, { // right y axis
          linkedTo: 0,
          gridLineWidth: 0,
          opposite: true,
          title: {
            text: 'Satisfaction Rate',
            x: 10,
          },
          labels: {
            align: 'right',
            x: 10,
            y: 0,
            format: '{value:.,0f}%'
          },
          showFirstLabel: true,
        }],
        // yAxis: {
        //     title: {
        //         text: '',
        //     },
        // },
        tooltip: {
          shared: false
        },
        plotOptions: {
          series: {
            dataLabels: {
              enabled: true,
				style: {color: '#ffffff',textOutline: false }
            }
          }
        },
        series: [{
          name: 'Defects',
          type: 'column',
          data: [93, 98, 101, 91, 85, 89],
          color: '#CA7CE5',
        }, {
          name: 'Defects Rate',
          type: 'spline',
          data: [1.82, 2.06, 2.03, 1.86, 1.75, 1.75],
          color: '#56156C',
          lineWidth: 1,
          dataLabels: {
            enabled: true,
          },
          marker: {
            enabled: true
          }
        }]
      });
      
      // Returned Items by Reason
     	$.ajax({
		url: "dashboard-return-item-by-reason?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			//var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
					
		var jsonData = JSON.parse(response.body);
		var allData = jsonData.dashboardData;
		
		// Step 1: Create an object to store aggregated data by monthYear and reasonName
		var aggregatedData = {};
		
		// Loop through the dashboardData and accumulate totalCount for each monthYear and reasonName
		allData.forEach(item => {
		    var monthYear = item.monthYear;
		    var reasonName = item.reasonName;
		    var totalCount = item.totalCount;
		
		    // Initialize the monthYear object if it doesn't exist
		    if (!aggregatedData[monthYear]) {
		        aggregatedData[monthYear] = {};
		    }
		
		    // Initialize the reasonName array if it doesn't exist for the specific monthYear
		    if (!aggregatedData[monthYear][reasonName]) {
		        aggregatedData[monthYear][reasonName] = 0;
		    }
		
		    // Accumulate the totalCount
		    aggregatedData[monthYear][reasonName] += totalCount;
		});
		
		// Step 2: Prepare the series array
		var series = [
		    { name: 'Returned Due to Damage Product', data: [], color: '#B422B6' },
		    { name: 'Poor Quality', data: [], color: '#F79C92' },
		    { name: 'UnMatched Specimen', data: [], color: '#FPOK92' },
		    { name: 'Size Issue', data: [], color: '#bf05ff' }
		];
		
		// Step 3: Populate the data for each reasonName in the series array
		var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
		
		monthYeartList.forEach(monthYear => {
		    // Push totalCount for each reason into the series data
		    series[0].data.push(aggregatedData[monthYear]["Returned Due to Damage Product"] || 0);
		    series[1].data.push(aggregatedData[monthYear]["Poor Quality"] || 0);
		    series[2].data.push(aggregatedData[monthYear]["UnMatched Specimen"] || 0);
		    series[3].data.push(aggregatedData[monthYear]["Size Issue"] || 0);
		});
		
		console.log(series);
				
			
			
		Highcharts.chart('returneditems', {
	    chart: {
	        type: 'column',
	        animation: true,
	        height: 250,
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
	    legend: {
	        enabled: true,  // Enable the legend
	        layout: 'horizontal',  // Arrange legend items horizontally
	        align: 'center',  // Align it in the center
	        verticalAlign: 'bottom',  // Place it at the bottom
	        itemStyle: {
	            fontWeight: 'normal',
	            fontSize: '14px',
	            color: '#333'  // Set the color of legend text
	        }
	    },
	    xAxis: {
	        categories: monthYeartList,
	        rotation: '45deg',
	    },
	    yAxis: {
	        title: {
	            text: ''
	        },
	        labels: {enabled: false},
	        stackLabels: {
	            enabled: true
	        }
	    },
	    plotOptions: {
	        series: {
	            stacking: 'normal',
	            dataLabels: {
	                enabled: false,
	                color: '#ffffff'
	            }
	        }
	    },
	    series: series
	});

			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
      //Rate of return
      Highcharts.chart('rateof-return', {
        chart: {
          type: 'column',
          zoomType: 'xy',
          animation: true,
          height: 300
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: ['Jan 2017', 'Feb 2017', 'Mar 2017', 'Apr 2017', 'May 2017', 'Jun 2017', 'Jul 2017', 'Aug 2017', 'Sep 2017', 'Oct 2017', 'Nov 2017', 'Dec 2017'],
          crosshair: true
        }],
        yAxis: [{
          labels: {
            format: '{value} %',
          },
          title: {
            text: '',
          },
        }],
        plotOptions: {
          column: {
            pointPadding: 0,
            borderWidth: 0
          }
        },
        legend: {
          enabled: true
        },
        series: [{
          name: 'Laptop A10 460M',
          data: [27.6, 28.8, 21.7, 34.1, 29.0, 28.4, 45.6, 51.7, 39.0,
            60.0, 28.6, 32.1
          ],
          tooltip: {
            valueSuffix: ' mm'
          },
          color: '#CA7CE5',
        }, {
          name: 'Laptop A15 460M',
          data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5,
            106.6, 92.3
          ],
          color: '#B422B6',
          margin: '0',
        }]
      });

      //return on assets
      Highcharts.chart('returnOnAssets', {
        chart: {
          animation: true,
          height: 250
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: ['Jan 2023', 'Feb 2023', 'Mar 2023', 'Apr 2023', 'May 2023', 'Jun 2023', 'Jul 2023', 'Aug 2023', 'Sep 2023', 'Oct 2023', 'Nov 2023', 'Dec 2023'],
          crosshair: true,
          lineColor: '#cccccc',
          rotation: '45deg'
        }],
        yAxis: {
          labels: {
            format: '{value} %',
          },
          title: {
            text: '',
          },
        },
        tooltip: {
          shared: false
        },
        legend: {
          enabled: false,
        },
        plotOptions: {
          plotWidth: 10,
        },
        series: [{
          name: '',
          type: 'column',
          data: [-5, 4, 8, 11, 9, 2, -2, -3, -4, 5, 7, 10],
          color: '#B422B6',
        }, {
          name: 'Benchmark',
          type: 'line',
          data: [3, 3.5, 4, 4.5, 5, 5.5, 6, 6.5, 7, 7.5, 8, 8.5],
          color: '#b4617c',
          lineWidth: 1,
          dashStyle: 'LongDash',
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });
      //Asset Turnover
      Highcharts.chart('asset-turnover', {
        chart: {
          type: 'column',
          animation: true,
          height: 250,
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
          categories: ['2013', '2014', '2015', '2016', '2017'],
        },
        yAxis: {
          title: {
            text: ''
          },
          labels: {
            format: '{value} %',
          },
        },
        plotOptions: {
          series: {
            dataLabels: {
              enabled: true,
              format: '{y} %',
              color: '#000000'
            }
          }
        },
        legend: {
          enabled: false,
        },
        series: [{
          name: '',
          data: [1.6, 1.9, 1.5, 1.9, 2.1],
          color: '#F79C92'
        }]
      });
    
      //efectiveness curr prev 1
      Highcharts.chart('controlefectiveness1', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [59],
          color: '#F79C92',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [44],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //efectiveness curr prev 2
      Highcharts.chart('controlefectiveness2', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [45],
          color: '#F79C92',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [41],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //efectiveness curr prev 3
      Highcharts.chart('controlefectiveness3', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [40],
          color: '#F79C92',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [38],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //efectiveness curr prev 4
      Highcharts.chart('controlefectiveness4', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [25],
          color: '#F79C92',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [27],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //qualityPerf curr prev 1
      Highcharts.chart('controlquality1', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [45],
          color: '#bf05ff',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [43],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //qualityPerf curr prev 2
      Highcharts.chart('controlquality2', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [45],
          color: '#bf05ff',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [47],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //qualityPerf curr prev 3
      Highcharts.chart('controlquality3', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [32],
          color: '#bf05ff',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [34],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //qualityPerf curr prev 4
      Highcharts.chart('controlquality4', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [25],
          color: '#bf05ff',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [27],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //production curr prev 1
      Highcharts.chart('controlproduction1', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [25],
          color: '#CA7CE5',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [26],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //production curr prev 2
      Highcharts.chart('controlproduction2', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [30],
          color: '#CA7CE5',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [35],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //production curr prev 3
      Highcharts.chart('controlproduction3', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [32],
          color: '#CA7CE5',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [34],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //production curr prev 4
      Highcharts.chart('controlproduction4', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [10],
          color: '#CA7CE5',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [15],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //costRevenue curr prev 1
      Highcharts.chart('controlcostRevenue1', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [40],
          color: '#B422B6',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [36],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //costRevenue curr prev 2
      Highcharts.chart('controlcostRevenue2', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [40],
          color: '#B422B6',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [36],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //costRevenue curr prev 3
      Highcharts.chart('controlcostRevenue3', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [35],
          color: '#B422B6',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [30],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //costRevenue curr prev 4
      Highcharts.chart('controlcostRevenue4', {
        chart: {
          animated: true,
          height: 40,
          margin: 0
        },
        title: {
          text: ''
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false,
        },
        xAxis: {
          categories: ['6 months'],
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
        },
        yAxis: {
          min: 0,
          max: 50,
          title: {
            text: ''
          },
          labels: {
            enabled: false,
          },
          lineColor: 'transparent',
          gridLineColor: 'transparent'
        },
        legend: {
          enabled: false,
        },
        series: [{
          type: 'bar',
          name: '',
          data: [40],
          color: '#B422B6',
        }, {
          name: '',
          color: 'transparent',
          lineColor: 'transparent',
          data: [45],
          type: 'spline',
          dataLabels: {
            enabled: false,
          },
          marker: {
            lineWidth: 2,
            fillColor: '#005c9f'
          }
        }, ]
      });
      //ooe machine A
      Highcharts.chart('controlooeMachineA', {
        chart: {
          animation: true,
          height: 100,
          margin: 0
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: ['1', '2', '3', '4', '5', '6'],
          crosshair: true,
          lineColor: '#cccccc',
          labels: {
            enabled: false
          }
        }],
        yAxis: [{ // Primary yAxis
          labels: {
            enabled: false,
          },
          title: {
            text: '',
          },
          gridLineColor: 'transparent'
        }, { // Secondary yAxis
          title: {
            text: 'Precipitation',
            style: {
              display: 'none',
            }
          },
          labels: {
            enabled: false,
          },
          gridLineColor: 'transparent'
        }],
        tooltip: {
          shared: false
        },
        legend: {
          enabled: false,
        },
        plotOptions: {
          plotWidth: 10,
          column: {
            zones: [{
              value: 28,
              color: '#56156C'
            }, {
              color: '#B422B6'
            }]
          }
        },
        series: [{
          name: 'Precipitation',
          type: 'column',
          yAxis: 1,
          data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7,
            27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7
          ],
        }, {
          name: 'Benchmark',
          type: 'spline',
          data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
            30, 30, 30, 30, 30, 30
          ],
          color: '#CA7CE5',
          lineWidth: 3,
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });
      //ooe machine B
      Highcharts.chart('controlooeMachineB', {
        chart: {
          animation: true,
          height: 100,
          margin: 0
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: ['1', '2', '3', '4', '5', '6'],
          crosshair: true,
          lineColor: '#cccccc',
          labels: {
            enabled: false
          }
        }],
        yAxis: [{ // Primary yAxis
          labels: {
            enabled: false,
          },
          title: {
            text: 'Temperature',
            style: {
              display: 'none',
            }
          },
          gridLineColor: 'transparent'
        }, { // Secondary yAxis
          title: {
            text: 'Precipitation',
            style: {
              display: 'none',
            }
          },
          labels: {
            enabled: false,
          },
          gridLineColor: 'transparent'
        }],
        tooltip: {
          shared: false
        },
        legend: {
          enabled: false,
        },
        plotOptions: {
          plotWidth: 10,
          column: {
            zones: [{
              value: 28,
              color: '#56156C'
            }, {
              color: '#B422B6'
            }]
          }
        },
        series: [{
          name: 'Precipitation',
          type: 'column',
          yAxis: 1,
          data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7,
            27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7
          ],
        }, {
          name: 'Benchmark',
          type: 'spline',
          data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
            30, 30, 30, 30, 30, 30
          ],
          color: '#CA7CE5',
          lineWidth: 3,
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });
      //ooe machine C
      Highcharts.chart('controlooeMachineC', {
        chart: {
          animation: true,
          height: 100,
          margin: 0
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: ['1', '2', '3', '4', '5', '6'],
          crosshair: true,
          lineColor: '#cccccc',
          labels: {
            enabled: false
          }
        }],
        yAxis: [{ // Primary yAxis
          labels: {
            enabled: false,
          },
          title: {
            text: 'Temperature',
            style: {
              display: 'none',
            }
          },
          gridLineColor: 'transparent'
        }, { // Secondary yAxis
          title: {
            text: 'Precipitation',
            style: {
              display: 'none',
            }
          },
          labels: {
            enabled: false,
          },
          gridLineColor: 'transparent'
        }],
        tooltip: {
          shared: false
        },
        legend: {
          enabled: false,
        },
        plotOptions: {
          plotWidth: 10,
          column: {
            zones: [{
              value: 28,
              color: '#56156C'
            }, {
              color: '#B422B6'
            }]
          }
        },
        series: [{
          name: 'Precipitation',
          type: 'column',
          yAxis: 1,
          data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7,
            27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7
          ],
        }, {
          name: 'Benchmark',
          type: 'spline',
          data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
            30, 30, 30, 30, 30, 30
          ],
          color: '#CA7CE5',
          lineWidth: 3,
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });
      //ooe machine D
      Highcharts.chart('controlooeMachineD', {
        chart: {
          animation: true,
          height: 100,
          margin: 0
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
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
        xAxis: [{
          categories: ['1', '2', '3', '4', '5', '6'],
          crosshair: true,
          lineColor: '#cccccc',
          labels: {
            enabled: false
          }
        }],
        yAxis: [{ // Primary yAxis
          labels: {
            enabled: false,
          },
          title: {
            text: 'Temperature',
            style: {
              display: 'none',
            }
          },
          gridLineColor: 'transparent'
        }, { // Secondary yAxis
          title: {
            text: 'Precipitation',
            style: {
              display: 'none',
            }
          },
          labels: {
            enabled: false,
          },
          gridLineColor: 'transparent'
        }],
        tooltip: {
          shared: false
        },
        legend: {
          enabled: false,
        },
        plotOptions: {
          plotWidth: 10,
          column: {
            zones: [{
              value: 28,
              color: '#56156C'
            }, {
              color: '#B422B6'
            }]
          }
        },
        series: [{
          name: 'Precipitation',
          type: 'column',
          yAxis: 1,
          data: [27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7,
            27.6, 28.8, 21.7, 34.1, 23.6, 34.7, 27.6, 28.8, 21.7, 34.1, 23.6, 34.7
          ],
        }, {
          name: 'Benchmark',
          type: 'spline',
          data: [30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30,
            30, 30, 30, 30, 30, 30
          ],
          color: '#CA7CE5',
          lineWidth: 3,
          dataLabels: {
            enabled: false,
          },
          marker: {
            enabled: false
          }
        }]
      });
      $(document).ready(function() {
        $('body').bootstrapMaterialDesign();
      });
      //Ticket by Priority        
      Highcharts.chart('priority', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}:  < b > {point.percentage: .1 f} % < /b>'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
        credits: {
          enabled: false
        },
        exporting: {
          enabled: false
        },
        plotOptions: {
          pie: {
            colors: ['#CA7CE5', '#bf05ff', '#B422B6', '#F79C92'],
            dataLabels: {
              enabled: true,
              distance: 10,
              style: {
                fontWeight: 'bold',
                color: 'white'
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
          innerSize: '70%',
          data: [
            ['16.67%', 17],
            ['25%', 25],
            ['41.67%', 42],
            ['16.67%', 17]
          ]
        }]
      });
      // Ticket age by agent
      Highcharts.chart('agent', {
        chart: {
          type: 'bar',
          backgroundColor: '#ffffff',
          color: 'black',
          height: 370
        },
        title: {
          text: ''
        },
        exporting: {
          enabled: false
        },
        credits: {
          enabled: false
        },
        xAxis: {
          categories: ['Emily Andreson', 'Jessica Brown', 'David patel', 'Sarah Michell', 'Sarah Johnson', 'Alexander Rodriguez'],
          title: {
            text: null
          }
        },
        yAxis: {
          min: 0,
          color: 'black',
          gridLineWidth: 1,
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
          data: [14, 90, 110, 50, 30, 20],
          color: '#bf05ff'
        }]
      });
      //House Work by agents
      Highcharts.chart('housework', {
        chart: {
          type: 'bar',
          backgroundColor: '#ffffff',
          color: 'black',
          height: 370
        },
        title: {
          text: ''
        },
        exporting: {
          enabled: false
        },
        credits: {
          enabled: false
        },
        xAxis: {
          categories: ['Adrews Klein', 'Brandon Kayakrish', 'Tony Brands'],
          title: {
            text: null
          }
        },
        yAxis: {
          min: 0,
          color: 'black',
          gridLineWidth: 1,
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
          data: [24.30, 17, 5.73],
          color: '#bf05ff'
        }]
      });
      //Revenue Company Subscription
      Highcharts.chart('ticketsource', {
        chart: {
          type: 'treemap',
          height: 350
        },
        credits: {
          enabled: true
        },
        title: false,
        tooltip: {
          enabled: true,
          borderWidth: 0,
          outside: true,
          useHTML: true,
          className: 'test',
          formatter() {
            return this.point.options.name;
          }
        },
        series: [{
          cursor: 'pointer',
          borderWidth: 2,
          borderColor: '#fff',
          //type: "treemap",
          layoutAlgorithm: 'strip',
          states: {
            hover: {
              borderColor: undefined,
              brightness: -0.1
            }
          },
          data: [{
            id: 'A',
            name: '',
            color: "#BF05FF"
          }, {
            name: 'Web Form',
            parent: 'A',
            color: "#B422B6",
            value: 97
          }, {
            name: 'Customer Portal',
            parent: 'A',
            color: "#F79C92",
            value: 81
          }, {
            name: 'Agent Portal',
            parent: 'B',
            color: "#bf05ff",
            value: 83
          }, {
            name: 'Email',
            color: "#CA7CE5",
            parent: 'A',
            value: 39
          }, ],
          title: {
            text: 'Fruit consumption'
          },
          dataLabels: {
            align: 'left',
            verticalAlign: 'top',
            useHTML: true,
            allowOverlap: true,
            crop: false,
            overflow: 'allow',
            formatter() {
              // Get the font colour based on the background colour of the section
              let fontColor = '#FFF';
              // if (this.point.categoryColor === Theme.HighContrastAAA) {
              //   fontColor = Color.getFontColor(this.point.color, true, Theme.HighContrastAAA);
              // }
              const value = this.point.percent ? this.point.percent : this.point.value;
              let label = ' < div style = "color: ' + fontColor + ';">< div class = "data-label ' + 'bottom' + (true === true ? ' scaled' : '') + '" >< div class = "value" > ' + value + ' < /div>< div class = "data-label-desc" >< span class = "name" > ' +
              this.point.name + ' < /span>';
              if (this.point.iconClass) {
                label = label + ' < i class = "icon ' + this.point.iconClass + '" > < />';
              }
              label = label + ' < /div>< /div>< /div>';
              return label;
            }
          },
          point: {
            events: {
              click() {
                if (true) {
                  const event = WindowFills.customEvent(window, 'click.treemap', {
                    detail: this.options
                  });
                  self.container.dispatchEvent(event);
                } else {
                  return;
                }
              }
            }
          }
        }],
        legend: {
          enabled: false
        },
        credits: {
          enabled: false
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        }
      });
      //Tickets By Type
      Highcharts.chart('ticketsbytype', {
        chart: {
          type: 'bar',
          backgroundColor: '#ffffff',
          color: 'black',
          height: 350
        },
        title: {
          text: ''
        },
        exporting: {
          enabled: false
        },
        credits: {
          enabled: false
        },
        xAxis: {
          categories: ['Query', 'Indcident', 'Feture Request'],
          title: {
            text: null
          }
        },
        yAxis: {
          min: 0,
          color: 'black',
          gridLineWidth: 1,
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
          data: [150, 75, 75],
          color: '#bf05ff'
        }]
      });
      // SLR Adherence 
      Highcharts.chart('adherence', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}:  < b > {point.percentage: .1 f} % < /b>'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
        credits: {
          enabled: false
        },
        exporting: {
          enabled: false
        },
        plotOptions: {
          pie: {
            colors: ['#F79C92', '#bf05ff', ],
            dataLabels: {
              enabled: true,
              distance: 10,
              style: {
                fontWeight: 'bold',
                color: 'white'
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
          innerSize: '70%',
          data: [
            ['52.19%', 50],
            ['47.81%', 50]
          ]
        }]
      });
      // Customer satisfaction 
      Highcharts.chart('customersatisfaction', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}:  < b > {point.percentage: .1 f} % < /b>'
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
        credits: {
          enabled: false
        },
        exporting: {
          enabled: false
        },
        plotOptions: {
          pie: {
            colors: ['#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', ],
            dataLabels: {
              enabled: true,
              distance: 10,
              style: {
                fontWeight: 'bold',
                color: 'white'
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
          innerSize: '70%',
          data: [
            ['15%', 15],
            ['5%', 5],
            ['4.67%', 5],
            ['39.33%', 40],
            ['', 40],
          ]
        }]
      });
      // Resolution Time vs. Response Time
      Highcharts.chart('resolutiontime', {
        chart: {
          type: 'area'
        },
        title: {
          text: '',
          align: 'left'
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        subtitle: {
          text: '',
          align: 'left'
        },
        xAxis: {
          categories: ['04/8/2024', '04/9/2024', '04/10/2024', '04/11/2024', '04/12/2024', '04/13/2024', '04/14/2024', '04/15/2024', '04/16/2024', '04/17/2024', '04/18/2024', '04/19/2024', '04/20/2024', '04/21/2024', '04/22/2024'],
        },
        yAxis: {
          title: {
            text: 'TWh',
          }
        },
        legend: {
          layout: 'horizontal',
          align: 'left',
          x: 600,
          verticalAlign: 'top',
          y: 40,
          floating: false,
        },
        credits: {
          enabled: false
        },
        series: [{
          name: 'Avg. Resolution Time',
          data: [
            45.67, 27.75, 39.25, 29.67, 38.60, 32, 26.43, 44.43, 32.25, 34, 31.83, 30, 30.75, 37, 60
          ],
          color: '#CA7CE5',
          dataLabels: {
            enabled: true,
          }
        }, {
          name: 'Avg. Response Time',
          data: [
            21.33, 13.50, 7, 9.67, 16.80, 17, 17.43, 18, 19, 21, 20, 14.67, 15.25, 21, 23
          ],
          color: '#56156C',
          dataLabels: {
            enabled: true,
          }
        }, ]
      });
      // Ticket Volume vs. Satisfaction Rate
      Highcharts.chart('ticketvolume', {
        chart: {
          type: 'spline',
          scrollablePlotArea: {
            minWidth: 700
          }
        },
        title: {
          text: '',
          align: 'left'
        },
        subtitle: {
          text: '',
          align: 'left'
        },
        navigation: {
          buttonOptions: {
            enabled: false
          }
        },
        credits: {
          enabled: false
        },
        xAxis: {
          // tickInterval: 7 * 24 * 3600 * 1000, 
          tickWidth: 0,
          gridLineWidth: 0,
          labels: {
            align: 'left',
            x: -15,
            y: 55
          },
          categories: ['3/24/2024', '3/25/2024', '3/26/2024', '3/27/2024', '3/28/2024', '3/29/2024', '3/30/2024', '3/31/2024', '4/1/2024', '4/2/2024', '4/3/2024', '4/4/2024', '4/5/2024', '4/6/2024', '4/7/2024', '4/8/2024', '4/9/2024', '4/10/2024', '4/11/2024', '4/12/2024', '4/13/2024', '4/14/2024', '4/15/2024', '4/16/2024', '4/17/2024'],
          crosshair: false
        },
        yAxis: [{ // left y axis
          title: {
            text: 'Tickets'
          },
          labels: {
            align: 'left',
            x: 3,
            y: 16,
            format: '{value:.,0f}'
          },
          showFirstLabel: true
        }, { // right y axis
          linkedTo: 0,
          gridLineWidth: 0,
          opposite: true,
          title: {
            text: 'Satisfaction Rate'
          },
          labels: {
            align: 'right',
            x: -3,
            y: 16,
            format: '{value:.,0f}%'
          },
          showFirstLabel: true,
        }],
        legend: {
          align: 'center',
          verticalAlign: 'top',
          borderWidth: 0
        },
        tooltip: {
          shared: true,
          crosshairs: true
        },
        plotOptions: {
          series: {
            cursor: 'pointer',
            className: 'popup-on-click',
            marker: {
              lineWidth: 1
            }
          }
        },
        series: [{
          name: 'Tickets',
          lineWidth: 1,
          data: [
            40, 42.9, 22, 53.88, 45, 55, 40.40, 39, 38, 55.94, 49, 68.79, 42.05, 52, 40, 59.28, 50, 52.95, 53, 37.84, 38.84, 78.26, 40.49, 50, 18
          ],
          color: '#bf05ff',
          dataLabels: {
            enabled: true,
          },
          marker: {
            radius: 4
          }
        }, {
          name: 'Satisfaction Rate',
          lineWidth: 1,
          data: [
            9, 3, 7, 6, 3, 11, 5, 2, 8, 5, 8, 4, 3, 9, 8, 7, 13, 7, 6, 3, 7, 7, 4, 2, 1
          ],
          color: '#B422B6',
          dataLabels: {
            enabled: true,
          }
        }, ]
      });
      // Unresolved Tickets by Status and Priority
      Highcharts.chart('unresolvedtickets', {
        chart: {
          type: 'heatmap',
          marginTop: 20,
          marginBottom: 40,
          plotBorderWidth: 1,
          height: 358,
        },
        title: {
          text: '',
          style: {
            fontSize: '1em'
          }
        },
        xAxis: {
          categories: ['low', 'Medium', 'High', 'Urgent']
        },
        yAxis: {
          categories: ['Waiting to Be Resolved', 'Waiting on Customer', 'Pending', 'Open'],
          title: null,
          reversed: true
        },
        accessibility: {
          point: {
            descriptionFormat: '{(add index 1)}. ' + '{series.xAxis.categories.(x)} sales ' + '{series.yAxis.categories.(y)}, {value}.'
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
          enabled: false,
          align: 'right',
          layout: 'vertical',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
          symbolHeight: 280
        },
        // tooltip: {
        //   format: ' < b > {series.xAxis.categories.(point.x)} < /b> sold < br > ' +
        //   ' < b > {point.value} < /b> items on  < br > ' +
        //   ' < b > {series.yAxis.categories.(point.y)} < /b>'
        // },
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
          data: [
            [0, 0, 2],
            [0, 1, 2],
            [0, 2, 1],
            [0, 3, 3],
            [1, 0, 4],
            [1, 1, 2],
            [1, 2, 2],
            [1, 3, 1],
            [2, 0, 5],
            [2, 1, 2],
            [2, 2, 5],
            [2, 3, 2],
            [3, 0, 1],
            [3, 1, 1],
            [3, 2, 5],
            [3, 3, 2],
          ],
          dataLabels: {
            enabled: true,
            color: '#000000'
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
      // Agent Workload by Ticket Type
      Highcharts.chart('agentworkload', {
        chart: {
          type: 'bar',
          backgroundColor: '#ffffff',
          color: 'black',
        },
        title: {
          text: ''
        },
        exporting: {
          enabled: false
        },
        credits: {
          enabled: false
        },
        xAxis: {
          categories: ['Feture Request', 'Question', 'Refunds', 'Incident', 'Bulk Orders', 'Refunds and Returns'],
          title: {
            text: null
          }
        },
        yAxis: {
          min: 0,
          color: 'black',
          gridLineWidth: 1,
          title: {
            text: '',
            align: 'high'
          },
          labels: {
            format: '{value}'
          }
        },
        legend: {
          align: 'center',
          layout: 'horizontal',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
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
          name: 'Resolved',
          data: [6.5, 4.5, 6.5, 10.5, 2, 8],
          color: '#bf05ff'
        }, {
          name: 'Unresolved',
          data: [2.5, 6, 6.5, 6.9, 8.9, 12],
          color: '#F79C92'
        }]
      });
      
      
      ///////////////////////////////////////////////////////////////////////////////////////////////*/
      //Agent Customer satisfaction 
      Highcharts.chart('agentcustomersatisfaction', {
        chart: {
          plotBackgroundColor: null,
          plotBorderWidth: 0,
          plotShadow: false
        },
        title: {
          text: '',
          align: 'center',
          verticalAlign: 'middle',
          y: 60
        },
        tooltip: {
          pointFormat: '{series.name}:  < b > {point.percentage: .1 f} % < /b>'
        },
        legend: {
          enabled: true,
          align: 'right',
          layout: 'vertical',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
          symbolHeight: 280
        },
        accessibility: {
          point: {
            valueSuffix: '%'
          }
        },
        credits: {
          enabled: false
        },
        exporting: {
          enabled: false
        },
        plotOptions: {
          pie: {
            colors: ['#56156C', '#F79C92', '#bf05ff', '#B422B6', '#CA7CE5', ],
            dataLabels: {
              enabled: true,
              distance: 10,
              style: {
                fontWeight: 'bold',
                color: 'white'
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
          innerSize: '70%',
          data: [
            ['12.33%', 12],
            ['15.07%', 15],
            ['17.81%', 18],
            ['27.4%', 27],
            ['27.4%', 27],
          ]
        }]
      });
      //Tickets by Month
      Highcharts.chart('ticketbymonth', {
        chart: {
          type: 'spline',
        },
        title: {
          text: ''
        },
        xAxis: {
          categories: ['Oct 2023', 'Nov 2023', 'Dec 2023', 'Jan 2024', 'Feb 2024', 'Mar 2024'],
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
        legend: {
          align: 'center',
          layout: 'horizontal',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
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
          name: 'Assigned',
          marker: {
            symbol: 'circle'
          },
          data: [10, 8, 7, 8, 7, 5],
          color: '#BF05FF',
          dataLabels: {
            enabled: true,
          }
        }, {
          name: 'Resolved',
          marker: {
            symbol: 'circle'
          },
          data: [4, 5, 4, 4, 2, 4],
          color: '#F79C92',
          dataLabels: {
            enabled: true,
          }
        }]
      })
	
}



function returnByReasonControl(id){
		var org = $("#controlOrganization").val();
		var orgDiv = $("#controlDivision").find('option:selected').text();
		var location = $("#controlLocation").find('option:selected').text();
		var fromDate = $("#fromDate7").val();
		var toDate = $("#toDate7").val();
	
		$.ajax({
		url: "dashboard-return-item-by-reason-by-type?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&type=" + id,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			//var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
					
	var jsonData = JSON.parse(response.body);
	var allData = jsonData.dashboardData;
	
	// Step 1: Create an object to store aggregated data by monthYear and reasonName
	var aggregatedData = {};
	
	// Loop through the dashboardData and accumulate totalCount for each monthYear and reasonName
	allData.forEach(item => {
	    var monthYear = item.monthYear;
	    var reasonName = item.reasonName;
	    var totalCount = item.totalCount;
	
	    // Initialize the monthYear object if it doesn't exist
	    if (!aggregatedData[monthYear]) {
	        aggregatedData[monthYear] = {};
	    }
	
	    // Initialize the reasonName array if it doesn't exist for the specific monthYear
	    if (!aggregatedData[monthYear][reasonName]) {
	        aggregatedData[monthYear][reasonName] = 0;
	    }
	
	    // Accumulate the totalCount
	    aggregatedData[monthYear][reasonName] += totalCount;
	});
	
	// Step 2: Prepare the series array
	var series = [
	    { name: 'Returned Due to Damage Product', data: [], color: '#B422B6' },
	    { name: 'Poor Quality', data: [], color: '#F79C92' },
	    { name: 'UnMatched Specimen', data: [], color: '#FPOK92' },
	    { name: 'Size Issue', data: [], color: '#bf05ff' }
	];
	
	// Step 3: Populate the data for each reasonName in the series array
	var monthYeartList = [...new Set(allData.map(item => item.monthYear))];
	
	monthYeartList.forEach(monthYear => {
	    // Push totalCount for each reason into the series data
	    series[0].data.push(aggregatedData[monthYear]["Returned Due to Damage Product"] || 0);
	    series[1].data.push(aggregatedData[monthYear]["Poor Quality"] || 0);
	    series[2].data.push(aggregatedData[monthYear]["UnMatched Specimen"] || 0);
	    series[3].data.push(aggregatedData[monthYear]["Size Issue"] || 0);
	});
	
	console.log(series);
			
			
			
	Highcharts.chart('returneditems', {
    chart: {
        type: 'column',
        animation: true,
        height: 250,
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
    legend: {
        enabled: true,  // Enable the legend
        layout: 'horizontal',  // Arrange legend items horizontally
        align: 'center',  // Align it in the center
        verticalAlign: 'bottom',  // Place it at the bottom
        itemStyle: {
            fontWeight: 'normal',
            fontSize: '14px',
            color: '#333'  // Set the color of legend text
        }
    },
    xAxis: {
        categories: monthYeartList,
        rotation: '45deg',
    },
    yAxis: {
        title: {
            text: ''
        },
        labels: {enabled: false},
        stackLabels: {
            enabled: true
        }
    },
    plotOptions: {
        series: {
            stacking: 'normal',
            dataLabels: {
                enabled: false,
                color: '#ffffff'
            }
        }
    },
    series: series
});

			
		},
		error: function(error) {
			console.error(error);
		}
	});
	
	}
	
function rateOfReturnControl(id){
	var org = $("#controlOrganization").val();
	var orgDiv = $("#controlDivision").find('option:selected').text();
	var location = $("#controlLocation").find('option:selected').text();
	var fromDate = $("#fromDate7").val();
	var toDate = $("#toDate7").val();
	
	$.ajax({
		url: "dashboard-rate-of-return-by-type?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv+ "&type=" + id,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
			
			
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			   
			    if (id === '0') {
			         monthYearList.push(item.monthYear);
			   		 purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			   		 salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			    
			    if (id === '1') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			        //purchaseRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(0);
			    }
			    
			     if (id === '2') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(0);
			        //salesRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			}
			
			
			
		Highcharts.chart('rateof-return', {
			chart: {
				type: 'column',
				zoomType: 'xy',
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
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
			xAxis: [{
				categories: monthYearList,
				crosshair: true
			}],
			yAxis: [{
				labels: {format: '{value} %', },
				title: {
					text: '',
				},
			}
			],
			plotOptions: {
				column: {
					pointPadding: 0,
					borderWidth: 0
				}
			},
			legend: {
				enabled: true
			},
			series: [{
				name: 'Purchase',
				data: purchaseRateOfReturnList,
				tooltip: {
					valueSuffix: ' mm'
				},
				color: '#bf05ff',

			}, {
				name: 'Sales',
				data: salesRateOfReturnList,
				color: '#F79C92',
				margin: '0',

			}]
		});	
			
		},
		error: function(error) {
			console.error(error);
		}
	});
		
	}
	
	
function defectDensityControl(id){
	var org = $("#qualityOrganization").val();
	var orgDiv = $("#qualityDivision").find('option:selected').text();
	var location = $("#qualityLocation").find('option:selected').text();
	var fromDate = $("#fromDate3").val();
	var toDate = $("#toDate3").val();
	
		
	$.ajax({
		url: "dashboard-defect-density?fromDate=" + fromDate + "&toDate=" + toDate + 
		"&location=" + location + "&org=" + org + "&orgDiv=" + orgDiv,
		type: 'GET',
		dataType: 'json',
		success: function(response) {
			var jsonData = JSON.parse(response.body);
			var allData = jsonData.dashboardData;
			
			var monthYearList = [];
			var purchaseRateOfReturnList = [];
			var salesRateOfReturnList = [];
					
			for (var i = allData.length - 1; i >= 0; i--) {
			    var item = allData[i];
			   
			    if (id === '0') {
			         monthYearList.push(item.monthYear);
			   		 purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			   		 salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			    
			    if (id === '1') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(item.purchaseRateOfReturn);
			        //purchaseRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(0);
			    }
			    
			     if (id === '2') {
				 	monthYearList.push(item.monthYear);
			        purchaseRateOfReturnList.push(0);
			        //salesRateOfReturnList.push(Math.floor(Math.random() * 100) + 1);
			    	salesRateOfReturnList.push(item.salesRateOfReturn);
			    }
			}
			
			
			
		Highcharts.chart('defect-density', {
			chart: {
				animation: true,
				height: 250
			},
			navigation: {
				buttonOptions: {
					enabled: false
				}
			},
			title: {
				text: ''
			},
			subtitle: {
				text: ''
			},
			credits: {enabled: false},
			xAxis: [{
				categories: monthYearList,
				crosshair: true,
				lineColor: '#cccccc',
				rotation: '45deg'
			}],
			yAxis: {
				title: {
					text: '',
				},
				labels: {format: '{value} %', },
			},
			tooltip: {
				shared: false
			},
			plotOptions: {
				series: {
					dataLabels: {
						enabled: true,
						color: '#000000'
					}

				}
			},
			series: [{
				name: 'Purchase',
				type: 'line',
				color: '#F79C92',
				data: purchaseRateOfReturnList,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}

			}, {
				name: 'Sales',
				type: 'line',
				data: salesRateOfReturnList,
				color: '#B422B6',
				lineWidth: 1,
				dataLabels: {
					enabled: false,
				},
				marker: {
					enabled: false
				}
			}]
		});
					
			
		},
		error: function(error) {
			console.error(error);
		}
	});
}
	