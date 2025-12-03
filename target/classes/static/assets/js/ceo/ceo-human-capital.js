//ceo Hc training cost for 3-year
Highcharts.chart("ceoTrainingCostForThreeYear", {
  chart: {
    zoomType: "xy",
    animation: true,
    backgroundColor: "transparent",
    height: 306,
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
      categories: ["1", "2", "3"],
      crosshair: true,

      labels: {
        style: {
          fontSize: "9px",
        },
      },
      title: {
        text: "Training Years",
      },

      gridLineColor: "transparent",
      lineColor: "transparent",
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
      name: "Net Costs",
      type: "column",
      yAxis: 1,
      data: [5, 3, 2],
      color: "#deaaf0",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "center",
        format: "{y}",
        style: {
          fontSize: "9px",
        },
      },
    },
    {
      name: "Returns",
      type: "column",
      yAxis: 1,
      data: [11.4, 13.8, 16.6],
      color: "#bf05ff",
      tooltip: {
        valueSuffix: " mm",
      },
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "center",
        format: "{y}",
        style: {
          fontSize: "9px",
        },
      },
    },
  ],
});
//ceo Hc training cost for 2-year
Highcharts.chart("ceoTrainingCostForTwoYear", {
  chart: {
    zoomType: "xy",
    animation: true,
    backgroundColor: "transparent",
    height: 306,
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
      title: {
        text: "Training Years",
      },
      categories: ["1", "2"],
      crosshair: true,
      gridLineColor: "transparent",
      lineColor: "transparent",
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
        gridLineColor: "transparent",
        lineColor: "transparent",

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
  // tooltip: {
  //     shared: true
  // },
  legend: {
    align: "center",
    verticalAlign: "bottom",
    floating: false,
    backgroundColor: "transparent",
  },
  series: [
    {
      name: "Net Costs",
      type: "column",
      yAxis: 1,
      data: [5.3, 5.4],
      color: "#deaaf0",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "center",
        format: "{y}",
        style: {
          fontSize: "9px",
        },
      },
    },
    {
      name: "Returns",
      type: "column",
      yAxis: 1,
      data: [10.6, 12.4],
      color: "#bf05ff",
      dataLabels: {
        enabled: true,
        rotation: 0,
        color: "#000000",
        align: "center",
        format: "{y}",
        style: {
          fontSize: "9px",
        },
      },
    },
  ],
});
//ceo Hc Recruitment Conversion Rate 1
Highcharts.chart("ceoHcRecruitmentConversionRate1", {
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
//ceo Hc Recruitment Conversion Rate 2
Highcharts.chart("ceoHcRecruitmentConversionRate2", {
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
//ceo Hc Recruitment Conversion Rate 3
Highcharts.chart("ceoHcRecruitmentConversionRate3", {
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
// ceo Hc Avg Absenteesim Rate
Highcharts.chart("ceoHcAvgAbsenteesimRate", {
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
    style: { fontSize: "12", color: "#f79c92" },
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
        color: "#f79c92",
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
//ceo Hc Avg Absenteesim Rate Last 5 Years
Highcharts.chart("ceoHcAvgAbsenteesimRateLast5Years", {
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
    categories: ["2018", "2019", "2020", "2021", "2022", "2023", "2024"],
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
//ceo Hc Avg Overtime Hours By Age Group
Highcharts.chart("ceoHcAvgOvertimeHoursByAgeGroup", {
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
    categories: ["<=25", "26-35", "36-45", "46-55", ">55"],
  },
  yAxis: {
    title: {
      text: "hours",
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
      data: [7.4, 7.3, 4.7, 5.7, 6.4],
      color: "#bf05ff",
    },
  ],
});
//ceo Hc Turnover rate by Group Voluntary Leavers
Highcharts.chart("ceoHcTurnoverRatebyGroupVoluntaryLeavers", {
  chart: {
    type: "bar",
    animation: true,
    height: 305,
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
      "Black",
      "Company Overall",
      "Hispanic & Latino",
      "Female",
      "Other",
      "Asian",
      "Divers/Others",
      "Persons with disabilities",
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
      data: [74, 73, 47, 53, 67, 62, 42, 22],
      color: "#ce7fea",
    },
  ],
});
//ceo Hc Dismissal Rate
Highcharts.chart("ceoHcDismissalRate", {
  chart: {
    type: "bar",
    animation: true,
    height: 310,
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
    categories: ["6 months", "1 year", "2 years", "5 years"],
  },
  yAxis: {
    min: 0,
    max: 20,
    title: {
      text: "",
    },
    labels: {
      format: "{value}%",
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
      data: [13, 15, 6.8, 4.9],
      color: "#9e4fb9",
    },
  ],
});
//ceo Hc Gender Ratio by Department
Highcharts.chart("ceoHcGenderRatiobyDepartment", {
  chart: {
    type: "bar",
    animation: true,
    height: 268,
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
    categories: [
      "Admin & HR",
      "Customer Support",
      "Finance & Accounting",
      "IT",
      "Product",
      "Sales & Marketing",
    ],
    labels: {
      style: { fontSize: "10px" },
    },
  },
  yAxis: {
    min: 0,
    max: 100,
    title: {
      text: "",
    },
    labels: {
      style: { fontSize: "10px" },
    },
  },
  legend: {
    reversed: true,
  },
  plotOptions: {
    series: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
        format: "{y}%",
      },
    },
  },
  series: [
    {
      name: "Female",
      data: [31, 41, 48, 84, 57, 72],
      color: "#deaaf0",
    },
    {
      name: "Male",
      data: [69, 59, 52, 16, 43, 28],
      color: "#bf05ff",
    },
  ],
});
//ceo Hc Role Level By Gender
Highcharts.chart("ceoHcRoleLevelByGender", {
  chart: {
    type: "bar",
    animation: true,
    height: 325,
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
    categories: ["Management", "Non-management"],
    labels: {
      style: { fontSize: "10px" },
    },
  },
  yAxis: {
    min: 0,
    max: 100,
    title: {
      text: "",
    },
    labels: {
      format: "{value}%",
      style: { fontSize: "10px" },
    },
  },
  legend: {
    reversed: true,
  },
  plotOptions: {
    series: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
        format: "{y}%",
      },
    },
  },
  series: [
    {
      name: "Female & Divers/Others",
      data: [12, 37],
      color: "#deaaf0",
    },
    {
      name: "Males",
      data: [88, 63],
      color: "#bf05ff",
    },
  ],
});
//ceo Hc Job Tenure By Seniority Level
Highcharts.chart("ceoHcJobTenureBySeniorityLevel", {
  chart: {
    type: "bar",
    animation: true,
    height: 268,
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
    categories: ["Executive", "Senior-level", "Mid-level", "Entry-level"],
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
      data: [4, 5, 2, 7],
      color: "#deaaf0",
      pointPadding: 0.1,
    },
    {
      name: "Job Tenure",
      data: [3, 10, 8, 4],
      color: "#bf05ff",
      pointPadding: 0.3,
    },
  ],
});
//ceo Hc Turnover Rate By Age Group
Highcharts.chart("ceoHcTurnoverRateByAgeGroup", {
  chart: {
    type: "column",
    animation: true,
    height: 309,
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
    categories: ["<=25", "26-35", "36-45", "46-55", ">55"],
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
      name: "Voluntary Loss",
      data: [23, 17, 45, 12, 34],
      color: "#deaaf0",
    },
    {
      name: "Involuntary Loss",
      data: [12, 36, 13, 26, 19],
      color: "#bf05ff",
    },
  ],
});
//ceo Hc Talent Turnover Rate By Department
Highcharts.chart("ceoHcTalentTurnoverRateByDepartment", {
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
    categories: ["Finance", "HR", "IT", "Marketing"],
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
        format: "{y}%",
      },
    },
  },
  series: [
    {
      name: "Voluntary",
      data: [23, 17, 45, 12],
      color: "#deaaf0",
    },
    {
      name: "Involuntary",
      data: [12, 36, 13, 26],
      color: "#bf05ff",
    },
  ],
});
//ceo Hc Departments by Ethnicity and Gender
Highcharts.chart("ceoHcDepartmentsbyEthnicityandGender", {
  chart: {
    type: "column",
    animation: true,
    height: 217,
    marginTop: 30,
    marginBottom: 0,
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
      "Admin/HR/Finance",
      "Manufacturing",
      "IT",
      "Sales/Marketing",
      "Customer Support",
    ],
    lineColor: "transparent",
    opposite: true,
    labels: {
      rotation: 0,
      style: {
        fontSize: "9px",
      },
    },
  },
  credits: { enabled: false },
  yAxis: {
    min: 0,
    max: 100,
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
    reversedStacks: false,
  },
  legend: {
    enabled: false,
  },
  plotOptions: {
    column: {
      stacking: "normal",
      dataLabels: {
        enabled: true,
        format: "{y}%",
        style: { fontSize: "9px" },
      },
    },
  },
  series: [
    {
      name: "White",
      data: [71, 56, 65, 49, 48],
      color: "#56156c",
    },
    {
      name: "Asian",
      data: [null, 13, null, 8, null],
      color: "#f79c92",
    },
    {
      name: "Black",
      data: [7, 11, 9, 10, 11],
      color: "#ca7ce5",
    },
    {
      name: "Latino",
      data: [22, 11, 22, 26, 36],
      color: "#bf05ff",
    },
    {
      name: "Other",
      data: [null, 14, 4, 7, 5],
      color: "#b422b6",
    },
  ],
});
//ceo Hc Salary Cost by Department
Highcharts.chart("ceoHcSalaryCostbyDepartment", {
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
        ["Admin & HR", 34],
        ["Customer Support", 19],
        ["IT", 16],
        ["Finance & Accounting", 15],
        ["Product", 10],
        ["Sales & Marketing", 6],
      ],
      size: "100%",
      innerSize: "65%",
      showInLegend: false,
      dataLabels: {
        enabled: true,
        format: "{point.percentage:.1f} %",
        distance: -10,
        style: { fontSize: "10px" },
      },
    },
  ],
});
//ceo Hc Avg Cost Of Hiring By Seniority Level
Highcharts.chart("ceoHcAvgCostOfHiringBySeniorityLevel", {
  chart: {
    type: "pie",
    animation: true,
    height: 234,
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
  colors: ["#f79c92", "#bf05ff", "#d4c9d8"],
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
        ["Junior", 25],
        ["Mid-level", 32],
        ["Senior", 43],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: true,
        format: "{point.percentage:.1f} %",
        distance: -25,
        style: { fontSize: "10px" },
      },
    },
  ],
});
//ceo Hc Avg Hiring Costs This Year
Highcharts.chart("ceoHcAvgHiringCostsThisYear", {
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
      color: "#ca7ce5",
      showInLegend: false,
      marker: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Female to Male Ratio This Year
Highcharts.chart("ceoHcFemaletoMaleRatioThisYear", {
  chart: {
    type: "pie",
    animation: true,
    height: 80,
    margin: 0,
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
  colors: ["#f79c92", "#d4c9d8"],
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
        ["Male", 75],
        ["Female", 25],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Departments by Ethnicity and Gender Admin
Highcharts.chart("ceoHcDepartmentsByEthnicityandGenderAdmin", {
  chart: {
    type: "pie",
    animation: true,
    height: 60,
    margin: 0,
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
  colors: ["#f79c92", "#d4c9d8"],
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
        ["Male", 75],
        ["Female", 25],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Departments by Ethnicity and Gender It
Highcharts.chart("ceoHcDepartmentsByEthnicityandGenderIt", {
  chart: {
    type: "pie",
    animation: true,
    height: 60,
    margin: 0,
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
  colors: ["#f79c92", "#d4c9d8"],
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
        ["Male", 75],
        ["Female", 25],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Departments by Ethnicity and Gender Sales
Highcharts.chart("ceoHcDepartmentsByEthnicityandGenderSales", {
  chart: {
    type: "pie",
    animation: true,
    height: 60,
    margin: 0,
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
  colors: ["#f79c92", "#d4c9d8"],
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
        ["Male", 75],
        ["Female", 25],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Departments by Ethnicity and Gender Manufacturing
Highcharts.chart("ceoHcDepartmentsByEthnicityandGenderManufacturing", {
  chart: {
    type: "pie",
    animation: true,
    height: 60,
    margin: 0,
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
  colors: ["#f79c92", "#d4c9d8"],
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
        ["Male", 75],
        ["Female", 25],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Departments by Ethnicity and Gender Support
Highcharts.chart("ceoHcDepartmentsByEthnicityandGenderSupport", {
  chart: {
    type: "pie",
    animation: true,
    height: 60,
    margin: 0,
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
  colors: ["#f79c92", "#d4c9d8"],
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
        ["Male", 75],
        ["Female", 25],
      ],
      size: "100%",
      innerSize: "0%",
      showInLegend: false,
      dataLabels: {
        enabled: false,
      },
    },
  ],
});
//ceo Hc Talent Satisfaction
Highcharts.chart("ceoHcTalentSatisfaction", {
  chart: {
    type: "gauge",
    plotBackgroundColor: null,
    plotBackgroundImage: null,
    plotBorderWidth: 0,
    plotShadow: false,
    height: 180,
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
        color: "#f79c92",
      },
      {
        from: -20,
        to: 40,
        thickness: 15,
        color: "#bf05ff",
      },
      {
        from: 40,
        to: 100,
        thickness: 15,
        color: "#d4c9d8",
      },
    ],
  },

  series: [
    {
      name: "",
      data: [68],
      // dataLabels: {
      //     enabled: false,
      // },
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
//ceo Hc OLE Over Last 5 Years
Highcharts.chart("ceoHcOLEOverLast5Years", {
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
    style: { fontSize: "12", color: "#f79c92" },
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
        color: "#f79c92",
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
//ceo Hc OLE Over Last 5 Years Line
Highcharts.chart("ceoHcOLEOverLast5YearsLine", {
  chart: {
    animation: true,
    height: 180,
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
    lineColor: "transparent,",
  },
  credits: { enabled: false },

  xAxis: {
    tickLength: 0,
    tickWidth: 0,
    showInLegend: false,
    categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
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
      name: "",
      data: [43934, 48656, 65165, 81827, 112143, 142383],
      color: "#ca7ce5",
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
//ceo Hc Part-Time vs Full-Time Employees
Highcharts.chart("ceoHcPartTimeVsFullTimeEmployees", {
  chart: {
    zoomType: "xy",
    animation: true,
    height: 355,
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
      categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
      crosshair: true,
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      labels: {
        format: "{value}%",
      },
      title: {
        text: "Full Time",
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "Part Time",
      },
      labels: {
        format: "{value}%",
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },

  series: [
    {
      name: "Part Time",
      type: "line",
      yAxis: 1,
      data: [27.6, 38.8, 51.7, 64.1, 79.0, 88.4],
      color: "#b422b6",
    },
    {
      name: "Full Time",
      type: "line",
      data: [13.6, 24.9, 35.8, 40.7, 53.1, 63.0],
      dashStyle: "ShortDashDot",
      color: "#bf05ff",
    },
  ],
});
//ceo Hc Female to Male Ratio
Highcharts.chart("ceoHcFemaleToMaleRatio", {
  chart: {
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
  yAxis: {
    title: {
      text: "",
    },
  },
  credits: { enabled: false },

  xAxis: {
    tickLength: 0,
    tickWidth: 0,
    showInLegend: false,
    categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
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
      name: "Female",
      data: [43934, 48656, 65165, 81827, 112143, 142383],
      color: "#bf05ff",
      dashStyle: "ShortDashDot",
    },
    {
      type: "spline",
      name: "Male",
      data: [142383, 112143, 81857, 65165, 48656, 43934],
      color: "#A594F9",
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
//ceo Hc Recruitment Breakdown by Ethnicity
Highcharts.chart("ceoHcRecruitmentBreakdownbyEthnicity", {
  chart: {
    type: "areaspline",
    animation: true,
    height: 277,
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
  xAxis: {
    categories: ["Application", "Interview", "Offer", "Hire"],
  },
  yAxis: {
    visible: false,
  },
  credits: {
    enabled: false,
  },
  series: [
    {
      name: "White",
      data: [2000, 900, 500, 400],
      marker: false,
      color: "#d4c9d8",
    },
    {
      name: "Asian",
      data: [1700, 700, 300, 250],
      marker: false,
      color: "#bf05ff",
    },
    {
      name: "Black",
      data: [1300, 400, 100, 50],
      marker: false,
      color: "#ca7ce5",
    },
    {
      name: "Hispanic & Latino",
      data: [-900, -400, -100, -50],
      marker: false,
      color: "#f79c92",
    },
    {
      name: "Other",
      data: [-1400, -700, -300, -250],
      marker: false,
      color: "#56156c",
    },
  ],
});
//ceo Hc Avg Time To Fill By Development
Highcharts.chart("ceoHcAvgTimeToFillByDevelopment", {
  chart: {
    animated: true,
    height: 247.5,
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
    categories: [
      "Customer Service",
      "Engineering & Data Science",
      "HR, Finance & Accounting",
      "Marketing",
      "Product Management & Design",
      "Sales",
    ],
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
      type: "column",
      name: "Time To Fill",
      data: [59, 83, 65, 228, 59, 83],
      color: "#d4c9d8",
    },
    {
      name: "Target",
      color: "transparent",
      lineColor: "transparent",
      data: [47, 83.33, 70.66, 239.33, 47, 83.33],
      type: "spline",
      dataLabels: {
        enabled: false,
      },
      marker: {
        lineWidth: 2,
        fillColor: "#f79c92",
      },
    },
  ],
});
//ceo Hc Talent Rating 6 Month
Highcharts.chart("ceoHcTalentRating6Month", {
  chart: {
    polar: true,
    type: "line",
    height: 267,
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
    categories: [
      "Communication",
      "Delivery",
      "Effectiveness",
      "Knowledge",
      "Skill Set",
    ],
    tickmarkPlacement: "on",
    lineWidth: 0,
  },

  yAxis: {
    gridLineInterpolation: "pentagon",
    lineWidth: 0,
    min: 0,
  },

  legend: {
    enabled: false,
  },

  series: [
    {
      name: "",
      data: [43000, 19000, 60000, 35000, 17000],
      pointPlacement: "on",
      color: "#f79c92",
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
//ceo Hc Headcount Development
Highcharts.chart("ceoHcHeadcountDevelopment", {
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
      categories: ["2018", "2019", "2020", "2021", "2022", "2023"],
      crosshair: true,
    },
  ],
  yAxis: [
    {
      // Primary yAxis
      title: {
        text: "Total",
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "Lost/New",
      },
      opposite: true,
    },
  ],
  tooltip: {
    shared: true,
  },

  series: [
    {
      name: "Lost",
      type: "spline",
      yAxis: 1,
      data: [27.6, 38.8, 51.7, 64.1, 79.0, 88.4],
      color: "#b422b6",
      zIndex: 2,
      marker: false,
    },
    {
      name: "New",
      type: "spline",
      yAxis: 1,
      data: [88.4, 79.0, 64.1, 51.7, 38.8, 23.0],
      color: "#ca7ce5",
      zIndex: 2,
      marker: false,
    },
    {
      name: "Total",
      type: "area",
      data: [93.6, 94.9, 95.8, 90.7, 93.1, 93.0],
      color: "#d4c9d8",
    },
  ],
});
//ceo Hc Salary Cost Development By Department
Highcharts.chart("ceoHcSalaryCostDevelopmentByDepartment", {
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
        text: "Salary Cost",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "Salaries to Gross Revenue",
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
      name: "Admin & HR",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#56156c",
    },
    {
      name: "Customer Support",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#f79c92",
    },
    {
      name: "Finance & Accounting",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#ca7ce5",
    },
    {
      name: "IT",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#bf05ff",
    },
    {
      name: "Product",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#ab8ab6",
    },
    {
      name: "Sales & Marketing",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#d1c3d6",
    },
    {
      name: "Salaries to Revenue",
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
//ceo Hc Salary Cost Development By Country
Highcharts.chart("ceoHcSalaryCostDevelopmentByCountry", {
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
        text: "Salary Cost",
      },
      labels: {
        format: "{value}",
        style: { fontSize: "9px" },
      },
    },
    {
      // Secondary yAxis
      title: {
        text: "Salaries to Gross Revenue",
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
      name: "France",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#56156c",
    },
    {
      name: "Germany",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#f79c92",
    },
    {
      name: "India",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#ca7ce5",
    },
    {
      name: "Slovakia",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#bf05ff",
    },
    {
      name: "UK",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#ab8ab6",
    },
    {
      name: "USA",
      type: "column",
      data: [20, 20, 20, 20, 20, 20, 20, 20],
      color: "#d1c3d6",
    },
    {
      name: "Salaries to Revenue",
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
//ceo Hc Time To Quit Job
Highcharts.chart("ceoHcTimeToQuitJob", {
  chart: {
    type: "area",
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
  xAxis: {
    categories: ["1", "2", "3", "4", "5", "6", "7", "8"],
    title: {
      text: "Years At Company",
    },
  },
  yAxis: {
    title: {
      text: "Notices",
    },
  },
  legend: false,
  plotOptions: {
    area: {
      dashStyle: "ShortDash",
      marker: {
        enabled: true,
        symbol: "circle",
        fillColor: "#bf05ff",
      },
    },
  },
  series: [
    {
      name: "USA",
      data: [0, 50, 45, 41, 18, 10, 5, 2],
      color: "#deaaf0",
    },
  ],
});

//ceo Hc Employee Satisfaction and Manager Feedback Score
Highcharts.chart('ceoHcEmployeeSatisfactionAndManagerFeedbackScore', {

  chart: {
    type: 'bubble',
    animation: true,
    height: 177,
  },
  navigation: {
    buttonOptions: {
      enabled: false,
    },
  },
  credits: { enabled: false },
  title: {
    text: ''
  },
  legend: {enabled: false},

  subtitle: {
    text: ''
  },


  xAxis: {
    gridLineColor: 'transparent',
    title: {
      text: 'Manager Feedback Score'
    },
    categories: ['50','51','52','53','54','55']
  },

  yAxis: {
    title: {
      text: ''
    },
     gridLineColor: 'transparent',
  },

  tooltip: {
    useHTML: true,
    headerFormat: '<table>',
    pointFormat:'<tr><th>Sales Volume:</th><td>{point.y}</td></tr>',
    
    followPointer: true
  },

  plotOptions: {
    series: {
      dataLabels: {
        enabled: true,
        format: '{point.name}'
      }
    }
  },

  series: [{
    data: [95,85,45,65,78,45],
    name: 'Sales Volume',
    color: '#bf05ff'
  }]

});

