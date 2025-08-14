//ceo Procurement Nr Of Suppliers
Highcharts.chart("ceoProcurementNrOfSuppliers", {
  chart: {
    type: "column",
    backgroundColor: "transparent",
    height: 149.5,
    margin: 0,
  },
  colors: ["#bf05ff", "#ca7ce5", "#61426c"],

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
    labels: {
      enabled: false,
    },
    lineColor: "transparent",
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
      name: "IPS",
      data: [
        ["1", 21],
        ["2", 109],
        ["3", 67],
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
//ceo Procurement Supplier Defect Type
Highcharts.chart("ceoProcurementSupplierDefectType", {
  chart: {
    type: "column",
    animation: true,
    height: 217,
  },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    categories: [
      "Supplier1",
      "Supplier2",
      "Supplier3",
      "Supplier4",
      "Supplier5",
      "Supplier6",
    ],
    lineColor: "#e8e8e8",
    labels: {
      rotation: 0,
      style: {
        fontSize: "11px",
      },
    },
  },
  credits: { enabled: false },
  yAxis: {
    min: 0,
    title: {
      text: "Defect Type",
    },
    stackLabels: {
      enabled: false,
    },
    labels: {
      enabled: false,
    },
    lineColor: "transparent",
    gridLineColor: "transparent",
  },
  legend: {
    align: "center",
    verticalAlign: "bottom",
    floating: false,
  },
  plotOptions: {
    column: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "Rejected",
      data: [7, 7, 7, 7, 7, 7],
      color: "#9792e8",
    },
    {
      name: "Impact",
      data: [7, 7, 7, 7, 7, 7],
      color: "#deaaf0",
    },
    {
      name: "No Impact",
      data: [7, 7, 7, 7, 7, 7],
      color: "#bf05ff",
    },
  ],
});
//ceo Procurement By Supplier Category
Highcharts.chart("ceoProcurementCABySupplierCategory", {
  chart: {
    type: "bar",
    animation: true,
    height: 190,
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
      "Supplier 1",
      "Supplier 2",
      "Supplier 3",
      "Supplier 4",
      "Supplier 5",
      "Supplier 6",
    ],
  },
  yAxis: {
    min: 0,
    max: 100,
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },

    gridLineColor: "transparent",
    lineColor: "#cccccc",
  },
  legend: {
    enabled: false,
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        format: "{y} %",
        color: "#000000",
      },
    },
  },

  series: [
    {
      name: "",
      data: [74, 73, 47, 53, 67, 62],
      color: "#c313ff",
    },
  ],
});
//ceo Procurement Average By Category
Highcharts.chart("ceoProcurementAverageByCategory", {
  chart: {
    type: "bar",
    animation: true,
    height: 233,
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
      "Supplier 1",
      "Supplier 2",
      "Supplier 3",
      "Supplier 4",
      "Supplier 5",
      "Supplier 6",
    ],
  },
  yAxis: {
    min: 0,
    max: 100,
    title: {
      text: "",
    },
    labels: {
      enabled: false,
    },

    gridLineColor: "transparent",
    lineColor: "#cccccc",
  },
  legend: {
    enabled: false,
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        format: "{y} %",
        color: "#000000",
      },
    },
  },

  series: [
    {
      name: "",
      data: [74, 73, 47, 53, 67, 62],
      color: "#c313ff",
    },
  ],
});

