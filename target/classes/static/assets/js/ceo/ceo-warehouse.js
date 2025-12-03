//ceo Warehouse Distribution of Transportation Related Costs
Highcharts.chart("ceoWarehouseDistributionOfTransportationRelatedCosts", {
  chart: {
    type: "pie",
    animation: true,
    height: 200,
  },
  credits: { enabled: false },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#d4c9d8"],
  yAxis: {
    title: {
      text: "",
    },
  },
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  tooltip: {
    formatter: function () {
      return "<b>" + this.point.name + "</b>: " + this.y + " %";
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Transportation Costs", 43],
        ["Warehousing Costs", 26],
        ["Inventory Carrying Costs", 21],
        ["Administrative Costs", 10],
        ["Order Processing Costs", 6],
      ],
      size: "50%",
      innerSize: "45%",
      showInLegend: false,
      dataLabels: {
        distance: 10,
        enabled: true,
        format: "{point.percentage:.1f} %",
      },
    },
  ],
});
//ceo Warehouse Warehouse Operating Cost Distribution
Highcharts.chart("ceoWarehouseOperatingCostDistribution", {
  chart: {
    type: "pie",
    animation: true,
    height: 260,
  },
  credits: { enabled: false },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#d4c9d8"],
  yAxis: {
    title: {
      text: "",
    },
  },
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  tooltip: {
    formatter: function () {
      return "<b>" + this.point.name + "</b>: " + this.y + " %";
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Order Picking", 43],
        ["Storage", 26],
        ["Shipping", 21],
        ["Receiving", 10],
        ["Other", 6],
      ],
      size: "50%",
      innerSize: "45%",
      showInLegend: true,
      dataLabels: {
        enabled: true,
        distance: 10,
        format: "{point.percentage:.1f} %",
      },
    },
  ],
});
//ceo Warehouse Operating Expenses for This Year
Highcharts.chart("ceoWarehouseOperatingExpensesForThisYear", {
  chart: {
    type: "pie",
    animation: true,
    height: 200,
  },
  credits: { enabled: false },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff"],
  yAxis: {
    title: {
      text: "",
    },
  },
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  tooltip: {
    formatter: function () {
      return "<b>" + this.point.name + "</b>: " + this.y + " %";
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Administrative Costs", 25],
        ["Inventory", 32],
        ["Storage", 43],
        ["Transportation", 43],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: true,
      dataLabels: {
        enabled: true,
        format: "{point.percentage:.1f} %",
        distance: -25,
        style: { fontSize: "10px" },
      },
    },
  ],
});
//ceo Warehouse Shipments By Country Gauge
Highcharts.chart("ceoWarehouseShipmentsByCountryGauge", {
  chart: {
    type: "pie",
    animation: true,
    height: 176,
  },
  credits: { enabled: false },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff"],
  yAxis: {
    title: {
      text: "",
    },
  },
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  tooltip: {
    formatter: function () {
      return "<b>" + this.point.name + "</b>: " + this.y + " %";
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Administrative Costs", 25],
        ["Inventory", 32],
        ["Storage", 43],
        ["Transportation", 43],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: true,
      dataLabels: {
        enabled: true,
        format: "{point.percentage:.1f} %",
        distance: -15,
        style: { fontSize: "10px" },
      },
    },
  ],
});
//ceo Warehouse Trailer Utilization Rate
Highcharts.chart("ceoWarehouseTrailerUtilizationRate", {
  chart: {
    type: "column",
    animation: true,
    height: 266,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    categories: [
      "Jan 2023",
      "Feb 2023",
      "Mar 2023",
      "Apr 2023",
      "May 2023",
      "Jun 2023",
      "Jul 2023",
      "Aug 2023",
      "Sep 2023",
      "Oct 2023",
      "Nov 2023",
      "Dec 2023",
    ],
  },
  yAxis: {
    gridLineColor: "transparent",
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: false,
      },
    },
  },

  series: [
    {
      name: "This Year",
      data: [13, 15, 15, 34, 23, 24, 12, 15, 34, 19, 23, 21],
      color: "#bf05ff",
    },
    {
      name: "Last Year",
      data: [13, 15, 15, 34, 23, 12, 32, 25, 43, 26, 23, 13],
      color: "#ca7ce5",
    },
  ],
});
//ceo Warehouse Use Of Packing Material
Highcharts.chart("ceoWarehouseUseOfPackingMaterial", {
  chart: {
    type: "bar",
    animation: true,
    height: 299,
  },
  title: {
    text: "",
  },
  credits: false,
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    categories: ["Line A", "Line B", "Line C"],
  },
  yAxis: {
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
      },
    },
    bar: {
      grouping: false,
      shadow: false,
    },
  },
  series: [
    {
      name: "Company Average",
      data: [4, 5, 2],
      color: "#e6a5fd",
      pointPadding: 0.1,
    },
    {
      name: "Job Tenure",
      data: [3, 10, 8],
      color: "#bf05ff",
      pointPadding: 0.3,
    },
  ],
});
//ceo Warehouse Operating Ratio
Highcharts.chart("ceoWarehouseOperatingRatio", {
  chart: {
    type: "bar",
    animation: true,
    height: 150,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    categories: ["Q1 2023", "Q2 2023", "Q3 2023", "Q4 2023", "Q1 2024"],
  },
  yAxis: {
    gridLineColor: "transparent",
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },
  },
  legend: {
    enabled: false,
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        color: "#000000",
        format: "{y}%",
      },
    },
  },

  series: [
    {
      name: "",
      data: [13, 15, 15, 34, 23],
      color: "#bf05ff",
    },
  ],
});
//ceo Warehouse Shipments By Country
Highcharts.chart("ceoWarehouseShipmentsByCountry", {
  chart: {
    type: "line",
    animation: true,
    height: 200,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  yAxis: {
    title: {
      text: "",
    },
  },

  xAxis: {
    categories: [
      "Jan 2023",
      "Feb 2023",
      "Mar 2023",
      "Apr 2023",
      "May 2023",
      "Jun 2023",
      "Jul 2023",
      "Aug 2023",
      "Sep 2023",
      "Oct 2023",
      "Nov 2023",
      "Dec 2023",
    ],
  },

  plotOptions: {
    series: {
      lineWidth: 2,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "Country 1",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 90],
      marker: false,
      color: "#b605f3",
    },
    {
      name: "Country 2",
      data: [114, 30, 16, 19, 20, 24, 32, 30, 27, 29, 25, 19],
      marker: false,
      color: "#f58d68",
    },
    {
      name: "Country 3",
      data: [91, 95, 111, 142, 189, 118, 102, 133, 110, 150, 210, 21],
      marker: false,
      color: "#51a4d6",
    },
    {
      name: "Country 4",
      data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
      marker: false,
      color: "#9792e8",
    },
  ],

  responsive: {
    rules: [
      {
        condition: {
          maxWidth: 500,
        },
        chartOptions: {
          legend: {
            layout: "horizontal",
            align: "center",
            verticalAlign: "bottom",
          },
        },
      },
    ],
  },
});
//ceo Warehouse Inventory Carrying Costs
Highcharts.chart("ceoWarehouseInventoryCarryingCosts", {
  chart: {
    type: "column",
    animation: true,
    height: 243,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    categories: ["Admin", "Freight", "Risk", "Service", "Storage"],
  },
  yAxis: {
    gridLineColor: "transparent",
    title: {
      text: "Response Time in Secs",
    },
    labels: {
      enabled: false,
    },
  },
  legend: {
    enabled: false,
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        color: "#000000",
      },
    },
  },

  series: [
    {
      name: "",
      data: [13, 15, 15, 34, 23],
      color: "#bf05ff",
    },
  ],
});
//ceo Warehouse Average Dwell Time for This Week
Highcharts.chart("ceoWarehouseAverageDwellTimeforThisWeek", {
  chart: {
    type: "column",
    backgroundColor: "transparent",
    height: 303.5,
  },
  colors: [
    "#56156c",
    "#f79c92",
    "#ca7ce5",
    "#bf05ff",
    "#d4c9d8",
    "#9792e8",
    "#51a4d6",
  ],
  plotOptions: {
    column: {
      colorByPoint: true,
    },
  },
  credits: false,
  title: {
    text: "",
  },
  subtitle: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    type: "category",
    title: {
      text: "",
    },
    lineColor: "#cccccc",
    labels: {
      rotation: 0,
      style: {
        fontSize: "11px",
      },
    },
  },
  yAxis: {
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },
    gridLineColor: "transparent",
  },
  legend: {
    enabled: false,
  },
  tooltip: {
    enabled: false,
  },
  series: [
    {
      name: "",
      data: [
        ["Monday", 21],
        ["Tuesday", 109],
        ["Wednesday", 67],
        ["Thursday", 109],
        ["Friday", 67],
        ["Saturday", 109],
        ["Sunday", 67],
      ],
      pointWidth: 25,
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "center",
        style: {
          fontSize: "13px",
        },
      },
    },
  ],
});
//ceo Warehouse Inventory Turnover
Highcharts.chart("ceoWarehouseInventoryTurnover", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 230,
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
  credits: { enabled: false },
  xAxis: [
    {
      categories: ["Apr2021", "May2021", "Jun2021", "Jul2021", "Aug2021"],
      crosshair: true,
      labels: {
        enabled: false,
      },
    },
  ],
  yAxis: [
    {
      labels: {
        format: "{value}",
      },
      title: {
        text: "",
      },
    },
  ],
  series: [
    {
      name: "Unit Cost",
      type: "column",
      data: [27.6, 28.8, 21.7, 34.1, 43.2],
      color: "#ca7ce5",
    },
    {
      name: "Unit Cost Target",
      type: "line",
      data: [25, 25, 25, 25, 25],
      color: "#ea34a1",
      dataLabels: {
        enabled: true,
        color: "#000000",
      },
    },
  ],
});
//ceo Warehouse Average Monthly Inventory Accuracy
Highcharts.chart("ceoWarehouseAverageMonthlyInventoryAccuracy", {
  chart: {
    type: "line",
    animation: true,
    height: 180,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  yAxis: {
    title: {
      text: "",
    },
    label: { format: "{value}%" },
  },

  xAxis: {
    categories: [
      "Jan 2023",
      "Feb 2023",
      "Mar 2023",
      "Apr 2023",
      "May 2023",
      "Jun 2023",
      "Jul 2023",
      "Aug 2023",
      "Sep 2023",
      "Oct 2023",
      "Nov 2023",
      "Dec 2023",
    ],
  },

  plotOptions: {
    series: {
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "Inventory Accuracy",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 100],
      color: "#bf05ff",
    },
    {
      name: "Target",
      data: [40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40],
      color: "#f58d68",
      marker: false,
      dashStyle: "ShortDot",
    },
  ],

  responsive: {
    rules: [
      {
        condition: {
          maxWidth: 500,
        },
        chartOptions: {
          legend: {
            layout: "horizontal",
            align: "center",
            verticalAlign: "bottom",
          },
        },
      },
    ],
  },
});
//ceo Warehouse Inventory To Sales Ratio
Highcharts.chart("ceoWarehouseInventoryToSalesRatio", {
  chart: {
    type: "line",
    animation: true,
    height: 280,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  yAxis: {
    title: {
      text: "",
    },
    label: { format: "{value}%" },
  },

  xAxis: {
    categories: [
      "Jan 2023",
      "Feb 2023",
      "Mar 2023",
      "Apr 2023",
      "May 2023",
      "Jun 2023",
      "Jul 2023",
      "Aug 2023",
      "Sep 2023",
      "Oct 2023",
      "Nov 2023",
      "Dec 2023",
    ],
  },
  legend: { enabled: false },

  plotOptions: {
    series: {
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 100],
      color: "#bf05ff",
      marker: false,
    },
  ],

  responsive: {
    rules: [
      {
        condition: {
          maxWidth: 500,
        },
        chartOptions: {
          legend: {
            layout: "horizontal",
            align: "center",
            verticalAlign: "bottom",
          },
        },
      },
    ],
  },
});
//ceo Warehouse Picking Accuracy
Highcharts.chart("ceoWarehousePickingAccuracy", {
  chart: {
    type: "line",
    animation: true,
    height: 216,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  yAxis: {
    title: {
      text: "",
    },
    label: { format: "{value}%" },
  },

  xAxis: {
    categories: [
      "Jan 2023",
      "Feb 2023",
      "Mar 2023",
      "Apr 2023",
      "May 2023",
      "Jun 2023",
      "Jul 2023",
      "Aug 2023",
      "Sep 2023",
      "Oct 2023",
      "Nov 2023",
      "Dec 2023",
    ],
  },

  plotOptions: {
    series: {
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "Picking Accuracy",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 100],
      color: "#bf05ff",
      zones: [
        {
          value: 40,
          color: "#9792e8",
        },
        {
          color: "#bf05ff",
        },
      ],
    },
    {
      name: "Target",
      data: [40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40],
      color: "#f58d68",
      marker: false,
      dashStyle: "ShortDot",
    },
  ],

  responsive: {
    rules: [
      {
        condition: {
          maxWidth: 500,
        },
        chartOptions: {
          legend: {
            layout: "horizontal",
            align: "center",
            verticalAlign: "bottom",
          },
        },
      },
    ],
  },
});
//ceo Warehouse Perfect Order Rate
Highcharts.chart("ceoWarehousePerfectOrderRate", {
  chart: {
    type: "line",
    animation: true,
    height: 150,
  },
  title: {
    text: "",
  },
  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  yAxis: {
    title: {
      text: "",
    },
    label: { format: "{value}%" },
  },

  xAxis: {
    categories: [
      "Jan 2023",
      "Feb 2023",
      "Mar 2023",
      "Apr 2023",
      "May 2023",
      "Jun 2023",
      "Jul 2023",
      "Aug 2023",
      "Sep 2023",
      "Oct 2023",
      "Nov 2023",
      "Dec 2023",
    ],
  },
  legend: { enabled: false },

  plotOptions: {
    series: {
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 100],
      color: "#bf05ff",
      marker: false,
    },
  ],

  responsive: {
    rules: [
      {
        condition: {
          maxWidth: 500,
        },
        chartOptions: {
          legend: {
            layout: "horizontal",
            align: "center",
            verticalAlign: "bottom",
          },
        },
      },
    ],
  },
});
//ceo Warehouse On Time Shipping
Highcharts.chart("ceoWarehouseOnTimeShipping", {
  chart: {
    type: "pie",
    height: 180,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "87%",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 78,
    margin: 0,
    style: { fontSize: "15", color: "#000000" },
  },
  subtitle: {
    text: "Within Time Limit",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 91,
    margin: 0,
    style: { fontSize: "11", color: "#000000" },
  },
  credits: {
    enabled: false,
  },
  yAxis: {
    title: {
      text: "",
    },
  },
  plotOptions: {
    pie: {
      shadow: false,
      colors: ["#bf05ff", "#deaaf0"],
      allowPointSelect: true,
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Within Time Limit", 549],
        ["Out Of Time Limit", 73],
      ],
      size: "120%",
      innerSize: "80%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Warehouse Perfect Order Rate Gauge
Highcharts.chart("ceoWarehousePerfectOrderRateGauge", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 80,
    margin: 0,
  },

  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },

  pane: {
    startAngle: -89,
    endAngle: 88,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: 0,
    max: 100,
    // tickPixelInterval: 72,
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
        to: 80,
        thickness: 15,
        color: "#bf05ff",
      },
      {
        from: 80,
        to: 100,
        thickness: 15,
        color: "#f1d3fc",
      },
    ],
  },

  series: [
    {
      name: "",
      data: [68],
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
//ceo Warehouse Pick And Pack Cycle Time
Highcharts.chart("ceoWarehousePickAndPackCycleTime", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 130,
  },

  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },

  pane: {
    startAngle: -129,
    endAngle: 128,
    background: null,
    center: ["50%", "75%"],
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
        to: 80,
        thickness: 15,
        color: "#bf05ff",
      },
      {
        from: 80,
        to: 100,
        thickness: 15,
        color: "#f1d3fc",
      },
    ],
  },

  series: [
    {
      name: "",
      data: [68],
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
//ceo Warehouse Inventory Accuracy Gauge
Highcharts.chart("ceoWarehouseInventoryAccuracyGauge", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 100,
    margin: 0,
  },

  title: {
    text: "94%",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 30,
    margin: 0,
    style: { fontSize: "16", color: "#61426c" },
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },

  pane: {
    startAngle: -89,
    endAngle: 88,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: 0,
    max: 100,
    // tickPixelInterval: 72,
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
        to: 80,
        thickness: 15,
        color: "#bf05ff",
      },
      {
        from: 80,
        to: 100,
        thickness: 15,
        color: "#f1d3fc",
      },
    ],
  },

  series: [
    {
      name: "",
      data: [68],
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
//ceo Warehouse Equipment Utilization Rate
Highcharts.chart("ceoWarehouseEquipmentUtilizationRate", {
  chart: {
    animated: true,
    height: 220.5,
  },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },
  xAxis: {
    categories: ["Line A", "Line B", "Line C"],
    labels: {
      rotation: 0,
      style: { fontSize: "9px" },
    },
  },
  yAxis: {
    title: {
      text: "",
    },
  },

  series: [
    {
      type: "bar",
      name: "Value",
      data: [59, 83, 65],
      color: "#deaaf0",
    },
    {
      name: "Target",
      color: "transparent",
      lineColor: "transparent",
      data: [47, 82, 60],
      type: "spline",
      dataLabels: {
        enabled: false,
      },
      marker: {
        lineWidth: 2,
        fillColor: "#bf05ff",
      },
    },
  ],
});
//ceo Warehouse Pick And Pack Cycle Time Bar
Highcharts.chart("ceoWarehousePickAndPackCycleTimeBar", {
  chart: {
    animated: true,
    height: 175.5,
  },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },
  xAxis: {
    categories: ["Line A", "Line B", "Line C"],
    labels: {
      rotation: 0,
      style: { fontSize: "9px" },
    },
  },
  yAxis: {
    title: {
      text: "",
    },
  },

  series: [
    {
      type: "bar",
      name: "Value",
      data: [59, 83, 65],
      color: "#deaaf0",
    },
    {
      name: "Target",
      color: "transparent",
      lineColor: "transparent",
      data: [47, 82, 60],
      type: "spline",
      dataLabels: {
        enabled: false,
      },
      marker: {
        lineWidth: 2,
        fillColor: "#bf05ff",
      },
    },
  ],
});
//ceo Warehouse Order Cycle Time
Highcharts.chart("ceoWarehouseOrderCycleTime", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 305,
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
  credits: { enabled: false },
  xAxis: [
    {
      categories: [
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday",
      ],
      crosshair: true,
      labels: {
        enabled: false,
      },
    },
  ],
  yAxis: [
    {
      labels: {
        format: "{value}",
      },
      title: {
        text: "",
      },
    },
  ],
  series: [
    {
      name: "Unit Cost",
      type: "column",
      data: [27.6, 28.8, 21.7, 34.1, 43.2],
      color: "#ca7ce5",
    },
    {
      name: "Unit Cost Target",
      type: "line",
      data: [25, 25, 25, 25, 25],
      color: "#ea34a1",
      dataLabels: {
        enabled: true,
        color: "#000000",
      },
    },
  ],
});
//ceo Warehouse Pick And Pack Cost
Highcharts.chart("ceoWarehousePickAndPackCost", {
  chart: {
    polar: true,
    type: "line",
    height: 244,
  },

  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: {
    enabled: false,
  },
  title: {
    text: "",
  },
  subtitle: {
    text: "",
  },
  pane: {
    size: "80%",
  },

  xAxis: {
    categories: ["Line A", "Line B", "Line C"],
    tickmarkPlacement: "on",
    lineWidth: 0,
  },

  yAxis: {
    gridLineInterpolation: "triangle",
    lineWidth: 0,
    min: 0,
  },

  legend: {
    enabled: false,
  },

  series: [
    {
      name: "",
      data: [73, 19, 60],
      pointPlacement: "on",
      color: "#f79c92",
      dataLabels: { enabled: true },
    },
  ],

  responsive: {
    rules: [
      {
        condition: {
          maxWidth: 500,
        },
        chartOptions: {
          pane: {
            size: "70%",
          },
        },
      },
    ],
  },
});
//ceo Warehouse Inventory Turnover Line
Highcharts.chart("ceoWarehouseInventoryTurnoverLine", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 50,
  },
  title: {
    text: "",
  },
  subtitle: {
    text: "",
  },

  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    tickLength: 0,
    tickWidth: 0,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    categories: ["Jan", "Feb", "Mar", "Apr", "May"],
  },
  yAxis: {
    gridLineColor: "transparent",
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },
    showInLegend: false,
  },
  plotOptions: {
    line: {
      dataLabels: {
        enabled: false,
      },
      showInLegend: false,
    },
  },
  series: [
    {
      name: "",
      data: [
        16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2,
        23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9,
        32.2,
      ],
      color: "#b422b6",
      showInLegend: false,
      marker: {
        enabled: false,
      },
      dashStyle: "ShortDashDot",
    },
  ],
});
//ceo Warehouse Average Transportation Cost Line
Highcharts.chart("ceoWarehouseAverageTransportationCostLine", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 50,
  },
  title: {
    text: "",
  },
  subtitle: {
    text: "",
  },

  credits: {
    enabled: false,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    tickLength: 0,
    tickWidth: 0,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    categories: ["Jan", "Feb", "Mar", "Apr", "May"],
  },
  yAxis: {
    gridLineColor: "transparent",
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },
    showInLegend: false,
  },
  plotOptions: {
    line: {
      dataLabels: {
        enabled: false,
      },
      showInLegend: false,
    },
  },
  series: [
    {
      name: "",
      data: [
        16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2,
        23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9, 32.2, 16.0, 28.2, 23.1, 17.9,
        32.2,
      ],
      color: "#b422b6",
      showInLegend: false,
      marker: {
        enabled: false,
      },
      dashStyle: "ShortDashDot",
    },
  ],
});
//ceo Warehouse Avg Delivery Time In Days

