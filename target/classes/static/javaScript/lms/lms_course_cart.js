$(document).ready(function() {

	const cart = JSON.parse(localStorage.getItem('cart')) || [];


	renderCartItemsList();

	$(document).on('click', '.btn-remove', function(e) {
		e.stopPropagation();
		e.preventDefault();
		const index = $(this).data('index');
		const cart = JSON.parse(localStorage.getItem('cart')) || [];

		cart.splice(index, 1);
		localStorage.setItem('cart', JSON.stringify(cart));

		renderCartItemsList();

	});
});


function addToCartBtn(event) {
	const cart = JSON.parse(localStorage.getItem('cart')) || [];

	const card = $(event).closest('.course-card');
	const courseId = card.find('.course_content_id').text();
	const title = card.find('.course-title').text().trim();
	const instructor = card.find('.course-instructor-name').text().trim();
	const price = card.find('.current-price').text();
	const image = card.find('.course-image img').attr('src');
	const currencyType = price.trim().charAt(0);

	const alreadyInCart = cart.some(item => item.courseId === courseId);

	if (alreadyInCart) {
		Swal.fire({
			icon: 'warning',
			title: 'Item already in cart!',
			toast: true,
			position: 'bottom',
			showConfirmButton: false,
			timer: 2000,
			timerProgressBar: true,
			customClass: {
				popup: 'small-toast'
			}
		});
		return;
	}

	const item = { title, instructor, price, image, courseId, currencyType };
	cart.push(item);
	localStorage.setItem('cart', JSON.stringify(cart));
	Swal.fire({
		icon: 'success',
		title: 'Item added to cart successfully.',
		toast: true,
		position: 'bottom',
		showConfirmButton: false,
		timer: 2000,
		timerProgressBar: true,
		customClass: {
			popup: 'small-toast'
		}
	});

	renderCartItemsList();
}


function renderCartItemsList() {
	const cart = JSON.parse(localStorage.getItem('cart')) || [];

	$('.cart-count').text(cart.length);
	$('.cart_item_count').text(cart.length);

	const container = $('#cart-items-container');
	container.empty();

	if (cart.length === 0) {
		container.append(`
			<div class="text-center text-muted py-3">
				<i class="fas fa-shopping-cart fa-2x mb-2"></i>
				<p>Your cart is empty</p>
			</div>
		`);
		$('.cart-total span:last-child').text('₹0.00');
		$('.cart-footer').hide();
		return;
	}

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
					<img src="${item.image}" alt="Course" class="cart-item-img">
					<div class="cart-item-details">
						<h6>${item.title}</h6>
						<p>${currency}${numericPrice.toFixed(2)} 
							<small class="text-muted">
								(${currency === '$' ? '₹' + priceInINR.toFixed(2) : 'INR'})
							</small>
						</p>
						<button class="btn-remove" data-index="${index}">
							<i class="fas fa-times"></i>
						</button>
					</div>
				</div>
			`;
			container.append(cartItem);
		});

		$('.cart-total span:last-child').text(`₹${totalInINR.toFixed(2)}`);
		$('.cart-footer').show();
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