//ceo Procurement Return to Vendor Costs & Vendor Rejection Rate
Highcharts.chart("ceoProcurementReturntoVendorCosts", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 287,
  },
   colors: ["#bf05ff", "#F79C92", "#61426c", "#db79fc", "#c313ff"],
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
  xAxis: [
    {
      categories: [
        "Globex Ship",
        "McMillan Log",
        "Plumbus Shop",
        "Raw Suppl. Co.",
        "Tech Consult.",
      ],
      crosshair: true,
      lineColor: "#cccccc",
      labels: {
        rotation: 0,
        style: {
          fontSize: "11px",
        },
      },
    },
  ],
  yAxis: [
    {
      labels: {
        format: "",
        style: {
          display: "none",
        },
      },
      title: {
        text: "Return Costs",
        style: {
          display: "none",
        },
      },
    },
    {
      title: {
        text: "Rejection Rates",
        style: {
          display: "none",
        },
      },
      labels: {
        format: "",
        style: {
          display: "none",
        },
      },
      opposite: false,
    },
  ],
  tooltip: {
    shared: false,
  },
  legend: {
    align: "center",
    verticalAlign: "bottom",
    floating: false,
    backgroundColor: "transparent",
  },
  series: [
    {
      name: "Return Costs",
      type: "column",
      color: "#9792e8",
      yAxis: 1,
       data: [
    { y: 27.6, color: "#FF0000" }, // Red
    { y: 28.8, color: "#00FF00" }, // Green
    { y: 21.7, color: "#0000FF" }, // Blue
    { y: 34.1, color: "#FFFF00" }, // Yellow
    { y: 23.6, color: "#FF00FF" }, // Magenta
  ],
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "center",
        verticalAlign: "center",
        format: "${point.y:.1f}K",
        style: {
          fontSize: "10px",
        },
      },
    },

    {
      name: "Rejection Rate",
      type: "spline",
      data: [-13.6, -14.9, -5.8, -0.7, -13.14],
      color: "#bf05ff",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#FFFFFF",
        backgroundColor: "#bf05ff",
        align: "right",
        format: "{point.y:.1f}%",
        style: {
          fontSize: "10px",
        },
      },
      marker: {
        lineWidth: 2,
        fillColor: "#bf05ff",
      },
    },
  ],
});
//ceo Procurement Development of Avg. Purchase Order Cycle
Highcharts.chart("ceoProcurementDevelopmentOfAvgPurchaseOrderCycle", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 150,
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
    enabled: false,
  },

  yAxis: {
    title: {
      text: "",
    },
    showInLegend: false,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    categories: [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
    ],
    // lineColor: 'transparent',
    // labels: {
    //     enabled: false
    // },
    // tickWidth: 0,
    // tickLength: 0,
    showInLegend: false,
  },
  tooltip: { enabled: false },
  legend: { enabled: false },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
      pointStart: 0,
    },
  },

  series: [
    {
      name: "",
      data: [4, 5, 10, 2, 5, 6, 8, 1, 5, 3, 5],
      color: "#bf05ff",
    },
  ],
});
//ceo Procurement Cost Reduction Five Year Trend
Highcharts.chart("ceoProcurementCostReductionFiveYearTrend", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 100,
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
    enabled: false,
  },

  yAxis: {
    title: {
      text: "",
    },
    showInLegend: false,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    tickWidth: 0,
    tickLength: 0,
    showInLegend: false,
  },
  tooltip: { enabled: false },
  legend: { enabled: false },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
      pointStart: 0,
    },
  },

  series: [
    {
      name: "",
      data: [4, 5, 10, 12, 5],
      color: "#bf05ff",
    },
  ],
});
//ceo Procurement Cost Avoidance Five Year Trend
Highcharts.chart("ceoProcurementCostAvoidanceFiveYearTrend", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 100,
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
    enabled: false,
  },

  yAxis: {
    title: {
      text: "",
    },
    showInLegend: false,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    tickWidth: 0,
    tickLength: 0,
    showInLegend: false,
  },
  tooltip: { enabled: false },
  legend: { enabled: false },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
      pointStart: 0,
    },
  },

  series: [
    {
      name: "",
      data: [4, 5, 10, 12, 5],
      color: "#bf05ff",
    },
  ],
});
//ceo Procurement Maverick Spend Quaterly Trend
Highcharts.chart("ceoProcurementMaverickSpendQuaterlyTrend", {
  chart: {
    type: "spline",
    animation: true,
    backgroundColor: "transparent",
    height: 100,
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
    enabled: false,
  },

  yAxis: {
    title: {
      text: "",
    },
    showInLegend: false,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    tickWidth: 0,
    tickLength: 0,
    showInLegend: false,
  },
  tooltip: { enabled: false },
  legend: { enabled: false },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
      pointStart: 0,
    },
  },

  series: [
    {
      name: "",
      data: [4, 5, 10, 12, 5],
      color: "#bf05ff",
      marker: { enabled: false },
    },
  ],
});
//ceo Procurement Supplier Quality Rating Bar
Highcharts.chart("ceoProcurementSupplierQualityRatingBar", {
  chart: {
    type: "column",
    backgroundColor: "transparent",
    height: 189,
  },
  colors: ["#bf05ff", "#F79C92", "#61426c", "#db79fc", "#c313ff"],

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
      name: "IPS",
      data: [
        ["Globex Ship", 21],
        ["McMillan Log", 109],
        ["Raw Suppl. Co.", 67],
        ["Plumbus Shop", 109],
        ["Tech Consult", 67],
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
//ceo Procurement Quarterly Average By Product
Highcharts.chart("ceoProcurementQuarterlyAverageByProduct", {
  chart: {
    zoomType: "xy",
    animation: true,
    backgroundColor: "transparent",
    height: 199.5,
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
  xAxis: [
    {
      categories: ["Q1", "Q2", "Q3"],
      crosshair: true,
      labels: {
        style: {
          fontSize: "9px",
        },
      },
    },
  ],
  yAxis: [
    {
      labels: {
        enabled: false,
        format: "{value}",
        style: {
          color: "#005c9f",
        },
      },
      title: {
        text: "",
      },
    },
    {
      title: {
        text: "",
      },
      labels: {
        enabled: false,
        format: "{value}",
        style: {
          color: "#68bfff",
        },
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },
  legend: {
    align: "center",
    verticalAlign: "bottom",
    floating: false,
    backgroundColor: "transparent",
  },
  series: [
    {
      name: "ER-289",
      type: "column",
      yAxis: 1,
      data: [17.6, 18.8, 11.7],
      color: "#f58d68",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "right",
        format: "{point.y:.1f}%",
        style: {
          fontSize: "7px",
        },
      },
    },
    {
      name: "ER-569",
      type: "column",
      yAxis: 1,
      data: [27.6, 28.8, 21.7],
      color: "#9792e8",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "right",
        format: "{point.y:.1f}%",
        style: {
          fontSize: "7px",
        },
      },
    },
    {
      name: "LA-273",
      type: "column",
      yAxis: 1,
      data: [37.6, 38.8, 31.7],
      color: "#bf05ff",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "right",
        format: "{point.y:.1f}%",
        style: {
          fontSize: "7px",
        },
      },
    },
  ],
});
//ceo Procurement Cost Of A Purchase Order Trend
Highcharts.chart("ceoProcurementCostOfAPurchaseOrderTrend", {
  chart: {
    type: "line",
    animation: true,
    backgroundColor: "transparent",
    height: 263,
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
    enabled: false,
  },

  yAxis: {
    title: {
      text: "",
    },
    showInLegend: false,
    lineColor: "transparent",
    labels: {
      enabled: false,
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    categories: [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
    ],
    // lineColor: 'transparent',
    // labels: {
    //     enabled: false
    // },
    // tickWidth: 0,
    // tickLength: 0,
    showInLegend: false,
  },
  tooltip: { enabled: false },
  legend: { enabled: false },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
      pointStart: 0,
    },
  },

  series: [
    {
      name: "",
      data: [4, 5, 10, 2, 5, 6, 8, 1, 5, 3, 5],
      color: "#bf05ff",
      dataLabels: {
        enabled: true,
      },
    },
  ],
});
//ceo Procurement Spend Under Management
Highcharts.chart("ceoProcurementSpendUnderManagement", {
  chart: {
    type: "line",
    animation: true,
    height: 257,
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
      label: {
        connectorAllowed: false,
      },
    },
  },
  legend: {
    enabled: false,
  },

  series: [
    {
      name: "1",
      data: [43, 48, 65, 81, 112, 142, 171, 165, 155, 113, 81, 48],
      marker: false,
      dataLabels: true,
      color: "#b422b6",
    },
    {
      name: "Target",
      data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
      dashStyle: "ShortDot",
      marker: false,
      color: "#df0000",
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
//ceo Procurement ROI By Supplier Category
Highcharts.chart("ceoProcurementROIBySupplierCategory", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 309,
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
      name: "ROI",
      type: "column",
      data: [27.6, 28.8, 21.7, 34.1, 43.2],
      color: "#bf05ff",
    },
    {
      name: "Benchmark",
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
//ceo Procurement Cost Reduction By Supplier
Highcharts.chart("ceoProcurementCostReductionBySupplier", {
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
  colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#b422b6", "#d4c9d8"],
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
        ["Transistor", 34],
        ["Switches", 19],
        ["Sensors", 16],
        ["Battery", 15],
        ["Display", 10],
        ["Other", 6],
      ],
      size: "50%",
      innerSize: "45%",
      showInLegend: false,
      dataLabels: {
        enabled: true,
        format: "{point.percentage:.1f} %",
      },
    },
  ],
});
//ceo Procurement Lead Time In Days
Highcharts.chart("ceoProcurementLeadTimeInDays", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 232,
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

  yAxis: {
    title: {
      text: "",
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    categories: [],
    crosshair: true,
    lineColor: "#cccccc",
  },

  legend: {
    layout: "horizontal",
    align: "center",
    verticalAlign: "center",
  },
  tooltip: { enabled: false },

  plotOptions: {
    series: {
      states: {
        hover: {
          enabled: false,
        },
      },
      label: {
        connectorAllowed: false,
      },
      pointStart: 2010,
    },
  },

  series: [
    {
      name: "Lead Time > 16",
      data: [null, null, null, null, null, null, null, null, null, 18, 18],
      lineColor: "transparent",
      marker: {
        fillColor: "red",
      },
    },
    {
      name: "Lead Time",
      data: [2, 6, 8, 17, 8, 10, 12, 15, 16, 18, 18],
      marker: {
        enabled: false,
        fillColor: "#f79c92",
        border: "#f79c92",
      },
      lineColor: "#f79c92",
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
//ceo Procurement Supplier Availability
Highcharts.chart("ceoProcurementSupplierAvailability", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 287,
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

  yAxis: {
    title: {
      text: "",
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    categories: [],
    crosshair: true,
    lineColor: "#cccccc",
  },

  legend: {
    layout: "horizontal",
    align: "center",
    verticalAlign: "center",
  },
  tooltip: { enabled: false },

  plotOptions: {
    series: {
      states: {
        hover: {
          enabled: false,
        },
      },
      label: {
        connectorAllowed: false,
      },
      pointStart: 2010,
    },
  },

  series: [
    {
      name: "Supplier Availability < 90%",
      data: [null, null, 45, null, null, null, null, null, null, 68, 78],
      lineColor: "transparent",
      marker: {
        fillColor: "red",
      },
    },
    {
      name: "Supplier Availability",
      data: [72, 76, 45, 99, 98, 96, 52, 75, 36, 68, 78],
      marker: {
        enabled: false,
        fillColor: "#f79c92",
        border: "#f79c92",
      },
      lineColor: "#f79c92",
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
//ceo Procurement Supplier Availability
Highcharts.chart("ceoProcurementSupplierAvailability", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 287,
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

  yAxis: {
    title: {
      text: "",
    },
    gridLineColor: "transparent",
  },

  xAxis: {
    categories: [],
    crosshair: true,
    lineColor: "#cccccc",
  },

  legend: {
    layout: "horizontal",
    align: "center",
    verticalAlign: "center",
  },
  tooltip: { enabled: false },

  plotOptions: {
    series: {
      states: {
        hover: {
          enabled: false,
        },
      },
      label: {
        connectorAllowed: false,
      },
      pointStart: 2010,
    },
  },

  series: [
    {
      name: "Supplier Availability < 90%",
      data: [null, null, 45, null, null, null, null, null, null, 68, 78],
      lineColor: "transparent",
      marker: {
        fillColor: "red",
      },
    },
    {
      name: "Supplier Availability",
      data: [72, 76, 45, 99, 98, 96, 52, 75, 36, 68, 78],
      marker: {
        enabled: false,
        fillColor: "#f79c92",
        border: "#f79c92",
      },
      lineColor: "#f79c92",
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
//ceo Procurement Purchase Order Coverage
Highcharts.chart("ceoProcurementPurchaseOrderCoverage", {
  chart: {
    animation: true,
    height: 279,
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
  credits: false,
  yAxis: {
    title: {
      text: "",
    },
    labels: {
      format: "{value}%",
    },
  },

  xAxis: {
    categories: [
      "Q1 2023",
      "Q2 2023",
      "Q3 2023",
      "Q4 2023",
      "Q1 2024",
      "Q2 2024",
      "Q3 2024",
      "Q4 2024",
    ],
  },

  legend: {
    enabled: false,
  },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
    },
  },

  series: [
    {
      type: "spline",
      name: "Trend",
      data: [49, 79, 97, 19, 22, 10, 81, 68],
      lineWidth: 2,
      color: "#bf05ff",
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
//ceo Procurement Purchase Price Variance
Highcharts.chart("ceoProcurementPurchasePriceVariance", {
  chart: {
    animation: true,
    height: 260,
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
  yAxis: {
    title: {
      text: "",
    },
  },
  credits: { enabled: false },

  xAxis: {
    tickLength: 0,
    tickWidth: 0,
    // labels: {
    //     rotation: -45,
    //     style: {
    //         fontSize: '10px',
    //         color: '#000000'
    //     }
    // },
    showInLegend: false,
    categories: [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ],
  },
  legend: {
    layout: "horizontal",
    align: "center",
    verticalAlign: "bottom",
  },

  plotOptions: {
    series: {
      label: {
        connectorAllowed: false,
      },
      pointStart: 2010,
    },
  },

  series: [
    {
      type: "spline",
      name: "Pruchase Price Varinace",
      data: [
        43934, 48656, 65165, 81827, 112143, 142383, 71533, 65174, 15557, 16454,
        15610,
      ],
      color: "#bf05ff",
    },
    {
      type: "spline",
      name: "Benchmark",
      data: [
        65165, 65165, 65165, 65165, 65165, 65165, 65165, 65165, 65165, 65165,
        65165,
      ],
      color: "#A594F9",
      marker: {
        enabled: false,
      },
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
//ceo Procurement No Of Suppliers Per Year Pie
Highcharts.chart("ceoProcurementNoOfSuppliersPerYearPie", {
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
    text: "886",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: 0,
    margin: 0,
    style: { fontSize: "12", color: "#000000" },
  },
  subtitle: {
    text: "Suppliers",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: 15,
    margin: 0,
    style: { fontSize: "9", color: "#000000" },
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
      colors: ["#9792e8"],
      allowPointSelect: true,
    },
  },
  series: [
    {
      name: "",
      data: [["", 100]],
      size: "100%",
      innerSize: "70%",
      color: "#9792e8",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Procurement Compliance Rate Pie
Highcharts.chart("ceoProcurementComplianceRatePie", {
  chart: {
    type: "pie",
    height: 140,
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
    y: 55,
    margin: 0,
    style: { fontSize: "15", color: "#000000" },
  },
  subtitle: {
    text: "Compliance Rate",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 70,
    margin: 0,
    style: { fontSize: "9", color: "#000000" },
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
      colors: ["#c313ff", "#f1cffc"],
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
      color: "#c313ff",
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Procurement No Of Suppliers By Year
Highcharts.chart("ceoProcurementNoOfSuppliersByYear", {
  chart: {
    type: "column",
    animation: true,
    height: 217,
  },
  title: {
    text: "",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  xAxis: {
    categories: ["2020", "2021", "2022", "2023", "2024", "2025"],
    lineColor: "#e8e8e8",
    labels: {
      rotation: 0,
      style: {
        fontSize: "11px",
      },
    },
  },
  credits: { enabled: false },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
    stackLabels: {
      enabled: false,
    },
    labels: {
      enabled: false,
    },
    lineColor: "transparent",
    gridLineColor: "transparent",
  },
  legend: {
    align: "center",
    verticalAlign: "bottom",
    floating: false,
  },
  plotOptions: {
    column: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "Contracted Supplier",
      data: [7, 7, 7, 7, 7, 7],
      color: "#bf05ff",
    },
    {
      name: "Unlisted Supplier",
      data: [7, 7, 7, 7, 7, 7],
      color: "#deaaf0",
    },
  ],
});
//ceo Procurement Compliance Rate
Highcharts.chart("ceoProcurementComplianceRate", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 202,
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
      categories: ["2020", "2021", "2022", "2023", "2024"],
      crosshair: true,
    },
  ],
  yAxis: [
    {
      title: {
        text: "",
      },
      labels: {
        format: "{value} %",
      },
    },
  ],
  series: [
    {
      name: "Contract Rate",
      type: "column",
      data: [27.6, 128.8, 71.7, 34.1, 63.2],
      color: "#c313ff",
    },
    {
      name: "Target = 60%",
      type: "spline",
      data: [60, 60, 60, 60, 60],
      color: "#e7acfb",
      dataLabels: {
        enabled: false,
      },
      marker: {
        enabled: false,
      },
    },
  ],
});
