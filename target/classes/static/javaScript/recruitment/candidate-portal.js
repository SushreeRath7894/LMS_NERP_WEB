
document.addEventListener("DOMContentLoaded", function() {
	const signUpButton = document.getElementById("signUp");
	const signInButton = document.getElementById("signIn");
	const container = document.getElementById("container");

	signUpButton.addEventListener("click", () => {
		container.classList.add("right-panel-active");
	});

	signInButton.addEventListener("click", () => {
		container.classList.remove("right-panel-active");
	});

	document.querySelectorAll('.otp-input').forEach((input, index, array) => {
		input.addEventListener('input', (e) => {
			if (e.target.value && index < array.length - 1) {
				array[index + 1].focus();
			}
		});
		input.addEventListener('keydown', (e) => {
			if (e.key === 'Backspace' && !e.target.value && index > 0) {
				array[index - 1].focus();
			}
		});
	});
});

$(document).ready(function() {
	getJobLists("");

	$("#jobSearchInput").on("keyup", function() {
		showLoader();
		let searchQuery = $(this).val().trim();
		getJobLists(searchQuery);
	});

	$("#loginBtn").click(function() {
		$("#authModal").modal('show');
		$("#container").removeClass("right-panel-active");
	});

	$("#registerBtn").click(function() {
		$("#authModal").modal('show');
		$("#container").addClass("right-panel-active");
	});

	$("#signIn").click(function() {
		$("#container").removeClass("right-panel-active");
	});

	$("#signUp").click(function() {
		$("#container").addClass("right-panel-active");
	});
});

function applyJob(jobId) {
	let userId = $("#userId").val();

	// Check if userId is null, empty, or undefined
	if (!userId || userId === "null") {
		$("#authModal").modal('show');
	} else {
		getJobDetails(jobId);

	}
}

function getJobDetails(jobId) {
	showLoader();
	$.ajax({
		type: "GET",
		url: "candidates-job-apply/get-job-details?jobId=" + jobId,
		async: false,
		success: function(response) {
			if (response.code === "success") {
				let jobDataArray = JSON.parse(response.body[0]);
				hideLoader();
				if (jobDataArray.length > 0) {
					let job = jobDataArray[0];
					myFunction();
					$("#jobTitleHead").text(job.title);
					$("#jobLocHead").text(job.location);
					$("#jobTittle").text(job.title);
					$("#jobLocation").text(job.location);
					$("#joinDate").text(job.joinDate);
					$("#jobExperience").text(job.minExp + " - " + job.maxExp + " Year");
					$("#jobSalary").text("₹ " + job.minSalary + " - ₹ " + job.maxSalary + " / yr");
					$("#positionSummary").text(job.summary);
					$("#positionResp").text(job.responsibilities);
					setSkills(job.skillsReq);

					// Populate skills table
					let skillsTableBody = $("#jobSkillsTable tbody");
					skillsTableBody.empty(); // Clear previous data

					if (job.skillsReq && job.skillsReq.length > 0) {
						job.skillsReq.forEach(skill => {
							let ratingDropdown = `
	                                <select class="skill-rating-dropdown">
	                                    <option value="" selected disabled>Ratings</option>
	                                    <option value="1">1 - Beginner</option>
	                                    <option value="2">2 - Basic</option>
	                                    <option value="3">3 - Developing</option>
	                                    <option value="4">4 - Intermediate</option>
	                                    <option value="5">5 - Competent</option>
	                                    <option value="6">6 - Proficient</option>
	                                    <option value="7">7 - Advanced</option>
	                                    <option value="8">8 - Very Advanced</option>
	                                    <option value="9">9 - Expert</option>
	                                    <option value="10">10 - Master</option>
	                                </select>
	                            `;

							let descriptionBox = `
	                                <textarea class="skill-description" placeholder="Describe your experience with this skill" rows="2" style="width:100%;"></textarea>
	                            `;

							skillsTableBody.append(`
	                                <tr>
	                                    <td>${skill.skillName}</td>
	                                    <td>${skill.skillExp}</td>
	                                    <td>${skill.skillRatings}</td>
	                                    <td>${ratingDropdown}</td>
	                                    <td>${descriptionBox}</td> <!-- Added description box -->
	                                </tr>
	                            `);
						});
					} else {
						skillsTableBody.append(`
	                            <tr>
	                                <td colspan="5" style="text-align:center;">No skills specified</td>
	                            </tr>
	                        `);
					}

					$("#pills-applyjobs-tab").tab('show');
				}
			}
		},
		error: function() {
			console.error("Error fetching job details.");
			hideLoader();
		}
	});
}