(async () => {
  const topology = await fetch(
    "https://code.highcharts.com/mapdata/countries/in/custom/in-all-disputed.topo.json"
  ).then((response) => response.json());

  // Instantiate the map
  Highcharts.mapChart("ceoWarehouseAvgDeliveryTimeInDays", {
    chart: {
      map: topology,
      height: 320,
    },
    title: {
      text: "",
    },
    subtitle: {
      text: "",
    },
    accessibility: {
      point: {
        describeNull: false,
      },
    },
    credits: {
      enabled: false,
    },
    navigation: {
      buttonOptions: {
        enabled: false,
      },
    },

    legend: {
      enabled: true,
    },
    colorAxis: {
      min: 1,
      minColor: "#E4A2FB",
      maxColor: "#000022",
      stops: [
        [0, "#E4A2FB"],
        [0.67, "#BF05FF"],
        [2, "#000022"],
      ],
    },
    series: [
      {
        name: "Country",
        data: [
          ["madhya pradesh", 1],
          ["uttar pradesh", 1],
          ["karnataka", 1],
          ["nagaland", 1],
          ["bihar", 1],
          ["lakshadweep", 1],
          ["andaman and nicobar", 2],
          ["assam", 2],
          ["west bengal", 3],
          ["puducherry", 1],
          ["daman and diu", 2],
          ["gujarat", 2],
          ["rajasthan", 3],
          ["dadara and nagar havelli", 3],
          ["chhattisgarh", 2],
          ["tamil nadu", 2],
          ["chandigarh", 3],
          ["punjab", 3],
          ["haryana", 3],
          ["andhra pradesh", 4],
          ["maharashtra", 4],
          ["himachal pradesh", 4],
          ["meghalaya", 4],
          ["odisha", 2],
        ],
        color: "#bf05ff",
        dataLabels: {
          enabled: false,
          color: "#FFFFFF",
          format: "{point.name}",
          nullFormat: "",
        },
        tooltip: {
          headerFormat: "",
          pointFormat: "{point.name}",
        },
      },
    ],
  });
})();
