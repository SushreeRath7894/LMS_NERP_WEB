function getActivityTimelineProducts(response) {
	$("#products-timeline-section").empty();
    // Object to store activities grouped by date
    let activityObjectByDate = {};

    // Group activities by date
    response.forEach(activity => {
        const { createdOn } = activity;
        if (!activityObjectByDate[createdOn]) {
            activityObjectByDate[createdOn] = [];
        }
        activityObjectByDate[createdOn].push(activity);
    });

    // Convert grouped activities into an array of objects
    const result = Object.keys(activityObjectByDate).map(date => ({
        date,
        activities: activityObjectByDate[date]
    }));

    // Generate HTML for each date and its activities
    result.forEach((data, count) => {
        let activityHtml = "";
        data.activities.forEach(activity => {
            activityHtml += generateActivityHTML(activity);
        });

        const dateHeaderHtml = `<div class="timeLineHistDate pB20" id="todayDateUnderHistoryPr_${count}">${data.date}</div>`;
        const activityListHtml = `<ul class="timeLineHistDataList" id="addActivityCls_${count}">${activityHtml}</ul>`;
        $("#products-timeline-section").append(dateHeaderHtml + activityListHtml);
    });
}
// Function to generate HTML for each activity
function generateActivityHTML(activity) {
    let activityHtml = "";
    const maxVisibleProducts = 5;
    const products = activity.productId.split(', ');
    const visibleProducts = products.slice(0, maxVisibleProducts);
    const hiddenProducts = products.slice(maxVisibleProducts);

    const visibleProductsHtml = visibleProducts.map(product => `<a style="color: #7c7b7c">${product}</a>`).join(', ');
    const hiddenProductsHtml = hiddenProducts.map(product => `<a style="color: #7c7b7c">${product}</a>`).join(', ');

    let productsHtml = visibleProductsHtml;
    if (hiddenProducts.length > 0) {
        productsHtml += `<span class="more-products" style="display:none;">, ${hiddenProductsHtml}</span>`;
        productsHtml += ` <a href="#" class="toggle-products">show more</a>`;
    }

    const baseHtml = `
        <li class="timeline_added Leads">
            <span class="timeLineHistTime crm-font-regular f13 fL">${activity.createdTime}
                <div class="grey-bg tm-bg"><i class="fa ${getIconClass(activity.activityName)} timeline-dsg" aria-hidden="true" style="color: ${getIconColor(activity.activityName)}!important;"></i></div>
            </span>
            <span class="crm-font-regular f15 fL timeLineHistCont pR">${activity.activityName}
                <div>
                    <span class="tl_by">By </span>
                    <span class="f13 timeline-owner-dsg" purpose="showUserBC" data-params="{&quot;userId&quot;:&quot;5249707000000361001&quot;}">${activity.leadOwner}</span>
                    <span class="tl_dat"> At ${activity.createdOn}</span>
                </div>
                <div>
                    <span class="tl_prod_id">Products: ${productsHtml}</span>
                </div>
            </span>
        </li>`;

    return baseHtml;
}

// Function to get the icon class based on activity name
function getIconClass(activityName) {
    switch(activityName) {
        case "Product Deleted":
            return "fa-trash";
        case "Product Added":
            return "fa-cart-plus";
        case "New Product Added":
            return "fa-cube";
        default:
            return "";
    }
}

// Function to get the icon color based on activity name
function getIconColor(activityName) {
    switch(activityName) {
        case "Product Deleted":
            return "red";
        case "Product Added":
            return "white";  // Default color
        case "New Product Added":
            return "white";
        default:
            return "white";
    }
}

// JavaScript to handle the "show more/show less" functionality
document.addEventListener('click', function(event) {
    if (event.target.classList.contains('toggle-products')) {
        event.preventDefault();
        const toggleLink = event.target;
        const moreProducts = toggleLink.previousElementSibling;
        if (moreProducts.style.display === 'none') {
            moreProducts.style.display = 'inline';
            toggleLink.textContent = 'show less';
        } else {
            moreProducts.style.display = 'none';
            toggleLink.textContent = 'show more';
        }
    }
});