function setSkills(skills) {
	let skillsContainer = $("#job-skills");
	skillsContainer.empty(); // Clear existing skills

	skills.forEach(skill => {
		let skillPercentage = skill.skillRatings * 10; // Convert rating to percentage
		let skillItem = `
	            <li class="list-group-item d-flex justify-content-between align-items-center">
	                ${skill.skillName}
	                <div class="progress w-50">
	                    <div class="progress-bar text-white text-center" style="width: ${skillPercentage}%;">
	                        ${skill.skillRatings} / 10
	                    </div>
	                </div>
	            </li>`;
		skillsContainer.append(skillItem);
	});
}


function locationDropdown(selectedLocation) {
	showLoader();
	let searchQuery = $("#searchInput").val();
	var dropdown = document.getElementById("locationDropdown");
	var selectedText = dropdown.options[dropdown.selectedIndex].text;
	getJobLists(searchQuery, selectedText);
}



function myFunction() {
	$("#myDIV").toggleClass('d-none');
}

function getJobLists(searchQuery = "", location = "") {
	$.ajax({
		type: "GET",
		url: "get-job-list",
		data: { search: searchQuery, location: location },
		success: function(response) {
			let jobContainer = $("#job-container");
			jobContainer.empty(); // Clear previous content

			if (response.code === "success" && response.body[0] !== null) {
				setJobListData(response.body[0]); // Populate job data
			} else {
				$("#totalJobs").text("0 Job Results");
				jobContainer.html('<div class="text-center">No jobs found</div>');
				hideLoader();
			}
		},
		error: function() {
			console.error("Error fetching job details.");
		}
	});
}

// Function to show loader
function showLoader() {
	document.querySelector('.loader-backdrop').style.visibility = 'visible';
}

// Function to hide loader
function hideLoader() {
	document.querySelector('.loader-backdrop').style.visibility = 'hidden';
}


