//operating profit margin
Highcharts.chart("ceoOperatingProfitMargin", {
  chart: {
    type: "column",
    animation: true,
    height: 192,
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
    title: {
      text: "",
    },
    gridLineColor: "transparent",
    labels: { enabled: false },
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
      data: [74, 73, 47, 57, 64, 35, 36],
      color: "#bf05ff",
    },
  ],
});
//accounts payable turnover
Highcharts.chart("ceoAccountsPayableTurnover", {
  chart: {
    type: "column",
    animation: true,
    height: 260,
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
    categories: ["1-30 Days", "31-60 Days", "61-90 Days", "90+ Days"],
  },
  yAxis: {
    title: {
      text: "",
    },
    gridLineColor: "transparent",
    labels: { enabled: false },
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
      data: [74, 73, 47, 57],
      color: "#d04cfd",
    },
  ],
});
//employee satisfaction
Highcharts.chart("ceoEmployeeSatisfaction", {
  chart: {
    type: "column",
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
    title: {
      text: "",
    },
    gridLineColor: "transparent",
    labels: { enabled: false },
  },
  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        color: "#000000",
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
//Return On Equity
Highcharts.chart("ceoReturnOnEquity", {
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
    enabled: false,
  },
  plotOptions: {
    plotWidth: 10,
  },
  series: [
    {
      name: "",
      type: "column",
      color: "#bf05ff",
      data: [
        27.6, 28.8, -21.7, 34.1, 23.6, -34.7, -28.8, 21.7, 34.1, 23.6, -34.7,
        43.4,
      ],
    },
    {
      name: "Benchmark",
      type: "line",
      data: [10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10],
      color: "#7c08a4",
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
//Return On Assets
Highcharts.chart("ceoReturnOnAssets", {
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
      color: "#f58d68",
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
//actual vs forecast expenses
Highcharts.chart("ceoActualVsForecastExpenses", {
  chart: {
    animated: true,
    height: 257,
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
  xAxis: {
    categories: ["Sales", "Marketing", "General & Admin", "Other Expenses"],
  },
  yAxis: {
    title: {
      text: "",
    },
  },

  series: [
    {
      type: "bar",
      name: "Actual",
      data: [59, 83, 228, 184],
      color: "#ca7ce5",
    },
    {
      name: "Forecast",
      color: "transparent",
      lineColor: "transparent",
      data: [47, 83.33, 239.33, 175.66],
      type: "spline",
      dataLabels: {
        enabled: false,
        // rotation: 0,
        // color: '#FFFFFF',
        // backgroundColor: '#bf05ff',
        // align: 'right',
        // format: '{point.y:.1f}',
        // style: {
        //     fontSize: '10px',
        // }
      },
      marker: {
        lineWidth: 2,
        fillColor: "#bf05ff",
      },
    },
  ],
});
//ceo variable expenses
Highcharts.chart("ceoVariableExpenses", {
  chart: {
    type: "pie",
    height: 162,
    margin: 10,
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
  },
  colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'],
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  series: [
    {
      name: "",
      data: [
        ["Salaries", 28],
        ["Marketing", 6],
        ["Accounting and Legal", 62],
        ["Rent and Utilities", 62],
        ["Office and Supplies", 62],
        ["Insurance", 62],
      ],
      size: "100%",
      innerSize: "50%",
      showInLegend: false,
      dataLabels: {
        enabled: true,
        formatter: function () {
          return Math.ceil(Math.round(this.percentage * 100) / 100) + " %";
        },
      },
    },
  ],
});
//ceo Fixed Expenses
Highcharts.chart("ceoFixedExpenses", {
  chart: {
    type: "pie",
    height: 162,
    margin: 10,
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
  },
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff', '#b422b6', '#d4c9d8'],
  series: [
    {
      name: "",
      data: [
        ["Salaries", 28],
        ["Marketing", 6],
        ["Accounting and Legal", 62],
        ["Rent and Utilities", 62],
        ["Office and Supplies", 62],
        ["Insurance", 62],
      ],
      size: "100%",
      innerSize: "50%",
      showInLegend: false,
      dataLabels: {
        enabled: true,
        formatter: function () {
          return Math.ceil(Math.round(this.percentage * 100) / 100) + " %";
        },
      },
    },
  ],
});
//ceo vendor payment error rate
Highcharts.chart("ceoVendorPaymentErrorRate", {
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
    labels: {
      rotation: -45,
      style: {
        fontSize: "10px",
        fontFamily: "Verdana, sans-serif",
        color: "#000000",
      },
    },
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
      name: "Vendor Payment Error Rate",
      data: [
        43934, 48656, 65165, 81827, 112143, 142383, 71533, 65174, 15557, 16454,
        15610,
      ],
      color: "#bf05ff",
      marker: {
        enabled: false,
      },
    },
    {
      type: "spline",
      name: "Average Vendor Payment Error Rate",
      data: [
        65165, 65165, 65165, 65165, 65165, 65165, 65165, 65165, 65165, 65165,
        65165,
      ],
      color: "#deaaf0",
      marker: {
        enabled: false,
      },
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
//ceo quick Ratio
Highcharts.chart("ceoQuickRatio", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    // height: '71.2%'
    height: 100,
  },

  title: {
    text: "1.37",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 90,
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
        to: 50,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 50,
        to: 70,
        color: "#9792e8",
        thickness: 15,
      },
      {
        from: 70,
        to: 100,
        color: "#deaaf0",
        thickness: 15,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [100],
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
//ceo Current Ratio qr
Highcharts.chart("ceoCurrentRatio", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 100,
  },

  title: {
    text: "1.37",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 90,
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
        to: 50,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 50,
        to: 70,
        color: "#9792e8",
        thickness: 15,
      },
      {
        from: 70,
        to: 100,
        color: "#deaaf0",
        thickness: 15,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [100],
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
//ceo Operating Profit Margin Gauge
Highcharts.chart("ceoOperatingProfitMarginGauge", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: "40%",
  },

  title: {
    text: "42 %",
    align: "center",
    verticalAlign: "center",
    floating: true,
    y: 100,
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
        to: 40,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 40,
        to: 100,
        color: "#f4ddfc",
        thickness: 15,
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
//ceo Economic Value Added
Highcharts.chart("ceoEconomicValueAdded", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 218,
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
        to: 50,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 50,
        to: 70,
        color: "#9792e8",
        thickness: 15,
      },
      {
        from: 70,
        to: 100,
        color: "#deaaf0",
        thickness: 15,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [100],
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
//ceo Net Profit Margin
Highcharts.chart("ceoNetProfitMargin", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 250,
    marginBottom: 30,
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
    min: 0,
    max: 100,
    // tickPixelInterval: 72,
    tickPosition: "inside",
    tickColor: "transparent",
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
      distance: 10,
      style: {
        fontSize: "11px",
      },
    },
    lineWidth: 0,
    plotBands: [
      {
        from: 0,
        to: 50,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 50,
        to: 70,
        color: "#9792e8",
        thickness: 15,
      },
      {
        from: 70,
        to: 100,
        color: "#deaaf0",
        thickness: 15,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [100],
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
//ceo Payroll Headcount Ratio
Highcharts.chart("ceoPayrollHeadcountRatio", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 218,
    marginBottom: 30,
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
    min: 0,
    max: 100,
    tickPosition: "inside",
    tickColor: "transparent",
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
      distance: 10,
      style: {
        fontSize: "11px",
      },
    },
    lineWidth: 0,
    plotBands: [
      {
        from: 0,
        to: 50,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 50,
        to: 70,
        color: "#9792e8",
        thickness: 15,
      },
      {
        from: 70,
        to: 100,
        color: "#deaaf0",
        thickness: 15,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [100],
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
//ceo Operating Expenses Ratio
Highcharts.chart("ceoOperatingExpensesRatio", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 305,
    backgroundColor: "transparent",
  },

  title: {
    text: "40%",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 0,
    margin: 0,
    style: { fontSize: "24", color: "#bf05ff" },
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
        to: 50,
        color: "#bf05ff",
        thickness: 15,
      },
      {
        from: 50,
        to: 70,
        color: "#9792e8",
        thickness: 15,
      },
      {
        from: 70,
        to: 100,
        color: "#deaaf0",
        thickness: 15,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [78],
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
//ceo Employee Satisfaction Gauge
Highcharts.chart("ceoEmployeeSatisfactionGauge", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 90,
  },

  title: {
    text: "40%",
    align: "center",
    verticalAlign: "bottom",
    floating: true,
    y: 20,
    margin: 0,
    style: { fontSize: "12", color: "#bf05ff" },
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
    min: -100,
    max: 100,
    tickPosition: "inside",
    tickColor: "transparent",
    tickLength: 0,
    tickWidth: 0,
    minorTickInterval: null,
    labels: {
      distance: 10,
      style: {
        fontSize: "8px",
      },
    },
    lineWidth: 0,
    plotBands: [
      {
        from: -100,
        to: 100,
        color: "#f58d68",
        thickness: 8,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [40],
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
//ceo Berry Ratio
Highcharts.chart("ceoBerryRatio", {
  chart: {
    animation: true,
    height: 303,
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
      name: "Berry Ratio",
      data: [
        4916, 7941, 9742, 19851, 22490, 10282, 8121, 6885, 13726, 24243, 31050,
        26735,
      ],
      lineWidth: 2,
      color: '#bf05ff'
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
//ceo accounts receivable turnover
Highcharts.chart("ceoAccountsReceivableTurnover", {
  chart: {
    type: "pie",
    height: 204,
    margin: 10,
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
  },
  plotOptions: {
    pie: {
      shadow: false,
    },
  },
  colors: ['#56156c', '#f79c92', '#ca7ce5', '#bf05ff'],
  series: [
    {
      name: "",
      data: [
        ["1-30 Days", 61],
        ["31-60 Days", 21],
        ["61-90 Days", 6],
        ["90+ Days", 12],
      ],
      size: "100%",
      innerSize: "50%",
      showInLegend: false,
      dataLabels: {
        enabled: true,
        distance: -10,
        color: "#000000",
        formatter: function () {
          return Math.ceil(Math.round(this.percentage * 100) / 100) + " %";
        },
      },
    },
  ],
});
//ceo current ratio
Highcharts.chart("ceoCurrentRatioLine", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 210,
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
  legend: false,
  xAxis: [
    {
      categories: [
        "2010",
        "2011",
        "2012",
        "2013",
        "2014",
        "2015",
        "2016",
        "2017",
        "2018",
        "2019",
        "2020",
        "2021",
      ],
      crosshair: true,
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      title: {
        text: "Assets and Liabilities",
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "Current Ratio",
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },
  series: [
    {
      name: "Assets and Liabilities",
      type: "column",
      yAxis: 1,
      data: [
        27.6, 28.8, 21.7, 34.1, 29.0, 28.4, 45.6, 51.7, 39.0, 60.0, 28.6, 32.1,
      ],
      color: "#ca7ce5",
    },
    {
      name: "Current Ratio",
      type: "column",
      yAxis: 1,
      data: [7.6, 8.8, 1.7, 4.1, 9.0, 8.4, 15.6, 21.7, 19.0, 30.0, 8.6, 12.1],
      color: "#9792e8",
    },
    {
      name: "",
      type: "line",
      data: [13.6, 14.9, 5.8, 0.7, 3.1, 13.0, 14.5, 10.8, 5.8, 0.7, 11.0, 16.4],
      color: "#bf05ff",
    },
  ],
});
//ceo Cash Conversion Cycle
Highcharts.chart("ceoCashConversionCycle", {
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
  credits: false,
  xAxis: [
    {
      categories: ["2020", "2021", "2022", "2023"],
      crosshair: true,
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      labels: {
        format: "{value}",
      },
      min: 0,
      title: {
        text: "DSO | DIO | DPO ",
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "CCC",
      },
      min: -100,
      labels: {
        format: "{value}",
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
    },
    dataLabels: {
      enabled: true,
    },
  },
  legend: {
    enabled: false,
  },
  series: [
    {
      name: "DSO",
      type: "column",
      stack: 1,
      yAxis: 1,
      data: [49.9, 71.5, 106.4, 129.2],
      color: '#9792e8'
    },
    {
      name: "DIO",
      type: "column",
      stack: 1,
      yAxis: 1,
      data: [49.9, 71.5, 106.4, 129.2],
      color: '#deaaf0'
    },
    {
      name: "DPO",
      type: "column",
      stack: 1,
      yAxis: 1,
      data: [49.9, 71.5, 106.4, 129.2],
      color: '#bf05ff'
    },
    {
      name: "CCC",
      type: "spline",
      data: [46.0, 36.9, 49.5, 58.5],
      color: '#f58d68'
    },
  ],
});
//ceoQratioCratioSpline
Highcharts.chart("ceoQratioCratioSpline", {
  chart: {
    type: "areaspline",
    zoomType: "xy",
    animation: true,
    height: 132,
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
  },
  legend: false,
  plotOptions: {
    area: {
      stacking: "normal",
      lineWidth: 1,
    },
  },
  series: [
    {
      name: "",
      data: [13234, 12729, 11533, 17798, 10398, 12811, 15483, 16196, 16214],
      marker: false,
      lineColor: "transparent",
      color: '#deaaf0'
    },
    {
      name: "",
      data: [6685, 6535, 6389, 6384, 6251, 5725, 5631, 5047, 5039],
      marker: false,
      lineColor: "transparent",
      color: '#9792e8'
    },
  ],
});
//ceo Gross Profit
Highcharts.chart("ceoGrossProfit", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    backgroundColor: "transparent",
    height: 233,
  },
  colors: ["#003961"],
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
  pane: {
    startAngle: -98,
    endAngle: 99.9,
    background: null,
    center: ["50%", "75%"],
    size: "110%",
  },

  // the value axis
  yAxis: {
    min: 0,
    max: 50,
    lineWidth: 0,
    tickPixelInterval: 0,
    tickPosition: "inside",
    tickColor: "#FFFFFF",
    tickLength: 0,
    minorTickInterval: null,
    labels: {
      enabled: false,
    },
    plotBands: [
      {
        from: 0,
        to: 22,
        color: "#bf05ff",
        thickness: 25,
      },
      {
        from: 22,
        to: 70,
        color: "#f2d8fc",
        thickness: 25,
      },
    ],
  },

  series: [
    {
      name: "",
      data: [22],

      dataLabels: {
        enabled: false,
      },
      dial: {
        radius: "0%",
      },
      pivot: {
        radius: 0,
      },
    },
  ],
});
