	$(document).ready(function() {
		renderWishlistItems();
		restoreWishlistUI();
		setWishlist();
	});
	
	
	function toggleWishlist(button) {
		const $btn = $(button);
		const $icon = $btn.find('i');
		const $card = $btn.closest('.course-card');
	
		const productId = $card.find('.course_content_id').text();
		const title = $card.find('.course-title').text().trim();
		const instructor = $card.find('.course-instructor-name').text().trim();
		const price = $card.find('.current-price').text().trim();
		const image = $card.find('.course-image img').attr('src');
		const currencyType = price.charAt(0); // ₹ or $
	
		let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
		const alreadyInWishlist = wishlist.some(item => item.productId === productId);
	
		if (alreadyInWishlist) {
			wishlist = wishlist.filter(item => item.productId !== productId);
			$icon.removeClass('fas').addClass('far');
	
			Swal.fire({
				icon: 'info',
				title: 'Removed from wishlist',
				toast: true,
				position: 'bottom',
				showConfirmButton: false,
				timer: 2000,
				timerProgressBar: true,
				customClass: { popup: 'small-toast' }
			});
		} else {
			wishlist.push({ productId, title, instructor, price, image, currencyType });
			$icon.removeClass('far').addClass('fas');

			const productImage = $card.find('.course-image img')[0];
			if (productImage) {
			    setTimeout(() => {
			        flyToWishlist(productImage, '.dropdown-item .fa-heart');
			    }, 50); // Small delay to ensure DOM is ready
			}
	
			Swal.fire({
				icon: 'success',
				title: 'Added to wishlist',
				toast: true,
				position: 'bottom',
				showConfirmButton: false,
				timer: 2000,
				timerProgressBar: true,
				customClass: { popup: 'small-toast' }
			});
		}
	
		localStorage.setItem('wishlist', JSON.stringify(wishlist));
		renderWishlistItems();
	}
	
	function renderWishlistItems() {
		const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
		$('.wishlist-count, .wishlist-item-count').text(wishlist.length);

		const container = $('#wishlist-items-container');
		container.empty();

		if (wishlist.length === 0) {
			container.append(`
				<div class="text-center text-muted py-3">
					<i class="fas fa-heart-broken fa-2x mb-2"></i>
					<p>No items in wishlist</p>
				</div>
			`);
			$('.wishlist-total span:last-child').text('₹0.00');
			$('.wishlist-footer').hide();
			return;
		}

		getExchangeRate('USD', 'INR', function (usdToInrRate) {
			let totalInINR = 0;

			wishlist.forEach((item, index) => {
				const currency = item.currencyType?.trim() === '$' ? '$' : '₹';
				const cleanPriceStr = (item.price || '').toString().replace(/[^0-9.]/g, '');
				const numericPrice = parseFloat(cleanPriceStr) || 0;

				const priceInINR = (currency === '$') ? numericPrice * usdToInrRate : numericPrice;
				totalInINR += priceInINR;

				const formattedPrice = numericPrice.toLocaleString('en-IN', {
					style: 'currency',
					currency: (currency === '$' ? 'USD' : 'INR'),
					minimumFractionDigits: 2
				});

				const formattedINR = priceInINR.toLocaleString('en-IN', {
					style: 'currency',
					currency: 'INR',
					minimumFractionDigits: 2
				});

				const wishItem = `
					<div class="cart-item">
						<img src="${item.image || 'placeholder.jpg'}" alt="${item.title || 'Item'}" class="cart-item-img">
						<div class="cart-item-details">
							<h6>${item.title || 'Untitled Item'}</h6>
							<p>
								${formattedPrice}
								${currency === '$' ? `<br><small class="text-muted">(${formattedINR})</small>` : ''}
							</p>

							
							<button class="btn-remove-wlist btn-remove-wishlist-page" data-id="${item.productId || index}">
														<i class="fas fa-times"></i>
													</button>
						</div>
					</div>
				`;
				container.append(wishItem);
			});

			const formattedTotal = totalInINR.toLocaleString('en-IN', {
				style: 'currency',
				currency: 'INR',
				minimumFractionDigits: 2
			});
			$('.wishlist-total span:last-child').text(formattedTotal);
			$('.wishlist-footer').show();
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

	
	function restoreWishlistUI() {
		const savedWishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
	
		$('.course-card-wrapper').each(function() {
			const productId = $(this).find('.course_content_id').text().trim();
			const isInWishlist = savedWishlist.some(item => item.productId.trim() === productId);
			const $icon = $(this).find('.wishlist-btn i');
	
			if (isInWishlist) {
				$icon.removeClass('far').addClass('fas');
			} else {
				$icon.removeClass('fas').addClass('far');
			}
		});
	
		$('.wishList-count, .wishlist-count, .wishlist-item-count').text(savedWishlist.length);
	}
	
	
	
	
	
	
	$('#courseTab').on('click', 'button', function() {
		const selectedCategory = $(this).data('category');
	
		$('#courseTab .nav-link').removeClass('active');
		$(this).addClass('active');
	
		$('.course-card-wrapper').each(function() {
			const cardCategory = $(this).data('category');
			if (selectedCategory === 'all' || selectedCategory === cardCategory) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
	});
	
	
	function setWishlist() {
		const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];

		$('.wishlist-count, .wishlist-item-count').text(wishlist.length);

		const $wishlistContainer = $('#wishlistItems');
		const $emptyWishlist = $('#emptyWishlist');
		const $sectionHeader = $('.section-header');

		$wishlistContainer.empty();

		if (wishlist.length === 0) {
			$emptyWishlist.removeClass('d-none');
			/*$sectionHeader.addClass('d-none'); */
			return;
		} else {
			$emptyWishlist.addClass('d-none');
			/*$sectionHeader.removeClass('d-none');*/
		}

		wishlist.forEach((item, index) => {
			const currency = item.currencyType || '₹';
			const cleanPriceStr = item.price.toString().replace(/[^0-9.]/g, '');
			const numericPrice = parseFloat(cleanPriceStr);

			const card = `
				<div class="col-md-6 col-lg-4">
					<div class="wishlist-card">
						<img src="${item.image}" alt="Course Image" class="wishlist-card-img">
						<div class="wishlist-card-body">
							<h5 class="wishlist-card-title">${item.title}</h5>
							<p class="wishlist-card-instructor">By ${item.instructor}</p>
							<p class="wishlist-card-price">${currency}${numericPrice.toFixed(2)}</p>
							<div class="wishlist-card-rating">
								<i class="fas fa-star"></i>
								<i class="fas fa-star"></i>
								<i class="fas fa-star"></i>
								<i class="fas fa-star"></i>
								<i class="far fa-star"></i>
								<span>(4.0)</span>
							</div>
							<div class="wishlist-card-actions">
							   <button class="btn btn-primary btn-sm btn-add-to-cart" data-id="${item.productId}">Add to Cart</button>

								<button class="btn btn-danger btn-sm btn-remove-wishlist-page" data-id="${item.productId}">Remove</button>
							</div>
						</div>
					</div>
				</div>`;
			$wishlistContainer.append(card);
		});
	}

	$(document).on('click', '.btn-remove-wishlist-page', function(e) {
		e.preventDefault();
		e.stopPropagation();

		const productId = $(this).data('id'); // ✅ Correct key
		let wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
		wishlist = wishlist.filter(item => item.productId !== productId);

		localStorage.setItem('wishlist', JSON.stringify(wishlist));		// Re-render

		renderWishlistItems();
		restoreWishlistUI();
		setWishlist();
		renderProfileWishlistItems();
	});
	
	$(document).on('click', '.btn-add-to-cart', function () {
		const productId = $(this).data('id');
		addWishlistItemToCart(productId);
	});

function addWishlistItemToCart(productId) {
	const wishlist = JSON.parse(localStorage.getItem('wishlist')) || [];
	let cart = JSON.parse(localStorage.getItem('cart')) || [];

	const item = wishlist.find(w => w.productId.toString() === productId.toString());
	if (!item) return;

	const alreadyInCart = cart.some(c => c.courseId.toString() === productId.toString());
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

	const cartItem = {
		title: item.title,
		instructor: item.instructor,
		price: item.price,
		image: item.image,
		courseId: item.productId,
		currencyType: item.currencyType
	};

	cart.push(cartItem);
	localStorage.setItem('cart', JSON.stringify(cart));

	renderCartItems();
	setWishlist(); // Refresh wishlist if needed

	Swal.fire({
		icon: 'success',
		title: 'Added to cart!',
		toast: true,
		position: 'bottom',
		showConfirmButton: false,
		timer: 1500,
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
function flyToWishlist(imgElement) {
    if (!imgElement) return;

    const $img = $(imgElement);
    const $target = $('.btn-wishlist'); // Specific to your wishlist icon

    if ($target.length === 0) {
        console.error("Wishlist button not found");
        return;
    }

    const imgOffset = $img.offset();
    const flyerWidth = $img.width();
    const flyerHeight = $img.height();

    const targetRect = $target[0].getBoundingClientRect();
    const scrollTop = window.scrollY || document.documentElement.scrollTop;
    const scrollLeft = window.scrollX || document.documentElement.scrollLeft;

    // Position near top-right corner of the heart button
    const finalLeft = targetRect.left + scrollLeft + targetRect.width - 20;
    const finalTop = targetRect.top + scrollTop + targetRect.height / 2 - 10;

    const $flyer = $img.clone()
        .css({
            position: 'absolute',
            top: imgOffset.top,
            left: imgOffset.left,
            width: flyerWidth,
            height: flyerHeight,
            zIndex: 9999,
            opacity: 0.9,
            borderRadius: '8px',
            pointerEvents: 'none'
        })
        .appendTo('body');

    $flyer.animate({
        top: finalTop,
        left: finalLeft,
        width: 20,
        height: 20,
        opacity: 0.3
    }, 800, 'swing', function () {
        $flyer.remove();
    });
}