function setJobListData(data) {
	let jobDataArray = JSON.parse(data);
	let jobContainer = $("#job-container");
	let userId = $("#userId").val(); // Get userId value

	// Clear existing dynamic job listings but keep static jobs
	$("#totalJobs").text(`${jobDataArray.length} Job Results`);
	jobContainer.find(".job-card.dynamic").remove();

	jobDataArray.forEach((job, index) => {
		let maxLength = 200; // Maximum character length for summary
		let isLong = job.summary.length > maxLength;
		let shortSummary = isLong
			? job.summary.substring(0, maxLength) + "..."
			: job.summary;

		// Show bookmark button only if userId is not empty
		let bookmarkButton = userId && userId.trim() !== ""
			? `<div class="btn btn-outline-success mt-1" onclick="bookmarkJob();" title="Bookmark Job">
	                <i class="fa-solid fa-bookmark"></i>
	               </div>`
			: "";

		let jobHtml = `
	            <div class="job-card mb-3 border rounded p-3 mb-2 dynamic">
	                <div class="job-info">
	                    <div class="d-flex">
	                        <div class="logo-sec">
	                            <img src="../assets/images/n-logo.png" class="img-fluid">
	                        </div>
	                        <div class="r-sec">
	                            <h5>${job.title}</h5>
	                            <p class="mb-1 d-flex gap-2 align-content-center align-items-center">
	                                <span class="comp-logo">${job.orgName}</span> 
	                                <span class="crl"></span> 
	                                <span class="badge bg-warning">${job.jobType}</span> 
	                                <span class="crl"></span> 
	                                <span class="badge bg-danger">Urgently hiring</span>
	                                ${job.totalApplied > 0
				? `<span class="badge bg-primary"><i class="fa-solid fa-users"></i> ${job.totalApplied}+ Applied</span>`
				: ""}
	                            </p>
	                        </div>
	                    </div>
	                    <ul class="job-desc">
	                        <li>
	                            <span class="short-summary" id="short-summary-${index}">${shortSummary}</span>
	                            <span class="full-summary d-none" id="full-summary-${index}">${job.summary}</span>
	                            ${isLong
				? `<a href="javascript:void(0);" class="toggle-summary" data-index="${index}" data-state="collapsed">See More</a>`
				: ""}
	                        </li>
	                    </ul>
	                </div>
	                <div class="job-meta">
	                    <p class="location-txt">
	                        <i class="fa-solid fa-location-dot mr-2"></i> ${job.location}
	                    </p>
	                    <p class="text-post">Posted on ${job.postedDate}</p>
	                    <div class="d-flex gap-2 justify-content-between">
	                        <div class="btn btn-outline-primary mt-1" onclick="applyJob('${job.jobId}');">Apply Now</div>
	                        ${bookmarkButton}
	                        <div class="btn btn-outline-success mt-1 share-btn" data-jobid="${job.jobId}" title="Share Job">
	                            <i class="fa-solid fa-share"></i>
	                        </div>
	                    </div>
	                    <!-- Share Options Popup -->
	                    <div class="share-options d-none" id="share-options-${job.jobId}">
	                        <a href="javascript:void(0);" class="share-whatsapp" data-jobid="${job.jobId}">
	                            <i class="fa-brands fa-whatsapp"></i> WhatsApp
	                        </a>
	                        <a href="javascript:void(0);" class="share-facebook" data-jobid="${job.jobId}">
	                            <i class="fa-brands fa-facebook"></i> Facebook
	                        </a>
	                        <a href="javascript:void(0);" class="share-naukri" data-jobid="${job.jobId}">
	                            <i class="fa-solid fa-briefcase"></i> Share on Naukri
	                        </a>
	                        <a href="javascript:void(0);" class="copy-link" data-jobid="${job.jobId}">
	                            <i class="fa-solid fa-link"></i> Copy Link
	                        </a>
	                    </div>
	                </div>
	            </div>
	        `;
		jobContainer.append(jobHtml);
	});
	hideLoader();

	// Event listener for "See More" / "See Less"
	$(".toggle-summary").on("click", function() {
		let index = $(this).data("index");
		let state = $(this).attr("data-state");

		if (state === "collapsed") {
			// Expand summary
			$(`#short-summary-${index}`).addClass("d-none");
			$(`#full-summary-${index}`).removeClass("d-none");
			$(this).text("See Less").attr("data-state", "expanded");
		} else {
			// Collapse summary
			$(`#full-summary-${index}`).addClass("d-none");
			$(`#short-summary-${index}`).removeClass("d-none");
			$(this).text("See More").attr("data-state", "collapsed");
		}
	});
}


$(document).ready(function() {
	// Show share options when clicking "Share Job"
	$(document).on("click", ".share-btn", function(event) {
		event.stopPropagation();
		let jobId = $(this).data("jobid");
		$(".share-options").addClass("d-none"); // Hide other share popups
		$(`#share-options-${jobId}`).toggleClass("d-none"); // Show current
	});

	// Click event for WhatsApp sharing
	$(document).on("click", ".share-whatsapp", function() {
		let jobId = $(this).data("jobid");
		let jobURL = `http://localhost:1013/candidates-job-apply/${jobId}`;
		let whatsappURL = `https://wa.me/?text=Check out this job: ${jobURL}`;
		window.open(whatsappURL, "_blank");
	});

	// Click event for Facebook sharing
	$(document).on("click", ".share-facebook", function() {
		let jobId = $(this).data("jobid");
		let jobURL = `http://localhost:1013/candidates-job-apply/${jobId}`;
		let facebookURL = `https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(jobURL)}`;
		window.open(facebookURL, "_blank");
	});

	// Click event for Naukri sharing (Copy URL)
	$(document).on("click", ".share-naukri, .copy-link", function() {
		let jobId = $(this).data("jobid");
		let jobURL = `http://localhost:1013/candidates-job-apply/${jobId}`;

		// Copy to clipboard
		let tempInput = $("<input>");
		$("body").append(tempInput);
		tempInput.val(jobURL).select();
		document.execCommand("copy");
		tempInput.remove();

		showSnackbar("Job link copied to clipboard!");
	});

	// Hide share options when clicking outside
	$(document).on("click", function() {
		$(".share-options").addClass("d-none");
	});
});



