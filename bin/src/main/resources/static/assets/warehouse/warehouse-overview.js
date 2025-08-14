function warehouseOverviewHighchart(){
	 
        //warehouseInventoryValue
        Highcharts.chart('warehouseInventoryValue', {
            chart: {
                type: 'pie',
                animation: true,
                height: 110,
                backgroundColor: 'transparent'
            },
            credits: { enabled: false },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            colors: ['#2091c8', '#96c3e1', '#d23148'],
            navigation: {
                buttonOptions: {
                    enabled: false
                }
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
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
                }
            },
            series: [{
                name: '',
                data: [["Completed", 40], ["In-Progress", 20], ["Pending", 30]],
                size: '100%',
                innerSize: '50%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '30%'
                }
            }]
        });
        //warehouseRestockingCost
        Highcharts.chart('warehouseRestockingCost', {
            chart: {
                type: 'pie',
                animation: true,
                height: 110,
                backgroundColor: 'transparent'
            },
            credits: { enabled: false },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            colors: ['#2091c8', '#96c3e1', '#d23148'],
            navigation: {
                buttonOptions: {
                    enabled: false
                }
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
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
                }
            },
            series: [{
                name: '',
                data: [["Completed", 40], ["In-Progress", 20], ["Pending", 30]],
                size: '100%',
                innerSize: '50%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '30%'
                }
            }]
        });
        //warehouseInventoryStock
        Highcharts.chart('warehouseInventoryStock', {
            chart: {
                type: 'pie',
                animation: true,
                height: 110,
                backgroundColor: 'transparent'
            },
            credits: { enabled: false },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            colors: ['#2091c8', '#96c3e1', '#d23148'],
            navigation: {
                buttonOptions: {
                    enabled: false
                }
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
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + this.y + ' %';
                }
            },
            series: [{
                name: '',
                data: [["Completed", 40], ["In-Progress", 20], ["Pending", 30]],
                size: '100%',
                innerSize: '50%',
                showInLegend: false,
                dataLabels: {
                    enabled: true,
                    padding: 0,
                    width: 20,
                    distance: '30%'
                }
            }]
        });
}