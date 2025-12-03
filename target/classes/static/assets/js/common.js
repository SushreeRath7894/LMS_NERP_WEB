/* Nirmalya Labs Common javascript File on 07 June 2024 */

/*############################################################# Start ############################################################*/
/*function for indian Number Formatter*/
function indianNumberFormatter(params) {
    if (!params.value) return '0';
    const value = parseFloat(params.value).toFixed(0); // Convert to integer and remove decimal places
    const x = value.toString();
    const lastThree = x.substring(x.length - 3);
    const otherNumbers = x.substring(0, x.length - 3);
    const formattedValue = otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + (otherNumbers ? ',' : '') + lastThree;
    return formattedValue;
}

/*function for Number Formatter*/
function numberFormatter(params) {
	return params.value ? params.value.toLocaleString() : '';
}

 