/* document.addEventListener("DOMContentLoaded", function () {
	showLoader();
	document.getElementById("pills-home-tab").addEventListener("click", function () {
		window.location.href = "https://www.nerp.in";
	});
	hideLoader();
}); */

let otpTimerInterval;
$(document).ready(function() {

	$(".otp-input").attr("disabled", false);
	function startOtpTimer() {
		let timer = 180;
		$("#otpTimer").text(timer + "s remaining");
		$("#resendOtpBtn").prop("disabled", true);

		// Clear any existing interval before starting a new one
		clearInterval(otpTimerInterval);

		otpTimerInterval = setInterval(function() {
			timer--;
			$("#otpTimer").text(timer + "s remaining");

			if (timer <= 0) {
				clearInterval(otpTimerInterval);
				$("#otpTimer").text("OTP Expired! Request a new OTP.");
				$("#resendOtpBtn").prop("disabled", false);
			}
		}, 1000);
	}
	// Trigger OTP Timer when OTP modal opens
	$("#signUpCandidate").click(function() {
		var email = $("#email").val();
		var firstName = $("#first-name").val();
		var lastName = $("#last-name").val();
		var phone = $("#phone").val();
		var gender = $("#gender").val();
		var dob = $("#dob").val();
		var location = $("#location").val();
		var jobSource = $("#job-source").val();
		if (!validateField(firstName, "First Name Required")) return false;
		if (!validateField(lastName, "Last Name Required")) return false;
		if (!validateField(email, "Email Required")) return false;
		if (!validateField(phone, "Phone Number Required")) return false;
		if (!validateField(gender, "Gender Required")) return false;
		if (!validateField(dob, "Date of Birth Required")) return false;
		if (!validateField(location, "Location Required")) return false;
		if (!validateField(jobSource, "Job Source Required")) return false;

		showLoader();

		$.ajax({
			type: "POST",
			url: "/sendOtp",
			contentType: "application/json",
			data: JSON.stringify({ email: email }),
			success: function(response) {
				if (response.code === "200") {
					$("#sign-up-container").addClass("d-none");
					$("#otp-container").removeClass("d-none");
					$("#userEmailDisplay").text(email);
					showSnackbar("OTP Sent Successfully!", "success");
					startOtpTimer();

				} else {
					showSnackbar("Failed to send OTP: " + response.message);
				}
				hideLoader();
			},
			error: function(xhr, status, error) {
				alert("Error sending OTP. Please try again!");
				hideLoader();
			}
		});
	});

	// OTP Verification
	$("#verifyOtpBtn").click(function() {
		let email = $("#email").val();

		if (email === "") {
			showSnackbar("Please enter an email!", "error");
			return;
		}

		// Collect OTP from all input fields
		let otp = "";
		$(".otp-input").each(function() {
			otp += $(this).val();
		});

		if (otp.length !== 4) {
			showSnackbar("Please enter a valid 4-digit OTP!", "error");
			return;
		}

		$.ajax({
			type: "GET",
			url: "/verifyOtp",
			data: { otp: otp, email: email },
			success: function(response) {
				if (response.code === "200") {
					showSnackbar("OTP Verified Successfully!", "success");

					// Stop and hide the OTP timer
					clearInterval(otpTimerInterval);
					$("#otpTimer").hide();

					// Disable OTP fields and buttons
					$(".otp-input").attr("disabled", true);
					$("#verifyOtpBtn").prop("disabled", true).text("Verified ✅").addClass("btn-success").removeClass("btn-danger");
					$("#resendOtpBtn").prop("disabled", true); // Prevent enabling resend button
					$("#otpVerified").html('<span class="text-success fw-bold">✅ OTP Verified Successfully!</span>');

					// Show password reset fields
					$("#passwordFields").fadeIn();
				} else {
					$(".otp-input").attr("disabled", false);
					showSnackbar(response.message, "error");
				}
			},
			error: function(xhr, status, error) {
				showSnackbar("Error verifying OTP. Please try again!", "error");
			}
		});
	});




	// Resend OTP Functionality
	$("#resendOtpBtn").click(function() {
		let email = $("#userEmailDisplay").text();
		if (email === "") {
			showSnackbar("Please enter an email!", "error");
			return;
		}
		showLoader();

		$.ajax({
			type: "POST",
			url: "/sendOtp",
			contentType: "application/json",
			data: JSON.stringify({ email: email }),
			success: function(response) {
				if (response.code === "200") {
					showSnackbar("New OTP has been sent", "success");
					$(".otp-input").val("");
					startOtpTimer();
					hideLoader();
				} else {
					showSnackbar("Failed to resend OTP. Please try again.", "error");
				}
			},
			error: function(xhr, status, error) {
				alert("Error resending OTP. Please try again!");
			}
		});
	});

	$("#submitPasswordBtn").click(function() {
		var newPassword = $("#password").val().trim();
		var confirmPassword = $("#confirmPassword").val().trim();
		var email = $("#email").val().trim();
		/*  var identityType = $("#identity-proof").val().trim();
		 var identity = $("#identity-number").val().trim(); */
		var firstName = $("#first-name").val().trim();
		/*  var middleName = $("#middle-name").val().trim(); */
		var lastName = $("#last-name").val().trim();
		var phone = $("#phone").val().trim();
		var gender = $("#gender").val().trim();
		var dob = $("#dob").val().trim();
		var location = $("#location").val().trim();
		var jobSource = $("#job-source").val().trim();

		// Validate passwords
		if (newPassword === "" || confirmPassword === "") {
			showSnackbar("Please enter both password fields!", "error");
			return;
		}

		if (newPassword !== confirmPassword) {
			showSnackbar("Passwords do not match!", "error");
			return;
		}
		showLoader();

		// Create data object
		var requestData = {
			personalEmail: email,
			/* aadharNo: identity, */
			firstName: firstName,
			lastName: lastName,
			mobileNo: phone,
			gender: gender,
			dob: dob,
			password: newPassword
		};

		console.log(requestData);
		// Send data via AJAX
		$.ajax({
			type: "POST",
			url: "/registerCandidate",
			contentType: "application/json",
			data: JSON.stringify(requestData),
			success: function(response) {
				if (response.code === "success") {
					hideLoader();
					showSnackbar("Registered Successfully", "success");
					signInSwitch();
					/* $("#otpModal").modal('hide');
					$("#loginModal").modal('show'); */
				} else {
					showSnackbar(response.message, "error");
				}
			},
			error: function() {
				showSnackbar("Error resetting password. Please try again!", "error");
			}
		});

		return false;
	});
});

