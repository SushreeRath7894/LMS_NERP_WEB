	$(document).ready(function() {
		setBellCurveData();
	
		var gridDiv = document.querySelector('#appraisalGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		gridOptions.api.setRowData([]);
	
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptionsEmp);
		gridOptionsEmp.api.setRowData([]);
	
		var gridDiv = document.querySelector('#myGridEmp');
		new agGrid.Grid(gridDiv, gridOptionsEmpList);
		gridOptionsEmpList.api.setRowData([]);
	
	
		/* Highcharts.chart('bellCurve', {
		   title: null,
		   credits: {enabled: false},
		   exporting: {enabled: false},
		   xAxis: [
			   {
				   title: { text: 'Data', },
				   alignTicks: false,
			   },
			   {
				   title: { text: 'Bell Curve', },
				   alignTicks: false,
				   opposite: true,
			   },
		   ],
	
		   yAxis: [
			   {
				   title: {text: 'Data'},
			   },
			   {
				   title: {text: 'Bell Curve'},
				   opposite: true,
			   },
		   ],
	
		   series: [
			   {
				   name: 'Bell Curve',
				   type: 'bellcurve',
				   xAxis: 1,
				   yAxis: 1,
				   baseSeries: 1,
				   zIndex: -1,
			   },
			   {
				   name: 'Data',
				   type: 'scatter',
				   data: [
					   3.5, 3, 3.2, 3.1, 3.6, 3.9, 3.4, 3.4, 2.9, 3.1, 3.7, 3.4
				   ],
				   accessibility: { exposeAsGroupOnly: true, },
				   marker: { radius: 1.5, },
			   },
		   ],
	   }); */
	
	});
	
	
	const appraisalColumnDefs = [
		{ headerCheckboxSelection: false, checkboxSelection: false, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },
		{ headerName: "Employee ID", field: "empId" },
		{ headerName: "Name", field: "empName" },
		{ headerName: "Designation", field: "designationName" },
		{ headerName: "Band", field: "bandName" },
		{ headerName: "Email ID", field: "personalMail" }
	];
	
	const gridOptions = {
		columnDefs: appraisalColumnDefs,
		defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
		rowSelection: 'single',
		pagination: true,
		paginationAutoPageSize: true,
		rowData: [],
	};
	
	
	/*	function getAllReviewedEmployeeList() {
			return agGrid.simpleHttpRequest({
				url: "get-all-reviewed-employee"
			}).then(function(response) {
				if (response.code === "Success") {
					const parsedBody = JSON.parse(response.body[0]);
					const employeesDetails = parsedBody.employees;
		
					const ratingsData = employeesDetails.map(emp => ({
						name: emp.empName,
						rating: parseInt(emp.managerRatings)
					}));
					console.log("Ratings Data:", ratingsData);
					gridOptions.api.setRowData(employeesDetails);
					return ratingsData;
				} else {
					console.error("Failed to fetch data");
					return [];
				}
			});
		}*/
	
	
	function getAllReviewedEmployeeList() {
		return agGrid.simpleHttpRequest({
			url: "get-all-reviewed-employee"
		}).then(function(response) {
			if (response.code === "Success") {
				const parsedBody = JSON.parse(response.body[0]);
				const employeesDetails = parsedBody.employees;
	
				const ratingsData = employeesDetails.map(emp => ({
					name: emp.empName,
					rating: parseInt(emp.managerRatings)
				}));
	
				console.log("Ratings Data:", ratingsData);
	
				// Set all employees data to first grid
				gridOptions.api.setRowData(employeesDetails);
	
				// Set categorized employees
				setCategorisedEmployee(employeesDetails);
	
				return ratingsData;
			} else {
				console.error("Failed to fetch data");
				return [];
			}
		});
	}
	
	
	async function setBellCurveData() {
		const ratingsData = await getAllReviewedEmployeeList();
	
		const dynamicRatings = ratingsData.map(emp => emp.rating);
	
		console.log(dynamicRatings)
	
		Highcharts.chart('bellCurve', {
			title: null,
			credits: { enabled: false },
			exporting: { enabled: false },
			xAxis: [
				{
					title: { text: 'Rating' },
					alignTicks: false
				},
				{
					title: { text: 'Bell Curve' },
					alignTicks: true,
					opposite: true,
				}
			],
			yAxis: [
				{
					title: { text: 'Frequency' }
				},
				{
					title: { text: 'Bell Curve' },
					opposite: true
				}
			],
			series: [
				{
					name: 'Bell Curve',
					type: 'bellcurve',
					xAxis: 1,
					yAxis: 1,
					baseSeries: 1,
					zIndex: -1,
					intervals: 4,
					pointsInInterval: 100,
					color: 'rgb(191, 5, 255)',
					fillOpacity: 0.5
				},
				{
					name: 'Employee Ratings',
					type: 'scatter',
					data: dynamicRatings,
					tooltip: {
						pointFormatter: function() {
							const emp = ratingsData[this.index];
							return `<b>${emp.name}</b><br/>Rating: ${emp.rating}`;
						}
					},
					accessibility: { exposeAsGroupOnly: true },
					marker: { radius: 3 }
				}
			]
		});
	}
	
	
	
	
	
	
	
	
	var columnDefs = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true,
			pinned: 'left',
		},
		{
			headerName: 'Report List',
			field: "reportName",
			flex: 4,
		}];
	
	var gridOptionsEmp = {
		columnDefs: columnDefs,
		rowSelection: 'single',
	
		groupSelectsChildren: true,
		suppressRowClickSelection: true,
		suppressAggFuncInHeader: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 251,
			height: 10
		},
		onFirstDataRendered: function(params) {
			setTimeout(function() {
				var firstRow = params.api.getDisplayedRowAtIndex(0);
				if (firstRow) {
					params.api.selectNode(firstRow, true);
				}
			}, 100);
		},
		onSelectionChanged: rowSelect,
	};
	function rowSelect() {
	
	}
	
	const employeeColumnDefs = [
		{ headerCheckboxSelection: false, checkboxSelection: true, width: 30, sortable: false, filter: false, resizable: true, pinned: 'left' },
		{ headerName: "Employee ID", field: "empId" },
		{ headerName: "Name", field: "empName" },
		{ headerName: "Designation", field: "designationName" },
		{ headerName: "Band", field: "bandName" },
		{ headerName: "Email ID", field: "personalMail" }
	];
	
	const gridOptionsEmpList = {
		columnDefs: employeeColumnDefs,
		defaultColDef: { resizable: true, sortable: true, filter: true, flex: 1 },
		rowSelection: 'single',
		pagination: true,
		paginationAutoPageSize: true,
		rowData: [],
	};
	
	
	
	function setCategorisedEmployee(employeesDetails) {
		const categories = {
			"Top Rated": [],
			"Mid Rated": [],
			"Bottom Rated": []
		};
	
		employeesDetails.forEach(emp => {
			const rating = parseInt(emp.managerRatings);
	
			if (rating >= 4) {
				categories["Top Rated"].push(emp);
			} else if (rating >= 2) {
				categories["Mid Rated"].push(emp);
			} else {
				categories["Bottom Rated"].push(emp);
			}
		});
	
		const categoryList = Object.keys(categories).map(cat => ({
			reportName: cat,
			employees: categories[cat]
		}));
	
		gridOptionsEmp.api.setRowData(categoryList);
	
		window.categoryEmployeeMapping = categories;
	}
	function rowSelect() {
		const selectedNodes = gridOptionsEmp.api.getSelectedNodes();
		if (selectedNodes.length > 0) {
			const selectedCategory = selectedNodes[0].data.reportName;
	
			const employeesForCategory = window.categoryEmployeeMapping[selectedCategory] || [];
	
			gridOptionsEmpList.api.setRowData(employeesForCategory);
		}else{
			gridOptionsEmpList.api.setRowData([]);
		}
	}
	
