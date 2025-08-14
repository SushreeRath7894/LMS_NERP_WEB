let type= '';
	$(document).ready(function() {
	
	const urlParams = new URLSearchParams(window.location.search);
		type = urlParams.get('id');
		
		if(type == null || type == 'null') {
			type = '';
		}
	
		$("#quickFilter").on("keydown", function(event) {
			if (event.key === "Enter" || event.which === 13) {
				event.preventDefault();
				onQuickFilterChanged();
			}
		});
		getAllSkuUom();
	
		var gridDiv = document.querySelector('#myGrid');
		new agGrid.Grid(gridDiv, gridOptions);
		gridOptions.api.setRowData([]);
	
	
		getAllProject();
		CKEDITOR.replace('editor');
	});
	
	let skuData = [];
	function getAllSkuUom() {
		$.ajax({
			type: "GET",
			url: "view-all-product-sku",
			success: function(response) {
				console.log("Psaidhksjk", response)
				skuData = response;
			},
			error: function(e) {
			}
		});
	
	}
	
	function generateDropdownOptions() {
		let options = '<option value="" disabled selected> </option>';
		skuData.forEach(item => {
			options += `<option value="${item.skuId}" data-code="${item.uomId}" data-name="${item.uomANme}">${item.skuName}</option>`;
		});
		return options;
	}
	
	
	
	function getAllProject() {
	
		agGrid.simpleHttpRequest({
			url: "view-all-project-estimation?type="+type
		}).then(function(response) {
			if (response.code === "Success") {
	
				const responseBody = JSON.parse(response.body);
				const projectDetails = responseBody.projectDetails;
	
				var newRowData = projectDetails.reverse();
				gridOptions.api.setRowData(newRowData);
	
				if (newRowData && newRowData.length > 0) {
					gridOptions.api.forEachNode(function(node) {
						if (node.rowIndex === 0) {
							node.setSelected(true);
						}
					});
				}
	
			} else {
				console.error("Failed to fetch data");
			}
		});
	
	}
	
	let projectId = "";
	let projectTypeId = "";
	function rowSelect() {
		var selectedRows = gridOptions.api.getSelectedRows();
	
		if (selectedRows.length > 0) {
			var datas = selectedRows[0];
			projectId = datas.projectname;
			projectTypeId = datas.projectType;
			$("#projectId").text(projectId);
			$("#projectIdStdCost").text(projectId);
			$("#projectIdEst").text(projectId);
			$("#projectIdProp").text(projectId);
			getResponse(projectTypeId);
			$("#save").prop("disabled", false);
			$("#saveStdCost").prop("disabled", false);
			$("#saveEstimation").prop("disabled", false);
	
		} else {
			$("#projectId").text('');
			$("#projectIdStdCost").text('');
			$("#projectIdEst").text('');
			$("#projectIdProp").text('');
			$("#save").prop("disabled", true);
			$("#saveStdCost").prop("disabled", true);
			$("#saveEstimation").prop("disabled", true);
			getResponse('');
			projectId = '';
		}
	
	
	}
	
	function getResponse(projectTypeId) {
	
		const activeTab = document.querySelector('.nav-link.active');
		const activeHref = activeTab.getAttribute('href');
		const cleanHrefId = activeHref.startsWith('#') ? activeHref.substring(1) : activeHref;
		const type = cleanHrefId.split("_");
	
		if (type == 'categories') {
			getAllCategory(projectTypeId);
		} else if (type == 'stdCost') {
			getAllCategoryEstimation();
		} else if (type == 'estimate') {
			getAllCategoryEstimation1();
		}
	}
	
	/*Get all cat and subCat by type id*/

	/*stdCostDetails = [];
function getAllCategory(id) {

    const activeTab = document.querySelector('.nav-link.active');
    const activeHref = activeTab.getAttribute('href');
    const cleanHrefId = activeHref.startsWith('#') ? activeHref.substring(1) : activeHref;
    const type = cleanHrefId.split("_");

    $("#tbodyData12").empty();

    agGrid.simpleHttpRequest({
        url: "view-all-category-details?id=" + id + "&type=" + type
    }).then(function (data) {

        let jsonData = JSON.parse(data.body[0]);
        if (jsonData[0].stdCostDetails && jsonData[0].stdCostDetails.trim() !== "") {
            stdCostDetails = JSON.parse(jsonData[0].stdCostDetails);

            if (Array.isArray(stdCostDetails) && stdCostDetails.length > 0) {
                getSetCategory(stdCostDetails);
                return;
            }
        }

        if (jsonData == null || jsonData.length === 0) {
            $('.loader').hide();
            $("#tbodyData12").empty();
        } else {
            $('.loader').hide();
            let tasks = {};

            jsonData.forEach(item => {
                if (!tasks[item.taskId]) {
                    tasks[item.taskId] = {
                        taskId: item.taskId,
                        taskName: item.taskName,
                        activities: {}
                    };
                }

                if (!tasks[item.taskId].activities[item.activityId]) {
                    tasks[item.taskId].activities[item.activityId] = {
                        activityId: item.activityId,
                        activityName: item.activityName,
                        variants: []
                    };
                }

                tasks[item.taskId].activities[item.activityId].variants.push(item);
            });

            $("#tbodyData12").empty();
            let taskCounter = 1;
            Object.values(tasks).forEach(task => {
                let taskRow = '<tr data-node-id="' + task.taskId + '" class="abc" id="' + task.taskId + '">'
                    + '<td colspan="8"><strong>' + taskCounter + '. ' + task.taskName + '</strong></td></tr>';
                $("#tbodyData12").append(taskRow);

                let activityCounter = 1;
                Object.values(task.activities).forEach(activity => {
                    let activityRow = '<tr data-node-id="' + activity.activityId + '" data-node-pid="' + task.taskId + '" class="abc" id="' + activity.activityId + '">'
                        + '<td colspan="8">' + taskCounter + '.' + activityCounter + ' ' + activity.activityName + '</td></tr>';
                    $("#tbodyData12").append(activityRow);

                    activity.variants.forEach((variant, index) => {
                        let variantNumber = taskCounter + '.' + activityCounter + '.' + (index + 1);
                        let variantRow = '<tr data-node-id="' + variant.variantId + '" data-node-pid="' + activity.activityId + '" class="abc" id="' + variant.variantId + '">'
                            + '<td class="firstnode">' + variantNumber + ' - ' + variant.variantName + '</td>'
                            + '</tr>';
                        $("#tbodyData12").append(variantRow);
                    });

                    activityCounter++;
                });

                taskCounter++;
            });

            $('#basic').simpleTreeTable({
                edatapander: $('#edatapander'),
                collapser: $('#collapser'),
                store: 'session',
                storeKey: 'simple-tree-table-basic',
            });
        }
    });
}*/