function validateField(value, message) {
	if (!value.trim()) {
		showSnackbar(message, "error");
		return false;
	}
	return true;
}

function showSnackbar(message, type = "info") {
	const snackbar = document.getElementById("snackbar");

	snackbar.className = "snackbar";

	snackbar.classList.add(type, "show");

	snackbar.innerHTML = `${message} <span class="snackbar-close" onclick="closeSnackbar()"><i class="fa-solid fa-xmark"></i></span>`;

	setTimeout(() => {
		snackbar.classList.remove("show");
	}, 3000);
}

function closeSnackbar() {
	document.getElementById("snackbar").classList.remove("show");
}

function signInSwitch() {
	$("#container").removeClass("right-panel-active");
}


$(document).ready(function() {
	$(".nav-link").click(function() {
		var selectedTab = $(this).attr("id");
		console.log("Clicked Tab ID: " + selectedTab);

		if (selectedTab === "pills-findjobs-tab") {
			console.log("Find Jobs tab clicked");
			// Perform actions for Find Jobs
		} else if (selectedTab === "pills-applyjobs-tab") {
			console.log("Applied Jobs tab clicked");
			// Perform actions for Applied Jobs  
		} else if (selectedTab === "pills-home-tab") {
			console.log("About tab clicked");
			// Perform actions for About
		} else if (selectedTab === "pills-profile-tab") {
			console.log("Profile tab clicked");
			// Perform actions for Profile
		}
	});
});

