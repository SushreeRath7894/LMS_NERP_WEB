$(document).ready(function() {
	renderCartItems();
	// Handle item removal
	$(document).on('click', '.cart-item-remove-btn', function() {
		const index = $(this).data('index');
		let cart = JSON.parse(localStorage.getItem('cart')) || [];

		cart.splice(index, 1);
		localStorage.setItem('cart', JSON.stringify(cart));
		setTimeout(() => {
			renderCartItems();
		}, 0);
	});

});

function renderCartItems() {
	const cart = JSON.parse(localStorage.getItem('cart')) || [];
	$('.cart-count').text(cart.length);
	$('.cart_item_count').text(cart.length);

	const tableBody = $('.cart-item-table tbody');
	const $cartContainer = $('#cart-container');

	if (cart.length === 0) {
		$("#cart-item-table-container").addClass("d-none");
		$("#cart-container").removeClass("d-none");
		$cartContainer.html(`
			<div class="empty-cart-container">
				<i class="fas fa-shopping-cart fa-2x mb-2"></i>
				<h2>Your Cart is Empty</h2>
				<p>Looks like you haven’t added anything to your cart yet.</p>
				<a href="/shop" class="btn">Continue Shopping</a>
			</div>
		`);
		return;
	}

	$("#cart-item-table-container").removeClass("d-none");
	$("#cart-container").addClass("d-none");
	tableBody.empty();

	getExchangeRate('USD', 'INR', function (usdToInrRate) {
		let totalInINR = 0;

		cart.forEach((item, index) => {
			const cleanPriceStr = item.price.toString().replace(/[^0-9.]/g, '');
			const numericPrice = parseFloat(cleanPriceStr);
			const currency = item.currencyType || '₹';

			const priceInINR = (currency === '$') ? numericPrice * usdToInrRate : numericPrice;
			totalInINR += priceInINR;

			const row = `
				<tr class="course-cart-items">
					<td>
						<img src="${item.image}" alt="${item.title}" class="cart-item-img" width="80">
					</td>
					<td>
						<div class="cart-item-details">
							<h6>${item.title}</h6>
							<p>By ${item.instructor}</p>
						</div>
					</td>
					<td>
						${currency}${numericPrice.toFixed(2)}
						${currency === '$' ? `<br><small class="text-muted">₹${priceInINR.toFixed(2)}</small>` : ''}
					</td>
					<td>
						<button class="cart-item-remove-btn" data-index="${index}">
							<i class="fas fa-trash-alt"></i>
						</button>
					</td>
				</tr>
			`;
			tableBody.append(row);
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

function getUserDetails() {
	const userId = $('#userId').val();
	let path = window.location.pathname;
	if (!userId) {
	    window.location.href = '/setRedirectAndLogin?url='+path;
	} else {
	    window.location.href = '/checkout';
	}
}
