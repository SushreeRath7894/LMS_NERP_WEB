$(document).ready(function() {
    // Placeholder for employee and role data
    let employees = [];
    let allRoles = [];
    let dynamicAccessControlData = {};
    let filteredEmployees = [];
    let selectedEmployee = null;
    let selectedRole = null;
    let selectedModule = null;

    // Pagination variables
    const employeesPerPage = 8;
    const maxPagesToShow = 5;
    let currentPage = 1;

    // Search terms for each tab
    let roleSearchTerm = '';
    let moduleSearchTerm = '';
    let activitySearchTerm = '';

    // Fetch roles from API
    function fetchRoles() {
        $.ajax({
            url: 'get-roles-listing',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                allRoles = data.map(role => ({
                    roleId: role.roleId,
                    roleName: role.roleName
                }));
                populateRoles();
                fetchModulesForSelectedRoles();
            },
            error: function(err) {
                console.error('Error fetching roles:', err);
            }
        });
    }

    // Fetch employee data from API
    function fetchEmployees() {
        $.ajax({
            url: 'get-employees',
            method: 'GET',
            dataType: 'json',
            success: function(data) {
                employees = data.map(employee => ({
                    id: employee.empId,
                    name: employee.name,
                    email: employee.email || 'No email',
                    department: employee.organization || 'Unknown',
                    roles: employee.role ? employee.role.split(',').map(role => role.trim()) : []
                }));
                employees.forEach(emp => {
                    if (!emp.roles.includes('Employee')) {
                        emp.roles.push('Employee');
                    }
                });
                filteredEmployees = [...employees];
                populateEmployeeList();
            },
            error: function(err) {
                console.error('Error fetching employees:', err);
            }
        });
    }

    // Fetch modules and activities for all selected roles
	function fetchModulesForSelectedRoles() {
	    if (!selectedEmployee || !selectedEmployee.roles.length) {
	        clearModules();
	        clearActivities();
	        return;
	    }

	    const selectedRoleIds = selectedEmployee.roles
	        .map(roleName => {
	            const role = allRoles.find(r => r.roleName === roleName);
	            return role ? `${String(role.roleId).padStart(3, '0')}` : null;
	        })
	        .filter(id => id)
	        .join(',');

	    if (!selectedRoleIds) {
	        clearModules();
	        clearActivities();
	        return;
	    }
		
		const empId=selectedEmployee.id;

	    $.ajax({
	        type: 'POST',
	        url: 'get-modules-role-wise?empId='+ empId,
	        dataType: 'json',
	        contentType: 'application/json',
	        data: JSON.stringify(selectedRoleIds),
	        success: function(resp) {
	            if (resp.code === 'success') {
	                const modules = JSON.parse(resp.body[0]);
	                dynamicAccessControlData[selectedRole] = { modules: {} };

	                modules.forEach(item => {
	                    const moduleName = item.module_Name;
	                    const moduleId = item.module_Id;
	                    if (!dynamicAccessControlData[selectedRole].modules[moduleName]) {
	                        dynamicAccessControlData[selectedRole].modules[moduleName] = {
	                            moduleId: moduleId,
	                            activities: [],
	                            module_assigned_status: item.module_assigned_status || 0
	                        };
	                    }
	                    if (!dynamicAccessControlData[selectedRole].modules[moduleName].activities.some(act => act.act_id === item.activity_Id)) {
	                        // Use activity_assigned_status if available, otherwise default to module_assigned_status
	                        const isAccessed = moduleName === 'Self-Services' ? 1 : 
	                                          (item.activity_assigned_status !== undefined ? item.activity_assigned_status : 
	                                           (item.module_assigned_status || 0));

	                        dynamicAccessControlData[selectedRole].modules[moduleName].activities.push({
	                            act_id: item.activity_Id,
	                            act_name: item.activity_Name,
	                            is_accessed: isAccessed,
	                            activity_url: item.activity_Url,        
	                            module_image: item.module_image,       
	                            function_id: item.function_Id,
	                            function_Name: item.function_Name,
	                            activity_assigned_status: item.activity_assigned_status || 0
	                        });
	                    }
	                });

	                populateModules();
	                populateActivities();
	            } else {
	                console.error('Failed to fetch modules and activities:', resp.message);
	                clearModules();
	                clearActivities();
	            }
	        },
	        error: function(err) {
	            console.error('Error fetching modules and activities:', err);
	            clearModules();
	            clearActivities();
	        }
	    });
	}
   /* function fetchModulesForSelectedRoles() {
        if (!selectedEmployee || !selectedEmployee.roles.length) {
            clearModules();
            clearActivities();
            return;
        }

        const selectedRoleIds = selectedEmployee.roles
            .map(roleName => {
                const role = allRoles.find(r => r.roleName === roleName);
                return role ? `${String(role.roleId).padStart(3, '0')}` : null;
            })
            .filter(id => id)
            .join(',');

        if (!selectedRoleIds) {
            clearModules();
            clearActivities();
            return;
        }

        $.ajax({
            type: 'POST',
            url: 'get-modules-role-wise',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(selectedRoleIds),
            success: function(resp) {
                if (resp.code === 'success') {
                    const modules = JSON.parse(resp.body[0]);
                    dynamicAccessControlData[selectedRole] = { modules: {} };

                    modules.forEach(item => {
                        const moduleName = item.module_Name;
                        const moduleId = item.module_Id;
                        if (!dynamicAccessControlData[selectedRole].modules[moduleName]) {
                            dynamicAccessControlData[selectedRole].modules[moduleName] = {
                                moduleId: moduleId,
                                activities: []
                            };
                        }
						if (!dynamicAccessControlData[selectedRole].modules[moduleName].activities.some(act => act.act_id === item.activity_Id)) {
						    const isAccessed = moduleName === 'Self-Services' ? 1 : (item.is_accessed ? 1 : 0); // Always enable Self-Services activities

						    dynamicAccessControlData[selectedRole].modules[moduleName].activities.push({
						        act_id: item.activity_Id,
						        act_name: item.activity_Name,
						        is_accessed: isAccessed,
						        activity_url: item.activity_Url,        
						        module_image: item.module_image,       
						        function_id: item.function_Id,
								function_Name: item.function_Name         
						    });
						}
                    });

                    populateModules();
                    populateActivities();
                } else {
                    console.error('Failed to fetch modules and activities:', resp.message);
                    clearModules();
                    clearActivities();
                }
            },
            error: function(err) {
                console.error('Error fetching modules and activities:', err);
                clearModules();
                clearActivities();
            }
        });
    }*/

    // Function to get employee photo
    function getEmployeePhoto(employee) {
        return 'https://png.pngtree.com/png-vector/20220709/ourmid/pngtree-businessman-user-avatar-wearing-suit-with-red-tie-png-image_5809521.png';
    }

    // Function to populate employee list with pagination
    function populateEmployeeList(page = 1) {
        const $employeeList = $('#employeeList');
        const $paginationContainer = $('#paginationContainer');
        $employeeList.empty();
        $paginationContainer.empty();

        currentPage = page;
        const startIndex = (page - 1) * employeesPerPage;
        const endIndex = Math.min(startIndex + employeesPerPage, filteredEmployees.length);
        const paginatedEmployees = filteredEmployees.slice(startIndex, endIndex);

        $.each(paginatedEmployees, function(index, employee) {
            const roleBadges = employee.roles.length > 0
			? employee.roles.map(role => `
			    <div>
			        <span class="badge badge-role ms-1 ${role === 'Employee' ? 'd-none' : ''}">${role}</span>
			    </div>
			`).join('')

                : '<span class="badge bg-light text-dark ms-1">No roles</span>';
            const $employeeCard = $(`
                <div class="col-md-6 mb-3">
                    <div class="card employee-card ${selectedEmployee && selectedEmployee.id === employee.id ? 'selected' : ''}" data-id="${employee.id}">
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <img src="${getEmployeePhoto(employee)}" alt="${employee.name}" class="employee-img">
                                <div>
                                    <h6 class="mb-1">${employee.name}</h6>
                                    <p class="text-muted small mb-0">${employee.email}</p>
                                    ${roleBadges}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            `);
            $employeeCard.find('.employee-card').on('click', function() {
                selectEmployee(employee.id);
            });
            $employeeList.append($employeeCard);
        });

        if (paginatedEmployees.length > 0 && !selectedEmployee) {
            selectEmployee(paginatedEmployees[0].id);
        }

        if (filteredEmployees.length > 0) {
            const totalPages = Math.ceil(filteredEmployees.length / employeesPerPage) || 1;
            let startPage = Math.max(1, currentPage - Math.floor(maxPagesToShow / 2));
            let endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

            if (endPage - startPage + 1 < maxPagesToShow) {
                startPage = Math.max(1, endPage - maxPagesToShow + 1);
            }

            const $pagination = $('<nav aria-label="Employee pagination"></nav>');
            const $paginationList = $('<ul class="pagination justify-content-center"></ul>');

            const $prevLi = $(`<li class="page-item ${page === 1 ? 'disabled' : ''}"><a class="page-link" href="#">Previous</a></li>`);
            $prevLi.find('a').on('click', function(e) {
                e.preventDefault();
                changePage(page - 1);
            });
            $paginationList.append($prevLi);

            for (let i = startPage; i <= endPage; i++) {
                const $pageLi = $(`<li class="page-item ${i === page ? 'active' : ''}"><a class="page-link" href="#">${i}</a></li>`);
                $pageLi.find('a').on('click', function(e) {
                    e.preventDefault();
                    changePage(i);
                });
                $paginationList.append($pageLi);
            }

            const $nextLi = $(`<li class="page-item ${page === totalPages ? 'disabled' : ''}"><a class="page-link" href="#">Next</a></li>`);
            $nextLi.find('a').on('click', function(e) {
                e.preventDefault();
                changePage(page + 1);
            });
            $paginationList.append($nextLi);

            $pagination.append($paginationList);
            $paginationContainer.append($pagination);
        }
    }

    // Function to change page
    function changePage(page) {
        const totalPages = Math.ceil(filteredEmployees.length / employeesPerPage) || 1;
        if (page < 1 || page > totalPages) return;
        populateEmployeeList(page);
    }

    // Function to handle employee selection
    function selectEmployee(employeeId) {
        selectedEmployee = employees.find(emp => emp.id === employeeId);
        selectedRole = null;
        selectedModule = null;

        $('.employee-card').removeClass('selected');
        $(`.employee-card[data-id="${employeeId}"]`).addClass('selected');

        if (selectedEmployee) {
            $('#employeeName').text(selectedEmployee.name);
            $('#employeePosition').text(selectedEmployee.email);
            $('#employeeProfileImg').attr({
                src: getEmployeePhoto(selectedEmployee),
                alt: selectedEmployee.name
            });

            $('#noEmployeeSelected').hide();
            $('#selectedEmployeeInfo').show();

            updateAccessSummary();
            populateRoles();
            fetchModulesForSelectedRoles();
            $('#roles-tab').trigger('click');
        }
    }

    // Function to update access summary
    function updateAccessSummary() {
        const $accessSummary = $('#accessSummary');
        $accessSummary.empty();

        if (!selectedEmployee) return;

        const roles = selectedEmployee.roles || [];
        $.each(roles, function(index, role) {
            $accessSummary.append(`<span class="badge badge-role me-2 mb-2">${role}</span>`);
        });

        if (roles.length === 0) {
            $accessSummary.html('<p class="text-muted">No roles assigned</p>');
        }
    }

    // Function to populate roles
    function populateRoles() {
        const $roleList = $('#roleList');
        $roleList.empty();

        let filteredRoles = allRoles.filter(role => 
            role.roleName.toLowerCase().includes(roleSearchTerm.toLowerCase())
        );

		if (filteredRoles.length === 0) {
            $roleList.html('<p class="text-muted">No roles found</p>');
            clearModules();
            clearActivities();
            $('#selectAllRoles').prop('checked', false);
            return;
        }

		$.each(filteredRoles, function(index, role) {
		    const isAssigned = selectedEmployee && selectedEmployee.roles.includes(role.roleName);
		    const isEmployeeRole = role.roleName === 'Employee';
		    const displayClass = isEmployeeRole ? 'd-none' : '';

		    const $roleItem = $(`
		        <div class="item ${displayClass}">
		            <div>
		                <span class="fw-bold">${role.roleId} - ${role.roleName}</span>
		                <span class="badge badge-role ms-2">Role</span>
		            </div>
		            <label class="toggle-switch">
		                <input type="checkbox" ${isAssigned ? 'checked' : ''} ${isEmployeeRole ? 'disabled' : ''} class="role-check" data-role="${role.roleId}">
		                <span class="toggle-slider"></span>
		            </label>
		        </div>
		    `);

		    // After appending checkbox, check its state and set .selected class accordingly
		    const $checkbox = $roleItem.find('.role-check');
		    if ($checkbox.prop('checked')) {
		        $roleItem.addClass('selected');
		    }

		    $checkbox.on('change', function() {
		        if (!isEmployeeRole) {
		            toggleRole(role.roleName, this);
		        }

		        // Toggle 'selected' class based on checked state
		        $(this).closest('.item').toggleClass('selected', this.checked);
		    });

		    $roleList.append($roleItem);
		});


        const allVisibleRolesChecked = filteredRoles
            .filter(role => role.roleName !== 'Employee')
            .every(role => selectedEmployee && selectedEmployee.roles.includes(role.roleName));
        $('#selectAllRoles').prop('checked', allVisibleRolesChecked);

        if (filteredRoles.length > 0 && !selectedRole) {
            const firstVisibleRole = filteredRoles.find(r => r.roleName !== 'Employee') || filteredRoles[0];
            selectRole(firstVisibleRole.roleName, firstVisibleRole.roleId);
        } else if (!filteredRoles.some(r => r.roleName === selectedRole)) {
            clearModules();
            clearActivities();
        }

        updatePermissionCounts();
    }

    // Function to toggle role
    function toggleRole(roleName, checkbox) {
        if (selectedEmployee && roleName !== 'Employee') {
            const $item = $(checkbox).closest('.item');
            if ($(checkbox).is(':checked')) {
                if (!selectedEmployee.roles.includes(roleName)) {
                    selectedEmployee.roles.push(roleName);
                    $item.addClass('selected');
                }
            } else {
                selectedEmployee.roles = selectedEmployee.roles.filter(r => r !== roleName && r !== 'Employee');
                $item.removeClass('selected');
                if (selectedRole === roleName) {
                    selectedRole = null;
                    selectedModule = null;
                    delete dynamicAccessControlData[roleName];
                    clearModules();
                    clearActivities();
                }
            }
            updateAccessSummary();
            populateRoles();
            fetchModulesForSelectedRoles();
            updatePermissionCounts();
        }
    }

    // Function to select a role
    function selectRole(roleName, roleId) {
        selectedRole = roleName;
        selectedModule = null;

        $('#roleList .item').removeClass('selected');
        $(`#roleList .item:has([data-role="${roleId}"])`).addClass('selected');

        populateModules();
        populateActivities();
    }

    // Function to populate modules
	function populateModules() {
	    const $moduleList = $('#moduleList');
	    $moduleList.empty();

	    if (!selectedRole || !dynamicAccessControlData[selectedRole]) {
	        $moduleList.html('<p class="text-muted">No modules available</p>');
	        $('#selectAllModules').prop('checked', false);
	        updatePermissionCounts();
	        return;
	    }

	    const modules = dynamicAccessControlData[selectedRole].modules;
	    let filteredModules = Object.keys(modules).filter(module => 
	        module.toLowerCase().includes(moduleSearchTerm.toLowerCase())
	    );

	    if (filteredModules.length === 0) {
	        $moduleList.html('<p class="text-muted">No modules found</p>');
	        $('#selectAllModules').prop('checked', false);
	        updatePermissionCounts();
	        return;
	    }

	    $.each(filteredModules, function(index, module) {
	        const moduleId = dynamicAccessControlData[selectedRole].modules[module].moduleId;
	        const isSelfServices = module === 'Self-Services';
	        // Use module_assigned_status if available, otherwise check if any activities are enabled
	        const moduleStatus = dynamicAccessControlData[selectedRole].modules[module].module_assigned_status;
	        const isChecked = moduleStatus !== undefined ? 
	                          moduleStatus === 1 : 
	                          dynamicAccessControlData[selectedRole].modules[module].activities.some(activity => activity.is_accessed === 1);
	        
	        const $moduleItem = $(`
	            <div class="item ${selectedModule === module ? 'selected' : ''} ${isChecked ? 'selected' : ''} ${isSelfServices ? 'd-none' : ''}">
	                <div>
	                    <span class="fw-bold">${module}</span>
	                    <span class="badge badge-module ms-2">Module</span>
	                </div>
	                <label class="toggle-switch">
	                    <input type="checkbox" ${isChecked ? 'checked' : ''} ${isSelfServices ? 'disabled' : ''} class="module-check" data-module="${moduleId}">
	                    <span class="toggle-slider"></span>
	                </label>
	            </div>
	        `);
	        $moduleItem.on('click', function() {
	            if (!isSelfServices) {
	                selectModule(module);
	            }
	        });
	        $moduleItem.find('.module-check').on('change', function() {
	            if (!isSelfServices) {
	                // Update module_assigned_status when checkbox changes
	                if (dynamicAccessControlData[selectedRole].modules[module].module_assigned_status !== undefined) {
	                    dynamicAccessControlData[selectedRole].modules[module].module_assigned_status = $(this).is(':checked') ? 1 : 0;
	                }
	                toggleModule(module, this);
	            }
	        });
	        $moduleList.append($moduleItem);
	    });

	    const allVisibleModulesChecked = filteredModules
	        .filter(module => module !== 'Self-Services')
	        .every(module => {
	            const moduleStatus = dynamicAccessControlData[selectedRole].modules[module].module_assigned_status;
	            return moduleStatus !== undefined ? 
	                   moduleStatus === 1 : 
	                   dynamicAccessControlData[selectedRole].modules[module].activities.some(activity => activity.is_accessed === 1);
	        });
	    $('#selectAllModules').prop('checked', allVisibleModulesChecked);

	    updatePermissionCounts();
	}
    /*function populateModules() {
        const $moduleList = $('#moduleList');
        $moduleList.empty();

        if (!selectedRole || !dynamicAccessControlData[selectedRole]) {
            $moduleList.html('<p class="text-muted">No modules available</p>');
            $('#selectAllModules').prop('checked', false);
            updatePermissionCounts();
            return;
        }

        const modules = dynamicAccessControlData[selectedRole].modules;
        let filteredModules = Object.keys(modules).filter(module => 
            module.toLowerCase().includes(moduleSearchTerm.toLowerCase())
        );

        if (filteredModules.length === 0) {
            $moduleList.html('<p class="text-muted">No modules found</p>');
            $('#selectAllModules').prop('checked', false);
            updatePermissionCounts();
            return;
        }

        $.each(filteredModules, function(index, module) {
            const moduleId = dynamicAccessControlData[selectedRole].modules[module].moduleId;
            const isSelfServices = module === 'Self-Services';
            const isChecked = dynamicAccessControlData[selectedRole].modules[module].activities.some(activity => activity.is_accessed === 1);
            const $moduleItem = $(`
                <div class="item ${selectedModule === module ? 'selected' : ''} ${isChecked ? 'selected' : ''} ${isSelfServices ? 'd-none' : ''}">
                    <div>
                        <span class="fw-bold">${module}</span>
                        <span class="badge badge-module ms-2">Module</span>
                    </div>
                    <label class="toggle-switch">
                        <input type="checkbox" ${isChecked ? 'checked' : ''} ${isSelfServices ? 'disabled' : ''} class="module-check" data-module="${moduleId}">
                        <span class="toggle-slider"></span>
                    </label>
                </div>
            `);
            $moduleItem.on('click', function() {
                if (!isSelfServices) {
                    selectModule(module);
                }
            });
            $moduleItem.find('.module-check').on('change', function() {
                if (!isSelfServices) {
                    toggleModule(module, this);
                }
            });
            $moduleList.append($moduleItem);
        });

        const allVisibleModulesChecked = filteredModules
            .filter(module => module !== 'Self-Services')
            .every(module => 
                dynamicAccessControlData[selectedRole].modules[module].activities.some(activity => activity.is_accessed === 1)
            );
        $('#selectAllModules').prop('checked', allVisibleModulesChecked);

        updatePermissionCounts();
    }*/

    // Function to toggle module
    function toggleModule(module, checkbox) {
        if (module !== 'Self-Services') {
            const $item = $(checkbox).closest('.item');
            const moduleId = dynamicAccessControlData[selectedRole].modules[module].moduleId;

            if ($(checkbox).is(':checked')) {
                $item.addClass('selected');
                if (!dynamicAccessControlData[selectedRole].modules[module].activities) {
                    dynamicAccessControlData[selectedRole].modules[module].activities = [];
                }
            } else {
                $item.removeClass('selected');
                if (selectedModule === module) {
                    selectedModule = null;
                }
                dynamicAccessControlData[selectedRole].modules[module].activities = dynamicAccessControlData[selectedRole].modules[module].activities.map(activity => ({
                    ...activity,
                    is_accessed: 0
                }));
            }

            updatePermissionCounts();
            const allVisibleModulesChecked = $('#moduleList .module-check').not('[data-module="' + dynamicAccessControlData[selectedRole].modules['Self-Services']?.moduleId + '"]').length > 0 &&
                $('#moduleList .module-check').not('[data-module="' + dynamicAccessControlData[selectedRole].modules['Self-Services']?.moduleId + '"]').not(':checked').length === 0;
            $('#selectAllModules').prop('checked', allVisibleModulesChecked);
            populateActivities();
        }
    }

    // Function to select a module
    function selectModule(module) {
        selectedModule = module;

        $('#moduleList .item').removeClass('selected');
        $(`#moduleList .item:has([data-module="${dynamicAccessControlData[selectedRole].modules[module].moduleId}"])`).addClass('selected');
    }

    // Function to populate activities
	function populateActivities() {
	    const $activityList = $('#activityList');
	    $activityList.empty();

	    if (!selectedRole || !dynamicAccessControlData[selectedRole]) {
	        $activityList.html('<p class="text-muted">No activities available</p>');
	        $('#selectAllActivities').prop('checked', false);
	        updatePermissionCounts();
	        return;
	    }

	    const activities = [];
	    const modules = dynamicAccessControlData[selectedRole].modules;
	    Object.keys(modules).forEach(module => {
	        modules[module].activities.forEach(activity => {
	            activities.push({
	                act_id: activity.act_id,
	                act_name: `${activity.act_name}`,
	                act_url: activity.activity_url,
	                is_accessed: activity.activity_assigned_status !== undefined ? 
	                            activity.activity_assigned_status : 
	                            activity.is_accessed,
	                module_image: activity.module_image,
	                funct_id: activity.function_id,
	                funct_name: activity.function_Name,
	                module: module,
	                moduleId: modules[module].moduleId,
	                activity_assigned_status: activity.activity_assigned_status
	            });
	        });
	    });

	    let filteredActivities = activities.filter(activity => 
	        activity.act_name.toLowerCase().includes(activitySearchTerm.toLowerCase())
	    );

	    if (filteredActivities.length === 0) {
	        $activityList.html('<p class="text-muted">No activities found</p>');
	        $('#selectAllActivities').prop('checked', false);
	        updatePermissionCounts();
	        return;
	    }

	    $.each(filteredActivities, function(index, activity) {
	        const isSelfServicesActivity = activity.module === 'Self-Services';
	        const isChecked = activity.activity_assigned_status !== undefined ? 
	                          activity.activity_assigned_status === 1 : 
	                          activity.is_accessed === 1;
	        
	        const $activityItem = $(`
	            <div class="item ${isChecked ? 'selected' : ''} ${isSelfServicesActivity ? 'd-none' : ''}">
	                <div>
	                    <span class="fw-bold text-primary">${activity.module}</span>: <strong>${activity.act_name}</strong>
	                    <span class="badge badge-activity ms-2">Activity</span>
	                </div>
	                <label class="toggle-switch">
	                    <input type="checkbox" ${isChecked ? 'checked' : ''} ${isSelfServicesActivity ? 'disabled' : ''} class="activity-check" 
	                    data-activity="${activity.act_id}" data-activity-name="${activity.act_name}" data-activity-url="${activity.act_url}" data-function="${activity.funct_id}" 
	                    data-function-name="${activity.funct_name}" data-module-name="${activity.module}" data-module="${activity.moduleId}" data-module-img="${activity.module_image}">
	                    <span class="toggle-slider"></span>
	                </label>
	            </div>
	        `);
	        $activityItem.find('.activity-check').on('change', function() {
	            if (!isSelfServicesActivity) {
	                // Update activity_assigned_status when checkbox changes
	                const module = dynamicAccessControlData[selectedRole].modules[activity.module];
	                const activityObj = module.activities.find(a => a.act_id === activity.act_id);
	                if (activityObj && activityObj.activity_assigned_status !== undefined) {
	                    activityObj.activity_assigned_status = $(this).is(':checked') ? 1 : 0;
	                }
	                toggleActivity(activity.act_id, activity.module, this);
	            }
	        });
	        $activityList.append($activityItem);
	    });

	    const allVisibleActivitiesChecked = filteredActivities
	        .filter(activity => activity.module !== 'Self-Services')
	        .every(activity => {
	            return activity.activity_assigned_status !== undefined ? 
	                   activity.activity_assigned_status === 1 : 
	                   activity.is_accessed === 1;
	        });
	    $('#selectAllActivities').prop('checked', allVisibleActivitiesChecked);

	    updatePermissionCounts();
	}

    // Function to toggle activity
    function toggleActivity(actId, module, checkbox) {
        if (module !== 'Self-Services') {
            const $item = $(checkbox).closest('.item');
            const activity = dynamicAccessControlData[selectedRole].modules[module].activities.find(a => a.act_id === actId);
            if (activity) {
                activity.is_accessed = $(checkbox).is(':checked') ? 1 : 0;
                if ($(checkbox).is(':checked')) {
                    $item.addClass('selected');
                } else {
                    $item.removeClass('selected');
                }
            }
            updatePermissionCounts();
            const allVisibleActivitiesChecked = $('#activityList .activity-check')
                .not('[data-module="Self-Services"]').length > 0 &&
                $('#activityList .activity-check')
                    .not('[data-module="Self-Services"]').not(':checked').length === 0;
            $('#selectAllActivities').prop('checked', allVisibleActivitiesChecked);
            populateModules();
        }
    }

    // Function to clear modules
    function clearModules() {
        $('#moduleList').html('<p class="text-muted">No modules available</p>');
        $('#selectAllModules').prop('checked', false);
        updatePermissionCounts();
    }

    // Function to clear activities
    function clearActivities() {
        $('#activityList').html('<p class="text-muted">No activities available</p>');
        $('#selectAllActivities').prop('checked', false);
        updatePermissionCounts();
    }

    // Function to count enabled permissions and update counts
    function updatePermissionCounts() {
        const enabledRoles = $('.role-check:checked').not('[data-role="' + 
            allRoles.find(r => r.roleName === 'Employee')?.roleId + '"]').length;
        const totalRoles = allRoles.filter(role => role.roleName !== 'Employee').length;
        $('#enabledRolesCount').text(`${enabledRoles}/${totalRoles} enabled`);

        let enabledModules = 0;
        let totalModules = 0;
        if (selectedRole && dynamicAccessControlData[selectedRole]) {
            totalModules = Object.keys(dynamicAccessControlData[selectedRole].modules)
                .filter(module => module !== 'Self-Services').length;
            Object.keys(dynamicAccessControlData[selectedRole].modules)
                .filter(module => module !== 'Self-Services')
                .forEach(module => {
                    if (dynamicAccessControlData[selectedRole].modules[module].activities
                        .some(activity => activity.is_accessed === 1)) {
                        enabledModules++;
                    }
                });
        }
        $('#enabledModulesCount').text(`${enabledModules}/${totalModules} enabled`);

        let enabledActivities = 0;
        let totalActivities = 0;
        if (selectedRole && dynamicAccessControlData[selectedRole]) {
            Object.keys(dynamicAccessControlData[selectedRole].modules)
                .filter(module => module !== 'Self-Services')
                .forEach(module => {
                    const activities = dynamicAccessControlData[selectedRole].modules[module].activities;
                    totalActivities += activities.length;
                    enabledActivities += activities.filter(a => a.is_accessed === 1).length;
                });
        }
        $('#enabledActivitiesCount').text(`${enabledActivities}/${totalActivities} enabled`);
    }

    // Function to filter employees
    function filterEmployees(searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        filteredEmployees = employees.filter(employee =>
            employee.name.toLowerCase().includes(searchTerm) ||
            employee.email.toLowerCase().includes(searchTerm) ||
            employee.department.toLowerCase().includes(searchTerm) ||
            employee.roles.some(role => role.toLowerCase().includes(searchTerm))
        );
        selectedEmployee = null;
        populateEmployeeList(1);
    }

    // Save permissions to backend
    function savePermissions() {
        if (!selectedEmployee) {
            alert('Please select an employee.');
            return;
        }

        // Validate roles
        const checkedRoles = $('.role-check:checked').not('[data-role="' + 
            allRoles.find(r => r.roleName === 'Employee')?.roleId + '"]').length;
        if (checkedRoles === 0) {
            toastr.error("Please select at least one role");
            return;
        }

        // Validate modules
        let checkedModules = 0;
        if (selectedRole && dynamicAccessControlData[selectedRole]) {
            Object.keys(dynamicAccessControlData[selectedRole].modules)
                .filter(module => module !== 'Self-Services')
                .forEach(module => {
                    if (dynamicAccessControlData[selectedRole].modules[module].activities
                        .some(activity => activity.is_accessed === 1)) {
                        checkedModules++;
                    }
                });
        }
        if (checkedModules === 0) {
            toastr.error('Please enable at least one module');
            return;
        }

        // Validate activities
        let checkedActivities = 0;
        if (selectedRole && dynamicAccessControlData[selectedRole]) {
            Object.keys(dynamicAccessControlData[selectedRole].modules)
                .filter(module => module !== 'Self-Services')
                .forEach(module => {
                    checkedActivities += dynamicAccessControlData[selectedRole].modules[module].activities
                        .filter(a => a.is_accessed === 1).length;
                });
        }
        if (checkedActivities === 0) {
            toastr.error('Please enable at least one activity');
            return;
        }

        // Collect checked activity data
        const activityData = [];
        $('#activityList .activity-check:checked').not('[data-module="Self-Services"]').each(function() {
            const $checkbox = $(this);
            activityData.push({
                activity_id: $checkbox.data('activity'),
				activity_name: $checkbox.data('activity-name'),
                activity_url: $checkbox.data('activity-url'),
                function_id: $checkbox.data('function'),
				function_name: $checkbox.data('function-name'),
                module_id: $checkbox.data('module'),
                module_name: $checkbox.data('module-name'),
				module_image: $checkbox.data('module-img'),
				sidebarVisible: 1,
				action: null,
				action_name: null
            });
        });

        // Prepare payload with activity data
        const payload = {
            empId: selectedEmployee.id,
            roles: selectedEmployee.roles.map(roleName => {
                const roleId = allRoles.find(r => r.roleName === roleName)?.roleId;
                const modules = [];
                if (dynamicAccessControlData[roleName]) {
                    Object.keys(dynamicAccessControlData[roleName].modules).forEach(mod_name => {
                        const moduleId = dynamicAccessControlData[roleName].modules[mod_name].moduleId;
                        const activities = dynamicAccessControlData[roleName].modules[mod_name].activities
                            .filter(activity => activity.is_accessed)
                            .map(activity => ({
                                activity_id: activity.act_id,
                                activity_url: activity.activity_url,
                                function_id: activity.function_id,
                                module_id: moduleId,
                                module_image: activity.module_image
                            }));
                        if (activities.length > 0) {
                            modules.push({ moduleId, activities });
                        }
                    });
                }
                return { roleId };
            }),
            activities: activityData
        };
		
		console.log(payload)

        $.ajax({
            type: 'POST',
            url: 'save-employee-role-data',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(payload),
            success: function(resp) {
                if (resp.code === 'success') {
                    toastr.success('Actvities Modified Successfully');
					fetchRoles();
					fetchEmployees();
					fetchModulesForSelectedRoles();
					
                } else {
                    toastr.error('Error Occured');
                }
            },
            error: function(err) {
                alert('Error saving permissions: ' + err.statusText);
            }
        });
    }

    // Select all functions for each section
    function toggleAllRoles(checked) {
        if (selectedEmployee) {
            const filteredRoles = allRoles.filter(role => 
                role.roleName.toLowerCase().includes(roleSearchTerm.toLowerCase())
            );
            $.each(filteredRoles, function(index, role) {
                if (role.roleName !== 'Employee') {
                    const isAssigned = selectedEmployee.roles.includes(role.roleName);
                    if (checked && !isAssigned) {
                        selectedEmployee.roles.push(role.roleName);
                    } else if (!checked && isAssigned) {
                        selectedEmployee.roles = selectedEmployee.roles.filter(r => r !== role.roleName);
                    }
                }
            });
            if (!selectedEmployee.roles.includes('Employee')) {
                selectedEmployee.roles.push('Employee');
            }
            updateAccessSummary();
            populateRoles();
            fetchModulesForSelectedRoles();
        }
    }

    function toggleAllModules(checked) {
        $('#moduleList .module-check').each(function() {
            const $this = $(this);
            const moduleId = $this.data('module');
            const module = Object.keys(dynamicAccessControlData[selectedRole].modules).find(mod => 
                dynamicAccessControlData[selectedRole].modules[mod].moduleId === moduleId
            );
            if (module && module !== 'Self-Services') {
                const $item = $this.closest('.item');
                if (checked) {
                    $item.addClass('selected');
                } else {
                    $item.removeClass('selected');
                    dynamicAccessControlData[selectedRole].modules[module].activities = dynamicAccessControlData[selectedRole].modules[module].activities.map(activity => ({
                        ...activity,
                        is_accessed: 0
                    }));
                }
            }
        });
        if (dynamicAccessControlData[selectedRole].modules['Self-Services']) {
            dynamicAccessControlData[selectedRole].modules['Self-Services'].activities = dynamicAccessControlData[selectedRole].modules['Self-Services'].activities.map(activity => ({
                ...activity,
                is_accessed: 1
            }));
        }
        updatePermissionCounts();
        const allVisibleModulesChecked = $('#moduleList .module-check').not('[data-module="' + dynamicAccessControlData[selectedRole].modules['Self-Services']?.moduleId + '"]').length > 0 &&
            $('#moduleList .module-check').not('[data-module="' + dynamicAccessControlData[selectedRole].modules['Self-Services']?.moduleId + '"]').not(':checked').length === 0;
        $('#selectAllModules').prop('checked', allVisibleModulesChecked);
        populateActivities();
    }

    function toggleAllActivities(checked) {
        $('#activityList .activity-check').each(function() {
            const $checkbox = $(this);
            const $item = $checkbox.closest('.item');
            const actId = $checkbox.data('activity');
            const module = $checkbox.data('module');
            if (module !== 'Self-Services') {
                const activity = dynamicAccessControlData[selectedRole].modules[module].activities.find(a => a.act_id === actId);
                if (activity) {
                    activity.is_accessed = checked ? 1 : 0;
                    $checkbox.prop('checked', checked);
                    if (checked) {
                        $item.addClass('selected');
                    } else {
                        $item.removeClass('selected');
                    }
                }
            }
        });
        if (dynamicAccessControlData[selectedRole].modules['Self-Services']) {
            dynamicAccessControlData[selectedRole].modules['Self-Services'].activities = dynamicAccessControlData[selectedRole].modules['Self-Services'].activities.map(activity => ({
                ...activity,
                is_accessed: 1
            }));
        }
        updatePermissionCounts();
        const allVisibleActivitiesChecked = $('#activityList .activity-check')
            .not('[data-module="Self-Services"]').length > 0 &&
            $('#activityList .activity-check')
                .not('[data-module="Self-Services"]').not(':checked').length === 0;
        $('#selectAllActivities').prop('checked', allVisibleActivitiesChecked);
        populateModules();
    }

    // Initialize tooltips
    $('[data-bs-toggle="tooltip"]').tooltip();

    // Fetch roles and employees
    fetchRoles();
    fetchEmployees();

    // Event handlers
    $('#employeeSearch').on('input', function() {
        filterEmployees($(this).val());
    });

    $('#roleSearch').on('input', function() {
        roleSearchTerm = $(this).val();
        populateRoles();
    });

    $('#moduleSearch').on('input', function() {
        moduleSearchTerm = $(this).val();
        populateModules();
    });

    $('#activitySearch').on('input', function() {
        activitySearchTerm = $(this).val();
        populateActivities();
    });

    $('#savePermissionsBtn').on('click', function() {
        const $saveBtn = $(this);
        const originalText = $saveBtn.html();

        savePermissions();

        $saveBtn.html('<i class="fas fa-check me-1"></i> Saved!').removeClass('btn-save').addClass('btn-success');

        setTimeout(() => {
            $saveBtn.html(originalText).addClass('btn-save').removeClass('btn-success');
        }, 2000);
    });

    $('#selectAllRoles').on('change', function() {
        toggleAllRoles($(this).is(':checked'));
    });

    $('#selectAllModules').on('change', function() {
        toggleAllModules($(this).is(':checked'));
    });

    $('#selectAllActivities').on('change', function() {
        toggleAllActivities($(this).is(':checked'));
    });
});