function calculateCompletion() {
	const inputs = document.querySelectorAll('.profile-input');
	let filled = 0;
	inputs.forEach(input => {
		if (input.value.trim() !== '') {
			filled++;
		}
	});
	let percentage = Math.round((filled / inputs.length) * 100);
	return percentage;
}

function animateProgressBar(targetPercentage) {
	const progressBar = document.getElementById('profileCompletion');
	const percentageText = document.getElementById('percentageText');
	let currentPercentage = 0;
	const interval = setInterval(() => {
		if (currentPercentage >= targetPercentage) {
			clearInterval(interval);
		} else {
			currentPercentage++;
			progressBar.style.setProperty('--value', currentPercentage);
			progressBar.setAttribute('aria-valuenow', currentPercentage);
			percentageText.textContent = `${currentPercentage}%`;
		}
	}, 20); // Adjust the speed of the animation by changing the interval time
}

// Calculate the initial percentage and animate the progress bar
$(document).ready(function() {
	const percentage = calculateCompletion();
	animateProgressBar(percentage);

	// Add event listeners to all input fields
	document.querySelectorAll('.profile-input').forEach(input => {
		input.addEventListener('input', () => {
			const percentage = calculateCompletion();
			const progressBar = document.getElementById('profileCompletion');
			const percentageText = document.getElementById('percentageText');
			progressBar.style.setProperty('--value', percentage);
			progressBar.setAttribute('aria-valuenow', percentage);
			percentageText.textContent = `${percentage}%`;
		});
	});

	// Allow users to update their profile picture
	document.getElementById('profilePictureInput').addEventListener('change', function(event) {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				document.getElementById('profileImage').src = e.target.result;
			};
			reader.readAsDataURL(file);
		}
	});
});

let experienceCount = 1;

function addExperience() {
	const experienceContainer = document.getElementById('experience-container');
	const experiences = experienceContainer.getElementsByClassName('experience-card');

	// Collapse previous experiences that are not already collapsed
	Array.from(experiences).forEach(exp => {
		if (!exp.classList.contains("collapsed")) {
			collapseExperience(exp);
		}
	});

	experienceCount++;
	const newCard = document.createElement('div');
	newCard.classList.add('experience-card');
	newCard.setAttribute('data-index', experienceCount);

	newCard.innerHTML = `
	            <h5 class="experience-title">Experience ${experienceCount}</h5>
	            <div class="mb-3">
	                <label class="form-label">Job Title</label>
	                <input type="text" class="form-control" placeholder="Enter your job title">
	            </div>
	            <div class="mb-3">
	                <label class="form-label">Company Name</label>
	                <input type="text" class="form-control" placeholder="Enter company name">
	            </div>
	            <div class="mb-3">
	                <label class="form-label">Experience Description</label>
	                <textarea class="form-control" placeholder="Describe your experience" rows="3"></textarea>
	            </div>
	        `;

	experienceContainer.appendChild(newCard);
	updateExperienceNumbers();
}

function collapseExperience(card) {
	const jobTitle = card.querySelector("input").value || "No Job Title";
	const companyName = card.querySelectorAll("input")[1].value || "No Company";
	const description = card.querySelector("textarea").value || "No Description";

	const collapsedHTML = `
	            <div class="collapsed-experience">
	                <div>
	                    <strong>${jobTitle}</strong> at <em>${companyName}</em>
	                    <p class="mb-0 text-muted">${description.length > 50 ? description.substring(0, 50) + "..." : description}</p>
	                </div>
	                <div>
	                    <button class="edit-experience-btn" onclick="editExperience(this)">
	                        <i class="fas fa-edit"></i>
	                    </button>
	                    <button class="remove-experience-btn" onclick="removeExperience(this)">
	                        <i class="fas fa-trash"></i>
	                    </button>
	                </div>
	            </div>
	        `;

	card.innerHTML = collapsedHTML;
	card.classList.add("collapsed");
}