stdCostDetails = [];

function getAllCategory(id) {
    const activeTab = document.querySelector('.nav-link.active');
    const activeHref = activeTab.getAttribute('href');
    const cleanHrefId = activeHref.startsWith('#') ? activeHref.substring(1) : activeHref;
    const type = cleanHrefId.split("_");

    $("#tbodyData12").empty();

    agGrid.simpleHttpRequest({
        url: "view-all-category-details?id=" + id + "&type=" + type
    }).then(function (data) {

        let jsonData = JSON.parse(data.body[0]);

        if (jsonData[0]?.stdCostDetails && jsonData[0].stdCostDetails.trim() !== "") {
            stdCostDetails = JSON.parse(jsonData[0].stdCostDetails);

            if (Array.isArray(stdCostDetails) && stdCostDetails.length > 0) {
                getSetCategory(stdCostDetails);
                return;
            }
        }

        if (!jsonData || jsonData.length === 0) {
            $('.loader').hide();
            $("#tbodyData12").empty();
            return;
        }

        $('.loader').hide();

        let activities = {};

        jsonData.forEach(item => {
            if (!activities[item.activityId]) {
                activities[item.activityId] = {
                    activityId: item.activityId,
                    activityName: item.activityName,
                    tasks: {}
                };
            }

            if (!activities[item.activityId].tasks[item.taskId]) {
                activities[item.activityId].tasks[item.taskId] = {
                    taskId: item.taskId,
                    taskName: item.taskName,
                    variants: []
                };
            }

            activities[item.activityId].tasks[item.taskId].variants.push(item);
        });

        $("#tbodyData12").empty();

        let activityCounter = 1;
        Object.values(activities).forEach(activity => {
            let activityRow = `<tr data-node-id="${activity.activityId}" class="abc" id="${activity.activityId}">
                                    <td colspan="8"><strong>${activityCounter}. ${activity.activityName}</strong></td>
                               </tr>`;
            $("#tbodyData12").append(activityRow);

            let taskCounter = 1;
            Object.values(activity.tasks).forEach(task => {
                let taskRow = `<tr data-node-id="${task.taskId}" data-node-pid="${activity.activityId}" class="abc" id="${task.taskId}">
                                    <td colspan="8">${activityCounter}.${taskCounter} ${task.taskName}</td>
                               </tr>`;
                $("#tbodyData12").append(taskRow);

                task.variants.forEach((variant, index) => {
                    let variantNumber = `${activityCounter}.${taskCounter}.${index + 1}`;
                    let variantRow = `<tr data-node-id="${variant.variantId}" data-node-pid="${task.taskId}" class="abc" id="${variant.variantId}">
                                        <td class="firstnode">${variantNumber} - ${variant.variantName}</td>
                                      </tr>`;
                    $("#tbodyData12").append(variantRow);
                });

                taskCounter++;
            });

            activityCounter++;
        });
        $('#basic').simpleTreeTable({
            edatapander: $('#edatapander'),
            collapser: $('#collapser'),
            store: 'session',
            storeKey: 'simple-tree-table-basic',
        });
    });
}


	
	function saveCatSubCate() {
    const tasks = {};

    $("#tbodyData12 > tr").each(function() {
        const nodeId = $(this).data("node-id");
        const nodePid = $(this).data("node-pid");
        const nodeName = $(this).find("strong").length
            ? $(this).find("strong").text().replace(/^\d+\.\s*/, "").trim()
            : $(this).find("td").text().replace(/^\d+\.\d+\.\d*\s*-\s*/, "").trim();

        if (!nodePid || nodeId === nodePid) {
            tasks[nodeId] = {
                taskId: nodeId,
                taskName: nodeName,
                activities: []
            };
        } else {
            const parentTask = tasks[nodePid];
            if (parentTask) {
                parentTask.activities.push({
                    activityId: nodeId,
                    activityName: nodeName,
                    variants: []
                });
            } else {
                const parentActivity = Object.values(tasks).flatMap(task => task.activities).find(activity => activity.activityId === nodePid);
                if (parentActivity) {
                    parentActivity.variants.push({
                        variantId: nodeId,
                        variantName: nodeName
                    });
                }
            }
        }
    });

    const allCategoryData = {
        estimationId: $("#estmationId").val(),
        projectTypeId: projectTypeId,
        projectId: projectId,
        taskDetails: Object.values(tasks)
    };

    saveAllCategoryData(allCategoryData);
}

	
	/*Save all cat data*/
	function saveAllCategoryData(allCategoryData) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "add-project-all-categories",
			contentType: "application/json",
			data: JSON.stringify(allCategoryData),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					$(".formValidation").remove();
					$('.loader').hide();
					getResponse(projectTypeId);
				} else {
					$('.loader').show();
				}
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	}
	
	
	function getAllCategoryEstimation() {
    const activeTab = document.querySelector('.nav-link.active');
    const activeHref = activeTab.getAttribute('href');
    const cleanHrefId = activeHref.startsWith('#') ? activeHref.substring(1) : activeHref;
    const type = cleanHrefId.split("_");

    var id = projectId;
    $("#tbodyData13").empty();

    agGrid.simpleHttpRequest({
        url: "view-all-category-details?id=" + id + "&type=" + type
    }).then(function(data) {
        let jsonData = JSON.parse(data.body[0]);
        if (jsonData == null) {
            $('.loader').hide();
            $("#tbodyData13").empty();
        } else {
            $('.loader').hide();
            let parents = {};
            console.log('jsonData', jsonData);

            jsonData[0].projectTypeDetails.forEach(item => {
                if (!parents[item.taskId]) {
                    parents[item.taskId] = item;
                }
            });

            $("#tbodyData13").empty();
            let taskCounter = 1;
            Object.values(parents).forEach(task => {
                let taskRow = `<tr data-node-id="${task.taskId}" class="abc task-row" id="${task.taskId}">
                   <td><strong>${taskCounter}. ${task.taskName}</strong></td>
                    <td class="type"><input type="text" value="${task.type || ''}"></td>
                    <td class="quantity" oninput="calculatePrice(this);"><input type="text" value="${task.quantity || ''}"></td>
                    <td class="uomList"><input type="text" id="uom" value="${task.uom || ''}"></td>
                    <td class="unitCost" oninput="calculatePrice(this);"><input type="text" value="${task.unitCost || ''}"></td>
                    <td class="cost" oninput="calculatePrice(this);"><input type="text" value="${task.cost || ''}"></td>
                    <td class="markup" oninput="calculatePrice(this);"><input type="text" value="${task.markup || ''}"></td>
                    <td class="tax" oninput="calculatePrice(this);"><input type="text" value="${task.tax || ''}"></td>
                    <td class="price"><input type="text" readonly value="${task.price || ''}"></td>
                    <td class="desc"><input type="text" value="${task.description || ''}"></td>
                    <td class="profitAmnt" style="display:none" oninput="calculatePrice(this);"><input type="text" value="${task.profitAmnt || ''}"></td>
                    <td class="profitMargin" style="display:none"><input type="text" value="${task.profitMargin || ''}"></td>
                </tr>`;
                $("#tbodyData13").append(taskRow);

                task.activities.forEach((activity, index) => {
                    let activityNumber = taskCounter + '.' + (index + 1);
                    let activityRow = `<tr data-node-id="${activity.activityId}" data-node-pid="${task.taskId}" class="abc activity-row" id="${activity.activityId}">
                        <td style="padding-left: 20px;"> ${activity.activityName}</td>
                        <td class="type"><input type="text" value="${activity.type || ''}"></td>
                        <td class="quantity" oninput="calculatePrice(this);"><input type="text" value="${activity.quantity || ''}"></td>
                        <td class="uomList"><input type="text" id="uom" value="${activity.uom || ''}"></td>
                        <td class="unitCost" oninput="calculatePrice(this);"><input type="text" value="${activity.unitCost || ''}"></td>
                        <td class="cost" oninput="calculatePrice(this);"><input type="text" value="${activity.cost || ''}"></td>
                        <td class="markup" oninput="calculatePrice(this);"><input type="text" value="${activity.markup || ''}"></td>
                        <td class="tax" oninput="calculatePrice(this);"><input type="text" value="${activity.tax || ''}"></td>
                        <td class="price"><input type="text" readonly value="${activity.price || ''}"></td>
                        <td class="desc"><input type="text" value="${activity.description || ''}"></td>
                        <td class="profitAmnt" style="display:none" oninput="calculatePrice(this);"><input type="text" value="${activity.profitAmnt || ''}"></td>
                        <td class="profitMargin" style="display:none"><input type="text" value="${activity.profitMargin || ''}"></td>
                    </tr>`;
                    $("#tbodyData13").append(activityRow);

                    if (activity.variants && Array.isArray(activity.variants)) {
                        activity.variants.forEach((variant, vIndex) => {
                            let variantNumber = activityNumber + '.' + (vIndex + 1);
                            let variantRow = `<tr data-node-id="${variant.variantId}" data-node-pid="${activity.activityId}" class="abc variant-row" id="${variant.variantId}">
                                <td style="padding-left: 40px;">${variantNumber} - ${variant.variantName}</td>
                                <td class="type"><input type="text" value="${variant.type || ''}"></td>
                                <td class="quantity" oninput="calculatePrice(this);"><input type="text" value="${variant.quantity || ''}"></td>
                                <td class="uomList"><input type="text" id="uom" value="${variant.uom || ''}"></td>
                                <td class="unitCost" oninput="calculatePrice(this);"><input type="text" value="${variant.unitCost || ''}"></td>
                                <td class="cost" oninput="calculatePrice(this);"><input type="text" value="${variant.cost || ''}"></td>
                                <td class="markup" oninput="calculatePrice(this);"><input type="text" value="${variant.markup || ''}"></td>
                                <td class="tax" oninput="calculatePrice(this);"><input type="text" value="${variant.tax || ''}"></td>
                                <td class="price"><input type="text" readonly value="${variant.price || ''}"></td>
                                <td class="desc"><input type="text" value="${variant.description || ''}"></td>
                                <td class="profitAmnt" style="display:none" oninput="calculatePrice(this);"><input type="text" value="${variant.profitAmnt || ''}"></td>
                                <td class="profitMargin" style="display:none"><input type="text" value="${variant.profitMargin || ''}"></td>
                            </tr>`;
                            $("#tbodyData13").append(variantRow);
                        });
                    }
                });

                taskCounter++;
            });

            calculatePrice();

            $('#basic1').simpleTreeTable({
                edatapander: $('#edatapander'),
                collapser: $('#collapser'),
                store: 'session',
                storeKey: 'simple-tree-table-basic',
            });
        }
    })
}

	
	/*column defination for project creation*/
	var columnDefs = [
		{
			headerCheckboxSelection: false,
			headerCheckboxSelectionFilteredOnly: true,
			checkboxSelection: true,
			width: 10,
			sortable: false,
			filter: false,
			resizable: true
		},
		{
			headerName: "Project Id",
			field: "projectId",
	flex:1
		},
		{
			headerName: "Project Name",
			field: "projectname",
			flex:1
		},
		{
			headerName: "Project Incharge Name",
			field: "projectInc",
	flex:1
		}, {
			headerName: "Project Type",
			field: "projectTypeName",
	flex:1
		},
	
		{
			headerName: "Creation Date",
			field: "creationDate",
			flex:1
		}, {
			headerName: "Project Type Id",
			field: "projectType",
			hide: true
	
		}];
	
	// Define grid options
	var gridOptions = {
		columnDefs: columnDefs,
		rowSelection: 'single',
		suppressRowClickSelection: true,
		defaultColDef: {
			sortable: true,
			filter: true,
			resizable: true,
			width: 150
		},
		pagination: true,
		paginationPageSize: 15,
	
		onSelectionChanged: rowSelect
	};
	
	function getUomData(selectElement) {
		// Get the selected option
		const selectedOption = selectElement.options[selectElement.selectedIndex];
	
		const dataCode = selectedOption.getAttribute("data-code");
		const dataName = selectedOption.getAttribute("data-name");
	
		const $parentRow = $(selectElement).closest('tr');
		const $uomList = $parentRow.find('.uomList');
	
		if ($uomList.length) {
			$uomList.find('input#uom').val(dataName);
		} else {
			console.log("No .uomList found in the parent row.");
		}
	
	}
	
	function getUomDataChild(element) {
		const selectedOption = element.options[element.selectedIndex];
	
		const dataCodeC = selectedOption.getAttribute("data-code");
		const dataNameC = selectedOption.getAttribute("data-name");
	
		const $childRow = $(element).closest('tr');
		const $uomListCh = $childRow.find('.uomListChild');
	
		console.log($uomListCh)
	
		if ($uomListCh.length) {
			console.log($uomListCh.find('input#uomChildData'))
			$uomListCh.find('input#uomChildData').val(dataNameC);
			console.log("UoM input value set to:", dataNameC);
		} else {
			console.log("No .uomList found in the parent row.");
		}
	}
	
	
	
	function calculatePrice(element) {
	
		let row = $(element).closest("tr");
	
		let quantity = parseFloat(row.find(".quantity input").val()) || 0;
		let unitCost = parseFloat(row.find(".unitCost input").val()) || 0;
		let markupPercent = parseFloat(row.find(".markup input").val()) || 0;
		let taxPercent = parseFloat(row.find(".tax input").val()) || 0;
	
		let cost = quantity * unitCost;
	
		let markup = (cost * markupPercent) / 100;
		let tax = (cost * taxPercent) / 100;
		let price = cost + markup + tax;
	
		let profitAmnt = markup;
		let profitMargin = (profitAmnt / price) * 100 || 0;
	
		row.find(".cost input").val(cost.toFixed(2));
		row.find(".price input").val(price.toFixed(2));
		row.find(".profitAmnt input").val(profitAmnt.toFixed(2));
		row.find(".profitMargin input").val(profitMargin.toFixed(2));
	
	
	
	}
	
	
	function saveStdCost() {
    const tasks = {};

    $("#tbodyData13 > tr.task-row").each(function() {
        const taskId = $(this).data("node-id");
        const taskName = $(this).find("td:first").text().replace(/\d+\.\s*/, "").trim();
        const type = $(this).find(".type input").val() || "";
        const quantity = $(this).find(".quantity input").val() || "";
        const uom = $(this).find(".uomList input").val() || "";
        const unitCost = $(this).find(".unitCost input").val() || "";
        const cost = $(this).find(".cost input").val() || "";
        const markup = $(this).find(".markup input").val() || "";
        const tax = $(this).find(".tax input").val() || "";
        const price = $(this).find(".price input").val() || "";
        const description = $(this).find(".desc input").val() || "";
        const profitAmnt = $(this).find(".profitAmnt input").val() || "";
        const profitMargin = $(this).find(".profitMargin input").val() || "";

        tasks[taskId] = {
            taskId,
            taskName,
            type,
            quantity,
            uom,
            unitCost,
            cost,
            markup,
            tax,
            price,
            description,
            profitAmnt,
            profitMargin,
            activities: []
        };
    });

    $("#tbodyData13 > tr.activity-row").each(function() {
        const activityId = $(this).data("node-id");
        const parentId = $(this).data("node-pid");
        const activityName = $(this).find("td:first").text().replace(/\d+\.\d+\s*-\s*/, "").trim();
        const type = $(this).find(".type input").val() || "";
        const quantity = $(this).find(".quantity input").val() || "";
        const uom = $(this).find(".uomList input").val() || "";
        const unitCost = $(this).find(".unitCost input").val() || "";
        const cost = $(this).find(".cost input").val() || "";
        const markup = $(this).find(".markup input").val() || "";
        const tax = $(this).find(".tax input").val() || "";
        const price = $(this).find(".price input").val() || "";
        const description = $(this).find(".desc input").val() || "";
        const profitAmnt = $(this).find(".profitAmnt input").val() || "";
        const profitMargin = $(this).find(".profitMargin input").val() || "";

        if (tasks[parentId]) {
            tasks[parentId].activities.push({
                activityId,
                activityName,
                type,
                quantity,
                uom,
                unitCost,
                cost,
                markup,
                tax,
                price,
                description,
                profitAmnt,
                profitMargin,
                variants: []
            });
        }
    });

    $("#tbodyData13 > tr.variant-row").each(function() {
        const variantId = $(this).data("node-id");
        const parentId = $(this).data("node-pid");
        const variantName = $(this).find("td:first").text().replace(/\d+\.\d+\.\d+\s*-\s*/, "").trim();
        const type = $(this).find(".type input").val() || "";
        const quantity = $(this).find(".quantity input").val() || "";
        const uom = $(this).find(".uomList input").val() || "";
        const unitCost = $(this).find(".unitCost input").val() || "";
        const cost = $(this).find(".cost input").val() || "";
        const markup = $(this).find(".markup input").val() || "";
        const tax = $(this).find(".tax input").val() || "";
        const price = $(this).find(".price input").val() || "";
        const description = $(this).find(".desc input").val() || "";
        const profitAmnt = $(this).find(".profitAmnt input").val() || "";
        const profitMargin = $(this).find(".profitMargin input").val() || "";

        Object.values(tasks).forEach(task => {
            const parentActivity = task.activities.find(activity => activity.activityId === parentId);
            if (parentActivity) {
                parentActivity.variants.push({
                    variantId,
                    variantName,
                    type,
                    quantity,
                    uom,
                    unitCost,
                    cost,
                    markup,
                    tax,
                    price,
                    description,
                    profitAmnt,
                    profitMargin
                });
            }
        });
    });

    const allCategoryData = {
        estimationId: $("#estmationId").val(),
        projectTypeId: projectTypeId,
        projectId: projectId,
        taskDetails: Object.values(tasks)
    };

    console.log("Nested JSON Data:", JSON.stringify(allCategoryData, null, 2));
    saveAllCategoryData(allCategoryData);
}

