/**
 *  ALL EXTEND RELATED NEW FUNCTIONALITES ARE PRESENT HERE
 */

/* FORM VALIDATION FUNCTION STARTS */
function validationAll(msg, id) {
    if ($('#' + id).val() == null || $('#' + id).val() == "") {
        var div = "<div class='formValidation'>" + msg + "</div>";
        $('#' + id).after(div);
        return false;
    } else {
        return true;
    }
}

function validationModal(msg, id) {
    var div = "<div class='formValidation'>" + msg + "</div>";
    $('#' + id).after(div);
}

//For removing onBlur
function removeValid(event) {
    var id = event.target.id;
    $("#" + id).nextAll('div').remove();
}

function removeValidById(fieldId) {
    var id = fieldId;
    $("#" + id).nextAll('div').remove();
}

/* FORM VALIDATION FUNCTION ENDS */

 

function salaryCheck(event) {
    var id = event.target.id;
    var num = $('#' + id).val();
    var num1 = Number(num).toLocaleString();
    $('#' + id).val(num1);
}

function salaryBack(event) {
    var id = event.target.id;
    if ($('#' + id).val()) {
        var salary = parseFloat($('#' + id).val().replace(/\D/g, ''));
        $('#' + id).val(salary);
    }

}

function validationUpdated(msg, id) {
    $("#" + id).nextAll('div').remove();
    if ($('#' + id).val() == null || $('#' + id).val() == "" || $('#' + id).val() == "null") {

        var div = "<div class='formValidation'>" + msg + "</div>";
        $('#' + id).after(div);
        return false;
    } else {
        return true;
    }
}

/* Date Formatter */
function dateFormat(getDate) {

    var date1 = (getDate).split("-");
    var date = date1[2] + "-" + date1[1] + "-" + date1[0];
    return date;
}

function currencyFormatter(params) {
    return Math.floor(params.value).toString().replace(
        /(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}


//for counting text area
var txtLen = 0;
function textCount(event) {

	var id = event.target.id;
	var pId = $('#' + id).next().attr("id");
	
	$('#' + pId + ' span').empty();
	
	txtLen = $('#' + id).val().length;
	
	$('#' + pId + ' span').append(txtLen);
}

function numberWithCommas(fieldId) {
	var x = $("#"+fieldId).val();
	var formatedNumber = x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	$("#"+fieldId).val(formatedNumber);
}

function addCommas(noValue) {
	if(noValue != null && noValue != ''){ 
		noValue = noValue.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
	}else{
		noValue = '0';
	}
	return noValue;
}

function removeCommas(str) {
	if(str != null && str != '') {
		while (str.search(",") >= 0) {
			str = (str + "").replace(',', '');
		}
	} else {
		str = '0';
	}
	return str;
};