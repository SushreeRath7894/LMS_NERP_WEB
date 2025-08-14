$(document).ready(()=>{
	// Data retrieved https://en.wikipedia.org/wiki/List_of_cities_by_average_temperature
	Highcharts.chart('bschart', {
	    chart: {
	        type: 'spline',
	        height: 170,
	    },
	    title: {
	        text: ''
	    },

	    xAxis: {
	        categories: [
	            'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
	        ]
	    },
	    yAxis: {
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: [{
	        name: '',
	        data: [
	            16000, 18200, 23100, 27900, 32200, 25604],
	        color: '#be05ff'
	    }],
	    credits: {
	        enabled: false
	    },
	    exporting: {
	        enabled :false
	    }
	});

	Highcharts.chart("nps", {
	    chart: {
	        type: "gauge",
	        plotBackgroundColor: null,
	        plotBackgroundImage: null,
	        plotBorderWidth: 0,
	        plotShadow: false,
	        height: 250,
	    },

	    title: {
	        text: "42 %",
	        align: "center",
	        verticalAlign: "center",
	        floating: true,
	        y: 70,
	        margin: 0,
	        style: { fontSize: "14", color: "#bf05ff" },
	    },
	    navigation: {
	        buttonOptions: {
	            enabled: false,
	        },
	    },
	    credits: { enabled: false },

	    pane: {
	        startAngle: -128,
	        endAngle: 127.9,
	        background: null,
	        center: ["50%", "50%"],
	        size: "100%",
	    },

	    // the value axis
	    yAxis: {
	        min: 0,
	        max: 100,
	        tickPosition: "inside",
	        tickColor: "transparent",
	        tickLength: 0,
	        tickWidth: 0,
	        minorTickInterval: null,
	        labels: {
	            enabled: false,
	        },
	        lineWidth: 0,
	        plotBands: [
	            {
	                from: 0,
	                to: 40,
	                color: "#bf05ff",
	                thickness: 20,
	            },
	            {
	                from: 40,
	                to: 100,
	                color: "#f4ddfc",
	                thickness: 20,
	            },
	        ],
	    },

	    series: [
	        {
	            name: "",
	            data: [40],
	            tooltip: {
	                valueSuffix: " km/h",
	            },
	            dataLabels: {
	                enabled: false,
	            },
	            dial: {
	                radius: "70%",
	                backgroundColor: "gray",
	                baseWidth: 6,
	                baseLength: "0%",
	                rearLength: "0%",
	            },
	            pivot: {
	                backgroundColor: "gray",
	                radius: 3,
	            },
	        },
	    ],
	});

	Highcharts.chart('clm', {
	    chart: {
	        type: 'spline',
	        height:225,
	    },
	    title: {
	        text: ''
	    },

	    xAxis: {
	        categories: [
	            'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
	        ]
	    },
	    yAxis: {
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: [{
	        name: '',
	        data: [
	            2400, 2600, 2900, 2560, 2200, 2100],
	        color: '#be05ff'
	    }],
	    credits: {
	        enabled: false
	    },
	    exporting: {
	        enabled :false
	    }
	});



	Highcharts.chart('wcr', {
	    chart: {
	        type: 'spline',
	        height:205,
	    },
	    title: {
	        text: ''
	    },

	    xAxis: {
	        categories: [
	            'Sep', 'Oct', 'Nov', 'Dec',
	        ]
	    },
	    yAxis: {
	    },
	    plotOptions: {
	        line: {
	            dataLabels: {
	                enabled: true
	            },
	            enableMouseTracking: false
	        }
	    },
	    series: [{
	        name: '',
	        data: [
	            2400, 2600, 2900, 2560, 2200, 2100],
	        color: '#be05ff'
	    }],
	    credits: {
	        enabled: false
	    },
	    exporting: {
	        enabled :false
	    }
	});
	
	
})