function getAllCategoryEstimation1() {
    const activeTab = document.querySelector('.nav-link.active');
    const activeHref = activeTab.getAttribute('href');
    const cleanHrefId = activeHref.startsWith('#') ? activeHref.substring(1) : activeHref;
    const type = cleanHrefId.split("_");
    const id = projectId;

    $("#tbodyData14").empty();

    agGrid.simpleHttpRequest({
        url: "view-all-category-details?id=" + id + "&type=" + type
    }).then(function (data) {
        try {
            let jsonData = JSON.parse(data.body[0]);

            if (jsonData[0].projectTypeEstDetails && jsonData[0].projectTypeEstDetails !== "") {
                let projectTypeEstDetails = jsonData[0].projectTypeEstDetails;

                if (typeof projectTypeEstDetails === "string") {
                    projectTypeEstDetails = JSON.parse(projectTypeEstDetails);
                }

                if (Array.isArray(projectTypeEstDetails) && projectTypeEstDetails.length > 0) {
                    setStdCostEstimation(projectTypeEstDetails);
                    return;
                }
            }

            $('.loader').hide();
            let parents = {};

            jsonData[0].projectTypeDetails.forEach(item => {
                if (!parents[item.taskId]) {
                    parents[item.taskId] = item;
                }
            });

            $("#tbodyData14").empty();
            let taskCounter = 1;

            Object.values(parents).forEach(task => {
                const taskRow = `
                    <tr data-node-id="${task.taskId}" class="abc task-row" id="${task.taskId}">
                        <td><strong>${taskCounter}. ${task.taskName}</strong></td>
                        <td class="type"><input type="text" value="${task.type || ''}"></td>
                         <td class="allowance"><input type="text" value=""></td> 
                        <td class="unitCost"><input type="text" value="${task.unitCost || ''}"></td>
                        <td class="uomList"><input type="text" id="uom" value="${task.uom || ''}"></td>
                        <td class="quantity" oninput="calculatePrice(this);"><input type="text" value="${task.quantity || ''}"></td>
                        <td class="cost"><input type="text" value="${task.cost || ''}"></td>
                        <td class="markup" oninput="calculatePrice(this);"><input type="text" value="${task.markup || ''}"></td>
                        <td class="profitAmnt"><input type="text" value="${task.profitAmnt || ''}"></td>
                        <td class="tax" oninput="calculatePrice(this);"><input type="text" value="${task.tax || ''}"></td>
                        <td class="profitMargin"><input type="text" value="${task.profitMargin || ''}"></td>
                        <td class="price"><input type="text" readonly value="${task.price || ''}"></td>
                        <td class="desc"><input type="text" value="${task.desc || ''}"></td>
                    </tr>`;
                $("#tbodyData14").append(taskRow);
                

                if (task.activities && Array.isArray(task.activities)) {
                    task.activities.forEach((activity, index) => {
                        const activityNumber = `${taskCounter}.${index + 1}`;
                        const activityRow = `
                            <tr data-node-id="${activity.activityId}" data-node-pid="${task.taskId}" class="abc activity-row" id="${activity.activityId}">
                                <td style="padding-left: 20px;">${activity.activityName}</td>
                                <td class="type"><input type="text" value="${activity.type || ''}"></td>
                                 <td class="allowance"><input type="text" value=""></td> 
                                <td class="unitCost"><input type="text" value="${activity.unitCost || ''}"></td>
                                <td class="uomList"><input type="text" id="uom" value="${activity.uom || ''}"></td>
                                <td class="quantity" oninput="calculatePrice(this);"><input type="text" value="${activity.quantity || ''}"></td>
                                <td class="cost"><input type="text" value="${activity.cost || ''}"></td>
                                <td class="markup" oninput="calculatePrice(this);"><input type="text" value="${activity.markup || ''}"></td>
                                <td class="profitAmnt"><input type="text" value="${activity.profitAmnt || ''}"></td>
                                <td class="tax" oninput="calculatePrice(this);"><input type="text" value="${activity.tax || ''}"></td>
                                <td class="profitMargin"><input type="text" value="${activity.profitMargin || ''}"></td>
                                <td class="price"><input type="text" readonly value="${activity.price || ''}"></td>
                                <td class="desc"><input type="text" value="${activity.desc || ''}"></td>
                            </tr>`;
                        $("#tbodyData14").append(activityRow);

                        if (activity.variants && Array.isArray(activity.variants)) {
                            activity.variants.forEach((variant, vIndex) => {
                                const variantNumber = `${activityNumber}.${vIndex + 1}`;
                                const variantRow = `
                                    <tr data-node-id="${variant.variantId}" data-node-pid="${activity.activityId}" class="abc variant-row" id="${variant.variantId}">
                                        <td style="padding-left: 40px;">${variantNumber} - ${variant.variantName}</td>
                                        <td class="type"><input type="text" value="${variant.type || ''}"></td>
                                         <td class="allowance"><input type="text" value=""></td> 
                                        <td class="unitCost"><input type="text" value="${variant.unitCost || ''}"></td>
                                        <td class="uomList"><input type="text" id="uom" value="${variant.uom || ''}"></td>
                                        <td class="quantity" oninput="calculatePrice(this);"><input type="text" value="${variant.quantity || ''}"></td>
                                        <td class="cost"><input type="text" value="${variant.cost || ''}"></td>
                                        <td class="markup" oninput="calculatePrice(this);"><input type="text" value="${variant.markup || ''}"></td>
                                        <td class="profitAmnt"><input type="text" value="${variant.profitAmnt || ''}"></td>
                                        <td class="tax" oninput="calculatePrice(this);"><input type="text" value="${variant.tax || ''}"></td>
                                        <td class="profitMargin"><input type="text" value="${variant.profitMargin || ''}"></td>
                                        <td class="price"><input type="text" readonly value="${variant.price || ''}"></td>
                                        <td class="desc"><input type="text" value="${variant.desc || ''}"></td>
                                    </tr>`;
                                $("#tbodyData14").append(variantRow);
                            });
                        }
                    });
                }

                taskCounter++;
            });

            calculatePrice();

            $('#basic2').simpleTreeTable({
                edatapander: $('#edatapander'),
                collapser: $('#collapser'),
                store: 'session',
                storeKey: 'simple-tree-table-basic',
            });

        } catch (error) {
            console.error("Error parsing data:", error);
        }
    });
}


