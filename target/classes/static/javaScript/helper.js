//Helper Functions
function showError(message) {
    toastr.error(message);
    return false;
}

function showLoader() {
    $('body').addClass('overlay');
    $('.loader').show();
}

function hideLoader() {
    $('body').removeClass('overlay');
    $('.loader').hide();
}

function indianNumberFormatterWithDecimal(params) {

	if (params.data.level === 'L1') {
		if (params.value === null || params.value === undefined || isNaN(params.value)) return null;
	} else {
		if (params.value === null || params.value === undefined || isNaN(params.value)) return '0.00';
	}

	const value = parseFloat(params.value).toFixed(2); // Ensure two decimal places
	const parts = value.split("."); // Split integer and decimal part
	const integerPart = parts[0]; // Integer part
	const decimalPart = parts[1]; // Decimal part (always two digits)

	// Handle numbers below 1000 separately
	if (integerPart.length <= 3) {
		return integerPart + "." + decimalPart;
	}

	// Format for numbers >= 1000
	const lastThree = integerPart.substring(integerPart.length - 3);
	const otherNumbers = integerPart.substring(0, integerPart.length - 3);
	const formattedValue =
		(otherNumbers ? otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," : "") + lastThree;

	return formattedValue + "." + decimalPart; // Append decimal part
}

function commaFormattedWithDecimal(amount) {

	if (amount === null || amount === undefined || isNaN(amount)) return '0.00';

	const value = parseFloat(amount).toFixed(2); // Ensure two decimal places
	const parts = value.split("."); // Split integer and decimal part
	const integerPart = parts[0]; // Integer part
	const decimalPart = parts[1]; // Decimal part (always two digits)

	// Handle numbers below 1000 separately
	if (integerPart.length <= 3) {
		return integerPart + "." + decimalPart;
	}

	// Format for numbers >= 1000
	const lastThree = integerPart.substring(integerPart.length - 3);
	const otherNumbers = integerPart.substring(0, integerPart.length - 3);
	const formattedValue =
		(otherNumbers ? otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + "," : "") + lastThree;

	return formattedValue + "." + decimalPart; // Append decimal part
}

const units = [
  "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
  "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
  "Seventeen", "Eighteen", "Nineteen"
];

const tens = [
  "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
];

function convertTwoDigit(number) {
  if (number < 20) return units[number];
  return tens[Math.floor(number / 10)] + (number % 10 !== 0 ? " " + units[number % 10] : "");
}

function convertPart(number, suffix) {
  return number > 0 ? convertNumber(number) + " " + suffix + " " : "";
}

function convertNumber(number) {
  if (number === 0) return "Zero";

  let result = "";

  result += convertPart(Math.floor(number / 10000000), "Crore");
  number %= 10000000;

  result += convertPart(Math.floor(number / 100000), "Lakh");
  number %= 100000;

  result += convertPart(Math.floor(number / 1000), "Thousand");
  number %= 1000;

  result += convertPart(Math.floor(number / 100), "Hundred");
  number %= 100;

  if (number > 0 && result !== "") {
    result += "and ";
  }

  result += convertTwoDigit(number);
  return result.trim();
}

function convertToWords(amount) {
  const parts = amount.toFixed(2).split(".");
  const rupees = parseInt(parts[0], 10);
  const paise = parseInt(parts[1], 10);

  let words = "Rupees " + convertNumber(rupees);
  if (paise > 0) {
    words += " and " + convertNumber(paise) + " Paise";
  }
  words += " Only";
  return words;
}
function removeSpecialChars(input) {
	input.value = input.value.replace(/[^a-zA-Z0-9 ,./]/g, '');
}