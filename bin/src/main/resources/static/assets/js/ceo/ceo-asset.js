//ceo Assets Top Asset By Downtime - since April 2024
Highcharts.chart("ceoAssetsTopAssetByDowntime", {
  chart: {
    type: "column",
    animation: true,
    // height: 260,
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
    categories: ["abc", "def", "ghi"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
    gridLineColor: "#efefef",
    labels: { enabled: true },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: false,
        color: "#000000",
        format: "{y}%",
      },
    },
  },
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [10, 7, 5],
      color: "#bf05ff",
    },
  ],
});
//ceo Assets Top Asset By Wo Labor
Highcharts.chart("ceoAssetsTopAssetByWoLabor", {
  chart: {
    type: "column",
    animation: true,
    // height: 260,
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
    categories: ["abc", "def", "ghi", "jkl", "mno", "pqr", "stu", "vwz"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
    gridLineColor: "#efefef",
    labels: { enabled: true },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: false,
        color: "#000000",
        format: "{y}%",
      },
    },
  },
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [10, 8, 6, 5, 4, 3, 2, 1],
      color: "#f58d68",
    },
  ],
});
//ceo Assets Avg. Asset Utilization
Highcharts.chart("ceoAssetsAvgAssetUtilization", {
  chart: {
    type: "area",
    // height: 260,
    animation: true,
  },
  title: {
    text: "",
  },
  subtitle: {
    text: "",
  },
  xAxis: {
    categories: ["2021", "2022", "2023", "2024"],
  },
  yAxis: {
    title: {
      text: "",
    },
  },
  credits: false,
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  plotOptions: {
    series: {
      pointStart: 2012,
    },
    area: {
      threshold: null,
      stacking: "normal",
      lineColor: "transparent",
      marker: {
        enabled: false,
      },
    },
  },
  series: [
    {
      name: "Mixing",
      data: [21, 5, 12, 3],
      color: "#c313ff",
    },
    {
      name: "Transport",
      data: [24, 5, 12, 4],
      color: "#9792e8",
    },
    {
      name: "Surface",
      data: [22, 5, 12, 3],
      color: "#f58d68",
    },
  ],
});
//ceo Assets Incident Resolution And Response Time
Highcharts.chart("ceoAssetsIncidentResolutionAndResponseTime", {
  chart: {
    animation: true,
    // height: 244,
    marginLeft: 0,
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
      categories: ["Supplier #1", "Supplier #2", "Supplier #3", "Supplier #4"],
      crosshair: true,
      labels: {
        rotation: 0,
        style: {
          fontSize: "10",
        },
      },
      lineColor: "#cccccc",
    },
  ],
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
  },
  legend: {
    align: "center",
    verticalAlign: "bottom",
    floating: false,
    backgroundColor: "transparent",
  },
  series: [
    {
      name: "TRsl LAB (d)",
      type: "column",
      color: "#deaaf0",
      data: [1.6, 2.8, 0.7, 1.1],
    },
    {
      name: "Limit",
      type: "spline",
      data: [3, 3, 3, 3],
      color: "#bf05ff",
      lineWidth: 2,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Assets Avg. Maint. Expenses
Highcharts.chart("ceoAssetsAvgMaintExpenses", {
  chart: {
    type: "area",
    // height: 260,
    animation: true,
  },
  title: {
    text: "",
  },
  subtitle: {
    text: "",
  },
  xAxis: {
    categories: ["2021", "2022", "2023", "2024"],
  },
  yAxis: {
    title: {
      text: "",
    },
  },
  credits: false,
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  plotOptions: {
    series: {
      pointStart: 2012,
    },
    area: {
      threshold: null,
      stacking: "normal",
      lineColor: "transparent",
      marker: {
        enabled: false,
      },
    },
  },
  series: [
    {
      name: "Infrastructure",
      data: [21, 5, 12, 3],
      color: "#c313ff",
    },
    {
      name: "Digital Assets",
      data: [24, 5, 12, 4],
      color: "#9792e8",
    },
    {
      name: "Machineries",
      data: [22, 5, 12, 3],
      color: "#f58d68",
    },
  ],
});
//ceo Assets Top Mean Time To Repair
Highcharts.chart("ceoAssetsTopMeanTimeToRepair", {
  chart: {
    type: "column",
    animation: true,
    // height: 260,
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
    categories: ["abc", "xyz"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
    gridLineColor: "#e6e6e6",
    labels: { enabled: true },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: false,
        color: "#000000",
        format: "{y}%",
      },
    },
  },
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [0.4, 0],
      color: "#9792e8",
    },
  ],
});
//ceo Assets End Of Life In Next 90 Days
Highcharts.chart("ceoAssetsEndOfLifeInNext90Days", {
  chart: {
    type: "bar",
    animation: true,
    // height: 260,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "",
  },
  xAxis: {
    categories: ["15"],
    title: {
      text: "Life Span",
    },
  },
  yAxis: {
    min: 0,
    title: {
      text: "Value",
    },
  },
  credits: {
    enabled: false,
  },
  legend: {
    reversed: true,
  },
  plotOptions: {
    series: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "15",
      data: [5],
      color: "#51a4d6",
    },
    {
      name: "365",
      data: [365],
      color: "#f58d68",
    },
    {
      name: "2",
      data: [2],
      color: "#9792e8",
    },
    {
      name: "1000",
      data: [1000],
      color: "#deaaf0",
    },
    {
      name: "365",
      data: [365],
      color: "#bf05ff",
    },
    {
      name: "90",
      data: [90],
      color: "#ca7ce5",
    },
    {
      name: "5",
      data: [5],
      color: "#e6a4fd",
    },
    {
      name: "365",
      data: [365],
      color: "#61426c",
    },
  ],
});
//ceo Assets Breakup Of Hardware Assets
Highcharts.chart("ceoAssetsBreakupOfHardwareAssets", {
  chart: {
    type: "column",
    // height: 262,
  },
  title: {
    text: "",
    align: "left",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: {
    enabled: false,
  },
  subtitle: {
    text: "",
    align: "left",
  },
  xAxis: {
    categories: ["0", "1", "2", "3", "4", "5"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
  },
  plotOptions: {
    column: {
      stacking: "number",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "Electronics",
      data: [1, 1, 1, 1, 1, 1, 1],
      color: "#bf05ff",
    },
    {
      name: "Furniture",
      data: [1, 1],
      color: "#e7acfb",
    },
  ],
});
//ceo Assets Total Spend
Highcharts.chart("ceoAssetsTotalSpend", {
  chart: {
    type: "column",
    // height: 262,
  },
  title: {
    text: "",
    align: "left",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: {
    enabled: false,
  },
  subtitle: {
    text: "",
    align: "left",
  },
  xAxis: {
    categories: ["0", "1", "2", "3", "4", "5"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
  },
  plotOptions: {
    column: {
      stacking: "number",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "Electronics",
      data: [1, 1, 1, 1, 1, 1, 1],
      color: "#bf05ff",
    },
    {
      name: "Furniture",
      data: [1, 1],
      color: "#e7acfb",
    },
  ],
});
//ceo Assets Asset Fulfillment Time
Highcharts.chart("ceoAssetsAssetFulfillmentTime", {
  chart: {
    type: "column",
    // height: 262,
  },
  title: {
    text: "",
    align: "left",
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: {
    enabled: false,
  },
  subtitle: {
    text: "",
    align: "left",
  },
  xAxis: {
    categories: ["0", "1", "2", "3", "4", "5"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
  },
  plotOptions: {
    column: {
      stacking: "number",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "Software",
      data: [5, 8, 3, 10, 7],
      color: "#bf05ff",
    },
    {
      name: "Hardware",
      data: [2, 4, 1, 6, 3],
      color: "#e7acfb",
    },
  ],
});
//ceo Assets Asset Value By Category
Highcharts.chart("ceoAssetsAssetValueByCategory", {
  chart: {
    type: "pie",
    // height: 140,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "2",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: -5,
    margin: 0,
    style: { fontSize: "18", color: "#000000" },
  },
  subtitle: {
    text: "Odisha",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: 8,
    margin: 0,
    style: { fontSize: "13", color: "#000000" },
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
      data: [["Odisha", 100]],
      size: "100%",
      innerSize: "70%",
      color: "#9792e8",
      showInLegend: true,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Assets Asset Count By Location
Highcharts.chart("ceoAssetsAssetCountByLocation", {
  chart: {
    type: "pie",
    // height: 140,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "9",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: -5,
    margin: 0,
    style: { fontSize: "18", color: "#000000" },
  },
  subtitle: {
    text: "Count",
    align: "center",
    verticalAlign: "middle",
    floating: true,
    y: 8,
    margin: 0,
    style: { fontSize: "13", color: "#000000" },
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
        ["Odisha", 65],
        ["Unknown", 35],
      ],
      size: "100%",
      innerSize: "70%",
      color: "#ca7ce5",
      showInLegend: true,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Assets Verification Status
Highcharts.chart("ceoAssetsVerificationStatus", {
  chart: {
    type: "column",
    animation: true,
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
    categories: ["Verify", "Verified", "Mismatch"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "",
    },
    gridLineColor: "#e6e6e6",
    labels: { enabled: true },
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
  legend: { enabled: false },

  series: [
    {
      name: "",
      data: [74, 73, 47],
      color: "#ca7ce5",
    },
  ],
});
//ceo Assets Assets Pulled From Pool
Highcharts.chart("ceoAssetsAssetsPulledFromPool", {
  chart: {
    type: "bar",
    animation: true,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  title: {
    text: "",
  },
  xAxis: {
    categories: ["Software", "Cloud", "Hardware"],
  },
  yAxis: {
    min: 0,
    title: {
      text: "# of assets",
    },
  },
  credits: {
    enabled: false,
  },
  legend: {
    reversed: true,
  },
  plotOptions: {
    series: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
      },
    },
  },
  series: [
    {
      name: "Pulled from the pool",
      data: [4, 4, 3],
      color: "#bf05ff",
    },
    {
      name: "Net New Purchase",
      data: [5, 3, 2],
      color: "#ca7ce5",
    },
  ],
});
