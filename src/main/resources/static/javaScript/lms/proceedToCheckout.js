$(document).ready(function() {
	renderCheckoutItemsList();
	
const fullName = ($("#userName").val() || "").trim();
const email=$("#userEmail").val();
const nameParts = fullName.split(" ");
const firstName = nameParts.slice(0, -1).join(" ");
const lastName = nameParts.slice(-1).join(" ");

  $("#firstName").val(fullName);
  $("#email").val(email);
});

function renderCheckoutItemsList() {
	const cart = JSON.parse(localStorage.getItem('cart')) || [];

	$('.cart-count').text(cart.length);
	$('.cart_item_count').text(cart.length);

	const container = $('#cart-items-container2');
	container.empty();


	getExchangeRate('USD', 'INR', function (usdToInrRate) {
		let totalInINR = 0;

		cart.forEach((item, index) => {
			const currency = item.currencyType || '₹';
			const cleanPriceStr = item.price.toString().replace(/[^0-9.]/g, '');
			const numericPrice = parseFloat(cleanPriceStr);

			const priceInINR = (currency === '$') ? numericPrice * usdToInrRate : numericPrice;
			totalInINR += priceInINR;

			const cartItem = `
			<div class="cart-item">
							<img src="${item.image}" alt="${item.title}" class="cart-item-img" width="60" height="60">
							<div class="cart-item-details">
								<h6>${item.title}</h6>
								<h6 class="d-none">${item.courseId}</h6>
								<p>${currency}${numericPrice.toFixed(2)} 
									<small class="text-muted">
										(${currency === '$' ? '₹' + priceInINR.toFixed(2) : 'INR'})
									</small>
								</p>
							</div>
						</div>
			
			
				
			`;
			container.append(cartItem);
		});
        
		$('.cart-total span:last-child').text(`₹${totalInINR.toFixed(2)}`);
	});
}
function getExchangeRate(from = 'USD', to = 'INR', callback = () => {}) {
	const url = `https://open.er-api.com/v6/latest/${from}`;

	fetch(url)
		.then(response => response.json())
		.then(data => {
			if (data && data.rates && data.rates[to]) {
				console.log(`Exchange Rate ${from} -> ${to}:`, data.rates[to]);
				callback(data.rates[to]);
			} else {
				console.warn("Fallback to static rate. Reason:", data?.error || 'Unknown');
				callback(83.0);
			}
		})
		.catch(error => {
			console.error("Error fetching exchange rate:", error);
			callback(83.0);
		});
}

function proceedToCheckOut() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const productIds = cart.map(item => item.courseId);
    const paymentMethod = $('input[name="paymentMethod"]:checked').val();

    const firstName = $("#firstName").val();
    const lastName = $("#lastName").val();
    const email = $("#email").val();
    const address = $("#address").val();
    const city = $("#city").val();
    const zip = $("#zip").val();

	const cardInfo ={};
    if (paymentMethod === "creditCard") {
        cardInfo = {
            cardNumber: $("#cardNumber").val(),
            expiryDate: $("#expiryDate").val(),
            cvv: $("#cvv").val()
        };
    }
	const paymentData = {
		 productIds,
		 paymentMethod,
		 email,
		 address,
		 city,
		 zip,
		 cardInfo
	};
	saveEnrollmentData(paymentData)
}

/*function saveEnrollmentData(data) {
	 console.log("Final Payment Data:", data);
	$.ajax({
		type: "POST",
		url: "student/save-enrollment-details",
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: function(response) {
			console.log("Server Response:", response);
			if (response.code === "success") {
				window.location.href = '/my-profile'; 
			} else {
				console.error("Registration failed:", response.message);
			}
		}
	});
}*/

function saveEnrollmentData(data) {
    console.log("Final Payment Data:", data);
    $.ajax({
        type: "POST",
        url: "student/save-enrollment-details",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function(response) {
            console.log("Server Response:", response);
            if (response.code === "success") {
				localStorage.setItem('cart', JSON.stringify([]));
				renderCartItemsList();
                Swal.fire({
                    title: 'Success!',
                    text: response.message,
                    icon: 'success',
                    confirmButtonText: 'OK'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = '/my-profile';
                    }
                });
            } else {
                console.error("Registration failed:", response.message);
                Swal.fire({
                    title: 'Error!',
                    text: response.message || 'Registration failed. Please try again.',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            }
        },
        error: function(xhr, status, error) {
            console.error("AJAX error:", error);
            Swal.fire({
                title: 'Error!',
                text: 'Something went wrong. Please try again later.',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }
    });
}
