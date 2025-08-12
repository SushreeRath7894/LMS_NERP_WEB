// ceo Support Monthly Net Retention
Highcharts.chart("ceoSupportMonthlyNetRetention", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 288,
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
        "Q1 2022",
        "Q2 2022",
        "Q3 2022",
        "Q4 2022",
        "Q1 2023",
        "Q2 2023",
        "Q3 2023",
        "Q4 2023",
      ],
      labels: { style: { fontSize: "10px" } },
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      title: {
        text: "",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "",
      },
      labels: {
        format: "{value}%",
        style: { fontSize: "9px" },
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },
  plotOptions: {
    column: {
      stacking: "normal",
      reversedStacks: false,
    },
  },
  legend: {
    itemStyle: { fontSize: "10px" },
  },
  series: [
    {
      name: "Lost Customers",
      type: "column",
      data: [-20, -20, -20, -20, -20, -20, -20, -20],
      color: "#f58d68",
    },
    {
      name: "New Customers",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#deaaf0",
    },
    {
      name: "Net Retention Rate",
      type: "line",
      data: [40, 45, 50, 70, 75, 64, 53, 48],
      color: "#3f0254",
      yAxis: 1,
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDash",
    },
  ],
});
// ceo Support Request Volume vs Service Level
Highcharts.chart("ceoSupportRequestVolumevsServiceLevel", {
  chart: {
    zoomType: "xy",
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
  credits: { enabled: false },
  xAxis: [
    {
      categories: [
        "Q1 2022",
        "Q2 2022",
        "Q3 2022",
        "Q4 2022",
        "Q1 2023",
        "Q2 2023",
        "Q3 2023",
        "Q4 2023",
      ],
      labels: { style: { fontSize: "10px" } },
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      title: {
        text: "Requests",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "% of Requests Answered",
      },
      labels: {
        format: "{value}%",
        style: { fontSize: "9px" },
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },
  legend: {
    itemStyle: { fontSize: "10px" },
  },
  series: [
    {
      name: "New Customers",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#ca7ce5",
    },
    {
      name: "Net Retention Rate",
      type: "line",
      data: [40, 45, 50, 70, 75, 64, 53, 48],
      color: "#bf05ff",
      yAxis: 1,
      zIndex: 2,
      dashStyle: "ShortDash",
    },
  ],
});
// ceo Support Costs To Revenue
Highcharts.chart("ceoSupportCostsToRevenue", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 244,
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
        "Q1 2022",
        "Q2 2022",
        "Q3 2022",
        "Q4 2022",
        "Q1 2023",
        "Q2 2023",
        "Q3 2023",
        "Q4 2023",
      ],
      labels: { style: { fontSize: "10px" } },
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      title: {
        text: "Revenue",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "% of Support Costs to Revenue",
      },
      labels: {
        format: "{value}%",
        style: { fontSize: "9px" },
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },
  legend: {
    itemStyle: { fontSize: "10px" },
  },
  series: [
    {
      name: "New Customers",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#ca7ce5",
    },
    {
      name: "Net Retention Rate",
      type: "spline",
      data: [40, 45, 50, 70, 75, 64, 53, 48],
      color: "#bf05ff",
      yAxis: 1,
      zIndex: 2,
      dashStyle: "ShortDash",
    },
  ],
});
// ceo Support MRR Growth Rate
Highcharts.chart("ceoSupportMRRGrowthRate", {
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
      categories: [
        "Q1 2022",
        "Q2 2022",
        "Q3 2022",
        "Q4 2022",
        "Q1 2023",
        "Q2 2023",
        "Q3 2023",
        "Q4 2023",
      ],
      labels: { style: { fontSize: "10px" } },
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      title: {
        text: "Revenue",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "% of Support Costs to Revenue",
      },
      labels: {
        format: "{value}%",
        style: { fontSize: "9px" },
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },
  legend: {
    itemStyle: { fontSize: "10px" },
  },
  series: [
    {
      name: "New Customers",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#bf05ff",
    },
    {
      name: "Net Retention Rate",
      type: "spline",
      data: [40, 45, 50, 70, 75, 64, 53, 48],
      color: "#f58d68",
      yAxis: 1,
      zIndex: 2,
      dashStyle: "ShortDash",
    },
  ],
});
// ceo Support Customer Satisfaction
Highcharts.chart("ceoSupportCustomerSatisfaction", {
  chart: {
    type: "pie",
    animation: true,
    height: 294,
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
  colors: ["#56156c", "#f79c92", "#ca7ce5", "#bf05ff", "#b422b6"],
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
  legend: {
    itemStyle: { fontSize: "11px" },
  },
  series: [
    {
      name: "",
      data: [
        ["Very Satisfied", 34],
        ["Satisfied", 19],
        ["Neutral", 16],
        ["Unsatisfied", 15],
        ["Very Unsatisfied", 16],
      ],
      size: "50%",
      innerSize: "45%",
      showInLegend: true,
      dataLabels: {
        enabled: true,
        format: "{point.percentage:.1f} %",
      },
    },
  ],
});
// ceo Support Abandon Rate Phone
Highcharts.chart("ceoSupportAbandonRatePhone", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 100,
  },

  title: {
    text: "40%",
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
        color: "#9792e8",
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
// ceo Support Abandon Rate Live Chat
Highcharts.chart("ceoSupportAbandonRateLiveChat", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 100,
  },

  title: {
    text: "40%",
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
        color: "#9792e8",
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
// ceo Support Net Promoter Score
Highcharts.chart("ceoSupportNetPromoterScore", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 200,
  },

  title: {
    text: "+40",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 18,
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
    min: -100,
    max: 100,
    // tickPixelInterval: 72,
    tickPosition: "inside",
    tickColor: "transparent",
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
      enabled: true,
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
        color: "#9792e8",
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
// ceo Support Average Customer Effect Score
Highcharts.chart("ceoSupportAverageCustomerEffectScore", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    marginBottom: 90,
    height: 267,
  },

  title: {
    text: "1.7",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 10,
    // margin: 0,
    style: { fontSize: "20", color: "#61426c" },
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
    max: 10,
    // tickPixelInterval: 72,
    // tickPosition: "inside",
    tickColor: "transparent",
    // tickLength: 0,
    // tickWidth: 0,
    minorTickInterval: null,
    labels: {
      enabled: true,
      distance: 15,
    },
    lineWidth: 0,
    plotBands: [
      {
        from: 0,
        to: 2,
        thickness: 15,
        color: "#bf05ff",
      },
      {
        from: 2,
        to: 4,
        thickness: 15,
        color: "#9792e8",
      },
      {
        from: 4,
        to: 10,
        thickness: 15,
        color: "#f58d68",
      },
    ],
  },

  series: [
    {
      name: "",
      data: [1.5],
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
// ceo Support Customer Retention
Highcharts.chart("ceoSupportCustomerRetention", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 116,
  },

  title: {
    text: "+40",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 10,
    margin: 0,
    style: { fontSize: "13", color: "#61426c" },
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
        color: "#9792e8",
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
// ceo Support Response Time By Weekday
Highcharts.chart("ceoSupportResponseTimeByWeekday", {
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
    categories: [
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
      "Sunday",
    ],
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
      data: [13, 15, 15, 34, 23, 18, 24],
      color: "#bf05ff",
    },
  ],
});
// ceo Support Cost Per Resolution
Highcharts.chart("ceoSupportCostPerResolution", {
  chart: {
    type: "bar",
    animation: true,
    height: 270,
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
    categories: ["Email", "Phone", "Chat", "Social", "Voicemail"],
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
// ceo Support Standard Request
Highcharts.chart("ceoSupportStandardRequest", {
  chart: {
    type: "pie",
    height: 70,
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
    y: 30,
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
// ceo Support Special Request
Highcharts.chart("ceoSupportSpecialRequest", {
  chart: {
    type: "pie",
    margin: 0,
    height: 70,
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
    y: 30,
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
      colors: ["#b422b6", "#cccccc"],
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
      color: "#b422b6",
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
// ceo Support Average Resolution Time
Highcharts.chart("ceoSupportAverageResolutionTime", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 225,
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
        "Q1 2022",
        "Q2 2022",
        "Q3 2022",
        "Q4 2022",
        "Q1 2023",
        "Q2 2023",
        "Q3 2023",
        "Q4 2023",
      ],
      labels: { style: { fontSize: "10px" } },
    },
  ],
  yAxis: [
    {
      title: {
        text: "",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
  ],
  tooltip: {
    shared: true,
  },
  plotOptions: {
    area: {
      stacking: "normal",
      reversedStacks: false,
    },
  },
  legend: {
    enabled: false,
  },
  series: [
    {
      name: "",
      type: "area",
      data: [20, 25, 26, 20, 22, 20, 27, 23],
      color: "#deaaf0",
    },
    {
      name: "",
      type: "area",
      data: [40, 45, 46, 40, 42, 40, 47, 43],
      color: "#9792e8",
    },
    {
      name: "",
      type: "line",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#bf05ff",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDash",
    },
    {
      name: "",
      type: "line",
      data: [40, 40, 40, 40, 40, 40, 40, 40],
      color: "#4e48a8",
      zIndex: 2,
      marker: false,
      dashStyle: "ShortDash",
    },
  ],
});
// ceo Support Call Resolution
Highcharts.chart("ceoSupportCallResolution", {
  chart: {
    type: "line",
    animation: true,
    height: 325,
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
      text: "Percentage of Calls",
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
      name: "First Call",
      data: [24, 37, 29, 21, 32, 30, 81, 68, 36, 33, 50, 90],
      marker: false,
      color: "#b605f3",
      dashStyle: "ShortDash",
    },
    {
      name: "Second Call",
      data: [114, 30, 16, 19, 20, 24, 32, 30, 27, 29, 25, 19],
      marker: false,
      color: "#f58d68",
      dashStyle: "LongDash",
    },
    {
      name: "Third Call or More",
      data: [91, 95, 111, 142, 189, 118, 102, 133, 110, 150, 210, 21],
      marker: false,
      color: "#51a4d6",
      dashStyle: "ShortDashDot",
    },
    {
      name: "Unresolved",
      data: [100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100],
      marker: false,
      color: "#9792e8",
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
// ceo Support Total And Solved Tickets By Channel
Highcharts.chart("ceoSupportTotalAndSolvedTicketsByChannel", {
  chart: {
    type: "bar",
    animation: true,
    height: 230,
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
    categories: ["Email", "Phone", "Chat", "Social", "Voicemail"],
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
      },
    },
  },

  series: [
    {
      name: "",
      data: [13, 15, 15, 34, 23],
      color: "#bf05ff",
    },
    {
      name: "",
      data: [13, 15, 15, 34, 23],
      color: "#ca7ce5",
    },
  ],
});
// ceo Support Average Number Of Issues
Highcharts.chart("ceoSupportAverageNumberOfIssues", {
  chart: {
    type: "column",
    animation: true,
    height: 270,
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
      enabled: true,
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
        enabled: false,
        formatter: function () {
          return this.total;
        },
      },
    },
  },
  series: [
    {
      name: "Call",
      data: [23, 17, 45, 12, 56, 34, 23, 56, 34, 12, 23],
      color: "#bf05ff",
    },
    {
      name: "Email",
      data: [12, 36, 13, 26, 56, 34, 76, 43, 23, 65, 43, 76],
      color: "#ca7ce5",
    },
    {
      name: "Chat",
      data: [12, 36, 13, 26, 67, 43, 23, 45, 43, 23, 54, 56],
      color: "#f79c92",
    },
  ],
});
// ceo Support Monthly Customer Churn Rate
Highcharts.chart("ceoSupportMonthlyCustomerChurnRate", {
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
      zones: [
        {
          value: 30,
          color: "#9792e8",
        },
        {
          color: "#bf05ff",
        },
      ],
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
// ceo Support Abandon Rate Phone Line
Highcharts.chart("ceoSupportAbandonRatePhoneLine", {
  chart: {
    type: "line",
    animation: true,
    height: 143,
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
    labels: {
      style: { fontSize: "8px" },
    },
  },
  legend: { enabled: false },

  plotOptions: {
    series: {
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
      dashStyle: "ShortDash",
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
    },
  ],
});
// ceo Support Abandon Rate Live Chat Line
Highcharts.chart("ceoSupportAbandonRateLiveChatLine", {
  chart: {
    type: "line",
    animation: true,
    height: 143,
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
    labels: {
      style: { fontSize: "8px" },
    },
  },
  legend: { enabled: false },

  plotOptions: {
    series: {
      lineWidth: 1,
      label: {
        connectorAllowed: false,
      },
      dashStyle: "ShortDash",
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
    },
  ],
});
// ceo Support Monthly Revenue Churn Rate
Highcharts.chart("ceoSupportMonthlyRevenueChurnRate", {
  chart: {
    type: "line",
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
          color: "#bf05ff",
        },
      ],
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
// ceo Support Customer Retention Area
Highcharts.chart("ceoSupportCustomerRetentionArea", {
  chart: {
    zoomType: "x",
    height: 200,
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
    title: {
      text: "",
    },
  },
  yAxis: {
    title: {
      text: "",
    },
  },
  legend: false,
  tooltip: {
    shared: true,
  },
  legend: {
    enabled: false,
  },
  plotOptions: {
    area: {
      lineColor: "#bf05ff",
      fillColor: {
        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
        stops: [
          [0, "#bf05ff"],
          [1, "#f4f4f4"],
        ],
      },
      lineWidth: 1,
      marker: {
        enabled: false,
        fillColor: "#bf05ff",
      },
      shadow: false,
      states: {
        hover: {
          lineWidth: 1,
        },
      },
      threshold: null,
    },
  },

  series: [
    {
      type: "area",
      name: "",
      data: [20, 22, 23, 25, 18, 14, 12, 15, 23, 19, 23, 29],
    },
  ],
});
// ceo Support Avg Costs per Support
Highcharts.chart("ceoSupportAvgCostsperSupport", {
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
    },
  ],
});
