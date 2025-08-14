
function hideShowMultipleElement(attrArray,option) {
	console.log(attrArray)
	attrArray.forEach(n => {
		option == true ? $(n).show() : $(n).hide(); 
	})

}