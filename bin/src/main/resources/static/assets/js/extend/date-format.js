function checkValue(str, max) {
    if (str.charAt(0) !== '0' || str == '00') {
        var num = parseInt(str);
        if (isNaN(num) || num <= 0 || num > max) num = 1;
        str = num > parseInt(max.toString().charAt(0)) && num.toString().length == 1 ? '0' + num : num.toString();
    };
    return str;
}

function checkValueZero(str, max) {
	if(str > max) {
		if(str.charAt(0) > max.toString().charAt(0)) {
			str = 0;
		}
		if(str.length > 1) {
			str = str.substr(0, 1);
		}
		
	}
	return str;
}

function formatDate(fieldId,dateFormat) {
	
    var date = document.getElementById(fieldId);
    
    /** DD-MM-YYYY **/
    if(dateFormat == 'd-m-Y') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split('-').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[0]) values[0] = checkValue(values[0], 31);
            if (values[1]) values[1] = checkValue(values[1], 12);
            var output = values.map(function(v, i) {
                return v.length == 2 && i < 2 ? v + '-' : v;
            });
            this.value = output.join('').substr(0, 10); 
    	});
    }
    
    /** YYYY-MM-DD **/
    if(dateFormat == 'Y-m-d') {
    	date.addEventListener('input', function(e) {
            this.type = 'text';
            var input = this.value;
            var values = input.split('-').map(function(v) {
                return v.replace(/\D/g, '')
            });
            
            if (values[0]) values[0] = checkValue(values[0], 9999);
            if (values[1]) values[1] = checkValue(values[1], 12);
            if (values[2]) values[2] = checkValue(values[2], 31);
            
            var dataset1 = [];
            dataset1.push(values[0]);
            var output1 = dataset1.map(function(v, i) {
            	return v.length == 4 && i < 2 ? v + '-' : v ;
            });
            
            var dataset2 = [];
            var output2 = "";
            if(values[1] != null && values[1] != "") {
            	dataset2.push(values[1]);
                output2 = dataset2.map(function(v1, i) {
                	return v1.length == 2 && i < 2 ? v1 + '-' : v1 ;
                });
            }
            
            var dataset3 = [];
            var output3 = "";
            if(values[2] != null && values[2] != "") {
            	dataset3.push(values[2]);
            	output3 = dataset3.map(function(v2, i) {
                	return v2.length == 2 && i < 2 ? v2 + '-' : v2 ;
                });
            }
            
            var output = output1 + output2 + output3;
            this.value = output.substr(0, 10);
        });
    }
    
    /** MM-DD-YYYY **/
    if(dateFormat == 'm-d-Y') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split('-').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[0]) values[0] = checkValue(values[0], 12);
    		if (values[1]) values[1] = checkValue(values[1], 31);
    		var output = values.map(function(v, i) {
    			return v.length == 2 && i < 2 ? v + '-' : v;
    		});
    		this.value = output.join('').substr(0, 10); 
    	});
    }
    
    /** MM-YYYY-DD **/
    if(dateFormat == 'm-Y-d') {
    	date.addEventListener('input', function(e) {
            this.type = 'text';
            var input = this.value;
            var values = input.split('-').map(function(v) {
                return v.replace(/\D/g, '')
            });
            
            if (values[0]) values[0] = checkValue(values[0], 12);
            if (values[1]) values[1] = checkValue(values[1], 9999);
            if (values[2]) values[2] = checkValue(values[2], 31);
            
            var dataset1 = [];
            dataset1.push(values[0]);
            var output1 = dataset1.map(function(v, i) {
            	return v.length == 2 && i < 2 ? v + '-' : v ;
            });
            
            var dataset2 = [];
            var output2 = "";
            if(values[1] != null && values[1] != "") {
            	dataset2.push(values[1]);
                output2 = dataset2.map(function(v1, i) {
                	return v1.length == 4 && i < 2 ? v1 + '-' : v1 ;
                });
            }
            
            var dataset3 = [];
            var output3 = "";
            if(values[2] != null && values[2] != "") {
            	dataset3.push(values[2]);
            	output3 = dataset3.map(function(v2, i) {
                	return v2.length == 2 && i < 2 ? v2 + '-' : v2 ;
                });
            }
            
            var output = output1 + output2 + output3;
            this.value = output.substr(0, 10);
        });
    }
    
    /** YYYY-DD-MM **/
    if(dateFormat == 'Y-d-m') {
    	date.addEventListener('input', function(e) {
            this.type = 'text';
            var input = this.value;
            var values = input.split('-').map(function(v) {
                return v.replace(/\D/g, '')
            });
            
            if (values[0]) values[0] = checkValue(values[0], 9999);
            if (values[1]) values[1] = checkValue(values[1], 31);
            if (values[2]) values[2] = checkValue(values[2], 12);
            
            var dataset1 = [];
            dataset1.push(values[0]);
            var output1 = dataset1.map(function(v, i) {
            	return v.length == 4 && i < 2 ? v + '-' : v ;
            });
            
            var dataset2 = [];
            var output2 = "";
            if(values[1] != null && values[1] != "") {
            	dataset2.push(values[1]);
                output2 = dataset2.map(function(v1, i) {
                	return v1.length == 2 && i < 2 ? v1 + '-' : v1 ;
                });
            }
            
            var dataset3 = [];
            var output3 = "";
            if(values[2] != null && values[2] != "") {
            	dataset3.push(values[2]);
            	output3 = dataset3.map(function(v2, i) {
                	return v2.length == 2 && i < 2 ? v2 + '-' : v2 ;
                });
            }
            
            var output = output1 + output2 + output3;
            this.value = output.substr(0, 10);
        });
    }
    
    /** DD-MM-YY **/
    if(dateFormat == 'd-m-y') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split('-').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[0]) values[0] = checkValue(values[0], 31);
    		if (values[1]) values[1] = checkValue(values[1], 12);
    		var output = values.map(function(v, i) {
    			return v.length == 2 && i < 2 ? v + '-' : v;
    		});
    		this.value = output.join('').substr(0, 8); 
    	});
    }
    
    /** DD-YY-MM **/
    if(dateFormat == 'd-y-m') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split('-').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[0]) values[0] = checkValue(values[0], 31);
    		if (values[1]) values[1] = checkValue(values[1], 99);
    		if (values[2]) values[2] = checkValue(values[2], 12);
    		var output = values.map(function(v, i) {
    			return v.length == 2 && i < 2 ? v + '-' : v;
    		});
    		this.value = output.join('').substr(0, 8); 
    	});
    }
    
    /** YY-DD-MM **/
    if(dateFormat == 'y-d-m') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split('-').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[2]) values[2] = checkValue(values[2], 12);
    		if (values[1]) values[1] = checkValue(values[1], 31);
    		if (values[0]) values[0] = checkValue(values[0], 99);
    		var output = values.map(function(v, i) {
    			return v.length == 2 && i < 2 ? v + '-' : v;
    		});
    		this.value = output.join('').substr(0, 8); 
    	});
    }
    
    /** YY-MM-DD **/
    if(dateFormat == 'y-m-d') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split('-').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[2]) values[2] = checkValue(values[2], 31);
    		if (values[1]) values[1] = checkValue(values[1], 12);
    		if (values[0]) values[0] = checkValue(values[0], 99);
    		var output = values.map(function(v, i) {
    			return v.length == 2 && i < 2 ? v + '-' : v;
    		});
    		this.value = output.join('').substr(0, 8); 
    	});
    }
    
    /** HH-MM-SS **/
    if(dateFormat == 'HHMMSS') {
    	date.addEventListener('input', function(e) {
    		this.type = 'text';
    		var input = this.value;
    		var values = input.split(':').map(function(v) {
    			return v.replace(/\D/g, '')
    		});
    		
    		if (values[2]) values[2] = checkValueZero(values[2], 59);
    		if (values[1]) values[1] = checkValueZero(values[1], 59);
    		if (values[0]) values[0] = checkValueZero(values[0], 23);
    		var output = values.map(function(v, i) {
    			return v.length == 2 && i < 2 ? v + ':' : v;
    		});
    		this.value = output.join('').substr(0, 8); 
    	});
    }
    
}