function setStdCostEstimation(projectTypeDetails) {
    if (!projectTypeDetails || !projectTypeDetails.length) {
        console.warn('No project type details available');
        return;
    }

    $("#tbodyData14").empty();
    let taskCounter = 1;

    projectTypeDetails.forEach(task => {
        const taskRow = `
            <tr data-node-id="${task.taskId}" class="abc task-row" id="${task.taskId}">
                <td><strong>${taskCounter}. ${task.taskName}</strong></td>
                <td class="type"><input type="text" value="${task.type || ''}"></td>
                <td class="allowance"><input type="text" value="${task.allowance || ''}"></td>
                <td class="unitCost"><input type="text" value="${task.unitCost || ''}"></td>
                <td class="uomList"><input type="text" id="uom" value="${task.uom || ''}"></td>
                <td class="quantity" oninput="calculatePriceEstimate(this);"><input type="text" value="${task.quantity || ''}"></td>
                <td class="cost"><input type="text" value="${task.cost || ''}"></td>
                <td class="markup" oninput="calculatePriceEstimate(this);"><input type="text" value="${task.markup || ''}"></td>
                <td class="profitAmnt"><input type="text" value="${task.profitAmnt || ''}"></td>
                <td class="tax" oninput="calculatePriceEstimate(this);"><input type="text" value="${task.tax || ''}"></td>
                <td class="profitMargin"><input type="text" value="${task.profitMargin || ''}"></td>
                <td class="price"><input type="text" readonly value="${task.price || ''}"></td>
                <td class="desc"><input type="text" value="${task.desc || ''}"></td>
            </tr>`;
        $("#tbodyData14").append(taskRow);

        if (task.activities && Array.isArray(task.activities)) {
            task.activities.forEach((activity, index) => {
                const activityNumber = `${taskCounter}.${index + 1}`;
                const activityRow = `
                    <tr data-node-id="${activity.activityId}" data-node-pid="${task.taskId}" class="abc activity-row" id="${activity.activityId}">
                        <td style="padding-left: 20px;"> ${activity.activityName}</td>
                        <td class="type"><input type="text" value="${activity.type || ''}"></td>
                         <td class="allowance"><input type="text" value="${activity.allowance || ''}"></td>
                        <td class="unitCost"><input type="text" value="${activity.unitCost || ''}"></td>
                        <td class="uomList"><input type="text" id="uom" value="${activity.uom || ''}"></td>
                        <td class="quantity" oninput="calculatePriceEstimate(this);"><input type="text" value="${activity.quantity || ''}"></td>
                        <td class="cost"><input type="text" value="${activity.cost || ''}"></td>
                        <td class="markup" oninput="calculatePriceEstimate(this);"><input type="text" value="${activity.markup || ''}"></td>
                        <td class="profitAmnt"><input type="text" value="${activity.profitAmnt || ''}"></td>
                        <td class="tax" oninput="calculatePriceEstimate(this);"><input type="text" value="${activity.tax || ''}"></td>
                        <td class="profitMargin"><input type="text" value="${activity.profitMargin || ''}"></td>
                        <td class="price"><input type="text" readonly value="${activity.price || ''}"></td>
                        <td class="desc"><input type="text" value="${activity.desc || ''}"></td>
                    </tr>`;
                $("#tbodyData14").append(activityRow);

				if (activity.variants && Array.isArray(activity.variants)) {
					activity.variants.forEach((variant, vIndex) => {
						const variantNumber = `${activityNumber}.${vIndex + 1}`;

						const variantName = String(variant.variantName)
							.replace(/^(\d+(\.\d+)*)\s*,\s*/, '$1 ');

						const variantRow = `
									    <tr data-node-id="${variant.variantId}" data-node-pid="${activity.activityId}" class="abc variant-row" id="${variant.variantId}">
									        <td style="padding-left: 40px;"> ${variantName}</td>
									        <td class="type"><input type="text" value="${variant.type || ''}"></td>
									        <td class="allowance"><input type="text" value="${variant.allowance || ''}"></td>
									        <td class="unitCost"><input type="text" value="${variant.unitCost || ''}"></td>
									        <td class="uomList"><input type="text" id="uom" value="${variant.uom || ''}"></td>
									        <td class="quantity" oninput="calculatePriceEstimate(this);"><input type="text" value="${variant.quantity || ''}"></td>
									        <td class="cost"><input type="text" value="${variant.cost || ''}"></td>
									        <td class="markup" oninput="calculatePriceEstimate(this);"><input type="text" value="${variant.markup || ''}"></td>
									        <td class="profitAmnt"><input type="text" value="${variant.profitAmnt || ''}"></td>
									        <td class="tax" oninput="calculatePriceEstimate(this);"><input type="text" value="${variant.tax || ''}"></td>
									        <td class="profitMargin"><input type="text" value="${variant.profitMargin || ''}"></td>
									        <td class="price"><input type="text" readonly value="${variant.price || ''}"></td>
									        <td class="desc"><input type="text" value="${variant.desc || ''}"></td>
									    </tr>`;
						$("#tbodyData14").append(variantRow);
					});
                }
            });
        }
        taskCounter++;
    });

    calculatePrice();

    $('#basic2').simpleTreeTable({
        edatapander: $('#edatapander'),
        collapser: $('#collapser'),
        store: 'session',
        storeKey: 'simple-tree-table-basic',
    });
}

	
	function calculatePriceEstimate(element) {
		let row = $(element).closest("tr");
	
		let quantity = parseFloat(row.find(".quantity input").val()) || 0;
		let unitCost = parseFloat(row.find(".unitCost input").val()) || 0;
		let markupPercent = parseFloat(row.find(".markup input").val()) || 0;
		let taxPercent = parseFloat(row.find(".tax input").val()) || 0;
	
		let cost = quantity * unitCost;
	
		let markup = (cost * markupPercent) / 100;
		let tax = (cost * taxPercent) / 100;
		let price = cost + markup + tax;
	
		let profitAmnt = markup;
		let profitMargin = (profitAmnt / price) * 100 || 0;
	
		row.find(".cost input").val(cost.toFixed(2));
		row.find(".price input").val(price.toFixed(2));
		row.find(".profitAmnt input").val(profitAmnt.toFixed(2));
		row.find(".profitMargin input").val(profitMargin.toFixed(2));
	}
	
	function saveEstimation() {
    const tasks = [];
    const taskRows = $("#tbodyData14 > tr.task-row");
    taskRows.each(function () {
        const taskId = $(this).data("node-id");
        const taskName = $(this).find("td:first").text().split('. ')[1].trim();
        const type = $(this).find(".type input").val() || "";
         const allowance = $(this).find(".allowance input").val() || "";
        const uom = $(this).find(".uomList input").val() || "";
        const quantity = $(this).find(".quantity input").val() || "";
        const unitCost = $(this).find(".unitCost input").val() || "";
        const cost = $(this).find(".cost input").val() || "";
        const markup = $(this).find(".markup input").val() || "";
        const profitAmnt = $(this).find(".profitAmnt input").val() || "";
        const tax = $(this).find(".tax input").val() || "";
        const profitMargin = $(this).find(".profitMargin input").val() || "";
        const price = $(this).find(".price input").val() || "";
        const desc = $(this).find(".desc input").val() || "";

        const task = {
            taskId,
            taskName,
            type,
            allowance,
            uom,
            quantity,
            unitCost,
            cost,
            markup,
            profitAmnt,
            tax,
            profitMargin,
            price,
            desc,
            activities: [],
        };
        const activityRows = $(`#tbodyData14 > tr.activity-row[data-node-pid='${taskId}']`);
        activityRows.each(function () {
            const activityId = $(this).data("node-id");
            const activityName = $(this).find("td:first").text().split('- ');
            const type = $(this).find(".type input").val() || "";
             const allowance = $(this).find(".allowance input").val() || "";
            const uom = $(this).find(".uomList input").val() || "";
            const quantity = $(this).find(".quantity input").val() || "";
            const unitCost = $(this).find(".unitCost input").val() || "";
            const cost = $(this).find(".cost input").val() || "";
            const markup = $(this).find(".markup input").val() || "";
            const profitAmnt = $(this).find(".profitAmnt input").val() || "";
            const tax = $(this).find(".tax input").val() || "";
            const profitMargin = $(this).find(".profitMargin input").val() || "";
            const price = $(this).find(".price input").val() || "";
            const desc = $(this).find(".desc input").val() || "";

            const activity = {
                activityId,
                activityName,
                type,
                allowance,
                uom,
                quantity,
                unitCost,
                cost,
                markup,
                profitAmnt,
                tax,
                profitMargin,
                price,
                desc,
                variants: [],
            };

            const variantRows = $(`#tbodyData14 > tr.variant-row[data-node-pid='${activityId}']`);
            variantRows.each(function () {
                const variantId = $(this).data("node-id");
                const variantName = $(this).find("td:first").text().split('- ');
                const type = $(this).find(".type input").val() || "";
                 const allowance = $(this).find(".allowance input").val() || "";
                const uom = $(this).find(".uomList input").val() || "";
                const quantity = $(this).find(".quantity input").val() || "";
                const unitCost = $(this).find(".unitCost input").val() || "";
                const cost = $(this).find(".cost input").val() || "";
                const markup = $(this).find(".markup input").val() || "";
                const profitAmnt = $(this).find(".profitAmnt input").val() || "";
                const tax = $(this).find(".tax input").val() || "";
                const profitMargin = $(this).find(".profitMargin input").val() || "";
                const price = $(this).find(".price input").val() || "";
                const desc = $(this).find(".desc input").val() || "";

                const variant = {
                    variantId,
                    variantName,
                    type,
                    allowance,
                    uom,
                    quantity,
                    unitCost,
                    cost,
                    markup,
                    profitAmnt,
                    tax,
                    profitMargin,
                    price,
                    desc,
                };

                activity.variants.push(variant);
            });

            task.activities.push(activity);
        });

        tasks.push(task);
    });

    const allEstimationDetails = {
        estimationId: $("#estmationId").val(),
        projectTypeId: projectTypeId,
        projectId: projectId,
        estimationDetails: tasks,
    };

    console.log("Hierarchical JSON Data:", JSON.stringify(allEstimationDetails, null, 2));
    saveAllEstimationDetails(allEstimationDetails);
}

	
	
	
	function saveAllEstimationDetails(allEstimationDetails) {
		$('.loader').show();
		$.ajax({
			type: "POST",
			url: "add-project-estimation-details",
			contentType: "application/json",
			data: JSON.stringify(allEstimationDetails),
			success: function(response) {
				if (response.code == "Success") {
					toastr.success(response.message);
					$(".formValidation").remove();
					$('.loader').hide();
					getResponse(projectTypeId);
				} else {
					$('.loader').show();
				}
			},
			error: function(datas) {
				console.log(datas);
			}
		});
	
	}
	
	
	function getSetCategory(stdCostDetails) {

    $("#tbodyData12").empty();

    let taskCounter = 1;
    stdCostDetails.forEach(task => {
        let taskRow = `<tr data-node-id="${task.taskId}" class="parent-row" id="${task.taskId}">
                           <td colspan="2"><strong>${taskCounter}. ${task.taskName}</strong></td>
                       </tr>`;
        $("#tbodyData12").append(taskRow);

        let activityCounter = 1;
        task.activities.forEach(activity => {
            let activityRow = `<tr data-node-id="${activity.activityId}" data-node-pid="${task.taskId}" class="child-row" id="${activity.activityId}">
                                   <td colspan="2"> ${activity.activityName}</td>
                               </tr>`;
            $("#tbodyData12").append(activityRow);

            activity.variants.forEach((variant, index) => {
                let variantNumber = `${taskCounter}.${activityCounter}.${index + 1}`;
                let variantRow = `<tr data-node-id="${variant.variantId}" data-node-pid="${activity.activityId}" class="variant-row" id="${variant.variantId}">
                                      <td style="padding-left: 40px;">${variantNumber} - ${variant.variantName}</td>
                                  </tr>`;
                $("#tbodyData12").append(variantRow);
            });

            activityCounter++;
        });

        taskCounter++;
    });

    $('#basic').simpleTreeTable({
        edatapander: $('#edatapander'),
        collapser: $('#collapser'),
        store: 'session',
        storeKey: 'simple-tree-table-basic',
    });
}
	
	
	
	function saveCateData() {
		const savedData = Object.values(stdCostDetails);
		if (savedData.length > 0) {
			savedCateData();
		} else {
			saveCatSubCate();
		}
	
	
	}
	
	
	function savedCateData() {
		const savedData = Object.values(stdCostDetails);
		console.log("Saved Data:", savedData);
		const allCategoryData = {
			estimationId: $("#estmationId").val(),
			projectTypeId: projectTypeId,
			projectId: projectId,
			taskDetails: savedData
		};
	
		console.log("Data to send:", JSON.stringify(allCategoryData, null, 2));
	
		saveAllCategoryData(allCategoryData);
	}
	
	function onQuickFilterChanged() {
		gridOptions.api
			.setQuickFilter(document.getElementById('quickFilter').value);
		var displayedRowCount = gridOptions.api.getDisplayedRowCount();
	
		var len = displayedRowCount;
		$('#totalReq').find('span').html(len);
	}
	
	function resetBtn() {
		$("#quickFilter").val('');
		gridOptions.api.setQuickFilter('');
		gridOptions.api.refreshCells({ force: true });
	}