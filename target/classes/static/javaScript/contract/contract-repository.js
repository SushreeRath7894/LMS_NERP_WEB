// Sample folder data structure
		/*const folderData = [
			{
			  "name": "Accounts",
			  "type": "folder",
			  "children": []
			},
			{
			  "name": "Hrms",
			  "type": "folder",
			  "children": []
			},
			{ 
			  "name": "Sales",
			  "type": "folder",
			  "children": []
			},
			{
			  "name": "Workspace",
			  "type": "folder",
			  "children": [
				{
				  "name": "Rasmita",
				  "type": "folder",
				  "children": [
					{
					  "url": "http://localhost:1011/document/dms/Workspace/Rasmita/1724393755709.png",
					  "name": "Screenshot (71).png",
					  "type": "file"
					},
					{
					  "url": "http://localhost:1011/document/dms/Workspace/Rasmita/1724393811478.png",
					  "name": "Screenshot (105).png",
					  "type": "file"
					}
				  ]
				},{
					"name": "Pankaj",
					"type": "folder",
					"children": [{
						"name": "Saurav",
						"type": "folder",
						"children": []
				  }]
				}
			  ]
			}
		  ];
		
		let currentPath = [];
		let currentFolderData = folderData;
		
		// Function to render the folders and files
		function renderFolderView(folderData) {
		  console.log("Panic", folderData)
			const folderView = $('#folderView');
			folderView.empty(); // Clear the current view
				const iconHtml = getNewIcon();
				folderView.append(iconHtml);
			folderData.forEach(item => {
				// Create the folder or file icon
				const icon = $('<div></div>').addClass('icon');
				const img = $('<img>').attr('src', item.type === 'folder' ? 'folder.png' : 'file-icon.png');
				const name = $('<div></div>').text(item.name);
		
				icon.append(img).append(name);
		
				// If the item is a folder, set click event to open it
				if (item.type === 'folder') {
					icon.click(() => openFolder(item));
				} else if (item.type === 'file') { // If it's a file, open the URL on click
					icon.click(() => window.open(item.url, '_blank'));
				}
		
				folderView.append(icon);
			});
			$("#new").click(addFolder);
		}
		
		//Function for get new icon
		function getNewIcon(){
			const icon = $('<div id="new"></div>').addClass('icon');
			const img = $('<img>').attr('src', 'newfoldericon.png');
			const name = $('<div></div>').text("New");
			icon.append(img).append(name);
		
			return icon;
		}
		
		// Function to open a folder and update the current view
		function openFolder(folder) {
		  alert(folder.name)
			currentPath.push(folder.name);
			currentFolderData = folder.children;
			renderFolderView(currentFolderData);
			updateBreadcrumb();
			$('#backButton').show(); // Show the back button when inside a folder
		}
		
		// Function to update the breadcrumb navigation
		function updateBreadcrumb() {
			$('#breadcrumb').text(currentPath.join(' > '));
		}
		
		// Function to go back to the previous folder
		function goBack() {
			if (currentPath.length > 1) {
				currentPath.pop(); // Remove the last folder from the path
				currentFolderData = findFolderByPath(folderData, currentPath);
				renderFolderView(currentFolderData);
				updateBreadcrumb();
			} else {
				currentPath.pop();
				currentFolderData = folderData;
				renderFolderView(currentFolderData);
				updateBreadcrumb();
				$('#backButton').hide(); // Hide the back button when at the root
			}
		}
		
		// Function to find folder data by a given path
		function findFolderByPath(data, path) {
			let result = data;
			path.forEach(name => {
				result = result.find(item => item.name === name).children;
			});
			return result;
		}
		
		//Function to add folder
		function addFolder(){
			console.log(currentPath);
		
			const folderName = prompt("Enter the name of the new folder:");
		
			// Check if a name was provided and it's not empty
			if (folderName) {
				// Create the new folder object
				const newFolder = {
					name: folderName,
					type: 'folder',
					children: []
				};
		
				// Add the new folder to the current folder data
				currentFolderData.push(newFolder);
		
				// Re-render the folder view to include the new folder
				renderFolderView(currentFolderData);
			} else {
				alert("Folder name cannot be empty.");
			}
		}
		
		// Initialize the folder view when the document is ready
		$(document).ready(function () {
			renderFolderView(folderData);
			$('#backButton').click(goBack);
		   // $("#new").click(addFolder);
		});*/


		// Sample JSON data
		const jsonData = [
			{
				"name": "A",
				"type": "folder",
				"parentId": "",
				"workspaceId": "WRKSPC/2024-25/000001"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915807.png",
				"parentId": "WRKSPC/2024-25/000001"
			},
			{
				"name": "B",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000001",
				"workspaceId": "WRKSPC/2024-25/000002"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915808.png",
				"parentId": "WRKSPC/2024-25/000002"
			},
			{
				"name": "C",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000002",
				"workspaceId": "WRKSPC/2024-25/000003"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915809.png",
				"parentId": "WRKSPC/2024-25/000003"
			},
			{
				"name": "D",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000003",
				"workspaceId": "WRKSPC/2024-25/000004"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915810.png",
				"parentId": "WRKSPC/2024-25/000004"
			},
			{
				"name": "E",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000004",
				"workspaceId": "WRKSPC/2024-25/000005"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915811.png",
				"parentId": "WRKSPC/2024-25/000005"
			},
			{
				"name": "F",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000005",
				"workspaceId": "WRKSPC/2024-25/000006"
			},
			{
				"name": "G",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000006",
				"workspaceId": "WRKSPC/2024-25/000007"
			},
			{
				"name": "HRMS",
				"type": "folder",
				"parentId": "",
				"workspaceId": "WRKSPC/2024-25/000008"
			},
			{
				"name": "HRMS1",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000008",
				"workspaceId": "WRKSPC/2024-25/000009"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915812.png",
				"parentId": "WRKSPC/2024-25/000009"
			},
			{
				"name": "BB",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000002",
				"workspaceId": "WRKSPC/2024-25/000010"
			},
			{
				"name": "aa",
				"type": "folder",
				"parentId": "",
				"workspaceId": "WRKSPC/2024-25/000011"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915813.png",
				"parentId": "WRKSPC/2024-25/000011"
			},
			{
				"name": "bb",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000011",
				"workspaceId": "WRKSPC/2024-25/000012"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915814.png",
				"parentId": "WRKSPC/2024-25/000012"
			},
			{
				"name": "cc",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000012",
				"workspaceId": "WRKSPC/2024-25/000013"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915815.png",
				"parentId": "WRKSPC/2024-25/000013"
			},
			{
				"name": "dd",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000013",
				"workspaceId": "WRKSPC/2024-25/000014"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915816.png",
				"parentId": "WRKSPC/2024-25/000014"
			},
			{
				"name": "ee",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000014",
				"workspaceId": "WRKSPC/2024-25/000015"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915817.png",
				"parentId": "WRKSPC/2024-25/000015"
			},
			{
				"name": "ff",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000015",
				"workspaceId": "WRKSPC/2024-25/000016"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915818.png",
				"parentId": "WRKSPC/2024-25/000016"
			},
			{
				"name": "gg",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000016",
				"workspaceId": "WRKSPC/2024-25/000017"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915819.png",
				"parentId": "WRKSPC/2024-25/000017"
			},
			{
				"name": "hh",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000017",
				"workspaceId": "WRKSPC/2024-25/000018"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915820.png",
				"parentId": "WRKSPC/2024-25/000018"
			},
			{
				"name": "ii",
				"type": "folder",
				"parentId": "WRKSPC/2024-25/000018",
				"workspaceId": "WRKSPC/2024-25/000019"
			},
			{
				"type": "file",
				"docUrl": "https://demo-nirmalya.nerp.in/document/dms//1724736915821.png",
				"parentId": "WRKSPC/2024-25/000019"
			}
		];

		let currentPath = [];
		let currentFolderData = jsonData.filter(item => item.parentId === "");

		// Function to render the folders and files
		function renderFolderView(folderData) {
			const folderView = $('#folderView');
			folderView.empty(); // Clear the current view
			const iconHtml = getNewIcon();
			folderView.append(iconHtml);

			folderData.forEach(item => {
				// Create the folder or file icon
				const icon = $('<div></div>').addClass('icon');
				const img = $('<img>').attr('src', item.type === 'folder' ? '../assets/images/folder.png' : '../assets/images/file-icon.png');
				const name = $('<div></div>').text(item.name || item.docUrl.split('/').pop());

				icon.append(img).append(name);

				// If the item is a folder, set click event to open it
				if (item.type === 'folder') {
					icon.click(() => openFolder(item.workspaceId));
				} else if (item.type === 'file') { // If it's a file, open the URL on click
					icon.click(() => window.open(item.docUrl, '_blank'));
				}

				folderView.append(icon);
			});
			$("#new").click(addFolder);
		}

		//Function for get new icon
		function getNewIcon() {
			const icon = $('<div id="new"></div>').addClass('icon');
			const img = $('<img>').attr('src', '../assets/images/newfoldericon.png');
			const name = $('<div></div>').text("New");
			icon.append(img).append(name);

			return icon;
		}

		// Function to open a folder and update the current view
		function openFolder(workspaceId) {
			currentPath.push(workspaceId);
			currentFolderData = jsonData.filter(item => item.parentId === workspaceId);
			renderFolderView(currentFolderData);
			updateBreadcrumb();
			$('#backButton').show(); // Show the back button when inside a folder
		}

		// Function to update the breadcrumb navigation
		function updateBreadcrumb() {
			const pathNames = currentPath.map(id => jsonData.find(item => item.workspaceId === id).name);
			$('#breadcrumb').text(pathNames.join(' > '));
		}

		// Function to go back to the previous folder
		function goBack() {
			if (currentPath.length > 1) {
				currentPath.pop(); // Remove the last folder from the path
				const previousWorkspaceId = currentPath[currentPath.length - 1];
				currentFolderData = jsonData.filter(item => item.parentId === previousWorkspaceId);
				renderFolderView(currentFolderData);
				updateBreadcrumb();
			} else {
				currentPath.pop();
				currentFolderData = jsonData.filter(item => item.parentId === "");
				renderFolderView(currentFolderData);
				updateBreadcrumb();
				$('#backButton').hide(); // Hide the back button when at the root
			}
		}

		// Function to add a new folder
		function addFolder() {
			const folderName = $("#FolderName").val();

			if (folderName) {
				const newFolder = {
					name: folderName,
					type: 'folder',
					parentId: currentPath.length ? currentPath[currentPath.length - 1] : '', // Set the parentId based on the current folder
					workspaceId: `WRKSPC/${new Date().getTime()}` // Generate a unique workspaceId
				};

				jsonData.push(newFolder);

				currentFolderData = jsonData.filter(item => item.parentId === (currentPath.length ? currentPath[currentPath.length - 1] : ''));
				renderFolderView(currentFolderData);
				$("#FolderName").val('');
			} else {
				$("#templateNameError").text("Folder name cannot be empty.");
			}
		}
		
		function clearValidationMessage(){
			$("#templateNameError").text(" ");
		}
		

		// Initialize the folder view when the document is ready
		$(document).ready(function () {
			renderFolderView(currentFolderData);
			$('#backButton').click(goBack);
		});