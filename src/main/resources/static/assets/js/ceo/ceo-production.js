//ceo Production Total Costs Per Unit
Highcharts.chart("ceoProductionTotalCostsPerUnit", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 50,
    margin: 0,
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
      color: "#bf05ff",
      showInLegend: false,
      marker: {
        enabled: false,
      },
    },
  ],
});
//ceo Production Rate Of Return
Highcharts.chart("ceoProductionRateOfReturn", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 40,
    margin: 0,
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
      color: "#bf05ff",
      showInLegend: false,
      marker: {
        enabled: false,
      },
    },
  ],
});
//ceo Production First Order Rate Of Return
Highcharts.chart("ceoProductionFirstOrderRateOfReturn", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 40,
    margin: 0,
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
      color: "#bf05ff",
      showInLegend: false,
      marker: {
        enabled: false,
      },
    },
  ],
});
//ceo Production Avg Costs By Production Component
Highcharts.chart("ceoProductionAvgCostsByProductionComponent", {
  chart: {
    type: "bar",
    animation: true,
    height: 254,
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
      "Electronics Assembly",
      "Packaging",
      "Cover Assembly",
      "Mechanical Assembly",
      "Final Prep",
    ],
  },
  yAxis: {
    max: 30,
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
      },
    },
  },

  series: [
    {
      name: "",
      data: [23, 15, 6.8, 4.9, 4],
      color: "#f58d68",
    },
  ],
});
//ceo Production Throughput
Highcharts.chart("ceoProductionThroughput", {
  chart: {
    type: "bar",
    animation: true,
    height: 226,
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
    categories: ["Machine A", "Machine B", "Machine C", "Machine D"],
  },
  yAxis: {
    max: 30,
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
        enabled: false,
      },
    },
  },

  series: [
    {
      name: "",
      type: "bar",
      data: [23, 15, 6, 14],
      color: "#ca7ce5",
    },
    {
      name: "",
      type: "line",
      data: [20, 20, 20, 20],
      color: "#56156c",
      marker: false,
      dashStyle: "shortDash",
    },
    {
      name: "",
      type: "line",
      data: [10, 10, 10, 10],
      color: "#56156c",
      marker: false,
      dashStyle: "shortDash",
    },
  ],
});
//ceo Production Share of Production Volume By Machine 1
Highcharts.chart("ceoProductionShareofProductionVolumeByMachine1", {
  chart: {
    type: "pie",
    height: 100,
    margin: 0,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "68%",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 43,
    margin: 0,
    style: { fontSize: "11", color: "#000000" },
  },
  subtitle: {
    text: "",
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
      colors: ["#ca7ce5", "#cccccc"],
      allowPointSelect: true,
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Contracted", 65],
        ["", 35],
      ],
      size: "120%",
      innerSize: "80%",
      showInLegend: false,
      color: "#bf05ff",
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Production Share of Production Volume By Machine 2
Highcharts.chart("ceoProductionShareofProductionVolumeByMachine2", {
  chart: {
    type: "pie",
    margin: 0,
    height: 100,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "68%",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 43,
    margin: 0,
    style: { fontSize: "11", color: "#000000" },
  },
  subtitle: {
    text: "",
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
      colors: ["#bf05ff", "#cccccc"],
      allowPointSelect: true,
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Contracted", 65],
        ["", 35],
      ],
      size: "120%",
      innerSize: "80%",
      showInLegend: false,
      color: "#bf05ff",
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Production Share of Production Volume By Machine 3
Highcharts.chart("ceoProductionShareofProductionVolumeByMachine3", {
  chart: {
    type: "pie",
    height: 100,
    margin: 0,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "68%",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 43,
    margin: 0,
    style: { fontSize: "11", color: "#000000" },
  },
  subtitle: {
    text: "",
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
      colors: ["#f58d68", "#cccccc"],
      allowPointSelect: true,
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Contracted", 65],
        ["", 35],
      ],
      size: "120%",
      innerSize: "80%",
      showInLegend: false,
      color: "#f58d68",
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Production Asset Turnover
Highcharts.chart("ceoProductionAssetTurnover", {
  chart: {
    type: "column",
    animation: true,
    height: 250,
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
    categories: ["2017", "2018", "2019", "2020", "2021", "2022", "2023"],
  },
  yAxis: {
    title: {
      text: "",
    },
    labels: {
      format: "{value}%",
    },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        format: "{y}%",
      },
    },
  },
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [74, 73, 47, 57, 64, 35, 36],
      color: "#bf05ff",
    },
  ],
});
//ceo Production Rate Of Return By Product
Highcharts.chart("ceoProductionRateOfReturnByProduct", {
  chart: {
    type: "column",
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
    categories: [
      "Product 1",
      "Product 2",
      "Product 3",
      "Product 4",
      "Product 5",
    ],
    labels: {
      rotation: 0,
    },
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
        format: "{y}%",
      },
    },
  },
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [74, 73, 47, 57, 64],
      color: "#ca7ce5",
    },
  ],
});
//ceo Production Average Revenue Per Employee
Highcharts.chart("ceoProductionAverageRevenuePerEmployee", {
  chart: {
    type: "column",
    animation: true,
    height: 284.5,
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
    categories: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul"],
    labels: {
      rotation: 0,
    },
  },
  yAxis: {
    title: {
      text: "",
    },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        format: "{y}%",
      },
    },
  },
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [74, 73, 47, 57, 64, 45, 27],
      color: "#bf05ff",
    },
  ],
});
//ceo Production Return On Assets
Highcharts.chart("ceoProductionReturnOnAssets", {
  chart: {
    animation: true,
    height: 250,
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
      crosshair: true,
      lineColor: "#cccccc",
      rotation: "45deg",
    },
  ],
  yAxis: {
    labels: { format: "{value} %" },
    title: {
      text: "",
    },
  },
  tooltip: {
    shared: false,
  },
  legend: {
    enabled: true,
  },
  plotOptions: {
    plotWidth: 10,
  },
  series: [
    {
      name: "ROA",
      type: "column",
      color: "#F48661",
      data: [
        27.6, 28.8, -21.7, 34.1, 23.6, -34.7, -28.8, 21.7, 34.1, 23.6, -34.7,
        43.4,
      ],
    },
    {
      name: "Trend Line ROA",
      type: "line",
      data: [10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10],
      color: "#B62E00",
      lineWidth: 1,
      dashStyle: "LongDash",
      dataLabels: {
        enabled: false,
      },
      marker: {
        enabled: false,
      },
    },
  ],
});
//ceo Production Overall Operations Effectiveness
Highcharts.chart("ceoProductionOverallOperationsEffectiveness", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 268,
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
      crosshair: true,
      labels: { enabled: false },
      lineColor: "transparent",
      gridLineColor: "transparent",
    },
  ],
  yAxis: [
    {
      title: {
        text: "",
      },
      labels: { enabled: false },
      gridLineColor: "transparent",
    },
  ],
  tooltip: {
    shared: true,
  },
  plotOptions: {
    areaspline: {
      lineColor: "#bf05ff",
      fillColor: {
        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
        stops: [
          [0, "#bf05ff"],
          [1, "#f4f4f4"],
        ],
      },
    },
  },
  series: [
    {
      name: "OEE",
      type: "spline",
      data: [15, 18, 19, 16, 18, 20, 16, 15, 19, 18, 16, 18],
      color: "#61426c",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDash",
      lineWidth: 1,
    },
    {
      name: "TEEP",
      type: "spline",
      data: [25, 28, 29, 26, 28, 30, 26, 25, 29, 28, 26, 28],
      color: "#61426c",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDot",
      lineWidth: 1,
    },
    {
      name: "OOE",
      type: "areaspline",
      data: [20, 23, 24, 21, 23, 25, 21, 20, 24, 23, 21, 23],
      shadow: false,
      marker: {
        enabled: true,
        symbol: "circle",
        radius: 4,
        fillColor: "#56156c",
        states: {
          hover: {
            lineWidth: 1,
          },
        },
      },
    },
  ],
});
//ceo Production Overall Equipment Effectiveness
Highcharts.chart("ceoProductionOverallEquipmentEffectiveness", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 268,
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
      crosshair: true,
      labels: { enabled: false },
      lineColor: "transparent",
      gridLineColor: "transparent",
    },
  ],
  yAxis: [
    {
      title: {
        text: "",
      },
      labels: { enabled: false },
      gridLineColor: "transparent",
    },
  ],
  tooltip: {
    shared: true,
  },
  plotOptions: {
    areaspline: {
      lineColor: "#7770ef",
      fillColor: {
        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
        stops: [
          [0, "#9792e8"],
          [1, "#f4f4f4"],
        ],
      },
    },
  },
  series: [
    {
      name: "OOE",
      type: "spline",
      data: [15, 18, 19, 16, 18, 20, 16, 15, 19, 18, 16, 18],
      color: "#7770ef",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDash",
      lineWidth: 1,
    },
    {
      name: "TEEP",
      type: "spline",
      data: [25, 28, 29, 26, 28, 30, 26, 25, 29, 28, 26, 28],
      color: "#7770ef",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDot",
      lineWidth: 1,
    },
    {
      name: "OEE",
      type: "areaspline",
      data: [20, 23, 24, 21, 23, 25, 21, 20, 24, 23, 21, 23],
      shadow: false,
      marker: {
        enabled: true,
        symbol: "circle",
        radius: 4,
        fillColor: "#7770ef",
        states: {
          hover: {
            lineWidth: 1,
          },
        },
      },
    },
  ],
});
//ceo Production Total Effective Equipment Performance
Highcharts.chart("ceoProductionTotalEffectiveEquipmentPerformance", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 268,
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
      crosshair: true,
      labels: { enabled: false },
      lineColor: "transparent",
      gridLineColor: "transparent",
    },
  ],
  yAxis: [
    {
      title: {
        text: "",
      },
      labels: { enabled: false },
      gridLineColor: "transparent",
    },
  ],
  tooltip: {
    shared: true,
  },
  plotOptions: {
    areaspline: {
      lineColor: "#e35647",
      fillColor: {
        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
        stops: [
          [0, "#f58d68"],
          [1, "#f4f4f4"],
        ],
      },
    },
  },
  series: [
    {
      name: "OEE",
      type: "spline",
      data: [15, 18, 19, 16, 18, 20, 16, 15, 19, 18, 16, 18],
      color: "#e35647",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDash",
      lineWidth: 1,
    },
    {
      name: "OOE",
      type: "spline",
      data: [25, 28, 29, 26, 28, 30, 26, 25, 29, 28, 26, 28],
      color: "#e35647",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDot",
      lineWidth: 1,
    },
    {
      name: "TEEP",
      type: "areaspline",
      data: [20, 23, 24, 21, 23, 25, 21, 20, 24, 23, 21, 23],
      shadow: false,
      marker: {
        enabled: true,
        symbol: "circle",
        radius: 4,
        fillColor: "#e35647",
        states: {
          hover: {
            lineWidth: 1,
          },
        },
      },
    },
  ],
});
//ceo Production Unit Costs With Target
Highcharts.chart("ceoProductionUnitCostsWithTarget", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 250,
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
//ceo Production Defect Density By Product
Highcharts.chart("ceoProductionDefectDensityByProduct", {
  chart: {
    type: "line",
    animation: true,
    height: 300,
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
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "Product 1",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 90],
      marker: false,
      color: "#b605f3",
    },
    {
      name: "Product 2",
      data: [114, 30, 16, 19, 20, 24, 32, 30, 27, 29, 25, 19],
      marker: false,
      color: "#d95aee",
    },
    {
      name: "Product 3",
      data: [91, 95, 111, 142, 189, 118, 102, 133, 110, 150, 210, 21],
      marker: false,
      color: "#51a4d6",
    },
    {
      name: "Product 4",
      data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
      marker: false,
      color: "#f58d68",
    },
    {
      name: "Product 5",
      data: [130, 110, 100, 123, 121, 154, 90, 98, 120, 110, 95, 99],
      marker: false,
      color: "#56156c",
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
//ceo Production Maintenance Cost By Month
Highcharts.chart("ceoProductionMaintenanceCostByMonth", {
  chart: {
    type: "line",
    animation: true,
    height: 224,
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
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
    },
  },
  series: [
    {
      name: "Maintenance Cost",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 90],
      marker: false,
      color: "#b605f3",
    },
    {
      name: "Target Maintenance Cost",
      data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
      marker: false,
      color: "#f58d68",
      dashStyle: "ShortDash",
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
//ceo Production Right First Time
Highcharts.chart("ceoProductionRightFirstTime", {
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
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 90],
      marker: false,
      color: "#f58d68",
      dashStyle: "ShortDash",
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
//ceo Production On Time Delivery
Highcharts.chart("ceoProductionOnTimeDelivery", {
  chart: {
    type: "spline",
    animation: true,
    height: 300,
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
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 90],
      color: "#bf05ff",
      dashStyle: "ShortDash",
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
//ceo Production Scrap Rate
Highcharts.chart("ceoProductionScrapRate", {
  chart: {
    type: "spline",
    animation: true,
    height: 285,
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
      zones: [
        {
          value: 30,
          color: "#9792e8",
        },
        {
          value: 70,
          color: "#bf05ff",
        },
        {
          color: "#dc3545",
        },
      ],
      //   color: "#bf05ff",
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
//ceo Production First Pass Yield
Highcharts.chart("ceoProductionFirstPassYield", {
  chart: {
    type: "spline",
    animation: true,
    height: 205,
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
      dashStyle: "ShortDash",
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
//ceo Production Capacity Utilization
Highcharts.chart("ceoProductionCapacityUtilization", {
  chart: {
    type: "spline",
    animation: true,
    height: 300,
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
      dashStyle: "ShortDash",
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
//ceo Production Downtimes By Cause
Highcharts.chart("ceoProductionDowntimesByCause", {
  chart: {
    type: "pie",
    height: 160,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "4.4%",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: 0,
    margin: 0,
    style: { fontSize: "15", color: "#000000" },
  },
  subtitle: {
    text: "Downtime",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: 10,
    margin: 0,
    style: { fontSize: "12", color: "#000000" },
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
      colors: ["#bf05ff", "#deaaf0", "#f58d68"],
      allowPointSelect: true,
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Broken Macine", 35],
        ["Missing Parts", 35],
        ["Service", 30],
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
//ceo Production Production Volume By Produced Units
Highcharts.chart("ceoProductionProdVolumeByProducedUnits", {
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
//ceo Production Throughput Gauge
Highcharts.chart("ceoProductionThroughputGauge", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 150,
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
    startAngle: -128,
    endAngle: 127.9,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: -100,
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
        from: -100,
        to: -20,
        thickness: 15,
        color: "#bf05ff",
      },
      {
        from: -20,
        to: 40,
        thickness: 15,
        color: "#deaaf0",
      },
      {
        from: 40,
        to: 100,
        thickness: 15,
        color: "#f58d68",
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
//ceo Production First Pass Yield Machine A
Highcharts.chart("ceoProductionFirstPassYieldMachineA", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 70,
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
    startAngle: -128,
    endAngle: 127.9,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: -100,
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
        from: -100,
        to: -20,
        thickness: 8,
        color: "#bf05ff",
      },
      {
        from: -20,
        to: 40,
        thickness: 8,
        color: "#deaaf0",
      },
      {
        from: 40,
        to: 100,
        thickness: 8,
        color: "#f58d68",
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
//ceo Production First Pass Yield Machine B
Highcharts.chart("ceoProductionFirstPassYieldMachineB", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 70,
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
    startAngle: -128,
    endAngle: 127.9,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: -100,
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
        from: -100,
        to: -20,
        thickness: 8,
        color: "#bf05ff",
      },
      {
        from: -20,
        to: 40,
        thickness: 8,
        color: "#deaaf0",
      },
      {
        from: 40,
        to: 100,
        thickness: 8,
        color: "#f58d68",
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
//ceo Production First Pass Yield Machine C
Highcharts.chart("ceoProductionFirstPassYieldMachineC", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 70,
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
    startAngle: -128,
    endAngle: 127.9,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: -100,
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
        from: -100,
        to: -20,
        thickness: 8,
        color: "#bf05ff",
      },
      {
        from: -20,
        to: 40,
        thickness: 8,
        color: "#deaaf0",
      },
      {
        from: 40,
        to: 100,
        thickness: 8,
        color: "#f58d68",
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
//ceo Production First Pass Yield Machine D
Highcharts.chart("ceoProductionFirstPassYieldMachineD", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 70,
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
    startAngle: -128,
    endAngle: 127.9,
    background: null,
    center: ["50%", "75%"],
    size: "100%",
  },

  // the value axis
  yAxis: {
    min: -100,
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
        from: -100,
        to: -20,
        thickness: 8,
        color: "#bf05ff",
      },
      {
        from: -20,
        to: 40,
        thickness: 8,
        color: "#deaaf0",
      },
      {
        from: 40,
        to: 100,
        thickness: 8,
        color: "#f58d68",
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
//ceo Production Right First Time Gauge
Highcharts.chart("ceoProductionRightFirstTimeGauge", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 100,
    backgroundColor: "transparent",
  },

  title: {
    text: "40%",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 10,
    margin: 0,
    style: { fontSize: "12", color: "#f58d68" },
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },

  pane: {
    startAngle: -98,
    endAngle: 97.9,
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
        to: 78,
        color: "#f58d68",
        thickness: 10,
      },
      {
        from: 78,
        to: 100,
        color: "#d4c9d8",
        thickness: 10,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [76],
      tooltip: {
        valueSuffix: "",
      },
      dataLabels: {
        enabled: false,
      },
      dial: {
        radius: "90%",
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
//ceo Production Average Maintenance Cost
Highcharts.chart("ceoProductionAverageMaintenanceCost", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 100,
    backgroundColor: "transparent",
    margin: 0,
  },

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
  credits: { enabled: false },

  pane: {
    startAngle: -98,
    endAngle: 97.9,
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
        to: 78,
        color: "#bf05ff",
        thickness: 10,
      },
      {
        from: 78,
        to: 100,
        color: "#d4c9d8",
        thickness: 10,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [76],
      tooltip: {
        valueSuffix: "",
      },
      dataLabels: {
        enabled: false,
      },
      dial: {
        radius: "90%",
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