function editExperience(button) {
	const card = button.closest(".experience-card");
	const index = card.getAttribute("data-index");

	card.innerHTML = `
	            <h5 class="experience-title">Experience ${index}</h5>
	            <div class="mb-3">
	                <label class="form-label">Job Title</label>
	                <input type="text" class="form-control" placeholder="Enter your job title">
	            </div>
	            <div class="mb-3">
	                <label class="form-label">Company Name</label>
	                <input type="text" class="form-control" placeholder="Enter company name">
	            </div>
	            <div class="mb-3">
	                <label class="form-label">Experience Description</label>
	                <textarea class="form-control" placeholder="Describe your experience" rows="3"></textarea>
	            </div>
	        `;

	card.classList.remove("collapsed");
	updateExperienceNumbers();
}

function removeExperience(button) {
	const card = button.closest(".experience-card");
	card.remove();
	updateExperienceNumbers();
}

function updateExperienceNumbers() {
	const cards = document.querySelectorAll(".experience-card");
	cards.forEach((card, index) => {
		card.setAttribute("data-index", index + 1);
		const title = card.querySelector(".experience-title");
		if (title) {
			title.textContent = `Experience ${index + 1}`;
		}
	});
}


var skillsArray = []; // Store selected skills

$(document).ready(function () {
    $(".chosen-select").chosen({
        no_results_text: "No skills found!",
        width: "100%"
    });

    // Fetch skills via AJAX
    $.ajax({
        url: 'view-new-requi-mstr-skills', // Replace with actual API endpoint
        type: 'GET',
        dataType: 'json',
        success: function (response) {
            if (response.body) {
                $.each(response.body, function (index, skill) {
                    $("#skillDropdown").append(`<option value="${skill.key}">${skill.name}</option>`);
                });
                $("#skillDropdown").trigger("chosen:updated"); // Update Chosen dropdown
            }
        },
        error: function () {
            showSnackbar("Failed to load skills. Please try again.", "error");
        }
    });

    // Add skill with experience
    $("#addSkill").click(function () {
        var skillId = $("#skillDropdown").val();
        var skillName = $("#skillDropdown option:selected").text();
        var experience = $("#experienceInput").val().trim();
        var expType = $("#experienceType").val();

        if (skillId !== "" && experience !== "" && experience > 0) {
            // Check if skill already exists
            if ($(`#skillsContainer span[data-skill-id="${skillId}"]`).length > 0) {
                showSnackbar("This skill is already added!", "error");
                return;
            }

            // Store skill in array
            skillsArray.push({ id: skillId, name: skillName, experience: experience, type: expType });

            // Add skill badge in UI
            $("#skillsContainer").append(`
                <span class="badge bg-primary m-1 p-2" data-skill-id="${skillId}">
                    ${skillName} - ${experience} ${expType} 
                    <span class="ms-2" onclick="removeSkill(this, '${skillId}')" style="cursor:pointer;">&times;</span>
                </span>
            `);

            // Update resume section
            updateResumeSkills();

            $("#skillDropdown").val("").trigger("chosen:updated");
            $("#experienceInput").val("");
        } else {
            showSnackbar("Please select a skill and enter valid experience!", "error");
        }
    });
});

// Remove skill from array and UI
function removeSkill(element, skillId) {
    $(element).parent().remove();
    skillsArray = skillsArray.filter(skill => skill.id !== skillId);
    updateResumeSkills();
}

function updateResumeSkills() {
    $("#resumeSkillsContainer").html(""); // Clear old data

    if (skillsArray.length > 0) {
        let skillsGrid = `<div class="d-flex flex-wrap gap-skills">`; // Flex container with wrapping
        skillsArray.forEach(skill => {
            skillsGrid += `
                <div class="d-flex align-items-center" style="white-space: nowrap;margin-bottom: -17px;">
                    <span class="bullet-point">&#8226;</span>
                    <span class="ms-2 skill-name">${skill.name}</span>
                </div>
            `;
        });
        skillsGrid += `</div>`; // Close flex container
        $("#resumeSkillsContainer").html(skillsGrid);
    }
}





function countWords() {
	let text = document.getElementById("description").value;
	let words = text.trim().split(/\s+/).filter(word => word.length > 0);
	document.getElementById("wordCount").innerText = `Word Count: ${words.length}/200`;
}

/* Project Js */
$(document).ready(function() {
    let projectCount = 1;

    $('#addProject').click(function() {
        projectCount++;
        let projectHtml = `
            <div class="card mt-3 p-3 position-relative" id="project-${projectCount}">
                <button type="button" class="btn btn-danger btn-sm position-absolute top-0 end-0 m-2 removeProject" data-id="${projectCount}">
                    <i class="fas fa-times"></i>
                </button>
                <div class="mb-2">
                    <label class="form-label">Project Name</label>
                    <input type="text" class="form-control project-name" name="project_name[]" required>
                </div>
                <div class="row mb-2">
                    <div class="col-md-12">
                        <label class="form-label">Project Duration</label>
                        <div class="d-flex">
                            <input type="month" class="form-control me-2 project-start" name="project_start[]" required>
                            <span class="align-self-center">to</span>
                            <input type="month" class="form-control ms-2 project-end" name="project_end[]" required>
                        </div>
                    </div>
                </div>
                <div class="mb-2">
                    <label class="form-label">Project Description</label>
                    <textarea class="form-control project-description" name="project_description[]" required></textarea>
                    <small class="text-muted">Max 100 words</small>
                </div>
            </div>`;
        
        $('#projectContainer').append(projectHtml);
    });

    // Removing a project from the form and the resume
    $(document).on('click', '.removeProject', function() {
        let projectId = $(this).data('id');
        $('#project-' + projectId).remove();
        $('#resumeProject-' + projectId).remove(); // Remove from resume section
    });

    // Sync project data with resume dynamically on input change
    $(document).on('input', '.project-name, .project-start, .project-end, .project-description', function() {
        updateResumeProjects();
    });

    function updateResumeProjects() {
        $('#resumeProjects').empty(); // Clear previous projects

        $('#projectContainer .card').each(function(index) {
            let projectName = $(this).find('.project-name').val();
            let projectStart = $(this).find('.project-start').val();
            let projectEnd = $(this).find('.project-end').val();
            let projectDesc = $(this).find('.project-description').val();

            if (projectName && projectStart && projectEnd && projectDesc) {
                let projectResumeHtml = `
                    <div class="section__list-item" id="resumeProject-${index + 1}">
                        <div class="d-flex justify-content-between">
                            <div class="name">${projectName}</div>
                            <div class="project-date">${formatMonthYear(projectStart)} - ${formatMonthYear(projectEnd)}</div>
                        </div>
                        <div class="text">${projectDesc}</div>
                    </div>`;
                
                $('#resumeProjects').append(projectResumeHtml);
            }
        });
    }

    function formatMonthYear(dateString) {
        if (!dateString) return '';
        let date = new Date(dateString + "-01");
        return date.toLocaleString('default', { month: 'long', year: 'numeric' });
    }
});



/* Resume Data set */
$(document).ready(function() {
	$("#candidate-name").on("input", function() {
		$("#resumeFirstName").text($(this).val());
		$("#view-cand-fname").text($(this).val());
	});

	$("#candidate-surname").on("input", function() {
		$("#resumeLastName").text($(this).val());
		$("#view-cand-lname").text($(this).val());
	});

	$("#profilePictureInput").on("change", function(event) {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				$("#resumeProfilePic").attr("src", e.target.result);
			};
			reader.readAsDataURL(file);
		}
	});

	$("#candidate-email").on("input", function() {
		$("#resumeEmail").text($(this).val());
		$("#view-cand-email").text($(this).val());
	});

	$("#candidate-phone").on("input", function() {
		$("#resumePhone").text($(this).val());
	});

	$("#candidate-designation").on("input", function() {
		$("#resumePosition").text($(this).val());
	});

	$("#profile-summary").on("input", function() {
		$("#resumeSummary").text($(this).val());
	});
	
});

$(document).ready(function() {
    $(".details").sortable({
        axis: "y", 
        cursor: "move", 
        handle: ".section__title", 
        placeholder: "section-placeholder", 
        tolerance: "pointer", 
        update: function(event, ui) {
            console.log("New order:", $(".details").sortable("toArray"));
        }
    });
});

